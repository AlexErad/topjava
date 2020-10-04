package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    List<Meal> getAllMeals();
    Meal getMeal(int id);
    void deleteMeal(int id);
    void addMeal(Meal meal);
    void updateMeal(Meal meal);
}
