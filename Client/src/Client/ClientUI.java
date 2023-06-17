package Client;
 
import javafx.application.Application;
import javafx.stage.Stage;


public class ClientUI extends Application {
	public static ClientController chat; //only one instance
	public static Connection aFrame;
	public static ClientPage a;

	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		//chat=new ClientController("localhost",5555);
		aFrame = new Connection();
		aFrame.start(primaryStage);		
	}
	
	
}
