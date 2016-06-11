import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BSIDatabase {
	private static Connection conn;
	private List<Inventory> invList;
	private List<Stock> stockList;
	private List<PriceHistory> priceList;
	private List<Wish> wishList;
	
	/**
	 * Initializes inventory system.
	 */
	public BSIDatabase() {
		try {
			invList = getInventory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Connect to database.
	 * 
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localHost/Book_Store", "root", "");

		System.out.println("Connected to database");
	}
	
	/**
	 * Retrieves inventory list.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Inventory> getInventory() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT * FROM inventoryview ORDER BY inventoryNum DESC";
		invList = new ArrayList<Inventory>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int invNum = rs.getInt("inventoryNum");
				String isbn = rs.getString("ISBN");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String genre = rs.getString("genre");
				double price = rs.getDouble("storePrice");
				String condition = rs.getString("condition");
				String stockDate = rs.getString("inStockDate");
				String saleDate = rs.getString("saleDate");
				Inventory book = new Inventory(invNum, isbn, title, author, genre, price,
											condition, stockDate, saleDate);
				invList.add(book);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return invList;
	}
	
	/**
	 * Retrieves wish list.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Wish> getWish() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT * FROM wishview";
		wishList = new ArrayList<Wish>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("customerName");
				String phone = rs.getString("customerPhone");
				String title = rs.getString("title");
				String author = rs.getString("Author");
				Wish book = new Wish(name, phone, title, author);
				wishList.add(book);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return wishList;
	}
	
	/**
	 * Inserts a wishlist entry.
	 * 
	 * @param customer
	 * @param phone
	 * @param title
	 * @param last
	 * @param first
	 * @param isbn
	 * @throws SQLException
	 */
	public void makeWish(String customer, String phone, String title, String last,
				String first, String isbn) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "CALL makeAWish('" + customer + "', '" + phone + "', '" + 
						title + "', '" + last + "', '" + first + "', '" + isbn + "');";
		stockList = new ArrayList<Stock>();
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(query);
		
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	/**
	 * Retrieves store stock.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Stock> getStock() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT * FROM stockview ORDER BY section DESC";
		stockList = new ArrayList<Stock>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int invNum = rs.getInt("inventoryNum");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String genre = rs.getString("genre");
				double price = rs.getDouble("storePrice");
				String section = rs.getString("section");
				Stock book = new Stock(invNum, title, author, genre, price,
											section);
				stockList.add(book);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return stockList;
	}
	
	/**
	 * Retrieves a book's price history.
	 * 
	 * @param search
	 * @return
	 * @throws SQLException
	 */
	public List<PriceHistory> getPriceHistory(String search) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		int goodData = 0;
		String query2 = "select count(*) goodData from book where isbn = '"+search+"';";
		Statement stmt2 = null;
		ResultSet rs;
		try {
			stmt2 = conn.createStatement();
			rs = stmt2.executeQuery(query2);
			while (rs.next()) {
				goodData = rs.getInt("goodData");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (goodData > 0) {
			Statement stmt = null;
			String query = "SELECT * FROM priceHistory WHERE Book_ISBN = '" + search + 
					"' ORDER BY priceDate ;";
			priceList = new ArrayList<PriceHistory>();
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				
				while (rs.next()) {
					String isbn = rs.getString("Book_ISBN");
					double listPrice  = rs.getDouble("listPrice");
					String condition = rs.getString("condition");
					double storePrice = rs.getDouble("storePrice");
					String priceDate = rs.getString("priceDate");
					PriceHistory hist = new PriceHistory(isbn, condition, priceDate, storePrice,
											listPrice);
					priceList.add(hist);
				}
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}


		} else {
			
			priceList = new ArrayList<PriceHistory>();
			priceList.add(new PriceHistory("0", "0", "0", 0, 0));
		}
		return priceList;
	}
	
	/**
	 * Prices a book.
	 * 
	 * @param num
	 * @param cond
	 * @param price
	 * @throws SQLException
	 */
	public void priceBook(String num, String cond, double price) throws SQLException {
		if (conn == null) {
			createConnection();
		}
			Statement stmt = null;
			String query = "CALL PriceBook('" + num + "', '" + cond + "', " + price + ");";
			priceList = new ArrayList<PriceHistory>();
			try {
				stmt = conn.createStatement();
				stmt.executeQuery(query);
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
	}
	
	/**
	 * Search by ISBN.
	 * 
	 * @param search
	 * @return
	 * @throws SQLException
	 */
	public List<Stock> searchIsbn(String search) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT inventoryNum, book.title, Author, genre, storePrice, section FROM stockview JOIN Book ON stockview.title = book.title AND stockview.author = CONCAT(book.authorLast, ', ', book.authorFirst) WHERE book.ISBN = " + search + ";";
		stockList = new ArrayList<Stock>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int invNum = rs.getInt("inventoryNum");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String genre = rs.getString("genre");
				double price = rs.getDouble("storePrice");
				String section = rs.getString("section");
				Stock book = new Stock(invNum, title, author, genre, price,
											section);
				stockList.add(book);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return stockList;
	}
	
	/**
	 * Search by Title.
	 * 
	 * @param search
	 * @return
	 * @throws SQLException
	 */
	public List<Stock> searchTitle(String search) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		
		String query = "SELECT * FROM stockview WHERE title = '" + search + "';";
		stockList = new ArrayList<Stock>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int invNum = rs.getInt("inventoryNum");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String genre = rs.getString("genre");
				double price = rs.getDouble("storePrice");
				String section = rs.getString("section");
				Stock book = new Stock(invNum, title, author, genre, price,
											section);
				stockList.add(book);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return stockList;
	}
	
	/**
	 * Search by Author.
	 * 
	 * @param search
	 * @return
	 * @throws SQLException
	 */
	public List<Stock> searchAuthor(String search) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "SELECT * FROM stockview WHERE Author = '" + search + "';";
		stockList = new ArrayList<Stock>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int invNum = rs.getInt("inventoryNum");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String genre = rs.getString("genre");
				double price = rs.getDouble("storePrice");
				String section = rs.getString("section");
				Stock book = new Stock(invNum, title, author, genre, price,
											section);
				stockList.add(book);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return stockList;
	}
	
	/**
	 * Sells a book.
	 * 
	 * @param search
	 * @throws SQLException
	 */
	public void sellBook(int search) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "CALL sellBook(" + search + ");";
		invList = new ArrayList<Inventory>();
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	/**
	 * Add a book to database.
	 * 
	 * @param isbn
	 * @param title
	 * @param last
	 * @param first
	 * @param price
	 * @param genre
	 * @throws SQLException
	 */
	public void addBook(String isbn, String title, String last, 
			String first, double price, String genre) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "CALL addBook('" + isbn + "', '" + title + "', '" + last 
					+ "', '" + first + "', " + price + ", '" + genre + "'); ";
		invList = new ArrayList<Inventory>();
		try {
			stmt = conn.createStatement();
			stmt.executeQuery(query);

		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}
}


