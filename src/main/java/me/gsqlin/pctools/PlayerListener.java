package me.gsqlin.pctools;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PCStorage;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class PlayerListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof InvHub) {
            BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    PCTools main = PCTools.pcTools;
                    int slot = e.getRawSlot();
                    Inventory inv = e.getInventory();
                    Player p = (Player) e.getWhoClicked();
                    InvHub invHub = (InvHub) inv.getHolder();
                    ItemStack clickItem = e.getCurrentItem();
                    Player owner = invHub.getOwner();
                    String rawTitle = invHub.getRawTitle();
                    if (slot >= inv.getSize() || e.getClick().equals(ClickType.DROP)) {
                        e.setCancelled(true);
                        return;
                    }
                    p.sendMessage(e.getAction().name());
                    if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || e.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD) || e.getAction().equals(InventoryAction.HOTBAR_SWAP))
                        e.setCancelled(true);
                    if (rawTitle.equals(main.getConfig().getString("PCToolsTitle"))) {
                        if (!GSQUtil.getKdj().contains(slot)) {
                            e.setCancelled(true);
                            if (slot == 50 || slot == 45 || slot == 47) {
                                InvHub fy;
                                ItemStack item = new ItemStack(Material.AIR);
                                if (p.getItemOnCursor().getType().equals(Material.getMaterial("PIXELMON_PIXELMON_SPRITE")))
                                    item = p.getItemOnCursor();
                                invHub.fy = true;
                                if (slot == 50) {
                                    if (GSQUtil.getMaxbox(owner) == invHub.getBox() + 1) {
                                        p.closeInventory();
                                        fy = new InvHub(owner, 0, GSQUtil.getMsg(owner, main.getConfig().getString("PCToolsTitle"), 1), 6 * 9,main.getConfig().getString("PCToolsTitle"));
                                        fy.putPhoto(item, invHub.getPokemon(item));
                                        p.openInventory(fy.getInventory());
                                        p.setItemOnCursor(item);
                                    } else {
                                        p.closeInventory();
                                        fy = new InvHub(owner, invHub.getBox() + 1, GSQUtil.getMsg(owner, main.getConfig().getString("PCToolsTitle"), invHub.getBox() + 2), 6 * 9,main.getConfig().getString("PCToolsTitle"));
                                        fy.putPhoto(item, invHub.getPokemon(item));
                                        p.openInventory(fy.getInventory());
                                        p.setItemOnCursor(item);
                                    }
                                } else if (slot == 45) {
                                    if (invHub.getBox() + 1 == 1) {
                                        p.closeInventory();
                                        fy = new InvHub(owner, GSQUtil.getMaxbox(p) - 1, GSQUtil.getMsg(owner, main.getConfig().getString("PCToolsTitle"), GSQUtil.getMaxbox(p)), 6 * 9,main.getConfig().getString("PCToolsTitle"));
                                        fy.putPhoto(item, invHub.getPokemon(item));
                                        p.openInventory(fy.getInventory());
                                        p.setItemOnCursor(item);
                                    } else {
                                        p.closeInventory();
                                        fy = new InvHub(owner, invHub.getBox() - 1, GSQUtil.getMsg(owner, main.getConfig().getString("PCToolsTitle"), invHub.getBox()), 6 * 9,main.getConfig().getString("PCToolsTitle"));
                                        fy.putPhoto(item, invHub.getPokemon(item));
                                        p.openInventory(fy.getInventory());
                                        p.setItemOnCursor(item);
                                    }
                                }
                            }
                        } else if (clickItem != null){
                            if (clickItem.getType().equals(Material.getMaterial("PIXELMON_PIXELMON_SPRITE"))) {
                                if (invHub.getPokemon(clickItem).isInRanch()) {
                                    e.setCancelled(true);
                                    p.sendMessage(GSQUtil.getMsg(owner,main.getConfig().getString("Message.OnRanch")));
                                    return;
                                }
                                if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                                    e.setCancelled(true);
                                    int i = GSQUtil.getNullPoke(GSQUtil.getDF(slot), inv);
                                    if (i < 55) {
                                        inv.setItem(i, e.getCurrentItem());
                                        inv.setItem(slot, null);
                                    }
                                } else if (e.getClick().equals(ClickType.RIGHT)) {
                                    InvHub qr = new InvHub(GSQUtil.getMsg(owner,main.getConfig().getString("ConfirmDeletion")), 3 * 9,main.getConfig().getString("ConfirmDeletion"));
                                    qr.setUpInv(inv);
                                    GSQUtil.qrsc(owner,clickItem, qr);
                                    qr.putPhoto(clickItem, invHub.getPokemon(clickItem));
                                    qr.setBox(invHub.getBox());
                                    p.closeInventory();
                                    p.openInventory(qr.getInventory());
                                }
                            }
                        }else if (!p.getItemOnCursor().getType().equals(Material.getMaterial("PIXELMON_PIXELMON_SPRITE"))){
                            e.setCancelled(true);
                        }
                    } else if (rawTitle.equals(main.getConfig().getString("ConfirmDeletion"))) {
                        e.setCancelled(true);
                        Inventory xinv = invHub.getUpInv();
                        if (slot == 20) {
                            ItemStack itemStack = inv.getItem(4);
                            xinv.remove(itemStack);
                            p.closeInventory();
                            p.openInventory(xinv);
                        } else if (slot == 24) {
                            p.closeInventory();
                            p.openInventory(xinv);
                        }
                    }
                }
            };
            bukkitRunnable.run();
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        e.getWhoClicked().sendMessage(e.getType().name());
        if (e.getInventory().getHolder() instanceof InvHub) {
            for (int i : e.getRawSlots()) {
                e.getWhoClicked().sendMessage(i+"");
                if (i > e.getInventory().getSize()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof InvHub) {
            BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                @Override
                public void run() {
                    PCTools main = PCTools.pcTools;
                    Inventory inv = e.getInventory();
                    InvHub invHub = (InvHub) inv.getHolder();
                    Player p = (Player) e.getPlayer();
                    Player owner = invHub.getOwner();
                    String rawTitle = invHub.getRawTitle();
                    if (rawTitle.equals(main.getConfig().getString("PCToolsTitle"))) {
                        //背包精灵处
                        if (owner == null){
                            p.sendMessage(GSQUtil.getMsg(p,main.getConfig().getString("Message.Error3")));
                            p.setItemOnCursor(new ItemStack(Material.AIR));
                            return;
                        }

                        for (int i = 0; i < 6; i++) {
                            ItemStack itemStack = inv.getItem(PCTools.boxGui.get(i));
                            PlayerPartyStorage pps = invHub.getPps();
                            if (itemStack != null) {
                                pps.set(i, invHub.getPokemon(itemStack));
                            } else {
                                pps.set(i, null);
                            }
                        }
                        //pc精灵处理
                        for (int i = 0; i < 30; i++) {
                            ItemStack itemStack;
                            if (i <= 5) {
                                itemStack = inv.getItem(i);
                            } else {
                                itemStack = inv.getItem(PCTools.boxGui.get(i));
                            }
                            PCStorage pcs = invHub.getPcs();
                            if (itemStack != null) {
                                pcs.set(invHub.getBox(), i, invHub.getPokemon(itemStack));
                            } else {
                                pcs.set(invHub.getBox(), i, null);
                            }
                        }
                        if (p.getItemOnCursor().getType().equals(Material.getMaterial("PIXELMON_PIXELMON_SPRITE")) && !invHub.fy) {
                            ItemStack item = p.getItemOnCursor();
                            Pokemon poke = invHub.getPokemon(item);
                            invHub.getPcs().add(poke);
                            p.sendMessage(GSQUtil.getMsg(owner,main.getConfig().getString("Message.AutomaticAllocation").replace("{RandomLocation}", (Objects.requireNonNull(poke.getPosition()).box + 1) + "/" + (poke.getPosition().order + 1))));
                        }
                        p.setItemOnCursor(new ItemStack(Material.AIR));
                    }
                    GSQUtil.OnlinePC.remove(owner);
                }
            };
            bukkitRunnable.run();
        }
    }
    @EventHandler
    public void onOpenInventory(InventoryOpenEvent e){
        if (!(e.getInventory().getHolder() instanceof InvHub)) return;
        InvHub invHub = (InvHub) e.getInventory().getHolder();
        PCTools main = PCTools.pcTools;
        if (invHub.getOwner() == null) return;
        Player owner = invHub.getOwner();
        String rawTitle = invHub.getRawTitle();
        if (rawTitle.equals(main.getConfig().getString("PCToolsTitle"))){
            if (GSQUtil.OnlinePC.get(owner) == null) {
                GSQUtil.OnlinePC.put(owner, true);
                return;
            }
            if (GSQUtil.OnlinePC.get(owner)){
                e.setCancelled(true);
                e.getPlayer().sendMessage(GSQUtil.getMsg(owner,main.getConfig().getString("Message.PcOccupied")));
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if (e.getClickedBlock() == null) return;
        if (GSQUtil.OnlinePC.get(e.getPlayer())==null) return;
        if (GSQUtil.OnlinePC.get(e.getPlayer())){
            if (e.getClickedBlock().getType().equals(Material.getMaterial("PIXELMON_PC"))){
                PCTools main = PCTools.pcTools;
                Player p = e.getPlayer();
                e.setCancelled(true);
                p.sendMessage(GSQUtil.getMsg(p,main.getConfig().getString("Message.PcOccupied")));
            }
        }
    }
}
