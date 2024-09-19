package controllers;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.BranchQaurter;
import common.Message1;
import common.MessageType;
import common.MyFile;
import common.ResturantForBM;
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
 * View Manager Quarterly Reports interface and will run after clicking on
 * View Manager Quarterly Reports on the CEO home page interface
 * @author Majd Msallam
 *
 */
public class ViewManagerQuarterlyReportsController implements Initializable {
	public static ObservableList<BranchQaurter> branchQaurters;
	@FXML
	private Button viewQaurter_btn;

	@FXML
	private TableView<BranchQaurter> branches_tbl;

	@FXML
	private TableColumn<BranchQaurter, String> BranchID_col;

	@FXML
	private TableColumn<BranchQaurter, Integer> year_col;

	@FXML
	private TableColumn<BranchQaurter, String> quarter_col;
	@FXML
	private TableColumn<BranchQaurter, String> repName_col;

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
	 * this action gets the reports that sent from the manager to ceo using the blobs
	 * and display them in a table for the ceo 
	 * @param  event this argument will be sent to the method after clicking on
	 *        view reports button
	 */
	@FXML
	void viewQuarter(ActionEvent event) {
		ClientUI.chat.accept(new Message1(MessageType.viewQuatrelyReport,
				branches_tbl.getSelectionModel().getSelectedItem().getRepName()));
		MyFile myFile = ChatClient.myfile;
		File file = new File(myFile.getFileName());
		try {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			System.out.println(myFile.getSize());
			System.out.println(myFile.getMybytearray());
			bos.write(myFile.getMybytearray(), 0, myFile.getSize());
			bos.flush();
			fos.flush();

			openFile(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize(null, null);
	}

	/**
	 * this action opens the selected report
	 * @param file: the report sent to the ceo
	 */
	private void openFile(File file) {
		if (Desktop.isDesktopSupported()) {// check if the desktop is supported
			try {
				Desktop.getDesktop().open(file);// open the file
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		viewQaurter_btn.setVisible(false);
		ClientUI.chat.accept(new Message1(MessageType.getQuarterlyReports, null));
		BranchID_col.setCellValueFactory(new PropertyValueFactory<BranchQaurter, String>("branchID"));
		year_col.setCellValueFactory(new PropertyValueFactory<BranchQaurter, Integer>("year"));
		quarter_col.setCellValueFactory(new PropertyValueFactory<BranchQaurter, String>("quarter"));
		repName_col.setCellValueFactory(new PropertyValueFactory<BranchQaurter, String>("repName"));
		branchQaurters = FXCollections.observableArrayList(ChatClient.branchQaurters);

		FilteredList<BranchQaurter> filteredList = new FilteredList<>(branchQaurters, b -> true);
		searchbar.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredList.setPredicate(BranchQaurter -> {
				if (newValue.isEmpty() || newValue == null) {
					return true;
				}
				String search = newValue.toLowerCase();
				if (BranchQaurter.getBranchID().toLowerCase().indexOf(search) > -1) {
					return true;
				}
				if (String.valueOf(BranchQaurter.getYear()).indexOf(search) > -1) {
					return true;
				}
				if (BranchQaurter.getQuarter().toLowerCase().indexOf(search) > -1) {
					return true;
				}
				if (BranchQaurter.getRepName().indexOf(search) > -1) {
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
					viewQaurter_btn.setVisible(true);
				}
			}
		});
		branches_tbl.setItems(filteredList);
	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/ViewManagerQuarterlyReports.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		primaryStage.centerOnScreen();

		primaryStage.show();

	}

}
