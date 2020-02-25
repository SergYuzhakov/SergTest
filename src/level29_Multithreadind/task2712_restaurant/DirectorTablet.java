package level29_Multithreadind.task2712_restaurant;

import level29_Multithreadind.task2712_restaurant.ad.Advertisement;
import level29_Multithreadind.task2712_restaurant.ad.StatisticAdvertisementManager;
import level29_Multithreadind.task2712_restaurant.statistic.StatisticManager;

import java.text.DecimalFormat;
import java.util.Map;

public class DirectorTablet {
    public void printAdvertisementProfit(){
        double totalAmount = 0;
        for (Map.Entry<String,Double> map : StatisticManager.getInstance().calcAdvertisementProfitPerDay().entrySet()) {
            ConsoleHelper.writeMessage(map.getKey() + " - " + new DecimalFormat("#0.00").format(map.getValue()));
            totalAmount = totalAmount + map.getValue();
        }
        ConsoleHelper.writeMessage("Total - " + new DecimalFormat("#0.00").format(totalAmount));
    }

    public void printCookWorkloading(){
        for (Map.Entry<String,Map<String,Integer>> map: StatisticManager.getInstance().calcCookWorkloading().entrySet()) {
            ConsoleHelper.writeMessage(map.getKey());
            for (Map.Entry<String,Integer> mapint: map.getValue().entrySet()) {
                ConsoleHelper.writeMessage(mapint.getKey() + " - " + mapint.getValue() + " min");
            }
        }

    }

    public void printActiveVideoSet(){
        for (Advertisement list: StatisticAdvertisementManager.getInstance().statisticActiveVideo()) {
            ConsoleHelper.writeMessage(list.getName() + " - " + list.getHits());
        }

    }

    public void printArchivedVideoSet(){
        for (Advertisement list: StatisticAdvertisementManager.getInstance().statisticArchiveVideo()) {
            ConsoleHelper.writeMessage(list.getName());

        }

    }

}
