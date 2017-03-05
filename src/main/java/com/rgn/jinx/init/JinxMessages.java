package com.rgn.jinx.init;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.network.EquipArrowInfoMessage;
import com.rgn.jinx.network.EquipArrowInfoMessageHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class JinxMessages {

    public static SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Jinx.MODID);

    public static void init(FMLPreInitializationEvent event) {
        networkWrapper.registerMessage(EquipArrowInfoMessageHandler.class, EquipArrowInfoMessage.class, 0, Side.SERVER);
    }

}