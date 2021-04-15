import java.awt.*;
import java.util.ArrayList;

// Класс Snake отвечает за движение змеи и ее состояние
public class Snake {

    // Направление движения змеи
    private SnakeDirection direction;
    // Состояние - жива змея или нет.
    private boolean isAlive;
    // Список кусочков змеи.
    private ArrayList<SnakeSection> sections;

    public Snake(int x, int y) {
        sections = new ArrayList<SnakeSection>();
        sections.add(new SnakeSection(x, y));
        sections.add(new SnakeSection(x, y));
        sections.add(new SnakeSection(x, y));
        this.direction = SnakeDirection.DOWN;
        this.isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }

    /**
     * Метод перемещает змею в соседнюю клетку.
     * Координаты клетки заданы относительно текущей головы с помощью переменных (dx, dy).
     */
    public void move(Room room, ICompareFunc isEaten) {
        if (!isAlive) return;
        // Создаем новую голову - новый "кусочек змеи".
        SnakeSection head = sections.get(0);
        Point point = GetPoint();
        head = new SnakeSection(head.getX() + point.x, head.getY() + point.y);

        // Проверяем - не вылезла ли голова за границу комнаты
        if (!checkBorders(head, room)) return;

        // Проверяем - не пересекает ли змея  саму себя
        if (!checkBody(head)) return;

        sections.add(0, head);           // Добавили новую голову

        if (!isEaten.isEqual(head.getX(), head.getY())) // если не съела
        {
            sections.remove(sections.size() - 1);   // удалили последний элемент с хвоста
        }
    }

    private Point GetPoint() {
        switch (direction) {
            case UP:
                return new Point(0, -1);
            case RIGHT:
                return new Point(1, 0);
            case DOWN:
                return new Point(0, 1);
            case LEFT:
                return new Point(-1, 0);
            default:
                return new Point(0, 0);
        }
    }

    /**
     * Метод проверяет - находится ли новая голова в пределах комнаты
     */
    private boolean checkBorders(SnakeSection head, Room room) {
        if ((head.getX() < 1
                || head.getX() >= room.getWidth() + 1)
                || head.getY() < 1
                || head.getY() >= room.getHeight() + 1) {
            isAlive = false;
        }
        return isAlive;
    }

    /**
     * Метод проверяет - не совпадает ли голова с каким-нибудь участком тела змеи.
     */
    private boolean checkBody(SnakeSection head) {
        if (sections.contains(head)) isAlive = false;
        return isAlive;
    }
}



