package com.bumt.sensormgm.controller;

import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TAreaService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private TAreaService areaService;
    @Resource
    private DataSource dataSource;

    @GetMapping("/{reportName}")
    public void getReportByParam(
            @PathVariable("reportName") final String reportName,
            @RequestParam(required = false) Map<String, Object> parameters,
            HttpServletResponse response, HttpSession session) throws SQLException, ClassNotFoundException, JRException, IOException {
        Assert.notNull(parameters, "参数不能为空");
        Assert.notNull(parameters.get("beginTime"), "开始时间不能为空");
        Assert.notNull(parameters.get("endTime"), "结束时间不能为空");
        TUser tUser = (TUser) session.getAttribute("user");

        //获取文件流
        InputStream jasperStream = null;
        try {
            jasperStream = new FileInputStream("/home/jaspers/" + reportName + ".jasper");
            //以附件形式保存文件
            String fileName = "%s(%s至%s).xls";
            if("history".equals(reportName)) {
                fileName = String.format(fileName, "历史记录", parameters.get("beginTime"), parameters.get("endTime"));
            }else if("warning".equals(reportName)) {
                fileName = String.format(fileName, "预警", parameters.get("beginTime"), parameters.get("endTime"));
            }else if("outStandard".equals(reportName)) {
                fileName = String.format(fileName, "超标", parameters.get("beginTime"), parameters.get("endTime"));
            }else {
                return;
            }
            if("warning".equals(reportName) || "outStandard".equals(reportName)) {
                List<TArea> userAreas = areaService.getUserAreas(Long.valueOf(tUser.getAreaId()), false);
                if(CollectionUtils.isEmpty(userAreas)) return;
                List<Long> areaIds = new ArrayList<>();
                for(TArea area : userAreas) {
                    areaIds.add(area.getId());
                }
                parameters.put("areaIds", areaIds.toString().replace("[", "(").replace("]", ")"));
            }

            response.setHeader("Content-disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "utf8"));

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

            response.setContentType("application/vnd_ms-excel");
            JRXlsxExporter xlsxExporter = new JRXlsxExporter();
            xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            xlsxExporter.exportReport();
        }finally {
           if(!StringUtils.isEmpty(jasperStream)) {
               jasperStream.close();
           }
        }
    }
}
