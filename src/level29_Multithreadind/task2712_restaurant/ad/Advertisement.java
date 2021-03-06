package level29_Multithreadind.task2712_restaurant.ad;

public class Advertisement {
    private Object content;  //  видео
    private String name;  // имя/название
    private long initialAmount;  //начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    private int hits;  // количество оплаченных показов
    private int duration;   // продолжительность в секундах



    private long amountPerOneDisplaying; // стоимости одного показа рекламного объявления в копейках

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        amountPerOneDisplaying =  hits > 0 ? initialAmount / hits : 0; // количество показов у любого рекламного ролика из набора - положительное число.
    }

    public int getHits() {
        return hits;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public long getAmountPerOneSecondDisplaying(){
        return amountPerOneDisplaying*1000/duration;
    }

    public void revalidate(){
        if(hits <= 0) throw new  NoVideoAvailableException();
        hits--;
    }

    @Override
    public String toString() {
        return getName() + String.format(" is displaying... %d, %d", amountPerOneDisplaying, getAmountPerOneSecondDisplaying());
    }
}
