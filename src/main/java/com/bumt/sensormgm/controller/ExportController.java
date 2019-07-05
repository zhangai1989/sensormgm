package com.bumt.sensormgm.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengdaolin
 * @Description: 导出文件
 * @email idaolin@163.com
 * @date 2019/7/516:18
 */
@RestController
@RequestMapping("/api/export")
public class ExportController {

    @Resource
    private DataSource dataSource;

    @GetMapping("/{reportName}")
    public void getReportByParam(
            @PathVariable("reportName") final String reportName,
            @RequestParam(required = false) Map<String, Object> parameters,
            HttpServletResponse response) throws SQLException, ClassNotFoundException, JRException, IOException {
        parameters = parameters == null ? new HashMap<>() : parameters;
        //获取文件流
        ClassPathResource resource = new ClassPathResource("jaspers" + File.separator + reportName + ".jasper");
        InputStream jasperStream = resource.getInputStream();
        //以附件形式保存文件
        String fileName = "历史记录.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename="
                + URLEncoder.encode(fileName,"utf8"));

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
        OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }
}
