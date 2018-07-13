package com.trustel.algorithm;

import java.util.HashMap;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class Subtotal extends Thread {
	private static Subtotal singleton;
	/**
	 * 待统计记录
	 */
	private List items;
	/**
	 * 统计记录MAP
	 */
	private HashMap nodes;
	/**
	 * 统计结果MAP
	 */
	private HashMap subtotals;
	/**
	 * 统计结束标志(用于后台统计)
	 */
	private boolean finished;

	public static Subtotal getInstance(List items) throws RuntimeException {
		if (singleton == null)
			singleton = new Subtotal(items);
		else
			singleton.items = items;
		
		singleton.finished = false;

		return singleton;
	}

	private Subtotal() {

	}

	private Subtotal(List items) {
		this.items = items;
		nodes = new HashMap();
		subtotals = new HashMap();

		for (int i = 0; i < items.size(); i++)
			nodes.put(((Node) items.get(i)).getId(), items.get(i));
	}

	public void run() {
		calculate();
	}

	public boolean getFinished() {
		return finished;
	}

	/**
	 * 取小计结果
	 * 
	 * @return
	 */
	public HashMap getSubtotals() {
		return subtotals;
	}

	/**
	 * 计算
	 */
	public void calculate() {
		for (int i = 0; i < items.size(); i++) {
			Node item = (Node) items.get(i);
			subtotal(item.getId(), item);
		}

		finished = true;
	}

	private void subtotal(String id, Node item) {
		if (subtotals.get(id) != null)
			((Summary) subtotals.get(id)).subtotal(item.getSumary());
		else
			subtotals.put(id, item.getSumary());

		Node node = (Node) nodes.get(id);
		if (node.getParentId() != null)
			subtotal(node.getParentId(), node);

	}
}
