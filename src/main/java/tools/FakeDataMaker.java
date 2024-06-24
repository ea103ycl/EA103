package tools;

import com.wel_record.model.WelRecordJDBCDAO;
import com.wel_record.model.WelRecordVO;

public class FakeDataMaker
{

	public static void main(String[] args)
	{
		// 請09-11依序各跑一次
		String month = "09";
//		String month = "10";
//		String month = "11";

		int memNumber = 15;

		// --------------------------假資料-隨機新增n筆交易紀錄----------------------------------
		WelRecordJDBCDAO welRecordDAO = new WelRecordJDBCDAO();
//		int[] srcArray = { 10, 20, 30, 31, 32, 33, 34, 35, 36, 37, 38, 40, 41, 42, 43 };
		String[] memArray = new String[memNumber];

		for (int i = 0; i < memNumber; i++)
			{

				memArray[i] = "M" + String.format("%06d", i + 1);
				System.out.println(memArray[i]);
			}

		int n = 30;
		for (int i = 0; i < n; i++)
			{
				System.out.println(i);
				// 隨機抽選交易來源srcArray&memArray索引號
				int r = (int) (Math.random() * 100 + 1); // 隨機抽取一數字(1-100間)
				int memIndex = (int) (Math.random() * memNumber);
				int amount = ((int) (Math.random() * 100) + 1) * 100;
				int amountShare = (int) (amount * 0.15);
				int srcIndex = 0;

				int sec = ((int) (Math.random() * 60) + 1);
				int min = ((int) (Math.random() * 60) + 1);
				int hour = ((int) (Math.random() * 12) + 1);

				WelRecordVO welRecordVO2 = new WelRecordVO();
				welRecordVO2.setMem_id(memArray[memIndex]);
				welRecordVO2.setOrder_id(null);

				if ((r > 0 && r <= 38)) // 儲值
					{
						welRecordVO2.setTns_src(10);
						welRecordVO2.setTns_amount(amount);

					} else if (r > 38 && r <= 48) // 提款
					{
						welRecordVO2.setTns_src(20);
						welRecordVO2.setTns_amount(-amount);

					} else if (r > 48 && r <= 53) // 平台退款

					{
						srcIndex = (int) (Math.random() * 3 + 35); // 35-38間抽取一數字
						welRecordVO2.setTns_src(srcIndex);
						welRecordVO2.setTns_amount((int) (amount * 0.3)); // 退款金額初估為儲值三成

					} else if (r > 63 && r <= 73) // 分潤+折扣金

					{
						srcIndex = (int) (Math.random() * 4 + 30); // 30-34間抽取一數字
						welRecordVO2.setTns_src(srcIndex);

						welRecordVO2.setTns_amount(amountShare);

					} else
					{
						srcIndex = (int) (Math.random() * 3 + 40); // 40-43間抽取一數字

						if (srcIndex == 40)
							{
								welRecordVO2.setOrder_id("S2020" + month + "12-00002");
							} else if (srcIndex == 41)
							{
								welRecordVO2.setOrder_id("P2020" + month + "07-00009");
							} else if (srcIndex == 42)
							{
								welRecordVO2.setOrder_id("B2020" + month + "23-00008");
							} else if (srcIndex == 43)
							{
								welRecordVO2.setOrder_id("M2020" + month + "10-00004");
							}
						welRecordVO2.setTns_src(srcIndex);
						welRecordVO2.setTns_amount((int) (-amount * 0.8)); // 平台扣款
					}

				welRecordVO2.setTns_time("2020-" + month + "-02 " + hour + ":" + min + ":" + sec);

				welRecordDAO.insert4FakeData(welRecordVO2);
			}

//		10:會員儲值
//		20:會員提款
//		30:平台撥款-一般購買分潤
//		31:平台撥款-預購分潤
//		32:平台撥款-競標分潤
//		33:平台撥款-見面會分潤
//		34:平台撥款-預購折扣金
//		35:平台退款-一般購買退款
//		36:平台退款-預購
//		37:平台退款-競標
//		38:平台退款-見面會
//		40:平台扣款-一般購買訂單
//		41:平台扣款-預購訂單
//		42:平台扣款-競標訂單
//		43:平台扣款-見面會

	}

}
