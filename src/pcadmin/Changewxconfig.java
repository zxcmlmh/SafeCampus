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

/**
 * Servlet implementation class Changewxconfig
 */
@WebServlet("/pc/Changewxconfig")
public class Changewxconfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Changewxconfig() {
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
		String schoolename=request.getParameter("schoolename");
		String schoolcode=request.getParameter("schoolcode");
		String appsecret=request.getParameter("appsecret");
		String appid=request.getParameter("appid");
		String apptoken=request.getParameter("apptoken");
		//System.out.println("Login!");
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1/safecampus";
			connection = DriverManager.getConnection(url, "root", "123456");
			String savechangesql="UPDATE wx_config SET School_Name='"+schoolename+"',School_Code='"+schoolcode+"',appid='"+appid+"',appsecret='"+appsecret+"',apptoken='"+apptoken+"' where ID=1";
			PreparedStatement preparedStatement2 = connection.prepareStatement(savechangesql);
			int result = preparedStatement2.executeUpdate();
			if(result>0)
			{
				response.getWriter().print("success");
			}
			else
			{
				response.getWriter().print("error");
			}
		}
		catch(ClassNotFoundException e) {   
			System.out.println("Sorry,can`t find the Driver!");   
			e.printStackTrace();   
		} 
		catch(SQLException e) {
			//���ݿ�����ʧ���쳣����
			e.printStackTrace();  
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			System.out.println("���ݿ����ݳɹ���ȡ����");
		}
	}

}