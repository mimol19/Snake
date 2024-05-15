package org.snake;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<Point> body;
    private Direction direction;
    private Point ending;

    public Snake() {
        ending = new Point();
        direction = Direction.D;
        body = new ArrayList<>();

        body.add(new Point(2,5));
        body.add(new Point(2,4));
        body.add(new Point(2,3));
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        for (Point point : getTail()) {
            g.fillRect(point.x*Board.SIZE, point.y* Board.SIZE, Board.SIZE, Board.SIZE);
        }

        g.setColor(new Color(55, 63, 227));
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

    public boolean isCollision() {
        Point head = getHead();

        for (Point point : getTail()) {
            if (head.equals(point)) {
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
            MainFrame.score.setText("Score: " + body.size());
            return true;
        }

        return false;
    }
}