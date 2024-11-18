package com.hirrao.WirelessEnergy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = WirelessEnergy.MOD_ID, name = WirelessEnergy.MOD_NAME, version = WirelessEnergy.VERSION, dependencies = "required-after:gtlitecore@[0.0.1-alpha,)")
public class WirelessEnergy {

    public static final String MOD_ID = "wirelessenergy";
    public static final String MOD_NAME = "WirelessEnergy";
    public static final String VERSION = "0.0.2-beta";

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     * Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        Log.LOGGER.info("WireLessEnergy is starting");
        event.registerServerCommand(new Command());
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new Command());
        MinecraftForge.EVENT_BUS.register(new EventTick());
    }

}
