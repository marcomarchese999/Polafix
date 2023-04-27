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
//import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

    @Id
    private String email;
    private String name;
    private String surname;
    private Subscription type;
    private Date dateOfBirth;
    private String IBAN;
    private String password;
    @OneToMany
    //(fetch = FetchType.EAGER)
    private List<SerieUser> ended;
    @OneToMany
    //(fetch = FetchType.EAGER)
    private List<SerieUser> started;
    @OneToMany
    //(fetch = FetchType.EAGER)
    private List<SerieUser> inlist;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Balance> balances;


    public User() {}

    public User(String email, Subscription type, String IBAN, String name, String surname, Date dateOfBirth){
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.type=type;
        this.IBAN=IBAN;
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
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
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

/////////////////////////

    public Balance addBalance(Month month, Year year) {
        float saldo = 0;
        Balance balance = new Balance(saldo, month, year);
        balances.add(balance);
        return balance;
    }

    public Balance getLastBalance(){
        return balances.get(balances.size()-1);
    }

    
    private void addCharge(SerieUser serie, int season, int chapter){
        LocalDate date = LocalDate.now();
        Month month = date.getMonth();
        int year = date.getYear();
        Charge charge = new Charge(date, serie.getSerie().getName(), season, chapter, serie.getSerie().getType().getprice());

        if(this.getLastBalance().getMonth().equals(month) && this.getLastBalance().getYear().getValue() == year){
            this.getLastBalance().addCharge(charge);
        }
        else{
            Balance newBalance = addBalance(month, Year.of(year));
            newBalance.addCharge(charge);
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

    private SerieUser getSerieUser(List<SerieUser> lista, SerieUser serie){
        for(int i=0; i<lista.size(); i++){
            if(lista.get(i).getSerie().equals(serie.getSerie())){
                return lista.get(i);
            }
        }
        return null; // sistema!!!
    }

    private boolean isLastChapter(SerieUser serie, ChapterSeen chapter){ 
        return serie.isLastChapter(chapter);
    }

    public void selectChapter(SerieUser s, int season, int chapter){ 
        if(isInListUser(s, inlist)){ 
            SerieUser serie = getSerieUser(inlist, s); 
            ChapterSeen c = serie.findChapter(serie.getUserChapters(), season, chapter); 
            serie.addChapterSeen(season, chapter); 
            serie.setCurrentSeason(season); 
            int num_chapters = serie.getSerie().getSeason(season).getChapters().size(); 
            serie.setNextSeason(c, num_chapters);
            this.addCharge(serie, season, chapter); 
            if(isLastChapter(serie, c)){ 
                System.out.println(" sono in list vado a ended"); 
                inlist.remove(serie); 
                ended.add(serie); 
                serie.setCurrentSeason(1); 
            } 
            else{ 
                System.out.println(" sono in list vado a started"); 
                inlist.remove(serie); 
                started.add(serie);     
            }   
        } 
        else{ 
            if(isInListUser(s, started)){ 
                SerieUser serie = getSerieUser(started, s); 
                ChapterSeen c = serie.findChapter(serie.getUserChapters(), season, chapter); 
                serie.addChapterSeen(season, chapter); 
                int num_chapters = serie.getSerie().getSeason(season).getChapters().size(); 
                serie.setNextSeason(c, num_chapters);
                this.addCharge(serie, season, chapter); 
                if(isLastChapter(serie, c)){ 
                    //System.out.print(" sono in started vado a ended"); 
                    ended.add(serie); 
                    started.remove(serie); 
                    serie.setCurrentSeason(1); 
                } 
            } 
            else{ 
                SerieUser serie = getSerieUser(ended, s); 
                ChapterSeen c = serie.findChapter(serie.getUserChapters(), season, chapter);
                serie.addChapterSeen(season, chapter); 
                int num_chapters = serie.getSerie().getSeason(season).getChapters().size(); 
                serie.setNextSeason(c, num_chapters);
                this.addCharge(serie, season, chapter); 
                //System.out.print(" sono a ended"); 
            } 
        } 
    }



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
