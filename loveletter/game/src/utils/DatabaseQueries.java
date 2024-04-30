package game.src.utils;

import game.src.database.DatabaseController;
import game.src.model.cards.Card;
import game.src.model.players.Player;

public class DatabaseQueries {

    public static void createAllTables() {
        createGameTable();
        createRoundTable();
        createTurnTable();
        createPlayerTable();
    }

    public static  void deleteAllTables() {
        DatabaseController.deleteTable("Game");
        DatabaseController.deleteTable("Round");
        DatabaseController.deleteTable("Turn");
        DatabaseController.deleteTable("Player");
    }

    public static void createGameTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Game (" +
                "game_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "winner_id INTEGER," +
                "nb_rounds INTEGER," +
                "nb_players INTEGER," +
                "FOREIGN KEY(winner_id) REFERENCES Player(player_id)" +
                ")";
        DatabaseController.request(sql);
    }

    public static void createRoundTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Round (" +
                "round_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "game_id INTEGER NOT NULL," +
                "winner_id INTEGER," +
                "round_num INTEGER NOT NULL," +
                "nb_turns INTEGER," +
                "FOREIGN KEY(game_id) REFERENCES game(game_id)" +
                "FOREIGN KEY(winner_id) REFERENCES Player(player_id)" +
                ")";
        DatabaseController.request(sql);
    }
    public static void createTurnTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Turn (" +
                "turn_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "round_id INTEGER NOT NULL," +
                "player_id INTEGER NOT NULL," +
                "attacked_id INTEGER," +
                "turn_num INTEGER NOT NULL," +
                "card_played TEXT NOT NULL," +
                "FOREIGN KEY(round_id) REFERENCES Round(round_id)" +
                "FOREIGN KEY(player_id) REFERENCES Player(player_id)" +
                "FOREIGN KEY(attacked_id) REFERENCES Player(player_id)" +
                ")";
        DatabaseController.request(sql);
    }


    public static void createPlayerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Player (" +
                "player_id INTEGER PRIMARY KEY," +
                "game_id INTEGER," +
                "name TEXT," +
                "type TEXT," +
                "FOREIGN KEY(game_id) REFERENCES Game(game_id)" +
                ")";
        DatabaseController.request(sql);
    }

    /**
     * @param nbPlayers
     * @return ID generated
     */
    public static int addGame(int nbPlayers) {
        String sql = "INSERT INTO Game (nb_players) " +
                "VALUES ( ? )";

        return DatabaseController.insertID(sql, nbPlayers);
    }

    public static void modifyGame(int game_id, Player winner, int nbRounds) {
        String sql = "UPDATE Game " +
                "SET winner_id = " +
                    "(SELECT player_id FROM Player WHERE game_id = ? AND name = ? ), " +
                "nb_rounds = ? " +
                "WHERE game_id = ?";
        DatabaseController.request(sql, game_id, winner.toString(), nbRounds, game_id);
    }

    public static void addPlayer(int game_id, Player player) {
        String sql = "INSERT INTO Player (game_id, name, type) VALUES ( ?, ?, ? )";
        DatabaseController.request(sql, game_id, player.toString(), player.getClass().getSimpleName());
    }

    public static void addRound(int game_id, int nbRound) {
        String sql = "INSERT INTO Round (game_id, round_num) VALUES ( ?, ? )";
        DatabaseController.request(sql, game_id, nbRound);
    }

    public static void modifyRound(int game_id, Player roundWinner, int nbRounds, int nbTurns) {
        String name = roundWinner.toString();
        String sql = "UPDATE Round " +
                "SET nb_turns = ?," +
                "winner_id = (SELECT player_id FROM Player WHERE name = ?) " +
                "WHERE game_id = ? AND round_num = ?";
        DatabaseController.request(sql, nbTurns, name, game_id, nbRounds);
    }

    public static void addTurn(int game_id, int nbRound, int nbTurn, Player player, Player attacked, Card card) {
        String playerName = player.toString();
        String cardName = card.toString();
        if (attacked != null) {
            String attackedName = attacked.toString();
            String sql = "INSERT INTO Turn (round_id, player_id, attacked_id, turn_num, card_played) VALUES (" +
                    "(SELECT round_id FROM Round WHERE game_id = ? AND round_num = ?), " +
                    "(SELECT player_id FROM Player WHERE game_id = ? AND name = ? ), " +
                    "(SELECT player_id FROM Player WHERE game_id = ? AND name = ? ), " +
                    "?, " +
                    "? )";
            DatabaseController.request(sql, game_id, nbRound, game_id, playerName, game_id, attackedName, nbTurn, cardName);
        } else {
            String sql = "INSERT INTO Turn (round_id, player_id, turn_num, card_played) VALUES (" +
                    "(SELECT round_id FROM Round WHERE game_id = ? AND round_num = ? ), " +
                    "(SELECT player_id FROM Player WHERE game_id = ? AND name = ? ), " +
                    "?, " +
                    "? )";
            DatabaseController.request(sql, game_id, nbRound, game_id, playerName, nbTurn, cardName);
        }
    }
}
