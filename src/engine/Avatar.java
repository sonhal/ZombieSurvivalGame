package engine;


/**
 * Reperesent the Avatar in the game that the player controls.
 */
public class Avatar extends LivingObject{
    private PlayerStats playerStats;

    public Avatar(){
        this.playerStats = new PlayerStats();
    }

    /**
     * Handles the use of health packs that the player might pickup.
     * @param healthpack reference to the healthpack to be consumed.
     */
    public void consumeHealthPack(Item healthpack){

    }

    /**
     * Handles the pickup of Weapons for the Avatar.
     * @param weapon reference to the weapon to be picked up.
     */
    public void pickupWeapon(Weapon weapon){

    }
}
