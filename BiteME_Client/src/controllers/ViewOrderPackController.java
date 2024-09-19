package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Message1;
import common.MessageType;
import common.OrdersForRes;
import common.OrderDish;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewOrderPackController implements Initializable {
	
	public static ObservableList<OrderDish> dishes;
	
	  @FXML
	    private Button BackBTN;

	    @FXML
	    private TableColumn<OrderDish, String> ColIngredient;

	    @FXML
	    private TableColumn<OrderDish, Integer> ColPrice;

	    @FXML
	    private TableColumn<OrderDish, Integer> ColQuantity;

	    @FXML
	    private TableColumn<OrderDish, String> ColTheDish;

	    @FXML
	    private Text Number;

	    @FXML
	    private TableView<OrderDish> Resturant;

	    @FXML
	    private TableColumn<OrderDish, String> typecol;
	    
	    

	    @FXML
	    void BackAction(ActionEvent event) {
	    	ViewOrdersController ViewOrdersController = new controllers.ViewOrdersController();
	    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	
	    	try {
				ViewOrdersController.start(stage);
			} catch (Exception e) {
				// TODO: handle exception
			}
	    	

	    }
	
	
	    
	    
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//orderNum.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		ClientUI.chat.accept(new Message1(MessageType.GetOrdersDishes, ViewOrdersController.selctedOrder.getOrderNumber()));
		
		typecol.setCellValueFactory(new PropertyValueFactory<OrderDish, String>("mealType"));
		ColTheDish.setCellValueFactory(new PropertyValueFactory<OrderDish, String>("mealName"));
		ColIngredient.setCellValueFactory(new PropertyValueFactory<OrderDish, String>("optionalIngredients"));	
		ColQuantity.setCellValueFactory(new PropertyValueFactory<OrderDish, Integer>("Quantity"));
		ColPrice.setCellValueFactory(new PropertyValueFactory<OrderDish, Integer>("total"));
		
		
		//Orders = FXCollections.observableArrayList(ChatClient.orders);
		
		dishes= FXCollections.observableArrayList(ChatClient.OrdersDishes);
		Number.setText(String.valueOf(ViewOrdersController.selctedOrder.getOrderNumber()));
		
		Resturant.setItems(dishes);
		
		
	}
	
	
	
	
	
	
	
	
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/OrderPack.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();

	}
	

}