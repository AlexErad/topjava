package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoHardcoded;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.DateFormatter;
import ru.javawebinar.topjava.util.DateParser;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final String LIST_MEALS_JSP_PATH = "/meals.jsp";
    private static final String ADD_EDIT_MEAL_JSP_PATH = "/meal.jsp";

    private MealDao mealDao;

    public MealServlet() {
        mealDao = new MealDaoHardcoded();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("POST request");
        String id = request.getParameter("id");
        LocalDateTime dateTime = DateParser.parseStringToDate(request.getParameter("dateTime"), "dd.MM.yyyy HH:mm:ss");
        String desc = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id==null || id.isEmpty()) {
            Meal meal = new Meal();
            meal.setCalories(calories);
            meal.setDateTime(dateTime);
            meal.setDescription(desc);
            mealDao.addMeal(meal);
        }
        else {
            throw new UnsupportedOperationException("Update is not supported yet");
        }
        List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(mealDao.getAllMeals(), 2000);
        request.setAttribute("meals", mealsWithExcess);
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS_JSP_PATH);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET request");
        String forward = LIST_MEALS_JSP_PATH;
        String action = request.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDao.deleteMeal(id);
            forward = LIST_MEALS_JSP_PATH;
        }
        else if ("update".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal mealToUpdate = mealDao.getMeal(id);
            request.setAttribute("meal", mealToUpdate);
            forward = ADD_EDIT_MEAL_JSP_PATH;
        }
        else if ("insert".equalsIgnoreCase(action)) {
            forward = ADD_EDIT_MEAL_JSP_PATH;
        }
        List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(mealDao.getAllMeals(), 2000);
        request.setAttribute("meals", mealsWithExcess);
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
}
