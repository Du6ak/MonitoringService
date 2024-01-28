package org.du6ak;

import static org.du6ak.services.out.menu.MainMenu.greetings;

/**
 * Main class of the program.
 */
public class Main {

    /**
     * Main method of the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws Exception {
        run();
    }

    /**
     * Runs the program in an infinite loop.
     */
    public static void run() throws Exception {
        while (true) {
            greetings();
        }
    }
}
