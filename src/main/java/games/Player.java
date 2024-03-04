package games;

import games.victory;

public class Player implements victory {
    private String name;
    private int defence;
    private int attack;

    public Player(String name, int defence, int attack) {
        this.name = name;
        this.defence = defence;
        this.attack = attack;
    }

    public String getName() {
        return name;
    }
    public void victory(){
    attack+=2;
    defence+=2;
    }

    @Override
    public void lose() {
        attack--;
        defence--;
    }

    @Override
    public void tie() {
      attack++;
      defence++;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public String toString() {
        return "name: "+name+
              " attack: "+attack+
             " defence: "+defence;
    }
}
