package QWithoutA;

import java.util.Timer;
import java.util.TimerTask;

public class Misc {

	public Misc() {
		// TODO Auto-generated constructor stub
		Timer t = new Timer();
		t.schedule(new TimerTask() {

		            @Override
		            public void run() {
		                System.out.println("Time's up!");

		            }
		        }, 400);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
