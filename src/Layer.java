import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Класс отвечает за отрисовку игры на экране
 */
public class Layer extends JPanel {
private Room room;
private Snake snake;
private Mouse mouse;
private int score;
public  Layer(Room room, Mouse mouse, Snake snake, int score ){
    this.room = room;
    this.mouse = mouse;
    this.snake =snake ;
    this.score = score;
}
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.drawString("Score: " + score, room.getHeight() * 10, 16);
        for (int i = 20; i <= room.getHeight() * 20 + 20; i += 20) {
            g.drawLine(20, i, room.getHeight() * 20 + 20, i);
        }
        for (int i = 20; i <= room.getWidth() * 20 + 20; i += 20) {
            g.drawLine(i, 20, i, room.getWidth() * 20 + 20);
        }
        g.setColor(Color.RED);
        g.fillRect(mouse.getX() * 20, mouse.getY() * 20, 20, 20);
        g.setColor(Color.DARK_GRAY);
        g.drawRect(mouse.getX() * 20, mouse.getY() * 20, 20, 20);
        g.setColor( Color.blue );


        List<SnakeSection> sections = snake.getSections();
        for (int i = 0; i < sections.size(); i++) {
            g.setColor(Color.GREEN);
            g.fillRect(sections.get(i).getX() * 20, sections.get(i).getY() * 20, 20, 20);
            g.setColor(Color.DARK_GRAY);
            g.drawRect(sections.get(i).getX() * 20, sections.get(i).getY() * 20, 20, 20);

            if (i == 0) {
                g.fillOval(sections.get(i).getX() * 20 + 4, sections.get(i).getY() * 20 + 5, 5, 5);
                g.fillOval(sections.get(i).getX() * 20 + 11, sections.get(i).getY() * 20 + 5, 5, 5);
                g.setColor(Color.DARK_GRAY);
                g.fillArc(sections.get(i).getX()*20+4, sections.get(i).getY()*20+10,
                        12, 6, 180, 180);
            }
        }
    }

}
