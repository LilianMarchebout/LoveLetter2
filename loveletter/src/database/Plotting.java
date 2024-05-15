package loveletter.src.database;

import loveletter.src.model.Game;
import loveletter.src.utils.PrintDisabler;

import java.util.ArrayList;
import java.util.List;

import static loveletter.src.database.DatabaseAnalysis.getPourcentBoard;
import static loveletter.src.database.DatabaseQueries.createAllTables;
import static loveletter.src.database.DatabaseQueries.deleteAllTables;

public class Plotting {

    public static void main(String[] args) throws Exception {
        deleteAllTables();
        createAllTables();
        int nbPlayers = 2;
        List<Double> pourcents = new ArrayList<>();
        for (int i = 0; i< 5; i++) {
            PrintDisabler.enablePrint();
            System.out.println(i);
            PrintDisabler.disablePrint();
            Game game = new Game(nbPlayers);
            game.launch();
            Object[][] results = getPourcentBoard();
            for (Object[] result : results) {
                if (((String)result[0]).equals("Gard")) {
                    pourcents.add((double)result[1]);
                }
            }
        }

        double[] y = convertToArray(pourcents);

        Figure fig = new Figure(y);
        fig.show();
        fig.save("Test");
    }

    private static double[] convertToArray(List<Double> list) {
        return list.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
    }


}

