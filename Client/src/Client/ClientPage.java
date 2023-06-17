package Client;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import common.ClientSockets;
import common.ExistsFile;
import common.Messages;
import common.MessagesClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
 
import javafx.scene.text.Text;
import javafx.stage.Stage;
 

/**
 * @author moham
 *
 */
public class ClientPage implements Initializable{
	public static ObservableList<String> files = FXCollections.observableArrayList();

	/**
	 * user text in order to put user name
	 */
	@FXML
    private ListView<String> fileslist;
	@FXML
	private TextField usertxt;
	/**
	 * status text error message
	 */
	@FXML
	private Text statustext;

	/**
	 * password text in order to put user password
	 */
	@FXML
	private PasswordField passwordtxt;

	/**
	 * @param event=action event of button exit
	 * @throws IOException this method to close login page when clicked in button
	 */
	@FXML
	void ExitBt(ActionEvent event) throws IOException {
		System.out.println("Close Client Page");
		ClientUI.chat.client.quit();
		for(ClientSockets ClientSockets:ChatClient.ClientSockets)
		{
			if(ClientSockets.getMyIP().equals((String)InetAddress.getLocalHost().getHostAddress()))
				 ClientSockets.getSocket().clientSocket.close();

		}
		System.exit(0);
	}

	@FXML
	void alertbt(ActionEvent event) {
		Alert alert = new Alert(AlertType.NONE, "", ButtonType.CLOSE);
		alert.setTitle("Message");
		alert.setContentText("");
		alert.getDialogPane().setPrefSize(400, 200);
		alert.showAndWait();
		// System.out.println(updatelist.toString());

	}

	

	
	@FXML
    void request(ActionEvent event) throws Exception {
		Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		RequestPageController LIF=new RequestPageController();
		Stage primaryStage = new Stage();
		LIF.start(primaryStage);
    }

    @FXML
    void share(ActionEvent event) throws Exception {
    	Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
		SharePage LIF=new SharePage();
		Stage primaryStage = new Stage();
		LIF.start(primaryStage);
    }
	/**
	 * @param primaryStage to open the qui
	 * @throws Exception start method
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/ClientGUI/ClientPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.setTitle("ClientPage Page");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	} 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
 		fileslist.getItems().clear();
		File folder = new File("C:\\"+"Client_Id_"+ChatClient.ID);
		files.clear();
        // Get a list of files in the folder
        File[] filesList = folder.listFiles();
 
        // Print the names of the files in the folder
        for (File file : filesList) {
            if (file.isFile()) {
           	 if(!file.getName().equals("history.txt"))
           		  if(!file.getName().equals("Client_Id_"+ChatClient.ID+"_info.txt"))
           	       files.add(file.getName());
            }
        }
			fileslist.getItems().addAll( files);
        
	}

        
	
}
