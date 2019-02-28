
/**
 * ConcurrentHttp Class
 * 
 * @author 	Majid Ghaderi
 */

package cpsc441.a1;

// TODO: Auto-generated Javadoc
/**
 * The Class ConcurrentHttp.
 */
public abstract class ConcurrentHttp {

	/** The conn. */
	protected int conn = 1; // number of concurrent requests

	/**
	 * Default no-arg constructor.
	 */
	public ConcurrentHttp() {
		// default is to use 1 request per object
		setConn(1);
	}

	/**
	 * Constructor.
	 *
	 * @param conn number of concurrent requests
	 */
	public ConcurrentHttp(int conn) {
		setConn(conn);
	}

	/**
	 * Set method for conn.
	 *
	 * @param conn the new conn
	 */
	public void setConn(int conn) {
		// ignore incorrect values
		if (conn > 0)
			this.conn = conn;
	}

	/**
	 * Get method for conn.
	 *
	 * @return number of concurrent requests
	 */
	public int getConn() {
		return conn;
	}

	/**
	 * Downloads the object specified by the parameter url using conn parallel
	 * requests.
	 *
	 * @param url URL of the object to be downloaded in the format
	 *            hostname[:port]/pathname
	 * @return the object
	 */
	public abstract void getObject(String url);
}
