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
 *@author ibraheem sabaane
 * this class act as controller that waiting for client actions on the restaurant home interface
 *
 */
public class ResturantHomeController implements Initializable{

	@FXML
	private Text resturant_name_txt;
    /**
     * this action views an alert on the interface to make sure that the client really wants to logout
     * if yes it sends a logout message to the server by calling accept method 
     * add the server connect with the DB to update the "islogged" status of this user to '0'
     * @param event this argument has been sent to the method after click on logout button
     */
    @FXML
    void logOut(ActionEvent event) {
    	Alert alert = new Alert(AlertType.WARNING, 
                "Are you sure you want to logout?", 
                ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES){
		// ... user chose YES
			ClientUI.chat.accept(new Message1(MessageType.logout, ChatClient.userlogged));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			BiteMeLoginController biteMeLoginController=new BiteMeLoginController();
			try {
				biteMeLoginController.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
    }

    /**
     *this method takes the stage of the current interface and initialize a parameter with type of UpdateMenuListForResturantController
     * and then calling the start method of this parameter with the stage we got to run the new interface
     * @param event this argument has been sent to the method after click on menu setting button
     */
    @FXML
    void menuSettings(ActionEvent event) {
    	Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
    	UpdateMenuListForResturantController updateMenuListForResturantController= new UpdateMenuListForResturantController();
    	try {
			updateMenuListForResturantController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * this method takes the stage of the current interface and initialize a parameter with type of ViewOrdersController
     * and then calling the start method of this parameter with the stage we got to run the new interface
     * @param event this argument has been sent to the method after click on orders in progress button
     */
    @FXML
    void viewOrderList(ActionEvent event) {
    	Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
    	ViewOrdersController ViewOrders =new ViewOrdersController();
    	try {
    		System.out.println("try to run view interface");
    		ViewOrders.start(stage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	

    }
    
    
    @FXML
    void Home(ActionEvent event) {
    	Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
    	ResturantHomeController Home= new ResturantHomeController();
    	try {
			Home.start(stage);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    
    /**
     *this method takes the stage of the current interface and initialize a parameter with type of WaitingOrdersController
     *  and then calling the start method of this parameter with the stage we got to run the new interface
     * @param event this argument has been sent to the method after click on view waiting orders button
     */
    @FXML
    void WaitingOrders(ActionEvent event) {
    	Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
    	WaitingOrdersController waitingOrders = new WaitingOrdersController();
    	
    	try {
			waitingOrders.start(stage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	

    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println(resturant_name_txt.getText()+ChatClient.resturant.getResturantName());
		resturant_name_txt.setText("Hello,"+ChatClient.resturant.getResturantName()+"!");
	}
	
    public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/resturantHome.fxml"));

		Scene scene = new Scene(root);
		// primaryStage.setTitle("BiteMe");
		primaryStage.setScene(scene);

		primaryStage.centerOnScreen();

		primaryStage.show();

	}



}