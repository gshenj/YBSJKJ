package kis.dao;

// Generated 2015-7-11 15:31:50 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;

import kis.entity.Company;
import kis.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class Company.
 * @see Company
 * @author Hibernate Tools
 */
public class CompanyHome {

	private static final Log log = LogFactory.getLog(CompanyHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public List<Company> findAll() {

		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			List<Company> companies = (List<Company>) s.createQuery("from kis.entity.Company company order by company.id").list();
			s.getTransaction().commit();
			if (companies == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return companies;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public Company findById(int id) {
		log.debug("getting Company instance with id: " + id);
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			Company instance = (Company) s.get("kis.entity.Company", id);
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
