package solarsystem.coffee.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;

public class C {
    public C() {
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> colorLines(List<String> lore) {
        List<String> color = new ArrayList();
        Iterator var2 = lore.iterator();

        while(var2.hasNext()) {
            String s = (String)var2.next();
            color.add(color(s));
        }

        return color;
    }
}