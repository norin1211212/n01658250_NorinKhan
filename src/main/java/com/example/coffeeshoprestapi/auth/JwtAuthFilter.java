package com.example.coffeeshoprestapi.auth;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        System.out.println("Processing path: " + path);

        // Skip login endpoint
        if (path.endsWith("/auth/login")) {
            System.out.println("Skipping auth for login endpoint");
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");
        System.out.println("Auth header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Missing or invalid auth header");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();
        System.out.println("Token extracted: " + token);

        String username = JwtUtil.validateToken(token);
        System.out.println("Username from token: " + username);

        if (username == null) {
            System.out.println("Token validation failed");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() { return () -> username; }
            @Override
            public boolean isUserInRole(String role) { return "admin".equals(role); }
            @Override
            public boolean isSecure() {
                return requestContext.getUriInfo().getRequestUri().getScheme().equals("https");
            }
            @Override
            public String getAuthenticationScheme() { return "Bearer"; }
        });
    }

}

