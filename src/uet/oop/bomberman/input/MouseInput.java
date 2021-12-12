package uet.oop.bomberman.input;

import uet.oop.bomberman.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        //Play Button
        if (Game.State == Game.STATE.MENU) {
            Game.audioPlay("select.wav", false);
            if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
                if (mx >= 150 && my <= 200) {
                    Game.State = Game.STATE.GAME;
                }
            }
            //Help Button
            if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
                if (mx >= 250 && my <= 350 && my >= 220) {
                    Game.State = Game.STATE.HELP;
                }
            }

            //Quit Button
            if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
                if (mx >= 250 && my <= 400 && my >= 350) {
                    System.exit(1);
                }
            }
        }

        if (Game.State == Game.STATE.HELP) {
            if (mx <= Game.WIDTH && my <= Game.HEIGHT + 20) {
                Game.audioPlay("select.wav", false);
                Game.State = Game.STATE.MENU;
            }
        }

        if (Game.State == Game.STATE.GAMEOVER) {
            if (mx >= 500 && mx <= 700 && my <= 700 && my >= 650) {
                Game.audioPlay("select.wav", false);
                Game._paused = false;
                Game.getBoard().loadLevel(1);
                Game.State = Game.STATE.GAME;
            } else if (mx >= 500 && mx <= 700 && my <= 800 && my >= 720) {
                Game.audioPlay("select.wav", false);
                Game.getBoard().loadLevel(1);
                Game.State = Game.STATE.MENU;
            }

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
