package level29_Multithreadind.task2712_restaurant.ad;

import java.util.ArrayList;
import java.util.List;

/*
хранилище рекламных роликов  должен быть синглтоном
 */
public class AdvertisementStorage {
    private static AdvertisementStorage instance;
    private final List<Advertisement> videos = new ArrayList();

    public List<Advertisement> list(){
        return videos;
    }

    public void add(Advertisement advertisement){
        videos.add(advertisement);
    }


    private AdvertisementStorage() {
        Object someContent = new Object();
        add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min
        add(new Advertisement(someContent, "Second Video", 100, 2, 5 * 60)); //15 min
        add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60)); //10 min
        add(new Advertisement(someContent,"Fourth Video", 200, 2, 8 * 60));
        add(new Advertisement(someContent,"Шестой видеоролик", 250, 5, 12 * 60));
       /*
        add(new Advertisement(someContent,"Fivth Video", 200, 2, 5 * 60));
        add(new Advertisement(someContent,"Sixth Video", 2000, 10, 25 * 60));
        add(new Advertisement(someContent,"Seventh Video", 100, 5, 12 * 60));
        */
    }

    public static synchronized AdvertisementStorage getInstance() {
        if(instance == null){
            instance = new AdvertisementStorage();
        }
        return instance;
    }
}
