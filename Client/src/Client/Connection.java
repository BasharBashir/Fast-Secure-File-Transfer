package Client;

import javafx.fxml.FXML; 
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
 
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.ClientSockets;
import common.ExistsFile;
import common.Messages;
import common.MessagesClass;
import common.SocketManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.io.*;
import java.net.*;
import java.net.*;
public class Connection implements Initializable{
	public static  String ip;
 
	public ClientController chat;
	@FXML
	private TextField IPAddress;
    public static File info;
    public static String InfoString;
	@FXML
	void Connect(ActionEvent event) throws Exception {
		ip = (String) IPAddress.getText();
		ClientUI.chat = new ClientController(ip, 5555);
		ClientUI.chat.accept(new MessagesClass(Messages.GetIPAddresses, null));
        System.out.println(ChatClient.ID+" the id number ");
 		// System.out.println(ChatClient.ClientAddresses.get(0).toString());
		//ClientUI.chat.accept(new MessagesClass(Messages.RequestPort, null));
		ClientUI.chat.accept(new MessagesClass(Messages.CheckPort, null));
		System.out.println("my port : "+ChatClient.port);

        ServerSocket serverSocket = new ServerSocket(ChatClient.port);         
		DataListener listener = new DataListener(serverSocket);
		Thread listenerThread = new Thread(listener);
		listenerThread.start();
 		if(ChatClient.ClientAddresses!=null)
			for(int i = 0; i < ChatClient.ClientAddresses.size();i++) {
				if(!ChatClient.ClientAddresses.get(i).equals((String)InetAddress.getLocalHost().getHostAddress())) {					
					//ask server
					ClientUI.chat.accept(new MessagesClass(Messages.RequestPort, ChatClient.ClientAddresses.get(i)));
					
					SocketManager manager = new SocketManager(ChatClient.ClientAddresses.get(i), ChatClient.Client_Port,3000);
					Thread thread = new Thread(manager);
					thread.start();
					//manager.sendFile(path); This is how to send file
					ChatClient.ClientSockets.add(new ClientSockets((String)InetAddress.getLocalHost().getHostAddress(),ChatClient.ClientAddresses.get(i),manager));
					for(int j = 0; j < ChatClient.ClientSockets.size();j++) {
						System.out.println(ChatClient.ClientSockets.get(j).getMyIP()+"     im in connection      "+ChatClient.ClientSockets.get(j).getIP());
					}
				  }
				}
			
 		File f = new File("C:\\"+"Client_Id_"+ChatClient.ID);
 		if(f.exists())
 		{
             f.delete() ;
 		}
 		f.mkdir();
 		info = new File("C:\\"+"Client_Id_"+ChatClient.ID+"\\"+"Client_Id_"+ChatClient.ID+"_info.txt");  
 		InfoString ="C:\\"+"Client_Id_"+ChatClient.ID+"\\"+"Client_Id_"+ChatClient.ID+"_info.txt";
 		if(!info.exists())
 		 info.createNewFile();
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		ClientPage LIF=new ClientPage();
		Stage primaryStage = new Stage();
		LIF.start(primaryStage);
	}
	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/ClientGUI/Connection.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("FirstPage");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			IPAddress.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
