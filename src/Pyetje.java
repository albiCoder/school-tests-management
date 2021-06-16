
public class Pyetje {
	private int pyetjeId;
	private String pyetja;
	private int pergjigjja;

	public Pyetje(String pyetja, int pergjigjja) {
		this.pyetja = pyetja;
		this.pergjigjja = pergjigjja;
	}

	
	public int getPyetjeId() {
		return pyetjeId;
	}

	public void setPyetjeId(int pyetjeId) {
		this.pyetjeId = pyetjeId;
	}

	public String getPyetja() {
		return pyetja;
	}

	public void setPyetja(String pyetja) {
		this.pyetja = pyetja;
	}

	public int getPergjigjja() {
		return pergjigjja;
	}

	public void setPergjigjja(int pergjigjja) {
		this.pergjigjja = pergjigjja;
	}
	
}
