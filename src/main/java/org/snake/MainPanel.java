package org.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel {

    private Snake snake1 = new Snake();
    private Apple apple = new Apple();
    private boolean gameOver = false;

    public MainPanel() {
        setPreferredSize(new Dimension(Board.MAX_X, Board.MAX_Y));
        MainTimer timer = new MainTimer();
        timer.start();

        MainFrame.score.setText("Score: " + snake1.getSize());

        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Board.draw(g);
        snake1.draw(g);
        snake1.draw(g);
        apple.draw(g);
    }

    private class MainTimer extends Timer {
        public static final int DELAY = 100;

        public MainTimer() {
            super(DELAY, e -> {
                if (!gameOver) {
                    snake1.move();

                    if (snake1.eatApple(apple)) {
                        apple = new Apple();
                    }

                    if (snake1.isCollision()) {
                        gameOver = true;
                        MainFrame.score.setText("Game Over - Score: " + snake1.getSize());
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
                case KeyEvent.VK_A:
                    if (snake1.getDirection() != Direction.R) {
                        snake1.setDirection(Direction.L);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (snake1.getDirection() != Direction.L) {
                        snake1.setDirection(Direction.R);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (snake1.getDirection() != Direction.U) {
                        snake1.setDirection(Direction.D);
                    }
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (snake1.getDirection() != Direction.D) {
                        snake1.setDirection(Direction.U);
                    }
                    break;
            }
        }
    }
}