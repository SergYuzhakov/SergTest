package level29_Multithreadind.task2712_restaurant.kitchen;

import level29_Multithreadind.task2712_restaurant.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {
    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        dishes = new ArrayList<>();
        Dish [] dishesTestOrder = Dish.values();
        int countDishes = (int) (Math.random() * dishesTestOrder.length +1);

        for (int i = 0; i < countDishes ; i++) {
            int id = (int)(Math.random() * dishesTestOrder.length);
            Dish dish = dishesTestOrder[id];
            dishes.add(Dish.valueOf(dish.toString()));
        }
    }
}
