package memorija;

public class Blok {
	private final static int BLOK_VELICINA = 4;
    private byte[] sadrzaj = new byte[BLOK_VELICINA];
    private final int adresa;
    private boolean zauzet;
    private Blok sljedeci;
    
    public Blok(int adresa) {
        this.adresa = adresa;
        setZauzet(false);
        sljedeci=null;
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

	

	public static int getVelicina() {
		return BLOK_VELICINA;
	}

	public int getAdresa() {
		return adresa;
	}

	

	public Blok getSljedeci() {
		return sljedeci;
	}

	public void setSljedeci(Blok sljedeci) {
		this.sljedeci = sljedeci;
	}

	public static int getBlokVelicina() {
		return BLOK_VELICINA;
	}
}
