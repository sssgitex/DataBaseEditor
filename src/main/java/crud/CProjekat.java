package crud;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.VD368Departman;
import model.VD368Projekat;
import model.VD368RadiNa;
import utils.PersistenceUtil;

public class CProjekat {
	public boolean insertProjekat(VD368Projekat projekat, VD368Departman dep) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		boolean stat = false;
		try {
			et = em.getTransaction();
			et.begin();
			
			dep = em.find(VD368Departman.class, dep.getManagerZapMbrZ());
			
			dep.addVD368Projekat(projekat);

			em.persist(projekat);
			
			em.flush(); 
			et.commit();
			stat = true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			if(et != null) {
				et.rollback();
			}
			stat = false;
		}
		finally{
			if(em != null)
				em.close();
		}
		return stat;
	}
	
	public void removeProjekat(VD368Projekat proj) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			proj = em.find(VD368Projekat.class, proj.getSifraP());
			
			VD368Departman dep = proj.getVD368Departman();
			proj.setVD368Departman(null);
			em.remove(proj);
			
			if(proj.getRadiNas().size() > 0) {
				CRadiNa cr = new CRadiNa();
				ArrayList<VD368RadiNa> list = new ArrayList<VD368RadiNa> (proj.getRadiNas());
				
				for(VD368RadiNa radiNa : list) {
					radiNa.setProjekat(null);
					cr.removeRadiNa(radiNa);
				}
				
				CRadnik crad = new CRadnik();
				if(crad.checkRadniks() != null)
					crad.removeRadniks(crad.checkRadniks());
			}
			
			
			dep.removeVD368Projekat(proj);
			em.merge(dep);
			
			em.flush();
			et.commit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			if(et != null) {
				et.rollback();
			}
		}
		finally{
			if(em != null)
				em.close();
		}
	}
	
	
	public VD368Projekat findProjekat(VD368Projekat proj) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		VD368Projekat res = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			res = em.find(VD368Projekat.class, proj.getSifraP());
			
			em.flush();
			et.commit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			if(et != null) {
				et.rollback();
			}
		}
		finally{
			if(em != null)
				em.close();
		}
		return res;
	}
	
	public List<VD368Projekat> listProjekat(){
		EntityManager em = PersistenceUtil.getEntityManager();
		String q = "select p from VD368Projekat p";
		Query query = em.createQuery(q);
		List<VD368Projekat> list = query.getResultList();
		em.close();
		return list;
	}
}
