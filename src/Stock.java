
public class Stock {
	private int inventoryNum;
	private String title, author, genre;
	private double storePrice;
	private String section;
	
	public Stock(int inv, String title, String author,
			 String genre, double storePrice,
			 String section) {
		setInventoryNum(inv);
		setTitle(title);
		setAuthor(author);
		setGenre(genre);
		setPrice(storePrice);
		setSection(section);
	}

	private void setSection(String section2) {
		// TODO Auto-generated method stub
		section = section2;
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

	private void setInventoryNum(int inv) {
		// TODO Auto-generated method stub
		inventoryNum = inv;
	}

	public int getInv() {
		// TODO Auto-generated method stub
		return inventoryNum;
	}
	
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}
	public String getAuthor() {
		// TODO Auto-generated method stub
		return author;
	}
	public double getStorePrice() {
		// TODO Auto-generated method stub
		return storePrice;
	}
	public String getGenre() {
		// TODO Auto-generated method stub
		return genre;
	}
	public String getSection() {
		// TODO Auto-generated method stub
		return section;
	}


	
}
