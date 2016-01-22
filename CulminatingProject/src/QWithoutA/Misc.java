package QWithoutA;

import java.util.Timer;
import java.util.TimerTask;

public class Misc extends Thread {

	public Misc() {
		// TODO Auto-generated constructor stub
		for(int i = 300; i >= 0; i -= 1){
			if(i == 0) System.exit(0);
			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException e){}
			
		}
	}

	 public static void main(final String args[]) {
	       
	    }
	}