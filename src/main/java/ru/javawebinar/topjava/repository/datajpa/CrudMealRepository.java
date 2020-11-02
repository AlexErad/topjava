package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    Meal findByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserId(int userId, Sort sort);

    int deleteByIdAndUserId(int id, int userId);

    //TODO: use JPA named query instead of this nightmare
    List<Meal> findAllByUserIdAndDateTimeGreaterThanEqualAndDateTimeLessThan
    (int userId, LocalDateTime startDateTime, LocalDateTime endDateTime, Sort sort);
}
