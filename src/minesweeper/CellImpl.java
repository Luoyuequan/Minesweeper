package minesweeper;

public class CellImpl extends BaseCell {
    /**
     * 单元格初始化
     *
     * @param x x轴坐标
     * @param y y轴坐标
     */
    CellImpl(int x, int y) {
        super(x, y);
    }

    /**
     * 点击单元格
     * 检测是否为雷
     *
     * @return 检测结果
     */
    @Override
    public boolean click() {
        if (this.getIsRay()) {
            this.updateContent(this.getNumberStrList().get(this.getNumberStrList().size() - 1));
            return true;
        } else {
//            标记为已检查标识，同时单元格总数-1
            if (this.getCheckSign() != 1) {
                this.onCheck();
            }
//            周围雷数为0时，才开始向外扩展
            if (this.getAroundRayNumber() == 0) {
                for (BaseCell cell : this.getAroundCell()) {
//                    已被检查的单元格，取消检查，防止重复检查
                    if (cell.getCheckSign() == 1) {
                        continue;
                    }
                    cell.click();
                }
            }
            this.updateContent(this.getNumberStrList().get(this.getAroundRayNumber()));
            return false;
        }
    }

    /**
     * 更改显示的内容
     *
     * @param content 显示的新内容
     */
    @Override
    public void updateContent(String content) {
        this.setShowContent(content);
    }
}
