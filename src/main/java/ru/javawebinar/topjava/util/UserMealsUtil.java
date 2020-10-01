package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        List<UserMealWithExcess> mealsFilteredByCyclesOptionalTask = filteredByCyclesOptionalTask(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsFilteredByCyclesOptionalTask.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        Map<LocalDate, Integer> cache = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate localDate = meal.getDateTime().toLocalDate();
            cache.merge(localDate, meal.getCalories(), Integer::sum);
        }
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcesses.add(
                        new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                new Boolean[]{cache.get(meal.getDateTime().toLocalDate()) > caloriesPerDay}));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> cache = meals
                .stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return
                meals
                        .stream()
                        .map(t -> new UserMealWithExcess(t.getDateTime(), t.getDescription(), t.getCalories(),
                                new Boolean[]{cache.get(t.getDateTime().toLocalDate()) > caloriesPerDay}))
                        .filter(t -> TimeUtil.isBetweenHalfOpen(t.getDateTime().toLocalTime(), startTime, endTime))
                        .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByCyclesOptionalTask(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> withExcesses = new ArrayList<>();
        Map<LocalDate, Integer> caloriesStats = new HashMap<>();
        Map<LocalDate, Boolean[]> excessesStats = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            caloriesStats.merge(mealDate, meal.getCalories(), Integer::sum);
            if (excessesStats.containsKey(mealDate)) {
                excessesStats.get(mealDate)[0] = caloriesStats.get(mealDate) > caloriesPerDay;
            }
            else {
                excessesStats.put(mealDate, new Boolean[]{caloriesStats.get(mealDate) > caloriesPerDay});
            }
            if (TimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime)) {
                withExcesses.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        excessesStats.get(mealDate)));
            }
        }
        System.out.println(caloriesStats);
        System.out.println(excessesStats);
        return withExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreamsOptionalTask(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        System.out.println(
                meals
                        .stream()
                        .collect(Collectors.toMap(userMeal -> userMeal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum))
        );
        return null;
    }
}
