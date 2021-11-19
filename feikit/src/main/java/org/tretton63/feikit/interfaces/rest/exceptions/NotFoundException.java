package org.tretton63.feikit.interfaces.rest.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{
    public NotFoundException(int id) {
        super(String.format("ID %d was not found", id));
    }

    public NotFoundException(UUID id){
        super(String.format("ID %s was not found", id));
    }
}
