package org.openjfx;

public class Singleton {
    private static Singleton instance = null;
    private boolean checkboxState;
    private int fullscreenState;

    // Constructor for the Singleton class that initializes the checkboxState and fullscreenState
    private Singleton() {
        checkboxState = false;
        fullscreenState = 0;
    }

    // Method for getting the instance of the Singleton class
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // Method for setting the checkbox state
    public void setCheckboxState(boolean state) {
        this.checkboxState = state;
    }

    // Method for getting the checkbox state
    public boolean getCheckboxState() {
        return this.checkboxState;
    }

    // Method for setting the fullscreen state
    public void setFullscreenState(int state) {
        this.fullscreenState = state;
    }

    // Method for getting the fullscreen state
    public int getFullscreenState() {
        return this.fullscreenState;
    }
}
