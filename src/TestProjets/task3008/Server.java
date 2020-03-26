package TestProjets.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static TestProjets.task3008.ConsoleHelper.readInt;
import static TestProjets.task3008.ConsoleHelper.writeMessage;

public class Server {

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<String, Connection>();

    public static void sendBroadcastMessage(Message message){
        try {

            for (Map.Entry k : connectionMap.entrySet()) {
               Connection con =(Connection)k.getValue();
                 con.send(message);
            }
        }catch (IOException e){
            writeMessage("Сообщение не доставлено");
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST,"Введите имя"));  // отправляем запрос имени используя метод send класса Connection.

                Message message = connection.receive();        // принимаем ответ используя метод receive  класса Connection
                String name = message.getData();

                if ((message.getType() == (MessageType.USER_NAME)) &&  // если тип сообщения полученного в ответ будет равен MessageType.USER_NAME
                        !name.isEmpty() &&                               //и  в ответ пришло не пустое имя,
                              (!connectionMap.containsKey(name))) {   //  и имя не содержится в connectionMap

                    connectionMap.put(name, connection);     //добавляем новую пару (имя, соединение) в connectionMap
                    connection.send(new Message(MessageType.NAME_ACCEPTED,  "Вы добавлены в чат")); // отправляем сообщение о том, что имя было принято.

                    return name; //возвращаем имя нового клиента с которым было установлено соединение.
                }
            }
        }

        private void notifyUsers(Connection connection, String userName) throws IOException{
            for (Map.Entry k: connectionMap.entrySet()) {  //   Для каждого пользователя из списка соединений
                if(!(k.getKey().toString().equals(userName))){  // исключая пользователя с тем же именем
                    connection.send(new Message(MessageType.USER_ADDED, k.getKey().toString())); // выводим сообщение об участиках чата
                }

            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true){
                Message mess = connection.receive();  // принимаем ответ используя метод receive  класса Connection

               if(mess.getType() == (MessageType.TEXT)){
                   sendBroadcastMessage(new Message(MessageType.TEXT,String.format("%s: %s",userName,mess.getData())));
               }
               else {
                   writeMessage("Ошибка - нет сообщений");
               }
            }
        }

        public void run(){

            writeMessage(String.format("Установлено новое соединение: %s", socket.getRemoteSocketAddress()));

            try {
                Connection connection = new Connection(socket);
                String nameUser = serverHandshake(connection);



                    sendBroadcastMessage(new Message(MessageType.USER_ADDED, nameUser));
                    notifyUsers(connection, nameUser);

                    serverMainLoop(connection, nameUser);

                    socket.close();


                    connectionMap.remove(nameUser);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, nameUser));

            } catch (IOException  | ClassNotFoundException e) {
                   writeMessage("Ошибка соединения");
            }
        }

        public Handler(Socket socket) {
            this.socket = socket;
        }
    }

    public static void main(String[] args) throws IOException {
       writeMessage("Введите адрес порта");
       ServerSocket server = new ServerSocket(readInt());
       writeMessage("Сервер запущен!");

try {
    while (true) {
        try {
            new Handler(server.accept()).start();
        } catch (IOException e) {
            writeMessage(e.getMessage());
            break;
        }
    }
}
    finally {
         server.close();
        }
    }
}
