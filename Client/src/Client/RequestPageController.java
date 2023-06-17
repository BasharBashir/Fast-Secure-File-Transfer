package Client;


import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import common.ClientFileInfo;
import common.ClientsPing;
import common.ExistsFile;
import common.Messages;
import common.MessagesClass;
import common.SendType;
import common.SocketManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RequestPageController implements Initializable{

    @FXML
    private ListView<String> listview;

    @FXML
    private ListView<String> clientlist;
    @FXML 
    private Button downloadbt;
    int NumberChunks;
    int CheckChunk;
    @FXML
    private TextField chunkarea;
    List<String> names =new ArrayList<>(Arrays.asList());                             
    @FXML
    private Button back;
    @FXML
    private Button loaddata;
String choosenFile;
    @FXML
    void backbt(ActionEvent event) { 
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ClientPage aFrame = new ClientPage();
		Stage primaryStage = new Stage();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		   
    }

    @FXML
    void downloadbt(ActionEvent event) throws UnknownHostException, InterruptedException {
        Double totalpingTime=0.0 ;
    	
		for(String name:ChatClient.clintsholdfile1.keySet())
		{
			 
		if(choosenFile.equals(name))
		{
			List<String>ipList = ChatClient.clintsholdfile1.get(name);
			for(String ip:ipList) {
				try {
					if(!ip.equals((String)InetAddress.getLocalHost().getHostAddress())) {
		                System.out.println("the ip  "+ip);
		                for(int i=0;i<ChatClient.ClientSockets.size();i++) {
		                	if(ChatClient.ClientSockets.get(i).getMyIP().equals((String)InetAddress.getLocalHost().getHostAddress()))
		                	{
		                		
		                		long startTime = System.currentTimeMillis();		                		
		                		long endTime;
		                		ChatClient.RequsetPing=false;
		                		 ChatClient.ClientSockets.get(i).getSocket().sendFile(SendType.sendping,"ping",0,false);
		                		 ChatClient.sem.acquire();
		                	     endTime = System.currentTimeMillis();		   
		                	     double responseTime =(double) (endTime - startTime) / 1000;
		                	     totalpingTime=(1/responseTime);
		                		 System.out.println("the ip that pinged: "+ip+" ping time is : "+responseTime);
		                		 ChatClient.ClientsPinglist.add(new ClientsPing(ip,responseTime,0));
		                	}
		                }
		                
		                

					}
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			 ClientUI.chat.accept(new MessagesClass(Messages.ClientsPerformance, ChatClient.ClientsPinglist));
			 Comparator<ClientsPing> comparator = new Comparator<ClientsPing>() {
			        
					@Override
					public int compare(ClientsPing o1, ClientsPing o2) {
						// TODO Auto-generated method stub
						return Double.compare(o1.getTime(),o2.getTime());	
						}
		        };
			 Collections.sort(ChatClient.ClientsPinglist,comparator);
			 long allchunks=0;
			for (ExistsFile i : ChatClient.finallistoffiles)
			{
				if(i.getFileName().equals(choosenFile))
				{
					for(ClientsPing y:ChatClient.ClientsPinglist)
					{
						y.setChunksnumber((Math.round(((1/y.getTime()/totalpingTime))*i.getChunksNum())));
						allchunks=allchunks+y.getChunksnumber();
						System.out.println("number of chunks :"+y.getChunksnumber());

					}
					
					if(allchunks!=i.getChunksNum())
					{
						long availblechunks=i.getChunksNum()-allchunks;
						System.out.println("number of chunks that still with no clients :"+availblechunks);
						ChatClient.ClientsPinglist.get(0).setChunksnumber(ChatClient.ClientsPinglist.get(0).getChunksnumber()+availblechunks);
					}
					 // NumberChunks=i.getChunksNum()/(i.getIpList().size()-1);
			    	 // CheckChunk=i.getChunksNum()%(i.getIpList().size()-1);
				}
			}

			
			//////send request chunks
			int k=1;
            //String FN=choosenFile.substring(0, choosenFile.lastIndexOf('.'));

			for(int j=0;j<ChatClient.ClientsPinglist.size();j++) {
				for(int i=0;i<ChatClient.ClientSockets.size();i++) {
	 	 			if(ChatClient.ClientSockets.get(i).getMyIP().equals((String)InetAddress.getLocalHost().getHostAddress())) {
	 	 				if(ChatClient.ClientSockets.get(i).getIP().equals(ChatClient.ClientsPinglist.get(j).getIp()))
	 	 				{
	 	 	 	 				for(int l=1;l<=ChatClient.ClientsPinglist.get(j).getChunksnumber();l++) {
	 	 			 				ChatClient.ClientSockets.get(i).getSocket().sendFile(SendType.RequestFile,choosenFile,k,false);
	 	 			 				k++; 
	 	 			 				SocketManager.semaphoreRequest.acquire();
	 	 			 				CheckChunk=0;
	 	 	 				}
	 	 	 				
	 	 				}
	 	 			}
			}
			
 	 			}
			 k=1;
			
		 
		}
		
		}
    	
    	
    	
    	
    	
    	
		 ChatClient.ClientsPinglist.clear();
    	
    }
    public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/ClientGUI/RequsetPage.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Request");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show(); 
		
	}
 
    
    @FXML
    void loaddata(ActionEvent event) {
    	ChatClient.finallistoffiles.clear();
    	if(ChatClient.clintsholdfile1!=null) {
			downloadbt.setVisible(true);
			loaddata.setVisible(true);
			ClientUI.chat.accept(new MessagesClass(Messages.GETDATA, null));
		if(!listview.getItems().isEmpty()) {
		      listview.getItems().clear();}
		   for (ExistsFile i : ChatClient.finallistoffiles) 
        {
			  
        	listview.getItems().addAll(i.getFileName());
			chunkarea.setText(i.getChunksNum()+"");
        }
		
		}
		
    	System.out.println(ChatClient.finallistoffiles);
		listview.setOnMouseClicked(mouseEvent -> {
			choosenFile=listview.getSelectionModel().getSelectedItem();
			 for (ExistsFile i : ChatClient.finallistoffiles) 
			 {
				 if(choosenFile.equals(i.getFileName()))
				    chunkarea.setText(i.getChunksNum()+"");
			 }
			     clientlist.getItems().clear();
				clientlist.getItems().addAll(ChatClient.clintsholdfile1.get(choosenFile));
	       	 });
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		downloadbt.setVisible(false);
		 
		 
		
	}
}
