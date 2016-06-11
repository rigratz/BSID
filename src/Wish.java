
public class Wish {
	private String name, phone, title, author;
	
	public Wish(String n, String p, String t, String a) {
		setName(n);
		setPhone(p);
		setTitle(t);
		setAuthor(a);
	}
	
	public String getName() {
		return name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setName(String name2) {
		name = name2;
	}
	
	public void setPhone(String phone2) {
		phone = phone2;
	}
	
	public void setTitle(String title2) {
		title = title2;
	}
	
	public void setAuthor(String author2) {
		author = author2;
	}
}
