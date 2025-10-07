package crud;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.VD368Departman;
import model.VD368Manager;
import model.VD368Projekat;
import model.VD368Zaposleni;
import utils.PersistenceUtil;

public class CDepartman {
	public void insertDepartman(VD368Departman departman, VD368Manager manager) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			manager = em.merge(manager);
			departman = em.merge(departman);
			
			manager.setVD368Departman(departman);
			departman.setVD368Manager(manager);
			
			em.merge(manager);
			em.persist(departman);

			
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
	
	public boolean removeConnsOfDepartman(VD368Departman departman) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		VD368Manager man = null;
		VD368Zaposleni zap = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			CProjekat cp = new CProjekat();
			
			departman = em.find(VD368Departman.class, departman.getManagerZapMbrZ());
			
			ArrayList<VD368Projekat> list = new ArrayList<VD368Projekat> (departman.getVD368Projekats());
			if(list.size() > 0) {
				for(VD368Projekat proj : list) {
					em.detach(proj);
					cp.removeProjekat(proj);

					proj = em.merge(proj);
					departman.removeVD368Projekat(proj);
				}
			}
			
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
		
		removeDepartman(departman);
		return true;
	}
	
	public void removeDepartman(VD368Departman dep) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			dep = em.find(VD368Departman.class, dep.getManagerZapMbrZ());

			em.remove(dep);
			
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
	
	public VD368Departman findDepartman(VD368Departman dep) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		VD368Departman res = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			res = em.find(VD368Departman.class, dep.getManagerZapMbrZ());
			
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
	
	public List<VD368Departman> listDepartman(){
		EntityManager em = PersistenceUtil.getEntityManager();
		String q = "select d from VD368Departman d";
		Query query = em.createQuery(q);
		List<VD368Departman> list = query.getResultList();
		em.close();
		return list;
	}
}
