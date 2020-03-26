package TestProjets.task3008;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public static void writeMessage(String message){
        System.out.println(message);
    }

   public static String readString() {
       String  s = null;
       try {
               s = br.readLine();
           }
       catch (IOException e) {
               writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
               return readString();
           }
           return s;

    }

    public static  int readInt() throws IOException {
        int ri = 0;

            try {
                ri = Integer.parseInt(readString());
            }
            catch (NumberFormatException e) {
                writeMessage("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
               return readInt();
            }
            return ri;

    }


}
