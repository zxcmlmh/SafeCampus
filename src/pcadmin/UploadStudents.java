package pcadmin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import PublicClass.ExcelReader;


/**
 * Servlet implementation class UploadStudents
 * 用于响应管理员后台中导入学生数据的请求
 */
@WebServlet("/pc/UploadStudents")
public class UploadStudents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadStudents() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		DiskFileItemFactory fac = new DiskFileItemFactory();
        //2.创建文件上传核心类对象
        ServletFileUpload upload = new ServletFileUpload(fac);
        //【一、设置单个文件最大30M】
        upload.setFileSizeMax(30*1024*1024);//30M
        //【二、设置总文件大小：50M】
        upload.setSizeMax(50*1024*1024); //50M
            try {
                //3.把请求数据转换为FileItem对象的集合
                List<FileItem> list = upload.parseRequest(request);
                //遍历，得到每一个上传项
                for (FileItem item : list){
                    //判断：是普通表单项，还是文件上传表单项
                    if (item.isFormField()){
                        //普通表单x
                        String fieldName = item.getFieldName();//获取元素名称
                        String value = item.getString("UTF-8"); //获取元素值
                        System.out.println(fieldName+" : "+value);
                    }else {
                        //文件上传表单
                        String name = item.getName(); //上传的文件名称
                        /**
                         * 【四、文件重名】
                         * 对于不同的用户的test.txt文件，不希望覆盖，
                         * 后台处理：给用户添加一个唯一标记！
                         */
                        //a.随机生成一个唯一标记
                        String id = UUID.randomUUID().toString();
                        //与文件名拼接
                        name = id + name;
                        //【三、上传到指定目录：获取上传目录路径】
                        String realPath = getServletContext().getRealPath("pc/Upload");
                        //创建文件对象
                        File file = new File(realPath, name);
                        item.write(file);
                        System.out.println("文件上传成功！");
                        item.delete();
                        ExcelReader eh=new ExcelReader();
                        //type设置为1，代表是导入学生数据
                        eh.type=1;
                        //把文件路径传入excelhandler进行处理，返回处理成功的条数，返回。
                        int count=eh.readExcelData(realPath+"/"+name);
                        response.getWriter().print("上传成功，共导入"+count+"条学生数据");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
	}
}
