public class KingsField_Player 
{
    // Constants
    final int  INVENTORY_SIZE = 50;  // The size of the inventory
    final byte NO_EQUIP       = 40;  // Use for equipment when nothing is equipped

    // Experience Graph
    public final static int[] ExperienceGraph = new int[] 
    { 
        0,                                  // Level 0
        20,    110,   187,   285,   408,    // Level 1  - 5
        561,   749,   972,   1242,  1556,   // Level 6  - 10
        1932,  2379,  2907,  3527,  4251,   // Level 11 - 15 
        5093,  6067,  7191,  8485,  9972,   // Level 16 - 20
        11674, 13622, 15829, 18336, 21184,  // Level 21 - 25
        24424, 27764, 30904, 34404, 100000  // Level 26 - 30
    };

    // HP, Max HP
    public int currentHP;
    public int maxHP;

    // Experience, Level
    public int experience;
    public byte level;

    // Current (max explored?) floor
    public byte floor;

    // Attack
    public byte attackBase;
    public int attack;
    public byte defenseBase;
    public int defense;

    // Equipment
    public byte currentWeaponID;
    public byte currentHelmID;
    public byte currentPlateID;
    public byte currentArmsID;
    public byte currentLegsID;
    public byte currentShieldID;

    // Inventory
    public byte[] inventory;

    // Position
    public int posX;
    public int posY;
    public int posZ;

    // Rotation
    public int rotX;    // Yaw ?
    public int rotY;    // Pitch ?
    public int rotZ;    // Roll ?

    /**
     * Constructs a new player object 
    **/
    public KingsField_Player()
    {
        currentHP = maxHP = 100;

        experience = 0;
        level = 1;

        floor = 0;

        attack  = attackBase  = 8;
        defense = defenseBase = 10;

        currentWeaponID = 0;
        currentHelmID   = NO_EQUIP;
        currentPlateID  = NO_EQUIP;
        currentArmsID   = NO_EQUIP;
        currentLegsID   = NO_EQUIP;
        currentShieldID = NO_EQUIP;

        inventory = new byte[INVENTORY_SIZE];

        posX = 24500;
        posY = 800;
        posZ = 500;    

        rotX = 0;
        rotY = 0;
        rotZ = 0;
    }

    /**
     * Clears the inventory of the player
    **/
    public void ClearInventory()
    {
        for (int i = 0; i < INVENTORY_SIZE; ++i)
            inventory[i] = 0;
    }
}
