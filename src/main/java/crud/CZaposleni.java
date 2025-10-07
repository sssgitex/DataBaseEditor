package crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.VD368Zaposleni;
import utils.PersistenceUtil;

public class CZaposleni {
	public boolean insertZaposleni(VD368Zaposleni zaposleni) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		boolean stat  = false;
		try {
			et = em.getTransaction();
			et.begin();
			
			em.persist(zaposleni);
			
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
	
	public void removeZaposleni(VD368Zaposleni zaposleni) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			zaposleni = em.merge(zaposleni);
			em.remove(zaposleni);
			
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
	
	public boolean updateZaposleni(VD368Zaposleni zaposleni, String ime, String prz) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		boolean stat = false;
		try {
			et = em.getTransaction();
			et.begin();
			
			zaposleni = em.find(VD368Zaposleni.class, zaposleni.getZapMbrZ());
			zaposleni.setZapIme(ime);
			zaposleni.setZapPrezime(prz);
			em.merge(zaposleni);
			
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
	
	public List<VD368Zaposleni> listZaposleni(){
		EntityManager em = PersistenceUtil.getEntityManager();
		String q = "select z from VD368Zaposleni z";
		Query query = em.createQuery(q);
		List<VD368Zaposleni> zaposleniList = query.getResultList();
		em.close();
		return zaposleniList;
	}
}
