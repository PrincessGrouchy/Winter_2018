package cpsc441.a1;

/**
 * A simple test driver
 * 
 * @author 	Majid Ghaderi
 *
 */

import cpsc441.a1.QuickUrl;
import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Driver.
 */
public class Driver {

	/** The obj 1. */
	private static String obj1 = "http://people.ucalgary.ca/~mghaderi/cpsc441/dots.txt";

	/** The obj 2. */
	private static String obj2 = "http://people.ucalgary.ca/~mghaderi/cpsc441/paper.pdf";

	/** The obj 3. */
	private static String obj3 = "http://people.ucalgary.ca/~mghaderi/cpsc441/galaxy.jpg";

	/** The conn. */
	private static int conn = 10;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		System.out.println("test started");
		System.out.println("------------\n");

		checkStatus(obj1, 1); // text object with single request
		checkStatus(obj2, 1); // binary object with single request
		checkStatus(obj3, 10); // big object with multiple requests

		System.out.println("--------------");
		System.out.println("test completed");
	}

	/**
	 * Check status.
	 *
	 * @param url  the url
	 * @param conn the conn
	 */
	public static void checkStatus(String url, int conn) {
		QuickUrl quick = new QuickUrl();

		System.out.printf("url: %s\n", url);
		System.out.printf("conn: %d\n", conn);

		quick.setConn(conn);
		quick.getObject(url);

		System.out.println("done\n");
	}
}
