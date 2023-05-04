package com.polafix.polafix.controller;

import java.time.Month;
import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.polafix.polafix.pojos.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SerieService serieService;

//----------------------------------------------USER-------------------------------------------------------------

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public User getUserById(@PathVariable String email) {
        return userService.getUserById(email);
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{email}")
    public User updateUser(@PathVariable String email, @RequestBody User user) {
        return userService.updateUser(email, user);
    }

    @DeleteMapping("/{email}")
    public boolean deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }

 //----------------------------------------------SERIE USER-------------------------------------------------------------

    @GetMapping("/{email}/started")
    public List<SerieUser> getUserStarted(@PathVariable String email) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            return existingUser.getStarted();
        } else {
            return null;
        }
    }

    @GetMapping("/{email}/started/{id}")
    public SerieUserResponse getSerieUserStarted(@PathVariable String email, @PathVariable Long id, @RequestParam int season) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            for (SerieUser s : existingUser.getStarted()) {
                if(s.getId()==(id)){
                    List<ChapterSeen> lista = s.getChapterForSeason(season);
                    SerieUserResponse sur = new SerieUserResponse(s.getTitle(), s.getCurrentSeason(), s.getSerie().getType(), lista);
                    return sur;
                }
            }
        }
        return null;
    }

    @GetMapping("/{email}/ended")
    public List<SerieUser> getUserEnded(@PathVariable String email) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            return existingUser.getEnded();
        } else {
            return null;
        }
    }

    @GetMapping("/{email}/ended/{id}")
    public SerieUserResponse getSerieUserEnded(@PathVariable String email, @PathVariable Long id, @RequestParam int season) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            for (SerieUser s : existingUser.getEnded()) {
                if(s.getId()==id){
                    List<ChapterSeen> lista = s.getChapterForSeason(season);
                    SerieUserResponse sur = new SerieUserResponse(s.getTitle(), s.getCurrentSeason(), s.getSerie().getType(), lista);
                    return sur;
                }
            }
        }
        return null;
    }

    @GetMapping("/{email}/inlist")
    public List<SerieUser> getUserInlist(@PathVariable String email) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            return existingUser.getInlist();
        } else {
            return null;
        }
    }

    @GetMapping("/{email}/inlist/{id}")
    public SerieUserResponse getSerieUserInlist(@PathVariable String email, @PathVariable Long id, @RequestParam int season) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            for (SerieUser s : existingUser.getInlist()) {
                if(s.getId()==id){
                    List<ChapterSeen> lista = s.getChapterForSeason(season);
                    SerieUserResponse sur = new SerieUserResponse(s.getTitle(), s.getCurrentSeason(), s.getSerie().getType(), lista);
                    return sur;
                }
            }
        }
        return null;
    }

//-----------------------------------------------BALANCES-------------------------------------------------------
    @GetMapping("/{email}/balances")
    public Balance getBalances(@PathVariable String email) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            return existingUser.getLastBalance(existingUser.getBalances());
        } else {
            return null;
        }
    }

    @GetMapping("/{email}/balances/{year}/{month}")
    public Balance getBalances(@PathVariable String email, @PathVariable Year year, @PathVariable Month month) {
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            return existingUser.getHistoryBalance(month, year);
        } else {
            return null;
        }
    }

//----------------------------------------------AGREGAR SERIE----------------------------------------------------

    @PutMapping("/{email}/inlist")
    public User addSerie(@PathVariable String email, @RequestParam Long id){
        Serie serie = serieService.getSerieById(id);
        User existingUser = userService.getUserById(email);
            if (existingUser != null) {
                existingUser.addSerie(serie);
                return userService.saveUser(existingUser);
            } else {
                return null;
        }
    }

//----------------------------------------------VER CHAPTER-------------------------------------------------------

    @PutMapping("/{email}/inlist/{id}/{season}")
    public User seeChapterFromInlist(@PathVariable String email, @PathVariable Long id, @PathVariable int season, @RequestParam int chapter){
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            for (SerieUser su : existingUser.getInlist()) {
                if(su.getId()==id){
                    existingUser.selectChapter(su,season,chapter);
                    return userService.saveUser(existingUser);
                }
            }
            return userService.saveUser(existingUser);
        } else return null;
    }

    @PutMapping("/{email}/started/{id}/{season}")
    public User seeChapterFromStarted(@PathVariable String email, @PathVariable Long id, @PathVariable int season, @RequestParam int chapter){
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            for (SerieUser su : existingUser.getStarted()) {
                if(su.getId()==id){
                    existingUser.selectChapter(su,season,chapter);
                    return userService.saveUser(existingUser);
                }
            }
            return userService.saveUser(existingUser);
        } else return null;
    }

    @PutMapping("/{email}/ended/{id}/{season}")
    public User seeChapterFromEnded(@PathVariable String email, @PathVariable Long id, @PathVariable int season, @RequestParam int chapter){
        User existingUser = userService.getUserById(email);
        if (existingUser != null) {
            for (SerieUser su : existingUser.getEnded()) {
                if(su.getId()==id){
                    existingUser.selectChapter(su,season,chapter);
                    return userService.saveUser(existingUser);
                }
            }
            return userService.saveUser(existingUser);
        } else return null;
    }
}