package controllers;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
* The ScanerQrController Controller implements an application that
* simply displays the information of the customer ScanQR.
*
* @author  Ashraf Zoube
*/
public class ScanerQrController implements Initializable {
	
	


    @FXML
    private ImageView Image1;

    @FXML
    private Button BackButton;

    @FXML
    private ImageView Image2;

    @FXML
    private ImageView Image4;

    @FXML
    private ImageView Image3;

    @FXML
    private Button scanButton;
    
    @FXML
    private Text Identify1;

    @FXML
    private Text Identify2;
    
    @FXML
    private TextField w4c_code;
    
    @FXML
    void Home(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	CustomerHomeController AFrame=new CustomerHomeController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

    @FXML
    void LogoutAction(ActionEvent event) {

		ItemDetailsController.itemList.clear();
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	BiteMeLoginController AFrame=new BiteMeLoginController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    /**
	 * Method for An action Event ,Clicking on Back button 
	 * to back from the ScannerQr to CustomerHome 
	 * @param event
	 * @throws Exception
	 */
    @FXML
    void BackButtonAction(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	CustomerHomeController AFrame=new CustomerHomeController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
	 * Method for An action Event ,Clicking on Scan button 
	 * @param event this arguments will be sent to the method after click on view order package
	 */
    @FXML
    void scanButtonAction(ActionEvent event) {
    	ClientUI.chat.accept(new Message1(MessageType.w4cCard,ChatClient.accounts.getW4C_QrCode()));
    	
    		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    		CustomerDetailsController AFrame=new CustomerDetailsController();
			try {
				AFrame.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    
    	

    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		w4c_code.setText(ChatClient.accounts.getW4C_QrCode());
		
	}
	
	public void start(Stage stage) throws Exception{
		// TODO Auto-generated method stub
		Parent root= FXMLLoader.load(getClass().getResource("/View/ScanerQr.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Scaner Qr Code Home");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	}

