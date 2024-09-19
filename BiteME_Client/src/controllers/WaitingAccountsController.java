package controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Client;
import common.Message1;
import common.MessageType;
import common.waiting_account;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


/**
 *this class act as controller the waits for client's action on waiting accounts (business) interface
 * and will run after click on employees approval button in HR interface
 * @author ibraheem sabaane
 *
 */
public class WaitingAccountsController implements Initializable {
	
	public static ObservableList<waiting_account> WaitingAccounts;
	public static waiting_account selectedAccount; 

	
	
	 @FXML
	    private TableColumn<waiting_account, String> BranchCol;

	    @FXML
	    private TableColumn<waiting_account, Integer> Ceilingcol;

	    @FXML
	    private TableColumn<waiting_account, String> FirstName;

	    @FXML
	    private TableColumn<waiting_account, String> IDcol;

	    @FXML
	    private TableColumn<waiting_account, String> LastName;

	    @FXML
	    private TableColumn<waiting_account, String> Phonecol;

	    @FXML
	    private TableColumn<waiting_account, String> creditcol;

	    @FXML
	    private TextField keyword;

	    @FXML
	    private TableView<waiting_account> waitingtbl;
	

    
    
    
    /**
     * this action views an alert on the interface to make sure that the client really wants to logout
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
    
    @FXML
    void back(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	HRHomeController homeController=new HRHomeController();
    	try {
			homeController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void goToHome(ActionEvent event) {

    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	HRHomeController homeController=new HRHomeController();
    	try {
			homeController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
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
    /**
     * this action presents an alert if the client didn't choose witch account to approve
     * else it will send to the server  
     * @param event this argument will be sent to the method after click on approve button
     */
    @FXML
    void ApproveAccount(ActionEvent event) {
    	
    	if(waitingtbl.getSelectionModel().getSelectedItem()==null)
    	{
    		Alert a = new Alert(AlertType.ERROR);
			a.setContentText("You have to select an order!");
			a.setHeaderText("Error");
			a.showAndWait();
    	}else {
    		
    		selectedAccount= waitingtbl.getSelectionModel().getSelectedItem();
    		ClientUI.chat.accept(new Message1(MessageType.ApproveaccountB, selectedAccount));
    		initialize(null, null);
    		
    	}
    	

    }
	


	/**
	 *we overrode this method to initialize our GUI
	 *it's send get employees message to get them as array list
	 *and then set the variables in the table
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ClientUI.chat.accept(new Message1(MessageType.GetEmployees, ChatClient.HR.getCompanyName()));
		
		FirstName.setCellValueFactory(new PropertyValueFactory<waiting_account, String>("FirstName"));
		LastName.setCellValueFactory(new PropertyValueFactory<waiting_account, String>("LastName"));
		IDcol.setCellValueFactory(new PropertyValueFactory<waiting_account, String>("ID"));
		Phonecol.setCellValueFactory(new PropertyValueFactory<waiting_account, String>("PhoneNumber"));
		creditcol.setCellValueFactory(new PropertyValueFactory<waiting_account, String>("CreditCard"));
		Ceilingcol.setCellValueFactory(new PropertyValueFactory<waiting_account, Integer>("ceiling"));
		
		BranchCol.setCellValueFactory(new PropertyValueFactory<waiting_account, String>("Address"));
		


		
		
		
		WaitingAccounts=FXCollections.observableArrayList(ChatClient.WaitingAccounts);
		waitingtbl.setItems(WaitingAccounts);
		
	}
	
	
	
	
	
	
	
	
	
	/**
	 the method loading fxml file from view package to 'root' parameter and initialize new scene on this root
     * then setting the new scene to the primary stage we've got and finally showing the new stage
	 * @param primaryStage this argument has been sent to the method after clicking on a button that runs the following interface of this controller
	 * @throws Exception the method supposed to throw an exception in case the fxml file does not exists
	 */
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/WaitingAccounts.fxml"));
		
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();

	}
	
	
	
	

}