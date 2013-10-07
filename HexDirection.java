public enum HexDirection {
    LEFT (-1, 0),
    RIGHT (1, 0),
    UPLEFT (-1, -1),
    UPRIGHT (0, -1),
    DOWNLEFT (-1, 1),
    DOWNRIGHT (0, 1);

    private final int x;
    private final int y;

    HexDirection (int x, int y) {
        this.x = x;
        this.y = y;
    }

public int x() {
return this.x;
}

public int y() {
return this.y;
}
}