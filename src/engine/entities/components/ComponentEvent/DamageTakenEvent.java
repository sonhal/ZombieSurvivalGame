package engine.entities.components.ComponentEvent;

public class DamageTakenEvent implements ComponentEvent {

    private final int damageTaken;

    public DamageTakenEvent(final int damageTaken) {
        this.damageTaken = damageTaken;
    }

    public int getDamageTaken() {
        return damageTaken;
    }
}
