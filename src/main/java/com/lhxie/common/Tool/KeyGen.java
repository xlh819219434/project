package com.lhxie.common.Tool;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.zjft.common.dao.IBaseDao;

public class KeyGen {
	
	/**
	 * 生成随机长整数（最大16位）
	 * @return
	 */
	public static long createLongId() {
		UUID uuid = UUID.randomUUID();
		return Math.abs(uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits()) >> 10;
	}
	
	/**
	 * 生成随机的顺序主键（24字符）
	 * @return
	 */
	public static String createRandomId() {
		return ObjectId.get().toHexString();
	}
	
	/**
	 * 生成UUID（32字符）
	 * @return
	 */
	public static String createUuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	/**
	 * 生成顺序主键
	 * @param lastId
	 * @param length
	 * @return
	 */
	public static String createSeqId(String lastId, int length) {
		long id = 0;
		if (!StringTool.isBlank(lastId)) {
			id = Long.parseLong(lastId);
		}
		id++;
		if (length > 0) {
			return String.format("%1$0"+length+"d", id);
		} else {
			return String.valueOf(id);
		}
	}
	
	/**
	 * 生成流程编号
	 * @param dao Dao对象
	 * @param vo  流程表对应的VO对象（需用注解定义表名和主键字段）
	 * @param prefix 编号的前缀（一般为4个字母）
	 * @return
	 */
	public static String createFormId(@SuppressWarnings("rawtypes") IBaseDao dao, Object vo, String prefix) {
		Class<?> classObject = vo.getClass();
		Method methods[] = classObject.getDeclaredMethods();
		String fieldName = null;
		Table table = classObject.getAnnotation(Table.class);
		if (table != null) {
			for (Method m : methods) {
				Id id = m.getAnnotation(Id.class);
				if (id != null) {
					Column col = m.getAnnotation(Column.class);
					if (col == null || StringTool.isBlank(col.name())) {
						fieldName = m.getName().substring(3);
					} else {
						fieldName = col.name();
					}
					break;
				}
			}
			if (fieldName != null) {
				String lead = prefix + DateTool.formatDateTime(DateTool.getSysDateTime(), "yyMMdd");
				String sql = "SELECT MAX(" + fieldName + ") AS " + fieldName + " FROM " + table.name()
						+ " WHERE " + fieldName + " LIKE '" + lead + "%'";
				@SuppressWarnings("unchecked")
				List<String> lastIds = dao.findBySql(sql);
				if (lastIds != null && lastIds.size() > 0) {
					String lastId = lastIds.get(0);
					if (lastId != null) {
						return lead + createSeqId(lastId.substring(lead.length()), 4);
					} else {
						return lead + "0001";
					}
				} else {
					return lead + "0001";
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据上一个formid生成下一个，一般用于批量生成
	 * @param formid
	 * @return
	 */
	public static String createNextFormId(String formid) {
		if (formid == null || formid.length() < 4)
			return null;
		// 取最后四位生成下一个顺序号
		String before = formid.substring(0, formid.length() - 4);
		
		String id = formid.substring(formid.length() - 4);
		String nextid = createSeqId(id, 4);
		return before + nextid;

	}

	/**
	 * 生成包括0到不包括n的随机数
	 */
	public static int random(int n) {
		try {
			Random random = new Random();
			return Math.abs(random.nextInt()) % n;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
