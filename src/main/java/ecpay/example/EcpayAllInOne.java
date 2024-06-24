package ecpay.example;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

public class EcpayAllInOne
{
	public static AllInOne all;

//	public static void main(String[] args) {
//
//		System.out.println("getForm: " + getForm(amount));
//
//	}

	private static void initial(String paymentConfPath)
	{
		all = new AllInOne("", paymentConfPath);
	}

	public static String getForm(int amount, String paymentConfPath, String url)
	{

		initial(paymentConfPath);

		return genAioCheckOutALL(amount, url);
	}

	public static String genAioCheckOutALL(int amount, String url)
	{
		AioCheckOutALL obj = new AioCheckOutALL();

		int random = (int) (Math.random() * 100000000);
		String amountstr = Integer.toString(amount);

		obj.setMerchantTradeNo("ArtsBlock" + random);
		obj.setMerchantTradeDate("2017/01/01 08:05:23");
		obj.setTotalAmount(amountstr);
		obj.setTradeDesc("test Description");
		obj.setItemName("ArtsBlock-Deposit");
		obj.setReturnURL("http://211.23.128.214:5000");
		obj.setOrderResultURL(url);
		obj.setNeedExtraPaidInfo("N");
		String form = all.aioCheckOut(obj, null);
		return form;
	}

}
