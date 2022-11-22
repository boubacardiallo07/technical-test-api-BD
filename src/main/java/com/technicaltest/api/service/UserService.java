package com.technicaltest.api.service;


import com.technicaltest.api.dto.UserRequest;
import com.technicaltest.api.entity.User;
import com.technicaltest.api.exception.UserNotFoundException;
import com.technicaltest.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * This class manages services to manage a user.
 *
 * @author boubacar
 */
@Transactional
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * register a user in the database
     *
     * @param userRequest the user to register
     * @return a user
     */
    public User saveUser(UserRequest userRequest) throws UserNotFoundException {
        if (userExists(userRequest)) {
            throw new UserNotFoundException("user " + userRequest.getName() + " already exists.");
        } else {
            User user = User.
                    build(0, userRequest.getName(), userRequest.getBirthDate(),
                            userRequest.getCountry(), userRequest.getPhoneNumber(), userRequest.getGender());
            return userRepository.save(user);
        }
    }

    /**
     * get the details of all users from the database
     *
     * @return users list
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * get user details by id from database
     *
     * @param userId username
     * @return a user
     * @throws UserNotFoundException
     */
    public User getUserById(int userId) throws UserNotFoundException {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("user not found with id : " + userId);
        }
    }

    /**
     * get user details by name from database
     *
     * @param name username
     * @return a user
     * @throws UserNotFoundException
     */
    public User getUserByName(String name) throws UserNotFoundException {
        User user = userRepository.findUserByName(name);
        if (user != null) {
            return user;
        } else {
            throw new UserNotFoundException("user not found with name : " + name);
        }
    }

    /**
     * check if the user has already been registered
     *
     * @param userRequest the user to verify
     * @return true if the user already exists
     */
    public boolean userExists(UserRequest userRequest) {
        Optional<User> dbUser = Optional.ofNullable(userRepository.findUserByName(userRequest.getName()));
        return dbUser.isPresent();
    }
}
