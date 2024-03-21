package org.openjfx;

public class Singleton {
    private static Singleton instance = null;
    private boolean checkboxState;
    private int fullscreenState;

    private Singleton() {
        checkboxState = false;
        fullscreenState = 0;
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void setCheckboxState(boolean state) {
        this.checkboxState = state;
    }

    public boolean getCheckboxState() {
        return this.checkboxState;
    }

    public void setFullscreenState(int state) {
        this.fullscreenState = state;
    }

    public int getFullscreenState() {
        return this.fullscreenState;
    }
}
