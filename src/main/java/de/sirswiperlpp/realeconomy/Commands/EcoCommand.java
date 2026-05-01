package de.sirswiperlpp.realeconomy.Commands;

import de.sirswiperlpp.realeconomy.Main.Main;
import de.sirswiperlpp.realeconomy.Provider.EcoProvider;
import de.sirswiperlpp.realeconomy.Utils.Language;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;

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
                p.sendMessage(language.get("prefix") + language.translateString("current.balance", String.valueOf(coin_balance), String.valueOf(shard_balance)));
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

}
