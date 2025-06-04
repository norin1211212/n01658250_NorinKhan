package com.example.coffeeshoprestapi.auth;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Dummy auth logic
        if ("admin".equals(username) && "nonu".equals(password)) {
            String token = JwtUtil.generateToken(username);
            Map<String, String> result = new HashMap<>();
            result.put("token", token);
            return Response.ok(result).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
