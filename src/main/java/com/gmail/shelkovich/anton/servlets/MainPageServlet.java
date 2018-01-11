package com.gmail.shelkovich.anton.servlets;

import com.gmail.shelkovich.anton.dao.UniDao;
import com.gmail.shelkovich.anton.dao.UniDaoImpl;
import com.gmail.shelkovich.anton.model.Room;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainPageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MainPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UniDao<Room> roomDao = new UniDaoImpl<>(Room.class);
        roomDao.getSession().beginTransaction();
        try {
            req.setAttribute("dataList", roomDao.getAll());
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
