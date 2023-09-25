package procesor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import memorija.Memorija;
import memorija.Particija;

public class Proces {
	private int pID;
	private ProcesStanje stanje;
	private String naziv,ulazniFajl;
	private int prioritet;
	private int velicina;
	private float responseRatio;
	private long startVrijeme,sveukupnoVrijeme;
	private ArrayList<String> instrukcije=new ArrayList<>();
	private int[] vrijednostRegistara;
	private int PC=-1;
	private int velicinaInstrukcija;
	private Path putanjaDoFajla;
	private int rezultatProcesa;
	private String izlazniFajl;
	public Proces(String naziv,String ulazniFajl,String izlazniFajl,int prioritet)
	{
		this.naziv=naziv;
		this.ulazniFajl=ulazniFajl;
		this.izlazniFajl=izlazniFajl;
		if(!Bootloader.getDisk().sadrziFajl(ulazniFajl))
		{
			System.out.println("Datoteka ne postoji");
		}else {
		this.sveukupnoVrijeme=0;
		
				
		
		this.putanjaDoFajla=Paths.get(Bootloader.getRoot().getTrenutni().getAbsolutePath()+"/"+ulazniFajl);
		this.prioritet=prioritet;
		this.stanje=ProcesStanje.SPREMAN;
		this.velicinaInstrukcija=instrukcije.size();
		this.pID=RasporedjivacProcesa.procesi.size();
		this.vrijednostRegistara=new int[4];
		ucitajFajl();
		for(int i=0;i<velicinaInstrukcija;i++)
			this.velicina+=instrukcije.get(i).length();
		for(String inst:instrukcije)
		{
			System.out.println(inst);
		}
		this.velicina=Math.round(this.velicina/8)+16;
		this.responseRatio=(sveukupnoVrijeme+velicina)/velicina;
		RasporedjivacProcesa.red.add(this);
		RasporedjivacProcesa.procesi.add(this);
		}
	}
	public void blokirajProces()
	{
		if(!this.isIzrsava())
		{
			System.out.println(this.pID+" nije pokrenut");
			return;
		}
		this.stanje=ProcesStanje.BLOKIRAN;
		RasporedjivacProcesa.red.remove(this);
	}
	public void deblokirajProces()
	{
		if(this.isIzrsava())
		{
			System.out.println(this.pID+" je vec spreman");
			return;
		}
		this.stanje=ProcesStanje.SPREMAN;
		RasporedjivacProcesa.red.add(this);
	}
	public void prekiniProces()
	{
		if(this.isIzrsava())
		{
			System.out.println(this.pID+" je vec spreman");
			return;
		}
		this.stanje=ProcesStanje.SPREMAN;
		RasporedjivacProcesa.red.add(this);
	}
	public float getResponseRatio() {
		return responseRatio;
	}
	public void setResponseRatio(float responseRatio) {
		this.responseRatio = responseRatio;
	}
	private void ucitajFajl()
	{
		List<String> fSadrzaj=List.of(Bootloader.getDisk().procitajFajl(Bootloader.getDisk().getFajl(this.ulazniFajl)).split("\n"));
		ArrayList<String> sadrzaj=new ArrayList<>();
		for(String s:fSadrzaj)
		{
			sadrzaj.add(s);
			System.out.println(s);
		}
		sadrzaj.remove(sadrzaj.size()-1);
		System.out.println("Vel: "+fSadrzaj.size());
		for(String inst:sadrzaj)
		{
			String masinska=Bootloader.getAsembler().pretvoriuMasinskiKod(inst);
			masinska=masinska.replace(' ', '0');
			System.out.println("inst"+masinska +"vel"+masinska.length());
			this.instrukcije.add(masinska);}
		
	}
	public ProcesStanje getStanje() {
		return stanje;
	}
	public void setStanje(ProcesStanje stanje) {
		this.stanje = stanje;
	}
	public boolean ucitajProces(Particija particija)
	{
		Particija pomocna=Memorija.zauzmiParticiju(particija, this);
		return pomocna!=null;
	}
	public int getpID() {
		return pID;
	}
	public void setpID(int pID) {
		this.pID = pID;
	}
	public String getNaziv() {
		return naziv;
	}
	// void setNaziv(String naziv) {
//		this.naziv = naziv;
	//}
	public String getUlazniFajl() {
		return ulazniFajl;
	}
	//public void setUlazniFajl(String ulazniFajl) {
	//	this.ulazniFajl = ulazniFajl;
	//}
	public int getPrioritet() {
		return prioritet;
	}
	public void setPrioritet(int prioritet) {
		this.prioritet = prioritet;
	}
	public int getVelicina() {
		return velicina;
	}
	public void setVelicina(int velicina) {
		this.velicina = velicina;
	}
	public long getStartVrijeme() {
		return startVrijeme;
	}
	public void setStartVrijeme(long startVrijeme) {
		this.startVrijeme = startVrijeme;
	}
	public long getSveukupnoVrijeme() {
		return sveukupnoVrijeme;
	}
	public void setSveukupnoVrijeme(long sveukupnoVrijeme) {
		this.sveukupnoVrijeme = sveukupnoVrijeme;
	}
	public ArrayList<String> getInstrukcije() {
		return instrukcije;
	}
	public void setInstrukcije(ArrayList<String> instrukcije) {
		this.instrukcije = instrukcije;
	}
	public int[] getVrijednostRegistara() {
		return vrijednostRegistara;
	}
	public void setVrijednostRegistara(int[] vrijednostRegistara_) {
		System.arraycopy(vrijednostRegistara_, 0, this.vrijednostRegistara,0,vrijednostRegistara_.length);
	}
	public int getPC() {
		return PC;
	}
	public void setPC(int pC) {
		PC = pC;
	}
	public int getVelicinaInstrukcija() {
		return velicinaInstrukcija;
	}
	public void setVelicinaInstrukcija(int velicinaInstrukcija) {
		this.velicinaInstrukcija = velicinaInstrukcija;
	}
	public Path getPutanjaDoFajla() {
		return putanjaDoFajla;
	}
	public void setPutanjaDoFajla(Path putanjaDoFajla) {
		this.putanjaDoFajla = putanjaDoFajla;
	}
	public int getRezultatProcesa() {
		return rezultatProcesa;
	}
	public void setRezultatProcesa(int rezultatProcesa) {
		this.rezultatProcesa = rezultatProcesa;
	}
	public String getIzlazniFajl() {
		return izlazniFajl;
	}
	public void setIzlazniFajl(String izlazniFajl) {
		this.izlazniFajl = izlazniFajl;
	}
	public boolean isSpreman()
	{
		return this.stanje.equals(ProcesStanje.SPREMAN);
	}
	public boolean isIzrsava()
	{
		return this.stanje.equals(ProcesStanje.IZVRSAVANJE);
	}
	public boolean isPrekinut()
	{
		return this.stanje.equals(ProcesStanje.PREKINUT);
	}
	public boolean isGotov()
	{
		return this.stanje.equals(ProcesStanje.GOTOV);
	}
	public boolean isBlokiran()
	{
		return this.stanje.equals(ProcesStanje.BLOKIRAN);
	}
	public boolean isSuspendovan()
	{
		return this.stanje.equals(ProcesStanje.SUSPENDOVAN);
	}
}
