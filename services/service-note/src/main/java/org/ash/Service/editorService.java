package org.ash.Service;

import org.ash.Dto.NodeAddDto;
import org.ash.Entity.Node;
import org.com.Entity.Result;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface editorService {
    List<Node> uploadTree(long account);
    void buildTreeRecursive(Node node, String label);
    boolean saveFileUrl(String nodeId,String fileUrl);
    Result handleFileUpload(MultipartFile file,String nodeId);
    boolean addNode(NodeAddDto nodeAddDto);
    boolean nodeEdit(String nodeLabel,String nodeId);
    String getFileContent(String nodeId);
    boolean deleteNode(String nodeId);
}
