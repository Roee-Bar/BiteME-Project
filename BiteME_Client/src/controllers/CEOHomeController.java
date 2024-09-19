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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * this class act as a controller that waits for client's action on
 * CEO home interface and will run after logging in as CEO user
 * @author Majd Msallam
 *
 */
public class CEOHomeController implements Initializable{

	public static String ceoID;
    @FXML
    private Button Loginbtn;


    /**
	 * this action displays an alert, if answer is yes it gets the primary stage
	 * then initialize new parameter with type of BiteMeLoginController
	 * then runs the start method of this parameter,if no nothing is done
     * @param event this argument will be sent to the method after clicking on
	 *        logout button
     */
    @FXML
    void logoutAction(ActionEvent event) {
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

    /**
     * this action gets the primary stage of the current window
	 * then initialize new parameter with type of ViewManagerQuarterlyReportsController
	 * then runs the start method of this parameter
     * @param event this argument will be sent to the method after clicking on
	 *        view manager quarterly reports button
     */
    @FXML
    void viewManagerQuarterlyReports(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	ViewManagerQuarterlyReportsController viewManagerQuarterlyReportsController=new ViewManagerQuarterlyReportsController();
    	try {
			viewManagerQuarterlyReportsController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * this action gets the primary stage of the current window
	 * then initialize new parameter with type of ViewManagerQuarterlyReportsController
	 * then runs the start method of this parameter
     * @param event this argument will be sent to the method after clicking on
	 *        view manager reports button
     */
    @FXML
    void viewManagerReports(ActionEvent event) {
       	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	ViewCEOManagerReportsController viewCEOManagerReportsController=new ViewCEOManagerReportsController();
    	try {
    		viewCEOManagerReportsController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * this action gets the primary stage of the current window
	 * then initialize new parameter with type of LastQuareterHistogramController
	 * then runs the start method of this parameter
     * @param event this argument will be sent to the method after clicking on
	 *        view quarterly histogram button
     */
    @FXML
    void viewQuarterlyHistogram(ActionEvent event) {

    	Stage stage=new Stage();
    	LastQuareterHistogramController lastQuareterHistogramController=new LastQuareterHistogramController();
    	try {
			lastQuareterHistogramController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/CEOHome.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		primaryStage.centerOnScreen();

		primaryStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ChatClient.userlogged.setId(ceoID);
		BiteMeLoginController.isCEO=true;
	}

}
