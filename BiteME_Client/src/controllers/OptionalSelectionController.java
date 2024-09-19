package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.ItemInCart;
import common.Message1;
import common.MessageType;
import common.Selection;
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
 * the Home with all the "OptionalSelectionController" 
 * @author Ashraf Zoube
 *
 */
public class OptionalSelectionController implements Initializable{

    ObservableList<Selection> Sel; //all the extras that we toke from DB   
    public static Integer totalPrice;   
    public static ArrayList<Selection> sel;  //list for extras 	
	public static Selection selection;
	public static ItemInCart AdditemList;
	
    @FXML
    private Button BackButton;

    @FXML
    private Text ChangeOp;

    @FXML
    private Button DoneBtn;

    @FXML
    private Text Optionaltxt;

    @FXML
    private Button Selectbutton;

    @FXML
    private Button UnSelectbutton;

    @FXML
    private ImageView image;

    @FXML
    private Button nextButton;

    @FXML
    private Text resturantnametxt;
    @FXML
    private TableView<Selection> OptionalSelectionList;

    @FXML
    private TableColumn<Selection,String> SelectionsCol;
    
    @FXML
    private TableColumn<Selection,Integer> PriceCol;
    /**
	 * Method for An action Event ,Clicking on back button 
	 * to back from the "Optional Selection" to "Dish" view
	 * @param event this arguments will be sent to the method after click on view order package
	 * @throws Exception
	 */
    @FXML
    void BackButtonAction(ActionEvent event) {
    	sel.clear();
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
    	DishController AFrame=new DishController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}
    }
    /**
     *The button for this action will show on  when there some order in the order list need to edit ingredients 
     * This action views an alerts on the interface in case the client didn't selected an item
     * This action will change the ingredient for the selected item 
     * @param event this arguments will be sent to the method after click on view order package
 	* @throws Exception
     */
    @FXML
	    	void Done(ActionEvent event) {
	    		ItemDetailsController.TotalPrice=totalPrice*ItemDetailsController.Quantity;
	        	for(int i=0;i<sel.size();i++)
	        		ItemDetailsController.TotalPrice=sel.get(i).getSelectionPrice()*ItemDetailsController.Quantity +ItemDetailsController.TotalPrice;
	    		if(OptionalSelectionController.sel.size()==0)
	    		{
	    			AdditemList =new ItemInCart (TybeMealController.tybe_meal.getTypeMeal(),DishController.dish.getDish()
	    					,"No Extra",ItemDetailsController.TotalPrice,ItemDetailsController.Quantity);
	    		}
	    		else 
	    		{
	    	    	AdditemList =new ItemInCart (TybeMealController.tybe_meal.getTypeMeal(),DishController.dish.getDish()
	    	     			,sel.toString(),ItemDetailsController.TotalPrice,ItemDetailsController.Quantity);
	    		}	 
	    		ItemDetailsController.itemList.add(AdditemList);
	    		ItemDetailsController.itemList.remove(MyCartController.ItemSelected);

	    		((Node) event.getSource()).getScene().getWindow().hide();// get stage
	    	  	MyCartController AFrame=new MyCartController();
	    		try {
	    			AFrame.start(MyCartController.stage1);
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch blocks
	    			e.printStackTrace();
	    		}
	    }
    /**
     *The button for this action will show up when there ingredient component selected  
     * This action views an alerts on the interface in case the client selected an ingredient that not selected before
     * This action will change the ingredient for the selected item 
     * @param event this arguments will be sent to the method after click on view order package
 	* @throws Exception
     */
    @FXML
    void UnSelectbuttonAction(ActionEvent event) {
    	if(OptionalSelectionList.getSelectionModel().getSelectedItem()!=null) {
    			if(sel.contains(selection)) { 
    				sel.remove(selection);
    				if(sel.size()==0)
    					UnSelectbutton.setVisible(false);
    				}
    		 	  else {
    		 		  Alert a = new Alert(AlertType.ERROR);
   	               a.setContentText("Error");
   	               a.setHeaderText("you didn't select this one yet");
   	               a.showAndWait();
    		 	  }
    			
    	}
    	else {
    		Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Error");
            a.setHeaderText("should you Select one you already selected:");
            a.showAndWait();
    	}
    }
    /**
     *The button for this action will show up when there ingredient component selected  
     * This action views an alerts on the interface in case the client selected an ingredient that not selected before
     * This action will change the ingredient for the selected item 
     * @param event this arguments will be sent to the method after click on view order package
 	* @throws Exception
     */
    @FXML
    void SelectButtonAction(ActionEvent event) {
    	if(OptionalSelectionList.getSelectionModel().getSelectedItem()!=null) {
    	               if(!sel.contains(selection))
    	                   {
    	                     sel.add(selection);
    	                     UnSelectbutton.setVisible(true);
    	                   }
    	               else {
    	               Alert a = new Alert(AlertType.ERROR);
    	               a.setContentText("Error");
    	               a.setHeaderText("you already selected it");
    	               a.showAndWait();
                   	}
    
    }
    	else {
    		Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Error");
            a.setHeaderText("should you Select one:");
            a.showAndWait();
    	}
    }
    /**
	 * Method for An action Event ,Clicking on next button 
	 * to go from the ingredient view to Items details view 
	 * @param event this arguments will be sent to the method after click on view order package
	 * @throws Exception
	 */

    @FXML
    void nextButtonAction(ActionEvent event) {
    	for(int i=0;i<sel.size();i++)
			totalPrice+=sel.get(i).getSelectionPrice();
        	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
        	ItemDetailsController AFrame=new ItemDetailsController();
    		try {
    			AFrame.start(stage);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}	
    		
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(MyCartController.Flag==true) {
			DoneBtn.setVisible(false);
			ChangeOp.setVisible(false);
			BackButton.setVisible(true);
			nextButton.setVisible(true);
			Optionaltxt.setVisible(true);

		}
		else {
			DoneBtn.setVisible(true);
			ChangeOp.setVisible(true);
			BackButton.setVisible(false);
			nextButton.setVisible(false);
			Optionaltxt.setVisible(false);
		}

		sel = new ArrayList<>();
		totalPrice=DishController.dish.getDishPrice();
		Selectbutton.setVisible(true);	
		if(sel.size()!=0)
	     	UnSelectbutton.setVisible(true);
		else
			UnSelectbutton.setVisible(false);
        resturantnametxt.setText(ChooseResturantController.resturant.getResturantName());
		SelectionsCol.setCellValueFactory(new PropertyValueFactory<Selection,String>("Selction"));
		PriceCol.setCellValueFactory(new PropertyValueFactory<Selection,Integer>("SelectionPrice"));
		ClientUI.chat.accept(new Message1(MessageType.ViewSelctionsList,DishController.dish.getDish_ID()));
		Sel=FXCollections.observableArrayList(ChatClient.selection);
		OptionalSelectionList.setItems(Sel);
		OptionalSelectionList.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if(OptionalSelectionList.getSelectionModel().getSelectedItem()!=null)
				selection=OptionalSelectionList.getSelectionModel().getSelectedItem();
				
			}
		});
		
	}

		public void start(Stage stage)  throws Exception {
		// TODO Auto-generated method stub
		
		Parent root = FXMLLoader.load(getClass().getResource("/View/OptionalSelection.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Optional Selection");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

}