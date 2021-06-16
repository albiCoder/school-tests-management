import javafx.beans.property.SimpleBooleanProperty;

public class Student extends Perdorues {
    
	private int year;
	private SimpleBooleanProperty selected = new SimpleBooleanProperty(false);

	public Student() { }
	public Student(String name, String email, String password, int year) {
		super(name, email, password);
		this.year = year;
	}
	
	public Student(String string) {
		super.setName(string);
	}
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
    
	public SimpleBooleanProperty selectedProperty() {
		return selected;
	}
	
}