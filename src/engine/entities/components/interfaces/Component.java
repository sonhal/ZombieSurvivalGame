package engine.entities.components.interfaces;

import java.io.Serializable;

public interface Component<T> extends Serializable {

    void update(T componentHolder);
}
