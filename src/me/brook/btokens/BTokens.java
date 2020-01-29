package me.brook.btokens;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.brook.assistant.BrookPlugin;
import me.brook.assistant.toolbox.ItemTools;
import me.brook.btokens.commands.TokensCommand;
import me.brook.btokens.listener.TokenListener;
import me.brook.btokens.papi.TokensExpansion;

public class BTokens extends BrookPlugin implements Listener {

	private TokenManager manager;
	private Config config;

	@Override
	public void onEnable() {
		super.onEnable();
		manager = new TokenManager(this);
		config = new Config(this);

		createSaveRunnable();

		if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new TokensExpansion(this).register();
		}

		loadCommand(new TokensCommand(this, "btokens"));
		new TokenListener(this);
	}

	private void createSaveRunnable() {
		BukkitRunnable runnable = new BukkitRunnable() {

			@Override
			public void run() {
				manager.save();
			}
		};

		runnable.runTaskTimer(this, 20 * 60 * 5, 20 * 60 * 5);
	}

	public boolean isItemAToken(ItemStack item) {

		if(item == null || item.getType() == Material.AIR) {
			return false;
		}

		if(item.getType() == config.getTokenMaterial()) {

			if(item.hasItemMeta()) {
				ItemMeta meta = item.getItemMeta();

				if(meta.hasDisplayName()) {

					if(meta.hasLore()) {
						return true;
					}

				}

			}

		}

		return false;
	}

	public long getTokensFromItem(ItemStack item) {

		if(isItemAToken(item)) {

			try {
				String line = item.getItemMeta().getLore().get(config.getLorelineWithAmount());
				// Remove text
				line = ChatColor.stripColor(line);
				line = removeText(line);

				long amount = Long.valueOf(line);
				return amount;

			}
			catch(Exception e) {
				e.printStackTrace();
			}

		}

		return 0;

	}

	private String removeText(String line) {
		return line.replaceAll("[^\\d.]", "");
	}

	@Override
	public void onDisable() {
		manager.save();
	}

	public ItemStack createToken(long amount) {
		List<String> lore = new ArrayList<>();

		for(String string : config.getTokenLore()) {
			lore.add(color(string.replace("{amount}", String.valueOf(amount))));
		}

		ItemStack token = ItemTools.createItem(config.getTokenName(), 1, config.getTokenMaterial(), (byte) 0, true,
				lore);

		return token;
	}

	public boolean doesPlayerHave(UUID uuid, long amount) {
		return manager.doesPlayerHave(uuid, amount);
	}

	public Long withdraw(UUID uuid, long toWithdraw) {
		return manager.withdraw(uuid, toWithdraw);
	}

	public Long deposit(UUID uuid, long toDeposit) {
		return manager.deposit(uuid, toDeposit);
	}

	public Long getTokensOf(UUID uuid) {
		return manager.getTokensOf(uuid);
	}

	public Long getTokensOf(OfflinePlayer plyr) {
		return getTokensOf(plyr.getUniqueId());
	}

	public Config getConfigInfo() {
		return config;
	}

}
