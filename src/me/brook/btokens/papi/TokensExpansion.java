package me.brook.btokens.papi;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.brook.btokens.BTokens;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class TokensExpansion extends PlaceholderExpansion {

	private final BTokens plugin;

	public TokensExpansion(BTokens main) {
		this.plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String onRequest(OfflinePlayer p, String identifier) {

		OfflinePlayer plyr;
		if(p != null && (identifier.equalsIgnoreCase("reflect"))) {
			plyr = p;
		}
		else if(identifier.contains("-")) {
			plyr = Bukkit.getOfflinePlayer(UUID.fromString(identifier));
		}
		else {
			plyr = Bukkit.getOfflinePlayer(identifier);
		}

		if(plyr == null) {
			return null;
		}

		Long tokens = plugin.getTokensOf(plyr);

		return tokens.toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		// %example_placeholder1%

		OfflinePlayer plyr;
		if(p != null && (identifier.equalsIgnoreCase("reflect"))) {
			plyr = p;
		}
		else if(identifier.contains("-")) {
			plyr = Bukkit.getOfflinePlayer(UUID.fromString(identifier));
		}
		else {
			plyr = Bukkit.getOfflinePlayer(identifier);
		}

		if(plyr == null) {
			return null;
		}

		Long tokens = plugin.getTokensOf(plyr);

		return tokens.toString();
	}

	@Override
	public boolean register() {
		return PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
	}

	@Override
	public String getAuthor() {
		return "Brook Stone";
	}

	@Override
	public String getIdentifier() {
		return "btokens";
	}

	@Override
	public String getVersion() {
		return "1.0.0";
	}

}
