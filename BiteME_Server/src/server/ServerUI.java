package server;

import controllers.ServerConnectionController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static EchoServer sv=new EchoServer(DEFAULT_PORT);

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {

		ServerConnectionController aFrame = new ServerConnectionController();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		aFrame.start(primaryStage);
	}

	public static void runServer(int p) {
		int port = 0; // Port to listen on

		try {
			port = p; // Set port to 5555

		} catch (Throwable t) {

			System.out.println("ERROR - Could not connect!");
		}

		sv = new EchoServer(port);
		System.out.println(2);
		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {

			System.out.println("ERROR - Could not listen for clients!");
		}
	}

}
