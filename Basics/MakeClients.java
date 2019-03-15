import java.util.Random;

public class MakeClients{

	public static void main(String[] args){
		long startTime = System.nanoTime();
		make1.start();
		make2.start();
		long endTime = System.nanoTime();
		System.out.println(endTime - startTime);
    }

    private static Thread make1 = new Thread() {
    	public void run(){
    		String commandE = "java BasicClient 4321 Easy";
			String commandM = "java BasicClient 4321 Medium";
			String commandH = "java BasicClient 4321 Hard";
    		Runtime rt = Runtime.getRuntime();
    		Random rand = new Random();
    		int diff;
    		long startTime = System.nanoTime();
	    	try {
	    		int count = 0;
	    		while(count < 50) {
	    			diff = rand.nextInt(3);
	    			if (diff == 0){
	    				Process pr = rt.exec(commandE);
	    			}
	    			if (diff == 1){
	    				Process pr = rt.exec(commandM);
	    			}
	    			if (diff == 2){
	    				Process pr = rt.exec(commandH);
	    			}
	    			count++;
	    		}
			} catch(Exception e){
			    e.printStackTrace();
			}
			long endTime = System.nanoTime();
			System.out.println(endTime - startTime);
    	}
    };

    private static Thread make2 = new Thread() {
    	public void run(){
    		String commandE = "java BasicClient 4321 Easy";
			String commandM = "java BasicClient 4321 Medium";
			String commandH = "java BasicClient 4321 Hard";
    		Runtime rt = Runtime.getRuntime();
    		Random rand = new Random();
    		int diff;
    		long startTime = System.nanoTime();
	    	try {
	    		int count = 0;
	    		while(count < 50) {
	    			diff = rand.nextInt(3);
	    			if (diff == 0){
	    				Process pr = rt.exec(commandE);
	    			}
	    			if (diff == 1){
	    				Process pr = rt.exec(commandM);
	    			}
	    			if (diff == 2){
	    				Process pr = rt.exec(commandH);
	    			}
	    			count++;
	    		}
			} catch(Exception e){
			    e.printStackTrace();
			}
			long endTime = System.nanoTime();
			System.out.println(endTime - startTime);
    	}
    };



}