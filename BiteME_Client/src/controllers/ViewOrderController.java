package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.ItemList;
import common.Message1;
import common.MessageType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class ViewOrderController implements Initializable {

	    @FXML
	    private Button BackBTN;

	    @FXML
	    private TableColumn<ItemList, String> ColIngredient;
	    @FXML
	    private TableColumn<ItemList, Integer> ColPrice;

	    @FXML
	    private TableColumn<ItemList, Integer> ColQuantity;

	    @FXML
	    private TableColumn<ItemList, String> ColTheDish;

	    @FXML
	    private TableColumn<ItemList, String> ColTheMeal;
	    
	    @FXML
	    private TableView<ItemList> Resturant;

		public static ObservableList<ItemList> Item_List = FXCollections.observableArrayList();

	    @FXML
	    private Text ResturantName;
	    
	    @FXML
	    void BackAction(ActionEvent event) {
			 ((Node) event.getSource()).getScene().getWindow().hide();// get stage

	    }


	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/View/PackageResturantOrder(Customer).fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Package");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
		
		
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ClientUI.chat.accept(new Message1(MessageType.getResName,MyOrderListController.OL.getResturant()));
		ResturantName.setText(ChatClient.res.getResturantName());
		ColTheMeal.setCellValueFactory(new PropertyValueFactory<ItemList, String>("TheMeal"));
		ColTheDish.setCellValueFactory(new PropertyValueFactory<ItemList, String>("TheDish"));
		ColIngredient.setCellValueFactory(new PropertyValueFactory<ItemList, String>("Ingredient"));
		ColPrice.setCellValueFactory(new PropertyValueFactory<ItemList, Integer>("Price"));
		ColQuantity.setCellValueFactory(new PropertyValueFactory<ItemList, Integer>("Quantity"));
		Item_List=FXCollections.observableArrayList(ChatClient.ItemBuild);
		Resturant.setItems(Item_List);

		

	}


}