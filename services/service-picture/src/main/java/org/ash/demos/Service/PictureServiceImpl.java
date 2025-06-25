package org.ash.demos.Service;

import org.ash.demos.DTO.ReNameDTO;
import org.ash.demos.DTO.TreeNode;
import org.ash.demos.Entity.Picture;
import org.ash.demos.Mapper.PictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public List<TreeNode> getNodeTree(Long account) {
        // 查询数据
        List<Picture> pictures = pictureMapper.findNodeByAccountAndNullUrl(account);
        List<TreeNode> treeNodeList = new ArrayList<>();

        // 用于存储每个层级对应的节点
        Map<String, TreeNode> levelNodeMap = new HashMap<>();

        // 根节点
        TreeNode root = new TreeNode();

        // 遍历查询结果
        for (Picture picture : pictures) {
            String level = picture.getLevel();
            TreeNode node = new TreeNode();
            node.setFileName(picture.getFileName());
            node.setLevel(level);

            // 将节点添加到 levelNodeMap 中
            levelNodeMap.put(level, node);

            // 找到父节点并添加当前节点为子节点
            if (level.length() > 2) {
                String parentLevel = level.substring(0, level.length() - 2);
                TreeNode parentNode = levelNodeMap.get(parentLevel);
                if (parentNode != null) {
                    parentNode.addChild(node);
                }
            } else {
                // 如果是一级节点，添加到根节点下
                root.addChild(node);
            }
        }

        // 将根节点放入列表中返回
        treeNodeList.add(root);
        return treeNodeList;
    }

    @Override
    @Transactional
    public int addNewNode(TreeNode node, Long account) {
        Picture picture = new Picture();
        picture.setFileName(node.getFileName());
        picture.setLevel(node.getLevel());
        picture.setCreateDatetime(new Date()); // 设置创建时间为当前时间
        picture.setUpdateDatetime(new Date()); // 设置更新时间为当前时间
        picture.setAccount(account); // 设置当前用户账户 ID（需要实现）
        String path = getPreviousPath(node.getLevel(), account);
        // 生成 path
        picture.setPath(generatePath(node.getLevel(), node, path));

        // 检查 path 是否已经存在
        int count = pictureMapper.checkPathExists(picture.getPath());
        if (count > 0) {
            // 如果 path 已经存在，返回 -1 表示插入失败
            return -1;
        }

        return pictureMapper.addNewNode(picture);
    }

    @Override
    public int renameNode(TreeNode node, Long account) {
        try {
            Picture picture = pictureMapper.findNodeByAccountAndLevel(account, node.getLevel());

            // 找到路径中最后一个斜杠的位置
            String directory = picture.getPath().substring(0, picture.getPath().lastIndexOf("/") + 1);

            picture.setUpdateDatetime(new Date());
            picture.setPath(directory + node.getFileName());
            picture.setFileName(node.getFileName());
            if (pictureMapper.checkPathExists(picture.getPath()) > 0) {
                return -1;
            }
            pictureMapper.updateNodeByAccountAndLevel(picture);
            String path = picture.getPath();
            int pathLength = picture.getPath().length();
            List<Picture> pictures = pictureMapper.findByLevelPrefix(node.getLevel().length(), node.getLevel(), account);
            for (Picture picture1 : pictures) {
                String originalPath = picture1.getPath();
                if (originalPath.length() >= pathLength) {
                    String newPathPrefix = path.substring(0, pathLength);
                    String originalPathSuffix = originalPath.substring(pathLength);
                    String newPath = newPathPrefix + originalPathSuffix;
                    picture1.setPath(newPath);
                }
                pictureMapper.updateNodeByAccountAndLevel(picture1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public int deleteNode(TreeNode treeNode, Long account) {
        int length = treeNode.getLevel().length();
        try {
            List<Picture> pictures = pictureMapper.findByLevelPrefix(length, treeNode.getLevel(), account);
            for (Picture picture : pictures) {
                pictureMapper.deleteNodeById(picture.getEid());
            }
            // 查询 level 大于指定前缀的节点
            List<Picture> modifyLevelPictures = pictureMapper.findModifyNodeByLevelPrefix(treeNode.getLevel());

            // 遍历并修改 level
            for (Picture picture : modifyLevelPictures) {
                String originalLevel = picture.getLevel();
                String prefixLevel = originalLevel.substring(0, length);
                int prefixLevelValue = Integer.parseInt(prefixLevel) - 1;
                prefixLevel = String.format("%0" + length + "d", prefixLevelValue);
                String laterLevel = originalLevel.substring(length);
                String newLevel = prefixLevel + laterLevel;
                picture.setLevel(newLevel);
                pictureMapper.updateLevelByAccountAndPath(picture.getAccount(), picture.getPath(), picture.getLevel());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public String getFilepath(Long account, String level) {
        return pictureMapper.findNodeByAccountAndLevel(account, level).getPath();
    }

    @Override
    public int upload(Picture[] picture) {
        try {
            for (Picture picture1 : picture) {
                String level = picture1.getLevel();
                picture1.setPath(pictureMapper.findNodeByAccountAndLevel(picture1.getAccount(), level.length() > 2 ? level.substring(0, level.length() - 2) : level).getPath() + "/" + picture1.getFileName());
                pictureMapper.addNewNode(picture1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int getFileChildren(Long account, String level) {
        List<Picture> pictures = pictureMapper.findByLevelPrefix(level.length(), level, account);
        // 使用流过滤掉 level 完全匹配的项
        List<Picture> filteredPictures = pictures.stream()
                .filter(picture -> !Objects.equals(picture.getLevel(), level))
                .toList();
        return filteredPictures.size();
    }

    @Override
    public String[] getPictureNameList(Long account, String level) {
        List<Picture> pictures = pictureMapper.findByLevelPrefix(level.length(), level, account);
        // 使用流过滤掉 level 完全匹配的项
        List<Picture> filteredPictures = pictures.stream()
                .filter(picture -> !Objects.equals(picture.getLevel(), level))
                .filter(picture -> picture.getUrl() != null && !picture.getUrl().isEmpty())
                .toList();
        return filteredPictures.stream()
                .map(Picture::getFileName)
                .toArray(String[]::new);
    }

    @Override
    public int deletePicture(String[] pictureNameList, Long account) {
        try {
            for (String pictureName: pictureNameList) {
                pictureMapper.deletePictureByAccountAndFileName(account, pictureName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    @Transactional
    public boolean renamePicture(ReNameDTO reNameDTO) {
        try {
            // 根据旧文件名和账户查找记录
            Picture picture = pictureMapper.findNodeByAccountAndFileName(reNameDTO.getAccount(), reNameDTO.getOldName());
            System.out.println(reNameDTO.getOldName());
            if (picture == null) {
                return false;
            }

            // 获取文件路径的目录部分
            String directory = picture.getPath().substring(0, picture.getPath().lastIndexOf("/") + 1);
            String newPath = directory + reNameDTO.getNewName();
            String newUrl = "/server/uploads/" + reNameDTO.getNewName();
            
            // 检查新路径是否已存在
            if (pictureMapper.checkPathExists(newPath) > 0) {
                return false;
            }

            // 检查新的 URL 是否已存在
            if (pictureMapper.checkUrlExists(newUrl) > 0) {
                return false;
            }

            // 使用 account 和 oldName 更新记录
            return pictureMapper.updateNodeByAccountAndOldName(
                reNameDTO.getAccount(),
                reNameDTO.getOldName(),
                reNameDTO.getNewName(),
                newPath,
                newUrl,
                new Date()
            ) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getPictureNames(Long account) {
        return pictureMapper.getPictureNames(account);
    }

    private String generatePath(String level, TreeNode node, String previousPath) {
        // 按每两位拆分 level
        String[] levels = level.split("(?<=\\G..)");

        // 遍历树结构，根据 level 查找对应的文件名
        TreeNode currentNode = node;
        StringBuilder path = new StringBuilder(previousPath);

        for (String l : levels) {
            if (currentNode == null) {
                break;
            }
            // 添加当前节点的文件名到路径
            path.append("/");
            path.append(currentNode.getFileName());

            // 查找下一层节点
            TreeNode nextNode = null;
            for (TreeNode child : currentNode.getChildren()) {
                if (child.getLevel().equals(level.substring(0, l.length()))) {
                    nextNode = child;
                    break;
                }
            }
            currentNode = nextNode;
        }

        return path.toString();
    }

    private String getPreviousPath(String level, Long account) {
        // 检查 level 的长度是否大于 2，如果是则截断最后两位
        if (level != null && level.length() > 2) {
            level = level.substring(0, level.length() - 2);
        }
        if (pictureMapper.findNodeByAccountAndLevel(account, level) != null) {
            return pictureMapper.findNodeByAccountAndLevel(account, level).getPath();
        } else {
            return "";
        }
    }

}
