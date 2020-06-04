package pl.mw.testing.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealRepository {

    List<Meal> meals = new ArrayList<>();

    public void add(Meal meal) {
        meals.add(meal);
    }

    public void delete(Meal meal) {
        meals.remove(meal);
    }

    public List<Meal> getAllMeals() {
        return meals;
    }

    public List<Meal> findByName(String mealName, boolean exactMatch) {

        Predicate<Meal> mealPredicate;
        if (exactMatch) {
            mealPredicate = meal -> meal.getName().equals(mealName);
        } else {
            mealPredicate = meal -> meal.getName().startsWith(mealName);
        }
        
        return meals.stream()
                    .filter(mealPredicate)
                    .collect(Collectors.toList());
    }

    public List<Meal> findByPrice(int price) {
        return meals.stream()
                    .filter(meal -> meal.getPrice() == price)
                    .collect(Collectors.toList());
    }
}
