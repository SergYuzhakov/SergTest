package level29_Multithreadind.task2712_restaurant;

import level29_Multithreadind.task2712_restaurant.kitchen.Cook;
import level29_Multithreadind.task2712_restaurant.kitchen.Order;
import level29_Multithreadind.task2712_restaurant.statistic.StatisticManager;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderManager implements Observer {
    private LinkedBlockingQueue<Order>  orderQueue = new LinkedBlockingQueue<>();

    public OrderManager() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Set<Cook> cooks = StatisticManager.getInstance().getCooks();
                while (true){
                    if(!orderQueue.isEmpty()) {
                        for (Cook cook: cooks) {
                            if(!cook.isBusy()){
                                cook.startCookingOrder(orderQueue.poll());
                            }
                        }
                        try {
                            Thread.sleep(10);
                        }
                        catch (InterruptedException e){

                        }
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    public void update(Observable o, Object arg) {

        orderQueue.offer((Order) arg);

    }
}
