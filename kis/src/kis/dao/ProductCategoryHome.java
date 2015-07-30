package kis.dao;

// Generated 2015-7-11 15:31:50 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;

import kis.entity.City;
import kis.entity.ProductCategory;
import kis.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class ProductCategory.
 * @see ProductCategory
 * @author Hibernate Tools
 */
public class ProductCategoryHome {

	private static final Log log = LogFactory.getLog(ProductCategoryHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public List<ProductCategory> findAll() {

		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			List<ProductCategory> cities = s.createQuery("from kis.entity.ProductCategory c order by c.id").list();
			s.getTransaction().commit();
			return cities;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	public void saveOrUpdate(ProductCategory category) {

		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			s.saveOrUpdate(category);
			s.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public void delete(int id) {
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			ProductCategory category = (ProductCategory) s.load(ProductCategory.class, id);
			s.delete(category);
			s.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public ProductCategory findById(int id) {
		log.debug("getting Customer instance with id: " + id);
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			ProductCategory instance = (ProductCategory) s.get(ProductCategory.class, id);
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
