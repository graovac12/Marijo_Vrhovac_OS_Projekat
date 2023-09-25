package procesor;

public class Asembler {
	private String kod;
	private String ime;
	public Asembler()
	{
		Instrukcija.instrukcije.add(new Instrukcija("hlt","0000"));
		Instrukcija.instrukcije.add(new Instrukcija("mov","0001"));
		Instrukcija.instrukcije.add(new Instrukcija("add","0010"));
		Instrukcija.instrukcije.add(new Instrukcija("sub","0011"));
		Instrukcija.instrukcije.add(new Instrukcija("inc","0100"));
		Instrukcija.instrukcije.add(new Instrukcija("dec","0101"));
		Instrukcija.instrukcije.add(new Instrukcija("mul","0110"));
		Instrukcija.instrukcije.add(new Instrukcija("cmp","0111"));
		Instrukcija.instrukcije.add(new Instrukcija("jmp","1000"));
	}
	
	public String pretvoriuMasinskiKod(String naredba)
	{
		String rez="";
		String[] naredbaSplit=naredba.split("[ |,]");
		for(String ts:naredbaSplit)
			System.out.println(ts);
		String operacija=null;
		for(Instrukcija i:Instrukcija.instrukcije)
		{
			if(i.getIme().compareTo(naredbaSplit[0])==0)
			{
				operacija=i.getKod();
				break;
			}
		}
		System.out.println(operacija);
		if(operacija!=null)
			rez+=operacija;
		if(naredbaSplit[0].equals("hlt"))
			return rez;
		else if(naredbaSplit[0].equals("jmp"))
		{
			rez+=pretvoriuBinarno(naredbaSplit[1]);
			return rez;
		}
		else if(naredbaSplit[0].equals("inc")||naredbaSplit[0].equals("dec"))
		{
			rez+=Procesor.vratiRegistarskiKod(naredbaSplit[1]);
			return rez;
		}
		else if(Procesor.vratiRegistarskiKod(naredbaSplit[2])!=null)
		{
			rez+=Procesor.vratiRegistarskiKod(naredbaSplit[1])+Procesor.vratiRegistarskiKod(naredbaSplit[2]);
			return rez;
		}
		else {
		rez+=Procesor.vratiRegistarskiKod(naredbaSplit[1]);
		rez+=pretvoriuBinarno(naredbaSplit[2]);
		return rez;
		}	
		}
	public void hlt()
	{
		Procesor.promijeniStatus(ProcesStanje.GOTOV);
	}
	public void mov(String adr1,String adr2)
	{
		Registar r1=Procesor.vratiRegistar(adr1);
		Registar r2=Procesor.vratiRegistar(adr2);
		if(r1!=null&&r2!=null)
		{
			r1.setVrijednost(r2.getVrijednost());
		}
	}
	public void add(String adr1, String vr)
	{
		Registar r1=Procesor.vratiRegistar(adr1);
		if(r1==null)
			return;
		if(vr.length()==8)
		{
			r1.povecajVrijedost(Integer.parseInt(vr,2));
			return;
		}
		if(vr.length()==4)
		{
			Registar r2=Procesor.vratiRegistar(vr);
			if(r2==null)
				return;
			r1.povecajVrijedost(r2.getVrijednost());
		}
	}
	public void sub(String adr1, String vr)
	{
		Registar r1=Procesor.vratiRegistar(adr1);
		if(r1==null)
			return;
		if(vr.length()==8)
		{
			r1.smanjiVrijedost(Integer.parseInt(vr,2));
			return;
		}
		if(vr.length()==4)
		{
			Registar r2=Procesor.vratiRegistar(vr);
			if(r2==null)
				return;
			r1.smanjiVrijedost(r2.getVrijednost());
		}
	}
	public void mul(String adr1, String vr)
	{
		Registar r1=Procesor.vratiRegistar(adr1);
		if(r1==null)
			return;
		if(vr.length()==8)
		{
			r1.pomnoziVrijedonst(Integer.parseInt(vr,2));
			return;
		}
		if(vr.length()==4)
		{
			Registar r2=Procesor.vratiRegistar(vr);
			if(r2==null)
				return;
			r1.pomnoziVrijedonst(r2.getVrijednost());
		}
	}
	public void cmp(String adr1,String vr)
	{
		
			Registar r1=Procesor.vratiRegistar(adr1);
			if(r1==null)
				return;
			if(vr.length()==8)
			{
				int uslov=r1.getVrijednost()==Integer.parseInt(vr, 2)?1:0;
				Procesor.setzFVrijednost(uslov);
				return;
			}
			if(vr.length()==4)
			{
				Registar r2=Procesor.vratiRegistar(vr);
				if(r2==null)
					return;
				int uslov=r1.getVrijednost()==r2.getVrijednost()?1:0;
				Procesor.setzFVrijednost(uslov);
			}
		
	}
	public void jmp(String adr)
	{
		int skok=Integer.parseInt(adr, 2);
		if(skok>=Procesor.getTrenutniProces().getVelicinaInstrukcija())
		{
			Procesor.getTrenutniProces().setStanje(ProcesStanje.PREKINUT);
			return;
		}
		Procesor.setpCVrijednost(skok);
	}
	public void inc(String adr)
	{
		Registar r=Procesor.vratiRegistar(adr);
		if(r!=null)
			r.povecajVrijedost(1);
	}
	public void dec(String adr)
	{
		Registar r=Procesor.vratiRegistar(adr);
		if(r!=null)
		r.smanjiVrijedost(1);
	}
	public String getInstrukcijaVrijednost(String instrukcija)
	{
		for(Instrukcija i:Instrukcija.instrukcije)
			if(i.getIme().equals(instrukcija))
				return i.getKod();
		return null;
	}
	private String pretvoriuBinarno(String naredba)
	{
		int vrijednost=(Integer.parseInt(naredba));
		return String.format("%8s", Integer.toBinaryString(vrijednost).replace(' ', '0'));
				
	}
}
