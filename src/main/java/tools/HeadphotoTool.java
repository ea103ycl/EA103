package tools;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemService;

public class HeadphotoTool {

	public static void printHeadphotoByMemId(HttpServletRequest req, HttpServletResponse res, String mem_id)
			throws IOException {

		MemService memSvc = new MemService();
		byte[] photofromDB = memSvc.getPhoto(mem_id);
		ServletOutputStream servletOut = res.getOutputStream();
		servletOut.write(photofromDB);
	}
}
