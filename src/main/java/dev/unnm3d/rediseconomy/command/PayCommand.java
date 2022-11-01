package dev.unnm3d.rediseconomy.command;

import dev.unnm3d.rediseconomy.RedisEconomyPlugin;
import dev.unnm3d.rediseconomy.vaultcurrency.EconomyExchange;
import dev.unnm3d.rediseconomy.vaultcurrency.RedisEconomy;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public class PayCommand implements CommandExecutor, TabCompleter {
    private final RedisEconomy economy;
    private final EconomyExchange exchange;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 2) return true;
        if (!(sender instanceof Player p)) {
            RedisEconomyPlugin.settings().send(sender, RedisEconomyPlugin.settings().NO_CONSOLE);
            return true;
        }
        String target = args[0];
        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            RedisEconomyPlugin.settings().send(sender, RedisEconomyPlugin.settings().INVALID_AMOUNT);

            return true;
        }
        if (target.equalsIgnoreCase(sender.getName())) {
            RedisEconomyPlugin.settings().send(sender, RedisEconomyPlugin.settings().PAY_SELF);
            return true;
        }
        if (!economy.hasAccount(target)) {
            RedisEconomyPlugin.settings().send(sender, RedisEconomyPlugin.settings().PLAYER_NOT_FOUND);
            return true;
        }

        if (!economy.withdrawPlayer(p, amount).transactionSuccess()) {
            RedisEconomyPlugin.settings().send(sender, RedisEconomyPlugin.settings().INSUFFICIENT_FUNDS);
            return true;
        }

        if (!economy.depositPlayer(target, amount).transactionSuccess()) {
            RedisEconomyPlugin.settings().send(sender, RedisEconomyPlugin.settings().PAY_FAIL);
            return true;
        }

        //Send msg to sender
        RedisEconomyPlugin.settings().send(sender, RedisEconomyPlugin.settings().PAY_SUCCESS.replace("%amount%", economy.format(amount)).replace("%player%", target));
        //Send msg to target
        economy.getEzRedisMessenger().sendObjectPacketAsync("rediseco:paymsg", new PayMsg(sender.getName(), target, economy.format(amount)));
        //Register transaction
        exchange.saveTransaction(p.getName(), target, economy.format(amount));


        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return economy.getNameUniqueIds().keySet().stream().filter(name -> name.startsWith(args[0])).toList();
        } else if (args.length == 2)
            return List.of("69");

        return List.of();
    }


    public record PayMsg(String sender, String receiverName, String amount) implements Serializable {
    }
}
