package de.sirswiperlpp.realeconomy.API;

import de.sirswiperlpp.realeconomy.Main.Main;
import de.sirswiperlpp.realeconomy.Provider.EcoProvider;
import de.sirswiperlpp.realeconomy.Utils.Language;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;

public class EcoAPI
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    public static void sendCoins(Player sender, Player target, int amount)
    {
        try {

            if (sender == target)
            {
                sender.sendMessage(language.get("prefix") + language.translateString("player.offline", sender.getName()));
                return;
            }

            int sender_balance = EcoProvider.readCoinsFromSQL(sender.getUniqueId());
            int target_balance = EcoProvider.readCoinsFromSQL(target.getUniqueId());

            if (amount < 5)
            {
                sender.sendMessage(language.get("prefix") + language.get("input.negative.pay"));
                return;
            }

            if (sender_balance < amount)
            {
                sender.sendMessage(language.get("prefix") + language.get("balance.negative"));
                return;
            }

            int new_sender_balance = sender_balance - amount;
            int new_target_balance = target_balance + amount;

            EcoProvider.updateCoins(sender.getUniqueId(), new_sender_balance);
            EcoProvider.updateCoins(target.getUniqueId(), new_target_balance);

            sender.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(new_sender_balance)));
            target.sendMessage(language.get("prefix") + language.translateString("coins.updated.p", sender.getName(), String.valueOf(amount)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addCoins(Player player, int coins)
    {
        try {
            int current_balance = EcoProvider.readCoinsFromSQL(player.getUniqueId());

            if (coins <= 0)
            {
                player.sendMessage(language.get("prefix") + language.get("input.negative"));
                return;
            }

            int new_balance = current_balance + coins;

            EcoProvider.updateCoins(player.getUniqueId(), new_balance);
            player.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(new_balance)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean removeCoins(Player player, int coins)
    {
        try {
            int current_balance = EcoProvider.readCoinsFromSQL(player.getUniqueId());

            if (coins <= 0)
            {
                player.sendMessage(language.get("prefix") + language.get("input.negative"));
                return false;
            }

            int new_balance = current_balance - coins;

            if (new_balance < 0)
            {
                player.sendMessage(language.get("prefix") + language.get("balance.negative"));
                return false;
            }

            EcoProvider.updateCoins(player.getUniqueId(), new_balance);
            player.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(new_balance)));
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addShards(Player player, int shards)
    {
        try {
            int current_balance = EcoProvider.readShardsFromSQL(player.getUniqueId());

            if (shards <= 0)
            {
                player.sendMessage(language.get("prefix") + language.get("input.negative"));
                return;
            }

            int new_balance = current_balance + shards;

            EcoProvider.updateCoins(player.getUniqueId(), new_balance);
            player.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(new_balance)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean removeShards(Player player, int shards)
    {
        try {
            int current_balance = EcoProvider.readCoinsFromSQL(player.getUniqueId());

            if (shards <= 0)
            {
                player.sendMessage(language.get("prefix") + language.get("input.negative"));
                return false;
            }

            int new_balance = current_balance - shards;

            if (new_balance < 0)
            {
                player.sendMessage(language.get("prefix") + language.get("balance.negative"));
                return false;
            }

            EcoProvider.updateCoins(player.getUniqueId(), new_balance);
            player.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(new_balance)));
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
