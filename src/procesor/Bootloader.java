package procesor;

import java.io.File;

import memorija.Memorija;
import memorija.SekundarnaMemorija;
import memorija.SistemFajlova;
import memorija.UpravljacMemorije;

public class Bootloader {
	private static Procesor procesor;
	private static UpravljacMemorije upravljacMemorije;
	private static Asembler asembler;
	private static SekundarnaMemorija disk;
	private static SistemFajlova root;
	private static RasporedjivacProcesa rasporedjivac;
	
	public static void ukljucivanje()
	{
		upravljacMemorije=new UpravljacMemorije(4096);
		disk=new SekundarnaMemorija();
		root=new SistemFajlova(new File("C:\\Users\\graovac\\eclipse-workspace\\Marijo_Vrhovac_OS_Projekat\\src\\programi"));
		asembler=new Asembler();
		procesor=new Procesor(asembler);
		rasporedjivac=new RasporedjivacProcesa();
		rasporedjivac.start();
	}
	//private static 
	public static Procesor getProcesor() {
		return procesor;
	}
	public static SistemFajlova getRoot() {
		return root;
	}
	public static void setRoot(SistemFajlova root) {
		Bootloader.root = root;
	}
	public static void setProcesor(Procesor procesor) {
		Bootloader.procesor = procesor;
	}
	public static UpravljacMemorije getUpravljacMemorije() {
		return upravljacMemorije;
	}
	public static void setUpravljacMemorije(UpravljacMemorije upravljacMemorije) {
		Bootloader.upravljacMemorije = upravljacMemorije;
	}
	public static Asembler getAsembler() {
		return asembler;
	}
	public static void setAsembler(Asembler asembler) {
		Bootloader.asembler = asembler;
	}
	public static SekundarnaMemorija getDisk() {
		return disk;
	}
	public static void setDisk(SekundarnaMemorija disk) {
		Bootloader.disk = disk;
	}
	public static void refreshTree()
	{
		root=new SistemFajlova(new File("C:\\Users\\graovac\\eclipse-workspace\\Marijo_Vrhovac_OS_Projekat\\src\\programi"));
	}
	public static void main(String args[])
	{
		//ukljucivanje();
		//System.out.println(SekundarnaMemorija.slobodniBlokovi.size());
		//SistemFajlova.obrisiFajl("test03.asm");
		//root=new SistemFajlova(new File("C:\\Users\\graovac\\Desktop\\os-project_os-simulator-master\\src\\main\\resources\\programs"));
		//SistemFajlova.prikaziDirektorij();
		//SistemFajlova.obrisiDirektorij("l");
		//SistemFajlova.obrisiFajl(".txt");
		//Proces p=new Proces("a","test01.asm",null,0);
		//Proces p2=new Proces("a","test01.asm",null,0);
		//Memorija.ispisStanja();
		//System.out.println(p.getRezultatProcesa());

		
		
		
	}
}
