package me.brook.btokens.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import me.brook.assistant.command.BSimpleCMD;
import me.brook.btokens.BTokens;

public class GiveTokensCommand extends BSimpleCMD<BTokens> {

	public GiveTokensCommand(BTokens brookPlugin, String command, String permission, String description) {
		super(brookPlugin, command, permission, description);
		addAlias("deposit");
	}

	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		
		if(args.length == 2) {
			@SuppressWarnings("deprecation")
			OfflinePlayer plyr = Bukkit.getOfflinePlayer(args[0]);
			
			try {
				long amount = Long.parseLong(args[1]);
				
				plugin.deposit(plyr.getUniqueId(), amount);
				
				sender.sendMessage(color(plugin.getConfigInfo().getDepositSuccess(plyr, amount)));
			}
			catch (Exception e) {
				sender.sendMessage(color("&cUsage: &e/mobrelics deposit [player] [amount]"));
			}
			
			
		}
		else {
			sender.sendMessage(color("&cUsage: &e/mobrelics deposit [player] [amount]"));
		}
		
		return false;
	}

}
