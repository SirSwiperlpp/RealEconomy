package de.sirswiperlpp.realeconomy.API;

import de.sirswiperlpp.realeconomy.Main.Main;
import de.sirswiperlpp.realeconomy.Provider.EcoProvider;
import de.sirswiperlpp.realeconomy.Utils.Language;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class EcoAPI
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    public static int getCoinBalance(Player player)
    {
        try {
            return EcoProvider.readCoinsFromSQL(player.getUniqueId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getShardBalance(Player player)
    {
        try {
            return EcoProvider.readShardsFromSQL(player.getUniqueId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
                sender.playSound(sender, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return;
            }

            if (sender_balance < amount)
            {
                sender.sendMessage(language.get("prefix") + language.get("balance.negative"));
                sender.playSound(sender, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return;
            }

            int new_sender_balance = sender_balance - amount;
            int new_target_balance = target_balance + amount;

            EcoProvider.updateCoins(sender.getUniqueId(), new_sender_balance);
            EcoProvider.updateCoins(target.getUniqueId(), new_target_balance);

            DecimalFormat formatter = new DecimalFormat("#,###");
            formatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
            String formatted_sender = formatter.format(new_sender_balance);
            String formatted_target = formatter.format(amount);

            sender.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(formatted_sender)));
            target.sendMessage(language.get("prefix") + language.translateString("coins.updated.p", sender.getName(), String.valueOf(formatted_target)));
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
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return;
            }

            int new_balance = current_balance + coins;

            EcoProvider.updateCoins(player.getUniqueId(), new_balance);

            DecimalFormat formatter = new DecimalFormat("#,###");
            formatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
            String formatted = formatter.format(new_balance);

            player.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(formatted)));
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
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return false;
            }

            int new_balance = current_balance - coins;

            if (new_balance < 0)
            {
                player.sendMessage(language.get("prefix") + language.get("balance.negative"));
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return false;
            }

            EcoProvider.updateCoins(player.getUniqueId(), new_balance);

            DecimalFormat formatter = new DecimalFormat("#,###");
            formatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
            String formatted = formatter.format(new_balance);

            player.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(formatted)));
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
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return;
            }

            int new_balance = current_balance + shards;

            EcoProvider.updateShards(player.getUniqueId(), new_balance);

            DecimalFormat formatter = new DecimalFormat("#,###");
            formatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
            String formatted = formatter.format(new_balance);

            player.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(formatted)));
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
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return false;
            }

            int new_balance = current_balance - shards;

            if (new_balance < 0)
            {
                player.sendMessage(language.get("prefix") + language.get("balance.negative"));
                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                return false;
            }

            EcoProvider.updateShards(player.getUniqueId(), new_balance);

            DecimalFormat formatter = new DecimalFormat("#,###");
            formatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
            String formatted = formatter.format(new_balance);

            player.sendMessage(language.get("prefix") + language.translateString("coins.updated", String.valueOf(formatted)));
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
