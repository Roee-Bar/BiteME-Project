package client;
import controllers.BiteMeLoginController;
import controllers.ClientConnectionController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class ClientUI extends Application {
	public static ClientController chat; //only one instance

	public static void main( String args[] ) throws Exception
	   { 
		
		    launch(args);  
	   } // end main 
	
	
	 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
						  		
		ClientConnectionController aFrame=new ClientConnectionController();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		aFrame.start(primaryStage);
	}
	
	
}
