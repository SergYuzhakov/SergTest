package level29_Multithreadind.task2712_restaurant;

import level29_Multithreadind.task2712_restaurant.ad.AdvertisementManager;
import level29_Multithreadind.task2712_restaurant.ad.NoVideoAvailableException;
import level29_Multithreadind.task2712_restaurant.kitchen.Order;
import level29_Multithreadind.task2712_restaurant.kitchen.TestOrder;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet  {
    private final int number;
    private static  java.util.logging.Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> orderQueue;

    public void setOrderQueue(LinkedBlockingQueue<Order> orderQueue) {
        this.orderQueue = orderQueue;
    }

    public Tablet(int number) {
        this.number = number;
    }

    public void  createTestOrder(){  // метод будет случайным образом генерировать заказ со случайными блюдами не общаясь с реальным человеком.
        try {
            TestOrder testOrder = new TestOrder(this);
            manageOrder(testOrder);
        } catch(IOException e){
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    public Order createOrder(){
        Order order = null;
        try {
            order = new Order(this); // создали новый заказ
            manageOrder(order);  // обработали его

        } catch (IOException e) {
            logger.log(Level.SEVERE,"Console is unavailable.");
        }

        return order;
    }


    public void manageOrder(Order order) {
        if (!order.isEmpty()) {
            ConsoleHelper.writeMessage(order.toString());  // вывели его на консоль
            try {
                orderQueue.put(order); // поместили заказ в очередь
            }
            catch (InterruptedException e){

            }

          //  setChanged();   // помечаем объект как измененный
          //  notifyObservers(order); // и извещаем наблюдателя
            try {

                AdvertisementManager adManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
                adManager.processVideos(); // Заказ готовится в то время, как видео смотрится.
            } catch (NoVideoAvailableException e) {
                logger.log(Level.INFO, "No video is available for the order " + order);
            }
        }
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }


}
