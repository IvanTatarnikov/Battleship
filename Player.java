package battleship;

import java.io.IOException;

public class Player {

    private String name;
    private final GameField myField;
    private final GameField opponentField;

    public Player(String name) {
        this.name = name;
        this.myField = new GameField();
        this.opponentField = new GameField();
    }

    public void arrangeShips() {
        System.out.printf("%s, place your ships on the game field%n", name);

        myField.print();
        myField.arrangeShips();

        promptEnterKey();
    }

    public void takeShot(Player player) {
        opponentField.print();
        System.out.println("---------------------");
        myField.print();
        System.out.printf("%s, it's your turn:%n", name);

        final Point point = Point.read();
        Cell cell = player.myField.takeShot(point);
        opponentField.setCell(point, cell);

        if (player.myField.allShipsSunk()) {
            System.out.println("You sank the last ship. You won. Congratulations!");
        } else {
            promptEnterKey();
        }
    }

    public boolean allShipsSunk() {
        return myField.allShipsSunk();
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
