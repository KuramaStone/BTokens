package me.brook.btokens;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;

import me.brook.assistant.toolbox.Configuration;
import me.brook.btokens.events.TokenGiveEvent;
import me.brook.btokens.events.TokenTakeEvent;

class TokenManager {

	private BTokens plugin;

	private Map<UUID, Long> tokens;

	public TokenManager(BTokens plugin) {
		this.plugin = plugin;

		loadTokens();
	}

	public Long getTokensOf(UUID uuid) {
		return tokens.containsKey(uuid) ? tokens.get(uuid) : 0;
	}
	
	public boolean doesPlayerHave(UUID uuid, long amount) {
		
		Long bal = getTokensOf(uuid);
		if(bal == null) {
			return false;
		}
		
		return bal >= amount;
	}

	public Long withdraw(UUID uuid, long toWithdraw) {

		Long balance = 0l;
		if(tokens.containsKey(uuid)) {
			balance = tokens.get(uuid);
		}
		
		TokenTakeEvent event = new TokenTakeEvent(uuid, toWithdraw);
		plugin.getServer().getPluginManager().callEvent(event);
		
		if(event.isCancelled()) {
			return balance;
		}
		toWithdraw = event.getTokens();

		balance = Long.valueOf(balance - toWithdraw);
		balance = Math.max(0, balance);
		tokens.put(uuid, balance);

		return balance;
	}

	public Long deposit(UUID uuid, long toDeposit) {

		Long balance = 0l;
		if(tokens.containsKey(uuid)) {
			balance = tokens.get(uuid);
		}

		
		TokenGiveEvent event = new TokenGiveEvent(uuid, toDeposit);
		plugin.getServer().getPluginManager().callEvent(event);
		
		if(event.isCancelled()) {
			return balance;
		}
		toDeposit = event.getTokens();

		balance = Long.valueOf(balance + toDeposit);
		balance = Math.max(0, balance);
		tokens.put(uuid, balance);

		return balance;
	}

	private void loadTokens() {
		tokens = new HashMap<>();

		Configuration config = new Configuration(plugin, "players.yml");

		ConfigurationSection data = config.getConfigurationSection("Players");
		if(data != null) {
			for(String id : data.getKeys(false)) {
				ConfigurationSection cs = data.getConfigurationSection(id);

				UUID uuid = UUID.fromString(id);
				long amount = cs.getLong("tokens");

				tokens.put(uuid, amount);
			}
		}

	}

	public void save() {
		Configuration config = new Configuration(plugin, "players.yml");
		config.delete();

		ConfigurationSection data = config.createSection("Players");
		for(Entry<UUID, Long> set : tokens.entrySet()) {
			ConfigurationSection cs = data.createSection(set.getKey().toString());

			cs.set("tokens", set.getValue());
		}

		config.saveData();
	}

}
