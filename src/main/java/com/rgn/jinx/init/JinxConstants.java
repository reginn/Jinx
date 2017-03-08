package com.rgn.jinx.init;

import com.rgn.jinx.item.EnumQuiverSize;

public class JinxConstants {

    public static final int GUI_ID_SMALL_QUIVER = 0;
    public static final int GUI_ID_MIDDLE_QUIVER = 1;
    public static final int GUI_ID_LARGE_QUIVER = 2;

    public static int getGuiID(EnumQuiverSize quiverSize) {
        switch (quiverSize) {
            case SMALL: return GUI_ID_SMALL_QUIVER;
            case MIDDLE: return GUI_ID_MIDDLE_QUIVER;
            case LARGE: return GUI_ID_LARGE_QUIVER;
            default : return -1;
        }
    }

}
