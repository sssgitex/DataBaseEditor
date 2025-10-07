package main;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import viewmodel.ViewModel;

public class Main {
	public static void main(String[] args) throws SQLException, ParseException {
//		CZaposleni cz = new CZaposleni();
//		cz.InsertZaposleni(new VD368Zaposleni(999 ,"Test", "Test"));
//		
//		System.out.println(cz.listZaposleni());
//		
//		CTables ct = new CTables();
//		
//		System.out.println(ct.tablesNames());
//		
//		CRadnik cr = new CRadnik();
//		System.out.println(cr.listRadnik());
		
//		CManager cm = new CManager();
//		CRadnik cr = new CRadnik();
//		CDepartman cd = new CDepartman();
//		
//		
//		VD368Departman d = new VD368Departman(new Date(), 999, "test", "test");
		///VD368Manager m = new VD368Manager(711, 155000);
//		VD368Radnik r = new VD368Radnik(999, 5);
//		//m.setZapMbrZ(710);
		//cm.InsertZaposleni(m, new VD368Zaposleni(711, "Test", "Test"));
//		cr.InsertZaposleni(r, new VD368Zaposleni(999, "Test", "Test"));
//		cd.InsertDepartman(d, new VD368Manager(999, 999));
		
//		CProjekat cp = new CProjekat();
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//Date d = formatter.parse("2024-08-13");
//		VD368Projekat p = new VD368Projekat(6);
//		//p.setVD368Departman(new VD368Departman(d, 999, "test1", "test1"));
//		//System.out.println(p.getVD368Departman().getNaziv());
//		cp.InsertProjekat(p, new VD368Departman(d, 999, "test1", "test1"));
		
		//CRadiNa cr = new CRadiNa();
		//cr.InsertRadnik(new VD368RadiNa(999), new VD368Radnik(999, 6), new VD368Projekat(6, new VD368Departman(d, 7, "DoF", "finance")));
		// adds new radnik and project ???
		
//		CRadnik cr = new CRadnik();
		//System.out.println(cr.checkRadniks());
		//cr.removeRadniks(cr.checkRadniks());
//		cr.RemoveRadnik(new VD368Radnik(777, 10));
		
//		CProjekat cp = new CProjekat();
//		cp.RemoveProjekat(new VD368Projekat(6));
//		
//		for(VD368Projekat p : cp.listProjekat()) {
//			for(String s: p.getAsArray())
//				System.out.println(s);
//		}
//		
//		CDepartman cd = new CDepartman();
//		cd.RemoveDepartman(new VD368Departman(999));
		//cd.UpdateDepartman(new VD368Departman(999));
		
//		CZaposleni cz = new CZaposleni();
//		cz.updateZaposleni(new VD368Zaposleni(767), "ONA", "ONA");
		
		System.exit(0);
	}
}
