// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package Client;

import ocsf.client.*;
import ocsf.server.ConnectionToClient;
import common.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient  {
	ChatClient cc;
	public static ArrayList<clientsInfo> ClientAddresseWithId;
	public static List<String> ClientAddresses= new ArrayList<>(Arrays.asList()); 
	public static List<ClientsPort> ClientsPort = new ArrayList<>(Arrays.asList());
	public static ArrayList<ClientSockets> ClientSockets=new ArrayList<ClientSockets>();
	public static String SharingIP;
    public static int port ;
	public static int ID;
	public static String RequirdFileKey ; 
	public static String HashCode ;
	public static ArrayList<ExistsFile> FileinfoList = new ArrayList<>();
	public static ArrayList<ExistsFile> FileInSystem = new ArrayList<ExistsFile>();
	 

	public static  Semaphore sem = new Semaphore(0);
	public static int ConnectedcilintsNumber;
	public static boolean awaitResponse = false;
	public static boolean RequsetPing = false;
	public static ArrayList<ClientsPing> ClientsPinglist=new  ArrayList<>();
	public static String DH_Key;
	public static String SecureHashDigest;
	public static ArrayList<ClientFileInfo> ClientsFile=new ArrayList<ClientFileInfo>();
	public static HashMap<String, List<String>> clintsholdfile1 = new HashMap<String, List<String>>();
    public static int Client_Port;
    public static ClientFileInfo  ClientFileInfo;
	ChatIF clientUI;
	public static ArrayList<ExistsFile> finallistoffiles = new ArrayList<>();
	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}

	// Instance methods ************************************************

	/**
	 * @param msg The message from the server.
	 */
	
	 
	@SuppressWarnings("unchecked")
	public void handleMessageFromServer(Object msg) {
		awaitResponse = false;

		System.out.println("--> handleMessageFromServer");
		MessagesClass message = (MessagesClass) msg;
		switch (message.getMsgType()) {
		case NumberOfConnectedClient:
			ConnectedcilintsNumber=(int)message.getMsgData();
			 System.out.println ("number of connected clients"+ConnectedcilintsNumber);
			break;
		case GetIPAddresses:
			System.out.println("the size :"+(String)message.getMsgData().toString());

			ChatClient.ClientAddresses=(List<String>)message.getMsgData();
			message.setMsgData(null);
			System.out.println("the size :"+ClientAddresses.size()+" the connection : "+ClientAddresses.toString());
			break;
		case SendId:
			ID=(int)message.getId();
			break;
		case CheckPort:
			port =(int)message.getMsgData();
			break;
		case UpdateServer:
			String s =(String)message.getMsgData();
			break;
		case GETDATA:
			boolean flag=true;
			FilePacket FilePacket=(FilePacket)message.getMsgData();
			String datafile ="C:\\Client_Id_"+ChatClient.ID+"\\"+FilePacket.getFile().getName();
			try {
				FileOutputStream fos = new FileOutputStream(datafile);
				byte[] fileArr =  FilePacket.getFileArr();
				fos.write(fileArr, 0, fileArr.length);
		        fos.close();
				clintsholdfile1=readFromFile(datafile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("the fucken list :"+FileInSystem);
			 for(int i=0;i<FileInSystem.size();i++) {
		    	   for(int j=0;j<FileInSystem.size();j++) {
		    		   if(FileInSystem.get(i).getFileName().equals(FileInSystem.get(j).getFileName())&&!FileInSystem.get(i).getIpList().equals(FileInSystem.get(j).getIpList()))
		    		   {
		    			   FileInSystem.get(i).getIpList().addAll(FileInSystem.get(j).getIpList());
		    			   finallistoffiles.add(new ExistsFile(FileInSystem.get(i).getFileName(), FileInSystem.get(i).getIpList(),FileInSystem.get(i).getChunksNum(),FileInSystem.get(i).getKey(),FileInSystem.get(i).getHashcode()))  ; 
		    			   FileInSystem.remove(i);
		    			   FileInSystem.remove(j);
		    			   flag=false;
		    			   System.out.println("atfer delete finallistoffiles"+finallistoffiles);		     
				    	   System.out.println("atfer delete file in system"+FileInSystem);
		    			   
		    		   }
   		   
		    	   }
		    	   if(flag) {
		  			   finallistoffiles.add(new ExistsFile(FileInSystem.get(i).getFileName(), FileInSystem.get(i).getIpList(),FileInSystem.get(i).getChunksNum(),FileInSystem.get(i).getKey(),FileInSystem.get(i).getHashcode()))  ; 
		    	   }else {
		    		   flag=true; } 
		    	   System.out.println("the finallistoffiles"+finallistoffiles);		     
		    	   System.out.println("the file in system"+FileInSystem);	}
		break;
		case BroadCast:		
			 System.out.println ("broad cast from server to all clients  "+(String)message.getMsgData()+"port : "+message.getPort());
			 String ip=(String)message.getMsgData();
			try {
				if(!InetAddress.getLocalHost().getHostAddress().equals(ip)) {
					if(ID==1) {
					 ClientAddresses.add((String)message.getMsgData());
					 System.out.println("the size :"+ClientAddresses.size()+" the connection : "+ClientAddresses.toString());
					}
					 
				//	 ClientUI.chat.accept(new MessagesClass(Messages.RequestPortInBroadCast, ip));
					//ask server
					SocketManager manager = new SocketManager((String)message.getMsgData(),(int)message.getPort(),3000);
					Thread thread = new Thread(manager);
					//should send it to server n order to update 
					ClientSockets.add(new ClientSockets((String)InetAddress.getLocalHost().getHostAddress(),(String)message.getMsgData(),manager));
					thread.start();
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
		case ClientsPerformance:
			String messagefromserver=(String)message.getMsgData();
			System.out.println(messagefromserver);
			break;
		case DH_Key:
			DH_Key=(String) message.getMsgData();
			System.out.println("the key:"+DH_Key);
			break;
			
		case FileName_FileChunks:
			 ClientFileInfo=(ClientFileInfo) message.getMsgData();
			System.out.println(ClientFileInfo.toString());
			//clintsholdfile =(HashMap<String, List<String>>)message.getMsgData1();
			ClientsFile.add(ClientFileInfo);
			for (String i : clintsholdfile1.keySet()) {
				  System.out.println("key: " + i + " value: " + clintsholdfile1.get(i).toString());
				}
		break;
		
		
		case RequestPortInBroadCast:
			 System.out.println ("im in RequestPortInBroadCast port  ");
			 Client_Port=(int)message.getMsgData();
			 System.out.println ("im in RequestPortInBroadCast port  "+Client_Port);
			break;
		case RequestPort:
			 Client_Port=(int)message.getMsgData();
			 System.out.println ("im in request port  "+Client_Port);

			break;
		case FileData:
			RequirdFileKey=(String)message.getMsgData();
			HashCode=(String)message.getMsgData1();
			 System.out.println ("im in FileKey "+RequirdFileKey);
			 System.out.println ("im in FileKey "+HashCode);
			break;
			
			
		case GetHistory:
			FilePacket filePacket =(FilePacket)message.getMsgData();
			String filepath ="C:\\Client_Id_"+ChatClient.ID+"\\"+filePacket.getFile().getName();
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(filepath);
				byte[] fileArr =  filePacket.getFileArr();
				fos.write(fileArr, 0, fileArr.length);
		        fos.close();
		       
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			break;
		default:
			break;
		}
	}
	
	
	
	
	    public  HashMap<String, List<String>> readFromFile(String filename) throws IOException {
	    	HashMap<String, List<String>> dataList = new HashMap<String, List<String>>();
	    	 List<String> ip = new ArrayList<String>(Arrays.asList());
	    	 List<String>	 iptemp = new ArrayList<String>(Arrays.asList());
	    	 String fileName = null;
	    	 String fileNametemp = null;
	    	 String numberchunks;
	    	 String key;
	    	 String hashcode;
	    	 ArrayList<String> names=new ArrayList<String>();
	        // Open the file and read each line
	        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        	  String line;
	        	  int i=1;
	        	while ((line = br.readLine()) != null) {
 	        	String[] parts = line.split("->");
	        	  fileName = parts[0];
	        	 String[] arrayData = parts[1].replaceAll("[\\[\\] ]", "").split(",");
	        	 
	        	 numberchunks=parts[2];
	        	 key=parts[3];
	        	 hashcode=parts[4];
	            for (String part : arrayData) {
	            	ip = new ArrayList<String>(Arrays.asList());
	            	String s=new String(part); 
	            	ip.add((s));
	            	  
	            }
	            String linetemp;
	            BufferedReader tc= new BufferedReader(new FileReader(filename));
	            while ((linetemp = tc.readLine()) != null) {
	            	String[] partstemp = linetemp.split("->");
		        	  fileNametemp = partstemp[0];
		        	 String[] arrayDatatemp = partstemp[1].replaceAll("[\\[\\] ]", "").split(",");
		        	 for (String part : arrayDatatemp) {
			            	iptemp = new ArrayList<String>(Arrays.asList());
			            	String s=new String(part); 
			            	iptemp.add((s));
			            	  
			            } 
	            	 if(fileNametemp.equals(fileName)&&!iptemp.equals(ip))
	            	 {
	            		ip.addAll(iptemp);
	            		names.add(fileNametemp);
	            	 }
	            	
	            	
	            }
	            
	            ExistsFile data=new ExistsFile();
	            data.setFileName(fileName);
	            data.setIpList(ip);
	            data.setChunksNum(Integer.parseInt(numberchunks));
	            data.setHashcode(hashcode);
	            data.setKey(key);
	            FileInSystem.add(data);	       
	            
	        	}
	                // Create a new DataLine object and add it to the ArrayList      
	        }
	        Map<String, Integer> nameCounts = new HashMap<>();
  
	        for (ExistsFile temp:FileInSystem) {
	            if (nameCounts.containsKey(temp.getFileName())) {
	                int count = nameCounts.get(temp.getFileName());
	                nameCounts.put(temp.getFileName(), count + 1);
	            } else {
	                nameCounts.put(temp.getFileName(), 1);
	            }
	        }

	        for (int i = FileInSystem.size() - 1; i >= 0; i--) {
	            String name = FileInSystem.get(i).getFileName();
	            int count = nameCounts.get(name);

	            if (count > 1) {
	                nameCounts.put(name, count - 1);
	                FileInSystem.remove(i);
	            }
	        }
	        for (ExistsFile temp:FileInSystem) { 
	        	dataList.put(temp.getFileName(), temp.getIpList());}
	        return dataList;
	    }
	     
	/**
	 *
	 * @param message The message from the UI.
	 */
	public void handleMessageFromClientUI(Object message) {
		awaitResponse = true;
		try {
			sendToServer(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// wait for response
		while (awaitResponse) {
			try {

				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {

			sendToServer(new MessagesClass(Messages.Disconnected, null));

			closeConnection();

		} catch (IOException e) {
		}
		// System.exit(0);
	}
}
//End of ChatClient class
