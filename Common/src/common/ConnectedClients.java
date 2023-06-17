package common;

public abstract class ConnectedClients {

	private String ip;

	public ConnectedClients(String ip) {
		// TODO Auto-generated constructor stub
		this.ip=ip;
	}
	public String getIP() {
		return ip;
	}
	public void setItemID(String ip) {
		this.ip = ip;
	}
}
