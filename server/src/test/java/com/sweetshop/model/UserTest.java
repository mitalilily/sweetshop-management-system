package com.sweetshop.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserTest {

    @Test
    void testUserCreation() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(UserRole.USER);

        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(UserRole.USER, user.getRole());
    }

    @Test
    void testUserWithAdminRole() {
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        user.setPassword("admin123");
        user.setRole(UserRole.ADMIN);

        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    void testUserEquality() {
        User user1 = new User();
        user1.setUsername("testuser");
        user1.setEmail("test@example.com");

        User user2 = new User();
        user2.setUsername("testuser");
        user2.setEmail("test@example.com");

        assertEquals(user1.getUsername(), user2.getUsername());
        assertEquals(user1.getEmail(), user2.getEmail());
    }
}

