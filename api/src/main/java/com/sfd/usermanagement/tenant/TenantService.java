package com.sfd.usermanagement.tenant;

import com.sfd.usermanagement.aws.s3.S3DownloadService;
import com.sfd.usermanagement.config.mongodb.MultiTenantMongoConfig;
import com.sfd.usermanagement.config.mongodb.TenantContext;
import com.sfd.usermanagement.config.mongodb.TenantMongoClient;
import com.sfd.usermanagement.config.mongodb.TenantProperties;
import com.sfd.usermanagement.role.Role;
import com.sfd.usermanagement.role.RoleService;
import com.sfd.usermanagement.users.UserInfo;
import com.sfd.usermanagement.users.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author kuldeep
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TenantService {
    private final S3DownloadService s3DownloadService;
    private final MultiTenantMongoConfig multiTenantMongoConfig;
    private final RoleService roleService;
    private final UserInfoService userInfoService;
    private final PasswordEncoder passwordEncoder;
    @Value("${system.setup.roles}") List<String> roles;
    @Value("${system.setup.users}") List<String> users;

    @Value("${spring.profiles.active}")
    String activeProfile;

    public String refreshConfig() {
        String filename = "mongodb-tenants-local.json";
        if(Objects.nonNull(activeProfile)
                && (Objects.equals("profile", "docker")
                || Objects.equals("profile", "prod"))) {
            filename = "mongodb-tenants-local.json";
        }
        List<TenantProperties> tenantProperties = s3DownloadService.downloadByActiveProfile(filename);
        multiTenantMongoConfig.refreshMultiTenantConfig(tenantProperties);
        TreeMap<String, TenantMongoClient> multiTenantConfig = multiTenantMongoConfig.getMultiTenantConfig();
        multiTenantMongoConfig.getMultiTenantConfig().keySet().forEach(key -> {
            TenantContext.setTenantId(key);
            createRoles(roles, roleService);
            createAdminUser(users, userInfoService, passwordEncoder, roleService);
            TenantContext.clear();
        });
        return "Refreshed successfully!";
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
