package game.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class FileIO {
    // Private variable of a new JFileChooser instance
    private static final JFileChooser chooser = new JFileChooser(System.getProperty("user.home")) {
        protected JDialog createDialog(Component parent) throws HeadlessException {
            JDialog jDialog = super.createDialog(parent);
            jDialog.setAlwaysOnTop(true);
            super.setFileFilter(new FileNameExtensionFilter("Text & Data Files", "txt", "dat", "data"));
            super.setAcceptAllFileFilterUsed(false);
            super.setFileSelectionMode(JFileChooser.FILES_ONLY);

            return jDialog;
        }
    };

    /*
     * Opens a GUI which lets the user select a file to export to and then exports the inputted string in to it
     * Pre: Takes in a string
     */
    public static void writeToFile(String contentToWrite) {
        chooser.setFileFilter(new FileNameExtensionFilter("Text & Data Files", "txt", "dat", "data"));
        chooser.setAcceptAllFileFilterUsed(false);
        int status = chooser.showSaveDialog(null);

        if (status == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile().getName().endsWith(".txt") || chooser.getSelectedFile().getName().endsWith(".dat")
                    || chooser.getSelectedFile().getName().endsWith(".data")) {
                File selectedFile = chooser.getSelectedFile();

                if (!selectedFile.exists()) {
                    try {
                        selectedFile.getParentFile().mkdir();
                        selectedFile.createNewFile();
                        System.out.println("Created new file at: " + selectedFile.getPath());
                    } catch (IOException e) {
                        System.out.println("ERROR CREATING FILE: " + e.getMessage());
                    } catch (SecurityException e) {
                        System.out.println("ERROR CREATING FILE: Does not have security permissions to create file");
                    }
                }

                try {
                    FileWriter out = new FileWriter(selectedFile);
                    BufferedWriter writeFile = new BufferedWriter(out);

                    writeFile.write(contentToWrite);

                    writeFile.close();
                    out.close();
                    System.out.println("Successfully exported to file!");
                } catch (IOException e) {
                    System.out.println("Had a problem writing to file!");
                    writeToFile(contentToWrite);
                }
            } else {
                System.out.println("Please ensure the file is a .txt or .dat!");
                writeToFile(contentToWrite);
            }
        } else {
            System.out.println("No file was selected.");
            writeToFile(contentToWrite);
        }
    }

    /*
     * Opens a GUI which lets user select a file to import and then returns every line as an element in a string array
     * Post: Returns a string array representing each line from the selected file
     */
    public static String[] readFile() {
        int status = chooser.showOpenDialog(null);
        String[] texts = new String[0];

        if (status == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile().getName().endsWith(".txt") || chooser.getSelectedFile().getName().endsWith(".dat")
                    || chooser.getSelectedFile().getName().endsWith(".data")) {
                File selectedFile = chooser.getSelectedFile();

                if (selectedFile.exists()) {
                    try {
                        FileReader in = new FileReader(selectedFile);
                        BufferedReader readFile = new BufferedReader(in);

                        String currentLine;

                        while ((currentLine = readFile.readLine()) != null) {
                            String[] newStringArray = new String[texts.length + 1];

                            for (int i = 0; i < texts.length; i++) {
                                newStringArray[i] = texts[i];
                            }

                            newStringArray[newStringArray.length-1] = currentLine;
                            texts = newStringArray;
                        }

                        readFile.close();
                        in.close();
                        System.out.println("Successfully loaded file, verifying file...");
                    } catch (IOException e) {
                        System.out.println("Had a problem reading the file!");
                        return readFile();
                    }
                } else {
                    System.out.println("File does not exist");
                    return readFile();
                }
            } else {
                System.out.println("Please ensure the file is a .txt or .dat!");
                return readFile();
            }
        } else {
            System.out.println("No file was selected.");
            return readFile();
        }

        return texts;
    }

}
