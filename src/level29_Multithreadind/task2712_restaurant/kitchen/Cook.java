package level29_Multithreadind.task2712_restaurant.kitchen;

import level29_Multithreadind.task2712_restaurant.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;

public class Cook extends Observable implements Observer {
    private String name;

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!((Order)arg).isEmpty()) {
            ConsoleHelper.writeMessage("Start cooking - " + arg + ",cooking time " + ((Order) arg).getTotalCookingTime() + "min");
            setChanged();
            notifyObservers(arg);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
