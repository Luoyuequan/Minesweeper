package minesweeper;

import java.io.IOException;
import java.util.*;

/**
 * @author Administrator
 */
public class Minesweeper {
    /**
     * 雷数量
     */
    private final int rayNumber;
    /**
     * 棋盘列数
     */
    private final int colNumber;
    /**
     * 棋盘行数
     */
    private final int rowNumber;
    /**
     * 保存生成的雷坐标
     */
    private List<Coordinate> rayBoard;
    /**
     * 棋盘单元格集合
     */
    private Map<Coordinate, BaseCell> cells = new TreeMap<>();
    /**
     * 棋盘内剩余的单元格总数
     */
    private int totalCell;
    /**
     * 游戏胜利标识
     */
    private boolean win = false;
    /**
     * 游戏失败标识
     */
    private boolean lose = false;

    /**
     * 初始化
     *
     * @param level 游戏难度级别
     */
    private Minesweeper(int level) {
        this.rayNumber = GameLevel.getGameLevel(level).getRayNumber();
        this.colNumber = GameLevel.getGameLevel(level).getColNumber();
        this.rowNumber = GameLevel.getGameLevel(level).getRowNumber();
//        棋盘单元格总数初始化为 行数*列数
        this.totalCell = this.rowNumber * this.colNumber;
    }

    /**
     * 生产单元格
     */
    void productCell() {
//        单元格坐标集合
        List<Coordinate> listCellCoordinate = new ArrayList<>();
        for (int x = 0; x < this.getRowNumber(); x++) {
            for (int y = 0; y < this.getColNumber(); y++) {
                Coordinate coordinate = new Coordinate(x, y);
                this.getCells().put(coordinate, new CellImpl(x, y));
                listCellCoordinate.add(coordinate);
            }
        }
//        开始生产雷
        this.productRay(listCellCoordinate);
    }

    /**
     * 生产雷
     * 将单元格坐标集合打乱
     * 从头开始取出指定数量的雷坐标
     * 将雷布置到对应坐标的单元格
     *
     * @param listCellCoordinate 单元格坐标集合
     */
    private void productRay(List<Coordinate> listCellCoordinate) {
        Collections.shuffle(listCellCoordinate, new Random());
        this.rayBoard = listCellCoordinate.subList(0, this.getRayNumber());
        for (Coordinate c : this.getRayBoard()) {
            this.getCells().get(c).setIsRay();
        }
//        每个单元格开始加载周围单元格
        for (Coordinate c : listCellCoordinate) {
            this.getCells().get(c).initAroundCell();
        }
        Collections.sort(this.getRayBoard());
    }

    private static Minesweeper minesweeper;

    static Minesweeper getInstance(int level) {
        minesweeper = new Minesweeper(level);
        return minesweeper;
    }

    /**
     * 绘制棋盘内容
     */
    void draw() {
        // 清屏命令
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.print("x|y\t");
        for (int i = 0; i < this.getColNumber(); i++) {
            System.out.print((i + 1) + "|");
        }
        for (Map.Entry<Coordinate, BaseCell> entry : this.getCells().entrySet()) {
//            每行开始时，打印行号
            if (entry.getKey().getY() == 0) {
                System.out.print("\n" + (entry.getKey().getX() + 1) + "\t");
            }
            System.out.print(entry.getValue().getShowContent());
        }
        System.out.println();
    }

    /**
     * 获取单元格总数，用于游戏胜利判断
     *
     * @return 当前棋盘剩余的单元格总数
     */
    int getTotalCell() {
        return totalCell;
    }

    /**
     * 检查单元格时，不为雷时，单元格总数-1
     */
    void subtractTotalCell() {
        --this.totalCell;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    int getRayNumber() {
        return rayNumber;
    }

    private int getColNumber() {
        return colNumber;
    }

    private int getRowNumber() {
        return rowNumber;
    }

    static Minesweeper getMinesweeper() {
        return minesweeper;
    }

    public static void setMinesweeper(Minesweeper minesweeper) {
        Minesweeper.minesweeper = minesweeper;
    }

    Map<Coordinate, BaseCell> getCells() {
        return cells;
    }

    private List<Coordinate> getRayBoard() {
        return rayBoard;
    }


}
