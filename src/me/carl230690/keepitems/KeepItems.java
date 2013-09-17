package me.carl230690.keepitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class KeepItems extends JavaPlugin
  implements Listener
{
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
  }

  public void onDisable()
  {
  }

  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerDropItem(PlayerDropItemEvent ev)
  {
    Player p = ev.getPlayer();
    if ((p != null) && (p.hasPermission("KeepItems.invdrop")))
    {
      p.sendMessage(ChatColor.DARK_RED + "You are prevented from dropping items.");

      ev.setCancelled(true);
    }
  }

  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerDeath(PlayerDeathEvent ev)
  {
    if ((ev.getEntity() instanceof Player))
    {
      Player p = ev.getEntity();

      if ((p != null) && (p.hasPermission("KeepItems.ondeath")))
      {
        p.sendMessage(ChatColor.DARK_RED + "You are prevented from dropping items and experience on death.");

        ev.setKeepLevel(true);
        ev.setDroppedExp(0);

        ItemStack[] inv = p.getInventory().getContents();
        if (inv != null)
        {
          getServer().getScheduler().scheduleSyncDelayedTask(this, new SaveInventoryThread(p, inv));
          for (ItemStack item : inv) {
            ev.getDrops().remove(item);
          }
        }

        ItemStack[] armour = p.getInventory().getArmorContents();
        if (armour != null)
        {
          getServer().getScheduler().scheduleSyncDelayedTask(this, new SaveArmourThread(p, armour));
          for (ItemStack item : armour)
            ev.getDrops().remove(item);
        }
      }
    }
  }

  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerInteract(PlayerInteractEvent ev)
  {
    Player p = ev.getPlayer();
    Block block = ev.getClickedBlock();
    if (block != null)
    {
      Material m = block.getType();
      if ((p != null) && (p.hasPermission("KeepItems.chestaccess")) && (m != null) && ((m.equals(Material.CHEST)) || (m.equals(Material.ENDER_CHEST))))
      {
        p.sendMessage(ChatColor.DARK_RED + "You are prevented from accessing chests.");

        ev.setCancelled(true);
      }
      else if ((p != null) && (p.hasPermission("KeepItems.furnace")) && (m != null) && ((m.equals(Material.FURNACE)) || (m.equals(Material.BURNING_FURNACE))))
      {
        p.sendMessage(ChatColor.DARK_RED + "You are prevented from accessing furnaces.");

        ev.setCancelled(true);
      }
      else if ((p != null) && (p.hasPermission("KeepItems.dispenser")) && (m != null) && (m.equals(Material.DISPENSER)))
      {
        p.sendMessage(ChatColor.DARK_RED + "You are prevented from accessing dispensers.");

        ev.setCancelled(true);
      }
      else if ((p != null) && (p.hasPermission("KeepItems.brewingstand")) && (m != null) && (m.equals(Material.BREWING_STAND)))
      {
        p.sendMessage(ChatColor.DARK_RED + "You are prevented from accessing brewing stands.");

        ev.setCancelled(true);
      }
    }
  }
}