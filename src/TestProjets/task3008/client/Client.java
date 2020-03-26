package TestProjets.task3008.client;

import TestProjets.task3008.Connection;
import TestProjets.task3008.ConsoleHelper;
import TestProjets.task3008.Message;
import TestProjets.task3008.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {

    protected Connection connection;
    private volatile boolean clientConnected = false;


    public class SocketThread extends Thread {

        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage(String.format("Пользователь %s присоединился к чату", userName));
        }

        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage(String.format("Пользователь %s покинул чат", userName));

        }

        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException {

            while (true) {
                Message messageHandshake = connection.receive();

                if (messageHandshake.getType() == MessageType.NAME_REQUEST) {
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                } else {
                    if (messageHandshake.getType() == MessageType.NAME_ACCEPTED) {
                        notifyConnectionStatusChanged(true);
                        break;
                    } else {
                        if (messageHandshake.getType() != MessageType.NAME_REQUEST || messageHandshake.getType() != MessageType.NAME_ACCEPTED) {
                            throw new IOException("Unexpected MessageType");
                        }
                    }

                }
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message clientMess = connection.receive();

                if (clientMess.getType() == MessageType.TEXT) {
                    processIncomingMessage(clientMess.getData());
                } else {
                    if (clientMess.getType() == MessageType.USER_ADDED) {
                        informAboutAddingNewUser(clientMess.getData());
                    } else {
                        if (clientMess.getType() == MessageType.USER_REMOVED) {
                            informAboutDeletingNewUser(clientMess.getData());
                        } else {
                            if (clientMess.getType() != MessageType.TEXT || clientMess.getType() != MessageType.USER_ADDED || clientMess.getType() != MessageType.USER_REMOVED) {
                                throw new IOException("Unexpected MessageType");
                            }
                        }

                    }
                }
            }
        }


        public void run(){
            try {
                Socket socket = new Socket(getServerAddress(),getServerPort());
            Client.this.connection = new Connection(socket);
               clientHandshake();
               clientMainLoop();
            }
            catch (Exception e){
                notifyConnectionStatusChanged(false);
            }
        }
    }

    protected String getServerAddress(){
        ConsoleHelper.writeMessage("Введите адрес сервера");
        return ConsoleHelper.readString();
    }

    protected int getServerPort() throws Exception{
        ConsoleHelper.writeMessage("Введите адрес порта");
        return ConsoleHelper.readInt();
    }

    protected String getUserName(){
        ConsoleHelper.writeMessage("Введите имя пользователя");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole(){
        return true;
    }

    protected SocketThread getSocketThread(){

        return new SocketThread();
    }

    protected void sendTextMessage(String text){
        try {
            connection.send(new Message(MessageType.TEXT, text));
        }
        catch (IOException e){
            ConsoleHelper.writeMessage("Произошла ошибка соединения");
            clientConnected = false;
        }

    }

    public void run(){

        SocketThread socketThread = getSocketThread();// создаем вспомогательный поток SocketThread
           socketThread.setDaemon(true);// Помечаем созданный поток как daemon - чтобы при выходе из программы вспомогательный поток прервался автоматически
           socketThread.start();               // стартуем поток

      synchronized (this) {
       try {
           wait();              // Заставляем текущий поток ожидать, пока он не получит нотификацию из другого потока.
       } catch (InterruptedException e) {
           sendTextMessage("Ошибка соединения");
       }
 //  После того, как поток дождался нотификации, проверяем значение clientConnected.
   if(shouldSendTextFromConsole()) {
       if (clientConnected) {
           sendTextMessage("Соединение установлено.\n" +
                   "Для выхода наберите команду 'exit'.");
       } else {
           sendTextMessage("Произошла ошибка во время работы клиента.");
       }
   }
    // и запускаем цикл считывания сообщения с консоли пока клиент подключен.
    while (true){
        String s = ConsoleHelper.readString();       // читаем с консоли
         if(s.equals("exit") || !clientConnected) {break;}    // проверяем на exit и потерю коннекта
         if(shouldSendTextFromConsole()){           // и если true отправляем считанный текст с помощью метода sendTextMessage().
             sendTextMessage(s);
         }
    }

}

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();



    }

}
