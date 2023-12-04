package edu.ncsu.csc.iTrust2.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.ncsu.csc.iTrust2.common.TestUtils;
import edu.ncsu.csc.iTrust2.models.LabTest;
import edu.ncsu.csc.iTrust2.services.LabTestService;



// @author: Yewon Lim
// @date: 2023/12/04
@RunWith ( SpringRunner.class )
@SpringBootTest
@AutoConfigureMockMvc
public class APILabTestTest {
    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private LabTestService       service;

    /**
     * Test set up
     */
    @Before
    public void setup () {
        mvc = MockMvcBuilders.webAppContextSetup( context ).build();
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
    @WithMockUser( username = "hcp", roles = { "HCP" } )
    public void testGetNonExistentLabTest () throws Exception {
        mvc.perform( get( "/api/v1/lab_tests/view_results/bob" ) ).andExpect( status().isNotFound() );
    }

    @Test
    @Transactional
    @WithMockUser( username = "hcp", roles = { "HCP" } )
    public void authorizationError () throws Exception {
        mvc.perform( get( "/api/v1/lab_tests/view_results/bob" ) ).andExpect( status().isForbidden());
    }
}
