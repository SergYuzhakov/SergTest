package level29_Multithreadind.task2712_restaurant.statistic.event;

import java.util.Date;

public interface EventDataRow {
    EventType getType();
    Date getDate();  // реализация вернет дату создания записи
    int getTime();    // реализация вернет время - продолжительность
}
