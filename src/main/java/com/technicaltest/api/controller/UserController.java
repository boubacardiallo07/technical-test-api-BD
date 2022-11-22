package com.technicaltest.api.controller;

import com.technicaltest.api.dto.UserRequest;
import com.technicaltest.api.entity.User;
import com.technicaltest.api.exception.UserNotFoundException;
import com.technicaltest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * the main application controller. Its is from expose /users endpoint
 *
 * @author boubacar
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * This method adds a user through the POST method. Returns an error if the user already exists.
     *
     * @param userRequest : the user to be registered
     * @return the user saved with Status: 200 OK or error with Status: 409 Conflict.
     */
    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRequest userRequest) throws UserNotFoundException {
        return new ResponseEntity<>(userService.saveUser(userRequest), HttpStatus.CREATED);
    }

    /**
     * @return all users list.
     */
    @GetMapping("/fetchAll")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    /**
     * @param userName the username of the user to find
     * @return the user if exists, or NOT_FOUND error if not
     * @throws UserNotFoundException when a user is not found, this exception is thrown
     */
    @GetMapping()
    public ResponseEntity<User> getUserByName(@RequestParam(value="name") String userName) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserByName(userName));
    }

    /**
     * @param userId the userId of the user to find
     * @return the user if exists, or NOT_FOUND error if not
     * @throws UserNotFoundException when a user is not found, this exception is thrown
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
