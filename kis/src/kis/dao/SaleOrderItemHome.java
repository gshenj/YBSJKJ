package kis.dao;

// Generated 2015-7-11 15:31:50 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;

import kis.entity.SaleOrderItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class SaleOrderItem.
 * @see SaleOrderItem
 * @author Hibernate Tools
 */
public class SaleOrderItemHome {

	private static final Log log = LogFactory.getLog(SaleOrderItemHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

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


}
