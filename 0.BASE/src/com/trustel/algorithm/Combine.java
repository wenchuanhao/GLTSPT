/*
 * Created on Aug 5, 2005
 *
 * 
 */
package com.trustel.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Administrator
 * 
 * 排列组合算法
 */
public class Combine {
    public static Combine getInstance() {
        return new Combine();
    }

    private Combine() {

    }

    /**
     * 预置部分必选项取排列组合
     * <p>
     * 如果必选项大于等于要取的样品个数，返回必选项
     * 
     * @param options
     *            可选项数组
     * @param selectedKeys
     *            必选项主键
     * @param samples
     *            样品个数
     * @return
     */
    public List combinate(java.util.Hashtable options, List selectedKeys,
            int samples) {
        return combinate(options, selectedKeys, null, samples);
    }

    /**
     * 预置部分必选项取排列组合
     * <p>
     * 如果必选项大于等于要取的样品个数，返回必选项
     * 
     * @param options
     *            可选项数组
     * @param selectedKeys
     *            必选项主键
     * @param comp
     *            排序类
     * @param samples
     *            样品个数
     * @return
     */
    public List combinate(java.util.Hashtable options, List selectedKeys,
            Comparator comp, int samples) {
        List list = null;
        java.util.Iterator it = selectedKeys.iterator();
        List selectedItems = new ArrayList();
        
        if (selectedKeys.size() > samples || options.size() < samples)
            return null;

        while (it.hasNext()) {
            Object key = it.next();
            Object selectedItem = options.get(key);
            if (selectedItem != null) {
                selectedItems.add(selectedItem);
                options.remove(key);
            }
        }

        if (selectedKeys.size() >= samples)
            list = selectedItems;
        else {
            Object[] items = getItems(options, comp);

            list = combinate(items, samples - selectedKeys.size());
            if (selectedKeys.size() > 0) {
                it = list.iterator();
                while (it.hasNext()) {
                    List item = (List) it.next();
                    item.addAll(selectedItems);
                }
            }
        }

        return list;
    }

    /**
     * 根据排序类取出可选项
     * 
     * @param options
     *            可选项
     * @param comp 排序类
     * @return
     */
    private Object[] getItems(Hashtable options, Comparator comp) {
        java.util.TreeSet sort = new java.util.TreeSet(comp);
        
        java.util.Enumeration en = options.elements();
        while (en.hasMoreElements()) {
            Object item = en.nextElement();
            sort.add(item);
        }
        
        return sort.toArray();
    }

    /**
     * 取排列组合
     * 
     * @param options
     *            可选项数组
     * @param samples
     *            样品个数
     * @return
     */
    public List combinate(Object[] options, int samples) {
        List list = new ArrayList();
        int[] selected = new int[samples];

        // 样品个数必须小于等于可选项个数
        if (options.length >= samples && samples > 0)
            combinate(list, selected, options, 0, samples);
        else
            return null;

        return list;
    }

    /**
     * 迭代排列组合调用
     * 
     * @param list
     *            排序组合结果
     * @param selected
     *            选择项数组
     * @param options
     *            可选项
     * @param start
     *            开始序号
     * @param count
     *            取样品个数
     */
    private void combinate(List list, int[] selected, Object[] options,
            int start, int count) {
        for (int i = start; i <= options.length - count; i++) {
            selected[selected.length - count] = i;
            if (count > 1)
                combinate(list, selected, options, i + 1, count - 1);
            else {
                list.add(getSelected(options, selected));
            }
        }
    }

    /**
     * 根据选项序号生成选项
     * 
     * @param options
     *            可选项
     * @param selected
     *            选择项序号数组
     * @return
     */
    private List getSelected(Object[] options, int[] selected) {
        List item = new ArrayList();

        for (int i = 0; i < selected.length; i++)
            item.add(options[selected[i]]);

        return item;
    }
}
