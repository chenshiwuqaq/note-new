package org.ash.Controller;

import org.apache.ibatis.annotations.Param;
import org.ash.Dto.NodeAddDto;
import org.ash.Dto.NodeEditDto;
import org.ash.Service.editorServiceImpl;
import org.com.Entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/editor")
public class editorController {
    @Autowired
    private editorServiceImpl editorService;
    @PostMapping("/onSave")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("nodeId") String nodeId) {
        return editorService.handleFileUpload(file,nodeId);
    }
    @GetMapping("/onLoad")
    public Result loadTree(@Param("account") long account){
        return Result.success(editorService.uploadTree(account));
    }
    @PostMapping("/NodeAdd")
    public Result addNode(@RequestBody NodeAddDto node){
        return Result.success(editorService.addNode(node));
    }
    @PostMapping("/NodeUpdate")
    public Result editNode(@RequestBody NodeEditDto node){
        return Result.success(editorService.nodeEdit(node.getNodeLabel(), node.getNodeId()));
    }
    @PostMapping("/getFile")
    public Result getFile(@RequestParam("nodeId") String nodeId){
        return Result.success(editorService.getFileContent(nodeId));
    }
    @PostMapping("/NodeDelete")
    public Result deleteNode(@RequestParam("nodeId") String nodeId){
        return Result.success(editorService.deleteNode(nodeId));
    }
    @PostMapping("/NodeChangeIsCollected")
    public Result NodeChangeIsCollected(@RequestParam("nodeId")String nodeId){
        return Result.success(editorService.NodeChangeIsCollected(nodeId));
    }
    @GetMapping("getAllCollectedNode")
    public Result getAllCollectedNode(@Param("account") long account){
        return Result.success(editorService.getAllCollectedNodeBatch(account));
    }
}
