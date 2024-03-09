package com.sfd.usermanagement.setup;

import com.sfd.usermanagement.config.mongodb.MultiTenantMongoConfig;
import com.sfd.usermanagement.config.mongodb.TenantContext;
import com.sfd.usermanagement.role.Role;
import com.sfd.usermanagement.role.RoleService;
import com.sfd.usermanagement.users.UserInfo;
import com.sfd.usermanagement.users.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;

/**
 * @author kuldeep
 */
@Configuration
public class SystemSetup {

    public SystemSetup(final RoleService roleService,
                       final UserInfoService userInfoService,
                       final PasswordEncoder passwordEncoder,
                       final MultiTenantMongoConfig multiTenantMongoConfig,
                       @Value("${system.setup.roles}") List<String> roles,
                       @Value("${system.setup.users}") List<String> users) {
        multiTenantMongoConfig.getMultiTenantConfig().keySet().forEach(key -> {
            TenantContext.setTenantId(key);
            createRoles(roles, roleService);
            createAdminUser(users, userInfoService, passwordEncoder, roleService);
            TenantContext.clear();
        });

    }

    private void createAdminUser(final List<String> users,
                                 final UserInfoService userInfoService,
                                 final PasswordEncoder passwordEncoder,
                                 final RoleService roleService) {
        users.stream()
                .filter(username -> Objects.isNull(userInfoService.findByUsername(username)))
                .forEach(username -> {
                    Role role = roleService.findByName("ROLE_SUPERADMIN");
                    UserInfo userInfo = new UserInfo();
                    userInfo.setFirstName("Super Admin");
                    userInfo.setUsername(username);
                    userInfo.setPassword(passwordEncoder.encode("Password@1"));
                    userInfo.setRole(role);
                    userInfoService.save(userInfo);
                });
    }

    private void createRoles(final List<String> roles, final RoleService roleService) {
        roles.stream()
                .filter(roleName -> Objects.isNull(roleService.findByName(roleName)))
                .forEach(roleName -> {
                    Role role = new Role();
                    role.setName(roleName);
                    roleService.createRole(role);
                });
    }
}
