public class Game {
    private Board board;
    private final Player playerX;
    private final Player playerO;

    public Game() {
        board = new Board();
        playerO = new Computer(XOSign.O);
        playerX = new Human(XOSign.X);
    }

    public void play() {
        board.printGameBoard();
        while(true) {

            board = playerX.move(board);
            if(board.endOfGame()){
                break;
            }
            board.printGameBoard();

            board = playerO.move(board);
            if(board.endOfGame()){
                break;
            }
            board.printGameBoard();
        }

        board.printGameBoard();
        System.out.println("Koniec");
    }
}