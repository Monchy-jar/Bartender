package com.drunkshulker.bartender.client.input;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.drunkshulker.bartender.Bartender;
import com.drunkshulker.bartender.client.gui.clickgui.ClickGuiSetting;
import com.drunkshulker.bartender.client.module.Bodyguard;
import com.drunkshulker.bartender.client.social.PlayerGroup;
import com.drunkshulker.bartender.util.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatObserver {
	
	public static boolean mooCheck = false;
	public static boolean help = false;
	public static boolean chatFix = false;
	public static boolean afkResponse = false;
	public static boolean allowLgbt = false;
	
	public static boolean partyTPA = false;
	
	public static final char commandPrefix = '@';
	private long lastLgbt = 0;
	
	final String pornUrl = "https://www.pornhub.com/random";
	private long lastPornhubSearch = 0;
	public static boolean pornhub = false;
	Timer timer;
	private String lastSafeChatMessage="";
	
	final String[] allSex = {
			"Agender",
			"Androgyne",
			"Androgynous",
			"Bigender",
			"Cis",
			"a cock sucker",
			"Cisgender",
			"a Cis Female",
			"a Cis Male",
			"a Cis Man",
			"a Cis Woman",
			"a Cisgender Female",
			"a Cisgender Male",
			"a Cisgender Man",
			"a Cisgender Woman",
			"a Female to Male",
			"FTM",
			"a Gender Fluid",
			"a Gender Nonconforming",
			"a Gender Questioning",
			"a Gender Variant",
			"Genderqueer",
			"Bait's boyfriend",
			"Intersex",
			"a Male to Female",
			"MTF",
			"anime",
			"Neither",
			"Neutrois",
			"Non-binary",
			"Other",
			"Pangender",
			"Trans",
			"a chicken",
			"Trans*",
			"a Trans Female",
			"a Trans* Female",
			"a Trans Male",
			"a Trans* Male",
			"Desert_Bunny's left testicle",
			"a Trans Man",
			"a Trans* Man",
			"a Trans Person",
			"a Trans* Person",
			"a Trans Woman",
			"a Trans* Woman",
			"Transfeminine",
			"Transgender",
			"a Transgender Female",
			"a Transgender Male",
			"a Transgender Man",
			"a Transgender Person",
			"a Transgender Woman",
			"Transmasculine",
			"Attack helicopter",
			"a Simp",
			"Gay",
			"a virgin",
			"Faggot",
			"Transsexual",
			"a Transsexual Female",
			"a Transsexual Male",
			"a Transsexual Man",
			"a Transsexual Person",
			"a Transsexual Woman",
			"Two-Spirit",
	};
	
	@SubscribeEvent
	public void onServerChatEvent(ClientChatReceivedEvent event){
		String message = event.getMessage().getUnformattedText();
		

		if(message.charAt(0) == '<') {
			String sender = getMessageSender(message);
			String text = getMessageText(sender, message);

	
			if(allowLgbt) {
				if(lgbt(message, sender, text)) return;
			}
		
			if(help) {
				if(execHelp(message, sender, text)) return;
			}
	
			if(pornhub) {
				pornSearch(message, sender);
			}
			return;
		}
		

		if(partyTPA) { 
			partyTpaccept(message);
			if(PlayerGroup.groupAcceptTpaHere) PlayerGroup.tpaccept(message);
		}
		else PlayerGroup.tpaccept(message);
		
	
		if(afkResponse) afk(message);
	}
	
	private boolean execHelp(String message, String sender, String text) {
		if(message.indexOf(commandPrefix+"help")==(sender.length()+3)&&text.length()<=5) {
			safeSendPublicChatMessage("> "+Bartender.NAME+" "+Bartender.VERSION+" by DrunkShulker");
			return true;
		}
		return false;
	}

	private String getMessageText(String sender, String message) {
		return message.substring(sender.length()+3);
	}

	private String getMessageSender(String message) {
	
		String sender = "";
		for (int i = 1; i < message.length(); i++){
		    char c = message.charAt(i);        
		    if(c=='>') break;
		    else sender+=c;
		}
		return sender;
	}
	
	public void safeSendPublicChatMessage(String messageText) {
		if(messageText.equals(lastSafeChatMessage)) {
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString("<"+Bartender.NAME+"> Someone is spamming you!"));
			return;
		}
		lastSafeChatMessage = messageText;
		Minecraft.getMinecraft().player.sendChatMessage(messageText);
	}
	
	public void pornSearch(String message, String sender) {
		if(lastPornhubSearch>0L&&System.currentTimeMillis()-lastPornhubSearch<2000) return; 
		if(message.contains("s PornHub search: ")) return; 
		
		
		if(message.indexOf(commandPrefix+"sex")==(sender.length()+3)) {

			final String senderf = sender;	
	
			if(lastPornhubSearch>0L&&System.currentTimeMillis()-lastPornhubSearch<15000) {
				String messageText = "> You have to wait 15 seconds before executing "+commandPrefix+"sex again";
				safeSendPublicChatMessage(messageText);
				return;
			}

			lastPornhubSearch = System.currentTimeMillis();
	    	timer = new Timer();
	    	timer.schedule(new TimerTask()
	    	{
	    	    public void run()
	    	    {  	 	    	  	    	
	    	    	try {
	    	    	URL obj = new URL(pornUrl);
	    			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	    			con.setRequestMethod("GET");
	    			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	    			int responseCode = con.getResponseCode();
	    			System.out.println("GET Response Code :: " + responseCode);
	    			if (responseCode == HttpURLConnection.HTTP_OK) { 
	    				BufferedReader in = new BufferedReader(new InputStreamReader(
	    						con.getInputStream()));
	    				String inputLine;
	    				StringBuffer response = new StringBuffer();
	
	    				while ((inputLine = in.readLine()) != null) {
	    					response.append(inputLine);
	    				}
	    				in.close();
	    				
	    				String r = "> "+senderf+"'s PornHub search: "+ response.substring(response.indexOf("<title>") + 7, response.indexOf("</title>"));
						r = r.replaceAll("[^A-Za-z0-9()'!?:.,_> \\[\\]]", "");
	    				safeSendPublicChatMessage(r.substring(0, r.length()-13));
	    			} else {
	    				Minecraft.getMinecraft().player.sendMessage(new TextComponentString("<"+Bartender.NAME+"> @sex failed due to pornhub api."));
	    			}}catch (Exception e) {Minecraft.getMinecraft().player.sendMessage(new TextComponentString("<"+Bartender.NAME+"> @sex failed with exception!"));}
	    	    	timer.cancel();
	    	    	timer=null;
	    	    }
	    	}, 40, 40); 
		}
	}
	
	private void partyTpaccept(String message) {
	
		String toYou = "has requested to teleport to you.";
		if(message.contains(toYou)) {
			Minecraft.getMinecraft().player.sendChatMessage("/tpaccept");		
		}
	}

	private boolean lgbt(String message, String sender, String msgOnly) {
		if(lastLgbt>0L&&System.currentTimeMillis()-lastLgbt<2000) return false; 
		if(message.length()>50) return false; 

		if(message.indexOf(commandPrefix+"gender")==(sender.length()+3)) {

			if(msgOnly.length()>8&&msgOnly.length()<=25) {
				String subs = msgOnly.substring(8);
				if(subs.length()>2) {
					if(!PlayerGroup.isPlayerOnline(subs)) {
						safeSendPublicChatMessage("> Player not found. Make sure they are online and use their /realname");
						return true;
					}else sender = subs;
				}
			}
			
			
			Random coin = new Random();
			int toss = coin.nextInt(allSex.length);
			lastLgbt = System.currentTimeMillis();
			String msg = "> "+sender+" identifies as "+allSex[toss].toLowerCase()+"!";
			msg = msg.replaceAll("[^A-Za-z0-9()'!?:.,_> \\[\\]]", "");
			safeSendPublicChatMessage(msg);
			return true;
		}
		return false;
	}

	private void afk(String message) {
		String m = "Im currently afk";
		if(!message.contains(" whispers:") ||message.contains(m)) return;
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.isGamePaused()) return;
		for (String member : PlayerGroup.members) {
			String predicate = member+" whispers:";
			if(message.length()<predicate.length()) return;
			if(message.startsWith(predicate)) return;
		}

		mc.player.sendChatMessage("/r "+m);
	}

	public boolean messageIsCommand(String msg) {
		final char[] cs = {'/', ',', '.', '-', ';', '?', '*', '^', '&', '%', '#', '$', '!', commandPrefix};
		for (char c : cs) {
			if(c==msg.charAt(0)) return true;
		}
		return false;
	}
	
	@SubscribeEvent
	public void onClientChatEvent(ClientChatEvent event){
	
		if(mooCheck) {
			if(event.getMessage().charAt(0)=='!') {
				if(!PlayerGroup.isPlayerOnline("moooomoooo")) {
					event.setCanceled(true);
					Minecraft.getMinecraft().player.sendMessage(new TextComponentString("<"+Bartender.NAME+"> moooomoooo is offline. Command canceled."));
					return;
				}
			}
		}

	
		if(chatFix) {
			if(messageIsCommand(event.getMessage())) return;
			if(Config.CHAT_POST_FIX.equals("")) return;
			
			String fix = Config.CHAT_POST_FIX;
			event.setMessage(event.getMessage()+fix);
		}
	}

	public static void applyPreferences(ClickGuiSetting[] contents) {
		for (ClickGuiSetting setting : contents) {
			if(setting.title.equals("moo check")) {
				mooCheck = setting.value == 0;
			}
			if(setting.title.equals("help")) {
				help = setting.value == 0;
			}
			else if(setting.title.equals("chatfix")) {
				chatFix = setting.value == 0;
			}
			else if(setting.title.equals("AFK reply")) {
				afkResponse = setting.value == 0;
			}
			else if(setting.title.equals("LGBT")) {
				allowLgbt = setting.value == 0;
			}
			else if(setting.title.equals("party tpa")) {
				partyTPA = setting.value == 0;
			}
			else if(setting.title.equals("pornhub")) {
				pornhub = setting.value == 0;
			}
		}
	}

	public static void clickAction(String action) {
		EntityPlayerSP p = Minecraft.getMinecraft().player;
		if(p==null) return;
		switch (action) {
		case "LGBT ad":
			advertiseLgbt();
			break;
		case "party ad":
			advertiseParty();
			break;
		case "pornhub ad":
			advertisePornhub();
			break;
		default:
			break;
		}
	}

	private static void advertisePornhub() {
		if(!pornhub) {
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString("<"+Bartender.NAME+"> Pornhub is not enabled!"));
			return;
		}
		else {
			Minecraft.getMinecraft().player.sendChatMessage("> Type "+commandPrefix+"sex to execute a random PornHub search!");
		}
	}

	private static void advertiseParty() {
		if(!partyTPA) {
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString("<"+Bartender.NAME+"> Party tpa is not enabled!"));
			return;
		}
		else {
			Minecraft.getMinecraft().player.sendChatMessage("> Party time! Tpa now and your request is automatically accepted! ");
		}
	}

	private static void advertiseLgbt() {
		if(!allowLgbt) {
			Minecraft.getMinecraft().player.sendMessage(new TextComponentString("<"+Bartender.NAME+"> Gender command is not enabled!"));
			return;
		}
		else {
			Minecraft.getMinecraft().player.sendChatMessage("> Not sure about someone's gender? Type: "+commandPrefix+"gender or "+commandPrefix+"gender (player) to find out! ");
		}
		
	}
}
