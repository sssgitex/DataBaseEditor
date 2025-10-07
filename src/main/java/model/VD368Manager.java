package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the 368vd_manager database table.
 * 
 */
@Entity
@Table(name="368vd_manager")
@NamedQuery(name="VD368Manager.findAll", query="SELECT v FROM VD368Manager v")
public class VD368Manager implements Serializable, MyTableModel {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="zap_mbr_z")
	private int zapMbrZ;

	private int plata;

	//bi-directional one-to-one association to VD368Zaposleni
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="zap_mbr_z")
	private VD368Zaposleni VD368Zaposleni;
	
	@OneToOne(mappedBy="VD368Manager")
	private VD368Departman VD368Departman;
	
	
	public String[] getAsArray() {
		String[] result = new String[]{
				Integer.toString(zapMbrZ),
				Integer.toString(plata)
		};
		return result;
	}
	
	
	public int getObjectId() {
		return this.getZapMbrZ();
	}

	public VD368Manager() {
	}
	
	public VD368Manager(int plata) {
		this.plata = plata;
	}
	
	public VD368Manager(int mbr, int plata) {
		this.zapMbrZ = mbr;
		this.plata = plata;
	}

	public int getZapMbrZ() {
		return this.zapMbrZ;
	}

	public void setZapMbrZ(int zapMbrZ) {
		this.zapMbrZ = zapMbrZ;
	}

	public int getPlata() {
		return this.plata;
	}

	public void setPlata(int plata) {
		this.plata = plata;
	}

	public VD368Zaposleni getVD368Zaposleni() {
		return this.VD368Zaposleni;
	}

	public void setVD368Zaposleni(VD368Zaposleni VD368Zaposleni) {
		this.VD368Zaposleni = VD368Zaposleni;
	}
	
	public VD368Departman getVD368Departman() {
		return VD368Departman;
	}

	public void setVD368Departman(VD368Departman vD368Departman) {
		VD368Departman = vD368Departman;
	}

}