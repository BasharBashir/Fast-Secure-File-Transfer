package Client;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.FilePacket;
import common.AESDecryption;
import common.AESEncryption;
import common.Decoder;
import common.ExistsFile;
 
import common.LZWCompressor;
import common.MergeFiles;
import common.Messages;
import common.MessagesClass;
import common.RequiredFile;
import common.SHA256HashOfFile;
import common.SendType;
import common.SocketManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

public class ClientHandler implements Runnable,Serializable {
    /**
	 * 
	 */
	ChatClient cc;
	private static final long serialVersionUID = 1L;
	private Socket clientSocket;
    private String FilePath;
    String name;
    String   filepath;
    String FN;
    DisplayThread displayThread = new DisplayThread();
    Thread thread = new Thread(displayThread);
    FileWriter writer;
    boolean FirstTime=true;
    int flag=0; 
    String clientIP;
    private ObjectInputStream inputStreamw=null;
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        InetAddress clientAddress = clientSocket.getInetAddress();
         clientIP = clientAddress.getHostAddress();
     
    }
    
    public void run() {
    	while(true) {
 			try {
 				try {
 				inputStreamw = new ObjectInputStream(this.clientSocket.getInputStream()); 
 				} catch (EOFException e) {
                    // Handle end of input stream
 					System.out.println("Socket stream closed ");
                    break;
                }
 				Object object=inputStreamw.readObject();
 				if(object instanceof FilePacket) {
				FilePacket filePacket = (FilePacket)object;
				System.out.println("file name from client 1 :"+filePacket.getFile().getName());
				int index=filePacket.getFile().getName().indexOf("_");
				 name=filePacket.getFile().getName().substring(0,index);
				 filepath ="C:\\Client_Id_"+ChatClient.ID+"\\"+name+"_Chunks\\"+filePacket.getFile().getName();
				 FN=	"C:\\Client_Id_"+ChatClient.ID+"\\"+name+"_Chunks";
				File directory = new File(FN) ;
		        if (!directory.exists())
		            directory.mkdirs();
				FileOutputStream fos = new FileOutputStream(filepath);
				byte[] fileArr =  filePacket.getFileArr();
				fos.write(fileArr, 0, fileArr.length);
		        fos.close();
		        if(filePacket.isForShare()) {
		    		for(int i=0;i<ChatClient.ClientSockets.size();i++) {
		    			try {
							if(ChatClient.ClientSockets.get(i).getMyIP().equals((String)InetAddress.getLocalHost().getHostAddress())) {
								if(!ChatClient.ClientSockets.get(i).getIP().equals(ChatClient.ClientFileInfo.getIpAddress())) {
									try {
										ChatClient.ClientSockets.get(i).getSocket().sendFile(SendType.sendfile,filepath,0,false);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							 }
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
		    		FirstTime=true;
		        }
		        flag++;
		        if(flag==filePacket.getNumberchunks())
		        {
		        	System.out.println(filePacket.getNumberchunks());
		        	int dotIndex = filePacket.getFile().getName().lastIndexOf('.');
		        	String fileType = filePacket.getFile().getName().substring(dotIndex);
		        	String filename=name+fileType;
 		        	ClientUI.chat.accept(new MessagesClass(Messages.FileData,filename));

		            String filePath=MergeFiles.MergeFiles(FN,name,ChatClient.RequirdFileKey,fileType);
		            writer = new FileWriter(Connection.InfoString, true);
		            List<String> ip=new  ArrayList<>(Arrays.asList());
		            ip.add((String)InetAddress.getLocalHost().getHostAddress());
		           // ip.add(clientIP);
		            writer.write( name+fileType+"->" +ip+"->"+filePacket.getNumberchunks()+"->"+ChatClient.RequirdFileKey+"->"+ChatClient.HashCode+ "\n");
					writer.flush();
					writer.close();
				 	byte[] buffer = new byte[1024];
		            try {
						buffer=Files.readAllBytes(Connection.info.toPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace(); 
					}
		            FilePacket fp = new FilePacket(Connection.info,buffer,0, false);
		            ClientUI.chat.accept(new MessagesClass(Messages.UpdateServer,fp));
		            Platform.runLater(() -> {
		            	Alert alert =  new Alert(AlertType.NONE,
		                        "default Dialog",ButtonType.OK);
		                alert.initStyle(StageStyle.UNDECORATED);
		                alert.setHeaderText(" ");
		                alert.setContentText(filename+" has been saved in your directory");
		                alert.showAndWait();
		                });
		        	Platform.runLater(() -> {
	            		Alert alert =  new Alert(AlertType.NONE,
	                            "default Dialog",ButtonType.OK);
	                alert.initStyle(StageStyle.UNDECORATED);
	                alert.setHeaderText(" ");
	                alert.setContentText("The file has been successfully verified using SHA256");

	                alert.showAndWait();
	                });
		            System.out.println(SHA256HashOfFile.SHA256HashOfFile(filePath));
		            System.out.println(ChatClient.HashCode);
		            if(SHA256HashOfFile.SHA256HashOfFile(filePath).equals(ChatClient.HashCode))
		            {
		             
		            	Platform.runLater(() -> {
		            		Alert alert =  new Alert(AlertType.NONE,
		                            "default Dialog",ButtonType.OK);
		                alert.initStyle(StageStyle.UNDECORATED);
		                alert.setHeaderText(" ");
		                alert.setContentText("You received decrypted file ");

 		                alert.showAndWait();
		                });
		            }  
		         flag=0;
		      //  ChatClient.ClientFileInfo;
		        }
		    
		        
 			}
 				
 				else if(object instanceof String)
 				{String str = (String)object;
 				if(str.equals("ping")) {
 					for(int i=0;i<ChatClient.ClientSockets.size();i++) {
 		    			 
 							if(ChatClient.ClientSockets.get(i).getMyIP().equals((String)InetAddress.getLocalHost().getHostAddress())) {
 								if(ChatClient.ClientSockets.get(i).getIP().equals(this.clientSocket.getInetAddress().getHostAddress())) {
 									try {
 										ChatClient.ClientSockets.get(i).getSocket().sendFile(SendType.PingResponse,"PingResponse",0,false);
 										
 									} catch (InterruptedException e) {
 										// TODO Auto-generated catch block
 										e.printStackTrace();
 									}
 								}
 							 }
 				 
 					}
 				}
 				else if(str.equals("PingResponse"))
 				{
 					 System.out.println("receive the ping");
 					 ChatClient.sem.release();
 					ChatClient.RequsetPing=true;
 				}
 				}
 				else if(object instanceof RequiredFile)
 				{
 					RequiredFile RequiredFile = (RequiredFile)object;
 					for(ExistsFile ExistsFile:ChatClient.FileInSystem)
 					{
 						if(ExistsFile.getFileName().equals(RequiredFile.getFileNmae()))
 						{
 							System.out.println("file name from client 1235 :"+RequiredFile.getFileNmae());
 							int dotIndex = RequiredFile.getFileNmae().lastIndexOf(".");
 					        String fileExtension = (dotIndex == -1) ? "" :  RequiredFile.getFileNmae().substring(dotIndex + 1);
 		 					String fileNameWithoutExtension = RequiredFile.getFileNmae().substring(0, RequiredFile.getFileNmae().lastIndexOf('.'));
 		 					String path="C:\\"+"Client_Id_"+ChatClient.ID+"\\"+fileNameWithoutExtension+"_Chunks\\"+fileNameWithoutExtension+"_encoded_Encrypted_"+RequiredFile.getChunk()+"."+fileExtension;
 		 					for(int i=0;i<ChatClient.ClientSockets.size();i++) {
 				    			 
 									if(ChatClient.ClientSockets.get(i).getMyIP().equals((String)InetAddress.getLocalHost().getHostAddress())) {
 										if(ChatClient.ClientSockets.get(i).getIP().equals(this.clientSocket.getInetAddress().getHostAddress())) {
 											try {
 												System.out.print("############################################################################");
 												ChatClient.ClientSockets.get(i).getSocket().sendFile(SendType.sendfile,path,ExistsFile.getChunksNum(),false);
 												 
 											} catch (InterruptedException e) {
 												// TODO Auto-generated catch block
 												e.printStackTrace();
 											}
 										}
 									 }
 						 
 							}
 						}
 					}
 				
 				}
 				else 
 				{
 					String filepath ="C:\\Client_Id_"+ChatClient.ID+"\\"+(String)object+"_Chunks\\";
 					File folder = new File(filepath);

 			        // Get a list of all the files in the folder
 			        File[] files = folder.listFiles();
 			       for (File file : files) {
 			    	  String filepath1 ="C:\\Client_Id_"+ChatClient.ID+"\\"+(String)object+"_Chunks\\"+file.getName() ;
 			    	//ChatClient.ClientSockets.get(i).getSocket().sendFile(filepath);
 			    	  //send file to the requested client
 			        }
 				}
 				
 				
 				
 				
 				
 				} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
 				}	}
    		
    		
    		
    		
    		/**
    		System.out.println("1");
	    	byte[] buffer = new byte[1024];
	    	InputStream inputStream = null;
			try {
				inputStream = clientSocket.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("2");
			 System.out.println("reading path");
	    	// Read the path of the file sent by Client1
	    	byte[] pathBytes = new byte[1024];
	        int pathBytesRead = -1;
	        System.out.println("3");
			try {
				while(pathBytesRead == -1) {
					try {
						Thread.sleep(5000);
						pathBytesRead = inputStream.read(pathBytes);
						System.out.println("4");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("5");
	        String filePath = new String(pathBytes, 0, pathBytesRead);
	        System.out.println("reading path : "+filePath);
	        // Receive the contents of the file
	    	FileOutputStream fileOutputStream = null;
	    	
	    	
	        int lastindex=filePath.lastIndexOf("\\");
	        String filename=filePath.substring(lastindex);
	        String FileName=filePath.substring(15,lastindex-7);
	        String FN=	"C:\\Client_Id_"+ChatClient.ID+"\\"+FileName+"_Chunks";
	        String filepath ="C:\\Client_Id_"+ChatClient.ID+"\\"+FileName+"_Chunks\\"+filename;
	        File directory = new File(FN) ;
	        if (!directory.exists())
	            directory.mkdirs();
			try {
				fileOutputStream = new FileOutputStream(filepath);
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("6");
	    	int bytesRead;
	    	    try {
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						try {
							fileOutputStream.write(buffer, 0, bytesRead);
							System.out.println("7");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	System.out.println("9");
	    	String IP = (String) clientSocket.getInetAddress().getHostAddress(); // IP of the chunck sender
	    	if(IP.equals(ChatClient.SharingIP)) {
	    		for(int i=0;i<ChatClient.ClientSockets.size();i++) {
	    			try {
						if(ChatClient.ClientSockets.get(i).getMyIP().equals((String)InetAddress.getLocalHost().getHostAddress())) {
							if(!ChatClient.ClientSockets.get(i).getIP().equals(ChatClient.SharingIP)) {
								try {
									ChatClient.ClientSockets.get(i).getSocket().sendFile(filepath);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						 }
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		 }
	    	} 
	    	System.out.println("7");
	    	try {
				fileOutputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.println("8");
    	
    	**/}
    	
    }
