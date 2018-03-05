package pcadmin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SaveKnowledge
 */
@WebServlet("/pc/SaveKnowledge")
public class SaveKnowledge extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveKnowledge() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String id=request.getParameter("id");
				String name=request.getParameter("text");
				String topid=request.getParameter("topid");
				String iscourse=request.getParameter("iscourse");
				String vurl=request.getParameter("vurl");
				String time=request.getParameter("time");
				Connection connection = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1/safecampus";
					connection = DriverManager.getConnection(url, "root", "123456");
					String sql="";
					sql="UPDATE  directories SET text='"+name+"',iscourse="+iscourse+",topid="+topid+",url='"+vurl+"',time='"+time+"' WHERE id="+id;
					System.out.println(sql);
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					int re = preparedStatement.executeUpdate();
					if(re>0)
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
					//数据库连接失败异常处理
					e.printStackTrace();  
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}finally{
					System.out.println("Create Directories finished");
				}
	}

}
