package cpsc441.a1;

import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The Class QuickUrl.
 * 
 * @author arebe
 * 
 *         CPSC 441 (Winter 2019) Lecture 1 Rebecca Reid
 * 
 *         This is the main method for downloading objects. The parameter url
 *         specified a properly formatted URL that specifies the object to be
 *         downloaded. As explained earlier, if the server supports Range
 *         requests, you should divide the specified object into conn parts,
 *         where all parts are download concurrently using conn threads. There
 *         is a set method in class ConcurrentHttp to set the value of field
 *         conn, which is defined as a private member variable.
 * 
 *         1. Send a HEAD request for url. Parse the response to retrieve:
 *         (a)Accept-Ranges (b) Content-Length
 * 
 *         2. If Range requests are not supported, then set conn = 1
 * 
 *         3. Start conn threads: (a) Each thread opens a TCP connection (b)
 *         Sends a Range request for one part of the object (c) Each part has
 *         Content-Length/conn bytes
 * 
 *         4. Wait for all threads to complete
 * 
 *         5. Merge all parts to rebuild the full object
 * 
 *         6. Clean up (e.g., close sockets, streams and remove temp files)
 */
public class QuickUrl extends ConcurrentHttp {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cpsc441.a1.ConcurrentHttp#getObject(java.lang.String)
	 */
	@Override
	public void getObject(String url) {
		String myHost, myFilepath;
		int myPortNum = 80;

		String noHttp = url.replace("http://", ""); // also need to throw out "http://"
		String[] splitUrl = noHttp.split("/", 2); // try to split url to get host and port.

		// now should have people.ucalgary.ca and /~mghaderi stuff.
		// Will now check for port number.
		// remaining url doesn't have http:, so shouldn't worry?
		// Source: Cyriac James.
		myFilepath = "/" + splitUrl[1];
		if (splitUrl[0].contains(":")) {
			String myHostPort[] = splitUrl[0].split(":", 2);
			myHost = myHostPort[0];
			myPortNum = Integer.parseInt(myHostPort[1]);
		} else { // no port number
			myHost = splitUrl[0];
		}

		try {
			SocketMaker(myHost, myPortNum, myFilepath);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void SocketMaker(String myHost, int myPort, String myFilepath) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		/*
		 * HEAD /~mghaderi/cpsc441/dots.txt HTTP/1.1 Host: people.ucalgary.ca
		 */
		// calling head to make sure... there's no content length stuff.
		Socket socket = new Socket(myHost, myPort);
		String headReq = "HEAD " + myFilepath + " HTTP/1.1\r\n";
		String hostReq = "Host: " + myHost + "\r\n";
		String endReq = "\r\n";
		String sendingReq = headReq + hostReq + endReq;
		byte[] sendingReq_bytes = sendingReq.getBytes("US-ASCII");
		System.out.println(sendingReq);
		socket.getOutputStream().write(sendingReq_bytes);
		socket.getOutputStream().flush();

		// now, put return header into a string
		// dunno why it's 2048 size... thank u totorial slides?
		byte[] response_bytes = new byte[2048];
		// int num_byte_read = 0;
		int readOffset = 0;
		String response_string = null;
		while (true) {
			socket.getInputStream().read(response_bytes, readOffset, 1);
			// reads 1 byte at a time
			readOffset++;
			response_string = new String(response_bytes, 0, readOffset, "US-ASCII");
			// i have no idea how this saves the whole header, but OK.
			// Source: syedahmedzaidi
			if (response_string.contains("\r\n\r\n")) {
				// System.out.println("start header");
				System.out.println(response_string);
				// System.out.println("end header");
				break;
			}
		}

		// get Content-Length
		String[] splitBeforeContentLength = response_string.split("Content-Length: ", 2);
		String[] splitAfterContentLength_EoL = splitBeforeContentLength[1].split("\r\n", 2);
		int objectLength = Integer.parseInt(splitAfterContentLength_EoL[0]);

		// (a)Accept-Ranges
		if (!response_string.contains("Accept-Ranges:") || response_string.contains("Accept-Ranges: none")) {
			// must not use range requests.
			this.setConn(1);
			// i mean... conn is sent by Driver anyways......
		}

		// start me threads now
		// int threadLength = (objectLength / conn); //may need to +1 because int
		// division?
		// rounds down?
		// MyThreadMaker threadmaker = new MyThreadMaker(host, port, filepath, conn,
		// objectLength);
		Thread[] threadArr = new Thread[conn];
		for (int i = 0; i < conn; i++) {
			threadArr[i] = new Thread(new MyThread(myHost, myPort, myFilepath, conn, objectLength, i));
			threadArr[i].start();
		}
		// should only need 1 thread if only 1 connection/
		// behaviour dont change my dude
		for (int i = 0; i < conn; i++) {
			try {
				threadArr[i].join();
				// gotta join them so don't try to access below code (combining file code)
				// before they done
			} catch (InterruptedException e) {
				e.printStackTrace(); // o shi
			}
		}

		if (conn > 1) {
			String[] filePathSplit = myFilepath.split("/");
			String fileName = filePathSplit[filePathSplit.length - 1];
			File file = new File(fileName);
			if (file.createNewFile()) {
				// System.out.println(fileName + " File Created");
			} else {
				// System.out.println(fileName + " already exists");
			}
			FileOutputStream outStream = new FileOutputStream(file);
			for (int i = 0; i < conn; i++) {
				File inFile = new File(i + "_" + fileName);
				FileInputStream inStream = new FileInputStream(inFile);
				byte[] http_object_byte = new byte[objectLength / conn];
				// int myOff = i * 1024;
				int fileRead = inStream.read(http_object_byte);
				// outStream.write(http_object_byte, myOff, fileRead);
				outStream.write(http_object_byte, 0, fileRead);
				inStream.close();
				inFile.delete();
			}
			outStream.close();
		}
		socket.close();
	}

}
