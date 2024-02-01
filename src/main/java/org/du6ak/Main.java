package org.du6ak;

import org.du6ak.services.out.menu.MainMenu;

/**
 * Main class of the program.
 */
public class Main {
    private static final Main INSTANCE = new Main();

    public static Main getInstance() {
        return INSTANCE;
    }
    final MainMenu mainMenu = MainMenu.getInstance();

    /**
     * Main method of the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws Exception {
        Main.getInstance().run();
    }

    /**
     * Runs the program in an infinite loop.
     */
    public void run() throws Exception {
        while (true) {
            mainMenu.greetings();
        }
    }
}
