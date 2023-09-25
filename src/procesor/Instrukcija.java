package procesor;

import java.util.ArrayList;

public class Instrukcija {
	private String ime,kod;
	public static ArrayList<Instrukcija> instrukcije=new ArrayList<>();
	public Instrukcija(String ime,String kod)
	{
		this.ime=ime;
		this.kod=kod;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getKod() {
		return kod;
	}
	public void setKod(String kod) {
		this.kod = kod;
	}
	public static ArrayList<Instrukcija> getInstrukcije() {
		return instrukcije;
	}
	public static void setInstrukcije(ArrayList<Instrukcija> instrukcije) {
		Instrukcija.instrukcije = instrukcije;
	}
}
