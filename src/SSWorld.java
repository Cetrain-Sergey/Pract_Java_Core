import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class SSWorld {

    private static Monsters monster;
    Scanner scan = new Scanner(System.in);
    public String namePlayer;
    private static Players player;

    public void enterName() {
        System.out.println("Введите имя персонажа: ");
        namePlayer = scan.nextLine();
        System.out.println("");
        System.out.println("Приветствуем Вас, о Великий " + namePlayer + " в мире SS!\n" +
                "Наш замок уже 1274 дня в осаде......\n" +
                "Вводите пожалуйста только цифры, иначе главные ворота будут сломлены...");
        System.out.println("");
        player = new Players(namePlayer, 300, 25, 12, 0, 2000, 1);
    }

    public void menu1() {
        System.out.println("Так чем же мы сегодня займемся?");
        System.out.println("1. Пойдем воевать ЗА РОДИНУ!\n2. Закупимся у торговца волшебным зельем\n" +
                "3. Узнать количество опыта, золота" +
                " и текущий уровень\n4. Заныкаемся в сене");
    }

    public static void monsterCreate() {
        int i = (int) (Math.random() * 2);
        if (i == 1) {
            monster = new Monsters("Skeleton", 250, 24, 13);
        } else {
            monster = new Monsters("Goblin", 290, 21, 15);
        }
    }

    public boolean isFightEnded() {
        if (player.getHp() <= 0 || monster.getHp() <= 0) {
            return true;
        } else return false;
    }

    public void fight() {

        if (player.getHp() <= 0) {
            System.out.println("Мало здоровья, надо подлечиться...");
            return;
        }
        monsterCreate();
        AtomicInteger i = new AtomicInteger(player.getHp());
        AtomicInteger j = new AtomicInteger(monster.getHp());
        System.out.println("");
        System.out.println("Против " + player.getName() + " выступает могучий " + monster.getName() + "!");
        System.out.println("");
        Runnable runnable = () -> {
            do {
                i.addAndGet(-monster.attack());
                player.setHp(i.get());
                System.out.println(monster.getName() + " наносит " + monster.attack() + " урона.");
                if (player.getHp() <= 0) {
                    System.out.println(monster.getName() + " победил!");
                    System.out.println("осталось " + player.getHp() + " здоровья.");
                    System.out.println("__________________________________________");
                    menu1();
                    return;
                }
                j.addAndGet(-player.attack());
                monster.setHp(j.get());
                System.out.println(player.getName() + " наносит " + player.attack() + " урона.");
                System.out.println("");
                if (monster.getHp() <= 0) {
                    System.out.println(player.getName() + " победил!");
                    System.out.println("осталось " + player.getHp() + " здоровья.");
                    if (monster.getName() == "Skeleton") {
                        player.setGold(300 + player.getGold());
                        player.setXp(25 + player.getXp());
                        System.out.println("Получено 300 золота и 25 опыта.");
                    } else {
                        player.setGold(350 + player.getGold());
                        player.setXp(29 + player.getXp());
                        System.out.println("Получено 350 золота и 29 опыта.");
                    }
                    if (player.getXp() > 100) {
                        int f = player.getLvl();
                        System.out.println("Уровень повышен!");
                        player.setLvl(f + 1);
                        player.setXp(0);
                    }
                    System.out.println("__________________________________________");
                    menu1();
                    return;
                }
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (isFightEnded() == false);
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void menuCreate() {
        menu1();
        int j;
        do {
            j = scan.nextInt();
            switch (j) {
                case 1: {
                    fight();
                    break;
                }
                case 2: {
                    int l = player.getGold();
                    int i = (int) (Math.random() * 100);
                    player.setHp(i + player.getHp());
                    player.setGold(l -= i);
                    if (l < 100) {
                        System.out.println("");
                        System.out.println("Недостаточно золота, но местные жители с радостью пожертвовали вам!");
                        System.out.println("");
                        player.setGold(2000);
                        menu1();
                        break;
                    } else {
                        System.out.println("");
                        System.out.println("Куплено " + i + " целебного зелья.\nВосстановлено " + i + " здоровья.");
                        System.out.println("Текущий уровень здоровья " + player.getHp());
                        System.out.println("");
                    }
                    if (player.getHp() >= 300) {
                        player.setHp(300);
                        System.out.println("Больше зелье не действует...");
                        System.out.println("");
                    }
                    menu1();
                    scan.nextLine();
                    break;
                }
                case 3: {
                    System.out.println("");
                    System.out.println("У вас " + player.getXp() + " опыта, " + player.getGold() + " золота и "
                            + player.getLvl() + " уровень.");
                    System.out.println("");
                    menu1();
                    scan.nextLine();
                    break;
                }
                case 4: {
                    System.out.println("");
                    System.out.println("Спрятались... Повоюем позже...");
                }
                break;
                default: {
                    System.out.println("Выбери вариант 1...2...3");
                }
            }
        } while (j != 4);
    }

    public void startGame() {
        enterName();
        menuCreate();
    }
}