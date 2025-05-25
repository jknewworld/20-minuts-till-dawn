// کلاس launcher دسکتاپ LibGDX
package com.P3.Model;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.P3.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.util.List;

public class DesktopLauncher {
    public static void main () {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("My LibGDX Game");
        config.setWindowedMode(800, 600);

        // اجرای بازی LibGDX
        new Lwjgl3Application(new Main(), config);

        // ایجاد و نمایش پنجره جداگانه برای Drag & Drop فایل
        SwingUtilities.invokeLater(() -> createDragDropWindow());
    }

    private static void createDragDropWindow() {
        JFrame frame = new JFrame("Drop files here");
        JTextArea textArea = new JTextArea("Drop files here...");
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(new JScrollPane(textArea));
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        new DropTarget(textArea, new DropTargetListener() {
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable t = dtde.getTransferable();
                    if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                        StringBuilder sb = new StringBuilder();
                        for (File file : files) {
                            sb.append(file.getAbsolutePath()).append("\n");
                            System.out.println("Dropped file: " + file.getAbsolutePath());
                        }
                        textArea.setText(sb.toString());
                        dtde.dropComplete(true);
                    } else {
                        dtde.rejectDrop();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    dtde.rejectDrop();
                }
            }
            public void dragEnter(DropTargetDragEvent dtde) {}
            public void dragOver(DropTargetDragEvent dtde) {}
            public void dropActionChanged(DropTargetDragEvent dtde) {}
            public void dragExit(DropTargetEvent dte) {}
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
