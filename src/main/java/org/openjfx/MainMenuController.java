package org.openjfx;

import javafx.scene.layout.AnchorPane;

public class MainMenuController {
    AnchorPane anchorPane;
    GameController gameController = new GameController();
    Enemy enemy = new Enemy();
    Bullets bullets = new Bullets(anchorPane);
    public void startGame() throws Exception {
        gameController.shipLocations.clear();
        enemy.enemyShips.clear();
        bullets.destroyedShips.clear();
        bullets.bulletsShotEnemy.clear();
        bullets.bulletsShotPlayer.clear();
        bullets.AllShots.clear();
        bullets.gameOver = false;
        bullets.shipDestroyed = false;
        gameController.gameStarted = 0;
        gameController.ships.clear();
        bullets.isHit = false;
        bullets.destroyedShipsEnemy.clear();
        bullets.destroyedShipsPlayer.clear();
        App.setRoot("Game");
    }
    public void setting() throws Exception {
        App.setRoot("Settings");
        gameController.cameFrom = "MainMenu";
    }
    public void AccountEdit() throws Exception {
        if (LoginController.account.equals("a, a, a")) {
            App.setRoot("AccountEditAdmin");
        }
        else
        {
            System.out.println(LoginController.account);
            App.setRoot("AccountEdit");
        }
        gameController.cameFrom = "MainMenu";
    }
    public void exitGame() {
        System.exit(0);
    }
}
