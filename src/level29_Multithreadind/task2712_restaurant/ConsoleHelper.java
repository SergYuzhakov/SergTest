package level29_Multithreadind.task2712_restaurant;

import level29_Multithreadind.task2712_restaurant.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper  {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return br.readLine();
    }

    public static  List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishList = new ArrayList<>();
        String s;
        writeMessage("МЕНЮ:");
        writeMessage(Dish.allDishesToString());
        writeMessage("Выберите блюдо из списка и введите его название, exit - для завершения.");

        while (!(s = readString()).equals("exit")){
            try {
                dishList.add(Dish.valueOf(s));
            }
            catch (Exception e){
                writeMessage("Данное блюдо отсутствует в меню");
            }
        }
        return dishList;

    }
}
