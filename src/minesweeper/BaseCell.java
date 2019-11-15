package minesweeper;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本单元格
 *
 * @author Administrator
 */
public abstract class BaseCell {
    /**
     * 上单元格
     */
    private BaseCell topCell;
    /**
     * 左单元格
     */
    private BaseCell leftCell;
    /**
     * 左上角单元格
     */
    private BaseCell leftTopCell;
    /**
     * 是否为雷标志
     * 0非雷
     * 1为雷
     */
    private byte isRay;
    /**
     * 单元格坐标
     */
    private Coordinate coordinate;
    /**
     * 检查标志
     * 0未检查
     * 1已检查
     */
    private byte checkSign;
    /**
     * 展示内容
     * 默认内容空
     */
    private String showContent;
    /**
     * 游戏主棋盘
     */
    private Minesweeper minesweeper = Minesweeper.getMinesweeper();
    /**
     * 周围单元格集合
     */
    private List<BaseCell> aroundCell = new ArrayList<>();
    /**
     * 当前单元格，周围的雷数
     */
    private int aroundRayNumber;
    /**
     * 周围雷数对应的标识符
     */
    private List<String> numberStrList = List.of("◎", "①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "●");

    /**
     * 单元格初始化
     */
    BaseCell(int x, int y) {
        this.coordinate = new Coordinate(x, y);
        this.showContent = "○";
    }

    /**
     * 加载当前单元格周围的单元格
     * 并同时计算当前单元格周围的雷数
     * 仅通过上单元格和左单元格和左上单元格
     */
    void initAroundCell() {
//        上单元格的坐标
        Coordinate topCoordinate = this.coordinate.xSubtract(1);
//        左单元格的坐标
        Coordinate leftCoordinate = this.coordinate.ySubtract(1);
//        左上单元格的坐标
        Coordinate leftTopCoordinate = this.coordinate.subtract(1, 1);
        this.setTopCell(this.minesweeper.getCells().get(topCoordinate));
        this.setLeftCell(this.minesweeper.getCells().get(leftCoordinate));
        this.setLeftTopCell(this.minesweeper.getCells().get(leftTopCoordinate));
//        将当前单元格与上单元格进行互相添加，互相计算雷数
        if (this.getTopCell() != null) {
            this.getTopCell().getAroundCell().add(this);
            this.getAroundCell().add(this.getTopCell());
            if (this.getIsRay()) {
                this.getTopCell().addAroundRayNumber();
            }
            if (this.getTopCell().getIsRay()) {
                this.addAroundRayNumber();
            }
        }
//        将当前单元格与左单元格进行互相添加，互相计算雷数
        if (this.getLeftCell() != null) {
            this.getLeftCell().getAroundCell().add(this);
            this.getAroundCell().add(this.getLeftCell());
            if (this.getIsRay()) {
                this.getLeftCell().addAroundRayNumber();
            }
            if (this.getLeftCell().getIsRay()) {
                this.addAroundRayNumber();
            }
        }
//        将当前单元格与左上单元格进行互相添加，互相计算雷数
        if (this.getLeftTopCell() != null) {
            this.getLeftTopCell().getAroundCell().add(this);
            this.getAroundCell().add(this.getLeftTopCell());
            if (this.getIsRay()) {
                this.getLeftTopCell().addAroundRayNumber();
            }
            if (this.getLeftTopCell().getIsRay()) {
                this.addAroundRayNumber();
            }
//          左上单元格存在时，左单元格与上单元格进行互相添加，互相计算雷数
            this.getLeftCell().getAroundCell().add(this.getTopCell());
            this.getTopCell().getAroundCell().add(this.getLeftCell());
            if (this.getLeftCell().getIsRay()) {
                this.getTopCell().addAroundRayNumber();
            }
            if (this.getTopCell().getIsRay()) {
                this.getLeftCell().addAroundRayNumber();
            }
        }
    }

    /**
     * 点击单元格
     * 检测是否为雷
     *
     * @return 检测结果
     */
    public abstract boolean click();

    /**
     * 更改显示的内容
     *
     * @param content 显示的新内容
     */
    public abstract void updateContent(String content);

    /**
     * 将未检查改为已检查
     */
    void onCheck() {
        this.checkSign = 1;
//        棋盘内剩余的单元格总数-1
        this.minesweeper.subtractTotalCell();
    }

    /**
     * 获取检查标志
     *
     * @return 1已检查，0未检查
     */
    byte getCheckSign() {
        return checkSign;
    }

    /**
     * 设置为雷，即1
     */
    void setIsRay() {
        this.isRay = (byte) 1;
    }

    /**
     * 检查当前单元格是否为雷
     *
     * @return 为雷true，非雷false
     */
    boolean getIsRay() {
        return this.isRay == 1;
    }

    /**
     * 当前单元格周围雷数+1
     */
    private void addAroundRayNumber() {
        ++this.aroundRayNumber;
    }

    /**
     * @return 获取单元格显示内容
     */
    String getShowContent() {
        return showContent;
    }

    /**
     * 改变单元格显示内容
     *
     * @param showContent 新内容
     */
    void setShowContent(String showContent) {
        this.showContent = showContent;
    }

    /**
     * 将单元格计算周围得出的雷数，转换为对应的字符串，用于显示
     *
     * @return 字符串列表
     */
    List<String> getNumberStrList() {
        return numberStrList;
    }

    private BaseCell getTopCell() {
        return topCell;
    }

    private void setTopCell(BaseCell topCell) {
        this.topCell = topCell;
    }

    private BaseCell getLeftCell() {
        return leftCell;
    }

    private void setLeftCell(BaseCell leftCell) {
        this.leftCell = leftCell;
    }

    private BaseCell getLeftTopCell() {
        return leftTopCell;
    }

    private void setLeftTopCell(BaseCell leftTopCell) {
        this.leftTopCell = leftTopCell;
    }

    List<BaseCell> getAroundCell() {
        return aroundCell;
    }

    int getAroundRayNumber() {
        return aroundRayNumber;
    }

    @Override
    public String toString() {
        return "BaseCell{" +
                "topCell=" + topCell +
                ", leftCell=" + leftCell +
                ", isRay=" + isRay +
                ", coordinate=" + coordinate +
                ", checkSign=" + checkSign +
                ", showContent=" + showContent +
                '}';
    }
}
