package edu.ncsu.csc.iTrust2.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import edu.ncsu.csc.iTrust2.models.Hospital;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.PersonalRepresentative;
import edu.ncsu.csc.iTrust2.models.Personnel;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.services.PersonalRepresentativeService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.models.Personnel;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import edu.ncsu.csc.iTrust2.models.enums.AppointmentType;
import edu.ncsu.csc.iTrust2.models.enums.BloodType;
import edu.ncsu.csc.iTrust2.models.enums.Ethnicity;
import edu.ncsu.csc.iTrust2.models.enums.Gender;
import edu.ncsu.csc.iTrust2.models.enums.HouseholdSmokingStatus;
import edu.ncsu.csc.iTrust2.models.enums.PatientSmokingStatus;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.models.enums.State;
import edu.ncsu.csc.iTrust2.models.enums.Status;
import java.time.LocalDate;

import edu.ncsu.csc.iTrust2.common.TestUtils;
import edu.ncsu.csc.iTrust2.controllers.api.APIPersonalRepresentativeController;
import edu.ncsu.csc.iTrust2.forms.PatientForm;
import edu.ncsu.csc.iTrust2.forms.PersonalRepresentativeForm;
import edu.ncsu.csc.iTrust2.forms.UserForm;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.cucumber.java.Before;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class APIPersonalRepresentativeTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private PersonalRepresentativeService personalRepresentativeService;

  @Autowired
  private UserService userService;

  @Autowired
  private PatientService patientService;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    personalRepresentativeService.deleteAll();

    final User patient_1 = new Patient(new UserForm("patient_1", "123456", Role.ROLE_PATIENT, 1));
    final User patient_2 = new Patient(new UserForm("patient_2", "123456", Role.ROLE_PATIENT, 1));

    final User hcp = new Personnel(new UserForm("hcp", "123456", Role.ROLE_HCP, 1));

    // final Patient antti = buildPatient();

    // userService.saveAll(List.of(patient, hcp, antti));

    // final Hospital hosp = new Hospital();
    // hosp.setAddress("123 Raleigh Road");
    // hosp.setState(State.NC);
    // hosp.setZip("27514");
    // hosp.setName("iTrust Test Hospital 2");

    // hospitalService.save(hosp);
    userService.saveAll(List.of(patient_1, patient_2, hcp));
  }

  private Patient buildPatient() {
    final Patient antti = new Patient(new UserForm("antti", "123456", Role.ROLE_PATIENT, 1));

    antti.setAddress1("1 Test Street");
    antti.setAddress2("Some Location");
    antti.setBloodType(BloodType.APos);
    antti.setCity("Viipuri");
    final LocalDate date = LocalDate.of(1977, 6, 15);
    antti.setDateOfBirth(date);
    antti.setEmail("antti@itrust.fi");
    antti.setEthnicity(Ethnicity.Caucasian);
    antti.setFirstName("Antti");
    antti.setGender(Gender.Male);
    antti.setLastName("Walhelm");
    antti.setPhone("123-456-7890");
    antti.setState(State.NC);
    antti.setZip("27514");

    return antti;
  }

  private final APIPersonalRepresentativeController controller = new APIPersonalRepresentativeController();

  // @Test
  // @Transactional
  // public void basicTest() {
  // assertEquals(1, 1);
  // }
  // -----------------------------------------------

  @Test
  @Transactional
  @WithMockUser(username = "hcp", roles = { "HCP" })
  public void testHCPViewAssignor() throws Exception {
    mvc.perform(get("/api/v1/personal_representatives/view/assignor/test")).andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "hcp", roles = { "HCP" })
  public void testHCPViewAssignee() throws Exception {
    mvc.perform(get("/api/v1/personal_representatives/view/assignee/test")).andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "antti", roles = { "PATIENT" })
  public void testAssignAssignor() throws Exception {
    final User antti = new Patient(new UserForm("antti", "123456", Role.ROLE_PATIENT, 1));

    patientService.save(antti);

    final PatientForm patient = new PatientForm();
    patient.setAddress1("1 Test Street");
    patient.setAddress2("Some Location");
    patient.setBloodType(BloodType.APos.toString());
    patient.setCity("Viipuri");
    patient.setDateOfBirth("1977-06-15");
    patient.setEmail("antti@itrust.fi");
    patient.setEthnicity(Ethnicity.Caucasian.toString());
    patient.setFirstName("Antti");
    patient.setGender(Gender.Male.toString());
    patient.setLastName("Walhelm");
    patient.setPhone("123-456-7890");
    patient.setUsername("antti");
    patient.setState(State.NC.toString());
    patient.setZip("27514");

    mvc.perform(put("/api/v1/patients/antti").contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.asJsonString(patient))).andExpect(status().isOk());

    mvc.perform(post("/api/v1/personal_representatives/assign_assignor/test")).andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "antti", roles = { "PATIENT" })
  public void testAssignAssignee() throws Exception {
    final User antti = new Patient(new UserForm("antti", "123456", Role.ROLE_PATIENT, 1));

    patientService.save(antti);

    final PatientForm patient = new PatientForm();
    patient.setAddress1("1 Test Street");
    patient.setAddress2("Some Location");
    patient.setBloodType(BloodType.APos.toString());
    patient.setCity("Viipuri");
    patient.setDateOfBirth("1977-06-15");
    patient.setEmail("antti@itrust.fi");
    patient.setEthnicity(Ethnicity.Caucasian.toString());
    patient.setFirstName("Antti");
    patient.setGender(Gender.Male.toString());
    patient.setLastName("Walhelm");
    patient.setPhone("123-456-7890");
    patient.setUsername("antti");
    patient.setState(State.NC.toString());
    patient.setZip("27514");

    mvc.perform(put("/api/v1/patients/antti").contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.asJsonString(patient))).andExpect(status().isOk());

    mvc.perform(post("/api/v1/personal_representatives/assign_assignee/test")).andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "antti", roles = { "PATIENT" })
  public void testViewAssignee() throws Exception {
    final User antti = new Patient(new UserForm("antti", "123456", Role.ROLE_PATIENT, 1));

    patientService.save(antti);

    final PatientForm patient = new PatientForm();
    patient.setAddress1("1 Test Street");
    patient.setAddress2("Some Location");
    patient.setBloodType(BloodType.APos.toString());
    patient.setCity("Viipuri");
    patient.setDateOfBirth("1977-06-15");
    patient.setEmail("antti@itrust.fi");
    patient.setEthnicity(Ethnicity.Caucasian.toString());
    patient.setFirstName("Antti");
    patient.setGender(Gender.Male.toString());
    patient.setLastName("Walhelm");
    patient.setPhone("123-456-7890");
    patient.setUsername("antti");
    patient.setState(State.NC.toString());
    patient.setZip("27514");

    mvc.perform(put("/api/v1/patients/antti").contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.asJsonString(patient))).andExpect(status().isOk());

    mvc.perform(get("/api/v1/personal_representatives/view/assignee")).andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "antti", roles = { "PATIENT" })
  public void testViewAssignor() throws Exception {
    final User antti = new Patient(new UserForm("antti", "123456", Role.ROLE_PATIENT, 1));

    patientService.save(antti);

    final PatientForm patient = new PatientForm();
    patient.setAddress1("1 Test Street");
    patient.setAddress2("Some Location");
    patient.setBloodType(BloodType.APos.toString());
    patient.setCity("Viipuri");
    patient.setDateOfBirth("1977-06-15");
    patient.setEmail("antti@itrust.fi");
    patient.setEthnicity(Ethnicity.Caucasian.toString());
    patient.setFirstName("Antti");
    patient.setGender(Gender.Male.toString());
    patient.setLastName("Walhelm");
    patient.setPhone("123-456-7890");
    patient.setUsername("antti");
    patient.setState(State.NC.toString());
    patient.setZip("27514");

    mvc.perform(put("/api/v1/patients/antti").contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.asJsonString(patient))).andExpect(status().isOk());

    mvc.perform(get("/api/v1/personal_representatives/view/assignor")).andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "antti", roles = { "PATIENT" })
  public void testReleaseAssignor() throws Exception {
    final User antti = new Patient(new UserForm("antti", "123456", Role.ROLE_PATIENT, 1));

    patientService.save(antti);

    final PatientForm patient = new PatientForm();
    patient.setAddress1("1 Test Street");
    patient.setAddress2("Some Location");
    patient.setBloodType(BloodType.APos.toString());
    patient.setCity("Viipuri");
    patient.setDateOfBirth("1977-06-15");
    patient.setEmail("antti@itrust.fi");
    patient.setEthnicity(Ethnicity.Caucasian.toString());
    patient.setFirstName("Antti");
    patient.setGender(Gender.Male.toString());
    patient.setLastName("Walhelm");
    patient.setPhone("123-456-7890");
    patient.setUsername("antti");
    patient.setState(State.NC.toString());
    patient.setZip("27514");

    mvc.perform(put("/api/v1/patients/antti").contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.asJsonString(patient))).andExpect(status().isOk());

    mvc.perform(post("/api/v1/personal_representatives/assign_assignor/test")).andExpect(status().isOk());
    mvc.perform(delete("/api/v1/personal_representatives/release_assignor/test")).andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "antti", roles = { "PATIENT" })
  public void testReleaseAssignee() throws Exception {
    final User antti = new Patient(new UserForm("antti", "123456", Role.ROLE_PATIENT, 1));

    patientService.save(antti);

    final PatientForm patient = new PatientForm();
    patient.setAddress1("1 Test Street");
    patient.setAddress2("Some Location");
    patient.setBloodType(BloodType.APos.toString());
    patient.setCity("Viipuri");
    patient.setDateOfBirth("1977-06-15");
    patient.setEmail("antti@itrust.fi");
    patient.setEthnicity(Ethnicity.Caucasian.toString());
    patient.setFirstName("Antti");
    patient.setGender(Gender.Male.toString());
    patient.setLastName("Walhelm");
    patient.setPhone("123-456-7890");
    patient.setUsername("antti");
    patient.setState(State.NC.toString());
    patient.setZip("27514");

    mvc.perform(put("/api/v1/patients/antti").contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.asJsonString(patient))).andExpect(status().isOk());

    mvc.perform(post("/api/v1/personal_representatives/assign_assignee/test")).andExpect(status().isOk());
    mvc.perform(delete("/api/v1/personal_representatives/release_assignee/test")).andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "antti", roles = { "PATIENT" })
  public void testGetPatient() throws Exception {
    final User antti = new Patient(new UserForm("antti", "123456", Role.ROLE_PATIENT, 1));

    patientService.save(antti);

    final PatientForm patient = new PatientForm();
    patient.setAddress1("1 Test Street");
    patient.setAddress2("Some Location");
    patient.setBloodType(BloodType.APos.toString());
    patient.setCity("Viipuri");
    patient.setDateOfBirth("1977-06-15");
    patient.setEmail("antti@itrust.fi");
    patient.setEthnicity(Ethnicity.Caucasian.toString());
    patient.setFirstName("Antti");
    patient.setGender(Gender.Male.toString());
    patient.setLastName("Walhelm");
    patient.setPhone("123-456-7890");
    patient.setUsername("antti");
    patient.setState(State.NC.toString());
    patient.setZip("27514");

    mvc.perform(put("/api/v1/patients/antti").contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.asJsonString(patient))).andExpect(status().isOk());

    mvc.perform(get("/api/v1/personal_representatives/patients")).andExpect(status().isOk());
  }

  @Test
  @Transactional
  @WithMockUser(username = "patient_1", roles = { "PATIENT" })
  public void invalidAssignAssignor() throws Exception {
    mvc.perform(post("/api/v1/personal_representatives/assign_assignor/test")).andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  @WithMockUser(username = "patient_1", roles = { "PATIENT" })
  public void invalidAssignAssignee() throws Exception {
    mvc.perform(post("/api/v1/personal_representatives/assign_assignee/test")).andExpect(status().isBadRequest());
  }

  // @Test
  // @Transactional
  // @WithMockUser(username = "patient_1", roles = { "PATIENT" })
  // public void testReleaseAssignor() throws Exception {
  // final Patient testPatient = buildPatient();
  // userService.save(testPatient);

  // mvc.perform(delete("/api/v1/personal_representatives/release_assignor/patient_1")).andExpect(status().isOk());
  // }

  @Test
  @Transactional
  @WithMockUser(username = "patient_1", roles = { "PATIENT" })
  public void invalidReleaseAssignor() throws Exception {
    mvc.perform(delete("/api/v1/personal_representatives/release_assignor/blank")).andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  @WithMockUser(username = "patient_1", roles = { "PATIENT" })
  public void invalidReleaseAssignee() throws Exception {
    mvc.perform(delete("/api/v1/personal_representatives/release_assignee/blank")).andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  @WithMockUser(username = "hcp", roles = { "HCP" })
  public void testAssignRelationship() throws Exception {
    mvc.perform(post("/api/v1/personal_representatives/assign_relationship/test1/test2"))
        .andExpect(status().isOk());
  }

  // @Test
  // @Transactional
  // @WithMockUser(username = "hcp", roles = { "HCP" })
  // public void invalidAssignRelationship() throws Exception {
  // mvc.perform(post("/api/v1/personal_representatives/assign_relationship/test1/"))
  // .andExpect(status().isBadRequest());
  // }

}
