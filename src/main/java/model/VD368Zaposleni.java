package model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the 368vd_zaposleni database table.
 * 
 */
@Entity
@Table(name="368vd_zaposleni")
@NamedQuery(name="VD368Zaposleni.findAll", query="SELECT v FROM VD368Zaposleni v")
public class VD368Zaposleni  implements Serializable, MyTableModel {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="zap_mbr_z")
	private int zapMbrZ;

	@Column(name="zap_ime")
	private String zapIme;

	@Column(name="zap_prezime")
	private String zapPrezime;

	//bi-directional one-to-one association to VD368Manager
	@OneToOne(mappedBy="VD368Zaposleni")
	private VD368Manager VD368Manager;

	//bi-directional one-to-one association to VD368Radnik
	@OneToOne(mappedBy="VD368Zaposleni")
	private VD368Radnik VD368Radnik;

	public VD368Zaposleni() {
	}
	
	public VD368Zaposleni(int mbr) {
		this.zapMbrZ = mbr;
	}
	
	public VD368Zaposleni(String ime, String prz) {
		this.zapIme = ime;
		this.zapPrezime = prz;
	}
	
	public VD368Zaposleni(int mbr, String ime, String prz) {
		this.zapMbrZ = mbr;
		this.zapIme = ime;
		this.zapPrezime = prz;
	}
	
	public int getObjectId() {
		return this.getZapMbrZ();
	}

	
	public String[] getAsArray() {
		String[] result = new String[]{
				Integer.toString(zapMbrZ),
				zapIme,
				zapPrezime
		};
		return result;
	}

	public int getZapMbrZ() {
		return this.zapMbrZ;
	}

	public void setZapMbrZ(int zapMbrZ) {
		this.zapMbrZ = zapMbrZ;
	}

	public String getZapIme() {
		return this.zapIme;
	}

	public void setZapIme(String zapIme) {
		this.zapIme = zapIme;
	}

	public String getZapPrezime() {
		return this.zapPrezime;
	}

	public void setZapPrezime(String zapPrezime) {
		this.zapPrezime = zapPrezime;
	}

	public VD368Manager getVD368Manager() {
		return this.VD368Manager;
	}

	public void setVD368Manager(VD368Manager VD368Manager) {
		this.VD368Manager = VD368Manager;
	}

	public VD368Radnik getVD368Radnik() {
		return this.VD368Radnik;
	}

	public void setVD368Radnik(VD368Radnik VD368Radnik) {
		this.VD368Radnik = VD368Radnik;
	}

}