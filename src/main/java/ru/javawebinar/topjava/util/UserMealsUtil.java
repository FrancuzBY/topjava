package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {

    private static Map<LocalDate, Integer> mapCaloriesPerDay = new HashMap<>();

    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 30, 10, 0),
                        "Завтрак",
                        500),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 30, 13, 0),
                        "Обед",
                        1000),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 30, 20, 0),
                        "Ужин",
                        500),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 31, 0, 0),
                        "Еда на граничное значение",
                        100),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 31, 10, 0),
                        "Завтрак",
                        1000),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 31, 13, 0),
                        "Обед",
                        500),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 31, 20, 0),
                        "Ужин",
                        410)
        );

        addMapTotalCaloriesPerDay(meals);
        List<UserMealWithExcess> mealsTo = filteredByCycles(
                meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        mealsTo.forEach(System.out::println);

        System.out.println(
                filteredByStreams(
                        meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> mealsTo = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDateTime mealDateTime = meal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(mealDateTime.toLocalTime(), startTime, endTime)) {
                mealsTo.add(converterEntityToDTO(meal, caloriesPerDay));
            }
        }
        return mealsTo;
    }

    public static List<UserMealWithExcess> filteredByStreams(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .filter(
                        userMeal -> TimeUtil.isBetweenHalfOpen(
                                userMeal.getDateTime().toLocalTime(),
                                startTime,
                                endTime)
                )
                .map(userMeal -> converterEntityToDTO(userMeal, caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess converterEntityToDTO(UserMeal userMeal, int caloriesPerDay) {
        return new UserMealWithExcess(
                userMeal.getDateTime(),
                userMeal.getDescription(),
                userMeal.getCalories(),
                IsExcessThisDay(userMeal, caloriesPerDay));
    }

    private static void addMapTotalCaloriesPerDay(List<UserMeal> meals) {
        mapCaloriesPerDay = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        Collectors.summingInt(UserMeal::getCalories)));
    }

    private static boolean IsExcessThisDay(UserMeal userMeal, int caloriesPerDay) {
        return mapCaloriesPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
    }

}
