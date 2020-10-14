package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

    public List<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public void save(Meal meal) {
        if (meal.isNew())
            service.create(meal);
        else
            service.update(meal);
    }

    public Meal get(int id, int authUserId) {
        return service.get(id, authUserId);
    }

    public List<MealTo> getAll(LocalDate dateFrom, LocalDate dateTo, LocalTime timeFrom, LocalTime timeTo) {

        return getAll()
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalDate(), dateFrom, dateTo))
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), timeFrom, timeTo))
                .collect(Collectors.toList());
    }
}