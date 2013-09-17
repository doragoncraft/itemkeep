package me.carl230690.keepitems;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class SaveInventoryThread extends Thread
{
  private Player plr;
  private ItemStack[] inv;

  public SaveInventoryThread(Player plr, ItemStack[] inv)
  {
    this.plr = plr;
    this.inv = inv;
  }

  public void run()
  {
    this.plr.getInventory().setContents(this.inv);
  }
}