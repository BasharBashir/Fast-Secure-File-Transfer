package common;

import java.io.Serializable;

public class ClientsPing implements Serializable{
private String ip ;
private double Time;
private long  chunksnumber;
public ClientsPing(String ip, double time, long chunksnumber) {
	super();
	this.ip = ip;
	Time = time;
	this.chunksnumber = chunksnumber;
}
public long getChunksnumber() {
	return chunksnumber;
}
public void setChunksnumber(long l) {
	this.chunksnumber = l;
}
@Override
public String toString() {
	return "ClientsPing [ip=" + ip + ", Time=" + Time + ", chunksnumber=" + chunksnumber + "]";
}
public ClientsPing(String ip, double time) {
	super();
	this.ip = ip;
	Time = time;
}
public String getIp() {
	return ip;
}
public void setIp(String ip) {
	this.ip = ip;
}
public double getTime() {
	return Time;
}
public void setTime(double time) {
	Time = time;
}
}
