package procesor;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Lock;

import memorija.UpravljacMemorije;

public class RasporedjivacProcesa extends Thread{
	private Lock lock;
	private static Procesor procesor=Bootloader.getProcesor();
	private static UpravljacMemorije upravljacMemorije=Bootloader.getUpravljacMemorije();
	//private static Proces proces;
	public static ArrayList<Proces> procesi=new ArrayList<>();
	public static PriorityQueue<Proces> red=new PriorityQueue<>((p1,p2)->{
		if(p1.getResponseRatio()<p2.getResponseRatio())
			return -1;
		if(p1.getResponseRatio()>p2.getResponseRatio())
			return 1;
		return 0;
	});
	private static void pokreniProces(Proces proces)
	{
		Procesor.setTrenutniProces(proces);
		if(proces.getPC()==-1) {
			System.out.println("pID:"+proces.getpID()+" poceo sa radom");
			upravljacMemorije.ucitajProces(proces);
			Procesor.setpCVrijednost(0);
			proces.setStanje(ProcesStanje.IZVRSAVANJE);
			procesor.izvrsi(proces,System.currentTimeMillis());
		}
		else {
			System.out.println("pID:"+proces.getpID()+" nastavio sa radom");
			procesor.ucitajRegistre();
			proces.setStanje(ProcesStanje.IZVRSAVANJE);
			procesor.izvrsi(proces, System.currentTimeMillis());
		}
	}
	public void run()
	{
		while(true)
		{
			synchronized(this){
				
				Proces sljedeci=red.peek();
				if(sljedeci!=null)
				{
					red.remove(sljedeci);
					if(Procesor.getTrenutniProces()!=null
							&&Procesor.getTrenutniProces()!=sljedeci
							&&Procesor.getTrenutniProces().getResponseRatio()>sljedeci.getResponseRatio())
					{
						Procesor.getTrenutniProces().blokirajProces();
						pokreniProces(sljedeci);
					}else
						pokreniProces(sljedeci);
					if(!sljedeci.isBlokiran()&&!sljedeci.isPrekinut()&&!sljedeci.isGotov()&&sljedeci.isSuspendovan())
						red.add(sljedeci);
				}
			
		}
	}
}}
