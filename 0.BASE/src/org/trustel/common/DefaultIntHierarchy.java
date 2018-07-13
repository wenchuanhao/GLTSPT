package org.trustel.common;

/**
 * 
 * 类 名：等级模型[INT]
 * 
 * 版 本：0.0.0.1
 * 
 * 设 计：万志勇
 * 
 * 日 期：2011-03-21
 * 
 * 
 * 描 述：使用整型字段编码的等级结构，该结构类似于OLAP中的维表设计原则，通过空间换取速度
 * 
 */

public class DefaultIntHierarchy implements IIntHierarchy {
	/**
	 * 编码
	 */
	private int id = 0;

	/**
	 * 上级节点编码
	 */
	private int parentId = 0;

	/**
	 * 所在级别
	 */
	private int rank = 0;

	/**
	 * 名称
	 */
	private String title;

	/**
	 * 一级编码
	 */
	private int rank1 = 0;

	/**
	 * 二级编码
	 */
	private int rank2 = 0;

	/**
	 * 三级编码
	 */
	private int rank3 = 0;

	/**
	 * 四级编码
	 */
	private int rank4 = 0;

	/**
	 * 五级编码
	 */
	private int rank5 = 0;

	/**
	 * 六级编码
	 */
	private int rank6 = 0;

	/**
	 * 七级编码
	 */
	private int rank7 = 0;

	/**
	 * 八级编码
	 */
	private int rank8 = 0;

	/**
	 * 九级编码
	 */
	private int rank9 = 0;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 语言
	 */
	private String lang;

	private int[] ranks = null;

	public int getChildrenCount() {
		return 0;
	}

	public String getCode() {
		return String.valueOf(id);
	}

	/**
	 * 
	 * @return 描述(description<--description)
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @return 编码(id<--id)
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return 语言(lang<--lang)
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * 
	 * @return 上级节点编码(parentId<--parentId)
	 */
	public int getParentId() {
		// TODO:parent_id
		return parentId;
	}

	/**
	 * 
	 * @return 所在级别(rank<--rank)
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * 
	 * @return 一级编码(rank1<--rank1)
	 */
	public int getRank1() {
		return rank1;
	}

	/**
	 * 
	 * @return 二级编码(rank2<--rank2)
	 */
	public int getRank2() {
		return rank2;
	}

	/**
	 * 
	 * @return 三级编码(rank3<--rank3)
	 */
	public int getRank3() {
		return rank3;
	}

	/**
	 * 
	 * @return 四级编码(rank4<--rank4)
	 */
	public int getRank4() {
		return rank4;
	}

	/**
	 * 
	 * @return 五级编码(rank5<--rank5)
	 */
	public int getRank5() {
		return rank5;
	}

	/**
	 * 
	 * @return 六级编码(rank6<--rank6)
	 */
	public int getRank6() {
		return rank6;
	}

	/**
	 * 
	 * @return 七级编码(rank7<--rank7)
	 */
	public int getRank7() {
		return rank7;
	}

	/**
	 * 
	 * @return 八级编码(rank8<--rank8)
	 */
	public int getRank8() {
		return rank8;
	}

	/**
	 * 
	 * @return 九级编码(rank9<--rank9)
	 */
	public int getRank9() {
		return rank9;
	}

	public String getTip() {
		return description;
	}

	/**
	 * 
	 * @return 名称(title<--title)
	 */
	public String getTitle() {
		return title;
	}

	public String getUcode() {
		return String.valueOf(parentId);
	}

	public boolean isHomologous(IIntHierarchy hierarchy) {
		if (hierarchy == null)
			return false;
		if (ranks == null)
			ranks = new int[] { rank1, rank2, rank3, rank4, rank5, rank6,
					rank7, rank8, rank9 };
		return ranks[hierarchy.getRank() - 1] == hierarchy.getId();
	}

	/**
	 * 设置描述
	 * 
	 * @param 描述
	 *            (description-->description)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 设置编码
	 * 
	 * @param 编码
	 *            (id-->id)
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 设置语言
	 * 
	 * @param 语言
	 *            (lang-->lang)
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * 设置上级节点编码
	 * 
	 * @param 上级节点编码
	 *            (parentId-->parentId)
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * 设置所在级别
	 * 
	 * @param 所在级别
	 *            (rank-->rank)
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * 设置一级编码
	 * 
	 * @param 一级编码
	 *            (rank1-->rank1)
	 */
	public void setRank1(int rank1) {
		this.rank1 = rank1;
	}

	/**
	 * 设置二级编码
	 * 
	 * @param 二级编码
	 *            (rank2-->rank2)
	 */
	public void setRank2(int rank2) {
		this.rank2 = rank2;
	}

	/**
	 * 设置三级编码
	 * 
	 * @param 三级编码
	 *            (rank3-->rank3)
	 */
	public void setRank3(int rank3) {
		this.rank3 = rank3;
	}

	/**
	 * 设置四级编码
	 * 
	 * @param 四级编码
	 *            (rank4-->rank4)
	 */
	public void setRank4(int rank4) {
		this.rank4 = rank4;
	}

	/**
	 * 设置五级编码
	 * 
	 * @param 五级编码
	 *            (rank5-->rank5)
	 */
	public void setRank5(int rank5) {
		this.rank5 = rank5;
	}

	/**
	 * 设置六级编码
	 * 
	 * @param 六级编码
	 *            (rank6-->rank6)
	 */
	public void setRank6(int rank6) {
		this.rank6 = rank6;
	}

	/**
	 * 设置七级编码
	 * 
	 * @param 七级编码
	 *            (rank7-->rank7)
	 */
	public void setRank7(int rank7) {
		this.rank7 = rank7;
	}

	/**
	 * 设置八级编码
	 * 
	 * @param 八级编码
	 *            (rank8-->rank8)
	 */
	public void setRank8(int rank8) {
		this.rank8 = rank8;
	}

	/**
	 * 设置九级编码
	 * 
	 * @param 九级编码
	 *            (rank9-->rank9)
	 */
	public void setRank9(int rank9) {
		this.rank9 = rank9;
	}

	/**
	 * 设置名称
	 * 
	 * @param 名称
	 *            (title-->title)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public void inherit(IIntHierarchy root) {
		rank1 = -1;
		rank2 = -1;
		rank3 = -1;
		rank4 = -1;
		rank5 = -1;
		rank6 = -1;
		rank7 = -1;
		rank8 = -1;
		rank9 = -1;
		if (root == null) {
			rank = 1;
			parentId = 0;
		} else {
			rank = root.getRank() + 1;
			parentId = root.getId();
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
}
