package com.bumt.sensormgm.service;

import com.bumt.sensormgm.common.pojo.TreeNode;
import com.bumt.sensormgm.common.service.BaseService;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TUser;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TAreaService extends BaseService {

    List<TreeNode> getAreaTreeByAreaId(String areaId);

    Object getAreaListBySession(HttpSession session);

    Object getAreaList();

    List<TArea> findByName(String cname);

    List<TUser> findByNameAndIdNot(String name, long id);
}
