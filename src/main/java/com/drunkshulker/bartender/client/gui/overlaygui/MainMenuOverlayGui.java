package com.drunkshulker.bartender.client.gui.overlaygui;

import com.drunkshulker.bartender.Bartender;
import com.drunkshulker.bartender.client.gui.GuiHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class MainMenuOverlayGui extends Gui{

	public MainMenuOverlayGui(Minecraft mc) {
		ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        
        
		
        if(GuiHandler.menuWaterMark) {
        	String versionText = Bartender.NAME +" "+Bartender.VERSION + " by DrunkShulker";
			drawString(mc.fontRenderer, versionText, width - mc.fontRenderer.getStringWidth(versionText) - 4, 4, Integer.parseInt("FFFFFF", 16));
        }
	}

}
