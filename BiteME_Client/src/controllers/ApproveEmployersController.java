package controllers;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;

import common.Employer;
import common.Message1;
import common.MessageType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * this class act as a controller that waits for client's action on 
 * approve employers interface and will run after clicking on approve employers
 * on the manager home page interface
 * @author Mohamed Abo Saleh
 *
 */
public class ApproveEmployersController implements Initializable {

	public static ObservableList<Employer> employers;
	@FXML
	private Button approve_btn;

	@FXML
	private TableView<Employer> employers_tbl;

	@FXML
	private TableColumn<Employer, String> employerID_col;

	@FXML
	private TableColumn<Employer, String> CompanyName_col;

	@FXML
	private Button decline_btn;

	@FXML
	private TextField searchbar;

	/**
	 * this action displays an alert to the client after clicking on approve button
	 * if he is sure that he want to accept if yes the data are updated in the data base
	 * if no, he stays in the same interface
	 * @param event  this argument will be sent to the method after clicking on
	 *        approve,yes button
	 */
	@FXML
	void approve(ActionEvent event) {
		if(employers_tbl.getSelectionModel().getSelectedItem()!=null) {
			Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to approve this employer?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.YES) {
				ClientUI.chat.accept(new Message1(MessageType.approveEmployer, employers_tbl.getSelectionModel().getSelectedItem()));
				initialize(null, null);
				
			}
		}
		
	}

	/**
	 * this action displays an alert to the client after clicking on decline button
	 * if he is sure that he want to delete if yes the data are updated in the data base
	 * if no, he stays in the same interface
	 * @param event this argument will be sent to the method after clicking on
	 *        decline,no button
	 */
	@FXML
	void decline(ActionEvent event) {
		if(employers_tbl.getSelectionModel().getSelectedItem()!=null) {
			Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to delete this employer?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.YES) {
				ClientUI.chat.accept(new Message1(MessageType.declineEmployer, employers_tbl.getSelectionModel().getSelectedItem()));
				initialize(null, null);
				
			}
		}
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		searchbar.setText("");
		approve_btn.setVisible(false);
		decline_btn.setVisible(false);
		ClientUI.chat.accept(new Message1(MessageType.getNotApprovedEmployers, null));
		employerID_col.setCellValueFactory(new PropertyValueFactory<Employer, String> ("employerID"));
		CompanyName_col.setCellValueFactory(new PropertyValueFactory<Employer, String>("companyName"));
		employers=FXCollections.observableArrayList(ChatClient.notApprovedEmployers);
		
		FilteredList<Employer> filteredList=new FilteredList<>(employers,b->true);
		searchbar.textProperty().addListener((observable, oldValue, newValue)->{

			filteredList.setPredicate(Employer->{
				if (newValue.isEmpty() || newValue == null) {
					return true;
				}
				String search=newValue.toLowerCase();
				if(Employer.getCompanyName().toLowerCase().indexOf(search)>-1) {
					return true;
				}
				if(Employer.getEmployerID().indexOf(search)>-1) {
					return true;
				}
				return false;
			});
		});
		employers_tbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(employers_tbl.getSelectionModel().getSelectedItem()!=null) {
					approve_btn.setVisible(true);
					decline_btn.setVisible(true);
				}
			}
		});
		employers_tbl.setItems(filteredList);
		

	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/ApproveEmployers.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setTitle("Approve Employers");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

}
