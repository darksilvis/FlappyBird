import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class PlayerData {
    private static final String FILE_NAME = "player_data.json";
    private Map<String, Player> players;
    private String currentPlayer;

    public PlayerData() {
        players = new HashMap<>();
        loadPlayerData();
    }

    public boolean addPlayer(String username) {
        if (players.containsKey(username)) {
            return false;
        }
        players.put(username, new Player(username));
        savePlayerData();
        return true;
    }

    public void setCurrentPlayer(String username) {
        currentPlayer = username;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void updateScore(int score) {
        if (currentPlayer == null) return;

        Player player = players.get(currentPlayer);
        if (player != null) {
            player.incrementGamesPlayed();
            player.updateHighScore(score);
            savePlayerData();
        }
    }

    public int getHighScore(String username) {
        Player player = players.get(username);
        return player != null ? player.getHighScore() : 0;
    }

    public int getGamesPlayed(String username) {
        Player player = players.get(username);
        return player != null ? player.getGamesPlayed() : 0;
    }

    public List<String> getLeaderboard() {
        List<Player> sortedPlayers = new ArrayList<>(players.values());
        sortedPlayers.sort(Comparator.comparingInt(Player::getHighScore).reversed());

        List<String> leaderboard = new ArrayList<>();
        for (Player player : sortedPlayers) {
            leaderboard.add(player.getUsername() + " - " + player.getHighScore());
        }
        return leaderboard;
    }

    public int getCurrentPlayerRank() {
        List<Player> sortedPlayers = new ArrayList<>(players.values());
        sortedPlayers.sort(Comparator.comparingInt(Player::getHighScore).reversed());

        for (int i = 0; i < sortedPlayers.size(); i++) {
            if (sortedPlayers.get(i).getUsername().equals(currentPlayer)) {
                return i + 1;
            }
        }
        return -1;
    }

    private void loadPlayerData() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            JSONParser parser = new JSONParser();
            JSONArray playerArray = (JSONArray) parser.parse(reader);

            for (Object obj : playerArray) {
                JSONObject playerJson = (JSONObject) obj;
                String username = (String) playerJson.get("username");
                int highScore = ((Long) playerJson.get("highScore")).intValue();
                int gamesPlayed = ((Long) playerJson.get("gamesPlayed")).intValue();

                Player player = new Player(username);
                player.setHighScore(highScore);
                player.setGamesPlayed(gamesPlayed);
                players.put(username, player);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Data file not found or corrupted. Starting fresh.");
        }
    }

    private void savePlayerData() {
        JSONArray playerArray = new JSONArray();

        for (Player player : players.values()) {
            JSONObject playerJson = new JSONObject();
            playerJson.put("username", player.getUsername());
            playerJson.put("highScore", player.getHighScore());
            playerJson.put("gamesPlayed", player.getGamesPlayed());
            playerArray.add(playerJson);
        }

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write(playerArray.toJSONString());
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static class Player {
        private String username;
        private int highScore;
        private int gamesPlayed;

        public Player(String username) {
            this.username = username;
            this.highScore = 0;
            this.gamesPlayed = 0;
        }

        public String getUsername() {
            return username;
        }

        public int getHighScore() {
            return highScore;
        }

        public int getGamesPlayed() {
            return gamesPlayed;
        }

        public void setHighScore(int highScore) {
            this.highScore = highScore;
        }

        public void setGamesPlayed(int gamesPlayed) {
            this.gamesPlayed = gamesPlayed;
        }

        public void incrementGamesPlayed() {
            this.gamesPlayed++;
        }

        public void updateHighScore(int score) {
            if (score > this.highScore) {
                this.highScore = score;
            }
        }
    }
}
