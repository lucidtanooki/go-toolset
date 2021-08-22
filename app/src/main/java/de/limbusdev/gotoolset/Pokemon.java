package de.limbusdev.gotoolset;

/**
 * Created by georg on 09.10.16.
 */
public class Pokemon {
    public int ID;
    public int baseAttack, baseDefense, baseStamina;

    public Pokemon(int ID, int baseAttack, int baseDefense, int baseStamina) {
        this.ID = ID;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseStamina = baseStamina;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(int baseDefense) {
        this.baseDefense = baseDefense;
    }

    public int getBaseStamina() {
        return baseStamina;
    }

    public void setBaseStamina(int baseStamina) {
        this.baseStamina = baseStamina;
    }

    public String toString() {
        return "Pokemon " + ID + " with base values: \nSTAMINA=" + baseStamina + "\nATTACK=" + baseAttack + "\nDEFENSE=" + baseDefense;
    }
}
