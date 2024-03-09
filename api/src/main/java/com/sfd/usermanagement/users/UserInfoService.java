package com.sfd.usermanagement.users;

import com.sfd.usermanagement.role.Role;
import com.sfd.usermanagement.role.RoleException;
import com.sfd.usermanagement.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author kuldeep
 */
@Service
@Slf4j
public record UserInfoService(MongoTemplate mongoTemplate, RoleService roleService) implements UserDetailsService {
    public UserInfo createUser(CreateUserRequest createUserRequest) {
        Role role = roleService.findByName(createUserRequest.getRoleName());
        if(Objects.isNull(role)) {
            throw new RoleException("Role not found", HttpStatus.BAD_REQUEST.value());
        }
        return mongoTemplate.save(UserInfo.getInstance(createUserRequest, role));
    }

    public List<UserInfo> findAll() {
        return mongoTemplate().findAll(UserInfo.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    public UserInfo findByUsername(String username) {
        Criteria criteria = Criteria.where("username").is(username);
        Query query = Query.query(criteria);
        return mongoTemplate.findOne(query, UserInfo.class);
    }

    public UserInfo save(UserInfo userInfo) {
        return mongoTemplate.save(userInfo);
    }
}
