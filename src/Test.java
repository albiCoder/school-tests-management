import java.util.LinkedList;

public class Test {
	private int testId;
	private String emertimiTestit;
	private int pedagogId;
	private LinkedList<Pyetje> pyetje = new LinkedList<>();

	public Test(String emertimiTestit) {
		this.emertimiTestit = emertimiTestit;
	}

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public LinkedList<Pyetje> getPyetje() {
		return pyetje;
	}

	public void setPyetje(LinkedList<Pyetje> pyetje) {
		this.pyetje = pyetje;
	}

	public int getPedagogId() {
		return pedagogId;
	}

	public void setPedagogId(int pedagogId) {
		this.pedagogId = pedagogId;
	}

	public String getEmertimiTestit() {
		return emertimiTestit;
	}

	public void setEmertimiTestit(String emertimiTestit) {
		this.emertimiTestit = emertimiTestit;
	}
}
