package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：自动回复信息
 * 
 * @author Dick.guo
 * @date 2015年07月30日
 */
@Document
public class MemberBalanceIncomeRank extends BaseModel {
	/**
	 * 排名时间
	 */
	private Date rankDate;

	/**
	 * 排名
	 */
	private Integer rank;

	/**
	 * @return the rankDate
	 */
	public Date getRankDate() {
		return rankDate;
	}

	/**
	 * @param rankDate
	 *            the rankDate to set
	 */
	public void setRankDate(Date rankDate) {
		this.rankDate = rankDate;
	}

	/**
	 * @return the rank
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
}
