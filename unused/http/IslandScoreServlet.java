package calebzhou.rdicloudrest.http;


import calebzhou.rdicloudrest.dao.DatabaseConnector;
import com.google.common.base.Joiner;
import com.google.gson.internal.LinkedTreeMap;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/v2/island_score")
public class IslandScoreServlet extends BasicServlet{
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        super.doGet(req, resp);
        Map<String,Integer> map = new LinkedTreeMap<>();
        String person=req.getParameter("person")==null?"":req.getParameter("person");
        String sql = "select * from IslandScoreView ";
        ResultSet rs;
        switch (person){
            case "all" ->{
                rs= DatabaseConnector.getPreparedStatement(sql).executeQuery();
            }
            default -> {
                sql +=" where pname=?";
                rs= DatabaseConnector.getPreparedStatement(sql,person).executeQuery();
            }
        }

        while(rs.next()){
            map.put(rs.getString("pname"),rs.getInt("score"));
        }
        map=map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .filter(e->e.getValue()>1000)
                //.limit(30)
                .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        write(resp, Joiner.on("\n").withKeyValueSeparator(": ").join(map));
    }

}
