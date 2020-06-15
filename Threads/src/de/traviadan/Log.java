package de.traviadan;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;

public class Log extends Thread {

	public enum Level {
		Debug, Info, Warning, Failure
	}
	public enum Out {
		System, File, Db
	}
	private String workingDir = System.getProperty("user.dir");
	private FileWriter fw;
	private BufferedWriter bw;
	private PrintWriter pw;

	private Queue<String> logs = new LinkedList<>();
	public Log.Level lvl = Log.Level.Failure;
	public Log.Out out = Log.Out.System;
	
	public Log() {
		
	}
	
	public void run() {
		do {
			synchronized (this.logs) {
				try {
					this.logs.wait();
				} catch (InterruptedException e) {
					interrupt();
				}
				this.printLog();
			}
		} while(!isInterrupted());
		this.msg("Log-Thread beendet");
		this.printLog();
		try {
			this.closeLogfile();
		} catch (IOException e) {
			System.out.println("I/O Exception beim schliessen des Logfiles: " + e.getMessage());
		}
	}
	
	private void printLog() {
		while(this.logs.peek() != null) {
			switch(this.out) {
			case File:
				try {
					this.getPrintWriter().println(this.logs.poll());
				} catch (IOException e) {
					this.out = Log.Out.System;
					this.msg("I/O Exception beim schreiben ins Logfile: " + e.getMessage(), Log.Level.Failure);
				}
				break;
			case Db:
				break;
			default:
				System.out.println(this.logs.poll());
			}
		}
	}

	private void closeLogfile() throws IOException {
		if(this.pw != null) {
			this.pw.close();
			this.bw.close();
			this.fw.close();
		}
	}
	private PrintWriter getPrintWriter() throws IOException {
		if(this.pw == null) {
			this.fw = new FileWriter(this.workingDir + "/Thread.log", true);
		    this.bw = new BufferedWriter(fw);
		    this.pw = new PrintWriter(bw);
		}
		return this.pw;
	}
	
	public void msg(String msg) {
		this.msg(msg, Log.Level.Debug);
	}
	public void msg(String msg, Log.Level lvl) {
		if(lvl.equals(this.lvl)) {
			logs.add(msg);
			synchronized (this.logs) {
				this.logs.notify();
			}
		}
	}
}
