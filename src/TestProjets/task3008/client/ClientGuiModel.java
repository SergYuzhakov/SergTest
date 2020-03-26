package TestProjets.task3008.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClientGuiModel {
   private final Set<String> allUserNames = new HashSet<>();
   private String newMessage;

    public Set<String> getAllUserNames() {
        return Collections.unmodifiableSet(allUserNames); // запретив модифицировать возвращенное множество
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public void addUser(String newUserName){
        allUserNames.add(newUserName);
    }

    public void deleteUser(String userName){
        Iterator<String> it = allUserNames.iterator();

        while (it.hasNext()){
            String nameuser = it.next();
            if(nameuser.equals(userName)) it.remove();
            }
        }

    }



