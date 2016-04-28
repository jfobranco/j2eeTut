package com.jb.filters;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jb.dao.CustomerDao;
import com.jb.dao.OrderHeaderDao;
import com.jb.entities.Customer;
import com.jb.entities.OrderHeader;

@WebFilter(urlPatterns = "/*")
public class LoadingFilter implements Filter {

	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String ATT_SESSION_ORDERS = "orderlist";
	public static final String ATT_SESSION_CUSTOMERS = "customerlist";

	@EJB
	private CustomerDao customerDao;
	@EJB
	private OrderHeaderDao orderDao;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		/* Cast des objets request et response */
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();

		Map<Long, Customer> customerList = (Map<Long, Customer>) session.getAttribute(ATT_SESSION_CUSTOMERS);
		if (customerList == null) {
			customerList = customerDao.list();
			session.setAttribute(ATT_SESSION_CUSTOMERS, customerList);
		}

		Map<Long, OrderHeader> orderList = (Map<Long, OrderHeader>) session.getAttribute(ATT_SESSION_ORDERS);
		if (orderList == null) {
			orderList = orderDao.list();
			session.setAttribute(ATT_SESSION_ORDERS, orderList);
		}

		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
