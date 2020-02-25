package level29_Multithreadind.task2712_restaurant.kitchen;

import level29_Multithreadind.task2712_restaurant.ConsoleHelper;
import level29_Multithreadind.task2712_restaurant.statistic.StatisticManager;
import level29_Multithreadind.task2712_restaurant.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;

public class Cook extends Observable  {
    private String name;
    private boolean busy;

    public boolean isBusy() {
        return busy;
    }

    public Cook(String name) {
        this.name = name;
    }

    public void startCookingOrder(Order order){
        busy = true;
        if(!(order.isEmpty())) {
            ConsoleHelper.writeMessage("Start cooking - " + order + ",cooking time " + (order.getTotalCookingTime() + "min"));
            setChanged();
            notifyObservers(order);
            StatisticManager.getInstance().register(new CookedOrderEventDataRow(null, name, order.getTotalCookingTime(), order.getDishes()));
            // регистрируем событие для повара во время приготовления еды
        }
        try {
            Thread.sleep(order.getTotalCookingTime()  * 10);
        }
        catch (InterruptedException e){

        }
        busy = false;

    }

    @Override
    public String toString() {
        return name;
    }
}
