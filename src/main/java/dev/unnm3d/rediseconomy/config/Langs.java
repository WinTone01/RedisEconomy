package dev.unnm3d.rediseconomy.config;

import de.exlll.configlib.Configuration;
import dev.unnm3d.rediseconomy.RedisEconomyPlugin;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

@Configuration
public final class Langs {
    private final BukkitAudiences audiences = BukkitAudiences.create(RedisEconomyPlugin.getInstance());
    public String noConsole = "<red>You must be in-game to use this command!</red>";
    public String noPermission = "<red>You do not have permission to use this command!</red>";
    public String playerNotFound = "<red>Player not found!</red>";
    public String invalidAmount = "<red>Invalid amount!</red>";
    public String invalidCurrency = "<red>Invalid currency!</red>";
    public String insufficientFunds = "<red>You do not have enough money!</red>";
    public String balance = "<green>You have %balance%!</green>";
    public String balanceSet = "<green>You set %player% account to %balance% !</green>";
    public String balanceOther = "<green>%player% has %balance% !</green>";
    public String balanceTop = "<green>Top richest players:</green><br>%prevpage%      %page%      %nextpage%";
    public String balanceTopFormat = "<aqua>%pos% - %player% %balance%</aqua>";
    public String paySelf = "<red>You cannot pay yourself!</red>";
    public String paySuccess = "<green>You paid %player% %amount% with %tax_percentage% %tax_applied% of transaction fee!</green>";
    public String payFail = "<red>Payment failed!</red>";
    public String payReceived = "<green>You received %amount% from %player%!</green>";
    public String purgeUserSuccess = "<green>Users matching %player% have been purged!</green>";
    public String switchCurrencySuccess = "<green>Switched %currency% to %switch-currency%.<br>Please restart immediately every instance<br> with RedisEconomy installed to avoid any overwrite!</green>";
    public TransactionItem transactionItem = new TransactionItem(
            "<aqua>%timestamp%</aqua> <click:run_command:/transaction %account-owner% %afterbefore%><dark_green>%account-owner%</dark_green></click> -> <click:run_command:/transaction %other-account% %afterbefore%><green>%other-account%</green></click> Change: %amount%%symbol%<br><yellow>Reason: </yellow>%reason%",
            "<aqua>%timestamp%</aqua> <click:run_command:/transaction %other-account% %afterbefore%><green>%other-account%</green></click> -> <click:run_command:/transaction %account-owner% %afterbefore%><dark_green>%account-owner%</dark_green></click> Change: %amount%%symbol%<br><yellow>Reason: </yellow>%reason%");
    public UnitSymbols unitSymbols = new UnitSymbols("k", "m", "b", "t", "q");

    public record TransactionItem(
            String outgoingFunds,
            String incomingFunds
    ) {
    }

    public record UnitSymbols(
            String thousand,
            String million,
            String billion,
            String trillion,
            String quadrillion
    ) {
    }

    public void send(CommandSender sender, String text) {
        audiences.sender(sender).sendMessage(MiniMessage.miniMessage().deserialize(text));
    }
}