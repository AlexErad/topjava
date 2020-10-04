package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MealDaoHardcoded implements MealDao {
    private List<Meal> testMeals;
    private static int count = 0;

    public MealDaoHardcoded() {
        testMeals = new ArrayList<>();
        testMeals.add(new Meal(++count, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        testMeals.add(new Meal(++count, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        testMeals.add(new Meal(++count, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        testMeals.add(new Meal(++count, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        testMeals.add(new Meal(++count, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        testMeals.add(new Meal(++count, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        testMeals.add(new Meal(++count,LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public List<Meal> getAllMeals() {
        return testMeals;
    }

    @Override
    public Meal getMeal(int id) {
        for (Meal meal: testMeals) {
            if (meal.getId()==id)
                return meal;
        }
        return null;
    }

    @Override
    public void deleteMeal(int id) {
        for (Meal meal : testMeals) {
            if (meal.getId() == id) {
                testMeals.remove(meal);
                break;
            }
        }
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(++count);
        testMeals.add(meal);
    }

    @Override
    public void updateMeal(Meal meal) {

    }
}
