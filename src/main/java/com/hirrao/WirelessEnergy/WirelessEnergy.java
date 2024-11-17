package com.hirrao.WirelessEnergy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = WirelessEnergy.MOD_ID, name = WirelessEnergy.MOD_NAME, version = WirelessEnergy.VERSION)
public class WirelessEnergy {

    public static final String MOD_ID = "wirelessenergy";
    public static final String MOD_NAME = "WirelessEnergy";
    public static final String VERSION = "0.0.1-beta";
    public static final Logger LOGGER = LogManager.getLogger(WirelessEnergy.MOD_NAME);

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     * Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("WireLessEnergy is starting");
        event.registerServerCommand(new Command());
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new Command());
    }

}
