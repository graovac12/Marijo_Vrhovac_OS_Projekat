package memorija;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.control.TreeItem;
import procesor.Bootloader;
import procesor.Proces;

public class SistemFajlova {
	private static File korijen;
	private static File trenutni;
	private TreeItem<File> cvor;
	public SistemFajlova(File putanja)
	{
		System.out.println("Sistem fajlova kreiran:");
		korijen=putanja;
		trenutni=korijen;
		cvor=new TreeItem<>(korijen);
		kreirajDrvo(cvor);
		
	}
	public void kreirajDrvo(TreeItem<File> korijen)
	{
		try(DirectoryStream<Path> directoryStream =Files.newDirectoryStream(Paths.get(korijen.getValue().getAbsolutePath())))
		{
			for(Path putanja:directoryStream)
			{
				TreeItem<File> item=new TreeItem<>(putanja.toFile());
				item.setExpanded(false);
				korijen.getChildren().add(item);
				if(Files.isDirectory(putanja))
				{
					kreirajDrvo(item);
				}
				else
				{
					byte[] sadrzaj=Files.readAllBytes(item.getValue().toPath());
					Fajl noviFajl=new Fajl(item.getValue().getName(),sadrzaj);
					System.out.println(noviFajl.getVelicina());
					if(!Bootloader.getDisk().sadrziFajl(item.getValue().getName()))
					{
						Bootloader.getDisk().sacuvajFajl(noviFajl);
					}
				}
						
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public TreeItem<File> getDrvoItem()
	{
		cvor=new TreeItem<>(trenutni);
		kreirajDrvo(cvor);
		return cvor;
	}
	public static void napraviFajl(Proces proces)
	{
		if(proces.getIzlazniFajl()==null)
			return;
		File novi =new File(proces.getPutanjaDoFajla().getParent()+"/"+proces.getIzlazniFajl()+".txt");
		try {
			FileWriter fw=new FileWriter(novi);
			fw.write("Rezultat: "+ proces.getRezultatProcesa()+" Naziv procesa: "+proces.getNaziv());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void promijeniDirektorij(String dir)
	{
		if(dir.compareTo("...")==0)
			trenutni=korijen;
		else if(dir.compareTo("..")==0 && !trenutni.equals(korijen))
		{
			trenutni=trenutni.getParentFile();
		}
		else
		{
			for(TreeItem<File> fajl:Bootloader.getRoot().getDrvoItem().getChildren())
			{
				if(fajl.getValue().getName().equals(dir)&& fajl.getValue().isDirectory())
				{
					trenutni=fajl.getValue();
				}
			}
		}
	}
	public static void napraviDirektorij(String dir)
	{
		File folder=new File(trenutni.getAbsolutePath()+"/"+dir);
			if(!folder.exists())
				folder.mkdir();
					
				
	}
	public static void obrisiDirektorij(String dir)
	{
		 for (TreeItem<File> fajl : Bootloader.getRoot().getDrvoItem().getChildren()) {
	            if (fajl.getValue().getName().equals(dir) && fajl.getValue().isDirectory())
	                fajl.getValue().delete();
	        }
	}
	public static void premimenujDirektorij(String dirStari,String dirNovi)
	{
		for (TreeItem<File> fajl : Bootloader.getRoot().getDrvoItem().getChildren()) {
            if (fajl.getValue().getName().equals(dirStari) && fajl.getValue().isDirectory())
                fajl.getValue().renameTo(new File(trenutni.getAbsolutePath()+"/"+dirNovi));
        }
	}
	public static void obrisiFajl(String fIme)
	{
		for (TreeItem<File> fajl : Bootloader.getRoot().getDrvoItem().getChildren()) {
            if (fajl.getValue().getName().equals(fIme) && fajl.getValue().isFile())
                fajl.getValue().delete();
            if(Bootloader.getDisk().sadrziFajl(fIme))
            {
            	Bootloader.getDisk().obrisiFajl(Bootloader.getDisk().getFajl(fIme));
            }
        }
	}
	public static void prikaziDirektorij()
	{
		System.out.println("Direktorij: "+trenutni.getName());
		for(TreeItem<File> fajl:Bootloader.getRoot().getDrvoItem().getChildren())
		{
			byte[] sadrzajDatoteke=null;
			try {
				if(!fajl.getValue().isDirectory())
				{
					sadrzajDatoteke=Files.readAllBytes(fajl.getValue().toPath());
				}
				if(fajl.getValue().isDirectory())
					System.out.println("dir "+ fajl.getValue().getName());
				else
					System.out.println("dat "+ fajl.getValue().getName()+" "+fajl.getValue().getName()+" "+sadrzajDatoteke.length);
			}catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	public  File getKorijen() {
		return korijen;
	}
	public void setKorijen(File korijen) {
		SistemFajlova.korijen = korijen;
	}
	public File getTrenutni() {
		return trenutni;
	}
	public void setTrenutni(File trenutni) {
		SistemFajlova.trenutni = trenutni;
	}
	public TreeItem<File> getCvor() {
		return cvor;
	}
	public void setCvor(TreeItem<File> cvor) {
		this.cvor = cvor;
	}
}
