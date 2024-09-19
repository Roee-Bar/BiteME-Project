package controllers;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import client.ChatClient;
import client.ClientUI;
import common.DataForIncome;
import common.HistogramValues;
import common.Message1;
import common.MessageType;
import common.MyFile;
import common.ResturantForBM;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * this class act as a controller that waits for client's action on view reports
 * 
 * @author Mohamed Abo Saleh
 *
 */
public class ViewMonthlyReportsController implements Initializable {
	public static ObservableList<ResturantForBM> resturants;
	@FXML
	private Button getFile_btn;

	@FXML
	private TableView<ResturantForBM> resturants_tbl;

	@FXML
	private TableColumn<ResturantForBM, String> resturantName_col;

	@FXML
	private TableColumn<ResturantForBM, String> resturantID_col;

	@FXML
	private TextField searchbar;

	@FXML
	private ComboBox<String> reportType_combo;

	@FXML
	private Text reportType_txt;

	@FXML
	private TextField year_txtField;

	@FXML
	private TextField month_txtField;

	@FXML
	private Text year_txt;

	@FXML
	private Text month_txt;

	@FXML
	void getFile(ActionEvent event) {
		String[] str = LocalDate.now().toString().split("-");

		ArrayList<Object> arrayObjects = new ArrayList<Object>();
		ResturantForBM res = resturants_tbl.getSelectionModel().getSelectedItem();
		if (reportType_combo.getSelectionModel().getSelectedItem() == null) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("select report type");
			a.setHeaderText("Error");
			a.showAndWait();
		} else if (year_txtField.getText().equals("") || month_txtField.getText().equals("")) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("set year and month");
			a.setHeaderText("Error");
			a.showAndWait();

		} else if (Integer.parseInt(year_txtField.getText()) == Integer.parseInt(str[0])
				&& Integer.parseInt(month_txtField.getText()) == Integer.parseInt(str[1])) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("month has not ended");
			a.setHeaderText("Error");
			a.showAndWait();
		} else if ((Integer.parseInt(year_txtField.getText()) == res.getYear()
				&& Integer.parseInt(month_txtField.getText()) < res.getMonth())
				|| Integer.parseInt(year_txtField.getText()) < res.getYear()) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("this resturant opened in " + res.getMonth() + "/" + res.getYear());
			a.setHeaderText("Error");
			a.showAndWait();

			// user can't insert year after this year
		} else if (((Integer.parseInt(year_txtField.getText()) == Integer.parseInt(str[0]))
				&& (Integer.parseInt(month_txtField.getText()) > Integer.parseInt(str[1])))) {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("we still in " + str[1] + "/" + str[0]);
			a.setHeaderText("Error");
			a.showAndWait();
		} else {
			if (reportType_combo.getSelectionModel().getSelectedItem().equals("income")) {

				String filename = "income_" + res.getResturantName() + res.getId() + "" + year_txtField.getText() + ""
						+ month_txtField.getText() + ".pdf";

				ClientUI.chat.accept(new Message1(MessageType.getIncomeFile, (filename)));
				if (!(boolean) ChatClient.temp.get(0)) {// file does not exist
					ArrayList<String> arr = new ArrayList<String>();
					System.out.println("file not");
					arr.add(res.getId());
					arr.add(year_txtField.getText());
					arr.add(month_txtField.getText());
					arr.add(ChatClient.userlogged.getId());
					ClientUI.chat.accept(new Message1(MessageType.getDataForIncomeFile, arr));
					File file = new File(filename);

					Stage stage = new Stage();
					stage.setTitle("Income for restaurant " + res.getResturantName() + " for month "
							+ month_txtField.getText() + "/" + year_txtField.getText());

					// defining the axes
					final CategoryAxis xAxis = new CategoryAxis();
					final NumberAxis yAxis = new NumberAxis();

					xAxis.setAnimated(false);
					yAxis.setAnimated(false);

					// creating the bar chart with two axis
					final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
					bc.setAnimated(false);
					bc.setTitle("Income for restaurant " + res.getResturantName() + " for month "
							+ month_txtField.getText() + "/" + year_txtField.getText());
					xAxis.setLabel("Weeks");
					yAxis.setLabel("Number Of Orders");
					for (DataForIncome dataForIncome : ChatClient.dataForIncomes) {
						XYChart.Series<String, Number> series = new XYChart.Series<>();
						Data<String, Number> data = new XYChart.Data<>(dataForIncome.getWeek(),
								dataForIncome.getNumberOfOrders());
						series.getData().add(data);
						Text text = new Text(dataForIncome.getIncome() + "$");

						series.setName(dataForIncome.getWeek());
						bc.getData().add(series);
						Node node = data.getNode();
						((Group) node.getParent()).getChildren().add(text);
						node.boundsInParentProperty().addListener((ChangeListener<Bounds>) (ov, oldBounds, bounds) -> {
							text.setLayoutX(
									Math.round(bounds.getMinX() + bounds.getWidth() / 2 - text.prefWidth(-1) / 2));
							text.setLayoutY(Math.round(bounds.getMinY() - text.prefHeight(-1) * 0.5));
						});
					}

					String imageName = "income_" + res.getResturantName() + res.getId() + "_" + year_txtField.getText()
							+ "_" + month_txtField.getText() + ".png";
					Scene scene = new Scene(bc, 500, 450);
					WritableImage image = bc.snapshot(new SnapshotParameters(), null);
					File file1 = new File(imageName);

					try {
						System.out.println(2);
						ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file1);
						System.out.println(1);
					} catch (IOException e) {
						// TODO: handle exception here
					}

					///////
					try {
						Image img1 = Image.getInstance(imageName);
						Document doc = new Document();
						try {
							PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filename));
							doc.open();
							doc.add(new Paragraph("Income report for restaurant " + res.getResturantName()
									+ " for month " + month_txtField.getText() + "/" + year_txtField.getText()));
							// img1.setAbsolutePosition(10f, 0f);
							img1.scaleToFit(PageSize.A4.getWidth() - 50, PageSize.A4.getHeight() - 50);
							float x = (PageSize.A4.getWidth() - img1.getScaledWidth()) / 2;
							float y = (PageSize.A4.getHeight() - img1.getScaledHeight()) / 2;
							img1.setAbsolutePosition(x, y);
							doc.add(img1);
							doc.close();

							writer.close();

							createNewFile(arrayObjects, res, filename, file);
							ClientUI.chat.accept(new Message1(MessageType.saveIncomeFile, arrayObjects));
							openFile(file);
							// doc.add
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (BadElementException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					System.out.println("file yes");
					File file = new File(filename);
					try {
						file.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					MyFile myFile = (MyFile) ChatClient.temp.get(1);
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
				}
			} else if (reportType_combo.getSelectionModel().getSelectedItem().equals("orders")) {
				String filename = "orders_" + res.getResturantName() + res.getId() + "" + year_txtField.getText() + ""
						+ month_txtField.getText() + ".pdf";
				ClientUI.chat.accept(new Message1(MessageType.getOredersFile, (filename)));
				if (!(boolean) ChatClient.temp.get(0)) {
					Stage stage = new Stage();
					stage.setTitle("Order Chart");
					Scene scene = new Scene(new Group());

					/////////

					ArrayList<String> arr = new ArrayList<String>();
					System.out.println("file not");
					arr.add(res.getId());
					arr.add(year_txtField.getText());
					arr.add(month_txtField.getText());
					arr.add(ChatClient.userlogged.getId());
					ClientUI.chat.accept(new Message1(MessageType.getDataForOrdersFile, arr));
					File file = new File(filename);
//////////////////////////////////////////////////
					try {
						file.createNewFile();
						HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
						int numberOfOrders = 0;
						int numberOfSpecificOrder = 0;
						for (String string : ChatClient.hashMap.keySet()) {
							for (String string1 : ChatClient.hashMap.get(string)) {
								numberOfOrders++;
								numberOfSpecificOrder++;
							}
							hashMap.put(string, numberOfSpecificOrder);
							numberOfSpecificOrder = 0;
						}

						ArrayList<PieChart.Data> pieChartDataArrayList = new ArrayList<PieChart.Data>();
						for (String string : ChatClient.hashMap.keySet()) {
							pieChartDataArrayList.add(new PieChart.Data(
									string + " " + (float) hashMap.get(string) / numberOfOrders * 100 + "%",
									(float) hashMap.get(string) / numberOfOrders * 100));
						}

						ObservableList<PieChart.Data> pieChartData = FXCollections
								.observableArrayList(pieChartDataArrayList);
						final PieChart chart = new PieChart(pieChartData);
						chart.setTitle("Orders for restaurant " + res.getResturantName() + " for month "
								+ month_txtField.getText() + "/" + year_txtField.getText());
						((Group) scene.getRoot()).getChildren().add(chart);

						String imageName = "orders_" + res.getResturantName() + res.getId() + "_"
								+ year_txtField.getText() + "_" + month_txtField.getText() + ".png";

						WritableImage image = chart.snapshot(new SnapshotParameters(), null);
						File file1 = new File(imageName);

						try {
							ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file1);
						} catch (IOException e) {
							// TODO: handle exception here
						}

						///////
						try {
							Image img1 = Image.getInstance(imageName);
							Document doc = new Document();
							try {
								PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filename));
								doc.open();
								doc.add(new Paragraph("Orders report for restaurant " + res.getResturantName()
										+ " for month " + month_txtField.getText() + "/" + year_txtField.getText()));
								img1.scaleToFit(PageSize.A4.getWidth() - 50, PageSize.A4.getHeight() - 50);
								float x = (PageSize.A4.getWidth() - img1.getScaledWidth()) / 2;
								float y = (PageSize.A4.getHeight() - img1.getScaledHeight()) / 2;
								img1.setAbsolutePosition(x, y);
								doc.add(img1);
								doc.close();
								writer.close();
								createNewFile(arrayObjects, res, filename, file);
								ClientUI.chat.accept(new Message1(MessageType.saveOrdersFile, arrayObjects));
								openFile(file);
								// doc.add
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (DocumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (BadElementException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} catch (IOException e) { // TODO Auto-generated catch block
						e.printStackTrace();
					}
//////////////////////////////////////////////////////
				} else {
					System.out.println("file yes");
					File file = new File(filename);
					try {
						file.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					MyFile myFile = (MyFile) ChatClient.temp.get(1);
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
				}
			} else if (reportType_combo.getSelectionModel().getSelectedItem().equals("performance")) {
				String filename = "performance_" + res.getResturantName() + res.getId() + "_" + year_txtField.getText()
						+ "_" + month_txtField.getText() + ".pdf";
				ClientUI.chat.accept(new Message1(MessageType.getPerformanceFile, (filename)));
				if (!(boolean) ChatClient.temp.get(0)) {
					Stage stage = new Stage();
					stage.setTitle("Performance Chart");
					Scene scene = new Scene(new Group());
					ArrayList<String> arr = new ArrayList<String>();
					System.out.println("file not");
					arr.add(res.getId());
					arr.add(year_txtField.getText());
					arr.add(month_txtField.getText());
					arr.add(ChatClient.userlogged.getId());
					/////////////////////////////////////////////////
					ClientUI.chat.accept(new Message1(MessageType.getDataForPerformanceFile, arr));

					File file = new File(filename);

					Document doc = new Document();
					try {
						PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filename));
						doc.open();
						doc.add(new Paragraph("Performance report for restaurant " + res.getResturantName()
								+ " for month " + month_txtField.getText() + "/" + year_txtField.getText()));

						doc.add(new Paragraph(ChatClient.dataForFiles.get(1)));

						////////////////////////////////
						String format;
						ArrayList<PieChart.Data> pieChartDataArrayList = new ArrayList<PieChart.Data>();

						format = String.format("%.2f", Double.parseDouble(ChatClient.dataForFiles.get(0)));
						pieChartDataArrayList.add(new PieChart.Data("Late: " + format + "%",
								Double.parseDouble(ChatClient.dataForFiles.get(0))));
						format = String.format("%.2f", 100 - Double.parseDouble(ChatClient.dataForFiles.get(0)));
						pieChartDataArrayList.add(new PieChart.Data("Not Late: " + format + "%",
								100 - Double.parseDouble(ChatClient.dataForFiles.get(0))));
						ObservableList<PieChart.Data> pieChartData = FXCollections
								.observableArrayList(pieChartDataArrayList);
						final PieChart chart = new PieChart(pieChartData);
						chart.setTitle("Performance for restaurant " + res.getResturantName() + " for month "
								+ month_txtField.getText() + "/" + year_txtField.getText());
						((Group) scene.getRoot()).getChildren().add(chart);

						String imageName = "performance_" + res.getResturantName() + res.getId() + "_"
								+ year_txtField.getText() + "_" + month_txtField.getText() + ".png";

						WritableImage image = chart.snapshot(new SnapshotParameters(), null);
						File file1 = new File(imageName);

						try {
							ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file1);
						} catch (IOException e) {
							// TODO: handle exception here
						}

						try {
							Image img1 = Image.getInstance(imageName);
							try {
								img1.scaleToFit(PageSize.A4.getWidth() - 50, PageSize.A4.getHeight() - 50);
								float x = (PageSize.A4.getWidth() - img1.getScaledWidth()) / 2;
								float y = (PageSize.A4.getHeight() - img1.getScaledHeight()) / 2;
								img1.setAbsolutePosition(x, y);
								doc.add(img1);
								doc.close();
								writer.close();
								createNewFile(arrayObjects, res, filename, file);
								ClientUI.chat.accept(new Message1(MessageType.savePerformanceFile, arrayObjects));
								openFile(file);
								// doc.add
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (DocumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (BadElementException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					System.out.println("file yes");
					File file = new File(filename);
					try {
						file.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					MyFile myFile = (MyFile) ChatClient.temp.get(1);
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
				}
			}
		}
	}

	/**
	 * 
	 * this method converts a file to array of bytes using BufferedInputStream, and
	 * saves the file and the relevant data to and array of objects
	 * 
	 * @param arrayObjects: used to be sent to the server
	 * @param res:          restaurant data
	 * @param filename
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void createNewFile(ArrayList<Object> arrayObjects, ResturantForBM res, String filename, File file)
			throws FileNotFoundException, IOException {
		byte[] mybytearray = new byte[(int) file.length()];
		MyFile myFile = new MyFile(filename);
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		myFile.initArray(mybytearray.length);
		myFile.setSize(mybytearray.length);
		bis.read(myFile.getMybytearray(), 0, mybytearray.length);
		arrayObjects.add(ChatClient.userlogged.getId());
		arrayObjects.add(res.getResturantName());
		arrayObjects.add(month_txtField.getText());
		arrayObjects.add(year_txtField.getText());
		arrayObjects.add(filename);
		arrayObjects.add(myFile);
	}

	/**
	 * this method used to open file
	 * 
	 * @param file: file to open
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

	/**
	 * this action gets the primary stage of the current window then initialize new
	 * parameter with type of ManagerHomeController or the
	 * ViewCEOManagerReportsController if the user was CEO then runs the start
	 * method of this parameter
	 * 
	 * @param event this argument will be sent to the method after clicking on
	 *              return button
	 */
	@FXML
	void returnToPage(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		if (!BiteMeLoginController.isCEO) {
			ManagerHomeController managerHomeController = new ManagerHomeController();
			try {
				managerHomeController.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			ViewCEOManagerReportsController viewCEOManagerReportsController = new ViewCEOManagerReportsController();
			try {
				viewCEOManagerReportsController.start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		reportType_combo.getItems().clear();
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("income");
		arr.add("orders");
		arr.add("performance");
		reportType_combo.getItems().addAll(arr);
		setView(false);
		// accept
		ClientUI.chat.accept(new Message1(MessageType.getResturantsForBranch, ChatClient.userlogged.getId()));
		resturantID_col.setCellValueFactory(new PropertyValueFactory<ResturantForBM, String>("id"));
		resturantName_col.setCellValueFactory(new PropertyValueFactory<ResturantForBM, String>("resturantName"));
		resturants = FXCollections.observableArrayList(ChatClient.resturantsForBM);

		FilteredList<ResturantForBM> filteredList = new FilteredList<>(resturants, b -> true);
		searchbar.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredList.setPredicate(Resturant -> {
				if (newValue.isEmpty() || newValue == null) {
					return true;
				}
				String search = newValue.toLowerCase();
				if (Resturant.getResturantName().toLowerCase().indexOf(search) > -1) {
					return true;
				}
				if (Resturant.getId().indexOf(search) > -1) {
					return true;
				}
				return false;
			});
		});
		resturants_tbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (resturants_tbl.getSelectionModel().getSelectedItem() != null) {
					setView(true);
				}

			}
		});
		month_txtField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\s0-9*")) {
				newValue = newValue.replaceAll("[^\\s0-9]", "");
				month_txtField.setText(newValue);
			}
			if (!newValue.equals(""))
				if (Integer.parseInt(newValue) > 12) {
					month_txtField.setText("12");
				}
		});
		year_txtField.textProperty().addListener((observable, oldValue, newValue) -> {
			String[] str = LocalDate.now().toString().split("-");
			if (!newValue.matches("\\s0-9*")) {
				newValue = newValue.replaceAll("[^\\s0-9]", "");
				year_txtField.setText(newValue);
			}
			if (!newValue.equals(""))
				if (Integer.parseInt(newValue) > Integer.parseInt(str[0])) {
					year_txtField.setText(str[0]);
				}

		});
		resturants_tbl.setItems(filteredList);
	}

	/**
	 * set all the text field visible or not
	 * 
	 * @param flag to change the status from visible to invisible and vice versa
	 */
	private void setView(boolean flag) {
		getFile_btn.setVisible(flag);
		reportType_combo.setVisible(flag);
		reportType_txt.setVisible(flag);
		year_txtField.setVisible(flag);
		month_txtField.setVisible(flag);
		year_txt.setVisible(flag);
		month_txt.setVisible(flag);

	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("/View/ViewMonthlyReports.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();

	}

}