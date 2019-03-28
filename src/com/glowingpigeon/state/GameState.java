public interface GameState {
    public void windowEvent();
    public void mouseEvent();
    public void keyEvent();

    public void update();
    public void render();
}