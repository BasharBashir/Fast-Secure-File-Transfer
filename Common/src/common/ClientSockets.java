package common;

import java.io.Serializable;

public class ClientSockets implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String MyIP;
	private String IP;
	private SocketManager socket;
	public ClientSockets(String HostName, String iP, SocketManager socket) {
		this.MyIP = HostName;
		this.IP = iP;
		this.socket = socket;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public SocketManager getSocket() {
		return socket;
	}
	public void setSocket(SocketManager socket) {
		this.socket = socket;
	}
	public String getHostName() {
		return MyIP;
	}
	public void setHostName(String hostName) {
		MyIP = hostName;
	}
	@Override
	public String toString() {
		return "ClientSockets [MyIP=" + MyIP + ", IP=" + IP + ", socket=" + socket.toString() + "]";
	}
	public String getMyIP() {
		return MyIP;
	}
	public void setMyIP(String myIP) {
		MyIP = myIP;
	}
	

}