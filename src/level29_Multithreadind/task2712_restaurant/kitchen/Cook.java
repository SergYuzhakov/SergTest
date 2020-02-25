package level29_Multithreadind.task2712_restaurant.kitchen;

import level29_Multithreadind.task2712_restaurant.ConsoleHelper;
import level29_Multithreadind.task2712_restaurant.statistic.StatisticManager;
import level29_Multithreadind.task2712_restaurant.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook implements Runnable {
    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue;

    public void setOrderQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public boolean isBusy() {
        return busy;
    }

    public Cook(String name) {
        this.name = name;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        if (!(order.isEmpty())) {
            ConsoleHelper.writeMessage("Start cooking - " + order + ",cooking time " + (order.getTotalCookingTime() + "min"));
            // setChanged();
            // notifyObservers(order);
            StatisticManager.getInstance().register(new CookedOrderEventDataRow(this.toString(), name, order.getTotalCookingTime(), order.getDishes()));
            // регистрируем событие для повара во время приготовления еды
        }
        try {
            Thread.sleep(order.getTotalCookingTime() * 10);
        } catch (InterruptedException e) {

        }
        busy = false;

    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void run() {
        while (true) {
            if (!queue.isEmpty()) {
                if (!this.isBusy()) {
                    this.startCookingOrder(queue.poll());
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
