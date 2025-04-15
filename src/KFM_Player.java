public class KFM_Player 
{
    // Constants
    final static int  INVENTORY_SIZE = 50;  // The size of the inventory
    final static byte NO_EQUIP       = 40;  // Use for equipment when nothing is equipped

    public final static byte COND_POISON    = 0;
    public final static byte COND_PARALYZE  = 1;
    public final static byte COND_DARK      = 2;
    public final static byte COND_CURSE     = 3;

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

    // Condition
    public byte[] status;
    public int statusTickCount;

    // Position
    public int posX;
    public int posY;
    public int posZ;

    // Rotation
    public int rotX;    // Yaw ?
    public int rotY;    // Pitch ?
    public int rotZ;    // Roll ?

    // Misc
    public int viewDistance;
    public int turnSpeed;
    public int moveSpeed;
    public boolean damageFrame;

    /**
     * Constructs a new player object 
    **/
    public KFM_Player()
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

        status = new byte[4];
        statusTickCount = 0;

        for (int i = 0; i < 4; ++i)
            status[i] = -1;

        posX = 24500;
        posY = 800;
        posZ = 500;    

        rotX = 0;
        rotY = 0;
        rotZ = 0;

        viewDistance = 6;
        moveSpeed = 50;
        turnSpeed = 20;
        damageFrame = false;
    }

    /**
     * Clears the inventory of the player
    **/
    public void ClearInventory()
    {
        for (int i = 0; i < INVENTORY_SIZE; ++i)
            inventory[i] = 0;
    }

    /**
     * Clears a status from the player
    **/
    public void ClearStatus(int statusType)
    {
        status[statusType] = -1;    // -1 means no tick. >= 0 is status on.

        // Reverse any effects of the status...
        switch (statusType)
        {
            case COND_PARALYZE:
                moveSpeed = 50;
                turnSpeed = 20;
            break;

            case COND_DARK:
                viewDistance = 6;
                // this.viewWaterArea();
            break;
        }
    }

    /**
     * Adds a status to the player
    **/
    public void AddStatus(int statusType)
    {   
        status[statusType] = 0;

        // Reverse any effects of the status...
        switch (statusType)
        {
            case COND_PARALYZE:
                turnSpeed = 10;
                moveSpeed = 25;
            break;

            case COND_DARK:
                viewDistance = 3;
                // this.viewWaterArea();
            break;
        }
    }

    /**
     * Ticks player status 
    **/
    public void TickStatus()
    {
        // Apply status every 100 frames
        if (statusTickCount < 100)
        {
            statusTickCount++;
            return;
        }

        if (status[COND_POISON] >= 0)
        {
            currentHP  -= (maxHP * 5) / 100;
            damageFrame = true;

            status[COND_POISON]++;
            if (status[COND_POISON] >= 5)
                ClearStatus(COND_POISON);
        }

        if (status[COND_PARALYZE] >= 0)
        {
            status[COND_PARALYZE]++;
            if (status[COND_PARALYZE] >= 5)
                ClearStatus(COND_PARALYZE);
        }

        if (status[COND_DARK] >= 0)
        {
            status[COND_DARK]++;
            if (status[COND_DARK] >= 5)
                ClearStatus(COND_DARK);
        }

        if (status[COND_CURSE] >= 0)
        {
            status[COND_CURSE]++;
            if (status[COND_CURSE] >= 5)
                ClearStatus(COND_CURSE);
        }    
        
        // Reset status tick after status tick as processed
        statusTickCount = 0;
    }
}
