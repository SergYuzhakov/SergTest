package level29_Multithreadind.task2712_restaurant;

import level29_Multithreadind.task2712_restaurant.ad.AdvertisementManager;
import level29_Multithreadind.task2712_restaurant.ad.NoVideoAvailableException;
import level29_Multithreadind.task2712_restaurant.kitchen.Order;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet extends Observable { //Класс наследуемый от Observable - оповещатель, информирует об изменениях через notifyObservers()
    private final int number;
    private static  java.util.logging.Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder(){
        Order order = null;
        AdvertisementManager adManager = null;
        try {
            order = new Order(this); // создали новый заказ

            if(!order.isEmpty()) {
                ConsoleHelper.writeMessage(order.toString());  // вывели его на консоль
                adManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
                try {
                    adManager.processVideos(); // Заказ готовится в то время, как видео смотрится.
                }
                catch (NoVideoAvailableException e){
                    logger.log(Level.INFO, "No video is available for the order " + order);
                }
                setChanged();   // помечаем объект как измененный
                notifyObservers(order); // и извещаем наблюдателя
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE,"Console is unavailable.");
        }

        finally {

        }
        return order;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
