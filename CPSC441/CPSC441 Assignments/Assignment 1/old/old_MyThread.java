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
public class old_MyThread implements Runnable {
	String myHost;
	int myPort;
	String myfilepath;
	int myConn;
	int myObjectLength;
	int i;

	public old_MyThread(String myHost, int myPort, String myFilepath, int myConn, int myObjectLength, int i) {
		this.myHost = myHost;
		this.myPort = myPort;
		this.myfilepath = myFilepath;
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
		int length = myObjectLength / myConn;
		// System.out.println(i+"= i myLengthis" + length+"myobjLen "+objectLength+"conn
		// "+ conn);
		int minRange = i *  length;
		int maxRange = ((i + 1) * length);

		try {
			Socket socket = new Socket(myHost, myPort);
			// send the get request for only my bits
			String requestLine_1 = "GET " + myfilepath + " HTTP/1.1\r\n";
			String requestLine_2 = "Host: " + myHost + "\r\n";
			String rangeLine = "Range: bytes=" + minRange + "-" + maxRange + "\r\n";
			String eoh_line = "\r\n";

			String http_header = requestLine_1 + requestLine_2 + rangeLine + eoh_line;
			byte[] http_header_in_bytes = http_header.getBytes("US-ASCII");
			System.out.println(http_header);
			socket.getOutputStream().write(http_header_in_bytes);
			socket.getOutputStream().flush();

			// get filename? need different for many
			String[] filePathSplit = myfilepath.split("/");
			// String fileName = filePathSplit[filePathSplit.length - 1];
			// if (myConn > 1) {
			String fileName = i + "_" + filePathSplit[filePathSplit.length - 1];
			// }
			File file = new File(fileName);
			// file.getParentFile().mkdirs();
			if (file.createNewFile()) {
				//System.out.println(fileName + " File Created");
			} else {
				//System.out.println(fileName + " already exists");
			}
			FileOutputStream outStream = new FileOutputStream(file);

			byte[] http_response_header_bytes = new byte[2048];
			int off = 0;
			String http_response_header_string = null;
			// read header
			while (true) {

				socket.getInputStream().read(http_response_header_bytes, off, 1);
				off++;
				http_response_header_string = new String(http_response_header_bytes, 0, off, "US-ASCII");
				if (http_response_header_string.contains("\r\n\r\n")) {
					break;
				}
			}

			int num_byte_read = 0;
			int counter = 0;
			byte[] http_object_byte = new byte[1024];
			// get the file now
			while (num_byte_read != -1) {
				if (counter == myObjectLength) {
					break;
				}
				num_byte_read = socket.getInputStream().read(http_object_byte);
				outStream.write(http_object_byte, 0, num_byte_read);
				// outStream.write(http_object_byte);
				counter = counter + num_byte_read;
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
