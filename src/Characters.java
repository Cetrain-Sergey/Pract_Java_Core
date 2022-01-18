public abstract class Characters {
    private int hp, str, dex, xp, gold, lvl;
    private String name;

    public Characters(String name, int hp, int str, int dex, int xp, int gold, int lvl) {
        this.name = name;
        this.hp = hp;
        this.str = str;
        this.dex = dex;
        this.xp = xp;
        this.gold = gold;
        this.lvl = lvl;
    }


    public Characters(String name, int hp, int str, int dex) {
        this.name = name;
        this.hp = hp;
        this.str = str;
        this.dex = dex;
    }

    public int attack() {
        int i = (int) (Math.random() * 20);
        if (dex > i) return str;
        else if (dex == i) return str * 2;
        else return 0;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public int getXp() {
        return xp;
    }

    public int getGold() {
        return gold;
    }

    public int getLvl() {
        return lvl;
    }
}