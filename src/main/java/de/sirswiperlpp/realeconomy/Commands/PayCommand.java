package de.sirswiperlpp.realeconomy.Commands;

import de.sirswiperlpp.realeconomy.API.EcoAPI;
import de.sirswiperlpp.realeconomy.Main.Main;
import de.sirswiperlpp.realeconomy.Utils.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class PayCommand implements CommandExecutor
{

    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));
    public static HashMap<UUID, Long> blocked = new HashMap<UUID, Long>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("eco.pay"))
        {
            p.sendMessage(language.get("prefix") + language.get("no.perm"));
            p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 1);
            return true;
        }

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

        if (blocked.containsKey(p.getUniqueId()) && System.currentTimeMillis() - blocked.get(p.getUniqueId()) < 5000) {
            p.sendMessage(language.get("prefix") + language.get("action.blocked"));
            return true;
        }

        EcoAPI.sendCoins(p, target, amount);
        blocked.put(p.getUniqueId(), System.currentTimeMillis());
        return true;
    }

}
