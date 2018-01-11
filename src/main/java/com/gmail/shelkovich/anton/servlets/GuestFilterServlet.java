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
import java.util.HashMap;

public class GuestFilterServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GuestFilterServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder queryStr = new StringBuilder("SELECT r FROM Hotel h JOIN h.rooms r JOIN r.guests g WHERE h.rating BETWEEN 5 AND 8 ");
        HashMap<String, Object> queryParams = new HashMap<>();

        String surname = null;
        String surnameStr = req.getParameter("surname");
        if (surnameStr != null && !"".equals(surnameStr)) {
            surname = surnameStr;
            queryStr.append(" AND g.fullName LIKE :surname");
            queryParams.put("surname", "%" + surname + "%");
        }

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
