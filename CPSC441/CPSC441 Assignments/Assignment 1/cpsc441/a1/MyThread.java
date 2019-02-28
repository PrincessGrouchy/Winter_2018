/**
 * 
 */
package cpsc441.a1;

import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author arebe
 *
 */
public class MyThread implements Runnable {
	String myHost;
	int myPort;
	String myFilepath;
	int myConn;
	int myObjectLength;
	int i;

	public MyThread(String myHost, int myPort, String myFilepath, int myConn, int myObjectLength, int i) {
		this.myHost = myHost;
		this.myPort = myPort;
		this.myFilepath = myFilepath;
		this.myConn = myConn;
		this.myObjectLength = myObjectLength;
		this.i = i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		double length = Math.ceil((double) myObjectLength / myConn);
		// System.out.println(i+"= i myLengthis" + length+"myobjLen "+objectLength+"conn
		// "+ conn);
		int minRange = i * (int) length;
		int maxRange = minRange + (int) length;
		// dont ask me how this works
		// because I donnae know

		try {
			// yaya this is repeated code byt couldn't figure out how to
			// put into its own function so :P
			Socket socket = new Socket(myHost, myPort);
			// send the get request for only my bits
			String getReq = "GET " + myFilepath + " HTTP/1.1\r\n";
			String hostReq = "Host: " + myHost + "\r\n";
			String rangeReq = "Range: bytes=" + minRange + "-" + maxRange + "\r\n";
			String endReq = "\r\n";

			String sendingReq = getReq + hostReq + rangeReq + endReq;
			byte[] sendingReq_bytes = sendingReq.getBytes("US-ASCII");
			System.out.println(sendingReq);
			socket.getOutputStream().write(sendingReq_bytes);
			socket.getOutputStream().flush();

			// get filename?
			String[] filePathSplit = myFilepath.split("/");
			String fileName = filePathSplit[filePathSplit.length - 1];
			// String fileName = filePathSplit[filePathSplit.length - 1];
			if (myConn > 1) {
				fileName = i + "_" + filePathSplit[filePathSplit.length - 1];
			}
			File file = new File(fileName);
			// file.getParentFile().mkdirs();
			if (file.createNewFile()) {
				// System.out.println(fileName + " File Created");
			} else {
				// System.out.println(fileName + " already exists");
			}
			FileOutputStream outStream = new FileOutputStream(file);

			byte[] responseBytes = new byte[2048];
			int readOff = 0;
			String responseString = null;
			// read header
			while (true) {

				socket.getInputStream().read(responseBytes, readOff, 1);
				readOff++;
				responseString = new String(responseBytes, 0, readOff, "US-ASCII");
				if (responseString.contains("\r\n\r\n")) {
					break;
				}
			}

			int bytesRead = 0;
			int counter = 0;
			byte[] objectSegment = new byte[1024];
			// get the file now
			while (bytesRead != -1) {
				if (counter == myObjectLength) {
					break;
				}
				bytesRead = socket.getInputStream().read(objectSegment);
				try {
					outStream.write(objectSegment, 0, bytesRead);
				} catch (IndexOutOfBoundsException e) {
					// System.out.println("IndexOutOfBoundsExeption happened, and is being ignored
					// because code works");
					// e.printStackTrace();
				}
				counter = counter + bytesRead;
				outStream.flush();
			}

			outStream.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
