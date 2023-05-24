package com.food.delivery.repositories;

import com.food.delivery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);

    User findByEmailOrContact(String email, String contact);

}
