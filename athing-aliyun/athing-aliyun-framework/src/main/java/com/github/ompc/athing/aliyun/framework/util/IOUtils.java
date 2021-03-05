package com.github.ompc.athing.aliyun.framework.util;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.Files.readAllLines;
import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;

/**
 * I/O操作工具类
 */
public class IOUtils {

    /**
     * 获取LOGO
     *
     * @return LOGO
     */
    public static String getLogo(String path) {
        final ClassLoader loader = IOUtils.class.getClassLoader();
        final StringBuilder logoSB = new StringBuilder();
        try (final Scanner scanner = new Scanner(requireNonNull(loader.getResourceAsStream(path)))) {
            while (scanner.hasNextLine()) {
                logoSB.append(scanner.nextLine()).append("\n");
            }
        }
        return logoSB.toString();
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (null != closeable) {
                closeable.close();
            }
        } catch (Exception cause) {
            // ignore...
        }
    }

    public static void main(String[] args) {
        final ClassLoader loader = IOUtils.class.getClassLoader();
        final StringBuilder sb = new StringBuilder();
        try {

            Scanner scanner = new Scanner(get("E:\\2021\\2\\IOT\\athing\\athing-aliyun\\athing-aliyun-thing\\src\\main\\resources\\athing-logo.txt").toFile());
            while(scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }

            System.out.println(sb.toString());

            try {
                List<String> logos = readAllLines(get("E:\\2021\\2\\IOT\\athing\\athing-aliyun\\athing-aliyun-thing\\src\\main\\resources\\athing-logo.txt"));
                logos.stream().map(line -> line + "\n").forEach(System.out::print);

            } catch (Exception e) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
