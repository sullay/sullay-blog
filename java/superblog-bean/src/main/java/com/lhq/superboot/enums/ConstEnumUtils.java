package com.lhq.superboot.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 常量枚举类
 * @author: lct
 * @date: 2019年4月17日 上午11:13:34
 */
public class ConstEnumUtils {

	public static final String tokenOrderFilePath = "/usr/local/superboot/static/files";

	public static final String imageFilePath = "/usr/local/superboot/static/images";

	/**
	 * @Description: 渠道枚举类
	 * @author: lct
	 * @date: 2019年5月16日 上午11:23:25
	 */
	public static enum ENUM_CHANNEL {
		HT("HT"), PC("PC"), XCX("XCX");

		private String value;

		private ENUM_CHANNEL(String value) {
			this.value = value;
		}

		public String value() {
			return this.value;
		}
	}

	/**
	 * @Description: 是否可用 NO-0-禁用 YES-1-启用
	 * @author: lct
	 * @date: 2019年4月17日 上午11:14:09
	 */
	public static enum IS_ENABLED {
		NO(0), YES(1);

		private int value;

		private IS_ENABLED(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * @Description: 是否删除 NO-0-未删除 YES-1-已删除
	 * @author: lct
	 * @date: 2019年4月17日 上午11:14:59
	 */
	public static enum IS_DELETE {
		NO(0), YES(1);

		private int value;

		private IS_DELETE(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * @Description: 是否分享 NO-0-未分享 YES-1-已分享
	 * @author: lct
	 * @date: 2019年4月17日 下午4:02:38
	 */
	public static enum IS_SHARE {
		NO(0), YES(1);

		private int value;

		private IS_SHARE(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * @Description: 是否使用 NO-0-未使用 YES-1-已使用 LOSE-2-过期
	 * @author: lct
	 * @date: 2019年4月18日 下午1:38:23
	 */
	public static enum IS_USED {
		NO(0), YES(1), LOSE(2);

		private int value;

		private IS_USED(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * @Description: 剩余 NO-0-无剩余/全部 YES-1-剩余
	 * @author: lct
	 * @date: 2019年4月25日 下午7:10:08
	 */
	public static enum IS_REMAINING {
		NO(0), YES(1);

		private int value;

		private IS_REMAINING(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * @Description: token交易状态
	 * @author: lct
	 * @date: 2019年4月18日 下午2:12:57
	 */
	public static enum TOKEN_TRADE_TYPE {
		IN(1, "收入"), OUT(2, "支出");

		private int key;
		private String value;

		private TOKEN_TRADE_TYPE(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public int key() {
			return this.key;
		}

		public String value() {
			return this.value;
		}

		public static String getValue(Integer key) {
			TOKEN_TRADE_TYPE[] enums = values();
			for (TOKEN_TRADE_TYPE entity : enums) {
				if (entity.key() == key) {
					return entity.value;
				}
			}
			return null;
		}
	}

	/**
	 * @Description: token交易描述
	 * @author: lct
	 * @date: 2019年4月19日 下午4:12:13
	 */
	public static enum TOKEN_TRADE_DESC {
		BUY("1-1", "购买"), RECEIVE("1-2", "收入"), GIVE("2-1", "赠送"), EXCHANGE("2-2", "兑换");

		private String key;
		private String value;

		private TOKEN_TRADE_DESC(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String key() {
			return this.key;
		}

		public String value() {
			return this.value;
		}

		public static String getValue(String key) {
			TOKEN_TRADE_DESC[] enums = values();
			for (TOKEN_TRADE_DESC entity : enums) {
				if (entity.key().equals(key)) {
					return entity.value;
				}
			}
			return null;
		}
	}

	public static enum TOKEN_ORDER_STATUS {
		NOPASS(-1, "审核不通过"), WAIT(0, "待审核"), PASS(1, "审核通过/发出"), BACK(2, "回收");

		private int key;
		private String value;

		private TOKEN_ORDER_STATUS(int key, String value) {
			this.key = key;
			this.value = value;
		}

		public int key() {
			return this.key;
		}

		public String value() {
			return this.value;
		}

		public static String getValue(int key) {
			TOKEN_ORDER_STATUS[] enums = values();
			for (TOKEN_ORDER_STATUS entity : enums) {
				if (entity.key() == key) {
					return entity.value;
				}
			}
			return null;
		}
	}

	/**
	 * @Description: 通知是否已读
	 * @author: lihaoqi
	 * @date: 2019年7月05日 下午4:10:08
	 */
	public static enum IS_READ {
		NO(0), YES(1);

		private int value;

		private IS_READ(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	/**
	 * @Description: 支付是否生效
	 * @author: lihaoqi
	 * @date: 2019年7月08日 下午4:10:08
	 */
	public static enum IS_VAILD {
		NO(0, "已退款"), YES(1, "已支付");

		private int value;
		private String code;

		private IS_VAILD(int value, String code) {
			this.value = value;
			this.code = code;
		}

		public int value() {
			return this.value;
		}

		public static String getValue(int key) {
			IS_VAILD[] enums = values();
			for (IS_VAILD entity : enums) {
				if (entity.value() == key) {
					return entity.code;
				}
			}
			return null;
		}
	}

	public static enum GOODS_CATEGORIES {
		DEFAULT(0), CARD(1), ENTITY(2);

		private int value;

		private GOODS_CATEGORIES(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	public static enum IS_GROUP {
		NO(0), YES(1);

		private int value;

		private IS_GROUP(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	public static enum IS_ONLINE {
		NO(0), YES(1);

		private int value;

		private IS_ONLINE(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}

		public static Integer getValue(int key) {
			List<Integer> list = Arrays.asList(values()).stream().filter(v -> v.value == key).map(v -> v.value)
					.collect(Collectors.toList());
			return (list == null || list.size() < 1) ? null : list.get(0);
		}
	}

	/**
	 * @Description: 是否收入
	 * @author: lihaoqi
	 * @date: 2019年7月8日 下午5:10:38
	 */
	public static enum IS_INCOME {
		NO(0, "支出"), YES(1, "收入");

		private int value;
		private String code;

		private IS_INCOME(int value, String code) {
			this.value = value;
			this.code = code;
		}

		public int value() {
			return this.value;
		}

		public static String getValue(int key) {
			IS_INCOME[] enums = values();
			for (IS_INCOME entity : enums) {
				if (entity.value() == key) {
					return entity.code;
				}
			}
			return null;
		}
	}

	/**
	 * @Description: 是否已经处理
	 * @author: lihaoqi
	 * @date: 2019年7月10日 下午11:10:38
	 */
	public static enum IS_HANDLE {
		NO(0), HANDLING(1), YES(2);

		private int value;

		private IS_HANDLE(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
	
	/**
	 * @Description: 是否默认
	 * @author: lihaoqi
	 * @date: 2019年7月10日 下午11:10:38
	 */
	public static enum IS_DEFAULT {
		NO(0), YES(1);

		private int value;

		private IS_DEFAULT(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
	
	public static enum IS_INVALID {
		NO(0), YES(1);

		private int value;

		private IS_INVALID(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
	
	public static enum ORDER_STATUS {
		FAILURE(0), NOTPAY(1), PAYING(2), FINISH(3);

		private int value;

		private ORDER_STATUS(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
	
	public static enum PAYMENT_STATUS {
		FAILD(-1), NOTPAY(0), SUCCESS(1);

		private int value;

		private PAYMENT_STATUS(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
	
	public static enum CARD_ORDER_STATUS {
		FAILURE(-1), NOTSEND(0), SENDING(1), FINISH(2);

		private int value;

		private CARD_ORDER_STATUS(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}
	
	public static enum ENTITY_ORDER_STATUS {
		FAILURE(-1), NOTSEND(0), SENDING(1), FINISH(2);

		private int value;

		private ENTITY_ORDER_STATUS(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

}
