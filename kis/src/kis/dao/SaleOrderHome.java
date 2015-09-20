package kis.dao;

// Generated 2015-7-11 15:31:50 by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import kis.entity.SaleOrder;
import kis.entity.SaleOrderItem;
import kis.service.SaleOrderQueryCondition;
import kis.service.StatisticsItemResult;
import kis.service.StatisticsOrderResult;
import kis.util.HibernateUtil;
import kis.util.Pagination;
import kis.util.Settings;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class SaleOrder.
 *
 * @author Hibernate Tools
 * @see SaleOrder
 */
public class SaleOrderHome {

    private static final Log log = LogFactory.getLog(SaleOrderHome.class);

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static final String GET_ORDER_NUMBER0 = "update sale_order_number set order_number=order_number+1";
    public static final String GET_ORDER_NUMBER1 = "select order_number from sale_order_number";


    public static final String STATISTICS_ORDERS = "SELECT SALE_ORDER.CUSTOMER_NAME customer,\n" +
            "       ITEM.PRODUCT_CATEGORY_TEXT category,\n" +
            "       ITEM.PRODUCT_MODAL_TEXT modal,\n" +
            "       SUM(ITEM.QUANTITY) quantity,\n" +
            "       SUM(ITEM.QUANTITY * ITEM.UNIT_PRICE) money\n" +
            "FROM SALE_ORDER SALE_ORDER,\n" +
            "     SALE_ORDER_ITEM ITEM,\n" +
            "     CUSTOMER CUSTOMER\n" +
            "WHERE SALE_ORDER.ID = ITEM.SALE_ORDER\n" +
            "  AND CUSTOMER.ID = SALE_ORDER.CUSTOMER\n" +
            "  AND SALE_ORDER.FLAG = :flag\n" +
            "  AND CUSTOMER.COMPANY = :companyId\n" +
            "  AND SALE_ORDER.DATE_TEXT BETWEEN :beginDate AND :endDate\n" +
            "  AND CUSTOMER.ID = :customerId\n" +
            "GROUP BY SALE_ORDER.CUSTOMER_NAME,\n" +
            "          ITEM.PRODUCT_CATEGORY_TEXT,\n" +
            "          ITEM.PRODUCT_MODAL_TEXT\n" +
            "ORDER BY SALE_ORDER.CUSTOMER_NAME,\n" +
            "          ITEM.PRODUCT_MODAL_TEXT";

    public static final String STATISTICS_ORDERS_1 = "SELECT SALE_ORDER.CUSTOMER_NAME customer,\n" +
            "       ITEM.PRODUCT_CATEGORY_TEXT category,\n" +
            "       ITEM.PRODUCT_MODAL_TEXT modal,\n" +
            "       SUM(ITEM.QUANTITY) quantity,\n" +
            "       SUM(ITEM.QUANTITY * ITEM.UNIT_PRICE) money\n" +
            "FROM SALE_ORDER SALE_ORDER,\n" +
            "     SALE_ORDER_ITEM ITEM,\n" +
            "     CUSTOMER CUSTOMER\n" +
            "WHERE SALE_ORDER.ID = ITEM.SALE_ORDER\n" +
            "  AND CUSTOMER.ID = SALE_ORDER.CUSTOMER\n" +
            "  AND SALE_ORDER.FLAG = :flag\n" +
            "  AND CUSTOMER.COMPANY = :companyId\n" +
            "  AND SALE_ORDER.DATE_TEXT BETWEEN :beginDate AND :endDate\n" +
            "GROUP BY SALE_ORDER.CUSTOMER_NAME,\n" +
            "          ITEM.PRODUCT_CATEGORY_TEXT,\n" +
            "          ITEM.PRODUCT_MODAL_TEXT\n" +
            "ORDER BY SALE_ORDER.CUSTOMER_NAME,\n" +
            "          ITEM.PRODUCT_MODAL_TEXT";


    public List<StatisticsOrderResult> statisticsOrders(int companyId, int customerId, String beginDate, String endDate) {
        // select sum(quantity) from saleOrder
        // 客户1  品名1  ？千克  共？元
        //        品名2  ?千克  ？
        //  客户2  品名1  ？千克  共？元
        //                ?千克  ？
        //                共?千克   ？元
      /*  int companyId = 0;
        int customerId = 0;//condition.getCustomerId();
        String beginDate = "";//condition.getBeginDate();
        String endDate = ""; //condition.getEndDate();
*/

        System.out.println(beginDate);
        System.out.println(endDate);
        System.out.println(companyId);
        boolean isAllCustomer = (customerId == Settings.SALE_ORDER_ALL);
        Session s = sessionFactory.getCurrentSession();
        s.beginTransaction();
        SQLQuery query = s.createSQLQuery(isAllCustomer ? STATISTICS_ORDERS_1 : STATISTICS_ORDERS);
        query.setParameter("flag", Settings.SALE_ORDER_VALID)
                .setParameter("companyId", companyId)
                .setParameter("beginDate", beginDate)
                .setParameter("endDate", endDate);
        if (!isAllCustomer) {
            query.setParameter("customerId", customerId);
        }
        List<Object[]> results = query.list();
        System.out.println(results.size());
        s.getTransaction().commit();

        //[{"cust":.., modals:[{"modalName":na, "qu":1, "s":2}]}]

        List<StatisticsOrderResult> statisticsResults = new ArrayList();
        String lastCustomer = null;
        List<StatisticsItemResult> lastItemResults = null;

        for (Object[] objects : results) {
            String customer = (String) objects[0];
            String category = (String) objects[1];
            String modal = (String) objects[2];
            BigDecimal quantity = (BigDecimal) objects[3];
            BigDecimal sum = (BigDecimal) objects[4];

            StatisticsOrderResult r = null;

            StatisticsItemResult itemResult = new StatisticsItemResult();
            itemResult.setCategoryName(category);
            itemResult.setModalName(modal);
            itemResult.setQuantity(quantity);
            itemResult.setSum(sum);

            if (!customer.equals(lastCustomer)) {
                lastCustomer = customer;
                lastItemResults = new ArrayList<StatisticsItemResult>();
                lastItemResults.add(itemResult);

                r = new StatisticsOrderResult();
                r.setCustomerName(customer);
                r.setItemResults(lastItemResults);

                statisticsResults.add(r);

            } else {

                lastItemResults.add(itemResult);
            }


           // System.out.println(customer + "--" + modal + "---" + quantity + "--" + sum);
        }

        return statisticsResults;
    }


    public Pagination findAllSaleOrderPagination(SaleOrderQueryCondition condition) {

        int customerId = condition.getCustomerId();
        int flag = condition.getFlag();
        String beginDate = condition.getBeginDate();
        String endDate = condition.getEndDate();
        Pagination<SaleOrder> pagination = condition.getPagination();


        Session s = sessionFactory.getCurrentSession();
        s.beginTransaction();

        Criteria criteria = s.createCriteria(SaleOrder.class, "saleOrder")
                .createAlias("customer", "customer", JoinType.LEFT_OUTER_JOIN)
                .createAlias("kisUser", "kisUser", JoinType.LEFT_OUTER_JOIN);


        if (StringUtils.isNotBlank(beginDate)) {
            criteria = criteria.add(Restrictions.ge("saleOrder.dateText", beginDate));
        }
        if (StringUtils.isNotBlank(endDate)) {
            criteria = criteria.add(Restrictions.le("saleOrder.dateText", endDate));
        }

        if (customerId != Settings.SALE_ORDER_ALL) {
            criteria = criteria.add(Restrictions.eq("customer.id", customerId));
        }

        if (flag != Settings.SALE_ORDER_ALL) {
            criteria = criteria.add(Restrictions.eq("saleOrder.flag", flag));
        }

        criteria = criteria.add(Restrictions.eq("customer.company.id", condition.getCompanyId()));


        long rowCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        pagination.setTotalCount(rowCount);
        criteria.setProjection(null);

        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

        criteria = criteria.addOrder(Order.desc("saleOrder.id"));

        criteria = criteria.setFirstResult(pagination.getBeginIndex());
        //if (pagination.getPageSize() > 0) { // -1表示查询所有记录
        criteria = criteria.setMaxResults(pagination.getPageSize());
        //}

        List<SaleOrder> results = criteria.list();
        System.out.println(results.size() + "-----");
        pagination.setData(results);
        s.getTransaction().commit();

        return pagination;
    }


    public int getSeqSaleOrderValue(boolean nextValue) {
        try {
            Integer seqCurrentValue;
            Session s = sessionFactory.getCurrentSession();
            s.beginTransaction();
            if (nextValue) {
                s.createSQLQuery(GET_ORDER_NUMBER0).executeUpdate();
            }
            seqCurrentValue = (Integer) s.createSQLQuery(GET_ORDER_NUMBER1).uniqueResult();
            s.getTransaction().commit();
            return seqCurrentValue;
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }

    }

    public void saveOrUpdate(SaleOrder transientInstance) {
        log.debug("persisting SaleOrder instance");
        try {
            Session s = sessionFactory.getCurrentSession();
            s.beginTransaction();
            for (SaleOrderItem item : transientInstance.getSaleOrderItems()) {
                s.saveOrUpdate(item);
            }
            s.saveOrUpdate(transientInstance);
            s.getTransaction().commit();
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }


    public void persist(SaleOrder transientInstance) {
        log.debug("persisting SaleOrder instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void attachDirty(SaleOrder instance) {
        log.debug("attaching dirty SaleOrder instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SaleOrder instance) {
        log.debug("attaching clean SaleOrder instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void invalid(int id) {
        log.debug("deleting SaleOrder instance");
        try {
            Session s = sessionFactory.getCurrentSession();//.delete(persistentInstance);
            s.beginTransaction();
            SaleOrder saleOrder = (SaleOrder) s.get(SaleOrder.class, id);
            if (saleOrder != null && saleOrder.getFlag() != Settings.SALE_ORDER_INVALID) {
                saleOrder.setFlag(Settings.SALE_ORDER_INVALID);
                s.save(saleOrder);
            }
            s.getTransaction().commit();
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SaleOrder merge(SaleOrder detachedInstance) {
        log.debug("merging SaleOrder instance");
        try {
            SaleOrder result = (SaleOrder) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public static final String FIND_SALE_ORDER_BY_ID = "from kis.entity.SaleOrder saleOrder " +

            "left join fetch saleOrder.saleOrderItems items" +
            "left join fetch saleOrder.kisUser kis_ser" +
            "left join fetch saleOrder.customer customer " +
            "left join fetch customer.company company " +
            "where saleOrder.id=:id";

    public static final String FIND_SALE_ORDER_ITEMS_BY_SALE_ORDER = "from kis.entity.SaleOrderItem item " +
/*            "left join fetch item.productModal modal "+
            "left join fecth modal.productUnits units " +
            "left join fetch item"*/
            "left join fetch item.saleOrder saleOrder " +
            "left join fetch saleOrder.kisUser kisUser " +
            "left join fetch saleOrder.customer customer " +
            "left join fetch customer.company company " +
            "where saleOrder.id = :id " +
            "order by item.serialNumber";

    public SaleOrder findById1(int id) {
        log.debug("getting SaleOrder instance with id: " + id);
        try {
            Session s = sessionFactory.getCurrentSession();
            s.beginTransaction();
            List<SaleOrderItem> items = (List<SaleOrderItem>) s.createQuery(FIND_SALE_ORDER_ITEMS_BY_SALE_ORDER).setParameter("id", id).list();
            s.getTransaction().commit();
            if (items == null) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }
            if (items.size() == 0) return null;
            else {
                SaleOrder saleOrder = items.get(0).getSaleOrder();
                saleOrder.setSaleOrderItems(new LinkedHashSet<SaleOrderItem>(items));
                return saleOrder;
            }
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }



    public static final String FIND_SALE_ORDER_ITEMS_BY_ORDER_NUMBER = "from kis.entity.SaleOrderItem item " +
/*            "left join fetch item.productModal modal "+
            "left join fecth modal.productUnits units " +
            "left join fetch item"*/
            "left join fetch item.saleOrder saleOrder " +
            "left join fetch saleOrder.kisUser kisUser " +
            "left join fetch saleOrder.customer customer " +
            "left join fetch customer.company company " +
            "where saleOrder.orderNumber = :orderNumber " +
            "order by item.serialNumber";

    public SaleOrder findByOrderNumber(String orderNumber) {
        log.debug("getting SaleOrder instance with id: " + orderNumber);
        try {
            Session s = sessionFactory.getCurrentSession();
            s.beginTransaction();
            List<SaleOrderItem> items = (List<SaleOrderItem>) s.createQuery(FIND_SALE_ORDER_ITEMS_BY_ORDER_NUMBER).setParameter("orderNumber", orderNumber).list();
            s.getTransaction().commit();
            if (items == null) {
                log.debug("get successful, no instance found");
            } else {
                log.debug("get successful, instance found");
            }
            if (items.size() == 0) return null;
            else {
                SaleOrder saleOrder = items.get(0).getSaleOrder();
                saleOrder.setSaleOrderItems(new LinkedHashSet<SaleOrderItem>(items));
                return saleOrder;
            }
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public SaleOrder findById(int id) {
        log.debug("getting SaleOrder instance with id: " + id);
        try {
            Session s = sessionFactory.getCurrentSession();
            s.beginTransaction();
            SaleOrder instance = (SaleOrder) s.createQuery(FIND_SALE_ORDER_BY_ID).setParameter("id", id).uniqueResult();
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

    public List<SaleOrder> findByExample(SaleOrder instance) {
        log.debug("finding SaleOrder instance by example");
        try {
            List<SaleOrder> results = (List<SaleOrder>) sessionFactory
                    .getCurrentSession().createCriteria("kis.entity.SaleOrder")
                    .add(create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }
}
