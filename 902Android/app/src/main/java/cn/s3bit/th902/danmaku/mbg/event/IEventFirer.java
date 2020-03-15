package cn.s3bit.th902.danmaku.mbg.event;

import cn.s3bit.mbgparser.event.*;
import cn.s3bit.th902.danmaku.mbg.*;

public interface IEventFirer<T extends AbstractMBGComponent<?>> {
	public void fireDataOperation(T obj, MBGEventTask task);
	public void fireCommand(T obj, CommandAction action);
}
