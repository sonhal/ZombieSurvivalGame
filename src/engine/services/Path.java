package engine.services;

import engine.entities.world.Tile;

public class Path {
    public int getLength();
    public Tile getStep(int index);
    public int getX(int index);
    public int getY(int index);
    public void appendStep(int x, int y);
    public void prependStep(int x, int y);
    public boolean contains(int x, int y);
}
