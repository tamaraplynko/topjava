package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapCaloriesPerDay = new HashMap<>();
        /*
        ListIterator<UserMeal> iterator = mealList.listIterator();
        while (iterator.hasNext()){
            UserMeal meal = iterator.next();
        */

        for (UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            Integer calories = meal.getCalories();
            if (mapCaloriesPerDay.containsKey(date)) {
                calories = calories + mapCaloriesPerDay.get(date);
            }
            mapCaloriesPerDay.put(date, calories);
        }

        List<UserMealWithExceed> result = new ArrayList<>();

        for (UserMeal meal : mealList) {
            LocalTime time = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(time, startTime, endTime)) {
                boolean exceed = mapCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
                result.add(new UserMealWithExceed(meal, exceed));
            }

        }

        return result;
    }

}
