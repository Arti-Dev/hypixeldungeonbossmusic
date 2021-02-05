package com.articreep.dungeonbossroommusic;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class StopMusic extends CommandBase {

	@Override
	public String getCommandName() {
		return "stopmusic";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "Usage: /stopmusic";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "Music stopped"));
		Listeners.clip.stop(); 
        Listeners.clip.close(); 
        Listeners.stopmusic = true;
		
	}
	
	@Override
    public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender)
    {
        return true;
    }
}
