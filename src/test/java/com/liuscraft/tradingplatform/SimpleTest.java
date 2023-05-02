package com.liuscraft.tradingplatform;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author LiusCraft
 * @DateTime 2023/4/25 14:40
 */
public class SimpleTest {
    @Test
    public void testGetSystemInfo() {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
    }

    @Test
    void getClassPathFile() throws IOException {
        File file0 = new File("");
        System.out.println(file0.getAbsolutePath());
        System.out.println(file0.getCanonicalPath());
        File file = new File("", "404.png");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());
        File file1 = new File(".", "404.png");
        System.out.println(file1.getAbsolutePath());
        System.out.println(file1.getCanonicalPath());
        System.out.println(System.getProperty("user.dir"));
        String path = getClass().getClassLoader().getResource("404.png").getPath();
        File file2 = new File(path);
        System.out.println(Objects.requireNonNull(getClass().getClassLoader().getResource("404.png")).getFile());
        System.out.println(file2.exists());
    }
}
