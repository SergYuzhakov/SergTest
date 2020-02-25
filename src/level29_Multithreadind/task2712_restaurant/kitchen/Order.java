package level29_Multithreadind.task2712_restaurant.kitchen;

import level29_Multithreadind.task2712_restaurant.ConsoleHelper;
import level29_Multithreadind.task2712_restaurant.Tablet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public List<Dish> getDishes() {
        return dishes;
    }

    protected void initDishes() throws IOException{
        dishes = ConsoleHelper.getAllDishesForOrder(); // список dishes должен быть инициализирован результатом работы метода getAllDishesForOrder
    }

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();

    }

    public int getTotalCookingTime(){
        return dishes.stream().mapToInt(Dish::getDuration).sum();
    }

    public boolean isEmpty(){
        return dishes.isEmpty();
    }

    @Override
    public String toString() {
        if(dishes.isEmpty()) return "";
        else {
            return String.format("Your order: %s of %s", Arrays.toString(dishes.toArray()),tablet.toString());
        }
    }
}

