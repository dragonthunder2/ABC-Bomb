package uet.oop.bomberman.ui;

import uet.oop.bomberman.Game;

import java.awt.*;

public class Menu {

    public Rectangle playButton = new Rectangle(Game.WIDTH  / 2 + 120, 150, 100, 50);
    public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font fnt0 = new Font("arial", Font.BOLD, 80);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("BOMBERMAN", Game.WIDTH / 2, 100);

        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x + 20, playButton.y + 35);
        g2d.draw(playButton);
        g.drawString("Help", helpButton.x + 20, helpButton.y + 35);
        g2d.draw(helpButton);
        g.drawString("Quit", quitButton.x + 20, quitButton.y + 35);
        g2d.draw(quitButton);
    }
}
