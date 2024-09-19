package controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Message1;
import common.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * this class act as controller that waits for client's actions on Human resource manager home interface
 * and will run after logging in as HR user
 * @author ibraheem sabaane
 *
 */
public class HRHomeController implements Initializable {
	
	@FXML
    private Text HRWelcome;

    /**
     * this action gets the primary stage of the current window 
     * then initialize new parameter with Type of WaitingAccountsController
     * then runs the start method of this parameter
     * @param event this argument will be sent to the method after click on employees approval button
     */
    @FXML
    void GetEmployees(ActionEvent event) {
    	System.out.println(1);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	System.out.println(2);

    	WaitingAccountsController waitingcontroller = new WaitingAccountsController();
    	try {
			waitingcontroller.start(stage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	

    }

    /**
     * this action gets the primary stage of the current window 
     * then initialize new parameter with Type of AddEmployerController
     * then runs the start method of this parameter
     * @param event this argument will be sent to the method after click on employer registration button
     */
    @FXML
    void NewEmployer(ActionEvent event) {
    	Stage stage=new Stage();
    	AddEmployerController AddEmployer = new AddEmployerController();
    	
    	try {
			AddEmployer.start(stage);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	

    }

    @FXML
    void goToHome(ActionEvent event) {

    	
    }

    /**
     * *  this action views an alert on the interface to make sure that the client really wants to logout
     * if yes it sends a logout message to the server by calling accept method 
     * add the server connect with the DB to update the "islogged" status of this user to '0'
     * @param event this argument will be sent to the method after click on logout button
     */
    @FXML
    void logOut(ActionEvent event) {
    	Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			// ... user chose YES
			ClientUI.chat.accept(new Message1(MessageType.logout, ChatClient.userlogged));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			BiteMeLoginController biteMeLoginController = new BiteMeLoginController();
			try {
				biteMeLoginController.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	the method loading fxml file from view package to 'root' parameter and initialize new scene on this root
     * then setting the new scene to the primary stage we've got and finally showing the new stage
	 * @param primaryStage this argument has been sent to the method after clicking on a button that runs the following interface of this controller
	 * @throws Exception the method supposed to throw an exception in case the fxml file does not exists
	 */
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/HRHome.fxml"));

		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);

		primaryStage.centerOnScreen();

		primaryStage.show();

	}

}