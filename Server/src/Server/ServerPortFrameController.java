package Server;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;  
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import ServerGUI.*;
import common.ClientFileInfo;
import common.DiffieHellman;
import common.ExistsFile;
import common.clientDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class ServerPortFrameController implements Initializable {
	private URL location;
	private ResourceBundle resources;
	static EchoServer ech;
	private Label Host = new Label();
	private Label IP = new Label();
	private Label Status = new Label();
	private Label Hosttxt = new Label();
	private Label IPtxt = new Label();
	private Label Statustxt = new Label();
	public static boolean c = false;
	@FXML
	private Button extebt;

	@FXML
	private Button startserverbt;
	static ArrayList<String> DBInfo =new ArrayList<>();
	@FXML
	private Button importdatabt;
    @FXML
    private Text startText;
	@FXML
	private TextField serverip;
	public static ObservableList<ExistsFile> ExistsFile = FXCollections.observableArrayList();

	@FXML
	private TextField DBtable;

	@FXML
	private TextField Bitemetable;

	@FXML
	private TextField DBpassword;
	@FXML
	TableView<clientDetails> tableServer;
	@FXML
	private TableColumn<clientDetails, String> colHostName;
	@FXML
	private TableColumn<clientDetails, String> colIP;
	@FXML
	private TableColumn<clientDetails, String> colStatus;
	@FXML
    private TableView<ClientFileInfo> filetableview;

    @FXML
    private TableColumn<ClientFileInfo, String> filename;
	public static HashMap<String, List<String>> clintsholdfile = new HashMap<String, List<String>>();

    @FXML
    private TableColumn<ClientFileInfo, Integer> chunksnumber;

	public static String key;
	private static ObservableList<clientDetails> clients = FXCollections.observableArrayList();
	public static ObservableList<ClientFileInfo> ClientsFile = FXCollections.observableArrayList();
 	private static ObservableList<ClientFileInfo> ClientsFileSelected=FXCollections.observableArrayList();;
 	public String StatuseOfPrevriosClient;
	public void start(Stage primaryStage) throws Exception {
		IP.setText("IP: ");
		Host.setText("Host: ");
		Status.setText("Status: ");
		Hosttxt.setText(" ");
		IPtxt.setText(" ");
		Statustxt.setText(" ");

		FXMLLoader loader = new FXMLLoader();

		Parent root = loader.load(getClass().getResource("/ServerGUI/serverFXML.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static HashMap<String, List<String>> readFromFile(String filename) throws IOException {
    	HashMap<String, List<String>> dataList = new HashMap<String, List<String>>();
    	 List<String> ip = new ArrayList<>(Arrays.asList());
    	 String fileName = null;
    	 String numberchunks;
    	 String key;
    	 String hashcode;
        // Open the file and read each line
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        	  String line;
        	  int i=1;
        	while ((line = br.readLine()) != null) {
        		ip.clear();
        	String[] parts = line.split("->");
        	  fileName = parts[0];
        	  
	
        	 String[] arrayData = parts[1].replaceAll("[\\[\\] ]", "").split(",");
        	 
        	 numberchunks=parts[2];
        	 key=parts[3];
        	 hashcode=parts[4];
            for (String part : arrayData) {
            	ip.add((part));
            }
            ExistsFile.add(new ExistsFile(fileName,ip,Integer.parseInt(numberchunks),key,hashcode));
            dataList.put(fileName, ip);
        	}
                // Create a new DataLine object and add it to the ArrayList
             
            
        }

        return dataList;
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	 
		this.location = location;
		this.resources = resources;
		try {
			serverip.setText(InetAddress.getLocalHost().getHostAddress().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 
		colHostName.setCellValueFactory(new PropertyValueFactory<clientDetails, String>("hostName"));
		colIP.setCellValueFactory(new PropertyValueFactory<clientDetails, String>("ip"));
		colStatus.setCellValueFactory(new PropertyValueFactory<clientDetails, String>("Status"));
		tableServer.setItems(clients);
		
		tableServer.setOnMouseClicked(mouseEvent -> {
			 
				
			
			//for(String namefile:ExistsFile)
			//{
				
			//}
			
			
			
			
			
			
			
			//for(ClientFileInfo f:ClientsFile)
			//{
			//	if(	tableServer.getSelectionModel().getSelectedItem().getIp().equals(f.getIpAddress())) {
		     //   ClientsFileSelected = FXCollections.observableArrayList();
				//ClientsFileSelected.add(f);}
					
				
			//}
	       	 if(mouseEvent.getClickCount() == 2){
	       		try {
					clintsholdfile= readFromFile("history.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       		filetableview.getItems().clear();
	       		ClientsFileSelected.clear();
	       		for(int j=0;j<ExistsFile.size();j++) {
					for( String lst:ExistsFile.get(j).getIpList())
					if(	tableServer.getSelectionModel().getSelectedItem().getIp().equals(lst)) {
				        ClientFileInfo ClientFileInfo=new ClientFileInfo(ExistsFile.get(j).getFileName(),ExistsFile.get(j).getChunksNum(),lst); 
						ClientsFileSelected.add(ClientFileInfo);}}
	       		filename.setCellValueFactory(new PropertyValueFactory<ClientFileInfo, String>("FileName"));
	       		chunksnumber.setCellValueFactory(new PropertyValueFactory<ClientFileInfo, Integer>("FileChunks"));
	       		filetableview.setItems(ClientsFileSelected);
	       	 }});
		 
	}

	public void startServerBtn(ActionEvent event) throws Exception {
		ServerUI.runServer(ServerUI.DEFAULT_PORT);
		//ech = new EchoServer(5555);
		startText.setText("Server is listening on port "+ServerUI.DEFAULT_PORT);
		extebt.setDisable(false);
		DiffieHellman Dh=new DiffieHellman();
		key =Dh.stepTwo(Dh.stepOne());
		 
		 
 	}

	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Close Server");
		System.exit(0);
	}

	public void UpdateClient(InetAddress Host, String IP, String Status) {

		javafx.application.Platform.runLater(new Runnable() {
			@Override
			public void run() {
				clientDetails client = new clientDetails(Host.toString(), IP, Status);

				if(Status.equals("Disconnected"))
				{
					for(int i=0;i<clients.size();i++)
						if(clients.get(i).getIp().equals(IP))
							{clients.remove(i);
						    clients.add(i, client);
							}
					
				
				}
				else
				clients.add(client);
				
			
				
			}
		});
	}

	public void UpdateClientFile(String filename,int chunknumber,String ip)
	{
		ClientFileInfo ClientFileInfo=new ClientFileInfo(filename,chunknumber,ip);
		ClientsFile.add(ClientFileInfo);
		
	}
	public void disconnect() {
		tableServer.getItems().clear();
	}
}