package TestProjects.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
 /*
 Для открытия или сохранения файла мы будем использовать JFileChooser из библиотеки swing.
Объекты этого типа поддерживают фильтры, унаследованные от FileFilter.

  */
public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File file) {   // возвращает true, если переданный файл директория или содержит в конце имени ".html" или ".htm" без учета регистра.
        String s = file.getName().toLowerCase();
        return  (file.isDirectory() || s.endsWith(".html") || s.endsWith(".htm"));

    }

    @Override
    public String getDescription() {  // Чтобы в окне выбора файла в описании доступных типов файлов отображался текст "HTML и HTM файлы"
        return "HTML и HTM файлы";
    }
}
