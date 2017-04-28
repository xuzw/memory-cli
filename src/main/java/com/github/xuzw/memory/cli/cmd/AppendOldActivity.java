package com.github.xuzw.memory.cli.cmd;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import com.github.xuzw.memory.api.MemoryRepository;
import com.github.xuzw.memory.api.MemoryRepository.MemoryWrapper;
import com.github.xuzw.memory.model.Memory;
import com.github.xuzw.memory.model.MemoryBuilder;
import com.github.xuzw.memory.model.MemoryType;
import com.github.xuzw.memory.utils.DynamicField;
import com.github.xuzw.memory.utils.DynamicObject;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年4月5日 下午3:38:09
 */
public class AppendOldActivity {
    public static final String cmd = "old";
    private MemoryType memoryType = MemoryType.old_activity;

    private String _format(MemoryWrapper memoryWrapper, DynamicObject ext, MemoryRepository memoryRepository) {
        int index = memoryWrapper.getIndex();
        Memory memory = memoryWrapper.getMemory();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss.SSS");
        String time = dateFormat.format(memory.getTimestamp());
        String locale = memory.getLocale();
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("[%d] %s %s\n", index, memoryType.getName(), ext.getRequiredFields().get(0).getValue()));
        sb.append(ext.toJsonExceptFirstRequiredField().toJSONString());
        sb.append("\n");
        sb.append(String.format("%s %s", locale, time));
        return sb.toString();
    }

    public void execute(String firstArg, List<String> args, MemoryRepository memoryRepository) throws ParseException, IOException {
        DynamicObject ext = memoryType.newExtDynamicObject().setRaw(args);
        DynamicField sources = ext.get("sources");
        if (sources != null && sources.isBlank()) {
            args.add("sources");
            args.add(memoryRepository.getWhoEntity().getName());
            ext = memoryType.newExtDynamicObject().setRaw(args);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmm");
        MemoryBuilder memoryBuilder = new MemoryBuilder();
        memoryBuilder.raw(ext.getRaw());
        memoryBuilder.timestamp(dateFormat.parse(ext.get("time").getValue()).getTime());
        memoryBuilder.uuid(UUID.randomUUID().toString());
        memoryBuilder.type(memoryType);
        MemoryWrapper memoryWrapper = memoryRepository.append(memoryBuilder.build());
        System.out.println(_format(memoryWrapper, ext, memoryRepository));
    }
}
