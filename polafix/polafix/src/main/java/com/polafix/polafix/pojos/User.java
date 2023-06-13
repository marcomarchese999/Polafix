package com.polafix.polafix.pojos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.Year;
import java.time.Month;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.polafix.polafix.controller.Views;

@Entity
public class User {

    @Id 
    @JsonView ({Views.UserDescription.class}) 
    private String email; 
    @JsonView ({Views.UserDescription.class}) 
    private String name; 
    @JsonView ({Views.UserDescription.class}) 
    private String surname; 
    private Subscription type;   
    private Date dateOfBirth; 
    @JsonIgnore  
    private String iban; 
    @JsonIgnore  
    private String password; 
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
    @OrderColumn(name = "index") 
    @JsonView ({Views.UserDescription.class}) 
    private List<SerieUser> ended; 
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderColumn(name = "index") 
    @JsonView ({Views.UserDescription.class}) 
    private List<SerieUser> started; 
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) 
    @OrderColumn(name = "index") 
    @JsonView ({Views.UserDescription.class}) 
    private List<SerieUser> inlist; 
    @OneToMany(cascade = CascadeType.ALL) 
    @OrderColumn(name = "index") 
    private List<Balance> balances;

    public User() {}

    public User(String email, Subscription type, String IBAN, String name, String surname, Date dateOfBirth){
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.type=type;
        this.iban=IBAN;
        this.dateOfBirth = dateOfBirth;
        
        
        setPassword(password);
        this.balances = new ArrayList<Balance>();
        this.started= new ArrayList<SerieUser>();
        this.ended= new ArrayList<SerieUser>();
        this.inlist= new ArrayList<SerieUser>();

        Balance firstB = new Balance(0, LocalDate.now().getMonth(), Year.now());
        balances.add(firstB);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return this.password;
    }

    public List<Balance> getBalances() {
        return this.balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth(){
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date date){
        this.dateOfBirth=date;
    }

    private void setPassword(String password){
        this.password=password;
    }

    public Subscription getType() {
        return this.type;
    }

    public void setType(Subscription type) {
        this.type = type;
    }

    public String getIBAN() {
        return iban;
    }

    public void setIBAN(String IBAN) {
        this.iban = IBAN;
    }

    public List<SerieUser> getEnded() {
        return ended;
    }

    public List<SerieUser> getStarted() {
        return started;
    }

    public List<SerieUser> getInlist() {
        return inlist;
    }

//--------------------------------------------------------------------------------------------------//

    //Potria ser private, es public para el test initial
    public Balance addBalance(Month month, Year year) {
        float saldo = 0;
        Balance balance = new Balance(saldo, month, year);
        balances.add(balance);
        return balance;
    }

    public Balance getLastBalance(List<Balance> balances){
        return balances.get(balances.size()-1);
    }

    
    private void addCharge(SerieUser serie, int season, int chapter){
        LocalDate date = LocalDate.now();
        Month month = date.getMonth();
        int year = date.getYear();
        Balance lastBalance = getLastBalance(this.balances);
        Charge charge = new Charge(date, serie.getSerie().getName(), season, chapter, serie.getSerie().getType().getprice());
        if(lastBalance.getMonth().equals(month) && year==lastBalance.getYear().getValue()){
            lastBalance.addCharge(charge);
        }else{
            Balance balance = addBalance(month, Year.of(year));
            balance.addCharge(charge);
        }
    } 

    public Balance getHistoryBalance(Month month, Year year){
        List<Balance> balances = this.getBalances();
        for (Balance balance : balances){
            Month m = balance.getMonth();
            Year y = balance.getYear();
            if(year.equals(y) && month.equals(m)){
                return balance;
            }
        }
        return null;
    }
    
    private boolean isInList(Serie serie, List<SerieUser> lista){
        for(SerieUser s : lista){
            if(s.getSerie().equals(serie)){
                return true;
            }
        }
        return false;
    }

    private boolean isInListUser(SerieUser serie, List<SerieUser> lista){ 
        for(SerieUser s : lista){ 
            if(s.getSerie().equals(serie.getSerie())){ 
                return true; 
            } 
        } 
        return false; 
    }

    public void addSerie(Serie serie){
        if(isInList(serie, inlist)==false && isInList(serie, ended)==false && isInList(serie, started)==false){
            SerieUser serieuser = new SerieUser(serie);
            inlist.add(serieuser);
        }
    }

    public SerieUser getSerieUser(List<SerieUser> lista, Long id){
        for(SerieUser su : lista){
            if(su.getId()==id){
                return su;
            }
        }
        return null; 
    }

    private boolean isLastChapter(SerieUser serie, ChapterSeen chapter){ 
        return serie.isLastChapter(chapter);
    }

    public void selectChapter(SerieUser s, int season, int chapter){ 
        ChapterSeen c = s.findChapter(s.getUserChapters(), season, chapter);
        s.addChapterSeen(season, chapter); 
        s.setCurrentSeason(season); 
        int num_chapters = s.getSerie().getSeason(season).getChapters().size(); 
        s.setNextSeason(c, num_chapters);
        this.addCharge(s, season, chapter);  
        if(isInListUser(s, inlist)){ 
            if(isLastChapter(s, c)){  
                inlist.remove(s); 
                ended.add(s); 
                s.setCurrentSeason(1); 
            } 
            else{ 
                inlist.remove(s); 
                started.add(s);     
            }   
        } 
        else{ 
            if(isInListUser(s, started)){
                if(isLastChapter(s, c)){ 
                    ended.add(s);
                    started.remove(s); 
                    s.setCurrentSeason(1); 
                } 
            } 
        } 
    }

    //Para el test initial
    public SerieUser viewSerieUser(List<SerieUser> userList, String nameSerie){
        for (SerieUser serieUtente : userList) {
            if(serieUtente.getSerie().getName().equals(nameSerie))
                return serieUtente;
        }
        return null; 
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
