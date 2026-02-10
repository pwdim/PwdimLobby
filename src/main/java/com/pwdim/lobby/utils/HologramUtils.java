package com.pwdim.lobby.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class HologramUtils implements Listener {

    public static void createHologram(Location location, String text){
        Entity entity = location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        entity.setCustomNameVisible(true);
        entity.setCustomName(MyUtils.color(text));
        Vector v = new Vector(0, 0, 0);
        entity.setVelocity(v);
        ArmorStand armor = (ArmorStand) entity;
        armor.setVisible(false);
        armor.setGravity(false);
    }
}
