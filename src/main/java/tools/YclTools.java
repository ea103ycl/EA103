package tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.sql.Clob;
import java.sql.SQLException;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class YclTools {

	// ======================================================================
	// 將路徑裡的資料轉為InpuetSteam(JDBC的第三種範例)
	// 使用的是資料流，為了避免硬體方面管線上的限制，資料量大時用這種會比較好，將資料分流寫入DB而不是一次大量寫入(>=100mb時算資料量大)
	// ======================================================================
	public static InputStream getUploadFileStream(String path) throws IOException {
		System.out.println(path);
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		return fis;
	}

	// ======================================================================
	// 將路徑裡的資料轉為Byte陣列(JDBC的第二種範例)
	// 使用的是JDBC原生的方法，且byte[]從JAVA 1.0開始就有，不受各家DB廠商提供的driver影響
	// 比較適合用在資料量小時使用
	// ======================================================================
	public static byte[] getUploadFileByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file); // (L)低接管
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // 此資料流會把write的位元資料存到一個內建的byte[]
		byte[] buffer = new byte[8192]; // 8kb
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		fis.close();
		return baos.toByteArray(); // toByteArray()可以讓我們取得這個資料流內建的byte[]
	}

	// ======================================================================
	// 將取得的Part轉為byte陣列
	// ======================================================================
	public static byte[] transPartToByteArray(Part filePart) throws IOException {

		InputStream fileContent = filePart.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[fileContent.available()]; // 緩衝區大小8kb
		int i;
		while ((i = fileContent.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		fileContent.close();

		return baos.toByteArray();

	}

	// ======================================================================
	// 判斷上傳檔是否為空
	// ======================================================================
	public static boolean isUploadFile(Part filePart) {
		String fileName = (filePart.getSubmittedFileName()).trim();
		if (fileName == null || fileName.length() == 0) {
			return false;
		} else {
			return true;
		}

	}

	// ========================================================================
	// 將db內讀出的byte[]轉為Stream並呈現於頁面
	// ========================================================================
	public static void readByteArrayFromDB(HttpServletRequest req, HttpServletResponse res, byte[] dbData)
			throws IOException {

		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dbData);
		BufferedInputStream in = new BufferedInputStream(byteArrayInputStream);
		byte[] buf = new byte[10 * 1024]; // 4K buffer
		int len;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		in.close();
	}

	// ========================================================================
	// 將db內讀出的Clob轉為String，並加上textarea的換行符號&#13;&#10;
	// ========================================================================
	public static String readClobToString(Clob clob) throws SQLException, IOException {

		String result = "";

		if (clob != null) {

			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(clob.getCharacterStream());
			String str;

			while ((str = br.readLine()) != null) {
				sb.append(str);
				sb.append("&#13;&#10;"); // &#13;&#10;
//				sb.append("\n");
				// https://stackoverflow.com/questions/14534767/how-to-append-a-newline-to-stringbuilder
				// sb.append(System.getProperty("line.separator"));
			}

			br.close();
			result = sb.toString();
		}

		return result;

	}

	// ========================================================================
	// 將db內讀出的VARCHAR2轉為String，並將換行符號轉換為<br>
	// ========================================================================
	public static String readStringAppendBr(String text) {

		String result = "";
		if (text != null && text.length() != 0) {

			StringBuilder sb = new StringBuilder();
			String[] resultArr = text.split("\n");

			for (String str : resultArr) {
				sb.append(str);
				sb.append("<br>");
			}

			result = sb.toString();
		}

		return result;

	}

	/**
	 * Author:小吳老師 (10/20分享的package:idv.david.main)
	 * 將圖形縮小後回傳，如果發生錯誤就直接回傳原圖， 例如：imageSize值<=1、無法取得圖的寬高、發生Exception
	 * 
	 * @param srcImageData 來源圖形資料
	 * @param scaleSize    欲將圖形縮至多少尺寸以下，50代表將尺寸縮小至50x50以內
	 * @return 縮小完畢的圖形資料
	 */
	public static byte[] shrink( byte[] srcImageData, int scaleSize ) {
		ByteArrayInputStream bais = new ByteArrayInputStream(srcImageData);
		// 縮小比例，4代表除以4
		int sampleSize = 1;
		int imageWidth = 0;
		int imageHeight = 0;

		// 如果imageSize為0、負數(代表錯誤)或1(1代表與原圖一樣大小)，就直接回傳原圖資料
		if (scaleSize <= 1) {
			return srcImageData;
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(bais);
			// 如果無法識別圖檔格式(TYPE_CUSTOM)就回傳TYPE_INT_ARGB；否則回傳既有格式
			int type = srcBufferedImage.getType() == BufferedImage.TYPE_CUSTOM ? BufferedImage.TYPE_INT_RGB
					: srcBufferedImage.getType();
			
			imageWidth = srcBufferedImage.getWidth();
			imageHeight = srcBufferedImage.getHeight();
			if (imageWidth == 0 || imageHeight == 0) {
				return srcImageData;
			}
			// 只要圖檔較長的一邊超過指定長度(desireSize)，就計算欲縮小的比例
			// 並將圖檔寬高都除以欲縮小比例
			int longer = Math.max(imageWidth, imageHeight);
			if (longer > scaleSize) {
				sampleSize = longer / scaleSize;
				imageWidth = srcBufferedImage.getWidth() / sampleSize;
				imageHeight = srcBufferedImage.getHeight() / sampleSize;
			}
			BufferedImage scaledBufferedImage = new BufferedImage(imageWidth, imageHeight, type);
			Graphics graphics = scaledBufferedImage.createGraphics();
			graphics.drawImage(srcBufferedImage, 0, 0, imageWidth, imageHeight, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(scaledBufferedImage, "png", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return srcImageData;
		}
	}

}