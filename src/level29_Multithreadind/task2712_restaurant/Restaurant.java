package level29_Multithreadind.task2712_restaurant;

import level29_Multithreadind.task2712_restaurant.kitchen.Cook;
import level29_Multithreadind.task2712_restaurant.kitchen.Waiter;

public class Restaurant {
    public static void main(String[] args) {
        Tablet tablet = new Tablet(1);
        Cook cook = new Cook("Amigo"); // создаем повара
        Waiter waiter = new Waiter(); // создаем официанта
        tablet.addObserver(cook);  // добавляем повара наблюдателем объекта Tablet
        cook.addObserver(waiter); // добавляем официанта наблюдателем объекта Cook
        tablet.createOrder();  // инициируем создание заказа
       // tablet.createOrder();  // инициируем создание заказа
       // tablet.createOrder();  // инициируем создание заказа
       // tablet.createOrder();  // инициируем создание заказа


    }
}
