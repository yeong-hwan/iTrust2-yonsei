package edu.ncsu.csc.iTrust2.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.PersonalRepresentative;
import edu.ncsu.csc.iTrust2.models.Personnel;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.services.PersonalRepresentativeService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.models.Personnel;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.iTrust2.controllers.api.APIPersonalRepresentativeController;
import edu.ncsu.csc.iTrust2.forms.PersonalRepresentativeForm;
import edu.ncsu.csc.iTrust2.forms.UserForm;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.iTrust2.services.PersonalRepresentativeService;
import edu.ncsu.csc.iTrust2.services.UserService;
import io.cucumber.java.Before;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class APIPersonalRepresentativeTest {
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private PersonalRepresentativeService personalRepresentativeService;

  @Autowired
  private UserService userService;

  // mvc=MockMvcBuilders.webAppContextSetup(context).build();
  // personalRepresentativeService.deleteAll();

  User patient = new Patient(new UserForm("patient", "123456", Role.ROLE_PATIENT, 1));
  User hcp = new Personnel(new UserForm("hcp", "123456", Role.ROLE_HCP, 1));

  // @Test
  // @Transactional
  // @WithMockUser(username = "patient", roles = { "PATIENT" })
  // public void assignAssignee() throws Exceptio

  // mvc.perform(post("api/v1/lab"))
  // }

  private final APIPersonalRepresentativeController controller = new APIPersonalRepresentativeController();

  private User mockUser(String username) {
    User user = mock(User.class);
    when(user.getUsername()).thenReturn(username);
    // Add any other necessary behaviors for your User mock
    return user;
  }

  // -----------------------------------------------

  // @Test
  // @Transactional
  // @WithMockUser(username = "patient", roles = { "PATIENT" })
  // void testAssignAssignee() {
  // when(userService.findByName("patient")).thenReturn(patient);

  // ResponseEntity response = controller.assignAssignee("assignee");

  // assertEquals(HttpStatus.OK, response.getStatusCode());
  // }

  // ------------------------------

  // @Test
  // void testAssignAssignor() {
  // when(userService.findByName("currentUser")).thenReturn(new
  // User("currentUser"));

  // ResponseEntity response = controller.assignAssignor("assignor");

  // assertEquals(HttpStatus.OK, response.getStatusCode());
  // }

  // @Test
  // void testViewAssignee() {
  // when(userService.findByName("currentUser")).thenReturn(new
  // User("currentUser"));
  // when(personalRepresentativeService.findByAssginorContains("currentUser"))
  // .thenReturn(Arrays.asList(new PersonalRepresentative()));

  // ResponseEntity<List<PersonalRepresentative>> response =
  // controller.viewAssignee();

  // assertEquals(HttpStatus.OK, response.getStatusCode());
  // assertEquals(1, response.getBody().size());
  // }

  // @Test
  // void testViewAssignor() {
  // when(userService.findByName("currentUser")).thenReturn(new
  // User("currentUser", "1234", Role.ROLE_PATIENT, 1);
  // when(personalRepresentativeService.findByAssgineeContains("currentUser"))
  // .thenReturn(Arrays.asList(new PersonalRepresentative()));

  // ResponseEntity<List<PersonalRepresentative>> response =
  // controller.viewAssignor();

  // assertEquals(HttpStatus.OK, response.getStatusCode());
  // assertEquals(1, response.getBody().size());
  // }

  // @Test
  // void testReleaseAssignor() {
  // when(userService.findByName("currentUser")).thenReturn(new
  // User("currentUser"));
  // when(personalRepresentativeService.findByAssginorAndAssigneeContains("assignor",
  // "currentUser"))
  // .thenReturn(Arrays.asList(new PersonalRepresentative()));

  // ResponseEntity response = controller.releaseAssignor("assignor");

  // assertEquals(HttpStatus.OK, response.getStatusCode());
  // }

  // @Test
  // void testReleaseAssignee() {
  // when(userService.findByName("currentUser")).thenReturn(new
  // User("currentUser"));
  // when(personalRepresentativeService.findByAssginorAndAssigneeContains("currentUser",
  // "assignee"))
  // .thenReturn(Arrays.asList(new PersonalRepresentative()));

  // ResponseEntity response = controller.releaseAssignee("assignee");

  // assertEquals(HttpStatus.OK, response.getStatusCode());
  // }

  // @Test
  // void testViewPatientAssignor() {
  // when(personalRepresentativeService.findByAssgineeContains("assignee"))
  // .thenReturn(Arrays.asList(new PersonalRepresentative()));

  // ResponseEntity<List<PersonalRepresentative>> response =
  // controller.viewPatientAssignor("assignee");

  // assertEquals(HttpStatus.OK, response.getStatusCode());
  // assertEquals(1, response.getBody().size());
  // }

  // @Test
  // void testViewPatientAssignee() {
  // when(personalRepresentativeService.findByAssginorContains("assignor"))
  // .thenReturn(Arrays.asList(new PersonalRepresentative()));

  // ResponseEntity<List<PersonalRepresentative>> response =
  // controller.viewPatientAssignee("assignor");

  // assertEquals(HttpStatus.OK, response.getStatusCode());
  // assertEquals(1, response.getBody().size());
  // }

  // @Test
  // void testAssignRelationship() {
  // ResponseEntity response = controller.assignRelationship("assignee",
  // "assignor");

  // assertEquals(HttpStatus.OK, response.getStatusCode());
  // }

}
