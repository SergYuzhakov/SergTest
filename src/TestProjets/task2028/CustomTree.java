package TestProjets.task2028;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root;

    public CustomTree(){
           root = new Entry<>("0");
    }

    public CustomTree(Entry<String> root) {
        root = new Entry<>("0");
    }



    @Override
    public boolean add(String elementName) {
        Entry actualEntry;
        Queue<Entry> queue = new LinkedList<>(); // для добавления элементов используем очередь для обхода дерева по горизонтали
        queue.add(root);                      // первый элемент очереди всегда root
        boolean isNotHaveChildren = false;   // флаг показывает невозможность добавить элемент в список

        while(!queue.isEmpty()) {
             actualEntry = queue.poll();         // и пока очередь не пуста

             if (actualEntry.availableToAddLeftChildren) {  // добавляем ссылку на следующий элемент списка  слева от родительского
                 Entry tempL = new Entry(elementName);
                 actualEntry.availableToAddLeftChildren = false;
                 tempL.parent = actualEntry;
                 actualEntry.leftChild = tempL;

                 isNotHaveChildren = true;  // устанавливаем флаг

                    queue.clear();     // и очищаем очередь
             }
               else {
                 if (actualEntry.availableToAddRightChildren) {  // и  добавляем ссылку на следующий элемент списка справа от родительского
                      Entry tempR = new Entry(elementName);
                      actualEntry.availableToAddRightChildren = false;
                      tempR.parent = actualEntry;
                      actualEntry.rightChild = tempR;

                      isNotHaveChildren = true;

                         queue.clear();
                 }
                  else {   // когда исчерпываем возможность добавлять элементы слева и справа от родительского вставляем в очередь потомков(если они есть) и добавление происходит дальше
                     if(actualEntry.leftChild != null) queue.add(actualEntry.leftChild);
                     if(actualEntry.rightChild != null) queue.add(actualEntry.rightChild);
                  }
               }
             }
            if(!isNotHaveChildren) isChekHaveChildren(elementName); // если не получилось добавить элемент по причине заблокированных полей availableToAdd вызываем метод восстановления
            // если операция прошла успешно возвращаем true
        return true;
    }

    @Override
    public String get(int index){
        throw new  UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new  UnsupportedOperationException();
    }

    @Override
    public int size() {  // для вычисления размера нашего списка используем стек для прохода по вертикали по элементам дерева
        int size = -1;
        Stack<Entry> stack = new Stack<>();
        Entry actual = root;
        while (actual != null || !stack.empty()){

            if(!stack.empty()){
                actual = stack.pop();
            }
            while (actual != null){
                size++;  // подсчитываем кол-во элементов списка
                if(actual.rightChild != null) {stack.push(actual.rightChild);}  // если есть потомок справа - заносим его в стек
                actual = actual.leftChild;         // а следующим элементом дерева будет потомок слева и так до полного исчерпания стека
            }
        }
        return size;
    }

    public void isChekHaveChildren(String elementName){  // если после операций удаления отсутствует возможность добавить новые элементы
        Queue<Entry> queue = new LinkedList<>();   // запускаем горизонтальный проход по дереву
        Entry topChek;
        queue.add(root);

        while (!queue.isEmpty()) {
            topChek = queue.poll();

            if((!topChek.isAvailableToAddChildren() && topChek.leftChild == null && topChek.rightChild == null)){
                topChek.availableToAddLeftChildren = true;
                topChek.availableToAddRightChildren = true;   // восстанавливаем способность иметь потомков
            }
            else {
                if(topChek.leftChild != null)queue.add(topChek.leftChild);
                if(topChek.rightChild != null)queue.add(topChek.rightChild);
            }

        }
        add(elementName); // и запускаем метод add с недобавленным обычным способом элементом
    }



    public String getParent(String s){  // для поиска родителя используем тот же проход по горизонтали с помощью очереди

          Queue<Entry> queue = new LinkedList<>();
          Entry top;
          queue.add(root);

          while (!queue.isEmpty()){
              top = queue.poll();

              if(top.leftChild != null && top.leftChild.elementName.equals(s)) {
                     queue.clear();
                     return top.leftChild.parent.elementName;}
                 else {
                     if (top.rightChild != null && top.rightChild.elementName.equals(s)) {
                         queue.clear();
                         return top.leftChild.parent.elementName;
                     }
                      else {
                         if(top.leftChild != null)queue.add(top.leftChild);
                         if(top.rightChild != null)queue.add(top.rightChild);
                     }
              }
          }

          return null;
    }

    public boolean remove(Object o){     //  для удаления элемента используем проход по вертикали с помощью стека
        String s;
        try {
            s = String.valueOf(o);
        }
        catch (Exception e){
            throw new UnsupportedOperationException();
        }
        Stack<Entry> stack = new Stack<>();
        Entry actual = root;
        while (actual != null || !stack.empty()) {

            if (!stack.empty()) {
                actual = stack.pop();
            }
            while (actual != null) {
                if (actual.elementName.equals(s)) { // и если элемент нашелся просто обнуляем ссылку родителя на этого потомка
                    if(actual == actual.parent.leftChild) actual.parent.leftChild = null; // т.е. если потомок слева - обнуляем ссылку слева
                    if(actual == actual.parent.rightChild) actual.parent.rightChild = null; //  и если потомок справа - обнуляем ссылку справа
                    while (!stack.empty()) {
                        stack.pop();
                    }
                    return true;
                }
                else {
                    if (actual.rightChild != null) {
                        stack.push(actual.rightChild);
                    }
                    actual = actual.leftChild;
                }
            }
        }

        throw new  UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new  UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new  UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new  UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new  UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new  UnsupportedOperationException();
    }

    static class Entry<T> implements Serializable{
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren (){
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}


