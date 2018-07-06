public class Position {
    private Integer positionX;
    private Integer positionY;

    public Position(Integer x, Integer y) {
        positionX = x;
        positionY = y;
    }

    public void movePosition(Direction direction) {
        switch(direction) {
            case NORTH:
                positionX--;
                break;
            case SOUTH:
                positionX++;
                break;
            case EAST:
                positionY++;
                break;
            case WEST:
                positionY--;
                break;
            case NE:
                positionX--;
                positionY++;
                break;
            case NW:
                positionX--;
                positionY--;
                break;
            case SE:
                positionX++;
                positionY++;
                break;
            case SW:
                positionX++;
                positionY--;
                break;
        }
    }

    public Integer getPositionX() {
        return positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }
}