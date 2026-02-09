package net.deadlydiamond.legend_of_steve.client.iridescence;

public class IridescenceItemOffsetManager {
    private static float[] posOffset = new float[3];
    private static boolean inScreen = false;

    public static void resetPosOffset() {
        posOffset[0] = 0f;
        posOffset[1] = 0f;
        posOffset[2] = 0f;
    }

    public static void setPosOffset(float x, float y, float z) {
        posOffset[0] = x;
        posOffset[1] = y;
        posOffset[2] = z;
    }

    public static float[] getPosOffset() {
        return posOffset;
    }

    public static void setInScreen(boolean value) {
        inScreen = value;
    }

    public static boolean isInScreen() {
        return inScreen;
    }
}
