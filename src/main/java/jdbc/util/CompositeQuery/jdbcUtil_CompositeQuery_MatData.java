/*
 *  1. 萬用複合查詢-可由客戶端隨意增減任何想查詢的欄位
 *  2. 為了避免影響效能:
 *        所以動態產生萬用SQL的部份,本範例無意採用MetaData的方式,也只針對個別的Table自行視需要而個別製作之
 * */

package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_MatData {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("ma_status".equals(columnName)) // 用於其他
			aCondition = columnName + " = " + value;

		else if ("ma_price_down".equals(columnName))
			aCondition = "ma_price between " + value + " and ";

		else if ("ma_price_up".equals(columnName))
			aCondition = value;

		else if ("ma_no".equals(columnName) || "ma_name".equals(columnName) || "ma_ty_no".equals(columnName)) // 用於varchar
			aCondition = columnName + " like '%" + value + "%'";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		
		Set<String> keys = map.keySet();
		for(String key : keys) {
			if(key.equals("ma_price_down") && (map.get(key)[0]).length() == 0) {
				map.put("ma_price_down", new String[] { "0" });	
			}
		}
		
		for(String key : keys) {
			if(key.equals("ma_price_up") && (map.get(key)[0]).length() == 0) {
				map.put("ma_price_up", new String[] { "9999" });	
			}
		}

		StringBuffer whereCondition = new StringBuffer();
		String sb1 = null;
		String sb2 = null;
		int times = 0;
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if ((key.equals("ma_price_down")) || (key.equals("ma_price_up"))) {
					
					count--;
					if (key.equals("ma_price_down")) {
						sb1 = aCondition;
						times++;
					}

					if (key.equals("ma_price_up")) {
						sb2 = aCondition;
						times++;
					}

					if (times == 2) {
						aCondition = sb1 + sb2;
						
						if (count == 0)
							whereCondition.append(" where " + aCondition);
						else
							whereCondition.append(" and " + aCondition);	
					}
				} else {

					if (count == 1)
						whereCondition.append(" where " + aCondition);
					else
						whereCondition.append(" and " + aCondition);

					System.out.println("有送出查詢資料的欄位數count = " + count);
				}
			}
		}

		System.out.println(whereCondition.toString());
		return whereCondition.toString();
	}
}

//	public static void main(String argv[]) {
//
//		// 配合 req.getParameterMap()方法 回傳
//		// java.util.Map<java.lang.String,java.lang.String[]> 之測試
//		Map<String, String[]> map = new TreeMap<String, String[]>();
//		map.put("ma_no", new String[] { "L0001" });
//		map.put("ma_ty_no", new String[] { "T0001" });
//		map.put("ma_name", new String[] { "馬克杯" });
//		map.put("ma_price_down", new String[] { "0" });
//		map.put("ma_price_up", new String[] { "500" });
//
//		map.put("ma_status", new String[] { "0" });
//		map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key
//
//		String finalSQL = "select * from MATERIAL_DATA " + jdbcUtil_CompositeQuery_MatData.get_WhereCondition(map)
//				+ "order by ma_no";
//		System.out.println("●●finalSQL = " + finalSQL);
//	}
//}
