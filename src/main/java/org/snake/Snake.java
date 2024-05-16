package org.snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static boolean instanceExists = false;
    private List<Point> body;
    private Direction direction;
    private Point ending;

    public Snake() {
        if (instanceExists) {
            ending = new Point();
            direction = Direction.D;
            body = new ArrayList<>();

            body.add(new Point(5, 5));
            body.add(new Point(5, 4));
            body.add(new Point(5, 3));
        } else {

            ending = new Point();
            direction = Direction.D;
            body = new ArrayList<>();

            body.add(new Point(2, 5));
            body.add(new Point(2, 4));
            body.add(new Point(2, 3));

            instanceExists = true;
        }
    }

    public void draw(Graphics g,Color headColor, Color tailColor) {
        g.setColor(tailColor);
        for (Point point : getTail()) {
            g.fillRect(point.x*Board.SIZE, point.y* Board.SIZE, Board.SIZE, Board.SIZE);
        }

        g.setColor(headColor);
        g.fillOval(getHead().x*Board.SIZE, getHead().y* Board.SIZE, Board.SIZE, Board.SIZE);
    }

    private Point getHead() {
        return body.get(0);
    }

    public void setHead(Point head) {
        this.body.set(0,head);
    }

    private List<Point> getTail() {
        return body.subList(1, body.size());
    }
    private List<Point> getBody() {
        return body;
    }

    public void move() {
        Point head = getHead();
        ending.setLocation(body.get(body.size()-1));
        for (int i = body.size()-1; i>0; i--) {
            body.get(i).setLocation(body.get(i-1));
        }

        if (head.x < 0 ){
            setHead(new Point(Board.FIELD_X,head.y));
        }
        if (head.x >= Board.FIELD_X ){
            setHead(new Point(0,head.y));
        }
        if (head.y < 0 ){
            setHead(new Point(head.x,Board.FIELD_Y));
        }
        if (head.y >= Board.FIELD_Y ){
            setHead(new Point(head.x,0));
        }

        switch (direction) {
            case D -> getHead().y++;
            case U -> getHead().y--;
            case R -> getHead().x++;
            case L -> getHead().x--;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isCollisionWithHimself() {
        Point head1 = getHead();

        for (Point point : getTail()) {
            if (head1.equals(point)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollisionWithOtherSnake(Snake snake) {
        Point head1 = getHead();

        for (Point point : snake.getBody()) {
            if (head1.equals(point)) {
                return true;
            }
        }

        return false;
    }

    public int getSize() {
        return body.size();
    }

    public boolean eatApple(Apple apple) {

        if (getHead().equals(apple)) {
            body.add(new Point(ending));
            return true;
        }

        return false;
    }
}