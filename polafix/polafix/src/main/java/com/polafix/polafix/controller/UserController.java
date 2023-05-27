package com.polafix.polafix.controller;

import java.time.Month;
import java.time.Year;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.polafix.polafix.pojos.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SerieService serieService;

//----------------------------------------------USER-------------------------------------------------------------
/* 
    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
*/
    @GetMapping("/{email}")
    @JsonView({Views.UserDescription.class})
    public ResponseEntity<User> getUserById(@PathVariable String email) {
        User existingUser = userService.getUserById(email);
        if(existingUser != null){
            return ResponseEntity.ok(existingUser);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{email}")
    @Transactional
    @JsonView({Views.UserDescription.class})
    public User updateUser(@PathVariable String email, @RequestBody User user) {
        return userService.updateUser(email, user);
    }

    @DeleteMapping("/{email}")
    public boolean deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }

 //----------------------------------------------SERIE USER-------------------------------------------------------------

/* 
    @GetMapping("/{email}/inlist")
    public List<SerieUser> getUserInlist(@PathVariable String email) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            return existingUser.getInlist();
        } else {
            return null;
        }
    }
*/
@GetMapping("/{email}/inlist/{id}") 
@JsonView ({Views.SerieUserDescription.class}) 
public ResponseEntity<SerieUser> getSerieUserInlist(@PathVariable String email, @PathVariable Long id, @RequestParam int season) { 
    User existingUser = userService.getUserById(email); 
    SerieUser serie = null;
    if (existingUser != null){
        serie = userService.getSerieUser(id, existingUser);
        return ResponseEntity.ok(serie);
    } else{
        return ResponseEntity.badRequest().build();
    }
}

//-----------------------------------------------BALANCES-------------------------------------------------------
    @GetMapping("/{email}/balances")
    public ResponseEntity<Balance> getBalances(@PathVariable String email) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            Balance b = existingUser.getLastBalance(existingUser.getBalances());
            return ResponseEntity.ok(b);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{email}/balances/{year}/{month}")
    public ResponseEntity<Balance> getBalances(@PathVariable String email, @PathVariable Year year, @PathVariable Month month) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            Balance b = existingUser.getHistoryBalance(month, year);
            return ResponseEntity.ok(b);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//----------------------------------------------AGREGAR SERIE----------------------------------------------------

    @PutMapping("/{email}/inlist")
    @Transactional
    public ResponseEntity<List<SerieUser>> addSerie(@PathVariable String email, @RequestParam Long id){
        Serie serie = serieService.getSerieById(id);
        User existingUser = userService.getUserById(email);
            if (existingUser != null) {
                existingUser.addSerie(serie);
                userService.saveUser(existingUser);
                List<SerieUser> lsu = existingUser.getInlist();
                return ResponseEntity.ok(lsu);
            } else {
                return ResponseEntity.badRequest().build();
        }
    }

//----------------------------------------------VER CHAPTER-------------------------------------------------------

    @PutMapping("/{email}/inlist/{id}/{season}") 
    @Transactional 
    public ResponseEntity<SerieUser> seeChapterFromInlist(@PathVariable String email, @PathVariable Long id, @PathVariable int season, @RequestParam int chapter){ 
        User existingUser = userService.getUserById(email); 
        SerieUser serie = null; 
        if (existingUser != null) { 
            SerieUser su = userService.getSerieUser(id, existingUser);
            existingUser.selectChapter(su,season,chapter); 
            userService.saveUser(existingUser); 
            serie = su; 
            return ResponseEntity.ok(serie);
            } 
            return ResponseEntity.badRequest().build();
    }
}