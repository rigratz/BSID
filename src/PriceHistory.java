public class PriceHistory {
	private String isbn, condition, priceDate;
	private double storePrice, listPrice;
	
	public PriceHistory(String isbnNum, String condition, String date,
			 double storePrice, double lPrice) {

		setISBN(isbnNum);
		setCondition(condition);
		setPriceDate(date);
		setPrice(storePrice);
		setListPrice(lPrice);
	}

	private void setPrice(double storePrice2) {
		// TODO Auto-generated method stub
		storePrice = storePrice2;
	}

	private void setPriceDate(String date) {
		// TODO Auto-generated method stub
		priceDate = date;
	}

	private void setCondition(String condition2) {
		// TODO Auto-generated method stub
		condition = condition2;
	}

	private void setISBN(String isbn2) {
		// TODO Auto-generated method stub
		isbn = isbn2;
	}

	private void setListPrice(double price) {
		// TODO Auto-generated method stub
		listPrice = price;
	}
	
	public String getIsbn() {
		// TODO Auto-generated method stub
		return isbn;
	}
	public String getCondition() {
		// TODO Auto-generated method stub
		return condition;
	}
	public double getStorePrice() {
		// TODO Auto-generated method stub
		return storePrice;
	}
	public String getPriceDate() {
		// TODO Auto-generated method stub
		return priceDate;
	}
	public double getListPrice() {
		// TODO Auto-generated method stub
		return listPrice;
	}


	
}
