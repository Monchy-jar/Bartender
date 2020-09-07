package com.drunkshulker.bartender.client.module;

import java.util.ArrayList;
import java.util.Collections;

import com.drunkshulker.bartender.client.social.PlayerFriends;
import com.drunkshulker.bartender.client.social.PlayerGroup;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class EntityRadar {
	
	public static ArrayList<String> nearbyPlayers(){	
		ArrayList<String> a = new ArrayList<String>();
		if(Minecraft.getMinecraft().player==null) return a;
		if(Minecraft.getMinecraft().world==null) return a;
		for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
            if (e == null || e.isDead) continue;
            
            if(e instanceof EntityPlayer) {
            	if(((EntityPlayer) e).getDisplayNameString().equals(Minecraft.getMinecraft().player.getDisplayNameString())) continue;
                a.add(((EntityPlayer) e).getDisplayNameString());
            }
        }
		Collections.sort(a);
		return a;
	}
	
	public static ArrayList<String> nearbyGroupMembers(){
		ArrayList<String> a = nearbyPlayers();
		
		ArrayList<String> membersClone = new ArrayList<String>(PlayerGroup.members);
		
		for (String member : PlayerGroup.members) {
			if(!a.contains(member)) membersClone.remove(member);
		}
		Collections.sort(membersClone);
		return membersClone;
	}

	
	public static ArrayList<String> nearbyPlayersNoGroup(){
		ArrayList<String> a = nearbyPlayers();

		for (String member : PlayerGroup.members) {
			if(a.contains(member)) a.remove(member);
		}
		Collections.sort(a);
		return a;
	}

	
	public static ArrayList<String> nearbyPotentialEnemiesToBodyGuard(){
		ArrayList<String> a = nearbyPlayers();

		for (String member : PlayerGroup.members) {
			if(a.contains(member)) a.remove(member);
		}
		if(Bodyguard.friendly== Bodyguard.Friendly.BOTH||Bodyguard.friendly==Bodyguard.Friendly.IMPACT_FRIENDS)
		for (String member : PlayerFriends.impactFriends) {
			if(a.contains(member)) a.remove(member);
		}
		if(Bodyguard.friendly== Bodyguard.Friendly.BOTH||Bodyguard.friendly==Bodyguard.Friendly.FRIENDS_LIST)
		for (String member : PlayerFriends.friends) {
			if(a.contains(member)) a.remove(member);
		}
		Collections.sort(a);
		return a;
	}

	public static ArrayList<Entity> nearbyMobs(){
		ArrayList<Entity> a = new ArrayList<>();
		if(Minecraft.getMinecraft().player==null) return a;
		if(Minecraft.getMinecraft().world==null) return a;
		for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
            if(e == null || e.isDead) continue;     
            a.add(e);
        }
		return a;
	}


	public static EntityPlayer getEntityPlayer(String name) {
		if(name==null) return null;
		for (Entity e : Minecraft.getMinecraft().world.loadedEntityList) {
            if (e == null || e.isDead) continue;
            
            if(e instanceof EntityPlayer) {
            	if(((EntityPlayer) e).getDisplayNameString().equals(Minecraft.getMinecraft().player.getDisplayNameString())) continue;
            	if(((EntityPlayer) e).getDisplayNameString().equals(name)) return ((EntityPlayer) e);
            }
        }

		return null;
	}

}
