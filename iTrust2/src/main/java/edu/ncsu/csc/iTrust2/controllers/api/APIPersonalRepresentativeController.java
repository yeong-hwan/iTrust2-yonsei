package edu.ncsu.csc.iTrust2.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.iTrust2.forms.PersonalRepresentativeForm;
import edu.ncsu.csc.iTrust2.models.Drug;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.PersonalRepresentative;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.PersonalRepresentativeService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
// import io.swagger.annotations.Api;

import org.springframework.http.HttpStatus;

// @Api(tags = { "API 정보를 제공하는 Controller" })
// @RequestMapping("/api")
@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
public class APIPersonalRepresentativeController extends APIController {

  @Autowired
  private LoggerUtil loggerUtil;

  @Autowired
  private PersonalRepresentativeService personalRepresentativeService;

  @Autowired
  private UserService userService;

  @Autowired
  private PatientService patientService;

  @GetMapping(BASE_PATH + "/personal_representatives/patients")
  public List<Patient> getPatient() {

    // Except for myself
    final User self = userService.findByName(LoggerUtil.currentUser());

    final List<Patient> allPatients = (List<Patient>) patientService.findAll();

    allPatients.removeIf(patient -> patient.getUsername().equals(self.getUsername()));

    // Except for already assigned patients
    final List<PersonalRepresentative> alreadyPersonalRepresentative = personalRepresentativeService
        .findByAssginorContains(self.getUsername());

    allPatients.removeIf(patient -> alreadyPersonalRepresentative.stream()
        .anyMatch(rep -> rep.getAssignee().equals(patient.getUsername())));

    return allPatients;
  }

  @PostMapping(BASE_PATH +
      "/personal_representatives/assign_assignee/{assignee}")
  public ResponseEntity assignAssignee(@PathVariable("assignee") String assignee) {
    try {
      final PersonalRepresentative personalRepresentative = new PersonalRepresentative();

      final User self = userService.findByName(LoggerUtil.currentUser());
      personalRepresentative.setAssignor(self.getUsername());
      personalRepresentative.setAssignee(assignee);
      personalRepresentativeService.save(personalRepresentative);

      return new ResponseEntity(personalRepresentative, HttpStatus.OK);

    } catch (final Exception e) {
      return new ResponseEntity(
          errorResponse("Error: " + e.getMessage()),
          HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(BASE_PATH +
      "/personal_representatives/assign_assignor/{assignor}")
  public ResponseEntity assignAssignor(@PathVariable("assignor") String assignor) {
    try {
      final PersonalRepresentative personalRepresentative = new PersonalRepresentative();

      final User self = userService.findByName(LoggerUtil.currentUser());
      personalRepresentative.setAssignor(assignor);
      personalRepresentative.setAssignee(self.getUsername());
      personalRepresentativeService.save(personalRepresentative);

      return new ResponseEntity(personalRepresentative, HttpStatus.OK);

    } catch (final Exception e) {
      return new ResponseEntity(
          errorResponse("Error: " + e.getMessage()),
          HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(BASE_PATH +
      "/personal_representatives/view/assignee")
  public ResponseEntity viewAssignee() {
    final User self = userService.findByName(LoggerUtil.currentUser());

    final List<PersonalRepresentative> assignorAssigneePair = personalRepresentativeService
        .findByAssginorContains(self.getUsername());

    return ResponseEntity.ok(assignorAssigneePair);
  }

  @GetMapping(BASE_PATH +
      "/personal_representatives/view/assignor")
  public ResponseEntity viewAssignor() {
    final User self = userService.findByName(LoggerUtil.currentUser());

    final List<PersonalRepresentative> assigneeAssignorPair = personalRepresentativeService
        .findByAssgineeContains(self.getUsername());

    return ResponseEntity.ok(assigneeAssignorPair);
  }

  @DeleteMapping(BASE_PATH +
      "/personal_representatives/release_assignor/{assignor}")
  public ResponseEntity releaseAssignor(@PathVariable("assignor") String assignor) {
    try {
      final User self = userService.findByName(LoggerUtil.currentUser());

      final String assignee = self.getUsername();

      final List<PersonalRepresentative> personalRepresentative = (List<PersonalRepresentative>) personalRepresentativeService
          .findByAssginorAndAssigneeContains(assignor, assignee);

      personalRepresentativeService.deleteLoop(personalRepresentative);

      return new ResponseEntity(personalRepresentative, HttpStatus.OK);

    } catch (final Exception e) {
      return new ResponseEntity(errorResponse("Error: " + e.getMessage()),
          HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping(BASE_PATH +
      "/personal_representatives/release_assignee/{assignee}")
  public ResponseEntity releaseAssignee(@PathVariable("assignee") String assignee) {
    try {
      final User self = userService.findByName(LoggerUtil.currentUser());

      final String assignor = self.getUsername();

      final List<PersonalRepresentative> personalRepresentative = (List<PersonalRepresentative>) personalRepresentativeService
          .findByAssginorAndAssigneeContains(assignor, assignee);

      personalRepresentativeService.deleteLoop(personalRepresentative);

      return new ResponseEntity(personalRepresentative, HttpStatus.OK);

    } catch (final Exception e) {
      return new ResponseEntity(errorResponse("Error: " + e.getMessage()),
          HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(BASE_PATH +
      "/personal_representatives/view/assignor/{assignee}")
  public ResponseEntity viewPatientAssignor(@PathVariable("assignee") String assignee) {
    try {

      final List<PersonalRepresentative> personalRepresentative = personalRepresentativeService
          .findByAssgineeContains(assignee);

      return ResponseEntity.ok(personalRepresentative);

    } catch (final Exception e) {
      return new ResponseEntity(errorResponse("Error: " + e.getMessage()),
          HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(BASE_PATH +
      "/personal_representatives/view/assignee/{assignor}")
  public ResponseEntity viewPatientAssignee(@PathVariable("assignor") String assignor) {
    try {

      final List<PersonalRepresentative> personalRepresentative = personalRepresentativeService
          .findByAssginorContains(assignor);

      return ResponseEntity.ok(personalRepresentative);

    } catch (final Exception e) {
      return new ResponseEntity(errorResponse("Error: " + e.getMessage()),
          HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(BASE_PATH + "/personal_representatives/assign_relationship/{assignee}/{assignor}")
  public ResponseEntity assignRelationship(@PathVariable("assignee") String assignee,
      @PathVariable("assignor") String assignor) {
    try {
      final PersonalRepresentative personalRepresentative = new PersonalRepresentative();

      personalRepresentative.setAssignee(assignee);
      personalRepresentative.setAssignor(assignor);

      personalRepresentativeService.save(personalRepresentative);

      return new ResponseEntity(personalRepresentative, HttpStatus.OK);

    } catch (final Exception e) {
      return new ResponseEntity(errorResponse("Error: " + e.getMessage()),
          HttpStatus.BAD_REQUEST);
    }
  }
}
