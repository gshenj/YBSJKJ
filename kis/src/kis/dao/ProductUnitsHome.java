package kis.dao;

// Generated 2015-7-11 15:31:50 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;

import kis.entity.City;
import kis.entity.ProductUnits;
import kis.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class ProductUnits.
 * @see ProductUnits
 * @author Hibernate Tools
 */
public class ProductUnitsHome {

	private static final Log log = LogFactory.getLog(ProductUnitsHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}


	public List<ProductUnits> findAll() {

		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			List<ProductUnits> productUnits = s.createQuery("from kis.entity.ProductUnits u order by u.id").list();
			s.getTransaction().commit();
			return productUnits;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	public ProductUnits findById(int id) {
		log.debug("getting ProductUnits instance with id: " + id);
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			ProductUnits instance = (ProductUnits) s.get("kis.entity.ProductUnits", id);
			s.getTransaction().commit();
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
