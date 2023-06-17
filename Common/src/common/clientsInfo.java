package common;

import java.io.Serializable;

import ocsf.server.ConnectionToClient;

/**
 * save client host details
 * @author asem
 *
 */
public class clientsInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String clientIp;
	int id;
	
	
	
	public clientsInfo(String client, int id ) {
		super();
		this.clientIp = client;
		this.id = id;
	}
	public int getId() {
		return id;
	}
	@Override
	public String toString() {
		return "clientsInfo [clientIp=" + clientIp + ", id=" + id + "]";
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getclientIp() {
		return clientIp;
	}
	public void setclientIp(String client) {
		this.clientIp = client;
	}
	 
	
}
