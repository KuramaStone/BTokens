package me.brook.btokens.events;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TokenWithdrawEvent extends Event implements Cancellable {

	private UUID user;
	private long tokens;
	
	private boolean cancelled;

	public TokenWithdrawEvent(UUID user, long tokens) {
		this.user = user;
		this.tokens = tokens;
	}
	
	
	public UUID getWithdrawnFrom() {
		return user;
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
