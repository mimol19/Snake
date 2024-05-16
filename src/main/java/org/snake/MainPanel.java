package org.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel {

    private Snake snake1 = new Snake();
    private Snake snake2 = new Snake();
    private Apple apple = new Apple();
    private boolean gameOver = false;

    public MainPanel() {
        setPreferredSize(new Dimension(Board.MAX_X, Board.MAX_Y));
        MainTimer timer = new MainTimer();
        timer.start();

        MainFrame.score.setText("Snake I Score: " + snake1.getSize() + " Snake II Score:" + snake2.getSize());

        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
    }



    @Override
    protected void paintComponent(Graphics g) {
        Board.draw(g);
        snake1.draw(g,Color.GREEN,Color.BLUE);
        snake2.draw(g,Color.MAGENTA,Color.YELLOW);
        apple.draw(g);
    }

    private class MainTimer extends Timer {
        public static final int DELAY = 100;

        public MainTimer() {
            super(DELAY, e -> {
                if (!gameOver) {
                    snake1.move();
                    snake2.move();

                    if (snake1.eatApple(apple) || snake2.eatApple(apple)) {
                        MainFrame.score.setText("Snake I Score: " + (snake1.getSize()-3) +
                                " Snake II Score:" + snake2.getSize());
                        apple = new Apple();
                    }

                    if (snake1.isCollisionWithHimself() || snake2.isCollisionWithHimself() ||
                            snake1.isCollisionWithOtherSnake(snake2) || snake2.isCollisionWithOtherSnake(snake1)) {
                        gameOver = true;
                        MainFrame.score.setText("Game Over - Snake I Score: " + snake1.getSize() +
                                " Snake II Score: "+snake2.getSize());
                    }
                    repaint();
                }
            });
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snake2.getDirection() != Direction.R) {
                        snake2.setDirection(Direction.L);
                    }
                    break;
                case KeyEvent.VK_A:
                    if (snake1.getDirection() != Direction.R) {
                        snake1.setDirection(Direction.L);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake2.getDirection() != Direction.L) {
                        snake2.setDirection(Direction.R);
                    }
                    break;
                case KeyEvent.VK_D:
                    if (snake1.getDirection() != Direction.L) {
                        snake1.setDirection(Direction.R);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake2.getDirection() != Direction.U) {
                        snake2.setDirection(Direction.D);
                    }
                    break;
                case KeyEvent.VK_S:
                    if (snake1.getDirection() != Direction.U) {
                        snake1.setDirection(Direction.D);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snake2.getDirection() != Direction.D) {
                        snake2.setDirection(Direction.U);
                    }
                    break;
                case KeyEvent.VK_W:
                    if (snake1.getDirection() != Direction.D) {
                        snake1.setDirection(Direction.U);
                    }
                    break;
            }
        }
    }
}