package com.polafix.polafix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.polafix.polafix.pojos.*;
import com.polafix.polafix.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(String email) {
       return userRepository.findById(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    private boolean isInList(Long id, List<SerieUser> lista){
        for(SerieUser s : lista){
            if(s.getId()==id){
                return true;
            }
        }
        return false;
    }

    @Override 
    public SerieUser getSerieUser(Long id, User user){
        SerieUser serie = null;
        if(user != null){
            if(isInList(id, user.getInlist())){
                serie = user.getSerieUser(user.getInlist(), id);}
            else if(isInList(id, user.getStarted())){
                serie = user.getSerieUser(user.getStarted(), id);}
            else 
                serie = user.getSerieUser(user.getEnded(), id);
        }
        return serie;
    }


    /* 
    
    OPERACIONES PARA EL SISTEMA, NO UTILIZADAS EN LAS INTERFACES DE LA DESCRIPCION DEL SISTEMA
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String email, User user) {
        User existingUser = userRepository.findById(email).orElse(null);
        if (existingUser != null) {
            existingUser.setType(user.getType());
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    @Override
    public User updateInList(String email, User user) {
        User existingUser = userRepository.findById(email).orElse(null);
        if (existingUser != null) {
            existingUser.getInlist().add(user.getInlist().get(user.getInlist().size()-1));
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteUser(String email) {
        boolean deleted = false;
        if (userRepository.existsById(email)) {
            userRepository.deleteById(email);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public User updateUserInlist(String email, User user) {
        User existingUser = userRepository.findById(email).orElse(null);
        if (existingUser != null) {
            existingUser.getInlist().add(user.getInlist().get(user.getInlist().size()-1));
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }
   */ 
}