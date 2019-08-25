package com.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class FileComparisonUtil{
    /**
     * 句末符号定义
     */
    private  final String []endSymbol={".","。","?","？","!","！",",","，","；",";"};

    /**
     * 标记标签
     */
    private  String startLable="<SPAN STYLE=\"background-color:#\" TITLE=\"@\">";
    private  String endLable="</SPAN>";
    /**
     * 字数限制
     */
    private  JSONArray limit=null;
    /**
     * 统计重复字数
     */
    private  int repeat=0;
    /**
     * 统计总字数
     */
    private  int countWord=0;
    /**
     * 验证是否有超过最大字数限制
     * 默认为TRUE 没有超过
     * FALSE 超过
     *
     * 字数限制 JSONArray limit 中 必须有一条数据没有最大值及maxnum值为空，否则一直为通过
     */
    private boolean isPass=true;

    private String CopNote;
    private String CopOrig;

    public String getCopNote(){
        return this.CopNote;
    }
    public String getCopOrig(){
        return this.CopOrig;
    }
    public int getRepeat(){
        return this.repeat;
    }
    public int getCountWord(){
        return this.countWord;
    }
    public boolean getIsPass(){
        return this.isPass;
    }
    public String getRatio(){
        return String.format("%.2f",Double.valueOf(this.repeat*100.0/this.countWord));
    }
    /**
     * 获取文件内容
     * 根据文件后缀获取内容，支持doc、docx、pdf、txt、html格式
     * @param mf
     * @return
     * @throws Exception
     */
    public static StringBuffer getContent(MultipartFile mf){
        StringBuffer retl=new StringBuffer();
        InputStream oStream=null;
        try {
            oStream = mf.getInputStream();
            String name = mf.getOriginalFilename();
            if (name.toLowerCase().endsWith(".txt")) {//txt文本 默认GBK
                retl = HtmlToText.getContent(oStream,"GBK");
                String ret=new String(retl.toString().getBytes(),"UTF-8");
                retl=new StringBuffer(ret);
            } else if (name.toLowerCase().endsWith(".doc")) {
                WordExtractor ex = new WordExtractor(oStream);
                retl.append(ex.getText());
            } else if (name.toLowerCase().endsWith(".docx")) {
                XWPFDocument xwpfDocument = new XWPFDocument(oStream);
                POIXMLTextExtractor ex = new XWPFWordExtractor(xwpfDocument);
                retl.append(ex.getText());
            } else if (name.toLowerCase().endsWith(".pdf")) {
                PdfReader reader = new PdfReader(oStream);
                PdfReaderContentParser parser = new PdfReaderContentParser(reader);
                TextExtractionStrategy strategy;
                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                    strategy = parser.processContent(i,
                            new SimpleTextExtractionStrategy());
                    retl.append(strategy.getResultantText());
                }
            }else {//其他格式 按 UTF-8 文本读取
                retl = HtmlToText.getContent(oStream,"UTF-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(oStream !=null) {
                    oStream.close();
                }
            }catch (Exception e){}
        }
        return retl;
    }

    /**
     * 根据位置信息插入到内容相应位置
     * @param content 内容
     * @param keyArry 位置信息，1 符号 2 语气词 3  重复字
     */
    public static void Assemble(StringBuffer content, JSONArray keyArry){
        //int fs=0;
        int is=0;
        for (int i=0;i<keyArry.size();i++){
            JSONObject obj=keyArry.getJSONObject(i);
            String f=obj.getString("key");//插入值
            int site=obj.getIntValue("site");//位置
            int type=obj.getIntValue("type");//类型
            int len=obj.getIntValue("len");//长度
            if(type==-3 && f.indexOf("<SPAN STYLE=")>-1){
                //颜色标记开始部分特殊处理
                List sf=keyArry.stream().filter(o-> ((JSONObject) o).getIntValue("site")==site).collect(Collectors.toList());
                sf.sort(Comparator.comparing(o -> ((JSONObject) o).getIntValue("type")).reversed());
                for(int z=0;z<sf.size();z++){
                    JSONObject o=(JSONObject)sf.get(z);
                    content.insert(site + is, (o.getIntValue("type") == 1 ? StringEscapeUtils.unescapeJava(o.getString("key")) : o.getString("key")));
                    is += o.getIntValue("len");
                }

                i=i+sf.size()-1;
            }else {
                content.insert(site + is, (type == 1 ? StringEscapeUtils.unescapeJava(f) : f));
                is += len;
            }
        }
    }

    /**
     *根据关键字获取每个关键字在内容中的位置
     * @param content 内容
     * @param keyArry 关键字
     * @param siteBuf 位置信息
     */
    public  void getSite(StringBuffer content,JSONArray keyArry,StringBuffer siteBuf){
        for (int i=0;i<keyArry.size();i++){
            JSONObject obj=keyArry.getJSONObject(i);
            String f=(1==obj.getIntValue("types")?StringEscapeUtils.unescapeJava(obj.getString("val")):obj.getString("val"));
            int len=f.length();
            if(obj.getIntValue("types")!=2) {
                for (int x, y = 0; (x = content.indexOf(f, y)) > -1;) {
                    if (siteBuf.length()==1)
                        siteBuf.append("{'site':" + x + ",'key':'" + ("'".equals(f) ? "\\'" : f) + "','type':" + obj.getString("types") + ",'len':" + len + "}");
                    else
                        siteBuf.append(",{'site':" + x + ",'key':'" + ("'".equals(f) ? "\\'" : f) + "','type':" + obj.getString("types") + ",'len':" + len + "}");
                    y = x + len;
                }
            }else{
                for(int e=0;e<this.endSymbol.length;e++){
                    String tf=f+this.endSymbol[e];
                    for (int x, y = 0; (x = content.indexOf(tf, y)) > -1; ) {
                        if (siteBuf.length()==1)
                            siteBuf.append("{'site':" + x + ",'key':'" + f + "','type':" + obj.getString("types") + ",'len':" + len + "}");
                        else
                            siteBuf.append(",{'site':" + x + ",'key':'" +f+ "','type':" + obj.getString("types") + ",'len':" + len + "}");
                        y = x + len;
                    }
                }
            }
        }
    }

    /**
     * 根据过滤字符位置获取相对于去掉过滤字内容中的位置
     * @param siteBuf
     */
    public static void getRelatSite(StringBuffer siteBuf){
        siteBuf.append("]");
        JSONArray siteJ=JSON.parseArray(siteBuf.toString());
        siteJ.sort(Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("site")));
        JSONObject tmp=null;
        int len=0;
        for (int i=0;i<siteJ.size();i++){
            JSONObject obj=siteJ.getJSONObject(i);
            if(obj.getIntValue("type")==2){//语气词特殊处理
                if(tmp!=null && (tmp.getString("key").indexOf(obj.getString("key"))<0 ||(tmp.getString("key").equals(obj.getString("key"))))){
                    obj.put("site",obj.getIntValue("site")-len);
                    len +=obj.getIntValue("len");
                    tmp=null;
                } else if(tmp==null){
                    tmp=siteJ.getJSONObject(i);;
                    obj.put("site",obj.getIntValue("site")-len);
                    len +=obj.getIntValue("len");
                }else{
                    siteJ.remove(obj);
                    i--;
                }
            }else {
                obj.put("site",obj.getIntValue("site")-len);
                len +=obj.getIntValue("len");
                tmp=null;
            }
        }
        String s=siteJ.toString();
        siteBuf.delete(0,siteBuf.length());
        siteBuf.append(s.substring(0,s.length()-1));
    }

    /**
     * 重新构建字数限制对象
     * @param keywords
     */
    public  void refactLimit(JSONArray keywords){
        StringBuffer limitbuf=new StringBuffer("[");

        for(int i=0;i<keywords.size();i++){
            JSONObject obj=keywords.getJSONObject(i);
            if(i==0)
                limitbuf.append("{'min':'"+obj.getString("minnum")+"','max':'"+obj.getString("maxnum")+"','val':'"+startLable.replaceAll("#",obj.getString("fcolor"))+"','len':"+startLable.replaceAll("#",obj.getString("fcolor")).length()+"}");
            else
                limitbuf.append(",{'min':'"+obj.getString("minnum")+"','max':'"+obj.getString("maxnum")+"','val':'"+startLable.replaceAll("#",obj.getString("fcolor"))+"','len':"+startLable.replaceAll("#",obj.getString("fcolor")).length()+"}");
        }
        limitbuf.append("]");
        limit=JSON.parseArray(limitbuf.toString());
        limit.sort(Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("min")));
    }

    /**
     * 对过滤对象进行整理排序，语气词 按字数降序。语气词在前，符号在后
     */
    private void sortFilter(JSONArray filter){
        JSONArray cArray=new JSONArray();
        JSONArray fArray=new JSONArray();
        for(int i=0;i<filter.size();i++){
            JSONObject obj=filter.getJSONObject(i);
            if(obj.getIntValue("types")==1){
                fArray.add(obj);
            }else{
                cArray.add(obj);
            }
        }
        cArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("val").length()).reversed());
        filter.clear();
        filter.addAll(cArray);
        filter.addAll(fArray);
    }
    /**
     * 内容对比
     * filter 格式为[{val：值，types：值}]。val: 过滤的内容，types：类型，1 符号 2 语气词。
     * keywords 格式为[{minnum:值,maxnum:值,fcolor:值}]. minnum:大于等于；maxnum：小于；fcolor：颜色
     * @param original 原文件
     * @param notes 笔记文件
     * @param filter 过滤字
     * @param keywords 限制字数区间
     */
    public  void comparedTo(StringBuffer original,StringBuffer notes,JSONArray filter,JSONArray keywords){
        //按字符长短降序排列
        sortFilter(filter);
        //getCount(notes,filter);
        refactLimit(keywords);
        StringBuffer orfilter=new StringBuffer("[");//过滤字在原文件位置
        StringBuffer nofilter=new StringBuffer("[");//过滤字在笔记中位置
        //获取过滤字符位置
        getSite(original,filter,orfilter);
        getSite(notes,filter,nofilter);

        getRelatSite(orfilter);
        getRelatSite(nofilter);

        //删除内容中的过滤字符
        removeK(original,filter);
        removeK(notes,filter);
        this.countWord=notes.length();//过滤后笔记总字数
        this.CopNote=notes.toString();
        this.CopOrig=original.toString();
        comparedAToB(original,notes,orfilter,nofilter);
        orfilter.append("]");
        JSONArray osite=JSON.parseArray(orfilter.toString());
        osite.sort(Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("site")));
        nofilter.append("]");
        JSONArray nsite=JSON.parseArray(nofilter.toString());
        nsite.sort(Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("site")));

        Assemble(notes,nsite);
        Assemble(original,osite);
    }

    /**
     * 统计笔记总字数
     * @param notes
     */
    private void getCount(StringBuffer notes,JSONArray filter){
        String s=notes.toString();
        for(int i=0;i<filter.size();i++){
            JSONObject obj=filter.getJSONObject(i);
            if("1".equals(obj.getString("types"))){
                s=s.replaceAll(escapeExpr(obj.getString("val")),"");
            }else{
                for(int z=0;z<this.endSymbol.length;z++){
                    s = s.replaceAll(obj.getString("val")+escapeExpr(this.endSymbol[z]), "");
                }
            }
        }
        this.countWord=s.length();
    }
    /**
     * 对比两个文本，查找相同部分
     * @param original
     * @param notes
     */
    private  void comparedAToB(StringBuffer original,StringBuffer notes,StringBuffer osite,StringBuffer nsite){
        //最低限制
        int min=limit.getJSONObject(0).getIntValue("min");
        int sit=0;
        for(int i=0;i<notes.length()-1;i++){
            String maxCh="";//存放最大字串
            String os="";
            String ns="";
            int start=min;
            if(i+min<sit+1){
                start=sit+1;
            }else{
                sit=0;
                start +=i;
            }
            for(;start<notes.length()+1;start++) {
                String temp=notes.substring(i, start);
                if(original.indexOf(temp)>-1){
                    maxCh=temp;
                    sit=start;
                    os="{'site':"+original.indexOf(temp)+",'key':'#key','type':-3,'len':#len},{'site':"+(original.indexOf(temp)+temp.length())+",'key':'"+endLable+"','type':3,'len':"+endLable.length()+"}";
                    ns="{'site':"+i+",'key':'#key','type':-3,'len':#len},{'site':"+(i+temp.length())+",'key':'"+endLable+"','type':3,'len':"+endLable.length()+"}";
                }else{
                    break;
                }
            }
            if(!maxCh.equals("")) {
                String key = "";
                for (int o = 0; o < limit.size(); o++) {//计算颜色
                    int mi = limit.getJSONObject(o).getIntValue("min");
                    int ma = limit.getJSONObject(o).getString("max").equals("") ? Integer.MAX_VALUE : limit.getJSONObject(o).getIntValue("max");
                    if (mi <= maxCh.length() && ma > maxCh.length()) {
                        key = limit.getJSONObject(o).getString("val");
                        //如果字数大于设置最大字数，验证不通过
                        if(ma==Integer.MAX_VALUE) isPass=false;
                        break;
                    }
                }
                //记录重复字个数
                this.repeat +=maxCh.length();
                key=key.replaceAll("@",maxCh);
                os=os.replaceAll("#key",key);
                os=os.replaceAll("#len",key.length()+"");
                osite.insert(1,os);
                ns=ns.replaceAll("#key",key);
                ns=ns.replaceAll("#len",key.length()+"");
                nsite.insert(1,ns);
            }
        }
    }

    /**
     * 去除过滤字符
     * @param sbuf
     * @param filter
     */
    public  void removeK(StringBuffer sbuf,JSONArray filter){
        String s=sbuf.toString();
        for(int i=0;i<filter.size();i++){
            JSONObject obj=filter.getJSONObject(i);
            if(obj.getIntValue("types")==1) {
                s = s.replaceAll(escapeExpr(obj.getString("val")), "");
            }else{
                for(int z=0;z<this.endSymbol.length;z++){
                    s = s.replaceAll(obj.getString("val")+escapeExpr(this.endSymbol[z]), "");
                }
            }
        }
        sbuf.delete(0,sbuf.length());
        sbuf.append(s);
    }
    public static String escapeExpr(String keyword){
        if(StringUtils.isNotBlank(keyword)){
            String []fbsArr={"$","(",")","*","+",".","[","]","?","^","{","}","|"};
            for(String key:fbsArr){
                if(keyword.contains(key))
                    keyword=keyword.replace(key,"\\"+key);
            }
        }
        return keyword;
    }

}
