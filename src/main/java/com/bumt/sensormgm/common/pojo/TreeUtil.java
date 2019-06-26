package com.bumt.sensormgm.common.pojo;

import com.github.pagehelper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author:     zhangai
 * @CreateDate: 2019/6/26 14:18
 * @Version: 1.0
 */
public class TreeUtil {

    public static List<TreeNode> getNodeList(Map<String, TreeNode> nodelist) {
        List<TreeNode> tnlist=new ArrayList<>();
        for (String id : nodelist.keySet()) {
            TreeNode node = nodelist.get(id);
            if (StringUtil.isEmpty(node.getParentId())) {
                tnlist.add(node);
            } else {
                if(nodelist.get(node.getParentId()) == null){
                    continue;
                }
                if (nodelist.get(node.getParentId()).getNodes() == null)
                    nodelist.get(node.getParentId()).setNodes(new ArrayList<TreeNode>());
                nodelist.get(node.getParentId()).getNodes().add(node);
            }
        }
        return tnlist;
    }

}
