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
 * Servlet implementation class Changepssword
 */
@WebServlet("/pc/Changepassword")
public class Changepassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Changepassword() {
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
		String oldpwd=request.getParameter("oldpwd");
		String newpwd=request.getParameter("newpwd");
		
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("Username");
		System.out.println("Username:"+username);
		System.out.println("oldpwd:"+oldpwd);
		System.out.println("newpwd:"+newpwd);
		//System.out.println("Login!");
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1/safecampus";
			connection = DriverManager.getConnection(url, "root", "123456");
			String sql="SELECT password FROM admin_account where username='"+username+"'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet re = preparedStatement.executeQuery();
			if(re.next()){ 
				String oldpassword=re.getString("password");
				if(oldpassword.equals(oldpwd))
				{
					String changepwdsql="UPDATE admin_account SET password='"+newpwd+"' where username='"+username+"'";
					PreparedStatement preparedStatement2 = connection.prepareStatement(changepwdsql);
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
				else
				{
					response.getWriter().print("pwderror");
				}
					
				System.out.println(oldpassword);
			 }
		}
		catch(ClassNotFoundException e) {   
			System.out.println("Sorry,can`t find the Driver!");   
			e.printStackTrace();   
		} 
		catch(SQLException e) {
			//数据库连接失败异常处理
			e.printStackTrace();  
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			System.out.println("数据库数据成功获取！！");
		}
	}

}
