package level29_Multithreadind.task2712_restaurant.statistic.event;

import level29_Multithreadind.task2712_restaurant.kitchen.Dish;

import java.util.Date;
import java.util.List;

public class CookedOrderEventDataRow implements EventDataRow {
    private Date currentDate;
    private String tabletName;               // имя планшета
    private String cookName;              // имя повара
    private int cookingTimeSeconds;     // время приготовления заказа в секундах
    private List<Dish> cookingDishs; // список блюд для приготовления

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishs) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishs = cookingDishs;
        currentDate = new Date();
    }

    public String getCookName() {
        return cookName;
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }
}
