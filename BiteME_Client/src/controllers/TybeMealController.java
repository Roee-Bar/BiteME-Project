package controllers;


import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
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
 * the Home with all the "Type Meals" 
 * @author Mohammed Egbaria
 *
 */
public class TybeMealController  implements Initializable {

	ObservableList<TybeMeal> Meals;
	
	public static TybeMeal tybe_meal;
	
    @FXML
    private ImageView image;

    @FXML
    private Button ExistButton;

    @FXML
    private Button nextButton;

    @FXML
    private Text resturantnametxt;

    @FXML
    private Text TybeMealtxt;

    @FXML
    private TableView<TybeMeal> TypeMealList;

    @FXML
    private TableColumn<TybeMeal, String> TybeMealCol;
    /**
	 * Method for An action Event ,Clicking on exit button 
	 * to exit from the TypeMeal
	 * @param event this arguments will be sent to the method after click on view order package
	 * @throws Exception
	 */
    @FXML
    void ExistButtonAction(ActionEvent event) {
    //	Stage stage = (Stage)
    			((Node) event.getSource()).getScene().getWindow().hide();// get stage

    /*	ChooseResturantController AFrame=new ChooseResturantController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	  
    }
    /**
	 * Method for An action Event ,Clicking on next button 
	 * This action views an alerts on the interface in case the client didn't selected a TypeMeal
	 * to go from the TypeMeal to Dish view 
	 * @param event this arguments will be sent to the method after click on view order package
	 * @throws Exception
	 */
    @FXML
    void nextButtonAction(ActionEvent event) {
    	if(TypeMealList.getSelectionModel().getSelectedItem()!=null) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage

    	DishController AFrame=new DishController();
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
            a.setHeaderText("should you Select Your TybeMeal:");
            a.showAndWait();
    	}
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		resturantnametxt.setText(ChooseResturantController.resturant.getResturantName());
		
		TybeMealCol.setCellValueFactory(new PropertyValueFactory<TybeMeal,String>("TypeMeal"));
		ClientUI.chat.accept(new Message1(MessageType.ViewTybeMeallist,ChooseResturantController.resturant.getResturantID())); //// sending id resturant to get tybe meal list.
		Meals=FXCollections.observableArrayList(ChatClient.tybemeal);
		TypeMealList.setItems(Meals);
		 
		
		TypeMealList.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if(TypeMealList.getSelectionModel().getSelectedItem()!=null)
				tybe_meal=TypeMealList.getSelectionModel().getSelectedItem();
			}
		});
		
	
	}

	public void start(Stage stage)  throws Exception {
		// TODO Auto-generated method stub
		
		Parent root = FXMLLoader.load(getClass().getResource("/View/TybeMeal.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("TybeMeal");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}



}

