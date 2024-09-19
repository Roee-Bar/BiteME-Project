	package controllers;
	
	import java.net.URL;
	import java.util.ArrayList;
	import java.util.ResourceBundle;
	import common.ItemInCart;
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.fxml.FXMLLoader;
	import javafx.fxml.Initializable;
	import javafx.scene.Node;
	import javafx.scene.Parent;
	import javafx.scene.Scene;
	import javafx.scene.control.Alert;
	import javafx.scene.control.Button;
	import javafx.scene.control.TextField;
	import javafx.scene.control.Alert.AlertType;
	import javafx.scene.image.ImageView;
	import javafx.scene.text.Text;
	import javafx.stage.Stage;
	/**
	* The ItemDetailsController Controller implements an application that
	* simply displays the first view of the home customer page to the stout output.
	*
	* @author  Mohammed Egbaria
	*/
	public class ItemDetailsController implements Initializable {
		
	public static ArrayList<ItemInCart> itemList =new ArrayList<>();
	
	
	
	public static ItemInCart AdditemList;
	
	public  static Integer TotalPrice;
	
	public static Integer Quantity;
	
	
	
	
	    @FXML
	    private ImageView image;
	
	    @FXML
	    private Button BackButton;
	
	    @FXML
	    private Button addButton;
	
	    @FXML
	    private Text resturantnametxt;
	
	    @FXML
	    private Text ItemDetailstxt;
	
	    @FXML
	    private Text tybemealtxt;
	
	    @FXML
	    private Text dishtxt;
	
	    @FXML
	    private Text extrastxt;
	
	    @FXML
	    private Text tybemealfield;
	    
	    @FXML
	    private Text Quantitytxt;
	
	    @FXML
	    private TextField QuantityField;
	
	    @FXML
	    private Text dishfield;
	
	    @FXML
	    private Text extrasField;
	    
	    @FXML
	    private Button DoneBtn;
	    /**
		 * Done: method to return the addButtonAction 
		 * @return addButtonAction
		 */
	    @FXML
	    void Done(ActionEvent event) {
	    	addButtonAction(event);
	    }
	    /**
		 * Method for An action Event ,Clicking on back button 
		 * to back from the "Items details" view  to "Optional Selection" view
		 * @param event
		 * @throws Exception
		 */
	    @FXML
	    void BackButtonAction(ActionEvent event) {
	    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
	    	OptionalSelectionController AFrame=new OptionalSelectionController();
			try {
				AFrame.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch blocks
				e.printStackTrace();
			}
	    }
	    /**
	     *The button for this action will show up when there selected item need to edit quantity  
	     * This action views an alerts on the interface in case the client selected an unpositive quantity
	     * This action will change the quantity for the selected item 
	     * @param event this arguments will be sent to the method after click on view order package
	 	* @throws Exception
	     */
	    @FXML
	    void addButtonAction(ActionEvent event) {// add to cart
	    //	long pid = ProcessHandle.current().pid();
	    	
	    		//	String.valueOf(pid); 
	    	
	    	if(QuantityField.getText().matches("[a-zA-Z_]+")||QuantityField.getText().equals("")) {
	    		Alert a = new Alert(AlertType.ERROR);
	            a.setContentText("Error");
	            a.setHeaderText("Quantity is Wrong!");
	            a.showAndWait();
	    	}
	    	else {
	    		Quantity=Integer.parseInt(QuantityField.getText());
	    		if(Quantity<=0) {
	        		Alert a = new Alert(AlertType.ERROR);
	                a.setContentText("Error");
	                a.setHeaderText("Enter Should Enter Quantity!");
	                a.showAndWait();
	    			return;
	
	    		}
	    	MyCartController.numberitem++;
	    	if(!MyCartController.Flag) {
	    		TotalPrice=MyCartController.ItemSelected.getTotalPrice()/MyCartController.ItemSelected.getQuantity()*Quantity;
	        	MyCartController.numberitem--;
	    		ItemDetailsController.itemList.remove(MyCartController.ItemSelected);
	    	}
	    	else {
	    	TotalPrice=OptionalSelectionController.totalPrice*Quantity;}
	 if(OptionalSelectionController.sel.size()!=0) {
		 
	    	AdditemList =new ItemInCart (TybeMealController.tybe_meal.getTypeMeal(),DishController.dish.getDish()
	    			,OptionalSelectionController.sel.toString(),TotalPrice,Quantity);
	 }
	 
	 else {  AdditemList =new ItemInCart (TybeMealController.tybe_meal.getTypeMeal(),DishController.dish.getDish()
	 			,"No Extra",TotalPrice,Quantity);
	 }
	
	 itemList.add(AdditemList);
	
	 
	 TotalPrice=0;
	 for(int i=0;i<itemList.size();i++)
	 {
		 TotalPrice+=itemList.get(i).getTotalPrice();
	 }
	    	
	    	OptionalSelectionController.totalPrice=0;
	   // Stage stage =   (Stage)
	    	((Node) event.getSource()).getScene().getWindow().hide();// get stage
	    	
	    	MyCartController AFrame=new MyCartController();
			try {
				AFrame.start(ChooseResturantController.stage1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	}
	    	
	    }
	
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			if(MyCartController.Flag==true) {
				DoneBtn.setVisible(false);
				BackButton.setVisible(true);
				addButton.setVisible(true);
	
			}
			else {
				DoneBtn.setVisible(true);
				BackButton.setVisible(false);
				addButton.setVisible(false);
			}
			resturantnametxt.setText(ChooseResturantController.resturant.getResturantName());
			tybemealfield.setText(TybeMealController.tybe_meal.getTypeMeal());		
			dishfield.setText(DishController.dish.getDish()+", Price:"+DishController.dish.getDishPrice());
					if(OptionalSelectionController.sel.size()==0)
				extrasField.setText("No Extras");
			
			else extrasField.setText(OptionalSelectionController.sel.toString());
			
		}
	
			public void start(Stage stage)  throws Exception {
			// TODO Auto-generated method stub
			
			Parent root = FXMLLoader.load(getClass().getResource("/View/ItemDetails.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Item Details");
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
	
	}