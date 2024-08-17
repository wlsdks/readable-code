package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

public class Minesweeper {

    // magic number, magic string
    private static final int BOARD_ROW_SIZE = 8;
    private static final int BOARD_COL_SIZE = 10;

    private final GameBoard gameBoard = new GameBoard(BOARD_ROW_SIZE, BOARD_COL_SIZE);
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public void run() {
        // 게임 시작 print
        consoleOutputHandler.showGameStartComments();

        // 게임판 초기화
        gameBoard.initializeGame();

        // 게임판 그리기 시작
        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);

                // 게임 종료 조건
                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameLosingComment();
                    break;
                }

                // 사용자 입력 받기
                String cellInput = getCellInputFromUser();
                String userActionInput = getUserActionInputFromUser();
                actOnCell(cellInput, userActionInput);
            } catch (AppException e) {
                consoleOutputHandler.printExceptionMessage(e);
            } catch (Exception e) {
                consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
    }

    // Early return 작업 (if-else 구조를 줄이기 위해)
    private void actOnCell(String cellInput, String userActionInput) {
        // 사용자 입력 처리
        int selectedColIndex = boardIndexConverter.getSelectedColIndex(cellInput, gameBoard.getColSize());
        int selectedRowIndex = boardIndexConverter.getSelectedRowIndex(cellInput, gameBoard.getRowSize());

        // 사용자가 선택한 셀에 대한 행위 처리
        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flag(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex, selectedColIndex);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
            checkIfGameIsOver();
            return;
        }

        throw new AppException("잘못된 번호를 선택하셨습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserChooseToOpenCell(String userActionInput) {
        return userActionInput.equals("1");
    }

    private boolean doesUserChooseToPlantFlag(String userActionInput) {
        return userActionInput.equals("2");
    }

    private String getUserActionInputFromUser() {
        consoleOutputHandler.printCommentForUserAction();
        return consoleInputHandler.getUserInput();
    }

    private String getCellInputFromUser() {
        consoleOutputHandler.printCommentForSelectingCell();
        return consoleInputHandler.getUserInput();
    }


    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }

}
