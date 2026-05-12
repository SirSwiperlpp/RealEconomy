package de.sirswiperlpp.realeconomy.Listener;

import de.sirswiperlpp.realeconomy.Commands.PayCommand;
import de.sirswiperlpp.realeconomy.Provider.EcoProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerListener implements Listener
{

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();

        try {
            EcoProvider.registerPlayer(player.getUniqueId());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();

        PayCommand.blocked.remove(player.getUniqueId());
    }

}
