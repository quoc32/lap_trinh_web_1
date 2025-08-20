import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra cookie
        Cookie[] cookies = req.getCookies();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
                if ("username".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                    // Nếu có cookie hợp lệ, chuyển thẳng sang home
                    resp.sendRedirect("home");
                    return;
                }
            }
        }

        // Nếu không có cookie, hiển thị trang login
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String path = getServletContext().getRealPath("/pages/login.html");
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
        // Lấy thông tin từ form
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        
        // In ra console server
        System.out.println("Username: " + user);
        System.out.println("Password: " + pass);
        
        if (!"quoc".equals(user) || !"123".equals(pass)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
            return;
        }

        // Tạo cookie lưu username (ví dụ)
        Cookie userCookie = new Cookie("username", user);
        userCookie.setMaxAge(60 * 60); // sống 1 giờ
        resp.addCookie(userCookie);

        // Redirect đến trang home
        resp.sendRedirect("home");
    }
}
