package me.brook.btokens.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.brook.assistant.command.BSimpleCMD;
import me.brook.assistant.command.CommandType;
import me.brook.assistant.toolbox.ItemTools;
import me.brook.assistant.toolbox.ItemTools.GivePolicy;
import me.brook.btokens.BTokens;
import me.brook.btokens.events.TokenWithdrawEvent;

public class WithdrawCommand extends BSimpleCMD<BTokens> {

	public WithdrawCommand(BTokens brookPlugin, String command, String permission, String description) {
		super(brookPlugin, command, permission, description);
		setCommandType(CommandType.PLAYER_ONLY);
	}

	@Override
	public boolean runCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		
		if(args.length == 1) {
			
			try {
				long amount = Long.parseLong(args[0]);
				amount = Math.min(amount, plugin.getTokensOf(player));
				
				if(amount <= 0) {
					sender.sendMessage(color(plugin.getConfigInfo().getWithdrawInsufficient(amount, plugin.getTokensOf(player))));
					return true;
				}
				
				TokenWithdrawEvent event = new TokenWithdrawEvent(player.getUniqueId(), amount);
				plugin.getServer().getPluginManager().callEvent(event);
				
				if(event.isCancelled()) {
					return true;
				}
				
				plugin.withdraw(player.getUniqueId(), amount);
				
				// Give the token
				ItemStack token = plugin.createToken(amount);
				
				ItemTools.addItemToPlayer(player, GivePolicy.DROP, token);
				
				sender.sendMessage(color(plugin.getConfigInfo().getWithdrawSuccess(amount)));
			}
			catch (Exception e) {
				sender.sendMessage(color("&cUsage: &e/mobrelics withdraw [amount]"));
			}
			
			
		}
		else {
			sender.sendMessage(color("&cUsage: &e/mobrelics withdraw [amount]"));
		}
		
		return false;
	}

}
