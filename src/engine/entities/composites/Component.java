package engine.entities.composites;

import java.io.Serializable;

public interface Component<T> extends Serializable {

    void update(T componentHolder);
}
