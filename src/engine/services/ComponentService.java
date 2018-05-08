package engine.services;

import engine.entities.components.ComponentType;
import engine.entities.components.ScriptableComponent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ComponentService {

    public static Optional<ScriptableComponent> getComponentByType(
            List<ScriptableComponent> components, ComponentType type){

        return components.stream()
                .filter(scriptableComponent -> scriptableComponent.getType() == type).findFirst();
    }

    public static Stream<ScriptableComponent> getComponentsByListOfTypes(List<ScriptableComponent> components,
                                                                   List<ComponentType> types){
                return components.stream().filter(scriptableComponent -> types.contains(scriptableComponent.getType()));
    }

}
