package cleancode.minesweeper.tobe;

public class BoardIndexConverter {

    private static final char BASE_CHAR_OR_COL = 'a';

    public int getSelectedColIndex(String cellInput) {
        char cellInputCol = cellInput.charAt(0);
        return convertColFrom(cellInputCol);
    }

    public int getSelectedRowIndex(String cellInput) {
        String cellInputRow = cellInput.substring(1);
        return convertRowFrom(cellInputRow);
    }

    private int convertColFrom(char cellInputCol) {
        int colIndex = cellInputCol - BASE_CHAR_OR_COL;
        if (colIndex < 0) {
            throw new GameException("잘못된 입력입니다.");
        }

        return colIndex;
    }

    private int convertRowFrom(String cellInputRow) { // "10"
        int rowIndex = Integer.parseInt(cellInputRow) - 1;
        if (rowIndex < 0) {
            throw new GameException("잘못된 입력입니다.");
        }

        return rowIndex;
    }

}
