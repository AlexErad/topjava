package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class DataJpaMealRepository implements MealRepository {

    private static final Sort SORT_DATE_TIME = Sort.by(Sort.Direction.DESC, "dateTime");
    private final CrudMealRepository crudRepository;

    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository crudUserRepository) {
        this.crudRepository = crudRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserId(userId, SORT_DATE_TIME);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.findAllByUserIdAndDateTimeGreaterThanEqualAndDateTimeLessThan
                (userId, startDateTime, endDateTime, SORT_DATE_TIME);
    }
}
