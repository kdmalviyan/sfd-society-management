package com.sfd.usermanagement.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kuldeep
 */

@RestController
@RequestMapping(value = "users")
public record UserController(UserInfoService userInfoService) {
    @PostMapping
    public ResponseEntity<UserInfo> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userInfoService.createUser(createUserRequest));
    }

    @GetMapping
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        return ResponseEntity.ok(userInfoService.findAll());
    }
}
