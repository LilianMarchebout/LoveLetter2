package game.src.utils;

import game.src.database.DatabaseController;

public class DatabaseAnalysis {

    public static void betterBoardPlayer() {
        String sql
                = "SELECT p.type, COUNT(*) AS count " +
                "FROM Round r JOIN Player p ON r.winner_id = p.player_id "+
                "GROUP BY p.type " +
                "ORDER BY COUNT(*) DESC ";
        Object[][] results = DatabaseController.findAll(sql);
        for (Object[] line : results) {
            for (Object result : line) {
                System.out.print(result + " ");
            }
            System.out.println();
        }
    }

    /**
     * Donne le classement des cartes les plus utilisées
     */
    public static void cardMoreUseBoard() {
        String sql
                = "SELECT card_played, COUNT(*) AS count " +
                "FROM Turn "+
                "GROUP BY card_played " +
                "ORDER BY COUNT(*) DESC ";
        Object[][] results = DatabaseController.findAll(sql);
        for (Object[] line : results) {
            for (Object result : line) {
                System.out.print(result + " ");
            }
            System.out.println();
        }
    }
}
