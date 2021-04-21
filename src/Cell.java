package battleship;

enum Cell {
    FOG('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M');

    private final char value;

    Cell(char value) {
        this.value = value;
    }

    void print() {
        System.out.printf("%c ", value);
    }
}