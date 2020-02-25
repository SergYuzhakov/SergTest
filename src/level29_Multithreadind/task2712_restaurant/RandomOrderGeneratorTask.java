package level29_Multithreadind.task2712_restaurant;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    List<Tablet> tablets;
    final int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {  // метод генерирует случайный заказ для случайно выбранного планшета с интервалом
        int index = (int) (Math.random() * tablets.size());
        Tablet currentTablet = tablets.get(index);
        try {
            Thread.sleep(interval);
            currentTablet.createTestOrder();
        }
        catch (InterruptedException e){

        }

    }
}
