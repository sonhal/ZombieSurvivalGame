package engine.services.pathfinder;

import engine.entities.world.Tile;

public abstract class Path {
    public abstract int getLength();
    public abstract Tile getStep(int index);
    public abstract int getX(int index);
    public abstract int getY(int index);
    public abstract void appendStep(int x, int y);
    public abstract void prependStep(int x, int y);
    public abstract boolean contains(int x, int y);
}
