package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.model.record.BlockRecord2;
import calebzhou.rdicloudrest.utils.SqlUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/BlockRecord2")
public class BlockRecordServlet extends BasicServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        BlockRecord2 record = parseRequstJsonToObject(BlockRecord2.class,req);
        try {
            SqlUtils.insertObjectToTable(record,BlockRecord2.class);
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
