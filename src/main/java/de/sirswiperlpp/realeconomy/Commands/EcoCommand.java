package de.sirswiperlpp.realeconomy.Commands;

import de.sirswiperlpp.realeconomy.API.EcoAPI;
import de.sirswiperlpp.realeconomy.Main.Main;
import de.sirswiperlpp.realeconomy.Provider.EcoProvider;
import de.sirswiperlpp.realeconomy.Utils.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class EcoCommand implements CommandExecutor
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player p = (Player) sender;

        if (args.length == 0)
        {
            try {

                int coin_balance = EcoProvider.readCoinsFromSQL(p.getUniqueId());
                int shard_balance = EcoProvider.readShardsFromSQL(p.getUniqueId());

                DecimalFormat formatter = new DecimalFormat("#,###");
                formatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.GERMANY));
                String formatted = formatter.format(coin_balance);

                p.sendMessage(language.get("prefix") + language.translateString("current.balance", String.valueOf(formatted), String.valueOf(shard_balance)));
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (args[0].equalsIgnoreCase("set")) {

            if (!p.hasPermission("eco.admin.set"))
            {
                p.sendMessage(language.get("prefix") + language.get("no.perm"));
                p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 1);
                return true;
            }

            if (args.length == 4)
            {

                Player target = Bukkit.getPlayer(args[1]);
                int amount;

                if (target == null)
                {
                    p.sendMessage(language.get("prefix") + language.translateString("player.offline", args[1].toLowerCase()));
                    return true;
                }

                switch (args[2].toLowerCase())
                {
                    case "coins":
                        try {
                            amount = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            amount = EcoAPI.getCoinBalance(target);
                        }

                        try {
                            EcoProvider.updateCoins(target.getUniqueId(), amount);
                            p.sendMessage(language.get("prefix") + language.translateString("coins.set", target.getName(), String.valueOf(amount)));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;

                    case "shard":
                        try {
                            amount = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            amount = EcoAPI.getShardBalance(target);
                        }

                        try {
                            EcoProvider.updateShards(target.getUniqueId(), amount);
                            p.sendMessage(language.get("prefix") + language.translateString("shards.set", target.getName(), String.valueOf(amount)));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                }

            }

        }
        return true;
    }

}
