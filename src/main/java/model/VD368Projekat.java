package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the 368vd_projekat database table.
 * 
 */
@Entity
@Table(name="368vd_projekat")
@NamedQuery(name="VD368Projekat.findAll", query="SELECT v FROM VD368Projekat v")
public class VD368Projekat implements Serializable, MyTableModel {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sifra_p")
	private int sifraP;
	
	@Column(name="naziv_p")
	private String nazivP;

	//bi-directional many-to-one association to VD368Departman
	@ManyToOne
	@JoinColumn(name="departman_mbr_z")
	private VD368Departman VD368Departman;

	@OneToMany(mappedBy="projekat")
	private List<VD368RadiNa> radiNas;

	public VD368Projekat() {
	}
	
	public VD368Projekat(int sifra, String nazivP, VD368Departman VD368Departman) {
		this.sifraP = sifra;
		this.VD368Departman = VD368Departman;
		this.nazivP = nazivP;
	}
	
	public VD368Projekat(int sifra) {
		this.sifraP = sifra;
	}
	
	public VD368Projekat(String nazivP) {
		this.nazivP = nazivP;
	}
	
	public int getObjectId() {
		return this.getSifraP();
	}

	
	public String[] getAsArray() {
		String[] result = new String[]{
				Integer.toString(sifraP),
				nazivP,
				Integer.toString(VD368Departman.getManagerZapMbrZ()),
				VD368Departman.getNaziv()
		};
		return result;
	}

	public int getSifraP() {
		return this.sifraP;
	}

	public void setSifraP(int sifraP) {
		this.sifraP = sifraP;
	}

	public VD368Departman getVD368Departman() {
		return this.VD368Departman;
	}

	public void setVD368Departman(VD368Departman VD368Departman) {
		this.VD368Departman = VD368Departman;
	}
	

	public String getNazivP() {
		return nazivP;
	}

	public void setNazivP(String nazivP) {
		this.nazivP = nazivP;
	}
	
	public List<VD368RadiNa> getRadiNas() {
		return radiNas;
	}

	public void setRadiNas(List<VD368RadiNa> radiNas) {
		this.radiNas = radiNas;
	}


}