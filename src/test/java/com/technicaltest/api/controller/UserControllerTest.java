package com.technicaltest.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.technicaltest.api.dto.UserRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technicaltest.api.entity.User;
import com.technicaltest.api.service.UserService;

/**
 * Unit tests for the class
 * {@link com.technicaltest.api.controller.UserController
 * UserController }. In the test cases we mock the Service layer to isolate the
 * controller.
 *
 * @author boubacar
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

	private static final String ENDPOINT = "/api/users";
	private static final String USERNAME = "ben";
	private static final String BIRTHDATE = "2000-01-01";
	private static final String COUNTRY = "France";
	private static final String PHONE_NUMBER = "0400000000";
	private static final String GENDER = "Male";
	private static final Integer STATUS_CODE_201 = 201;
	private static final Integer STATUS_CODE_400 = 400;
	private static final Integer STATUS_CODE_409 = 409;

	private static User validUser;

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Before starting the tests, instantiate the property validUser
	 */
	@BeforeAll
	public static void initUser() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(BIRTHDATE);
			Date date = sdf.parse(BIRTHDATE);
			validUser = User.build(0, USERNAME, date, COUNTRY, PHONE_NUMBER, GENDER);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Unit test for the GET user endpoint, the api should return the user given by
	 * the mocked service
	 *
	 * @throws Exception exception
	 */
	@Test
	public void givenUser_whenGetUser_thenReturnUser() throws Exception {
		Mockito.when(userService.getUserByName(USERNAME)).thenReturn(validUser);
		mvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "?name=" + USERNAME).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", is(USERNAME)))
				.andExpect(jsonPath("$.country", is(COUNTRY)));
	}

	/**
	 * Unit test for the GET user endpoint when the user is not mocked, the api
	 * should a 404 error
	 *
	 * @throws Exception exception
	 */
	@Test
	public void givenNoUser_whenGetUser_thenNotFound() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(ENDPOINT + "?name=" + USERNAME).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	/**
	 * Unit test for the POST user endpoint when the user is correct, the api should
	 * return OK and the user
	 *
	 * @throws Exception exception
	 */
	@Test
	public void sendingValidUser_whenUserAdd_thenReturnOkUserJson() throws Exception {
		Mockito.when(userService.saveUser(any(UserRequest.class))).thenReturn(validUser);
		mvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(validUser)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(USERNAME)))
				.andExpect(jsonPath("$.country", is(COUNTRY)))
				.andExpect(jsonPath("$.phoneNumber", is(PHONE_NUMBER)));
	}

	/**
	 * Unit test for the POST user endpoint when the user is not correct, the api
	 * should return NOT FOUND 400 and the errors descriptions
	 *
	 * @throws Exception exception
	 */
	@Test
	public void sendingUserWithErrors_whenUserAdd_thenReturnErrorJson() throws Exception {
		User userWithErrors = User.build(0, USERNAME, new Date(), "Belgium", null, null);

		mvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userWithErrors)))
				.andExpect(status().is(STATUS_CODE_400))
				.andExpect(jsonPath("$.birthDate", is("User should be adult")))
				.andExpect(jsonPath("$.country", is("Only French users can register")));
	}

	/**
	 * Unit test for POST user endpoint, when the user already exists the api should
	 * return CONFLICT 409
	 *
	 * @throws Exception exception
	 */
	@Test
	public void sendingValidUser_givenUserAlreadyExists_whenUserAdd_thenReturnErrorJson() throws Exception {
		Mockito.when(userService.userExists(any(UserRequest.class))).thenReturn(true);
		mvc.perform(MockMvcRequestBuilders.post(ENDPOINT + "/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(validUser)))
				.andExpect(status().is(STATUS_CODE_201));
	}
}
