import java.io.*;
import java.util.ArrayList;

public class leveloader {
	
	boolean lvlv[][] = new boolean[25][15];
	boolean lvlh[][] = new boolean[26][14];
	ArrayList<int[]> nub = new ArrayList<int[]>();
	
	public ArrayList<int[]> loadA(String lvl){
		try {
			BufferedReader in = new BufferedReader(new FileReader(lvl));
		    String str;
		    int i = 0;
		    while ((str = in.readLine()) != null) {
		    	if(i<=14){
			    	for (int j=0; j <= 24; j++){
		    			if (str.charAt(j)=='t')
		    				lvlv[j][i]=true;
		    			else
		    				lvlv[j][i]=false;
			    	}
		    	}
		    	else if(i<=28){
			    	for (int j=0; j <= 25; j++){
		    			if (str.charAt(j)=='t')
		    				lvlh[j][i-15]=true;
		    			else
		    				lvlh[j][i-15]=false;
			    	}
		    	}
		    	else{
		    		int k=0;
		    		int l=0;
		    		int c[] = new int[12];
		    		for(int j=0;j<12;j++){
		    			k = str.indexOf(',', l);
		    			l = str.indexOf(',', k+1);
		    			c[j] = Integer.parseInt(str.substring(k+1, l));
		    		}
		    		nub.add(c);
		    	}
		    	i++;
		    }
		    in.close();
        }catch (Exception e) {}
		return nub;
	}
	public boolean[][] loadV(){
		return lvlv;
	}
	public boolean[][] loadH(){
		return lvlh;
	}
}
