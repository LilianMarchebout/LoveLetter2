package game.src.database;

import game.src.controller.GameController;

public class DatabaseQueries {

    public static void createGameTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Game (" +
                "game_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "winner_id INTEGER NOT NULL," +
                "nbTurns INTEGER," +
                "nbPlayers INTEGER," +
                "FOREIGN KEY(winner_id) REFERENCES Player(player_id)" +
                ")";
        DatabaseController.request(sql);
    }

    public static void createTurnTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Turn (" +
                "turn_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "game_id INTEGER NOT NULL," +
                "FOREIGN KEY(game_id) REFERENCES Game(game_id)" +
                ")";
        DatabaseController.request(sql);
    }

    public static void createTurnPlayerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TurnPlayer (" +
                "turn_id INTEGER," +
                "player_id INTEGER," +
                "cardPlayer TEXT NOT NULL," +
                "FOREIGN KEY(turn_id) REFERENCES Turn(turn_id)" +
                "FOREIGN KEY(player_id) REFERENCES Player(player_id)" +
                "PRIMARY KEY (turn_id, player_id)" +
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

    public static void createAllTables() {
        createGameTable();
        createTurnTable();
        createPlayerTable();
        createTurnPlayerTable();
    }

    public static  void deleteAllTables() {
        DatabaseController.deleteTable("Game");
        DatabaseController.deleteTable("Turn");
        DatabaseController.deleteTable("Player");
        DatabaseController.deleteTable("TurnPlayer");
    }
}
