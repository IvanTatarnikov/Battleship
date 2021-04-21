package battleship;

public class GameField {

    static final int rowCount = 10;
    static final int columnCount = 10;

    private final Cell[][] field = new Cell[rowCount][columnCount];
    private final Ship[][] fieldWithShips = new Ship[rowCount][columnCount];

    private final Ship[] ships = {
            new AircraftCarrier(),
            new Battleship(),
            new Submarine(),
            new Cruiser(),
            new Destroyer()
    };

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
        for (Ship ship : ships) {
            result = result && ship.isKilled();
        }
        return result;
    }

    public void arrangeShips() {
        for (Ship ship : ships) {
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

    private void placeShip(Ship ship) {
        for (Ship currentShip : ships) {
            if (currentShip == ship || currentShip.begin == null) {
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
                field[i][j] = Cell.SHIP;
                fieldWithShips[i][j] = ship;
            }
        }
    }

    public Cell takeShot(Point point) {
        if (point.x >= columnCount || point.y >= rowCount) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return Cell.FOG;
        }

        Cell cell = field[point.y][point.x];
        Ship ship = fieldWithShips[point.y][point.x];
        String message;

        if (cell == Cell.SHIP || cell == Cell.HIT) {
            cell = Cell.HIT;
            ship.hitCount += 1;
            if (ship.isKilled()) {
                message = "You sank a ship!";
            } else {
                message = "You hit a ship!";
            }
        } else {
            message = "You missed!";
            cell = Cell.MISS;
        }

        field[point.y][point.x] = cell;

        System.out.println(message);
        //print();

        return cell;
    }
}
