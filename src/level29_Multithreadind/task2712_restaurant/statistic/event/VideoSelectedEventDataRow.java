package level29_Multithreadind.task2712_restaurant.statistic.event;

import level29_Multithreadind.task2712_restaurant.ad.Advertisement;

import java.util.Date;
import java.util.List;

public class VideoSelectedEventDataRow implements EventDataRow {
    List<Advertisement> optimalVideoSet; // список видео-роликов, отобранных для показа
    long amount;                            // сумма денег в копейках
    int totalDuration;                  // общая продолжительность показа отобранных рекламных роликов
    Date currentDate;

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration) {
        this.optimalVideoSet = optimalVideoSet;
        this.amount = amount;
        this.totalDuration = totalDuration;
        currentDate = new Date();
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public EventType getType() {
        return EventType.SELECTED_VIDEOS;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return totalDuration;
    }
}
