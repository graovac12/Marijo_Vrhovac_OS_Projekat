package memorija;

import procesor.Proces;

public class Particija {
	private Proces proces;
	private int zauzeto;
	private int velicina;
	public Particija(int velicina)
	{
		proces=null;
		zauzeto=0;
		this.velicina=velicina;
	}
	@Override
	public String toString() {
		return "Particija [zauzeto=" + zauzeto + ", velicina=" + velicina + "]";
	}
	public boolean equals(Particija particija)
	{
		if(this.proces==particija.proces && this.zauzeto==particija.zauzeto && this.velicina==particija.velicina)
			return true;
		return false;
	}
	public Proces getProces() {
		return proces;
	}
	public void setProces(Proces proces) {
		this.proces = proces;
	}
	public int getZauzeto() {
		return zauzeto;
	}
	public boolean isKoristenje()
	{
		if(this.proces!=null)
			return true;
		return false;
	}
	public void setZauzeto(int zauzeto) {
		this.zauzeto = zauzeto;
	}
	public int getVelicina() {
		return velicina;
	}
	public void setVelicina(int velicina) {
		this.velicina = velicina;
	}
	public Particija(Particija p1, Particija p2)
	{
		velicina=p1.velicina+p2.velicina;
		zauzeto=0;
	}
	public void oslobodiParticiju()
	{
		if(this.proces!=null)
		{
			this.proces=null;
			zauzeto=0;
		}
	}
	public Particija zauzmiParticiju(Proces proces)
	{
		oslobodiParticiju();
		if(proces.getVelicina()>velicina)
			return null;
		this.proces=proces;
		this.zauzeto=proces.getVelicina();
		return this;
	}
	public int slobodnoProstora()
	{
		return velicina-zauzeto;
	}
}
