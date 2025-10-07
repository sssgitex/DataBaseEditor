package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the 368vd_departman database table.
 * 
 */
@Entity
@Table(name="368vd_departman")
@NamedQuery(name="VD368Departman.findAll", query="SELECT v FROM VD368Departman v")
public class VD368Departman implements Serializable, MyTableModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="manager_zap_mbr_z")
	private int managerZapMbrZ;

	@Temporal(TemporalType.DATE)
	@Column(name="datum_pos")
	private Date datumPos;

	@Column(name="naziv_d")
	private String naziv;

	@Column(name="tip_d")
	private String tip;

	//bi-directional many-to-one association to VD368Projekat
	@OneToMany(mappedBy="VD368Departman", fetch = FetchType.EAGER)
	private List<VD368Projekat> VD368Projekats = new ArrayList<VD368Projekat>();

	//bi-directional one-to-one association to VD368Manager
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="manager_zap_mbr_z", referencedColumnName="zap_mbr_z")
	private VD368Manager VD368Manager;
	

	public VD368Departman() {
	}
	
	public VD368Departman(int mbr) {
		this.managerZapMbrZ = mbr;
	}
	
	public VD368Departman(Date date, int mbr, String naziv, String tip) {
		this.datumPos = date;
		this.managerZapMbrZ = mbr;
		this.naziv = naziv;
		this.tip = tip;
	}
	
	public int getObjectId() {
		return this.getManagerZapMbrZ();
	}

	
	public String[] getAsArray() {
		String[] result = new String[]{
				Integer.toString(managerZapMbrZ),
				naziv,
				tip,
				datumPos.toString()
				
		};
		return result;
	}

	public int getManagerZapMbrZ() {
		return this.managerZapMbrZ;
	}

	public void setManagerZapMbrZ(int managerZapMbrZ) {
		this.managerZapMbrZ = managerZapMbrZ;
	}

	public Date getDatumPos() {
		return this.datumPos;
	}

	public void setDatumPos(Date datumPos) {
		this.datumPos = datumPos;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getTip() {
		return this.tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public List<VD368Projekat> getVD368Projekats() {
		return this.VD368Projekats;
	}

	public void setVD368Projekats(List<VD368Projekat> VD368Projekats) {
		this.VD368Projekats = VD368Projekats;
	}

	public VD368Projekat addVD368Projekat(VD368Projekat VD368Projekat) {
		getVD368Projekats().add(VD368Projekat);
		VD368Projekat.setVD368Departman(this);

		return VD368Projekat;
	}

	public VD368Projekat removeVD368Projekat(VD368Projekat VD368Projekat) {
		
		ArrayList<VD368Projekat> list = new ArrayList<VD368Projekat> (getVD368Projekats());
		list.remove(VD368Projekat);
		setVD368Projekats(list);

		return VD368Projekat;
	}
	
	public VD368Manager getVD368Manager() {
		return this.VD368Manager;
	}

	public void setVD368Manager(VD368Manager VD368Manager) {
		this.VD368Manager = VD368Manager;
	}

}