package com.bumt.sensormgm.service.impl;

import com.bumt.sensormgm.common.pojo.TreeNode;
import com.bumt.sensormgm.common.pojo.TreeUtil;
import com.bumt.sensormgm.common.service.impl.BaseServiceImpl;
import com.bumt.sensormgm.common.dao.BaseJpaDao;
import com.bumt.sensormgm.dao.TEnterpriseDao;
import com.bumt.sensormgm.entity.TArea;
import com.bumt.sensormgm.entity.TEnterprise;
import com.bumt.sensormgm.entity.TUser;
import com.bumt.sensormgm.service.TAreaService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Service;
import com.bumt.sensormgm.dao.TAreaDao;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

		// 获取数据
		Map<String, TreeNode> nodeList = new LinkedHashMap<String, TreeNode>();
		List<TArea> tAreaList = dao.findAll();
		for (TArea area : tAreaList) {
			TreeNode node = new TreeNode();
			node.setText(area.getName());
			node.setId(area.getId().toString());
			if(!areaId.equals(area.getId().toString())){
				node.setParentId(area.getParentId().toString());
			}

			node.setLevelCode(area.getLevel());
			node.setType(area.getDeleteFlag().toString());
			nodeList.put(node.getId(), node);
		}
		List<TEnterprise> tEnterpriseList= tEnterpriseDao.findAll();
		for (TEnterprise tEnterprise : tEnterpriseList) {
			TreeNode node = new TreeNode();
			node.setText(tEnterprise.getName());
			node.setId(tEnterprise.getCode().toString());
			node.setParentId(tEnterprise.getAreaId().toString());
			node.setLevelCode("4");
			node.setType("");
			nodeList.put(node.getId(), node);
		}
			// 构造树形结构
		List<TreeNode>	tnlist = TreeUtil.getNodeList(nodeList);
		return tnlist;

	}

	@Override
	public Object getAreaListBySession(HttpSession session) {
//		TUser tUser = (TUser) session.getAttribute("user");
//		if(tUser!=null&&tUser.getLevel()==3){
//			return dao.findById(Long.parseLong(tUser.getAreaId()));
//		}
		return  dao.findByLevel("2");
	}

	@Override
	public Object getAreaList() {
		return dao.findByLevel("2");
	}
}
