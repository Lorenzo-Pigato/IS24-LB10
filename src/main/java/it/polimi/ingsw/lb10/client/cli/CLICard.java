package it.polimi.ingsw.lb10.client.cli;


import it.polimi.ingsw.lb10.client.cli.ansi.AnsiColor;
import it.polimi.ingsw.lb10.client.cli.ansi.AnsiString;
import it.polimi.ingsw.lb10.server.model.Resource;
import it.polimi.ingsw.lb10.server.model.cards.GoldenCard;
import it.polimi.ingsw.lb10.server.model.cards.PlaceableCard;
import it.polimi.ingsw.lb10.server.model.cards.corners.Corner;
import it.polimi.ingsw.lb10.server.model.cards.corners.Position;

public abstract class CLICard {
    public static void printPlaceableCard(PlaceableCard card, int col, int row) {
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

        for(Position position : Position.values()) {
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
    }
}
