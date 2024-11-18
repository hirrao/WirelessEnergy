package com.hirrao.WirelessEnergy;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;

public class EventTick {
    private static final int TICKS_PER_SECOND = 20;
    public static HashMap<UUID, BigInteger> totalEnergyNow;
    public static HashMap<UUID, BigInteger> totalEnergyLast;
    private int tickCount = 0;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tickCount++;
            if (tickCount >= TICKS_PER_SECOND) {
                tickCount = 0;
                try {
                    Class<?> clazz = Class.forName("magicbook.gtlitecore.api.misc.GlobalVariableStorage");
                    Field field = clazz.getDeclaredField("WirelessEnergy");
                    HashMap<UUID, BigInteger> wirelessEnergy = (HashMap<UUID, BigInteger>) field.get(null);
                    if (totalEnergyNow == null) {
                        totalEnergyNow = wirelessEnergy;
                    }
                    totalEnergyLast = new HashMap<>(totalEnergyNow);
                    totalEnergyNow = wirelessEnergy;
                } catch (NoSuchFieldException | ClassNotFoundException e) {
                    Log.LOGGER.error("Failed to get WirelessEnergy from GlobalVariableStorage");
                } catch (Exception e) {
                    Log.LOGGER.error(e);
                }
            }
        }
    }
}
