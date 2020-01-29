package me.brook.btokens.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import me.brook.assistant.command.BSimpleCMD;
import me.brook.btokens.BTokens;

public class RemoveTokensCommand extends BSimpleCMD<BTokens> {

	public RemoveTokensCommand(BTokens brookPlugin, String command, String permission, String description) {
		super(brookPlugin, command, permission, description);
		addAlias("take");
	}

	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		
		if(args.length == 2) {
			@SuppressWarnings("deprecation")
			OfflinePlayer plyr = Bukkit.getOfflinePlayer(args[0]);
			
			try {
				long amount = Long.parseLong(args[1]);

				plugin.withdraw(plyr.getUniqueId(), amount);
				
				sender.sendMessage(color(plugin.getConfigInfo().getRemoveSuccess(plyr.getName(), amount)));
			}
			catch (Exception e) {
				sender.sendMessage(color("&cUsage: &e/mobrelics remove [player] [amount]"));
			}
			
			
		}
		else {
			sender.sendMessage(color("&cUsage: &e/mobrelics remove [player] [amount]"));
		}
		
		return false;
	}

}
