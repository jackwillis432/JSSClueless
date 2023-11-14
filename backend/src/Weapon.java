public class Weapon {
    private String weaponName;
    private int[] position;

    public Weapon(String weaponName, int[] position) {
        this.weaponName = weaponName;
        this.position = position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getPosition() {
        return position;
    }

    // Getter and setter for weaponName
    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }
}
