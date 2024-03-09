package com.sfd.usermanagement.auth;

import com.sfd.usermanagement.role.Role;
import com.sfd.usermanagement.role.RoleService;
import com.sfd.usermanagement.security.InvalidCredentialsException;
import com.sfd.usermanagement.security.JwtTokenGenerator;
import com.sfd.usermanagement.users.UserException;
import com.sfd.usermanagement.users.UserInfo;
import com.sfd.usermanagement.users.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

/**
 * @author kuldeep
 */
@Service
@Slf4j
public record AuthenticationService(UserInfoService userInfoService,
                                    JwtTokenGenerator jwtTokenGenerator,
                                    PasswordEncoder passwordEncoder,
                                    RoleService roleService) {
    public AuthResponse login(final LoginRequest loginRequest) {

        UserDetails userDetails = userInfoService.loadUserByUsername(loginRequest.getUsername());
        if(Objects.isNull(userDetails)) {
            throw new InvalidCredentialsException("Username or Password is incorrect.", HttpStatus.UNAUTHORIZED.value());
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        try {
            String authToken = jwtTokenGenerator.generate((UserInfo) userDetails, false);
            return AuthResponse.builder()
                    .token(authToken)
                    .role(((UserInfo) userDetails).getRole())
                    .build();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        if(Objects.nonNull(userInfoService.findByUsername(signupRequest.getUsername()))){
            throw new UserException("Username is already taken, please try something different.");
        }
        // Default role is CUSTOMER
        Role role = roleService.findByName("ROLE_CUSTOMER");
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(signupRequest.getUsername());
        userInfo.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userInfo.setEmail(signupRequest.getEmail());
        userInfo.setPhone(signupRequest.getPhone());
        userInfo.setFirstName(signupRequest.getFirstName());
        userInfo.setMiddleName(signupRequest.getMiddleName());
        userInfo.setLastName(signupRequest.getLastName());
        userInfo.setRole(role);
        userInfo = userInfoService.save(userInfo);
        return SignupResponse.builder()
                .message("User created successfully")
                .firstName(userInfo.getFirstName())
                .middleName(userInfo.getMiddleName())
                .lastName(userInfo.getLastName())
                .username(userInfo.getUsername())
                .build();
    }
}
