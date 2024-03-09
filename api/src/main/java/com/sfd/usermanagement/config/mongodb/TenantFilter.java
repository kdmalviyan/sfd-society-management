package com.sfd.usermanagement.config.mongodb;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author kuldeep
 */
@AllArgsConstructor
@Slf4j
@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_ID_HEADER = "X-Tenant";

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws IOException, ServletException {
        final String tenant = request.getHeader(TENANT_ID_HEADER);

        if (StringUtils.hasText(tenant)) {
            TenantContext.setTenantId(tenant);
        } else {
            throw new TenantNotFoundException("Tenant not found.");
        }
        filterChain.doFilter(request, response);
        TenantContext.clear();
    }
}
