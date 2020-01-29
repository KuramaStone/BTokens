package me.brook.btokens;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import me.brook.assistant.BrookPlugin;
import me.brook.assistant.toolbox.Configuration;

public class Config {

	private BTokens plugin;

	private String withdrawSuccess, withdrawInsufficient;
	private String depositSuccess;
	private String balanceViewSelf, balanceViewOther;
	private String playerNotFound;
	private String removeSuccess;
	private String paySuccessReceiver, paySuccessSender;

	private Material tokenMaterial;
	private List<String> tokenLore;
	private int lorelineWithAmount;
	private String tokenName;

	public Config(BTokens plugin) {
		this.plugin = plugin;

		loadConfig();
	}

	private void loadConfig() {
		Configuration config = new Configuration(plugin, "config.yml");
		
		ConfigurationSection cs = config.getConfigurationSection("WithdrawToken.item");
		
		tokenMaterial = getMaterial(cs.getString("material"));
		tokenName = BrookPlugin.color(cs.getString("name"));
		tokenLore = new ArrayList<>();
		
		List<String> list = cs.getStringList("lore");
		for(int i = 0; i < list.size(); i++) {
			String line = list.get(i);
			
			tokenLore.add(BrookPlugin.color(line));
			if(line.contains("{amount}")) {
				lorelineWithAmount = i;
			}
		}

		cs = config.getConfigurationSection("Messages");

		withdrawSuccess = cs.getString("withdraw.success");
		withdrawInsufficient = cs.getString("withdraw.insufficientFunds");
		
		depositSuccess = cs.getString("deposit.success");
		
		removeSuccess = cs.getString("remove.success");
		
		balanceViewSelf = cs.getString("balance.self");
		balanceViewOther = cs.getString("balance.other");

		paySuccessReceiver = cs.getString("pay.receiver");
		paySuccessSender = cs.getString("pay.sender");
		
		playerNotFound = cs.getString("misc.notFound");
	}

	private Material getMaterial(String string) {

		try {
			return Material.valueOf(string);
		}
		catch(Exception e) {
			return Material.DOUBLE_PLANT;
		}

	}

	public String getWithdrawSuccess(long amount) {
		return withdrawSuccess.replace("{amount}", String.valueOf(amount));
	}

	public String getDepositSuccess(OfflinePlayer plyr, long amount) {
		return depositSuccess.replace("{name}", plyr.getName()).replace("{amount}", String.valueOf(amount));
	}

	public String getBalanceViewSelf(OfflinePlayer plyr, long amount) {
		return balanceViewSelf.replace("{name}", plyr.getName()).replace("{amount}", String.valueOf(amount));
	}

	public String getBalanceViewOther(OfflinePlayer plyr, long amount) {
		return balanceViewOther.replace("{name}", plyr.getName()).replace("{amount}", String.valueOf(amount));
	}

	public String getPlayerNotFound(String name) {
		return playerNotFound.replace("{name}", name);
	}

	public String getRemoveSuccess(String name, long amount) {
		return removeSuccess.replace("{amount}", String.valueOf(amount)).replace("{name}", name);
	}

	public String getWithdrawInsufficient(long amount, Long balance) {
		return withdrawInsufficient.replace("{balance}", String.valueOf(balance)).replace("{amount}",
				String.valueOf(amount));
	}

	public String getPaySuccessReceiver(String sender, long amount) {
		return paySuccessReceiver.replace("{sender}", sender).replace("{amount}", String.valueOf(amount));
	}

	public String getPaySuccessSender(String receiver, long amount) {
		return paySuccessSender.replace("{receiver}", receiver).replace("{amount}", String.valueOf(amount));
	}

	public Material getTokenMaterial() {
		return tokenMaterial;
	}

	public String getTokenName() {
		return tokenName;
	}

	public List<String> getTokenLore() {
		return tokenLore;
	}
	
	public int getLorelineWithAmount() {
		return lorelineWithAmount;
	}

}
