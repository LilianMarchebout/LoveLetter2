package loveletter.src.database;


import static loveletter.src.database.DatabaseController.*;

public class DatabaseAnalysis {

    /**
     * Donne le classement des types de player ayant le plus gagner
     */
    public static void betterBoardPlayer() {
        String sql
                = "SELECT p.type, COUNT(*) AS count " +
                "FROM Round r JOIN Player p ON r.winner_id = p.player_id "+
                "GROUP BY p.type " +
                "ORDER BY COUNT(*) DESC ";
        Object[][] results = findAll(sql);
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
    public static Object[][] cardMoreUseBoard() {
        String sql
                = "SELECT card_played, COUNT(*) AS count " +
                "FROM Turn "+
                "GROUP BY card_played " +
                "ORDER BY COUNT(*) DESC ";
        Object[][] results = findAll(sql);
        for (Object[] line : results) {
            for (Object result : line) {
                System.out.print(result + " ");
            }
            System.out.println();
        }
        return results;
    }

    /**
     * Donne la carte qui finit le plus de manche
     */
    public static Object[][] cardFinishedRound() {
        //Avoir les cartes qui ont fini leur tour
        String finishRoundSql
                = "SELECT card_played, turn_num " +
                "FROM Turn " +
                "WHERE (round_id, turn_num) IN ( " +
                "SELECT round_id, MAX(turn_num) AS max_turn_num " +
                " FROM Turn " +
                "GROUP BY round_id " +
                ")";
        String sql
                = String.format("SELECT card_played, COUNT(*) as count " +
                "FROM (%s) " +
                "GROUP BY card_played " +
                ";", finishRoundSql);
        Object[][] results = findAll(sql);
//        for (Object[] line : results) {
//            for (Object result : line) {
//                System.out.print(result + " ");
//            }
//            System.out.println();
//        }
        return results;
    }


    /**
     * Donne la carte qui finit le plus de manches en gagnant
     */
    public static Object[][] cardFinishedRoundWinner() {
        //Avoir les cartes qui ont fini leur tour
        String finishRoundSql
                = "SELECT card_played, turn_num " +
                "FROM Turn JOIN Round ON Round.winner_id = Turn.player_id AND Round.round_id = Turn.round_id " +
                "WHERE (Turn.round_id, turn_num) IN ( " +
                "SELECT Turn.round_id, MAX(turn_num) AS max_turn_num " +
                "FROM Turn " +
                "GROUP BY Turn.round_id " +
                ")";
        String sql
                = String.format("SELECT card_played, COUNT(*) as count " +
                "FROM (%s) " +
                "GROUP BY card_played " +
                ";", finishRoundSql);
        Object[][] results = findAll(sql);
//        for (Object[] line : results) {
//            for (Object result : line) {
//                System.out.print(result + " ");
//            }
//            System.out.println();
//        }
        return results;
    }

    /**
     * Permet d'avoir le pourcentage de manches gagnées par cartes
     * @return
     */
    public static Object[][] getPourcentBoard() {
        Object[][] roundWinner = cardFinishedRoundWinner();
        Object[][] roundFinished = cardFinishedRound();
        Object[][] pourcents = new Object[roundWinner.length][2]; // Taille déterminée par le nombre de lignes dans roundWinner
        int idxPourcent = 0;

        for (Object[] lineWinner : roundWinner) {
            String winner = (String) lineWinner[0];
            int nbWinnerRound = (int) lineWinner[1];

            for (Object[] lineFinished : roundFinished) {
                String round = (String) lineFinished[0];
                int nbRoundFinished = (int) lineFinished[1];

                if (winner.equals(round)) {
                    pourcents[idxPourcent][0] = winner;
                    pourcents[idxPourcent][1] = (double) nbWinnerRound / nbRoundFinished;
                    idxPourcent++;
                }
            }
        }
        for (Object[] line : pourcents) {
            for (Object result : line) {
                System.out.print(result + " ");
            }
            System.out.println();
        }
        return pourcents;
    }

    /**
     * Permet d'avoir le nombre de lignes d'une table
     * @return
     */
    public static int getNbLine(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        return (int) find(sql)[0];
    }

    public static Object[] getNbGamePlayed(String type) {
        String sql = "SELECT Player.type, COUNT(*) " +
                "FROM Game JOIN Player ON Game.game_id = Player.game_id " +
                "WHERE Player.type = ? " +
                "GROUP BY Player.type;";
        Object[] result = find(sql, type);
        return result;
    }

    public static Object[] getNbGameWin(String type) {
        String sql = "SELECT Player.type, COUNT(*) " +
                "FROM Game JOIN Player ON (Game.game_id = Player.game_id AND Game.winner_id = Player.player_id) " +
                "WHERE Player.type = ? " +
                "GROUP BY Player.type;";
        Object[] result = find(sql, type);
        return result;
    }

    public static void getWinningRate(String type) {
        int nbGamePlayed = (int)getNbGamePlayed(type)[1];
        int nbGameWin = (int)getNbGameWin(type)[1];
        System.out.println("Win rate : " + ((double)nbGameWin/(double)nbGamePlayed)*100 + "%");
    }

    public static void displayGame(int game_id) {
        String sql = "SELECT Turn.round_id, p.name, a.name, Turn.turn_num, Turn.card_played, w.name " +
                "FROM Round JOIN Turn ON Round.round_id = Turn.round_id " +
                "JOIN Player p ON p.game_id = ? AND Turn.player_id = p.player_id " +
                "JOIN Player a ON p.game_id = ? AND Turn.attacked_id = a.player_id " +
                "JOIN Player w ON p.game_id = ? AND Round.winner_id = w.player_id " +
                "WHERE Round.game_id = ? " +
                "ORDER BY Round.round_num, Turn.turn_num ;";

        Object[][] results = findAll(sql, game_id,game_id,game_id, game_id);
        int nbRound = (int) results[0][0];
        for (Object[] line : results) {
            int round_id = (int) line[0];

            String playerName = "";
            if (line[1] != null)
                playerName = (String) line[1];

            String attackedName = "";
            if (line[2] != null)
                attackedName = (String) line[2];

            int turn_num = (int) line[3];
            String card_played = (String) line[4];

            String winnerName = "";
            if (line[5] != null)
                winnerName = (String) line[5];

            if (round_id != nbRound) {
                nbRound = round_id;
                System.out.println("Winner : " + winnerName);
                System.out.println();
                System.out.println("Round " + nbRound);
            }

            if (!attackedName.isEmpty())
                System.out.printf("Turn %d : %s attacked %s with %s%n", turn_num, playerName, attackedName, card_played);
            else
                System.out.printf("Turn %d : %s uses %s%n", turn_num, playerName, card_played);
        }
    }
}
