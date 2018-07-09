import java.util.HashSet;

public class Computer implements Player {
    private final XOSign sign;
    private final char charSign;

    public Computer(XOSign sign) {
        this.sign = sign;
        charSign = XOSign.getCharSign(sign);
    }

    @Override
    public Board move(Board board) {

        System.out.println("Ruch gracza " + charSign + ", wybierz pozycje:");

        minimax(board, true, true, Integer.MIN_VALUE, Integer.MAX_VALUE);

        return board;
    }

    private HashSet<Position> findPositionsToMove(Board board) {
        int size = board.getSIZE();
        HashSet<Position> positions = new HashSet<>();

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Position current = new Position(i, j);
                try {
                    if (board.signAtPosition(current) == XOSign.NOTHING) {
                        positions.add(new Position(current.getPositionX(), current.getPositionY()));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return positions;
    }

    private int heuristicFunction(Board board) {
        XOSign winner = board.whoWin();
        if(winner == XOSign.NOTHING) {
            return 0;
        }
        else if(winner == sign) {
            return 10;
        }
        else {
            return -10;
        }
    }

    private int minimax(Board board, Boolean maxPlayer, Boolean isOrigin, int alpha, int beta) {
        HashSet<Position> moves = findPositionsToMove(board);
        if(moves.size() == 0 || board.whoWin() != XOSign.NOTHING) {
            return heuristicFunction(board);
        }
        if(maxPlayer) {
            int bestValue = Integer.MIN_VALUE;
            Position bestPosition = null;

            for(Position position : moves) {
                try {
                    board.move(position, sign);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int v = minimax(board, false, false, alpha, beta);
                alpha = Math.max(alpha, v);

                try {
                    board.revertMove(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(isOrigin && v > bestValue) {
                    System.out.println(v);
                    bestPosition = position;
                }

                bestValue = Math.max(bestValue, v);

                if(alpha >= beta) {
                    break;
                }
            }
            if(isOrigin) {
                try {
                    board.move(bestPosition, sign);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return bestValue;
        }
        else {
            int bestValue = Integer.MAX_VALUE;
            for(Position position : moves) {
                try {
                    board.move(position, opponentSign());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                int v = minimax(board, true, false, alpha, beta);
                beta = Math.min(beta,v);

                try {
                    board.revertMove(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                bestValue = Math.min(bestValue, v);

                if(alpha >= beta) {
                    break;
                }
            }
            return bestValue;
        }
    }

    private XOSign opponentSign() {
        if(sign == XOSign.X) {
            return XOSign.O;
        }
        if(sign == XOSign.O) {
            return XOSign.X;
        }
        return XOSign.NOTHING;
    }

}