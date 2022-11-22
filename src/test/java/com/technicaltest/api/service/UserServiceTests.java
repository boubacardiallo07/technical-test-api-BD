package com.technicaltest.api.service;

import com.technicaltest.api.entity.User;
import com.technicaltest.api.exception.UserNotFoundException;
import com.technicaltest.api.repository.UserRepository;
import com.technicaltest.api.dto.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the class
 * {@link com.technicaltest.api.service.UserService UserService}.
 * In the test cases we mock the Repository layer to isolate the service.
 *
 * @author boubacar
 *
 */
public class UserServiceTests {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private static User userBuild;
    private static List<User> userBuildList;
    private static UserRequest userRequest;
    private static final String USERNAME = "david";
    private static final String BIRTHDATE = "2000-01-02-";
    private static final String COUNTRY = "France";
    private static final String PHONE_NUMBER = "0600000000";
    private static final String GENDER = "M";
    private static final String DATEFORMAT = "yyyy-MM-dd";

    /**
     * Before starting the tests, instantiate the property validUser
     *
     * @throws ParseException
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        Date date = sdf.parse(BIRTHDATE);
        userBuild = new User().build(0, USERNAME, date,COUNTRY,PHONE_NUMBER,GENDER);
        userBuildList = new ArrayList<>(Arrays.asList(
                new User().build(0, USERNAME, date, COUNTRY, PHONE_NUMBER, GENDER),
                new User().build(0, USERNAME, date, COUNTRY, PHONE_NUMBER, GENDER)
        ));
        userRequest = new UserRequest().build(USERNAME, date,COUNTRY,PHONE_NUMBER,GENDER);
    }

    /**
     * Unit Test for the addUser method of UserService When no user given,
     * userExists should return false
     *
     * @throws Exception
     */
    @Test
    public void saveUserTest() throws ParseException, UserNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        Date birthdate = sdf.parse(BIRTHDATE);
        Mockito.when(userRepository.save( userBuild )).thenReturn(userBuild);
        User userSaved = userService.saveUser(userRequest);
        assertEquals(USERNAME, userSaved.getName());
        assertEquals(COUNTRY, userSaved.getCountry());
        assertEquals(birthdate, userSaved.getBirthDate());
    }

    /**
     * Unit Test for the addUser method of getUserByUsername When the correct user
     * is given, getUserByUsername should return the user
     *
     * @throws Exception
     */
    @Test
    public void getUserByNameTest() throws UserNotFoundException {
        when( userRepository.findUserByName( anyString() )).thenReturn( userBuild);
        User userRequest = userService.getUserByName(USERNAME);
        assertNotNull(userRequest);
        assertEquals(USERNAME, userRequest.getName());
    }

    @Test
    public void getUserByName_whenUserNameNotFoundTest() {
        when( userRepository.findUserByName( anyString() )).thenReturn( null );
        assertThrows(UserNotFoundException.class,
                ()-> userService.getUserByName(USERNAME));
    }

    @Test
    public void checkConstraintsPropertiesTest() throws Exception {
        when( userRepository.findUserByName( anyString() )).thenReturn( userBuild );
        String userName = userService.getUserByName(USERNAME).getName();
        String country = userService.getUserByName(USERNAME).getCountry();
        assertNotNull(userName);
        assertNotNull(country);
    }

    /**
     * Unit Test for the addUser method of getAllUsers When the correct user
     * is given, getAllUsers should return all users
     *
     * @throws Exception
     */
    @Test
    public void getAllUsersTest()  {
        when(userRepository.findAll()).thenReturn(userBuildList);
        User actualUser = userService.getUsers().get(0);
        assertEquals(2, userService.getUsers().size());
        assertNotNull(actualUser.getName());
        assertEquals(USERNAME, actualUser.getName());
        assertEquals(COUNTRY, actualUser.getCountry());
    }
}
