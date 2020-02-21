package level29_Multithreadind.task2712_restaurant.ad;

import level29_Multithreadind.task2712_restaurant.ConsoleHelper;

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
         // проверяем, что наши списки с роликами не пусты
        if (storage.list().isEmpty() || getVideoList().isEmpty()) throw new NoVideoAvailableException();
        // Перед выводом на консоль необходимо отсортировать согласно условию и вызвать для каждого ролика метод уменьшающий кол-во показов
        prepareLists(getVideoList()).stream()
                .sorted(Comparator.comparingLong(Advertisement::getAmountPerOneDisplaying).reversed().thenComparingLong(Advertisement::getAmountPerOneSecondDisplaying))
                .peek(s -> s.revalidate())
                .forEach(p -> ConsoleHelper.writeMessage(p.toString() + "Осталось показов " + p.getHits()));
    }

       private List<Advertisement> getVideoList(){
            return storage.list().stream()
                                 .filter(s -> s.getHits() != 0)                     // отбираем ролики с ненулевым кол-вом показов
                                 .filter(s -> s.getDuration() <= (timeSeconds))    // и отбираем ролики с временем не больше времени приготовления заказа.
                                 .collect(Collectors.toList());
    }

      private List<Advertisement> prepareLists(List<Advertisement> list){
           List<List<Advertisement>> lists = new ArrayList<>(); // хранилище всех наборов списков роликов

                setAllLists(lists, list);   // заполняем варианты

          for (int i = 0; i < lists.size() ; i++) {
              lists.get(i).stream().forEach(s -> ConsoleHelper.writeMessage(s.toString()));
              ConsoleHelper.writeMessage("Total time "  + calcDuration(lists.get(i))/60);
              ConsoleHelper.writeMessage("..............................................");
          }

            if(lists.size() > 1) lists = filterMaxAmount(lists);  // отбираем по максимальной цене
            if(lists.size() > 1) lists = filterMaxDuration(lists); // далее - по максимальной длительности
            if(lists.size() > 1) lists = filterBetweenTwo(lists); //  и если остались одинаковые наборы по предыдущим критериям - выбираем с меньшим кол-вом роликов



       return lists.get(0);
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
 ConsoleHelper.writeMessage("Max Profit " + maxAmaunt);
        return   adList.stream().filter(a -> calcPrice(a) == maxAmaunt).collect(Collectors.toList());

    }

    private List<List<Advertisement>> filterMaxDuration (List<List<Advertisement>> adList){
       long maxDuration = calcPrice(adList.stream().max(Comparator.comparingLong(this::calcDuration)).get()); // определим максимальное время списка из набора списков
ConsoleHelper.writeMessage("Max Duration " + maxDuration);
        return adList.stream().filter(a-> calcDuration(a) == maxDuration).collect(Collectors.toList());
    }

    private List<List<Advertisement>> filterBetweenTwo (List<List<Advertisement>> adList){
        for (int i = 0; i < adList.size() -1 ; i++) {
            if(calcPrice(adList.get(i)) == calcPrice(adList.get(i+1)) && calcDuration(adList.get(i)) == calcDuration(adList.get(i+1))) {
                if (adList.get(i).size() > adList.get(i + 1).size()) {
                    adList.remove(adList.get(i));
                }
            }
              else {
                    adList.remove(adList.get(i+1));

            }
        }

        return adList;
    }

    private Integer calcDuration(List<Advertisement> list){  // вычисляем суммарное время рекламных роликов

        return list.stream().mapToInt(s -> s.getDuration()).sum();
    }

    private long calcPrice (List<Advertisement> list){ // вычисляем суммарную стоимость роликов в списке

        return list.stream().mapToLong(s -> s.getAmountPerOneDisplaying()).sum();
    }


}
