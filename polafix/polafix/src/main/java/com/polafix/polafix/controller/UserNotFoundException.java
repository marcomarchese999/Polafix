package com.polafix.polafix.controller;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("User not found with id " + id);
    }
}