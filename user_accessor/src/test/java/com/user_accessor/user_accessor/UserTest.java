package com.user_accessor.user_accessor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.user_accessor.user_accessor.BL.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.user_accessor.user_accessor.BL.UserController;
import com.user_accessor.user_accessor.DAL.user.User;

import lombok.extern.log4j.Log4j2;


import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

        import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.user_accessor.user_accessor.DAL.user.LoginUser;
import com.user_accessor.user_accessor.DAL.user.User;
import com.user_accessor.user_accessor.DAL.user.UserOut;
import com.user_accessor.user_accessor.DAL.user.UserRepository;
@Log4j2
@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private LoginUser testLoginUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User()
                .setEmail("test@test.com")
                .setName("Test User")
                .setPassword("password");
        testLoginUser = new LoginUser("test@test.com", "password");
    }

    @Test
    void testCreateUser_WithName() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.creteUser(testUser);

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getName(), result.getName());
        assertNull(result.getPassword()); // Password should not be returned
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUser_WithoutName() {
        User userWithoutName = new User()
                .setEmail("test@test.com")
                .setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(userWithoutName);

        User result = userService.creteUser(userWithoutName);

        assertEquals(userWithoutName.getEmail(), result.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testLoginUser_Success() {
        User dbUser = new User()
                .setEmail("test@test.com")
                .setPassword(UserService.getSHA256Hash("password"))
                .setName("Test User");

        when(userRepository.findById(testLoginUser.getEmail())).thenReturn(Optional.of(dbUser));

        User result = userService.loginUser(testLoginUser.getEmail());

        assertNotNull(result);
        assertEquals(dbUser.getEmail(), result.getEmail());
        assertEquals(dbUser.getName(), result.getName());
    }

    @Test
    void testUpdateUserName() {
        User user = new User()
                .setEmail("test@test.com")
                .setName("Name")
                .setPassword("1234");
        User result1 = userService.creteUser(user);
        assertNotNull(result1);
        UserOut updatedUser = new UserOut()
                .setEmail("test@test.com")
                .setName("New Name");

        UserOut result2 = userService.updateUserName(updatedUser);

        assertNotNull(result2);
        assertNotEquals(result1.getName(), result2.getName());
        assertEquals(result1.getEmail(), result2.getEmail());


    }
}

    


