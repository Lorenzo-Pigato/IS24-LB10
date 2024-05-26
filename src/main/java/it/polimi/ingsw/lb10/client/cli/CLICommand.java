package it.polimi.ingsw.lb10.client.cli;

/**
 * This interface provides all methods to create simple graphics on terminal
 */
public abstract class CLICommand {
    private static final int defaultWidth = 160;
    private static final int defaultHeight = 50;

    /**
     * Set cursor position to (1,1) - Home
     */
    public static void home() {
        System.out.print("\033[H");
        System.out.flush();
    }

    /**
     * Clear screen using ANSI code
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void clearLineAfterCursor() {
        System.out.print("\033[0K");
        System.out.flush();
    }

    public static void clearLineUntilCursor() {
        System.out.print("\033[1K");
        System.out.flush();
    }

    public static void clearLine() {
        System.out.print("\033[2K");
        System.out.flush();
    }

    /**
     * @param lineCount number of lines to clear
     */
    public static void clearNextLines(int lineCount) {
        for (int i = 0; i < lineCount; i++) {
            clearLine();
            System.out.println("\n");
        }
    }

    public static void clearScreenAfterCursor() {
        System.out.print("\033[0J");
        System.out.flush();
    }

    /**
     * @param input string to clear
     */
    public static void clearUserInput(String input) {
        CLICommand.restoreCursorPosition();     //Pushing back cursor to the beginning of the input

        for (int i = 0; i < input.length(); i++) System.out.print(" ");     //Overwriting input with spaces

        CLICommand.restoreCursorPosition();     //Pushing back cursor to the beginning of the input
    }


    /**
     * This method sets current terminal windows size
     * note that some terminals may not support this feature (e.g. Windows cmd)
     * @param cols number of terminal columns
     * @param rows number of terminal rows
     */
    public static void setScreenSize(int cols, int rows) {
        System.out.print("\033[8;" + rows + ";" + cols + "t");
    }

    /**
     * Set terminal window's size
     * clear window, cursor in (1,1)
     */
    public static void initialize(int width, int height) {
        setScreenSize(width, height);
        resetInvisibleInput();
        home();
        clearScreen();
    }

    /**
     * Set terminal window's default state:
     * window size of 160x50
     * clear window, cursor in (1,1)
     */
    public static void initialize() {
        initialize(defaultWidth, defaultHeight);
    }

    /**
     * Set cursor position inside window
     *
     * @param col column position - starting at 1
     * @param row row position - starting at 1
     */
    public static void setPosition(int col, int row) {
        if (row < 1) row = 1;
        if (col < 1) col = 1;
        System.out.print("\033[" + row + ";" + col + "H");
    }

    public static void saveCursorPosition() {
        System.out.print("\0337");
        System.out.flush();
    }

    public static void restoreCursorPosition() {
        System.out.print("\0338");
        System.out.flush();
    }


    public static int getDefaultHeight() {
        return defaultHeight;
    }

    public static int getDefaultWidth() {
        return defaultWidth;
    }


    public static void setInvisibleInput() {
        System.out.print("\033[8m");
        System.out.flush();
        System.out.print("\033[?25l");
        System.out.flush();
    }

    public static void resetInvisibleInput() {
        System.out.print("\033[28m");
        System.out.flush();
        System.out.print("\033[?25h");
        System.out.flush();
    }

}