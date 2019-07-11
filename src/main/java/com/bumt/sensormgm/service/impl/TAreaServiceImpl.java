package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.common.pojo.TreeNode;
import com.bumt.sensormgm.common.pojo.TreeUtil;
import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.dao.TAreaDao;
import com.bumt.sensormgm.dao.TEnterpriseDao;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TAreaService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class TAreaServiceImpl extends BaseServiceImpl implements TAreaService  {

	@Resource
	private TAreaDao dao;

	@Resource
	private TEnterpriseDao tEnterpriseDao;

	@Override
	public BaseJpaDao getGenericMapper() {return dao; }

	@Override
	public List<TreeNode> getAreaTreeByAreaId(String areaId) {
		List<TreeNode> nodeList = new ArrayList<>();
		List<TArea> tAreaList = getUserAreas(Long.valueOf(areaId), false);
		if(CollectionUtils.isEmpty(tAreaList)) return nodeList;
		for (TArea area : tAreaList) {
			TreeNode node = new TreeNode();
			node.setText(area.getName());
			node.setId(area.getId().toString());
			node.setParentId(area.getParentId().toString());
			node.setLevelCode(area.getLevel());
			nodeList.add( node);
		}
		List<TEnterprise> tEnterpriseList= tEnterpriseDao.findAll();
		for (TEnterprise tEnterprise : tEnterpriseList) {
			TreeNode node = new TreeNode();
			node.setText(tEnterprise.getName());
			node.setId(tEnterprise.getId().toString());
			node.setParentId(tEnterprise.getAreaId().toString());
			node.setLevelCode("4");
			node.setType("");
			nodeList.add(node);
		}
			// 构造树形结构
		List<TreeNode>	tnlist = TreeUtil.buildTree(areaId, nodeList);
		return tnlist;

	}

	@Override
	public Object getAreaListBySession(HttpSession session) {
		TUser tUser = (TUser) session.getAttribute("user");
		return getUserAreas(Long.valueOf(tUser.getAreaId()), false);
	}

	@Override
	public Object getAreaList() {
		return dao.findByLevelAndDeleteFlag("2",0);
	}

	@Override
	public List<TArea> findByName(String cname) {
		return dao.findByNameAndDeleteFlag(cname,0);
	}

	@Override
	public List<TArea> findByNameAndIdNot(String name, long id) {
		return dao.findByNameAndIdNotAndDeleteFlag(name,id,0);
	}

	@Override
	public List<TArea> getUserAreas(Long areaId, boolean containSelf) {
		List<TArea> result = new ArrayList<>();
		// 查询出所有区域
		List<TArea> allArea = dao.findAll();
		if(CollectionUtils.isEmpty(allArea)) return result;
		buildUserAreas(areaId, result, allArea);
		if(containSelf) {
			for(TArea area : allArea) {
				if(areaId.equals(area.getId())) {
					result.add(area);
				}
			}
		}
		return result;
	}

	private void buildUserAreas(Long parentId, List<TArea> mareas, List<TArea> allArea) {
		for(TArea area : allArea) {
			if(area.getDeleteFlag()==0 && parentId.equals(area.getParentId())) {
				mareas.add(area);
				buildUserAreas(area.getId(), mareas, allArea);
			}
		}
	}
}
