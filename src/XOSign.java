public enum XOSign {
    X,
    O,
    NOTHING;

    public static char getCharSign(XOSign sign) {
        if(sign == XOSign.X) {
            return 'X';
        }
        else if(sign == XOSign.O) {
            return 'O';
        }
        else {
            return ' ';
        }
    }
}
