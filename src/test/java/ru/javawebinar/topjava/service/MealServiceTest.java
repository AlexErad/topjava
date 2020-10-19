package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }


    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal actualMeal = service.get(MEAL_ID_1, USER_ID);
        assertEquals(MEAL_1, actualMeal);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID_1, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        throw new RuntimeException();
    }

    @Test
    public void getAll() {
        List<Meal> actualMeals = service.getAll(USER_ID);
        Assertions.assertThat(actualMeals).containsExactlyInAnyOrder(MEAL_1, MEAL_2);
    }

    @Test
    public void update() {
        Meal expectedMeal = getUpdated();
        service.update(expectedMeal, USER_ID);
        Assertions.assertThat(service.get(expectedMeal.getId(), USER_ID)).usingRecursiveComparison().isEqualTo(expectedMeal);
    }

    @Test
    public void create() {
        Meal expectedMeal = getNew();
        Meal actualMeal = service.create(expectedMeal, USER_ID);
        expectedMeal.setId(actualMeal.getId());
        Assertions.assertThat(actualMeal).usingRecursiveComparison().isEqualTo(expectedMeal);
        Assertions.assertThat(actualMeal).usingRecursiveComparison().isEqualTo(service.get(expectedMeal.getId(), USER_ID));
    }

    @Test
    public void getNotFoundMealMismatch() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotFoundUserMismatch() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_1, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }
}