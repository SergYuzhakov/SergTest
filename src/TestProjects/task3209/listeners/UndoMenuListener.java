package TestProjects.task3209.listeners;

import TestProjects.task3209.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class UndoMenuListener implements MenuListener {

    private View view;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;


    public UndoMenuListener(View view, JMenuItem undoMenuItem, JMenuItem redoMenuItem) {
        this.view = view;
        this.undoMenuItem = undoMenuItem;
        this.redoMenuItem = redoMenuItem;
    }

    @Override
    public void menuSelected(MenuEvent menuEvent) {
            undoMenuItem.setEnabled(this.view.canUndo());  // запрашиваем у представления  можем ли мы отменить действие с помощью метода boolean canUndo().
            redoMenuItem.setEnabled(this.view.canRedo()); // запрашиваем у представления  можем ли мы отменить действие с помощью метода boolean canRedo().
    }

    @Override
    public void menuDeselected(MenuEvent menuEvent) {

    }

    @Override
    public void menuCanceled(MenuEvent menuEvent) {

    }
}
