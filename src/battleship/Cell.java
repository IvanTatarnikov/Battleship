package battleship;

class Cell {
    static final Cell FOG = new Cell('~', null);
    static final Cell MISS = new Cell('M', null);
    static final Cell HIT = new Cell('H', null);

    private final char value;
    private final Fleet ship;

    Cell(char value, Fleet ship) {
        this.value = value;
        this.ship = ship;
    }

    void print() {
        System.out.printf("%c ", value);
    }

    boolean isHit() {
        return false;
    }

    Fleet getShip() {
        return ship;
    }
}

class Ship extends Cell {
    Ship(Fleet ship) {
        super('O', ship);
    }

    @Override
    boolean isHit() {
        return true;
    }
}
