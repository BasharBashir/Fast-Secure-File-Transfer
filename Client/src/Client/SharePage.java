package Client;


import java.io.File; 
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import common.AESEncryption;
import common.ClientFileInfo;
import common.Decoder;
import common.Encoder;
import common.LZWCompressor;
import common.Messages;
import common.MessagesClass;
import common.SHA256HashOfFile;
import common.SendType;
import common.SocketManager;
import common.MergeFiles;
import javafx.scene.control.TextField;

 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
/**
 * @author Bashar Bashir Controller class for CEO home-page.
 */
public class SharePage implements Initializable {

	/**
	 * CEO Stage for home page
	 */
	 @FXML
	    private Text statustext;
	 
	 public static boolean flag =true;
	 
	    @FXML
	    private Button shareB;

	    @FXML
	    private Button backB; 
	    @FXML
	    private Text sizeText;

	    @FXML
	    private CheckBox CclientsCheck;
	    @FXML
	    private Text divdetext;
	    @FXML
	    private CheckBox GnumberCheck;

	    @FXML
	    private TextField numberchunks;

	    @FXML
	    private TextField filepath;
	    @FXML
	    private Button selectB;
 
	    public static  File file;
	    public static int ChunksNumber;

	    @FXML
	    void backB(ActionEvent event) {
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
	    void selectB(ActionEvent event) {
         FileChooser fs =new FileChooser();
        file=fs.showOpenDialog(null);
        if(file==null||!file.exists()) {
        	statustext.setText("The file is not exists");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText("Error");
            alert.setContentText("Select file");
            alert.showAndWait();
        }
        else {
         filepath.setText(file.getAbsolutePath());
         statustext.setText("");
         sizeText.setText(file.length()+"kb");
         divdetext.setVisible(true);
 		CclientsCheck.setVisible(true);
 		GnumberCheck.setVisible(true);
 		 numberchunks.setVisible(true);
  		
        }
         }
          
	    

	    

	    @FXML
	    void shareB(ActionEvent event) throws Exception {
	    	 
	    	ClientUI.chat.accept(new MessagesClass(Messages.NumberOfConnectedClient, null));
	    	String filename=file.toString().replace("\\", "\\\\");
	    	String newpath="C:\\"+"Client_Id_"+ChatClient.ID;
            File sourceFile = new File(filename);
            File destinationFolder = new File(newpath);
            File destinationFile = new File(destinationFolder, sourceFile.getName()); 
            if(!destinationFile.exists())
               Files.copy(sourceFile.toPath(), destinationFile.toPath());
	    	ChatClient.SecureHashDigest=SHA256HashOfFile.SHA256HashOfFile(filename);
	    	 
	    	 int NumberChunks = 0;
	    	 int CheckChunk = 0;
	    	 if(GnumberCheck.isSelected()) {
			        numberchunks.setVisible(true);
			        String number =numberchunks.getText();
			        ChunksNumber= SplitFile.split(filename,ChatClient.ID,Integer.parseInt(number));   
	    	 }
	    	 else
	    	 {
	    		 if(CclientsCheck.isSelected()) {
	 	    	  ChunksNumber= SplitFile.split(filename,ChatClient.ID,ChatClient.ConnectedcilintsNumber-1 );
	 	    	  
	    		 }
	    	 }
	    	// statustext.setText("The file has been shared with all connected clients");
	    	 NumberChunks=ChunksNumber/(ChatClient.ConnectedcilintsNumber-1);
	    	 CheckChunk=ChunksNumber%(ChatClient.ConnectedcilintsNumber-1);
	    	int dotIndex = file.getName().lastIndexOf(".");
	        String fileExtension = (dotIndex == -1) ? "" :  file.getName().substring(dotIndex + 1);
	    	int last=file.toString().lastIndexOf("\\");
	    	int last1=file.toString().lastIndexOf(".");
	    	String FileName=file.toString().substring(last+1);
	    	String FN=file.toString().substring(last+1,last1);
	    	ChatClient.SharingIP = (String) InetAddress.getLocalHost().getHostAddress();
	    	
	    	flag=false;
	    	
	    	ClientUI.chat.accept(new MessagesClass(Messages.FileName_FileChunks, new ClientFileInfo(FileName,ChunksNumber,(String)InetAddress.getLocalHost().getHostAddress(),ChatClient.DH_Key,ChatClient.SecureHashDigest)));
	    	
	    	 
            int k=1;
	    	for(int i=0;i<ChatClient.ClientSockets.size();i++) {
	    		//System.out.println(ChatClient.ClientSockets.get(i).getMyIP()+"        "+ChatClient.ClientSockets.get(i).getIP());
 	 			if(ChatClient.ClientSockets.get(i).getMyIP().equals((String)InetAddress.getLocalHost().getHostAddress())) {
 	 				if(CheckChunk!=0)
 	 				{
 	 	 				for(int j=1;j<=CheckChunk;j++) {
 	 	 					String path="C:\\"+"Client_Id_"+ChatClient.ID+"\\"+FN+"_Chunks\\"+FN+"_encoded_Encrypted_"+k+"."+fileExtension;
 			 				ChatClient.ClientSockets.get(i).getSocket().sendFile(SendType.sendfile,path,ChunksNumber,true);
 			 				k++;
 			 				
 			 				SocketManager.semaphoreSend.acquire();
 			 				CheckChunk=0;
 	 	 				}
 	 				}
 	 				for(int j=1;j<=NumberChunks;j++) {
	 					String path="C:\\"+"Client_Id_"+ChatClient.ID+"\\"+FN+"_Chunks\\"+FN+"_encoded_Encrypted_"+k+"."+fileExtension;
		 				ChatClient.ClientSockets.get(i).getSocket().sendFile(SendType.sendfile,path,ChunksNumber,true);
		 				k++;
		 	
		 				SocketManager.semaphoreSend.acquire();
 	 				}
 	 				}
	 		}
	    	k=1;
	   // String filePath=	MergeFiles.MergeFiles(path,FN);
	    	Alert alert =  new Alert(AlertType.NONE,
                    "default Dialog",ButtonType.OK);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText(" ");
            alert.setContentText("The has been encrypted by AES-256 ");
            alert.showAndWait();
	    	
	    	
	    	
	    	Alert alert1 =  new Alert(AlertType.NONE,
                    "default Dialog",ButtonType.OK);
            alert1.initStyle(StageStyle.UNDECORATED);
            alert1.setHeaderText(" ");
            alert1.setContentText("The file has been shared with all connected clients");
            alert1.showAndWait();
            
	    	  
	            
	    
	    }
	public void start(Stage primaryStage) throws Exception {
		Parent root1 = FXMLLoader.load(getClass().getResource("/ClientGUI/SharePage.fxml"));
		Scene scene = new Scene(root1);
		primaryStage.setTitle("Share");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show(); 
		
	}
	

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		divdetext.setVisible(false);
		CclientsCheck.setVisible(false);
		GnumberCheck.setVisible(false);
		 numberchunks.setVisible(false);
		 ClientUI.chat.accept(new MessagesClass(Messages.NumberOfConnectedClient, null));
		if(ChatClient.ConnectedcilintsNumber<=1)
		{
			shareB.setVisible(false);
		}
		
 	}
}