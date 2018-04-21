package wechat;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import PublicClass.DBConnection;

/**
 * Servlet implementation class Loginservice
 */
@WebServlet("/wechat/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
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
		String username=request.getParameter("username");
		String code=request.getParameter("code");
		String phone=request.getParameter("phone");
		String testopenid=request.getParameter("openid");
		//System.out.println("Login!");
		try {
			DBConnection dbc=new DBConnection();
			Connection connection = dbc.getConnnection();
			String sql="SELECT department FROM students WHERE name='"+username+"' and code='"+code+"'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet re = preparedStatement.executeQuery();
			if(re.next()){ 
				String department=re.getString("department");
				sql="INSERT INTO users(name,code,phone,openid,department) VALUE('"+username+"','"+code+"','"+phone+"','"+testopenid+"','"+department+"')";
				PreparedStatement preparedStatement2 = connection.prepareStatement(sql);
				int re2 = preparedStatement2.executeUpdate();
				if(re2>0)
				{
					response.getWriter().print("regist success");
				}
				else
					response.getWriter().print("regist error");
			 }
			else
				response.getWriter().print("validation error");
		}
		catch(SQLException e) {
			//数据库连接失败异常处理
			e.printStackTrace();  
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//System.out.println("登录成功");
		}
	}

}
