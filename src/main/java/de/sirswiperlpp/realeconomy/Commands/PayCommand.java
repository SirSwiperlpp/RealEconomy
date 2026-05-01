package de.sirswiperlpp.realeconomy.Commands;

import de.sirswiperlpp.realeconomy.API.EcoAPI;
import de.sirswiperlpp.realeconomy.Main.Main;
import de.sirswiperlpp.realeconomy.Utils.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class PayCommand implements CommandExecutor
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

        if (args.length < 2)
        {
            p.sendMessage(ChatColor.RED + "Usage: /pay <player> <amount>");
            return true;
        }


        String raw_target = args[0].toLowerCase();
        Player target = Bukkit.getPlayer(raw_target);
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            amount = 0;
        }

        if (target == null)
        {
            p.sendMessage(language.get("prefix") + language.translateString("player.offline"));
            return true;
        }

        EcoAPI.sendCoins(p, target, amount);
        return true;
    }

}
