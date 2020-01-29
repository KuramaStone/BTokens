package me.brook.btokens.events;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TokenGiveEvent extends Event implements Cancellable {

	private UUID receiever;
	private long tokens;

	private boolean cancelled;

	public TokenGiveEvent(UUID receiever, long tokens) {
		this.receiever = receiever;
		this.tokens = tokens;
	}

	public void setTokens(long tokens) {
		this.tokens = tokens;
	}

	public UUID getReceiever() {
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
