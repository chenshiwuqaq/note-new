package org.ash.demos.Controller;

import org.ash.demos.DTO.ReNameDTO;
import org.ash.demos.DTO.TreeNode;
import org.ash.demos.Entity.Picture;
import org.ash.demos.Service.PictureService;
import org.com.Entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @GetMapping ("/getNodeTree")
    public Result getNodeTree(@RequestParam("account") Long account){
        return Result.success(pictureService.getNodeTree(account));
    }

    @PostMapping("/addNewNode")
    public Result addNewNode(@RequestBody TreeNode treeNode, @RequestParam("account") Long account){
        return Result.success(pictureService.addNewNode(treeNode, account));
    }

    @PostMapping("/renameNode")
    public Result renameNode(@RequestBody TreeNode treeNode, @RequestParam("account") Long account){
        return Result.success(pictureService.renameNode(treeNode, account));
    }

    @PostMapping("/deleteNode")
    public Result deleteNode(@RequestBody TreeNode treeNode, @RequestParam("account") Long account){
        return Result.success(pictureService.deleteNode(treeNode, account));
    }

    @GetMapping("/getFilepath")
    public Result getFilepath(@RequestParam("account") Long account, @RequestParam("level") String level){
        return Result.success(pictureService.getFilepath(account, level));
    }

    @PostMapping("/upload")
    public Result upload(@RequestBody Picture[] pictures) { // 接收 Picture 数组
        System.out.println("Received pictures: " + Arrays.toString(pictures)); // 打印接收到的数据
        return Result.success(pictureService.upload(pictures));
    }

    @GetMapping("/getFileChildren")
    public Result getPictureLevel(@RequestParam("account") Long account, @RequestParam("level") String level){
        return Result.success(pictureService.getFileChildren(account, level));
    }

    @GetMapping("/getPictureNameList")
    public Result getPictureNameList(@RequestParam("account") Long account, @RequestParam("level") String level){
        return Result.success(pictureService.getPictureNameList(account, level));
    }

    @PostMapping("/deletePicture")
    public Result deletePicture(@RequestBody String[] pictureNameList, @RequestParam("account") Long account) {
        return Result.success(pictureService.deletePicture(pictureNameList, account));
    }
    @PostMapping("/renamePicture")
    public Result renamePicture(@RequestBody ReNameDTO reNameDTO) {
        return Result.success(pictureService.renamePicture(reNameDTO));
    }
    @PostMapping("/getPictureNames")
    public Result getPictureNames(@RequestParam("account") Long account) {
        return Result.success(pictureService.getPictureNames(account));
    }
}
