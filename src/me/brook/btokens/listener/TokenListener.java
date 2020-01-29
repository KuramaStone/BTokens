package me.brook.btokens.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.brook.assistant.listener.BListener;
import me.brook.btokens.BTokens;

public class TokenListener extends BListener<BTokens> {

	public TokenListener(BTokens plugin) {
		super(plugin);
	}

	@EventHandler
	public void onTokenClaim(PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		if(plugin.isItemAToken(item)) {
			long amount = plugin.getTokensFromItem(item);

			if(item.getAmount() == 1) {
				event.getPlayer().getInventory().remove(item);
			}
			else {
				item.setAmount(item.getAmount() - 1);
			}

			plugin.deposit(event.getPlayer().getUniqueId(), amount);
		}
	}

}
