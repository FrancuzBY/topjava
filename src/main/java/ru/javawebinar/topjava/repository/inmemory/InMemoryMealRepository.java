package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
        save(2, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save(2, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(2, new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", meal);
        Map<Integer, Meal> mealMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            return meal;
        }
        return mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", id);
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (CollectionUtils.isEmpty(mealMap)) {
            return false;
        } else {
            Meal meal = mealMap.remove(id);
            return meal == null;
        }
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {}", id);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap == null ? null : mealMap.get(id);
    }

    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        log.info("getBetweenHalfOpen");
        return getAllFiltered(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        return CollectionUtils.isEmpty(mealMap)? Collections.emptyList():mealMap.values()
                .stream()
                .filter(filter)
                .sorted((Comparator.comparing(Meal::getDate)).reversed())
                .collect(Collectors.toList());
    }
}

