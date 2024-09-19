package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
* The CustomerDetailsController Controller implements an application that
* simply displays the information of the customer.
*
* @author  Ashraf Zoube
*/
public class CustomerDetailsController implements Initializable {

    @FXML
    private ImageView Image;

    @FXML
    private Button Exitbutton;

    @FXML
    private Button ConfirmButton;

    @FXML
    private Text IDtxt;

    @FXML
    private Text FirstNametxt;

    @FXML
    private Text LastNametxt;

    @FXML
    private Text PhoneNumbertxt;

    @FXML
    private Text Emailtxt;

    @FXML
    private Text Credittxt;

    @FXML
    private Text AccountTybetxt;

    @FXML
    private Text FirstNameField;

    @FXML
    private Text LastNameField;

    @FXML
    private Text IDField;

    @FXML
    private Text PhoneNumberField;

    @FXML
    private Text EmailField;

    @FXML
    private Text AccountField;

    @FXML
    private Text CreditField;

    @FXML
    private Text YourDetailstxt;
    
    @FXML
    private Text LocationFiled;

    @FXML
    private Text LocationTxt;
    
    
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
     * Method for An action Event ,Clicking on "Confirm" button 
     * @param event this arguments will be sent to the method after click on view order package
     */
    @FXML
    void ConfirmButtonAction(ActionEvent event) {
    	
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	ChooseResturantController AFrame=new ChooseResturantController();
    	
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
	 * Method for An action Event ,Clicking on Back button 
	 * to back from the CustomerDetails to scan 
	 * @param event
	 * @throws Exception
	 */
    @FXML
    void ExitbuttonAction(ActionEvent event) {
    	
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	ScanerQrController AFrame=new ScanerQrController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		YourDetailstxt.setText(ChatClient.userlogged.getUserName()+" Details");
		FirstNameField.setText(ChatClient.accounts.getFirstName());
		LastNameField.setText(ChatClient.accounts.getLasName());
		IDField.setText(ChatClient.accounts.getID());
		PhoneNumberField.setText(ChatClient.accounts.getPhoneNumber());
		EmailField.setText(ChatClient.accounts.getEmail());
		CreditField.setText(ChatClient.w4ccard.getCreditCardNumber());
		AccountField.setText(ChatClient.w4ccard.getAccountType());
		LocationFiled.setText(ChatClient.accounts.getLocation());
	}
	
	public void start(Stage stage) throws Exception{
		// TODO Auto-generated method stub
		Parent root= FXMLLoader.load(getClass().getResource("/View/CustomerDetails.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Coustomer Details");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
		
	}

}