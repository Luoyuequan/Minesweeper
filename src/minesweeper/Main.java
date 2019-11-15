package minesweeper;


import java.util.Objects;
import java.util.Scanner;

/**
 * @author Administrator
 */
public class Main {
    public static void main(String[] args) {
        exit:
        while (true) {
            Scanner inputLevel = new Scanner(System.in);
            System.out.print(GameLevel.getGameStartNote());
            String levelContent = inputLevel.nextLine().trim();
            int level = Integer.valueOf(levelContent);
//            创建棋盘
            Minesweeper minesweeper = Minesweeper.getInstance(level);
//            开始生产单元格
            minesweeper.productCell();
//            开始游戏
            long startTime = System.currentTimeMillis();
            while (true) {
//                绘制棋盘内容
                minesweeper.draw();
//                棋盘内剩余的单元格皆为雷时，游戏胜利
                minesweeper.setWin(minesweeper.getTotalCell() <= minesweeper.getRayNumber());
                try {
                    if (minesweeper.isWin()) {
                        System.out.println("------You Win，Happy Every Day!!!------");
                        long endTime = System.currentTimeMillis();
                        System.out.println("本局游戏胜利所用时间:" + (endTime - startTime) / 1000 + "秒");
                    }
                    if (minesweeper.isLose()) {
//                            点击到雷, 退出游戏, over
                        System.out.println("------GAME OVER!!!------");
                    }
                    try {
                        Scanner input = new Scanner(System.in);
                        System.out.println("--------------游戏操作说明---------------" +
                                "\n输入1.0退出游戏，输入0.1重新开始一局游戏");
                        System.out.println("当前游戏难度=" + GameLevel.getGameLevel(level).getLevelString() + ",雷数=" + minesweeper.getRayNumber());
                        System.out.print("坐标格式为x.y,例如:1.1\n请输入坐标:");
                        String[] content = input.nextLine().trim().split("\\.", 2);
                        int x = Integer.valueOf(content[0]);
                        int y = Integer.valueOf(content[1]);
                        Coordinate coordinate = new Coordinate(x, y);
                        if (Objects.equals(coordinate, new Coordinate(1, 0))) {
//                            结束游戏
                            break exit;
                        }
                        if (coordinate.equals(new Coordinate(0, 1))) {
//                            重新开始一局游戏
                            break;
                        }
                        if (!minesweeper.isLose() && !minesweeper.isWin()) {
//                        真实坐标，相对于棋盘显示的坐标 - 1
                            Coordinate realCoordinate = coordinate.subtract(1, 1);
                            BaseCell cell = minesweeper.getCells().get(realCoordinate);
                            if (cell != null) {
//                            点击非雷false，为雷true
                                minesweeper.setLose(cell.click());
                                System.out.println("你输入的坐标为:" + coordinate);
                            } else {
                                System.out.println("请输入有效坐标");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("请输入符合要求的合法坐标,你输入的为:" + e.getMessage());
                    }
                } finally {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
