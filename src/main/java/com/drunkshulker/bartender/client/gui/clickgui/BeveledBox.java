package com.drunkshulker.bartender.client.gui.clickgui;

import net.minecraft.client.gui.Gui;

public class BeveledBox {
	
	public static void drawBeveledBox(int x1, int y1, int x2, int y2, int thickness, int topleftcolor, int botrightcolor, int fillcolor) {
	    Gui.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
	    
	    Gui.drawRect(x1+thickness, y1, x2 - 1, y1 + thickness, topleftcolor);
	    Gui.drawRect(x1, y1, x1 + thickness, y2 - 1, topleftcolor);
	    Gui.drawRect(x2 - thickness, y1, x2, y2 - 1, botrightcolor);
	    Gui.drawRect(x1, y2 - thickness, x2, y2, botrightcolor);
	}
	
}
