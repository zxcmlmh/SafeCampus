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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class DeleteQuiz_D
 * 用于响应管理员后台中删除回收站中的试卷的请求
 */
@WebServlet("/pc/DeleteQuiz_D")
public class DeleteQuiz_D extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQuiz_D() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取参数
		response.setContentType("application/text;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		String quizid=request.getParameter("id");
		try {
			//执行删除
			DBConnection dbc=new DBConnection();
			Connection connection = dbc.getConnection();
			String sql="";
			sql="DELETE FROM quizes where ID="+quizid;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			int re = preparedStatement.executeUpdate();
			if(re>0)
			{
				response.getWriter().print("success");
			}
			else
			{
				response.getWriter().print("error");
				System.out.println(sql);
			}
			preparedStatement.close();
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
			System.out.println("Operation Finished:Delete Deleted Quiz");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
