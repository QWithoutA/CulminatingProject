package QWithoutA;


public class CountdownTimer
{
    static Thread thread = new Thread();
    public static void main(String args[]) throws InterruptedException {
    	for(int i = 60; i>0; i--){
    		thread.sleep(1000);
    		System.out.println(i);
    	}
    }
}
   
