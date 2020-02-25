package level29_Multithreadind.task2712_restaurant.ad;

import level29_Multithreadind.task2712_restaurant.ConsoleHelper;
import level29_Multithreadind.task2712_restaurant.statistic.StatisticManager;
import level29_Multithreadind.task2712_restaurant.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AdvertisementManager {
    final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;      // время заказа
    }

    public void processVideos() {       // метод обеспечивает подбор списка видео из доступных, просмотр которых обеспечивает максимальную выгоду
        if (storage.list().isEmpty() || getVideoList().isEmpty()) throw new NoVideoAvailableException();

        List<Advertisement> optimalVideoSet = prepareLists(); // получаем оптимальный набор видеороликов
        // регистрируем событие  "видео выбрано" перед отображением видео в консоль
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(optimalVideoSet, calcPrice(optimalVideoSet),calcDuration(optimalVideoSet)));

        // Перед выводом на консоль необходимо вызвать для каждого ролика метод уменьшающий кол-во показов
        for (Advertisement ad: optimalVideoSet) {
            ConsoleHelper.writeMessage(ad.toString());
            ad.revalidate();
        }
    }

    private List<Advertisement> getVideoList(){
        return storage.list().stream()
                .filter(s -> s.getDuration() <= (timeSeconds)) // и отбираем ролики с временем не больше времени приготовления заказа.
                .filter(s -> s.getHits() != 0)                            // отбираем ролики с ненулевым кол-вом показов
                .collect(Collectors.toList());
    }

    private List<Advertisement> prepareLists(){

        List<List<Advertisement>> lists = new ArrayList<>();
        setAllLists(lists, getVideoList());

        if(lists.size() > 1) {lists = filterMaxAmount(lists);}

        if(lists.size() > 1) {lists = filterMaxDuration(lists);}

        if(lists.size() > 1) {lists = filterMinSize(lists);}

        return sortResultList(lists.get(0));

    }

    public List<Advertisement> sortResultList(List<Advertisement> resultList) {
        List<Advertisement> res = resultList.stream().sorted(Comparator
                .comparingLong(Advertisement::getAmountPerOneDisplaying).reversed()
                .thenComparingLong(Advertisement::getAmountPerOneSecondDisplaying))
                .collect(Collectors.toList());
        return res;
    }
    // формируем все подходящие наборы(сочетания) рекламных роликов
    private void setAllLists(List<List<Advertisement>> lists, List<Advertisement> list){

        if(list.size() > 0 && calcDuration(list) <= timeSeconds && !lists.contains(list)) { lists.add(list);}

        for (int i = 0; i < list.size(); i++) {
            List<Advertisement> newAdList = new ArrayList<>(list); // для создания всех сочетаний роликов нам всегда нужна копия исходного списка, чтобы рекурсивно проходится по всем элементам
            newAdList.remove(i);  // удаляем один элемент
            setAllLists(lists, newAdList);  // и запускаем метод с новым списком
        }
    }


    private List<List<Advertisement>> filterMaxAmount(List<List<Advertisement>> adList){
        long maxAmaunt = calcPrice(adList.stream().max(Comparator.comparingLong(this::calcPrice)).get()); // определим максимальны профит в наборе списков роликов

        return   adList.stream()
                .filter(a -> calcPrice(a) == maxAmaunt).collect(Collectors.toList());

    }

    private List<List<Advertisement>> filterMaxDuration (List<List<Advertisement>> adList){
        long maxDuration = calcDuration(adList.stream().max(Comparator.comparingLong(this::calcDuration)).get()); // определим максимальное время списка из набора списков

        return adList.stream()
                .filter(a-> calcDuration(a) == maxDuration).collect(Collectors.toList());
    }

    private List<List<Advertisement>> filterMinSize (List<List<Advertisement>> adList){
        long minsize = calcSize(adList.stream().min(Comparator.comparingLong(this::calcSize)).get());

        return adList.stream()
                .filter(a -> calcSize(a) == minsize).collect(Collectors.toList());
    }

    private int calcDuration(List<Advertisement> list){  // вычисляем суммарное время рекламных роликов

        return list.stream().mapToInt(s -> s.getDuration()).sum();
    }

    private long calcPrice (List<Advertisement> list){ // вычисляем суммарную стоимость роликов в списке

        return list.stream().mapToLong(s -> s.getAmountPerOneDisplaying()).sum();
    }

    private long calcSize(List<Advertisement> list){
        return list.size();
    }
}
