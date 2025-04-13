import com.nttdocomo.io.HttpConnection;
import com.nttdocomo.opt.ui.j3d.ActionTable;
import com.nttdocomo.opt.ui.j3d.AffineTrans;
import com.nttdocomo.opt.ui.j3d.Figure;
import com.nttdocomo.opt.ui.j3d.Graphics3D;
import com.nttdocomo.opt.ui.j3d.Math;
import com.nttdocomo.opt.ui.j3d.PrimitiveArray;
import com.nttdocomo.opt.ui.j3d.Texture;
import com.nttdocomo.opt.ui.j3d.Vector3D;
import com.nttdocomo.ui.Canvas;
import com.nttdocomo.ui.Dialog;
import com.nttdocomo.ui.Font;
import com.nttdocomo.ui.Graphics;
import com.nttdocomo.ui.IApplication;
import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.MediaImage;
import com.nttdocomo.ui.MediaListener;
import com.nttdocomo.ui.MediaManager;
import com.nttdocomo.ui.MediaPresenter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import javax.microedition.io.Connector;

public final class KingsField_P6Canvas extends Canvas implements MediaListener 
{
    // CLEANED SHIT
    private KingsField_Player player = new KingsField_Player();

    // OLD PLAYER
    private int m_player_grid;
    private byte m_player_dire;
    private byte m_player_eq;

    private byte[][] m_player_condi = new byte[4][2];
    private boolean[] m_condi_cure = new boolean[4];

    // OLD SHIT
    final int APP_VERSION;
    final int SP_VERSION_DATA;
    final int SP_GAME_DATA;
    final int SP_RESOURCE_DATA;
    final int[] m_arGameDataSize = new int[]{87, 229, 900, 900, 900, 900, 900, 900, 900, 900, 900, 900, 0, 0, 0, 87, 229, 900, 900, 900, 900, 900, 900, 900, 900, 900, 900, 0, 0, 0, 7, 105};
    final int DS_HCENTER;
    final int DS_VCENTER;
    final int DS_LEFT;
    final int DS_RIGHT;
    final int DS_TOP;
    final int DS_BOTTOM;
    Graphics3D i_g3d;
    Font m_font_select = Font.getFont(0x71000100);
    static int[] m_arSizeTable;
    static int m_iDataOffset;
    J_Device m_J_Dev;
    J_ReadPNG m_J_rp;
    J_Sound m_J_se;
    final byte ID_player;
    final byte ID_enemy;

    private byte m_scene;
    final byte GAME_OPEN;
    final byte GAME_TITLE;
    final byte GAME_LIVE = (byte)2;
    final byte GAME_OVER = (byte)3;
    final byte GAME_CLEAR = (byte)4;
    final byte GAME_UPDN = (byte)5;
    final byte GAME_SAVE = (byte)6;
    final byte GAME_ENDI = (byte)7;

    private byte m_mode;
    final byte MODE_GETS;
    final byte MODE_SHUT = (byte)2;
    final byte MODE_CURE = (byte)3;
    final byte MODE_HINT = (byte)4;
    final byte MODE_SAVE = (byte)5;
    final byte MODE_MENU = (byte)6;
    final byte MODE_ITEM = (byte)7;
    final byte MODE_USE = (byte)8;
    final byte MODE_EQUIP = (byte)9;
    final byte MODE_EQSET = (byte)10;
    final byte MODE_GAME = (byte)11;
    final byte MODE_QSAVE = (byte)12;
    final byte MODE_MAP = (byte)13;
    final byte MODE_KEY = (byte)14;
    final byte MODE_OPT = (byte)15;
    final byte MODE_EXPL = (byte)16;

    private byte m_cursor_now;
    private byte m_cursor_place;
    final byte m_cursor_num = (byte)8;
    final byte[] m_cursor_max = new byte[]{3, 8, 10, 2, 6, 10, 2, 4};
    private int m_item_select_i;
    final byte title;
    final byte menu;
    final byte item = (byte)2;
    final byte use = (byte)3;
    final byte equip = (byte)4;
    final byte eqset = (byte)5;
    final byte qsave = (byte)6;
    final byte opt = (byte)7;
    final int[][][] m_cursor_pos = new int[][][]{new int[][]{{70, 136}, {70, 161}, {70, 186}}, new int[][]{{5, 52}, {5, 72}, {5, 92}, {5, 112}, {5, 132}, {5, 152}, {5, 172}, {5, 192}}, new int[][]{{110, 50}, {110, 70}, {110, 90}, {110, 110}, {110, 130}, {110, 150}, {110, 170}, {110, 190}, {110, 210}, {110, 230}}, new int[][]{{95, 137}, {95, 157}}, new int[][]{{5, 50}, {5, 70}, {5, 90}, {5, 110}, {5, 130}, {5, 150}}, new int[][]{{90, 50}, {90, 70}, {90, 90}, {90, 110}, {90, 130}, {90, 150}, {90, 170}, {90, 190}, {90, 210}, {90, 230}}, new int[][]{{80, 150}, {80, 180}}, new int[][]{{40, 67}, {40, 107}, {40, 147}, {40, 187}}};
    
    private byte m_shift_num;
    private byte[][] m_shift = new byte[5][4];
    final byte m_sh_grid_x;
    final byte m_sh_grid_z;
    final byte m_sh_next = (byte)2;
    final byte m_sh_dire = (byte)3;
    private int m_shift_now;
    final int BLACK = Graphics.getColorOfRGB(0, 0, 0);
    final int DARK = Graphics.getColorOfRGB(102, 102, 102);
    final int GRAY = Graphics.getColorOfRGB(204, 204, 204);
    final int WHITE = Graphics.getColorOfRGB(255, 255, 255);
    final int RED = Graphics.getColorOfRGB(204, 0, 0);
    final int ORANGE = Graphics.getColorOfRGB(255, 102, 0);
    final int YELLOW = Graphics.getColorOfRGB(204, 204, 0);
    final int OUDO = Graphics.getColorOfRGB(102, 102, 0);
    final int DOKU = Graphics.getColorOfRGB(255, 153, 255);
    final int GREEN = Graphics.getColorOfRGB(0, 204, 0);
    final int BLUE = Graphics.getColorOfRGB(0, 0, 204);
    final int WATER = Graphics.getColorOfRGB(0, 255, 255);
    final int HADA = Graphics.getColorOfRGB(255, 238, 204);
    final int RED_DEEP = Graphics.getColorOfRGB(152, 40, 40);
    final int RED_LIGHT = Graphics.getColorOfRGB(248, 80, 80);
    final byte x_minus;
    final byte x_plus;
    final byte z_minus = (byte)2;
    final byte z_plus = (byte)3;

    // These are Item IDs?..
    final byte I_k0;                // Weapons
    final byte I_k1;
    final byte I_k2 = (byte)2;
    final byte I_k3 = (byte)3;
    final byte I_k4 = (byte)4;
    final byte I_x0 = (byte)5;
    final byte I_x1 = (byte)6;
    final byte I_x2 = (byte)7;
    final byte I_x3 = (byte)8;
    final byte I_x4 = (byte)9;
    final byte I_p0 = (byte)10;
    final byte I_p1 = (byte)11;
    final byte I_p2 = (byte)12;
    final byte I_p3 = (byte)13;
    final byte I_p4 = (byte)14;
    final byte I_h0 = (byte)15;     // Helms
    final byte I_h1 = (byte)16;
    final byte I_h2 = (byte)17;
    final byte I_h3 = (byte)18;
    final byte I_h4 = (byte)19;
    final byte I_c0 = (byte)20;     // Plates
    final byte I_c1 = (byte)21;
    final byte I_c2 = (byte)22;
    final byte I_c3 = (byte)23;
    final byte I_c4 = (byte)24;
    final byte I_l0 = (byte)25;     // Arms
    final byte I_l1 = (byte)26;
    final byte I_l2 = (byte)27;
    final byte I_l3 = (byte)28;
    final byte I_l4 = (byte)29;
    final byte I_f0 = (byte)30;     // Legs
    final byte I_f1 = (byte)31;
    final byte I_f2 = (byte)32;
    final byte I_f3 = (byte)33;
    final byte I_f4 = (byte)34;
    final byte I_s0 = (byte)35;     // Shields
    final byte I_s1 = (byte)36;
    final byte I_s2 = (byte)37;
    final byte I_s3 = (byte)38;
    final byte I_s4 = (byte)39;
    final byte I_i0 = (byte)40;     // Usable...
    final byte I_i1 = (byte)41;
    final byte I_i2 = (byte)42;
    final byte I_i3 = (byte)43;
    final byte I_i4 = (byte)44;
    final byte I_i5 = (byte)45;
    final byte I_i6 = (byte)46;
    final byte I_i7 = (byte)47;
    final byte I_i8 = (byte)48;
    final byte I_i9 = (byte)49;
    final byte[][] m_item_data = new byte[][]{{7, 4, 4}, {21, 5, 3}, {37, 5, 4}, {53, 6, 3}, {68, 8, 2}, {12, 3, 6}, {28, 3, 6}, {45, 4, 5}, {59, 4, 5}, {42, 9, 5}, {11, 5, 3}, {12, 5, 3}, {25, 6, 2}, {42, 6, 2}, {53, 7, 2}, {2}, {5}, {8}, {13}, {18}, {5}, {9}, {14}, {19}, {30}, {3}, {5}, {7}, {10}, {14}, {1}, {3}, {8}, {11}, {16}, {2}, {5}, {8}, {17}, {22}, {0, 0, 0}};
    
    
    private boolean[] m_box_flag = new boolean[124];
    private boolean[] m_door_flag = new boolean[51];
    private boolean[] m_taru_flag = new boolean[51];
    final byte[] m_box_number = new byte[]{0, 0, 2, 5, 9, 9, 14, 19, 23, 28, 33, 38, 38, 43, 47, 52, 57, 60, 65, 69, 72, 76, 81, 84, 89, 92, 96, 96, 99, 102, 105, 110, 111, 112, 117, 119, 119, 123};
    final byte[] m_door_number = new byte[]{0, 0, 1, 2, 2, 2, 2, 3, 3, 5, 5, 5, 5, 5, 6, 6, 7, 8, 8, 8, 8, 10, 10, 10, 10, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 12};
    final byte[][] STEP_dire = new byte[][]{{0, 2}, {2, 0}, {2, 0}, {0, 0}, {2, 0}, {0, 0}, {1, 3}, {0, 1}, {0, 3}, {0, 0}, {0, 1}, {2, 3}, {2, 0}, {0, 2}, {0, 0}, {0, 0}, {0, 0}, {2, 0}, {0, 1}, {0, 0}, {0, 1}, {0, 0}, {1, 0}, {0, 0}, {2, 0}, {2, 2}, {2, 0}, {0, 0}, {0, 3}, {0, 0}, {0, 0}, {0, 1}, {0, 0}, {2, 0}, {0, 2}, {2, 0}, {3, 0}, {2, 0}};
    final byte[][] m_box_data = new byte[][]{{35, 0}, {49, 0}, {42, 3}, {45, 3}, {15, 2}, {20, 3}, {44, 2}, {5, 0}, {49, 2}, {30, 0}, {49, 3}, {42, 3}, {40, 0}, {11, 1}, {42, 0}, {25, 3}, {42, 2}, {10, 1}, {47, 1}, {40, 3}, {16, 1}, {42, 1}, {49, 0}, {46, 0}, {42, 1}, {44, 0}, {1, 2}, {31, 2}, {42, 3}, {49, 1}, {36, 2}, {42, 2}, {43, 2}, {21, 3}, {12, 3}, {43, 3}, {46, 2}, {47, 3}, {46, 0}, {49, 0}, {47, 0}, {6, 0}, {43, 0}, {26, 3}, {43, 3}, {47, 1}, {42, 2}, {2, 0}, {17, 0}, {47, 1}, {43, 2}, {49, 2}, {43, 0}, {43, 0}, {43, 0}, {45, 3}, {32, 3}, {27, 0}, {22, 1}, {44, 2}, {49, 0}, {44, 1}, {44, 3}, {44, 1}, {44, 3}, {46, 1}, {47, 2}, {37, 1}, {47, 1}, {7, 1}, {43, 2}, {49, 0}, {47, 0}, {13, 0}, {43, 0}, {43, 2}, {49, 0}, {45, 0}, {47, 0}, {44, 3}, {18, 0}, {46, 1}, {23, 1}, {45, 1}, {43, 0}, {3, 0}, {49, 1}, {47, 2}, {44, 2}, {28, 0}, {46, 3}, {43, 3}, {43, 0}, {44, 3}, {38, 0}, {47, 0}, {43, 0}, {33, 0}, {43, 1}, {9, 1}, {47, 1}, {43, 2}, {39, 3}, {44, 2}, {47, 2}, {49, 0}, {47, 2}, {45, 2}, {14, 2}, {44, 2}, {34, 2}, {29, 3}, {43, 0}, {47, 0}, {8, 0}, {43, 2}, {40, 3}, {44, 1}, {47, 3}, {44, 0}, {19, 0}, {45, 3}, {47, 2}, {4, 0}};
    final byte[][] m_taru_data = new byte[][]{{0, 0}, {0, 40}, {2, 41}, {0, 41}, {0, 0}, {4, 42}, {4, 42}, {0, 42}, {0, 41}, {1, 42}, {1, 42}, {0, 0}, {4, 42}, {5, 42}, {4, 40}, {0, 43}, {3, 40}, {2, 43}, {2, 42}, {0, 45}, {0, 0}, {2, 43}, {2, 40}, {1, 43}, {0, 47}, {1, 42}, {0, 0}, {3, 42}, {0, 43}, {2, 42}, {5, 42}, {0, 47}, {0, 0}, {0, 40}, {1, 24}, {2, 43}, {1, 42}, {1, 44}};
    final byte[] m_statue_dire = new byte[]{0, 0, 0, 1, 1, 1, 2, 0, 0, 2, 1, 1, 2, 3, 3, 0, 3, 1, 0, 3, 1, 0, 1, 2, 0, 0, 0, 3, 0, 2, 0, 2, 2, 0, 2, 0, 1, 0};

    // Enemy stuff
    final byte EN_bone_1s;
    final byte EN_bone_2s;
    final byte EN_bone_3s = (byte)2;
    final byte EN_bone_4s = (byte)3;
    final byte EN_bone_5s = (byte)4;
    final byte EN_eate_1s = (byte)5;
    final byte EN_eate_2s = (byte)6;
    final byte EN_eate_3s = (byte)7;
    final byte EN_eate_4s = (byte)8;
    final byte EN_eate_5s = (byte)9;
    final byte EN_gole_1s = (byte)10;
    final byte EN_gole_2s = (byte)11;
    final byte EN_gole_3s = (byte)12;
    final byte EN_gole_4s = (byte)13;
    final byte EN_gole_5s = (byte)14;
    final byte EN_deth_1s = (byte)15;
    final byte EN_deth_2s = (byte)16;
    final byte EN_deth_3s = (byte)17;
    final byte EN_deth_4s = (byte)18;
    final byte EN_deth_5s = (byte)19;
    final byte EN_devil_1s = (byte)24;
    final int[] m_enemyID_life = new int[]{40, 65, 120, 150, 160, 50, 70, 100, 140, 100, 90, 120, 200, 275, 300, 80, 110, 160, 210, 400, 0, 0, 0, 0, 400};
    final byte[] m_enemyID_deff = new byte[]{15, 27, 49, 88, 40, 12, 24, 48, 95, 15, 29, 60, 62, 86, 70, 34, 67, 83, 83, 70, 0, 0, 0, 0, 89};
    final int[] m_enemyID_atta = new int[]{48, 88, 185, 477, 205, 37, 75, 162, 442, 92, 104, 234, 482, 633, 407, 103, 328, 389, 585, 585, 0, 0, 0, 0, 808};
    final int[] m_enemyID_addi = new int[]{0, 3, 15, 30, 2, 2, 2, 6, 21, 30, 0, 0, 0, 0, 3, 5, 5, 5, 35, 21, 0, 0, 0, 0, 3};
    final byte[] m_enemyID_view = new byte[]{4, 4, 5, 5, 6, 3, 3, 4, 5, 5, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 0, 0, 0, 0, 6};
    final byte[] m_enemyID_speed = new byte[]{1, 2, 3, 3, 5, 0, 1, 2, 3, 3, 0, 0, 2, 2, 6, 1, 1, 2, 2, 4, 0, 0, 0, 0, 5};
    final byte[] m_enemyID_reach = new byte[]{12, 13, 13, 13, 12, 10, 10, 10, 10, 12, 10, 10, 11, 11, 13, 11, 12, 12, 12, 11, 0, 0, 0, 0, 11};
    final byte[] m_enemyID_range = new byte[]{6, 6, 7, 7, 8, 5, 6, 7, 8, 8, 6, 7, 7, 7, 8, 6, 6, 6, 6, 8, 0, 0, 0, 0, 10};
    final int[] m_enemyID_expe = new int[]{12, 35, 62, 88, 100, 4, 28, 51, 78, 80, 27, 62, 77, 95, 150, 35, 68, 72, 85, 175, 0, 0, 0, 0, 0};
    final byte[] m_enemyID_item = new byte[]{1, 1, 2, 2, 5, 1, 1, 2, 2, 5, 1, 2, 2, 4, 5, 4, 4, 2, 3, 5, 0, 0, 0, 0, 0};
    final byte[] m_enemyID_area = new byte[]{8, 7, 7, 7, 7, 10, 10, 10, 10, 11, 9, 9, 9, 9, 11, 5, 5, 5, 5, 7, 0, 0, 0, 0, 8};
    private byte m_enemy_num;
    private int[][] m_enemy = new int[15][14];
    final int posX;
    final int posY;
    final int posZ;
    final int dire;
    final int kind;
    final int life;
    final int dist;
    final int behv;
    final int fram;
    final int grid;
    final int area;
    final int limit;
    final int lr;

    final byte in;
    final byte out;
    final byte lock = (byte)2;
    final byte die = (byte)3;
    private boolean[] m_enemy_attack = new boolean[15];
    private int m_enemy_step = 20;
    final int m_enemy_back;
    private boolean m_boss_live;
    private int m_command;
    final int MANY_POLY_KIND;
    final int MANY_POLY_NUM;
    final int under;
    final int floor;
    final int above;
    private int[] m_poly_num = new int[4];
    private int[][] m_poly_ver = new int[4][2400];
    private int[][] m_poly_nor = new int[4][600];
    private int[][] m_uv = new int[4][1600];
    private int[][] m_colors = new int[4][200];
    final int[][] m_uv_corner = new int[][]{{0, 0, 63, 0}, {64, 0, 127, 0}, {128, 0, 191, 0}, {192, 0, 255, 0}, {0, 64, 63, 64}, {64, 64, 127, 64}, {128, 64, 191, 64}, {192, 64, 255, 64}};
    private int m_cover_num = 10;
    private int[][] m_cover_ver = new int[10][12];
    private int[] m_cover_nor = new int[3];
    private int[] m_cover_uv = new int[8];
    private int[] m_cover_colors;
    private int[] m_grid_start_num = new int[13];
    private int[][] m_grid_start = new int[13][25];
    private byte m_room_No;
    private byte m_step_No;
    private byte m_stage_ID;
    private byte m_before_room;
    final int m_span;
    final int m_high;
    private int m_start_grid;
    private int m_goal_grid;
    private byte m_grid_x = (byte)30;
    private byte m_grid_z = (byte)30;
    private int m_grid_all = 900;
    private int m_shutter_num;
    private int m_shutter_id;
    private int m_shutter_frame;
    private boolean m_stand_by;
    private int[][] m_shutter = new int[5][2];
    final byte m_s_grid;
    final byte m_s_dire;
    private int m_box_num;
    private int[][] m_box = new int[7][5];
    private int m_box_frame;
    private byte[] m_block_exist;
    final byte none;
    final byte through;
    final byte fix = (byte)2;
    final byte tare = (byte)3;
    final byte move = (byte)4;
    private int[] m_block_level;
    private int m_statue;
    private int m_statue_x;
    private int m_statue_z;
    private int m_item_id;
    private int m_item_grid;
    private int m_openbox;
    private boolean m_item_treasure;
    private int START = -1;
    private int GOAL = -2;
    private int CONTI = -3;
    private boolean m_stair_right;
    final byte POI;
    final byte PAR;
    final byte DAR = (byte)2;
    final byte CUR = (byte)3;
    
    private Figure m_wep_fig;
    private Figure m_han_fig;
    private Figure m_statue_fig;
    private Figure m_box_near_fig;
    private Figure m_box_far_fig;
    private Figure m_item_fig;
    private Figure m_warp_fig;
    private Figure m_taru_fig;
    private Figure[] m_fig = new Figure[5];
    private ActionTable m_wep_act;
    private ActionTable m_box_near_act;
    private ActionTable[] m_act = new ActionTable[5];
    
    private int m_wep_posY = 150;
    private int m_wep_rotX = 2048;
    private int m_wep_rotY = 384;
    private int m_wep_rotZ = 0;
    private int m_view_step;
    final int m_view_stepA;
    private int m_view_rot;
    final int m_view_rotA;
    private int m_rotL_add;
    private int m_rotR_add;
    private int m_view_area = 6;
    private int m_step_count;
    private boolean m_player_damaged = false;
    private boolean m_set_flag = false;
    private boolean m_paint_flag = false;
    private boolean m_levelup_flag = false;
    private boolean m_nodamage = false;
    
    final int CMD_NIL;
    final int CMD_TITLE;
    final int CMD_MENU;
    final int CMD_ATTACK;
    final int CMD_BACK;
    private Random m_rand = new Random();
    final byte[] m_memory_start = new byte[]{0, 5, 12, 18, 28, 38};
    private boolean[][] m_map_memory = new boolean[10][900];
    private boolean m_memory;
    private int m_map_ID;
    private boolean m_memory_reset;
    private byte[] m_data;
    private byte[] m_map_info;
    final byte unknown;
    final byte shutt = (byte)2;
    final byte wall = (byte)3;
    final byte ground = (byte)4;
    final byte poison = (byte)5;
    final byte hole = (byte)6;
    final byte throu = (byte)7;
    private int m_theta;
    private int m_shinpuku = 15;
    private int m_wave = 0;
    private Image m_img_cursor;
    private Image m_img_hp;
    private Image m_img_power;
    private Image[] m_img_condi = new Image[4];
    private Image m_img_houi;
    private Image m_img_game_title;
    private Image m_img_logo0;
    private Image m_img_logo1;
    private Image m_img_copyright;
    private AffineTrans m_mat_x = new AffineTrans();
    private AffineTrans m_mat_y = new AffineTrans();
    private AffineTrans m_mat_z = new AffineTrans();
    private AffineTrans m_3D_trans = new AffineTrans();
    private AffineTrans m_3D_transX = new AffineTrans();
    private AffineTrans m_3D_b_trans = new AffineTrans();
    private AffineTrans[] m_enemy_trans;
    private AffineTrans m_wep_trans = new AffineTrans();
    private AffineTrans m_statue_trans = new AffineTrans();
    private AffineTrans[] m_box_trans = new AffineTrans[5];
    private AffineTrans m_item_trans = new AffineTrans();
    private AffineTrans m_item_trans2 = new AffineTrans();
    private AffineTrans m_warp_trans = new AffineTrans();
    private AffineTrans[] m_taru_trans = new AffineTrans[10];
    private AffineTrans m_trans = new AffineTrans();

    int[][] g_Element_p = new int[][]{{4096, 0, 0, 0}, {0, 4096, 0, 0}, {0, 0, 4096, 0}};


    private long FPS_one_frame_time = 50L;
    private long FPS_timeBegin = 0L;
    private long FPS_timeCur = 0L;
    private long FPS_timePast = 0L;

    private boolean[] m_player_area;
    private boolean[] m_map_area;
    Font m_font_small = Font.getFont(1895826432);
    Font m_font_default = Font.getFont(0x71000100);
    private int m_target_Noid;
    private int m_warp = 0;
    private byte m_y = 0;
    private int m_at_frame;
    private int m_at_limit;
    private byte m_gauge_value = 0;
    private byte m_gauge_attack = 0;
    private byte m_gauge_max = (byte)40;
    private int m_past_grid = 0;
    private int m_past_dire = 0;
    final boolean m_switch_fps_paint;
    final int m_switch_fps_frame;
    private boolean m_on = false;
    private int m_heater_act;
    private Texture m_poly_tex;
    private byte m_array_num;
    private byte[] m_array = new byte[15];
    private String m_comment_text;
    private int m_comment_frame = 50;
    private int m_item_rot;
    private int m_item_posX;
    private int m_item_posY;
    private int m_item_posZ;
    private int m_warp_posX;
    private int m_warp_posY;
    private int m_warp_posZ;
    private byte m_eq_now;
    private int m_warp_rotY;
    private int m_warp_high;
    private int m_item_scale;
    private byte m_save_data;
    private byte m_condi_count;
    private byte[] m_buf = new byte[66616];
    private boolean m_saving;
    private byte m_plt_num = (byte)10;
    private byte[] m_plt_now = new byte[this.m_plt_num];
    private byte[] m_plt_past = new byte[this.m_plt_num];
    private boolean m_roop;
    private boolean[] m_option = new boolean[4];
    private int m_vib_count;
    private int m_a;
    private int m_taru_num;
    private int[] m_taru = new int[10];
    private int m_op_frame;
    final int m_op_end;
    private boolean m_starning = false;
    private byte m_step_auto;
    private boolean m_taru_on;
    private byte m_item_style;
    private boolean m_shutter_draw;
    private int m_end_select;
    private boolean m_at_ok;
    private int m_wep_page;
    private int m_black;
    private int[] m_dead_count = new int[5];
    private int m_dead_all;
    private int m_getper_box;
    private int m_getper_taru;
    private int m_cure_count;
    private boolean m_add_comment;
    private String m_add_comment_text;
    private boolean m_ending = false;
    private int m_ending_count = 0;
    private int[] m_df_targX = new int[15];
    private int[] m_df_targZ = new int[15];
    private int[] m_df_stepX = new int[15];
    private int[] m_df_stepZ = new int[15];
    private boolean m_hole;


    void setFont(Graphics graphics, Font font) 
    {
        graphics.setFont(font);
        this.m_font_select = font;
    }

    void drawString(Graphics graphics, String string, int x, int y, int n3) 
    {       
        if ((n3 & 1) != 0) 
        {
            x -= this.m_font_select.stringWidth(string) / 2;
        } 
        else if ((n3 & 8) != 0) 
        {
            x -= this.m_font_select.stringWidth(string);
        }

        y += this.m_font_select.getHeight();
        if ((n3 & 2) != 0) 
        {
            y -= this.m_font_select.getHeight() / 2;
        } 
        else if ((n3 & 0x20) != 0) 
        {
            y -= this.m_font_select.getHeight();
        }

        graphics.drawString(string, x, y);
    }

    void drawImage(Graphics graphics, Image image, int n, int n2, int n3) {
        if ((n3 & 1) != 0) {
            n -= image.getWidth() / 2;
        } else if ((n3 & 8) != 0) {
            n -= image.getWidth();
        }
        if ((n3 & 2) != 0) {
            n2 -= image.getHeight() / 2;
        } else if ((n3 & 0x20) != 0) {
            n2 -= image.getHeight();
        }
        graphics.drawImage(image, n, n2);
    }

    void drawRoundRect(Graphics graphics, int x, int y, int w, int h) 
    {
        int[] nArray  = new int[]{x, x, x + 1, x + w - 1, x + w, x + w, x + w - 1, x + 1, x};
        int[] nArray2 = new int[]{y + 1, y + h - 1, y + h, y + h, y + h - 1, y + 1, y, y, y + 1};
        graphics.drawPolyline(nArray, nArray2, nArray.length);
    }

    void fillRoundRect(Graphics graphics, int n, int n2, int n3, int n4, int n5, int n6) {
        n5 = 2;
        n6 = 2;
        int[] nArray = new int[]{n, n, n + n5 / 2, n + n3 - n5 / 2, n + n3, n + n3, n + n3 - n5 / 2, n + n5 / 2, n};
        int[] nArray2 = new int[]{n2 + n6 / 2, n2 + n4 - n6 / 2, n2 + n4, n2 + n4, n2 + n4 - n6 / 2, n2 + n6 / 2, n2, n2, n2 + n6 / 2};
        graphics.fillPolygon(nArray, nArray2, nArray.length);
    }

    static Image loadImage(int n) throws Exception {
        byte[] byArray = KingsField_P6Canvas.loadResourceData(n);
        MediaImage mediaImage = MediaManager.getImage(byArray);
        mediaImage.use();
        return mediaImage.getImage();
    }

    static Image loadImage(String string) {
        try {
            MediaImage mediaImage = MediaManager.getImage("resource:///" + string);
            mediaImage.use();
            return mediaImage.getImage();
        }
        catch (Exception exception) {
            return null;
        }
    }

    Figure loadFigure(int n) {
        Figure figure = null;
        try {
            byte[] byArray = KingsField_P6Canvas.loadResourceData(n);
            figure = new Figure(byArray);
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("loadFigure:" + exception.toString());
        }
        return figure;
    }

    ActionTable loadActionTable(int n) {
        ActionTable actionTable = null;
        try {
            byte[] byArray = KingsField_P6Canvas.loadResourceData(n);
            actionTable = new ActionTable(byArray);
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("loadActionTable:" + exception.toString());
        }
        return actionTable;
    }

    /**
	 * Loads and returns a DoJa SDK Texture from the scratchpad file. function of paramBoolean is unknown.
	**/
    Texture loadTexture(int n, boolean bl) 
    {
        Texture texture = null;
        try 
        {
            byte[] byArray = KingsField_P6Canvas.loadResourceData(n);
            texture = new Texture(byArray, !bl);
        }
        catch (Exception exception) 
        {
            KingsField_P6Canvas.Print("loadTexture:" + exception.toString());
        }
        return texture;
    }

    /**
	 * Loads and returns a DoJa SDK Texture from a byte buffer. function of paramBoolean is unknown.
	**/
    Texture loadTexture(byte[] byArray, boolean bl) {
        return new Texture(byArray, !bl);
    }

    /**
	 * Loads a resource file from the SP.
	**/
    static byte[] loadResourceData(int n) throws Exception {
        int n2 = m_iDataOffset;
        for (int i = 0; i < n; ++i) {
            n2 += m_arSizeTable[i];
        }
        DataInputStream dataInputStream = Connector.openDataInputStream("scratchpad:///0;pos=" + n2 + ",length=" + m_arSizeTable[n]);
        byte[] byArray = new byte[m_arSizeTable[n]];
        dataInputStream.read(byArray);
        dataInputStream.close();
        return byArray;
    }

    /**
	 * Connects to a URL and downloads a resource, stores it in the sp file at spStoreOffset.
	**/
    int downloadResourceData(String string, int n) {
        int n2 = 0;
        OutputStream outputStream = null;
        HttpConnection httpConnection = null;
        InputStream inputStream = null;
        try {
            int n3;
            outputStream = Connector.openOutputStream("scratchpad:///0;pos=" + n);
            httpConnection = (HttpConnection)((Object)Connector.open(string, 1, true));
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            inputStream = httpConnection.openInputStream();
            byte[] byArray = new byte[102400];
            while ((n3 = inputStream.read(byArray)) > 0) {
                outputStream.write(byArray, 0, n3);
                n2 += n3;
            }
        }
        catch (Exception exception) {
            n2 = -1;
        }
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (httpConnection != null) {
                httpConnection.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return n2;
    }

    /**
	 * This function is responsible for initializing all resource data.
	**/
    void initResourceData() {
        try 
        {
            int n;
            Graphics graphics = (Graphics)((Object)this.i_g3d);
            int n2 = this.getWidth();
            int n3 = this.getHeight();
            graphics.lock();
            graphics.setColor(this.BLACK);
            graphics.fillRect(0, 0, n2, n3);
            this.setFont(graphics, this.m_font_small);
            graphics.setColor(this.WHITE);
            this.drawString(graphics, "NOW LOADING...", n2 / 2, n3 / 2, 3);
            graphics.unlock(true);

            DataInputStream dataInputStream = Connector.openDataInputStream("scratchpad:///0;pos=0,length=4");
            int n4 = dataInputStream.readInt();
            dataInputStream.close();
            if (n4 != 1) {
                String[] stringArray = IApplication.getCurrentApp().getArgs();
                String string = stringArray != null && stringArray.length > 0 ? stringArray[0] : IApplication.getCurrentApp().getSourceURL();
                int n5 = 0;
                for (n = 0; n < 2; ++n) {
                    int n6 = this.downloadResourceData(string + "res" + n + ".bin", 20480 + n5);
                    if (n6 <= 0) {
                        Dialog dialog = new Dialog(2, "ERROR");
                        dialog.setText("COULD NOT DOWNLOAD DATA!");
                        dialog.show();
                        IApplication.getCurrentApp().terminate();
                    } else {
                        n5 += n6;
                    }
                    // system.gc();
                }
                DataOutputStream dataOutputStream = Connector.openDataOutputStream("scratchpad:///0;pos=0");
                dataOutputStream.writeInt(1);
                dataOutputStream.close();
            }
            m_iDataOffset = 20480;
            dataInputStream = Connector.openDataInputStream("scratchpad:///0;pos=" + m_iDataOffset + ",length=4");
            int n7 = dataInputStream.readInt();
            dataInputStream.close();
            m_arSizeTable = new int[n7];
            dataInputStream = Connector.openDataInputStream("scratchpad:///0;pos=" + (m_iDataOffset += 4) + ",length=" + n7 * 4);
            for (n = 0; n < n7; ++n) {
                KingsField_P6Canvas.m_arSizeTable[n] = dataInputStream.readInt();
                m_iDataOffset += 4;
            }
            dataInputStream.close();
        }
        catch (Exception exception) {
            Dialog dialog = new Dialog(2, "ERROR");
            dialog.setText("COULD NOT LOAD DATA!");
            dialog.show();
            IApplication.getCurrentApp().terminate();
        }
    }

    /**
	 * Was a Stubbed Print in the decomp, but I added it back.
	**/
    static void Print(String string) 
    {
      System.out.println(string);
    }

    void dispLogo() {
        Image[] imageArray = new Image[]{KingsField_P6Canvas.loadImage("logo0.gif"), KingsField_P6Canvas.loadImage("logo1.gif")};
        boolean bl = false;
        int n = 0;
        int n2 = 0;
        try {
            for (int i = 0; i < 2; ++i) {
                Graphics graphics = (Graphics)this.i_g3d;
                int n3 = this.getWidth();
                int n4 = this.getHeight();
                graphics.lock();
                graphics.setColor(this.WHITE);
                graphics.fillRect(0, 0, n3, n4);
                this.drawImage(graphics, imageArray[i], n3 / 2, n4 / 2, 3);
                graphics.unlock(true);
                for (int j = 0; j < 20; ++j) {
                    n2 = n;
                    n = KingsField_P6Main.canvas.getKeypadState();
                    
                    if (i == 0 && j < 5) {
                        n = 0;
                    }
                    if (n2 == 0) {
                        this.m_J_Dev.getClass();
                        if ((n & 0x100000) != 0) {
                            bl = true;
                            break;
                        }
                    }
                    try {
                        Thread.sleep(100L);
                        continue;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (!bl) {
                    continue;
                }
                break;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        imageArray[1].dispose();
        imageArray[0].dispose();
        imageArray[1] = null;
        imageArray[0] = null;
        imageArray = null;
        // system.gc();
    }

    public KingsField_P6Canvas() {
        this.APP_VERSION = 1;
        this.SP_VERSION_DATA = 0;
        this.SP_GAME_DATA = 4;
        this.SP_RESOURCE_DATA = 20480;
        this.DS_HCENTER = 1;
        this.DS_VCENTER = 2;
        this.DS_LEFT = 4;
        this.DS_RIGHT = 8;
        this.DS_TOP = 16;
        this.DS_BOTTOM = 32;
        this.ID_player = 0;
        this.ID_enemy = 1;
        this.GAME_OPEN = 0;
        this.GAME_TITLE = 1;
        this.MODE_GETS = 1;
        this.title = 0;
        this.menu = 1;
        this.m_sh_grid_x = 0;
        this.m_sh_grid_z = 1;
        this.x_minus = 0;
        this.x_plus = 1;
        this.I_k0 = 0;
        this.I_k1 = 1;
        this.EN_bone_1s = 0;
        this.EN_bone_2s = 1;
        this.posX = 0;
        this.posY = 1;
        this.posZ = 2;
        this.dire = 3;
        this.kind = 4;
        this.life = 5;
        this.dist = 6;
        this.behv = 7;
        this.fram = 8;
        this.grid = 9;
        this.area = 10;
        this.limit = 11;
        this.lr = 13;
        this.in = 0;
        this.out = 1;
        this.m_enemy_back = 150;
        this.MANY_POLY_KIND = 4;
        this.MANY_POLY_NUM = 200;
        this.under = 0;
        this.floor = 1;
        this.above = 2;
        this.m_span = 1000;
        this.m_high = 1200;
        this.m_s_grid = 0;
        this.m_s_dire = 1;
        this.none = 0;
        this.through = 1;
        this.POI = 0;
        this.PAR = 1;
        this.m_view_stepA = 50;
        this.m_view_rotA = 20;
        this.CMD_NIL = 0;
        this.CMD_TITLE = 1;
        this.CMD_MENU = 2;
        this.CMD_ATTACK = 3;
        this.CMD_BACK = 4;
        this.unknown = 1;
        this.m_switch_fps_paint = false;
        this.m_switch_fps_frame = 20;
        this.m_op_end = 580;
        this.i_g3d = (Graphics3D)((Object)this.getGraphics());
    }

    private void initialize() {
        int n;
        this.m_J_Dev = new J_Device();
        this.dispLogo();
        this.initResourceData();
        this.m_J_Dev.Init();
        this.m_J_rp = new J_ReadPNG();
        this.m_J_se = new J_Sound();
        this.m_J_se.init(this);
        this.roomSetup();
        this.dataSetup();
        try {
            byte[] byArray = KingsField_P6Canvas.loadResourceData(9);
            System.arraycopy(byArray, 0, this.m_buf, 0, this.m_buf.length);
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("error plt base");
        }
        for (n = 0; n < this.m_plt_num; ++n) {
            this.m_plt_now[n] = 0;
            this.m_plt_past[n] = 0;
        }
        for (n = 0; n < 10; ++n) {
            for (int i = 0; i < 900; ++i) {
                this.m_map_memory[n][i] = false;
            }
        }
        this.checkTitle();
        this.openingStart();
    }

    private void checkTitle() {
        byte by;
        try {
            this.m_data = null;
            // system.gc();
            this.m_data = this.ReadRecord(1);
            if (this.m_data != null) {
                by = this.m_data[0];
                this.m_save_data = (byte)2;
            } else {
                this.m_save_data = 0;
            }
        }
        catch (Exception exception) {
            this.m_save_data = 0;
        }
        try {
            this.m_data = null;
            // system.gc();
            this.m_data = this.ReadRecord(16);
            if (this.m_data != null) {
                by = this.m_data[0];
                this.m_save_data = (byte)(this.m_save_data + 1);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.m_cursor_now = 0;
    }

    private void openingStart() 
    {
        this.m_item_id = -1;
        this.m_item_treasure = false;
        player.level = 1;
        player.currentHP = 1;
        this.m_op_frame = 0;
        this.m_player_grid = 25;

        player.posX = 24500;
        player.posY = 800;
        player.posZ = 500;

        this.m_view_area = 6;
        this.m_step_No = 0;
        this.m_room_No = 1;
        player.floor = 0;
        this.restage(this.CONTI);
        this.m_mode = 0;
        if (this.m_scene == 7) {
            this.m_cover_colors = new int[]{this.WHITE};
        } else {
            this.m_scene = 1;
            this.m_cover_colors = new int[]{this.BLACK};
            this.m_cursor_place = 0;
        }
        this.m_roop = true;
        this.m_stair_right = false;
        this.m_add_comment = false;
        this.m_set_flag = true;

        SoftLabelsClear();

        this.m_at_frame = this.m_at_limit = 10;
        this.m_cursor_place = 0;
        this.m_view_step = 50;
        this.m_view_rot = 20;
    }

    public void run() {
        this.initialize();
        while (this.m_roop) {
            this.m_J_Dev.KeyRead();
            if (this.m_scene == 1) {
                if (this.m_starning) {
                    this.starning();
                }
                ++this.m_op_frame;
                if (this.m_op_frame < 580) {
                    this.flyThrough();
                    this.keyAct_stage();
                    this.everyReload();
                } else {
                    this.keyAct_nostage();
                    if (this.m_op_frame == 880) {
                        this.openingStart();
                    }
                }
            } else if (this.m_scene == 7) {
                if (this.m_ending) {
                    if (this.m_ending_count < 100) {
                        ++this.m_ending_count;
                        this.m_cover_num = this.m_ending_count / 15;
                        this.m_cover_colors = new int[]{this.WHITE};
                        this.posThroughBack();
                        if (this.m_ending_count == 100) {
                            this.openingStart();
                        }
                    } else if (this.m_op_frame < 630) {
                        ++this.m_op_frame;
                        this.flyThrough();
                        this.everyReload();
                    } else if (this.m_ending_count < 130) {
                        ++this.m_ending_count;
                        this.m_cover_num = (this.m_ending_count - 94) / 6;
                        this.m_cover_colors = new int[]{this.BLACK};
                        this.posThroughBack();
                        if (this.m_ending_count == 130) {
                            SoftLablesSet(0, 1);
                        }
                    }
                } else {
                    this.enemyAct(1);
                }
            } else if (this.m_scene == 5) {
                this.m_shinpuku = 30;
                this.updown();
            } else if (this.m_scene == 6) {
                this.keyAct_nostage();
            } else if (this.m_scene == 2) {
                if (this.m_mode == 0) {
                    int n;
                    if (this.m_block_level[this.m_player_grid] >= -200) {
                        this.keyAct_stage();
                    }
                    this.everyReload();
                    for (n = 0; n < this.m_enemy_num; ++n) {
                        this.enemyArea(n);
                        this.enemyEye(n);
                        this.enemyDirect(n, false);
                        this.enemyHitEach(n);
                        this.enemyAct(n);
                    }
                    this.hitCharacter();
                    this.hitStage(0, 0);
                    for (n = 0; n < this.m_enemy_num; ++n) {
                        this.hitStage(1, n);
                    }
                } else {
                    this.keyAct_nostage();
                }
            }
            if (this.m_set_flag) {
                if (this.m_mode == 0) {
                    this.viewSetting();
                    this.enemySetting();
                    this.attackSetting();
                    this.objectSetting();
                }
                this.warpSetting();
                this.itemSetting();
                if (this.m_mode == 1) {
                    this.boxOpenSetting();
                }
                if (this.m_mode == 2) {
                    this.shutterSetting();
                }
                this.m_set_flag = false;
                this.m_paint_flag = true;
            }
            if (this.m_option[0]) {
                ++this.m_vib_count;
                if (this.m_vib_count == 5) {
                    this.m_vib_count = 0;
                    this.m_J_Dev.SetVibMode(false);
                }
            } else {
                this.m_J_Dev.SetVibMode(false);
            }
            this.repaint();

            if (this.m_mode == 12) 
            {
                this.qsavetime();
                try {
                    Thread.sleep(1000L);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }

            this.FPS_update();
  
            //Print("Memory: [Free = " + m_J_Dev.GetFreeMemory() + ", Total = " + m_J_Dev.GetTotalMemory() + "]");
            m_J_Dev.MemoryGC();
        }

        IApplication.getCurrentApp().terminate();
    }

    private void starning() {
        this.m_starning = false;
        this.m_cover_num = 1;
        if (this.m_cursor_now == 0) {
            this.init_new();
            this.m_y = (byte)2;
            this.m_warp = 80;
            this.m_stair_right = false;
            this.m_scene = (byte)5;
        } else if (this.m_cursor_now == 1) {
            this.init_load1(1, 2, 3);
            this.m_warp = 80;
            this.m_stair_right = false;
            this.m_scene = (byte)5;
        } else {
            this.init_load1(16, 17, 18);
            this.init_load2(31);
            this.restage(this.CONTI);
            this.init_load3(32);
            for (int i = 16; i <= 32; ++i) {
                this.WriteRecord(i, null);
            }
            this.m_save_data = (byte)(this.m_save_data - 1);
            this.m_scene = (byte)2;
            SoftLablesSet(2, 3);
        }
        this.m_comment_frame = 50;
        this.m_op_frame = 580;
    }

    private void flyThrough() 
    {
        int n = this.m_op_frame * 64 % 4096;
        int n2 = this.m_op_frame * 32 % 4096;

        player.posY = 700 + Math.sin(n) * 250 / 4096;
        player.rotZ = 0;

        if (this.m_op_frame < 30) {
            this.m_cover_num = 6;
        } else if (this.m_op_frame < 60) {
            this.m_cover_num = (60 - this.m_op_frame) / 5;
        } else if (this.m_op_frame >= 550) {
            this.m_cover_num = (this.m_op_frame - 550) / 5;
            if (this.m_cover_num > 5) {
                this.m_cover_num = 5;
            }
        } else {
            this.m_cover_num = 0;
        }
        if (this.m_scene == 7 && this.m_op_frame < 580 && this.m_cover_num < 1) {
            this.m_cover_num = 1;
        }
        if (this.m_op_frame < 30) {
            int n3 = this.m_op_frame;

            player.posX = 24500;
            player.posZ = 500;
            player.rotY = 0;
            player.rotZ = 0;
        } else if (this.m_op_frame < 90) {
            int n4 = this.m_op_frame - 30;

            player.posX = 24500;
            player.posZ = 500 + 6000 * n4 / 60;

            player.rotY = 0;
            if (n4 > 30) {
                player.rotZ = 192 * (n4 - 30) / 30;
            }
        } else if (this.m_op_frame < 120) {
            int n5 = this.m_op_frame - 90;

            player.posX = 24500 - 1000 * n5 / 30;
            player.posZ = 6500 + 3000 * n5 / 30;

            player.rotY = -256 * n5 / 30;
            player.rotZ = 192 - 192 * n5 / 30;
        } else if (this.m_op_frame < 150) {
            int n6 = this.m_op_frame - 120;
            player.posX = 23500 + 500 * n6 / 30;
            player.posZ = 9500 + 3500 * n6 / 30;
            player.rotY = -256 + 384 * n6 / 30;
            player.rotZ = -256 * n6 / 30;
        } else if (this.m_op_frame < 180) {
            int n7 = this.m_op_frame - 150;
            player.posX = 24000 + 2000 * n7 / 30;
            player.posZ = 13000 + 2500 * n7 / 30;
            player.rotY = 128 + 256 * n7 / 30;
            player.rotZ = -256 + 256 * n7 / 30;
        } else if (this.m_op_frame < 210) {
            int n8 = this.m_op_frame - 180;
            player.posX = 26000 + 1000 * n8 / 30;
            player.posZ = 15500 + 4000 * n8 / 30;
            player.rotY = 384 - 256 * n8 / 30;
            player.rotZ = 64 * n8 / 30;
        } else if (this.m_op_frame < 240) {
            int n9 = this.m_op_frame - 210;
            player.posX = 27000 + 500 * n9 / 30;
            player.posZ = 19500 + 3000 * n9 / 30;
            player.rotY = 128 - 128 * n9 / 30;
            player.rotZ = 64 + 64 * n9 / 30;
        } else if (this.m_op_frame < 270) {
            int n10 = this.m_op_frame - 240;
            player.posX = 27500 - 1000 * n10 / 30;
            player.posZ = 22500 + 2500 * n10 / 30;
            player.rotY = -768 * n10 / 30;
            player.rotZ = 128 - 64 * n10 / 30;
        } else if (this.m_op_frame < 290) {
            int n11 = this.m_op_frame - 270;
            player.posX = 26500 - 3500 * n11 / 20;
            player.posZ = 25000 + Math.sin(2048 * n11 / 20) * 500 / 4096;
            player.rotY = -768 - 384 * n11 / 20;
            player.rotZ = 64 - 64 * n11 / 20;
        } else if (this.m_op_frame < 332) {
            int n12 = this.m_op_frame - 290;
            player.posX = 23000 - 5000 * n12 / 42;
            player.posZ = 25000 - Math.sin(2048 * n12 / 42) * 500 / 4096;
            player.rotY = -1152 + 256 * n12 / 42;
            player.rotZ = n12 < 21 ? -64 * n12 / 21 : -64 + 64 * (n12 - 21) / 21;
        } else if (this.m_op_frame < 374) {
            int n13 = this.m_op_frame - 332;
            player.posX = 18000 - 5000 * n13 / 42;
            player.posZ = 25000 + Math.sin(2048 * n13 / 42) * 500 / 4096;
            player.rotY = -896 - 256 * n13 / 42;
            player.rotZ = 320 * n13 / 42;
        } else if (this.m_op_frame < 402) {
            int n14 = this.m_op_frame - 374;
            player.posX = 13000 - 1500 * n14 / 28;
            player.posZ = 25000 - 2500 * n14 / 28;
            player.rotY = -1152 - 896 * n14 / 28;
            player.rotZ = 320 - 160 * n14 / 28;
        } else if (this.m_op_frame < 426) {
            int n15 = this.m_op_frame - 402;
            player.posX = 11500 + 500 * n15 / 24;
            player.posZ = 22500 - 2500 * n15 / 24;
            player.rotY = -2048 - 128 * n15 / 24;
            player.rotZ = 160 - 160 * n15 / 24;
        } else if (this.m_op_frame < 447) {
            int n16 = this.m_op_frame - 426;
            player.posX = 12000 + 500 * n16 / 21;
            player.posZ = 20000 - 2500 * n16 / 21;
            player.rotY = -2176 + 128 * n16 / 21;
            player.rotZ = -192 * n16 / 21;
        } else if (this.m_op_frame < 468) {
            int n17 = this.m_op_frame - 447;
            player.posX = 12500 - 1500 * n17 / 21;
            player.posZ = 17500 - 2500 * n17 / 21;
            player.rotY = -2048 + 768 * n17 / 21;
            player.rotZ = -192 + 128 * n17 / 21;
        } else if (this.m_op_frame < 498) {
            int n18 = this.m_op_frame - 468;
            player.posX = 11000 - 4000 * n18 / 30;
            player.posZ = 15000;
            player.rotY = -1280 + 384 * n18 / 30;
            player.rotZ = -64 + 192 * n18 / 30;
        } else if (this.m_op_frame < 513) {
            int n19 = this.m_op_frame - 498;
            player.posX = 7000 - 1000 * n19 / 15;
            player.posZ = 15000 - 700 * n19 / 15;
            player.rotY = -896 - 512 * n19 / 15;
            player.rotZ = 128 - 32 * n19 / 15;
        } else if (this.m_op_frame < 528) {
            int n20 = this.m_op_frame - 513;
            player.posX = 6000 - 500 * n20 / 15;
            player.posZ = 14300 - 1300 * n20 / 15;
            player.rotY = -1408 - 512 * n20 / 15;
            player.rotZ = 96 - 32 * n20 / 15;
        } else if (this.m_op_frame < 580) {
            int n21 = this.m_op_frame - 528;
            player.posX = 5500;
            player.posZ = 13000 - (n21 * n21 * 31 / 10 + 80 * n21);
            player.rotY = 2048;
            if (n21 < 30) {
                player.rotZ = 64 - 64 * n21 / 30;
                player.rotY = 2176 - 128 * n21 / 30;
            }
        }
    }

    private void init_new() {
        int n;
        player.level = 1;
        player.experience = 0;
        player.currentHP = player.maxHP = 100;
        player.attackBase = (byte)8;
        player.defenseBase = (byte)10;

        for (n = 0; n < 4; ++n) {
            this.m_player_condi[n][0] = 0;
            this.m_player_condi[n][1] = 0;
        }

        player.ClearInventory();

        for (n = 0; n < 5; ++n) {
            this.m_dead_count[n] = 0;
        }

        player.inventory[0] = 1;
        player.inventory[40] = 3;
        player.inventory[41] = 1;

        player.currentWeaponID = 0;
        player.currentHelmID = (byte)40;
        player.currentPlateID = (byte)40;
        player.currentArmsID = (byte)40;
        player.currentLegsID = (byte)40;
        player.currentShieldID = (byte)40;
        this.equipSet();
        this.setWepFigure();
        this.wepPltChange(player.currentWeaponID);
        this.rePalet();
        this.m_gauge_value = 0;
        this.m_option[0] = false;
        this.m_option[1] = false;
        this.m_option[2] = true;
        this.m_option[3] = false;
        for (n = 0; n < 124; ++n) {
            this.m_box_flag[n] = true;
        }
        for (n = 0; n < 51; ++n) {
            this.m_door_flag[n] = true;
        }
        for (n = 0; n < 51; ++n) {
            this.m_taru_flag[n] = true;
        }
        for (n = 0; n < 10; ++n) {
            for (int i = 0; i < 900; ++i) {
                this.m_map_memory[n][i] = false;
            }
        }
    }

    private void init_load1(int n, int n2, int n3) {
        int n4;
        this.m_data = this.ReadRecord(n);
        player.floor = this.m_data[0];
        player.level = this.m_data[1];
        player.experience = this.m_data[2] * 10000 + this.m_data[3] * 100 + this.m_data[4];

        player.currentHP = this.m_data[5] * 100 + this.m_data[6];
        player.maxHP = this.m_data[7] * 100 + this.m_data[8];

        player.attackBase = this.m_data[9];
        player.defenseBase = this.m_data[10];
        player.currentWeaponID = this.m_data[11];
        player.currentHelmID = this.m_data[12];
        player.currentPlateID = this.m_data[13];
        player.currentArmsID = this.m_data[14];
        player.currentLegsID = this.m_data[15];
        player.currentShieldID = this.m_data[16];
        this.equipSet();
        this.setWepFigure();
        this.wepPltChange(player.currentWeaponID);
        this.rePalet();
        this.m_gauge_value = 0;
        this.m_player_condi[0][0] = this.m_data[17];
        this.m_player_condi[0][1] = this.m_data[18];
        this.m_player_condi[1][0] = this.m_data[19];
        this.m_player_condi[1][1] = this.m_data[20];
        this.m_player_condi[2][0] = this.m_data[21];
        this.m_player_condi[2][1] = this.m_data[22];
        this.m_player_condi[3][0] = this.m_data[23];
        this.m_player_condi[3][1] = this.m_data[24];
        for (n4 = 0; n4 < 4; ++n4) {
            if (this.m_player_condi[n4][0] != 0) continue;
            this.m_condi_cure[n4] = true;
        }
        this.condiCure();
        if (this.m_player_condi[2][0] == 1) {
            this.m_view_area = 3;
        }
        if (this.m_player_condi[1][0] == 1) {
            this.m_view_step = 25;
            this.m_view_rot = 10;
        }
        for (n4 = 0; n4 < 50; ++n4) {
            player.inventory[n4] = this.m_data[25 + n4];
            if (player.inventory[n4] >= 0) continue;
            player.inventory[n4] = 127;
        }
        for (n4 = 0; n4 < 4; ++n4) {
            this.m_option[n4] = this.m_data[75 + n4] == 0;
        }
        for (n4 = 0; n4 < 4; ++n4) {
            this.m_dead_count[n4] = this.m_data[79 + n4 * 2] * 100 + this.m_data[80 + n4 * 2];
        }
        this.m_data = null;
        // system.gc();
        this.m_data = this.ReadRecord(n2);
        this.m_y = this.m_data[0];
        this.m_room_No = this.m_data[1];
        this.m_step_No = this.m_data[2];
        for (n4 = 0; n4 < 124; ++n4) {
            this.m_box_flag[n4] = this.m_data[n4 + 3] == 0;
        }
        for (n4 = 0; n4 < 51; ++n4) {
            this.m_door_flag[n4] = this.m_data[n4 + 127] == 0;
        }
        for (n4 = 0; n4 < 51; ++n4) {
            this.m_taru_flag[n4] = this.m_data[n4 + 178] == 0;
        }
        for (n4 = 0; n4 < 10; ++n4) {
            this.m_data = null;
            // system.gc();
            this.m_data = this.ReadRecord(n3 + n4);
            for (int i = 0; i < 900; ++i) {
                this.m_map_memory[n4][i] = this.m_data[i] == 0;
            }
        }
        this.m_data = null;
        // system.gc();
    }

    private void init_load2(int n) {
        this.m_data = this.ReadRecord(n);
        this.m_player_grid = this.m_data[0] * 100 + this.m_data[1];
        player.rotX = this.m_data[2] * 100 + this.m_data[3];
        player.rotY = this.m_data[4] * 100 + this.m_data[5];
        player.rotZ = 0;
        this.m_player_dire = this.m_data[6];
        this.m_data = null;
        // system.gc();
        player.posX = this.m_player_grid % 30 * 1000 + 500;
        player.posY = 650;
        player.posZ = this.m_player_grid / 30 * 1000 + 500;
    }

    private void init_load3(int n) {
        this.m_data = this.ReadRecord(n);
        for (int i = 0; i < this.m_enemy_num; ++i) {
            this.m_enemy[i][9] = this.m_data[i * 7 + 0] * 100 + this.m_data[i * 7 + 1];
            this.m_enemy[i][5] = this.m_data[i * 7 + 2] * 100 + this.m_data[i * 7 + 3];
            this.m_enemy[i][3] = this.m_data[i * 7 + 4] * 100 + this.m_data[i * 7 + 5];
            this.m_enemy[i][10] = this.m_data[i * 7 + 6];
            this.m_enemy[i][0] = this.m_enemy[i][9] % 30 * 1000 + 500;
            this.m_enemy[i][2] = this.m_enemy[i][9] / 30 * 1000 + 500;
        }
        this.m_data = null;
        // system.gc();
    }

    public synchronized void restage(int n) 
    {
        // Load stage data
        this.m_stage_ID = this.stageID(this.m_room_No, this.m_step_No);
        this.getStageData(this.m_stage_ID);

        for (int n2 = 0; n2 < this.m_box_num; ++n2) {
            this.m_box[n2][1] = this.m_box_data[this.m_box_number[this.m_stage_ID] + n2][0];
            this.m_box[n2][2] = this.m_box_data[this.m_box_number[this.m_stage_ID] + n2][1];
            this.m_box[n2][3] = this.m_box[n2][0] % this.m_grid_x * 1000 + 500;
            this.m_box[n2][4] = this.m_box[n2][0] / this.m_grid_x * 1000 + 500;
        }
        this.m_plt_now[0] = (byte)(this.m_stage_ID == 4 || this.m_stage_ID == 11 || this.m_stage_ID == 17 || this.m_stage_ID == 26 || this.m_stage_ID == 35 ? 4 : (this.m_step_No <= 3 ? 0 : (this.m_step_No <= 6 ? 6 : (this.m_step_No <= 9 ? 7 : (this.m_step_No <= 13 ? 3 : (this.m_step_No <= 16 ? 5 : (this.m_step_No <= 22 ? 2 : (this.m_step_No <= 27 ? 1 : (this.m_step_No <= 30 ? 8 : (this.m_step_No <= 34 ? 10 : 9))))))))));
        this.rePalet();
        if (n != this.CONTI) {
            if (n == 0) {
                for (int n2 = 0; n2 < this.m_shift_num; ++n2) {
                    if (this.m_shift[n2][2] != this.m_before_room) continue;
                    this.m_shift_now = n2;
                    this.m_player_grid = this.grid(this.m_shift[n2][0], this.m_shift[n2][1]);
                    player.rotY = this.m_shift[n2][3] * 1024;
                }
            } else if (n == this.START) {
                this.m_player_grid = this.m_start_grid;
                player.rotY = this.STEP_dire[this.m_stage_ID][0] * 1024;
                this.m_item_posX = this.m_start_grid % this.m_grid_x * 1000 + 500;
                this.m_item_posY = 0;
                this.m_item_posZ = this.m_start_grid / this.m_grid_x * 1000 + 500;
            } else {
                this.m_player_grid = this.m_goal_grid;
                player.rotY = this.STEP_dire[this.m_stage_ID][1] * 1024;
                this.m_item_posX = this.m_goal_grid % this.m_grid_x * 1000 + 500;
                this.m_item_posY = 0;
                this.m_item_posZ = this.m_goal_grid / this.m_grid_x * 1000 + 500;
            }
            player.posX = this.m_player_grid % this.m_grid_x * 1000 + 500;
            player.posY = 650;
            player.posZ = this.m_player_grid / this.m_grid_x * 1000 + 500;
            player.rotX = 0;
            player.rotZ = 0;
        }
        this.posEnemy();
        this.posShutter();
        this.m_stair_right = false;
        this.m_player_damaged = false;
        playerDire();
        this.m_map_ID = this.m_stage_ID - this.m_memory_start[player.floor];
        this.viewWaterArea();
        this.mapWaterArea();
        this.getMapInfo();
        this.m_item_id = -1;
    }

    private void rePalet() {
        for (int i = 0; i < this.m_plt_num; ++i) {
            if (this.m_plt_now[i] == this.m_plt_past[i]) continue;
            this.readResource((byte)i, this.m_plt_now[i]);
            this.m_plt_past[i] = this.m_plt_now[i];
        }
        this.m_poly_tex = null;
        // system.gc();
        this.m_poly_tex = this.loadTexture(this.m_buf, true);
        this.resetTexuture();
    }

    public void getStageData(byte by) {
        if (this.m_J_rp.readImage("stage.raw", by)) {
            this.m_grid_x = (byte)this.m_J_rp.m_pix_w;
            this.m_grid_z = (byte)this.m_J_rp.m_pix_h;
            this.m_grid_all = this.m_grid_x * this.m_grid_z;
            this.m_block_exist = new byte[this.m_grid_all];
            this.m_map_info = new byte[this.m_grid_all];
            this.m_block_level = new int[this.m_grid_all];
            this.m_player_area = new boolean[this.m_grid_all];
            this.m_map_area = new boolean[this.m_grid_all];
            this.m_shift_num = 0;
            this.m_shutter_num = 0;
            this.m_enemy_num = 0;
            this.m_box_num = 0;
            this.m_taru_num = 0;
            this.m_boss_live = false;
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            int n = 0xFF9900;
            int n2 = 16751104;
            bl3 = this.m_J_rp.m_color[0] != 0xF80000;
            int n3 = 0xFF0000;
            int n4 = 0xF8F8F8;
            int n5 = 0xC8C8C8;
            int n6 = 16291840;
            int n7 = 0xF89898;
            int n8 = 0;
            int n9 = 0xF898F8;
            int n10 = 63736;
            int n11 = 16292040;
            int n12 = 16277656;
            int n13 = 16304128;
            int n14 = 16304144;
            int n15 = 16304160;
            int n16 = 16304176;
            int n17 = 63488;
            int n18 = 61440;
            int n19 = 59392;
            int n20 = 57344;
            int n21 = 55296;
            int n22 = 53248;
            int n23 = 51200;
            int n24 = 49152;
            int n25 = 47104;
            int n26 = 38912;
            int n27 = 20480;
            int n28 = 10000480;
            int n29 = 0xF8F800;
            int n30 = 248;
            int n31 = 240;
            int n32 = 232;
            int n33 = 224;
            int n34 = 216;
            int n35 = 208;
            int n36 = 200;
            int n37 = 192;
            int n38 = 184;
            int n39 = 176;
            int n40 = 168;
            int n41 = 160;
            int n42 = 152;
            int n43 = 144;
            int n44 = 136;
            int n45 = 128;
            int n46 = 120;
            int n47 = 112;
            int n48 = 104;
            int n49 = 96;
            int n50 = 88;
            n3 = 0xFF0000;
            n4 = 0xFFFFFF;
            n5 = 0xCCCCCC;
            n6 = 0xFF9900;
            n7 = 0xFF9999;
            n8 = 0;
            n9 = 0xFF99FF;
            n10 = 65535;
            n11 = 0xFF99CC;
            n12 = 0xFF6699;
            n13 = 0xFFCC00;
            n14 = 0xFFCC11;
            n15 = 0xFFCC22;
            n16 = 0xFFCC33;
            n17 = 64000;
            n18 = 61952;
            n19 = 59904;
            n20 = 57856;
            n21 = 55808;
            n22 = 53248;
            n23 = 51712;
            n24 = 49664;
            n25 = 47616;
            n26 = 39168;
            n27 = 21760;
            n28 = 0x999966;
            n30 = 248;
            n31 = 240;
            n32 = 232;
            n33 = 224;
            n34 = 216;
            n35 = 208;
            n36 = 200;
            n37 = 192;
            n38 = 184;
            n39 = 176;
            n40 = 168;
            n41 = 160;
            n42 = 152;
            n43 = 144;
            n44 = 136;
            n45 = 128;
            n46 = 120;
            n47 = 112;
            n48 = 104;
            n49 = 96;
            n50 = 88;
            for (int i = 0; i < this.m_grid_all; ++i) {
                this.m_map_info[i] = 1;
                try {
                    this.m_block_level[i] = 0;
                    this.m_block_exist[i] = 0;
                    if (this.m_J_rp.m_color[i] == n4) continue;
                    if (this.m_J_rp.m_color[i] == n3) {
                        this.m_block_exist[i] = 2;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n5) {
                        this.m_block_level[i] = -1;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n7) {
                        this.m_block_exist[i] = 3;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n8) {
                        this.m_block_level[i] = -4000;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n9) {
                        this.m_block_level[i] = -200;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n11) {
                        this.m_start_grid = i;
                        bl = true;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n12) {
                        this.m_goal_grid = i;
                        bl2 = true;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] >= n13 && this.m_J_rp.m_color[i] <= n16) {
                        this.m_shift[this.m_shift_num][0] = (byte)(i % this.m_grid_x);
                        this.m_shift[this.m_shift_num][1] = (byte)(i / this.m_grid_x);
                        if (this.m_J_rp.m_color[i] == n13) {
                            this.m_shift[this.m_shift_num][2] = 0;
                        }
                        if (this.m_J_rp.m_color[i] == n14) {
                            this.m_shift[this.m_shift_num][2] = 1;
                        }
                        if (this.m_J_rp.m_color[i] == n15) {
                            this.m_shift[this.m_shift_num][2] = 2;
                        }
                        if (this.m_J_rp.m_color[i] == n16) {
                            this.m_shift[this.m_shift_num][2] = 3;
                        }
                        this.m_shift[this.m_shift_num][3] = (byte)(i < this.m_grid_x * 2 ? 0 : (i % this.m_grid_x < 2 ? 1 : (i % this.m_grid_x >= this.m_grid_x - 2 ? 3 : 2)));
                        this.m_shift_num = (byte)(this.m_shift_num + 1);
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n6) {
                        this.m_block_exist[i] = 1;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n10) {
                        this.m_block_exist[i] = (byte)(this.m_door_flag[this.m_door_number[this.m_stage_ID] + this.m_shutter_num] ? 4 : 3);
                        this.m_shutter[this.m_shutter_num][0] = i;
                        ++this.m_shutter_num;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n28) {
                        this.m_taru[this.m_taru_num] = i;
                        ++this.m_taru_num;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n26) {
                        this.m_statue = i;
                        this.m_statue_x = i % this.m_grid_x * 1000 + 500;
                        this.m_statue_z = i / this.m_grid_x * 1000 + 500;
                        this.m_plt_now[8] = 0;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n27) {
                        this.m_statue = i;
                        this.m_statue_x = i % this.m_grid_x * 1000 + 500;
                        this.m_statue_z = i / this.m_grid_x * 1000 + 500;
                        this.m_plt_now[8] = 1;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] == n22) {
                        this.m_box[this.m_box_num][0] = i;
                        ++this.m_box_num;
                        continue;
                    }
                    if (this.m_J_rp.m_color[i] <= 255) {
                        int n51;
                        if (this.m_J_rp.m_color[i] >= n34) {
                            if (this.m_J_rp.m_color[i] == n30) {
                                n51 = 0;
                            } else if (this.m_J_rp.m_color[i] == n31) {
                                n51 = 1;
                            } else if (this.m_J_rp.m_color[i] == n32) {
                                n51 = 2;
                            } else if (this.m_J_rp.m_color[i] == n33) {
                                n51 = 3;
                            } else {
                                n51 = 4;
                                if (this.m_stage_ID >= this.m_memory_start[player.floor] && this.m_stage_ID == 11) {
                                    this.m_boss_live = true;
                                }
                            }
                            this.m_enemy[this.m_enemy_num][4] = 0 + n51;
                            this.m_plt_now[4] = (byte)n51;
                        } else if (this.m_J_rp.m_color[i] >= n39) {
                            if (this.m_J_rp.m_color[i] == n35) {
                                n51 = 0;
                            } else if (this.m_J_rp.m_color[i] == n36) {
                                n51 = 1;
                            } else if (this.m_J_rp.m_color[i] == n37) {
                                n51 = 2;
                            } else if (this.m_J_rp.m_color[i] == n38) {
                                n51 = 3;
                            } else {
                                n51 = 4;
                                if (this.m_stage_ID >= this.m_memory_start[player.floor] && this.m_stage_ID == 4) {
                                    this.m_boss_live = true;
                                }
                            }
                            this.m_enemy[this.m_enemy_num][4] = 5 + n51;
                            this.m_plt_now[5] = (byte)n51;
                        } else if (this.m_J_rp.m_color[i] >= n44) {
                            if (this.m_J_rp.m_color[i] == n40) {
                                n51 = 0;
                            } else if (this.m_J_rp.m_color[i] == n41) {
                                n51 = 1;
                            } else if (this.m_J_rp.m_color[i] == n42) {
                                n51 = 2;
                            } else if (this.m_J_rp.m_color[i] == n43) {
                                n51 = 3;
                            } else {
                                n51 = 4;
                                if (this.m_stage_ID >= this.m_memory_start[player.floor] && this.m_stage_ID == 17) {
                                    this.m_boss_live = true;
                                }
                            }
                            this.m_enemy[this.m_enemy_num][4] = 10 + n51;
                            this.m_plt_now[6] = (byte)n51;
                        } else if (this.m_J_rp.m_color[i] >= n49) {
                            if (this.m_J_rp.m_color[i] == n45) {
                                n51 = 0;
                            } else if (this.m_J_rp.m_color[i] == n46) {
                                n51 = 1;
                            } else if (this.m_J_rp.m_color[i] == n47) {
                                n51 = 2;
                            } else if (this.m_J_rp.m_color[i] == n48) {
                                n51 = 3;
                            } else {
                                n51 = 4;
                                if (this.m_stage_ID >= this.m_memory_start[player.floor] && this.m_stage_ID == 26) {
                                    this.m_boss_live = true;
                                }
                            }
                            this.m_enemy[this.m_enemy_num][4] = 15 + n51;
                            this.m_plt_now[7] = (byte)n51;
                        } else {
                            if (this.m_stage_ID >= this.m_memory_start[player.floor] && this.m_stage_ID == 35) {
                                this.m_boss_live = true;
                            }
                            this.m_enemy[this.m_enemy_num][4] = 24;
                        }
                        this.m_enemy[this.m_enemy_num][9] = i;
                        this.m_enemy[this.m_enemy_num][0] = i % this.m_grid_x * 1000 + 500;
                        this.m_enemy[this.m_enemy_num][1] = 0;
                        this.m_enemy[this.m_enemy_num][2] = i / this.m_grid_x * 1000 + 500;
                        this.m_enemy[this.m_enemy_num][13] = (this.m_rand.nextInt() >>> 1) % 2;
                        this.m_enemy_num = (byte)(this.m_enemy_num + 1);
                        this.m_enemy_attack[this.m_enemy_num] = false;
                        continue;
                    }
                    KingsField_P6Canvas.Print(i + ":0x" + Integer.toHexString(this.m_J_rp.m_color[i]));
                    continue;
                }
                catch (Exception exception) {
                    KingsField_P6Canvas.Print("error:" + i + ":0x" + Integer.toHexString(this.m_J_rp.m_color[i]));
                }
            }
            if (!bl) {
                this.m_start_grid = 0;
            }
            if (!bl2) {
                this.m_goal_grid = this.m_grid_x - 1;
            }
        } else {
            KingsField_P6Canvas.Print("stageRead error");
        }
    }

    private void getMapInfo() {
        this.m_map_ID = this.m_stage_ID - this.m_memory_start[player.floor];
        if (this.m_map_ID < 0) {
            this.m_memory = false;
        } else {
            this.m_memory = true;
            for (int i = 0; i < this.m_grid_all; ++i) {
                if (!this.m_map_memory[this.m_map_ID][i]) continue;
                this.mapCheck(i);
            }
        }
    }

    private void posEnemy() {
        this.m_enemy_trans = new AffineTrans[this.m_enemy_num];
        for (int i = 0; i < this.m_enemy_num; ++i) {
            this.m_enemy[i][3] = 1024 * ((this.m_rand.nextInt() >>> 1) % 4);
            this.m_enemy[i][5] = this.m_enemyID_life[this.m_enemy[i][4]];
            this.m_enemy[i][6] = 0;
            this.m_enemy[i][7] = 2;
            this.m_enemy[i][10] = 1;
            this.m_enemy[i][8] = 0;
            this.m_enemy[i][11] = 0;
            this.m_enemy_trans[i] = new AffineTrans();
            this.m_enemy_trans[i].setIdentity();
            if (!this.BossOrNot(i) || this.m_boss_live) continue;
            this.m_enemy[i][5] = 0;
            this.m_enemy[i][10] = 3;
            this.m_enemy[i][11] = 60;
        }
    }

    private void posShutter() {
        for (int i = 0; i < this.m_shutter_num; ++i) {
            this.m_shutter[i][1] = this.m_block_exist[this.m_shutter[i][0] - this.m_grid_x] != 2 && this.m_block_exist[this.m_shutter[i][0] + this.m_grid_x] != 2 ? 0 : 1;
        }
    }

    private void updown() {
        if (this.m_y == 0) {
            if (this.m_warp < 20) {
                if (this.m_shift[this.m_shift_now][3] == 0) {
                    player.posZ -= 50;
                } else if (this.m_shift[this.m_shift_now][3] == 1) {
                    player.posX -= 50;
                } else if (this.m_shift[this.m_shift_now][3] == 2) {
                    player.posZ += 50;
                } else {
                    player.posX += 50;
                }
            } else if (this.m_warp == 20) {
                this.restage(0);
            } else if (this.m_warp < 40) {
                if (this.m_shift[this.m_shift_now][3] == 0) {
                    player.posZ += 50;
                } else if (this.m_shift[this.m_shift_now][3] == 1) {
                    player.posX += 50;
                } else if (this.m_shift[this.m_shift_now][3] == 2) {
                    player.posZ -= 50;
                } else {
                    player.posX -= 50;
                }
            } else {
                this.m_scene = (byte)2;
                SoftLablesSet(2, 3);
                this.m_step_auto = 0;
            }
        } else if ((this.m_y == 1 || this.m_y == 2) && this.m_warp >= 75) {
            if (this.m_warp == 75) {
                this.m_cursor_now = 1;
                this.m_cursor_place = (byte)6;
                this.m_scene = (byte)6;
            } else if (this.m_warp >= 79) {
                if (this.m_warp == 79) {
                    this.savetime();
                } else if (this.m_warp == 80) {
                    this.m_saving = false;
                    if (this.m_y == 1) {
                        this.m_step_No = (byte)(this.m_step_No - 1);
                        this.restage(this.GOAL);
                    } else {
                        this.m_step_No = (byte)(this.m_step_No + 1);
                        this.restage(this.START);
                    }
                } else if (this.m_warp >= 160 && this.m_warp == 160) {
                    this.m_scene = (byte)2;
                    SoftLablesSet(2, 3);
                    this.m_step_auto = 0;
                }
            }
        }
        ++this.m_warp;
    }

    public void hitCharacter() {
        for (int i = 0; i < this.m_enemy_num; ++i) {
            int n = this.m_enemy[i][4] % 5 == 4 ? 550 : 400;
            if (this.m_enemy[i][10] == 1 || this.m_enemy[i][5] <= 0 || this.m_enemy[i][6] >= n) continue;
            int n2 = (player.posX + this.m_enemy[i][0]) / 2;
            int n3 = (player.posZ + this.m_enemy[i][2]) / 2;
            int n4 = this.m_enemy[i][0] - player.posX;
            int n5 = this.m_enemy[i][2] - player.posZ;
            int n6 = this.askValue2(n4, n5);
            if (this.m_enemy[i][4] / 5 != 3 || this.m_enemy[i][7] != 5 && this.m_enemy[i][7] != 6) {
                this.m_enemy[i][0] = n2 + n4 * (n / 2) / n6;
                this.m_enemy[i][2] = n3 + n5 * (n / 2) / n6;
            }
            player.posX = n2 - n4 * (n / 2) / n6;
            player.posZ = n3 - n5 * (n / 2) / n6;
        }
    }

    public void hitStage(int n, int n2) {
        int n3 = 0;
        int n4 = 0;
        if (n == 0) {
            int n5;
            int n6 = player.posX / 1000;
            int n7 = player.posZ / 1000;
            n3 = 250;
            if (player.posX < n6 * 1000 + n3) {
                n5 = this.m_player_grid - 1;
                if (this.judgeP(n5)) {
                    player.posX = n6 * 1000 + n3;
                }
            } else if (player.posX > (n6 + 1) * 1000 - n3 && this.judgeP(n5 = this.m_player_grid + 1)) {
                player.posX = (n6 + 1) * 1000 - n3;
            }
            if (player.posZ < n7 * 1000 + n3) {
                n5 = this.m_player_grid - this.m_grid_x;
                if (this.judgeP(n5)) {
                    player.posZ = n7 * 1000 + n3;
                }
            } else if (player.posZ > (n7 + 1) * 1000 - n3 && this.judgeP(n5 = this.m_player_grid + this.m_grid_x)) {
                player.posZ = (n7 + 1) * 1000 - n3;
            }
            if (player.posX < n6 * 1000 + n3) {
                if (player.posZ < n7 * 1000 + n3) {
                    n5 = this.m_player_grid - this.m_grid_x - 1;
                    if (this.judgeP(n5)) {
                        if (player.posX % 1000 >= player.posZ % 1000) {
                            player.posX = n6 * 1000 + n3;
                        } else {
                            player.posZ = n7 * 1000 + n3;
                        }
                    }
                } else if (player.posZ > (n7 + 1) * 1000 - n3 && this.judgeP(n5 = this.m_player_grid + this.m_grid_x - 1)) {
                    if (player.posX % 1000 >= 1000 - player.posZ % 1000) {
                        player.posX = n6 * 1000 + n3;
                    } else {
                        player.posZ = (n7 + 1) * 1000 - n3;
                    }
                }
            } else if (player.posX > (n6 + 1) * 1000 - n3) {
                if (player.posZ < n7 * 1000 + n3) {
                    n5 = this.m_player_grid - this.m_grid_x + 1;
                    if (this.judgeP(n5)) {
                        if (1000 - player.posX % 1000 >= player.posZ % 1000) {
                            player.posX = (n6 + 1) * 1000 - n3;
                        } else {
                            player.posZ = n7 * 1000 + n3;
                        }
                    }
                } else if (player.posZ > (n7 + 1) * 1000 - n3 && this.judgeP(n5 = this.m_player_grid + this.m_grid_x + 1)) {
                    if (1000 - player.posX % 1000 >= 1000 - player.posZ % 1000) {
                        player.posX = (n6 + 1) * 1000 - n3;
                    } else {
                        player.posZ = (n7 + 1) * 1000 - n3;
                    }
                }
            }
        } else if (!(n != 1 || this.m_enemy[n2][n] / 5 == 3 && this.m_hole)) {
            if (this.m_enemy[n2][n] % 5 == 4) {
                n3 = 700;
                n4 = 500;
            } else if (this.m_enemy[n2][n] / 5 == 0) {
                n3 = 200;
                n4 = 300;
            } else if (this.m_enemy[n2][n] / 5 == 1) {
                n3 = 350;
                n4 = 450;
            } else if (this.m_enemy[n2][n] / 5 == 2) {
                n3 = 400;
                n4 = 490;
            } else if (this.m_enemy[n2][n] / 5 == 3) {
                n3 = 200;
                n4 = 300;
            } else {
                n3 = 200;
                n4 = 300;
            }
            int n8 = this.m_enemy[n2][9] % this.m_grid_x;
            int n9 = this.m_enemy[n2][9] / this.m_grid_x;
            boolean bl = false;
            byte by = 0;
            int n10 = 0;
            int n11 = this.m_enemy[n2][9] - 1;
            by = this.judgeE(n11, n2);
            if (by == 1) {
                n10 = n3;
            } else if (by == 2) {
                n10 = n4;
            }
            if (by > 0 && this.m_enemy[n2][0] < n8 * 1000 + n10) {
                this.m_enemy[n2][0] = n8 * 1000 + n10;
                bl = true;
            }
            if ((by = this.judgeE(n11 = this.m_enemy[n2][9] + 1, n2)) == 1) {
                n10 = n3;
            } else if (by == 2) {
                n10 = n4;
            }
            if (by > 0 && this.m_enemy[n2][0] > (n8 + 1) * 1000 - n10) {
                this.m_enemy[n2][0] = (n8 + 1) * 1000 - n10;
                bl = true;
            }
            if ((by = this.judgeE(n11 = this.m_enemy[n2][9] - this.m_grid_x, n2)) == 1) {
                n10 = n3;
            } else if (by == 2) {
                n10 = n4;
            }
            if (by > 0 && this.m_enemy[n2][2] < n9 * 1000 + n10) {
                this.m_enemy[n2][2] = n9 * 1000 + n10;
                bl = true;
            }
            if ((by = this.judgeE(n11 = this.m_enemy[n2][9] + this.m_grid_x, n2)) == 1) {
                n10 = n3;
            } else if (by == 2) {
                n10 = n4;
            }
            if (by > 0 && this.m_enemy[n2][2] > (n9 + 1) * 1000 - n10) {
                this.m_enemy[n2][2] = (n9 + 1) * 1000 - n10;
                bl = true;
            }
            if ((by = this.judgeE(n11 = this.m_enemy[n2][9] - this.m_grid_x - 1, n2)) == 1) {
                n10 = n3;
            } else if (by == 2) {
                n10 = n4;
            }
            if (by > 0 && this.m_enemy[n2][0] < n8 * 1000 + n10 && this.m_enemy[n2][2] < n9 * 1000 + n10) {
                if (this.m_enemy[n2][0] % 1000 >= this.m_enemy[n2][2] % 1000) {
                    this.m_enemy[n2][0] = n8 * 1000 + n10;
                } else {
                    this.m_enemy[n2][2] = n9 * 1000 + n10;
                }
                bl = true;
            }
            if ((by = this.judgeE(n11 = this.m_enemy[n2][9] + this.m_grid_x - 1, n2)) == 1) {
                n10 = n3;
            } else if (by == 2) {
                n10 = n4;
            }
            if (by > 0 && this.m_enemy[n2][0] < n8 * 1000 + n10 && this.m_enemy[n2][2] > (n9 + 1) * 1000 - n10) {
                if (this.m_enemy[n2][0] % 1000 >= 1000 - this.m_enemy[n2][2] % 1000) {
                    this.m_enemy[n2][0] = n8 * 1000 + n10;
                } else {
                    this.m_enemy[n2][2] = (n9 + 1) * 1000 - n10;
                }
                bl = true;
            }
            if ((by = this.judgeE(n11 = this.m_enemy[n2][9] - this.m_grid_x + 1, n2)) == 1) {
                n10 = n3;
            } else if (by == 2) {
                n10 = n4;
            }
            if (by > 0 && this.m_enemy[n2][0] > (n8 + 1) * 1000 - n10 && this.m_enemy[n2][2] < n9 * 1000 + n10) {
                if (1000 - this.m_enemy[n2][0] % 1000 >= this.m_enemy[n2][2] % 1000) {
                    this.m_enemy[n2][0] = (n8 + 1) * 1000 - n10;
                } else {
                    this.m_enemy[n2][2] = n9 * 1000 + n10;
                }
                bl = true;
            }
            if ((by = this.judgeE(n11 = this.m_enemy[n2][9] + this.m_grid_x + 1, n2)) == 1) {
                n10 = n3;
            } else if (by == 2) {
                n10 = n4;
            }
            if (by > 0 && this.m_enemy[n2][0] > (n8 + 1) * 1000 - n10 && this.m_enemy[n2][2] > (n9 + 1) * 1000 - n10) {
                if (1000 - this.m_enemy[n2][0] % 1000 >= 1000 - this.m_enemy[n2][2] % 1000) {
                    this.m_enemy[n2][0] = (n8 + 1) * 1000 - n10;
                } else {
                    this.m_enemy[n2][2] = (n9 + 1) * 1000 - n10;
                }
                bl = true;
            }
            if (bl && this.m_enemy[n2][10] != 2) {
                int[] nArray = this.m_enemy[n2];
                nArray[3] = nArray[3] + this.m_rand.nextInt() % 2 * 1024;
            }
        }
    }

    private boolean judgeP(int n) {
        int n2;
        boolean bl = false;
        if (this.m_block_exist[n] == 2 || this.m_block_exist[n] == 4) {
            bl = true;
        }
        for (n2 = 0; n2 < this.m_box_num; ++n2) {
            if (n != this.m_box[n2][0]) continue;
            bl = true;
        }
        for (n2 = 0; n2 < this.m_taru_num; ++n2) {
            if (n != this.m_taru[n2]) continue;
            bl = true;
        }
        if (n == this.m_statue) {
            bl = true;
        }
        return bl;
    }

    private byte judgeE(int n, int n2) {
        byte by = 0;
        if (this.m_block_exist[n] != 0) {
            by = 2;
        } else if (this.m_block_level[n] < 0) {
            if (this.m_enemy[n2][4] / 5 != 3 || this.m_enemy[n2][7] != 5 && this.m_enemy[n2][7] != 6) {
                by = 1;
            }
        } else {
            int n3;
            for (n3 = 0; n3 < this.m_box_num; ++n3) {
                if (n != this.m_box[n3][0]) continue;
                by = 1;
            }
            for (n3 = 0; n3 < this.m_taru_num; ++n3) {
                if (n != this.m_taru[n3]) continue;
                by = 1;
            }
            if (n == this.m_statue) {
                by = 1;
            }
        }
        return by;
    }

    public void keyAct_stage() {
        int n;
        int n2 = 0;
        int n3 = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n3 & 0x100000) != 0) {
            if (this.m_scene == 1) {
                this.m_op_frame = 580;
            } else {
                int n4;
                int n5 = player.posX + Math.sin(player.rotY) * 800 / 4096;
                int n6 = player.posZ + Math.cos(player.rotY) * 800 / 4096;
                for (n = 0; n < this.m_box_num; ++n) {
                    if (!this.m_player_area[this.m_box[n][0]] || this.m_player_grid != (n2 = this.m_box[n][2] == 0 ? this.m_box[n][0] + this.m_grid_x : (this.m_box[n][2] == 1 ? this.m_box[n][0] + 1 : (this.m_box[n][2] == 2 ? this.m_box[n][0] - this.m_grid_x : this.m_box[n][0] - 1))) || (n4 = this.askValue2(n5 - this.m_box[n][3], n6 - this.m_box[n][4])) >= 300) continue;
                    if (this.m_box_flag[this.m_box_number[this.m_stage_ID] + n]) {
                        this.m_item_id = this.m_box_data[this.m_box_number[this.m_stage_ID] + n][0];
                        this.m_item_grid = this.m_box[n][0];
                        this.m_openbox = n;
                        if (this.m_option[1]) {
                            this.m_J_se.playMMF(0);
                        }
                        this.setItemFigure(this.m_item_id);
                        this.m_item_posX = this.m_item_grid % this.m_grid_x * 1000 + 500;
                        this.m_item_posY = 350;
                        if (this.m_item_id <= 39) {
                            this.m_item_posY = 250;
                        }
                        this.m_item_posZ = this.m_item_grid / this.m_grid_x * 1000 + 500;
                        this.itemSetting();
                        this.m_comment_text = "FOUND " + KingsField_Text.ItemName[this.m_item_id] + "!";
                        this.m_mode = 1;
                        SoftLabelsClear();
                        this.m_item_treasure = true;
                        this.m_item_style = 0;
                        continue;
                    }
                    this.m_comment_text = "IT'S EMPTY";
                    this.m_comment_frame = 30;
                }
                n = 1;
                if (this.m_item_id >= 0 && this.m_item_style == 1 && this.gridDistance(this.m_player_grid, this.m_item_grid, 0) <= 1) {
                    if (player.inventory[this.m_item_id] < 127) {
                        int n7 = this.m_item_id;
                        player.inventory[n7] = (byte)(player.inventory[n7] + 1);
                    }
                    this.m_comment_text = "FOUND " + KingsField_Text.ItemName[this.m_item_id] + "!";
                    this.m_comment_frame = 30;
                    this.m_item_id = -1;
                    this.m_item_posX = 0;
                    this.m_item_posY = 0;
                    this.m_item_posZ = 0;
                    n = 0;
                }
                for (int i = 0; i < this.m_taru_num; ++i) {
                    int n8 = this.m_taru[i] % this.m_grid_x * 1000 + 500;
                    int n9 = this.m_taru[i] / this.m_grid_x * 1000 + 500;
                    n4 = this.askValue2(n5 - n8, n6 - n9);
                    if (n4 > 300) continue;
                    if (this.m_taru_flag[this.m_stage_ID] && i == this.m_taru_data[this.m_stage_ID][0]) {
                        this.m_item_grid = this.m_taru[i];
                        this.m_item_id = this.m_taru_data[this.m_stage_ID][1];
                        this.setItemFigure(this.m_item_id);
                        this.m_item_posX = n8;
                        this.m_item_posY = this.m_item_id >= 40 ? 650 : 750;
                        this.m_item_posZ = n9;
                        this.m_comment_text = "FOUND " + KingsField_Text.ItemName[this.m_item_id] + "!";
                        this.m_mode = 1;
                        SoftLabelsClear();
                        this.m_taru_on = true;
                        this.m_item_style = (byte)2;
                    } else {
                        this.m_comment_text = "IT'S EMPTY";
                    }
                    this.m_comment_frame = 30;
                }
                if (this.gridDistance(this.m_player_grid, this.m_statue, 0) <= 1 && n != 0) {
                    n5 = player.posX + Math.sin(player.rotY) * 800 / 4096;
                    n6 = player.posZ + Math.cos(player.rotY) * 800 / 4096;
                    n4 = this.askValue2(n5 - this.m_statue_x, n6 - this.m_statue_z);
                    n2 = this.m_statue_dire[this.m_stage_ID] == 0 ? this.m_statue + this.m_grid_x : (this.m_statue_dire[this.m_stage_ID] == 1 ? this.m_statue + 1 : (this.m_statue_dire[this.m_stage_ID] == 2 ? this.m_statue - this.m_grid_x : this.m_statue - 1));
                    if (this.m_player_grid == n2 && n4 < 500) {
                        SoftLabelsClear();
                        this.posThroughBack();
                        if (this.m_plt_now[8] == 0) {
                            this.m_mode = (byte)4;
                        } else {
                            this.m_warp_posX = player.posX;
                            this.m_warp_posY = 0;
                            this.m_warp_posZ = player.posZ;
                            this.m_mode = (byte)3;
                            this.m_cure_count = 0;
                            this.m_plt_now[9] = 1;
                            this.rePalet();
                        }
                    }
                }
                if (this.m_stand_by && n != 0) 
                {
                    if (player.inventory[49] > 0) {
                        player.inventory[49] = (byte)(player.inventory[49] - 1);
                        this.m_shutter_frame = 0;
                        this.m_block_exist[this.m_shutter[this.m_shutter_id][0]] = 3;
                        this.viewWaterArea();
                        this.m_mode = (byte)2;
                    } else {
                        this.m_comment_text = "NO KEY";
                        this.m_comment_frame = 25;
                    }
                }
            }
        }
        this.m_shinpuku = 15;
        this.m_wave = 128;
        n = 0;
        int n10 = this.m_J_Dev.m_Cont;
        this.m_J_Dev.getClass();
        if ((n10 & 0x10000) != 0) {
            ++this.m_rotL_add;
            player.rotY = this.m_rotL_add < 15 ? (player.rotY += this.m_view_rot) : (this.m_rotL_add < 30 ? (player.rotY += this.m_view_rot + (this.m_rotL_add - 15)) : (player.rotY += this.m_view_rot + 15));
        } else {
            this.m_rotL_add = 0;
        }
        int n11 = this.m_J_Dev.m_Cont;
        this.m_J_Dev.getClass();
        if ((n11 & 0x40000) != 0) {
            ++this.m_rotR_add;
            player.rotY = this.m_rotR_add < 15 ? (player.rotY -= this.m_view_rot) : (this.m_rotR_add < 30 ? (player.rotY -= this.m_view_rot + (this.m_rotR_add - 15)) : (player.rotY -= this.m_view_rot + 15));
        } else {
            this.m_rotR_add = 0;
        }
        if (this.m_option[3]) {
            int n12 = this.m_J_Dev.m_Trg;
            this.m_J_Dev.getClass();
            if ((n12 & 0x20000) != 0) {
                this.m_step_auto = this.m_step_auto == 1 ? (byte)0 : 1;
            } else {
                int n13 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n13 & 0x80000) != 0) {
                    this.m_step_auto = this.m_step_auto == 2 ? (byte)0 : (byte)2;
                }
            }
            if (this.m_step_auto == 1) {
                player.posX += Math.sin(player.rotY) * this.m_view_step / 4096;
                player.posZ += Math.cos(player.rotY) * this.m_view_step / 4096;
                n = 1;
            }
            if (this.m_step_auto == 2) {
                player.posX -= Math.sin(player.rotY) * this.m_view_step / 4096;
                player.posZ -= Math.cos(player.rotY) * this.m_view_step / 4096;
                n = 1;
            }
        } else {
            int n14 = this.m_J_Dev.m_Cont;
            this.m_J_Dev.getClass();
            if ((n14 & 0x20000) != 0) {
                player.posX += Math.sin(player.rotY) * this.m_view_step / 4096;
                player.posZ += Math.cos(player.rotY) * this.m_view_step / 4096;
                n = 1;
            } else {
                int n15 = this.m_J_Dev.m_Cont;
                this.m_J_Dev.getClass();
                if ((n15 & 0x80000) != 0) {
                    player.posX -= Math.sin(player.rotY) * this.m_view_step / 4096;
                    player.posZ -= Math.cos(player.rotY) * this.m_view_step / 4096;
                    n = 1;
                }
            }
        }
        int n16 = this.m_J_Dev.m_Cont;
        this.m_J_Dev.getClass();
        if ((n16 & 0x40) != 0) {
            player.posX -= Math.sin(player.rotY + 1024) * this.m_view_step / 4096;
            player.posZ -= Math.cos(player.rotY + 1024) * this.m_view_step / 4096;
            n = 1;
        } else {
            int n17 = this.m_J_Dev.m_Cont;
            this.m_J_Dev.getClass();
            if ((n17 & 0x10) != 0) {
                player.posX += Math.sin(player.rotY + 1024) * this.m_view_step / 4096;
                player.posZ += Math.cos(player.rotY + 1024) * this.m_view_step / 4096;
                n = 1;
            }
        }
        int n18 = this.m_J_Dev.m_Cont;
        this.m_J_Dev.getClass();
        if ((n18 & 4) != 0) {
            player.rotX += this.m_view_rot;
        } else {
            int n19 = this.m_J_Dev.m_Cont;
            this.m_J_Dev.getClass();
            if ((n19 & 0x100) != 0) {
                player.rotX -= this.m_view_rot;
            }
        }
        int n20 = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n20 & 0x20) != 0 && this.m_scene == 2 && this.m_mode == 0) {
            this.m_mode = (byte)13;
            SoftLablesSet(4, 0);
        }
        int n21 = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n21 & 1) != 0 && player.currentWeaponID != 40 && this.m_at_frame == this.m_at_limit && this.m_player_condi[3][0] == 0) {
            this.m_at_ok = true;
            if (this.m_option[1]) {
                this.m_J_se.playMMF(4);
            }
            this.m_at_frame = 0;
            this.m_gauge_attack = this.m_gauge_value;
            this.m_gauge_value = 0;
        }
        if (player.rotY < 0) {
            player.rotY += 4096;
        } else if (player.rotY >= 4096) {
            player.rotY -= 4096;
        }
        if (player.rotX > 128) {
            player.rotX = 128;
        } else if (player.rotX < -128) {
            player.rotX = -128;
        }
        if (player.rotZ > 256) {
            player.rotZ = 256;
        } else if (player.rotZ < -256) {
            player.rotZ = -256;
        }
        if (n != 0) {
            this.m_shinpuku = 30;
            this.m_wave = 512;
            if (this.m_at_frame == this.m_at_limit) {
                ++this.m_step_count;
                if (this.m_step_count == 10) {
                    if (this.m_option[1]) {
                        this.m_J_se.playMMF(3);
                    }
                    this.m_step_count = 0;
                }
            }
        }
    }

    private synchronized void keyAct_nostage() {
        if (this.m_scene == 1) {
            this.keyTitle();
        } else if (this.m_scene == 6) {
            this.keysave();
        } else if (this.m_mode == 4) {
            this.keyCancel();
        } else if (this.m_mode == 1) {
            this.keyGets();
        } else if (this.m_mode == 6) {
            this.keyMenu();
        } else if (this.m_mode == 7) {
            this.keyItem();
        } else if (this.m_mode == 8) {
            this.keyUse();
        } else if (this.m_mode == 9) {
            this.keyEquip();
        } else if (this.m_mode == 10) {
            this.keyEqset();
        } else if (this.m_mode == 11) {
            this.keyqsave();
        } else if (this.m_mode == 13) {
            this.keyMap();
        } else if (this.m_mode == 15) {
            this.keyOpt();
        }
    }

    private void keyUPorDOWN(int n) {
        int n2 = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n2 & 0x20000) != 0) {
            this.m_cursor_now = (byte)(this.m_cursor_now - 1);
            if (this.m_cursor_now < 0) {
                this.m_cursor_now = (byte)(this.m_cursor_max[n] - 1);
            }
        } else {
            int n3 = this.m_J_Dev.m_Trg;
            this.m_J_Dev.getClass();
            if ((n3 & 0x80000) != 0) {
                this.m_cursor_now = (byte)(this.m_cursor_now + 1);
                if (this.m_cursor_now == this.m_cursor_max[n]) {
                    this.m_cursor_now = 0;
                }
            }
        }
    }

    private void keyTitle() {
        int n = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n & 0x20000) != 0) {
            if (this.m_save_data != 0) {
                this.m_cursor_now = (byte)(this.m_cursor_now - 1);
                if (this.m_save_data == 1) {
                    if (this.m_cursor_now < 0) {
                        this.m_cursor_now = (byte)2;
                    } else if (this.m_cursor_now == 1) {
                        this.m_cursor_now = 0;
                    }
                } else if (this.m_save_data == 2) {
                    if (this.m_cursor_now < 0) {
                        this.m_cursor_now = 1;
                    }
                } else if (this.m_cursor_now < 0) {
                    this.m_cursor_now = (byte)2;
                }
            }
            this.m_op_frame = 580;
        } else {
            int n2 = this.m_J_Dev.m_Trg;
            this.m_J_Dev.getClass();
            if ((n2 & 0x80000) != 0) {
                if (this.m_save_data != 0) {
                    this.m_cursor_now = (byte)(this.m_cursor_now + 1);
                    if (this.m_save_data == 1) {
                        if (this.m_cursor_now == 1) {
                            this.m_cursor_now = (byte)2;
                        } else if (this.m_cursor_now == 3) {
                            this.m_cursor_now = 0;
                        }
                    } else if (this.m_save_data == 2) {
                        if (this.m_cursor_now == 2) {
                            this.m_cursor_now = 0;
                        }
                    } else if (this.m_cursor_now == 3) {
                        this.m_cursor_now = 0;
                    }
                }
                this.m_op_frame = 580;
            } else {
                int n3 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n3 & 0x100000) != 0) {
                    this.m_starning = true;
                }
            }
        }
    }

    private void keyCancel() {
        if (this.m_J_Dev.m_Trg != 0) {
            this.m_mode = 0;
            SoftLablesSet(2, 3);
        }
    }

    private void keyGets() {
        if (!this.m_item_treasure && this.m_J_Dev.m_Trg != 0) {
            if (player.inventory[this.m_item_id] < 127) {
                int n = this.m_item_id;
                player.inventory[n] = (byte)(player.inventory[n] + 1);
            }
            if (this.m_item_style == 2) {
                this.m_taru_flag[this.m_stage_ID] = false;
            } else {
                this.m_box_frame = 0;
                this.m_box_flag[this.m_box_number[this.m_stage_ID] + this.m_openbox] = false;
            }
            if (this.m_item_id <= 14) {
                this.wepPltChange(player.currentWeaponID);
                this.rePalet();
            }
            this.m_comment_frame = 50;
            this.m_item_id = -1;
            this.m_item_posX = 0;
            this.m_item_posY = 0;
            this.m_item_posZ = 0;
            this.m_mode = 0;
            SoftLablesSet(2, 3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void keyMenu() {
        block16: {
            block15: {
                int n = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n & 0x20000) != 0) break block15;
                int n2 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n2 & 0x80000) == 0) break block16;
            }
            this.keyUPorDOWN(1);
            return;
        }
        int n = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n & 0x100000) == 0) return;
        switch (this.m_cursor_now) {
            case 0: {
                this.m_cursor_now = 0;
                this.m_array_num = 0;
                for (int i = 40; i < 50; ++i) {
                    if (player.inventory[i] <= 0) continue;
                    this.m_array[this.m_array_num] = (byte)i;
                    this.m_array_num = (byte)(this.m_array_num + 1);
                }
                this.m_cursor_max[2] = this.m_array_num;
                if (this.m_cursor_now >= this.m_cursor_max[2]) {
                    this.m_cursor_now = (byte)(this.m_cursor_max[2] - 1);
                }
                this.m_mode = (byte)7;
                this.m_cursor_place = (byte)2;
                this.m_item_select_i = 0;
                return;
            }
            case 1: {
                this.m_cursor_now = 0;
                this.m_mode = (byte)9;
                this.m_cursor_place = (byte)4;
                return;
            }
            case 2: {
                this.m_mode = (byte)13;
                return;
            }
            case 3: {
                this.m_cursor_now = 0;
                this.m_mode = (byte)15;
                this.m_cursor_place = (byte)7;
                return;
            }
            case 4: {
                this.m_mode = (byte)16;
                return;
            }
            case 5: {
                if (this.m_save_data != 2) return;
                this.m_cursor_now = 1;
                this.m_cursor_place = (byte)6;
                this.m_mode = (byte)11;
                this.m_end_select = 0;
                return;
            }
            case 6: {
                this.m_cursor_now = 1;
                this.m_cursor_place = (byte)6;
                this.m_mode = (byte)11;
                this.m_end_select = 1;
                return;
            }
            case 7: {
                this.m_cursor_now = 1;
                this.m_cursor_place = (byte)6;
                this.m_mode = (byte)11;
                this.m_end_select = 2;
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void keyItem() {
        block6: {
            block5: {
                if (this.m_array_num <= 0) return;
                if (this.m_cursor_now >= this.m_cursor_max[2]) {
                    this.m_cursor_now = (byte)(this.m_cursor_max[2] - 1);
                }
                int n = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n & 0x20000) != 0) break block5;
                int n2 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n2 & 0x80000) == 0) break block6;
            }
            this.keyUPorDOWN(2);
            this.m_item_select_i = this.m_cursor_now;
            return;
        }
        int n = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n & 0x100000) == 0) return;
        if (this.m_array[this.m_cursor_now] == 49) {
            this.m_mode = (byte)14;
            return;
        }
        this.m_cursor_place = (byte)3;
        this.m_cursor_now = 1;
        this.m_mode = (byte)8;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void keyUse() {
        int n;
        block30: {
            block29: {
                int n2 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n2 & 0x20000) != 0) break block29;
                int n3 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n3 & 0x80000) == 0) break block30;
            }
            this.keyUPorDOWN(3);
            return;
        }
        int n4 = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n4 & 0x100000) == 0) return;
        switch (this.m_cursor_now) {
            case 0: {
                if (this.m_array[this.m_item_select_i] >= 49) break;
                if (this.m_array[this.m_item_select_i] == 40) {
                    player.currentHP += 50;
                } else if (this.m_array[this.m_item_select_i] == 41) {
                    this.m_condi_cure[0] = true;
                } else if (this.m_array[this.m_item_select_i] == 42) {
                    player.currentHP += 100;
                    this.m_condi_cure[0] = true;
                    this.m_condi_cure[1] = true;
                } else if (this.m_array[this.m_item_select_i] == 43) {
                    player.currentHP += 150;
                    for (n = 0; n < 4; ++n) {
                        this.m_condi_cure[n] = true;
                    }
                } else if (this.m_array[this.m_item_select_i] == 44) {
                    player.currentHP += 500;
                    for (n = 0; n < 4; ++n) {
                        this.m_condi_cure[n] = true;
                    }
                } else if (this.m_array[this.m_item_select_i] == 45) {
                    n = (byte)((this.m_rand.nextInt() >>> 1) % 2 + 1);
                    player.attackBase = (byte)(player.attackBase + n);
                    this.equipSet();
                } else if (this.m_array[this.m_item_select_i] == 46) {
                    n = (byte)((this.m_rand.nextInt() >>> 1) % 2 + 1);
                    player.defenseBase = (byte)(player.defenseBase + n);
                    this.equipSet();
                } else if (this.m_array[this.m_item_select_i] == 47) {
                    n = (byte)((this.m_rand.nextInt() >>> 1) % 3 + 3);
                    player.maxHP += n;
                    this.equipSet();
                }
                if (player.attackBase > 99) {
                    player.attackBase = (byte)99;
                }
                if (player.defenseBase > 99) {
                    player.defenseBase = (byte)99;
                }
                if (player.maxHP > 999) {
                    player.maxHP = 999;
                }
                this.condiCure();
                if (player.currentHP > player.maxHP) {
                    player.currentHP = player.maxHP;
                }
                byte by = this.m_array[this.m_item_select_i];
                player.inventory[by] = (byte)(player.inventory[by] - 1);
                break;
            }
        }
        this.m_array_num = 0;
        for (n = 40; n < 50; ++n) {
            if (player.inventory[n] <= 0) continue;
            this.m_array[this.m_array_num] = (byte)n;
            this.m_array_num = (byte)(this.m_array_num + 1);
        }
        this.m_cursor_max[2] = this.m_array_num;
        if (this.m_item_select_i >= this.m_cursor_max[2]) {
            this.m_item_select_i = (byte)(this.m_cursor_max[2] - 1);
        }
        this.m_cursor_now = (byte)this.m_item_select_i;
        this.m_cursor_place = (byte)2;
        this.m_mode = (byte)7;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void keyEquip() {
        int n;
        int n2;
        block15: {
            block14: {
                n2 = 0;
                n = 0;
                int n3 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n3 & 0x20000) != 0) break block14;
                int n4 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n4 & 0x80000) == 0) break block15;
            }
            this.keyUPorDOWN(4);
            return;
        }
        int n5 = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n5 & 0x100000) == 0) return;
        this.m_array_num = 0;
        switch (this.m_cursor_now) {
            case 0: {
                n2 = 0;
                n = 14;
                this.m_player_eq = player.currentWeaponID;
                this.m_eq_now = 0;
                break;
            }
            case 1: {
                n2 = 15;
                n = 19;
                this.m_player_eq = player.currentHelmID;
                this.m_eq_now = 1;
                break;
            }
            case 2: {
                n2 = 20;
                n = 24;
                this.m_player_eq = player.currentPlateID;
                this.m_eq_now = (byte)2;
                break;
            }
            case 3: {
                n2 = 25;
                n = 29;
                this.m_player_eq = player.currentArmsID;
                this.m_eq_now = (byte)3;
                break;
            }
            case 4: {
                n2 = 30;
                n = 34;
                this.m_player_eq = player.currentLegsID;
                this.m_eq_now = (byte)4;
                break;
            }
            case 5: {
                n2 = 35;
                n = 39;
                this.m_player_eq = player.currentShieldID;
                this.m_eq_now = (byte)5;
                break;
            }
        }
        int n6 = n2;
        while (true) {
            if (n6 > n) {
                this.m_cursor_max[5] = this.m_array_num;
                this.m_cursor_now = 0;
                this.m_cursor_place = (byte)5;
                this.m_mode = (byte)10;
                return;
            }
            if (player.inventory[n6] > 0) {
                this.m_array[this.m_array_num] = (byte)n6;
                this.m_array_num = (byte)(this.m_array_num + 1);
            }
            ++n6;
        }
    }

    private void keyEqset() {
        if (this.m_array_num > 0) {
            int n = this.m_J_Dev.m_Trg;
            this.m_J_Dev.getClass();
            if ((n & 0x20000) != 0) {
                this.m_cursor_now = (byte)(this.m_cursor_now - 1);
                if (this.m_cursor_now < 0) {
                    this.m_cursor_now = (byte)(this.m_cursor_max[5] - 1);
                }
            } else {
                int n2 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n2 & 0x80000) != 0) {
                    this.m_cursor_now = (byte)(this.m_cursor_now + 1);
                    if (this.m_cursor_now == this.m_cursor_max[5]) {
                        this.m_cursor_now = 0;
                    }
                } else {
                    int n3 = this.m_J_Dev.m_Trg;
                    this.m_J_Dev.getClass();
                    if ((n3 & 0x10000) != 0) {
                        if (this.m_player_eq == player.currentWeaponID && this.m_cursor_now >= 10) {
                            this.m_cursor_now = (byte)(this.m_cursor_now - 10);
                        }
                    } else {
                        int n4 = this.m_J_Dev.m_Trg;
                        this.m_J_Dev.getClass();
                        if ((n4 & 0x40000) != 0) {
                            if (this.m_player_eq == player.currentWeaponID && this.m_cursor_now < 10) {
                                this.m_cursor_now = (byte)(this.m_cursor_now + 10);
                                if (this.m_cursor_now >= this.m_cursor_max[5]) {
                                    this.m_cursor_now = (byte)(this.m_cursor_max[5] - 1);
                                }
                            }
                        } else {
                            int n5 = this.m_J_Dev.m_Trg;
                            this.m_J_Dev.getClass();
                            if ((n5 & 0x100000) != 0) {
                                if (this.m_eq_now == 0) {
                                    player.currentWeaponID = player.currentWeaponID == this.m_array[this.m_cursor_now] ? (byte)40 : this.m_array[this.m_cursor_now];
                                    this.setWepFigure();
                                } else if (this.m_eq_now == 1) {
                                    player.currentHelmID = player.currentHelmID == this.m_array[this.m_cursor_now] ? (byte)40 : this.m_array[this.m_cursor_now];
                                } else if (this.m_eq_now == 2) {
                                    player.currentPlateID = player.currentPlateID == this.m_array[this.m_cursor_now] ? (byte)40 : this.m_array[this.m_cursor_now];
                                } else if (this.m_eq_now == 3) {
                                    player.currentArmsID = player.currentArmsID == this.m_array[this.m_cursor_now] ? (byte)40 : this.m_array[this.m_cursor_now];
                                } else if (this.m_eq_now == 4) {
                                    player.currentLegsID = player.currentLegsID == this.m_array[this.m_cursor_now] ? (byte)40 : this.m_array[this.m_cursor_now];
                                } else {
                                    player.currentShieldID = player.currentShieldID == this.m_array[this.m_cursor_now] ? (byte)40 : this.m_array[this.m_cursor_now];
                                }
                                this.equipSet();
                                this.wepPltChange(player.currentWeaponID);
                                this.rePalet();
                                this.m_cursor_now = 0;
                                this.m_cursor_place = (byte)4;
                                this.m_mode = (byte)9;
                            }
                        }
                    }
                }
            }
        }
    }

    private void wepPltChange(int n) {
        this.m_plt_now[1] = (byte)(n == 0 ? 0 : (n == 1 ? 1 : (n == 2 ? 2 : (n == 3 ? 3 : (n == 4 ? 4 : (n == 5 ? 2 : (n == 6 ? 2 : (n == 7 ? 4 : (n == 8 ? 3 : (n == 9 ? 1 : (n == 10 ? 1 : (n == 11 ? 1 : (n == 12 ? 0 : (n == 13 ? 2 : 0))))))))))))));
    }

    private void guaPltChange(int n) {
        if (n == 15) {
            this.m_plt_now[2] = 0;
        } else if (n == 16) {
            this.m_plt_now[2] = 2;
        } else if (n == 17) {
            this.m_plt_now[2] = 1;
        } else if (n == 18) {
            this.m_plt_now[2] = 4;
        } else if (n == 19) {
            this.m_plt_now[2] = 4;
        } else if (n == 20) {
            this.m_plt_now[2] = 0;
        } else if (n == 21) {
            this.m_plt_now[2] = 2;
        } else if (n == 22) {
            this.m_plt_now[2] = 1;
        } else if (n == 23) {
            this.m_plt_now[2] = 3;
        } else if (n == 24) {
            this.m_plt_now[2] = 4;
        } else if (n == 25) {
            this.m_plt_now[2] = 0;
        } else if (n == 26) {
            this.m_plt_now[2] = 2;
        } else if (n == 27) {
            this.m_plt_now[2] = 1;
        } else if (n == 28) {
            this.m_plt_now[2] = 2;
        } else if (n == 29) {
            this.m_plt_now[2] = 4;
        } else if (n == 30) {
            this.m_plt_now[2] = 0;
        } else if (n == 31) {
            this.m_plt_now[2] = 2;
        } else if (n == 32) {
            this.m_plt_now[2] = 1;
        } else if (n == 33) {
            this.m_plt_now[2] = 2;
        } else if (n == 34) {
            this.m_plt_now[2] = 4;
        } else if (n == 35) {
            this.m_plt_now[2] = 0;
        } else if (n == 36) {
            this.m_plt_now[2] = 1;
        } else if (n == 37) {
            this.m_plt_now[2] = 2;
        } else if (n == 38) {
            this.m_plt_now[2] = 4;
        } else if (n == 39) {
            this.m_plt_now[2] = 4;
        } else if (n == 45) {
            this.m_plt_now[3] = 0;
        } else if (n == 46) {
            this.m_plt_now[3] = 1;
        } else if (n == 47) {
            this.m_plt_now[3] = 3;
        } else if (n == 49) {
            this.m_plt_now[2] = 0;
        }
    }

    private void keyMap() {
        int n = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n & 0x20) != 0) {
            this.m_mode = 0;
            SoftLablesSet(2, 3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void keyOpt() {
        block5: {
            block4: {
                int n = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n & 0x20000) != 0) break block4;
                int n2 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n2 & 0x80000) == 0) break block5;
            }
            this.keyUPorDOWN(7);
            return;
        }
        int n = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n & 0x100000) == 0) return;
        if (this.m_option[this.m_cursor_now]) {
            this.m_option[this.m_cursor_now] = false;
            if (this.m_cursor_now != 3) return;
            this.m_step_auto = 0;
            return;
        }
        this.m_option[this.m_cursor_now] = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void keysave() {
        block7: {
            block6: {
                int n = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n & 0x20000) != 0) break block6;
                int n2 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n2 & 0x80000) == 0) break block7;
            }
            this.keyUPorDOWN(6);
            return;
        }
        int n = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n & 0x100000) == 0) return;
        switch (this.m_cursor_now) {
            case 0: {
                this.m_saving = true;
                this.m_cursor_now = 0;
                this.m_cursor_place = 1;
                this.m_scene = (byte)5;
                return;
            }
            case 1: {
                this.m_saving = false;
                this.m_cursor_now = 0;
                this.m_cursor_place = 1;
                this.m_scene = (byte)5;
                return;
            }
        }
    }

    private void savetime() {
        if (this.m_saving) {
            this.savePlayerData(1);
            this.saveStageData(2);
            for (int i = 0; i < 10; ++i) {
                this.saveMapData(3 + i);
            }
            this.m_save_data = (byte)2;
        }
    }

    private void savePlayerData(int n) {
        int n2 = player.currentHP / 100;
        int n3 = player.currentHP % 100;
        int n4 = player.maxHP / 100;
        int n5 = player.maxHP % 100;
        int n6 = player.experience / 10000;
        int n7 = player.experience % 10000;
        int n8 = n7 / 100;
        int n9 = n7 % 100;
        byte by = this.m_option[0] ? (byte)0 : 1;
        byte by2 = this.m_option[1] ? (byte)0 : 1;
        byte by3 = this.m_option[2] ? (byte)0 : 1;
        byte by4 = this.m_option[3] ? (byte)0 : 1;
        int n10 = this.m_dead_count[0] / 100;
        int n11 = this.m_dead_count[0] % 100;
        int n12 = this.m_dead_count[1] / 100;
        int n13 = this.m_dead_count[1] % 100;
        int n14 = this.m_dead_count[2] / 100;
        int n15 = this.m_dead_count[2] % 100;
        int n16 = this.m_dead_count[3] / 100;
        int n17 = this.m_dead_count[3] % 100;
        this.m_data = new byte[]{player.floor, player.level, (byte)n6, (byte)n8, (byte)n9, (byte)n2, (byte)n3, (byte)n4, (byte)n5, player.attackBase, player.defenseBase, player.currentWeaponID, player.currentHelmID, player.currentPlateID, player.currentArmsID, player.currentLegsID, player.currentShieldID, this.m_player_condi[0][0], this.m_player_condi[0][1], this.m_player_condi[1][0], this.m_player_condi[1][1], this.m_player_condi[2][0], this.m_player_condi[2][1], this.m_player_condi[3][0], this.m_player_condi[3][1], player.inventory[0], player.inventory[1], player.inventory[2], player.inventory[3], player.inventory[4], player.inventory[5], player.inventory[6], player.inventory[7], player.inventory[8], player.inventory[9], player.inventory[10], player.inventory[11], player.inventory[12], player.inventory[13], player.inventory[14], player.inventory[15], player.inventory[16], player.inventory[17], player.inventory[18], player.inventory[19], player.inventory[20], player.inventory[21], player.inventory[22], player.inventory[23], player.inventory[24], player.inventory[25], player.inventory[26], player.inventory[27], player.inventory[28], player.inventory[29], player.inventory[30], player.inventory[31], player.inventory[32], player.inventory[33], player.inventory[34], player.inventory[35], player.inventory[36], player.inventory[37], player.inventory[38], player.inventory[39], player.inventory[40], player.inventory[41], player.inventory[42], player.inventory[43], player.inventory[44], player.inventory[45], player.inventory[46], player.inventory[47], player.inventory[48], player.inventory[49], by, by2, by3, by4, (byte)n10, (byte)n11, (byte)n12, (byte)n13, (byte)n14, (byte)n15, (byte)n16, (byte)n17};
        this.WriteRecord(n, null);
        // system.gc();
        this.WriteRecord(n, this.m_data);
    }

    private void saveStageData(int n) {
        int n2;
        byte[] byArray = new byte[229];
        byArray[0] = this.m_y;
        byArray[1] = this.m_room_No;
        byArray[2] = this.m_step_No;
        for (n2 = 0; n2 < 124; ++n2) {
            byArray[n2 + 3] = this.m_box_flag[n2] ? (byte)0 : 1;
        }
        for (n2 = 0; n2 < 51; ++n2) {
            byArray[n2 + 127] = this.m_door_flag[n2] ? (byte)0 : 1;
        }
        for (n2 = 0; n2 < 51; ++n2) {
            byArray[n2 + 178] = this.m_taru_flag[n2] ? (byte)0 : 1;
        }
        this.WriteRecord(n, null);
        // system.gc();
        this.WriteRecord(n, byArray);
    }

    private void saveMapData(int n) {
        this.m_data = new byte[this.m_grid_all];
        int n2 = n <= 15 ? n - 3 : n - 18;
        for (int i = 0; i < this.m_grid_all; ++i) {
            this.m_data[i] = this.m_map_memory[n2][i] ? (byte)0 : 1;
        }
        this.WriteRecord(n, null);
        // system.gc();
        this.WriteRecord(n, this.m_data);
    }

    private void saveOthers1(int n) {
        this.m_data = new byte[]{(byte)(this.m_player_grid / 100), (byte)(this.m_player_grid % 100), (byte)(player.rotX / 100), (byte)(player.rotX % 100), (byte)(player.rotY / 100), (byte)(player.rotY % 100), this.m_player_dire};
        this.WriteRecord(n, null);
        // system.gc();
        this.WriteRecord(n, this.m_data);
    }

    private void saveOthers2(int n) {
        this.m_data = new byte[this.m_enemy_num * 7];
        for (int i = 0; i < this.m_enemy_num; ++i) {
            this.m_data[i * 7 + 0] = (byte)(this.m_enemy[i][9] / 100);
            this.m_data[i * 7 + 1] = (byte)(this.m_enemy[i][9] % 100);
            this.m_data[i * 7 + 2] = (byte)(this.m_enemy[i][5] / 100);
            this.m_data[i * 7 + 3] = (byte)(this.m_enemy[i][5] % 100);
            this.m_data[i * 7 + 4] = (byte)(this.m_enemy[i][3] / 100);
            this.m_data[i * 7 + 5] = (byte)(this.m_enemy[i][3] % 100);
            this.m_data[i * 7 + 6] = (byte)this.m_enemy[i][10];
        }
        this.WriteRecord(n, null);
        // system.gc();
        this.WriteRecord(n, this.m_data);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void keyqsave() {
        block5: {
            block4: {
                int n = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n & 0x20000) != 0) break block4;
                int n2 = this.m_J_Dev.m_Trg;
                this.m_J_Dev.getClass();
                if ((n2 & 0x80000) == 0) break block5;
            }
            this.keyUPorDOWN(6);
            return;
        }
        int n = this.m_J_Dev.m_Trg;
        this.m_J_Dev.getClass();
        if ((n & 0x100000) == 0) return;
        if (this.m_cursor_now == 0) {
            this.m_mode = (byte)12;
            return;
        }
        this.m_cursor_now = 0;
        this.m_cursor_place = 1;
        this.m_mode = (byte)6;
    }

    private void qsavetime() {
        if (this.m_end_select == 0) {
            this.init_load1(1, 2, 3);
            this.m_warp = 80;
            this.m_stair_right = false;
            this.m_scene = (byte)5;
            this.m_mode = 0;
        } else {
            if (this.m_end_select == 1) {
                this.savePlayerData(16);
                this.saveStageData(17);
                for (int i = 0; i < 10; ++i) {
                    this.saveMapData(18 + i);
                }
                this.saveOthers1(31);
                this.saveOthers2(32);
            }
            this.m_roop = false;
        }
    }

    private void everyReload() {
        this.m_theta += this.m_wave;
        if (this.m_theta >= 4096) {
            this.m_theta -= 4096;
        }
        playerDire();
        playerData();
        this.condiEffect();
        this.condiCure();
        this.posThroughBack();
    }

    private void playerDire() {
        if (player.rotY < 0) {
            player.rotY += 4096;
        } else if (player.rotY >= 4096) {
            player.rotY -= 4096;
        }
        this.m_player_dire = player.rotY >= 512 && player.rotY < 1536 ? (byte)1 : (player.rotY >= 1536 && player.rotY < 2560 ? (byte)2 : (player.rotY >= 2560 && player.rotY < 3584 ? (byte)3 : (byte)0));
    }

    private void playerData() {
        int n;
        int n2;
        int n3;
        this.m_player_grid = player.posZ / 1000 * this.m_grid_x + player.posX / 1000;
        if (this.m_player_grid != this.m_past_grid || this.m_player_dire != this.m_past_dire) {
            this.m_stand_by = false;
            for (n3 = 0; n3 < this.m_shutter_num; ++n3) {
                n2 = this.gridDistance(this.m_player_grid, this.m_shutter[n3][0], 1);
                n = this.gridDistance(this.m_player_grid, this.m_shutter[n3][0], 2);
                if (this.m_block_exist[this.m_shutter[n3][0]] != 4 || n2 + n > 1) continue;
                this.m_stand_by = true;
                this.m_shutter_id = n3;
            }
            this.viewWaterArea();
            this.mapWaterArea();
            if (this.m_player_grid != this.m_past_grid && this.m_block_level[this.m_player_grid] == -200) {
                this.m_player_damaged = true;
                player.currentHP -= 15;
            }
        }
        this.m_past_grid = this.m_player_grid;
        this.m_past_dire = this.m_player_dire;
        if (this.m_scene != 1 && this.m_scene != 7) {
            n3 = this.m_block_level[this.m_player_grid] + 650;
            player.posY -= 100;
            if (this.m_block_level[this.m_player_grid] >= -500) {
                if (player.posY < n3) {
                    player.posY = n3;
                }
                if (this.m_option[2]) {
                    player.posY += Math.sin(this.m_theta) * this.m_shinpuku / 4096;
                }
            } else {
                player.rotX -= this.m_view_rot;
                if (player.rotX < -512) {
                    player.rotX = -512;
                }
                this.m_at_frame = this.m_at_limit;
            }
        }
        if (this.m_gauge_value < this.m_gauge_max && this.m_at_frame == this.m_at_limit) {
            this.m_gauge_value = (byte)(this.m_gauge_value + 1);
        }
        for (n3 = 0; n3 < this.m_shift_num; ++n3) {
            if (this.m_player_grid != this.grid(this.m_shift[n3][0], this.m_shift[n3][1])) continue;
            this.m_shift_now = n3;
            this.m_before_room = this.m_room_No;
            this.m_room_No = this.m_shift[n3][2];
            this.m_y = 0;
            this.m_warp = 0;
            this.m_scene = (byte)5;
            SoftLabelsClear();
        }
        n = 0;
        boolean bl = false;
        if (this.m_stair_right) {
            if (this.m_player_grid == this.m_start_grid) {
                if (this.m_step_No != 1 && this.askValue2(player.posX - (n3 = this.m_start_grid % this.m_grid_x * 1000 + 500), player.posZ - (n2 = this.m_start_grid / this.m_grid_x * 1000 + 500)) <= 500) {
                    this.m_warp_posX = player.posX;
                    this.m_warp_posY = 0;
                    this.m_warp_posZ = player.posZ;
                    this.m_y = 1;
                    this.m_warp = 0;
                    this.m_stair_right = false;
                    this.m_scene = (byte)5;
                    this.m_mode = 0;
                    SoftLabelsClear();
                }
            } else if (this.m_player_grid == this.m_goal_grid && !this.m_boss_live && this.askValue2(player.posX - (n3 = this.m_goal_grid % this.m_grid_x * 1000 + 500), player.posZ - (n2 = this.m_goal_grid / this.m_grid_x * 1000 + 500)) <= 500) {
                this.m_warp_posX = player.posX;
                this.m_warp_posY = 0;
                this.m_warp_posZ = player.posZ;
                this.m_y = (byte)2;
                this.m_warp = 0;
                this.m_stair_right = false;
                this.m_scene = (byte)5;
                this.m_mode = 0;
                SoftLabelsClear();
            }
        } else if (this.m_player_grid != this.m_start_grid && this.m_player_grid != this.m_goal_grid) {
            this.m_stair_right = true;
        }

        // Handle Player Level Up...
        if (player.level > 0 && player.experience >= KingsField_Player.ExperienceGraph[player.level]) 
        {
            player.level = (byte)(player.level + 1);
            player.attackBase = (byte)(player.attackBase + 1);
            player.defenseBase = (byte)(player.defenseBase + 1);
            player.maxHP += player.level + 1;
            if (player.attackBase > 99) {
                player.attackBase = (byte)99;
            }
            if (player.defenseBase > 99) {
                player.defenseBase = (byte)99;
            }
            if (player.maxHP > 999) {
                player.maxHP = 999;
            }
            this.equipSet();
            if (this.m_comment_frame < 50) 
            {
                this.m_add_comment = true;
                this.m_add_comment_text = "LEVEL UP!";
            } 
            else 
            {
                this.m_comment_text = "LEVEL UP!";
                this.m_comment_frame = 0;
            }
        }

        // Handle Player Death
        if (player.currentHP <= 0 || player.posY <= -4000) 
        {
            if (this.m_option[1]) {
                this.m_J_se.playMMF(2);
            }
            player.currentHP = 0;
            this.m_player_damaged = false;
            this.m_wave = 0;
            this.m_scene = (byte)3;
            SoftLablesSet(0, 1);
        }
    }

    private void condiEffect() {
        this.m_condi_count = (byte)(this.m_condi_count + 1);
        if (this.m_condi_count == 100) {
            this.m_condi_count = 0;
            if (this.m_player_condi[0][0] == 1) {
                byte[] byArray = this.m_player_condi[0];
                byArray[1] = (byte)(byArray[1] + 1);
                player.currentHP -= player.maxHP * 5 / 100;
                this.m_player_damaged = true;
                if (this.m_player_condi[0][1] >= 5) {
                    this.m_condi_cure[0] = true;
                }
            }
            if (this.m_player_condi[2][0] == 1) {
                byte[] byArray = this.m_player_condi[2];
                byArray[1] = (byte)(byArray[1] + 1);
                this.m_view_area = 3;
                if (this.m_player_condi[2][1] >= 5) {
                    this.m_condi_cure[2] = true;
                }
            }
            if (this.m_player_condi[1][0] == 1) {
                byte[] byArray = this.m_player_condi[1];
                byArray[1] = (byte)(byArray[1] + 1);
                this.m_view_step = 25;
                this.m_view_rot = 10;
                if (this.m_player_condi[1][1] >= 5) {
                    this.m_condi_cure[1] = true;
                }
            }
            if (this.m_player_condi[3][0] == 1) {
                byte[] byArray = this.m_player_condi[3];
                byArray[1] = (byte)(byArray[1] + 1);
                this.m_at_frame = this.m_at_limit;
                if (this.m_player_condi[3][1] >= 5) {
                    this.m_condi_cure[3] = true;
                }
            }
        }
    }

    private void condiCure() 
    {
        if (this.m_condi_cure[0]) {
            this.m_player_condi[0][0] = 0;
            this.m_player_condi[0][1] = 0;
            this.m_condi_cure[0] = false;
        }
        if (this.m_condi_cure[2]) {
            this.m_player_condi[2][0] = 0;
            this.m_player_condi[2][1] = 0;
            this.m_condi_cure[2] = false;
            this.m_view_area = 6;
            this.viewWaterArea();
        }
        if (this.m_condi_cure[1]) {
            this.m_player_condi[1][0] = 0;
            this.m_player_condi[1][1] = 0;
            this.m_condi_cure[1] = false;
            this.m_view_step = 50;
            this.m_view_rot = 20;
        }
        if (this.m_condi_cure[3]) {
            this.m_player_condi[3][0] = 0;
            this.m_player_condi[3][1] = 0;
            this.m_condi_cure[3] = false;
        }
    }

    private void posThroughBack() {
        int n = player.rotY;
        for (int i = 0; i < this.m_cover_num; ++i) {
            int n2 = player.rotX + 768;
            int n3 = player.rotX - 768;
            int n4 = Math.sin(n - 1024) * 2000 / 4096;
            int n5 = Math.cos(n - 1024) * 2000 / 4096;
            int n6 = Math.sin(n + 1024) * 2000 / 4096;
            int n7 = Math.cos(n + 1024) * 2000 / 4096;
            int n8 = (Math.sin(n) * Math.cos(n2) * 10 + i) / 168100;
            int n9 = Math.sin(n2) * 1000 / 4096;
            int n10 = (Math.cos(n) * Math.cos(n2) * 10 + i) / 168100;
            int n11 = (Math.sin(n) * Math.cos(n3) * 10 + i) / 168100;
            int n12 = Math.sin(n3) * 1000 / 4096;
            int n13 = (Math.cos(n) * Math.cos(n3) * 10 + i) / 168100;
            this.m_cover_ver[i] = new int[]{player.posX + n4 + n8, player.posY + n9, player.posZ + n5 + n10, player.posX + n4 + n11, player.posY + n12, player.posZ + n5 + n13, player.posX + n6 + n11, player.posY + n12, player.posZ + n7 + n13, player.posX + n6 + n8, player.posY + n9, player.posZ + n7 + n10};
        }
    }

    private synchronized void viewWaterArea() {
        int n;
        for (int i = 0; i < this.m_grid_all; ++i) {
            this.m_player_area[i] = false;
        }
        this.m_grid_start_num = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.m_grid_start[0][0] = this.m_player_grid;
        this.m_player_area[this.m_player_grid] = true;
        boolean bl = false;
        int n2 = 0;
        boolean bl2 = false;
        boolean bl3 = true;
        boolean bl4 = true;
        for (n = 0; n < 4; ++n) {
            this.m_poly_num[n] = 0;
        }
        for (n = 0; n < this.m_view_area * 2 + 1; ++n) {
            for (int i = 0; i < this.m_grid_start_num[n]; ++i) {
                int n3;
                n2 = this.m_grid_start[n][i];
                if (this.m_player_dire == 0 || this.m_player_dire == 2) {
                    if (n2 % this.m_grid_x >= this.m_player_grid % this.m_grid_x) {
                        this.makeSideX(n2, 1, n);
                    }
                    if (n2 % this.m_grid_x <= this.m_player_grid % this.m_grid_x) {
                        this.makeSideX(n2, 0, n);
                    }
                    if (this.m_player_dire == 0) {
                        this.makeSideZ(n2, 3, n);
                    } else {
                        this.makeSideZ(n2, 2, n);
                    }
                } else {
                    if (n2 / this.m_grid_x >= this.m_player_grid / this.m_grid_x) {
                        this.makeSideZ(n2, 3, n);
                    }
                    if (n2 / this.m_grid_x <= this.m_player_grid / this.m_grid_x) {
                        this.makeSideZ(n2, 2, n);
                    }
                    if (this.m_player_dire == 1) {
                        this.makeSideX(n2, 1, n);
                    } else {
                        this.makeSideX(n2, 0, n);
                    }
                }
                if (this.m_block_exist[n2] <= 3) {
                    n3 = this.m_block_exist[n2] == 3 ? 1200 : 2400;
                    this.makePolygonFlat(n2, n3);
                }
                if (this.m_block_level[n2] == 0 || this.m_block_level[n2] == -200) {
                    n3 = this.m_block_level[n2] == 0 ? 0 : -200;
                    this.makePolygonFlat(n2, n3);
                }
                if (this.m_mode == 3 || n2 != this.m_start_grid && n2 != this.m_goal_grid) continue;
                this.m_warp_posX = n2 % this.m_grid_x * 1000 + 500;
                this.m_warp_posY = 0;
                this.m_warp_posZ = n2 / this.m_grid_x * 1000 + 500;
            }
        }
    }

    private void makeSideX(int n, int n2, int n3) {
        int n4 = this.gridDistance(n, this.m_player_grid, 0);
        int n5 = n2 == 1 ? n + 1 : n - 1;
        if (n5 >= 0 && n5 <= this.m_grid_all && n / this.m_grid_x == n5 / this.m_grid_x) {
            if (this.m_block_exist[n5] == 2) {
                if (this.m_block_exist[n] != 4) {
                    this.makePolygonX(n, n2, 0, 1200);
                }
                if (this.m_block_exist[n] != 3) {
                    this.makePolygonX(n, n2, 1200, 2400);
                }
            } else if (this.m_block_exist[n5] == 1) {
                this.makePolygonX(n, n2, 0, 1200);
                if (this.m_block_exist[n] != 3) {
                    this.makePolygonX(n, n2, 1200, 2400);
                }
            } else if (!this.m_player_area[n5]) {
                if (this.m_block_exist[n5] == 3 && this.m_block_exist[n] == 0) {
                    this.makePolygonX(n, n2, 1200, 2400);
                }
                if (this.m_block_exist[n5] == 4) {
                    this.makePolygonX(n, n2, 1200, 2400);
                    if (this.m_mode != 2) {
                        this.makePolygonX(n, n2, 0, 1200);
                    }
                }
                if (n4 < this.m_view_area) {
                    this.m_grid_start[n3 + 1][this.m_grid_start_num[n3 + 1]] = n5;
                    int n6 = n3 + 1;
                    this.m_grid_start_num[n6] = this.m_grid_start_num[n6] + 1;
                    this.m_player_area[n5] = true;
                }
            } else if (this.m_block_exist[n5] >= 3 && this.m_block_exist[n] == 0) {
                this.makePolygonX(n, n2, 1200, 2400);
            }
            if (this.m_block_level[n] < 0 && (this.m_block_level[n5] == 0 || this.m_block_level[n5] == -200) && this.m_block_level[n] != this.m_block_level[n5]) {
                int n7 = this.m_block_level[n] == -200 ? -200 : -1200;
                int n8 = this.m_block_level[n5] > 0 ? 0 : this.m_block_level[n5];
                this.makePolygonX(n, n2, n7, n8);
            }
        }
    }

    private void makeSideZ(int n, int n2, int n3) {
        int n4 = this.gridDistance(n, this.m_player_grid, 0);
        int n5 = n2 == 3 ? n + this.m_grid_x : n - this.m_grid_x;
        if (n5 >= 0 && n5 <= this.m_grid_all) {
            if (this.m_block_exist[n5] == 2) {
                if (this.m_block_exist[n] != 4) {
                    this.makePolygonZ(n, n2, 0, 1200);
                }
                if (this.m_block_exist[n] != 3) {
                    this.makePolygonZ(n, n2, 1200, 2400);
                }
            } else if (this.m_block_exist[n5] == 1 && this.m_block_exist[n] == 0) {
                this.makePolygonZ(n, n2, 0, 1200);
                if (this.m_block_exist[n] == 0) {
                    this.makePolygonZ(n, n2, 1200, 2400);
                }
            } else if (!this.m_player_area[n5]) {
                if (this.m_block_exist[n5] == 3 && this.m_block_exist[n] == 0) {
                    this.makePolygonZ(n, n2, 1200, 2400);
                }
                if (this.m_block_exist[n5] == 4) {
                    this.makePolygonZ(n, n2, 1200, 2400);
                    if (this.m_mode != 2) {
                        this.makePolygonZ(n, n2, 0, 1200);
                    }
                }
                if (n4 < this.m_view_area) {
                    this.m_grid_start[n3 + 1][this.m_grid_start_num[n3 + 1]] = n5;
                    int n6 = n3 + 1;
                    this.m_grid_start_num[n6] = this.m_grid_start_num[n6] + 1;
                    this.m_player_area[n5] = true;
                }
            } else if (this.m_block_exist[n5] >= 3 && this.m_block_exist[n] == 0) {
                this.makePolygonZ(n, n2, 1200, 2400);
            }
            if (this.m_block_level[n] < 0 && (this.m_block_level[n5] == 0 || this.m_block_level[n5] == -200) && this.m_block_level[n] != this.m_block_level[n5]) {
                int n7 = this.m_block_level[n] == -200 ? -200 : -1200;
                int n8 = this.m_block_level[n5] > 0 ? 0 : this.m_block_level[n5];
                this.makePolygonZ(n, n2, n7, n8);
            }
        }
    }

    private void makePolygonFlat(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8 = this.gridDistance(n, this.m_player_grid, 0);
        int n9 = 1000 * (n % this.m_grid_x);
        int n10 = 1000 * (n / this.m_grid_x);
        if (n2 == -200) {
            n7 = 0;
            n6 = 7;
        } else if (n2 == 0) {
            n7 = 1;
            n6 = n == this.m_start_grid || n == this.m_goal_grid ? 6 : (this.m_player_condi[2][0] == 1 ? n8 : n8 - 3);
        } else {
            n7 = 2;
            n6 = this.m_player_condi[2][0] == 1 ? n8 : n8 - 3;
        }
        if (n6 < 0) {
            n6 = 0;
        }
        if (this.m_shutter_draw) {
            n7 = 3;
        }
        if (n8 <= 1) {
            n5 = 4;
            n4 = 500;
            n3 = 31;
        } else {
            n5 = 1;
            n4 = 1000;
            n3 = 63;
        }
        for (int i = 0; i < n5; ++i) {
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 0] = n9 + i % 2 * n4;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 1] = n2;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 2] = n10 + i / 2 * n4;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 3] = n9 + (i % 2 + 1) * n4;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 4] = n2;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 5] = n10 + i / 2 * n4;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 6] = n9 + (i % 2 + 1) * n4;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 7] = n2;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 8] = n10 + (i / 2 + 1) * n4;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 9] = n9 + i % 2 * n4;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 10] = n2;
            this.m_poly_ver[n7][this.m_poly_num[n7] * 12 + 11] = n10 + (i / 2 + 1) * n4;
            this.m_uv[n7][this.m_poly_num[n7] * 8 + 0] = this.m_uv_corner[n6][0] + i % 2 * 32;
            this.m_uv[n7][this.m_poly_num[n7] * 8 + 1] = this.m_uv_corner[n6][1] + i / 2 * 32;
            this.m_uv[n7][this.m_poly_num[n7] * 8 + 2] = this.m_uv_corner[n6][0] + i % 2 * 32 + n3;
            this.m_uv[n7][this.m_poly_num[n7] * 8 + 3] = this.m_uv_corner[n6][1] + i / 2 * 32;
            this.m_uv[n7][this.m_poly_num[n7] * 8 + 4] = this.m_uv_corner[n6][0] + i % 2 * 32 + n3;
            this.m_uv[n7][this.m_poly_num[n7] * 8 + 5] = this.m_uv_corner[n6][1] + i / 2 * 32 + n3;
            this.m_uv[n7][this.m_poly_num[n7] * 8 + 6] = this.m_uv_corner[n6][0] + i % 2 * 32;
            this.m_uv[n7][this.m_poly_num[n7] * 8 + 7] = this.m_uv_corner[n6][1] + i / 2 * 32 + n3;
            int n11 = n7;
            this.m_poly_num[n11] = this.m_poly_num[n11] + 1;
        }
    }

    public void makePolygonX(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10 = this.gridDistance(n, this.m_player_grid, 0);
        int n11 = n2 == 1 ? 1000 * (n % this.m_grid_x + 1) : 1000 * (n % this.m_grid_x);
        int n12 = 1000 * (n / this.m_grid_x + 1);
        int n13 = n3 < 0 ? 0 : 2;
        if (this.m_shutter_draw) {
            n13 = 3;
        }
        if ((n9 = this.m_player_condi[2][0] == 1 ? n10 + 1 : n10 - 2) < 1) {
            n9 = 1;
        }
        if (n10 <= 1) {
            n8 = 4;
            n7 = 500;
            n6 = (n4 - n3) / 2;
            n5 = 31;
        } else {
            n8 = 1;
            n7 = 1000;
            n6 = n4 - n3;
            n5 = 63;
        }
        for (int i = 0; i < n8; ++i) {
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 0] = n11;
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 1] = n4 - n6 * (i / 2);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 2] = n12 - n7 * (i % 2);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 3] = n11;
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 4] = n4 - n6 * (i / 2);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 5] = n12 - n7 * (i % 2 + 1);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 6] = n11;
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 7] = n4 - n6 * (i / 2 + 1);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 8] = n12 - n7 * (i % 2 + 1);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 9] = n11;
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 10] = n4 - n6 * (i / 2 + 1);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 11] = n12 - n7 * (i % 2);
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 0] = this.m_uv_corner[n9][0] + i % 2 * 32;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 1] = this.m_uv_corner[n9][1] + i / 2 * 32;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 2] = this.m_uv_corner[n9][0] + i % 2 * 32 + n5;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 3] = this.m_uv_corner[n9][1] + i / 2 * 32;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 4] = this.m_uv_corner[n9][0] + i % 2 * 32 + n5;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 5] = this.m_uv_corner[n9][1] + i / 2 * 32 + n5;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 6] = this.m_uv_corner[n9][0] + i % 2 * 32;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 7] = this.m_uv_corner[n9][1] + i / 2 * 32 + n5;
            int n14 = n13;
            this.m_poly_num[n14] = this.m_poly_num[n14] + 1;
        }
    }

    public void makePolygonZ(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10 = this.gridDistance(n, this.m_player_grid, 0);
        int n11 = n2 == 3 ? 1000 * (n / this.m_grid_x + 1) : 1000 * (n / this.m_grid_x);
        int n12 = 1000 * (n % this.m_grid_x);
        int n13 = n3 < 0 ? 0 : 2;
        if (this.m_shutter_draw) {
            n13 = 3;
        }
        if ((n9 = this.m_player_condi[2][0] == 1 ? n10 + 2 : n10 - 1) < 2) {
            n9 = 2;
        }
        if (n10 <= 1) {
            n8 = 4;
            n7 = 500;
            n6 = (n4 - n3) / 2;
            n5 = 31;
        } else {
            n8 = 1;
            n7 = 1000;
            n6 = n4 - n3;
            n5 = 63;
        }
        for (int i = 0; i < n8; ++i) {
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 0] = n12 + n7 * (i % 2);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 1] = n4 - n6 * (i / 2);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 2] = n11;
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 3] = n12 + n7 * (i % 2 + 1);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 4] = n4 - n6 * (i / 2);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 5] = n11;
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 6] = n12 + n7 * (i % 2 + 1);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 7] = n4 - n6 * (i / 2 + 1);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 8] = n11;
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 9] = n12 + n7 * (i % 2);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 10] = n4 - n6 * (i / 2 + 1);
            this.m_poly_ver[n13][this.m_poly_num[n13] * 12 + 11] = n11;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 0] = this.m_uv_corner[n9][0] + i % 2 * 32;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 1] = this.m_uv_corner[n9][1] + i / 2 * 32;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 2] = this.m_uv_corner[n9][0] + i % 2 * 32 + n5;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 3] = this.m_uv_corner[n9][1] + i / 2 * 32;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 4] = this.m_uv_corner[n9][0] + i % 2 * 32 + n5;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 5] = this.m_uv_corner[n9][1] + i / 2 * 32 + n5;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 6] = this.m_uv_corner[n9][0] + i % 2 * 32;
            this.m_uv[n13][this.m_poly_num[n13] * 8 + 7] = this.m_uv_corner[n9][1] + i / 2 * 32 + n5;
            int n14 = n13;
            this.m_poly_num[n14] = this.m_poly_num[n14] + 1;
        }
    }

    public void mapWaterArea() {
        for (int i = 0; i < this.m_grid_all; ++i) {
            this.m_map_area[i] = false;
        }
        int[] nArray = new int[]{1, 0, 0, 0, 0, 0};
        int[][] nArray2 = new int[this.m_view_area][20];
        nArray2[0] = new int[]{this.m_player_grid, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.m_map_area[this.m_player_grid] = true;
        this.mapCheck(this.m_player_grid);
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < this.m_view_area; ++i) {
            for (int j = 0; j < nArray[i]; ++j) {
                n = nArray2[i][j];
                if (j == 0) {
                    n2 = 0;
                }
                for (int k = 0; k < 4; ++k) {
                    int n3 = k == 0 ? n - 1 : (k == 1 ? n + 1 : (k == 2 ? n - this.m_grid_x : n + this.m_grid_x));
                    if (n < this.m_grid_x || n >= this.m_grid_all - this.m_grid_x || n % this.m_grid_x == 0 || n % this.m_grid_x == this.m_grid_x - 1) continue;
                    this.mapCheck(n3);
                    if (this.m_block_exist[n3] == 2 || this.m_block_exist[n3] == 1 || this.m_block_exist[n3] == 4) {
                        if (k == 0 || k == 1) {
                            this.mapCheck(n3 - this.m_grid_x);
                            this.mapCheck(n3 + this.m_grid_x);
                            continue;
                        }
                        this.mapCheck(n3 - 1);
                        this.mapCheck(n3 + 1);
                        continue;
                    }
                    if (this.m_map_area[n3] || i >= this.m_view_area - 1) continue;
                    nArray2[i + 1][nArray[i + 1]] = n3;
                    this.m_map_area[n3] = true;
                    ++n2;
                    int n4 = i + 1;
                    nArray[n4] = nArray[n4] + 1;
                }
            }
        }
    }

    private void mapCheck(int n) {
        if (this.m_map_ID >= 0) {
            this.m_map_info[n] = (byte)(this.m_block_exist[n] == 2 ? 3 : (this.m_block_exist[n] == 1 ? 3 : (this.m_block_exist[n] == 4 ? 3 : (this.m_block_exist[n] == 3 ? 4 : (this.m_block_level[n] == -1 ? 6 : (this.m_block_level[n] == -200 ? 5 : (this.m_block_level[n] < -200 ? 6 : 4)))))));
            this.m_map_memory[this.m_map_ID][n] = true;
        }
    }

    private void enemyArea(int n) {
        this.m_enemy[n][9] = this.m_enemy[n][2] / 1000 * this.m_grid_x + this.m_enemy[n][0] / 1000;
        this.m_enemy[n][6] = this.askValue2(player.posX - this.m_enemy[n][0], player.posZ - this.m_enemy[n][2]);
        if (!this.BossOrNot(n) && this.m_enemy[n][10] == 3 && this.gridDistance(this.m_player_grid, this.m_enemy[n][9], 0) >= 15) {
            this.m_enemy[n][5] = this.m_enemyID_life[this.m_enemy[n][4]];
            this.m_enemy[n][10] = 1;
            this.m_enemy[n][7] = 2;
            this.m_enemy[n][8] = 0;
        }
        if (this.m_player_area[this.m_enemy[n][9]]) {
            if (this.m_enemy[n][10] == 1) {
                this.m_enemy[n][10] = 0;
            }
        } else if (this.m_enemy[n][10] != 3) {
            this.m_enemy[n][10] = 1;
        }
    }

    private void enemyEye(int n) {
        boolean bl = false;
        if (this.m_enemy[n][10] == 0 && this.gridDistance(this.m_enemy[n][9], this.m_player_grid, 0) <= this.m_enemyID_view[this.m_enemy[n][4]]) {
            this.m_enemy[n][10] = 0;
            int n2 = this.m_player_grid % this.m_grid_x;
            int n3 = this.m_player_grid / this.m_grid_x;
            int n4 = this.m_enemy[n][9] % this.m_grid_x;
            int n5 = this.m_enemy[n][9] / this.m_grid_x;
            if (this.m_enemy[n][3] > 256 && this.m_enemy[n][3] <= 768) {
                int n6 = n2 - n4;
                int n7 = n3 - n5;
                if (n6 >= 0 && n7 >= 0) {
                    bl = true;
                }
            } else if (this.m_enemy[n][3] > 768 && this.m_enemy[n][3] <= 1280) {
                int n8 = n2 - n4;
                int n9 = this.gridDistance(this.m_enemy[n][9], this.m_player_grid, 2);
                if (n8 >= n9) {
                    bl = true;
                }
            } else if (this.m_enemy[n][3] > 1280 && this.m_enemy[n][3] <= 1792) {
                int n10 = n2 - n4;
                int n11 = n5 - n3;
                if (n10 >= 0 && n11 >= 0) {
                    bl = true;
                }
            } else if (this.m_enemy[n][3] > 1792 && this.m_enemy[n][3] <= 2304) {
                int n12 = n5 - n3;
                int n13 = this.gridDistance(this.m_enemy[n][9], this.m_player_grid, 1);
                if (n12 >= n13) {
                    bl = true;
                }
            } else if (this.m_enemy[n][3] > 2304 && this.m_enemy[n][3] <= 2816) {
                int n14 = n4 - n2;
                int n15 = n5 - n3;
                if (n14 >= 0 && n15 >= 0) {
                    bl = true;
                }
            } else if (this.m_enemy[n][3] > 2816 && this.m_enemy[n][3] <= 3328) {
                int n16 = n4 - n2;
                int n17 = this.gridDistance(this.m_enemy[n][9], this.m_player_grid, 2);
                if (n16 >= n17) {
                    bl = true;
                }
            } else if (this.m_enemy[n][3] > 3328 && this.m_enemy[n][3] <= 3840) {
                int n18 = n4 - n2;
                int n19 = n3 - n5;
                if (n18 >= 0 && n19 >= 0) {
                    bl = true;
                }
            } else {
                int n20 = n3 - n5;
                int n21 = this.gridDistance(this.m_enemy[n][9], this.m_player_grid, 1);
                if (n20 >= n21) {
                    bl = true;
                }
            }
        }
        if (bl) {
            this.m_enemy[n][10] = 2;
        }
    }

    private void enemyDirect(int n, boolean bl) {
        boolean bl2 = false;
        int n2 = 0;
        n2 = this.m_enemyID_speed[this.m_enemy[n][4]] == 0 ? 8 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 1 ? 10 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 2 ? 12 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 3 ? 14 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 4 ? 16 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 5 ? 18 : 20)))));
        if (bl) {
            bl2 = true;
        } else {
            if (this.m_enemy[n][7] == 2 && this.m_enemy[n][10] == 2) {
                bl2 = true;
            }
            if (this.m_enemy[n][7] == 7) {
                bl2 = true;
            }
        }
        if (this.m_enemy[n][4] / 5 == 3 && (this.m_enemy[n][7] == 5 || this.m_enemy[n][7] == 6)) {
            bl2 = false;
        }
        if (bl2) {
            this.shortDire(n);
            if (this.m_enemy[n][13] == 0) {
                int[] nArray = this.m_enemy[n];
                nArray[3] = nArray[3] + n2;
            } else {
                int[] nArray = this.m_enemy[n];
                nArray[3] = nArray[3] - n2;
            }
        }
        if (this.m_enemy[n][3] >= 4096) {
            int[] nArray = this.m_enemy[n];
            nArray[3] = nArray[3] - 4096;
        }
        if (this.m_enemy[n][3] < 0) {
            int[] nArray = this.m_enemy[n];
            nArray[3] = nArray[3] + 4096;
        }
    }

    private void enemyHitEach(int n) {
        if (this.m_enemy[n][10] != 1 && this.m_enemy[n][10] != 3) {
            for (int i = n + 1; i < this.m_enemy_num; ++i) {
                int n2;
                int n3;
                int n4;
                int n5;
                if (this.m_enemy[i][10] == 1 || this.m_enemy[i][10] == 3 || (n5 = this.askValue2(n4 = this.m_enemy[n][0] - this.m_enemy[i][0], n3 = this.m_enemy[n][2] - this.m_enemy[i][2])) >= 700) continue;
                if (this.m_enemy[n][6] < this.m_enemy[i][6]) {
                    n2 = i;
                    n4 = this.m_enemy[i][0] - this.m_enemy[n][0];
                    n3 = this.m_enemy[i][2] - this.m_enemy[n][2];
                } else {
                    n2 = n;
                }
                if (n5 == 0) {
                    n5 = 1;
                }
                int[] nArray = this.m_enemy[n2];
                nArray[0] = nArray[0] + n4 * 50 / n5;
                int[] nArray2 = this.m_enemy[n2];
                nArray2[2] = nArray2[2] + n3 * 50 / n5;
            }
        }
    }

    private void enemyAct(int n) {
        int n2 = 0;
        int n3 = this.m_enemy[n][4] / 5;
        n2 = (this.m_rand.nextInt() >>> 1) % 10;
        if (this.m_enemy[n][7] == 1) {
            if (this.m_enemy[n][8] < this.m_act[n3].getMaxFrame(1)) {
                int[] nArray = this.m_enemy[n];
                nArray[8] = nArray[8] + 32768;
            } else if (this.m_enemy[n][11] < 60) {
                this.m_enemy[n][10] = 0;
                int[] nArray = this.m_enemy[n];
                nArray[11] = nArray[11] + 1;
                if (this.m_enemy[n][11] / 4 % 2 == 1) {
                    this.m_enemy[n][10] = 1;
                }
            } else {
                this.m_enemy[n][10] = 3;
                if (this.m_enemy[n][4] == 24) {
                    this.m_ending = true;
                }
            }
        } else {
            int n4 = this.m_enemyID_speed[this.m_enemy[n][4]] == 0 ? 28672 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 1 ? 30720 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 2 ? 32768 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 3 ? 34816 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 4 ? 36864 : (this.m_enemyID_speed[this.m_enemy[n][4]] == 5 ? 38911 : 40960)))));
            if (n3 == 0 && this.m_enemy[n][7] == 5) {
                n4 = -n4;
            }
            int[] nArray = this.m_enemy[n];
            nArray[8] = nArray[8] + n4;
            if (this.m_enemy[n][7] == 0) {
                if (this.m_enemy[n][8] > this.m_act[n3].getMaxFrame(0)) {
                    this.m_enemy[n][7] = 7;
                    this.m_enemy[n][8] = 0;
                }
            } else if (this.m_enemy[n][7] == 2) {
                int[] nArray2 = this.m_enemy[n];
                nArray2[0] = nArray2[0] + Math.sin(this.m_enemy[n][3]) * this.m_enemy_step / 4096;
                int[] nArray3 = this.m_enemy[n];
                nArray3[2] = nArray3[2] + Math.cos(this.m_enemy[n][3]) * this.m_enemy_step / 4096;
                boolean bl = false;
                int n5 = this.m_enemy[n][4] % 5 == 4 ? 1000 : 700;
                if (this.m_enemy[n][6] > 1000 + n2 && this.m_enemy[n][6] < 1010 + n2) {
                    bl = true;
                } else if (this.m_enemy[n][6] < n5) {
                    bl = true;
                }
                if (bl) {
                    if (this.m_enemy[n][10] == 2 && n3 == 0 && this.m_at_frame != this.m_at_limit && n2 + this.m_enemyID_speed[this.m_enemy[n][4]] * 2 >= 12) {
                        this.m_enemy[n][7] = 5;
                        KingsField_P6Canvas.Print("backstep");
                        this.m_enemy[n][8] = this.m_act[n3].getMaxFrame(2) / 2;
                    } else {
                        this.m_enemy[n][7] = n3 == 4 && n2 < 5 ? 5 : 3;
                        this.m_enemy[n][8] = 0;
                    }
                } else if (n3 == 3 && this.m_enemy[n][10] == 2 && this.m_enemy[n][6] > 2500 && n2 < this.m_enemyID_speed[this.m_enemy[n][4]]) {
                    int[] nArray4 = new int[6];
                    int[] nArray5 = new int[6];
                    int[] nArray6 = new int[6];
                    boolean[] blArray = new boolean[6];
                    for (int i = 0; i < 6; ++i) {
                        nArray4[i] = this.m_enemy[n][0] + Math.sin(this.m_enemy[n][3]) * (4 * (i + 1)) / 41;
                        nArray5[i] = this.m_enemy[n][2] + Math.cos(this.m_enemy[n][3]) * (4 * (i + 1)) / 41;
                        nArray6[i] = this.grid(nArray4[i] / 1000, nArray5[i] / 1000);
                        blArray[i] = this.m_block_exist[nArray6[i]] == 0;
                    }
                    if (blArray[0] && blArray[1] && blArray[2] && blArray[3] && blArray[4] && blArray[5] && this.m_block_level[nArray6[5]] == 0) {
                        this.m_df_targX[n] = nArray6[5] % this.m_grid_x * 1000 + 500;
                        this.m_df_targZ[n] = nArray6[5] / this.m_grid_x * 1000 + 500;
                        this.m_df_stepX[n] = Math.sin(this.m_enemy[n][3]) * 80 / 4096;
                        this.m_df_stepZ[n] = Math.cos(this.m_enemy[n][3]) * 80 / 4096;
                        this.m_enemy[n][7] = 5;
                        this.m_enemy[n][8] = 0;
                    }
                }
                if (this.m_enemy[n][8] >= this.m_act[n3].getMaxFrame(2)) {
                    if (n2 < 5) {
                        this.m_enemy[n][8] = 0;
                    } else {
                        this.m_enemy[n][7] = 4;
                        this.m_enemy[n][8] = 0;
                    }
                }
            } else if (this.m_enemy[n][7] == 3 || n3 == 4 && this.m_enemy[n][7] == 5) {
                int n6 = 0;
                n6 = n3 == 0 ? 6 : (n3 == 1 ? 6 : (n3 == 2 ? 6 : (n3 == 3 ? 5 : 3)));
                if (this.m_enemy[n][8] <= 40960) {
                    this.m_enemy_attack[n] = true;
                }
                if (this.m_enemy[n][10] == 2 && this.m_enemy[n][8] >= n6 * 65536 && this.m_enemy_attack[n]) {
                    this.directAttack(1, n);
                    this.m_enemy_attack[n] = false;
                }
                if (this.m_enemy[n][8] >= this.m_act[n3].getMaxFrame(3)) {
                    if (this.m_enemy[n][6] < 1500) {
                        if (n2 < 5) {
                            this.m_enemy[n][7] = 4;
                        }
                    } else {
                        this.m_enemy[n][7] = 4;
                    }
                    this.m_enemy[n][8] = 0;
                }
            } else if (this.m_enemy[n][7] == 4) {
                if (this.m_enemy[n][10] == 2) {
                    this.m_enemy[n][7] = 7;
                    this.m_enemy[n][8] = 0;
                }
                if (this.m_enemy[n][8] >= this.m_act[n3].getMaxFrame(4)) {
                    if (n2 < 7) {
                        this.m_enemy[n][8] = 0;
                    } else {
                        this.m_enemy[n][7] = 2;
                        this.m_enemy[n][8] = 0;
                    }
                }
            } else if (this.m_enemy[n][7] == 7) {
                int n7 = this.direEnPl(n);
                if (this.m_enemy[n][10] == 1) {
                    this.m_enemy[n][7] = 4;
                    this.m_enemy[n][8] = 0;
                }
                if (this.m_enemy[n][3] - n7 > -100 && this.m_enemy[n][3] - n7 < 100) {
                    this.m_enemy[n][8] = 0;
                    this.m_enemy[n][7] = 2;
                }
                if (this.m_enemy[n][8] >= this.m_act[n3].getMaxFrame(2)) {
                    if (n2 < 3) {
                        this.m_enemy[n][7] = 2;
                    }
                    this.m_enemy[n][8] = 0;
                }
            } else if (this.m_enemy[n][7] == 5) {
                if (n3 == 0) {
                    int[] nArray7 = this.m_enemy[n];
                    nArray7[0] = nArray7[0] - Math.sin(this.m_enemy[n][3]) * this.m_enemy_step / 4096;
                    int[] nArray8 = this.m_enemy[n];
                    nArray8[2] = nArray8[2] - Math.cos(this.m_enemy[n][3]) * this.m_enemy_step / 4096;
                    if (this.m_enemy[n][8] <= 0) {
                        int[] nArray9 = this.m_enemy[n];
                        nArray9[0] = nArray9[0] + Math.sin(this.m_enemy[n][3]) * (this.m_enemy_step * 10) / 4096;
                        int[] nArray10 = this.m_enemy[n];
                        nArray10[2] = nArray10[2] + Math.cos(this.m_enemy[n][3]) * (this.m_enemy_step * 10) / 4096;
                        this.m_enemy[n][7] = 3;
                        this.m_enemy[n][8] = 0;
                    }
                } else if (n3 == 3 && this.m_enemy[n][8] > 131072) {
                    if (this.m_enemy[n][8] <= 196608) {
                        this.m_enemy[n][1] = 0;
                        int[] nArray11 = this.m_enemy[n];
                        nArray11[0] = nArray11[0] + this.m_df_stepX[n];
                        int[] nArray12 = this.m_enemy[n];
                        nArray12[2] = nArray12[2] + this.m_df_stepZ[n];
                    } else if (this.m_enemy[n][8] <= 262144) {
                        this.m_enemy[n][1] = 300;
                        int[] nArray13 = this.m_enemy[n];
                        nArray13[0] = nArray13[0] + this.m_df_stepX[n];
                        int[] nArray14 = this.m_enemy[n];
                        nArray14[2] = nArray14[2] + this.m_df_stepZ[n];
                    } else if (this.m_enemy[n][8] >= this.m_act[n3].getMaxFrame(5)) {
                        this.m_enemy[n][1] = 600;
                        this.m_enemy[n][7] = 6;
                        this.m_enemy[n][8] = 0;
                    }
                }
            } else if (this.m_enemy[n][7] == 6 && n3 == 3) {
                if (this.m_enemy[n][11] < 25) {
                    int[] nArray15 = this.m_enemy[n];
                    nArray15[11] = nArray15[11] + 1;
                    int[] nArray16 = this.m_enemy[n];
                    nArray16[0] = nArray16[0] + this.m_df_stepX[n];
                    this.m_enemy[n][1] = 900;
                    int[] nArray17 = this.m_enemy[n];
                    nArray17[2] = nArray17[2] + this.m_df_stepZ[n];
                    this.m_enemy[n][8] = 0;
                    int n8 = this.askValue2(this.m_enemy[n][0] - this.m_df_targX[n], this.m_enemy[n][2] - this.m_df_targZ[n]);
                    if (this.m_enemy[n][11] == 25 || n8 < 500 || this.m_enemy[n][6] < 400) {
                        this.m_enemy[n][1] = 600;
                    }
                    this.m_enemy[n][8] = 0;
                } else if (this.m_enemy[n][8] <= 65536) {
                    this.m_enemy[n][1] = 300;
                    this.m_enemy_attack[n] = true;
                } else if (this.m_enemy[n][8] <= 196608) {
                    this.m_enemy[n][1] = 0;
                    if (this.m_enemy_attack[n]) {
                        this.directAttack(1, n);
                        this.m_enemy_attack[n] = false;
                    }
                    if (this.m_block_level[this.m_enemy[n][9]] < 0) {
                        this.m_hole = true;
                        int[] nArray18 = this.m_enemy[n];
                        nArray18[0] = nArray18[0] + this.m_df_stepX[n] * 2;
                        int[] nArray19 = this.m_enemy[n];
                        nArray19[2] = nArray19[2] + this.m_df_stepZ[n] * 2;
                    } else {
                        this.m_hole = false;
                    }
                } else if (this.m_enemy[n][8] <= 262144) {
                    if (this.m_block_level[this.m_enemy[n][9]] < 0) {
                        this.m_hole = true;
                        int[] nArray20 = this.m_enemy[n];
                        nArray20[0] = nArray20[0] + this.m_df_stepX[n] * 2;
                        int[] nArray21 = this.m_enemy[n];
                        nArray21[2] = nArray21[2] + this.m_df_stepZ[n] * 2;
                    } else {
                        this.m_hole = false;
                    }
                } else if (this.m_enemy[n][8] >= this.m_act[n3].getMaxFrame(6)) {
                    this.m_hole = false;
                    this.m_enemy[n][7] = n2 < 5 ? 7 : 3;
                    this.m_enemy[n][8] = 0;
                    this.m_enemy[n][11] = 0;
                    this.m_enemy[n][1] = 0;
                }
            }
        }
    }

    private int direEnPl(int n) {
        int n2 = (player.posZ - this.m_enemy[n][2]) * 100;
        if (player.posX >= this.m_enemy[n][0]) {
            int n3 = player.posX - this.m_enemy[n][0];
            if (n3 == 0) {
                n3 = 1;
            }
            if (n2 / n3 > 1015) {
                return 0;
            }
            if (n2 / n3 > 330) {
                return 128;
            }
            if (n2 / n3 > 187) {
                return 256;
            }
            if (n2 / n3 > 122) {
                return 384;
            }
            if (n2 / n3 > 82) {
                return 512;
            }
            if (n2 / n3 > 54) {
                return 640;
            }
            if (n2 / n3 > 30) {
                return 768;
            }
            if (n2 / n3 > 10) {
                return 896;
            }
            if (n2 / n3 > -10) {
                return 1024;
            }
            if (n2 / n3 > -30) {
                return 1152;
            }
            if (n2 / n3 > -54) {
                return 1280;
            }
            if (n2 / n3 > -82) {
                return 1408;
            }
            if (n2 / n3 > -122) {
                return 1536;
            }
            if (n2 / n3 > -187) {
                return 1664;
            }
            if (n2 / n3 > -330) {
                return 1792;
            }
            if (n2 / n3 > -1015) {
                return 1920;
            }
            return 2048;
        }
        int n4 = this.m_enemy[n][0] - player.posX;
        if (n4 == 0) {
            n4 = 1;
        }
        if (n2 / n4 > 1015) {
            return 0;
        }
        if (n2 / n4 > 330) {
            return 3968;
        }
        if (n2 / n4 > 187) {
            return 3840;
        }
        if (n2 / n4 > 122) {
            return 3712;
        }
        if (n2 / n4 > 82) {
            return 3584;
        }
        if (n2 / n4 > 54) {
            return 3456;
        }
        if (n2 / n4 > 30) {
            return 3328;
        }
        if (n2 / n4 > 10) {
            return 3200;
        }
        if (n2 / n4 > -10) {
            return 3072;
        }
        if (n2 / n4 > -30) {
            return 2944;
        }
        if (n2 / n4 > -54) {
            return 2816;
        }
        if (n2 / n4 > -82) {
            return 2688;
        }
        if (n2 / n4 > -122) {
            return 2560;
        }
        if (n2 / n4 > -187) {
            return 2432;
        }
        if (n2 / n4 > -330) {
            return 2304;
        }
        if (n2 / n4 > -1015) {
            return 2176;
        }
        return 2048;
    }

    private synchronized void viewSetting() {
        this.m_mat_x.setRotateX(-player.rotX);
        this.m_mat_y.setRotateY(player.rotY);
        this.m_mat_z.setRotateZ(player.rotZ);
        this.m_mat_x.mul(this.m_mat_z, this.m_mat_x);
        this.m_3D_b_trans.mul(this.m_mat_y, this.m_mat_x);
        this.m_3D_b_trans.m03 = player.posX;
        this.m_3D_b_trans.m13 = player.posY;
        this.m_3D_b_trans.m23 = player.posZ;
        this.m_mat_z.setIdentity();
        this.m_mat_z.m00 = -4096;
        this.m_mat_z.m11 = -4096;
        this.m_3D_b_trans.mul(this.m_3D_b_trans, this.m_mat_z);
        this.M3D_Atrans3i_Transpose(this.m_3D_b_trans);
        int n = this.m_3D_b_trans.m03 * -1;
        int n2 = this.m_3D_b_trans.m13 * -1;
        int n3 = this.m_3D_b_trans.m23 * -1;
        int n4 = (this.m_3D_b_trans.m00 * n + 2048 >> 12) + (this.m_3D_b_trans.m01 * n2 + 2048 >> 12) + (this.m_3D_b_trans.m02 * n3 + 2048 >> 12);
        int n5 = (this.m_3D_b_trans.m10 * n + 2048 >> 12) + (this.m_3D_b_trans.m11 * n2 + 2048 >> 12) + (this.m_3D_b_trans.m12 * n3 + 2048 >> 12);
        int n6 = (this.m_3D_b_trans.m20 * n + 2048 >> 12) + (this.m_3D_b_trans.m21 * n2 + 2048 >> 12) + (this.m_3D_b_trans.m22 * n3 + 2048 >> 12);
        this.m_3D_b_trans.m03 = n4;
        this.m_3D_b_trans.m13 = n5;
        this.m_3D_b_trans.m23 = n6;
        Vector3D vector3D = new Vector3D(player.posX, player.posY, player.posZ);
        Vector3D vector3D2 = new Vector3D(Math.sin(player.rotY) * Math.cos(player.rotX) / 168100, Math.sin(player.rotX) * 100 / 4096, Math.cos(player.rotY) * Math.cos(player.rotX) / 168100);
        Vector3D vector3D3 = new Vector3D(-Math.sin(player.rotZ) * Math.cos(player.rotY) / 4096, Math.cos(player.rotZ), Math.sin(player.rotZ) * Math.sin(player.rotY) / 4096);
        vector3D2.x += vector3D.x;
        vector3D2.y += vector3D.y;
        vector3D2.z += vector3D.z;
        this.m_trans.lookAt(vector3D, vector3D2, vector3D3);
        this.i_g3d.setViewTrans(this.m_trans);
    }

    private void enemySetting() {
        int n = 0;
        for (int i = 0; i < this.m_enemy_num; ++i) {
            n = this.m_enemy[i][4] % 5 == 4 ? 6144 : 4096;
            this.m_enemy_trans[i].setIdentity();
            this.m_enemy_trans[i].m00 = n;
            this.m_enemy_trans[i].m11 = n;
            this.m_enemy_trans[i].m22 = n;
            this.m_mat_x.setRotateX(0);
            this.m_mat_y.setRotateY(this.m_enemy[i][3]);
            this.m_mat_y.mul(this.m_mat_y, this.m_mat_x);
            this.m_enemy_trans[i].mul(this.m_mat_y, this.m_enemy_trans[i]);
            this.m_enemy_trans[i].m03 = this.m_enemy[i][0];
            this.m_enemy_trans[i].m13 = this.m_enemy[i][1];
            this.m_enemy_trans[i].m23 = this.m_enemy[i][2];
            this.m_enemy_trans[i].mul(this.m_3D_b_trans, this.m_enemy_trans[i]);
        }
    }

    private void boxOpenSetting() {
        if (this.m_item_treasure) {
            this.m_box_frame += 32768;
            if (this.m_box_frame >= this.m_box_near_act.getMaxFrame(0)) {
                this.m_item_treasure = false;
            }
        } else {
            this.m_comment_frame = 0;
        }
    }

    private synchronized void shutterSetting() {
        if (this.m_mode == 2) {
            ++this.m_shutter_frame;
        }
        int n = 1200 * this.m_shutter_frame / 30;
        int n2 = 1200;
        if (this.m_shutter_frame < 30) {
            this.m_shutter_draw = true;
            this.m_poly_num[3] = 0;
            int n3 = this.m_shutter[this.m_shutter_id][0];
            if (this.m_shutter[this.m_shutter_id][1] == 0) {
                this.makePolygonZ(n3, 2, n, n2);
                this.makePolygonZ(n3, 3, n, n2);
            } else {
                this.makePolygonX(n3, 0, n, n2);
                this.makePolygonX(n3, 1, n, n2);
            }
            this.makePolygonFlat(n3, n);
            this.m_shutter_draw = false;
        } else {
            this.m_map_info[this.m_shutter[this.m_shutter_id][0]] = 4;
            this.m_poly_num[3] = 0;
            this.m_shutter_frame = 0;
            this.m_mode = 0;
            this.m_stand_by = false;
            this.m_door_flag[this.m_door_number[this.m_stage_ID] + this.m_shutter_id] = false;
            this.viewWaterArea();
            SoftLablesSet(2, 3);
        }
    }

    private void attackSetting() {
        int n = 0;
        int n2 = 0;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        if (player.currentWeaponID != 40 && this.m_at_frame < this.m_at_limit) {
            this.m_wep_trans.setIdentity();
            this.m_wep_trans.m00 = 16384;
            this.m_wep_trans.m11 = 16384;
            this.m_wep_trans.m22 = 16384;
            ++this.m_at_frame;
            int n3 = this.m_at_frame;
            if (player.currentWeaponID <= 4) {
                this.m_wep_rotX = 2048 + 2000 / this.m_at_limit * n3;
                this.m_wep_rotY = player.rotY + 224;
                this.m_wep_rotZ = 0;
                this.m_wep_posY = player.posY - 50 - 150 / this.m_at_limit * n3;
                if (this.m_at_ok && n3 * 100 / this.m_at_limit >= 58) {
                    this.directAttack(0, 0);
                    this.m_at_ok = false;
                }
                n = player.posX - Math.cos(player.rotY) * 170 / 4096 + Math.sin(player.rotY) * (250 - 200 / this.m_at_limit * n3) / 4096;
                n2 = player.posZ + Math.sin(player.rotY) * 170 / 4096 + Math.cos(player.rotY) * (250 - 200 / this.m_at_limit * n3) / 4096;
            } else if (player.currentWeaponID <= 9) {
                this.m_wep_rotX = 2048 + 2048 / this.m_at_limit * n3;
                this.m_wep_rotY = player.rotY + 128;
                this.m_wep_rotZ = 768;
                this.m_wep_posY = player.posY - 50 - 150 / this.m_at_limit * n3;
                if (this.m_at_ok && n3 * 100 / this.m_at_limit >= 58) {
                    this.directAttack(0, 0);
                    this.m_at_ok = false;
                }
                n = player.posX - Math.cos(player.rotY) * 100 / 4096 + Math.sin(player.rotY) * 100 / 4096;
                n2 = player.posZ + Math.sin(player.rotY) * 100 / 4096 + Math.cos(player.rotY) * 100 / 4096;
            } else {
                this.m_wep_rotX = -1024;
                this.m_wep_rotY = player.rotY + 96;
                this.m_wep_rotZ = 0;
                this.m_wep_posY = player.posY - 200;
                int n4 = this.m_at_limit / 2 + 1;
                int n5 = n3 == n4 ? 130 : (n3 == n4 - 1 || n3 == n4 + 1 ? 180 : (n3 == n4 - 2 || n3 == n4 + 2 ? 100 : (n3 == 20 ? -200 : 20)));
                if (this.m_at_ok && n3 == n4) {
                    this.directAttack(0, 0);
                    this.m_at_ok = false;
                }
                n = player.posX - Math.cos(player.rotY) * 100 / 4096 + Math.sin(player.rotY) * n5 / 4096;
                n2 = player.posZ + Math.sin(player.rotY) * 100 / 4096 + Math.cos(player.rotY) * n5 / 4096;
            }
            this.m_mat_x.setRotateX(this.m_wep_rotX);
            this.m_mat_y.setRotateY(this.m_wep_rotY);
            this.m_mat_z.setRotateZ(this.m_wep_rotZ);
            this.m_mat_x.mul(this.m_mat_z, this.m_mat_x);
            this.m_mat_y.mul(this.m_mat_y, this.m_mat_x);
            this.m_wep_trans.mul(this.m_mat_y, this.m_wep_trans);
            this.m_wep_trans.m03 = n;
            this.m_wep_trans.m13 = this.m_wep_posY;
            this.m_wep_trans.m23 = n2;
            this.m_wep_trans.mul(this.m_3D_b_trans, this.m_wep_trans);
        }
    }

    private void objectSetting() {
        int n;
        if (this.m_player_area[this.m_statue]) {
            this.m_statue_trans.setIdentity();
            this.m_statue_trans.m00 = 6144;
            this.m_statue_trans.m11 = 6144;
            this.m_statue_trans.m22 = 6144;
            this.m_mat_x.setRotateX(0);
            this.m_mat_y.setRotateY(this.m_statue_dire[this.m_stage_ID] * 1024);
            this.m_mat_y.mul(this.m_mat_y, this.m_mat_x);
            this.m_statue_trans.mul(this.m_mat_y, this.m_statue_trans);
            this.m_statue_trans.m03 = this.m_statue % this.m_grid_x * 1000 + 500;
            this.m_statue_trans.m13 = 0;
            this.m_statue_trans.m23 = this.m_statue / this.m_grid_x * 1000 + 500;
            this.m_statue_trans.mul(this.m_3D_b_trans, this.m_statue_trans);
        }
        for (n = 0; n < this.m_box_num; ++n) {
            if (!this.m_player_area[this.m_box[n][0]]) continue;
            this.m_box_trans[n] = new AffineTrans();
            this.m_box_trans[n].setIdentity();
            this.m_box_trans[n].m00 = 3686;
            this.m_box_trans[n].m11 = 3686;
            this.m_box_trans[n].m22 = 3686;
            this.m_mat_x.setRotateX(0);
            this.m_mat_y.setRotateY(this.m_box[n][2] * 1024);
            this.m_mat_y.mul(this.m_mat_y, this.m_mat_x);
            this.m_box_trans[n].mul(this.m_mat_y, this.m_box_trans[n]);
            this.m_box_trans[n].m03 = this.m_box[n][0] % this.m_grid_x * 1000 + 500;
            this.m_box_trans[n].m13 = 0;
            this.m_box_trans[n].m23 = this.m_box[n][0] / this.m_grid_x * 1000 + 500;
            this.m_box_trans[n].mul(this.m_3D_b_trans, this.m_box_trans[n]);
        }
        for (n = 0; n < this.m_taru_num; ++n) {
            if (!this.m_player_area[this.m_taru[n]]) continue;
            this.m_taru_trans[n] = new AffineTrans();
            this.m_taru_trans[n].setIdentity();
            this.m_taru_trans[n].m00 = 6144;
            this.m_taru_trans[n].m11 = 4096;
            this.m_taru_trans[n].m22 = 6144;
            this.m_mat_x.setRotateX(0);
            this.m_mat_y.setRotateY(0);
            this.m_mat_y.mul(this.m_mat_y, this.m_mat_x);
            this.m_taru_trans[n].mul(this.m_mat_y, this.m_taru_trans[n]);
            this.m_taru_trans[n].m03 = this.m_taru[n] % this.m_grid_x * 1000 + 500;
            this.m_taru_trans[n].m13 = 0;
            this.m_taru_trans[n].m23 = this.m_taru[n] / this.m_grid_x * 1000 + 500;
            this.m_taru_trans[n].mul(this.m_3D_b_trans, this.m_taru_trans[n]);
        }
    }

    private void itemSetting() {
        if (this.m_item_id >= 0 && this.m_player_area[this.m_item_grid] && (this.m_mode == 0 || this.m_mode == 1)) {
            this.m_item_rot -= 64;
            if (this.m_item_rot >= 4096) {
                this.m_item_rot -= 4096;
            }
            if (this.m_item_style == 0 && this.m_item_posY <= 600) {
                this.m_item_posY += 20;
            }
            if (this.m_item_style == 1 && this.m_item_posY > 0) {
                this.m_item_posY -= 20;
            }
            if (this.m_item_id >= 25 && this.m_item_id <= 34) {
                this.m_item_trans.setIdentity();
                this.m_item_trans.m00 = this.m_item_scale;
                this.m_item_trans.m11 = this.m_item_scale;
                this.m_item_trans.m22 = this.m_item_scale;
                this.m_mat_x.setRotateX(0);
                this.m_mat_y.setRotateY(this.m_item_rot + 896);
                this.m_mat_y.mul(this.m_mat_y, this.m_mat_x);
                this.m_item_trans.mul(this.m_mat_y, this.m_item_trans);
                this.m_item_trans.m03 = this.m_item_posX + Math.sin(this.m_item_rot) * 100 / 4096;
                this.m_item_trans.m13 = this.m_item_posY;
                this.m_item_trans.m23 = this.m_item_posZ + Math.cos(this.m_item_rot) * 100 / 4096;
                this.m_item_trans.mul(this.m_3D_b_trans, this.m_item_trans);
                this.m_item_trans2.setIdentity();
                this.m_item_trans2.m00 = this.m_item_scale;
                this.m_item_trans2.m11 = this.m_item_scale;
                this.m_item_trans2.m22 = this.m_item_scale;
                this.m_mat_x.setRotateX(0);
                this.m_mat_y.setRotateY(this.m_item_rot + 1152);
                this.m_mat_y.mul(this.m_mat_y, this.m_mat_x);
                this.m_item_trans2.mul(this.m_mat_y, this.m_item_trans2);
                this.m_item_trans2.m03 = this.m_item_posX + Math.sin(this.m_item_rot + 2048) * 100 / 4096;
                this.m_item_trans2.m13 = this.m_item_posY;
                this.m_item_trans2.m23 = this.m_item_posZ + Math.cos(this.m_item_rot + 2048) * 100 / 4096;
                this.m_item_trans2.mul(this.m_3D_b_trans, this.m_item_trans2);
            } else {
                this.m_item_trans.setIdentity();
                this.m_item_trans.m00 = this.m_item_scale;
                this.m_item_trans.m11 = this.m_item_scale;
                this.m_item_trans.m22 = this.m_item_scale;
                this.m_mat_x.setRotateX(0);
                this.m_mat_y.setRotateY(this.m_item_rot);
                this.m_mat_y.mul(this.m_mat_y, this.m_mat_x);
                this.m_item_trans.mul(this.m_mat_y, this.m_item_trans);
                this.m_item_trans.m03 = this.m_item_posX;
                this.m_item_trans.m13 = this.m_item_posY;
                this.m_item_trans.m23 = this.m_item_posZ;
                this.m_item_trans.mul(this.m_3D_b_trans, this.m_item_trans);
            }
        }
    }

    private void warpSetting() {
        int n = 0;
        int n2 = 0;
        if (this.m_mode == 3) {
            ++this.m_cure_count;
            this.m_warp_rotY -= 128;
            if (this.m_cure_count <= 25) {
                player.currentHP += player.maxHP / 25;
                if (player.currentHP > player.maxHP) {
                    player.currentHP = player.maxHP;
                }
                if (this.m_cure_count == 1) {
                    for (int i = 0; i < 4; ++i) {
                        this.m_condi_cure[i] = true;
                    }
                    this.condiCure();
                }
                if (this.m_cure_count < 15) {
                    n2 = 8192 * this.m_cure_count / 15;
                    this.m_warp_posY = 300;
                } else {
                    n2 = 8192;
                    this.m_warp_posY = 300;
                }
            } else {
                this.m_plt_now[9] = 0;
                this.rePalet();
                this.m_mode = (byte)4;
                this.m_black = 1;
                this.viewWaterArea();
                if (player.currentHP < player.maxHP) {
                    player.currentHP = player.maxHP;
                }
            }
            n = 2048;
        } else if (this.m_scene == 5 && this.m_mode == 0) {
            this.m_warp_rotY -= 64;
            this.m_warp_high += 512;
            if (this.m_warp < 80) {
                n2 = 8192 * this.m_warp / 80 + Math.sin(this.m_warp_high) * 1024 / 4096;
            } else if (this.m_warp > 80) {
                n2 = 8192 * (160 - this.m_warp) / 80 + Math.sin(this.m_warp_high) * 1024 / 4096;
            }
            n = 4096;
        } else if (this.m_player_area[this.m_start_grid] && this.m_step_No == 1) {
            n2 = 0;
            n = 0;
            this.m_warp_rotY = 0;
            this.m_warp_high = 0;
        } else {
            n2 = 500 + Math.sin(this.m_warp_high) * 100 / 4096;
            n = 4096;
            this.m_warp_rotY -= 8;
            this.m_warp_high += 256;
        }
        if (this.m_warp_rotY >= 4096) {
            this.m_warp_rotY -= 4096;
        }
        if (this.m_warp_high >= 4096) {
            this.m_warp_high -= 4096;
        }
        this.m_warp_trans.setIdentity();
        this.m_warp_trans.m00 = n;
        this.m_warp_trans.m11 = n2;
        this.m_warp_trans.m22 = n;
        this.m_mat_y.setRotateY(this.m_warp_rotY);
        this.m_warp_trans.mul(this.m_mat_y, this.m_warp_trans);
        this.m_warp_trans.m03 = this.m_warp_posX;
        this.m_warp_trans.m13 = this.m_warp_posY;
        this.m_warp_trans.m23 = this.m_warp_posZ;
        this.m_warp_trans.mul(this.m_3D_b_trans, this.m_warp_trans);
    }

    public synchronized void paint(Graphics graphics) {
        graphics = (Graphics)((Object)this.i_g3d);

        if (this.m_paint_flag)
        {
            graphics.lock();
            graphics.setColor(this.BLACK);
            if (this.m_ending_count > 100) {
                graphics.setColor(this.WHITE);
            }
            graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            Graphics3D graphics3D = (Graphics3D)((Object)graphics);
            this.drawUnder(graphics3D);
            graphics3D.flush();
            Graphics3D graphics3D2 = (Graphics3D)((Object)graphics);
            this.drawFloor(graphics3D2);
            graphics3D2.flush();
            Graphics3D graphics3D3 = (Graphics3D)((Object)graphics);
            this.drawAbove(graphics3D3);
            this.drawStatue(graphics3D3);
            this.drawBox(graphics3D3);
            this.drawTaru(graphics3D3);
            this.drawItem(graphics3D3);
            this.drawEnemy(graphics3D3);
            this.drawWarp(graphics3D3);
            graphics3D3.flush();
            Graphics3D graphics3D4 = (Graphics3D)((Object)graphics);
            if (this.m_scene == 2 && this.m_at_frame > 0 && this.m_at_frame < this.m_at_limit) {
                this.drawWeapon(graphics3D4);
            }
            graphics3D4.flush();
            Graphics3D graphics3D5 = (Graphics3D)((Object)graphics);
            this.drawBackcover(graphics3D5);
            if (this.m_mode == 2) {
                this.drawShutter(graphics3D5);
            }
            graphics3D5.flush();
            this.drawOver(graphics);
            if (this.m_black > 0 && this.m_mode != 0) {
                if (this.m_scene != 1 && this.m_scene != 7) {
                    this.drawParameter(graphics);
                }
                graphics.setOrigin(0, -16);
                this.DrawUI(graphics);
                this.drawCursor(graphics);
                graphics.setOrigin(0, 0);
            } else {
                if (this.m_scene != 1 && this.m_scene != 7) {
                    this.drawParameter(graphics);
                }
                this.drawCursor(graphics);
            }
            this.m_set_flag = true;
            this.m_paint_flag = false;
            graphics.unlock(true);
        }
    }

    private void drawUnder(Graphics3D graphics3D) {
        try {
            if (this.m_poly_num[0] > 0) {
                int n;
                PrimitiveArray primitiveArray = new PrimitiveArray(4, 12800, this.m_poly_num[0]);
                int[] nArray = primitiveArray.getVertexArray();
                for (n = 0; n < this.m_poly_num[0] * 12; ++n) {
                    nArray[n] = this.m_poly_ver[0][n];
                }
                int[] nArray2 = primitiveArray.getNormalArray();
                for (n = 0; n < this.m_poly_num[0] * 3; ++n) {
                    nArray2[n] = this.m_poly_nor[0][n];
                }
                int[] nArray3 = primitiveArray.getTextureCoordArray();
                for (n = 0; n < this.m_poly_num[0] * 8; ++n) {
                    nArray3[n] = this.m_uv[0][n];
                }
                this.i_g3d.setViewTrans(this.m_trans);
                this.i_g3d.setPrimitiveTextureArray(this.m_poly_tex);
                this.i_g3d.setPrimitiveTexture(0);
                this.i_g3d.renderPrimitives(primitiveArray, 16);
            }
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("drawFloor0 render: " + exception + "---error");
        }
    }

    private void drawFloor(Graphics3D graphics3D) {
        try {
            if (this.m_poly_num[1] > 0) {
                int n;
                PrimitiveArray primitiveArray = new PrimitiveArray(4, 12800, this.m_poly_num[1]);
                int[] nArray = primitiveArray.getVertexArray();
                for (n = 0; n < this.m_poly_num[1] * 12; ++n) {
                    nArray[n] = this.m_poly_ver[1][n];
                }
                int[] nArray2 = primitiveArray.getNormalArray();
                for (n = 0; n < this.m_poly_num[1] * 3; ++n) {
                    nArray2[n] = this.m_poly_nor[1][n];
                }
                int[] nArray3 = primitiveArray.getTextureCoordArray();
                for (n = 0; n < this.m_poly_num[1] * 8; ++n) {
                    nArray3[n] = this.m_uv[1][n];
                }
                this.i_g3d.setViewTrans(this.m_trans);
                this.i_g3d.setPrimitiveTextureArray(this.m_poly_tex);
                this.i_g3d.setPrimitiveTexture(0);
                this.i_g3d.renderPrimitives(primitiveArray, 16);
            }
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("drawFloor1 render: " + exception + "---error");
        }
    }

    private void drawAbove(Graphics3D graphics3D) {
        try {
            if (this.m_poly_num[2] > 0) {
                int n;
                PrimitiveArray primitiveArray = new PrimitiveArray(4, 12800, this.m_poly_num[2]);
                int[] nArray = primitiveArray.getVertexArray();
                for (n = 0; n < this.m_poly_num[2] * 12; ++n) {
                    nArray[n] = this.m_poly_ver[2][n];
                }
                int[] nArray2 = primitiveArray.getNormalArray();
                for (n = 0; n < this.m_poly_num[2] * 3; ++n) {
                    nArray2[n] = this.m_poly_nor[2][n];
                }
                int[] nArray3 = primitiveArray.getTextureCoordArray();
                for (n = 0; n < this.m_poly_num[2] * 8; ++n) {
                    nArray3[n] = this.m_uv[2][n];
                }
                this.i_g3d.setViewTrans(this.m_trans);
                this.i_g3d.setPrimitiveTextureArray(this.m_poly_tex);
                this.i_g3d.setPrimitiveTexture(0);
                this.i_g3d.renderPrimitives(primitiveArray, 16);
            }
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("drawStage render: " + exception + "---error");
        }
    }

    private void drawShutter(Graphics3D graphics3D) {
        try {
            if (this.m_poly_num[3] > 0) {
                int n;
                PrimitiveArray primitiveArray = new PrimitiveArray(4, 12800, this.m_poly_num[3]);
                int[] nArray = primitiveArray.getVertexArray();
                for (n = 0; n < this.m_poly_num[3] * 12; ++n) {
                    nArray[n] = this.m_poly_ver[3][n];
                }
                int[] nArray2 = primitiveArray.getNormalArray();
                for (n = 0; n < this.m_poly_num[3] * 3; ++n) {
                    nArray2[n] = this.m_poly_nor[3][n];
                }
                int[] nArray3 = primitiveArray.getTextureCoordArray();
                for (n = 0; n < this.m_poly_num[3] * 8; ++n) {
                    nArray3[n] = this.m_uv[3][n];
                }
                this.i_g3d.setViewTrans(this.m_trans);
                this.i_g3d.setPrimitiveTextureArray(this.m_poly_tex);
                this.i_g3d.setPrimitiveTexture(0);
                this.i_g3d.renderPrimitives(primitiveArray, 16);
            }
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("drawShutter render: " + exception + "---error");
        }
    }

    private void drawWeapon(Graphics3D graphics3D) {
        try {
            this.i_g3d.setViewTrans(this.m_wep_trans);
            int n = player.currentWeaponID <= 4 ? 262144 : (player.currentWeaponID <= 9 ? 131072 : 0);
            this.m_wep_fig.setPosture(this.m_wep_act, 0, n);
            this.i_g3d.renderFigure(this.m_wep_fig);
            this.i_g3d.renderFigure(this.m_han_fig);
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("Weapon & Hand error");
        }
    }

    private void drawStatue(Graphics3D graphics3D) {
        if (this.m_player_area[this.m_statue]) {
            try {
                this.i_g3d.setViewTrans(this.m_statue_trans);
                this.i_g3d.renderFigure(this.m_statue_fig);
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("The statue of Liberty!");
            }
        }
    }

    private void drawBox(Graphics3D graphics3D) {
        for (int i = 0; i < this.m_box_num; ++i) {
            if (!this.m_player_area[this.m_box[i][0]]) continue;
            try {
                this.i_g3d.setViewTrans(this.m_box_trans[i]);
                if (!this.m_box_flag[this.m_box_number[this.m_stage_ID] + i]) {
                    this.m_box_near_fig.setPosture(this.m_box_near_act, 1, 0);
                    this.i_g3d.renderFigure(this.m_box_near_fig);
                    continue;
                }
                if (this.m_mode == 1 && i == this.m_openbox) {
                    this.m_box_near_fig.setPosture(this.m_box_near_act, 0, this.m_box_frame);
                    this.i_g3d.renderFigure(this.m_box_near_fig);
                    continue;
                }
                this.i_g3d.renderFigure(this.m_box_far_fig);
                continue;
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("box error:" + i);
            }
        }
    }

    private void drawTaru(Graphics3D graphics3D) {
        for (int i = 0; i < this.m_taru_num; ++i) {
            if (!this.m_player_area[this.m_taru[i]]) continue;
            try {
                this.i_g3d.setViewTrans(this.m_taru_trans[i]);
                this.i_g3d.renderFigure(this.m_taru_fig);
                continue;
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("taru error:" + i);
            }
        }
    }

    private void drawItem(Graphics3D graphics3D) {
        if (this.m_item_id >= 0 && this.m_player_area[this.m_item_grid] && this.m_item_id >= 0 && this.m_player_area[this.m_item_grid]) {
            try {
                this.i_g3d.setViewTrans(this.m_item_trans);
                if (this.m_item_id <= 14) {
                    this.m_item_fig.setPosture(this.m_wep_act, 0, 393216);
                    this.i_g3d.renderFigure(this.m_item_fig);
                } else {
                    this.m_item_fig.setPosture(this.m_wep_act, 0, 262144);
                    this.i_g3d.renderFigure(this.m_item_fig);
                    if (this.m_item_id >= 25 && this.m_item_id <= 34) {
                        this.i_g3d.setViewTrans(this.m_item_trans2);
                        this.i_g3d.renderFigure(this.m_item_fig);
                    }
                }
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("item draw error");
            }
        }
    }

    private void drawEnemy(Graphics3D graphics3D) {
        for (int i = 0; i < this.m_enemy_num; ++i) {
            if (this.m_enemy[i][10] != 0 && this.m_enemy[i][10] != 2) continue;
            this.i_g3d.setViewTrans(this.m_enemy_trans[i]);
            int n = this.m_enemy[i][4] / 5;
            try {
                boolean bl = false;
                if (this.m_enemy[i][7] == 7) {
                    bl = true;
                } else if (this.m_enemy[i][7] == 5 && n == 0) {
                    bl = true;
                }
                if (bl) {
                    this.m_fig[n].setPosture(this.m_act[n], 2, this.m_enemy[i][8]);
                    this.i_g3d.renderFigure(this.m_fig[n]);
                    continue;
                }
                this.m_fig[n].setPosture(this.m_act[n], this.m_enemy[i][7], this.m_enemy[i][8]);
                this.i_g3d.renderFigure(this.m_fig[n]);
                continue;
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("drawEnemy error : " + i + ", " + " a=" + n + ", behv=" + this.m_enemy[i][7] + "frame" + this.m_enemy[i][8]);
            }
        }
    }

    private void drawWarp(Graphics3D graphics3D) {
        boolean bl = false;
        if (this.m_mode == 3) {
            bl = true;
        } else if (this.m_player_area[this.m_start_grid]) {
            bl = true;
        } else if (this.m_player_area[this.m_goal_grid] && !this.m_boss_live) {
            bl = true;
        }
        if (bl) {
            try {
                this.i_g3d.setViewTrans(this.m_warp_trans);
                this.i_g3d.renderFigure(this.m_warp_fig);
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("drawWarp: erroer");
            }
        }
    }

    private void drawBackcover(Graphics3D graphics3D) {
        PrimitiveArray primitiveArray = new PrimitiveArray(4, 1536, 1);
        if (this.m_mode >= 4) {
            if (this.m_black == 0) {
                this.m_black = 1;
            }
            try {
                for (int i = 0; i < this.m_cover_num; ++i) {
                    int n;
                    int[] nArray = primitiveArray.getVertexArray();
                    for (n = 0; n < 12; ++n) {
                        nArray[n] = this.m_cover_ver[i][n];
                    }
                    int[] nArray2 = primitiveArray.getNormalArray();
                    for (n = 0; n < 3; ++n) {
                        nArray2[n] = this.m_cover_nor[n];
                    }
                    int[] nArray3 = primitiveArray.getColorArray();
                    for (n = 0; n < 1; ++n) {
                        nArray3[n] = this.m_cover_colors[0] == this.BLACK ? 0 : (this.m_cover_colors[0] == this.WHITE ? 0xFFFFFF : this.m_cover_colors[0]);
                    }
                    this.i_g3d.setViewTrans(this.m_trans);
                    this.i_g3d.renderPrimitives(primitiveArray, 32);
                }
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("BackCover0 error");
            }
        } else if (this.m_scene == 1 || this.m_scene == 7) {
            try {
                for (int i = 0; i < this.m_cover_num; ++i) {
                    int n;
                    int[] nArray = primitiveArray.getVertexArray();
                    for (n = 0; n < 12; ++n) {
                        nArray[n] = this.m_cover_ver[i][n];
                    }
                    int[] nArray4 = primitiveArray.getNormalArray();
                    for (n = 0; n < 3; ++n) {
                        nArray4[n] = this.m_cover_nor[n];
                    }
                    int[] nArray5 = primitiveArray.getColorArray();
                    for (n = 0; n < 1; ++n) {
                        nArray5[n] = this.m_cover_colors[0] == this.BLACK ? 0 : (this.m_cover_colors[0] == this.WHITE ? 0xFFFFFF : this.m_cover_colors[0]);
                    }
                    this.i_g3d.setViewTrans(this.m_trans);
                    this.i_g3d.renderPrimitives(primitiveArray, 32);
                }
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("BackCover0 error");
            }
        } else {
            this.m_black = 0;
        }
    }

    public void drawParameter(Graphics graphics) {
        int n;
        this.setFont(graphics, this.m_font_small);
        graphics.setColor(this.WHITE);
        this.drawImage(graphics, this.m_img_hp, 5, 7, 0);
        this.drawImage(graphics, this.m_img_power, 5, 23, 0);
        int n2 = player.currentHP * 40 / player.maxHP;
        graphics.setColor(this.BLACK);
        graphics.fillRect(42, 10, 40, 5);
        graphics.fillRect(42, 26, 40, 2);
        graphics.setColor(this.RED_DEEP);
        if (n2 > 0) {
            graphics.fillRect(42, 10, n2, 1);
            graphics.fillRect(42, 14, n2, 1);
        }
        graphics.setColor(this.RED_LIGHT);
        if (n2 > 0) {
            graphics.fillRect(42, 11, n2, 3);
        }
        graphics.fillRect(42, 26, this.m_gauge_value, 2);
        int n3 = 0;
        int[][] nArrayArray = new int[][]{{120, 3}, {170, 3}, {120, 20}, {170, 20}};
        for (n = 0; n < 4; ++n) {
            if (this.m_player_condi[n][0] != 1) continue;
            this.drawImage(graphics, this.m_img_condi[n], nArrayArray[n3][0], nArrayArray[n3][1], 1);
            ++n3;
        }
        this.drawImage(graphics, this.m_img_houi, 203, 3, 0);
        graphics.setColor(this.RED);
        graphics.drawLine(220 + Math.sin(player.rotY) * 8 / 4096, 18 + Math.cos(player.rotY) * 8 / 4096, 220 - Math.sin(player.rotY) * 8 / 4096, 18 - Math.cos(player.rotY) * 8 / 4096);
        for (n = 1; n <= 16; ++n) {
            graphics.drawLine(220 + Math.sin(player.rotY) * (-8 + n) / 4096, 18 + Math.cos(player.rotY) * (-8 + n) / 4096, 220 - Math.sin(player.rotY + 16 * n) * 8 / 4096, 18 - Math.cos(player.rotY + 16 * n) * 8 / 4096);
            graphics.drawLine(220 + Math.sin(player.rotY) * (-8 + n) / 4096, 18 + Math.cos(player.rotY) * (-8 + n) / 4096, 220 - Math.sin(player.rotY - 16 * n) * 8 / 4096, 18 - Math.cos(player.rotY - 16 * n) * 8 / 4096);
        }
    }


    /**
     * Handle Drawing UI
    **/
    private void DrawUI(Graphics graphics)
    {
        switch (m_mode)
        {
            //
            // DRAW STATUE HINTS
            //
            case 4:
                // Don't draw a hint for the first stage (the flying camera thing)
                if (m_stage_ID <= 0)
                    return;

                // Get the current hint
                KingsField_Hint stageHint = KingsField_Text.StatueHint[this.m_stage_ID - 1];

                // Configure graphics for the scene...
                m_font_select = m_font_small;
                graphics.setFont(m_font_select);
                graphics.setColor(stageHint.Colour);
                
                // Now draw each line of the hint...
                for (int i = 0; i < stageHint.Lines.length; ++i)
                    drawString(graphics, stageHint.Lines[i], 30, 85 + 15 * i, 0);             
            break;

            //
            // DRAW PROMPT SCREEN
            //
            case 11:
                // Configure graphics for the scene...
                m_font_select = m_font_small;
                graphics.setFont(m_font_select);
                graphics.setColor(RED);

                // Need to switch again for the prompt title
                switch (m_end_select)
                {
                    case 0: 
                        drawString(graphics, "~ LOAD GAME ~", getWidth() / 2, 60, 1); 
                    break;

                    case 1:
                        drawString(graphics, "~ SUSPEND GAME ~", getWidth() / 2, 60, 1);
                    break;

                    default:
                        drawString(graphics, "~ QUIT GAME ~", getWidth() / 2, 60, 1);
                    break;
                }

                // Now draw the prompt text itself
                graphics.setColor(WHITE);
                this.drawString(graphics, "ARE YOU SURE?", this.getWidth() / 2, 100, 1);
                
                // And finally, the prompt options
                this.drawString(graphics, "YES", this.getWidth() / 2, 147, 1);
                this.drawString(graphics, "NO", this.getWidth() / 2, 177, 1);
            break;

            // DRAW EXIT SCREEN
            case 12:
                this.setFont(graphics, this.m_font_small);
                graphics.setColor(this.WHITE);
                if (this.m_end_select == 0) {
                    this.drawString(graphics, "LOADING GAME...", this.getWidth() / 2, 115, 1);
                } else if (this.m_end_select == 1) {
                    this.drawString(graphics, "SAVING SUSPEND DATA...", this.getWidth() / 2, 115, 1);
                    this.drawString(graphics, "YOU CAN CONTINUE FROM THIS POINT.", this.getWidth() / 2, 145, 1);
                } else {
                    this.drawString(graphics, "EXITING GAME...", this.getWidth() / 2, 115, 1);
                }
            break;

            //
            // DRAW OPTIONS SCREEN
            //
            case 15:
                this.setFont(graphics, this.m_font_small);
                graphics.setColor(this.WHITE);
                this.drawString(graphics, "VIBRATION", 60, 65, 0);
                this.drawString(graphics, "SOUND", 60, 105, 0);
                this.drawString(graphics, "HEAD BOB", 60, 145, 0);
                this.drawString(graphics, "AUTO WALK", 60, 185, 0);
                for (int i = 0; i < 4; ++i) {
                    graphics.setColor(this.WHITE);
                    this.drawRoundRect(graphics, 55, 60 + 40 * i, 140, 22);
                    if (this.m_option[i]) {
                        graphics.setColor(this.ORANGE);
                        this.drawString(graphics, "ON", 185, 65 + 40 * i, 8);
                        continue;
                    }
                    graphics.setColor(this.GRAY);
                    this.drawString(graphics, "OFF", 185, 65 + 40 * i, 8);
                }
            break;

            //
            // DRAW CONTROLS SCREEN
            //
            case 16:

                // Configure Draw Parameters
                m_font_select = m_font_small;
                graphics.setFont(m_font_select);
                graphics.setColor(this.WHITE);

                // Draw Frame
                this.drawRoundRect(graphics, 10, 40, 220, 210);

                // Draw Text
                this.drawString(graphics, "ENTER", 15, 45, 0);
                this.drawString(graphics, " : ACTION", 65, 45, 0);
                this.drawString(graphics, "SOFT1", 15, 60, 0);
                this.drawString(graphics, " : MENU", 65, 60, 0);
                this.drawString(graphics, "SOFT2", 15, 75, 0);
                this.drawString(graphics, " : ATTACK", 65, 75, 0);
                this.drawString(graphics, "UP", 15, 95, 0);
                this.drawString(graphics, " : FORWARD", 65, 95, 0);
                this.drawString(graphics, "DOWN", 15, 110, 0);
                this.drawString(graphics, " : BACKWARD", 65, 110, 0);
                this.drawString(graphics, "LEFT", 15, 125, 0);
                this.drawString(graphics, " : LOOK LEFT", 65, 125, 0);
                this.drawString(graphics, "RIGHT", 15, 140, 0);
                this.drawString(graphics, " : LOOK RIGHT", 65, 140, 0);
                this.drawString(graphics, "NUM4", 15, 160, 0);
                this.drawString(graphics, " : STRAFE LEFT", 65, 160, 0);
                this.drawString(graphics, "NUM6", 15, 175, 0);
                this.drawString(graphics, " : STRAFE RIGHT", 65, 175, 0);
                this.drawString(graphics, "NUM2", 15, 190, 0);
                this.drawString(graphics, " : LOOK UP", 65, 190, 0);
                this.drawString(graphics, "NUM8", 15, 205, 0);
                this.drawString(graphics, " : LOOK DOWN", 65, 205, 0);
                this.drawString(graphics, "NUM5", 15, 220, 0);
                this.drawString(graphics, " : OPEN MAP", 65, 220, 0);
                this.drawString(graphics, "NUM0", 15, 235, 0);
                this.drawString(graphics, " : ATTACK", 65, 235, 0);
            break;

            // Fallback to the original method...
            default:
                drawMode(graphics);
        }
    }

    private void drawMode(Graphics graphics) {
        int n;
        int n2;
        if (this.m_mode == 6 || this.m_mode == 7 || this.m_mode == 8 || this.m_mode == 14) {
            graphics.setColor(this.WHITE);
            this.setFont(graphics, this.m_font_small);
            this.drawString(graphics, "USE ITEM", 22, 50, 0);
            this.drawString(graphics, "EQUIPMENT", 22, 70, 0);
            this.drawString(graphics, "OPEN MAP", 22, 90, 0);
            this.drawString(graphics, "OPTIONS", 22, 110, 0);
            this.drawString(graphics, "CONTROLS", 22, 130, 0);
            this.drawString(graphics, "LOAD", 22, 150, 0);
            this.drawString(graphics, "SUSPEND", 22, 170, 0);
            this.drawString(graphics, "EXIT", 22, 190, 0);
            for (n2 = 0; n2 < 8; ++n2) {
                this.drawRoundRect(graphics, 20, 48 + 20 * n2, 80, 16);
            }
            if (this.m_save_data != 2) {
                graphics.setColor(this.GRAY);
                this.drawString(graphics, "LOAD", 22, 150, 0);
                this.drawRoundRect(graphics, 20, 148, 80, 16);
            }
        }

        if (this.m_mode == 6) {
            graphics.setColor(this.WHITE);
            this.setFont(graphics, this.m_font_small);
            this.drawRoundRect(graphics, 110, 45, 120, 205);
            this.drawString(graphics, "EXP.", 115, 50, 0);
            this.drawString(graphics, "" + player.experience, 225, 50, 8);
            this.drawString(graphics, "LEVEL", 115, 70, 0);
            this.drawString(graphics, "" + player.level, 225, 70, 8);
            this.drawString(graphics, "FLOOR", 115, 90, 0);
            this.drawString(graphics, "" + this.m_step_No, 225, 90, 8);
            this.drawString(graphics, "HP", 115, 110, 0);
            this.drawString(graphics, player.currentHP + "/" + player.maxHP, 225, 110, 8);
            this.drawString(graphics, "STATUS", 115, 130, 0);
            n2 = 0;
            String string = " ";
            String string2 = " ";
            String string3 = " ";
            for (n = 0; n < 4; ++n) {
                if (this.m_player_condi[n][0] != 1) continue;
                string3 = n == 0 ? "POISON" : (n == 1 ? "STUN" : (n == 2 ? "DARK" : "CURSE"));
                if (n2 < 2) {
                    string = string + " " + string3;
                } else {
                    string2 = string2 + " " + string3;
                }
                n2 = (byte)(n2 + 1);
            }
            if (n2 == 0) {
                this.drawString(graphics, "FINE", 225, 130, 8);
            } else {
                this.drawString(graphics, "" + string, 225, 130, 8);
                this.drawString(graphics, "" + string2, 225, 150, 8);
            }
            this.drawString(graphics, "BASE ATK.", 115, 170, 0);
            this.drawString(graphics, "" + player.attackBase, 225, 170, 8);
            this.drawString(graphics, "BASE DEF.", 115, 190, 0);
            this.drawString(graphics, "" + player.defenseBase, 225, 190, 8);
            this.drawString(graphics, "ATK.", 115, 210, 0);
            this.drawString(graphics, "" + player.attack, 225, 210, 8);
            this.drawString(graphics, "DEF.", 115, 230, 0);
            this.drawString(graphics, "" + player.defense, 225, 230, 8);
        } 
        else if (this.m_mode == 7 || this.m_mode == 8 || this.m_mode == 14) 
        {
            this.setFont(graphics, this.m_font_small);
            graphics.setColor(this.WHITE);
            this.drawRoundRect(graphics, 105, 45, 125, 195);
            for (n2 = 0; n2 < this.m_array_num; ++n2) {
                this.drawString(graphics, KingsField_Text.ItemName[this.m_array[n2]], 125, 47 + 20 * n2, 0);
                this.drawString(graphics, "" + player.inventory[this.m_array[n2]], 225, 47 + 20 * n2, 8);
            }
            if (this.m_mode == 8) {
                graphics.setColor(this.BLACK);
                this.fillRoundRect(graphics, 65, 88, 110, 87, 5, 5);
                graphics.setColor(this.WHITE);
                this.drawRoundRect(graphics, 65, 88, 110, 87);
                this.drawString(graphics, KingsField_Text.ItemName[this.m_array[this.m_item_select_i]] + "", this.getWidth() / 2, 98, 1);
                this.drawString(graphics, "USE THIS ITEM?", this.getWidth() / 2, 115, 1);
                this.drawString(graphics, "YES", 110, 135, 0);
                this.drawString(graphics, "NO", 110, 155, 0);
            } else if (this.m_mode == 14) {
                graphics.setColor(this.BLACK);
                this.fillRoundRect(graphics, 45, 100, 150, 60, 5, 5);
                graphics.setColor(this.WHITE);
                this.drawRoundRect(graphics, 45, 100, 150, 60);
                this.drawString(graphics, "KEYS ARE USED", this.getWidth() / 2, 110, 1);
                this.drawString(graphics, "AUTOMATICALLY BY", this.getWidth() / 2, 125, 1);
                this.drawString(graphics, "PRESSING ACTION.", this.getWidth() / 2, 140, 1);
            }
        } 
        else if (this.m_mode == 9 || this.m_mode == 10) 
        {
            this.setFont(graphics, this.m_font_small);
            graphics.setColor(this.WHITE);
            for (n2 = 0; n2 < 6; ++n2) {
                this.drawRoundRect(graphics, 20, 45 + 20 * n2, 60, 16);
            }
            this.drawString(graphics, "ARMS", 22, 47, 0);
            this.drawString(graphics, "HEAD", 22, 67, 0);
            this.drawString(graphics, "BODY", 22, 87, 0);
            this.drawString(graphics, "ARMS", 22, 107, 0);
            this.drawString(graphics, "LEGS", 22, 127, 0);
            this.drawString(graphics, "SHIELD", 22, 147, 0);

            if (this.m_mode == 9) 
            {
                this.drawRoundRect(graphics, 85, 45, 145, 116);

                if (player.currentWeaponID != 40) 
                {
                    this.drawString(graphics, KingsField_Text.ItemName[player.currentWeaponID], 90, 47, 0);
                }
                if (player.currentHelmID != 40) 
                {
                    this.drawString(graphics, KingsField_Text.ItemName[player.currentHelmID], 90, 67, 0);
                }
                if (player.currentPlateID != 40) 
                {
                    this.drawString(graphics, KingsField_Text.ItemName[player.currentPlateID], 90, 87, 0);
                }
                if (player.currentArmsID != 40) 
                {
                    this.drawString(graphics, KingsField_Text.ItemName[player.currentArmsID], 90, 107, 0);
                }
                if (player.currentLegsID != 40) 
                {
                    this.drawString(graphics, KingsField_Text.ItemName[player.currentLegsID], 90, 127, 0);
                }
                if (player.currentShieldID != 40) 
                {
                    this.drawString(graphics, KingsField_Text.ItemName[player.currentShieldID], 90, 147, 0);
                }
            } 
            else 
            {
                int n3;
                int n4;
                this.drawRoundRect(graphics, 85, 45, 145, 205);
                if (this.m_cursor_now < 10) {
                    n2 = 0;
                    n4 = this.m_cursor_max[5] < 10 ? this.m_cursor_max[5] : 10;
                } else {
                    n2 = 10;
                    n4 = this.m_cursor_max[5];
                }
                for (n3 = n2; n3 < n4; ++n3) {
                    graphics.setColor(this.WHITE);
                    this.drawString(graphics, KingsField_Text.ItemName[this.m_array[n3]], 105, 47 + 20 * (n3 % 10), 0);
                    graphics.setColor(this.YELLOW);
                    if (this.m_array[n3] != this.m_player_eq) continue;
                    this.drawString(graphics, "E", 225, 47 + 20 * (n3 % 10), 8);
                }
                if (this.m_cursor_max[5] >= 10) {
                    graphics.setColor(this.BLACK);
                    this.fillRoundRect(graphics, 200, 245, 30, 14, 5, 5);
                    graphics.setColor(this.WHITE);
                    this.drawRoundRect(graphics, 200, 245, 30, 14);
                    n3 = this.m_cursor_now < 10 ? 1 : 2;
                    this.drawString(graphics, n3 + "/2", 225, 247, 8);
                }
                graphics.setColor(this.WHITE);
                this.drawRoundRect(graphics, 20, 165, 60, 85);
                if (this.m_array_num > 0) {
                    this.drawRoundRect(graphics, 20, 165, 60, 85);
                    this.drawString(graphics, "STAT", 50, 170, 1);
                    n3 = 0;
                    int n5 = 0;
                    if (this.m_eq_now == 0) {
                        n3 = player.attackBase;
                        n5 = player.attack - this.m_item_data[player.currentWeaponID][0] + this.m_item_data[this.m_array[this.m_cursor_now]][0];
                        this.drawString(graphics, "ATK.", 50, 183, 1);
                        this.drawString(graphics, "" + player.attack, 50, 200, 1);
                    } else {
                        n3 = player.defense - this.m_item_data[this.m_player_eq][0];
                        n5 = player.defense - this.m_item_data[this.m_player_eq][0] + this.m_item_data[this.m_array[this.m_cursor_now]][0];
                        this.drawString(graphics, "DEF.", 50, 183, 1);
                        this.drawString(graphics, "" + player.defense, 50, 200, 1);
                    }
                    this.drawString(graphics, "TO", 50, 215, 1);
                    if (this.m_player_eq == this.m_array[this.m_cursor_now]) {
                        this.drawString(graphics, "" + n3, 50, 230, 1);
                    } else {
                        this.drawString(graphics, "" + n5, 50, 230, 1);
                    }
                }
            }
        } 
        else if (this.m_mode == 13) 
        {
            int n6;
            int n7;
            n2 = 45 + (150 - this.m_grid_x * 5) / 2;
            int n8 = 57 + (150 - this.m_grid_z * 5) / 2;
            for (n7 = 0; n7 < this.m_grid_z; ++n7) {
                for (n6 = 0; n6 < this.m_grid_x; ++n6) {
                    int n9;
                    n = n7 * this.m_grid_x + n6;
                    if (this.m_map_ID < 0) {
                        if (n7 == 0 && n6 == 0) {
                            graphics.setColor(this.OUDO);
                        } else if (this.m_block_exist[n] == 2) {
                            graphics.setColor(this.OUDO);
                        } else if (this.m_block_exist[n] == 1) {
                            graphics.setColor(this.ORANGE);
                        } else if (this.m_block_exist[n] == 4) {
                            graphics.setColor(this.YELLOW);
                        } else if (this.m_block_exist[n] == 3) {
                            graphics.setColor(this.HADA);
                        } else if (this.m_block_level[n] == -1) {
                            graphics.setColor(this.BLACK);
                        } else if (this.m_block_level[n] == -200) {
                            graphics.setColor(this.DOKU);
                        } else if (this.m_block_level[n] < -200) {
                            graphics.setColor(this.BLACK);
                        } else {
                            graphics.setColor(this.HADA);
                        }
                        if (this.m_block_exist[n] != 2) {
                            if (n == this.m_start_grid) {
                                graphics.setColor(this.WATER);
                            } else if (n == this.m_goal_grid) {
                                graphics.setColor(this.BLUE);
                            } else {
                                for (n9 = 0; n9 < this.m_box_num; ++n9) {
                                    if (n != this.m_box[n9][0]) continue;
                                    graphics.setColor(this.GREEN);
                                }
                            }
                        }
                    } else if (this.m_map_memory[this.m_map_ID][n]) {
                        if (this.m_map_info[n] == 3) {
                            graphics.setColor(this.OUDO);
                        } else if (this.m_map_info[n] == 4) {
                            graphics.setColor(this.HADA);
                        } else if (this.m_map_info[n] == 2) {
                            graphics.setColor(this.YELLOW);
                        } else if (this.m_map_info[n] == 5) {
                            graphics.setColor(this.DOKU);
                        } else if (this.m_map_info[n] == 6) {
                            graphics.setColor(this.BLACK);
                        }
                        if (this.m_block_exist[n] != 2) {
                            if (n == this.m_start_grid) {
                                graphics.setColor(this.WATER);
                            } else if (n == this.m_goal_grid) {
                                graphics.setColor(this.BLUE);
                            } else {
                                for (n9 = 0; n9 < this.m_box_num; ++n9) {
                                    if (n != this.m_box[n9][0]) continue;
                                    graphics.setColor(this.GREEN);
                                }
                            }
                        }
                    } else {
                        graphics.setColor(this.GRAY);
                    }
                    graphics.fillRect(n2 + n6 * 5, n8 + n7 * 5, 5, 5);
                }
            }
            n7 = n2 + this.m_player_grid % this.m_grid_x * 5;
            n6 = n8 + this.m_player_grid / this.m_grid_x * 5;
            graphics.setColor(this.RED);
            if (player.rotY < 256 || player.rotY > 3840) {
                graphics.fillRect(n7, n6, 5, 1);
                graphics.fillRect(n7 + 1, n6 + 1, 3, 2);
                graphics.fillRect(n7 + 2, n6 + 3, 1, 2);
            } else if (player.rotY >= 256 && player.rotY < 768) {
                graphics.fillRect(n7, n6 + 2, 1, 2);
                graphics.fillRect(n7 + 1, n6 + 1, 1, 3);
                graphics.fillRect(n7 + 2, n6, 2, 4);
                graphics.fillRect(n7 + 3, n6 + 3, 2, 2);
            } else if (player.rotY >= 768 && player.rotY < 1280) {
                graphics.fillRect(n7, n6, 1, 5);
                graphics.fillRect(n7 + 1, n6 + 1, 2, 3);
                graphics.fillRect(n7 + 3, n6 + 2, 2, 1);
            } else if (player.rotY >= 1280 && player.rotY < 1792) {
                graphics.fillRect(n7, n6 + 1, 1, 2);
                graphics.fillRect(n7 + 1, n6 + 1, 1, 3);
                graphics.fillRect(n7 + 2, n6 + 1, 2, 4);
                graphics.fillRect(n7 + 3, n6, 2, 2);
            } else if (player.rotY >= 1792 && player.rotY < 2304) {
                graphics.fillRect(n7 + 2, n6, 1, 2);
                graphics.fillRect(n7 + 1, n6 + 2, 3, 2);
                graphics.fillRect(n7, n6 + 4, 5, 1);
            } else if (player.rotY >= 2304 && player.rotY < 2816) {
                graphics.fillRect(n7, n6, 2, 2);
                graphics.fillRect(n7 + 1, n6 + 1, 2, 4);
                graphics.fillRect(n7 + 3, n6 + 1, 1, 3);
                graphics.fillRect(n7 + 4, n6 + 1, 1, 2);
            } else if (player.rotY >= 2816 && player.rotY < 3328) {
                graphics.fillRect(n7, n6 + 2, 2, 1);
                graphics.fillRect(n7 + 2, n6 + 1, 2, 3);
                graphics.fillRect(n7 + 4, n6, 1, 5);
            } else {
                graphics.fillRect(n7, n6 + 3, 2, 2);
                graphics.fillRect(n7 + 1, n6, 2, 4);
                graphics.fillRect(n7 + 3, n6 + 1, 1, 3);
                graphics.fillRect(n7 + 4, n6 + 2, 1, 2);
            }
        } 
    }

    public void drawOver(Graphics graphics) {
        if (this.m_scene == 2 && (this.m_mode == 0 || this.m_mode == 1)) {
            if (this.m_player_damaged) {
                graphics.setColor(this.RED);
                graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
                this.m_player_damaged = false;
            }
            if (this.m_comment_frame < 50) {
                graphics.setColor(this.BLACK);
                graphics.fillRect(0, 215, this.getWidth(), 23);
                graphics.setColor(this.WHITE);
                this.setFont(graphics, this.m_font_small);
                ++this.m_comment_frame;
                this.drawString(graphics, this.m_comment_text, this.getWidth() / 2, 220, 1);
            } else if (this.m_add_comment) {
                this.m_comment_frame = 0;
                this.m_comment_text = this.m_add_comment_text;
                this.m_add_comment = false;
            }
        } else if (this.m_scene == 1) {
            if (this.m_op_frame < 550) {
                graphics.setColor(this.WHITE);
                this.setFont(graphics, this.m_font_small);
                this.drawString(graphics, "Presented by FromSoftware", this.getWidth() / 2, this.getHeight() / 2, 3);
            } else if (this.m_op_frame <= 10 || this.m_op_frame >= 580) {
                graphics.setColor(this.BLACK);
                graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
                graphics.setOrigin(0, -16);
                this.drawImage(graphics, this.m_img_game_title, this.getWidth() / 2, 40, 1);
                if (!this.m_starning) {
                    graphics.setColor(this.WHITE);
                    this.setFont(graphics, this.m_font_small);
                    this.drawString(graphics, "NEW GAME ", this.getWidth() / 2, 150, 1);
                    if (this.m_save_data < 2) {
                        graphics.setColor(this.DARK);
                    } else {
                        graphics.setColor(this.WHITE);
                    }
                    this.drawString(graphics, "LOAD GAME", this.getWidth() / 2, 175, 1);
                    if (this.m_save_data % 2 == 0) {
                        graphics.setColor(this.DARK);
                    } else {
                        graphics.setColor(this.WHITE);
                    }
                    this.drawString(graphics, "CONTINUE ", this.getWidth() / 2, 200, 1);
                    graphics.setColor(this.WHITE);
                    this.drawImage(graphics, this.m_img_copyright, this.getWidth() / 2, 239, 33);
                }
                graphics.setOrigin(0, 0);
            }
        } else if (this.m_scene == 7) {
            graphics.setColor(this.BLACK);
            this.setFont(graphics, this.m_font_small);
            if (this.m_op_frame > 50 && this.m_op_frame < 100) {
                this.drawString(graphics, "SKELETON KILLS: " + this.m_dead_count[0] + "", this.getWidth() / 2, this.getHeight() / 2, 1);
            } else if (this.m_op_frame > 120 && this.m_op_frame < 170) {
                this.drawString(graphics, "HEAD EATER KILLS: " + this.m_dead_count[1] + "", this.getWidth() / 2, this.getHeight() / 2, 1);
            } else if (this.m_op_frame > 190 && this.m_op_frame < 240) {
                this.drawString(graphics, "GOLEM KILLS: " + this.m_dead_count[2] + "", this.getWidth() / 2, this.getHeight() / 2, 1);
            } else if (this.m_op_frame > 260 && this.m_op_frame < 310) {
                this.drawString(graphics, "DEATH FIGHTER KILLS: " + this.m_dead_count[3] + "", this.getWidth() / 2, this.getHeight() / 2, 1);
            } else if (this.m_op_frame > 330 && this.m_op_frame < 380) {
                this.drawString(graphics, "TOTAL KILLS: " + this.m_dead_all + "", this.getWidth() / 2, this.getHeight() / 2, 1);
            } else if (this.m_op_frame > 400 && this.m_op_frame < 450) {
                this.drawString(graphics, "CHEST COMPLETION: " + this.m_getper_box + "%", this.getWidth() / 2, this.getHeight() / 2, 1);
            } else if (this.m_op_frame > 470 && this.m_op_frame < 520) {
                this.drawString(graphics, "ITEM COMPLETION : " + this.m_getper_taru + "%", this.getWidth() / 2, this.getHeight() / 2, 1);
            } else if (this.m_ending_count == 100 && (this.m_op_frame < 10 || this.m_op_frame >= 600)) {
                graphics.setColor(this.WHITE);
                graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            } else if (this.m_ending_count > 100) {
                graphics.setColor(this.WHITE);
                this.setFont(graphics, this.m_font_small);
                this.drawString(graphics, "End", this.getWidth() / 2, this.getHeight() / 2, 1);
            }
        } else if (this.m_scene == 3) {
            graphics.setColor(this.BLACK);
            graphics.fillRect(0, this.getHeight() / 2 - 25, 240, 50);
            graphics.setColor(this.WHITE);
            this.setFont(graphics, this.m_font_default);
            this.drawString(graphics, "GAME OVER", this.getWidth() / 2, this.getHeight() / 2 - 10, 3);
        } else if (this.m_scene == 5) {
            if (this.m_step_No == 0) {
                graphics.setColor(this.BLACK);
                graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            } else if (this.m_y == 0) {
                if (this.m_warp > 15 && this.m_warp < 25) {
                    graphics.setColor(this.BLACK);
                    graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            } else if (this.m_warp > 70 && this.m_warp < 90) {
                graphics.setColor(this.BLACK);
                graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
                if (this.m_saving) {
                    this.setFont(graphics, this.m_font_small);
                    graphics.setColor(this.WHITE);
                    this.drawString(graphics, "SAVING...", this.getWidth() / 2, this.getHeight() / 2 - 30, 1);
                    this.drawString(graphics, "WAIT UNTIL SAVE IS", this.getWidth() / 2, this.getHeight() / 2, 1);
                    this.drawString(graphics, "COMPLETE BEFORE EXITING!", this.getWidth() / 2, this.getHeight() / 2 + 30, 1);
                }
            }
        } else if (this.m_scene == 6) {
            graphics.setColor(this.BLACK);
            graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            this.setFont(graphics, this.m_font_small);
            graphics.setColor(this.WHITE);
            if (this.m_scene == 6) {
                this.drawString(graphics, "SAVE GAME?", this.getWidth() / 2, 85, 1);
            }
            this.drawString(graphics, "YES", this.getWidth() / 2, 147, 1);
            this.drawString(graphics, "NO", this.getWidth() / 2, 177, 1);
        }
    }

    private void drawCursor(Graphics graphics) {
        boolean bl = false;
        if (this.m_scene == 1 && this.m_op_frame >= 580) {
            bl = true;
        }
        if (this.m_scene == 6) {
            bl = true;
        } else if (this.m_mode == 5 || this.m_mode == 6 || this.m_mode == 8 || this.m_mode == 9 || this.m_mode == 11 || this.m_mode == 15) {
            bl = true;
        } else if (this.m_mode == 7) {
            if (this.m_cursor_max[2] > 0) {
                bl = true;
            }
        } else if (this.m_mode == 10 && this.m_array_num > 0) {
            bl = true;
        }
        if (bl) {
            this.drawImage(graphics, this.m_img_cursor, this.m_cursor_pos[this.m_cursor_place][this.m_cursor_now % 10][0], this.m_cursor_pos[this.m_cursor_place][this.m_cursor_now % 10][1], 20);
        }
    }

    private synchronized void directAttack(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        if (n == 0) {
            for (n7 = 0; n7 < this.m_enemy_num; ++n7) {
                n6 = this.m_enemyID_area[this.m_enemy[n7][4]] * 100 / 2;
                n5 = player.posX + Math.sin(player.rotY) * this.m_item_data[player.currentWeaponID][1] / 41;
                n4 = player.posZ + Math.cos(player.rotY) * this.m_item_data[player.currentWeaponID][1] / 41;
                boolean bl = false;
                if (this.m_enemy[n7][10] != 1 && this.m_enemy[n7][5] > 0 && (n3 = this.askValue2(this.m_enemy[n7][0] - n5, this.m_enemy[n7][2] - n4)) < n6) {
                    bl = true;
                }
                if (!bl || this.m_enemy[n7][4] / 5 == 3 && (this.m_enemy[n7][7] == 5 || this.m_enemy[n7][7] == 6)) continue;
                this.m_target_Noid = n7;
                this.enemyDamaged(this.m_target_Noid);
            }
        }
        if (n == 1) {
            n7 = this.m_enemyID_reach[4];
            n6 = this.m_enemyID_range[4] * 100 / 2;
            n5 = this.m_enemy[n2][0] + Math.sin(this.m_enemy[n2][3]) * n7 / 82;
            n3 = this.askValue2(player.posX - n5, player.posZ - (n4 = this.m_enemy[n2][2] + Math.cos(this.m_enemy[n2][3]) * n7 / 82));
            if (n3 < n6) {
                if (player.currentHP > 0) {
                    playerDamaged(n2);
                }
            } else {
                this.enemyDirect(n2, true);
            }
        }
    }

    private void playerDamaged(int n) {
        if (this.m_option[0]) {
            this.m_J_Dev.SetVibMode(true);
            this.m_vib_count = 0;
        }
        if (this.m_option[1] && player.currentHP > 0) {
            this.m_J_se.playMMF(1);
        }
        this.m_at_frame = this.m_at_limit;
        this.attackSetting();
        this.m_player_damaged = true;
        player.posX += Math.sin(this.m_enemy[n][3]) * 200 / 4096;
        player.posZ += Math.cos(this.m_enemy[n][3]) * 200 / 4096;
        int[] nArray = this.m_enemy[n];
        nArray[0] = nArray[0] - Math.sin(this.m_enemy[n][3]) * 200 / 4096;
        int[] nArray2 = this.m_enemy[n];
        nArray2[2] = nArray2[2] - Math.cos(this.m_enemy[n][3]) * 200 / 4096;
        this.hitStage(0, 0);
        int n2 = this.m_enemy[n][4];
        int n3 = this.m_enemyID_atta[n2] * this.m_enemyID_atta[n2] / (this.m_enemyID_atta[n2] * 2 + player.defense * player.defense / 5);
        if (n3 <= 0) {
            n3 = 1;
        }
        if (!this.m_nodamage) {
            player.currentHP -= n3;
        }
        int n4 = (this.m_rand.nextInt() >>> 1) % 10;
        if (!this.m_nodamage && n4 < 3) {
            if (this.m_enemyID_addi[this.m_enemy[n][4]] != 0 && this.m_enemyID_addi[this.m_enemy[n][4]] % 2 == 0) {
                this.m_player_condi[0][0] = 1;
            }
            if (this.m_enemyID_addi[this.m_enemy[n][4]] != 0 && this.m_enemyID_addi[this.m_enemy[n][4]] % 3 == 0) {
                this.m_player_condi[1][0] = 1;
                this.m_view_step = 25;
                this.m_view_rot = 10;
            }
            if (this.m_enemyID_addi[this.m_enemy[n][4]] != 0 && this.m_enemyID_addi[this.m_enemy[n][4]] % 5 == 0) {
                this.m_player_condi[2][0] = 1;
                this.m_view_area = 3;
                this.viewWaterArea();
            }
            if (this.m_enemyID_addi[this.m_enemy[n][4]] != 0 && this.m_enemyID_addi[this.m_enemy[n][4]] % 7 == 0) {
                this.m_player_condi[3][0] = 1;
            }
        }
    }

    private void enemyDamaged(int n) {
        int n2 = 0;
        boolean bl = false;
        int[] nArray = this.m_enemy[n];
        nArray[0] = nArray[0] + Math.sin(player.rotY) * 150 / 4096;
        int[] nArray2 = this.m_enemy[n];
        nArray2[2] = nArray2[2] + Math.cos(player.rotY) * 150 / 4096;
        this.hitStage(1, n);
        int n3 = this.m_enemy[n][4];
        int n5 = player.defense * 10 / this.m_enemyID_atta[n3];
        if (n5 == 0) {
            n5 = 1;
        }
        int n6 = player.attack - this.m_enemyID_deff[n3] + player.attack * player.attack / (this.m_enemyID_deff[n3] * 14 / 10);
        if ((n6 = n6 * this.m_gauge_attack / this.m_gauge_max) <= 0) {
            n6 = 1;
        }
        int[] nArray3 = this.m_enemy[n];
        nArray3[5] = nArray3[5] - n6;
        if (this.m_enemy[n][5] <= 0) {
            int n7;
            if (this.m_dead_count[n3 / 5] < 9999) {
                int n8 = n3 / 5;
                this.m_dead_count[n8] = this.m_dead_count[n8] + 1;
            }
            this.m_enemy[n][5] = 0;
            this.m_enemy[n][10] = 0;
            this.m_enemy[n][8] = 0;
            this.m_enemy[n][7] = 1;
            player.experience += this.m_enemyID_expe[this.m_enemy[n][4]];
            if (player.experience > 99999) {
                player.experience = 99999;
            }
            int n9 = 0;
            if (this.m_enemy[n][4] == 24) {
                int n10;
                this.m_scene = (byte)7;
                this.m_op_frame = 0;
                this.m_cover_colors = new int[]{this.WHITE};
                this.m_cover_num = 0;
                SoftLabelsClear();
                int n11 = 0;
                int n12 = 0;
                for (n10 = 0; n10 < 124; ++n10) {
                    if (this.m_box_flag[n10]) continue;
                    ++n11;
                }
                for (n10 = 1; n10 < 38; ++n10) {
                    if (this.m_taru_flag[n10]) continue;
                    ++n12;
                }
                this.m_getper_box = n11 * 100 / 124;
                this.m_getper_taru = n12 * 100 / 37;
                this.m_dead_all = this.m_dead_count[0] + this.m_dead_count[1] + this.m_dead_count[2] + this.m_dead_count[3];
            } else if (this.BossOrNot(n)) {
                this.m_boss_live = false;
                bl = true;
                n2 = 5;
                this.m_comment_text = "GOT LEVEL MAP!";
                this.m_comment_frame = 0;
                player.floor = (byte)(player.floor + 1);
                this.m_stair_right = false;
                this.m_memory = false;
                this.m_map_ID = this.m_stage_ID - this.m_memory_start[player.floor];
                this.m_map_memory = null;
                // system.gc();
                this.m_map_memory = new boolean[10][900];
            } else {
                if (this.m_enemy[n][4] <= 4) {
                    n9 = 5;
                } else if (this.m_enemy[n][4] <= 9) {
                    n9 = 6;
                } else if (this.m_enemy[n][4] <= 14) {
                    n9 = 5;
                } else if (this.m_enemy[n][4] <= 19) {
                    n9 = 6;
                }
                n7 = (this.m_rand.nextInt() >>> 1) % n9;
                if (n7 == 0) {
                    bl = true;
                    n2 = this.m_enemyID_item[this.m_enemy[n][4]];
                }
            }
            if (bl) {
                n7 = (this.m_rand.nextInt() >>> 1) % 5;
                if (n2 == 0) {
                    this.m_item_id = n7 < 4 ? 40 : 41;
                } else if (n2 == 1) {
                    this.m_item_id = n7 < 3 ? 40 : 41;
                } else if (n2 == 2) {
                    this.m_item_id = n7 < 2 ? 40 : (n7 < 3 ? 41 : (n7 < 4 ? 42 : 47));
                } else if (n2 == 3) {
                    this.m_item_id = n7 < 1 ? 40 : (n7 < 3 ? 42 : (n7 < 4 ? 45 : 46));
                } else if (n2 == 4) {
                    this.m_item_id = n7 < 3 ? 42 : (n7 < 4 ? 43 : 44);
                } else if (n2 == 5) {
                    this.m_item_id = this.BossOrNot(n) ? (n7 < 2 ? 45 : (n7 < 4 ? 46 : 47)) : (n7 < 3 ? 42 : (n7 < 4 ? 43 : 44));
                }
                if (this.m_item_id == 5) {
                    this.m_plt_now[3] = 0;
                } else if (this.m_item_id == 6) {
                    this.m_plt_now[3] = 1;
                } else if (this.m_item_id == 7) {
                    this.m_plt_now[3] = 3;
                }
                this.rePalet();
                this.m_item_style = 1;
                this.m_item_grid = this.m_enemy[n][9];
                this.setItemFigure(this.m_item_id);
                this.m_item_posX = this.m_enemy[n][0];
                this.m_item_posY = 500;
                this.m_item_posZ = this.m_enemy[n][2];
            }
        } else {
            int n13;
            this.m_enemy[n][8] = 0;
            this.m_enemy[n][7] = 0;
            this.m_enemy[n][10] = 2;
            if (this.m_enemy[n][4] / 5 == 2 && this.m_enemy[n][4] % 5 >= 2) {
                n13 = 0;
                if (this.m_enemy[n][3] < 1024) {
                    if (player.rotY - this.m_enemy[n][3] < 1024 || player.rotY - this.m_enemy[n][3] > 3072) {
                        n13 = 1;
                    }
                } else if (this.m_enemy[n][3] > 3072) {
                    if (this.m_enemy[n][3] - player.rotY < 1024 || this.m_enemy[n][3] - player.rotY > 3072) {
                        n13 = 1;
                    }
                } else if (player.rotY - this.m_enemy[n][3] > -1024 && player.rotY - this.m_enemy[n][3] < 1024) {
                    n13 = 1;
                }
                if (n13 != 0) {
                    int[] nArray4 = this.m_enemy[n];
                    nArray4[3] = nArray4[3] + 2048;
                }
            }
            for (n13 = 0; n13 < 30; ++n13) {
                this.shortDire(n);
                this.enemyDirect(n, true);
            }
        }
    }

    private boolean BossOrNot(int n) {
        if (this.m_enemy[n][4] == 9 && this.m_stage_ID == 4) {
            return true;
        }
        if (this.m_enemy[n][4] == 4 && this.m_stage_ID == 11) {
            return true;
        }
        if (this.m_enemy[n][4] == 14 && this.m_stage_ID == 17) {
            return true;
        }
        if (this.m_enemy[n][4] == 19 && this.m_stage_ID == 26) {
            return true;
        }
        return this.m_enemy[n][4] == 24 && this.m_stage_ID == 35;
    }

    private void shortDire(int n) {
        int n2 = this.m_enemy[n][3] - 2048;
        if (n2 > 0) {
            this.m_enemy[n][13] = player.rotY <= this.m_enemy[n][3] && player.rotY > n2 ? 0 : 1;
        } else {
            n2 += 4096;
            this.m_enemy[n][13] = player.rotY <= this.m_enemy[n][3] || player.rotY > n2 ? 0 : 1;
        }
    }

    private void FPS_update() {
        this.FPS_timeCur = System.currentTimeMillis();
        if (this.FPS_timeCur - this.FPS_timeBegin >= 1000L) 
        {
            this.FPS_timeBegin = System.currentTimeMillis();
        }
        this.FPS_timePast = this.FPS_timeCur - this.FPS_timePast;

        if (this.FPS_timePast < this.FPS_one_frame_time) 
        {
            try 
            {
                Thread.sleep(this.FPS_one_frame_time - this.FPS_timePast);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }   
        }

        this.m_paint_flag = true;
        this.FPS_timePast = System.currentTimeMillis();
    }

    public synchronized void processEvent(int n, int n2) {
        if (n == 0 && n2 == 21 && this.m_scene == 2) {
            if (this.m_mode == 0 || this.m_mode == 5 || this.m_mode == 7 || this.m_mode == 9 || this.m_mode == 11 || this.m_mode == 13 || this.m_mode == 15 || this.m_mode == 16) {
                this.m_mode = (byte)6;
                this.m_cursor_place = 1;
                this.m_cursor_now = 0;
            } else if (this.m_mode == 6) {
                this.m_mode = 0;
            } else if (this.m_mode == 8 || this.m_mode == 14) {
                this.m_mode = (byte)7;
                this.m_cursor_place = (byte)2;
                this.m_cursor_now = 0;
            } else if (this.m_mode == 10) {
                this.m_mode = (byte)9;
                this.m_cursor_place = (byte)4;
                this.m_cursor_now = 0;
            }
            if (this.m_mode == 0) {
                SoftLablesSet(2, 3);
            } else {
                if (this.m_mode != 2 && (this.m_mode == 6 || this.m_mode == 9 || this.m_mode == 7)) {
                    SoftLablesSet(4, 0);
                }
            }
            this.m_set_flag = false;
            this.m_paint_flag = true;
        }
        if (n == 0 && n2 == 22) {
            if (this.m_scene == 3 || this.m_scene == 7 && this.m_ending_count == 130) {
                this.m_cursor_place = 0;
                this.m_cursor_now = 0;
                this.m_scene = 1;
                this.checkTitle();
                this.openingStart();
                this.m_op_frame = 580;
                SoftLabelsClear();
                this.m_ending = false;
                this.m_ending_count = 0;
                this.m_cursor_now = 0;
                this.m_comment_frame = 50;
                this.m_add_comment = false;
                for (int i = 0; i < 4; ++i) {
                    this.m_player_condi[i][0] = 0;
                    this.m_player_condi[i][1] = 0;
                }
                this.m_option[0] = false;
                this.m_option[1] = false;
                this.m_option[2] = true;
                this.m_option[3] = false;
            } else if (this.m_scene == 2 && this.m_mode == 0 && player.currentWeaponID != 40 && this.m_at_frame == this.m_at_limit && this.m_player_condi[3][0] == 0) {
                this.m_at_ok = true;
                if (this.m_option[1]) {
                    this.m_J_se.playMMF(4);
                }
                this.m_at_frame = 0;
                this.m_gauge_attack = this.m_gauge_value;
                this.m_gauge_value = 0;
            }
        }
    }

    private void SoftLabelsClear() 
    {
        this.setSoftLabel(0, "");
        this.setSoftLabel(1, "");
    }

    private void SoftLablesSet(int soft1, int soft2) 
    {
        this.setSoftLabel(0, KingsField_Text.Commands[soft1]);
        this.setSoftLabel(1, KingsField_Text.Commands[soft2]);
    }

    private void readResource(byte by, byte by2) {
        byte[] byArray;
        if (by == 0) 
        {
            int n = by2 * 192;
            int n2 = 192;
            byArray = new byte[n];
            try {
                byte[] byArray2 = KingsField_P6Canvas.loadResourceData(48);
                System.arraycopy(byArray2, 0, byArray, 0, n);
                System.arraycopy(byArray2, n, this.m_buf, 54, n2);
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("palet read: error");
            }
        } else {
            int n;
            int n3;
            if (by == 1) {
                n3 = 316;
                n = 56;
            } else if (by == 2) {
                n3 = 372;
                n = 140;
            } else if (by == 3) {
                n3 = 512;
                n = 44;
            } else if (by == 4) {
                n3 = 768;
                n = 64;
            } else if (by == 5) {
                n3 = 832;
                n = 64;
            } else if (by == 6) {
                n3 = 896;
                n = 64;
            } else if (by == 7) {
                n3 = 960;
                n = 64;
            } else if (by == 8) {
                n3 = 600;
                n = 40;
            } else {
                n3 = 256;
                n = 40;
            }
            byArray = new byte[n3];
            StringBuffer stringBuffer = new StringBuffer("palet.plt");
            stringBuffer.insert(5, by2);
            try {
                byte[] byArray3 = KingsField_P6Canvas.loadResourceData(43 + by2);
                System.arraycopy(byArray3, 0, byArray, 0, n3 - 192);
                System.arraycopy(byArray3, n3 - 192, this.m_buf, 54 + n3, n);
            }
            catch (Exception exception) {
                KingsField_P6Canvas.Print("palet read: error");
            }
        }
        byArray = null;
        // system.gc();
    }

    /**
     * Calculates the manhatten distance in 1D?..
    **/
    private int gridDistance(int x, int y, int n3) 
    {
        int dx = x % m_grid_x - y % m_grid_x;
            dx = (dx ^ (dx >> 31)) + (dx >>> 31);

        if (n3 == 1)
            return dx;

        int dy = x / m_grid_x - y / m_grid_x;
            dy = (dy ^ (dy >> 31)) + (dy >>> 31);
       
        if (n3 == 2)
            return dy;

        return dx >= dy ? dx : dy;
    }

    private int askValue2(int n, int n2) 
    {
        return Math.sqrt(n * n + n2 * n2);
    }

    private void setItemFigure(int n) {
        try {
            this.m_item_fig = n <= 4 ? this.loadFigure(22) : (n <= 9 ? this.loadFigure(23) : (n <= 14 ? this.loadFigure(24) : (n <= 19 ? this.loadFigure(26) : (n <= 24 ? this.loadFigure(27) : (n <= 29 ? this.loadFigure(28) : (n <= 34 ? this.loadFigure(29) : (n <= 39 ? this.loadFigure(30) : (n == 40 ? this.loadFigure(35) : (n == 41 ? this.loadFigure(36) : (n == 42 ? this.loadFigure(37) : (n == 43 ? this.loadFigure(38) : (n == 44 ? this.loadFigure(39) : (n <= 47 ? this.loadFigure(40) : this.loadFigure(41))))))))))))));
            this.m_item_fig.setTexture(this.m_poly_tex);
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("setItemFigure  : read error");
        }
        if (n <= 14) {
            this.wepPltChange(n);
        } else if (n <= 39 || n >= 45) {
            this.guaPltChange(n);
        }
        this.rePalet();
        this.m_item_scale = n <= 14 ? 16384 : (n >= 40 ? 2048 : 4096);
    }

    private void setWepFigure() {
        try {
            this.m_wep_fig = player.currentWeaponID <= 4 ? this.loadFigure(22) : (player.currentWeaponID <= 9 ? this.loadFigure(23) : this.loadFigure(24));
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("wep figure : read error");
        }
        this.m_wep_fig.setTexture(this.m_poly_tex);
    }

    private void equipSet() {
        player.attack = player.attackBase + this.m_item_data[player.currentWeaponID][0];
        player.defense = player.defenseBase + this.m_item_data[player.currentHelmID][0] + this.m_item_data[player.currentPlateID][0] + this.m_item_data[player.currentArmsID][0] + this.m_item_data[player.currentLegsID][0] + this.m_item_data[player.currentShieldID][0];
        this.m_at_limit = this.m_item_data[player.currentWeaponID][2] > 2 ? this.m_item_data[player.currentWeaponID][2] * 2 : 5;
        this.m_at_frame = this.m_at_limit;
        this.m_at_ok = true;
    }

    private void resetTexuture() {
        this.m_fig[0].setTexture(this.m_poly_tex);
        this.m_fig[1].setTexture(this.m_poly_tex);
        this.m_fig[2].setTexture(this.m_poly_tex);
        this.m_fig[3].setTexture(this.m_poly_tex);
        this.m_fig[4].setTexture(this.m_poly_tex);
        this.m_wep_fig.setTexture(this.m_poly_tex);
        this.m_han_fig.setTexture(this.m_poly_tex);
        this.m_statue_fig.setTexture(this.m_poly_tex);
        this.m_box_near_fig.setTexture(this.m_poly_tex);
        this.m_box_far_fig.setTexture(this.m_poly_tex);
        this.m_item_fig.setTexture(this.m_poly_tex);
        this.m_warp_fig.setTexture(this.m_poly_tex);
        this.m_taru_fig.setTexture(this.m_poly_tex);
    }

    private int grid(int n, int n2) {
        return n2 * this.m_grid_x + n;
    }

    private byte stageID(int n, int n2) 
    {
        if (n2 == 0) {
            return 0;
        }
        if (n2 == 1) {
            return 1;
        }
        if (n2 == 2) {
            if (n == 1) {
                return 2;
            }
            return 3;
        }
        if (n2 == 3) {
            return 4;
        }
        if (n2 == 4) {
            if (n == 0) {
                return 5;
            }
            return 6;
        }
        if (n2 == 5) {
            if (n == 0) {
                return 7;
            }
            if (n == 1) {
                return 8;
            }
            return 9;
        }
        if (n2 == 6) {
            if (n == 0) {
                return 10;
            }
            return 11;
        }
        if (n2 == 7) {
            if (n == 0) {
                return 12;
            }
            return 13;
        }
        if (n2 == 8) {
            if (n == 0) {
                return 14;
            }
            if (n == 1) {
                return 15;
            }
            return 16;
        }
        if (n2 == 9) {
            return 17;
        }
        if (n2 == 10) {
            if (n == 0) {
                return 18;
            }
            if (n == 1) {
                return 19;
            }
            if (n == 2) {
                return 20;
            }
            return 21;
        }
        if (n2 == 11) {
            if (n == 0) {
                return 22;
            }
            if (n == 1) {
                return 23;
            }
            if (n == 2) {
                return 24;
            }
            return 25;
        }
        if (n2 == 12) {
            if (n == 2) {
                return 26;
            }
            return 27;
        }
        if (n2 == 13) {
            if (n == 1) {
                return 28;
            }
            if (n == 2) {
                return 29;
            }
            return 30;
        }
        if (n2 == 14) {
            if (n == 0) {
                return 31;
            }
            if (n == 1) {
                return 32;
            }
            if (n == 2) {
                return 33;
            }
            return 34;
        }
        if (n2 == 15) {
            if (n == 0) {
                return 35;
            }
            if (n == 1) {
                return 36;
            }
            if (n == 2) {
                return 36;
            }
            return 36;
        }
        return 37;
    }

    private boolean WriteRecord(int n, byte[] byArray) {
        --n;
        DataOutputStream filterOutputStream = null;
        try {
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                ++n2;
                n2 += this.m_arGameDataSize[i];
            }
            filterOutputStream = Connector.openDataOutputStream("scratchpad:///0;pos=" + (4 + n2));
            if (byArray != null) {
                filterOutputStream.writeByte(1);
                filterOutputStream.write(byArray, 0, byArray.length);
            } else {
                filterOutputStream.writeByte(0);
            }
            filterOutputStream.close();
        }
        catch (Exception exception) {
            try {
                if (filterOutputStream != null) {
                    filterOutputStream.close();
                }
            }
            catch (Exception exception2) {
                // empty catch block
            }
            return false;
        }
        return true;
    }

    private byte[] ReadRecord(int n) {
        --n;
        byte[] byArray = null;
        DataInputStream filterInputStream = null;
        try {
            int n2;
            int n3 = 0;
            for (n2 = 0; n2 < n; ++n2) {
                ++n3;
                n3 += this.m_arGameDataSize[n2];
            }
            n2 = this.m_arGameDataSize[n];
            filterInputStream = Connector.openDataInputStream("scratchpad:///0;pos=" + (4 + n3) + ",length=" + (n2 + 1));
            if (filterInputStream.readByte() != 0) {
                byArray = new byte[n2];
                filterInputStream.read(byArray);
            }
            filterInputStream.close();
        }
        catch (Exception exception) {
            try {
                if (filterInputStream != null) {
                    filterInputStream.close();
                }
            }
            catch (Exception exception2) {
                // empty catch block
            }
        }
        return byArray;
    }

    private void M3D_Atrans3i_Transpose(AffineTrans affineTrans) {
        this.m_3D_transX.m00 = affineTrans.m00;
        this.m_3D_transX.m10 = affineTrans.m01;
        this.m_3D_transX.m20 = affineTrans.m02;
        this.m_3D_transX.m01 = affineTrans.m10;
        this.m_3D_transX.m11 = affineTrans.m11;
        this.m_3D_transX.m21 = affineTrans.m12;
        this.m_3D_transX.m02 = affineTrans.m20;
        this.m_3D_transX.m12 = affineTrans.m21;
        this.m_3D_transX.m22 = affineTrans.m22;
        this.m_3D_transX.m03 = affineTrans.m03;
        this.m_3D_transX.m13 = affineTrans.m13;
        this.m_3D_transX.m23 = affineTrans.m23;
        this.m_3D_b_trans.m00 = this.m_3D_transX.m00;
        this.m_3D_b_trans.m10 = this.m_3D_transX.m10;
        this.m_3D_b_trans.m20 = this.m_3D_transX.m20;
        this.m_3D_b_trans.m01 = this.m_3D_transX.m01;
        this.m_3D_b_trans.m11 = this.m_3D_transX.m11;
        this.m_3D_b_trans.m21 = this.m_3D_transX.m21;
        this.m_3D_b_trans.m02 = this.m_3D_transX.m02;
        this.m_3D_b_trans.m12 = this.m_3D_transX.m12;
        this.m_3D_b_trans.m22 = this.m_3D_transX.m22;
        this.m_3D_b_trans.m03 = this.m_3D_transX.m03;
        this.m_3D_b_trans.m13 = this.m_3D_transX.m13;
        this.m_3D_b_trans.m23 = this.m_3D_transX.m23;
    }

    private void roomSetup() {
        this.i_g3d.setPerspective(1, 10000, 768);
        this.i_g3d.setScreenCenter(this.getWidth() / 2, this.getHeight() / 2);
        this.m_3D_b_trans.setIdentity();
        this.i_g3d.setAmbientLight(2048);
        this.i_g3d.setDirectionLight(new Vector3D(0, 4096, 0), 4096);
        this.i_g3d.enableLight(true);
        this.i_g3d.enableSemiTransparent(true);
        this.i_g3d.enableToonShader(false);
        this.i_g3d.enableSphereMap(false);
    }

    private void dataSetup() {
        try {
            this.m_img_cursor = KingsField_P6Canvas.loadImage(0);
            this.m_img_hp = KingsField_P6Canvas.loadImage(1);
            this.m_img_power = KingsField_P6Canvas.loadImage(2);
            this.m_img_condi[0] = KingsField_P6Canvas.loadImage(3);
            this.m_img_condi[2] = KingsField_P6Canvas.loadImage(4);
            this.m_img_condi[1] = KingsField_P6Canvas.loadImage(5);
            this.m_img_condi[3] = KingsField_P6Canvas.loadImage(6);
            this.m_img_houi = KingsField_P6Canvas.loadImage(7);
            this.m_img_game_title = KingsField_P6Canvas.loadImage(8);
            this.m_img_copyright = KingsField_P6Canvas.loadImage("copy.gif");
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            this.m_poly_tex = null;
            // system.gc();
            this.m_poly_tex = this.loadTexture(9, true);
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            this.m_fig[0] = this.loadFigure(11);
            this.m_act[0] = this.loadActionTable(12);
            this.m_fig[1] = this.loadFigure(13);
            this.m_act[1] = this.loadActionTable(14);
            this.m_fig[2] = this.loadFigure(15);
            this.m_act[2] = this.loadActionTable(16);
            this.m_fig[3] = this.loadFigure(17);
            this.m_act[3] = this.loadActionTable(18);
            this.m_fig[4] = this.loadFigure(19);
            this.m_act[4] = this.loadActionTable(20);
            this.m_wep_fig = this.loadFigure(22);
            this.m_wep_act = this.loadActionTable(25);
            this.m_han_fig = this.loadFigure(21);
            this.m_statue_fig = this.loadFigure(42);
            this.m_box_near_fig = this.loadFigure(31);
            this.m_box_near_act = this.loadActionTable(32);
            this.m_box_far_fig = this.loadFigure(33);
            this.m_item_fig = this.loadFigure(35);
            this.m_warp_fig = this.loadFigure(10);
            this.m_taru_fig = this.loadFigure(34);
            this.resetTexuture();
        }
        catch (Exception exception) {
            KingsField_P6Canvas.Print("figure read ; error");
        }
    }

    public synchronized void mediaAction(MediaPresenter mediaPresenter, int n, int n2) {
    }
}
 