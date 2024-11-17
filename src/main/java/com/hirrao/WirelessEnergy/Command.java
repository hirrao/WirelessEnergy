package com.hirrao.WirelessEnergy;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;

public class Command extends CommandBase {

    @Override
    public String getName() {
        return "energy";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/energy <user>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException(getUsage(sender));
        }
        String playerName = args[0];
        EntityPlayer player;
        try {
            player = getPlayer(server, sender, playerName);
        } catch (PlayerNotFoundException e) {
            sender.sendMessage(new TextComponentString("No player found with name: " + playerName));
            return;
        }
        UUID uuid = player.getUniqueID();
        World world = sender.getEntityWorld();
        if (world.isRemote) {
            sender.sendMessage(new TextComponentString("This command can only be run on the server."));
            return;
        }
        try {
            Class<?> clazz = Class.forName("magicbook.gtlitecore.api.misc.GlobalVariableStorage");
            Field field = clazz.getDeclaredField("WirelessEnergy");
            @SuppressWarnings("unchecked") HashMap<UUID, BigInteger> wirelessEnergy = (HashMap<UUID, BigInteger>) field.get(null);
            BigInteger energy = wirelessEnergy.getOrDefault(uuid, BigInteger.ZERO);
            sender.sendMessage(new TextComponentString("Energy: " + energy));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
