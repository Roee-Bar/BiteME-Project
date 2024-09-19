
package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientController;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientConnectionController implements Initializable {

	@FXML
	private Button connect_btn;

	@FXML
	private TextField ip_txt;

	@FXML
	void connect(ActionEvent event) {


		ClientUI.chat = new ClientController(ip_txt.getText(), ClientController.DEFAULT_PORT);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		BiteMeLoginController biteMeLoginController=new BiteMeLoginController();
		try {
			biteMeLoginController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ip_txt.setText("localhost");
	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/ClientIPConnection.fxml"));

		Scene scene = new Scene(root);
		
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
}
