package cu.lacumbre.auditor.utils.ui;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;


public class IconManager {
    private static final Map<String, ImageIcon> iconCache = new HashMap<>();
    private static final int DEFAULT_ICON_SIZE = 20;
    
    public static ImageIcon getIcon(String name) {
        return getIcon(name, DEFAULT_ICON_SIZE);
    }
    
    public static ImageIcon getIcon(String name, int size) {
        String key = name + "_" + size;
        
        return iconCache.computeIfAbsent(key, k -> {
            String path = "/icons/" + name + ".svg";
            URL resourceUrl = IconManager.class.getResource(path);
            
            if (resourceUrl != null) {
                ImageIcon originalIcon = new ImageIcon(resourceUrl);
                Image scaledImage = originalIcon.getImage()
                    .getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
            
            System.err.println("No se pudo cargar el icono: " + path);
            return null;
        });
    }
    
    public static void preloadIcons() {
        String[] icons = {
            "inventory",
            "staff",
            "sale",
            "documents",
            "settings",
            "expenses",
            "switch",
            "raw-material"
        };
        
        for (String icon : icons) {
            getIcon(icon);
        }
    }
}
