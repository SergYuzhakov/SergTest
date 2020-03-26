package TestProjets.task3008.client;

import TestProjets.task3008.ConsoleHelper;
import TestProjets.task3008.MessageType;
import javafx.scene.input.DataFormat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BotClient extends Client {

    public class BotSocketThread extends SocketThread{
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {

            ConsoleHelper.writeMessage(message);

            if(!message.contains(":")) return;

            String name = message.replaceAll(":.*","");
            String text = (message.substring(message.indexOf(":") + 1 , message.length())).trim();

            Map<String,String> mapbot = new HashMap<>();
                 mapbot.put("дата","d.MM.YYYY");
                 mapbot.put("день","d");
                 mapbot.put("месяц","MMMM");
                 mapbot.put("год","YYYY");
                 mapbot.put("время","H:mm:ss");
                 mapbot.put("час","H");
                 mapbot.put("минуты","m");
                 mapbot.put("секунды","s");

            for (Map.Entry<String,String>  m: mapbot.entrySet()) {
                if (text.equals(m.getKey())) {
                    SimpleDateFormat df = new SimpleDateFormat(m.getValue(),Locale.ENGLISH);
                    Date date = new Date();
                    sendTextMessage(String.format("Информация для %s: %s",name, df.format(date.getTime())));
                }
            }

        }
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    protected String getUserName(){

        return "date_bot_" + (int)(Math.random()*100);
    }

    public static void main(String[] args) {
        BotClient bc = new BotClient();
        bc.run();
    }
}
