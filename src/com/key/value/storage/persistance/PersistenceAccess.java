package com.key.value.storage.persistance;

import com.key.value.storage.database.Repository;

public class PersistenceAccess {

    public static Repository repository;

    private PersistenceAccess() {
    }

    public static Repository getInstance() {
        synchronized (PersistenceAccess.class) {
            if(repository==null) {
                repository = new Repository();
            }
        }
        return repository;
    }
}
