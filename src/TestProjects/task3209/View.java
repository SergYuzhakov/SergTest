package TestProjects.task3209;

import TestProjects.task3209.listeners.FrameListener;
import TestProjects.task3209.listeners.TabbedPaneChangeListener;
import TestProjects.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane(); // создаем панель вкладок на Java Swing - позволяет размещать компоненты на так называемых вкладках tabs
    private JTextPane htmlTextPane = new JTextPane();  // создаем текстовый редактор, котрый добавляет к возможностям JEditorPane разметку текста стилями
    private JEditorPane plainTextPane = new JEditorPane(); // создаем еще один текстовый редактор для отображения текста любого формата (HTML и RTF)
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);

        View(){ // конструктор устанавливает внешний вид и поведение (look and feel) нашего приложения такими же, как это определено в системе.
         try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
         catch (Exception e){
             ExceptionHandler.log(e);
         }
}

    public UndoListener getUndoListener() { return undoListener; }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void init(){
        initGui(); // вызываем инициализацию графического интерфейса initGui().
        FrameListener listener = new FrameListener(this); // Добавим слушателя событий нашего окна.
        // В качестве подписчика создем и используем объект класса FrameListener.
         addWindowListener(listener);  //Adds the specified window listener to receive window events from this window.
         setVisible(true); // делает видимым наше окно
    }

    public void initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        // инициализируем меню в следующем порядке: Файл, Редактировать, Стиль, Выравнивание, Цвет, Шрифт и Помощь.
        MenuHelper.initFileMenu(this,menuBar);
        MenuHelper.initEditMenu(this,menuBar);
        MenuHelper.initStyleMenu(this, menuBar);
        MenuHelper.initAlignMenu(this,menuBar);
        MenuHelper.initColorMenu(this, menuBar);
        MenuHelper.initFontMenu(this, menuBar);
        MenuHelper.initHelpMenu(this, menuBar);
       // Добавим в верхнюю часть панели контента текущего фрейма нашу панель меню
        getContentPane().add(menuBar,BorderLayout.NORTH);



    }

    public void initEditor(){
        htmlTextPane.setContentType("text/html");  // Устанавливаем значение "text/html" в качестве типа контента для текстового редактора htmlTextPane
        JScrollPane jScrollPaneHtml = new JScrollPane(htmlTextPane);   // на основе компонента htmlTextPane создаем новый компонент - панель прокрутки для HTML панели
        tabbedPane.add("HTML",jScrollPaneHtml);   // и добавляем эту панель в панель вкладок с названием HTML

        plainTextPane.setContentType("text");
        JScrollPane jScrollPaneText = new JScrollPane(plainTextPane);
        tabbedPane.add("Текст", jScrollPaneText); // аналогично для текстового редактора plainTextPane - добавляем панель "Текст" в панель вкладок

        tabbedPane.setPreferredSize(new Dimension(300,300)); // Устанавливим предпочтительный размер панели вкладок tabbedPane

        TabbedPaneChangeListener tpcl = new TabbedPaneChangeListener(this);
        tabbedPane.addChangeListener(tpcl);   // Добавим объект класса TabbedPaneChangeListener в качестве слушателя изменений в tabbedPane

        getContentPane().add(tabbedPane,BorderLayout.CENTER); // и добавим нашу панел вкладок в панель контента текущего фрейма
    }


    public void initGui(){
        initMenuBar();
        initEditor();
        pack(); // подбирает размеры окна оптимально с учетом содержимого
    }

    public void selectedTabChanged(){
            if(tabbedPane.getSelectedIndex() == 0){ controller.setPlainText(plainTextPane.getText());} // Если выбрана вкладка с индексом 0 (html вкладка), значит нам нужно получить текст из plainTextPane
                        // и установить его в контроллер с помощью метода setPlainText
        else {
            if(tabbedPane.getSelectedIndex() == 1) {
                plainTextPane.setText(controller.getPlainText()); //  Если выбрана вкладка с индексом 1 (вкладка с html текстом),
                // то необходимо получить текст у контроллера с помощью метода getPlainText() и установить его в панель plainTextPane.
            }
        }
        resetUndo();

    }

    public boolean canUndo(){
            return undoManager.canUndo();
    }
    public boolean canRedo(){
            return undoManager.canRedo();
    }

    public void undo(){try {    // метод должен отменяеть последнее действие.
        undoManager.undo();
    }
    catch (Exception e){
        ExceptionHandler.log(e);
    }
    }

    public void redo(){try {    // метод должен возвращаеть ранее отмененное действие
        undoManager.redo();
    }
    catch (Exception e){
        ExceptionHandler.log(e);
    }
    }

    public void  resetUndo(){
            undoManager.discardAllEdits();   // метод должен сбрасывать все правки в менеджере undoManager
    }
    public boolean isHtmlTabSelected(){
            return  tabbedPane.getSelectedIndex() == 0;  // Он должен возвращать true, если выбрана вкладка, отображающая html в панели вкладок (ее индекс 0)
    }

    public void selectHtmlTab(){  // переключаемся на вкладку HTML  и сбрасываем все правки
            tabbedPane.setSelectedIndex(0);
            resetUndo();
    }

    public void update(){  // получаем модель (документ) у контроллера и устанавливаем его в панель редактирования htmlTextPane.
            htmlTextPane.setDocument(controller.getDocument());
        }

    public void showAbout(){ // выводит сообщение о программе ( запускается в методе actionPerformed(ActionEvent e)
        JOptionPane.showMessageDialog(getContentPane(),
                        " Version 1.0",
                        " About",
                JOptionPane.INFORMATION_MESSAGE);
    }



    public void exit(){
        controller.exit();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
            switch (actionEvent.getActionCommand()){
                case ("Новый"):
                    controller.createNewDocument();
                    break;
                case ("Открыть"):
                    controller.openDocument();
                    break;
                case ("Сохранить"):
                    controller.saveDocument();
                    break;
                case ("Сохранить как..."):
                    controller.saveDocumentAs();
                    break;
                case("Выход"):
                    exit();
                    break;
                case ("О программе"):
                    showAbout();
                    break;
            }
        }
}
