package com.drunkshulker.bartender.client;

import com.drunkshulker.bartender.client.input.ClickOnEntity;
import com.drunkshulker.bartender.client.ipc.IPCHandler;
import com.drunkshulker.bartender.client.module.Aura;
import com.drunkshulker.bartender.client.module.AutoEat;
import com.drunkshulker.bartender.client.module.BaseFinder;
import com.drunkshulker.bartender.client.module.Bodyguard;
import com.drunkshulker.bartender.client.module.Flight;
import com.drunkshulker.bartender.client.module.PlayerParticles;
import com.drunkshulker.bartender.client.module.SafeTotemSwap;
import com.drunkshulker.bartender.client.module.Search;
import com.drunkshulker.bartender.util.Update;

import net.minecraftforge.common.MinecraftForge;

public class ModulesRegistry {
	
	public static void registerAll(){	
		MinecraftForge.EVENT_BUS.register(new Update());
		MinecraftForge.EVENT_BUS.register(new SafeTotemSwap());
        MinecraftForge.EVENT_BUS.register(new Search());
        MinecraftForge.EVENT_BUS.register(new Aura());
        MinecraftForge.EVENT_BUS.register(new PlayerParticles());
        MinecraftForge.EVENT_BUS.register(new BaseFinder());
        MinecraftForge.EVENT_BUS.register(new Bodyguard());
        MinecraftForge.EVENT_BUS.register(new Flight());
        MinecraftForge.EVENT_BUS.register(new AutoEat());
        MinecraftForge.EVENT_BUS.register(new ClickOnEntity());
        MinecraftForge.EVENT_BUS.register(new IPCHandler());
	}
}
