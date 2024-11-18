package com.hirrao.WirelessEnergy;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.math.BigInteger;
import java.util.UUID;

import static com.hirrao.WirelessEnergy.EventTick.totalEnergyLast;
import static com.hirrao.WirelessEnergy.EventTick.totalEnergyNow;

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
            BigInteger energyNow = totalEnergyNow.getOrDefault(uuid, BigInteger.ZERO);
            BigInteger energyLast = totalEnergyLast.getOrDefault(uuid, BigInteger.ZERO);
            BigInteger energyDiff = energyNow.subtract(energyLast);
            Log.LOGGER.info("energyNow: " + energyNow + ", energyLast: " + energyLast + ", energyDiff: " + energyDiff);
            sender.sendMessage(new TextComponentString("无线电网存储能量 " + energyNow + " EU"));
            if (energyDiff.compareTo(BigInteger.ZERO) >= 0) {
                sender.sendMessage(new TextComponentString("平均输入" + energyDiff.divide(BigInteger.valueOf(20)) + " EU/t"));
            } else if (energyDiff.compareTo(BigInteger.ZERO) < 0) {
                sender.sendMessage(new TextComponentString("平均输出" + energyDiff.abs().divide(BigInteger.valueOf(20)) + " EU/t"));
                BigInteger time = energyNow.divide(energyDiff.abs());
                sender.sendMessage(new TextComponentString("预计" + time + "S后能量耗尽"));
            }
        } catch (Exception e) {
            Log.LOGGER.error(e);
        }

    }
}
