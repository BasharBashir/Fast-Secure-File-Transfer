package common;

import java.io.Serializable;
import java.util.ArrayList;

public class LocalPorts implements Serializable {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public String Ip;
public ArrayList<Integer> ports;
public String getIp() {
	return Ip;
}
public void setIp(String ip) {
	Ip = ip;
}
public ArrayList<Integer> getPorts() {
	return ports;
}
public void setPorts(ArrayList<Integer> ports) {
	this.ports = ports;
}
public LocalPorts(String ip, ArrayList<Integer> ports) {
	super();
	Ip = ip;
	this.ports = ports;
}
@Override
public String toString() {
	return "LocalPorts [Ip=" + Ip + ", ports=" + ports + "]";
}
public LocalPorts(String ip) {
	super();
	Ip = ip;
	this.ports=new ArrayList<Integer>();
}

}
