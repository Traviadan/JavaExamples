package de.traviadan;

import java.nio.file.Paths;

public class Launcher {
	public static void main(String[] args) {
		Log log = new Log();
		log.lvl = Log.Level.Debug;
		log.out = Log.Out.File;
		log.start();
		log.msg("Open MainWindow");
		log.msg("Actual path: " + System.getProperty("user.dir"));
		log.msg("Paths.get: " + Paths.get( System.getProperty("user.dir") + "/Thread.log" ));
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
