import java.util.ArrayList;

public class Board {
    private final int SIZE = 3;
    private ArrayList<ArrayList<XOSign>> gameBoard;

    Board() {
        gameBoard = new ArrayList<>();

        for(int i = 0; i < SIZE; i++) {
            gameBoard.add(new ArrayList<>());
            for(int j = 0; j < SIZE; j++) {
                gameBoard.get(i).add(XOSign.NOTHING);
            }
        }

    }

    public int getSIZE() {
        return SIZE;
    }

    public void printGameBoard() {
        System.out.println(makeVisualizationOfBoard());
    }

    public void move(Position position, XOSign sign) throws Exception {
        if(positionIsInBoard(position)) {
            if(positionIsNotEmpty(position)) {
                throw new Exception("This position have some sign");
            }
            else {
                gameBoard.get(position.getPositionX()).set(position.getPositionY(), sign);
            }
        }
        else {
            throw new Exception("This position is invalid");
        }
    }

    public void revertMove(Position position) throws Exception {
        if(positionIsInBoard(position)) {
                gameBoard.get(position.getPositionX()).set(position.getPositionY(), XOSign.NOTHING);
        }
        else {
            throw new Exception("This position is invalid");
        }
    }

    public XOSign signAtPosition(Position position) throws Exception {
        if(positionIsInBoard(position)) {
            return gameBoard.get(position.getPositionX()).get(position.getPositionY());
        }
        else {
            throw new Exception("This position is invalid");
        }
    }

    public boolean endOfGame() {
        boolean tie = true;

        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(gameBoard.get(i).get(j) == XOSign.NOTHING){
                    tie = false;
                }
            }
        }

        if(tie){
            System.out.println("Tie");
            return true;
        }

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(gameBoard.get(i).get(j) == XOSign.X || gameBoard.get(i).get(j) == XOSign.O) {
                    if(checkWinForPosition(new Position(i, j))) {
                        System.out.println("The winner is: "+ gameBoard.get(i).get(j));
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public XOSign whoWin() {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(gameBoard.get(i).get(j) == XOSign.X || gameBoard.get(i).get(j) == XOSign.O) {
                    if(checkWinForPosition(new Position(i, j))) {
                        return gameBoard.get(i).get(j);
                    }
                }
            }
        }

        return XOSign.NOTHING;
    }

    private boolean positionIsInBoard(Position position) {
        int posX = position.getPositionX();
        int posY = position.getPositionY();

        return posX >= 0 && posX < SIZE && posY >= 0 && posY < SIZE;
    }

    private boolean positionIsNotEmpty(Position position) {
        return gameBoard.get(position.getPositionX()).get(position.getPositionY()) != XOSign.NOTHING;
    }

    private String makeVisualizationOfBoard() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("    ");

        for(int j = 0; j < SIZE; j++) {
            stringBuilder.append("  ");
            stringBuilder.append(j + 1);
            stringBuilder.append(' ');
        }

        stringBuilder.append('\n');

        stringBuilder.append("     ");

        for(int j = 0; j < SIZE; j++) {
            stringBuilder.append("____");
        }

        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append('\n');

        for(int i = 0; i < SIZE; i++) {
            stringBuilder.append("  ");
            stringBuilder.append(i + 1);
            stringBuilder.append(' ');

            for(int j = 0; j < SIZE; j++) {
                stringBuilder.append("| ");
                XOSign sign = gameBoard.get(i).get(j);

                stringBuilder.append(XOSign.getCharSign(sign));
                stringBuilder.append(' ');
            }

            stringBuilder.append("|\n");
            stringBuilder.append("    ");

            for(int j = 0; j < SIZE; j++) {
                stringBuilder.append("|___");
            }

            stringBuilder.append("|\n");
        }

        return stringBuilder.toString();
    }

    private boolean checkWinForPosition(Position position) {
        return  checkDirectionForWinAtPosition(position,Direction.EAST) ||
                checkDirectionForWinAtPosition(position,Direction.SOUTH) ||
                checkDirectionForWinAtPosition(position,Direction.SE) ||
                checkDirectionForWinAtPosition(position,Direction.SW);
    }

    private boolean checkDirectionForWinAtPosition(Position position, Direction direction) {

        Position current = new Position(position.getPositionX(), position.getPositionY());

        for(int i = 0; i < SIZE; i++){
            try {
                if(positionNotOfThanSign(current, signAtPosition(position))) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            current.movePosition(direction);
        }

        return true;
    }

    private boolean positionNotOfThanSign(Position position, XOSign sign) {
        if(!positionIsInBoard(position)){
            return true;
        }
        return gameBoard.get(position.getPositionX()).get(position.getPositionY()) != sign;
    }
}