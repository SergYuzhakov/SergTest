package TestProjects.task3209.listeners;

import TestProjects.task3209.ExceptionHandler;
import TestProjects.task3209.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;


public class TextEditMenuListener implements MenuListener {
    private   View view;
    private JTabbedPane tabbedPane;

        public TextEditMenuListener(View view) {
            this.view = view;
        }

        @Override
    public void menuSelected(MenuEvent menuEvent) {
            JMenu jMenu = (JMenu)menuEvent.getSource();  // из выбранноаго пункта меню получаем объект меню

     for (Component c: jMenu.getMenuComponents()) {  // и для каждого выбранного элемента нашего объекта JMenu
           c.setEnabled(view.isHtmlTabSelected());       // установим доступность пункта в зависимости от типа вкладки  - HTML/Текст
     }
    }

    @Override
    public void menuDeselected(MenuEvent menuEvent) {

    }

    @Override
    public void menuCanceled(MenuEvent menuEvent) {

    }
}
