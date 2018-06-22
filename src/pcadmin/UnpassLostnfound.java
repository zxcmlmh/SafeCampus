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
import javax.servlet.http.HttpSession;

import PublicClass.DBConnection;

/**
 * Servlet implementation class UnpassLostnfound
 * 用于响应管理员后台中不通过失物招领的请求
 */
@WebServlet("/pc/UnpassLostnfound")
public class UnpassLostnfound extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnpassLostnfound() {
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
		//获取失物招领ID
		String id=request.getParameter("id");
		try {
			//设置状态为不通过(status=2)
			DBConnection dbc=new DBConnection();
			Connection connection = dbc.getConnection();
			String sql="Update lostnfound set checked=2 where ID="+id;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			int re = preparedStatement.executeUpdate();
			if(re>0){ 
				response.getWriter().print("success");
			 }
			else
			{
				response.getWriter().print("error");
				
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
			System.out.println("Operation Finished:ChangePassword");
		}
	}

}
