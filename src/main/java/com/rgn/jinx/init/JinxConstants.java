package com.rgn.jinx.init;

import com.rgn.jinx.item.EnumQuiverSize;
import com.rgn.jinx.item.ItemSeedBag;
import net.minecraft.item.ItemStack;
import scala.tools.nsc.doc.model.Public;

public class JinxConstants {

    public static final int GUI_ID_SMALL_QUIVER = 0;
    public static final int GUI_ID_MIDDLE_QUIVER = 1;
    public static final int GUI_ID_LARGE_QUIVER = 2;
    public static final int GUI_ID_SEED_BAG = 3;

    public static int getGuiID(EnumQuiverSize quiverSize) {
        switch (quiverSize) {
            case SMALL: return GUI_ID_SMALL_QUIVER;
            case MIDDLE: return GUI_ID_MIDDLE_QUIVER;
            case LARGE: return GUI_ID_LARGE_QUIVER;
            default : return -1;
        }
    }

    public static int getGuiID(ItemStack itemStack) {

        if (itemStack.getItem() instanceof ItemSeedBag) {
            return GUI_ID_SEED_BAG;
        }

        return -1;
    }

}
