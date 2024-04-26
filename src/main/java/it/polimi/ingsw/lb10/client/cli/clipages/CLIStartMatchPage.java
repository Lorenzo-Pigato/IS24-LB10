package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.BottomLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TopLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomRight;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopRight;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CLIStartMatchPage implements CLIPage {
    private static final CLIString invalidInput = new CLIString(">> Choose [1] or [2] <<", AnsiColor.RED, AnsiFormat.BOLD, 1, 45);
    private static final CLIString chooseQuest = new CLIString(">> Choose a quest to start the match <<", AnsiColor.CYAN, AnsiFormat.BOLD, 1, 45);
    private static final CLIString inputField = new CLIString(">> ", AnsiColor.CYAN, 61, 46);

    private CLIState state = new CLIStartMatchPage.Default();

    @Override
    public void changeState(@NotNull CLIState state) {
        this.state = state;
    }

    @Override
    public void print(Object[] args) {
        state.apply(args);
    }

    public static class Default implements CLIState {
        @Override
        public void apply(Object[] args) {
            CLICommand.initialize();
            CLIBanner.displayChooseQuest();

            for(int i=0; i < 2; i++){
                CLIBox.draw(23 + i*59,21,55,23,AnsiColor.WHITE);
                CLIBox.draw(23 + i*59,41,55,3,"["+ (i+1) +"]", AnsiColor.CYAN, AnsiColor.WHITE, AnsiFormat.BOLD);
                CLIBox.draw(45 + i*59,21,11,8,AnsiColor.YELLOW);

                CLIBanner.displayNumber(47 + 59*i, 22, ((Quest)args[i]).getPoints(), AnsiColor.YELLOW);
                CLIBanner.displayQuest(args[i], 40 + 59*i,32);
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
}
