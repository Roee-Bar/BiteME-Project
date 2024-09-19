package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.BranchManager;
import common.BranchQaurter;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * this class act as a controller that waits for client's action on 
 *  View CEO Manager Reports interface and will run after clicking on View CEO Manager Reports
 * on the CEO home page interface
 * @author Mohamed Abo Saleh
 *
 */
public class ViewCEOManagerReportsController implements Initializable{
	public static ObservableList<BranchManager> branchManagers;
    @FXML
    private Button viewReports_btn;

    @FXML
    private TableView<BranchManager> branches_tbl;

    @FXML
    private TableColumn<BranchManager, String> BranchID_col;

    @FXML
    private TableColumn<BranchManager, String> UserName_col;

    @FXML
    private TextField searchbar;

    /**
	 * this action gets the primary stage of the current window
	 * then initialize new parameter with type of CEOHomeController
	 * then runs the start method of this parameter
	 * @param event this argument will be sent to the method after clicking on
	 *        return button
     */
    @FXML
    void returnToPage(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		CEOHomeController ceoHomeController = new CEOHomeController();
		try {
			ceoHomeController.start(stage);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    /**
     * this action displays the branches id and their username and gets the primary stage of the current window
	 * then initialize new parameter with type of ViewMonthlyReportsController
	 * then runs the start method of this parameter
     * @param event this argument will be sent to the method after clicking on
	 *        view reports button
     */
    @FXML
    void viewReports(ActionEvent event) {
    	ChatClient.userlogged.setId(branches_tbl.getSelectionModel().getSelectedItem().getBranchID());
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		ViewMonthlyReportsController viewMonthlyReportsController = new ViewMonthlyReportsController();
		try {
			viewMonthlyReportsController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/ViewCEOManagerReports.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		primaryStage.centerOnScreen();

		primaryStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ChatClient.userlogged.setId(CEOHomeController.ceoID);
		viewReports_btn.setVisible(false);
		ClientUI.chat.accept(new Message1(MessageType.getAllBrancheManagers, null));
		BranchID_col.setCellValueFactory(new PropertyValueFactory<BranchManager, String>("branchID"));
		UserName_col.setCellValueFactory(new PropertyValueFactory<BranchManager, String>("userName"));
		branchManagers = FXCollections.observableArrayList(ChatClient.branchManagers);
		
		FilteredList<BranchManager> filteredList = new FilteredList<>(branchManagers, b -> true);
		searchbar.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredList.setPredicate(BranchManager -> {
				if (newValue.isEmpty() || newValue == null) {
					return true;
				}
				String search = newValue.toLowerCase();
				if (BranchManager.getBranchID().toLowerCase().indexOf(search) > -1) {
					return true;
				}
				
				if (BranchManager.getUserName().toLowerCase().indexOf(search) > -1) {
					return true;
				}
				
				return false;
			});
		});
		
		branches_tbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (branches_tbl.getSelectionModel().getSelectedItem() != null) {
					viewReports_btn.setVisible(true);
				}
			}
		});
		branches_tbl.setItems(filteredList);
	}
}
