package memorija;

import java.util.ArrayList;
import java.util.LinkedList;


public class SekundarnaMemorija {
	private static int velicina;
    public static ArrayList<Blok> slobodniBlokovi=new ArrayList<>();
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
    		slobodniBlokovi.add(sviBlokovi[i]);
    	}
    	System.out.println(slobodniBlokovi.size());
    	fajlovi=new ArrayList<Fajl>();
    }
	public static int getVelicina() {
		return velicina;
	}
	public void sacuvajFajl(Fajl fajl)
	{
		int ostatak=fajl.getVelicina()%Blok.getBlokVelicina();
		System.out.println(fajl.getVelicina());
		int potrebnoBlokova;
		if(ostatak==0)
		{
			potrebnoBlokova=fajl.getVelicina()/4;
		}
		else {
			potrebnoBlokova=(fajl.getVelicina()+4-ostatak)/4;
		}
		System.out.println(potrebnoBlokova);
		if(potrebnoBlokova>brojSlobodnih())
		{
			System.out.println("Spremanje neuspjesno, nedovoljno memorije");
			return;
		}
		int brojac=0;
		Blok pomocni=null;
		int indeks=slobodniBlokovi.get(0).getAdresa();
		slobodniBlokovi.remove(0);
		fajlovi.add(fajl);
		while(true)
		{
			if(brojac==0)
			{
				if(slobodniBlokovi.isEmpty())
					return;
				//slobodniBlokovi.remove(0);
				sviBlokovi[indeks].setZauzet(true);
				fajl.setIndeksBlok(sviBlokovi[indeks].getAdresa());
				sviBlokovi[indeks].setSadrzaj(Fajl.parsiraj(brojac));
				sviBlokovi[indeks].setSljedeci(slobodniBlokovi.get(0).getAdresa());
				if(brojac==potrebnoBlokova)
				{
					fajl.setDuzina(brojac);
					sviBlokovi[indeks].setSljedeci(-1);
					return;
				}
				indeks=sviBlokovi[sviBlokovi[indeks].getSljedeci()].getAdresa();
				brojac++;
			}
			else
			{
				
				sviBlokovi[sviBlokovi[indeks].getAdresa()].setSadrzaj(Fajl.parsiraj(brojac));
				slobodniBlokovi.remove(0);
				if(slobodniBlokovi.isEmpty())
					return;
				sviBlokovi[indeks].setZauzet(true);
				sviBlokovi[indeks].setSljedeci(slobodniBlokovi.get(0).getAdresa());
				if(brojac==potrebnoBlokova)
				{
					fajl.setDuzina(brojac);
					sviBlokovi[indeks].setSljedeci(-1);
					return;
				}
				indeks=sviBlokovi[sviBlokovi[indeks].getSljedeci()].getAdresa();
				brojac++;
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
		while(pomocni.getSljedeci()!=-1)
		{
			pomocni.setZauzet(false);
			pomocni.setSadrzaj(null);
			slobodniBlokovi.add(sviBlokovi[pomocni.getAdresa()]);
			pomocni=sviBlokovi[pomocni.getSljedeci()];
			
		}
		pomocni.setZauzet(false);
		pomocni.setSadrzaj(null);
		slobodniBlokovi.add(sviBlokovi[pomocni.getAdresa()]);
		fajlovi.remove(fajl);
	}
	public String procitajFajl(Fajl fajl)
	{
		String rez="";
		System.out.println(fajlovi.size());
		int indeks=fajl.getIndeksBlok();
		Blok pomocni=sviBlokovi[indeks];
		if(pomocni==null)
			return rez;
		while(pomocni.getSljedeci()!=-1)
		{
			byte[] sadrzaj=sviBlokovi[pomocni.getAdresa()].getSadrzaj();
			for(byte bajt:sadrzaj)
			{
				rez+=(char)bajt;
			}
			pomocni=sviBlokovi[pomocni.getSljedeci()];
		}
		byte[] sadrzaj=sviBlokovi[pomocni.getAdresa()].getSadrzaj();
		for(byte bajt:sadrzaj)
		{
			rez+=(char)bajt;
		}
		System.out.println(rez+" ");
		String[] rezSplit=rez.split("\n");
		System.out.println(rezSplit.length);
		return rez;
	}
	public Fajl getFajl(String fName)
	{
		for(Fajl f:fajlovi)
		{
			if(f.naziv.equals(fName))
			{
				System.out.println(f.naziv);
				return f;
			}
		}
		return null;
	}
	public static void setVelicina(int velicina) {
		SekundarnaMemorija.velicina = velicina;
	}
	public static ArrayList<Blok> getSlobodniBlokovi() {
		return slobodniBlokovi;
	}
	public static void setSlobodniBlokovi(ArrayList<Blok> slobodniBlokovi) {
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
	public static int brojSlobodnih()
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
