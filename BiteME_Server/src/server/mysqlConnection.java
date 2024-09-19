package server;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.print.attribute.standard.DateTimeAtCompleted;

import common.*;

/**
 * @author mblac
 *
 */
/**
 * @author Majd Msallam 
 *
 */
public class mysqlConnection {
	static Connection conn;
	public static String db_name;
	public static String db_user;
	public static String db_pass;
	public static Integer PaCkage;
	public static LocalDateTime now = LocalDateTime.now();

	public static void connectToDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection(db_name, db_user, db_pass);
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		System.out.println("SQL connection succeed");

		Thread t1 = new Thread(() -> {
			int dayofmonth = now.getDayOfMonth();
			while (true) {
				LocalDateTime now2 = LocalDateTime.now();

				if (dayofmonth == 29) {
					updateMonthlyceiling();
					dayofmonth = 1;

				}
				if (dayofmonth == now2.getDayOfMonth()) {
					dayofmonth++;
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		);

		t1.start();

	}

	public static void updateMonthlyceiling() {
		PreparedStatement ps;
		PreparedStatement ps1;
		ResultSet res;

		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT staticCeiling FROM bitemedb.business");
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {
			} else {
				ps1 = mysqlConnection.conn.prepareStatement("update business set Ceiling =staticCeiling");
				ps1.execute();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Resturants getresname(String Idres) {
		PreparedStatement ps;
		ResultSet res;
		Resturants rest = null;
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.resturants where ResturantId =?");
			ps.setString(1, Idres);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next())
				return rest;
			rest = new Resturants(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
					res.getString(5), res.getString(6), res.getString(7), res.getString(8));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rest;

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Object checkUserLogIn(String userName, String passWord) {
		PreparedStatement ps;
		ResultSet res;
		ArrayList<Object> arr = new ArrayList<>();
		Users user = null;
		try {
			ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.users where username=? and password=?");
			ps.setString(1, userName);
			ps.setString(2, passWord);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next())
				return null;
			boolean isLogged = res.getInt(4) == 1 ? true : false;
			user = new Users(res.getString(1), res.getString(2), res.getString(3), isLogged,
					UserType.valueOf(res.getString(5)), res.getString(6), res.getString(7), res.getString(8),
					res.getString(9), res.getString(10));
			System.out.println(res.toString());
			if (!user.getStatus().equals("Frozen")) {
				if (!user.getType().equals(UserType.UnKnown) && !user.getType().equals(UserType.Waiting)
						&& !user.getType().equals(UserType.Employer)) {
					ps = mysqlConnection.conn
							.prepareStatement("UPDATE bitemedb.users SET IsLoggedIN =1 where username=?");
					ps.setString(1, userName);
					ps.execute();
				}

			}
			arr.add(user);/// 0
			/////////////////////////////
			switch (user.getType()) {
			case Supplier:
				Resturant resturant = null;
				System.out.println(user.getId());
				ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.resturants where ResturantId=?");
				ps.setString(1, user.getId());
				ps.execute();
				res = ps.getResultSet();
				System.out.println(res.next());
				resturant = new Resturant(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7), res.getString(8));
				System.out.println(resturant);
				arr.add(resturant);/////// 1
				break;
			case HR:
				HoumanResources HR = null;
				ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.human_resources where ID=?");
				ps.setString(1, user.getId());
				ps.execute();
				res = ps.getResultSet();
				res.next();

				HR = new HoumanResources(res.getString(1), res.getString(2));
				arr.add(HR);////////// 2
				break;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	public static void importExternalDB() {
		PreparedStatement ps, ps1;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.external");
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				ps1 = mysqlConnection.conn.prepareStatement("insert into bitemedb.users values (?,?,?,?,?,?,?,?,?,?)");
				ps1.setString(1, res.getString(1));
				ps1.setString(2, res.getString(2));
				ps1.setString(3, res.getString(3));
				ps1.setInt(4, 0);
				ps1.setString(5, res.getString(8));
				ps1.setString(6, res.getString(4));
				ps1.setString(7, res.getString(5));
				ps1.setString(8, res.getString(6));
				ps1.setString(9, res.getString(7));
				ps1.setString(10, "Active");
				ps1.execute();
			}
			ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.external_hr");
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				ps1 = mysqlConnection.conn.prepareStatement("insert into bitemedb.human_resources values (?,?)");
				ps1.setString(1, res.getString(1));
				ps1.setString(2, res.getString(2));
				ps1.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Business getBussinessInformationfromDB(String w4c_code) {
		PreparedStatement ps;
		ResultSet res;
		Business business = null;
		try {
			ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.business where w4cCode=?");
			ps.setString(1, w4c_code);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next())
				return null;
			business = new Business(res.getString(1), res.getString(2), res.getString(3), res.getString(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return business;
	}

	public static W4C_Card getW4cInformationfromDB(String w4c_code) {
		PreparedStatement ps;
		ResultSet res;
		W4C_Card w4c = null;
		try {
			ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.w4c_card where W4CCode=?");
			ps.setString(1, w4c_code);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next())
				return null;
			w4c = new W4C_Card(res.getString(1), res.getString(2), res.getString(3));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return w4c;
	}

	public static void SetItemsOfOrderinDB(String theMeal, String theDish, String ingredient, Integer quantity,
			Integer price, String packageID) {
		PreparedStatement ps;
		try {
			ps = mysqlConnection.conn.prepareStatement("Insert into bitemedb.item_list values (?,?,?,?,?,?)");
			ps.setString(1, theMeal);
			ps.setString(2, theDish);
			ps.setString(3, ingredient);
			ps.setInt(4, quantity);
			ps.setInt(5, price);
			ps.setString(6, packageID);
			ps.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setOrderinDB(String customer_ID, String resturant, String requestDate, String orderedDate,
			String totalPrice, String address, String deleiveryService, String status, String arrivalTime,
			String approvalRecieving, String branchlocation, String year, String month, String day,
			String arrivedToCustomerTime, String orderReadyTime) {
		PreparedStatement ps;
		try {
			ps = mysqlConnection.conn
					.prepareStatement("Insert Into bitemedb.order_list (Customer_ID, Resturant, RequestDate,"
							+ " OrderDate, TotalPrice, Address, DeleiveryService, Status, ArrivalTime, ApprovalRecieving,"
							+ "Branch,year,month,day,ArrivedToCustomerTime,OrderReadyTime) Values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, customer_ID);
			ps.setString(2, resturant);
			ps.setString(3, requestDate);
			ps.setString(4, orderedDate);
			ps.setString(5, totalPrice);
			ps.setString(6, address);
			ps.setString(7, deleiveryService);
			ps.setInt(8, 3);
			ps.setString(9, arrivalTime);
			ps.setInt(10, 2);
			ps.setString(11, branchlocation);
			ps.setString(12, year);
			ps.setString(13, month);
			ps.setInt(14, Integer.valueOf(day));
			ps.setString(15, arrivedToCustomerTime);
			ps.setString(16, orderReadyTime);
			ps.execute();

			// res=ps.getResultSet();
			// System.out.println();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void updatecelling(String Celling, String w4ccode) {
		PreparedStatement ps;
		try {
			ps = mysqlConnection.conn.prepareStatement("update bitemedb.business set Ceiling=? where w4cCode=?");
			ps.setString(1, Celling);
			ps.setString(2, w4ccode);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static OrdersList getOrder(String CustomerId) {
		PreparedStatement ps;
		ResultSet res;
		int max = 0;
		OrdersList order = null;
		try {
			ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.order_list where Customer_ID=?");
			ps.setString(1, CustomerId);
			ps.execute();

			res = ps.getResultSet();
			while (res.next()) {
				if (res.getInt(3) > max)
					max = res.getInt(3);
			}

			ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.order_list where OrderPackageNumber=?");
			ps.setInt(1, max);
			ps.execute();
			res = ps.getResultSet();
			res.next();
			System.out.println(res.getInt(3));
			order = new OrdersList(res.getString(1), res.getString(2), res.getInt(3), res.getString(4),
					res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9),
					res.getString(10), res.getString(11), res.getString(12), res.getString(13), res.getString(14),
					res.getInt(15), res.getString(16), res.getString(17));

			return order;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}

	public static Account getAccountListFromDB(String ID) {
		Account account = null;
		PreparedStatement ps;
		ResultSet res;
		try {

			ps = mysqlConnection.conn.prepareStatement("Select * From bitemedb.account where ID=?");
			ps.setString(1, ID);
			ps.execute();

			res = ps.getResultSet();
			if (!res.next())
				return null;
			account = new Account(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
					res.getString(5), res.getString(6), res.getString(7));
			return account;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static ArrayList<TybeMeal> getTybeMealListFromDB(String ResturantID) {
		ArrayList<TybeMeal> tybemeal = new ArrayList<TybeMeal>();
		TybeMeal temp;
		PreparedStatement ps;
		ResultSet res;
		try {

			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.typemeal where ResturantId=?");
			ps.setString(1, ResturantID);
			ps.execute();

			res = ps.getResultSet();
			while (res.next()) {
				temp = new TybeMeal(res.getString(1), res.getString(2), res.getString(3));
				tybemeal.add(temp);
			}
			res.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tybemeal;

	}

	public static ArrayList<Dish> getDishListFromDB(String TybeMealID) {
		ArrayList<Dish> Dish = new ArrayList<Dish>();
		Dish temp;
		PreparedStatement ps;
		ResultSet res;
		try {

			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.dish where TypemealId=?");
			ps.setString(1, TybeMealID);
			ps.execute();

			res = ps.getResultSet();
			while (res.next()) {
				temp = new Dish(res.getString(1), res.getString(2), res.getString(3), res.getInt(4));
				Dish.add(temp);
			}
			res.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Dish;

	}

	public static ArrayList<Selection> getSelectionListFromDB(String DishID) {
		ArrayList<Selection> selection = new ArrayList<Selection>();
		Selection temp;
		PreparedStatement ps;
		ResultSet res;
		try {

			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.selection where DishId=?");
			ps.setString(1, DishID);
			ps.execute();

			res = ps.getResultSet();
			while (res.next()) {
				temp = new Selection(res.getString(1), res.getString(2), res.getString(3), res.getInt(4));
				selection.add(temp);
			}
			res.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selection;

	}

	public static ArrayList<Resturants> GetAllResturants() {
		ArrayList<Resturants> list = new ArrayList<Resturants>();
		Resturants temp;
		Statement statment;
		ResultSet res;
		try {
			statment = mysqlConnection.conn.createStatement();
			res = statment.executeQuery("SELECT * FROM bitemedb.resturants");
			while (res.next()) {
				temp = new Resturants(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7), res.getString(8));
				list.add(temp);
			}
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<Resturants> getResturantsListFromDB(String Location) {
		ArrayList<Resturants> list = new ArrayList<Resturants>();
		Resturants temp;
		Statement statment;
		ResultSet res;
		try {
			statment = mysqlConnection.conn.createStatement();
			res = statment.executeQuery("SELECT * FROM bitemedb.resturants");
			while (res.next()) {
				if (res.getString(6).equals(Location)) {
					temp = new Resturants(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
							res.getString(5), res.getString(6), res.getString(7), res.getString(8));
					list.add(temp);
				}
			}
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static ArrayList<ItemList> BuildItemList(Integer OrderPackageNumber) {
		ArrayList<ItemList> Item_list = new ArrayList<ItemList>();
		ResultSet res;
		Connection conn;
		String PackageID;
		try {
			conn = mysqlConnection.conn;
			res = conn.createStatement().executeQuery("Select * from  bitemedb.item_list");
			while (res.next()) {
				PackageID = (res.getString(6));
				if (PackageID.equals(String.valueOf(OrderPackageNumber))) {
					Item_list.add(new ItemList(res.getString(1), res.getString(2), res.getString(3),
							Integer.parseInt(res.getString(4)), Integer.parseInt(res.getString(5)), (res.getInt(6))));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Item_list;
	}

	public static ArrayList<OrdersList> BuildOrderTable(String CustomerID) {
		// TABLE VIEW AND DATA
		ArrayList<OrdersList> Order_list = new ArrayList<OrdersList>();
		PreparedStatement ps;
		ResultSet res;

		try {
			ps = mysqlConnection.conn.prepareStatement("Select * from bitemedb.order_list where Customer_ID =? ");
			ps.setString(1, CustomerID);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				Order_list.add(new OrdersList(res.getString(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9),
						res.getString(10), res.getString(11), res.getString(12), res.getString(13), res.getString(14),
						res.getInt(15), res.getString(16), res.getString(17)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Order_list;
	}

	public static void EditBuildOrderTable(String Package, String customerId) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		PreparedStatement ps;
		String time = dtf.format(now);
		System.out.println(time);
		try {

			ps = mysqlConnection.conn
					.prepareStatement("update bitemedb.order_list set Status=? where OrderPackageNumber =? ");
			ps.setInt(1, 4);
			ps.setInt(2, Integer.valueOf(Package));
			ps.execute();
			System.out.println(11);
			ps = mysqlConnection.conn.prepareStatement(
					"update bitemedb.order_list set ArrivedToCustomerTime=? where OrderPackageNumber =? ");
			ps.setString(1, time);
			ps.setInt(2, Integer.valueOf(Package));
			ps.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void RefundBuild(String CustomerID, String ResturantID, String refund) {
		PreparedStatement ps;
		PreparedStatement ps1;
		ResultSet res;
		ResultSet res2;
		int Sum;
		try {
			ps = mysqlConnection.conn
					.prepareStatement("SELECT * FROM bitemedb.refund where Customer_ID =? and ResturantId =?");
			ps.setString(1, CustomerID);
			ps.setString(2, ResturantID);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) { // if next null
				ps1 = mysqlConnection.conn.prepareStatement("Insert into bitemedb.refund Values (?,?,?)");
				ps1.setString(1, CustomerID);
				ps1.setString(2, ResturantID);
				ps1.setString(3, refund);
				ps1.execute();
				ps1 = mysqlConnection.conn
						.prepareStatement("SELECT * FROM bitemedb.refund where Customer_ID =? and ResturantId =?");
				ps1.setString(1, CustomerID);
				ps1.setString(2, ResturantID);
				ps1.execute();
				res2 = ps.getResultSet();

			} else {
				Sum = Integer.valueOf(res.getString(3)) + Integer.valueOf(refund);
				ps1 = mysqlConnection.conn.prepareStatement(
						"update bitemedb.refund set Refund=? where Customer_ID =? and ResturantId =? ");
				ps1.setString(1, String.valueOf(Sum));
				ps1.setString(2, CustomerID);
				ps1.setString(3, ResturantID);
				ps1.execute();
				ps1 = mysqlConnection.conn
						.prepareStatement("SELECT * FROM bitemedb.refund where Customer_ID =? and ResturantId =?");
				ps1.setString(1, CustomerID);
				ps1.setString(2, ResturantID);
				ps1.execute();
				res2 = ps.getResultSet();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Refund getRefundFromdb(String customerid, String resturantid) {
		PreparedStatement ps;
		PreparedStatement ps1;
		ResultSet res;
		ResultSet res2;
		Refund ref = null;
		try {
			ps = mysqlConnection.conn
					.prepareStatement("SELECT * FROM bitemedb.refund where Customer_ID =? and ResturantId =?");
			System.out.println(customerid);
			ps.setString(1, customerid);
			ps.setString(2, resturantid);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {
				ps1 = mysqlConnection.conn.prepareStatement("Insert into bitemedb.refund Values (?,?,?)");
				System.out.println(customerid);
				ps1.setString(1, customerid);
				ps1.setString(2, resturantid);
				ps1.setString(3, String.valueOf(0));
				ps1.execute();
				ps1 = mysqlConnection.conn
						.prepareStatement("SELECT * FROM bitemedb.refund where Customer_ID =? and ResturantId =?");
				ps1.setString(1, customerid);
				ps1.setString(2, resturantid);
				ps1.execute();
				res2 = ps1.getResultSet();
				res2.next();
				ref = new Refund(res2.getString(1), res2.getString(2), res2.getString(3));
			} else
				ref = new Refund(res.getString(1), res.getString(2), res.getString(3));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ref.getCustomerID());
		return ref;
	}

	public static void UpdateRefund(String customerid, String resturantid, String ref) {
		PreparedStatement ps;
		ResultSet res;
		try {
			System.out.println(ref);
			ps = mysqlConnection.conn
					.prepareStatement("update bitemedb.refund set Refund=? where Customer_ID =? and ResturantId =? ");
			ps.setString(1, ref);
			ps.setString(2, customerid);
			ps.setString(3, resturantid);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void userLogOut(Users user) {
		PreparedStatement ps;
		try {
			ps = mysqlConnection.conn.prepareStatement("UPDATE bitemedb.users SET isLoggedIn =0 where username=?");
			ps.setString(1, user.getUserName());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * this method select from type meal table in DB the whole types that descends
	 * to the restaurant that has this ID and then in a loop while there is more
	 * types to this restaurant select the dishes of this type from dish table and
	 * in another loop while there is more dishes go and select the optional
	 * ingredients of this dish and then enter to another loop while there is more
	 * optional ingredients insert them to string builder and in the end initial new
	 * dish parameter with the details that we got from the serial loops and add it
	 * to dishes array list
	 * 
	 * @param resturantId
	 * @return ArrayList<DishForRes>
	 */
	public static ArrayList<DishForResturant> getResturantDishes(String resturantId) {

		PreparedStatement ps, ps1, ps2;
		ResultSet res, res1, res2;
		String TypeID, DishID;
		ArrayList<DishForResturant> dishes = new ArrayList<DishForResturant>();
		DishForResturant dish;
		StringBuilder stringBuilder = new StringBuilder();//////////////////////////////////////////////////////////////////////////

		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.typemeal where ResturantId=?");
			ps.setString(1, resturantId);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {// while there are more types for this restaurant
				TypeID = res.getString(2);
				ps1 = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.dish where Typemealid=?");
				ps1.setString(1, TypeID);
				ps1.execute();
				res1 = ps1.getResultSet();
				while (res1.next()) {
					DishID = res1.getString(2);
					ps2 = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.selection where DishId=?");
					ps2.setString(1, DishID);
					ps2.execute();
					res2 = ps2.getResultSet();
					while (res2.next()) {
						if (res2.getInt(4) == 0) {
							stringBuilder.append(res2.getString(3));/////////////////////////////////////////////////////
							stringBuilder.append("\n");
						} else {
							stringBuilder.append(res2.getString(3) + " " + res2.getInt(4));
							stringBuilder.append("\n");
						}

					}
					res2.close();

					dish = new DishForResturant(resturantId, res.getString(3), res1.getString(3),
							stringBuilder.toString(), res1.getString(2), res1.getInt(4));
					dishes.add(dish);
					stringBuilder = new StringBuilder();

				} // while res1 dishes

			} // while res types
		} catch (SQLException e) {
			e.printStackTrace();
		} /////////////////////////////////////////////////////////////////////////////////////////////

		return dishes;
	}

	/**
	 * first it saves the type ID of this Dish this method delete from selection
	 * table the ingredients of this dish then delete the dish from Dish table and
	 * then checks if there is no more dishes in Dish table that has the same type
	 * ID it will delete the type
	 * 
	 * @param dish
	 */
	public static void deleteDish(DishForResturant dish) {
		PreparedStatement ps, ps1;
		ResultSet res, res1;
		String typeID;

		try {
			System.out.println(123);
			System.out.println(Integer.valueOf(dish.getMealId()));
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.dish where DishId=?");
			ps.setInt(1, Integer.valueOf(dish.getMealId()));
			ps.execute();
			res = ps.getResultSet();
			res.next();
			typeID = res.getString(1);
			System.out.println(res.getInt(1));

			//
			////
			//

			////////////////////////////////////////////// we don't dalete the types becuase
			////////////////////////////////////////////// there is only one appearing in DB
			////////////////////////////////////////////// if we delete it we will lose all
			////////////////////////////////////////////// of the dishes
			///////////////////////////////////////////// for getting the menu the program
			////////////////////////////////////////////// os not taking types with no
			////////////////////////////////////////////// dishes(so it can't affect on the
			////////////////////////////////////////////// menu view) :)..
			ps = mysqlConnection.conn.prepareStatement("DELETE FROM bitemedb.selection WHERE DishId=?");
			ps.setString(1, dish.getMealId());
			ps.execute();
			ps = mysqlConnection.conn.prepareStatement("DELETE FROM bitemedb.dish WHERE DishId=?");
			ps.setString(1, dish.getMealId());
			ps.execute();

			ps1 = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.dish where TypemealId=?");
			ps1.setString(1, typeID);///////////
			ps1.execute();////////
			res1 = ps1.getResultSet();

			if (!res1.next()) {
				ps = mysqlConnection.conn.prepareStatement("DELETE FROM bitemedb.typemeal WHERE TypemealId=?");
				ps.setInt(1, Integer.valueOf(typeID));
				ps.execute();
			}

			res1.close();
			res.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * this method select all of the ingredients in selection table that has the
	 * same dish ID and add them to and array list
	 * 
	 * @param dishID the ID of the dish
	 * @return ArrayList<OptionalIngredients>
	 */
	public static ArrayList<OptionalIngredients> getOptionalIngredients(String dishID) {
		PreparedStatement ps;
		ResultSet res;
		ArrayList<OptionalIngredients> optionalIngredients = new ArrayList<OptionalIngredients>();
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.selection where dishID=?");
			ps.setString(1, dishID);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				optionalIngredients
						.add(new OptionalIngredients(res.getString(3), res.getInt(4), dishID, res.getInt(2)));
			}
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return optionalIngredients;
	}

	/**
	 * 
	 * this method checks if there is meal type like this to the same restaurant in
	 * type meal table if no it will add it as new type if there is one like this it
	 * will take its ID and insert the new dish to dish table with this typeID and
	 * then take the id of this dish and add the optional ingredients to selection
	 * table type ID and dish ID are serial(auto increment) in the DATA base
	 * 
	 * @param dish the new dish we want to add
	 * @return boolean
	 */
	public static boolean AddItem(DishForResturant dish) {// we have the restaurant id!!
		PreparedStatement ps, ps1, ps2;
		ResultSet res, res1;
		String[] split = dish.getOptionalIngredients().split("\n");
		try {

			ps = mysqlConnection.conn
					.prepareStatement("SELECT * FROM bitemedb.typemeal where ResturantId=? and TybeMeal=?");
			ps.setString(1, dish.getResturantId());
			ps.setString(2, dish.getMealType());
			ps.execute();
			res = ps.getResultSet();// getting the the meal type from DB be in case it exists

			if (!res.next()) {// if there is no type like this we adding it to TypeMeal table
				ps = mysqlConnection.conn
						.prepareStatement("insert into bitemedb.typemeal(ResturantId,TybeMeal) values(?,?)");
				ps.setString(1, dish.getResturantId());
				ps.setString(2, dish.getMealType());
				ps.execute();
			}

			ps = mysqlConnection.conn
					.prepareStatement("SELECT * FROM bitemedb.typemeal where ResturantId=? and TybeMeal=?");
			ps.setString(1, dish.getResturantId());
			ps.setString(2, dish.getMealType());/////// get the new type meal id
			ps.execute();

			res = ps.getResultSet();
			res.next();
			ps1 = mysqlConnection.conn
					.prepareStatement("insert into bitemedb.dish(TypemealId,Dish,DishPrice) values(?,?,?)");
			ps1.setString(1, res.getString(2));// get the meal type id/////////////
			ps1.setString(2, dish.getMealName());
			ps1.setInt(3, dish.getPrice());// add the dish and its price,, here we have new dish id that we didn't saved
											// yet
			ps1.execute();

			ps1 = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.dish where TypemealId=? and Dish=?");
			ps1.setInt(1, res.getInt(2));
			ps1.setString(2, dish.getMealName());
			ps1.execute();

			res1 = ps1.getResultSet();// get the new dish id
			res1.next();

			if (!dish.getOptionalIngredients().equals("")) {
				for (int i = 0; i < split.length; i++) {
					String[] split2 = split[i].split(",");
					ps2 = mysqlConnection.conn.prepareStatement(
							"insert into bitemedb.selection (DishId,Selection,SelectionPrice) values(?,?,?)");
					ps2.setString(1, res1.getString(2));
					ps2.setString(2, split2[0]);
					ps2.setString(3, split2[1]);
					ps2.execute();

				}
			}
			res.close();
			res1.close();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}

		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * this method add new optional ingredient to a dish accordeing to it's ID to
	 * selection table
	 * 
	 * @param Option the new option we want to add
	 * @return
	 */
	public static boolean AddOption(OptionalIngredients Option) {
		PreparedStatement ps;
		try {
			ps = mysqlConnection.conn
					.prepareStatement("insert into bitemedb.selection (DishId,Selection,SelectionPrice) values(?,?,?)");
			ps.setString(1, Option.getDishID());
			ps.setString(2, Option.getOption());
			ps.setInt(3, Option.getPrice());
			ps.execute();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * this message delete from selection table the row the has selection id like
	 * this
	 * 
	 * @param Option the option ID
	 */
	public static void deleteOption(int Option) {
		PreparedStatement ps;

		try {
			ps = mysqlConnection.conn.prepareStatement("DELETE FROM bitemedb.selection WHERE SelectionId=?");
			ps.setInt(1, Option);
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * this method updates dish details in data base where there are the same ID
	 * 
	 * @param dish the dish with new details but same id
	 * @return
	 */
	public static Boolean updateItem(DishForResturant dish) {
		PreparedStatement ps;
		ResultSet res;

		try {

			// ps= mysqlConnection.conn.prepareStatement("update bitemedb.order_list set
			// ApprovalRecieving=? ,ApproveTime=? where OrderPackageNumber=?");
			ps = mysqlConnection.conn.prepareStatement(" update bitemedb.dish set Dish=?,DishPrice=? where DishId=? ");
			ps.setString(1, dish.getMealName());
			ps.setInt(2, dish.getPrice());
			ps.setString(3, dish.getMealId());
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();

		}

		return true;
	}//////////////////////////////////////// update

	/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * the method select from order list all the approved orders with the same
	 * restaurant location and add them to an array list
	 * 
	 * @param resturant the restaurant that we want to get it's orders
	 * @return ArrayList<OrdersForRes>
	 */
	public static ArrayList<OrdersForRes> GetResturantOrders(Resturant resturant) {
		ArrayList<OrdersForRes> orders = new ArrayList<>();
		PreparedStatement ps;
		ResultSet res;

		try {

			ps = mysqlConnection.conn.prepareStatement(
					"SELECT * FROM bitemedb.order_list where Resturant=? and ApprovalRecieving=? and Branch=?");
			ps.setString(1, resturant.getId());
			ps.setString(2, "Approved");
			ps.setString(3, resturant.getBranch());
			ps.execute();
			res = ps.getResultSet();

			while (res.next()) {

				orders.add(new OrdersForRes(res.getString(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9),
						res.getString(10), res.getString(11), res.getString(12), res.getString(13), res.getString(14),
						res.getInt(15), res.getString(16), res.getString(17)));

			}
			res.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return orders;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * the method update the approval status the orders that has this number to
	 * approved(by restaurant) and the status to 'in progress' if its not approved
	 * 
	 * @param OrderNum the order number
	 * @return boolean
	 */
	public static boolean ApproveOrder(int OrderNum) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.order_list where OrderPackageNumber=?");
			ps.setInt(1, OrderNum);
			ps.execute();
			res = ps.getResultSet();
			res.next();
			if (res.getString(11).equals("Approved"))
				return false;

			ps = mysqlConnection.conn.prepareStatement(
					"update bitemedb.order_list set ApprovalRecieving=? ,Status=?  where OrderPackageNumber=?");
			ps.setString(1, "Approved");
			ps.setInt(2, 2);
			ps.setInt(3, OrderNum);
			ps.execute();

			// UPDATE `bitemedb`.`dish` SET `dishprice` = ? WHERE (`dishID` = ?)
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * the method select all of the rows from item_list table that has PackageID
	 * like this number and add them to an array list
	 * 
	 * @param OrderNum the order number
	 * @return ArrayList<OrderDish>
	 */
	public static ArrayList<OrderDish> GetOrdersDishes(int OrderNum) {
		ArrayList<OrderDish> OrderDishes = new ArrayList<>();
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("select * from bitemedb.item_list where PackageID=?");
			ps.setInt(1, OrderNum);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				OrderDishes.add(new OrderDish(res.getString(1), res.getString(2), res.getString(3), res.getInt(5),
						res.getInt(4)));

			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return OrderDishes;

	}

	/**
	 * the method update the status of the order that has this number to ready(in
	 * order_list table)
	 * 
	 * @param OrderNumber order number
	 * @param Status      the wanted status
	 */
	public static void UpdateStatus(int OrderNumber, String Status) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		PreparedStatement ps;
		String time = dtf.format(now);
		try {
			ps = mysqlConnection.conn
					.prepareStatement("Update bitemedb.order_list set Status=? where OrderPackageNumber=? ");
			ps.setString(1, Status);
			ps.setInt(2, OrderNumber);
			ps.execute();
			ps = mysqlConnection.conn
					.prepareStatement("Update bitemedb.order_list set OrderReadyTime=? where OrderPackageNumber=? ");
			ps.setString(1, time);
			ps.setInt(2, OrderNumber);
			ps.execute();

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * the method select all the accounts from waiting_accounts table that has the
	 * same company name
	 * 
	 * @param CompanyName
	 * @return ArrayList<waiting_account>
	 */
	public static ArrayList<waiting_account> GetWaitingAccounts(String CompanyName) {

		PreparedStatement ps;
		ResultSet res;
		ArrayList<waiting_account> accounts = new ArrayList<>();
		try {
			ps = mysqlConnection.conn.prepareStatement("select * from bitemedb.waiting_accounts where CompanyName=?");
			ps.setString(1, CompanyName);
			ps.execute();
			res = ps.getResultSet();

			while (res.next()) {
				accounts.add(new waiting_account(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), CompanyName, res.getString(7), res.getString(8), res.getInt(9),
						res.getString(10)));
				System.out.println(res.getString(6) + res.getString(1));
			}

			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	/**
	 * the method delete the account from waiting accounts table and then add it to
	 * w4c table if its not there else it update its type to business then get its
	 * w4c code and add it to account table and business table
	 * 
	 * @param account the account we want to approve
	 */
	public static void ApproveBusinessAccount(waiting_account account) {

		PreparedStatement ps;
		ResultSet res;
		String W4C;
		try {

			ps = mysqlConnection.conn.prepareStatement("delete from bitemedb.waiting_accounts where ID=? ");
			ps.setString(1, account.getID());
			ps.execute();

			ps = mysqlConnection.conn.prepareStatement("select * from bitemedb.w4c_card where CreditCard=?");
			ps.setString(1, account.getCreditCard());
			ps.execute();
			res = ps.getResultSet();

			if (!res.next()) {// if there is no private account for this customer

				ps = mysqlConnection.conn
						.prepareStatement("insert into bitemedb.w4c_card (CreditCard,accounttype) values(?,?)");
				ps.setString(1, account.getCreditCard());
				ps.setString(2, "business");
				ps.execute();

				ps = mysqlConnection.conn.prepareStatement("select * from bitemedb.w4c_card where CreditCard=?");
				ps.setString(1, account.getCreditCard());
				ps.execute();
				res = ps.getResultSet();
				res.next();
				W4C = String.valueOf(res.getInt(1));

			} else {// if there is private account for this customer
				W4C = String.valueOf(res.getInt(1));
				ps = mysqlConnection.conn
						.prepareStatement("update bitemedb.w4c_card set accounttype=? where W4CCode=? ");
				ps.setString(1, "business");
				ps.setString(2, W4C);
				ps.execute();

			}

			ps = mysqlConnection.conn.prepareStatement("insert into bitemedb.account values(?,?,?,?,?,?,?)");
			ps.setString(1, account.getID());
			ps.setString(2, account.getFirstName());
			ps.setString(3, account.getLastName());
			ps.setString(4, account.getPhoneNumber());
			ps.setString(5, account.getEmail());
			ps.setString(6, W4C);
			ps.setString(7, account.getAddress());//////////////////////// add this with another '?'
			ps.execute();

			ps = mysqlConnection.conn.prepareStatement("insert into bitemedb.business values(?,?,?,?,?)");
			ps.setString(1, W4C);
			ps.setString(2, account.getEmplyerID());
			ps.setString(3, account.getCompanyName());
			ps.setString(4, String.valueOf(account.getCeiling()));
			ps.setString(5, String.valueOf(account.getCeiling()));
			ps.execute();

			ps = mysqlConnection.conn.prepareStatement("update bitemedb.users set userType=? where ID=?");
			ps.setString(1, "Customer");
			ps.setString(2, account.getID());
			ps.execute();///////////////// add to Mohammad

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * the method check if there is an empty user(unknown type) with this ID and
	 * update it type to employer and insert his details to employer table
	 * 
	 * @param ID   employer ID
	 * @param Name employer name
	 * @param HRID human resource manager
	 * @return boolean
	 */
	public static boolean AddEmployer(String ID, String Name, String HRID) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("select * from bitemedb.users where ID=? and userType=? ");
			ps.setString(1, ID);
			ps.setString(2, "UnKnown");
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {
				return false;
			} else {
				ps = mysqlConnection.conn.prepareStatement("update bitemedb.users set userType=? where ID=?");
				ps.setString(1, "Employer");
				ps.setString(2, ID);
				ps.execute();

				ps = mysqlConnection.conn.prepareStatement("insert into bitemedb.employer values(?,?,?,?)");
				ps.setString(1, ID);
				ps.setString(2, Name);
				ps.setString(3, HRID);
				ps.setInt(4, 0);
				ps.execute();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * the method get all the orders from order_list table the has the same
	 * restaurant ID , branch and there status is waiting for approve and add them
	 * to an array list
	 * 
	 * @param resturant the restaurant that we want to get its orders
	 * @return ArrayList<OrdersForRes>
	 */
	public static ArrayList<OrdersForRes> GetWaitingOrders(Resturant resturant) {

		ArrayList<OrdersForRes> orders = new ArrayList<>();
		PreparedStatement ps;
		ResultSet res;

		try {

			ps = mysqlConnection.conn.prepareStatement(
					"SELECT * FROM bitemedb.order_list where Resturant=? and ApprovalRecieving=? and Branch=?");
			ps.setString(1, resturant.getId());
			ps.setString(2, "Waiting for approve");
			ps.setString(3, resturant.getBranch());
			ps.execute();
			res = ps.getResultSet();

			while (res.next()) {

				orders.add(new OrdersForRes(res.getString(1), res.getString(2), res.getInt(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9),
						res.getString(10), res.getString(11), res.getString(12), res.getString(13), res.getString(14),
						res.getInt(15), res.getString(16), res.getString(17)));

			}
			res.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return orders;
	}

	public static Account getCustomerDetails(String customerId) {
		PreparedStatement ps;
		ResultSet res;
		Account cus = null;
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * From bitemedb.account Where ID=?");
			ps.setString(1, customerId);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next())
				return null;
			cus = new Account(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5),
					res.getString(6), res.getString(7));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cus;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* abosale7 */

	/**
	 * functionality: this method checks if there is an account with the same id
	 * that was deleted by the same branch, if yes the boolean value will be false
	 * else check if there is an existing account with the same id ,if yes the
	 * boolean value will be false else check if the id is existed in database, if
	 * not the boolean value will be false else the method gets the user's data from
	 * the database by the user id and return all the relevant data
	 * 
	 * @param ID:user   id
	 * @param branchID: branch manager id
	 * @return false with relevant message or true with the user's data
	 */
	public static ArrayList<Object> getDataForAccount(String ID, String branchID) {
		PreparedStatement ps;
		ResultSet res;
		Users user;
		ArrayList<Object> arr = new ArrayList<Object>();

		///// this could be deleted, duplicated code
		try {
			ps = mysqlConnection.conn
					.prepareStatement("SELECT * FROM bitemedb.deleted_customers where customerID=? and branchID=?");
			ps.setString(1, ID);
			ps.setString(2, branchID);
			ps.execute();
			res = ps.getResultSet();
			if (res.next()) {
				arr.add(false);
				arr.add("this customer has been deleted from this branch");
				return arr;
			}
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.account where ID=?");
			ps.setString(1, ID);
			ps.execute();
			res = ps.getResultSet();
			if (res.next()) {
				arr.add(false);
				arr.add("There is account with the same id");
				return arr;
			}
			////////////////////
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.users where ID=?");
			ps.setString(1, ID);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {
				arr.add(false);
				arr.add("There is no user with this id");
				return arr;
			} else {
				boolean isLogged = res.getInt(4) == 1 ? true : false;
				user = new Users(res.getString(1), res.getString(2), res.getString(3), isLogged,
						UserType.valueOf(res.getString(5)), res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10));
				if (!user.getType().equals(UserType.UnKnown)) {
					arr.add(false);
					arr.add("there is an account for this user");
					return arr;
				}
				arr.add(true);
				arr.add(user);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * functionality: this method checks if the credit card number is used for
	 * another user in database, if yes it returns false , else the method add the
	 * user as private account to the relevant tables in database
	 * 
	 * @param user:             object the holds all the user's data
	 * @param creditCardNumber: credit card number for the user
	 * @param location:         the location of the user
	 * @return boolean
	 */
	public static boolean addPrivateAccount(Users user, String creditCardNumber, String location) {
		PreparedStatement ps;
		ResultSet res;
		try {

			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.w4c_card where CreditCard= ?");
			ps.setString(1, creditCardNumber);
			ps.execute();
			res = ps.getResultSet();
			if (res.next())
				return false;
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.waiting_accounts where CridetCard= ?");
			ps.setString(1, creditCardNumber);
			ps.execute();
			res = ps.getResultSet();
			if (res.next())
				return false;

			ps = mysqlConnection.conn
					.prepareStatement("INSERT INTO bitemedb.w4c_card (CreditCard, accounttype) VALUES (?, 'private')");
			ps.setString(1, creditCardNumber);
			ps.execute();
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.w4c_card where CreditCard= ?");
			ps.setString(1, creditCardNumber);
			ps.execute();
			res = ps.getResultSet();
			res.next();
			String w4c = res.getString(1);
			ps = mysqlConnection.conn.prepareStatement("INSERT INTO bitemedb.account VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, user.getId());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getLastName());
			ps.setString(4, user.getPhoneNumber());
			ps.setString(5, user.getEmail());
			ps.setString(6, w4c);
			ps.setString(7, location);
			ps.execute();
			ps = mysqlConnection.conn.prepareStatement("UPDATE bitemedb.users SET `userType` = 'Customer' WHERE ID =?");
			ps.setString(1, user.getId());
			ps.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Functionality: this method checks if there is an account with the same id
	 * that was deleted by the same branch, if yes the boolean value will be false
	 * else check if there is already an existing account waiting for approval with
	 * the same id, if yes the boolean value will be false, else checks if there is
	 * an existing business account with the same id , if yes the boolean value will
	 * be false, else check if there is a user with this id , if not the boolean value
	 * will be false, else checks if the user's status does not equal to unknown, if yes
	 * the boolean value will be false, else the boolean value will be true and the method
	 * gets the user's data from the database by the user id and return all the relevant data
	 * @param ID: user's id
	 * @param branchID: branch manager id
	 * @return false with relevant message or true with user's data
	 */
	public static ArrayList<Object> getDataForBusinessAccount(String ID, String branchID) {
		PreparedStatement ps, ps1;
		ResultSet res, res1;
		Users user;

		ArrayList<Object> arr = new ArrayList<Object>();
		try {// waiting_accounts

			ps = mysqlConnection.conn
					.prepareStatement("SELECT * FROM bitemedb.deleted_customers where customerID=? and branchID=?");
			ps.setString(1, ID);
			ps.setString(2, branchID);
			ps.execute();
			res = ps.getResultSet();
			if (res.next()) {
				arr.add(false);
				arr.add("this customer has been deleted from this branch");
				return arr;
			}
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.waiting_accounts where ID=?");
			ps.setString(1, ID);
			ps.execute();
			res = ps.getResultSet();
			if (res.next()) {
				arr.add(false);
				arr.add("There is already Business account that's waiting for approval");
				return arr;
			}
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.account where ID=?");
			ps.setString(1, ID);
			ps.execute();
			res = ps.getResultSet();

			if (res.next()) {
				System.out.println(res.getString(6));
				ps1 = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.w4c_card where W4CCode=?");
				ps1.setString(1, res.getString(6));
				ps1.execute();
				res1 = ps1.getResultSet();
				if (res1.next()) {
					System.out.println(res1.getString(3));

					if (res1.getString(3).equals("Business")) {
						arr.add(false);
						arr.add("There is already Business account with this ID");
						return arr;
					}
				}
			}
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.users where ID=?");
			ps.setString(1, ID);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {
				arr.add(false);
				arr.add("There is no user with this id");
				return arr;
			} else {
				boolean isLogged = res.getInt(4) == 1 ? true : false;
				user = new Users(res.getString(1), res.getString(2), res.getString(3), isLogged,
						UserType.valueOf(res.getString(5)), res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10));
				if (!user.getType().equals(UserType.UnKnown)) {
					arr.add(false);
					arr.add("there is an account for this user");
					return arr;
				}
				arr.add(true);
				arr.add(user);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * Functionality: this method checks if there is an employer with the same id and company name
	 * if not the boolean value will be false, else checks if the credit card number is associated with
	 * another business account, if yes the boolean value will be false, else the method checks if the
	 * credit card number is associated with a business account in the waiting list, if yes the boolean
	 * value will be false, else the method adds the user to the waiting list with the relevant data 
	 * and the boolean value will be true
	 * @param user: object that holds all the user data
	 * @param creditCardNumber: credit card number of the user
	 * @param companyName: company that the user works for
	 * @param EmployerID: the employers ID
	 * @param ceiling: the max amount of money the business account can use in a month from his company
	 * @param location: the location of the user
	 * @return false with a relevant message or true with a relevant message
	 */
	public static ArrayList<Object> addBusinessAccount(Users user, String creditCardNumber, String companyName,
			String EmployerID, int ceiling, String location) {
		PreparedStatement ps;
		ResultSet res;
		ArrayList<Object> arr = new ArrayList<Object>();

		try {

			ps = mysqlConnection.conn.prepareStatement(
					"SELECT * FROM bitemedb.employer where EmployerID=? and CompanyName=? and isApproved=1");
			ps.setString(1, EmployerID);
			ps.setString(2, companyName);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {
				arr.add(false);
				arr.add("there is no data for this employer in our system");
				return arr;
			}
			ps.execute();
			ps = mysqlConnection.conn
					.prepareStatement("SELECT * FROM bitemedb.w4c_card where CreditCard= ? and accounttype='business'");
			ps.setString(1, creditCardNumber);
			ps.execute();
			res = ps.getResultSet();
			if (res.next()) {
				arr.add(false);
				arr.add("this cridet card already taken for existing business account");
				return arr;
			}
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.waiting_accounts where CridetCard= ?");
			ps.setString(1, creditCardNumber);
			ps.execute();
			res = ps.getResultSet();
			if (res.next()) {
				arr.add(false);
				arr.add("this cridet card already used in the waiting list");
				return arr;
			}

			// ;
			ps = mysqlConnection.conn.prepareStatement("UPDATE bitemedb.users SET userType = 'Waiting' WHERE ID = ?");
			ps.setString(1, user.getId());
			ps.execute();
			ps = mysqlConnection.conn
					.prepareStatement("INSERT INTO bitemedb.waiting_accounts VALUES (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, user.getId());
			ps.setString(2, user.getFirstName());
			ps.setString(3, user.getLastName());
			ps.setString(4, user.getPhoneNumber());
			ps.setString(5, user.getEmail());
			ps.setString(6, companyName);
			ps.setString(7, EmployerID);
			ps.setString(8, creditCardNumber);
			ps.setInt(9, ceiling);
			ps.setString(10, location);
			ps.execute();
			arr.add(true);
			arr.add("your account is waiting for approval");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * Functionality: this method checks if there is a user with "ID", if no the boolean value will
	 * be false, else if the status does not equal to unknown the boolean value will be false
	 * else, the the boolean value will be true 
	 * @param ID: user's ID
	 * @return false with a relevant message or true with user object
	 */
	public static ArrayList<Object> getDataForUser(String ID) {
		PreparedStatement ps;
		ResultSet res;
		Users user;
		ArrayList<Object> arr = new ArrayList<Object>();
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.users where ID=?");
			ps.setString(1, ID);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {
				arr.add(false);
				arr.add("There is no user with this id");
				return arr;
			} else {
				boolean isLogged = res.getInt(4) == 1 ? true : false;
				user = new Users(res.getString(1), res.getString(2), res.getString(3), isLogged,
						UserType.valueOf(res.getString(5)), res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10));
				if (!user.getType().equals(UserType.UnKnown)) {
					arr.add(false);
					arr.add("there is an account for this user");
					return arr;
				}
				arr.add(true);
				arr.add(user);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * functionality: this method gets all the employers that still not approved
	 * (isApproved =0) from the employers table from the data base,and returns them
	 * 
	 * @return list of not approved employers
	 */
	public static ArrayList<Employer> getNotApprovedEmployers() {
		ArrayList<Employer> employers = new ArrayList<Employer>();
		Employer employer;
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.employer where isApproved=0");
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				employer = new Employer(res.getString(1), res.getString(2), res.getString(3), false);
				employers.add(employer);
			}
			res.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employers;
	}

	/**
	 * functionality: this method updates the isApproved value to 1 in the data base
	 * 
	 * @param employer: object the holds all the employer's data
	 */
	public static void approveEmployer(Employer employer) {
		PreparedStatement ps;
		try {
			ps = mysqlConnection.conn
					.prepareStatement("UPDATE bitemedb.employer SET isApproved = '1' WHERE EmployerID = ?");
			ps.setString(1, employer.getEmployerID());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * functionality:this method is executed whe the
	 * 
	 * @param employer: object the holds all the employer's data
	 */
	public static void declineEmployer(Employer employer) {
		PreparedStatement ps;
		//
		try {
			ps = mysqlConnection.conn.prepareStatement("DELETE FROM bitemedb.employer WHERE EmployerID = ?");
			ps.setString(1, employer.getEmployerID());
			ps.execute();
			ps = mysqlConnection.conn.prepareStatement("UPDATE bitemedb.users SET userType = 'UnKnown' WHERE ID = ?");
			ps.setString(1, employer.getEmployerID());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Functionality: this method uses the parameters and update the data about the restaurant
	 * in the database, also updates its usertype to 'Supplier'
	 * @param user: user object
	 * @param resturantName: the restaurant name
	 * @param phoneNumber: phone number of the restaurant 
	 * @param location: location of the restaurant
	 * @param branchID: the ID of the branch manager of the restaurant
	 */
	public static void resturantRegistration(Users user, String resturantName, String phoneNumber, String location,
			String branchID) {
		PreparedStatement ps;
		try {

			ps = mysqlConnection.conn.prepareStatement("INSERT INTO bitemedb.resturants VALUES (?,?,?,?,?,?,?,?)");
			ps.setString(1, resturantName);
			ps.setString(2, "Open");
			ps.setString(3, phoneNumber);
			ps.setString(4, user.getId());
			ps.setString(5, branchID);
			ps.setString(6, location);
			String[] str = LocalDate.now().toString().split("-");
			ps.setString(7, str[0]);
			ps.setString(8, str[1]);
			ps.execute();
			ps = mysqlConnection.conn.prepareStatement("UPDATE bitemedb.users SET userType = 'Supplier' WHERE ID = ?");
			ps.setString(1, user.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * functionality: this method changes the status of the user to the new status
	 * value
	 * 
	 * @param userID:    string the holds the user id
	 * @param newStatus: the status that replaces the old status, it can be "Active"
	 *                   or "Frozen"
	 */
	public static ArrayList<Users> getUsersForChangePermission() {
		ArrayList<Users> arr = new ArrayList<Users>();
		Users user;
		// SELECT * FROM bitemedb.users where userType="Customer" or
		// userType="Supplier";
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.users where userType='Customer'");
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				boolean isLogged = res.getInt(4) == 1 ? true : false;
				user = new Users(res.getString(1), res.getString(2), res.getString(3), isLogged,
						UserType.valueOf(res.getString(5)), res.getString(6), res.getString(7), res.getString(8),
						res.getString(9), res.getString(10));
				arr.add(user);
			}
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	public static void changePermission(String userID, String newStatus) {
		PreparedStatement ps;
		try {
			ps = mysqlConnection.conn.prepareStatement("UPDATE bitemedb.users SET status =? WHERE ID = ?");
			ps.setString(1, newStatus);
			ps.setString(2, userID);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Functionality: this method in general deletes all the data about a certain user
	 * by accessing the relevant data about him and deleting it, also it updates its usertype 
	 * to 'UnKnown' and inserting his ID and BranchID into the deleted customers table
	 * @param userID: the ID of the user we want to delete
	 * @param branchID: the ID of the Branch manager who will delete the user
	 */
	public static void deleteCustomer(String userID, String branchID) {
		PreparedStatement ps1;
		ResultSet res1;
		String accountType;
		try {
			ps1 = mysqlConnection.conn.prepareStatement("select * from bitemedb.account where ID=?");
			ps1.setString(1, userID);
			ps1.execute();
			res1 = ps1.getResultSet();
			res1.next();
			ps1 = mysqlConnection.conn.prepareStatement("select * from bitemedb.w4c_card where W4CCode=?");
			ps1.setInt(1, res1.getInt(6));
			ps1.execute();
			res1 = ps1.getResultSet();
			res1.next();
			accountType = res1.getString(3);
			// DELETE FROM `bitemedb`.`w4c_card` WHERE (`W4CCode` = '2');
			ps1 = mysqlConnection.conn.prepareStatement("DELETE FROM bitemedb.w4c_card WHERE W4CCode = ?");
			ps1.setInt(1, res1.getInt(1));
			ps1.execute();

			if (accountType.equals("Business")) {
				ps1 = mysqlConnection.conn.prepareStatement("DELETE FROM bitemedb.business WHERE w4cCode = ?");
				ps1.setInt(1, res1.getInt(1));
				ps1.execute();
			}

			ps1 = mysqlConnection.conn.prepareStatement("DELETE FROM bitemedb.account WHERE ID = ?");
			ps1.setString(1, userID);
			ps1.execute();
			// ;
			ps1 = mysqlConnection.conn.prepareStatement("UPDATE bitemedb.users SET userType = 'UnKnown' WHERE ID = ?");
			ps1.setString(1, userID);
			ps1.execute();
			ps1 = mysqlConnection.conn.prepareStatement("INSERT INTO bitemedb.deleted_customers VALUES (?,?)");
			ps1.setString(1, userID);
			ps1.setString(2, branchID);
			ps1.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * functionality: this method gets all the restaurants that relevant to a
	 * specific branch manager
	 * 
	 * @param branchID: a string that holds the id of a specific branch manager
	 * @return list of restaurants
	 */
	public static ArrayList<ResturantForBM> getResturantsForBranch(String branchID) {
		ArrayList<ResturantForBM> arr = new ArrayList<ResturantForBM>();
		ResturantForBM resturant;
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.resturants where branchID=?");
			ps.setString(1, branchID);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				boolean isOpen = res.getString(2).equals("Open");
				resturant = new ResturantForBM(res.getString(1), isOpen, res.getString(3), res.getString(4), branchID,
						res.getString(6), Integer.parseInt(res.getString(7)), Integer.parseInt(res.getString(8)));
				arr.add(resturant);
			}
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(arr);
		return arr;
	}

	/**
	 * Functionality: this method checks if the file is existed in the database
	 * if no, it returns false, else it will return the requested file
	 * @param fileName name of the requested file
	 * @return false, requested file
	 */
	public static ArrayList<Object> getIncomeFile(String fileName) {
		PreparedStatement ps;
		ResultSet res;
		ArrayList<Object> arr = new ArrayList<Object>();
		MyFile myFile = new MyFile(fileName);
		byte[] mybytearray;
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.income where fileName=?");
			ps.setString(1, fileName);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {// if file not exist
				arr.add(false);
				return arr;
			}
			arr.add(true);
			mybytearray = new byte[(int) res.getBlob(6).length()];
			mybytearray = res.getBytes(6);
			myFile.initArray(mybytearray.length);
			myFile.setSize(mybytearray.length);
			myFile.setMybytearray(mybytearray);
			arr.add(myFile);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * Functionality: this method generates a text file that contains data about monthly income for 
	 * each supplier divided into weeks of the month, each week contains the orders with their price
	 * and the sum of all the orders in the same week, in the end it has the total income for the month 
	 * @param resurantID: requested restaurant ID
	 * @param year: year of the report
	 * @param month:month of the report
	 * @return the content of the file as an arraylist
	 */
	public static ArrayList<DataForIncome> getDataForIncomeFile(String resurantID, String year, String month) {
		ArrayList<DataForIncome> arr = new ArrayList<DataForIncome>();
		PreparedStatement ps;
		ResultSet res;
		int sum = 0, rev_week = 0;
		int numberOfOrders = 0;
		try {
			ps = mysqlConnection.conn.prepareStatement(
					"SELECT * FROM bitemedb.order_list where Resturant=? and year=? and month=? and day >=1 and day<=7");
			ps.setString(1, resurantID);
			ps.setString(2, year);
			ps.setString(3, month);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				numberOfOrders++;
				rev_week += Integer.parseInt(res.getString(6));

			}
			arr.add(new DataForIncome("week 1", numberOfOrders, rev_week));
			rev_week = 0;
			numberOfOrders = 0;
			ps = mysqlConnection.conn.prepareStatement(
					"SELECT * FROM bitemedb.order_list where Resturant=? and year=? and month=? and day >=8 and day<=14");
			ps.setString(1, resurantID);
			ps.setString(2, year);
			ps.setString(3, month);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				numberOfOrders++;
				rev_week += Integer.parseInt(res.getString(6));
			}
			arr.add(new DataForIncome("week 2", numberOfOrders, rev_week));
			rev_week = 0;
			numberOfOrders = 0;
			ps = mysqlConnection.conn.prepareStatement(
					"SELECT * FROM bitemedb.order_list where Resturant=? and year=? and month=? and day >=15 and day<=21");
			ps.setString(1, resurantID);
			ps.setString(2, year);
			ps.setString(3, month);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				numberOfOrders++;
				rev_week += Integer.parseInt(res.getString(6));
			}
			arr.add(new DataForIncome("week 3", numberOfOrders, rev_week));
			rev_week = 0;
			numberOfOrders = 0;
			ps = mysqlConnection.conn.prepareStatement(
					"SELECT * FROM bitemedb.order_list where Resturant=? and year=? and month=? and day >=22 and day<=28");
			ps.setString(1, resurantID);
			ps.setString(2, year);
			ps.setString(3, month);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				numberOfOrders++;
				rev_week += Integer.parseInt(res.getString(6));
			}
			arr.add(new DataForIncome("week 4", numberOfOrders, rev_week));
			rev_week = 0;
			numberOfOrders = 0;
			ps = mysqlConnection.conn.prepareStatement(
					"SELECT * FROM bitemedb.order_list where Resturant=? and year=? and month=? and day >=29 and day<=31");
			ps.setString(1, resurantID);
			ps.setString(2, year);
			ps.setString(3, month);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				numberOfOrders++;
				rev_week += Integer.parseInt(res.getString(6));
			}
			arr.add(new DataForIncome("week 5", numberOfOrders, rev_week));
			rev_week = 0;
			numberOfOrders = 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * Functionality: this method saves the created income file as a blob in the database 
	 * @param branchID: the ID of the branch manager 
	 * @param resturantName: name of the restaurant that requested the report
	 * @param month: month of the requested report
	 * @param year: year of the requested report
	 * @param fileName: file name to save it
	 * @param inputStream: the requested file as bytes
	 */
	public static void saveIncomeFile(String branchID, String resturantName, int month, int year, String fileName,
			InputStream inputStream) {
		PreparedStatement ps;

		try {
			ps = mysqlConnection.conn.prepareStatement("INSERT INTO bitemedb.income values(?,?,?,?,?,?)");
			ps.setString(1, branchID);
			ps.setString(2, resturantName);
			ps.setInt(3, month);
			ps.setInt(4, year);
			ps.setString(5, fileName);
			ps.setBlob(6, inputStream);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Functionality: this method checks if the file is existed in the database
	 * if no, it returns false, else it will return the requested file
	 * @param fileName name of the requested file
	 * @return false, requested file
	 */
	public static ArrayList<Object> getOredersFile(String fileName) {
		PreparedStatement ps;
		ResultSet res;
		ArrayList<Object> arr = new ArrayList<Object>();
		MyFile myFile = new MyFile(fileName);
		byte[] mybytearray;
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.orders_reports where fileName=?");
			ps.setString(1, fileName);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {// if file not exist
				arr.add(false);
				return arr;
			}
			arr.add(true);
			mybytearray = new byte[(int) res.getBlob(6).length()];
			mybytearray = res.getBytes(6);
			myFile.initArray(mybytearray.length);
			myFile.setSize(mybytearray.length);
			myFile.setMybytearray(mybytearray);
			arr.add(myFile);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * Functionality: this method gets all the relevant data about the orders that are done in 
	 * certain month and year and sort them using hash map by dividing them into the type of meal
	 * of each order
	 * @param resurantID: ID of the requested restaurant for the report
	 * @param year: year of the requested report
	 * @param month: month of the requested report
	 * @return
	 */
	public static HashMap<String, ArrayList<String>> getDataForOrdersFile(String resurantID, String year,
			String month) {
		HashMap<String, ArrayList<String>> hashMap = new HashMap<String, ArrayList<String>>();
		PreparedStatement ps1, ps2;
		ResultSet res1, res2;
		try {
			ps1 = mysqlConnection.conn
					.prepareStatement("SELECT * FROM bitemedb.order_list where Resturant=? and year=? and month=?");
			ps1.setString(1, resurantID);
			ps1.setString(2, year);
			ps1.setString(3, month);
			ps1.execute();
			res1 = ps1.getResultSet();
			while (res1.next()) {
				System.out.println("pack " + res1.getInt(3));
				ps2 = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.item_list where packageID=?");
				ps2.setInt(1, res1.getInt(3));
				ps2.execute();
				res2 = ps2.getResultSet();
				if (res2.next()) {
					if (!hashMap.keySet().contains(res2.getString(1))) {
						hashMap.put(res2.getString(1), new ArrayList<String>());
					}
					hashMap.get(res2.getString(1)).add("PackageID: " + res2.getInt(6));
					// res2.close();
				}

			}
			res1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashMap;
	}

	/**
	 * Functionality: this method saves the created income file as a blob in the database 
	 * @param branchID: the ID of the branch manager 
	 * @param resturantName: name of the restaurant that requested the report
	 * @param month: month of the requested report
	 * @param year: year of the requested report
	 * @param fileName: file name to save it
	 * @param inputStream: the requested file as bytes
	 */
	public static void saveOrdersFile(String branchID, String resturantName, int month, int year, String fileName,
			InputStream inputStream) {
		PreparedStatement ps;

		try {
			ps = mysqlConnection.conn.prepareStatement("INSERT INTO bitemedb.orders_reports values(?,?,?,?,?,?)");
			ps.setString(1, branchID);
			ps.setString(2, resturantName);
			ps.setInt(3, month);
			ps.setInt(4, year);
			ps.setString(5, fileName);
			ps.setBlob(6, inputStream);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Functionality: this method checks if the file is existed in the database
	 * if no, it returns false, else it will return the requested file
	 * @param fileName name of the requested file
	 * @return false, requested file
	 */
	public static ArrayList<Object> getPerformanceFile(String fileName) {
		PreparedStatement ps;
		ResultSet res;
		ArrayList<Object> arr = new ArrayList<Object>();
		MyFile myFile = new MyFile(fileName);
		byte[] mybytearray;
		try {
			ps = mysqlConnection.conn.prepareStatement("SELECT * FROM bitemedb.performance_reports where fileName=?");
			ps.setString(1, fileName);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {// if file not exist
				arr.add(false);
				return arr;
			}
			arr.add(true);
			mybytearray = new byte[(int) res.getBlob(6).length()];
			mybytearray = res.getBytes(6);
			myFile.initArray(mybytearray.length);
			myFile.setSize(mybytearray.length);
			myFile.setMybytearray(mybytearray);
			arr.add(myFile);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * Functionality: this method gets all the relevant data about the orders that are done in 
	 * certain month and year and and calculates the percentage of the late orders 
	 * also it calculates the average time of preparing an order and saves it it arraylist
	 * @param resurantID: ID restaurant that requested the report 
	 * @param year: year of the requested report
	 * @param month: month of the requested report
	 * @return arraylist with the relevant info
	 */
	public static ArrayList<String> getDataForPerformanceFile(String resurantID, String year, String month) {
		ArrayList<String> arr = new ArrayList<String>();
		PreparedStatement ps;
		ResultSet res;
		int temp = 0;
		int numberOfOrders = 0;
		int late = 0;
		try {
			ps = mysqlConnection.conn
					.prepareStatement("SELECT * FROM bitemedb.order_list where Resturant=? and year=? and month=?");
			ps.setString(1, resurantID);
			ps.setString(2, year);
			ps.setString(3, month);
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				numberOfOrders++;
				Time arrivalToCustomerTime = res.getTime(16);
				Time requestedTime = res.getTime(4);
				@SuppressWarnings("deprecation")

				LocalTime localTime = LocalTime.of(arrivalToCustomerTime.getHours(), arrivalToCustomerTime.getMinutes(),
						arrivalToCustomerTime.getSeconds());

				if (localTime.minusHours(requestedTime.getHours()).minusMinutes(requestedTime.getMinutes())
						.minusSeconds(requestedTime.getMinutes()).getHour() >= 1)
					late++;
				Time OrderReadyTime = res.getTime(17);
				Time OrderTime = res.getTime(5);
				LocalTime localTime1 = LocalTime.of(OrderReadyTime.getHours(), OrderReadyTime.getMinutes(),
						OrderReadyTime.getSeconds());
				localTime1 = localTime1.minusHours(OrderTime.getHours()).minusMinutes(OrderTime.getMinutes())
						.minusSeconds(OrderTime.getMinutes());
				// System.out.println(localTime1.getHour()*60+localTime1.getMinute());
				temp += localTime1.getHour() * 60 + localTime1.getMinute() + 1;

			}
			res.close();
			System.out.println(temp);
			if (numberOfOrders != 0) {
				arr.add(((float) late / numberOfOrders) * 100 + "");
				temp = temp / numberOfOrders;
			}

			else
				arr.add("0");

			arr.add("Average preparation time = " + LocalTime.of(temp / 60, temp % 60));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * * Functionality: this method saves the created performance file as a blob in the database 
	 * @param branchID: the ID of the branch manager 
	 * @param resturantName: name of the restaurant that requested the report
	 * @param month: month of the requested report
	 * @param year: year of the requested report
	 * @param fileName: file name to save it
	 * @param inputStream: the requested file as bytes
	 */
	public static void savePerformanceFile(String branchID, String resturantName, int month, int year, String fileName,
			InputStream inputStream) {
		PreparedStatement ps;

		try {
			ps = mysqlConnection.conn.prepareStatement("INSERT INTO bitemedb.performance_reports values(?,?,?,?,?,?)");
			ps.setString(1, branchID);
			ps.setString(2, resturantName);
			ps.setInt(3, month);
			ps.setInt(4, year);
			ps.setString(5, fileName);
			ps.setBlob(6, inputStream);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Functionality: this method checks if the report was uploaded to quarertly_reports
	 * if yes, the boolean value will be false else, the boolean value will be true
	 * @param branchID:the ID of the branch manager 
	 * @param fileName: name of the uploaded report
	 * @param inputStream: to save the file as a bytes
	 * @param year: year of the report
	 * @param quarter : enum('first','second','third','fourth')
	 * @return boolean
	 */
	public static boolean uploadReport(String branchID, String fileName, InputStream inputStream, int year,
			String quarter) {
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement(
					"select * from quarertly_reports where branchID=? and fileName=? and year=? and quarter=?");
			ps.setString(1, branchID);
			ps.setString(2, fileName);
			ps.setInt(3, year);
			ps.setString(4, quarter);
			ps.execute();
			res = ps.getResultSet();
			if (!res.next()) {
				ps = mysqlConnection.conn.prepareStatement("insert into quarertly_reports values (?,?,?,?,?)");
				ps.setString(1, branchID);
				ps.setString(2, fileName);
				ps.setBlob(3, inputStream);
				ps.setInt(4, year);
				ps.setString(5, quarter);
				ps.execute();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("finish");
		return false;
	}

	/**
	 * Functionality: this method saves the branch manager ID and the report's year and quarter
	 * as an array list 
	 * @return array list with the relevant data about each report
	 */
	public static ArrayList<BranchQaurter> getQuarterlyReports() {
		ArrayList<BranchQaurter> arrayList = new ArrayList<BranchQaurter>();
		BranchQaurter branchQaurter;
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("select * from quarertly_reports");
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				branchQaurter = new BranchQaurter(res.getString(1), res.getInt(4), res.getString(5), res.getString(2));
				arrayList.add(branchQaurter);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayList;
	}

	/**
	 * Functionality: this method find the requested file in the database
	 * and displays it when required
	 * @param fileName: name of the requested file
	 * @return the file we want to display
	 */
	public static MyFile viewQuatrelyReport(String fileName) {

		MyFile myFile = new MyFile(fileName);
		byte[] mybytearray;
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("select * from quarertly_reports where fileName=?");
			ps.setString(1, fileName);
			ps.execute();
			res = ps.getResultSet();
			res.next();
			mybytearray = new byte[(int) res.getBlob(3).length()];
			mybytearray = res.getBytes(3);
			myFile.setFileName(res.getString(2));
			myFile.initArray(mybytearray.length);
			myFile.setSize(mybytearray.length);
			myFile.setMybytearray(mybytearray);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myFile;
	}

	/**
	 * Functionality: this method returns all the users that are
	 * branch managers and save them in an arraylist
	 * @return arraylist of branch managers
	 */
	public static ArrayList<BranchManager> getAllBrancheManagers() {
		BranchManager branchManager;
		ArrayList<BranchManager> arrayList = new ArrayList<BranchManager>();
		PreparedStatement ps;
		ResultSet res;
		try {
			ps = mysqlConnection.conn.prepareStatement("select * from users where userType='BranchManager'");
			ps.execute();
			res = ps.getResultSet();
			while (res.next()) {
				branchManager = new BranchManager(res.getString(1), res.getString(2));
				arrayList.add(branchManager);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayList;
	}

	public static ArrayList<HistogramValues> getHistogramValues(int year, int month) {
		ArrayList<HistogramValues> arrayList = new ArrayList<HistogramValues>();
		HistogramValues histogramValues;
		int sum = 0;
		PreparedStatement ps1, ps2, ps3;
		ResultSet res1, res2, res3;
		try {
			ps1 = mysqlConnection.conn.prepareStatement("select * from resturants");
			ps1.execute();
			res1 = ps1.getResultSet();
			while (res1.next()) {
				ps2 = mysqlConnection.conn.prepareStatement(
						"select * from order_list where resturant=? and year =? and(month=? or month =? or month=?)");
				ps2.setString(1, res1.getString(4));
				ps2.setInt(2, year);
				ps2.setInt(3, month);
				ps2.setInt(4, month + 1);
				ps2.setInt(5, month + 2);
				ps2.execute();
				res2 = ps2.getResultSet();
				while (res2.next()) {
					sum += Integer.parseInt(res2.getString(6));
				}
				ps3 = mysqlConnection.conn.prepareStatement(
						"select count(*) from order_list where resturant=? and year =? and(month=? or month =? or month=?)");
				ps3.setString(1, res1.getString(4));
				ps3.setInt(2, year);
				ps3.setInt(3, month);
				ps3.setInt(4, month + 1);
				ps3.setInt(5, month + 2);
				ps3.execute();
				res3 = ps3.getResultSet();
				res3.next();
				histogramValues = new HistogramValues(res1.getString(1), res3.getInt(1), sum);
				sum = 0;
				arrayList.add(histogramValues);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayList;
	}

}
