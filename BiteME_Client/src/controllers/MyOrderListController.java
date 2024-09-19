package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import client.ChatClient;
import client.ClientUI;
import common.ItemList;
import common.Message1;
import common.MessageType;
import common.OrdersList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
* The MyOrderListController Controller implements an application that
* simply displays the information of the customer Packages.
*
* @author  Mohammed Egbaria
*/
public class MyOrderListController implements Initializable {

	public static OrdersList OL;
	public static OrdersList ItemSelected;

	public static String TimeAccepted;
	public static String ArrivelTime;
	public static Integer refund;
	public   long differenceInMilliSeconds ;
	public   long differenceInHours;
	public   long differenceInMinutes;
	public   Date TimeAccepted1;
	public  Date ArrivelTime1;
	@FXML
    private Button BackBtn;

    @FXML
    private TableColumn<OrdersList,String> ColAddress;

    @FXML
    private TableColumn<OrdersList,String> ColApproval;

    @FXML
    private TableColumn<OrdersList,String> ColArrivalTime;

    @FXML
    private TableColumn<OrdersList,String> ColPackageNumber;

    @FXML
    private TableColumn<OrdersList,String> ColPrice;

    @FXML
    private TableColumn<OrdersList,String> ColResturant;

    @FXML
    private TableColumn<OrdersList,String> ColService;

    @FXML
    private TableColumn<OrdersList,String> ColStatus;

    @FXML
    private TableColumn<OrdersList,String> ColTime;

    @FXML
    private TableView<OrdersList> OrderList1;

    @FXML
    private Button ViewOrderBtn;
    
    @FXML
    private Button AcceptBtn;
    
/////////////////////////////////////////////////////////////////////////////////////
public static final String ACCOUNT_SID = "ACb6bc51f8ca05c4418ecf8b6d280e7768";
public static final String AUTH_TOKEN = "6d74c8692fccf8146744c7b9a3f3e436";
/////////////////////////////////////////////////////////////////////////////////////
    
	
	@FXML
	void Home(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		CustomerHomeController CHC=new CustomerHomeController();
		try {
			CHC.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	void MakeNewOrder(ActionEvent event) {
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
	void OrderList(ActionEvent event) {
	
	}
	
	@FXML
	void SignOut(ActionEvent event) {
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
	 * Method for An action Event ,Clicking on "Order Accepted" button 
	 * The button is displayed when the package in unready yet 
	 * When the package is ready the button appeared 
	 * In this case the selected order set the the accepted time as the current time 
	 * And then calling initialize method to refresh
	 * @param event
	 */
    @FXML
    void AcceptedOrder(ActionEvent event) {
    
    	LocalDateTime now = LocalDateTime.now();
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");   	   
    	ItemSelected =OrderList1.getSelectionModel().getSelectedItem();
    	System.out.println(ItemSelected.getOrderPackageNumber()+" "+ItemSelected.getCustomer_ID());
		ClientUI.chat.accept(new Message1(MessageType.OrderListBuildEdit, 
				ItemSelected.getOrderPackageNumber()+" "+ItemSelected.getCustomer_ID()));
		Order_list.remove(ItemSelected);
		
		ArrivelTime=ItemSelected.getArrivalTime();
		TimeAccepted=dtf.format(now);
		try {
			TimeAccepted1=simpleDateFormat.parse(TimeAccepted);
			ArrivelTime1=simpleDateFormat.parse(ArrivelTime);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		refund=Integer.valueOf(ItemSelected.getTotalPrice())/2;
		if(TimeAccepted1.after(ArrivelTime1)) {
			ClientUI.chat.accept(new Message1(MessageType.RefundAdd,ItemSelected.getCustomer_ID()+" "+ItemSelected.getResturant()+" "+String.valueOf(refund)));
		  
			String number =("+972"+ChatClient.accounts.getPhoneNumber());
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	        Message message1 = Message.creator(
	                new com.twilio.type.PhoneNumber(number),
	                new com.twilio.type.PhoneNumber("+15739933793"),
	                "BiteMe Company:\n"
	                + "We are sorry for the delay\r\n"
	                + "You get a refund of "+refund+"$")
	            .create();
			
		}
		
		
		initialize(null, null);
		

    }

		public static ObservableList<OrdersList> Order_list = FXCollections.observableArrayList();
		
		
		/**
		 * Method for An action Event ,Clicking on Back button 
		 * to back from the OrderList to CutomerHome 
		 * @param event
		 * @throws Exception
		 */
	    @FXML
	    void BackAction(ActionEvent event) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
			CustomerHomeController CHC=new CustomerHomeController();
			try {
				CHC.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    /**
	     * Method for An action Event ,Clicking on "ViewOrderAction" button 
	     * This action views an alerts on the interface in case the client didn't selected a package
	     * else the selected package open an new view that shows the more details of the package
	     * @param event this arguments will be sent to the method after click on view order package
	     */
	    @FXML
	    void ViewOrderAction(ActionEvent event) {
	    	
	    	if(OrderList1.getSelectionModel().getSelectedItem()==null) {
	    		Alert a = new Alert(AlertType.ERROR);
   				a.setContentText("Error");
   				a.setHeaderText("Please Pick an Order Package First ");
   				a.showAndWait();	  
   				}
	    	else {
			//Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
	    		Stage stage=new Stage();
	    	ClientUI.chat.accept(new Message1(MessageType.ItemList, OL.getOrderPackageNumber()));
			ObservableList<ItemList> Item_List = FXCollections.observableArrayList(ChatClient.ItemBuild);
			ViewOrderController VOC=new ViewOrderController();
			try {
				VOC.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    }
	



	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/View/OrderList(ForCustomer).fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Customer Order List");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();

		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		AcceptBtn.setDisable(true);
	    ColAddress.setCellValueFactory(new PropertyValueFactory<OrdersList, String>("Address"));
		ColApproval.setCellValueFactory(new PropertyValueFactory<OrdersList, String>("ApprovalRecieving"));
		ColArrivalTime.setCellValueFactory(new PropertyValueFactory<OrdersList, String>("ArrivalTime"));
		ColPackageNumber.setCellValueFactory(new PropertyValueFactory<OrdersList, String>("OrderPackageNumber"));
		ColPrice.setCellValueFactory(new PropertyValueFactory<OrdersList, String>("TotalPrice"));
		ColResturant.setCellValueFactory(new PropertyValueFactory<OrdersList, String>("Resturant"));
		ColStatus.setCellValueFactory(new PropertyValueFactory<OrdersList, String>("Status"));
		ColService.setCellValueFactory(new PropertyValueFactory<OrdersList, String>("DeleiveryService"));
		ColTime.setCellValueFactory(new PropertyValueFactory<OrdersList, String>("OrderedDate"));
		ClientUI.chat.accept(new Message1(MessageType.OrderListBuild, ChatClient.userlogged.getId()));
		Order_list=FXCollections.observableArrayList(ChatClient.OrderBuild); 
		OrderList1.setItems(Order_list);
		OrderList1.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				OL=OrderList1.getSelectionModel().getSelectedItem();
			System.out.println(OL.getStatus());	
				if(OL.getStatus().equals("Ready")) 
					AcceptBtn.setDisable(false);		
				else
					AcceptBtn.setDisable(true);
				
			}
		});
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}