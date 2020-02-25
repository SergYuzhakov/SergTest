package level29_Multithreadind.task2712_restaurant.ad;

import java.util.List;
import java.util.stream.Collectors;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    private final AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public static StatisticAdvertisementManager getInstance() {
        if(instance == null){
            instance = new StatisticAdvertisementManager();
        }
        return instance;
    }

    public List<Advertisement> statisticActiveVideo(){
        return advertisementStorage.list().stream()
                .filter(s -> s.getHits() > 0)
                .sorted(((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName())))
                .collect(Collectors.toList());
    }

    public List<Advertisement> statisticArchiveVideo(){

        return advertisementStorage.list().stream()
                .filter(s -> s.getHits() == 0 )
                .sorted(((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName())))
                .collect(Collectors.toList());
    }
}
