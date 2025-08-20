import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Home extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Đường dẫn tuyệt đối trong web app
        String path = getServletContext().getRealPath("/pages/home.html");
        File file = new File(path);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                out.println(line);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Ví dụ: xử lý logout khi form POST gửi action "logout"
        String action = req.getParameter("action");
        
        if ("logout".equals(action)) {
            // Xóa cookie username
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        cookie.setValue("");
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    }
                }
            }
            // Redirect về trang login
            resp.sendRedirect("login");
            return;
        }

        // Nếu muốn xử lý form khác, có thể thêm ở đây
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h3>Form POST đã được gửi!</h3>");
    }

}
