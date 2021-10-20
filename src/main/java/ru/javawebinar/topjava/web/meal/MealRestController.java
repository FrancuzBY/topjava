package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) {
        return service.save(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        service.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id) {
        return checkNotFoundWithId(service.get(SecurityUtil.authUserId(), id), id);
    }

    public Collection<Meal> getAll() {
        return service.getAll(SecurityUtil.authUserId());
    }

}