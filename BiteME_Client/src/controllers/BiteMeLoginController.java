package controllers;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author mblac
 *
 */
public class BiteMeLoginController implements Initializable {
	public static boolean isCEO;

	@FXML
	private TextField Username;

	@FXML
	private PasswordField Password;

	@FXML
	private Button Loginbtn;

	@FXML
	private Button Loginbtn1;

	/**
	 * when this action executed an alert is displayed if the client press yes 
	 * the server disconnects
	 * @param event  this argument will be sent to the method after clicking on
	 *        disconnect button
	 */
	

    @FXML
    void close(ActionEvent event) {
		Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			InetAddress ip = null;
			try {
				ip = InetAddress.getLocalHost();
				// System.out.println(ip.getCanonicalHostName());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ClientUI.chat.accept(new Message1(MessageType.disconnect,
					ip.getHostAddress() + " " + ip.getHostName() + " " + "Disconnected"));
			ClientController.client.quit();
			System.exit(0);

		}
    }

	
	/**
	 * this action displays an alert if any of the text boxes is empty
	 * else it opens the correct user interface (customer, hr, branch manager, sulpplier,ceo)
	 * @param event this argument will be sent to the method after clicking on
	 *        login button
	 */
	@FXML
	void LoginAction(ActionEvent event) {

		if (Username.getText().equals("") || Password.getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("Enter username and password");
			a.showAndWait();
		} else {

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
			ClientUI.chat.accept(new Message1(MessageType.login, Username.getText() + " " + Password.getText()));

			if (ChatClient.userlogged != null) {
				if (ChatClient.userlogged.isLoggedIn()) {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("user is logged in");
					a.setHeaderText("Error");
					a.showAndWait();
				} else {
					switch (ChatClient.userlogged.getType()) {
					case Customer:
						ClientUI.chat.accept(new Message1(MessageType.scan, ChatClient.userlogged.getId()));
						if (ChatClient.userlogged.getStatus().equals("Frozen")) {
							Alert a = new Alert(AlertType.ERROR);
							a.setContentText("Error");
							a.setHeaderText("this Account is Frozen");
							a.showAndWait();
						} else {
							CustomerHomeController AFrame = new CustomerHomeController();
							try {
								AFrame.start(stage);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					case HR:
						HRHomeController hr = new HRHomeController();
						try {
							hr.start(stage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case BranchManager:
						ManagerHomeController managerHomeController = new ManagerHomeController();
						try {
							managerHomeController.start(stage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case Supplier:
						ResturantHomeController resturantHomeController = new ResturantHomeController();
						try {
							resturantHomeController.start(stage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case CEO:
						CEOHomeController.ceoID = ChatClient.userlogged.getId();
						CEOHomeController ceoHomeController = new CEOHomeController();
						try {
							ceoHomeController.start(stage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;

					default:
						Alert a = new Alert(AlertType.ERROR);
						a.setContentText("you dont have access for the system");
						a.setHeaderText("Error");
						a.showAndWait();
						break;
					}
				}

			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Error");
				a.setHeaderText("Your username or password is wrong");
				a.showAndWait();
			}
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ChatClient.userlogged = null;
		BiteMeLoginController.isCEO=false;

	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/BiteMeLogin.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setTitle("BiteMeLogin");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

}
