package ru.javawebinar.topjava.service.Jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class, profiles = "jdbc")
public class UserServiceJdbcTest extends UserServiceTest {

}
