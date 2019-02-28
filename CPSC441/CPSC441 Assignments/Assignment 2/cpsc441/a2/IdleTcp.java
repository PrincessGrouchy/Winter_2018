package cpsc441.a2;


/**
 * IdleTcp class
 * CPSC 441
 *
 * to open multiple TCP connections
 * to a given server at a given port
 * can be used to check server's multi-threading functionality
 *
 *
 * @author: Majid Ghaderi
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class IdleTcp {
	
	private static int		serverPort;
	private static String	serverName;
	private static int		numConns;
	
	public static void main(String[] args) {
		// parse command line args
		parseCommandLine(args);
		
		try {
			Socket[] conns = new Socket[numConns];
			
			for (int i = 0; i < numConns; i++)
				conns[i] = new Socket(serverName, serverPort);
			
			System.out.printf("%d connections to %s:%d\n", numConns, serverName, serverPort);
			System.out.println("type \"quit\" to stop");
			System.out.println("......................");
			
			readQuit();
			
			for (int i = 0; i < numConns; i++)
				conns[i].close();
			
			System.out.println("connections terminated");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void parseCommandLine(String[] args) {
		if (args.length != 3)
			error("usage: IdleTcp <server name> <server port> <num conns>");
		
		serverName = args[0];
		serverPort = Integer.parseInt(args[1]);
		numConns = Integer.parseInt(args[2]);
	}
	
	private static void readQuit() {
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			String line = keyboard.readLine();
			
			while (!line.equals("quit"))
				line = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void error(String msg) {
		System.out.println(msg);
		System.exit(0);
	}
}
