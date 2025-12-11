package org.ash.Service;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import org.ash.AliyunOssUtil.AliyunOssUtils;
import org.ash.Dto.NodeAddDto;
import org.ash.Dto.TreeNodeDto;
import org.ash.Entity.Node;
import org.ash.Entity.TreeRelation;
import org.ash.Mapper.TreeNodeMapper;
import org.com.Entity.Result;
import org.com.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class editorServiceImpl implements editorService{
    @Autowired
    private AliyunOssUtils aliyunOssUtil;
    private Set<String> visitedNodes = new HashSet<>();
    private final TreeNodeMapper treeNodeMapper;

    public editorServiceImpl(TreeNodeMapper treeNodeMapper) {
        this.treeNodeMapper = treeNodeMapper;
    }

    @Override
    public List<Node> uploadTree(long account) {
        visitedNodes.clear();
        List<TreeRelation> RootNodes =treeNodeMapper.findRootNodeByAccount(account);
        List<Node> rootNodes = new ArrayList<>();
        for(TreeRelation rootNode : RootNodes){
            Node root =buildTree(rootNode.getDescendant(),treeNodeMapper.SearchLabelById(rootNode.getDescendant()),treeNodeMapper.CheckIsCollectedById(rootNode.getDescendant()));
            rootNodes.add(root);
        }
        return rootNodes;
    }

    @Override
    public  void buildTreeRecursive(Node parentNode, String label) {
        // 如果当前节点已经处理过，则直接返回
        if (visitedNodes.contains(parentNode.getId())) {
            return;
        }

        // 将当前节点标记为已访问
        visitedNodes.add(parentNode.getId());

        // 找到所有子节点
        List<TreeRelation> relations = treeNodeMapper.findByAncestor(parentNode.getId());
        for (TreeRelation relation : relations) {
            // 跳过自身节点
            if (Objects.equals(relation.getDescendant(), parentNode.getId())) {
                continue;
            }
            // 创建子节点
            Node childNode = new Node(relation.getDescendant(), treeNodeMapper.SearchLabelById(relation.getDescendant()), treeNodeMapper.CheckIsCollectedById(relation.getDescendant()));
            parentNode.addChild(childNode);
            // 递归处理子节点
            buildTreeRecursive(childNode, childNode.getLabel());
        }
    }

    @Override
    public boolean saveFileUrl(String nodeId, String fileUrl) {
        return treeNodeMapper.SetFileUrlByNodeId(nodeId, fileUrl);
    }

    @Override
    public Result handleFileUpload(MultipartFile file,String nodeId) {
        if (file.isEmpty()) {
            return Result.error("文件不能为空");
        }
        try {
            // 文件在 OSS 中的路径（如 "uploads/first.md"）
            String filePath = "uploads/" + nodeId;
            // 上传文件到阿里云 OSS
            System.out.println("上传文件: " + filePath);
            String fileUrl = aliyunOssUtil.uploadFile(file, filePath);
            saveFileUrl(nodeId, fileUrl);
            return Result.success(fileUrl);
        } catch (IOException e) {
            return Result.error("文件上传失败");
        }
    }

    @Override
    public boolean addNode(NodeAddDto nodeAddDto) {
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1, 1);
        long nodeIdNumber = idGenerator.nextId();
        String nodeId = String.valueOf(nodeIdNumber);
        if("0".equals(nodeAddDto.getParentId())){
            return treeNodeMapper.addNode(nodeId,nodeAddDto.getNodeLabel(),nodeAddDto.getUserAccount())
                    &&treeNodeMapper.addNodeRelation(nodeId,nodeId,nodeAddDto.getDepth(),
                    nodeAddDto.getUserAccount());
        }
        else {
            return treeNodeMapper.addNode(nodeId, nodeAddDto.getNodeLabel(), nodeAddDto.getUserAccount())
                    && treeNodeMapper.addNodeRelation(nodeAddDto.getParentId(), nodeId, nodeAddDto.getDepth(),
                    nodeAddDto.getUserAccount());
        }
    }

    @Override
    public boolean nodeEdit(String nodeLabel,String nodeId) {
        return treeNodeMapper.setNodeLabelById(nodeLabel,nodeId);
    }

    @Override
    public String getFileContent(String nodeId) {
        String nodeIdStr = "uploads/" + nodeId;
        return aliyunOssUtil.getFileContent(nodeIdStr);
    }

    @Override
    public boolean deleteNode(String nodeId) {
        return treeNodeMapper.deleteNodeRelation(nodeId) && treeNodeMapper.deleteNodeById(nodeId);
    }

    @Override
    public int NodeChangeIsCollected(String nodeId) {
        int isCollected =  treeNodeMapper.CheckIsCollectedById(nodeId);
        int NewIsCollectd = isCollected == 0 ? 1 : 0;
        if(treeNodeMapper.updateIsCollected(NewIsCollectd,nodeId)){
            return treeNodeMapper.CheckIsCollectedById(nodeId);
        }else{
            //2代表错误数据
            return 500;
        }
    }

    @Override
    public List<TreeNodeDto> getAllCollectedNode(long account) {
        return treeNodeMapper.getAllCollectedNode(account);
    }
    /**
     * 方法2：分批查询并构建路径
     */
    public List<TreeNodeDto> getAllCollectedNodeBatch(long account) {
        List<TreeNodeDto> result = new ArrayList<>();
        int batchSize = 100; // 每批处理100条
        int offset = 0;

        try {
            // 获取总数
            int total = treeNodeMapper.getCollectedCount(account);
            // 分批处理
            while (offset < total) {
                // 分批获取收藏节点
                List<Map<String, Object>> batchNodes = treeNodeMapper.getCollectedNodesByPage(
                        account, offset, batchSize);

                // 批量获取每个节点的路径
                List<TreeNodeDto> batchResult = buildNodePathsBatch(batchNodes, account);
                result.addAll(batchResult);

                offset += batchSize;
            }
            // 按路径排序
            result.sort(Comparator.comparing(TreeNodeDto::getNodePath));

            return result;
        } catch (Exception e) {
            throw new RuntimeException("获取收藏节点失败", e);
        }
    }

    /**
     * 批量构建节点路径
     */
    private List<TreeNodeDto> buildNodePathsBatch(List<Map<String, Object>> nodes, long account) {
        if (CollectionUtils.isEmpty(nodes)) {
            return Collections.emptyList();
        }

        List<TreeNodeDto> result = new ArrayList<>();

        for (Map<String, Object> node : nodes) {
            String nodeId = (String) node.get("node_id");
            String nodeLabel = (String) node.get("node_label");

            // 获取节点路径
            String ancestorPath = treeNodeMapper.getNodePath(nodeId, account);

            TreeNodeDto dto = new TreeNodeDto();
            dto.setNodeId(nodeId);

            // 构建完整路径
            if (ancestorPath != null && !ancestorPath.isEmpty()) {
                dto.setNodePath(ancestorPath + "/" + nodeLabel);
            } else {
                dto.setNodePath(nodeLabel);
            }

            result.add(dto);
        }

        return result;
    }
    public  Node buildTree(String nodeId,String label,int isCollected){
        Node node = new Node(nodeId,label,isCollected);
        buildTreeRecursive(node,label);
        return node;
    }

}
