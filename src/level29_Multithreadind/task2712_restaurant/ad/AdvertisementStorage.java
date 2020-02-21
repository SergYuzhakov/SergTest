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
        add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min
        add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60)); //10 min

        add(new Advertisement(someContent,"Fourth Video", 200, 2, 30 * 60));
        /*
        Object someContent = new Object();
        add(new Advertisement(someContent, "First Video", 100, 10, 3 * 60)); // 3 min
        add(new Advertisement(someContent, "Second Video", 5000, 100, 15 * 60)); //15 min
        add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60)); //10 min
        add(new Advertisement(someContent,"Fourth Video", 200, 2, 30 * 60));
        add(new Advertisement(someContent,"Fivth Video", 2000, 2, 5 * 60));
        add(new Advertisement(someContent,"Sixth Video", 2000, 2, 15 * 60));
        add(new Advertisement(someContent,"Eighth Video", 2000, 5, 15 * 60));
        add(new Advertisement(someContent,"Nineth Video", 3000, 5, 8 * 60));

         */
    }

    public static synchronized AdvertisementStorage getInstance() {
        if(instance == null){
            instance = new AdvertisementStorage();
        }
        return instance;
    }
}
