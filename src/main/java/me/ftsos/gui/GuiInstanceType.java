package me.ftsos.gui;

import dev.triumphteam.gui.guis.*;

public enum GuiInstanceType {
    PAGINATED(PaginatedGui.class),
    SCROLLING(ScrollingGui.class),
    STORAGE(StorageGui.class);

   private Class<? extends BaseGui> type;

   GuiInstanceType(Class<? extends BaseGui> guiClass) {
        this.type = guiClass;
   }

    public Class<? extends BaseGui> getType() {
        return type;
    }

    public boolean isSameType(Class<? extends BaseGui> clazz) {
       if(this.type == clazz) return true;
       return false;
   }

    public <T extends BaseGui> boolean isSameType(T gui) {
        if(type.isInstance(gui)) return true;
        return false;
    }

    public static <T extends BaseGui> GuiInstanceType getInstanceType(T gui) {
       for(GuiInstanceType respectiveType : GuiInstanceType.values()) {
           if(respectiveType.isSameType(gui)) return respectiveType;
       }
       return null;
    }
}
