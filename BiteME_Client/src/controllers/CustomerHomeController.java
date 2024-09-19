package controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import client.ChatClient;
import client.ClientUI;
import common.Message1;
import common.MessageType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class CustomerHomeController implements Initializable {
    public static Stage stage1 ;
    @FXML
    private ImageView Cart;

    @FXML
    private Button Logoutbtn;

    @FXML
    private Button MakeAnOrderBTN;

    @FXML
    private Button MyCartButton;

    @FXML
    private Button MyOrderBTN;

    @FXML
    private Button MyOrderListBTN;

    @FXML
    private Text User;


    @FXML
    void Home(ActionEvent event) {

    }
    
    @FXML
    void NewOrder(ActionEvent event) {
    	MakeAnOrder(event);
    }
    
    
    @FXML
    void LogoutAction(ActionEvent event) {
    	ItemDetailsController.itemList.clear();
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
    
    @FXML
    void MyOrder(ActionEvent event) {
    	MyOrderList(event);
    }
    
    @FXML
    void MakeAnOrder(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	ScanerQrController AFrame=new ScanerQrController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void MyCartAction(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
   	 MyCartController AFrame=new MyCartController();
        try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void MyOrderList(ActionEvent event) {
       	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	MyOrderListController AFrame=new MyOrderListController();
		try {
			AFrame.start(stage); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public void start(Stage stage) throws Exception{
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("/View/HomeCustomerV2.fxml"));
		stage1=stage;
		Scene scene = new Scene(root);
		stage.setTitle("CoustomerHome");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		User.setText(ChatClient.userlogged.getUserName());
		if(ItemDetailsController.itemList.size()==0) {
			MyCartButton.setVisible(false);
		    Cart.setVisible(false);
		}
		else { MyCartButton.setVisible(true); Cart.setVisible(true);}
	}
}