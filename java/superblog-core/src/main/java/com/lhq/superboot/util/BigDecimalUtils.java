package com.lhq.superboot.util;

import java.math.BigDecimal;

/**
 * @Description bigDecimal(金额)操作
 *
 * @author: lihaoqi
 * @date: 2019年7月16日 下午1:55:17
 * @version: v1.0.0
 */
public class BigDecimalUtils {

	/** 金额转分时的换算比例 **/
	private static final int MONEY_QUANTILE = 100;

	/** 金额保留小数位数 **/
	private static final int MONEY_SCALE = 2;

	/**
	 * @Description: Long(分位)类型的money转化为BigDecimal(个位)
	 *
	 * @param money
	 * @return
	 */
	public static BigDecimal toDecimal(Long money) {
		BigDecimal moneyDe = new BigDecimal(money);
		BigDecimal quantileDe = new BigDecimal(MONEY_QUANTILE);
		return moneyDe.divide(quantileDe, MONEY_SCALE, BigDecimal.ROUND_UP);
	}

	/**
	 * @Description: BigDecimal(个位)类型的money转化为Long(分位)
	 *
	 * @param money
	 * @return
	 */
	public static Long toLong(BigDecimal money) {
		BigDecimal quantileDe = new BigDecimal(MONEY_QUANTILE);
		return money.multiply(quantileDe).longValue();
	}
	
}
