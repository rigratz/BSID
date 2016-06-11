import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


@SuppressWarnings("serial")
public class BookstoreGUI extends JFrame implements ActionListener, TableModelListener {
	private BSIDatabase db;
	private List<Inventory> invList;
	private List<Stock> stockList;
	private List<PriceHistory> priceList;
		private String priceIsbn;
		private String priceCondition;
		private double pricePrice;
		
		private List<String> searchFields;
	private List<Wish> wishList;
	
	private Object[][] data;
	private String[] invColumnNames = {"Inventory Number", "ISBN", "Title", "Author",
								"Genre", "Condition", "Store Price", "In Stock Date", 
								"Sale Date"};
	private String[] stockColumnNames = {"Inventory Number", "Title", "Author",
			"Genre", "Store Price", "Section"};
	private String[] priceColumnNames = {"ISBN", "List Price", "Condition", 
			"Store Price", "Price Date"};
	private String[] wishColumnNames = {"Customer Name", "Customer Phone", "Title",
										"Author"};
	
	private JButton btnList, btnSearch, btnPrice, btnBooks, wishButton,
					priceButton, searchButton, sellButton;
	private JPanel pnlButtons, pnlContent;

	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlSearch;
	private JButton btnTitleSearch;
	
	private JPanel pnlAdd;
	private JLabel[] bookLabel = new JLabel[6];
	private JTextField[] bookField = new JTextField[6];
	
	private JLabel sellLabel;
	private JTextField sellField;
	
	private JLabel[] wishLabel = new JLabel[6];
	private JTextField[] wishField= new JTextField[6];
	private JButton makeAWishButton;
	
	private JLabel[] searchLabel = new JLabel[3];
	private JTextField[] searchers = new JTextField[3];
	
	private JLabel[] priceLabel = new JLabel[3];
	private JTextField[] pricers = new JTextField[3];
	private JButton btnAddBook;
	
	/**
	 * Initializes the GUI.
	 */
	public BookstoreGUI() {
		super("Book Store");
		
		db = new BSIDatabase();
		try
		{
			invList = db.getInventory();
			
			data = new Object[invList.size()][invColumnNames.length];
			for (int i=0; i<invList.size(); i++) {
				data[i][0] = invList.get(i).getInv();
				data[i][1] = invList.get(i).getISBN();
				data[i][2] = invList.get(i).getTitle();
				data[i][3] = invList.get(i).getAuthor();
				data[i][4] = invList.get(i).getGenre();
				data[i][5] = invList.get(i).getCondition();
				data[i][6] = invList.get(i).getPrice();
				data[i][7] = invList.get(i).getStockDate();
				data[i][8] = invList.get(i).getSaleDate();

				
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		createComponents();
		
		setVisible(true);
		setSize(1250, 500);
		
	}
	/**
	 * Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		BookstoreGUI bsGUI = new BookstoreGUI();
		bsGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	/**
	 * Create GUI components.
	 */
	private void createComponents()
	{
		pnlButtons = new JPanel();
		btnList = new JButton("Inventory List");
		btnList.addActionListener(this);
		
		btnSearch = new JButton("Book Search");
		btnSearch.addActionListener(this);
		
		btnPrice = new JButton("Price Book");
		btnPrice.addActionListener(this);
		
		priceButton = new JButton("Enter Price");
		priceButton.addActionListener(this);
		
		searchButton = new JButton("Search Store");
		searchButton.addActionListener(this);
		
		sellButton = new JButton("Sell");
		sellButton.addActionListener(this);
		
		btnBooks = new JButton("Add Book");
		btnBooks.addActionListener(this);
		
		wishButton = new JButton("Wish List");
		wishButton.addActionListener(this);
		
		makeAWishButton = new JButton("Make A Wish");
		makeAWishButton.addActionListener(this);
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnSearch);
		pnlButtons.add(btnPrice);
		pnlButtons.add(btnBooks);
		pnlButtons.add(wishButton);
		add(pnlButtons, BorderLayout.NORTH);
		
		//Inventory Panel
		pnlContent = new JPanel();
		sellLabel = new JLabel("Enter Sold Book: ");
		sellField = new JTextField(15);
		table = new JTable(data, invColumnNames);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(700, 420));
		pnlContent.add(scrollPane);
		pnlContent.add(sellLabel);
		pnlContent.add(sellField);
		pnlContent.add(sellButton);
		table.getModel().addTableModelListener(this);
		
		//Price Panel
		pnlSearch = new JPanel();
		priceLabel[0] = new JLabel("Enter ISBN: ");
		pricers[0] = new JTextField(13);
		btnTitleSearch = new JButton("Search");
		btnTitleSearch.addActionListener(this);
		pnlSearch.add(priceLabel[0]);
		pnlSearch.add(pricers[0]);
		pnlSearch.add(btnTitleSearch);
		
		//Book Panel
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6, 0));
		String labelNames[] = {"Enter ISBN: ", "Enter Title: ", "Enter Author(Last): ",
							"Enter Author(First): ", "Enter List Price: ", "Enter Genre"};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			bookLabel[i] = new JLabel(labelNames[i]);
			bookField[i] = new JTextField(25);
			panel.add(bookLabel[i]);
			panel.add(bookField[i]);
			pnlAdd.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddBook = new JButton("Add New Book");
		btnAddBook.addActionListener(this);
		panel.add(btnAddBook);
		pnlAdd.add(panel);
		
		add(pnlContent, BorderLayout.CENTER);
		
		
	}
	
	/**
	 * Listener for table change. Currently unused.
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Event handler for GUI buttons.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnList) {
			try {
				invList = db.getInventory();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			data = new Object[invList.size()][invColumnNames.length];
			for (int i=0; i<invList.size(); i++) {
				data[i][0] = invList.get(i).getInv();
				data[i][1] = invList.get(i).getISBN();
				data[i][2] = invList.get(i).getTitle();
				data[i][3] = invList.get(i).getAuthor();
				data[i][4] = invList.get(i).getGenre();
				data[i][5] = invList.get(i).getCondition();
				data[i][6] = invList.get(i).getPrice();
				data[i][7] = invList.get(i).getStockDate();
				data[i][8] = invList.get(i).getSaleDate();
			}
			pnlContent.removeAll();
			table = new JTable(data, invColumnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(new Dimension(700, 420));
			pnlContent.add(scrollPane);
			pnlContent.add(sellLabel);
			pnlContent.add(sellField);
			pnlContent.add(sellButton);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnSearch) {
			try {
				stockList = db.getStock();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			data = new Object[stockList.size()][stockColumnNames.length];
			for (int i=0; i<stockList.size(); i++) {
				data[i][0] = stockList.get(i).getInv();
				data[i][1] = stockList.get(i).getTitle();
				data[i][2] = stockList.get(i).getAuthor();
				data[i][3] = stockList.get(i).getGenre();
				data[i][4] = stockList.get(i).getStorePrice();
				data[i][5] = stockList.get(i).getSection();
			}
			pnlContent.removeAll();
			table = new JTable(data, stockColumnNames);
			
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(new Dimension(700, 420));
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(4, 0));
			searchLabel[0] = new JLabel("Search by ISBN: ");
			searchers[0] = new JTextField(15);
			searchLabel[1] = new JLabel("Search by Author: ");
			searchers[1] = new JTextField(15);
			searchLabel[2] = new JLabel("Search by Title: ");
			searchers[2] = new JTextField(15);
			panel.add(searchLabel[0]);
			panel.add(searchers[0]);
			panel.add(searchLabel[1]);
			panel.add(searchers[1]);
			panel.add(searchLabel[2]);
			panel.add(searchers[2]);
			panel.add(searchButton);
			pnlContent.add(scrollPane);
			pnlContent.add(panel);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnPrice) {
			priceIsbn = "";
			priceCondition = "";
			pricePrice = 0;
			
			pnlContent.removeAll();
			pnlContent.add(pnlSearch);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnTitleSearch) {
			priceIsbn = pricers[0].getText();
			if (priceIsbn.length() > 0) {
				try {
					priceList = db.getPriceHistory(priceIsbn);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (priceList.size() > 0 && priceList.get(0).getIsbn().equals("0")) {
					JOptionPane.showMessageDialog(null, "Book Does Not Exist!");
				} else {
					data = new Object[priceList.size()][priceColumnNames.length];
					for (int i=0; i<priceList.size(); i++) {
						data[i][0] = priceList.get(i).getIsbn();
						data[i][1] = priceList.get(i).getListPrice();
						data[i][2] = priceList.get(i).getCondition();
						data[i][3] = priceList.get(i).getStorePrice();
						data[i][4] = priceList.get(i).getPriceDate();
					}
					pnlContent.removeAll();
					table = new JTable(data, priceColumnNames);
					table.getModel().addTableModelListener(this);
				
					scrollPane = new JScrollPane(table);
					scrollPane.setPreferredSize(new Dimension(700, 420));
					JPanel panel = new JPanel();
					panel.setLayout(new GridLayout(3, 0));
					priceLabel[1] = new JLabel("Enter Condition: ");
					pricers[1] = new JTextField(15);
					priceLabel[2] = new JLabel("Enter Price: ");
					pricers[2] = new JTextField(15);
					panel.add(priceLabel[1]);
					panel.add(pricers[1]);
					panel.add(priceLabel[2]);
					panel.add(pricers[2]);
					panel.add(priceButton);
					pnlContent.add(scrollPane);
					pnlContent.add(panel);
					pnlContent.revalidate();
					this.repaint();
				}
			}
		} else if (e.getSource() == priceButton) {
			priceCondition = pricers[1].getText();
			pricePrice = Double.parseDouble(pricers[2].getText());
			try {
				db.priceBook(priceIsbn, priceCondition, pricePrice);
				JOptionPane.showMessageDialog(null, "Book Priced!");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			pnlContent.removeAll();
			pnlSearch = new JPanel();
			priceLabel[0] = new JLabel("Enter ISBN: ");
			pricers[0] = new JTextField(13);
			btnTitleSearch = new JButton("Search");
			btnTitleSearch.addActionListener(this);
			pnlSearch.add(priceLabel[0]);
			pnlSearch.add(pricers[0]);
			pnlSearch.add(btnTitleSearch);
			pnlContent.add(pnlSearch);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == searchButton) {
			searchFields = new ArrayList<String>();
			searchFields.add(searchers[0].getText());
			searchFields.add(searchers[1].getText());
			searchFields.add(searchers[2].getText());
			try {
				if (!searchFields.get(0).equals("")) {
					stockList = db.searchIsbn(searchFields.get(0));
				} else if (!searchFields.get(1).equals("")) {
					stockList = db.searchAuthor(searchFields.get(1));
				} else if (!searchFields.get(2).equals("")) {
					stockList = db.searchTitle(searchFields.get(2));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			data = new Object[stockList.size()][stockColumnNames.length];
			for (int i=0; i<stockList.size(); i++) {
				data[i][0] = stockList.get(i).getInv();
				data[i][1] = stockList.get(i).getTitle();
				data[i][2] = stockList.get(i).getAuthor();
				data[i][3] = stockList.get(i).getGenre();
				data[i][4] = stockList.get(i).getStorePrice();
				data[i][5] = stockList.get(i).getSection();
			}
			pnlContent.removeAll();
			table = new JTable(data, stockColumnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(new Dimension(700, 420));
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(4, 0));
			searchLabel[0] = new JLabel("Search by ISBN: ");
			searchers[0] = new JTextField(15);
			searchLabel[1] = new JLabel("Search by Author: ");
			searchers[1] = new JTextField(15);
			searchLabel[2] = new JLabel("Search by Title: ");
			searchers[2] = new JTextField(15);
			panel.add(searchLabel[0]);
			panel.add(searchers[0]);
			panel.add(searchLabel[1]);
			panel.add(searchers[1]);
			panel.add(searchLabel[2]);
			panel.add(searchers[2]);
			panel.add(searchButton);
			pnlContent.add(scrollPane);
			pnlContent.add(panel);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == sellButton) {
			try {
				db.sellBook(Integer.parseInt(sellField.getText()));
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				invList = db.getInventory();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			data = new Object[invList.size()][invColumnNames.length];
			for (int i=0; i<invList.size(); i++) {
				data[i][0] = invList.get(i).getInv();
				data[i][1] = invList.get(i).getISBN();
				data[i][2] = invList.get(i).getTitle();
				data[i][3] = invList.get(i).getAuthor();
				data[i][4] = invList.get(i).getGenre();
				data[i][5] = invList.get(i).getCondition();
				data[i][6] = invList.get(i).getPrice();
				data[i][7] = invList.get(i).getStockDate();
				data[i][8] = invList.get(i).getSaleDate();
			}
			pnlContent.removeAll();
			table = new JTable(data, invColumnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(new Dimension(700, 420));
			pnlContent.add(scrollPane);
			pnlContent.add(sellLabel);
			pnlContent.add(sellField);
			pnlContent.add(sellButton);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnBooks) {
			pnlContent.removeAll();
			pnlContent.add(pnlAdd);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAddBook) {
			try {
				db.addBook(bookField[0].getText(), bookField[1].getText(), bookField[2].getText(), bookField[3].getText(), 
									Double.parseDouble(bookField[4].getText()), bookField[5].getText());
				JOptionPane.showMessageDialog(null, "Book Added!");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == wishButton) {
			try {
				wishList = db.getWish();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			data = new Object[wishList.size()][wishColumnNames.length];
			for (int i=0; i<wishList.size(); i++) {
				data[i][0] = wishList.get(i).getName();
				data[i][1] = wishList.get(i).getPhone();
				data[i][2] = wishList.get(i).getTitle();
				data[i][3] = wishList.get(i).getAuthor();
			}
			pnlContent.removeAll();
			table = new JTable(data, wishColumnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(new Dimension(700, 420));
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(7, 0));
			wishLabel[0] = new JLabel("Customer Name: ");
			wishField[0] = new JTextField(15);
			wishLabel[1] = new JLabel("Phone: ");
			wishField[1] = new JTextField(15);
			wishLabel[2] = new JLabel("Title: ");
			wishField[2] = new JTextField(15);
			wishLabel[3] = new JLabel("Author(Last): ");
			wishField[3] = new JTextField(15);
			wishLabel[4] = new JLabel("Author(First): ");
			wishField[4] = new JTextField(15);
			wishLabel[5] = new JLabel("ISBN: ");
			wishField[5] = new JTextField(15);
			panel.add(wishLabel[0]);
			panel.add(wishField[0]);
			panel.add(wishLabel[1]);
			panel.add(wishField[1]);
			panel.add(wishLabel[2]);
			panel.add(wishField[2]);
			panel.add(wishLabel[3]);
			panel.add(wishField[3]);
			panel.add(wishLabel[4]);
			panel.add(wishField[4]);
			panel.add(wishLabel[5]);
			panel.add(wishField[5]);
			panel.add(makeAWishButton);
			
			pnlContent.add(scrollPane);
			pnlContent.add(panel);
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == makeAWishButton) {
			try {
				db.makeWish(wishField[0].getText(), wishField[1].getText(), wishField[2].getText(), wishField[3].getText(), 
						wishField[4].getText(), wishField[5].getText());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
