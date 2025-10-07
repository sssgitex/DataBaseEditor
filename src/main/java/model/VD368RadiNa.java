package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the 368vd_radi_na database table.
 * 
 */
@Entity
@Table(name="368vd_radi_na")
@NamedQuery(name="VD368RadiNa.findAll", query="SELECT v FROM VD368RadiNa v")
public class VD368RadiNa implements Serializable, MyTableModel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VD368RadiNaPK id;
	
	@ManyToOne
    @MapsId("radnikZapMbrZ")
    @JoinColumn(name = "radnik_zap_mbr_z")
    VD368Radnik radnik;

	@ManyToOne
    @MapsId("projekatSifraP")
    @JoinColumn(name = "projekat_sifra_p")
    VD368Projekat projekat;

    @Column(name="brcasova")
	private int brcasova;

	public VD368RadiNa() {
	}
	
	public VD368RadiNa(int brcasova) {
		this.brcasova = brcasova;
	}
	
	public String[] getAsArray() {
		String[] result = new String[]{
				Integer.toString(id.getRadnikZapMbrZ()),
				Integer.toString(id.getProjekatSifraP()),
				Integer.toString(brcasova)
		};
		return result;
	}
	
	public VD368RadiNaPK getObjectPk() {
		return id;
	}
	
	public int getObjectId() {
		return -1;
	}
	
   public VD368Radnik getRadnik() {
		return radnik;
	}

	public void setRadnik(VD368Radnik radnik) {
		this.radnik = radnik;
	}

	public VD368Projekat getProjekat() {
		return projekat;
	}

	public void setProjekat(VD368Projekat projekat) {
		this.projekat = projekat;
	}

	public VD368RadiNaPK getId() {
		return this.id;
	}

	public void setId(VD368RadiNaPK id) {
		this.id = id;
	}

	public int getBrcasova() {
		return this.brcasova;
	}

	public void setBrcasova(int brcasova) {
		this.brcasova = brcasova;
	}

}