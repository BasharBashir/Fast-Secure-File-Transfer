package common;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientsHoldTheFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static String FileName;
public static ArrayList<Integer> Clients;
public ClientsHoldTheFile(String FileName ) {
	this.FileName=FileName;
	Clients=new ArrayList<Integer>();
}

public static void addclients(int id)
{
	
	Clients.add(id);
}
public static String getFileName() {
	return FileName;
}
public static void setFileName(String fileName) {
	FileName = fileName;
}
public static ArrayList<Integer> getClients() {
	return Clients;
}
public static void setClients(ArrayList<Integer> clients) {
	Clients = clients;
}

}
