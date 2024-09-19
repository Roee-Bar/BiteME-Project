package controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import common.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Screen;
import javafx.stage.Stage;

import server.ServerUI;
import server.mysqlConnection;

public class ServerConnectionController implements Initializable {

/////////////////////////////////////
int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
int initialX;
int initialY;


/////////////////////////////////////
	public static ObservableList<Client> clients_list = FXCollections.observableArrayList();

	@FXML
	private Button Connect_btn;

	@FXML
	private Button disconnect_btn;

	@FXML
	private TextField ip_txt;

	@FXML
	private TextField port_txt;

	@FXML
	private TextField DBName_txt;

	@FXML
	private TextField DBUser_txt;

	@FXML
	private PasswordField Password_txt;
	@FXML
	private TableView<Client> user_tbl;

	@FXML
	private TableColumn<Client, String> ip;

	@FXML
	private TableColumn<Client, String> host;

	@FXML
	private TableColumn<Client, String> status;

	@FXML
	private Label lable_txt;
	private boolean flag = true;
	private boolean flag1 = false;
	private boolean importflag=false;
	/////////////////////
	@FXML
	private ProgressBar progress;

	@FXML
	private Button importButton;

	@FXML
	private Label data_txt;

	//////////////
	@FXML
	void connect(ActionEvent event) {
		flag = false;

		mysqlConnection.db_name = DBName_txt.getText();
		mysqlConnection.db_user = DBUser_txt.getText();
		mysqlConnection.db_pass = Password_txt.getText();

		ServerUI.sv.setPort(Integer.parseInt(port_txt.getText()));
		try {
			ServerUI.sv.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (ServerUI.sv.isListening()) {
			lable_txt.setText("Server listening for connections on port " + port_txt.getText());
		} else
			lable_txt.setText("Not connected to Server!");


		importflag=true;
		Connect_btn.setDisable(!flag);
		DBName_txt.setDisable(true);
		DBUser_txt.setDisable(true);
		Password_txt.setDisable(true);
		disconnect_btn.setDisable(flag);
		port_txt.setDisable(true);
		clients_list.addListener(new ListChangeListener<Client>() {

			@Override
			public void onChanged(Change<? extends Client> arg0) {
				// TODO Auto-generated method stub
				System.out.println("change");

				initialize(null, null);

			}
		});


		initialize(null, null);
		
		
		
	}

	@FXML
	void disconnect(ActionEvent event) {
		
		
		// I was trying to get the values of the dishtype enum
		//Eventually  I changer the dishtype to String 
		//System.out.println((mysqlConnection.getDishTypeEnumValues()));
		flag = true;
		try {
			ServerUI.sv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connect_btn.setDisable(!flag);
		DBName_txt.setDisable(false);
		DBUser_txt.setDisable(false);
		Password_txt.setDisable(false);
		disconnect_btn.setDisable(flag);
		port_txt.setDisable(false);
		lable_txt.setText("Server has stopped listening for connections.");
		clients_list.clear();
		initialize(null, null);
	}

	@FXML
	void close(ActionEvent event) {
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		InetAddress serverIp;
		try {
			serverIp = InetAddress.getLocalHost();
			ip_txt.setText(serverIp.getHostAddress());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		importButton.setVisible(importflag);
		data_txt.setVisible(flag1);
		progress.setVisible(false);
		Connect_btn.setDisable(!flag);
		disconnect_btn.setDisable(flag);
		port_txt.setText("" + ServerUI.DEFAULT_PORT);
		ip.setCellValueFactory(new PropertyValueFactory<Client, String>("Ip"));
		host.setCellValueFactory(new PropertyValueFactory<Client, String>("Host"));
		status.setCellValueFactory(new PropertyValueFactory<Client, String>("Status"));
		user_tbl.setItems(clients_list);
	}

	////////////////////////////
	@FXML
	void importDB(ActionEvent event) throws InterruptedException {
		flag1=true;
		mysqlConnection.importExternalDB();
		importButton.setDisable(true);
		progress.setVisible(true);
		Load load = new Load(progress);
		Thread thread = new Thread(load);
		thread.setDaemon(true);
		thread.start();

	}

///// inner class
	public class Load implements Runnable {

		ProgressBar progressBar;

		public Load(ProgressBar progressBar) {

			this.progressBar = progressBar;
		}

		@Override
		public void run() {
			while (progressBar.getProgress() <= 1) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						progressBar.setProgress(progressBar.getProgress() + 0.01);
					}
				});
				synchronized (this) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();

					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			progress.setVisible(false);
			data_txt.setVisible(flag1);
		}
	}


	
	

 
	/////////////////
	public void start(Stage primaryStage) throws Exception {
		int sceneWidth = 0;
		int sceneHeight = 0;
		if (screenWidth <= 800 && screenHeight <= 600) {
			sceneWidth = 600;
			sceneHeight = 350;
		} else if (screenWidth <= 1280 && screenHeight <= 768) {
			sceneWidth = 800;
			sceneHeight = 450;
		} else if (screenWidth <= 1920 && screenHeight <= 1080) {
			sceneWidth = 1000;
			sceneHeight = 650;
		}
		Parent root = FXMLLoader.load(getClass().getResource("/View/ServerConnection.fxml"));
		Scene scene = new Scene(root);
		scene.setOnMousePressed(m -> {
			if (m.getButton() == MouseButton.PRIMARY) {
				scene.setCursor(Cursor.MOVE);
				initialX = (int) (primaryStage.getX() - m.getScreenX());
				initialY = (int) (primaryStage.getY() - m.getScreenY());
			}
		});

		scene.setOnMouseDragged(m -> {
			if (m.getButton() == MouseButton.PRIMARY) {
				primaryStage.setX(m.getScreenX() + initialX);
				primaryStage.setY(m.getScreenY() + initialY);
			}
		});

		scene.setOnMouseReleased(m -> {
			scene.setCursor(Cursor.DEFAULT);
		});
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);

		primaryStage.centerOnScreen();

		primaryStage.show();
	}

}
