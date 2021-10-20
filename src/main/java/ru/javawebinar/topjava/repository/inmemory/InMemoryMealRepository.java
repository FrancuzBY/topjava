package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
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
        Map<Integer, Meal> mealMap = getMealMap(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            repository.put(userId, mealMap);
            return meal;
        } else {
            // handle case: update, but not present in storage
            if (mealMap.isEmpty()) {
                return null;
            }
        }
        return mealMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", id);
        Map<Integer, Meal> mealMap = getMealMap(userId);
        return mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {}", id);
        Map<Integer, Meal> mealMap = getMealMap(userId);
        return mealMap.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        Map<Integer, Meal> mealMap = getMealMap(userId);
        return mealMap.values()
                .stream()
                .sorted((Comparator.comparing(Meal::getDate)).reversed())
                .collect(Collectors.toList());
    }

    private Map<Integer, Meal> getMealMap(int userId) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap == null) {
            mealMap = new ConcurrentHashMap<>();
            repository.put(userId, mealMap);
        }
        return mealMap;
    }
}

