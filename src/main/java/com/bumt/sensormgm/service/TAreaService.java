package com.bumt.sensormgm.service;

import com.bumt.sensormgm.common.pojo.TreeNode;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TUser;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TAreaService extends BaseService {

    List<TreeNode> getAreaTreeByAreaId(String areaId, boolean containEnterprise);

    Object getAreaListBySession(HttpSession session);

    Object getAreaList(HttpSession session);

    List<TArea> getUserAreas(Long areaId, boolean containSelf);

    List<TArea> findByName(String cname);

    List<TArea> findByNameAndIdNot(String name, long id);
}
