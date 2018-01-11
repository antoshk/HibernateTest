package com.gmail.shelkovich.anton.servlets;

import com.gmail.shelkovich.anton.dao.UniDao;
import com.gmail.shelkovich.anton.dao.UniDaoImpl;
import com.gmail.shelkovich.anton.model.Guest;
import com.gmail.shelkovich.anton.model.Hotel;
import com.gmail.shelkovich.anton.model.Room;
import com.gmail.shelkovich.anton.model.RoomInfo;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GenerateServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(GenerateServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Hotel> hotels = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();

        Random random = new Random();

        for (int i = 1; i < 3; i++) {
            Hotel hotel = new Hotel();
            hotel.setName("Hotel" + i);
            hotel.setRating(random.nextInt(10) + 1);
            hotels.add(hotel);
            for (int j = 1; j < 6; j++) {
                Room room = new Room();
                room.setDescription("RmDscr" + j);
                room.setPrice(new BigDecimal(random.nextInt(100) + 100));
                room.setNumber(j);
                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setHasShower(true);
                roomInfo.setHasTv(true);
                roomInfo.setMaxPeople(random.nextInt(10) + 1);
                room.setRoomInfo(roomInfo);
                hotel.getRooms().add(room);
                //room.setHotel(hotel);
                rooms.add(room);
            }
        }

        for (int i = 1; i < 21; i++) {
            Guest guest = new Guest();
            guest.setFullName("Name" + i + " Patr" + i + " Surname" + i);
            guest.setEmail("email" + i);
            hotels.get(random.nextInt(2)).getRooms().get(random.nextInt(5)).getGuests().add(guest);
        }

        UniDao<Hotel> hotelDao = new UniDaoImpl<>(Hotel.class);
        hotelDao.getSession().beginTransaction();
        try {
            for (Hotel hotel : hotels) {
                hotelDao.add(hotel);
            }
            hotelDao.getSession().getTransaction().commit();

            req.setAttribute("dataList", rooms);
            req.getRequestDispatcher("/WEB-INF/main.jsp").include(req, resp);
        } catch (Throwable ex) {
            hotelDao.getSession().getTransaction().rollback();
            logger.error("\n\nerror in transaction\n\n", ex);
            req.setAttribute("errorMessage", ex);
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }


    }
}
