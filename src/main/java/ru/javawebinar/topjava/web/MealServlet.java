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
    public static final String MEAL_LIST_JSP = "mealList.jsp";
    public static final String MEAL_JSP = "meal.jsp";
    public static final String ID_FIELD = "id";
    public static final String SERVLET_URL = "meals";

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
        response.sendRedirect(SERVLET_URL);
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
        RequestDispatcher view = request.getRequestDispatcher(MEAL_LIST_JSP);
        view.forward(request, response);
    }

    private void initiateInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher view = request.getRequestDispatcher(MEAL_JSP);
        view.forward(request, response);
    }

    private void initiateUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(ID_FIELD));
        Meal mealToUpdate = mealDao.getMeal(id);
        request.setAttribute("meal", mealToUpdate);
        request.getRequestDispatcher(MEAL_JSP).forward(request, response);
    }

    private void performDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter(ID_FIELD));
        mealDao.deleteMeal(id);
        response.sendRedirect(SERVLET_URL);
    }
}
