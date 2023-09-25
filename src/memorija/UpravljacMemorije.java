package memorija;

import java.util.ArrayList;

import procesor.Proces;

public class UpravljacMemorije {
	public UpravljacMemorije(int velicina)
	{
		Memorija.inicijalizacija(4096);
	}
	public boolean ucitajProces(Proces proces)
	{
		int adresa=0;
		int procesVelicina=proces.getVelicina();
		ArrayList<Particija> slobodneParticije=Memorija.getSlobodneParticije(procesVelicina);
		Memorija.razdvojBuddy(procesVelicina);
		for(Particija p:Memorija.getParticije())
		{
			if(Math.ceil(Math.log(p.getVelicina())/Math.log(2))==
				Math.ceil(Math.log(procesVelicina)/Math.log(2))&&p.isKoristenje()==false)
			{
				adresa=Memorija.getParticije().indexOf(p);
				break;
			}
		}
		if(slobodneParticije.size()>0) {
			proces.ucitajProces(Memorija.getParticije().get(adresa));
			return true;
		}
		else
			return false;
	}
	public static void ukloniProces(Proces proces)
	{
		for(Particija p:Memorija.getParticije())
		{
			if(proces.equals(p.getProces())&&proces!=null)
				{
				p.oslobodiParticiju();
				return ;
				}
		}
	
	}
}
