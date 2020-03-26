package TestProjects.task3209;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public void resetDocument(){
        if(this.document != null) { this.document.removeUndoableEditListener(view.getUndoListener()); } // Удалять у текущего документа document слушателя правок которые можно отменить/вернуть (Слушателя запрашиваем у представления)
            document = (HTMLDocument)(new HTMLEditorKit().createDefaultDocument()); // Создаем новый документ по умолчанию и присваиваем его полю document.
            document.addUndoableEditListener(view.getUndoListener()); //  Добавляем новому документу слушателя правок.
              view.update(); // уведомляем представление об изменениях в модели

    }

    public void setPlainText(String text){  // Он будет записывать переданный текст с html тегами в документ document
          resetDocument();
         StringReader reader = new StringReader(text);
         try{
             new HTMLEditorKit().read(reader, document,0);
         }
         catch (Exception e){
             ExceptionHandler.log(e);
         }
    }

    public  String getPlainText(){  // Он должен получать текст из документа со всеми html тегами.
        StringWriter writer = new StringWriter();
        try {
            new HTMLEditorKit().write(writer, document, 0, document.getLength());
        }
        catch (Exception e) {
            ExceptionHandler.log(e);
        }
        return writer.toString();

    }

    public void createNewDocument(){
        view.selectHtmlTab();   // Выбирать html вкладку у представления.
         resetDocument(); // Сбрасывать текущий документ.
         view.setTitle("HTML редактор"); // Устанавливать новый заголовок окна
        view.resetUndo(); // Сбрасывать правки в Undo менеджере
        currentFile = null;

    }
    public  void  openDocument(){
        view.selectHtmlTab();   // Выбирать html вкладку у представления.
        JFileChooser jFileChooser = new JFileChooser();  // Создавать новый объект для выбора файла JFileChooser.
        jFileChooser.setFileFilter(new HTMLFileFilter()); // Устанавливать ему в качестве фильтра объект HTMLFileFilter.
        int chooseOption = jFileChooser.showOpenDialog(view); // Запускаем диалог выбора файла
        if(chooseOption == JFileChooser.APPROVE_OPTION) {// Если пользователь подтвердит выбор файла:
            currentFile = jFileChooser.getSelectedFile();   // Сохраняем выбранный файл в поле currentFile.
            resetDocument(); // Сбрасываем документ
            view.setTitle(currentFile.getName());  // Устанавливаем имя файла в качестве заголовка окна представления
            try {
                FileReader reader = new FileReader(currentFile);
                new HTMLEditorKit().read(reader, document, 0);
                view.resetUndo();
                reader.close();
            }
            catch (Exception e){
                ExceptionHandler.log(e);
            }
        }

    }

    public void saveDocument(){
        view.selectHtmlTab();   // Выбирать html вкладку у представления.
        if(currentFile == null){ saveDocumentAs();}
        else {
            try
            { FileWriter writer = new FileWriter(currentFile);
                new HTMLEditorKit().write(writer, document, 0, document.getLength()); // Переписывать данные из документа document в объекта FileWriter-а аналогично тому,
                // как мы это делали в методе getPlainText().
                writer.flush();
                writer.close();

            }
            catch (Exception e){
                ExceptionHandler.log(e);
            }
        }

     }

    public void saveDocumentAs(){
        view.selectHtmlTab();   // Выбирать html вкладку у представления.
        JFileChooser jFileChooser = new JFileChooser();  // Создавать новый объект для выбора файла JFileChooser.
        jFileChooser.setFileFilter(new HTMLFileFilter()); // Устанавливать ему в качестве фильтра объект HTMLFileFilter.
        int chooseOption = jFileChooser.showSaveDialog(view);
        if(chooseOption == JFileChooser.APPROVE_OPTION) {// Если пользователь подтвердит выбор файла:
               currentFile = jFileChooser.getSelectedFile();   // Сохраняем выбранный файл в поле currentFile.
                view.setTitle(currentFile.getName());  // Устанавливаем имя файла в качестве заголовка окна представления
           try
           {
               FileWriter writer = new FileWriter(currentFile);
               new HTMLEditorKit().write(writer, document, 0, document.getLength()); // Переписывать данные из документа document в объекта FileWriter-а аналогично тому,
                                                                                        // как мы это делали в методе getPlainText().
               writer.flush();
               writer.close();

           }
           catch (Exception e){
               ExceptionHandler.log(e);
           }
        }
    }

    public void init(){
            createNewDocument();
    }

    public void exit(){
        System.exit(0);
    }

    public static void main(String[] args) {
        View view = new View();      // создаем объект представления
        Controller controller = new Controller(view);   // Создаем контроллер, используя представление
        view.setController(controller); // Устанавливаем у представления контроллер.
        view.init(); // Инициализируем представление
        controller.init(); // Инициализируем контроллер. Контроллер должен инициализироваться после представления.

    }
}
