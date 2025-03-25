import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlConnectionTest {

    public static void main(String[] args) {
        // 1. 配置 Druid 数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/ns_bbs?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("yjs");      // 请修改为您的用户名
        dataSource.setPassword("000000");   // 请修改为您的真实密码
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 2. 获取数据库连接
            conn = dataSource.getConnection();
            System.out.println("✅ 数据库连接成功！");

            // 3. 执行验证查询
            String testSQL = "SELECT 1 AS status";
            ps = conn.prepareStatement(testSQL);
            rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("🔍 测试查询结果: " + rs.getInt("status"));
            }

        } catch (SQLException e) {
            handleSqlException(e);
        } finally {
            // 4. 关闭资源
            closeResources(rs, ps, conn);
            dataSource.close();
        }
    }

    private static void handleSqlException(SQLException e) {
        System.err.println("❌ 数据库连接失败！错误原因：");
        e.printStackTrace();

        // 常见错误处理提示
        if (e.getErrorCode() == 1045) {
            System.err.println("\n👉 可能原因：用户名/密码错误");
        } else if (e.getErrorCode() == 1049) {
            System.err.println("\n👉 可能原因：数据库 'ns_bbs' 不存在");
        } else if (e.getMessage().contains("Communications link failure")) {
            System.err.println("\n👉 可能原因：MySQL 服务未启动或端口被阻止");
            System.err.println("   检查命令：mysql -u root -p");
            System.err.println("   启动服务：brew services start mysql@8.0");
        }
    }

    private static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    System.err.println("⚠️ 资源关闭异常: " + e.getMessage());
                }
            }
        }
    }
}