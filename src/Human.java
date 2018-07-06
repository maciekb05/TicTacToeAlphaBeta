import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Human implements Player {
    private final XOSign sign;
    private final char charSign;

    public Human(XOSign sign) {
        this.sign = sign;
        charSign = XOSign.getCharSign(sign);
    }

    @Override
    public Board move(Board board) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Ruch gracza " + charSign + ", wybierz pozycje:");
        try {
            String position = reader.readLine();
            String[] positionSplitted = position.split(" ");
            Integer posX = Integer.parseInt(positionSplitted[0]);
            Integer posY = Integer.parseInt(positionSplitted[1]);
            board.move(new Position(posX - 1, posY - 1), sign);
            return board;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return board;
    }

}