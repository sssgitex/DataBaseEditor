package crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.VD368Manager;
import model.VD368Radnik;
import model.VD368Zaposleni;
import utils.PersistenceUtil;

public class CRadnik {
	public boolean insertRadnik(VD368Radnik radnik, VD368Zaposleni zap) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		boolean stat = false;
		try {
			et = em.getTransaction();
			et.begin();
			
			zap = em.find(VD368Zaposleni.class, zap.getZapMbrZ());
			
			if(em.find(VD368Manager.class, zap.getZapMbrZ()) != null)
				return false;
			
			radnik = em.merge(radnik);
			
			zap.setVD368Radnik(radnik);
			radnik.setVD368Zaposleni(zap);
			
			em.merge(zap);
			em.persist(radnik);
			
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
	
	public void removeRadnik(VD368Radnik radnik) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		VD368Zaposleni zap = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			
			
			radnik = em.find(VD368Radnik.class, radnik.getZapMbrZ());
			
			zap = radnik.getVD368Zaposleni();
			
			em.remove(radnik);
		
			
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
	
	
	public List<VD368Radnik> listRadnik(){
		EntityManager em = PersistenceUtil.getEntityManager();
		String q = "select r from VD368Radnik r";
		Query query = em.createQuery(q);
		List<VD368Radnik> list = query.getResultList();
		em.close();
		return list;
	}

	public List<VD368Radnik> checkRadniks() {
		EntityManager em = PersistenceUtil.getEntityManager();
		String q = "FROM VD368Radnik r WHERE radiNas.size = '0'";
		Query query = em.createQuery(q);
		List<VD368Radnik> list = query.getResultList();
		em.close();
		
		if(list.size() > 0)
			return list;
		else
			return null;
	}
	
	public void removeRadniks(List<VD368Radnik> list) {
		if(list != null) {
			for(VD368Radnik r : list) {
				this.removeRadnik(r);
			}
		}
	}
}
