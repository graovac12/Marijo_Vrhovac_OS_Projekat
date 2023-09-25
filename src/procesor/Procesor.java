package procesor;

import java.util.ArrayList;

import memorija.SistemFajlova;
import memorija.UpravljacMemorije;

public class Procesor {
	private static Proces trenutniProces;
	private static Registar a,b,c,d,ir,zf,pc;
	private static final ArrayList<Registar> gpReg=new ArrayList<>();
	private Asembler asembler=Bootloader.getAsembler();
	private boolean neaktivan=true;
	public Procesor(Asembler asembler)
	{
		trenutniProces=null;
		a=new Registar("a","0000",0);
		b=new Registar("b","0001",0);
		c=new Registar("c","0010",0);
		d=new Registar("d","0011",0);
		ir=new Registar("instrukcijski registar ","");
		pc=new Registar("brojac programa ",0);
		gpReg.add(a);
		gpReg.add(b);
		gpReg.add(c);
		gpReg.add(d);
		
	}
	public static Registar vratiRegistar(String adresa)
	{
		for(Registar r:gpReg)
			if(r.getAdresa().compareTo(adresa)==0)
				return r;
		return null;
	}
	public void izvrsi(Proces proces,long startMS)
	{
		if(proces==null)
		{}else {
		neaktivan=false;
		trenutniProces.setStartVrijeme(startMS);
		int refVrijeme=1;
		while(proces.isIzrsava()&&System.currentTimeMillis()-startMS<refVrijeme)
		{
			ir.setStrVrijednost(proces.getInstrukcije().get(pc.getVrijednost()));
			izvrsiMasinskiKod();
			reginfo();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(proces.isGotov())
		{
			proces.setRezultatProcesa(a.getVrijednost());
			if(proces.getIzlazniFajl()!=null)
				SistemFajlova.napraviFajl(proces);
			System.out.println("Proces uspjesno izvrsen "+gpReg.get(0).getVrijednost());
			proces.setSveukupnoVrijeme(System.currentTimeMillis());
			UpravljacMemorije.ukloniProces(proces);
			neaktivan=true;
			trenutniProces=null;
			
		}
		else if(proces.isBlokiran())
		{
			System.out.println("pID:"+trenutniProces.getpID()+" blokiran");
			neaktivan=true;
		}
		else if(proces.isPrekinut())
		{
			System.out.println("pID:"+trenutniProces.getpID()+" prekinut");
			neaktivan=true;
		}
		else {
			spremiRegistre();
		}
		ocistiRegistre();
	}}
	public void izvrsiMasinskiKod()
	{
		boolean pcPromjena=false;
		System.out.println(ir.getStrVrijednost());
		String instrukcija=ir.getStrVrijednost().substring(0,4);
		
		if(instrukcija.equals(asembler.getInstrukcijaVrijednost("hlt")))
		{
			asembler.hlt();
		}
		if(instrukcija.equals(asembler.getInstrukcijaVrijednost("mov")))
		{
			String r1=ir.getStrVrijednost().substring(4,8);
			String r2=ir.getStrVrijednost().substring(8,12);
			asembler.mov(r1, r2);
		}
		if(instrukcija.equals(asembler.getInstrukcijaVrijednost("add")))
		{
			String r1=ir.getStrVrijednost().substring(4,8);
			String r2;//=ir.getStrVrijednost().substring(8,12);
			if(ir.getStrVrijednost().length()==12)
				r2=ir.getStrVrijednost().substring(8,12);
			else
				r2=ir.getStrVrijednost().substring(8,16);
			asembler.add(r1, r2);
			
		}
		if(instrukcija.equals(asembler.getInstrukcijaVrijednost("sub")))
		{
			String r1=ir.getStrVrijednost().substring(4,8);
			String r2;//=ir.getStrVrijednost().substring(8,12);
			if(ir.getStrVrijednost().length()==12)
				r2=ir.getStrVrijednost().substring(8,12);
			else
				r2=ir.getStrVrijednost().substring(8,16);
			asembler.sub(r1, r2);
			
		}
		if(instrukcija.equals(asembler.getInstrukcijaVrijednost("mul")))
		{
			String r1=ir.getStrVrijednost().substring(4,8);
			String r2;//=ir.getStrVrijednost().substring(8,12);
			if(ir.getStrVrijednost().length()==12)
				r2=ir.getStrVrijednost().substring(8,12);
			else
				r2=ir.getStrVrijednost().substring(8,16);
			asembler.mul(r1, r2);
			
		}
		if(instrukcija.equals(asembler.getInstrukcijaVrijednost("inc")))
		{
			String r1=ir.getStrVrijednost().substring(4,8);
			asembler.inc(r1);
		}
		if(instrukcija.equals(asembler.getInstrukcijaVrijednost("dec")))
		{
			String r1=ir.getStrVrijednost().substring(4,8);
			asembler.dec(r1);
		}
		if(instrukcija.equals(asembler.getInstrukcijaVrijednost("jmp")))
		{
			String r1=ir.getStrVrijednost().substring(4,8);
			asembler.jmp(r1);
		}
		if(!pcPromjena)
			pc.povecajVrijedost(1);
	}
	public static void promijeniStatus(ProcesStanje stanje)
	{
		trenutniProces.setStanje(stanje);
	}
	public static Registar getIr() {
		return ir;
	}
	public static void setIr(Registar ir) {
		Procesor.ir = ir;
	}
	public static Registar getZf() {
		return zf;
	}
	public static void setZf(Registar zf) {
		Procesor.zf = zf;
	}
	public static Registar getPc() {
		return pc;
	}
	public static void setPc(Registar pc) {
		Procesor.pc = pc;
	}
	public static void setzFVrijednost(int vr)
	{
		zf.setVrijednost(vr);
	}
	public static void setpCVrijednost(int vr)
	{
		pc.setVrijednost(vr);
	}
	public static Proces getTrenutniProces() {
		return trenutniProces;
	}
	public static void setTrenutniProces(Proces trenutniProces) {
		Procesor.trenutniProces = trenutniProces;
	}
	public static String vratiRegistarskiKod(String adresa)
	{
		for(Registar r:gpReg)
			if(r.getNaziv().compareTo(adresa)==0)
				return r.getAdresa();
		return null;
	}
	public void ocistiRegistre()
	{
		for(Registar r:gpReg)
		{
			r.setVrijednost(0);
		}
	}
	public void spremiRegistre()
	{
		int[] vrRegistara= {a.getVrijednost(),b.getVrijednost(),c.getVrijednost(),d.getVrijednost()};
		trenutniProces.setVrijednostRegistara(vrRegistara);
		trenutniProces.setPC(pc.getVrijednost());
	}
	public void ucitajRegistre()
	{
		int[] vrRegistara=trenutniProces.getVrijednostRegistara();
		for(int i=0;i<vrRegistara.length;i++)
			gpReg.get(i).setVrijednost(vrRegistara[i]);
		pc.setVrijednost(trenutniProces.getPC());
	}
	public void reginfo(){
		System.out.println("A:"+a.getVrijednost()+" B:"+b.getVrijednost()+
				"\nC:"+d.getVrijednost()+"D:"+d.getVrijednost()+"\nIR"+ir.getStrVrijednost());
	}
}
