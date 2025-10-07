package viewmodel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import crud.CDepartman;
import crud.CManager;
import crud.CProjekat;
import crud.CRadiNa;
import crud.CRadnik;
import crud.CTables;
import crud.CZaposleni;
import model.VD368Departman;
import model.VD368Manager;
import model.VD368Projekat;
import model.VD368RadiNa;
import model.VD368Radnik;
import model.VD368Zaposleni;

public class ViewModel {
	
	private final PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
	
	private ArrayList<VD368Zaposleni> dataZaposleni = new ArrayList<VD368Zaposleni>();
	private ArrayList<VD368Radnik> dataRadnik = new ArrayList<VD368Radnik>();
	private ArrayList<VD368Manager> dataManager = new ArrayList<VD368Manager>();
	private ArrayList<VD368Departman> dataDepartman = new ArrayList<VD368Departman>();
	private ArrayList<VD368Projekat> dataProjekat= new ArrayList<VD368Projekat>();
	private ArrayList<VD368RadiNa> dataRadiNa= new ArrayList<VD368RadiNa>();
	private ArrayList<String> dataTables = new ArrayList<String>();
	
	public void setDataZapolseni(ArrayList<VD368Zaposleni> list) {
		ArrayList<VD368Zaposleni> oldState = this.dataZaposleni;
		this.dataZaposleni = list;
		support.firePropertyChange("dataZaposleni", oldState, this.dataZaposleni);
		
	}
	
	public ArrayList<VD368Zaposleni> getDataZapolseni() {
		return dataZaposleni;
	}
	
	public void setDataRadnik(ArrayList<VD368Radnik> list) {
		ArrayList<VD368Radnik> oldState = this.dataRadnik;
		this.dataRadnik = list;
		support.firePropertyChange("dataRadnik", oldState, this.dataRadnik);
	}
	
	public ArrayList<VD368Radnik> getDataRadnik() {
		return dataRadnik;
	}
	
	public void setDataManager(ArrayList<VD368Manager> list) {
		ArrayList<VD368Manager> oldState = this.dataManager;
		this.dataManager = list;
		support.firePropertyChange("dataManager", oldState, this.dataManager);
	}
	
	public ArrayList<VD368Manager> getDataManager() {
		return dataManager;
	}
	
	public void setDataDepartman(ArrayList<VD368Departman> list) {
		ArrayList<VD368Departman> oldState = this.dataDepartman;
		this.dataDepartman = list;
		support.firePropertyChange("dataDepartman", oldState, this.dataDepartman);
	}
	
	public ArrayList<VD368Departman> getDataDepartman() {
		return dataDepartman;
	}
	
	public void setDataProjekat(ArrayList<VD368Projekat> list) {
		ArrayList<VD368Projekat> oldState = this.dataProjekat;
		this.dataProjekat = list;
		support.firePropertyChange("dataProjekat", oldState, this.dataProjekat);
	}
	
	public ArrayList<VD368Projekat> getDataProjekat() {
		return dataProjekat;
	}
	
	public void setDataRadiNa(ArrayList<VD368RadiNa> list) {
		ArrayList<VD368RadiNa> oldState = this.dataRadiNa;
		this.dataRadiNa = list;
		support.firePropertyChange("dataRadiNa", oldState, this.dataRadiNa);
	}
	
	public ArrayList<VD368RadiNa> getDataRadiNat() {
		return dataRadiNa;
	}
	
	public void setDataTables(ArrayList<String> tables) {
		ArrayList<String> oldState = this.dataTables;
		this.dataTables = tables;
		support.firePropertyChange("tablesNames", oldState, this.dataTables);
	}
	
	public ArrayList<String> getDataTables() {
		return dataTables;
	}
	
	public void updateDataZaposleni() {
		CZaposleni cz = new CZaposleni();
		setDataZapolseni((ArrayList<VD368Zaposleni>)cz.listZaposleni());
	}
	
	public void updateDataRadnik() {
		CRadnik cr = new CRadnik();
		setDataRadnik((ArrayList<VD368Radnik>)cr.listRadnik());
	}
	
	public void updateDataManager() {
		CManager cm = new CManager();
		setDataManager((ArrayList<VD368Manager>)cm.listManager());
	}
	
	public void updateDataDepartman() {
		CDepartman cd = new CDepartman();
		setDataDepartman((ArrayList<VD368Departman>)cd.listDepartman());
	}
	
	public void updateDataProjekat() {
		CProjekat cp = new CProjekat();
		setDataProjekat((ArrayList<VD368Projekat>)cp.listProjekat());
	}
	
	public void updateDataRadiNa() {
		CRadiNa cr = new CRadiNa();
		setDataRadiNa((ArrayList<VD368RadiNa>)cr.listRadiNa());
	}
	
	public void updateDataTables() throws SQLException {
		CTables ct = new CTables();
		setDataTables(ct.getTablesNames());
	}
	
	public boolean uploadNewDepartman(String name, String tip, LocalDate date, Object man) {
		VD368Manager manager = (VD368Manager) man;
		CDepartman cd = new CDepartman();
		VD368Departman dep = new VD368Departman(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), manager.getZapMbrZ(), name, tip);
		cd.insertDepartman(dep, manager);
		
		VD368Departman depFound = cd.findDepartman(dep);
		if(depFound != null) {
			this.updateDataDepartman();
			return true;
		}
		return false;
	}
	
	public boolean uploadNewProjekat(String name, Object dep) {
		VD368Departman departman = (VD368Departman) dep;
		CProjekat cp = new CProjekat();
		VD368Projekat proj = new VD368Projekat(name);
		cp.insertProjekat(proj, departman);
		
		VD368Projekat projFound = cp.findProjekat(proj);
		if(projFound != null) {
			this.updateDataProjekat();
			return true;
		}
		return false;
	}
	
	public ArrayList<VD368Projekat> getProjekatsOfDepartman(Object dep) {
		VD368Departman departman = (VD368Departman) dep;
		CDepartman cd = new CDepartman();
		
		VD368Departman depFound = cd.findDepartman(departman);
		
		if(depFound != null) {
			ArrayList<VD368Projekat> res =  new ArrayList<VD368Projekat>(departman.getVD368Projekats());
			return res;
		}
		return null;
	}

	public boolean deleteDepartman(Object dep) {
		
		VD368Departman departman = (VD368Departman) dep;
		CDepartman cd = new CDepartman();
		if(departman != null) {
			cd.removeConnsOfDepartman(departman);
		}
		else
			return false;
		return true;
		
	}
	
	public boolean uploadNewEntity(String entityType, String[] properties){
		
		int mbr = -1;
		int plata = -1;
		int staz = -1;
		String ime = null;
		String prz = null;
		String naziv = null;
		boolean persisted = false;
		
		switch (entityType) {
			case "zap":
				try {
					mbr = Integer.parseInt(properties[0]);
					ime = properties[1];
					prz = properties[2];
					CZaposleni cz = new CZaposleni();
					persisted = cz.insertZaposleni(new VD368Zaposleni(mbr, ime, prz));
				}
				catch (Exception ex){
					persisted = false;
				}
				break;
			case "man":
				try {
					mbr = Integer.parseInt(properties[0]);
					plata = Integer.parseInt(properties[1]);
					CManager cm = new CManager();
					persisted = cm.insertManager(new VD368Manager(mbr, plata), new VD368Zaposleni(mbr));
				}
				catch (Exception ex){
					persisted = false;
				}
				break;
			case "rad":
				try {
					mbr = Integer.parseInt(properties[0]);
					staz = Integer.parseInt(properties[1]);
					CRadnik cr = new CRadnik();
					persisted = cr.insertRadnik(new VD368Radnik(mbr, staz), new VD368Zaposleni(mbr));
				}
				catch (Exception ex){
					persisted = false;
				}
				break;
//			case "dep":
//				try {
//					mbr = Integer.parseInt(properties[0]);
//					staz = Integer.parseInt(properties[1]);
//					CRadnik cr = new CRadnik();
//					persisted = cr.InsertRadnik(new VD368Radnik(mbr, staz), new VD368Zaposleni(mbr));
//				}
//				catch (Exception ex){
//					persisted = false;
//				}
//				break;
			case "proj":
				try {
					naziv = properties[0];
					mbr = Integer.parseInt(properties[1]);
					CProjekat cp = new CProjekat();
					persisted = cp.insertProjekat(new VD368Projekat(naziv), new VD368Departman(mbr));
				}
				catch (Exception ex){
					persisted = false;
				}
				break;
			case "radina":
				try {
					int mbrRad = Integer.parseInt(properties[0]);
					int mbrProj = Integer.parseInt(properties[1]);
					int brc = Integer.parseInt(properties[2]);
					CRadiNa cr = new CRadiNa();
					persisted = cr.insertRadiNa(new VD368RadiNa(brc), new VD368Radnik(mbrRad), new VD368Projekat(mbrProj));
				}
				catch (Exception ex){
					persisted = false;
				}
				break;
		}
		
		return persisted;
	}
}
