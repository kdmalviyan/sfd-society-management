package com.sfd.usermanagement.users;

import com.sfd.usermanagement.role.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author kuldeep
 */

@Document
@Data
public class UserInfo implements UserDetails {
    @Id
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String username;
    private String password;

    @DocumentReference
    private Role role;

    public static UserInfo getInstance(CreateUserRequest createUserRequest, Role role) {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(createUserRequest.getFirstName());
        userInfo.setMiddleName(createUserRequest.getMiddleName());
        userInfo.setLastName(createUserRequest.getLastName());
        userInfo.setEmail(createUserRequest.getEmail());
        userInfo.setPhone(createUserRequest.getPhone());
        userInfo.setUsername(createUserRequest.getUsername());
        userInfo.setPassword(createUserRequest.getPassword());
        userInfo.setRole(role);
        return userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
