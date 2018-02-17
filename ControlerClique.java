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
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ControlerClique implements MouseListener {
    MaFenetreJeu frame;
    Personnage perso;
    AfficherPersonnage af;
    JBalle ba;
    MenuPause mp;
    Jeu j;
    Terrain t;
    
    ControlerClique(Personnage perso, AfficherPersonnage af, MaFenetreJeu frame, JBalle ba, MenuPause mp, Jeu j, Terrain t){
	this.perso = perso;
	this.af = af;
        this.frame=frame;
	this.ba = ba;
	this.mp = mp;
	this.j=j;
	this.t = t;
    }
    
    public void mouseClicked(MouseEvent e){
	if(e.getButton() == MouseEvent.BUTTON1){
	if(!(mp.getEnPause()))
	    (new Tirer(perso, frame, af, ba, mp, t)).start();
	}else if(e.getButton() == MouseEvent.BUTTON3 && j.getNiveau().getCac()==true){
	    System.out.println("DROIT");
	    Personnage Steve = af.personnageVisible.get(0);
	     for(int i = 1 ; i!=af.personnageVisible.size();i++){
		 if(af.personnageVisible.get(i).getVie()>0){
		     if(Hitbox.collision(Steve.getHitboxCC()/*Forcement Steve*/,af.personnageVisible.get(i).getHitbox())){
			 System.out.println("DANS LA COLLISION");
			 af.personnageVisible.get(i).setVie(af.personnageVisible.get(i).getVie()-1);
			 af.repaint();
			 if(this.j.verifWin())
			     this.j.levelComplete();
			 
		     }
		 }
	     }
	     
	}
	//af.repaint();
	//frame.revalidate();
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
