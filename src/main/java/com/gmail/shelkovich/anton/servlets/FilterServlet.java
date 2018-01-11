package com.gmail.shelkovich.anton.servlets;

import com.gmail.shelkovich.anton.dao.UniDao;
import com.gmail.shelkovich.anton.dao.UniDaoImpl;
import com.gmail.shelkovich.anton.model.Room;
import org.apache.log4j.Logger;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;

public class FilterServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(FilterServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder queryStr = new StringBuilder("SELECT r FROM Hotel h JOIN h.rooms r JOIN r.roomInfo i WHERE 1=1 ");
        HashMap<String, Object> queryParams = new HashMap<>();

        Integer rating = null;
        String ratingStr = req.getParameter("rating");
        if (ratingStr != null && !"".equals(ratingStr)) {
            try {
                rating = Integer.parseInt(ratingStr);
                queryStr.append(" AND h.rating >= :rating ");
                queryParams.put("rating", rating);
            } catch (Exception e) {
                logger.error("Error while parsing ratingStr (" + ratingStr + ")", e);
            }
        }

        Integer peopleCount = null;
        String peopleCountStr = req.getParameter("peopleCount");
        if (peopleCountStr != null && !"".equals(peopleCountStr)) {
            try {
                peopleCount = Integer.parseInt(peopleCountStr);
                queryStr.append(" AND i.maxPeople = :peopleCount ");
                queryParams.put("peopleCount", peopleCount);
            } catch (Exception e) {
                logger.error("Error while parsing peopleCountStr (" + peopleCountStr + ")", e);
            }
        }

        BigDecimal priceTo = null;
        String priceToStr = req.getParameter("priceTo");
        if (priceToStr != null && !"".equals(priceToStr)) {
            DecimalFormat df = new DecimalFormat();
            df.setParseBigDecimal(true);
            try {
                priceTo = (BigDecimal) df.parse(priceToStr);
                queryStr.append(" AND r.price <= :priceTo ");
                queryParams.put("priceTo", priceTo);
            } catch (Exception e) {
                logger.error("Error while parsing priceToStr (" + priceToStr + ")", e);
            }
        }

        BigDecimal priceFrom = null;
        String priceFromStr = req.getParameter("priceFrom");
        if (priceFromStr != null && !"".equals(priceFromStr)) {
            DecimalFormat df = new DecimalFormat();
            df.setParseBigDecimal(true);
            try {
                priceFrom = (BigDecimal) df.parse(priceFromStr);
                queryStr.append(" AND r.price >= :priceFrom ");
                queryParams.put("priceFrom", priceFrom);
            } catch (Exception e) {
                logger.error("Error while parsing priceToStr (" + priceFromStr + ")", e);
            }
        }

        logger.info("rating = " + rating + " peopleCount = " + peopleCount + " priceFrom = " + priceFrom + " priceTo = " + priceTo);

        UniDao<Room> roomDao = new UniDaoImpl<>(Room.class);
        roomDao.getSession().beginTransaction();
        Query query = roomDao.getSession().createQuery(queryStr.toString());

        for (String param : query.getParameterMetadata().getNamedParameterNames()) {
            query.setParameter(param, queryParams.get(param));
        }

        try {
            req.setAttribute("dataList", query.list());
            req.getRequestDispatcher("/WEB-INF/main.jsp").include(req, resp);
            roomDao.getSession().getTransaction().commit();
        } catch (Throwable ex) {
            roomDao.getSession().getTransaction().rollback();
            logger.error("\n\nerror in transaction\n\n", ex);
            req.setAttribute("errorMessage", ex);
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }

    }
}
