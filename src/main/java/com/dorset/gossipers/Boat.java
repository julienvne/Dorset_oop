package com.dorset.gossipers;

public class Boat {
    private final String name;
    private final int height;
    private final int length;
    private int life;

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
            return "boat " + name + " is sunk";
        }
        return "Life of the boat " + name + " = " + life;
    }
}
