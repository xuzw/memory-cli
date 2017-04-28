package com.github.xuzw.memory.cli;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.github.xuzw.memory.api.MemoryRepository;
import com.github.xuzw.memory.api.MemoryRepositoryFileFormatException;
import com.github.xuzw.memory.cli.cmd.UnfinishedActivity;
import com.github.xuzw.memory.cli.cmd.AllMemories;
import com.github.xuzw.memory.cli.cmd.Append;
import com.github.xuzw.memory.cli.cmd.Help;
import com.github.xuzw.memory.cli.cmd.AppendOldActivity;
import com.github.xuzw.memory.cli.cmd.OverActivity;
import com.github.xuzw.memory.cli.cmd.Preview;
import com.github.xuzw.memory.cli.cmd.Version;
import com.github.xuzw.memory.cli.cmd.Who;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月29日 下午4:08:06
 */
public class Main {
    public static void main(String[] args) throws IOException, MemoryRepositoryFileFormatException, InstantiationException, IllegalAccessException, ParseException {
        MemoryRepository memoryRepository = new MemoryRepository(Config.load().getMemoryRepositoryFilePath());
        if (args.length == 0) {
            new UnfinishedActivity().execute(memoryRepository);
            memoryRepository.close();
            return;
        }
        List<String> argList = _getArgList(args);
        String firstArg = argList.remove(0);
        if (Preview.cmd.equalsIgnoreCase(firstArg)) {
            new Preview().execute(memoryRepository);
        } else if (AllMemories.cmd.equalsIgnoreCase(firstArg)) {
            new AllMemories().execute(memoryRepository);
        } else if (Version.cmd.equalsIgnoreCase(firstArg)) {
            new Version().execute();
        } else if (Help.cmd.equalsIgnoreCase(firstArg)) {
            new Help().execute();
        } else if (Who.cmd.equalsIgnoreCase(firstArg)) {
            new Who().execute(argList, memoryRepository);
        } else if (AppendOldActivity.cmd.equalsIgnoreCase(firstArg)) {
            new AppendOldActivity().execute(firstArg, argList, memoryRepository);
        } else if (OverActivity.cmd.equalsIgnoreCase(firstArg)) {
            new OverActivity().execute(argList, memoryRepository);
        } else {
            new Append().execute(firstArg, argList, memoryRepository);
        }
        memoryRepository.close();
    }

    private static List<String> _getArgList(String[] args) {
        List<String> list = new ArrayList<>();
        for (String arg : args) {
            list.add(arg);
        }
        return list;
    }
}
