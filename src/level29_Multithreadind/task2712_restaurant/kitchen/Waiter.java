package level29_Multithreadind.task2712_restaurant.kitchen;

import Level27_Multithreading.task3110.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;

public class Waiter implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        ConsoleHelper.writeMessage(arg + " was cooked by " + o );
    }
}
