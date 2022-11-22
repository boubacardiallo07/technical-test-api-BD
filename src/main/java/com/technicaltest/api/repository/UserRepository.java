package com.technicaltest.api.repository;

import com.technicaltest.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for generic CRUD for a {@link com.technicaltest.api.entity.User User} type.
 *
 * @author boubacar
 */
public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByUserId(int userId);
    User findUserByName(String name);
}
