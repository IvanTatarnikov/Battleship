package battleship;

enum Fleet {
    AIRCRAFT("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    String name;
    int size;
    Point begin;
    Point end;
    int hitCount;

    Fleet(String name, int size) {
        this.name = name;
        this.size = size;
    }

    Point getBegin() {
        return begin;
    }

    Point getEnd() {
        return end;
    }

    public boolean isKilled() {
        return hitCount >= size;
    }

    protected String getFullName() {
        return String.format("%s (%d cells)", this.name, this.size);
    }

    public void build() {
        begin = Point.read();
        end = Point.read();
        check();
    }

    private void check() {
        if (begin.x != end.x && begin.y != end.y) {
            String error = "Error! Wrong ship location!";
            throw new IllegalArgumentException(error);
        }

        int currentSize = Math.abs(begin.x - end.x + begin.y - end.y) + 1;
        if (currentSize != this.size) {
            String error = String.format("Error! Wrong length of the %s!", this.name);
            throw new IllegalArgumentException(error);
        }
    }

    public boolean isTooClose(Fleet otherShip) {
        boolean result = false;

        for (int shift : new int[]{-1, 1}) {
            Point begin = new Point(otherShip.getBegin().x, otherShip.getBegin().y);
            Point end = new Point(otherShip.getEnd().x, otherShip.getEnd().y);
            begin.x += shift;
            end.x += shift;
            result = result || isIntersected(begin, end);
        }

        for (int shift : new int[]{-1, 1}) {
            Point begin = new Point(otherShip.getBegin().x, otherShip.getBegin().y);
            Point end = new Point(otherShip.getEnd().x, otherShip.getEnd().y);
            begin.y += shift;
            end.y += shift;
            result = result || isIntersected(begin, end);
        }

        return result;
    }

    public boolean isIntersected(Point begin, Point end) {
        Point a = this.begin;
        Point b = this.end;
        Point c = begin;
        Point d = end;

        return intersect (a.x, b.x, c.x, d.x)
                && intersect (a.y, b.y, c.y, d.y)
                && area(a,b,c) * area(a,b,d) <= 0
                && area(c,d,a) * area(c,d,b) <= 0;
    }

    private int area(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    private boolean intersect(int a, int b, int c, int d) {
        if (a > b) {
            int k = a;
            a = b;
            b = k;
        }
        if (c > d) {
            int k = c;
            c = d;
            d = k;
        }
        return Math.max(a,c) <= Math.min(b,d);
    }
}
