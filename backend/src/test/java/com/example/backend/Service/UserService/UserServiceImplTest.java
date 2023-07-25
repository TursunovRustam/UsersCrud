package com.example.backend.Service.UserService;

import com.example.backend.Entity.Role;
import com.example.backend.Entity.User;
import com.example.backend.Payload.LoginReq;
import com.example.backend.Payload.UserReq;
import com.example.backend.Projection.UserProjection;
import com.example.backend.Repo.RoleRepo;
import com.example.backend.Repo.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepo usersRepo;
    @Mock
    private RoleRepo roleRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserServiceImpl underTest;
    String secretKey = "timurrustamulugbektimurabdusobirjahongir";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new UserServiceImpl(authenticationConfiguration, userDetailsService, usersRepo, roleRepo, passwordEncoder);
        underTest.setSecretKey(secretKey);
    }

    @Test
    public void itShouldLogin() throws Exception {
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test_user");
        when(userDetailsService.loadUserByUsername("test_user")).thenReturn(userDetails);
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        AuthenticationConfiguration authenticationConfiguration = Mockito.mock(AuthenticationConfiguration.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);
        underTest.setAuthenticationConfiguration(authenticationConfiguration);
        LoginReq loginReq = new LoginReq("test_user", "password");
        String token = underTest.login(loginReq);
        Assertions.assertEquals("test_user", Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getIssuer());
    }

        @Test
        void itShouldGetUserByUsername() throws Exception {
            // Given
            String username = "testusername";
            String password = "password";

            // Create a User object
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRoles(new ArrayList<>());

            // Create UserDetails instance
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, password, new ArrayList<>());

            // Mock userDetailsService to return UserDetails
            when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

            // Mock AuthenticationManager
            AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
            when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);
            when(authenticationManager.authenticate(Mockito.any())).thenReturn(new TestingAuthenticationToken(userDetails, null));

            // Save the user
            when(usersRepo.save(Mockito.any(User.class))).thenReturn(user);

            // Mock usersRepo.findByUsername(username)
            when(usersRepo.findByUsername(username)).thenReturn(user);

            // Prepare login request
            LoginReq loginReq = new LoginReq();
            loginReq.setUsername(username);
            loginReq.setPassword(password);

            // Login to generate a token
            String token = underTest.login(loginReq);

            // When
            UserDetails userByUsername = underTest.getUserByUsername(token);

            // Then
            Assertions.assertEquals(username, userByUsername.getUsername());
        }
    @Test
    void itShouldFindUsernameByToken() throws Exception {
        String username = "testusername";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(new ArrayList<>());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, password, new ArrayList<>());
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(Mockito.any())).thenReturn(new TestingAuthenticationToken(userDetails, null));
        when(usersRepo.save(Mockito.any(User.class))).thenReturn(user);
        LoginReq loginReq = new LoginReq();
        loginReq.setUsername(username);
        loginReq.setPassword(password);
        String token = underTest.login(loginReq);
        String actualUsername = underTest.findUsernameByToken(token);
        Assertions.assertEquals(username, actualUsername);
    }

    @Test
    void itShouldGetUsers() {
        List<UserProjection> users = new ArrayList<>();
        when(usersRepo.getUsers()).thenReturn(users);
        List<UserProjection> resUsers = underTest.getUsers();
        Assertions.assertEquals(resUsers, users);
    }

    @Test
    void itShouldRegister() {
        Role testRole = new Role(null, "TEST_ROLE");
        roleRepo.save(testRole);
        UserReq userReq = new UserReq("TEST_ROLE");
        userReq.setUsername("TEST_USER");
        userReq.setPassword("123");
        List<Role> roles = new ArrayList<>();
        roles.add(testRole);
        User savedUser = new User(null, "TEST_USER", "123", roles);
        when(usersRepo.save(Mockito.any(User.class))).thenReturn(savedUser);
        User resUser = underTest.register(userReq);
        Assertions.assertEquals(savedUser, resUser);
    }

}