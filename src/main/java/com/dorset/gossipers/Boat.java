package com.dorset.gossipers;

public class Boat {
    String name;
    int vertical;
    int horizontal;
    int life;

    public Boat(String name, int vertical,
               int horizontal, int life)
    {
        this.name = name;
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.life = life;
    }

    public String getName(){
        return name;
    }
    public int getLife(){
        return life;
    }

    public void removeLife(){
        life -= 1;
    }

    public String getStatus(){
        if(life == 0){
            return "sunk";
        }
        return "touch";
    }

    public static void main(String[] args) {
        Boat b = new Boat("NameShip", 3, 1, 3);
        System.out.println(b.getName());
        System.out.println(b.getLife());
        b.removeLife();
        System.out.println(b.getLife());
        System.out.println(b.getStatus());
    }
}
