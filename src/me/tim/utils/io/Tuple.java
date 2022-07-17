package me.tim.utils.io;

import java.util.ArrayList;

public class Tuple<First, Second> {
    private ArrayList<First> first;
    private ArrayList<Second> second;

    public Tuple() {
        this.first = new ArrayList<>();
        this.second = new ArrayList<>();
    }

    public void add(First first, Second second)
    {
        this.first.add(first);
        this.second.add(second);
    }

    public First getFirst(int id)
    {
        return this.first.get(id);
    }

    public ArrayList<First> getFirst() {
        return first;
    }

    public ArrayList<Second> getSecond() {
        return second;
    }

    public Second getSecond(int id)
    {
        return this.second.get(id);
    }

    public void clear()
    {
        this.first.clear();
        this.second.clear();
    }
}
