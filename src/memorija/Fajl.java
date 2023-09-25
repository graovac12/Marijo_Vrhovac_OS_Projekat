package memorija;

import procesor.Bootloader;

public class Fajl {
	String naziv;
	private int velicina;
	private int indeksBlok;
	private int duzina;
	private static byte[] sadrzajFajla=new byte[0];
	public String getNaziv() {
		return naziv;
	}
	
	 public static byte[] parsiraj(int indeks)
	    {
	    	byte[] rez=new byte[Blok.getBlokVelicina()];
	    	int br=0;
	    	for(int i=indeks*Blok.getBlokVelicina();i<sadrzajFajla.length;i++)
	    	{
	    		rez[br]=sadrzajFajla[i];
	    		if(br==Blok.getBlokVelicina()-1)
	    			break;
	    		br++;
	    	}
	    	while(br<Blok.getBlokVelicina()-1)
    		{
	    		byte[] b= " ".getBytes();
	    		rez[br]=b[0];
	    		br++;
    		}
	    	return rez;
	    }
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public int getDuzina() {
		return duzina;
	}
	public void setDuzina(int duzina) {
		this.duzina = duzina;
	}
	public int getVelicina() {
		return velicina;
	}
	public void setVelicina(int velicina) {
		this.velicina = velicina;
	}
	public int getIndeksBlok() {
		return indeksBlok;
	}
	public void setIndeksBlok(int indeksBlok) {
		this.indeksBlok = indeksBlok;
	}
	public static byte[] getSadrzajSt() {
		return sadrzajFajla;
	}
	public static void setSadrzajSt(byte[] sadrzajSt) {
		Fajl.sadrzajFajla= sadrzajSt;
	}
	
	public Fajl(String naziv,byte sadrzaj[])
	{
		this.naziv=naziv;
		duzina=sadrzaj.length;
		sadrzajFajla=sadrzaj;
		velicina=sadrzaj.length;
	}
}
