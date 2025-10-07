package crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.VD368Manager;
import model.VD368Radnik;
import model.VD368Zaposleni;
import utils.PersistenceUtil;

public class CManager {
	public boolean insertManager(VD368Manager manager, VD368Zaposleni zap) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		boolean stat = false;
		try {
			et = em.getTransaction();
			et.begin();
			
			zap = em.find(VD368Zaposleni.class, zap.getZapMbrZ());
			
			if(em.find(VD368Radnik.class, zap.getZapMbrZ()) != null)
				return false;
				
			manager = em.merge(manager);
			
			zap.setVD368Manager(manager);
			manager.setVD368Zaposleni(zap);
			
			em.merge(zap);
			em.persist(manager);
			
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
	
	public void removeManager(VD368Manager manager) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		VD368Zaposleni zap = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			manager = em.find(VD368Manager.class, manager.getZapMbrZ());
			
			zap = manager.getVD368Zaposleni();
			
			em.remove(manager);
			
			zap.setVD368Radnik(null);
			zap = em.merge(zap);
			
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
			
			if(zap != null) {
				CZaposleni cz = new CZaposleni();
				cz.removeZaposleni(zap);
			}
		}
		
	}
	
	
	public List<VD368Manager> listManager(){
		EntityManager em = PersistenceUtil.getEntityManager();
		String q = "select m from VD368Manager m";
		Query query = em.createQuery(q);
		List<VD368Manager> list = query.getResultList();
		em.close();
		return list;
	}
}
