package com.github.xuzw.memory.cli.cmd;

import java.text.SimpleDateFormat;

import com.github.xuzw.memory.api.MemoryRepository;
import com.github.xuzw.memory.api.MemoryRepository.MemoryWrapper;
import com.github.xuzw.memory.model.Memory;
import com.github.xuzw.memory.model.MemoryType;
import com.github.xuzw.memory.utils.DynamicObject;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月29日 下午10:58:15
 */
public class AllMemories {
    public static final String cmd = "all";

    private String _format(MemoryWrapper memoryWrapper, MemoryRepository memoryRepository) {
        int index = memoryWrapper.getIndex();
        Memory memory = memoryWrapper.getMemory();
        String memoryTypeString = memory.getType();
        MemoryType memoryType = MemoryType.parse(memoryTypeString);
        DynamicObject ext = memoryType.newExtDynamicObject().setRaw(memory.getRaw());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss.SSS");
        String time = dateFormat.format(memory.getTimestamp());
        String locale = memory.getLocale();
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("[%d] %s %s\n", index, memoryType.getName(), ext.getRequiredFields().get(0).getValue()));
        if (MemoryType.over_activity == memoryType) {
            java.util.List<String> raw = memoryRepository.get(ext.get("index").getInt()).getRaw();
            sb.append(MemoryType.new_activity.newExtDynamicObject().setRaw(raw).toJson().toJSONString());
        } else {
            sb.append(ext.toJsonExceptFirstRequiredField().toJSONString());
        }
        sb.append("\n");
        sb.append(String.format("%s %s", locale, time));
        return sb.toString();
    }

    public void execute(MemoryRepository memoryRepository) {
        for (int i = 0; i < memoryRepository.size(); i++) {
            if (i > 0) {
                System.out.println();
            }
            System.out.println(_format(new MemoryWrapper(memoryRepository.get(i), i), memoryRepository));
        }
    }
}
