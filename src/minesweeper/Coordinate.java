package minesweeper;

import java.util.Objects;

/**
 * 采用笛卡尔坐标
 *
 * @author Administrator
 */
public class Coordinate implements Comparable<Coordinate> {
    private int x;
    private int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * x轴坐标减法
     *
     * @param x x轴差值
     * @return 新的坐标
     */
    Coordinate xSubtract(int x) {
        return new Coordinate(this.x - x, this.y);
    }

    /**
     * y轴坐标减法
     *
     * @param y y轴差值
     * @return 新的坐标
     */
    Coordinate ySubtract(int y) {
        return new Coordinate(this.x, this.y - y);
    }

    /**
     * 对角坐标减法
     *
     * @param x x轴差值
     * @param y y轴差值
     * @return 新的坐标
     */
    Coordinate subtract(int x, int y) {
        return new Coordinate(this.x - x, this.y - y);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Coordinate o) {
        if (this.x == o.x && this.y == o.y) {
            return 0;
        } else if (this.x == o.x) {
            return this.y > o.y ? 1 : -1;
        } else {
            return this.x > o.x ? 1 : -1;
        }
    }
}
