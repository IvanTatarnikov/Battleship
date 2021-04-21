package battleship;

import java.util.Scanner;

class Point {
    private static final Scanner scanner = new Scanner(System.in);

    int x;
    int y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static Point read() {
        String input = scanner.next();

        Point point = new Point();
        point.y = input.charAt(0) - 'A';
        point.x = Integer.parseInt(input.substring(1)) - 1;

        return point;
    }
}
