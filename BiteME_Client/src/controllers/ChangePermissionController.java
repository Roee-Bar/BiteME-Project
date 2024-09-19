package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.SingleSelectionModel;

import client.ChatClient;
import client.ClientUI;
import common.Employer;
import common.Message1;
import common.MessageType;
import common.Users;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * this class act as a controller that waits for client's action on 
 * change permission interface and will run after clicking on change permission
 * on the manager home page interface
 * @author Basel Kabia
 *
 */
public class ChangePermissionController implements Initializable {

	public static ObservableList<Users> users;
	@FXML
	private TableView<Users> users_tbl;

	@FXML
	private TableColumn<Users, String> userID_col;

	@FXML
	private TableColumn<Users, String> UserName_col;

	@FXML
	private TableColumn<Users, String> UserType_col;

	@FXML
	private TableColumn<Users, String> Status_col;

	@FXML
	private TextField searchbar;

	@FXML
	private ComboBox<String> status;
	@FXML
	private Button delete_btn;

	
	   /**
     * this action saves the id of the user we want to delete and the branch manager 
     * who deleted him and then update the data in the database
     * @param event  this argument will be sent to the method after clicking on
	 *        delete button
     */

    @FXML
    void deleteUser(ActionEvent event) {
    	ArrayList<String> arr = new ArrayList<String>();
		arr.add(users_tbl.getSelectionModel().getSelectedItem().getId());
		arr.add(ChatClient.userlogged.getId());
		ClientUI.chat.accept(new Message1(MessageType.deleteCustomer, arr));
		initialize(null, null);
		users_tbl.refresh();
    }


    /**
	 * this action saves the id of the user we want to change his status
	 * then update the data in the database
	 * @param event  this argument will be sent to the method after clicking on
	 *       status combo box and choosing status 
	 */
	@FXML
	void changeStatus(ActionEvent event) {
		if (!users_tbl.getSelectionModel().getSelectedItem().getStatus()
				.equals(status.getSelectionModel().getSelectedItem())) {
			ArrayList<String> arr = new ArrayList<String>();
			arr.add(users_tbl.getSelectionModel().getSelectedItem().getId());
			arr.add(status.getSelectionModel().getSelectedItem());
			ClientUI.chat.accept(new Message1(MessageType.changePermission, arr));
		}
		initialize(null, null);
		users_tbl.refresh();
	}

	/**
	 * this action gets the primary stage of the current window
	 * then initialize new parameter with type of ManagerHomeController
	 * then runs the start method of this parameter
	 * @param event this argument will be sent to the method after clicking on
	 *        return button
	 */
	@FXML
	void returnToPage(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ManagerHomeController managerHomeController = new ManagerHomeController();
		try {
			managerHomeController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/ChangePermission.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setTitle("Change Permission");
		primaryStage.setScene(scene);

		primaryStage.centerOnScreen();

		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		ArrayList<String> arr = new ArrayList<String>();
		arr.add("Active");
		arr.add("Frozen");
		ObservableList<String> list = FXCollections.observableArrayList(arr);
		if (status.getItems().isEmpty())
			status.setItems(list);
		status.setVisible(false);
		delete_btn.setVisible(false);
		ClientUI.chat.accept(new Message1(MessageType.getUsersForChangePermission, null));
		userID_col.setCellValueFactory(new PropertyValueFactory<Users, String>("id"));
		UserName_col.setCellValueFactory(new PropertyValueFactory<Users, String>("userName"));
		UserType_col.setCellValueFactory(new PropertyValueFactory<Users, String>("type"));
		Status_col.setCellValueFactory(new PropertyValueFactory<Users, String>("status"));
		users = FXCollections.observableArrayList(ChatClient.users);
		FilteredList<Users> filteredList = new FilteredList<>(users, b -> true);
		searchbar.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredList.setPredicate(User -> {
				if (newValue.isEmpty() || newValue == null) {
					return true;
				}
				String search = newValue.toLowerCase();
				if (User.getId().indexOf(search) > -1) {
					return true;
				}
				if (User.getUserName().indexOf(search) > -1) {
					return true;
				}
				if (User.getType().toString().toLowerCase().indexOf(search) > -1) {
					return true;
				}
				if (User.getStatus().toLowerCase().indexOf(search) > -1) {
					return true;
				}
				return false;
			});
		});
		users_tbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (users_tbl.getSelectionModel().getSelectedItem() != null) {
					status.setVisible(true);
					delete_btn.setVisible(true);
				}
			}
		});
		users_tbl.setItems(filteredList);
	}

}
