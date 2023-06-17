package Server;
// This file contains material supporting section 3.7 of the textbook:

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;  
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Client.ChatClient;
import common.ClientFileInfo;
import common.ClientSockets;
import common.ClientsPing;
import common.ConnectedClients;
import common.ExistsFile;
import common.FilePacket;
import common.LocalPorts;
import common.Messages;
import common.MessagesClass;
import common.ClientsPort;
import common.clientDetails;
import common.clientsInfo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import ocsf.server.*;
import java.io.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	public static List<String> ClientAddresses = new ArrayList<>(Arrays.asList());
	public static List<ClientsPort> ClientsPort = new ArrayList<>(Arrays.asList());
	public static ArrayList<clientsInfo> ClientAddresseWithId = new ArrayList<>();
//	public static ArrayList<String> ClientAddresses = new ArrayList<String>();
	public static ArrayList<ClientFileInfo> ClientFile = new ArrayList<>();
	public static ArrayList<LocalPorts> ClientPorts=new ArrayList<LocalPorts>();
	public static HashMap<String, List<String>> clintsholdfile = new HashMap<String, List<String>>();
	public  ArrayList<ExistsFile> clientsfile = new ArrayList<>();
    public static int port;
    public static int Client_Port;
	public static ArrayList<ExistsFile> ExistsFile = new ArrayList<>();
	public static ArrayList<ExistsFile> FileinfoList = new ArrayList<>(); 
	public static ArrayList<ExistsFile> finallistoffiles = new ArrayList<>();
	public  ArrayList<ExistsFile> File_InHistory = new ArrayList<>();
	public static ArrayList<ClientsPing> ClientsPinglist=new  ArrayList<>();
    public static int ID=1;
    String HistoryFile = "history.txt";
	final public static int DEFAULT_PORT = 5555;
	FileWriter writer,clienttimer;
	 File dir;
	 File FilesInfo;
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	
	public EchoServer(int port) {
		super(port);
		 writer = null;
		 String currentDirectory = System.getProperty("user.dir");
		  
		  FilesInfo=new File("FilesInformation.txt");
		 File f = new File("FileInfo");
		 dir=new File(f.getPath());
		 if(!f.exists())
			 f.mkdir();
	    //should add the hash
	    try {
			writer = new FileWriter(HistoryFile, true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static int FoundPort(String ip){
		int port = 0;
		for (ClientsPort addr : ClientsPort) {
		if(ip.equals(addr.getHostip())) {
		port=addr.getPort();
		  }
		}
		return port;
		
	}
	
	
	
	
	public static int generateRandomPort(){
        int port;
        Random random = new Random();
        do {
            port = random.nextInt(65535 - 1024 + 1) + 1024;
        } while (portExists(port, ClientsPort));
        
        return port;
    }

    private static boolean portExists(int port, List<ClientsPort> existingPorts) {
        for (ClientsPort addr : existingPorts) {
            if (addr.getPort() == port) {
                return true;
            }
        }
        return false;
    }
//between 1024 and 65535
	public void clientConnected(ConnectionToClient client) {
		System.out.println("->Client Connected");
		try {
			UpdateClient(client.getInetAddress().getLocalHost(), client.getInetAddress().getHostAddress(), "Connected");
			ClientAddresseWithId.add(new clientsInfo(client.getInetAddress().getHostAddress(),ID));
			ClientAddresses.add(client.getInetAddress().getHostAddress());
			ClientPorts.add(new LocalPorts((String) client.getInetAddress().getHostAddress()));
			System.out.println("the id number :"+ID);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(client.getInetAddress().getLocalHost().toString()+"  " + client.getInetAddress().getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		}

	public void clientDisconnected(ConnectionToClient client) {
		System.out.println("->Client DisConnected");
		try {

			UpdateClient(client.getInetAddress().getLocalHost(), client.getInetAddress().getHostAddress(), "Disconnected");
			ClientAddresseWithId.remove(new clientsInfo(client.getInetAddress().getHostAddress(),ID));
			//ClientAddresses.remove(client.getInetAddress().getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void UpdateClient(InetAddress HostName, String IP, String Status) {
		ServerUI.aFrame.UpdateClient(HostName, IP, Status);
	}
	// Instance methods ************************************************

	/**
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */

	@SuppressWarnings("unchecked")
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {

		System.out.println("Message received: " + ((MessagesClass) msg).getMsgType() + " from " + client);
		MessagesClass message = (MessagesClass) msg;
	
		switch (message.getMsgType()) {
	///// general//////
		case Disconnected:////
		 
			clientDisconnected(client);
			break;
			
		case GETDATA:
		   
			 PrintWriter pw = null;
			try {
				pw = new PrintWriter("FilesInformation.txt");
			} catch (FileNotFoundException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			 
		        // Get list of all the files in form of String Array
		        String[] fileNames = dir.list();
		 
		        // loop for reading the contents of all the files
		        // in the directory GeeksForGeeks
		        for (String fileName : fileNames) {
		            System.out.println("Reading from " + fileName);
		 
		            // create instance of file from Name of
		            // the file stored in string Array
		            File f = new File(dir, fileName);
		 
		            // create object of BufferedReader
 		            // Read from current file
		            
						try {
							BufferedReader	br = new BufferedReader(new FileReader(f));
							String line = br.readLine();
							 while (line != null) {
								 pw.println(line);
										line = br.readLine();
					            }
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		             
		            pw.flush();
		        }
		        
	            byte[] b = new byte[1024];
	            try {
					b=Files.readAllBytes(FilesInfo.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            FilePacket filePa = new FilePacket(FilesInfo,b,0, false);
	            sendToAllClients( new MessagesClass(Messages.GETDATA,filePa));
		       
			break;
		case UpdateServer:
			FilePacket fp =(FilePacket)message.getMsgData();
			String filepath1 ="FileInfo\\"+fp.getFile().getName();
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(filepath1);
				byte[] fileArr =  fp.getFileArr();
				fos.write(fileArr, 0, fileArr.length);
		        fos.close();
		        clientsfile= readFromFile(filepath1); 
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			try {
				client.sendToClient(new MessagesClass(Messages.UpdateServer,"receive the list"));
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			break;
		case GetIPAddresses:
			//System.out.println( "the number o9f cionnected clients in system "
					// +getNumberOfClients());
			System.out.println(ClientAddresses.toString()+" in echo server");
			sendToAllClients(new MessagesClass(Messages.GetIPAddresses,ClientAddresses));

			message = new MessagesClass(Messages.SendId,null,ID);
			try {
				
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
			sendToAllClients(new MessagesClass(Messages.DH_Key,ServerPortFrameController.key));
			//sendToAllClients(new MessagesClass(Messages.BroadCast,client.getInetAddress().getHostAddress(),client.getInetAddress().getHostAddress()));
			ID++;
		
			break;
		case NumberOfConnectedClient:
			sendToAllClients(new MessagesClass(Messages.NumberOfConnectedClient,getNumberOfClients()));
			break;
		case RequestPortInBroadCast:
			Client_Port=FoundPort((String)message.getMsgData());
			 
			 MessagesClass message1 = (MessagesClass) msg;
			 message1 = new MessagesClass(Messages.RequestPortInBroadCast,Client_Port);
	            try {
	            	 System.out.println(message1.toString());
					client.sendToClient(message1);
				} catch (IOException e) {
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}
			break;
			
			
			
			
		case RequestPort:
			 Client_Port=FoundPort((String)message.getMsgData());
			message = new MessagesClass(Messages.RequestPort,Client_Port);
            try {
            	 System.out.println(message.toString());
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
			break;
        case CheckPort:
        	port =generateRandomPort();
        	ClientsPort.add(new ClientsPort(client.getInetAddress().getHostAddress(),port));
        	message = new MessagesClass(Messages.CheckPort,port);
			try {
				
				client.sendToClient(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
			sendToAllClients(new MessagesClass(Messages.BroadCast,client.getInetAddress().getHostAddress(),port));
			break;
			
			
        case ClientsPerformance:
        	ClientsPinglist=(ArrayList<ClientsPing> )message.getMsgData();//the client  Performance
        	System.out.print(ClientsPinglist.toString());
        	try {
				clienttimer = new FileWriter("ClientsPerformance.txt", false);
				for (ClientsPing i : ClientsPinglist) {
					clienttimer = new FileWriter("ClientsPerformance.txt", true);
					clienttimer.write( i.getIp()+" need = " +i.getTime()+ " sec \n");
					clienttimer.flush();
					clienttimer.close();
	        	}
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        	try {
				client.sendToClient(new MessagesClass(Messages.ClientsPerformance,"receive the list"));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        	System.out.println(ClientsPinglist.toString());
        	break;
		case FileName_FileChunks:
			ClientFile.add((ClientFileInfo)message.getMsgData());
			System.out.println("new file from client 1 :"+ClientFile);
			ClientFileInfo ClientFileInfo=(ClientFileInfo)message.getMsgData();
		    ServerUI.aFrame.UpdateClientFile(((ClientFileInfo) message.getMsgData()).getFileName(),((ClientFileInfo) message.getMsgData()).getFileChunks(),((ClientFileInfo) message.getMsgData()).getIpAddress());
		    for(int i=0;i<ClientFile.size();i++)
				System.out.println("new file in system:"+ClientFile.get(i).toString());
		    clintsholdfile.put(ClientFileInfo.getFileName(),ClientAddresses);
		    try {
				File_InHistory=readFromFile(HistoryFile);
				 writer = new FileWriter(HistoryFile, false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (String i : clintsholdfile.keySet()) {
	    	try {
	    		for(ClientFileInfo CF:ClientFile)
	    		{
	    			if(CF.getFileName().equals(i))
	    			{
	    		    writer = new FileWriter(HistoryFile, true);
					writer.write( i+"->" +clintsholdfile.get(i)+"->"+CF.getFileChunks()+"->"+CF.getKey()+"->"+CF.getSHA256HashOfFile()+ "\n");
					writer.flush();
					writer.close();
	    			}
	    		}
			} catch (IOException e) {
					 //TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				  System.out.println("key: " + i + " ips: " + clintsholdfile.get(i).toString());}
		    sendToAllClients(new MessagesClass(Messages.FileName_FileChunks, (ClientFileInfo)message.getMsgData(),(HashMap<String, List<String>>)clintsholdfile));
 			break;
 			
 			
		case GetHistory:
			File file=new File(HistoryFile);
            byte[] buffer = new byte[1024];
            try {
				buffer=Files.readAllBytes(file.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            FilePacket filePacket = new FilePacket(file,buffer,0, false);
            sendToAllClients( new MessagesClass(Messages.GetHistory,filePacket));
			break;
			
			
		case FileData:
			String filename=(String)message.getMsgData();
			  boolean Exists=false;
			if(!ClientFile.isEmpty()) {
			for(ClientFileInfo Cf:ClientFile) {
				if(Cf.getFileName().equals(filename))
				{
					sendToAllClients(new MessagesClass(Messages.FileData,Cf.getKey(),Cf.getSHA256HashOfFile()));
					Exists=true;
				}
			}
			}
			else if (!Exists) {
				try {
					
					ArrayList<ExistsFile> Ef=readFromFile(HistoryFile);
					for(ExistsFile ExistsFile : Ef) {
						if(ExistsFile.getFileName().equals(filename))
						{
							try {
								client.sendToClient(new MessagesClass(Messages.FileData,ExistsFile.getKey(),ExistsFile.getHashcode()));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			break;
			
		default:
			break;
		}
	}
	 public static ArrayList<ExistsFile> readFromFile(String filename) throws IOException {
	    	HashMap<String, List<String>> dataList = new HashMap<String, List<String>>();
	    	 List<String> ip = new ArrayList<>(Arrays.asList());
	    	 String fileName = null;
	    	 String numberchunks;
	    	 String hashcode;
	    	 ExistsFile.clear();
	    	 String key;
	        // Open the file and read each line
	        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        	  String line;
	        	  int i=1;
	        	while ((line = br.readLine()) != null) {
	        		ip.clear();
	        	String[] parts = line.split("->");
	        	  fileName = parts[0];
	        	  
				  System.out.println(parts[1]);
	        	 String[] arrayData = parts[1].replaceAll("[\\[\\] ]", "").split(",");
	        	 
	        	 numberchunks=parts[2];
	        	 key=parts[3];
	        	 hashcode=parts[4];

             System.out.print(line);
	            for (String part : arrayData) {
					  System.out.println(part);

	            	ip.add((part));
	            }
	            ExistsFile.add(new ExistsFile(fileName,ip,Integer.parseInt(numberchunks),key,hashcode));
	             
	        	}
	                // Create a new DataLine object and add it to the ArrayList
	             
	            
	        }

	        return ExistsFile;
	    }
	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}


	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *                is entered.
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
//End of EchoServer class
