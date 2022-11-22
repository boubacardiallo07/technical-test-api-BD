package com.technicaltest.api.repository;

import static org.hamcrest.Matchers.is;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.technicaltest.api.dto.UserRequest;
import com.technicaltest.api.entity.User;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


/**
 * Unit tests for the repository layer
 * {@link com.technicaltest.api.repository.UserRepository UserRepository}
 * In the test cases, we use the class TestEntityManager that allows us to
 * use EntityManager in tests.
 *
 * @author boubacar
 *
 */
@DataJpaTest
public class UserRepositorytTest {
    private static final String USERNAME = "ben";
    private static final String BIRTHDATE = "2000-01-01";
    private static final String COUNTRY = "France";
    private static final String PHONE_NUMBER = "0400000000";
    private static final String GENDER = "Male";

    private static User validUser;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    /**
     * Before starting the tests, instantiate the proprety validUser
     *
     * @throws ParseException
     */
    @BeforeAll
    public static void initUser() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(BIRTHDATE);
        Date date = sdf.parse(BIRTHDATE);
        validUser = new User().build(0, USERNAME, date,COUNTRY,PHONE_NUMBER,GENDER);
        validUser.setPhoneNumber(PHONE_NUMBER);
        validUser.setGender(GENDER);
    }

    /**
     * Unit Test for the findById method when user is saved, should return the
     * correct user
     *
     * @throws Exception
     */
    @Test
    public void whenUserSaved_findByIdReturnUser() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(BIRTHDATE);
        Date date = sdf.parse(BIRTHDATE);
        User userSaved = entityManager.persistAndFlush(new User().build(0, USERNAME, date,COUNTRY,PHONE_NUMBER,GENDER));
        Assertions.assertEquals(userRepository.findUserByName(USERNAME), userSaved);
    }

    /**
     * Unit Test for the findById method when user is not saved should return empty
     * optional
     *
     * @throws Exception
     */
    @Test
    public void whenNoUserSaved_findByIdReturnEmptyOptional() throws Exception {
        MatcherAssert.assertThat(userRepository.findById(validUser.getUserId()), is(Optional.empty()));
    }
}