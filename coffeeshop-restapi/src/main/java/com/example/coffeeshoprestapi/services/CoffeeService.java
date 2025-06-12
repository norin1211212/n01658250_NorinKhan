package com.example.coffeeshoprestapi.services;

import com.example.coffeeshoprestapi.models.Coffee;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class CoffeeService {

    @PersistenceContext(unitName = "CoffeePU")
    private EntityManager em;

    public List<Coffee> getAllCoffees() {
        return em.createQuery("SELECT c FROM Coffee c", Coffee.class).getResultList();
    }

    public Coffee addCoffee(Coffee coffee) {
        em.persist(coffee);
        return coffee;
    }

    public boolean deleteCoffee(int id) {
        Coffee c = em.find(Coffee.class, id);
        if (c != null) {
            em.remove(c);
            return true;
        }
        return false;
    }
}