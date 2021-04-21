package battleship;

public class Game {

    private static final Player firstPlayer = new Player("Player 1");
    private static final Player secondPlayer = new Player("Player 2");

    public static void run() {
        arrangeShips();
        startGame();
    }

    private static void arrangeShips() {
        firstPlayer.arrangeShips();
        secondPlayer.arrangeShips();
    }

    private static void startGame() {
        boolean first = true;
        while (!firstPlayer.allShipsSunk() && !secondPlayer.allShipsSunk()) {
            if (first) {
                firstPlayer.takeShot(secondPlayer);
            } else {
                secondPlayer.takeShot(firstPlayer);
            }
            first = !first;
        }
    }
}
