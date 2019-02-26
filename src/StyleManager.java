import java.awt.*;

class StyleManager {

    public static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    public static void setFont(Component component, Font font)
    {
        component.setFont(font);
        if(component instanceof Container)
        {
            for(Component child : ((Container) component).getComponents())
            {
                setFont(child, font);
            }
        }
    }

    public static void setFileChooserFont(Component[] comp)
    {
        Font f = new Font("Arial", Font.PLAIN, width/100);
        for(int x = 0; x < comp.length; x++)
        {
            if(comp[x] instanceof Container) {
                setFileChooserFont(((Container)comp[x]).getComponents());
            }
            try {
                comp[x].setFont(f);
            }
            catch(Exception e) {}
        }
    }
}