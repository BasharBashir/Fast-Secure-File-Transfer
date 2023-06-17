package common;

import java.io.Serializable;

public class ClientsPort implements Serializable{
	@Override
	public String toString() {
		return "ClientsPort [hostip=" + hostip + ", port=" + port + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostip;
	private int port;
	public String getHostip() {
		return hostip;
	}
	public void setHostip(String hostip) {
		this.hostip = hostip;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public ClientsPort(String hostip, int port) {
		super();
		this.hostip = hostip;
		this.port = port;
	}

}
