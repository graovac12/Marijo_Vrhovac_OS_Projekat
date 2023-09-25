package memorija;

import java.util.ArrayList;

import procesor.Bootloader;
import procesor.Proces;

public class Memorija {
	private static int VELICINA;
	private static int ZAUZETO;
	private static ArrayList<Particija> particije=new ArrayList<>();
	public static void inicijalizacija(int velicina )
	{
		VELICINA=velicina;
		ZAUZETO=0;
		particije.add(new Particija(VELICINA));
	}
	public static Particija zauzmiParticiju(Particija particija, Proces proces)
	{
		if(!particije.contains(particija))
			return null;
		return particija.zauzmiParticiju(proces);
		
	}
	public static boolean razdvojParticiju(Particija particija)
	{
		int adresa=particije.indexOf(particija);
		return razdvojParticiju(adresa);
		
	}
	public static boolean razdvojParticiju(int adresa)
	{
		Particija particija=particije.get(adresa);
		if(particija.getVelicina()==particija.getZauzeto())
		{
			return false;
		}
		Particija particija1=new Particija(particija.getVelicina()/2);
		Particija particija2=new Particija(particija.getVelicina()/2);
		particije.set(adresa, particija1);
		particije.add(adresa+1,particija2);
		return true;
	}
	public static int razdvojBuddy(int velicina)
	{
		ArrayList<Particija> zadovoljavajuce=getSlobodneParticije(velicina);
		Particija pomocna=null;
		for(Particija p:particije)
		{
			if(zadovoljavajuce.get(0).equals(p))
			{
				pomocna=p;
				break;
			}
		}
		if(Math.ceil(Math.log(zadovoljavajuce.get(0).getVelicina())/Math.log(2))==
				Math.ceil(Math.log(velicina)/Math.log(2)))
			return particije.indexOf(pomocna);
		//else if(Math.ceil(Math.log(zadovoljavajuce.get(0).getVelicina())/Math.log(2))<Math.ceil(Math.log(velicina)/Math.log(2)))
			//return particije.indexOf(pomocna);
		else
		{
			razdvojParticiju(pomocna);
			razdvojBuddy(velicina);
			return particije.indexOf(pomocna);
		}
	}
	public static void ispisStanja()
	{
		System.out.println(VELICINA+"B Zauzeto: "+ZAUZETO+"B"+"\nParticije: ");
		for(Particija p:particije)
			System.out.println(p);
		
	}
	public static ArrayList<Particija> getSlobodneParticije(int velicina)
	{
		ArrayList<Particija> slobodneParticije=new ArrayList<>();
		for(Particija p: particije)
		{
			if(p.getProces()==null&&p.getVelicina()>=velicina)
				slobodneParticije.add(p);
		}
		return slobodneParticije;
	}
	public static ArrayList<Particija> getParticije() {
		return particije;
	}
	public static ArrayList<Particija> getZadovoljavajucuParticiju(int velicina)
	{
		ArrayList<Particija>zadovoljavajuce=new ArrayList<>();
		for(Particija p:particije)
			if(p.getVelicina()>=velicina)
				zadovoljavajuce.add(p);
		return zadovoljavajuce;
	}
	public static void main(String[] args)
	{
		Bootloader.ukljucivanje();
		inicijalizacija(4096);
		System.out.println(razdvojBuddy(513));
		particije.get(0).setProces(new Proces("a","a","a",1));
		System.out.println(razdvojBuddy(511));
		particije.get(1).setProces(new Proces("a","a","a",1));
		razdvojBuddy(244);
		//System.out.println(razdvojBuddy(511));
		ispisStanja();
		
		System.out.println(Math.ceil(Math.log(129)/Math.log(2)));
	}
}
