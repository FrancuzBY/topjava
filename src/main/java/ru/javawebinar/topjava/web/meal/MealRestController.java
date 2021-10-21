package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) {
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        log.info("save {} for user {}", meal, userId);
        return service.save(userId, meal);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {} for user {}", id, userId);
        service.delete(userId, id);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get {} for user {}", id, userId);
        return service.get(userId, id);
    }

    public Collection<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        Collection<Meal> meals = service.getAll(userId);
        return MealsUtil.getTos(meals, SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        log.info("update meal {} for user {}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealTo> getBetweenHalfOpen(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                           @Nullable LocalDate endDate, @Nullable LocalTime endTime) {

        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = service.getBetweenHalfOpen(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

}