package com.articreep.dungeonbossroommusic;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ExampleMod.MODID, useMetadata = true,  version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "dungeonroombossmusic";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(new Listeners());
    	ClientCommandHandler.instance.registerCommand(new StopMusic());
        
    }
    
}
