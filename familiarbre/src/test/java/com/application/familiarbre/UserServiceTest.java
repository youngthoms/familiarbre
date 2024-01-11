//package com.application.familiarbre;
//
//import com.application.familiarbre.models.dao.UserRepository;
//import com.application.familiarbre.models.entites.User;
//import com.application.familiarbre.models.services.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    public void testSaveUser() {
//        User mockUser = new User();
//        when(userRepository.save(any(User.class))).thenReturn(mockUser);
//
//        User result = userService.saveUser(mockUser);
//
//        assertNotNull(result);
//        assertEquals(mockUser, result);
//    }
//
//    @Test
//    public void testLoadUserByUsernameSuccess() {
//        String email = "test@example.com";
//        User mockUser = new User();
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));
//
//        UserDetails result = userService.loadUserByUsername(email);
//
//        assertNotNull(result);
//        assertEquals(mockUser, result);
//    }
//
//    @Test
//    public void testLoadUserByUsernameNotFound() {
//        String email = "nonexistent@example.com";
//        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> {
//            userService.loadUserByUsername(email);
//        });
//    }
//
//    @Test
//    public void testGetAll() {
//        List<User> mockUsers = Arrays.asList(new User(), new User()); // Replace with your User class constructors
//        when(userRepository.findAll()).thenReturn(mockUsers);
//
//        List<User> result = userService.getAll();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals(mockUsers, result);
//    }
//
//    @Test
//    public void testGetByIdSuccess() {
//        Long id = 1L;
//        User mockUser = new User();
//        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));
//
//        User result = userService.getById(id);
//
//        assertNotNull(result);
//        assertEquals(mockUser, result);
//    }
//
//    @Test
//    public void testGetByIdNotFound() {
//        Long id = 99L;
//        when(userRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(NoSuchElementException.class, () -> {
//            userService.getById(id);
//        });
//    }
//
//
//    @Test
//    public void testLoadUserByTokenSuccess() {
//        String testToken = "testToken";
//        User mockUser = new User();
//        when(userRepository.findByToken(testToken)).thenReturn(Optional.of(mockUser));
//
//        UserDetails result = userService.loadUserByToken(testToken);
//
//        assertNotNull(result);
//        assertEquals(mockUser, result);
//    }
//
//    @Test
//    public void testLoadUserByTokenNotFound() {
//        String testToken = "nonExistingToken";
//        when(userRepository.findByToken(testToken)).thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> {
//            userService.loadUserByToken(testToken);
//        });
//    }
//}
