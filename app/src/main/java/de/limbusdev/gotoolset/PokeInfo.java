package de.limbusdev.gotoolset;

import android.util.ArrayMap;

import java.util.ArrayList;

/**
 * Created by georg on 09.10.16.
 */
public class PokeInfo {

    public final static int ANY=0, NORMAL=1, FIGHT=2, FLYING=3, POISON=4, GROUND=5,
        ROCK=6, BUG=7, GHOST=8, STEEL=9, FIRE=10, WATER=11, GRASS=12, ELECTRO=13,
        PSYCHIC=14, ICE=15, DRAGON=16, DARK=17, FAIRY=18;

    public ArrayMap<Integer,Pokemon> pokeList;

    private static  PokeInfo instance;

    public static final float[] levelToCpm = {
        0.094f,       0.135137432f,  0.16639787f,   0.192650919f,   0.21573247f,    /* 01 - 05 = [1, 1.5, 2, 2.5, 3]    */
        0.236572661f, 0.25572005f,   0.273530381f,  0.29024988f,    0.306057377f,   /* 06 - 10 = [3.5, 4, 4.5, 5, 5.5]  */
        0.3210876f,   0.335445036f,  0.34921268f,   0.362457751f,   0.37523559f,    /* 11 - 15 = [6 .. 8]               */
        0.387592406f, 0.39956728f,   0.411193551f,  0.42250001f,    0.432926419f,   /* 16 - 20 = [8.5 .. 10.5]          */
        0.44310755f,  0.4530599578f, 0.46279839f,   0.472336083f,   0.48168495f,    /* 21 - 25 = [11 .. 13]             */
        0.4908558f,   0.49985844f,   0.508701765f,  0.51739395f,    0.525942511f,   /* 26 - 30 = [13.5 .. 15.5]         */
        0.53435433f,  0.542635767f,  0.55079269f,   0.558830576f,   0.56675452f,    /* 31 - 35 = [16 .. 18]             */
        0.574569153f, 0.58227891f,   0.589887917f,  0.59740001f,    0.604818814f,   /* 36 - 40 = [18.5 .. 20.5]         */
        0.61215729f,  0.619399365f,  0.62656713f,   0.633644533f,   0.64065295f,    /* 41 - 45 = [21 .. 23]             */
        0.647576426f, 0.65443563f,   0.661214806f,  0.667934f,      0.674577537f,   /* 46 - 50 = [23.5 .. 25.5]         */
        0.68116492f,  0.687680648f,  0.69414365f,   0.700538673f,   0.70688421f,    /* 51 - 55 = [26 .. 28]             */
        0.713164996f, 0.71939909f,   0.725571552f,  0.7317f,        0.734741009f,   /* 56 - 60 = [28.5 .. 30.5]         */
        0.73776948f,  0.740785574f,  0.74378943f,   0.746781211f,   0.74976104f,    /* 61 - 65 = [31 .. 33]             */
        0.752729087f, 0.75568551f,   0.758630378f,  0.76156384f,    0.764486065f,   /* 66 - 70 = [33.5 .. 35.5]         */
        0.76739717f,  0.770297266f,  0.7731865f,    0.776064962f,   0.77893275f,    /* 71 - 75 = [36 - 38]              */
        0.781790055f, 0.78463697f,   0.787473578f,  0.79030001f,    0.7931164f      /* 76 - 80 = [38.5 - 40.5]          */
    };

    /* Dust Costs for raising level from level 1 to 40.5 */
    public static final int[] dustCosts = {
        200,200,200,200,
        400,400,400,400,
        600,600,600,600,
        800,800,800,800,
        1000,1000,1000,1000,
        1300,1300,1300,1300,
        1600,1600,1600,1600,
        1900,1900,1900,1900,
        2200,2200,2200,2200,
        2500,2500,2500,2500,
        3000,3000,3000,3000,
        3500,3500,3500,3500,
        4000,4000,4000,4000,
        4500,4500,4500,4500,
        5000,5000,5000,5000,
        6000,6000,6000,6000,
        7000,7000,7000,7000,
        8000,8000,8000,8000,
        9000,9000,9000,9000,
        10000,10000,10000,10000
    };


    public static int[][] types = {
        {},
        {19,20,52,53,108,113,115,128,132,133,137,143},
        {56,57,66,67,68,106,107},
        {6,12,16,17,18,21,22,41,42,83,84,85,123,130,142,144,145,146,149},
        {1,2,3,13,14,15,23,24,29,30,31,32,33,34,41,42,43,44,45,48,49,69,70,71,72,73,88,89,92,93,94,109,110},
        {27,28,31,34,50,51,74,75,76,95,104,111,112},
        {74,75,76,95,138,139,140,141,142},
        {10,11,12,13,14,15,46,47,48,49,123,127},
        {92,93,94},
        {81,82},
        {4,5,6,37,38,58,59,77,78,126,136,146},
        {7,8,9,54,55,60,61,62,72,73,79,80,86,87,90,91,98,99,116,117,118,119,120,121,129,130,131,134},
        {1,2,3,43,44,45,46,47,69,70,71,102,103,114},
        {25,26,81,82,100,101,125,135,145},
        {63,64,65,79,80,96,97,102,103,121,122,124,150,151},
        {87,91,124,131,144},
        {147,148,149},
        {},
        {35,36,39,40,122}
    };

    public static final int[][] poke_base_values = {
        {1,126,126,90},
        {2,156,158,120},
        {3,198,200,160},
        {4,128,108,78},
        {5,160,140,116},
        {6,212,182,156},
        {7,112,142,88},
        {8,144,176,118},
        {9,186,222,158},
        {10,62,66,90},
        {11,56,86,100},
        {12,144,144,120},
        {13,68,64,80},
        {14,62,82,90},
        {15,144,130,130},
        {16,94,90,80},
        {17,126,122,126},
        {18,170,166,166},
        {19,92,86,60},
        {20,146,150,110},
        {21,102,78,80},
        {22,168,146,130},
        {23,112,112,70},
        {24,166,166,120},
        {25,124,108,70},
        {26,200,154,120},
        {27,90,114,100},
        {28,150,172,150},
        {29,100,104,110},
        {30,132,136,140},
        {31,184,190,180},
        {32,110,94,92},
        {33,142,128,122},
        {34,204,170,162},
        {35,116,124,140},
        {36,178,178,190},
        {37,106,118,76},
        {38,176,194,146},
        {39,98,54,230},
        {40,168,108,280},
        {41,88,90,80},
        {42,164,164,150},
        {43,134,130,90},
        {44,162,158,120},
        {45,202,190,150},
        {46,122,120,70},
        {47,162,170,120},
        {48,108,118,120},
        {49,172,154,140},
        {50,108,86,20},
        {51,148,140,70},
        {52,104,94,80},
        {53,156,146,130},
        {54,132,112,100},
        {55,194,176,160},
        {56,122,96,80},
        {57,178,150,130},
        {58,156,110,110},
        {59,230,180,180},
        {60,108,98,80},
        {61,132,132,130},
        {62,180,202,180},
        {63,110,76,50},
        {64,150,112,80},
        {65,186,152,110},
        {66,118,96,140},
        {67,154,144,160},
        {68,198,180,180},
        {69,158,78,100},
        {70,190,110,130},
        {71,222,152,160},
        {72,106,136,80},
        {73,170,196,160},
        {74,106,118,80},
        {75,142,156,110},
        {76,176,198,160},
        {77,168,138,100},
        {78,200,170,130},
        {79,110,110,180},
        {80,184,198,190},
        {81,128,138,50},
        {82,186,180,100},
        {83,138,132,104},
        {84,126,96,70},
        {85,182,150,120},
        {86,104,138,130},
        {87,156,192,180},
        {88,124,110,160},
        {89,180,188,210},
        {90,120,112,60},
        {91,196,196,100},
        {92,136,82,60},
        {93,172,118,90},
        {94,204,156,120},
        {95,90,186,70},
        {96,104,140,120},
        {97,162,196,170},
        {98,116,110,60},
        {99,178,168,110},
        {100,102,124,80},
        {101,150,174,120},
        {102,110,132,120},
        {103,232,164,190},
        {104,102,150,100},
        {105,140,202,120},
        {106,148,172,100},
        {107,138,204,100},
        {108,126,160,180},
        {109,136,142,80},
        {110,190,198,130},
        {111,110,116,160},
        {112,166,160,210},
        {113,40,60,500},
        {114,164,152,130},
        {115,142,178,210},
        {116,122,100,60},
        {117,176,150,110},
        {118,112,126,90},
        {119,172,160,160},
        {120,130,128,60},
        {121,194,192,120},
        {122,154,196,80},
        {123,176,180,140},
        {124,172,134,130},
        {125,198,160,130},
        {126,214,158,130},
        {127,184,186,130},
        {128,148,184,150},
        {129,42,84,40},
        {130,192,196,190},
        {131,186,190,260},
        {132,110,110,96},
        {133,114,128,110},
        {134,186,168,260},
        {135,192,174,130},
        {136,238,178,130},
        {137,156,158,130},
        {138,132,160,70},
        {139,180,202,140},
        {140,148,142,60},
        {141,190,190,120},
        {142,182,162,160},
        {143,180,180,320},
        {144,198,242,180},
        {145,232,194,180},
        {146,242,194,180},
        {147,128,110,82},
        {148,170,152,122},
        {149,250,212,182},
        {150,284,202,212},
        {151,220,220,200}
    };

    private PokeInfo() {
        pokeList = new ArrayMap<>();
        for (int i = 1; i <= 151; i++) {
            Pokemon p = new Pokemon(i, 0,0,0);
            pokeList.put(i, p);
        }
    }

    public static PokeInfo getInstance() {
        if(instance == null) instance = new PokeInfo();
        return instance;
    }

    public static ArrayMap<Integer,Float[]> dustToLevel(int dust) {
        ArrayMap<Integer,Float[]> dustToLevel = new ArrayMap<>();

        dustToLevel.put(200,    new Float[]{1f,1.5f,2f,2.5f});
        dustToLevel.put(400,    new Float[]{3f,3.5f,4f,4.5f});
        dustToLevel.put(600,    new Float[]{5f,5.5f,6f,6.5f});
        dustToLevel.put(800,    new Float[]{7f,7.5f,8f,8.5f});
        dustToLevel.put(1000,   new Float[]{9f,9.5f,10f,10.5f});
        dustToLevel.put(1300,   new Float[]{11f,11.5f,12f,12.5f});
        dustToLevel.put(1600,   new Float[]{13f,13.5f,14f,14.5f});
        dustToLevel.put(1900,   new Float[]{15f,15.5f,16f,16.5f});
        dustToLevel.put(2200,   new Float[]{17f,17.5f,18f,18.5f});
        dustToLevel.put(2500,   new Float[]{19f,19.5f,20f,20.5f});
        dustToLevel.put(3000,   new Float[]{21f,21.5f,22f,22.5f});
        dustToLevel.put(3500,   new Float[]{23f,23.5f,24f,24.5f});
        dustToLevel.put(4000,   new Float[]{25f,25.5f,26f,26.5f});
        dustToLevel.put(4500,   new Float[]{27f,27.5f,28f,28.5f});
        dustToLevel.put(5000,   new Float[]{29f,29.5f,30f,30.5f});
        dustToLevel.put(6000,   new Float[]{31f,31.5f,32f,32.5f});
        dustToLevel.put(7000,   new Float[]{33f,33.5f,34f,34.5f});
        dustToLevel.put(8000,   new Float[]{35f,35.5f,36f,36.5f});
        dustToLevel.put(9000,   new Float[]{37f,37.5f,38f,38.5f});
        dustToLevel.put(10000,  new Float[]{39f,39.5f,40f,40.5f});

        return dustToLevel;
    }



}
