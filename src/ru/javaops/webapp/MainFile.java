package ru.javaops.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        File file = new File("C:\\Projects\\basejava\\.gitignore");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File file1 = new File(".\\.gitignore");
        try {
            System.out.println(file1.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File file2 = new File("..\\basejava\\.gitignore");
        try {
            System.out.println(file2.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javaops/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        String filePath = ".\\.gitignore";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            System.out.println(fileInputStream.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try(FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\nbuildCatalogTree() method test!");
        File file3 = new File("C:\\Projects\\basejava\\src");
        buildCatalogTree(file3, "\t");
    }

    private static void buildCatalogTree(File file, String indent) {
        System.out.println(indent + file.getName() + ":");
        File[] fileList = file.listFiles();
        if (fileList == null) {
            return;
        }
        for (File someFile : fileList) {
            if (someFile.isDirectory()) {
                buildCatalogTree(someFile, indent + "\t");
            } else {
                System.out.println(indent + "\t" + someFile.getName());
            }
        }
    }
}
