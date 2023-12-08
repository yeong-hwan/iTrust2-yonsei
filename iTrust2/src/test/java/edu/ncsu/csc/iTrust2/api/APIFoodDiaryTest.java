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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import edu.ncsu.csc.iTrust2.common.TestUtils;
import edu.ncsu.csc.iTrust2.forms.FoodDiaryForm;
import edu.ncsu.csc.iTrust2.models.FoodDiary;
import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.Personnel;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.utils.LoggerUtil;
import edu.ncsu.csc.iTrust2.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class APIFoodDiaryTest {

    private static final String   USER_1 = "API_USER_1";

    private static final String   USER_2 = "API_USER_2";

    private static final String   PW     = "123456";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;


    private static final String TEST_USER = "testUser";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    @WithMockUser(username = TEST_USER)
    public void testAddEntry() throws Exception {

        final UserForm u = new UserForm( TEST_USER, PW, Role.ROLE_PATIENT, 1 );

        mockMvc.perform( MockMvcRequestBuilders.post( "/api/v1/users" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( u ) ) ).andExpect( MockMvcResultMatchers.status().isOk() );

        final FoodDiary base = new FoodDiary();
        final User self = userService.findByName(TEST_USER);
        base.setId((long) 1);
        base.setUsername(self.getUsername());
        base.setDate("2023-11-23");
        base.setMealType("Lunch");
        base.setFoodName("food");
        base.setServingNumber((long) 2);
        base.setCaloriesPerServing((long) 100);
        base.setFatPerServing((long) 10);
        base.setSodiumPerServing((long) 10);
        base.setCarbsPerServing((long) 20);
        base.setSugarsPerServing((long) 10);
        base.setFiberPerServing((long) 5);
        base.setProteinPerServing((long) 30);

        FoodDiaryForm form = new FoodDiaryForm(base);
        String jsonForm = TestUtils.asJsonString(form);

        mockMvc.perform(post("/api/v1/food_diary/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonForm))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetPatients() throws Exception {
        mockMvc.perform(get("/api/v1/food_diary/view"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testFindByUsernameContains() throws Exception {
        mockMvc.perform(get("/api/v1/food_diary/view/testUser"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testCalculateDailyTotal() throws Exception {
        mockMvc.perform(get("/api/v1/food_diary/view/testUser/2023-03-15"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testViewEntries() throws Exception {
        mockMvc.perform(get("/api/v1/food_diary/view/testUser/2023-03-15/Lunch"))
                .andExpect(status().isOk());
    }




}