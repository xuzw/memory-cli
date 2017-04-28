package com.github.xuzw.memory.cli.cmd;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.github.xuzw.entity.model.Entity;
import com.github.xuzw.memory.api.MemoryRepository;
import com.github.xuzw.memory.api.MemoryRepository.MemoryWrapper;
import com.github.xuzw.memory.model.Memory;
import com.github.xuzw.memory.model.MemoryBuilder;
import com.github.xuzw.memory.model.MemoryType;
import com.github.xuzw.memory.utils.DynamicObject;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年4月3日 下午12:59:44
 */
public class Who {
    public static final String cmd = "who";
    private MemoryType memoryType = MemoryType.who;

    private void _query(MemoryRepository memoryRepository) {
        Entity who = memoryRepository.getWhoEntity();
        if (who == null) {
            System.out.println("尚未设置这是谁的记忆");
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(who.getName());
        List<String> shortNames = who.getShortNames();
        if (!shortNames.isEmpty()) {
            sb.append(" ( ").append(StringUtils.join(shortNames, " , ")).append(" )");
        }
        System.out.println(sb.toString());
    }

    private void _set(List<String> args, MemoryRepository memoryRepository) throws IOException {
        DynamicObject ext = memoryType.newExtDynamicObject().setRaw(args);
        MemoryBuilder memoryBuilder = new MemoryBuilder();
        memoryBuilder.raw(ext.getRaw());
        memoryBuilder.timestamp(System.currentTimeMillis());
        memoryBuilder.uuid(UUID.randomUUID().toString());
        memoryBuilder.type(memoryType);
        memoryBuilder.locale(MemoryRepository.unknow_place);
        MemoryWrapper memoryWrapper = memoryRepository.append(memoryBuilder.build());
        System.out.println(_format(memoryWrapper, ext, memoryRepository));
    }

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

    public void execute(List<String> args, MemoryRepository memoryRepository) throws IOException {
        if (args.size() > 0) {
            _set(args, memoryRepository);
        } else {
            _query(memoryRepository);
        }
    }
}
