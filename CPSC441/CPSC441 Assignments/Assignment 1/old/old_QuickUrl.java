package cpsc441.a1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

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
public class old_QuickUrl extends ConcurrentHttp {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cpsc441.a1.ConcurrentHttp#getObject(java.lang.String)
	 */
	@Override
	public void getObject(String url) {
		String myHost, myFilepath;
		int myPort = 80;

		String noHttp = url.replace("http://", ""); // also need to throw out "http://"
		String[] splitUrl = noHttp.split("/", 2); // try to split url to get host and port.

		// now should have people.ucalgary.ca and /~mghaderi stuff.
		// Will now check for port number.
		// remaining url doesn't have http:, so shouldn't worry?
		// Source: Cyriac James.
		myFilepath = "/" + splitUrl[1];
		if (splitUrl[0].contains(":")) {
			String hostandport[] = splitUrl[0].split(":", 2);
			myHost = hostandport[0];
			myPort = Integer.parseInt(hostandport[1]);
		} else { // no port number
			myHost = splitUrl[0];
		}

		try {
			SocketMaker(myHost, myPort, myFilepath);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void SocketMaker(String host, int port, String filepath) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		/*
		 * HEAD /~mghaderi/cpsc441/dots.txt HTTP/1.1 Host: people.ucalgary.ca
		 */
		// calling head to make sure... there's no content length stuff.
		Socket mySocket = new Socket(host, port);
		String myRequest_1 = "HEAD " + filepath + " HTTP/1.1\r\n";
		String myRequest_2 = "Host: " + host + "\r\n";
		String myEnd = "\r\n";
		String sendingHeader = myRequest_1 + myRequest_2 + myEnd;
		byte[] sendingHeader_in_bytes = sendingHeader.getBytes("US-ASCII");
		// System.out.println(sendingHeader);
		mySocket.getOutputStream().write(sendingHeader_in_bytes);
		mySocket.getOutputStream().flush();

		// now, put return header into a string
		byte[] serverResponse_header_bytes = new byte[2048];
		int num_byte_read = 0;
		int myOffputResponse = 0;
		String serverResponse_header_string = null;
		while (true) {
			mySocket.getInputStream().read(serverResponse_header_bytes, myOffputResponse, 1);
			// reads 1 byte at a time
			myOffputResponse++;
			serverResponse_header_string = new String(serverResponse_header_bytes, 0, myOffputResponse, "US-ASCII");
			// i have no idea how this saves the whole header, but OK.
			// Source: syedahmedzaidi
			if (serverResponse_header_string.contains("\r\n\r\n")) {
				// System.out.println("start header");
				System.out.println(serverResponse_header_string);
				// System.out.println("end header");
				break;
			}
		}

		// get Content-Length
		String[] responseSplit_ContentLength = serverResponse_header_string.split("Content-Length: ", 2);
		String[] responseSplit2_AtEoL = responseSplit_ContentLength[1].split("\r\n", 2);
		int objectLength = Integer.parseInt(responseSplit2_AtEoL[0]);

		// (a)Accept-Ranges
		if (!serverResponse_header_string.contains("Accept-Ranges:")
				|| serverResponse_header_string.contains("Accept-Ranges: none")) {
			// must not use range requests.
			this.setConn(1);
		}

		// start me threads now
		// int threadLength = (objectLength / conn); //may need to +1 because int
		// division?
		// rounds down?
		// MyThreadMaker threadmaker = new MyThreadMaker(host, port, filepath, conn,
		// objectLength);
		Thread[] threadArr = new Thread[conn];
		for (int i = 0; i < conn; i++) {
			threadArr[i] = new Thread(new old_MyThread(host, port, filepath, conn, objectLength, i));
			threadArr[i].start();

		}
		for (int i = 0; i < conn; i++) {
			try {
				threadArr[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// gotta put it back together now
		// if (conn > 1) {
		String[] filePathSplit = filepath.split("/");
		String fileName = filePathSplit[filePathSplit.length - 1];
		File file = new File(fileName);
		// file.getParentFile().mkdirs();
		if (file.createNewFile()) {
			// System.out.println(fileName + " File Created");
		} else {
			// System.out.println(fileName + " already exists");
		}
		FileOutputStream outStream = new FileOutputStream(file);
		for (int i = 0; i < conn; i++) {
			File inFile = new File(i + "_" + fileName);
			FileInputStream inStream = new FileInputStream(inFile);
			byte[] http_object_byte = new byte[1024];
			// int myOff = i * 1024;
			int fileRead = inStream.read(http_object_byte);
			// outStream.write(http_object_byte, myOff, fileRead);
			outStream.write(http_object_byte, 0, fileRead);
			inStream.close();
			inFile.delete();
		}
		outStream.close();
		// }

		mySocket.close();
	}

}
