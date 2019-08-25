package com.controller;

import com.BaseEntity.ResultData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.base.util.FileComparisonUtil;
import com.entity.Filter;
import com.entity.Keywords;
import com.service.FilterService;
import com.service.KeywordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/filecomp")
public class FileComparisonController {
    @Autowired
    private FilterService filterService;
    @Autowired
    private KeywordsService keywordsService;

    @PostMapping("/save")
    public ResultData get(@RequestParam("resuFile") MultipartFile resuFile,@RequestParam("compFile") MultipartFile compFile){
        Map rtmap=new HashMap<>();
        try {
            if(!resuFile.isEmpty() && !compFile.isEmpty()) {//判断两个附件不能为空
                StringBuffer original= FileComparisonUtil.getContent(resuFile);//原文件
                StringBuffer notes=FileComparisonUtil.getContent(compFile);//笔记文件
                if(original.length()>0 && notes.length()>0){
                    List<Filter> filterList=filterService.findByAll();
                    List<Keywords> keywordsList=keywordsService.findByAll();
                    //去除重复过滤字
                    filterList=filterList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Filter::getVal))), ArrayList::new));
                    FileComparisonUtil fileComparisonUtil=new FileComparisonUtil();
                    fileComparisonUtil.comparedTo(original,notes, JSONArray.parseArray(JSON.toJSONString(filterList)),JSONArray.parseArray(JSON.toJSONString(keywordsList)));
                    String regEx="[\n\r]";
                    Pattern p=Pattern.compile(regEx);
                    //替换回车、换行 为html格式
                    rtmap.put("original",p.matcher(original).replaceAll("<br/>"));
                    rtmap.put("notes",p.matcher(notes).replaceAll("<br/>"));
                    rtmap.put("ratio",fileComparisonUtil.getRatio());//重复率
                    rtmap.put("repeat",fileComparisonUtil.getRepeat());//重复字数
                    rtmap.put("countWord",fileComparisonUtil.getCountWord());//笔记文件字数
                    rtmap.put("isPass",fileComparisonUtil.getIsPass());//验证是否超最大字数限制
                    rtmap.put("copNote",fileComparisonUtil.getCopNote());
                    rtmap.put("copOrig",fileComparisonUtil.getCopOrig());

                }else{
                    return ResultData.error("文件类型错误！");
                }
            }else {
                return ResultData.error("文件内容不能为空！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultData.error("ERROR");
        }
        return ResultData.success(rtmap);
    }
}
