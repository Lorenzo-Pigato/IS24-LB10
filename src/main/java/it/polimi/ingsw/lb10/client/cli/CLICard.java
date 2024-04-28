package it.polimi.ingsw.lb10.client.cli;


import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiFormat;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.BaseCard;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.StartingCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.BottomLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.Diagonal.TopLeftDiagonal;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.BottomRight;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopLeft;
import it.polimi.ingsw.lb10.server.model.quest.Pattern.LJ.TopRight;
import it.polimi.ingsw.lb10.server.model.quest.Quest;
import it.polimi.ingsw.lb10.server.model.quest.QuestCounter;

public abstract class CLICard {
    private static void printBaseCard(BaseCard card, int col, int row) {
        new CLIString(
                """
                        █████████████████████
                        █▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█
                        █▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█
                        █▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█
                        █▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█
                        █▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█
                        █▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█
                        █████████████████████
                        """
                , card.getColorCard().getAnsi(), col, row).print();

        for (Position position : Position.values()) {
            card.getStateCardCorners().stream()
                    .filter(c -> c.getPosition().equals(position) && c.isAvailable())
                    .findFirst()
                    .map(Corner::getResource)
                    .ifPresent(resource -> new CLIString(
                            "▛▀▀▀▜\n" +
                                    "▌ " + (resource.getLetter() == null ? "▒" : resource.getLetter()) + " ▐\n" +
                                    "▙▄▄▄▟"
                            , resource.getColor(), col + position.getCliColOffset(), row + position.getCliRowOffset()).print());

        }
    }

    public static void printPlaceableCard(PlaceableCard card, int col, int row) {
        printBaseCard(card, col, row);

        if(card.getStateCardPoints() > 0){
            if(card instanceof GoldenCard && card.getStateCardGoldenBuffResource() != Resource.EMPTY){
                new CLIString(
                        "▛▀▀▀▀▀▀▀▜\n" +
                              "▌ " + card.getStateCardPoints() + " x " + card.getGoldenBuffResource().getLetter() + " ▐\n" +
                              "▙▄▄▄▄▄▄▄▟"
                        , AnsiColor.YELLOW, col + 6, row).print();
            }
            else {
                new CLIString(
                        "▛▀▀▀▀▀▀▀▜\n" +
                              "▌   " + card.getStateCardPoints() + "   ▐\n" +
                              "▙▄▄▄▄▄▄▄▟"
                        , AnsiColor.YELLOW, col + 6, row).print();
            }
        }

        if(card instanceof GoldenCard){
            new CLIString(
                    """
                            ▛▀▀▀▀▀▀▀▜
                            ▌       ▐
                            ▙▄▄▄▄▄▄▄▟
                            """
                    , AnsiColor.YELLOW, col + 6, row + 5).print();

            CLICommand.setPosition(col + 8, row + 6);

            for(Resource resource : card.getActivationCost().keySet())
                AnsiString.print("▌".repeat(card.getActivationCost().get(resource)), resource.getColor());
        }

        CLICommand.restoreCursorPosition();
    }

    public static void displayQuestCard(Quest quest, int col, int row){
        CLIBox.draw(col, row, 16, 5, AnsiColor.WHITE);
        CLIBox.draw(col + 13, row, 3, 5, String.valueOf(quest.getPoints()), AnsiColor.YELLOW, AnsiColor.YELLOW, AnsiFormat.BOLD);

        if (quest.getId() == 94){
            int i = 0;
            for (Resource resource : ((QuestCounter)quest).getActivationCost().keySet()){
                CLICommand.setPosition(col + 5, row + (i + 1));
                System.out.print("1 x ");
                AnsiString.print(resource.getLetter(), AnsiColor.YELLOW);
                i ++;
            }
        }
        else if(quest instanceof QuestCounter){
            CLICommand.setPosition(col + 5, row + 2);
            System.out.print("3 x ");
            AnsiString.print("█",
                    ((QuestCounter)quest).getActivationCost().keySet()
                            .stream()
                            .filter(r -> ((QuestCounter) quest).getActivationCost().get(r) > 0)
                            .findFirst()
                            .get()
                            .getColor());
        }
        else if(quest instanceof TopLeft){
            new CLIString(
                    """
                          ▂▂
                          ▀▀
                          """,
                    ((TopLeft) quest).getToeColor().getAnsi(), col + 5, row + 1).print();
            new CLIString(
                        """
                        ▇▇
                        ▇▇
                        """,
                    ((TopLeft) quest).getBodyColor().getAnsi(), col + 7, row + 2).print();
        }
        else if(quest instanceof TopRight){
            new CLIString(
                    """
                          ▂▂
                          ▀▀
                          """,
                    ((TopRight) quest).getToeColor().getAnsi(), col + 7, row + 1).print();
            new CLIString(
                    """
                    ▇▇
                    ▇▇
                    """,
                    ((TopRight) quest).getBodyColor().getAnsi(), col + 5, row + 2).print();

        }
        else if(quest instanceof BottomLeft){
            new CLIString(
                    """
                          ▂▂
                          ▀▀
                          """,
                    ((BottomLeft) quest).getToeColor().getAnsi(), col + 5, row + 2).print();
            new CLIString(
                    """
                    ▇▇
                    ▇▇
                    """,
                    ((BottomLeft) quest).getBodyColor().getAnsi(), col + 7, row + 1).print();
        }
        else if(quest instanceof BottomRight){
            new CLIString(
                    """
                          ▂▂
                          ▀▀
                          """,
                    ((BottomRight) quest).getToeColor().getAnsi(), col + 7, row + 2).print();
            new CLIString(
                    """
                    ▇▇
                    ▇▇
                    """,
                    ((BottomRight) quest).getBodyColor().getAnsi(), col + 5, row + 1).print();
        }
        else if(quest instanceof TopLeftDiagonal){
            new CLIString(
                    """
                          ▁▁
                          ▀▀▅▅▁▁
                            ▔▔▀▀
                          """,
                    ((TopLeftDiagonal) quest).getColor().getAnsi(), col + 3, row + 1).print();
        }
        else if(quest instanceof BottomLeftDiagonal){
            new CLIString(
                    """
                                ▁▁
                            ▁▁▅▅▀▀
                            ▀▀▔▔
                          """,
                    ((BottomLeftDiagonal) quest).getColor().getAnsi(), col + 3, row + 1).print();
        }

        CLICommand.restoreCursorPosition();
    }

    public static void displayStartingCard(StartingCard card, int col, int row){
        printBaseCard(card, col, row);

        if(!card.getStateCardResources().isEmpty()){
            new CLIString(
                    """
                    ▛▀▀▀▜
                    ▌   ▐
                    ▌   ▐
                    ▌   ▐
                    ▙▄▄▄▟
                    """
                    , AnsiColor.YELLOW, col + 8, row + 2).print();

            for (Resource resource : card.getStateCardResources())
                new CLIString(
                        "▆",
                        resource.getColor(),
                        col + 10,
                        row + 3 + card.getStateCardResources().indexOf(resource)).print();

        }

        CLICommand.restoreCursorPosition();
    }

    public static void displayCorner(Corner corner, int col, int row){
        if(col >= 5 && row >= 6 && col <= 113 && row <= 30){
            switch (corner.getPosition()){
                case Position.TOPLEFT:
                    new CLIString(
                            """
                             ▒▒
                            ▒▒▒
                            """
                            , corner.getCardColor().getAnsi(), col, row).print();
                    break;

                case Position.TOPRIGHT:
                    new CLIString(
                            """
                            ▒▒
                            ▒▒▒
                            """
                            , corner.getCardColor().getAnsi(), col - 2, row).print();
                    break;

                case Position.BOTTOMLEFT:
                    new CLIString(
                            """
                            ▒▒▒
                             ▒▒
                            """
                            , corner.getCardColor().getAnsi(), col, row - 1).print();
                    break;

                case Position.BOTTOMRIGHT:
                    new CLIString(
                            """
                            ▒▒▒
                            ▒▒
                            """
                            , corner.getCardColor().getAnsi(), col - 2, row - 1).print();
                    break;
            }

            if(!corner.isAvailable())
                new CLIString("▒", corner.getCardColor().getAnsi(), col, row).print();
            else{
                if(corner.getResource().getLetter() == null)
                    new CLIString("█", corner.getResource().getColor(), col, row).print();
                else
                    new CLIString(corner.getResource().getLetter(), corner.getResource().getColor(), col, row).print();
            }
        }
    }
}
