package memorija;

import java.util.Arrays;

public class Blok {
	private final static int BLOK_VELICINA = 4;
    private byte[] sadrzaj = new byte[BLOK_VELICINA];
    private final int adresa;
    private boolean zauzet;
    private int sljedeci;
    
    public Blok(int adresa) {
        this.adresa = adresa;
       zauzet=false;
        sljedeci=-1;
    }
   
    public byte[] getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(byte[] sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public boolean isZauzet() {
		return zauzet;
	}

	public void setZauzet(boolean zauzet) {
		this.zauzet = zauzet;
	}

	

	@Override
	public String toString() {
		return "Blok [sadrzaj=" + Arrays.toString(sadrzaj) + ", adresa=" + adresa + ", zauzet=" + zauzet + ", sljedeci="
				+ sljedeci + "]";
	}

	public static int getVelicina() {
		return BLOK_VELICINA;
	}

	public int getAdresa() {
		return adresa;
	}

	

	public int getSljedeci() {
		return sljedeci;
	}

	public void setSljedeci(int sljedeci) {
		this.sljedeci = sljedeci;
	}

	public static int getBlokVelicina() {
		return BLOK_VELICINA;
	}
}
