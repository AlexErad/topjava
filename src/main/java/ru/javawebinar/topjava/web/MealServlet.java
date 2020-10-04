package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoHardcoded;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final String MEALS_JSP_PATH = "/meals.jsp";

    private MealDao mealDao;

    public MealServlet() {
        mealDao = new MealDaoHardcoded();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");
        String forward = MEALS_JSP_PATH;
        String action = request.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDao.deleteMeal(id);
        }
        List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(mealDao.getAllMeals(), 2000);
        request.setAttribute("meals", mealsWithExcess);
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
}
