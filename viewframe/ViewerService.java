package viewframe;


import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ViewerService {
    private List<File> currentFiles;
    private ViewerFileChooser fileChooser = new ViewerFileChooser();
    private File currentFile;
    private File currentDirectory;
    private double range = 0.5;

    static ViewerService getInstance() {
        return new ViewerService();
    }

    private void open(ViewerFrame frame) {
        String name = null;
        //如果选择打开
        if (fileChooser.showOpenDialog(frame) == ViewerFileChooser.APPROVE_OPTION) {
            //目前打开文件赋值
            this.currentFile = fileChooser.getSelectedFile();
            //获取文件路径
            name = this.currentFile.getPath();
            //获取当前文件夹
            File cd = fileChooser.getCurrentDirectory();
            //如果文件夹有改变
            if (cd != this.currentDirectory || this.currentDirectory == null) {
                //获取fileChooser的所有FileFilter
                FileFilter[] fileFilters = fileChooser.getChoosableFileFilters();
                File[] files = cd.listFiles();
                this.currentFiles = new ArrayList<>();
                for (File file : files) {
                    for (FileFilter filter : fileFilters) {
                        //如果是图片文件
                        if (filter.accept(file)) {
                            //添加文件
                            System.out.println("service中" + file.getName());
                            this.currentFiles.add(file);
                            break;
                        }
                    }
                }
            }
        }
        ImageIcon icon = new ImageIcon(name);
        frame.getLabel().setIcon(icon);
    }

    void zoom(ViewerFrame frame, boolean isEnlarge) {
        //获取放大或者缩小比例
        double enlargeRange = isEnlarge ? 1 + range : 1 - range;
        //获取目前图片
        ImageIcon icon = (ImageIcon) frame.getLabel().getIcon();
        //null判断
        if (icon != null) {
            int width = (int) (icon.getIconWidth() * enlargeRange);
            //获取改变大小后的图片
            ImageIcon newIcon = new ImageIcon(icon.getImage().getScaledInstance(width, -1, Image.SCALE_DEFAULT));
            frame.getLabel().setIcon(newIcon);
        }
    }

    void last(ViewerFrame frame) {
        //如果有打开包含图片的文件夹
        if (this.currentFiles != null && !this.currentFiles.isEmpty()) {
            int index = this.currentFiles.indexOf(this.currentFile);
            //打开上一张
            if (index > 0) {
                File file = this.currentFiles.get(index - 1);
                ImageIcon icon = new ImageIcon(file.getPath());
                frame.getLabel().setIcon(icon);
                this.currentFile = file;
            }
        }
    }

    void next(ViewerFrame frame) {
        //如果有打开包含图片的文件夹
        if (this.currentFiles != null && !this.currentFiles.isEmpty()) {
            int index = this.currentFiles.indexOf(this.currentFile);
            //打开上一张
            if (index < currentFiles.size() - 1) {
                File file = this.currentFiles.get(index + 1);
                ImageIcon icon = new ImageIcon(file.getPath());
                frame.getLabel().setIcon(icon);
                this.currentFile = file;
            }
        }
    }

    void menuDo(ViewerFrame frame, String cmd) {
        if (cmd.equals("打开(O)")) {
            open(frame);
        } else if (cmd.equals("放大(M)")) {
            zoom(frame, true);
        } else if (cmd.equals("缩小(O)")) {
            zoom(frame, false);
        } else if (cmd.equals("上一个(X)")) {
            last(frame);
        } else if (cmd.equals("下一个(P)")) {
            next(frame);
        } else if (cmd.equals("退出(X)")) {
            System.exit(0);
        }
    }
}