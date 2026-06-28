package de.sirswiperlpp.realeconomy.TabComplition;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EcoTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();

        if (!sender.hasPermission("eco.admin"))
        {
            return completions;
        }

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], Collections.singletonList("set"), completions);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            List<String> players = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                players.add(p.getName());
            }
            StringUtil.copyPartialMatches(args[1], players, completions);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            StringUtil.copyPartialMatches(args[2], Arrays.asList("coins", "shards"), completions);
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("set")) {
            StringUtil.copyPartialMatches(args[3], Arrays.asList("100", "500", "1000"), completions);
        }

        Collections.sort(completions);
        return completions;
    }
}