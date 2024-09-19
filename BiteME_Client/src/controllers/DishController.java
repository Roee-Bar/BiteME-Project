package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Dish;
import common.Message1;
import common.MessageType;
import common.Resturants;
import common.TybeMeal;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * TybeMealController :A controller implementing and showing 
 * the Home with all the "Dishs" 
 * @author Ashraf Zoube
 *
 */
public class DishController implements Initializable {

ObservableList<Dish> Dish;
	
	public static Dish dish;
	
    @FXML
    private ImageView image;

    @FXML
    private Button BackButton;

    @FXML
    private Button nextButton;

    @FXML
    private Text resturantnametxt;

    @FXML
    private Text TybeMealtxt;

    @FXML
    private TableView<Dish> DishList;

    @FXML
    private TableColumn<Dish, Integer> PriceCol;
    
    @FXML
    private TableColumn<Dish, String> DishCol;
    /**
	 * Method for An action Event ,Clicking on back button 
	 * to back from the Dish to TypeMeal view
	 * @param event this arguments will be sent to the method after click on view order package
	 * @throws Exception
	 */
    @FXML
    void BackButtonAction(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	TybeMealController AFrame=new TybeMealController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}
    }

    /**
	 * Method for An action Event ,Clicking on next button 
	 * This action views an alerts on the interface in case the client didn't selected a Dish
	 * to go from the Dish view to OptionalSelection view 
	 * @param event this arguments will be sent to the method after click on view order package
	 * @throws Exception
	 */
    @FXML
    void nextButtonAction(ActionEvent event) {
    	if(DishList.getSelectionModel().getSelectedItem()!=null) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	OptionalSelectionController AFrame=new OptionalSelectionController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
    	else {
    		Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Error");
            a.setHeaderText("should you Select Your Dish:");
            a.showAndWait();
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		resturantnametxt.setText(ChooseResturantController.resturant.getResturantName());
		
		DishCol.setCellValueFactory(new PropertyValueFactory<Dish,String>("Dish"));
		PriceCol.setCellValueFactory(new PropertyValueFactory<Dish,Integer>("DishPrice"));
		ClientUI.chat.accept(new Message1(MessageType.ViewDishList,TybeMealController.tybe_meal.getTybeMealID()));
		Dish=FXCollections.observableArrayList(ChatClient.dish);
		DishList.setItems(Dish);
		
		DishList.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if(DishList.getSelectionModel().getSelectedItem()!=null)
				dish=DishList.getSelectionModel().getSelectedItem();
			}
		});
	
	}

		public void start(Stage stage)  throws Exception {
		// TODO Auto-generated method stub
		
		Parent root = FXMLLoader.load(getClass().getResource("/View/Dish.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Dishes");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

}
