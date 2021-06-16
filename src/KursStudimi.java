import java.util.LinkedList;

public class KursStudimi {
	private int kursId;
	private String emriKursit;
	private LinkedList<Test> tests = new LinkedList<>();
	private LinkedList<Student> students = new LinkedList<>();

	public KursStudimi(String emriKursit) {
		this.emriKursit = emriKursit;
	}

	public KursStudimi() {
		// TODO Auto-generated constructor stub
	}

	public KursStudimi(int kursId, String emriKursit) {
		this.kursId = kursId;
		this.emriKursit = emriKursit;
	}

	public int getKursId() {
		return kursId;
	}

	public void setKursId(int kursId) {
		this.kursId = kursId;
	}

	public String getEmriKursit() {
		return emriKursit;
	}

	public void setEmriKursit(String emriKursit) {
		this.emriKursit = emriKursit;
	}

	public LinkedList<Student> getStudents() {
		return students;
	}

	public void setStudents(LinkedList<Student> students) {
		this.students = students;
	}

	public LinkedList<Test> getTests() {
		return tests;
	}

	public void setTests(LinkedList<Test> tests) {
		this.tests = tests;
	}

	public String toString() {
		String result = "KursId: " + kursId + ", emriKursit: " + emriKursit;
		for (int i = 0; i < tests.size(); i++) {
			result += ", test " + i + ": " + tests.get(i).getEmertimiTestit() + "--> ";
			for (int j = 0; j < tests.get(i).getPyetje().size(); j++) {
				result += "pyetje " + j + ": " + tests.get(i).getPyetje().get(j).getPyetja() + " ";
			}
		}

		return result;
	}
}
