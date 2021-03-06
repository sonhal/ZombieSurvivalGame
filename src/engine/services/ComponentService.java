package engine.services;

import engine.entities.components.ScriptableComponent;

import java.util.List;
import java.util.Optional;

/**
 * Helper class implementing commonly used Component handling
 */
public class ComponentService {

    public static <T extends ScriptableComponent> Optional<ScriptableComponent> getComponentByType(
            List<ScriptableComponent> components, Class<T> subclass){

        return components.stream()
                .filter(scriptableComponent -> subclass.isAssignableFrom(scriptableComponent.getClass())).findFirst();
    }

}
