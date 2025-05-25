package com.P3.Model;

import javax.swing.*;
import java.io.File;

public class DesktopFileChooser {
    public static String chooseFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }


}
