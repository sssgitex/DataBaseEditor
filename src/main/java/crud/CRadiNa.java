package crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.VD368Projekat;
import model.VD368RadiNa;
import model.VD368RadiNaPK;
import model.VD368Radnik;
import utils.PersistenceUtil;

public class CRadiNa {
	public boolean insertRadiNa(VD368RadiNa radiNa, VD368Radnik radnik, VD368Projekat proj) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		boolean stat = false;
		try {
			et = em.getTransaction();
			et.begin();
			

			radnik = em.find(VD368Radnik.class, radnik.getZapMbrZ());

			proj = em.find(VD368Projekat.class, proj.getSifraP());
			
			radiNa.setRadnik(radnik);
			radiNa.setProjekat(proj);
			radiNa.setId(new VD368RadiNaPK(radnik.getZapMbrZ(), proj.getSifraP()));

			em.persist(radiNa);
			
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
	
	public void removeRadiNa(VD368RadiNa radiNa) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			
			radiNa = em.merge(radiNa);
			
			radiNa.getRadnik().removeRadiNa(radiNa); // delete radiNa from radnik
			
			em.remove(radiNa);
			
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
	
	
	public List<VD368RadiNa> listRadiNa(){
		EntityManager em = PersistenceUtil.getEntityManager();
		String q = "select r from VD368RadiNa r";
		Query query = em.createQuery(q);
		List<VD368RadiNa> list = query.getResultList();
		em.close();
		return list;
	}
}
