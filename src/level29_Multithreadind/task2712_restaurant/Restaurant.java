package level29_Multithreadind.task2712_restaurant;

import level29_Multithreadind.task2712_restaurant.kitchen.Cook;
import level29_Multithreadind.task2712_restaurant.kitchen.Waiter;
import level29_Multithreadind.task2712_restaurant.statistic.StatisticManager;

import java.util.LinkedList;
import java.util.List;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;


    public static void main(String[] args) {
        Waiter waiter = new Waiter(); // создаем официанта
        List<Cook> cooks = new LinkedList<>();      //  создаем список поваров
        OrderManager orderManager = new OrderManager();

        for (int i = 0; i < 2 ; i++) {
            Cook cook = new Cook("Amigo" + i);
            StatisticManager.getInstance().register(cook);
            cook.addObserver(waiter);
            cooks.add(cook);

        }

        List<Tablet> tablets = new LinkedList<>();  // создаем 5 планшетов
        for (int i = 1; i <=5 ; i++) {
            Tablet tablet = new Tablet(i);
            tablet.addObserver(orderManager);

            tablets.add(tablet);
        }

        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        thread.start();
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e){

        }
        thread.interrupt();


        // tablet.addObserver(cook);  // добавляем повара наблюдателем объекта Tablet
        //tablet.addObserver(cook2);
        //cook.addObserver(waiter); // добавляем официанта наблюдателем объекта Cook
        //cook2.addObserver(waiter);
        //  tablet.createOrder();  // инициируем создание заказа


/*
        DirectorTablet dirTablet = new DirectorTablet();
        dirTablet.printAdvertisementProfit();
        dirTablet.printCookWorkloading();
        dirTablet.printActiveVideoSet();
        dirTablet.printArchivedVideoSet();
 */

        //tablet.createOrder();  // инициируем создание заказа
        //tablet.createOrder();  // инициируем создание заказа
        //tablet.createOrder();  // инициируем создание заказа



    }
}
