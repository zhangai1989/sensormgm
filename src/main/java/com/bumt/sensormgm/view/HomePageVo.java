package com.bumt.sensormgm.view;

import java.util.List;
import java.util.Map;

public class HomePageVo {

    /**
     * @Description:区域排名
     * @Author:     zhangai
     * @CreateDate: 2019/6/29 11:00
     * @Version: 1.0
     */
    private List<Map> areaRank;

    /**
     * @Description:企业排名
     * @Author:     zhangai
     * @CreateDate: 2019/6/29 11:00
     * @Version: 1.0
     */
    private List<Map> enterpriseRank;

    /**
     * @Description:企业超标
     * @Author:     zhangai
     * @CreateDate: 2019/6/29 11:27
     * @Version: 1.0
     */
    private List<String> enterpriseBeyond;


    public HomePageVo() {
    }

    public List<Map> getAreaRank() {
        return areaRank;
    }

    public void setAreaRank(List<Map> areaRank) {
        this.areaRank = areaRank;
    }

    public List<Map> getEnterpriseRank() {
        return enterpriseRank;
    }

    public void setEnterpriseRank(List<Map> enterpriseRank) {
        this.enterpriseRank = enterpriseRank;
    }

    public List<String> getEnterpriseBeyond() {
        return enterpriseBeyond;
    }

    public void setEnterpriseBeyond(List<String> enterpriseBeyond) {
        this.enterpriseBeyond = enterpriseBeyond;
    }
}
