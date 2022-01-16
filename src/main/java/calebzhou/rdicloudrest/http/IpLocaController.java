package calebzhou.rdicloudrest.http;

import calebzhou.rdicloudrest.model.geo.SimpleGeoLocation;
import calebzhou.rdicloudrest.utils.GeographyUtils;
import calebzhou.rdicloudrest.utils.ResponseUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ip2loca")
public class IpLocaController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ip = req.getParameter("ip");
        SimpleGeoLocation location = GeographyUtils.getSimpleGeoLocation(ip);
        ResponseUtils.write(resp, location.getCountry().equals("中国") ?
                String.format("%s-%s-%s",location.getProvince(),location.getCity(),location.getDistrict())
                :
                String.format("%s,%s,%s (%s)",location.getCity(),location.getProvince(),location.getCountry(),location.getDistrict()));
    }
}