package cleancode.minesweeper.tobe.position;

import cleancode.minesweeper.tobe.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {

    private final List<CellPosition> positions;

    private CellPositions(List<CellPosition> positions) {
        this.positions = positions;
    }

    public static CellPositions of(List<CellPosition> positions) {
        return new CellPositions(positions);
    }

    // board를 받아서 CellPosition을 생성하는 메서드
    public static CellPositions from(Cell[][] board) {
        List<CellPosition> cellPositions = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositions.add(cellPosition);
            }
        }

        return of(cellPositions);
    }

    public List<CellPosition> extractRandomPositions(int count) {
        ArrayList<CellPosition> cellPositions = new ArrayList<>(positions);

        // cellPositions를 섞는다.
        Collections.shuffle(cellPositions);

        // count만큼의 CellPosition을 잘라서 반환
        return cellPositions.subList(0, count);
    }

    public List<CellPosition> subtract(List<CellPosition> positionsListToSubtract) {
        List<CellPosition> cellPositions = new ArrayList<>(positions);
        CellPositions positionsToSubtract = CellPositions.of(positionsListToSubtract);

        return cellPositions.stream()
                .filter(positionsToSubtract::doesNotContain)
                .toList();
    }

    private boolean doesNotContain(CellPosition position) {
        return !positions.contains(position);
    }

    // getter
    // 외부에서 지금 이 객체에 접근해서 수정하지 못하게 하기 위해 새로운 ArrayList를 생성해서 반환
    public List<CellPosition> getPositions() {
        return new ArrayList<>(positions);
    }

}
