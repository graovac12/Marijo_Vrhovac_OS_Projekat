package terminal;

import java.util.Scanner;

import memorija.Memorija;
import memorija.SekundarnaMemorija;
import memorija.SistemFajlova;
import procesor.Bootloader;
import procesor.Proces;
import procesor.RasporedjivacProcesa;

public class Main {
public static void izvrsiKomandu(String ulaz)
{
	String[] komande=ulaz.split(" ");
	if(komande.length==1)
	{
		switch(komande[0])
		{
		case "ls":SistemFajlova.prikaziDirektorij();break;
		case "mem":Memorija.ispisStanja();break;
		case "exit":System.exit(1);break;
		case "htop":RasporedjivacProcesa.htop();break;
		case "cls":System.out.print("\033[H\033[2J");System.out.flush();break;
		case "diskfree":System.out.println(SekundarnaMemorija.brojSlobodnih());break;
		default:System.out.println("Komanda ne postoji");break;
		}
	}
	else if(komande.length==2)
	{
		switch(komande[0])
		{
		case "cd":SistemFajlova.promijeniDirektorij(komande[1]);break;
		case "load":Proces p=new Proces(komande[1],komande[1],null,0);break;
		case "kill":RasporedjivacProcesa.procesi.get(Integer.parseInt(komande[1])).prekiniProces();break;
		case "rmdir":SistemFajlova.obrisiFajl(komande[1]);break;
		case "rmf":SistemFajlova.obrisiFajl(komande[1]);Bootloader.refreshTree();break;
		case "mkdir":SistemFajlova.napraviDirektorij(komande[1]);break;
		default: System.out.println("Komanda ne postoji");break;
		}
	}else if(komande.length==3)
	{
		switch(komande[0])
		{
		case "rmdir":SistemFajlova.premimenujDirektorij(komande[1], komande[2]);break;
		default: System.out.println("Komanda ne postoji");break;
		}
	}
}
public static void main(String[] args)
{
	Bootloader.ukljucivanje();
	Scanner scanner=new Scanner(System.in);
	//System.out.println(Bootloader.getDisk().fajlovi.get(1).getIndeksBlok());
	//Bootloader.getDisk().procitajFajl(Bootloader.getDisk().fajlovi.get(0));
	while(true)
	{
		System.out.print("root$/");
		String ulaz=scanner.nextLine();
		izvrsiKomandu(ulaz);
	}
}

}
