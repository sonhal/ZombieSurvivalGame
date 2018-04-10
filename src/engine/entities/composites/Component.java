package engine.entities.composites;

public interface Component<T> {

    void update(T componentHolder);
}
