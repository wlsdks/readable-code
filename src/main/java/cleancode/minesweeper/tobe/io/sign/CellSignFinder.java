package cleancode.minesweeper.tobe.io.sign;

import cleancode.minesweeper.tobe.cell.CellSnapshot;

import java.util.List;

public class CellSignFinder {

    // 한번 만들어두고 재사용 가능하니 상수로 선언
    public static final List<CellSignProvidable> CELL_SIGN_PROVIDERS = List.of(
            new EmptyCellSignProvider(),
            new FlagCellSignProvider(),
            new LandMineCellSignProvider(),
            new NumberCellSignProvider(),
            new UncheckedCellSignProvider()
    );

    // CellSnapshot을 받아서 CellSignProvidable을 찾아서 해당 CellSignProvidable의 provide 메서드를 호출하여 셀의 표시를 반환
    public String findCellSignFrom(CellSnapshot snapshot) {
        return CELL_SIGN_PROVIDERS.stream()
                .filter(provider -> provider.supports(snapshot))
                .findFirst()
                .map(provider -> provider.provide(snapshot))
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));
    }

}
