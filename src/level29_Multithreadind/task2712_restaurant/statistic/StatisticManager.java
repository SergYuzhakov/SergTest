package level29_Multithreadind.task2712_restaurant.statistic;

import level29_Multithreadind.task2712_restaurant.kitchen.Cook;
import level29_Multithreadind.task2712_restaurant.statistic.event.CookedOrderEventDataRow;
import level29_Multithreadind.task2712_restaurant.statistic.event.EventDataRow;
import level29_Multithreadind.task2712_restaurant.statistic.event.EventType;
import level29_Multithreadind.task2712_restaurant.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager  {
    // У нас должно быть одно хранилище с одной точкой входа. Поэтому сделаем StatisticManager синглтоном.

    private static StatisticManager instance;
    private StatisticStorage statisticStorage = new StatisticStorage();
    private Set<Cook> cooks = new HashSet<>();

    public Set<Cook> getCooks() {
        return cooks;
    }

    public void register(EventDataRow data){  //  метод будет регистрировать событие в хранилище ( в statisticStorage)
        statisticStorage.put(data);
    }

    public void register(Cook cook){   //  методбудет регистрировать полученного повара
        cooks.add(cook);
    }

    private StatisticManager() {
    }

    public Map<String,Double> calcAdvertisementProfitPerDay(){

        Map<String,Double> mapAdProfitPerDay = new TreeMap<>(Collections.reverseOrder()); // для хранения используем TreeMap с обратным порядком
        double tempAmount = 0;
        String data;
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        List<EventDataRow> videoEventList = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS); // получим список событий по видео из хранилища

        for (EventDataRow edr: videoEventList) {
            VideoSelectedEventDataRow videoList = (VideoSelectedEventDataRow)edr;
            data = df.format(videoList.getDate());
            tempAmount = videoList.getAmount()*1.0/100;

            if(!mapAdProfitPerDay.containsKey(data))
            { mapAdProfitPerDay.put(data, tempAmount);}
            else {
                tempAmount = tempAmount + mapAdProfitPerDay.get(data);
                mapAdProfitPerDay.put(data,tempAmount);
            }

        }



        return mapAdProfitPerDay ;
    }

    public Map<String,Map<String,Integer>> calcCookWorkloading(){
        Map<String, Map<String,Integer>> mapCookWorkloadig = new TreeMap<>(Collections.reverseOrder());
        String data;
        String cookName;
        int tempTime;
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        List<EventDataRow> cooksWorkloadigList = statisticStorage.getStorage().get(EventType.COOKED_ORDER);

        for (EventDataRow edr: cooksWorkloadigList) {
            CookedOrderEventDataRow cooksList = (CookedOrderEventDataRow) edr;
            data = df.format(cooksList.getDate());
            cookName = cooksList.getCookName();
            tempTime = cooksList.getTime();

            if (!mapCookWorkloadig.containsKey(data)) {

                mapCookWorkloadig.put(data, new TreeMap<>());
                mapCookWorkloadig.get(data).put(cookName,tempTime);
            }
            else {
                if(!mapCookWorkloadig.get(data).containsKey(cookName)){
                    mapCookWorkloadig.get(data).put(cookName,tempTime);
                }
                else {
                    tempTime = tempTime + mapCookWorkloadig.get(data).get(cookName).intValue(); // если уже есть повар с такой фамилией - прибавляем время работы над следующим заказом
                    mapCookWorkloadig.get(data).put(cookName,tempTime);   // и запоминаем все эти изменения в мапе
                }
            }

        }

        return mapCookWorkloadig;
    }

    private class StatisticStorage {
        Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }

        private StatisticStorage() {
            for (EventType et : EventType.values()) {
                storage.put(et, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data){
            storage.get(data.getType()).add(data); // добавляем в наше хранилище соответсвующие типу события данные
        }
    }

    public static synchronized StatisticManager getInstance() {
        if(instance == null){
            instance = new StatisticManager();
        }
        return instance;
    }
}
