package com.sfd.usermanagement.tenant;

import com.sfd.usermanagement.aws.s3.S3DownloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kuldeep
 */

@RestController
@RequestMapping("tenants")
public record TenantController(TenantService tenantService) {

    @GetMapping("refresh")
    public ResponseEntity<?> refreashTenatConfiguration() {
        return ResponseEntity.ok(tenantService.refreshConfig());
    }
}
