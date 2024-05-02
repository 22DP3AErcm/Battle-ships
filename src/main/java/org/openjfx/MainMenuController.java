package org.openjfx;

public class MainMenuController {
    GameController gameController = new GameController();
    public void startGame() throws Exception {
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
