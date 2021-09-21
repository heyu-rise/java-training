package org.heyu.homework1301.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxx
 * @date 2020/04/18
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static final String SEPARATOR = File.separator;

    public static final String RELATIVE_PATH = "../";

    public static void deleteTempFile(String filePath) throws Exception {
        pathCheck(filePath);
        File file = new File(filePath);
        if (file.exists()) {
            boolean d = file.delete();
            if (d) {
                logger.info("删除临时文件成功:{}", filePath);
            } else {
                logger.error("删除临时文件失败:{}", filePath);
            }
        }

    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getCurDay() {
        return new SimpleDateFormat("yyyy/MMdd").format(new Date());
    }

    public static void createDir(String filePath) throws Exception {
        pathCheck(filePath);
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            boolean mk = file.getParentFile().mkdirs();
            if (!mk) {
                logger.error("创建目录{}失败", file.getParentFile().getPath());
            }
        }
    }

    public static void pathCheck(String... path) throws Exception {
        for (String s : path) {
            if (s.contains(RELATIVE_PATH)) {
                throw new Exception("非法路径");
            }
        }
    }
}
