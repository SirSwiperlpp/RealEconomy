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

public class PayTab implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>();

            for (Player p : Bukkit.getOnlinePlayers())
            {

                if (p == sender) continue;
                subCommands.add(p.getName());
            }

            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        }

        if (args.length == 2)
        {
            List<String> subCommands = Arrays.asList("5");
            StringUtil.copyPartialMatches(args[1], subCommands, completions);
        }

        Collections.sort(completions);
        return completions;
    }

}
