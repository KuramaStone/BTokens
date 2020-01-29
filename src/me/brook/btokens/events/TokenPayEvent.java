package me.brook.btokens.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TokenPayEvent extends Event implements Cancellable {

	private OfflinePlayer sender, receiever;
	private long tokens;
	
	private boolean cancelled;

	public TokenPayEvent(OfflinePlayer sender, OfflinePlayer receiever, long tokens) {
		this.sender = sender;
		this.receiever = receiever;
		this.tokens = tokens;
	}
	
	public OfflinePlayer getSender() {
		return sender;
	}
	
	public OfflinePlayer getReceiever() {
		return receiever;
	}
	
	public long getTokens() {
		return tokens;
	}

	@Override
	public HandlerList getHandlers() {
		return new HandlerList();
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		cancelled = arg0;
	}

}
