package tools;

import javax.servlet.http.HttpSession;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.wel_record.model.WelRecordService;

public class MoneyTool
{

	public static boolean checkOut(HttpSession session, int tns_src, String order_id, int amount)
	{

		MemVO memVO = (MemVO) session.getAttribute("memVO");

		MemService memSrc = new MemService();

		String mem_id = memVO.getMem_id();

		int balance = memSrc.findByPrimaryKey(mem_id).getBalance();// 重新在DB取一次該會員餘額

		if (amount + balance < 0 && amount < 0) // 若為提款(amount < 0)且餘額不足
			{
				return false;

			} else // 提款餘額足夠或為儲值時
			{
				// 新增資料進DB中
				WelRecordService wrSrc = new WelRecordService();
				wrSrc.addWelRecord(mem_id, tns_src, order_id, amount);

				// 再從DB取出並更新目前session之MemVO
				MemVO updatedMemVO = memSrc.findByPrimaryKey(mem_id);
				
				session.setAttribute("memVO", updatedMemVO);
				return true;
			}

	}
	public static boolean checkOut_back(String mem_id, int tns_src, String order_id, int amount)
	{

		MemService memSrc = new MemService();

		int balance = memSrc.findByPrimaryKey(mem_id).getBalance();// 重新在DB取一次該會員餘額

		if (amount + balance < 0 && amount < 0) // 若為提款(amount < 0)且餘額不足
			{
				return false;

			} else // 提款餘額足夠或為儲值時
			{
				// 新增資料進DB中
				WelRecordService wrSrc = new WelRecordService();
				wrSrc.addWelRecord(mem_id, tns_src, order_id, amount);

				return true;
			}

	}
}
