package com.trustel.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator 比较两组对象之间的区别
 *         <p>
 *         两组对象按主键排序混排在一起
 */
public class GroupCompare {
	private ObjectComparator comparator;

	private List result;

	private GroupCompare() {

	}

	public GroupCompare(ObjectComparator comparator) {
		this.comparator = comparator;
		result = new ArrayList();
	}

	/**
	 * 对外调用比较函数
	 * 
	 * @param items
	 *            对象数组
	 * @param key
	 *            对象的主键
	 * @return
	 */
	public List compare(Iterator items, Object baseline, Object define) {
		if (items.hasNext())
			compare(items.next(), items, baseline, define);

		return result;
	}

	/**
	 * 比较函数
	 * 
	 * @param source
	 *            从数组取出的对象
	 * @param items
	 *            数组
	 * @param baseline
	 *            基准标志
	 * @param define
	 *            对象主键
	 * @param result
	 *            结果集
	 */
	public void compare(Object source, Iterator items, Object baseline,
			Object define) {
		if (items.hasNext()) {
			Object compare = items.next();

			if (comparator.isKeyEqual(source, compare, define)) {
				if (comparator.fromSameGroup(source, compare)) {
					comparator.toDuplicate(result, source, compare, define);

					compare(compare, items, baseline, define);
				} else {
					if (!comparator.isEqual(source, compare, define))
						comparator.toDifference(result, source, compare,
								baseline, define);

					if (items.hasNext())
						compare(items.next(), items, baseline, define);
				}
			} else {
				comparator.toExtra(result, source, baseline, define);

				compare(compare, items, baseline, define);
			}
		} else
			comparator.toExtra(result, source, baseline, define);
	}
}
