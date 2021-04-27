package battleship;

public class GameField {

    static final int rowCount = 10;
    static final int columnCount = 10;
    static final int shipCount = 5;

    private final Cell[][] field = new Cell[rowCount][columnCount];
    private final Fleet[] fleet = new Fleet[shipCount];

    public GameField() {
        fillField();
    }

    private void fillField() {
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                field[i][j] = Cell.FOG;
            }
        }
    }

    public void setCell(Point point, Cell cell) {
        field[point.y][point.x] = cell;
    }

    public void print() {
        printHeader();
        printField();
    }
    private void printHeader() {
        System.out.print("  ");
        for (int columnId = 1; columnId <= columnCount; ++columnId) {
            System.out.printf("%d ", columnId);
        }
        System.out.println();
    }
    private void printField() {
        char rowId = 'A';
        for (var row : field) {
            System.out.printf("%c ", rowId++);
            for (Cell cell : row) {
                cell.print();
            }
            System.out.println();
        }
    }

    public boolean allShipsSunk() {
        boolean result = true;
        for (Fleet ship : fleet) {
            result = result && ship.isKilled();
        }
        return result;
    }

    public void arrangeShips() {
        int index = 0;
        for (Fleet ship : Fleet.values()) {
            fleet[index++] = ship;

            System.out.printf("Enter the coordinates of the %s:%n", ship.getFullName());

            while (true) {
                try {
                    ship.build();
                    placeShip(ship);
                    print();
                    break;
                } catch (IllegalArgumentException error) {
                    System.out.printf("%s Try again:%n", error.getMessage());
                }
            }
        }
    }

    private void placeShip(Fleet ship) {
        for (Fleet currentShip : fleet) {
            if (currentShip == ship || currentShip == null) {
                continue;
            }
            if (ship.isIntersected(currentShip.begin, currentShip.end)) {
                String error = "Error! Ships are intersected!";
                throw new IllegalArgumentException(error);
            }
            if (ship.isTooClose(currentShip)) {
                String error = "Error! You placed it too close to another one!";
                throw new IllegalArgumentException(error);
            }
        }

        final int beginX = Math.min(ship.begin.x, ship.end.x);
        final int endX = Math.max(ship.begin.x, ship.end.x);
        final int beginY = Math.min(ship.begin.y, ship.end.y);
        final int endY = Math.max(ship.begin.y, ship.end.y);

        for (int i = beginY; i <= endY; ++i) {
            for (int j = beginX; j <= endX; ++j) {
                field[i][j] = new Ship(ship);
            }
        }
    }

    public Cell takeShot(Point point) {
        if (point.x >= columnCount || point.y >= rowCount) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return null;
        }

        Cell cell = field[point.y][point.x];
        String message;

        if (cell.isHit()) {
            Fleet ship = cell.getShip();
            ship.hitCount += 1;
            message = ship.isKilled() ? "You sank a ship!" : "You hit a ship!";
            cell = Cell.HIT;
        } else {
            message = "You missed!";
            cell = Cell.MISS;
        }

        field[point.y][point.x] = cell;

        System.out.println(message);

        return cell;
    }
}
