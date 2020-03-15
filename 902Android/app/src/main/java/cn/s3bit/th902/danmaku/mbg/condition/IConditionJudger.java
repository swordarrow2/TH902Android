package cn.s3bit.th902.danmaku.mbg.condition;

import cn.s3bit.mbgparser.event.*;
import cn.s3bit.mbgparser.event.Condition.*;
import cn.s3bit.th902.danmaku.mbg.*;

public interface IConditionJudger<T extends AbstractMBGComponent<?>> {
	public boolean judgeCondition(T obj, Condition condition, int scaledTime);
	public boolean judgeExpr(T obj, Expression expression, int scaledTime);
}
