package procesor;

public class Registar {
	private String naziv;
	private String adresa;
	private String strVrijednost;
	public String getStrVrijednost() {
		return strVrijednost;
	}
	public void setStrVrijednost(String strVrijednost) {
		this.strVrijednost = strVrijednost;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	private int vrijednost;
	
	public Registar(String naziv,String adresa, int vrijednost)
	{
		this.naziv=naziv;
		this.adresa=adresa;
		this.vrijednost=vrijednost;
	}
	public Registar(String naziv,int vrijednost)
	{
		this.naziv=naziv;
		this.vrijednost=vrijednost;
	}
	public Registar(String naziv,String vrijednost)
	{
		this.naziv=naziv;
		this.strVrijednost=vrijednost;
	}
	public void povecajVrijedost(int deltaVrijednost)
	{
		this.vrijednost+=deltaVrijednost;
	}
	public void smanjiVrijedost(int deltaVrijednost)
	{
		this.vrijednost-=deltaVrijednost;
	}
	public void pomnoziVrijedonst(int mnozilac)
	{
		this.vrijednost*=mnozilac;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public int getVrijednost() {
		return vrijednost;
	}
	public void setVrijednost(int vrijednost) {
		this.vrijednost = vrijednost;
	}
	public String getNaziv() {
		return naziv;
	}
	//
}
