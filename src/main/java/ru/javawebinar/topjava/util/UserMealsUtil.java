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

    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(
                        2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(
                meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(
                meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> mealsTo = new ArrayList<>();
        Map<LocalDate, Integer> mapCaloriesPerDay = getMapTotalCaloriesPerDayForLoop(meals);

        for (UserMeal userMeal : meals) {
            LocalDateTime mealDateTime = userMeal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(mealDateTime.toLocalTime(), startTime, endTime)) {
                mealsTo.add(converterEntityToDto(userMeal, caloriesPerDay, mapCaloriesPerDay.get(userMeal.getDateTime().toLocalDate())));
            }
        }
        return mealsTo;
    }

    public static List<UserMealWithExcess> filteredByStreams(
            List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapCaloriesPerDay = getMapTotalCaloriesPerDayForStream(meals);

        return meals.stream()
                .filter(
                        userMeal -> TimeUtil.isBetweenHalfOpen(
                                userMeal.getDateTime().toLocalTime(),
                                startTime,
                                endTime)
                )
                .map(userMeal -> converterEntityToDto(userMeal, caloriesPerDay, mapCaloriesPerDay.get(userMeal.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess converterEntityToDto(UserMeal userMeal, int caloriesPerDay, int caloriesTotal) {
        return new UserMealWithExcess(
                userMeal.getDateTime(),
                userMeal.getDescription(),
                userMeal.getCalories(),
                isExcessThisDay(caloriesPerDay, caloriesTotal));
    }

    private static Map<LocalDate, Integer> getMapTotalCaloriesPerDayForStream(List<UserMeal> meals) {
        return meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,
                        Collectors.summingInt(UserMeal::getCalories)));
    }

    private static Map<LocalDate, Integer> getMapTotalCaloriesPerDayForLoop(List<UserMeal> meals) {

        Map<LocalDate, Integer> mapCaloriesPerDay = new HashMap<>();

        for (UserMeal userMeal : meals) {
            LocalDate dateMeal = userMeal.getDate();
            int mealCalories = userMeal.getCalories();
            int currentTotalCalories = mapCaloriesPerDay.getOrDefault(dateMeal, 0);
            if (currentTotalCalories > 0) {
                mapCaloriesPerDay.put(dateMeal, currentTotalCalories + mealCalories);
            } else {
                mapCaloriesPerDay.put(dateMeal, mealCalories);
            }
        }
        return mapCaloriesPerDay;
    }

    private static boolean isExcessThisDay(int caloriesPerDay, int caloriesTotal) {
        return caloriesTotal > caloriesPerDay;
    }
}
