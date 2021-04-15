import java.awt.event.KeyEvent;

/**
 * Класс отвечает за отображение и управление игрой
 */
public class Game {
    private Room room;
    private Mouse mouse;
    private Snake snake;
    private int score;

    private KeyboardObserver monitor;

    public Game() {
        monitor = new KeyboardObserver();
        newGame();
    }

    public int getScore() {
        return score;
    }

    public void addScore(int value) {
        score += value;
    }

    public void run() {
        Thread thread = new Thread(() -> monitor.run(room.getWidth(), room.getHeight()));
        thread.start();


        while (snake.isAlive()) {

            if (monitor.hasKeyEvents()) {
                KeyEvent event = monitor.getEventFromTop();
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> snake.setDirection(SnakeDirection.LEFT);
                    case KeyEvent.VK_RIGHT -> snake.setDirection(SnakeDirection.RIGHT);
                    case KeyEvent.VK_UP -> snake.setDirection(SnakeDirection.UP);
                    case KeyEvent.VK_DOWN -> snake.setDirection(SnakeDirection.DOWN);
                }
            }
            snake.move(room, this::isEatMouse);
            print();
            sleep();
        }
        System.out.println("Game Over!");
    }

    public boolean isEatMouse(int x, int y) {
        if (x == mouse.getX() && y == mouse.getY()) {
            addScore(20);
            createMouse();
            return true;
        }
        return false;
    }

    public void createMouse() {
        int x = (int) (1 + Math.random() * room.getWidth());
        int y = (int) (1 + Math.random() * room.getHeight());
        mouse = new Mouse(x, y);
    }


    /**
     * Выводим на экран текущее состояние игры
     */
    public void print() {
        if (monitor.frame != null) {
            Layer layer = new Layer(room, mouse, snake, getScore());
            monitor.frame.setContentPane(layer);
            monitor.frame.setVisible(true);
        }
    }

    public void sleep() {
        try {
            int level = snake.getSections().size();
            int delayStep = 20;
            int initialDelay = 500;
            int delay = level < 20 ? (initialDelay - delayStep * level) : 100;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }

    public void newGame() {
        this.room = new Room(20, 20);
        this.snake = new Snake(10, 10);
        createMouse();
    }


}

