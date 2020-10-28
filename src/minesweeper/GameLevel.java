package minesweeper;

/**
 * @author luoyuequan
 */

public enum GameLevel {
    /**
     * 简单模式
     */
    SIMPLE(10, 9, 9, 1, "初级"),
    /**
     * 中级模式
     */
    SENIOR(40, 16, 16, 2, "中级"),
    /**
     * 高级模式
     */
    DIFFICULT(99, 16, 30, 3, "高级");
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
     * 游戏等级值
     */
    private final int level;
    /**
     * 游戏等级文本内容
     */
    private final String levelString;

    GameLevel(int rayNumber, int rowNumber, int colNumber, int level, String levelString) {
        this.rayNumber = rayNumber;
        this.colNumber = colNumber;
        this.rowNumber = rowNumber;
        this.level = level;
        this.levelString = levelString;
    }

    /**
     * 获取游戏难度模式
     *
     * @param level 游戏等级
     * @return 游戏难度模式
     */
    public static GameLevel getGameLevel(int level) {
        GameLevel[] gameLevels = GameLevel.values();
        for (GameLevel gameLevel : gameLevels) {
            if (gameLevel.level == level) {
                return gameLevel;
            }
        }
        return GameLevel.SIMPLE;
    }

    /**
     * 获取游戏难度模式选择提示语句
     *
     * @return 游戏难度选择提示语句
     */
    public static String getGameStartNote() {
        StringBuilder levelSelectNote = new StringBuilder("请选择游戏难度级别,");
        for (GameLevel gameLevel : GameLevel.values()) {
            levelSelectNote.append(gameLevel.getLevelString()).append("=").append(gameLevel.getLevel()).append(",");
        }
        levelSelectNote.append("请输入你的选择:");
        return levelSelectNote.toString();
    }

    public int getRayNumber() {
        return rayNumber;
    }

    public int getColNumber() {
        return colNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public String getLevelString() {
        return levelString;
    }

    public int getLevel() {
        return level;
    }
}
