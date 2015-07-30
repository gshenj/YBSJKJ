package kis.dao;

// Generated 2015-7-11 15:31:50 by Hibernate Tools 4.3.1

import java.util.List;

import kis.entity.ProductModal;
import kis.entity.ProductUnits;
import kis.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class ProductModal.
 * @see ProductModal
 * @author Hibernate Tools
 */
public class ProductModalHome {

	private static final Log log = LogFactory.getLog(ProductModalHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public static final String HQL_FIND_PRODUCTS_BY_CUSTOMER = "from kis.entity.ProductModal as modal " +
			"inner join fetch modal.productCategory " +
			"inner join fetch modal.productUnits " +
			"where modal.customer.id = :customerId " +
			"order by modal.id" ;


	public static final String HQL_FIND_PRODUCTS_BY_CUSTOMER_AND_CATEGORY = "from kis.entity.ProductModal as modal " +
			"inner join fetch modal.productUnits " +
			"inner join fetch modal.customer customer " +
			"inner join fetch modal.productCategory category " +
			"where customer.id = :customerId " +
			"and category.id = :categoryId " +
			"order by modal.id" ;


	public List<ProductModal> findProductModalsByCustomer(int customerId) {
		log.debug("finding KisCustomer instance by example");
		try {
			Session s = sessionFactory
					.getCurrentSession();
			s.beginTransaction();
			List<ProductModal> results = (List<ProductModal>) s.createQuery(HQL_FIND_PRODUCTS_BY_CUSTOMER)
					.setInteger("customerId", customerId)
					.list();

			s.getTransaction().commit();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<ProductModal> findProductModalsByCustomerAndCategory(int customerId, int categoryId) {
		log.debug("finding KisCustomer instance by example");
		try {
			Session s = sessionFactory
					.getCurrentSession();
			s.beginTransaction();
			List<ProductModal> results = (List<ProductModal>) s.createQuery(HQL_FIND_PRODUCTS_BY_CUSTOMER_AND_CATEGORY)
					.setInteger("customerId", customerId)
					.setInteger("categoryId", categoryId)
					.list();

			s.getTransaction().commit();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public static final String QUERY_MODEL = "from kis.entity.ProductModal modal left join fetch modal.productCategory ctegory " +
			"left join fetch modal.productUnits units "+
			"left join fetch modal.customer customer " +
			"where modal.id = :id";
	public ProductModal getWithProperties(int id) {
		log.debug("getting ProductModal instance with id: " + id);
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			ProductModal instance = (ProductModal) s.createQuery(QUERY_MODEL).setInteger("id", id).uniqueResult();
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

	public ProductModal findById(int id) {
		log.debug("getting ProductModal instance with id: " + id);
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			ProductModal instance = (ProductModal) s.get("kis.entity.ProductModal", id);
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

	public List<ProductModal> findByExample(ProductModal instance) {
		log.debug("finding ProductModal instance by example");
		try {
			List<ProductModal> results = (List<ProductModal>) sessionFactory
					.getCurrentSession().createCriteria("kis.entity.ProductModal")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}


	public void saveOrUpdate(ProductModal modal) {
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			s.saveOrUpdate(modal);
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
			ProductModal category = (ProductModal) s.load(ProductModal.class, id);
			s.delete(category);
			s.getTransaction().commit();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
