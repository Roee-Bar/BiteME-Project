package controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Message1;
import common.MessageType;
import common.MyFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * this class act as a controller that waits for client's action on 
 * Upload Quarterly Report interface and will run after clicking on Upload Quarterly Report
 * on the manager home page interface
 * @author Majd Msallam
 *
 */
public class UploadQuarterlyReportController implements Initializable {

	@FXML
	private TextField path_txtfiled;

	@FXML
	private Button sendFile_btn;

	private File selectedFile;

	@FXML
	private TextField year_txtfield;

	@FXML
	private Text year_txt;
	@FXML
	private ComboBox<String> quarter_combo;

	@FXML
	private Text quarter_txt;

	
	/**
	 * this action displays a new stage where you can choose the PDF file 
	 * the client wants to upload then set visible the send to ceo button the year and
	 * the month of the report
	 * @param event this argument will be sent to the method after clicking on
	 *        choose file button
	 */
	@FXML
	void chooseFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null && selectedFile.exists() && !selectedFile.isDirectory()) {
			path_txtfiled.setText(selectedFile.getAbsolutePath());
			sendFile_btn.setVisible(true);
			year_txt.setVisible(true);
			year_txtfield.setVisible(true);
			quarter_txt.setVisible(true);
			quarter_combo.setVisible(true);
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

	/**
	 * this action checks a various type of error that a client can get
	 * and displays a suitable error and then saves the file as blob in the 
	 * database, it also displays a message when trying to upload a file that
	 *  was already uploaded
	 * @param event this argument will be sent to the method after clicking on
	 *        send to ceo button
	 */
	@FXML
	void sendFile(ActionEvent event) {
		String[] str = LocalDate.now().toString().split("-");
		int year = Integer.parseInt(str[0]);
		int month = Integer.parseInt(str[1]);
		if (selectedFile == null) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("please select file");
			a.setHeaderText("Error");
			a.showAndWait();
		} else if (quarter_combo.getSelectionModel().getSelectedItem() == null) {

			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("please select quarter");
			a.setHeaderText("Error");
			a.showAndWait();
		} else if (year_txtfield.getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("please set year");
			a.setHeaderText("Error");
			a.showAndWait();
		} else if (month <= 3 && Integer.parseInt(year_txtfield.getText()) == year) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("we still in the first quarter");
			a.setHeaderText("Error");
			a.showAndWait();
		} else if (Integer.parseInt(year_txtfield.getText()) == year && month >= 4 && month <= 6
				&& (!quarter_combo.getSelectionModel().getSelectedItem().equals("Firts"))) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("you can upload just the firts quarter");
			a.setHeaderText("Error");
			a.showAndWait();

		} else if (Integer.parseInt(year_txtfield.getText()) == year && month >= 7 && month <= 9
				&& !quarter_combo.getSelectionModel().getSelectedItem().equals("Second")) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("you can upload just the second quarter");
			a.setHeaderText("Error");
			a.showAndWait();
		} else if (Integer.parseInt(year_txtfield.getText()) == year && month >= 9 && month <= 12
				&& !quarter_combo.getSelectionModel().getSelectedItem().equals("Third")) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("you can upload just the third quarter");
			a.setHeaderText("Error");
			a.showAndWait();
		} else {
			// System.out.println(selectedFile.getAbsolutePath());
			MyFile myFile = new MyFile(selectedFile.getName());
			byte[] mybytearray = new byte[(int) selectedFile.length()];
			FileInputStream fis;
			try {
				fis = new FileInputStream(selectedFile);
				BufferedInputStream bis = new BufferedInputStream(fis);
				myFile.initArray(mybytearray.length);
				myFile.setSize(mybytearray.length);
				bis.read(myFile.getMybytearray(), 0, mybytearray.length);
				ArrayList<Object> arrList = new ArrayList<Object>();
				arrList.add(ChatClient.userlogged.getId());
				arrList.add(selectedFile.getName());
				arrList.add(myFile);
				arrList.add(year_txtfield.getText());
				arrList.add(quarter_combo.getSelectionModel().getSelectedItem());
				ClientUI.chat.accept(new Message1(MessageType.uploadReport, arrList));
				if (ChatClient.uploaded) {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setContentText("file sent successfully");
					a.setHeaderText("info");
					a.showAndWait();

				} else {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setContentText("file was already uploaded");
					a.setHeaderText("info");
					a.showAndWait();
				}
				initialize(null, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		quarter_combo.getItems().clear();
		ArrayList<String> arrlist = new ArrayList<String>();
		arrlist.add("First");
		arrlist.add("Second");
		arrlist.add("Third");
		arrlist.add("Fourth");

		quarter_combo.getItems().addAll(arrlist);
		sendFile_btn.setVisible(false);
		path_txtfiled.setText("");
		year_txtfield.setText("");
		path_txtfiled.setDisable(true);
		year_txt.setVisible(false);
		year_txtfield.setVisible(false);
		quarter_txt.setVisible(false);
		quarter_combo.setVisible(false);
		year_txtfield.textProperty().addListener((observable, oldValue, newValue) -> {
			String[] str = LocalDate.now().toString().split("-");
			if (!newValue.equals("")) {
				newValue = newValue.replaceAll(" ", "");
				year_txtfield.setText(newValue);
			}
			if (!newValue.matches("\\s0-9*")) {
				newValue = newValue.replaceAll("[^\\s0-9]", "");
				year_txtfield.setText(newValue);
			}
			if (!newValue.equals(""))
				if (Integer.parseInt(newValue) > Integer.parseInt(str[0])) {
					year_txtfield.setText(str[0]);

				}

		});

	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/UploadQuarterlyReport.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setTitle("Change Permission");
		primaryStage.setScene(scene);

		primaryStage.centerOnScreen();

		primaryStage.show();
	}

}
