package me.brook.btokens.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.brook.assistant.command.BComplexCMD;
import me.brook.assistant.command.HelpCommand;
import me.brook.btokens.BTokens;

public class TokensCommand extends BComplexCMD<BTokens> {

	public TokensCommand(BTokens brookplugin, String baseCommand) {
		super(brookplugin, baseCommand);

		addSubCommand(new HelpCommand<BTokens>(plugin, this));
		addSubCommand(new GiveTokensCommand(brookplugin, "give", "BTokens.commands.give", "Gives the amount to the player."));
		addSubCommand(new RemoveTokensCommand(brookplugin, "remove", "BTokens.commands.remove", "Takes the amount from the player."));
		addSubCommand(new WithdrawCommand(brookplugin, "withdraw", "BTokens.commands.withdraw", "Creates a token item!"));
		addSubCommand(new PayTokensCommand(brookplugin, "pay", "BTokens.commands.pay", "Pays the amount to the player."));
	}

	@Override
	public void subCommandNotFound(CommandSender sender, String[] args) {

		@SuppressWarnings("deprecation")
		OfflinePlayer plyr = Bukkit.getOfflinePlayer(args[0]);

		if(!plyr.hasPlayedBefore()) {
			sender.sendMessage(color(plugin.getConfigInfo().getPlayerNotFound(args[0])));
			return;
		}

		sender.sendMessage(color(plugin.getConfigInfo().getBalanceViewOther(plyr, plugin.getTokensOf(plyr))));
	}

	@Override
	public void onNoArgsCommand(CommandSender sender) {

		if(sender instanceof Player) {
			Player player = (Player) sender;

			player.sendMessage(color(plugin.getConfigInfo().getBalanceViewSelf(player, plugin.getTokensOf(player))));
		}
		else {
			sender.sendMessage(color("&cOnly players may use this!"));
		}

	}

	@Override
	public String getUsage() {
		return "/mobrelics help";
	}

}
