package edu.ncsu.csc.iTrust2.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.iTrust2.common.TestUtils;
import edu.ncsu.csc.iTrust2.forms.PatientForm;
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.Patient;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.BloodType;
import edu.ncsu.csc.iTrust2.models.enums.Ethnicity;
import edu.ncsu.csc.iTrust2.models.enums.Gender;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.models.enums.State;
import edu.ncsu.csc.iTrust2.services.PatientService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;



/**
 * Test for API functionality for interacting with Patients
 *
 * @author Kai Presler-Marshall
 *
 */
@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APIPatientTest {

    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PatientService        service;

    /**
     * Sets up test
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();

        service.deleteAll();
    }
    /*
     * Test for searching for patients
     *  - Search by name, hcp
     */
    @Test
    @Transactional
    @WithMockUser ( username = "hcp", roles = { "HCP" } )
    public void testSearchPatientsHCP () throws Exception {
        final PatientForm patient = new PatientForm();
        patient.setFirstName("yewon");
        patient.setLastName("lim");
        patient.setAddress1( "1 Test Street" );
        patient.setAddress2( "Some Location" );
        patient.setBloodType( BloodType.APos.toString() );
        patient.setCity( "Viipuri" );
        patient.setDateOfBirth( "1977-06-15" );
        patient.setEmail( "test@yonsei.ac.kr");
        
        User hcp = new Patient(new UserForm("test", "123456", Role.ROLE_PATIENT, 1));
        service.save(hcp);
        // Should also now be able to edit existing record with new information
        mvc.perform( put( "/api/v1/patients/test" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patient ) ) ).andExpect( status().isOk() );


        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("searchQuery", "ye_");
        info.add("searchType", "name");
        mvc.perform(get( "/api/v1/emergency_health_records/search" ).params(info))
                .andDo(MockMvcResultHandlers.print())
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$[0].firstName").value("yewon"));
        MultiValueMap<String, String> info2 = new LinkedMultiValueMap<>();
        info2.add("searchQuery", "te");
        info2.add("searchType", "username");
        mvc.perform(get( "/api/v1/emergency_health_records/search" ).params(info2))
                .andDo(MockMvcResultHandlers.print())
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$[0].firstName").value("yewon"));
        
    } 
    
    @Test
    @Transactional
    @WithMockUser ( username = "hcp", roles = { "HCP" } )
    public void testSearchPatientsER () throws Exception {
        final PatientForm patient = new PatientForm();
        patient.setFirstName("yewon");
        patient.setLastName("lim");
        patient.setAddress1( "1 Test Street" );
        patient.setAddress2( "Some Location" );
        patient.setBloodType( BloodType.APos.toString() );
        patient.setCity( "Viipuri" );
        patient.setDateOfBirth( "1977-06-15" );
        patient.setEmail( "test@yonsei.ac.kr");
        
        User hcp = new Patient(new UserForm("test", "123456", Role.ROLE_PATIENT, 1));
        service.save(hcp);
        // Should also now be able to edit existing record with new information
        mvc.perform( put( "/api/v1/patients/test" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patient ) ) ).andExpect( status().isOk() );

        MockMvc erMvc = MockMvcBuilders.webAppContextSetup( context )
                .defaultRequest(get("/").with(user("er").roles("ER")))
                .apply(springSecurity())
                .build();
        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
        info.add("searchQuery", "ye_");
        info.add("searchType", "name");
        erMvc.perform(get( "/api/v1/emergency_health_records/search" ).params(info))
                .andDo(MockMvcResultHandlers.print())
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$[0].firstName").value("yewon"));
        MultiValueMap<String, String> info2 = new LinkedMultiValueMap<>();
        info2.add("searchQuery", "te");
        info2.add("searchType", "username");
        erMvc.perform(get( "/api/v1/emergency_health_records/search" ).params(info2))
                .andDo(MockMvcResultHandlers.print())
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$[0].firstName").value("yewon"));
        
    } 
    
    /**
     * Tests that getting a patient that doesn't exist returns the proper
     * status.
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "hcp", roles = { "HCP" } )
    public void testGetNonExistentPatient () throws Exception {
        mvc.perform( get( "/api/v1/patients/-1" ) ).andExpect( status().isNotFound() );
    }

    /**
     * Tests PatientAPI
     *
     * @throws Exception
     */
    @Test
    @WithMockUser ( username = "hcp", roles = { "HCP" } )
    @Transactional
    public void testPatientAPI () throws Exception {

        final PatientForm patient = new PatientForm();
        patient.setAddress1( "1 Test Street" );
        patient.setAddress2( "Some Location" );
        patient.setBloodType( BloodType.APos.toString() );
        patient.setCity( "Viipuri" );
        patient.setDateOfBirth( "1977-06-15" );
        patient.setEmail( "antti@itrust.fi" );
        patient.setEthnicity( Ethnicity.Caucasian.toString() );
        patient.setFirstName( "Antti" );
        patient.setGender( Gender.Male.toString() );
        patient.setLastName( "Walhelm" );
        patient.setPhone( "123-456-7890" );
        patient.setUsername( "antti" );
        patient.setState( State.NC.toString() );
        patient.setZip( "27514" );

        // Editing the patient before they exist should fail
        mvc.perform( put( "/api/v1/patients/antti" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patient ) ) ).andExpect( status().isNotFound() );

        final User antti = new Patient( new UserForm( "antti", "123456", Role.ROLE_PATIENT, 1 ) );

        service.save( antti );

        // Creating a User should create the Patient record automatically
        mvc.perform( get( "/api/v1/patients/antti" ) ).andExpect( status().isOk() );

        // Should also now be able to edit existing record with new information
        mvc.perform( put( "/api/v1/patients/antti" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patient ) ) ).andExpect( status().isOk() );

        Patient anttiRetrieved = (Patient) service.findByName( "antti" );

        // Test a few fields to be reasonably confident things are working
        Assert.assertEquals( "Walhelm", anttiRetrieved.getLastName() );
        Assert.assertEquals( Gender.Male, anttiRetrieved.getGender() );
        Assert.assertEquals( "Viipuri", anttiRetrieved.getCity() );

        // Also test a field we haven't set yet
        Assert.assertNull( anttiRetrieved.getPreferredName() );

        patient.setPreferredName( "Antti Walhelm" );

        mvc.perform( put( "/api/v1/patients/antti" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patient ) ) ).andExpect( status().isOk() );

        anttiRetrieved = (Patient) service.findByName( "antti" );

        Assert.assertNotNull( anttiRetrieved.getPreferredName() );

        // Editing with the wrong username should fail.
        mvc.perform( put( "/api/v1/patients/badusername" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patient ) ) ).andExpect( status().is4xxClientError() );

    }

    /**
     * Test accessing the patient PUT request unauthenticated
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void testPatientUnauthenticated () throws Exception {
        final PatientForm patient = new PatientForm();
        patient.setAddress1( "1 Test Street" );
        patient.setAddress2( "Some Location" );
        patient.setBloodType( BloodType.APos.toString() );
        patient.setCity( "Viipuri" );
        patient.setDateOfBirth( "1977-06-15" );
        patient.setEmail( "antti@itrust.fi" );
        patient.setEthnicity( Ethnicity.Caucasian.toString() );
        patient.setFirstName( "Antti" );
        patient.setGender( Gender.Male.toString() );
        patient.setLastName( "Walhelm" );
        patient.setPhone( "123-456-7890" );
        patient.setUsername( "antti" );
        patient.setState( State.NC.toString() );
        patient.setZip( "27514" );

        final User antti = new Patient( new UserForm( "antti", "123456", Role.ROLE_PATIENT, 1 ) );

        service.save( antti );

        mvc.perform( put( "/api/v1/patients/antti" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patient ) ) ).andExpect( status().isUnauthorized() );
    }

    /**
     * Test accessing the patient PUT request as a patient
     *
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser ( username = "antti", roles = { "PATIENT" } )
    public void testPatientAsPatient () throws Exception {
        final User antti = new Patient( new UserForm( "antti", "123456", Role.ROLE_PATIENT, 1 ) );

        service.save( antti );

        final PatientForm patient = new PatientForm();
        patient.setAddress1( "1 Test Street" );
        patient.setAddress2( "Some Location" );
        patient.setBloodType( BloodType.APos.toString() );
        patient.setCity( "Viipuri" );
        patient.setDateOfBirth( "1977-06-15" );
        patient.setEmail( "antti@itrust.fi" );
        patient.setEthnicity( Ethnicity.Caucasian.toString() );
        patient.setFirstName( "Antti" );
        patient.setGender( Gender.Male.toString() );
        patient.setLastName( "Walhelm" );
        patient.setPhone( "123-456-7890" );
        patient.setUsername( "antti" );
        patient.setState( State.NC.toString() );
        patient.setZip( "27514" );

        // a patient can edit their own info
        mvc.perform( put( "/api/v1/patients/antti" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patient ) ) ).andExpect( status().isOk() );

        final Patient anttiRetrieved = (Patient) service.findByName( "antti" );

        // Test a few fields to be reasonably confident things are working
        Assert.assertEquals( "Walhelm", anttiRetrieved.getLastName() );
        Assert.assertEquals( Gender.Male, anttiRetrieved.getGender() );
        Assert.assertEquals( "Viipuri", anttiRetrieved.getCity() );

        // Also test a field we haven't set yet
        Assert.assertNull( anttiRetrieved.getPreferredName() );

        // but they can't edit someone else's
        patient.setUsername( "patient" );
        mvc.perform( put( "/api/v1/patients/patient" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( patient ) ) ).andExpect( status().isUnauthorized() );

        // we should be able to update our record too
        mvc.perform( get( "/api/v1/patient/" ) ).andExpect( status().isOk() );
    }
    
    @Test
    @Transactional
    @WithMockUser( username = "hcp", roles = { "HCP" } )
    public void testGetRecordsByPatientId() throws Exception {
        // Create a new patient
        final PatientForm patient = new PatientForm();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setAddress1( "1 Test Street" );
        patient.setAddress2( "Some Location" );
        patient.setBloodType( BloodType.APos.toString() );
        patient.setCity( "Viipuri" );
        patient.setDateOfBirth( "1977-06-15" );
        patient.setEmail( "test@yonsei.ac.kr");
        patient.setGender( Gender.Male.toString() );

        User er = new Patient(new UserForm("johnDoe", "123456", Role.ROLE_PATIENT, 1));
        service.save(er);

        mvc.perform(put("/api/v1/patients/johnDoe").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(patient))).andExpect(status().isOk());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("patientUsername", "johnDoe");

        mvc.perform(get("/api/v1/emergency_health_records/view").params(params))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(46));
    }

}
