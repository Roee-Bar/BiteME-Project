package controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.DishForResturant;
import common.Message1;
import common.MessageType;
import common.OptionalIngredients;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditOptionalIngredientsController implements Initializable {

	public static ObservableList<OptionalIngredients> optionalIngredients;
	public static boolean AddOption;
	public static boolean Update1;

	@FXML
	private TextField mealName_txt;

	@FXML
	private TableView<OptionalIngredients> optionalIngredients_table;

	@FXML
	private TableColumn<OptionalIngredients, String> option_col;

	@FXML
	private TableColumn<OptionalIngredients, Integer> price_col;

	@FXML
	private TextField mealPrice_txt;

	@FXML
	private TextField price_txt;

	@FXML
	private TextField Option;

	@FXML
	private Text success;
	@FXML
	private Button updatebtn;

	/**
	 * this method takes the stage of the current interface and initialize a
	 * parameter with type of UpdateMenuListForResturantController then calling the
	 * start method of this parameter "back action"
	 * 
	 * @param event this argument will be sent to the method after click on back
	 *              button
	 */
	@FXML
	void back(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		UpdateMenuListForResturantController updateMenuListForResturantController = new UpdateMenuListForResturantController();
		try {
			updateMenuListForResturantController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * if the client inserted the details correctly the method will send add option
	 * message to the server then initialize the GUI again. else will present an
	 * alert on the interface
	 * 
	 * @param event this argument will be sent to the method after click on add
	 *              option button
	 */
	@FXML
	void addOption(ActionEvent event) {
		if (Option.getText().equals("") || price_txt.getText().equals("")) {
			System.out.println(UpdateMenuListForResturantController.selectedDish.getMealId());
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("You must add Option details");
			a.showAndWait();
		} else {
			///////////////////////////// (String option, int price, String dishID, int
			///////////////////////////// selectionID)
			OptionalIngredients option = new OptionalIngredients(Option.getText(), Integer.valueOf(price_txt.getText()),
					UpdateMenuListForResturantController.selectedDish.getMealId(), 0);

			ClientUI.chat.accept(new Message1(MessageType.AddOption, option));
			if (!AddOption) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Error");
				a.setHeaderText("this option already exists!");
				a.showAndWait();
			}
			initialize(null, null);

		}

	}

	/**
	 * this method check if the client has selected witch option to delete and the
	 * send to the server delete option message with the option details witch will
	 * be deleted from DB else will present an alert..
	 * 
	 * @param event this argument will be sent to the method after click on delete
	 *              option button
	 */
	@FXML
	void deleteItem(ActionEvent event) {
		if (optionalIngredients_table.getSelectionModel().getSelectedItem() == null) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Select what do you want to delete");
			a.setHeaderText("Error");
			a.showAndWait();
		} else {

			System.out.println(optionalIngredients_table.getSelectionModel().getSelectedItem().toString());
			ClientUI.chat.accept(new Message1(MessageType.DeleteOption,
					optionalIngredients_table.getSelectionModel().getSelectedItem()));

			initialize(null, null);
		}

	}

	/**
	 * this action views an alert on the interface to make sure that the client
	 * really wants to logout if yes it sends a logout message to the server by
	 * calling accept method add the server connect with the DB to update the
	 * "islogged" status of this user to '0'
	 * 
	 * @param event this argument will be sent to the method after click on logout
	 *              button
	 */
	@FXML
	void Disconnect(ActionEvent event) {
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
	 * this method takes the stage of the current interface and initialize a
	 * parameter with type of UpdateMenuListForResturantController and then calling
	 * the start method of this parameter with the stage we got to run the new
	 * interface
	 * 
	 * @param event this argument has been sent to the method after click on menu
	 *              setting button
	 */
	@FXML
	void menuSettings(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		UpdateMenuListForResturantController updateMenuListForResturantController = new UpdateMenuListForResturantController();
		try {
			updateMenuListForResturantController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * this method takes the stage of the current interface and initialize a
	 * parameter with type of ViewOrdersController and then calling the start method
	 * of this parameter with the stage we got to run the new interface
	 * 
	 * @param event this argument has been sent to the method after click on orders
	 *              in progress button
	 */
	@FXML
	void viewOrderList(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ViewOrdersController ViewOrders = new ViewOrdersController();
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
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ResturantHomeController Home = new ResturantHomeController();
		try {
			Home.start(stage);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * this method takes the stage of the current interface and initialize a
	 * parameter with type of WaitingOrdersController and then calling the start
	 * method of this parameter with the stage we got to run the new interface
	 * 
	 * @param event this argument has been sent to the method after click on view
	 *              waiting orders button
	 */
	@FXML
	void WaitingOrders(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		WaitingOrdersController waitingOrders = new WaitingOrdersController();

		try {
			waitingOrders.start(stage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * @param event
	 */
	@FXML
	void update(ActionEvent event) {
		// Dish(String resturantId, String mealType, String mealName, String
		// optionalIngredients, String mealId,int price)
		StringBuilder sb = new StringBuilder();
		for (OptionalIngredients a : optionalIngredients) {
			sb.append(a.getOption());
			sb.append(",");
			sb.append(a.getPrice());
			sb.append("\n");

		}

		System.out.println("wow");
		DishForResturant dish = new DishForResturant(ChatClient.resturant.getId(), null, mealName_txt.getText(),
				sb.toString(), UpdateMenuListForResturantController.selectedDish.getMealId(),
				Integer.parseInt(mealPrice_txt.getText()));

		ClientUI.chat.accept(new Message1(MessageType.UpdateItem, dish));

		if (Update1 == true) {
			success.setVisible(Update1);
		} else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Can't update this item please try again!");
			a.setHeaderText("Error");
			a.showAndWait();
		}
	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/EditOptionalIngredients.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ClientUI.chat.accept(
				new Message1(MessageType.getOptionalIngredients, UpdateMenuListForResturantController.selectedDish));
		option_col.setCellValueFactory(new PropertyValueFactory<OptionalIngredients, String>("option"));
		price_col.setCellValueFactory(new PropertyValueFactory<OptionalIngredients, Integer>("price"));
		optionalIngredients = FXCollections.observableArrayList(ChatClient.optionalIngredients);
		optionalIngredients_table.setItems(optionalIngredients);

		mealName_txt.setText(UpdateMenuListForResturantController.selectedDish.getMealName());
		mealPrice_txt.setText(String.valueOf(UpdateMenuListForResturantController.selectedDish.getPrice()));
		success.setVisible(false);
		updatebtn.setDisable(true);
		mealName_txt.textProperty().addListener((observable, oldValue, newValue) -> {
			updatebtn.setDisable(false);
		});
		mealPrice_txt.textProperty().addListener((observable, oldValue, newValue) -> {
			
			updatebtn.setDisable(false);
			if (!newValue.matches("\\s0-9*")) {
				//newValue = newValue.replaceAll("[^\\s0-9]", "");
				mealPrice_txt.setText(newValue.replaceAll("[^\\s0-9]", ""));
			}

		});
		
	}

}
