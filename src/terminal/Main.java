package terminal;

import memorija.Memorija;
import memorija.SistemFajlova;
import procesor.Proces;

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
		
		//case "htop":Proces.
		}
	}
}
}
