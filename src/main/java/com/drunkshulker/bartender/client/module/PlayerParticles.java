package com.drunkshulker.bartender.client.module;


import com.drunkshulker.bartender.client.gui.clickgui.ClickGuiSetting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerParticles {

	public static final EnumParticleTypes defaultParticle = EnumParticleTypes.PORTAL;
	
	public static EnumParticleTypes particle = defaultParticle;
	public static boolean enabled = true;
	public static boolean enabledWhenFlying = false;
	public static int rate = 1, lift = 0;
	

	public static void setParticle(String name) {
        try {
        	particle = EnumParticleTypes.getByName(name);
		} catch (Exception e) {
			particle = defaultParticle;
		}
        if(particle == null) particle = defaultParticle;
	}
	
	private static void setState(String state) {	
		enabled = !state.equals("off");
		enabledWhenFlying = state.equals("flight");
	}
	
	private static void setRate(String i) {
		if(i.equals("low")) rate = 1;
		else if(i.equals("mid")) rate = 2;
		else rate = 3;
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.side.isClient() && enabled)
		{
			EntityPlayerSP player = Minecraft.getMinecraft().player;
			if(player.isElytraFlying()&&!enabledWhenFlying) return;
            short short1 = 128;
            for (int l = 0; l < rate; l++)
            {
                double d3 = player.posX;
                double d4 = player.posY;
                double d5 = player.posZ;
                double d6 = (double)l / ((double)short1 - 1.0D);
                float f = (player.getEntityWorld().rand.nextFloat() - 0.5F) * 0.2F;
                float f1 = (player.getEntityWorld().rand.nextFloat() - 0.5F) * 0.2F;
                float f2 = (player.getEntityWorld().rand.nextFloat() - 0.5F) * 0.2F;
                double d7 = d3 + (player.posX - d3) * d6 + (player.getEntityWorld().rand .nextDouble() - 0.5D) * (double)player.width * 2.0D;
                double d8 = d4 + (player.posY - d4) * d6 - player.getEntityWorld().rand.nextDouble() * (double)player.height;
                double d9 = d5 + (player.posZ - d5) * d6 + (player.getEntityWorld().rand.nextDouble() - 0.5D) * (double)player.width * 2.0D;
                player.world.spawnParticle(particle, d7, d8+lift, d9, (double)f, (double)f1, (double)f2);

            }
		}

	}

	public static void applyPreferences(ClickGuiSetting[] contents) {
		for (ClickGuiSetting setting : contents) {
			switch (setting.title) {
			case "type":
				setParticle(setting.values.get(setting.value).getAsString());
				break;
			case "state":
				setState(setting.values.get(setting.value).getAsString());
				break;
			case "rate":
				setRate(setting.values.get(setting.value).getAsString());
				break;
			case "lift":
				lift=setting.value;
				break;
			default:
				break;
			}
		}	
	}
}