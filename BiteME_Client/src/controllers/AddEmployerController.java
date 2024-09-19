package controllers;

import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *this class act as controller that waits for client actions on add employer interface
 * @author Ibraheem Sabaane
 *
 */
public class AddEmployerController implements Initializable {
    public static boolean added=false;
	
	@FXML
	private TextField Identity;

	@FXML
	private TextField Name;

	@FXML
	private Text success;

	/**
	 *this action check if the client has inserted the details correctly
	 * and then sends add employer message to the server
	 * else it will present an alert
	 * @param event this argument will be sent to the method after click on add button
	 */
	@FXML
	void add(ActionEvent event) {
		if (Identity.getText().equals("") || Name.getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Some details are missing!");
			a.setHeaderText("Error");
			a.showAndWait();
		}else {
			ClientUI.chat.accept(new Message1(MessageType.AddEmployer,Identity.getText()+" "+Name.getText()+" "+ChatClient.HR.getID()));
			success.setVisible(added);
			
		}

	}

	/**
	 * this action gets the primary stage of the current interface 
	 * and then initialize new parameter with type of HomeController
	 * then calling the start method of this parameter to run it's GUI
	 * (going back)
	 * @param event this argument will be sent to the method after click on back button
	 */
	@FXML
	void back(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();// get stage
		/*Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		HRHomeController HRHome = new HRHomeController();
		try {
			HRHome.start(stage);
		} catch (Exception e) {
			// TODO: handle exception
		}*/
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		success.setVisible(false);
		Name.setText(ChatClient.HR.getCompanyName());
		Name.setDisable(true);
	}

	/**
	 * * the method loading fxml file from view package to 'root' parameter and initialize new scene on this root
     * then setting the new scene to the primary stage we've got and finally showing the new stage
	 * @param primaryStage this argument has been sent to the method after clicking on a button that runs the following interface of this controller
	 * @throws Exception Exception the method supposed to throw an exception in case the fxml file does not exists
	 */
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/AddNewEmployer.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();

	}

}