package com.articreep.dungeonbossroommusic;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class Listeners {
	public static Clip clip = null;
	public static boolean stopmusic = false;
	public static boolean checkingoff = false;
	public static boolean checkingon = false;
    @SubscribeEvent
    public void ChatReceived(ClientChatReceivedEvent event) throws Exception {
    	String string = Minecraft.getMinecraft().getCurrentServerData().serverIP;
    	if (!(string.endsWith("hypixel.net"))) return; // You can't connect to Hypixel using their numeral IP lol
    	// Credit TrollyLoki via Discord
    	// the player is on hypixel
    	String message = event.message.getUnformattedText();
    	if (message.equals("[BOSS] The Watcher: You have proven yourself. You may pass.")) {
    		//time to check whether the in-game music is off
    		Minecraft.getMinecraft().thePlayer.sendChatMessage("/togglemusic");
    		checkingoff = true;
    		return;
    	}
    	// Most boss messages have a space on each side of the colon because Hypixel has them for some reason..?
    	if (message.equals("[BOSS] Scarf : This is where the journey ends for you, Adventurers.")
    			|| message.equals("[BOSS] The Professor: I was burdened with terrible news recently...")
    			|| message.equals("[BOSS] Thorn : Welcome Adventurers! I am Thorn, the Spirit! And host of the Vegan Trials!")
    			|| message.equals("[BOSS] Livid : Welcome, you arrive right on time. I am Livid, the Master of Shadows.") 
    			|| message.equals("[BOSS] Sadan : So you made it all the way here...and you wish to defy me? Sadan?!")
    			|| message.equals("[BOSS] Necron: Finally, I heard so much about you. The Eye likes you very much.")) {
    		stopmusic = false;
	    	new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						playSound();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	    		
	    	}).start();
	    	
    	} else if (message.equals("[BOSS] Scarf : His technique.. is too advanced..")
    			|| message.equals("[BOSS] Necron : Before I have to deal with you myself.")
    			|| message.contains("\u2620 Defeated Thorn in")
    			|| message.endsWith("My shadows are everywhere, THEY WILL FIND YOU!!")
    			|| message.equals("[BOSS] Sadan : NOOOOOOOOO!!! THIS IS IMPOSSIBLE!!")
    			|| message.equals("[BOSS] Necron: I understand your words now, my master.")) {
    		stopMusic();
    		checkingon = true;
    		Minecraft.getMinecraft().thePlayer.sendChatMessage("/togglemusic");
    	}
    }
    
    @SubscribeEvent
    public void ChatReceivedMusicOff(ClientChatReceivedEvent event) {
    	if (checkingoff == false) return;
    	String message = event.message.getUnformattedText();
    	if (message.equals("Play Music is now enabled!")) {
    		Minecraft.getMinecraft().thePlayer.sendChatMessage("/togglemusic");
    		checkingoff = false;
    	} else if (message.equals("Play Music is now disabled!")) {
    		checkingoff = false;
    	} else {
    		return;
    	}
    }
    
    @SubscribeEvent
    public void ChatReceivedMusicOn(ClientChatReceivedEvent event) {
    	if (checkingon == false) return;
    	String message = event.message.getUnformattedText();
    	if (message.equals("Play Music is now enabled!")) {
    		checkingon = false;
    	} else if (message.equals("Play Music is now disabled!")) {
    		Minecraft.getMinecraft().thePlayer.sendChatMessage("/togglemusic");
    		checkingon = false;
    	} else {
    		return;
    	}
    }
    
    
    private static void playSound() throws Exception {
		// grab files
		File firstfile = new File("C:\\Users\\boomc\\Music\\dungeondramastart.wav");
		File finalfile = new File("C:\\Users\\boomc\\Music\\dungeondramaloop.wav");
		String status = null;
		clip = AudioSystem.getClip();
        final AudioInputStream firstaudioIn = AudioSystem.getAudioInputStream(firstfile);
        final AudioInputStream finalaudioIn = AudioSystem.getAudioInputStream(finalfile);
        clip.open(firstaudioIn);
        clip.start();
        FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(15f - 30.0f);
        int i = 0;
		while (true) {
        	if (clip.isActive() && i == 0) {
        		i = 1;
        	}
        	if (clip.isActive() == false && i > 0) {
        		i = 0;
        		break;
        	}
        	Thread.sleep(1);
        }
        clip.close();
        clip.open(finalaudioIn);
        if (stopmusic) return;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
        FloatControl gainControl2 = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl2.setValue(15f - 30.0f);
        while (true) {
        	if (clip.isActive() && i == 0) {
        		i = 1;
        	}
        	if (clip.isActive() == false && i > 0) {
        		i = 0;
        		break;
        	}
        }
        clip.stop();
        clip.close();
	}
    
    public void stopMusic() {
		Listeners.clip.stop(); 
        Listeners.clip.close(); 
        Listeners.stopmusic = true;
    }
}
