package com.example.coffeeshoprestapi.app;

import com.example.coffeeshoprestapi.auth.AuthResource;
import com.example.coffeeshoprestapi.auth.JwtAuthFilter;
import com.example.coffeeshoprestapi.resources.CoffeeShopResource;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class CoffeeShopApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(AuthResource.class);
        classes.add(JwtAuthFilter.class);
        classes.add(CoffeeShopResource.class); // your other endpoints
        return classes;
    }
}
