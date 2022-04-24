package com.dorset.gossipers;

public class Boat {
    private final String name;
    private int height;
    private int length;
    private int life;
    private int x = 0;
    private int y = 0;


    public Boat(String name, int height,
               int length)
    {
        this.name = name;
        this.height = height;
        this.length = length;
        this.life = height*length;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName(){
        return name;
    }

    public int getLife(){
        return life;
    }

    public void removeLife(){
        life--;
    }

    public String getStatus(){
        if(life == 0){
            return "boat " + name + " is sunk";
        }
        return "Life of the boat " + name + " = " + life;
    }

    public void changeDirection(){
        int var = height;
        height = length;
        length = var;
    }
}
