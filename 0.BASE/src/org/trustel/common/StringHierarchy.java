package org.trustel.common;

/**
 * 
 * 类 名：字符型层级结构
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-03-21 17:52
 * 
 * 数据源：
 * 
 * 描 述：
 * 
 */

public class StringHierarchy implements IStringHierarchy {
	/**
	 * 编码 (映射code字段)
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 层级
	 */
	private int rank = 0;

	/**
	 * 上级编码
	 */
	private String ucode;

	/**
	 * 一级编码
	 */
	private String rank1;

	/**
	 * 二级编码
	 */
	private String rank2;

	/**
	 * 三级编码
	 */
	private String rank3;

	/**
	 * 四级编码
	 */
	private String rank4;

	/**
	 * 五级编码
	 */
	private String rank5;

	/**
	 * 六级编码
	 */
	private String rank6;

	/**
	 * 七级编码
	 */
	private String rank7;

	/**
	 * 八级编码
	 */
	private String rank8;

	/**
	 * 九级编码
	 */
	private String rank9;

	/**
	 * 
	 * @return 编码(id<--code)
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return 名称(name<--name)
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return 层级(rank<--rank)
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * 
	 * @return 上级编码(ucode<--ucode)
	 */
	public String getUcode() {
		return ucode;
	}

	/**
	 * 
	 * @return 一级编码(rank1<--rank1)
	 */
	public String getRank1() {
		return rank1;
	}

	/**
	 * 
	 * @return 二级编码(rank2<--rank2)
	 */
	public String getRank2() {
		return rank2;
	}

	/**
	 * 
	 * @return 三级编码(rank3<--rank3)
	 */
	public String getRank3() {
		return rank3;
	}

	/**
	 * 
	 * @return 四级编码(rank4<--rank4)
	 */
	public String getRank4() {
		return rank4;
	}

	/**
	 * 
	 * @return 五级编码(rank5<--rank5)
	 */
	public String getRank5() {
		return rank5;
	}

	/**
	 * 
	 * @return 六级编码(rank6<--rank6)
	 */
	public String getRank6() {
		return rank6;
	}

	/**
	 * 
	 * @return 七级编码(rank7<--rank7)
	 */
	public String getRank7() {
		return rank7;
	}

	/**
	 * 
	 * @return 八级编码(rank8<--rank8)
	 */
	public String getRank8() {
		return rank8;
	}

	/**
	 * 
	 * @return 九级编码(rank9<--rank9)
	 */
	public String getRank9() {
		return rank9;
	}

	/**
	 * 设置编码
	 * 
	 * @param 编码
	 *            (id-->code)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置名称
	 * 
	 * @param 名称
	 *            (name-->name)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置层级
	 * 
	 * @param 层级
	 *            (rank-->rank)
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * 设置上级编码
	 * 
	 * @param 上级编码
	 *            (ucode-->ucode)
	 */
	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

	/**
	 * 设置一级编码
	 * 
	 * @param 一级编码
	 *            (rank1-->rank1)
	 */
	public void setRank1(final String rank1) {
		this.rank1 = rank1;
	}

	/**
	 * 设置二级编码
	 * 
	 * @param 二级编码
	 *            (rank2-->rank2)
	 */
	public void setRank2(String rank2) {
		this.rank2 = rank2;
	}

	/**
	 * 设置三级编码
	 * 
	 * @param 三级编码
	 *            (rank3-->rank3)
	 */
	public void setRank3(String rank3) {
		this.rank3 = rank3;
	}

	/**
	 * 设置四级编码
	 * 
	 * @param 四级编码
	 *            (rank4-->rank4)
	 */
	public void setRank4(String rank4) {
		this.rank4 = rank4;
	}

	/**
	 * 设置五级编码
	 * 
	 * @param 五级编码
	 *            (rank5-->rank5)
	 */
	public void setRank5(String rank5) {
		this.rank5 = rank5;
	}

	/**
	 * 设置六级编码
	 * 
	 * @param 六级编码
	 *            (rank6-->rank6)
	 */
	public void setRank6(String rank6) {
		this.rank6 = rank6;
	}

	/**
	 * 设置七级编码
	 * 
	 * @param 七级编码
	 *            (rank7-->rank7)
	 */
	public void setRank7(String rank7) {
		this.rank7 = rank7;
	}

	/**
	 * 设置八级编码
	 * 
	 * @param 八级编码
	 *            (rank8-->rank8)
	 */
	public void setRank8(String rank8) {
		this.rank8 = rank8;
	}

	/**
	 * 设置九级编码
	 * 
	 * @param 九级编码
	 *            (rank9-->rank9)
	 */
	public void setRank9(String rank9) {
		this.rank9 = rank9;
	}

	public void inherit(IStringHierarchy root) {
		if (root == null) {
			rank = 1;
			ucode = "";
		} else {
			rank = root.getRank() + 1;
			ucode = root.getCode();
			rank1 = root.getRank1();
			rank2 = root.getRank2();
			rank3 = root.getRank3();
			rank4 = root.getRank4();
			rank5 = root.getRank5();
			rank6 = root.getRank6();
			rank7 = root.getRank7();
			rank8 = root.getRank8();
			rank9 = root.getRank9();
		}
		switch (rank) {
		case 1:
			rank1 = id;
			break;
		case 2:
			rank2 = id;
			break;
		case 3:
			rank3 = id;
			break;
		case 4:
			rank4 = id;
			break;
		case 5:
			rank5 = id;
			break;
		case 6:
			rank6 = id;
			break;
		case 7:
			rank7 = id;
			break;
		case 8:
			rank8 = id;
			break;
		case 9:
			rank9 = id;
			break;

		}
	}

	public String[] getRanks() {
		return new String[] { rank1, rank2, rank3, rank4, rank4, rank6, rank7,
				rank8, rank9 };
	}

	public int getChildrenCount() {
		return 0;
	}

	public String getCode() {
		return id;
	}

	public String getTip() {
		return null;
	}

	public String getTitle() {
		return name;
	}

}
