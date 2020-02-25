package level29_Multithreadind.task2712_restaurant;

import level29_Multithreadind.task2712_restaurant.kitchen.Cook;
import level29_Multithreadind.task2712_restaurant.kitchen.Order;
import level29_Multithreadind.task2712_restaurant.kitchen.Waiter;
import level29_Multithreadind.task2712_restaurant.statistic.StatisticManager;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();


    public static void main(String[] args) {
        Waiter waiter = new Waiter(); // создаем официанта
        List<Cook> cooks = new LinkedList<>();      //  создаем список поваров
        //OrderManager orderManager = new OrderManager();

        for (int i = 0; i < 2 ; i++) {
            Cook cook = new Cook("Amigo " + i);
            cook.setOrderQueue(orderQueue);  // устанавливаем нашу закрепленную константу -очередь в качестве очереди для созданного повара
           // StatisticManager.getInstance().register(cook);
          //  cook.addObserver(waiter);
            cooks.add(cook);

        }

        List<Tablet> tablets = new LinkedList<>();  // создаем 5 планшетов
        for (int i = 1; i <=5 ; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setOrderQueue(orderQueue);
            //tablet.addObserver(orderManager);
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
