import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
abstract class GameObject {
    protected int x, y;
    protected boolean active = true;
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public abstract void update();
}
interface WeaponStrategy {
    boolean shoot(int x, int y, List<Bullet> bullets, long timestamp);
}
class SingleShot implements WeaponStrategy {
    private long fireRate = 500;
    private long lastShot = 0;
    public boolean shoot(int x, int y, List<Bullet> bullets, long timestamp) {
        if (timestamp - lastShot >= fireRate) {
            bullets.add(new Bullet(x, y - 1));
            lastShot = timestamp;
            return true;
        }
        return false;
    }
}
class SpreadShot implements WeaponStrategy {
    private long fireRate = 500;
    private long lastShot = 0;
    public boolean shoot(int x, int y, List<Bullet> bullets, long timestamp) {
        if (timestamp - lastShot >= fireRate) {
            bullets.add(new Bullet(x - 1, y - 1));
            bullets.add(new Bullet(x, y - 1));
            bullets.add(new Bullet(x + 1, y - 1));
            lastShot = timestamp;
            return true;
        }
        return false;
    }
}
class Player extends GameObject {
    private List<Bullet> bullets = new ArrayList<>();
    private WeaponStrategy weapon = new SingleShot();
    public Player(int x, int y) {
        super(x, y);
    }
    public void setWeapon(WeaponStrategy weapon) {
        this.weapon = weapon;
    }
    public List<Bullet> getBullets() {
        return bullets;
    }
    public void shoot(long timestamp) {
        weapon.shoot(x, y, bullets, timestamp);
    }
    public void update() {
    }
}
class Bullet extends GameObject {
    public Bullet(int x, int y) {
        super(x, y);
    }
    public void update() {
        y--;
        if (y < 0) active = false;
    }
}
class Enemy extends GameObject {
    public Enemy(int x, int y) {
        super(x, y);
    }
    public void update() {
        y++;
        if (y > 10) active = false;
    }
}
interface ScoreObserver {
    void update(int score);
}
class ScoreSubject {
    private List<ScoreObserver> observers = new ArrayList<>();
    private int score = 0;
    public void addObserver(ScoreObserver observer) {
        observers.add(observer);
    }
    public void addPoints(int points) {
        score += points;
        notifyObservers();
    }
    private void notifyObservers() {
        for (ScoreObserver observer : observers) {
            observer.update(score);
        }
    }
}
class ScoreDisplay implements ScoreObserver {
    public void update(int score) {
        System.out.println("Score: " + score);
    }
}
public class SpaceShooter {
    private Player player;
    private List<Enemy> enemies = new ArrayList<>();
    private ScoreSubject scoreSubject;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;
    public SpaceShooter() {
        player = new Player(WIDTH / 2, HEIGHT - 1);
        scoreSubject = new ScoreSubject();
        scoreSubject.addObserver(new ScoreDisplay());
    }
    private void spawnEnemy(long timestamp) {
        if (timestamp % 20 == 0) {  
            enemies.add(new Enemy((int)(Math.random() * WIDTH), 0));
        }
    }
    private void checkCollisions() {
        List<Bullet> bullets = player.getBullets();
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.active && enemy.active && 
                    bullet.x == enemy.x && bullet.y == enemy.y) {
                    bullet.active = false;
                    enemy.active = false;
                    scoreSubject.addPoints(10);
                }
            }
        }
    }
    private void render() {
        char[][] screen = new char[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                screen[y][x] = '.';
            }
        }
        if (player.active) screen[player.y][player.x] = 'A';
        for (Bullet bullet : player.getBullets()) {
            if (bullet.active && bullet.y >= 0 && bullet.y < HEIGHT) 
                screen[bullet.y][bullet.x] = '|';
        }
        for (Enemy enemy : enemies) {
            if (enemy.active && enemy.y >= 0 && enemy.y < HEIGHT) 
                screen[enemy.y][enemy.x] = 'E';
        }
        for (int y = 0; y < HEIGHT; y++) {
            System.out.println(new String(screen[y]));
        }
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        long frame = 0;
        System.out.println("Controls: a/d to move, space to shoot, 1/2 for weapons, q to quit");
        try {
            while (true) {
                String input = scanner.nextLine();
                if (input.equals("a") && player.x > 0) player.x--;
                if (input.equals("d") && player.x < WIDTH - 1) player.x++;
                if (input.equals(" ")) player.shoot(System.currentTimeMillis());
                if (input.equals("1")) player.setWeapon(new SingleShot());
                if (input.equals("2")) player.setWeapon(new SpreadShot());
                if (input.equals("q")) break;
                player.update();
                player.getBullets().forEach(Bullet::update);
                enemies.forEach(Enemy::update);
                spawnEnemy(frame);
                checkCollisions();
                player.getBullets().removeIf(b -> !b.active);
                enemies.removeIf(e -> !e.active);
                System.out.println("\033[H\033[2J"); 
                render();
                frame++;
            }
        } catch (Exception e) {
            System.err.println("Game crashed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    public static void main(String[] args) {
        new SpaceShooter().run();
    }
}