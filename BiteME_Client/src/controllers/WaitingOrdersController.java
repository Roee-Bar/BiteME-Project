package controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


import client.ChatClient;
import client.ClientUI;
import common.OrdersForRes;
import common.Client;
import common.Message1;
import common.MessageType;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

///////////
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

//////////

/**
this class act as controller that waiting for clients actions on the waiting orders interface
 * and activate the relevant action accordingly 
 * @author ibraheem sabaane
 *
 */
public class WaitingOrdersController implements Initializable {
	public static OrdersForRes selctedOrder;
	public static boolean approvebool;
	/////////////////////////////////////////////////////////////////////////////////////
	 public static final String ACCOUNT_SID = "ACb6bc51f8ca05c4418ecf8b6d280e7768";
	    public static final String AUTH_TOKEN = "6d74c8692fccf8146744c7b9a3f3e436";
/////////////////////////////////////////////////////////////////////////////////////
	public static ObservableList<OrdersForRes> WaitingOrders;
	
	
	@FXML
    private TableColumn<OrdersForRes, String> Approval;

    @FXML
    private TableColumn<OrdersForRes, String> Total;

    @FXML
    private Button approvebtn;

    @FXML
    private TextField keyword;

    @FXML
    private TableColumn<OrdersForRes, Integer> orderNum;

    @FXML
    private TableView<OrdersForRes> orders_table;

    @FXML
    private Button pkgbtn;

    @FXML
    private TableColumn<OrdersForRes, String> time;

    /**
    * sends approve message to the server on order to update the order's status
     * if the server can not update it will show alert and the call initialize to hide approved orders
     * @param event
     */
    @FXML
    void Approve(ActionEvent event) {
    	if (orders_table.getSelectionModel().getSelectedItem() != null) {
			selctedOrder = orders_table.getSelectionModel().getSelectedItem();
			System.out.println(selctedOrder.getOrderNumber());
			ClientUI.chat.accept(
					new Message1(MessageType.approveOrder, selctedOrder.getOrderNumber()));
			if (!approvebool) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("this order already approved!");
				a.setHeaderText("Error");
				a.showAndWait();
			} else {
				/////////////////////////////////////
				ClientUI.chat.accept(new Message1(MessageType.getCustomer,selctedOrder.getCustomer_ID()));
				String number =("+972"+ChatClient.GetCustomerDetails.getPhoneNumber());
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			        Message message1 = Message.creator(
			                new com.twilio.type.PhoneNumber(number),
			                new com.twilio.type.PhoneNumber("+15739933793"),
			                "BiteMe Company:\n Thank you for ordering through our app\n"
			                + "Your order number '"+selctedOrder.getOrderNumber()+"' has been successfully received \n"
			                		+ "We'll send you when she's ready,Thanks").create();

			      //  System.out.println(message1.getSid());
				
				////////////////////////////////////

				initialize(null, null);
			}
		} else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("You have to select an order!");
			a.setHeaderText("Error");
			a.showAndWait();
		}
    }

  
    @FXML
    void LogOut(ActionEvent event) {
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
    this method takes the stage of the current interface and initialize a parameter with type of UpdateMenuListForResturantController
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
     *
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

    
    @FXML
    void back(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ResturantHomeController home = new ResturantHomeController();
		try {
			home.start(stage);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ClientUI.chat.accept(new Message1(MessageType.GetWaitingOrders, ChatClient.resturant));
		orderNum.setCellValueFactory(new PropertyValueFactory<OrdersForRes, Integer>("orderNumber"));
		time.setCellValueFactory(new PropertyValueFactory<OrdersForRes, String>("requestTime"));
		Total.setCellValueFactory(new PropertyValueFactory<OrdersForRes, String>("totalPrice"));
		Approval.setCellValueFactory(new PropertyValueFactory<OrdersForRes, String>("approvalStatus"));
		WaitingOrders=FXCollections.observableArrayList(ChatClient.WaitingOrders);
		orders_table.setItems(WaitingOrders);
		System.out.println(99);
		
	}
	
	
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/WaitingOrders.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
		
		

		
	}
		

}