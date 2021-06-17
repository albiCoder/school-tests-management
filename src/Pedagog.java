import java.util.LinkedList;

public class Pedagog extends Perdorues {

	private String titulli;
	private LinkedList<KursStudimi> kurset = new LinkedList<>();

	public Pedagog() {
	}

	public Pedagog(String name, String email, String password, String titulli, LinkedList<KursStudimi> kurset) {
		super(name, email, password);
		this.titulli = titulli;
		this.kurset = kurset;
	}

	public Pedagog(String name, String email, String password, String titulli) {
		super(name, email, password);
		this.titulli = titulli;
	}

	public Pedagog(int id, String name, String email, String password, String titulli) {
		super(id, name, email, password);
		this.titulli = titulli;
	}

	public String getTitulli() {
		return titulli;
	}

	public void setTitulli(String titulli) {
		this.titulli = titulli;
	}

	public LinkedList<KursStudimi> getKurset() {
		return kurset;
	}

	public void setKurset(LinkedList<KursStudimi> kurset) {
		this.kurset = kurset;
	}

	@Override
	public String toString() {
		return super.toString() + ", Titulli: " + titulli;
	}

}
