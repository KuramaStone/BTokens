package me.brook.btokens.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.brook.assistant.command.BSimpleCMD;
import me.brook.assistant.command.CommandType;
import me.brook.btokens.BTokens;
import me.brook.btokens.events.TokenPayEvent;

public class PayTokensCommand extends BSimpleCMD<BTokens> {

	public PayTokensCommand(BTokens brookPlugin, String command, String permission, String description) {
		super(brookPlugin, command, permission, description);
		setCommandType(CommandType.PLAYER_ONLY);
		addAlias("deposit");
	}

	@Override
	public boolean runCommand(CommandSender send, String[] args) {
		Player sender = (Player) send;
		
		if(args.length == 2) {
			@SuppressWarnings("deprecation")
			OfflinePlayer receiver = Bukkit.getOfflinePlayer(args[0]);
			
			try {
				long amount = Long.parseLong(args[1]);
				amount = Math.min(amount, plugin.getTokensOf(sender));
				
				if(!receiver.hasPlayedBefore() || receiver.isBanned()) {
					sender.sendMessage(color(plugin.getConfigInfo().getPlayerNotFound(receiver.getName())));
					return true;
				}
				
				TokenPayEvent event = new TokenPayEvent(sender, receiver, amount);
				plugin.getServer().getPluginManager().callEvent(event);
				
				if(event.isCancelled()) {
					return true;
				}
				
				plugin.withdraw(sender.getUniqueId(), amount);
				plugin.deposit(receiver.getUniqueId(), amount);
				
				sender.sendMessage(color(plugin.getConfigInfo().getPaySuccessSender(receiver.getName(), amount)));
				if(receiver.isOnline()) {
					receiver.getPlayer().sendMessage(color(plugin.getConfigInfo().getPaySuccessReceiver(sender.getName(), amount)));
				}
			}
			catch (Exception e) {
				sender.sendMessage(color("&cUsage: &e/mobrelics pay [player] [amount]"));
			}
			
			
		}
		else {
			sender.sendMessage(color("&cUsage: &e/mobrelics pay [player] [amount]"));
		}
		
		return false;
	}

}
