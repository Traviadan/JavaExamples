package de.traviadan;

public class Launcher {
	public static void main(String[] args) {
		Log log = new Log();
		log.lvl = Log.Level.Debug;
		log.out = Log.Out.System;
		log.start();
		log.msg("Open MainWindow");
		log.msg("Actual path: " + System.getProperty("user.dir"));
		MainWindow win = new MainWindow("Threads", log);
		
		do {
			
		} while(win.isDisplayable());
		log.interrupt();
		try {
			log.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
