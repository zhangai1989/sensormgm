package com.bumt.sensormgm.common.pojo;

import com.github.pagehelper.util.StringUtil;
import org.springframework.util.CollectionUtils;

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

    public static List<TreeNode> buildTree(String rootId, List<TreeNode> nodes) {
        List<TreeNode> result = getChildren(rootId, nodes);
        if(!CollectionUtils.isEmpty(result)) {
            for(TreeNode node : result) {
                appendChildren(node, nodes);
            }
        }
        return result;
    }

    public static List<TreeNode> getChildren(String parentId, List<TreeNode> nodes) {
        List<TreeNode> result = new ArrayList<>();
        for(TreeNode node : nodes) {
            if(parentId.equals(node.getParentId())) {
                result.add(node);
            }
        }
        return result;
    }

    public static void appendChildren(TreeNode parentNode, List<TreeNode> nodes) {
        for(TreeNode node : nodes) {
            if(parentNode.getId().equals(node.getParentId())) {
                parentNode.getNodes().add(node);
                if(node.getLevelCode().equals("4")) continue;
                appendChildren(node, nodes);
            }
        }
    }

}
