package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import common.HistogramValues;
import common.Message1;
import common.MessageType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LastQuareterHistogramController extends Application implements Initializable {

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	 public static void main(String[] args) {
	        launch(args);
	    }
	 @Override
	    public void start(Stage primaryStage) throws Exception {
		 ArrayList<Object> arrayObjects = new ArrayList<Object>();
		 int year;
		 int month;
		 String[] str = LocalDate.now().toString().split("-");
		 if(Integer.parseInt(str[1])>=1&&Integer.parseInt(str[1])<=3)
		 {
			 year=Integer.parseInt(str[0])-1;
			 month=10;
		 }
		 else if(Integer.parseInt(str[1])>=4&&Integer.parseInt(str[1])<=6) {
			 year=Integer.parseInt(str[0]);
			 month=1;
		 }
		 else if(Integer.parseInt(str[1])>=7&&Integer.parseInt(str[1])<=9) {
			 year=Integer.parseInt(str[0]);
			 month=4;
		 }
		 else {
			 year=Integer.parseInt(str[0]);
			 month=7;
		 }
		 
		 
		 arrayObjects.add(year);
		 arrayObjects.add(month);
		 
		 
		 ClientUI.chat.accept(new Message1(MessageType.getHistogramValues, arrayObjects));
	        primaryStage.setTitle("Last Quarter Histogram");

	        //defining the axes
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();

	        xAxis.setAnimated(false);
	        yAxis.setAnimated(false);

	        //creating the bar chart with two axis
	        final BarChart<String,Number> bc =  new BarChart<>(xAxis,yAxis);
	        bc.setAnimated(false);
	        bc.setTitle("Last Quarter");
	        xAxis.setLabel("Resturant");
	        yAxis.setLabel("Number Of Orders");
	        
	        for(HistogramValues histogramValues:ChatClient.histogramValues) {
	        	XYChart.Series<String, Number> series = new XYChart.Series<>();
	        	 Data<String, Number> data = new XYChart.Data<>(histogramValues.getResturantName(),histogramValues.getNumberOfOrdes());
	        	 series.getData().add(data);
	        	 Text text=new Text(histogramValues.getIncome()+"$");
	        	
	        	 series.setName(histogramValues.getResturantName());
	        	 bc.getData().add(series);
	        	 Node node = data.getNode();
	 	        ((Group) node.getParent()).getChildren().add(text);
	 	       node.boundsInParentProperty().addListener((ChangeListener<Bounds>) (ov, oldBounds, bounds) -> {
	 	            text.setLayoutX(
	 	                    Math.round( bounds.getMinX() + bounds.getWidth() / 2 - text.prefWidth(-1) / 2));
	 	            text.setLayoutY(Math.round( bounds.getMinY() - text.prefHeight(-1) * 0.5));
	 	        });
	        }
	        // setup scene
	        Scene scene = new Scene(bc,800, 800);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	 }
	 
	

}
