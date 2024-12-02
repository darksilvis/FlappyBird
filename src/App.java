import javax.swing.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        PlayerData playerData = new PlayerData();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Lütfen kullanıcı adınızı girin: ");
        String username = scanner.nextLine();

        while (!playerData.addPlayer(username)) {
            System.out.println("Bu kullanıcı adı zaten mevcut. Lütfen başka bir kullanıcı adı girin: ");
            username = scanner.nextLine();
        }

        playerData.setCurrentPlayer(username);
        System.out.println("Hoş geldiniz, " + username + "!");
        System.out.println("Şu ana kadar " + playerData.getGamesPlayed(username) + " kez oynadınız ve en yüksek skorunuz: " +
                playerData.getHighScore(username));

        // `username`'i final bir değişkene atıyoruz.
        final String finalUsername = username;

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flappy Bird");
            FlappyBird game = new FlappyBird(playerData);

            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Toplam oynama sayınız: " + playerData.getGamesPlayed(finalUsername));
            System.out.println("En yüksek skorunuz: " + playerData.getHighScore(finalUsername));

            int rank = playerData.getCurrentPlayerRank();
            System.out.println("Şu anki sıralamanız: " + rank);
            System.out.println("Liderlik Tablosu:");
            for (String entry : playerData.getLeaderboard()) {
                System.out.println(entry);
            }
        }));
    }
}
