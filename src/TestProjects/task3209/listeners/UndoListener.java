package TestProjects.task3209.listeners;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class UndoListener implements UndoableEditListener {
          private UndoManager undoManager;



    public UndoListener(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    public  void undoableEditHappened(UndoableEditEvent e){  // метод из переданного события получает правку и добавляет ее в undoManager
        undoManager.addEdit(e.getEdit());
    }
}
