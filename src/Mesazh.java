import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

public class Mesazh {
	private int mesazhId;
	private Pedagog pedagog = new Pedagog();
	private Student student = new Student();
	private String mesazhi;

	public Pedagog getPedagog() {
		return pedagog;
	}

	public void setPedagog(Pedagog pedagog) {
		this.pedagog = pedagog;
	}

	public Student getStudent() {
		return student;
	}
	
	public Property<String> studentNameProperty() {
		return new SimpleStringProperty((String) student.getName());
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}

	public int getMesazhId() {
		return mesazhId;
	}

	public void setMesazhId(int mesazhId) {
		this.mesazhId = mesazhId;
	}

	public String getMesazhi() {
		return mesazhi;
	}

	public void setMesazhi(String mesazhi) {
		this.mesazhi = mesazhi;
	}

	@Override
	public String toString() {
		return "Id: " + mesazhId + ", Student: " + student.getName() + ", Pedagog: " + pedagog.getName() + ", Teksti: "
				+ mesazhi;
	}

}
