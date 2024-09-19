package controllers;

import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.Approvedtype;
import common.ItemList;
import common.Message1;
import common.MessageType;
import common.OrdersForRes;
import common.OrdersList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OrdersDetailsController implements Initializable {
	public static OrdersList orders=null;
	public static Integer CountParticipants;
	public static String ArrivalTime;
	
	public Integer PricebeforRefund;

	public static String orderTime;
	public static Integer orderPackageNumber;
	public static String time;
	
	  public static  Integer day ;
	  public  static String month ;
	  public  static String year;

	
	public   long addtionInMilliSeconds ;
	public   long differenceInHours;
	public   long differenceInMinutes;
	
	
	public   Date date1;
	public  Date date2;
	public static long dateTemp;
	
	
	public static ArrayList<ItemList> Items=new ArrayList<>();

	public static ItemList AddItem;
	public int i=1;
	
    public int orderPrice;
    public double tempPrice;
    @FXML
    private ImageView image;

    @FXML
    private Button BackButton;

    @FXML
    private Button ConfirmButton;

    @FXML
    private Text Orderdetailstxt;

    @FXML
    private Text resturantnametxt;

    @FXML
    private Text resturantfield;

    @FXML
    private Text reqdatetxt;

    @FXML
    private Text deleiverytxt;

    @FXML
    private Text totalpricetxt;

    @FXML
    private Text totalmesstxt;

    @FXML
    private Text Citytxt;
    
    @FXML
    private Text addresstxt;

    @FXML
    private Text streettxt;

    @FXML
    private Text HouseNumbertxt;

    @FXML
    private Text requesteddatefield;

    @FXML
    private Text delevfield;

    @FXML
    private Text totalpricefield;

    @FXML
    private Text cityfield;

    @FXML
    private Text streetfield;

    @FXML
    private Text huosefiled;

    @FXML
    private Text credittxt;

    @FXML
    private Text empnametxt;

    @FXML
    private Text empidtxt;

    @FXML
    private Text creditfield;

    @FXML
    private Text empnamefield;

    @FXML
    private Text empidfield;
    
//////////////////////////////////////////////////////////////////////////////////////////////////////
    void TimerMath (Date data2,long dateTemp) {
    	
    	addtionInMilliSeconds = (date2.getTime() + dateTemp);
		   differenceInHours = (addtionInMilliSeconds / (60 * 60 * 1000))  % 24;
   // Calculating the difference in Minutes
		   differenceInMinutes= (addtionInMilliSeconds / (60 * 1000)) % 60;
 }
    
    public static void findDate(String date)
    {
        // Splitting the given date by '-'
        String dateParts[] = date.split("-");
 
        // Getting day, month, and year
        // from date
         day =Integer.valueOf(dateParts[2]);
         month = dateParts[1];
         year = dateParts[0];
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    void BackButtonAction(ActionEvent event) {
    	PaymentMethodController.ref=Integer.valueOf(ChatClient.getRefund.getRefund());
    	 ((Node) event.getSource()).getScene().getWindow().hide();// get stage
    	PaymentMethodController.IsDeleiveryShared=false;
    }

    @FXML
    void ConfirmButtonAction(ActionEvent event) {
    	 LocalDateTime now = LocalDateTime.now();
    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    	 
    	 LocalDateTime now2 = LocalDateTime.now();
      	 DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	findDate(dtf2.format(now2));
    	 
    	time=dtf.format(now);
    	if(PaymentMethodController.flagDate==1)
    	orderTime=(time);
    	else 
    		orderTime=PaymentMethodController.Time;
    	
    	orderPackageNumber=null;
    	
    	if(PaymentMethodController.flagDate==1)
    	{
    		try {
				date2=simpleDateFormat.parse(dtf.format(now));
				dateTemp=3600000*3; ////to add one hour.
				TimerMath(date2, dateTemp);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		ArrivalTime=(differenceInHours+":"+differenceInMinutes);
    	}else ArrivalTime=PaymentMethodController.Time;
    	PricebeforRefund=ItemDetailsController.TotalPrice+PaymentMethodController.pricedeleivery;
    	
    	if(!PaymentMethodController.DeleiveryType.equals("TakeAway")) {
    		orders= new OrdersList(ChatClient.userlogged.getId(),ChooseResturantController.resturant.getResturantID(),orderPackageNumber,orderTime
    				,time,String.valueOf(PricebeforRefund),PaymentMethodController.address.toString(),PaymentMethodController.DeleiveryType
    				,null,ArrivalTime,null,ChooseResturantController.resturant.getLocation(),year,month,day,"0","0");
    		
    		
    	}
    	else {
    		orders= new OrdersList(ChatClient.userlogged.getId(),ChooseResturantController.resturant.getResturantID(),orderPackageNumber,orderTime
    				,time,String.valueOf(PricebeforRefund),"NoAddress",PaymentMethodController.DeleiveryType
    				,null,ArrivalTime,null,ChooseResturantController.resturant.getLocation(),year,month,day,"0","0");
    		System.out.println(orders.getOrderPackageNumber()+" "+orders.getRequestDate());
    	}
    	
    	ClientUI.chat.accept(new Message1(MessageType.OrdersListToDataBase, orders.getCustomer_ID()+" "+orders.getResturant()
    	+" "+orders.getRequestDate()+" "+orders.getOrderedDate()+" "+orders.getTotalPrice()+" "+orders.getAddress()+" "+orders.getDeleiveryService()
    	+" "+orders.getStatus()+" "+orders.getArrivalTime()+" "+orders.getApprovalRecieving()+" "+orders.getBranchlocation()+" "+orders.getYear()+" "+orders.getMonth()
    	+" "+orders.getDay()+" "+orders.getArrivedToCustomerTime()+" "+orders.getOrderReadyTime()));
    	
    
    	
    	ClientUI.chat.accept(new Message1(MessageType.GetOrder,ChatClient.userlogged.getId()));
    	
    	System.out.println(ChatClient.order2.getOrderPackageNumber());

     	for(int i=0;i<ItemDetailsController.itemList.size();i++)
    	{
    	     if(OptionalSelectionController.sel.size()!=0) {
    		          AddItem=new ItemList(ItemDetailsController.itemList.get(i).getTypeMeal() , ItemDetailsController.itemList.get(i).getDishes(),
    				  ItemDetailsController.itemList.get(i).getExtras().toString().replaceAll(" ",""), ItemDetailsController.itemList.get(i).getQuantity()
    				  ,ItemDetailsController.itemList.get(i).getTotalPrice(),ChatClient.order2.getOrderPackageNumber());
    		   Items.add(AddItem);
    	       }
    	     else {
    		         AddItem=new ItemList(ItemDetailsController.itemList.get(i).getTypeMeal() , ItemDetailsController.itemList.get(i).getDishes(),
    				"NoExtra", ItemDetailsController.itemList.get(i).getQuantity()
   				   ,ItemDetailsController.itemList.get(i).getTotalPrice(),ChatClient.order2.getOrderPackageNumber());
    	 	Items.add(AddItem);
    	        }  
    }
    	
    	

    		ClientUI.chat.accept(new Message1(MessageType.itemsListtoDataBase, Items));

    	

    	
    	ItemDetailsController.itemList.clear();
    	Items.clear();
    	
    if(PaymentMethodController.accountpayment.equals("buissiness")) {
		String wallet=(PaymentMethodController.Wallet);
		int orederPrice=Integer.valueOf(orders.getTotalPrice());
		int x=Integer.valueOf(wallet)-orderPrice;
		wallet=String.valueOf(x);
		ClientUI.chat.accept(new Message1(MessageType.updateCelling,wallet+" "+ChatClient.w4ccard.getW4cCode()));
    }
    
		PaymentMethodController.address=null;
		PaymentMethodController.Time=null;
    	
		

		if(PaymentMethodController.DeleiveryType.equals("SharedDeleivery"))
		{
			     if(PaymentMethodController.Participants_Number==1)
			       {
			    	 PaymentMethodController.IsDeleiveryShared=false;
				 	 PaymentMethodController.DeleiveryType="null";
				 	 PaymentMethodController.flagDate=2;
				 ((Node) event.getSource()).getScene().getWindow().hide();// get stage
			    	CustomerHomeController AFrame=new CustomerHomeController();
					try {
						AFrame.start(CustomerHomeController.stage1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			else
			{
				if(i<PaymentMethodController.Participants_Number)
				{
					

					PaymentMethodController.Temp=PaymentMethodController.Temp+1;
					
					if(PaymentMethodController.Temp==PaymentMethodController.Participants_Number) {
						PaymentMethodController.IsDeleiveryShared=false;
					 	 PaymentMethodController.DeleiveryType="null";
					 	 PaymentMethodController.flagDate=2;
						 ((Node) event.getSource()).getScene().getWindow().hide();// get stage
					    	CustomerHomeController AFrame=new CustomerHomeController();
							try {
								AFrame.start(CustomerHomeController.stage1);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}else {
						((Node) event.getSource()).getScene().getWindow().hide();// get stage
				    	TybeMealController AFrame=new TybeMealController();
						try {
							AFrame.start(CustomerHomeController.stage1);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						i++;
					}
						 
							
				}
			}
			
		}
		else {
			
			PaymentMethodController.IsDeleiveryShared=false;
		 	 PaymentMethodController.DeleiveryType="null";
		 	 PaymentMethodController.flagDate=2;
			 ((Node) event.getSource()).getScene().getWindow().hide();// get stage
		    	CustomerHomeController AFrame=new CustomerHomeController();
				try {
					AFrame.start(CustomerHomeController.stage1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
		if(PaymentMethodController.isselected==true) {
			ClientUI.chat.accept(new Message1(MessageType.updateRegund,ChatClient.userlogged.getId()+" "+
					ChooseResturantController.resturant.getResturantID()+" "+String.valueOf(PaymentMethodController.ref)));
			
					System.out.println(String.valueOf(PaymentMethodController.ref));
		}
		
		  Alert a = new Alert(AlertType.INFORMATION);
	        a.setContentText("Your Order has Sent Successfully to resturant");
	        a.setHeaderText("good appetite");
	        a.showAndWait(); 
		
		
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(PaymentMethodController.accountpayment.equals("private")) {
			empidtxt.setVisible(false);
			empidfield.setVisible(false);
			empnamefield.setVisible(false);
			empnametxt.setVisible(false);
			creditfield.setVisible(true);
		creditfield.setText(ChatClient.w4ccard.getCreditCardNumber());
		}else {
			credittxt.setVisible(false);
			creditfield.setVisible(false);
			empidfield.setText(ChatClient.bussiness.getEmployerId());
			empnamefield.setText(ChatClient.bussiness.getEmployerName());
		}
			
		resturantfield.setText(ChooseResturantController.resturant.getResturantName());
		

		if(PaymentMethodController.flagDate==0)
		{
		requesteddatefield.setText(PaymentMethodController.Time);
		}
		else requesteddatefield.setText("now");
		
		delevfield.setText(PaymentMethodController.DeleiveryType);
		
		
		if(PaymentMethodController.isselected) {
				if(PaymentMethodController.ref==0)
					{orderPrice=ItemDetailsController.TotalPrice+PaymentMethodController.pricedeleivery;}
				
				else {
					     orderPrice=ItemDetailsController.TotalPrice+PaymentMethodController.pricedeleivery;
						if(orderPrice-PaymentMethodController.ref<=0)
							{
							   PaymentMethodController.ref=PaymentMethodController.ref-orderPrice;
							   orderPrice=0;
							}
						
					   else {
								orderPrice=orderPrice-PaymentMethodController.ref;
								PaymentMethodController.ref=0;
							}
						
					}
				
		}else orderPrice=ItemDetailsController.TotalPrice+PaymentMethodController.pricedeleivery;

		
		if(PaymentMethodController.flagDate==0) {//0->after two hour,1-> now
			tempPrice=(orderPrice)*0.1;
			orderPrice=orderPrice-(int)tempPrice;
		}
		
		
		totalpricefield.setText(String.valueOf(orderPrice));
		
		if(PaymentMethodController.DeleiveryType.equals("TakeAway")) {
			addresstxt.setVisible(false);
			Citytxt.setVisible(false);
			streettxt.setVisible(false);
			HouseNumbertxt.setVisible(false);
			cityfield.setVisible(false);
			streetfield.setVisible(false);
			huosefiled.setVisible(false);
		}
		else {
			if(PaymentMethodController.address!=null) {
			cityfield.setText(PaymentMethodController.address.getCity());
			streetfield.setText(PaymentMethodController.address.getStreet());
			huosefiled.setText(PaymentMethodController.address.getHouseNumber());
			}
		}
		
			
		
		
		
	}
	
	public void start(Stage stage)  throws Exception {
		// TODO Auto-generated method stub
		
		Parent root = FXMLLoader.load(getClass().getResource("/View/OrdersDetails.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Payment");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}

}
