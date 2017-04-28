package com.github.xuzw.memory.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月29日 下午11:15:22
 */
public class Config {
    private static final String config_file_name = ".MemoryCli";
    private static final String config_file_encoding = "utf-8";

    private String memoryRepositoryFilePath;

    public String getMemoryRepositoryFilePath() {
        return memoryRepositoryFilePath;
    }

    public void setMemoryRepositoryFilePath(String memoryRepositoryFilePath) {
        this.memoryRepositoryFilePath = memoryRepositoryFilePath;
    }

    public static Config load() throws IOException {
        String configFilePath = System.getProperty("user.home") + File.separator + config_file_name;
        InputStream input = new FileInputStream(configFilePath);
        List<String> lines = IOUtils.readLines(input, config_file_encoding);
        IOUtils.closeQuietly(input);
        return JSON.parseObject(StringUtils.join(lines, ""), Config.class);
    }
}
