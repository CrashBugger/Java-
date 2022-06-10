package viewframe;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class MyFileFilter extends FileFilter {
    private String description;
    private String[] suffarr;

    public MyFileFilter(String[] suffarr, String description) {
        super();
        this.suffarr = suffarr;
        this.description = description;
    }

    @Override
    public boolean accept(File file) {
        System.out.println(file.getName());
        String fileName = file.getName().toUpperCase();
        for (String s : suffarr) {
            if (fileName.endsWith(s)) {
                System.out.println(fileName);
                return true;
            }
        }
        //如果是目录,返回true,否则返回false
        return false;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}