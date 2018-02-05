import java.util.*;
import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;

public class Tirer extends Thread {
    Personnage perso;
    MaFenetreJeu frame;
    AfficherPersonnage af;
    Jeu j;
    public Tirer(Personnage perso, MaFenetreJeu frame, AfficherPersonnage af, Jeu j){
	this.perso = perso;
	this.frame = frame;
	this.af=af;
	this.j=j;
    }

    @Override
    public void run(){
	tirer();	
    }

    public synchronized void tirer(){
	int nbMun = this.perso.getArme().getMunition();
	if(nbMun>0){
	    this.perso.getArme().setMunition(nbMun-1);
	    Balle b = new Balle(perso, new Point(perso.getRotationX(),perso.getRotationY()), frame, af,j);
            b.start();
	    
	    //Son du tire
	    Son fusil = new Son("sounds/206.wav");
	    InputStream stream = new ByteArrayInputStream(fusil.getSamples());
	    fusil.play(stream);
	    
	    System.out.println("Munition : "+this.perso.getArme().getMunition());	
	    
	    try{
		sleep(this.perso.getArme().getCadence());
	    }catch(InterruptedException e){
		e.printStackTrace();
	    }
	}
    }  
    
}
