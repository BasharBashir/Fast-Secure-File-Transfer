package Server;

import java.time.LocalDateTime; 

import javafx.application.Application;
import javafx.stage.Stage;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static ServerPortFrameController aFrame;
	public static LocalDateTime OpeningDate;

	public static void main(String args[]) throws Exception {
		launch(args);
	}// end main

	@Override
	public void start(Stage primaryStage)  {
		aFrame = new ServerPortFrameController(); // create ServerFrame
		OpeningDate = LocalDateTime.now();

		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void runServer(int defaultPort) {
		int port = 0; // Port to listen on

		try {
			port = defaultPort; // Set port to 5555
		} catch (Throwable t) {
			System.out.println("ERROR - Could not connect!");
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

}