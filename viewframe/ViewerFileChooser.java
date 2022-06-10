package viewframe;

import javax.swing.*;

public class ViewerFileChooser extends JFileChooser {
    public ViewerFileChooser() {
        addFilter();
    }

    void addFilter() {
        this.addChoosableFileFilter(new MyFileFilter(new String[]{".BMP", "BMP (*.BMP)"},
                "BMP (*.BMP)"));
        this.addChoosableFileFilter(new MyFileFilter(new String[]{".JPG", ".JPEG", ".JPE", ".JFIF"},
                "JPEG (*.JPG;*.JPEG;*.JPE;*.JFIF)"));
        this.addChoosableFileFilter(new MyFileFilter(new String[]{".GIF"}, "GIF (*.GIF)"));
        this.addChoosableFileFilter(new MyFileFilter(new String[]{".PNG"}, "PNG (*.PNG)"));
        this.addChoosableFileFilter(new MyFileFilter(new String[]{".ICO"}, "ICO (*.ICO)"));
        this.addChoosableFileFilter(new MyFileFilter(new String[]{".TIF"}, "TIF (*.TIF)"));
        this.addChoosableFileFilter(new MyFileFilter(new String[]{".BMP", ".JPG", ".JPEG", ".JPE"
                , "JFIF", ".GIF", ".TIF", ".TIFF", ".PNG", ".ICO"}, "所有图形文件"));
    }
}
