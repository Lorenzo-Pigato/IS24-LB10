package it.polimi.ingsw.lb10.client.cli.clipages;

import it.polimi.ingsw.lb10.client.cli.CLIBanner;
import it.polimi.ingsw.lb10.client.cli.CLIBox;
import it.polimi.ingsw.lb10.client.cli.CLICommand;
import it.polimi.ingsw.lb10.client.cli.CLIString;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.server.model.cards.Color;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.BottomLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TopLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomRight;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopRight;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import org.jetbrains.annotations.NotNull;

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

            CLIBox.draw(23,21,50,20,AnsiColor.WHITE);
            CLIBox.draw(23,38,50,3,"[1]", AnsiColor.CYAN, AnsiColor.WHITE, AnsiFormat.BOLD);

            CLIBox.draw(86,21,50,20,AnsiColor.WHITE);
            CLIBox.draw(86,38,50,3,"[2]", AnsiColor.CYAN, AnsiColor.WHITE, AnsiFormat.BOLD);

            for(int i=0; i < 2; i++){
                CLIBanner.displayQuest(args[i], 27 + 63*i,28);
                CLIBanner.printSymbol(49 + 63*i, 29, '=');
                CLIBanner.displayNumber(62 + 63*i, 28, ((Quest)args[0]).getPoints());
            }

            chooseQuest.centerPrint();
            inputField.print();
            CLICommand.saveCursorPosition();
        }

    }

    // --------- TEST -------------//

    public static void main(String[] args) {
        CLIPage page = new CLIStartMatchPage();
        page.print(new Object[]{
                new TopLeftDiagonal(1, 2, Color.BLUE),
                new BottomLeftDiagonal(2, 3, Color.RED)
        });

        try{
            Thread.sleep(2000);
        } catch (Exception e){
            System.out.println("Exception occurred");
        }

        page.print(new Object[]{
                new BottomLeft(1, 2, Color.GREEN, Color.PURPLE),
                new BottomRight(2, 3, Color.RED, Color.BLUE)
        });

        try{
            Thread.sleep(2000);
        } catch (Exception e){
            System.out.println("Exception occurred");
        }

        page.print(new Object[]{
                new TopLeft(1, 2, Color.GREEN, Color.PURPLE),
                new TopRight(2, 3, Color.RED, Color.BLUE)
        });

    }
}
