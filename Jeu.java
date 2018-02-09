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
    Info i;
    FondPanel fond;
    JBalle ba;
    Deplacement d;
    
    Personnage p;
    Niveau n;
    JLayeredPane jlp;
    private int nombreEnnemi=1;
    private int enCours=1;
    
    public Jeu(){
	this.n= new Niveau(this);
	this.frame = new MaFenetreJeu();

	jlp = new JLayeredPane();
	frame.setLayeredPane(jlp);
	
	jlp.setOpaque(true);
	this.fond=  new FondPanel();
	this.af = new AfficherPersonnage();
	this.ba = new JBalle(af, frame, this);
	this.d = new Deplacement(p);
	
	frame.setLayout(null);

	initFond();
	initSteve();
	for(int e=0;e<nombreEnnemi;e++) {
	    initEnnemi();
	}
	fond.setBounds(0,0,1920,1040);
	af.setBounds(0,0,1920,1040);
	ba.setBounds(0,0,1920,1040);
	d.setBounds(0,0,1920,1040);
	i.setBounds(0,500,250,450);
	n.setBounds(150, 150, 1600, 700);

	af.setOpaque(false);
	ba.setOpaque(false);
	d.setOpaque(false);
	
	jlp.add(fond, new Integer(0));
	jlp.add(af,  new Integer(1));
	jlp.add(i,  new Integer(2));
	jlp.add(ba,  new Integer(3));
	jlp.add(d, new Integer(4));
	
	frame.revalidate();
    }
    
    void jouer() {
	for(int i=0;i<af.personnageVisible.size();i++) {
	    af.personnageVisible.get(i).setVie(af.personnageVisible.get(i).getVie()+1);
	    af.personnageVisible.get(i).setHitbox(new Hitbox(af.personnageVisible.get(i)));
	}
    	jlp.repaint();
    	af.boiteMunition.clear();
	Personnage Steve = af.personnageVisible.get(0);
    	Steve.setVie(n.getVie());
    	Steve.getArme().setCadence(n.getCadence());
    	Steve.getArme().setDispersion(n.getDispersion());
    	Steve.getArme().setMunition(n.getBalle());
	
    	int nbEnnemi=n.getEnnemis();
    	for(int e=0;e<nbEnnemi-nombreEnnemi;e++) {
	    initEnnemi();
	}
    }
    
    void initFond(){
	fond.repaint();
    }
    
    void initSteve(){
	
	Arme gunSteve = new Arme();
	Personnage Steve = new Personnage("Steve",5,"./assets/sprite.png",50,50,gunSteve);
	Steve.addListeDeSprite(new Sprite(Steve));
	Steve.addListeDeSprite(new Sprite("./assets/ennemi.png"));
	
	n.setPerso(Steve);
	af.addPersonnageVisible(Steve);
        Deplacement deplacement = new Deplacement(Steve);
        frame.addKeyListener(new DeplacementControler(deplacement,af,frame));
	//frame.addMouseListener(new ControlerSouris(Steve));
        this.i = new Info(Steve);
	i.repaint();
	af.repaint();
        frame.revalidate();
	frame.addMouseMotionListener(new ControlerSouris(Steve, af,frame));
	frame.addMouseListener(new ControlerClique(Steve, af, frame, ba));	   
	
    }
    
    void initEnnemi(){
	Random r = new Random();
	int nb = r.nextInt(1890) + 10 ; 
	int nb2 = r.nextInt(1000) + 10 ; 
	Personnage Ennemi = new Personnage("Ennemi"+i,1,"./assets/ennemi.png",nb,nb2);
	Ennemi.addListeDeSprite(new Sprite(Ennemi));
	Ennemi.addListeDeSprite(new Sprite("./assets/ennemi_mort1.png"));
	af.addPersonnageVisible(Ennemi);
	af.repaint();
	frame.revalidate();
    }
    
    boolean verifWin() {
        int nbMort=0;
        for(int i=1; i < af.personnageVisible.size() ; i++) {
	    if(af.personnageVisible.get(i).getVie() <= 0) {
       		nbMort++;
	    }
        }
        if(nbMort == af.personnageVisible.size()-1) {
	    return true;
        }
	return false;
    }
    
    void changerNiveau() {
	//jlp.remove(af);
	//jlp.remove(i);
	//jlp.repaint();
	jlp.add(n,  new Integer(4));	
	n.repaint();
	
    }
    
    void setNext() {
	this.enCours=1;
	jlp.remove(n);
    }
    
    public Niveau getNiveau() {
    	return this.n;
    }
}

class FondPanel extends JPanel{
    
    ImageIcon fond;
    public FondPanel(){
	fond = new ImageIcon(new ImageIcon("./assets/fond.png").getImage().getScaledInstance(1920,1040,Image.SCALE_DEFAULT));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
	drawFond(g);
        Toolkit.getDefaultToolkit().sync();
    }
    
    public void drawFond(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
	Image imagefond= fond.getImage();
	g2d.drawImage(imagefond,0,0,this);      
    }
}
