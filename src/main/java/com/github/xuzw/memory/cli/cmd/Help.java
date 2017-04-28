package com.github.xuzw.memory.cli.cmd;

/**
 * @author 徐泽威 xuzewei_2012@126.com
 * @time 2017年3月30日 上午11:09:49
 */
public class Help {
    public static final String cmd = "help";

    public void execute() {
        System.out.println("正在进行中的活动: memory");
        System.out.println("活动预览: memory list");
        System.out.println("查看全部记忆: memory all");
        System.out.println("版本: memory version");
        System.out.println("帮助: memory help");
        System.out.println("查看这是谁的记忆: memory who");
        System.out.println("设置这是谁的记忆: memory who <名称> [short <简称列表>] [prop <属性键值对>]");
        System.out.println("补全旧活动: memory old <时间> <类型名> <目标名> [source <源名列表>] [effect <效果>]");
        System.out.println("进入场所: memory into <目标名> [source <源名列表>] [effect <效果>]");
        System.out.println("新实体: memory entity <名称> [short <简称列表>] [prop <属性键值对>]");
        System.out.println("新活动: memory act <类型名> <目标名> [source <源名列表>] [effect <效果>]");
        System.out.println("结束活动: memory over <序号>");
    }
}
