package com.drunkshulker.bartender.client;

import com.drunkshulker.bartender.client.commands.BasefinderCommand;
import com.drunkshulker.bartender.client.commands.BindHotkeyCommand;
import com.drunkshulker.bartender.client.commands.ChatPostFixCommand;
import com.drunkshulker.bartender.client.commands.DateCommand;
import com.drunkshulker.bartender.client.commands.FriendsCommand;
import com.drunkshulker.bartender.client.commands.GroupCommand;

import net.minecraftforge.client.ClientCommandHandler;

public class CommandsRegistry {
	
	public static void registerAll() {
		ClientCommandHandler.instance.registerCommand(new DateCommand());
        ClientCommandHandler.instance.registerCommand(new BindHotkeyCommand());
        ClientCommandHandler.instance.registerCommand(new GroupCommand());
        ClientCommandHandler.instance.registerCommand(new FriendsCommand());
        ClientCommandHandler.instance.registerCommand(new ChatPostFixCommand());
        ClientCommandHandler.instance.registerCommand(new BasefinderCommand());
	}
}
