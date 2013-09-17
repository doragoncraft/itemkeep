package me.carl230690.keepitems;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class SaveArmourThread extends Thread
{
  private Player plr;
  private ItemStack[] armour;

  public SaveArmourThread(Player plr, ItemStack[] armour)
  {
    this.plr = plr;
    this.armour = armour;
  }

  public void run()
  {
    this.plr.getInventory().setArmorContents(this.armour);
  }
}