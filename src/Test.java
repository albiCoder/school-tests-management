import java.util.LinkedList;

public class Test {
	private int testId;
	private String emertimiTestit;
	private int pedagogId;
	private LinkedList<Pyetje> pyetjet = new LinkedList<>();

	public Test(String emertimiTestit) {
		this.emertimiTestit = emertimiTestit;
	}

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public Test(int testId, String emertimiTestit) {
		this.testId = testId;
		this.emertimiTestit = emertimiTestit;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public LinkedList<Pyetje> getPyetje() {
		return pyetjet;
	}

	public void setPyetjet(LinkedList<Pyetje> pyetjet) {
		this.pyetjet = pyetjet;
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

	@Override
	public String toString() {
		String result = "TestId: " + testId + ", emertimiTestit: " + emertimiTestit + ", pedagogId: " + pedagogId
				+ ", -->";
		for (int i = 0; i < pyetjet.size(); i++) {
			result += pyetjet.get(i).toString();
		}
		return result;
	}
}
