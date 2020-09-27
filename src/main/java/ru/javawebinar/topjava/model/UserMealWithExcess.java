package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Arrays;

public class UserMealWithExcess {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final Boolean[] excess;

    public UserMealWithExcess(LocalDateTime dateTime, String description, int calories, Boolean[] excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public Boolean[] isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "UserMealWithExcess{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess[0] +
                '}';
    }
}
