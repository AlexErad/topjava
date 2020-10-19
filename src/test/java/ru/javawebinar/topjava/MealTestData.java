package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

public class MealTestData {
    public static final int NOT_FOUND = 123;
    public static final int MEAL_ID_1 = 100002;
    public static final int MEAL_ID_2 = 100003;
    public static final int USER_ID = 100000;

    public static final Meal MEAL_1 = new Meal(MEAL_ID_1, DateTimeUtil.parseLocalDateTime("2020-01-31 09:00"),
            "Breakfast", 400);
    public static final Meal MEAL_2 = new Meal(MEAL_ID_2, DateTimeUtil.parseLocalDateTime("2020-01-31 13:00"),
            "Dinner", 500);

    public static Meal getNew() {
        return new Meal(null, DateTimeUtil.parseLocalDateTime("2020-01-31 10:00"), "desc", 100500);
    }

    public static Meal getUpdated() {
        return new Meal(100002, DateTimeUtil.parseLocalDateTime("2021-01-31 10:00"), "desc_updated", 100);
    }
}
