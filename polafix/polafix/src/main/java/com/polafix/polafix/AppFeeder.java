package com.polafix.polafix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.polafix.polafix.pojos.*;
import com.polafix.polafix.repositories.SerieRepository;
import com.polafix.polafix.repositories.UserRepository;

@Component
public class AppFeeder implements CommandLineRunner{

    @Autowired
	protected UserRepository ur;
    @Autowired
    protected SerieRepository sr;

    @Override
    public void run(String... args) throws Exception {

        feedUsers();
        feedSeries();
        
        testRepositoryUser();
        testRepositorySerie();
		
		System.out.println("Application feeded");
    }

    private void feedUsers() {

        SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy");
		Date birth1 = null;
        Date birth2 = null;
		try {
			birth1 = dateParser.parse("21-07-1999");
            birth2 = dateParser.parse("14-01-1999");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        //String email, Subscription type, String IBAN, String name, String surname, Date dateOfBirth
		User u1 = new User("paperino@polafix.es", Subscription.NOTSUBSCRIBED, "0000111122223333", "Paperino", "De Paperis", birth1);
        User u2 = new User("topolino@polafix.es", Subscription.SUBSCRIBED, "4444555566667777", "Topolino", "De Topolis", birth2);

		ur.save(u1);
		ur.save(u2);
	}

    private void setSerie(Serie lost, Season lost1, List<Chapter> chapters){
        for(Chapter c : chapters){
            lost1.addChapter(c);
        }
        lost.addSeason(lost1);
    }

    private void feedSeries(){

        //--------------------------------------1-LOST-----------------------------------------------------------
        Serie lost = new Serie("Lost", Type.STANDARD, "A plane crashes and some people survives.What will they do? What is hidden on the island?");
        
        Season lost1 = new Season("Lost1", 1);
        Chapter lost1_1 = new Chapter(1, "lost1_1", "lost11");
        Chapter lost1_2 = new Chapter(2, "lost1_2", "lost12");
        Chapter lost1_3 = new Chapter(3, "lost1_3", "lost13");
        List<Chapter> lostChapters1 = new ArrayList<Chapter>();
        lostChapters1.add(lost1_1);
        lostChapters1.add(lost1_2);
        lostChapters1.add(lost1_3);

        Season lost2 = new Season("Lost2", 2);
        Chapter lost2_1 = new Chapter(1, "lost1_1", "lost21");
        Chapter lost2_2 = new Chapter(2, "lost1_2", "lost22");
        Chapter lost2_3 = new Chapter(3, "lost1_3", "lost23");
        List<Chapter> lostChapters2 = new ArrayList<Chapter>();
        lostChapters2.add(lost2_1);
        lostChapters2.add(lost2_2);
        lostChapters2.add(lost2_3);

        setSerie(lost, lost1, lostChapters1);
        setSerie(lost, lost2, lostChapters2);

        sr.save(lost);

        //------------------------------------2-ROTTEN--------------------------------------------------------
        Serie rotten = new Serie("Rotten", Type.GOLD, "Rotten is an investigative documentary series, focusing on corruption in the global food supply chain.");
        
        Season rotten1 = new Season("Rotten1", 1);
        Chapter rotten1_1 = new Chapter(1, "Avvocati, pistole e miele", "rotten11");
        Chapter rotten1_2 = new Chapter(2, "Il problema delle arachidi", "rotten12");
        Chapter rotten1_3 = new Chapter(3, "Alito all'aglio", "rotten13");
        List<Chapter> rottenChapters1 = new ArrayList<Chapter>();
        rottenChapters1.add(rotten1_1);
        rottenChapters1.add(rotten1_2);
        rottenChapters1.add(rotten1_3);

        Season rotten2 = new Season("Rotten2", 2);
        Chapter rotten2_1 = new Chapter(1, "The avocado war", "rotten21");
        Chapter rotten2_2 = new Chapter(2, "Regno del terroir", "rotten22");
        Chapter rotten2_3 = new Chapter(3, "Troubled water", "rotten23");
        List<Chapter> rottenChapters2 = new ArrayList<Chapter>();
        rottenChapters2.add(rotten2_1);
        rottenChapters2.add(rotten2_2);
        rottenChapters2.add(rotten2_3);

        setSerie(rotten, rotten1, rottenChapters1);
        setSerie(rotten, rotten2, rottenChapters2);

        sr.save(rotten);
    }

    private void testRepositoryUser(){

        //-------------------USER REPOSITORY-----------------------------------------------------------------

        System.out.println("--------------------------TEST REPOSITORY USER-----------------------------------");
        User user = ur.findById("paperino@polafix.es").get();

        System.out.println(user.getName());
        System.out.println(user.getSurname());
        System.out.println(user.getType());
        //System.out.println("-------SERIE USER-----");
        //System.out.println(user.getInlist().size());
        //System.out.println(user.getEnded().size());
        //System.out.println(user.getStarted().size());
        //System.out.println("-------BALANCES USER-----");
        //System.out.println(user.getLastBalance().getAllCharges().size() + " = 1");
        //System.out.println(user.getLastBalance().getAllCharges().get(0));
    }

    private void testRepositorySerie(){
         //-------------------SEIRE REPOSITORY-----------------------------------------------------------------
        
         System.out.println("--------------------------TEST REPOSITORY SERIE-----------------------------------");
         List<Serie> rotten = sr.findByName("Rotten");
         System.out.println(rotten.get(0).getName());
         //Season s1 = sr.findByTitleAndNumber("Rotten1", 1);
         //System.out.println(s1.getTitle());
         //System.out.println(rotten.getChapter(s1, 3).getTitle());
    }
}
