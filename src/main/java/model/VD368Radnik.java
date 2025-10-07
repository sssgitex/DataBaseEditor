package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the 368vd_radnik database table.
 * 
 */
@Entity
@Table(name="368vd_radnik")
@NamedQuery(name="VD368Radnik.findAll", query="SELECT v FROM VD368Radnik v")
public class VD368Radnik implements Serializable, MyTableModel {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="zap_mbr_z")
	private int zapMbrZ;

	private int staz;

	//bi-directional one-to-one association to VD368Zaposleni
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="zap_mbr_z")
	private VD368Zaposleni VD368Zaposleni;
	
	//bi-directional many-to-many association to VD368Radnik
	@OneToMany(mappedBy="radnik")
	private List<VD368RadiNa> radiNas;

	public VD368Radnik() {
	}
	
	public VD368Radnik(int mbr) {
		this.zapMbrZ = mbr;
	}
	
	public VD368Radnik(int mbr, int staz) {
		//this.VD368Projekats = new ArrayList<VD368Projekat>();
		this.zapMbrZ = mbr;
		this.staz = staz;
	}
	
	public int getObjectId() {
		return this.getZapMbrZ();
	}

	
	public String[] getAsArray() {
		String[] result = new String[]{
				Integer.toString(zapMbrZ),
				Integer.toString(staz)
		};
		return result;
	}

	public int getZapMbrZ() {
		return this.zapMbrZ;
	}

	public void setZapMbrZ(int zapMbrZ) {
		this.zapMbrZ = zapMbrZ;
	}

	public int getStaz() {
		return this.staz;
	}

	public void setStaz(int staz) {
		this.staz = staz;
	}

	public VD368Zaposleni getVD368Zaposleni() {
		return this.VD368Zaposleni;
	}

	public void setVD368Zaposleni(VD368Zaposleni VD368Zaposleni) {
		this.VD368Zaposleni = VD368Zaposleni;
	}
	
	public List<VD368RadiNa> getRadiNas() {
		return radiNas;
	}

	public void setRadiNas(List<VD368RadiNa> radiNas) {
		this.radiNas = radiNas;
	}
	
	public void removeRadiNa(VD368RadiNa radiNa) {
		getRadiNas().remove(radiNa);
	}

}