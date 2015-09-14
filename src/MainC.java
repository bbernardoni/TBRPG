import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.lang.String;
import java.io.*;

public class MainC extends Core implements KeyListener {
	public static void main(String[] args){
		new MainC().run();
	}
	
	int screen = 1;
	boolean vl[][] = new boolean[25][15];
	boolean hl[][] = new boolean[26][14];
	int lsel[] = new int[3];
	int f = 0;		int h = 0;
	int sel = 0;
	int turns = 0;
	int chlvl = 0;
	int xp = 0;
	String name = "";
	boolean caps = false;
	String falsen = "";
	ArrayList<Integer> epx = new ArrayList<Integer>();	ArrayList<Integer> epy = new ArrayList<Integer>();//enmy previous
	ArrayList<Integer> cx = new ArrayList<Integer>();	ArrayList<Integer> cy = new ArrayList<Integer>(); //char coord
	ArrayList<Integer> ex = new ArrayList<Integer>();	ArrayList<Integer> ey = new ArrayList<Integer>(); //enmy coord
	ArrayList<Integer> ca = new ArrayList<Integer>();	ArrayList<Integer> ea = new ArrayList<Integer>(); //attack
	ArrayList<Integer> cd = new ArrayList<Integer>();	ArrayList<Integer> ed = new ArrayList<Integer>(); //defense
	ArrayList<Integer> cs = new ArrayList<Integer>();	ArrayList<Integer> es = new ArrayList<Integer>(); //skill
	ArrayList<Integer> ce = new ArrayList<Integer>();	ArrayList<Integer> ee = new ArrayList<Integer>(); //experience
	ArrayList<Integer> cp = new ArrayList<Integer>();	ArrayList<Integer> ep = new ArrayList<Integer>(); //predictability
	ArrayList<Integer> cl = new ArrayList<Integer>();	ArrayList<Integer> el = new ArrayList<Integer>(); //luck
	ArrayList<Integer> ch = new ArrayList<Integer>();	ArrayList<Integer> eh = new ArrayList<Integer>(); //health
	ArrayList<Integer> cm = new ArrayList<Integer>();	ArrayList<Integer> em = new ArrayList<Integer>(); //max health
	ArrayList<Integer> csp = new ArrayList<Integer>();	ArrayList<Integer> esp = new ArrayList<Integer>();//special points
	ArrayList<Integer> cms = new ArrayList<Integer>();	ArrayList<Integer> ems = new ArrayList<Integer>();//max special points
	ArrayList<int[]> nub = new ArrayList<int[]>();
	File[] files;
	
	public void init(){
		super.init();
		Window w = s.getFullScreenWindow();
		w.setFocusTraversalKeysEnabled(false);
		w.addKeyListener(this);
	}
	
	public void charLoad(int a,int d,int s,int e,int p,int l,int h,int sp){
		cx.add(0);	cy.add(0);
		ca.add(a);	cd.add(d);
		cs.add(s);	ce.add(e);
		cp.add(p);	cl.add(l);
		ch.add(h);	csp.add(sp);
		cm.add(h);	cms.add(sp);
	}
	public void enmyLoad(int[] x){
		ex.add(x[0]);	ey.add(x[1]);
		ea.add(x[2]);	ed.add(x[3]);
		es.add(x[4]);	ee.add(x[5]);
		ep.add(x[6]);	el.add(x[7]);
		eh.add(x[8]);	esp.add(x[9]);
		epx.add(x[10]);	epy.add(x[11]);
		em.add(x[8]);	ems.add(x[9]);
	}
	
    public synchronized void draw(Graphics2D g) {
        Window w = s.getFullScreenWindow();
        g.clearRect(0,0,s.getWidth(),s.getHeight());
        g.setColor(w.getBackground());
        g.fillRect(0,0,s.getWidth(),s.getHeight());
        g.setColor(w.getForeground());
        switch(screen){
        case 0:
		    g.drawString("Esc - Quit", 10, 45);
		    g.drawString("Home - Home", 710, 45);
		    g.drawString("P - Pause", 1050, 45);
		    g.setFont(new Font("Arial", Font.PLAIN, 20));
		    g.drawString("Health: " + ch.get(0) + "/" + cm.get(0), 239, 23);
		    g.drawString("Special Points: " + csp.get(0) + "/" + cms.get(0), 448, 23);
		    g.drawString("Turns: " + turns, 245, 47);
		    g.drawString("Experience Points: " + xp + "/" + (chlvl+1)*(chlvl+1)*100, 415, 47);
	        g.fillRect(0, 796, 1280, 4);
	        g.fillRect(0, 59, 1280, 4);
	        g.fillRect(0, 59, 4, 739);
	        g.fillRect(1276, 59, 4, 739);
	        for(int i=1; i<=25; i++){
	        	for(int j=0; j<=14; j++){
	        		if(vl[i-1][j] == true)
	        			g.fillRect(i*49+2,j*49+61,2,51);
	        	}
	        }
	        for(int i=1; i<=14; i++){
	        	for(int j=0; j<=25; j++){
	        		if(hl[j][i-1] == true)
	        			g.fillRect(j*49+2,i*49+61,51,2);
	        	}
	        }
	        for(int i=0; cx.size()>i; i++){
	        	g.drawOval(cx.get(i)*49+21, cy.get(i)*49+65, 12, 12);
		        g.drawLine(cx.get(i)*49+27, cy.get(i)*49+78, cx.get(i)*49+27, cy.get(i)*49+92);
		        g.drawLine(cx.get(i)*49+14, cy.get(i)*49+105, cx.get(i)*49+27, cy.get(i)*49+92);
		        g.drawLine(cx.get(i)*49+40, cy.get(i)*49+105, cx.get(i)*49+27, cy.get(i)*49+92);
		        g.drawLine(cx.get(i)*49+15, cy.get(i)*49+88, cx.get(i)*49+27, cy.get(i)*49+83);
		        g.drawLine(cx.get(i)*49+38, cy.get(i)*49+88, cx.get(i)*49+27, cy.get(i)*49+83);
	        }
	        g.setColor(Color.RED);
		    for(int i=0; ex.size()>i; i++){
	        	g.drawOval(ex.get(i)*49+21, ey.get(i)*49+65, 12, 12);
		        g.drawLine(ex.get(i)*49+27, ey.get(i)*49+78, ex.get(i)*49+27, ey.get(i)*49+92);
		        g.drawLine(ex.get(i)*49+14, ey.get(i)*49+105, ex.get(i)*49+27, ey.get(i)*49+92);
		        g.drawLine(ex.get(i)*49+40, ey.get(i)*49+105, ex.get(i)*49+27, ey.get(i)*49+92);
		        g.drawLine(ex.get(i)*49+15, ey.get(i)*49+88, ex.get(i)*49+27, ey.get(i)*49+83);
		        g.drawLine(ex.get(i)*49+38, ey.get(i)*49+88, ex.get(i)*49+27, ey.get(i)*49+83);
		    }
		    if(14==cy.get(0) && 25==cx.get(0) && !ex.isEmpty()){
			    g.setFont(new Font("Arial", Font.PLAIN, 300));
	    		g.drawString("Kill All", 215, 400);
	    		g.drawString("Enemies", 45, 650);
		    }
		    break;
        case 1:
	        g.drawString("Esc - Quit", 10, 50);
	        g.drawString("L - Level Editor", 470, 50);
	        g.drawString("S - Start", 1070, 50);
    		g.drawString("BY: Bennett Bernardoni", 375, 780);
    		g.setFont(new Font("Arial", Font.BOLD, 300));
        	g.drawString("Pillow", 190, 450);
        	break;
        case 2:
        	g.drawString("Esc - Quit", 10, 50);
        	g.drawString("A - Attack  D - Defend  S - Special Attack (25 points)", 50, 780);
        	g.drawString("Character's Health: " + ch.get(f), 50, 150);
        	g.drawString("Character's SP: " + csp.get(f), 50, 200);
        	g.setColor(Color.RED);
        	try{
        		g.drawString("Enemy's Health: " + eh.get(h), 700, 150);
	        	g.drawString("Enemy's SP: " + esp.get(h), 700, 200);
        	}catch(Exception ex){
        		g.drawString("Enemy's Health: 0", 700, 150);
	        	g.drawString("Enemy's SP: 0", 700, 200);
        	}
        	break;
        case 3:
        	g.drawString("Esc - Quit", 210, 250);
        	g.drawString("Home - Home", 800, 250);
        	g.drawString("Backspace - Back", 110, 300);
        	g.drawString("N - New Game", 795, 300);
        	g.drawString("Health: " + ch.get(0) + "/" + cm.get(0), 460, 400);
        	g.drawString("Special Points: " + csp.get(0) + "/" + cms.get(0), 360, 450);
        	g.drawString("Turns: " + turns, 535, 500);
        	g.drawString("Experience Points: " + xp + "/" + (chlvl+1)*(chlvl+1)*100, 350, 550);
        	g.drawString("Attack: " + ca.get(0) + "    Defense: " + cd.get(0), 340, 650);
        	g.drawString("Skill: " + cs.get(0) + "    Experience: " + ce.get(0), 330, 700);
        	g.drawString("Predictability: " + cp.get(0) + "    Luck: " + cl.get(0), 305, 750);
        	g.setFont(new Font("Arial", Font.BOLD, 150));
        	g.drawString("Pause", 425, 150);
        	break;
        case 4:
        	g.drawString("Esc - Quit", 10, 50);
        	g.drawString("Home - Home", 960, 50);
        	g.drawString("Select a Level", 480, 50);
        	File dir = new File(System.getProperty("user.dir"));
        	files = dir.listFiles(new FilenameFilter() {
        		public boolean accept(File dir, String filename)
        		{ return filename.endsWith(".lvl"); }
        	});
        	for (int i=0; i<files.length; i++){
        		if(i==sel)
        			g.setFont(new Font("Arial", Font.BOLD, 50));
        		else
        			g.setFont(new Font("Arial", Font.PLAIN, 50));
    	    	g.drawString(files[i].getName(), 20, (i+2)*60);
    	    }
        	break;
        case 5:
		    g.drawString("Esc - Quit", 10, 45);
		    g.drawString("Home - Home", 950, 45);
        	g.drawString("I - Instructions", 450, 45);
	        for(int i=1; i<=25; i++){
	        	for(int j=0; j<=14; j++){
	        		if(vl[i-1][j] == true)
	        			g.fillRect(i*49+2,j*49+61,2,51);
	        	}
	        }
	        for(int i=1; i<=14; i++){
	        	for(int j=0; j<=25; j++){
	        		if(hl[j][i-1] == true)
	        			g.fillRect(j*49+2,i*49+61,51,2);
	        	}
	        }
	        g.setColor(Color.GREEN);
	        if(lsel[0]==0)
	        	g.fillRect(lsel[1]*49+2,(lsel[2]+1)*49+61,51,2);
	        else
	        	g.fillRect((lsel[1]+1)*49+2,lsel[2]*49+61,2,51);

	        g.setColor(Color.RED);
		    for(int i=0; ex.size()>i; i++){
	        	g.drawOval(ex.get(i)*49+21, ey.get(i)*49+65, 12, 12);
		        g.drawLine(ex.get(i)*49+27, ey.get(i)*49+78, ex.get(i)*49+27, ey.get(i)*49+92);
		        g.drawLine(ex.get(i)*49+14, ey.get(i)*49+105, ex.get(i)*49+27, ey.get(i)*49+92);
		        g.drawLine(ex.get(i)*49+40, ey.get(i)*49+105, ex.get(i)*49+27, ey.get(i)*49+92);
		        g.drawLine(ex.get(i)*49+15, ey.get(i)*49+88, ex.get(i)*49+27, ey.get(i)*49+83);
		        g.drawLine(ex.get(i)*49+38, ey.get(i)*49+88, ex.get(i)*49+27, ey.get(i)*49+83);
		    }
	        g.setColor(Color.WHITE);
        	g.fillRect(0, 796, 1280, 4);
	        g.fillRect(0, 59, 1280, 4);
	        g.fillRect(0, 59, 4, 739);
	        g.fillRect(1276, 59, 4, 739);
        	break;
        case 6:
        	g.drawString("Esc - Quit", 10, 50);
		    g.drawString("Home - Home", 950, 50);
        	g.drawString("Backspace - Back", 420, 50);
        	g.drawString("Arrow Keys - Move Cursor", 350, 200);
        	g.drawString("S - Switch Between Vertical and Horizontal Rows", 100, 250);
        	g.drawString("Space - Toggle Wall", 410, 300);
        	g.drawString("A/D - Add Enemy Left/Right", 330, 350);
        	g.drawString("Enter - Save Level", 430, 400);
        	break;
        case 7:
        	g.drawString("Esc - Quit", 10, 50);
		    g.drawString("Home - Home", 950, 50);
        	g.drawString("End - Back", 470, 50);
        	g.drawString("Name: " + name, 10, 200);
        	g.drawString(falsen, 10, 250);
        	break;
        }
    }
    
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(screen){
        case 0:
	        switch(keyCode){
	        case KeyEvent.VK_ESCAPE:
	        	stop();
	        	break;
		    case KeyEvent.VK_UP:
		    	if(cy.get(0)>0 && hl[cx.get(0)][cy.get(0)-1]==false){
		    		cy.set(0, cy.get(0)-1);
		    		fight(1);
		    		moveE();
		    	}
		    	break;
		    case KeyEvent.VK_DOWN:
		    	if(cy.get(0)<14 && hl[cx.get(0)][cy.get(0)]==false){
		    		cy.set(0, cy.get(0)+1);
		    		fight(1);
		    		moveE();
		    	}
		    	break;
		    case KeyEvent.VK_LEFT:
		    	if(cx.get(0)>0 && vl[cx.get(0)-1][cy.get(0)]==false){
		    		cx.set(0, cx.get(0)-1);
		    		fight(1);
		    		moveE();
		    	}
		    	break;
		    case KeyEvent.VK_RIGHT:
		    	if(cx.get(0)<25 && vl[cx.get(0)][cy.get(0)]==false){
		    		cx.set(0, cx.get(0)+1);
		    		fight(1);
		    		moveE();
		    	}
		    	break;
		    case KeyEvent.VK_HOME:
		    	screen = 1;
		    	break;
		    case KeyEvent.VK_P:
		    	screen = 3;
		    	break;
		    }
	        fight(0);
	        break;
	    case 1:
		    switch(keyCode){
		    case KeyEvent.VK_ESCAPE:
		    	stop();
		    	break;
		    case KeyEvent.VK_L:
		    	lsel[0]=0;
		    	lsel[1]=0;
		    	lsel[2]=0;
		    	dieC();
		    	for(int i=0; i<25; i++){
		    		Arrays.fill(vl[i], false);
		    		Arrays.fill(hl[i], false);
		    	}
		    	Arrays.fill(hl[25], false);
		    	screen = 5;
		    	break;
		    case KeyEvent.VK_S:
		    	if(cx.isEmpty()){
		    		screen = 4;
		    	}else{
		    		screen = 0;
		    	}
		    	break;
		    case KeyEvent.VK_SPACE:
		    	if(cx.isEmpty()){
			    	screen = 4;
		    	}else{
		    		screen = 0;
		    	}
		    	break;
		    }
		    break;
	    case 2:
	    	Random ran = new Random();
	    	switch(keyCode){
		    case KeyEvent.VK_ESCAPE:
		    	stop();
		    	break;
		    case KeyEvent.VK_A:
		    	eh.set(h, eh.get(h) - (ca.get(f)/ed.get(h))*(cs.get(f)/es.get(h))*(cp.get(f)/ee.get(h))*(ca.get(f)/5) + ran.nextInt(cl.get(f)/20));
		    	if (eh.get(h)<=0){dieE();}
		    	else{attackE(cd.get(f));}
		    	break;
		    case KeyEvent.VK_D:
		    	if (eh.get(h)<=0){dieE();}
		    	else{attackE(cd.get(f)*2);}
		    	break;
		    case KeyEvent.VK_S:
		    	if (csp.get(f)>=25){
			    	eh.set(h, eh.get(h) - (ca.get(f)/ed.get(h))*(cs.get(f)/es.get(h))*(cp.get(f)/ee.get(h))*(ca.get(f)/3) + ran.nextInt(cl.get(f)/15));
			    	csp.set(f, csp.get(f) - 25);
			    	if (eh.get(h)<=0){dieE();}
			    	else{attackE(cd.get(f));}
		    	}
		    	break;
	    	}
	    	break;
	    case 3:
	    	switch(keyCode){
		    case KeyEvent.VK_ESCAPE:
		    	stop();
		    	break;
		    case KeyEvent.VK_HOME:
		    	screen = 1;
		    	break;
		    case KeyEvent.VK_BACK_SPACE:
		    	screen = 0;
		    	break;
		    case KeyEvent.VK_P:
		    	screen = 0;
		    	break;
		    case KeyEvent.VK_N:
		    	dieC();
		    	break;
	    	}
	    	break;
	    case 4:
	    	switch(keyCode){
	    	case KeyEvent.VK_ESCAPE:
		    	stop();
		    	break;
	    	case KeyEvent.VK_HOME:
		    	screen = 1;
		    	break;
		    case KeyEvent.VK_BACK_SPACE:
		    	screen = 1;
		    	break;
		    case KeyEvent.VK_UP:
		    	if(sel>0)
		    		sel--;
		    	else
		    		sel=files.length-1;
		    	break;
		    case KeyEvent.VK_DOWN:
		    	if(sel<files.length-1)
		    		sel++;
		    	else
		    		sel=0;
		    	break;
		    case KeyEvent.VK_ENTER:
		    	leveloader l = new leveloader();
				nub = l.loadA(files[sel].getName());
				vl = l.loadV();
				hl = l.loadH();
				if(cx.isEmpty())
					charLoad(100,100,100,100,100,100,100,100);
				for(int i=0;i<nub.size();i++){
					enmyLoad(nub.get(i));
				}
				screen = 0;
		    	break;
	    	}
	    	break;
	    case 5:
		    switch(keyCode){
		    case KeyEvent.VK_ESCAPE:
		    	stop();
		    	break;
		    case KeyEvent.VK_HOME:
		    	screen = 1;
		    	break;
		    case KeyEvent.VK_I:
		    	screen = 6;
		    	break;
		    case KeyEvent.VK_SPACE:
		    	if(lsel[0]==0)
		    		hl[lsel[1]][lsel[2]] = !hl[lsel[1]][lsel[2]];
		    	else
		    		vl[lsel[1]][lsel[2]] = !vl[lsel[1]][lsel[2]];
		    	break;
		    case KeyEvent.VK_S:
		    	lsel[0]=(lsel[0]+1)%2;
		    	if(lsel[0]==0 && lsel[2]==14)
		    		lsel[2]=13;
		    	if(lsel[0]==1 && lsel[1]==25)
		    		lsel[1]=24;
		    	break;
		    case KeyEvent.VK_A:
		    	int j=0;
		    	for(int i=0; ex.size()>i; i++){
		    		if(ex.get(i)==lsel[1] && ey.get(i)==lsel[2]){
		    			ex.remove(i);
		    			ey.remove(i);
		    			j=1;
		    		}
			    }
		    	if(j==0){
			    	ex.add(lsel[1]);
			    	ey.add(lsel[2]);
		    	}
		    	break;
		    case KeyEvent.VK_D:
		    	if(lsel[0]==0){
		    		int k=0;
			    	for(int i=0; ex.size()>i; i++){
			    		if(ex.get(i)==lsel[1] && ey.get(i)==lsel[2]+1){
			    			ex.remove(i);
			    			ey.remove(i);
			    			k=1;
			    		}
				    }
			    	if(k==0){
				    	ex.add(lsel[1]);
				    	ey.add(lsel[2]+1);
			    	}
		    	}
		    	else{
		    		int l=0;
			    	for(int i=0; ex.size()>i; i++){
			    		if(ex.get(i)==lsel[1]+1 && ey.get(i)==lsel[2]){
			    			ex.remove(i);
			    			ey.remove(i);
			    			l=1;
			    		}
				    }
			    	if(l==0){
				    	ex.add(lsel[1]+1);
				    	ey.add(lsel[2]);
			    	}
		    	}
		    	break;
		    case KeyEvent.VK_LEFT:
		    	if (lsel[1]>0)
		    		lsel[1]--;
		    	break;
		    case KeyEvent.VK_RIGHT:
		    	if (lsel[1]<25-lsel[0])
		    		lsel[1]++;
		    	break;
		    case KeyEvent.VK_UP:
		    	if (lsel[2]>0)
		    		lsel[2]--;
		    	break;
		    case KeyEvent.VK_DOWN:
		    	if (lsel[2]<13+lsel[0])
		    		lsel[2]++;
		    	break;
		    case KeyEvent.VK_ENTER:
		    	falsen = "";
		    	screen = 7;
		    	break;
		    }
		    break;
	    case 6:
		    switch(keyCode){
		    case KeyEvent.VK_ESCAPE:
		    	stop();
		    	break;
		    case KeyEvent.VK_HOME:
		    	screen = 1;
		    	break;
		    case KeyEvent.VK_BACK_SPACE:
		    	screen = 5;
		    	break;
		    }
		    break;
	    case 7:
	    	falsen = "";
		    switch(keyCode){
		    case KeyEvent.VK_ESCAPE:
		    	stop();
		    	break;
		    case KeyEvent.VK_HOME:
		    	screen = 1;
		    	break;
		    case KeyEvent.VK_END:
		    	screen = 5;
		    	break;
		    case KeyEvent.VK_ENTER:
		    	if(name!="" && (new File(name+".lvl")).exists()==false){
		    		try {
		        	    BufferedWriter out = new BufferedWriter(new FileWriter(name + ".lvl"));
		        	    for(int i=0; i<15; i++){
		        	    	for(int j=0; j<25; j++){
			        	    	if(vl[j][i]==true)
		        	    			out.write("t");
			        	    	else
			        	    		out.write("f");
			        	    }
		        	    	out.write("\n");
		        	    }
		        	    for(int i=0; i<14; i++){
		        	    	for(int j=0; j<26; j++){
			        	    	if(hl[j][i]==true)
		        	    			out.write("t");
			        	    	else
			        	    		out.write("f");
			        	    }
		        	    	out.write("\n");
		        	    }
		        	    while(!ex.isEmpty()){
		        	    	out.write(","+ex.remove(0)+","+ey.remove(0)+",60,60,60,60,60,60,100,100,25,15,\n");
		        	    }
		        	    out.close();
		        	} catch (IOException ex) {}
		        	screen = 1;
		        	falsen = "";
		    	}else{
		    		falsen = "Name is either empty or already being used";
		    	}
		    	break;
		    case KeyEvent.VK_BACK_SPACE:
		    	if(name.length()>0)
		    		name=name.substring(0, name.length()-1);
		    	break;
		    case KeyEvent.VK_SHIFT:
		    	caps = !caps;
		    	break;
		    case KeyEvent.VK_CAPS_LOCK:
		    	caps = !caps;
		    	break;
		    case KeyEvent.VK_MINUS:
		    	if(caps==false)
		    		name+="-";
		    	else
		    		name+="_";
		    	break;
		    case KeyEvent.VK_A:
		    	if(caps==false)
		    		name+="a";
		    	else
		    		name+="A";
		    	break;
		    case KeyEvent.VK_B:
		    	if(caps==false)
		    		name+="b";
		    	else
		    		name+="B";
		    	break;
		    case KeyEvent.VK_C:
		    	if(caps==false)
		    		name+="c";
		    	else
		    		name+="C";
		    	break;
		    case KeyEvent.VK_D:
		    	if(caps==false)
		    		name+="d";
		    	else
		    		name+="D";
		    	break;
		    case KeyEvent.VK_E:
		    	if(caps==false)
		    		name+="e";
		    	else
		    		name+="E";
		    	break;
		    case KeyEvent.VK_F:
		    	if(caps==false)
		    		name+="f";
		    	else
		    		name+="F";
		    	break;
		    case KeyEvent.VK_G:
		    	if(caps==false)
		    		name+="g";
		    	else
		    		name+="G";
		    	break;
		    case KeyEvent.VK_H:
		    	if(caps==false)
		    		name+="h";
		    	else
		    		name+="H";
		    	break;
		    case KeyEvent.VK_I:
		    	if(caps==false)
		    		name+="i";
		    	else
		    		name+="I";
		    	break;
		    case KeyEvent.VK_J:
		    	if(caps==false)
		    		name+="j";
		    	else
		    		name+="J";
		    	break;
		    case KeyEvent.VK_K:
		    	if(caps==false)
		    		name+="k";
		    	else
		    		name+="K";
		    	break;
		    case KeyEvent.VK_L:
		    	if(caps==false)
		    		name+="l";
		    	else
		    		name+="L";
		    	break;
		    case KeyEvent.VK_M:
		    	if(caps==false)
		    		name+="m";
		    	else
		    		name+="M";
		    	break;
		    case KeyEvent.VK_N:
		    	if(caps==false)
		    		name+="n";
		    	else
		    		name+="N";
		    	break;
		    case KeyEvent.VK_O:
		    	if(caps==false)
		    		name+="o";
		    	else
		    		name+="O";
		    	break;
		    case KeyEvent.VK_P:
		    	if(caps==false)
		    		name+="p";
		    	else
		    		name+="P";
		    	break;
		    case KeyEvent.VK_Q:
		    	if(caps==false)
		    		name+="q";
		    	else
		    		name+="Q";
		    	break;
		    case KeyEvent.VK_R:
		    	if(caps==false)
		    		name+="r";
		    	else
		    		name+="R";
		    	break;
		    case KeyEvent.VK_S:
		    	if(caps==false)
		    		name+="s";
		    	else
		    		name+="S";
		    	break;
		    case KeyEvent.VK_T:
		    	if(caps==false)
		    		name+="t";
		    	else
		    		name+="T";
		    	break;
		    case KeyEvent.VK_U:
		    	if(caps==false)
		    		name+="u";
		    	else
		    		name+="U";
		    	break;
		    case KeyEvent.VK_V:
		    	if(caps==false)
		    		name+="v";
		    	else
		    		name+="V";
		    	break;
		    case KeyEvent.VK_W:
		    	if(caps==false)
		    		name+="w";
		    	else
		    		name+="W";
		    	break;
		    case KeyEvent.VK_X:
		    	if(caps==false)
		    		name+="x";
		    	else
		    		name+="X";
		    	break;
		    case KeyEvent.VK_Y:
		    	if(caps==false)
		    		name+="y";
		    	else
		    		name+="Y";
		    	break;
		    case KeyEvent.VK_Z:
		    	if(caps==false)
		    		name+="z";
		    	else
		    		name+="Z";
		    	break;
		    case KeyEvent.VK_1:
		    	name+=1;
		    	break;
		    case KeyEvent.VK_2:
		    	name+=2;
		    	break;
		    case KeyEvent.VK_3:
		    	name+=3;
		    	break;
		    case KeyEvent.VK_4:
		    	name+=4;
		    	break;
		    case KeyEvent.VK_5:
		    	name+=5;
		    	break;
		    case KeyEvent.VK_6:
		    	name+=6;
		    	break;
		    case KeyEvent.VK_7:
		    	name+=7;
		    	break;
		    case KeyEvent.VK_8:
		    	name+=8;
		    	break;
		    case KeyEvent.VK_9:
		    	name+=9;
		    	break;
		    case KeyEvent.VK_0:
		    	name+=0;
		    	break;
		    }
		    break;
        }
        e.consume();
    }
    public void keyReleased(KeyEvent e) {e.consume();}
    public void keyTyped(KeyEvent e) {e.consume();}
    
    public void moveE(){
       	for(int i=0;i<ex.size();i++){
	    		 if(ex.get(i)>0 && vl[ex.get(i)-1][ey.get(i)]==false && ex.get(i)!=epx.get(i)+1){epx.set(i,ex.get(i));	epy.set(i,ey.get(i));	ex.set(i, ex.get(i)-1);}
	       	else if(ey.get(i)>0 && hl[ex.get(i)][ey.get(i)-1]==false && ey.get(i)!=epy.get(i)+1){epy.set(i,ey.get(i));	epx.set(i,ex.get(i));	ey.set(i, ey.get(i)-1);}
	       	else if(ey.get(i)<14 && hl[ex.get(i)][ey.get(i)]==false  && ey.get(i)!=epy.get(i)-1){epy.set(i,ey.get(i));	epx.set(i,ex.get(i));	ey.set(i, ey.get(i)+1);}
	       	else if(ex.get(i)<25 && vl[ex.get(i)][ey.get(i)]==false  && ex.get(i)!=epx.get(i)-1){epx.set(i,ex.get(i));	epy.set(i,ey.get(i));	ex.set(i, ex.get(i)+1);}
       	}
    }
    public void attackE(int cd){
    	Random ran = new Random();
  		if (esp.get(h)>=25 && ch.get(f)>0 && eh.get(h)>0){
  			int a = (ea.get(h)/cd)*(es.get(h)/cs.get(f))*(ep.get(h)/ce.get(f))*(ea.get(h)/3) + ran.nextInt(el.get(h)/15);
  			if (a>0){ch.set(f, ch.get(f) - a);}
  			esp.set(h, esp.get(h) - 25);
  		}else if (ch.get(f)>0 && eh.get(h)>0){
  			int a = (ea.get(h)/cd)*(es.get(h)/cs.get(f))*(ep.get(h)/ce.get(f))*(ea.get(h)/5) + ran.nextInt(el.get(h)/20);
  			if (a>0){ch.set(f, ch.get(f) - a);}
  		}
  		if (ch.get(f)<=0){dieC();}
    }
    
    public void fight(int x){
    	turns+=x;
    	if(turns%10==0){
    		ch.set(0, ch.get(0)+chlvl*x);
    		if(cm.get(0)<ch.get(0)){ch.set(0, cm.get(0));}
    	}
    	if(turns%3==0){
    		csp.set(0, csp.get(0)+chlvl*x);
    		if(csp.get(0)>cms.get(0)){csp.set(0, cms.get(0));}
    	}
    	for(int i=0;i<cx.size();i++){
    		for(int j=0;j<ex.size();j++){
		    	if(ey.get(j)==cy.get(i) && ex.get(j)==cx.get(i)){
		    		f = i;		h = j;
		    		screen = 2;
		    	}
    		}
    	}
    	if(14==cy.get(0) && 25==cx.get(0) && ex.isEmpty()){
    		screen = 4;
    		cx.set(0,0);	cy.set(0,0);
    		ey.clear();		ex.clear();
        	ea.clear();		ed.clear();
        	es.clear();		ee.clear();
        	ep.clear();		el.clear();
        	eh.clear();		esp.clear();
        	epx.clear();	epy.clear();
        	em.clear();		ems.clear();
    	}
    }
    public void dieC(){
    	screen = 1;
    	cy.clear();		cx.clear();		ey.clear();		ex.clear();
    	ca.clear();		ea.clear();		cd.clear();		ed.clear();
    	cs.clear();		es.clear();		ce.clear();		ee.clear();
    	cp.clear();		ep.clear();		cl.clear();		el.clear();
    	ch.clear();		eh.clear();		csp.clear();	esp.clear();
    	epx.clear();	epy.clear();	cm.clear();		em.clear();
    	cms.clear();	ems.clear();
    	xp=0;			turns=0;	chlvl = 0;
    }
    public void dieE(){
    	eh.remove(h);		esp.remove(h);
    	int txp=0;
    	screen = 0;
    	ey.remove(h);		ex.remove(h);
    	txp+=ea.remove(h);	txp+=ed.remove(h);
    	txp+=es.remove(h);	txp+=ee.remove(h);
    	txp+=ep.remove(h);	txp+=el.remove(h);
    	txp+=em.remove(h);	txp+=ems.remove(h);
    	epx.remove(h);		epy.remove(h);
    	xp+=txp/8;
    	if(xp >= (chlvl+1)*(chlvl+1)*100){
    		lvlup();
    	}
    }
    
    public void lvlup(){
    	Random ran = new Random();
    	ca.set (0,ca.get(0) + 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	cd.set (0,cd.get(0) + 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	cs.set (0,cs.get(0) + 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	ce.set (0,ce.get(0) + 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	cp.set (0,cp.get(0) + 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	cl.set (0,cl.get(0) + 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	ch.set (0,ch.get(0) + 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	csp.set(0,csp.get(0)+ 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	cm.set (0,cm.get(0) + 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	cms.set(0,cms.get(0)+ 2*chlvl+10 + ran.nextInt(cl.get(0)/20));
    	if(ch.get(0)>cm.get(0)){cm.set(0, ch.get(0));}
    	if(csp.get(0)>cms.get(0)){cms.set(0, csp.get(0));}
    	for(int i=0;i<ex.size();i++){
	    	ea.set (i,ea.get(i) + 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	ed.set (i,ed.get(i) + 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	es.set (i,es.get(i) + 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	ee.set (i,ee.get(i) + 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	ep.set (i,ep.get(i) + 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	el.set (i,el.get(i) + 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	eh.set (i,eh.get(i) + 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	esp.set(i,esp.get(i)+ 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	em.set (i,em.get(i) + 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	ems.set(i,ems.get(i)+ 2*chlvl+10 + ran.nextInt(el.get(i)/20));
	    	if(eh.get(i)>em.get(i)){em.set(i, eh.get(i));}
	    	if(esp.get(i)>ems.get(i)){ems.set(i, esp.get(i));}
    	}
    	chlvl++;
    }
}