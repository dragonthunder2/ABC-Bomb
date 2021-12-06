package uet.oop.bomberman.input;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        /**
         *
         public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
         public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
         public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);
         */
        //Play Button
        if (Game.State == Game.STATE.MENU){
            if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
                if (mx >= 150 && my <= 200){
                    Game.State = Game.STATE.GAME;
                }
            }

            //Quit Button
            if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
                if (mx >= 250 && my <= 400 && my >= 350 ){
                    System.exit(1);
                }
            }
        }

        if (Game.State == Game.STATE.GAMEOVER) {
            if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220) {
                if (mx >= 150 && my <= 200) {
                    Game._paused = false;
                    Game.getBoard().loadLevel(1);
                    Game.State = Game.STATE.GAME;
                }
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
