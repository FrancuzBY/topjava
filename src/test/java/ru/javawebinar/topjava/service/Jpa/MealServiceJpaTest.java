package ru.javawebinar.topjava.service.Jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = "jpa")
public class MealServiceJpaTest extends MealServiceTest {
}
