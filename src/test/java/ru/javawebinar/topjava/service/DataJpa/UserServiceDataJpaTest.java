package ru.javawebinar.topjava.service.DataJpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = "datajpa")
public class UserServiceDataJpaTest extends UserServiceTest {
}