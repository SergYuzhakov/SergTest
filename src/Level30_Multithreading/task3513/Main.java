package Level30_Multithreading.task3513;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
         Model model = new Model();
         Controller controller = new Controller(model);
         View view = new View(controller);
        JFrame game = new JFrame("GAME-2048");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(450,510);
        game.setResizable(true);

        game.add(controller.getView());
        game.setLocationRelativeTo(null);
        game.setVisible(true);

    }
}
