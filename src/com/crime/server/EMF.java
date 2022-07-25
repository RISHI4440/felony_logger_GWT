package com.crime.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {
    
    private EMF() {}

    public static EntityManagerFactory get() {
        
        EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("CRIMELOGGERPU");
        return emfInstance;
    }
}
