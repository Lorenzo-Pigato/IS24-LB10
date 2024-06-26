package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the CLI page where the player can choose its private quest before the start of the match
 * Page creation and management is done through the State design pattern, as previous pages
 * When calling the print method, the page will be displayed with the two quests passed as arguments
 * InvalidInput state is used to display an error message when the player inputs an invalid choice
 */
public class CLIChooseQuestPage implements CLIPage {
    private static final CLIString invalidInput = new CLIString(">> Choose [1] or [2] <<", AnsiColor.RED, AnsiFormat.BOLD, 1, 45);
    private static final CLIString chooseQuest = new CLIString(">> Choose a quest to start the match <<", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 45);
    private static final CLIString inputField = new CLIString(">> ", AnsiColor.CYAN, 65, 46);
    private static final CLIString wait = new CLIString(">> Waiting for other players... <<", AnsiColor.CYAN, AnsiFormat.BOLD, 61, 46);
    private CLIState state = new CLIChooseQuestPage.Default();

    @Override
    public void changeState(@NotNull CLIState state) {
        this.state = state;
    }

    @Override
    public void print(Object[] args) {
        state.apply(args);
    }

    /**
     * This class represents the default state of the page, where the player can choose its private quest
     * Two quests are displayed on the screen, with their points and description
     * The player can choose between the two of them by typing 1 or 2
     * Quest must be passed as an argument to the print method
     */
    public static class Default implements CLIState {
        @Override
        public void apply(Object[] args) {
            CLICommand.initialize();
            CLIBanner.displayChooseQuest();

            for (int i = 0; i < 2; i++) {
                CLIBox.draw(23 + i * 59, 21, 55, 23, AnsiColor.WHITE);
                CLIBox.draw(23 + i * 59, 41, 55, 3, "[" + (i + 1) + "]", AnsiColor.CYAN, AnsiColor.WHITE, AnsiFormat.BOLD);
                CLIBox.draw(45 + i * 59, 21, 11, 8, AnsiColor.YELLOW);

                CLIBanner.displayNumber(47 + 59 * i, 22, ((Quest) args[i]).getPoints(), AnsiColor.YELLOW);
                CLIBanner.displayQuest(args[i], 40 + 59 * i, 32);
            }

            chooseQuest.centerPrint();
            inputField.print();
            CLICommand.saveCursorPosition();
        }
    }

    public static class InvalidInput implements CLIState {
        @Override
        public void apply(Object @NotNull [] args) {
            CLIString.replace(chooseQuest, invalidInput);
            CLICommand.clearUserInput((String) args[0]);
        }
    }

    /**
     * This class is used to display a waiting message after the player has chosen its quest and is waiting for other players
     * to do the same.
     * In this state, player's input is disabled.
     */
    public static class WaitingState implements CLIState {
        @Override
        public void apply(Object @NotNull [] args) {
            CLIString.replace(chooseQuest, wait);
            inputField.deleteString();

            CLICommand.clearUserInput((String) args[0]);
            CLICommand.setInvisibleInput();
            CLICommand.setPosition(1, 48);
            CLICommand.saveCursorPosition();
        }
    }
}
