package org.ash.demos.Service;

import org.ash.demos.DTO.ReNameDTO;
import org.ash.demos.DTO.TreeNode;
import org.ash.demos.Entity.Picture;

import java.util.List;

public interface PictureService {
    List<TreeNode> getNodeTree(Long account);

    int addNewNode(TreeNode node, Long account);

    int renameNode(TreeNode node, Long account);

    int deleteNode(TreeNode treeNode, Long account);

    String getFilepath(Long account, String level);

    int upload(Picture[] picture);

    int getFileChildren(Long account, String level);

    String[] getPictureNameList(Long account, String level);

    int deletePicture(String[] pictureNameList, Long account);
    boolean  renamePicture(ReNameDTO reNameDTO);
    List<String> getPictureNames(Long account);
}
