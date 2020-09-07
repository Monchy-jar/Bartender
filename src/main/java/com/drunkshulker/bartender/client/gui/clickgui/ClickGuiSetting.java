package com.drunkshulker.bartender.client.gui.clickgui;

import java.util.ArrayList;

import com.drunkshulker.bartender.client.gui.GuiConfig;
import com.drunkshulker.bartender.util.Config;
import com.drunkshulker.bartender.util.Preferences;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class ClickGuiSetting {
	
	enum SettingType {
			CLICK, 
			CLICK_COMMAND, 
			TEXT, 
	}
	
	public String title;
	public String[] desc;
	public SettingType type;
	public int value;
	public JsonArray values;
	public boolean closeOnClick;
	
	
	public int renderMinX=0, renderMinY=0, renderMaxX=0, renderMaxY=0;
	
	public String panelTitle;
	
	public static ClickGuiSetting[] settingsFromJson(JsonArray json, String pTitle) {
		ArrayList<ClickGuiSetting> settings = new ArrayList<ClickGuiSetting>();
		
		json.forEach((elem) ->
	    {
	        if (elem.isJsonObject())
	        {
	        	settings.add(settingFromJson(elem.getAsJsonObject(), pTitle));
	        }
	    });
		
		ClickGuiSetting[] i = new ClickGuiSetting[settings.size()];
        return settings.toArray(i);
	}

	private static ClickGuiSetting settingFromJson(JsonObject json, String pTitle) {
		ClickGuiSetting setting = new ClickGuiSetting();
		setting.title = json.get("title").getAsString();
		setting.panelTitle = pTitle;
		ArrayList<String> descs = new ArrayList<String>();	
		json.get("desc").getAsJsonArray().forEach((elem) ->
	    {  
	        descs.add(elem.getAsString());  
	    });
		String[] i = new String[descs.size()];
		setting.desc = descs.toArray(i);

		switch (json.get("type").getAsString()) {
		case "text":
			setting.type = SettingType.TEXT;
			break;
		case "click":	
			setting.type = SettingType.CLICK;
			setting.closeOnClick = json.get("closeOnClick").getAsBoolean();
			break;
		case "clickCommand":	
			setting.type = SettingType.CLICK_COMMAND;
			setting.closeOnClick = json.get("closeOnClick").getAsBoolean();
			break;
		default:		
			break;
		}
		

		if(setting.type==SettingType.TEXT) {
			setting.value = json.get("value").getAsInt();
			setting.values = json.get("values").getAsJsonArray();
		}
		
		return setting;
	}

	public JsonObject toJson() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("title", title);
		
		JsonArray descs = new JsonArray();
		for (String s : desc) {
			descs.add(s);
		}
		obj.add("desc", descs);
		
		switch (type) {
		case TEXT:
			obj.addProperty("type", "text");
			obj.addProperty("value", value);
			obj.add("values", values);
			break;
		case CLICK:
			obj.addProperty("type", "click");
			obj.addProperty("closeOnClick", closeOnClick);
			break;
		case CLICK_COMMAND:
			obj.addProperty("type", "clickCommand");
			obj.addProperty("closeOnClick", closeOnClick);
			break;
		default:
			break;
		}
		
		return obj;
	}
	
	public static void handleClick(ClickGuiSetting setting, boolean middleClick) {
		switch (setting.type) {
		case TEXT:
			if(middleClick){
				if(setting.value<=0) setting.value = setting.values.size()-1;
				else setting.value--;

			}else {
				if(setting.value>=setting.values.size()-1) setting.value = 0;
				else setting.value++;

			}
			
			Preferences.apply();
			break;

		case CLICK:
			
			Preferences.execute(setting);
			
			if(setting.closeOnClick) {
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
			}
			break;
		case CLICK_COMMAND:
			Minecraft.getMinecraft().player.sendChatMessage(Config.HOTKEY_COMMANDS[Integer.parseInt(setting.title)]);
			
			if(setting.closeOnClick) {
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
			}

			break;
		default:
			System.out.println("handleClick() unexpected default case!");
			return;
		}

		
		GuiConfig.save();
	}
}
