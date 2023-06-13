package com.polafix.polafix.controller;

//import java.util.List;

import javax.transaction.Transactional;

import com.polafix.polafix.pojos.*;

public interface UserService {

    User getUserById(String email);    
    @Transactional
    User saveUser(User user);
    SerieUser getSerieUser(Long id, User user);
    
    /*
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(String email, User user);
    User updateUserInlist(String email, User user);
    boolean deleteUser(String email);
    User updateInList(String email, User user);
    */
}