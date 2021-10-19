package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal save(int userId, Meal meal) {
        return service.save(userId, meal);
    }

    public void delete(int userId, int id) {
        service.delete(userId, id);
    }

    public Meal get(int userId, int id) {
        return checkNotFoundWithId(service.get(userId, id), id);
    }

    public Collection<Meal> getAll(int userId) {
        return service.getAll(userId);
    }

}