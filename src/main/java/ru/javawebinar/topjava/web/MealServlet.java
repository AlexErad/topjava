package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoHardcoded;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.DateParser;
import ru.javawebinar.topjava.util.MealsUtil;

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
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = DateParser.parseStringToDate(request.getParameter("dateTime"), "dd.MM.yyyy HH:mm:ss");
        String desc = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal();
        meal.setCalories(calories);
        meal.setDateTime(dateTime);
        meal.setDescription(desc);
        if (id == null || id.isEmpty()) {
            mealDao.addMeal(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealDao.updateMeal(meal);
        }
        List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(mealDao.getAllMeals(), 2000);
        request.setAttribute("meals", mealsWithExcess);
        RequestDispatcher view = request.getRequestDispatcher("meals.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("GET request");
        String action = request.getParameter("action");
        if ("delete".equals(action))
            performDelete(request, response);
        else if ("update".equals(action))
            initiateUpdate(request, response);
        else if ("insert".equals(action))
            initiateInsert(request, response);
        else listMeals(request, response);
    }

    private void listMeals(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(mealDao.getAllMeals(), 2000);
        request.setAttribute("meals", mealsWithExcess);
        RequestDispatcher view = request.getRequestDispatcher("/meals.jsp");
        view.forward(request, response);
    }

    private void initiateInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher view = request.getRequestDispatcher("/meal.jsp");
        view.forward(request, response);
    }

    private void initiateUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal mealToUpdate = mealDao.getMeal(id);
        request.setAttribute("meal", mealToUpdate);
        request.getRequestDispatcher("/meal.jsp").forward(request, response);
    }

    private void performDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        mealDao.deleteMeal(id);
        List<MealTo> mealsWithExcess = MealsUtil.filteredByStreams(mealDao.getAllMeals(), 2000);
        response.sendRedirect("meals");
    }
}
