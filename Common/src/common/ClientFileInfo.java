package common;

import java.io.Serializable;

public class ClientFileInfo implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String FileName;
int FileChunks;
String IpAddress;
String key;
String SHA256HashOfFile;
public String getKey() {
	return key;
}
public void setKey(String key) {
	this.key = key;
}
public String getSHA256HashOfFile() {
	return SHA256HashOfFile;
}
public void setSHA256HashOfFile(String sHA256HashOfFile) {
	SHA256HashOfFile = sHA256HashOfFile;
}
public ClientFileInfo(String fileName, int fileChunks, String ipAddress, String key, String sHA256HashOfFile) {
	super();
	FileName = fileName;
	FileChunks = fileChunks;
	IpAddress = ipAddress;
	this.key = key;
	this.SHA256HashOfFile = sHA256HashOfFile;
}
public String getFileName() {
	return FileName;
}
public void setFileName(String fileName) {
	FileName = fileName;
}
public int getFileChunks() {
	return FileChunks;
}
public void setFileChunks(int fileChunks) {
	FileChunks = fileChunks;
}
@Override
public String toString() {
	return "ClientFileInfo [FileName=" + FileName + ", FileChunks=" + FileChunks + ", IpAddress=" + IpAddress + ", key="
			+ key + ", SHA256HashOfFile=" + SHA256HashOfFile + "]";
}
public String getIpAddress() {
	return IpAddress;
}
public void setIpAddress(String ipAddress) {
	IpAddress = ipAddress;
}
public ClientFileInfo(String fileName, int fileChunks, String ipAddress) {
	super();
	FileName = fileName;
	FileChunks = fileChunks;
	IpAddress = ipAddress;
}
}
