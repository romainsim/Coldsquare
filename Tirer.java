import java.util.*;
import java.awt.*;


public class Tirer extends Thread {
    Personnage perso;
    MaFenetreJeu frame;
    
    public Tirer(Personnage perso, MaFenetreJeu frame){
	this.perso = perso;
	this.frame = frame;
	    
    }

    @Override
    public void run(){
	tirer();	
    }

    public synchronized void tirer(){
	int nbMun = this.perso.getArme().getMunition();
	if(nbMun>0){
	    this.perso.getArme().setMunition(nbMun-1);
	    Balle b = new Balle(perso.getCoordonneX(),perso.getCoordonneY(),new Point(perso.getRotationX(),perso.getRotationY()), frame);
            b.start();
	    
	    System.out.println("Munition : "+this.perso.getArme().getMunition());	
	    
	    try{
		sleep(this.perso.getArme().getCadence());
	    }catch(InterruptedException e){
	    }
	}
    }  
    
}