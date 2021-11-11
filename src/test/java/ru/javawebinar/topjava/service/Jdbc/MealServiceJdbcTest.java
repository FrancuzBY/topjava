package ru.javawebinar.topjava.service.Jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = "jdbc")
public class MealServiceJdbcTest extends MealServiceTest {
}
