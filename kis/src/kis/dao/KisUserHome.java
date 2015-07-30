package kis.dao;

// Generated 2015-7-11 15:31:50 by Hibernate Tools 4.3.1

import java.util.List;

import kis.entity.KisUser;
import kis.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class KisUser.
 * @see KisUser
 * @author Hibernate Tools
 */
public class KisUserHome {

	private static final Log log = LogFactory.getLog(KisUserHome.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


	public void persist(KisUser transientInstance) {
		log.debug("persisting KisUser instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(KisUser instance) {
		log.debug("attaching dirty KisUser instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(KisUser instance) {
		log.debug("attaching clean KisUser instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(KisUser persistentInstance) {
		log.debug("deleting KisUser instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public KisUser merge(KisUser detachedInstance) {
		log.debug("merging KisUser instance");
		try {
			KisUser result = (KisUser) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public KisUser findById(int id) {
		log.debug("getting KisUser instance with id: " + id);
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			KisUser instance = (KisUser) s.get("kis.entity.KisUser", id);
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



	public KisUser findUserByName(String userName) {
		log.debug("finding KisUser instance by example");
		try {
			Session s = sessionFactory.getCurrentSession();
			s.beginTransaction();
			KisUser user = (KisUser) s.createQuery("from kis.entity.KisUser u where u.name = :name").setParameter("name", userName)
					.uniqueResult();
			s.getTransaction().commit();
			return user;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

}
