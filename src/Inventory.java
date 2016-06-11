
public class Inventory {
	private int inventoryNum;
	private String isbn, title, author, genre;
	private double storePrice;
	private String condition, inStockDate, saleDate;
	
	public Inventory(int inv, String isbn, String title, String author,
					 String genre, double storePrice, String condition,
					 String inStockDate, String saleDate) {
		setInventoryNum(inv);
		setISBN(isbn);
		setTitle(title);
		setAuthor(author);
		setGenre(genre);
		setPrice(storePrice);
		setCondition(condition);
		setStockDate(inStockDate);
		setSaleDate(saleDate);
	}

	private void setSaleDate(String saleDate2) {
		// TODO Auto-generated method stub
		saleDate = saleDate2;
	}

	private void setStockDate(String inStockDate2) {
		// TODO Auto-generated method stub
		inStockDate = inStockDate2;
	}

	private void setCondition(String condition2) {
		// TODO Auto-generated method stub
		condition = condition2;
	}

	private void setPrice(double storePrice2) {
		// TODO Auto-generated method stub
		storePrice = storePrice2;
	}

	private void setGenre(String genre2) {
		// TODO Auto-generated method stub
		genre = genre2;
	}

	private void setAuthor(String author2) {
		// TODO Auto-generated method stub
		author = author2;
	}

	private void setTitle(String title2) {
		// TODO Auto-generated method stub
		title = title2;
	}

	private void setISBN(String isbn2) {
		// TODO Auto-generated method stub
		isbn = isbn2;
	}

	private void setInventoryNum(int inv) {
		// TODO Auto-generated method stub
		inventoryNum = inv;
	}

	public Object getInv() {
		// TODO Auto-generated method stub
		return inventoryNum;
	}
	public Object getISBN() {
		// TODO Auto-generated method stub
		return isbn;
	}
	public Object getTitle() {
		// TODO Auto-generated method stub
		return title;
	}
	public Object getAuthor() {
		// TODO Auto-generated method stub
		return author;
	}
	public Object getGenre() {
		// TODO Auto-generated method stub
		return genre;
	}
	public Object getCondition() {
		// TODO Auto-generated method stub
		return condition;
	}
	public Object getPrice() {
		// TODO Auto-generated method stub
		return storePrice;
	}
	public Object getStockDate() {
		// TODO Auto-generated method stub
		return inStockDate;
	}
	public Object getSaleDate() {
		// TODO Auto-generated method stub
		return saleDate;
	}
	
}
