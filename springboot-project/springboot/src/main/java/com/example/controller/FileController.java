package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Mapper.FileMapper;
import com.example.common.Result;
import com.example.entity.MyFile;
import com.example.entity.User;
import com.example.utils.TokenUtils;
import nonapi.io.github.classgraph.utils.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传相关接口
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    /**
     *  文件上传接口
     * @param file 接口传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String type = FileUtil.extName(originalFileName);
        long size = file.getSize();

        //  先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断配置的文件目录是否存在
        if (!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }

        // 定义一个文件唯一的标志码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid);

        String url;
        String md5;

        file.transferTo(uploadFile);
        md5 = SecureUtil.md5(uploadFile);
        MyFile myFile = getFileMd5(md5);
        if (myFile != null){
            url = myFile.getUrl();
            uploadFile.delete();
        }else{
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://localhost:9090/file/"+fileUuid;
        }

        //  存储数据库
        MyFile saveFile = new MyFile();
        saveFile.setName(originalFileName);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);

        fileMapper.insert(saveFile);
        return url;
    }


    /**
     *  文件下载接口  http://localhost:9090/file/{fileUuid}
     * @param fileUuid 文件标志
     * @param response  网络响应
     * @throws IOException
     */
    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标志码获取文件
        File uploadFile = new File(fileUploadPath + fileUuid);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();

        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUuid,"UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }


    private MyFile getFileMd5(String md5){
        //查询是否有相同的md5
        QueryWrapper<MyFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<MyFile> myFiles = fileMapper.selectList(queryWrapper);
        return myFiles.size() == 0 ? null : myFiles.get(0);
    }


    @GetMapping("/page")
    public Result findPage(@RequestParam int pageNum,
                           @RequestParam int pageSize,
                           @RequestParam String name){
        Page<MyFile> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<MyFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),MyFile::getName,name);
        queryWrapper.eq(MyFile::getIsDelete,false);

        fileMapper.selectPage(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        MyFile myFile = fileMapper.selectById(id);
        myFile.setIsDelete(true);
        fileMapper.updateById(myFile);
        return Result.success();
    }


    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        QueryWrapper<MyFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<MyFile> myFiles = fileMapper.selectList(queryWrapper);
        for (MyFile myFile : myFiles) {
            myFile.setIsDelete(true);
            fileMapper.updateById(myFile);
        }
        return Result.success();
    }


    @PostMapping("/update")
    public Result save(@RequestBody MyFile myFile){
        return Result.success(fileMapper.updateById(myFile));
    }

}
