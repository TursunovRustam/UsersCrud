package com.example.backend.Service.UserService;

import com.example.backend.Entity.Role;
import com.example.backend.Entity.User;
import com.example.backend.Payload.LoginReq;
import com.example.backend.Payload.UserReq;
import com.example.backend.Projection.UserProjection;
import com.example.backend.Repo.RoleRepo;
import com.example.backend.Repo.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class UserServiceImpl implements UserService, EnvironmentAware {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepo usersRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${app.security.secretKey}")
    public static String secretKey;
    @Override
    public void setEnvironment(Environment environment) {
        secretKey = environment.getProperty("app.security.secretKey");
    }

    public static void setSecretKey(String secretKey) {
        UserServiceImpl.secretKey = secretKey;
    }

    @Override
    public String login(LoginReq loginReq) throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginReq.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                loginReq.getPassword(),
                userDetails.getAuthorities()
        );
        authenticationConfiguration.getAuthenticationManager().authenticate(authenticationToken);
        return Jwts
                .builder()
                .setIssuer(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 365))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    @Override
    public UserDetails getUserByUsername(String token) {
        String usernameByToken = findUsernameByToken(token);
        return usersRepo.findByUsername(usernameByToken);
    }
    public String findUsernameByToken(String token) {
        Claims body = Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return body.get("iss").toString();
    }

    @Override
    public List<UserProjection> getUsers() {
        return usersRepo.getUsers();
    }
    @Override
    public User register(UserReq userData) {
        Role role = roleRepo.findByName(userData.getRoleName());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        return usersRepo.save(new User(
                null,
                userData.getUsername(),
                passwordEncoder.encode(userData.getPassword()),
                roles
        ));
    }
}
