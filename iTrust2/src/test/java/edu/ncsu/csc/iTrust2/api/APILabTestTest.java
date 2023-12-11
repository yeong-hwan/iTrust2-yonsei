package edu.ncsu.csc.iTrust2.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import edu.ncsu.csc.iTrust2.common.TestUtils;
import edu.ncsu.csc.iTrust2.forms.LabTestForm;
import edu.ncsu.csc.iTrust2.forms.OfficeVisitForm;
import edu.ncsu.csc.iTrust2.models.LabTest;
import edu.ncsu.csc.iTrust2.models.enums.AppointmentType;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.services.LabTestService;
import edu.ncsu.csc.iTrust2.services.UserService;
import edu.ncsu.csc.iTrust2.services.PatientService;
import edu.ncsu.csc.iTrust2.forms.UserForm;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

// @author: Yewon Lim
// @date: 2023/12/04
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class APILabTestTest {
        private MockMvc mvc;

        @Autowired
        private WebApplicationContext context;

        @Autowired
        private LabTestService service;

        @Autowired
        private UserService userService;

        /**
         * Test set up
         */
        @Before
        public void setup() {
                mvc = MockMvcBuilders.webAppContextSetup(context).build();
                service.deleteAll();
        }

        /**
         * Tests getting a non existent hospital and ensures that the correct status
         * is returned.
         *
         * @throws Exception
         */
        @Test
        @Transactional
        @WithMockUser(username = "hcp", roles = { "HCP" })
        public void testGetNonExistentLabTest() throws Exception {
                mvc.perform(get("/api/v1/lab_tests/view_results/bob")).andExpect(status().isNotFound());
        }

        @Test
        @Transactional
        @WithMockUser(username = "hcp", roles = { "HCP" })
        public void orderBadRequest() throws Exception {
                LabTestForm labtest = new LabTestForm();
                labtest.setTestName("Blood Test");
                labtest.setLabName("LabCorp");
                labtest.setInstructions("Take blood sample");
                labtest.setPatientName("non-existent");

                mvc.perform(post("/api/v1/lab_tests/order").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest))).andExpect(status().isBadRequest());
        }

        @Test
        @Transactional
        @WithMockUser(username = "hcp", roles = { "HCP" })
        public void order() throws Exception {
                // create a patient
                final UserForm u = new UserForm("patient", "1234", Role.ROLE_PATIENT, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a labtech
                final UserForm u1 = new UserForm("labtech", "1234", Role.ROLE_LABTECH, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u1))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a hcp
                final UserForm hcp = new UserForm("hcp", "1234", Role.ROLE_HCP, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(hcp))).andExpect(MockMvcResultMatchers.status().isOk());

                LabTestForm labtest = new LabTestForm();
                labtest.setTestName("Blood Test");
                labtest.setLabName("LabCorp");
                labtest.setInstructions("Take blood sample");
                labtest.setPatientName("patient");

                mvc.perform(post("/api/v1/lab_tests/order").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk());

                Assert.assertEquals("There should be one lab test in the system after creating a LabTest", 1,
                                service.count());

        }

        @Test
        @Transactional
        @WithMockUser(username = "patient", roles = { "PATIENT" })
        public void unauthorizedOrder() throws Exception {
                // create a patient
                final UserForm u = new UserForm("patient", "1234", Role.ROLE_PATIENT, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a labtech
                final UserForm u1 = new UserForm("labtech", "1234", Role.ROLE_LABTECH, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u1))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a hcp
                final UserForm hcp = new UserForm("hcp", "1234", Role.ROLE_HCP, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(hcp))).andExpect(MockMvcResultMatchers.status().isOk());

                LabTestForm labtest = new LabTestForm();
                labtest.setTestName("Blood Test");
                labtest.setLabName("LabCorp");
                labtest.setInstructions("Take blood sample");
                labtest.setPatientName("patient");

                mvc.perform(post("/api/v1/lab_tests/order").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isUnauthorized());

                Assert.assertEquals("There should be no lab test in the system after creating a LabTest", 0,
                                service.count());
        }

        @Test
        @Transactional
        public void record_results() throws Exception {
                // create a patient
                final UserForm u = new UserForm("patient", "1234", Role.ROLE_PATIENT, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a labtech
                final UserForm u1 = new UserForm("labtech", "1234", Role.ROLE_LABTECH, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u1))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a hcp
                final UserForm hcp = new UserForm("hcp", "1234", Role.ROLE_HCP, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(hcp))).andExpect(MockMvcResultMatchers.status().isOk());

                LabTestForm labtest = new LabTestForm();
                labtest.setTestName("Blood Test");
                labtest.setLabName("LabCorp");
                labtest.setInstructions("Take blood sample");
                labtest.setPatientName("patient");

                MockMvc hcpMvc = MockMvcBuilders.webAppContextSetup(context)
                                .defaultRequest(get("/").with(user("hcp").roles("HCP")))
                                .apply(springSecurity())
                                .build();

                // HCP orders a lab test
                hcpMvc.perform(post("/api/v1/lab_tests/order").with(user("hcp").password("1234").roles("HCP"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk());

                Assert.assertEquals("There should be one lab test in the system after creating a LabTest", 1,
                                service.count());

                LabTestForm labtest2 = new LabTestForm();
                labtest2.setTestName("Blood Test");
                labtest2.setLabName("LabCorp");
                labtest2.setInstructions("Take blood sample");
                labtest2.setPatientName("patient");
                labtest2.setResults("positive");
                labtest2.setNotes("no notes");

                MockMvc labtechMvc = MockMvcBuilders.webAppContextSetup(context)
                                .defaultRequest(get("/").with(user("labtech").roles("LABTECH")))
                                .apply(springSecurity())
                                .build();

                labtechMvc.perform(post("/api/v1/lab_tests/record_results/")
                                .with(user("labtech").password("1234").roles("LABTECH"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest2)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk());

                Assert.assertEquals("There should be one lab test in the system after recording", 1, service.count());

                // view results
                labtechMvc.perform(get("/api/v1/lab_tests/view_results/patient")
                                .with(user("labtech").password("1234").roles("LABTECH")))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk());

        }

        @Test
        @Transactional
        public void record_results_no() throws Exception {
                // create a patient
                final UserForm u = new UserForm("patient", "1234", Role.ROLE_PATIENT, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a labtech
                final UserForm u1 = new UserForm("labtech", "1234", Role.ROLE_LABTECH, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u1))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a hcp
                final UserForm hcp = new UserForm("hcp", "1234", Role.ROLE_HCP, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(hcp))).andExpect(MockMvcResultMatchers.status().isOk());

                LabTestForm labtest = new LabTestForm();
                labtest.setTestName("Blood Test");
                labtest.setLabName("LabCorp");
                labtest.setInstructions("Take blood sample");
                labtest.setPatientName("patient");

                MockMvc hcpMvc = MockMvcBuilders.webAppContextSetup(context)
                                .defaultRequest(get("/").with(user("hcp").roles("HCP")))
                                .apply(springSecurity())
                                .build();

                // HCP orders a lab test
                hcpMvc.perform(post("/api/v1/lab_tests/order").with(user("hcp").password("1234").roles("HCP"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk());

                Assert.assertEquals("There should be one lab test in the system after creating a LabTest", 1,
                                service.count());

                LabTestForm labtest2 = new LabTestForm();
                labtest2.setTestName("Blood Test");
                labtest2.setLabName("Yonsei");
                labtest2.setInstructions("Take blood sample");
                labtest2.setPatientName("patient");
                labtest2.setResults("positive");
                labtest2.setNotes("no notes");

                MockMvc labtechMvc = MockMvcBuilders.webAppContextSetup(context)
                                .defaultRequest(get("/").with(user("labtech").roles("LABTECH")))
                                .apply(springSecurity())
                                .build();

                labtechMvc.perform(post("/api/v1/lab_tests/record_results/")
                                .with(user("labtech").password("1234").roles("LABTECH"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest2)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isNotFound());

                labtechMvc.perform(get("/api/v1/lab_tests/view_labtests")
                                .with(user("labtech").password("1234").roles("LABTECH"))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk());

        }

        @Test
        @Transactional
        public void record_results_unauthenticated() throws Exception {
                // create a patient
                final UserForm u = new UserForm("patient", "1234", Role.ROLE_PATIENT, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a labtech
                final UserForm u1 = new UserForm("labtech", "1234", Role.ROLE_LABTECH, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u1))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a hcp
                final UserForm hcp = new UserForm("hcp", "1234", Role.ROLE_HCP, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(hcp))).andExpect(MockMvcResultMatchers.status().isOk());

                LabTestForm labtest = new LabTestForm();
                labtest.setTestName("Blood Test");
                labtest.setLabName("LabCorp");
                labtest.setInstructions("Take blood sample");
                labtest.setPatientName("patient");

                MockMvc hcpMvc = MockMvcBuilders.webAppContextSetup(context)
                                .defaultRequest(get("/").with(user("hcp").roles("HCP")))
                                .apply(springSecurity())
                                .build();

                // HCP orders a lab test
                hcpMvc.perform(post("/api/v1/lab_tests/order").with(user("hcp").password("1234").roles("HCP"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk());

                Assert.assertEquals("There should be one lab test in the system after creating a LabTest", 1,
                                service.count());

                LabTestForm labtest2 = new LabTestForm();
                labtest2.setTestName("Blood Test");
                labtest2.setLabName("LabCorp");
                labtest2.setInstructions("Take blood sample");
                labtest2.setPatientName("patient");
                labtest2.setResults("positive");
                labtest2.setNotes("no notes");

                hcpMvc.perform(post("/api/v1/lab_tests/record_results/").with(user("hcp").password("1234").roles("HCP"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest2)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isUnauthorized());

        }

        @Test
        @Transactional
        @WithMockUser(username = "er", roles = { "ER" })
        public void unauthorizedView() throws Exception {
                // create a patient
                final UserForm u = new UserForm("patient", "1234", Role.ROLE_PATIENT, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a labtech
                final UserForm u1 = new UserForm("labtech", "1234", Role.ROLE_LABTECH, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u1))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a hcp
                final UserForm hcp = new UserForm("hcp", "1234", Role.ROLE_HCP, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(hcp))).andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/lab_tests/view_results/patient").with(user("er").password("1234").roles("ER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }


    @Test 
    @Transactional
    @WithMockUser( username = "hcp", roles = { "HCP" } )
    public void unauthorizedGetLabTests() throws Exception{
        // create a hcp
        final UserForm hcp = new UserForm( "hcp", "1234", Role.ROLE_HCP, 1 );

        mvc.perform( MockMvcRequestBuilders.post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( hcp ) ) ).andExpect( MockMvcResultMatchers.status().isOk() );

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/lab_tests/view_labtests")
                .contentType( MediaType.APPLICATION_JSON ))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }

    @Test 
    @Transactional
    public void emptyGetLabTests() throws Exception{
        // create a labtech
        final UserForm u1 = new UserForm( "labtech", "1234", Role.ROLE_LABTECH, 1 );

        mvc.perform( MockMvcRequestBuilders.post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u1 ) ) ).andExpect( MockMvcResultMatchers.status().isOk() );

        MockMvc labtechMvc = MockMvcBuilders.webAppContextSetup( context )
                        .defaultRequest(get("/").with(user("labtech").roles("LABTECH")))
                        .apply(springSecurity())
                        .build();

        labtechMvc.perform(MockMvcRequestBuilders.get("/api/v1/lab_tests/view_labtests").with(user("labtech").roles("LABTECH")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }


        @Test
        @Transactional
        @WithMockUser(username = "patient", roles = { "PATIENT" })
        public void NoReultsFound() throws Exception {
                // create a patient
                final UserForm u = new UserForm("patient", "1234", Role.ROLE_PATIENT, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a labtech
                final UserForm u1 = new UserForm("labtech", "1234", Role.ROLE_LABTECH, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(u1))).andExpect(MockMvcResultMatchers.status().isOk());

                // create a hcp
                final UserForm hcp = new UserForm("hcp", "1234", Role.ROLE_HCP, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(hcp))).andExpect(MockMvcResultMatchers.status().isOk());

                LabTestForm labtest = new LabTestForm();
                labtest.setTestName("Blood Test");
                labtest.setLabName("LabCorp");
                labtest.setInstructions("Take blood sample");
                labtest.setPatientName("patient");

                MockMvc hcpMvc = MockMvcBuilders.webAppContextSetup(context)
                                .defaultRequest(get("/").with(user("hcp").roles("HCP")))
                                .apply(springSecurity())
                                .build();

                // HCP orders a lab test
                hcpMvc.perform(post("/api/v1/lab_tests/order").with(user("hcp").password("1234").roles("HCP"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(labtest)))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isOk());

                hcpMvc.perform(get("/api/v1/lab_tests/view_results/yewon")
                                .with(user("hcp").password("1234").roles("HCP"))
                                .contentType(MediaType.APPLICATION_JSON))
                                .andDo(MockMvcResultHandlers.print())
                                .andExpect(status().isNotFound());
                // create a patient
                final UserForm yewon = new UserForm("yewon", "1234", Role.ROLE_PATIENT, 1);

                mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.asJsonString(yewon)))
                                .andExpect(MockMvcResultMatchers.status().isOk());

        hcpMvc.perform(get("/api/v1/lab_tests/view_results/yewon").with(user("hcp").password("1234").roles("HCP"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    @WithMockUser( username = "patient", roles = { "PATIENT" } )
    public void getMyLabTestResult404() throws Exception{
        // create a patient
        final UserForm u = new UserForm( "patient", "1234", Role.ROLE_PATIENT, 1 );

        mvc.perform( MockMvcRequestBuilders.post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u ) ) ).andExpect( MockMvcResultMatchers.status().isOk() );
        
        
        mvc.perform(get("/api/v1/lab_tests/view_my_results").with(user("patient").password("1234").roles("PATIENT"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @WithMockUser( username = "hcp", roles = { "HCP" } )
    public void getMyLabTestResult400() throws Exception{
        // create a patient
        final UserForm u = new UserForm( "hcp", "1234", Role.ROLE_HCP, 1 );

        mvc.perform( MockMvcRequestBuilders.post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u ) ) ).andExpect( MockMvcResultMatchers.status().isOk() );
        
        
        mvc.perform(get("/api/v1/lab_tests/view_my_results").with(user("hcp").password("1234").roles("HCP"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void getMyLabTestResult200() throws Exception{
        // create a patient
        final UserForm u = new UserForm( "patient", "1234", Role.ROLE_PATIENT, 1 );

        mvc.perform( MockMvcRequestBuilders.post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u ) ) ).andExpect( MockMvcResultMatchers.status().isOk() );

        // create a labtech
        final UserForm u1 = new UserForm( "labtech", "1234", Role.ROLE_LABTECH, 1 );

        mvc.perform( MockMvcRequestBuilders.post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u1 ) ) ).andExpect( MockMvcResultMatchers.status().isOk() );

        // create a hcp
        final UserForm hcp = new UserForm( "hcp", "1234", Role.ROLE_HCP, 1 );

        mvc.perform( MockMvcRequestBuilders.post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( hcp ) ) ).andExpect( MockMvcResultMatchers.status().isOk() );

        

        LabTestForm labtest = new LabTestForm();
        labtest.setTestName( "Blood Test" );
        labtest.setLabName( "LabCorp" );
        labtest.setInstructions( "Take blood sample" );
        labtest.setPatientName("patient");

        MockMvc hcpMvc = MockMvcBuilders.webAppContextSetup( context )
                        .defaultRequest(get("/").with(user("hcp").roles("HCP")))
                        .apply(springSecurity())
                        .build();


        // HCP orders a lab test
        hcpMvc.perform( post( "/api/v1/lab_tests/order" ).with(user("hcp").password("1234").roles("HCP"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(labtest)) )
                .andDo(MockMvcResultHandlers.print())
                .andExpect( status().isOk() );
        
        MockMvc hcpPatient = MockMvcBuilders.webAppContextSetup( context )
                        .defaultRequest(get("/").with(user("patient").roles("PATIENT")))
                        .apply(springSecurity())
                        .build();
        hcpPatient.perform(get("/api/v1/lab_tests/view_my_results")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }




}
