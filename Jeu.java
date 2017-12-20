import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.Event;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.EventQueue;

class Jeu{

    AfficherPersonnage af;
    MaFenetreJeu frame;
    private int nombreEnnemi=2;

    Jeu(){
        this.frame = new MaFenetreJeu();
        this.af = new AfficherPersonnage();
        frame.add(af);
	initFond();
        initSteve();
	initEnnemi();
    }

    void initFond(){
	
	af.repaint();
	frame.revalidate();
    }

    void initSteve(){
	Arme gunSteve = new Arme();
        Personnage Steve = new Personnage("Steve",5,"./assets/sprite.png",50,50,gunSteve);
        Steve.addListeDeSprite(new Sprite(0,0,50,100,Steve));
        af.addPersonnageVisible(Steve);
        Deplacement deplacement = new Deplacement(Steve);
        frame.addKeyListener(new DeplacementControler(deplacement,af,frame));
	//frame.addMouseListener(new ControlerSouris(Steve));
	af.repaint();
        frame.revalidate();
	frame.addMouseMotionListener(new ControlerSouris(Steve, af,frame));
	frame.addMouseListener(new ControlerClique(Steve));
    }

     void initEnnemi(){
	for(int i=0;i<nombreEnnemi;i++){
	    Personnage Ennemi = new Personnage("Ennemi"+i,5,"./assets/ennemi.png",40*i,40*i);
	    Ennemi.addListeDeSprite(new Sprite(0,0,50,100,Ennemi));
	    af.addPersonnageVisible(Ennemi);
	    af.repaint();
	    frame.revalidate();
	   
	}
    }
}
