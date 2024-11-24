package cleancode.minesweeper.tobe.cell;

import java.util.Arrays;
import java.util.List;

// 일급 컬렉션
public class Cells {

    // 일급 컬렉션은 클래스 자체의 멤버 변수로 컬렉션 1개만을 가진다.
    private final List<Cell> cells;

    private Cells(List<Cell> cells) {
        this.cells = cells;
    }

    // factory method
    public static Cells of(List<Cell> cells) {
        return new Cells(cells);
    }

    // factory method
    public static Cells from(Cell[][] cells) {
        List<Cell> cellList = Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .toList();

        return of(cellList);
    }

    public boolean isAllChecked() {
        return cells.stream()
                .allMatch(Cell::isChecked);
    }

}
