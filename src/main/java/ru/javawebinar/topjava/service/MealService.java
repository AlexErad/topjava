package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.Collection;

public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId) {
        return repository.get(id, userId);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id, int userId) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Meal meal) {
        ValidationUtil.checkNotFoundWithId(repository.save(meal), meal.getId());
    }
}