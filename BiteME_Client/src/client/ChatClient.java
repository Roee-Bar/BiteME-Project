// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import client.*;
import common.Account;
import common.BranchManager;
import common.BranchQaurter;
import common.Business;
import common.ChatIF;
import common.DataForIncome;
import common.Dish;
import common.DishForResturant;
import common.Employer;
import common.HistogramValues;
import common.HoumanResources;
import common.ItemInCart;
import common.ItemList;
import common.Message1;
import common.MessageType;
import common.MyFile;
import common.OptionalIngredients;
import common.OrderDish;
import common.OrdersForRes;
import common.OrdersList;
import common.Refund;
import common.Resturant;
import common.ResturantForBM;
import common.Resturants;
import common.Selection;
import common.TybeMeal;
import common.Users;
import common.W4C_Card;
import common.waiting_account;
import controllers.AddEmployerController;
import controllers.AddItemController;
import controllers.CreatePrivateAccountController;
import controllers.EditOptionalIngredientsController;
import controllers.WaitingOrdersController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/** majd
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************
	//customer
	//public static ArrayList<OrdersForRes> LisenerObservableList =new ArrayList<>() ;
	public static Users userlogged;
	public static W4C_Card w4ccard;
	public static Account accounts;
	public static Business bussiness;
	public static OrdersList order;
	public static OrdersList order2;
	public static ItemList items;
	public static ArrayList<Resturants> resturants;
	public static ArrayList<Resturants> allresturants;
	public static Resturants res;
	public static ArrayList<TybeMeal> tybemeal;
	public static ArrayList<Dish> dish;
	public static ArrayList<Selection> selection;
	public static ArrayList<OrdersList> OrderBuild ;
	public static ArrayList<ItemList> ItemBuild ;
	public static Refund getRefund;
	public static Account GetCustomerDetails;
	//resturant
	public static Resturant resturant;
	public static ArrayList<DishForResturant> dishes;
	public static ArrayList<OrdersForRes> orders;
	public static ArrayList<OptionalIngredients> optionalIngredients;
	public static ArrayList<OrdersForRes> WaitingOrders;
	public static ArrayList<OrderDish> OrdersDishes;
	//hr
	public static ArrayList<waiting_account> WaitingAccounts;
	public static HoumanResources HR;
	////branchmanger&ceo
	public static ResturantForBM resturantForBM;
	public static ArrayList<Object> dataforaccount;
	public static ArrayList<Object> temp;
	public static ArrayList<Employer> notApprovedEmployers;
	public static ArrayList<Users> users;
	public static ArrayList<ResturantForBM> resturantsForBM;
	public static ArrayList<String> dataForFiles;
	public static HashMap<String, ArrayList<String>> hashMap;
	public static ArrayList<BranchQaurter> branchQaurters;
	public static ArrayList<BranchManager> branchManagers;
	public static ArrayList<HistogramValues>histogramValues;
	public static MyFile myfile;
	public static boolean uploaded;
	public static ArrayList<DataForIncome> dataForIncomes;
	
	
	
	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;

	public static boolean awaitResponse = false;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		// openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	@SuppressWarnings("unchecked")
	public void handleMessageFromServer(Object msg) {
		
		awaitResponse = false;
		System.out.println("--> handleMessageFromServer");
		Message1 m = (Message1) msg;
		
		//System.out.println((String) m.getObject());

		if (m.getMessageType().equals(MessageType.login)) {
			ArrayList<Object> arr=(ArrayList<Object>)m.getObject();
			userlogged = (Users)(arr.get(0));
			if(m.getObject()==null)
				userlogged=null;
			else {
				switch (userlogged.getType()) {
				case Supplier:
					resturant=(Resturant)arr.get(1);
					break;
				case HR:
					HR=(HoumanResources)arr.get(1);
					break;
				default:
					break;
				}
			
			}
			
		}
		
		switch (m.getMessageType()) {
		case scan :
			accounts = (Account) m.getObject(); 
			break;
		case getResName:
			res=(Resturants)m.getObject();
			break;
		case w4cCard:
			w4ccard = (W4C_Card) m.getObject();
			break;
		case ViewResturants:
			resturants=(ArrayList<Resturants>)m.getObject();
			break;
		case ViewAllResturants:
			allresturants=(ArrayList<Resturants>)m.getObject();
			break;
		case ViewTybeMeallist:
			tybemeal=(ArrayList<TybeMeal>)m.getObject();
			break;
		case ViewDishList:
			dish=(ArrayList<Dish>)m.getObject();
			break;
		case ViewSelctionsList:
			selection=(ArrayList<Selection>)m.getObject();
			break;
		case bussinessAccounts:
			bussiness=(Business)m.getObject();
			break;
		case OrdersListToDataBase:
			order=(OrdersList)m.getObject();
			break;
		case GetOrder:
			order2=(OrdersList)m.getObject();
			break;
		case itemsListtoDataBase:
			items=(ItemList)m.getObject();
			break;
		case OrderListBuild:
			OrderBuild = (ArrayList<OrdersList>)m.getObject();
			break;
		case ItemList:
			ItemBuild = (ArrayList<ItemList>)m.getObject();
			break;
		case getRefund:
			getRefund=(Refund)m.getObject();
			break;
		case getCustomer:
			GetCustomerDetails= (Account) m.getObject(); 
			break;
		case getDishesFromResturant:
			 dishes = (ArrayList<DishForResturant>)m.getObject();
			break;
			
		case getOptionalIngredients:
			optionalIngredients=(ArrayList<OptionalIngredients>)m.getObject();
			break;
			
		case additem:
			AddItemController.add=(boolean)m.getObject();
			break;
		case AddOption:
			EditOptionalIngredientsController.AddOption=(boolean)m.getObject();
			break;
			
		case UpdateItem:
			EditOptionalIngredientsController.Update1=(boolean)m.getObject();
			break;
		case GetResturantOrders:
			orders=(ArrayList<OrdersForRes>)m.getObject();
			break;
		case GetWaitingOrders:
			WaitingOrders=(ArrayList<OrdersForRes>)m.getObject();
			break;
		case approveOrder:
			WaitingOrdersController.approvebool=(Boolean)m.getObject();
			break;
		case GetOrdersDishes:
			OrdersDishes=(ArrayList<OrderDish>)m.getObject();
			break;
		case GetEmployees:
			WaitingAccounts = (ArrayList<waiting_account>)m.getObject();
			break;
		case AddEmployer:
			AddEmployerController.added=(Boolean)m.getObject();
			break;
			///////////////////////abosale7
		case addPrivateAccount:
			CreatePrivateAccountController.val = (boolean) m.getObject();
			// CreatePrivateAccountController.message=(String)m.getObject();
			break;

		case getDataForAccount:
			dataforaccount = (ArrayList<Object>) m.getObject();
			break;
		case getDataForBusinessAccount:
			dataforaccount = (ArrayList<Object>) m.getObject();
			break;
		case addBusinessAccount:
			temp = (ArrayList<Object>) m.getObject();
			break;
		case getNotApprovedEmployers:

			notApprovedEmployers = (ArrayList<Employer>) m.getObject();
			break;
		case getDataForUser:
			temp = (ArrayList<Object>) m.getObject();
			break;
		case getUsersForChangePermission:
			users = (ArrayList<Users>) m.getObject();
			break;
		case getResturantsForBranch:
			resturantsForBM = (ArrayList<ResturantForBM>) m.getObject();
			System.out.println(resturantsForBM);
			break;
		case getIncomeFile:
			temp = (ArrayList<Object>) m.getObject();
			break;
		case getDataForIncomeFile:
			dataForIncomes=(ArrayList<DataForIncome>)m.getObject();
			break;

		case getOredersFile:
			temp = (ArrayList<Object>) m.getObject();
			break;
		case getDataForOrdersFile:
			hashMap = (HashMap<String, ArrayList<String>>) m.getObject();
			break;
		case getPerformanceFile:
			temp = (ArrayList<Object>) m.getObject();
			break;
		case getDataForPerformanceFile:
			dataForFiles = (ArrayList<String>) m.getObject();
			break;
		case uploadReport:
			uploaded = (boolean) m.getObject();

			break;
		case getQuarterlyReports:
			branchQaurters = (ArrayList<BranchQaurter>) m.getObject();
			break;
		case viewQuatrelyReport:
			myfile = (MyFile) m.getObject();
			break;
		case getAllBrancheManagers:
			branchManagers=(ArrayList<BranchManager>)m.getObject();
			break;
		case getHistogramValues:
			histogramValues=(ArrayList<HistogramValues>)m.getObject();
			break;
			
		default:
			break;
		}
		
		
		
		
		

		
		
		
	
		
	
		}
	



	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(Object message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
