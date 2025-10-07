package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the 368vd_radi_na database table.
 * 
 */
@Embeddable
public class VD368RadiNaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="radnik_zap_mbr_z")
	private int radnikZapMbrZ;

	@Column(name="projekat_sifra_p")
	private int projekatSifraP;

	public VD368RadiNaPK() {
	}
	
	public VD368RadiNaPK(int mbr, int sifra) {
		this.radnikZapMbrZ = mbr;
		this.projekatSifraP = sifra;
	}
	public int getRadnikZapMbrZ() {
		return this.radnikZapMbrZ;
	}
	public void setRadnikZapMbrZ(int radnikZapMbrZ) {
		this.radnikZapMbrZ = radnikZapMbrZ;
	}
	public int getProjekatSifraP() {
		return this.projekatSifraP;
	}
	public void setProjekatSifraP(int projekatSifraP) {
		this.projekatSifraP = projekatSifraP;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VD368RadiNaPK)) {
			return false;
		}
		VD368RadiNaPK castOther = (VD368RadiNaPK)other;
		return 
			(this.radnikZapMbrZ == castOther.radnikZapMbrZ)
			&& (this.projekatSifraP == castOther.projekatSifraP);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.radnikZapMbrZ;
		hash = hash * prime + this.projekatSifraP;
		
		return hash;
	}
}