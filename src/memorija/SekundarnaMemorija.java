package memorija;

import java.util.ArrayList;
import java.util.LinkedList;


public class SekundarnaMemorija {
	private static int velicina;
    public static LinkedList<Blok> slobodniBlokovi=new LinkedList<>();
    private static Blok[] sviBlokovi;
    private static int brojBlokova;
    public static ArrayList<Fajl> fajlovi=new ArrayList<>();
    public SekundarnaMemorija()
    {
    	velicina=4096;
    	brojBlokova=velicina/Blok.getVelicina();
    	sviBlokovi=new Blok[brojBlokova];
    	for(int i=0;i<brojBlokova;i++)
    	{
    		Blok noviBlok=new Blok(i);
    		sviBlokovi[i]=noviBlok;
    		slobodniBlokovi.add(noviBlok);
    	}
    	fajlovi=new ArrayList<Fajl>();
    }
	public static int getVelicina() {
		return velicina;
	}
	public void sacuvajFajl(Fajl fajl)
	{
		int ostatak=fajl.getVelicina()%Blok.getBlokVelicina();
		int potrebnoBlokova;
		if(ostatak==0)
		{
			potrebnoBlokova=fajl.getVelicina()/Blok.getBlokVelicina();
		}
		else {
			potrebnoBlokova=(fajl.getVelicina()+4-ostatak)/Blok.getBlokVelicina();
		}
		if(potrebnoBlokova>brojSlobodnih())
		{
			System.out.println("Spremanje neuspjesno, nedovoljno memorije");
			return;
		}
		int brojac=0;
		Blok pomocni=null;
		while(true)
		{
			fajlovi.add(fajl);
			if(brojac==0)
			{
				if(slobodniBlokovi.isEmpty())
					return;
				pomocni=slobodniBlokovi.getFirst();
				fajl.setIndeksBlok(pomocni.getAdresa());
				pomocni.setSadrzaj(Fajl.parsiraj(pomocni.getAdresa()));
				brojac++;
				slobodniBlokovi.removeFirst();
				pomocni.setSljedeci(slobodniBlokovi.getFirst());
				pomocni=pomocni.getSljedeci();
			}
			else
			{
				sviBlokovi[pomocni.getAdresa()].setSadrzaj(Fajl.parsiraj(pomocni.getAdresa()));
				brojac++;
				slobodniBlokovi.removeFirst();
				if(slobodniBlokovi.isEmpty())
					return;
				pomocni.setSljedeci(slobodniBlokovi.getFirst());
				pomocni=pomocni.getSljedeci();
				if(brojac==potrebnoBlokova)
				{
					fajl.setDuzina(brojac);
					
					pomocni.setSljedeci(new Blok(-1));
					return;
				}
			}
		}
	}
	public void obrisiFajl(Fajl fajl)
	{
		if(!fajlovi.contains(fajl))
		{
			System.out.println("Datoteka ne postoji!");
			return;
		}
		int indeks=fajl.getIndeksBlok();
		Blok pomocni=sviBlokovi[indeks];
		if(pomocni==null)
			return;
		while(pomocni!=null)
		{
			slobodniBlokovi.add(pomocni);
			pomocni.setZauzet(false);
			pomocni.setSadrzaj(null);
			pomocni=pomocni.getSljedeci();
			
		}
		fajlovi.remove(fajl);
	}
	public String procitajFajl(Fajl fajl)
	{
		String rez="";
		int indeks=fajl.getIndeksBlok();
		Blok pomocni=sviBlokovi[indeks];
		if(pomocni==null)
			return rez;
		while(pomocni!=null)
		{
			byte[] sadrzaj=pomocni.getSadrzaj();
			for(byte bajt:sadrzaj)
			{
				rez+=(char)bajt;
			}
			pomocni=pomocni.getSljedeci();
		}
		System.out.println(rez);
		return rez;
	}
	public Fajl getFajl(String fName)
	{
		for(Fajl f:fajlovi)
		{
			if(f.getNaziv().trim().compareTo(fName.trim())==0)
			{
				return f;
			}
		}
		return null;
	}
	public static void setVelicina(int velicina) {
		SekundarnaMemorija.velicina = velicina;
	}
	public static LinkedList<Blok> getSlobodniBlokovi() {
		return slobodniBlokovi;
	}
	public static void setSlobodniBlokovi(LinkedList<Blok> slobodniBlokovi) {
		SekundarnaMemorija.slobodniBlokovi = slobodniBlokovi;
	}
	public static Blok[] getSviBlokovi() {
		return sviBlokovi;
	}
	public static void setSviBlokovi(Blok[] sviBlokovi) {
		SekundarnaMemorija.sviBlokovi = sviBlokovi;
	}
	public static int getBrojBlokova() {
		return brojBlokova;
	}
	public static void setBrojBlokova(int brojBlokova) {
		SekundarnaMemorija.brojBlokova = brojBlokova;
	}
	public static ArrayList<Fajl> getFajlovi() {
		return fajlovi;
	}
	public static void setFajlovi(ArrayList<Fajl> fajlovi) {
		SekundarnaMemorija.fajlovi = fajlovi;
	}
	public boolean sadrziFajl(String fajl)
	{
		for(Fajl f:fajlovi)
			if(f.naziv.equals(fajl))
			{
				System.out.println(f.naziv);
				return true;
			}
		return false;
	}
	public int brojSlobodnih()
	{
		int br=0;
		for(Blok b:sviBlokovi)
		{
			if(!b.isZauzet())
				br++;
		}
		return br;
	}
}
