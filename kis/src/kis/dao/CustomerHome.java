package kis.dao;

// Generated 2015-7-11 15:31:50 by Hibernate Tools 4.3.1

import java.util.List;

import kis.entity.Company;
import kis.entity.Customer;
import kis.entity.ProductCategory;
import kis.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Home object for domain model class Customer.
 * @see Customer
 * @author Hibernate Tools
 */
public class CustomerHome {

	private static final Log log = LogFactory.getLog(CustomerHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


	private static String FIND_COMPANY_CUSTOMERS = "from kis.entity.Customer as customer " +
			"left join fetch customer.city city " +
			"where customer.company.id = :id " +
			"order by city.id, customer.id";


	public List<Customer> findCompanyCustomers(Company company) {
		return findCompanyCustomers(company.getId());
	}

	public List<Customer> findCompanyCustomers(int companyId) {
		log.debug("persisting City instance");
		try {
			Session s = sessionFactory
					.getCurrentSession();
			s.beginTransaction();
			List<Customer> results = (List<Customer>) s.createQuery(FIND_COMPANY_CUSTOMERS).setInteger("id", companyId).list();

			s.getTransaction().commit();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}


	public Customer findById(int id) {
		log.debug("getting Customer instance with id: " + id);
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			Customer instance = (Customer) s.get("kis.entity.Customer", id);
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

	public void delete(int customerId) {
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			Customer customer = (Customer) s.load(Customer.class, customerId);
			s.delete(customer);
			s.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	public void saveOrUpdate(Customer customer) {
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			s.saveOrUpdate(customer);
			s.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


}
