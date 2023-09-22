package procesor;

import memorija.SekundarnaMemorija;
import memorija.SistemFajlova;
import memorija.UpravljacMemorije;

public class Bootloader {
	private static Procesor procesor;
	private static UpravljacMemorije upravljacMemorije;
	private static Asembler asembler;
	private static SekundarnaMemorija disk;
	private static SistemFajlova root;
	
	public static void ukljucivanje()
	{
		//root=new SistemFajlova();
		disk=new SekundarnaMemorija();
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
	
}
