package org.trustel.service.id;

import java.util.HashMap;

import org.hibernate.Session;
import org.trustel.common.Utils;
import org.trustel.service.IEnterpriseService;
import org.trustel.service.id.a.NextCodeAService;
import org.trustel.service.id.a.NextCodeService;
import org.trustel.system.SystemLogger;

public class SequenceUtils {
	private static HashMap<String, EnhancedSequenceUtility> sequences = new HashMap<String, EnhancedSequenceUtility>();

	/**
	 * 获取指定长整型序列值
	 * 
	 * @param service
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @param minValue
	 *            序列开始值
	 * @param maxValue
	 *            序列最大值，达到最大值后会重新开始编号
	 * @return 指定名称序列值
	 */
	public static long getNextLongValue(IEnterpriseService service,
			String sequenceName, int minValue, long maxValue) {
		return service._getNextCode(sequenceName, minValue, maxValue);
	}

	/**
	 * 获取指定长整型序列值
	 * 
	 * @param Session
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @param minValue
	 *            序列开始值
	 * @param maxValue
	 *            序列最大值，达到最大值后会重新开始编号
	 * @return 指定名称序列值
	 */
	public static long getNextLongValue(Session session, String sequenceName,
			int minValue, long maxValue) {
		EnhancedSequenceUtility sequence = getSequenceByName(sequenceName);
		try {
			return sequence.getNextLongValue(new NextCodeService(session),
					sequenceName, minValue, maxValue);
		} catch (Exception e) {
			SystemLogger.error(e.getMessage());
			SystemLogger.error(e);
		}
		return minValue;
	}

	/**
	 * 获取增强型序列
	 * 
	 * @param service
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @return 下一个序列值（16进制字符串）。7位流水号和5位随机数构成。
	 */
	public static String getNext7Plus5Code(IEnterpriseService service,
			String sequenceName) {
		EnhancedSequenceUtility sequence = getSequenceByName(sequenceName);
		try {
			return sequence.getNext7Plus5Code(new NextCodeAService(service),
					sequenceName);
		} catch (Exception e) {
			SystemLogger.error(e.getMessage());
			SystemLogger.error(e);
		}
		return "00000000FFFF";
	}

	/**
	 * 获取增强型序列
	 * 
	 * @param session
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @return 下一个序列值（16进制字符串）。7位流水号和5位随机数构成。
	 */
	public static String getNext7Plus5Code(Session session, String sequenceName) {
		EnhancedSequenceUtility sequence = getSequenceByName(sequenceName);
		try {
			return sequence.getNext7Plus5Code(new NextCodeService(session),
					sequenceName);
		} catch (Exception e) {
			SystemLogger.error(e.getMessage());
			SystemLogger.error(e);
		}
		return "00000000FFFF";
	}

	/**
	 * 获取长整型序列
	 * 
	 * @param service
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @return 序列值
	 */
	public static long getNextLongValue(IEnterpriseService service,
			String sequenceName) {
		EnhancedSequenceUtility sequence = getSequenceByName(sequenceName);
		try {
			return sequence.getNextLongValue(new NextCodeAService(service),
					sequenceName);
		} catch (Exception e) {
			SystemLogger.error(e.getMessage());
			SystemLogger.error(e);
		}
		return 10000;
	}

	/**
	 * 获取长整型序列
	 * 
	 * @param session
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @return 序列值
	 */
	public static long getNextLongValue(Session session, String sequenceName) {
		EnhancedSequenceUtility sequence = getSequenceByName(sequenceName);
		try {
			return sequence.getNextLongValue(new NextCodeService(session),
					sequenceName);
		} catch (Exception e) {
			SystemLogger.error(e.getMessage());
			SystemLogger.error(e);
		}
		return 10000;
	}

	/**
	 * 获取指定长度的序列值，当长度不够时左补零
	 * 
	 * @param service
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @param fixLength
	 *            固定长度
	 * @return 指定长度序列
	 */
	public static String getNextCode(IEnterpriseService service,
			String sequenceName, int fixLength) {
		long value = getNextLongValue(service, sequenceName);
		String ret = Long.toHexString(value).toUpperCase();
		return Utils.fixLength(ret, fixLength);
	}

	/**
	 * 获取指定长度的序列值，当长度不够时左补零
	 * 
	 * @param session
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @param fixLength
	 *            固定长度
	 * @return 指定长度序列
	 */
	public static String getNextCode(Session session, String sequenceName,
			int fixLength) {
		long value = getNextLongValue(session, sequenceName);
		String ret = Long.toHexString(value).toUpperCase();
		return Utils.fixLength(ret, fixLength);
	}

	/**
	 * 获取指定长度序列
	 * 
	 * @param service
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @param fixLength
	 *            固定长度
	 * @param minValue
	 *            序列开始值
	 * @param maxValue
	 *            序列最大值
	 * @return 指定长度序列
	 */
	public static String getNextCode(IEnterpriseService service,
			String sequenceName, int fixLength, int minValue, long maxValue) {
		long value = getNextLongValue(service, sequenceName, minValue, maxValue);
		String ret = Long.toHexString(value).toUpperCase();
		return Utils.fixLength(ret, fixLength);
	}

	/**
	 * 获取指定长度序列
	 * 
	 * @param session
	 *            数据服务
	 * @param sequenceName
	 *            序列名称
	 * @param fixLength
	 *            固定长度
	 * @param minValue
	 *            序列开始值
	 * @param maxValue
	 *            序列最大值
	 * @return 指定长度序列
	 */
	public static String getNextCode(Session session, String sequenceName,
			int fixLength, int minValue, long maxValue) {
		long value = getNextLongValue(session, sequenceName, minValue, maxValue);
		String ret = Long.toHexString(value).toUpperCase();
		return Utils.fixLength(ret, fixLength);
	}

	private synchronized static EnhancedSequenceUtility getSequenceByName(
			String name) {
		EnhancedSequenceUtility item;
		name = name.toLowerCase();
		if (sequences.containsKey(name))
			item = sequences.get(name);
		else {
			item = new EnhancedSequenceUtility();
			sequences.put(name, item);
		}
		return item;
	}

}
