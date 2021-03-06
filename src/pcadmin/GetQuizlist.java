package pcadmin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import PublicClass.DBConnection;

/**
 * Servlet implementation class GetQuizlist
 * 用于响应管理员后台中获取试卷列表的请求
 */
@WebServlet("/pc/GetQuizlist")
public class GetQuizlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetQuizlist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String json="[";
		try {
			DBConnection dbc=new DBConnection();
			Connection connection = dbc.getConnection();
			//获取所有isdeleted为0的试卷
			String sql="SELECT * FROM quizes where isdeleted=0";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet re = preparedStatement.executeQuery();
			int count=0;
			//拼接字符串
			while(re.next()){ 
				String id=re.getString("ID");
				String text=re.getString("name");
				String starttime=re.getString("starttime");
				String endtime=re.getString("endtime");
				String time=re.getString("time");
				String totalsc=re.getString("totalsc");
				String passsc=re.getString("passsc");
				String times=re.getString("times");
				String issimulate=re.getString("issimulate");
				if(count>0)
					json=json+",";
				json=json+"{\"id\":"+id+",\"text\":\""+text+"\",\"starttime\":\""+starttime+"\",\"endtime\":\""+endtime+"\",\"time\":\""+time+"\",\"totalsc\":"+totalsc+",\"passsc\":\""+passsc+"\",\"times\":\""+times+"\",\"issimulate\":\""+issimulate+"\"}";
				count++;
			 }
			json=json+"]";
			//System.out.print(json);
			response.getWriter().print(json);
			preparedStatement.close();
			re.close();
			dbc.CloseConnection(connection);
		}
		catch(SQLException e) {
			//数据库连接失败异常处理
			e.printStackTrace();  
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			System.out.println("Operation Finished:GetQuizList");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

