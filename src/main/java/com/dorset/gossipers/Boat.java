package com.example.gossipers;

public class Boat {
    String name;
    int height;
    int length;
    int life;

    public Boat(String name, int height,
               int length, int life)
    {
        this.name = name;
        this.height = height;
        this.length = length;
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
