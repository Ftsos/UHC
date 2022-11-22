package me.ftsos.gui;

import dev.triumphteam.gui.guis.GuiItem;
import me.ftsos.utils.ReflectionUtils;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class PerPlayerGui<T extends Gui> {
     private Map<Player, T> playerGuiInstanceMap = new HashMap<>();
     public void openGuiToPlayer(Player player) {
          T guiInstance = null;
          try {
               Class<T> clazz = (Class<T>) ReflectionUtils.getGenericClassOfType(this);
               guiInstance = ReflectionUtils.getDefaultInstance(clazz);
          } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
               e.printStackTrace();
          }
          guiInstance.getGui().open(player);
          guiInstance.getGui().setCloseGuiAction(event -> {
               if(event == null) return;
               if(this.playerGuiInstanceMap.get(event.getPlayer()) == null) return;
               this.playerGuiInstanceMap.remove(event.getPlayer());
          });
          this.playerGuiInstanceMap.put(player, guiInstance);
     }


     public void addItemToGuis(GuiItem guiItem) {
          for(Map.Entry<Player, T> entry : playerGuiInstanceMap.entrySet()) {
               addItemToGui(entry.getKey(), guiItem);
          }
     }

     public void removeItemToGuis(GuiItem guiItem) {
          for(Map.Entry<Player, T> entry : playerGuiInstanceMap.entrySet()) {
               removeItemToGui(entry.getKey(), guiItem);
          }
     }

     public void addItemToGui(Player player, GuiItem ...guiItems) {
          if(this.playerGuiInstanceMap.get(player) == null) return;
          this.playerGuiInstanceMap.get(player).getGui().addItem(guiItems);
     }

     public void removeItemToGui(Player player, GuiItem guiItem) {
          if(this.playerGuiInstanceMap.get(player) == null) return;
          this.playerGuiInstanceMap.get(player).getGui().removeItem(guiItem);
     }

     public void updateGuis() {
          for(Map.Entry<Player, T> entry : playerGuiInstanceMap.entrySet()) {
               updateGui(entry.getKey());
          }
     }

     public void updateGui(Player player) {
          if(this.playerGuiInstanceMap.get(player) == null) return;
          this.playerGuiInstanceMap.get(player).getGui().update();
     }
}
