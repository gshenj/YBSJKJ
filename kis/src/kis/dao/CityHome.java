package kis.dao;

// Generated 2015-7-11 15:31:50 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;

import kis.entity.City;
import kis.entity.Company;
import kis.entity.Customer;
import kis.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class City.
 * @see City
 * @author Hibernate Tools
 */
public class CityHome {

	private static final Log log = LogFactory.getLog(CityHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


	public List<City> findAll() {

		try {
			Session s = sessionFactory.getCurrentSession();
						s.beginTransaction();
			List<City> cities = s.createQuery("from kis.entity.City city order by city.province, city.id").list();
			s.getTransaction().commit();
			return cities;
		} catch (RuntimeException re) {
		log.error("get failed", re);
		throw re;
	}
	}



	public City findById(int id) {
		log.debug("getting Customer instance with id: " + id);
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			City instance = (City) s.get("kis.entity.City", id);
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
