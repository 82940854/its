

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.FileInputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Test {
    public static void getMaxSubString(String a,String b){
        String max="",min="";
        max=a;
        min=b;
//        //for(int i=0;i<min.length();i++){
//            for(int y=0;y+5<=min.length();y++){
//                String temp=min.substring(y,y+5);
//               // System.out.println("min内容："+temp);
//                if(max.indexOf(temp)>-1){
//                    System.out.println("max内容："+temp+"；位置："+max.indexOf(temp));
//                    for(int i=max.indexOf(temp);max.substring(i);){
//                        System.out.println("max内容："+temp+"；位置："+max.indexOf(temp));
//                    }
//                }
//            }
//        //}
//       // return "";
        for(int x=0;x<min.length()-1;x++){
            for(int y=0,z=min.length()-x;z<=min.length();y++,z++){
                String temp=min.substring(y,z);
                int addr=0;
                System.out.println("X="+x+";Y="+y);
                int n=0;
                while((n=max.indexOf(temp,addr))>-1){
                    System.out.println("start="+n+";end="+(n+temp.length()));
                    System.out.println(temp);
                    addr=n+temp.length();
                }
            }
        }
    }
    public static void sop(String str){
        System.out.println(str);
    }
    public static void main(String[] args) throws Exception {
//        JSONArray keyArry=JSONArray.parseArray("[{'a':1,'b':1},{'a':2,'b':2},{'a':3,'b':3},{'a':1,'b':4},{'a':3,'b':5},{'a':2,'b':6},{'a':1,'b':7}]");
//        System.out.println(keyArry);
//        for(int i=1;i<100;i++) {
//            keyArry.sort(Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("a")).reversed());
//            System.out.println(keyArry);
//        }

//        String a="[{'s':2,'t':3,'v':'<span>'},{'s':1,'t':2,'v':'吧'},{'s':1,'t':1,'v':'。'},{'s':1,'t':3,'v':'</span>'},{'s':1,'t':-1,'v':'<span>'}]";
//        Comparator<Object> s=Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("s"));
//        Comparator<Object> t=Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("t")).reversed();
//        Comparator<Object> all=s.thenComparing(t);
//        JSONArray jsonArray=JSONArray.parseArray(a);
//        List jso=jsonArray.stream().sorted(all).collect(Collectors.toList());
        System.out.println(StringEscapeUtils.unescapeJava("\n"));
//StringBuffer aa=new StringBuffer("点点滴滴发生地");
//System.out.println(aa.insert(1,"bbbb"));

//        String s1="你|你";
//        String s2="你你";
//        String s3="(";
//        String s4=StringEscapeUtils.escapeJava(s3);
//        System.out.println(s1.replaceAll("\\|",""));
//        String []endSymbol={"\\.","\\。","\\?","\\？","\\!","\\！","\\,","\\，","\\；","\\;"};
//        for(int i=0;i<endSymbol.length;i++) {
////            System.out.println(s2 + StringEscapeUtils.unescapeJava(endSymbol[i]));
////            System.out.println(s2 + StringEscapeUtils.escapeJava(endSymbol[i]));
//            String s3=s2+StringEscapeUtils.unescapeJava(endSymbol[i]);
//            System.out.println(s3);
//            System.out.println(s1.replaceAll(s2+StringEscapeUtils.escapeJava(endSymbol[i]), ""));
//        }
//       System.out.println(s2.substring(0,s2.length()+10));
//        getMaxSubString(s1,s2);
        //System.out.println(DateUtil.getWeekDay());
        //System.out.println(DateUtil.msGetWeeks("2019-07-28"));
        //System.out.println(DateUtil.getWeekDay());
//        Map<String,String> m=new HashMap<>();
//        m.put("\t","sss");
//        System.out.println(s1.indexOf("\t"));
//        StringBuffer aa=new StringBuffer(s2);
//        aa.insert(1,"bbbbbbbb");
//        System.out.println(aa);
       // readByText();

//        String jsonArrStr = "[ { \"ID\": \"2016-05-25\", \"Name\": \"Fargo Chan\" },{ \"ID\": \"2016-05-23\", \"Name\": \"Aaron Luke\" },{ \"ID\": \"2016-05-26\", \"Name\": \"Dilip Singh\" }]";
//        System.out.println("排序前："+jsonArrStr);
//        JSONArray jsonArr = JSON.parseArray(jsonArrStr);
//        倒序
//        jsonArr.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("ID")).reversed());
//        正序
//        jsonArr.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("ID")));
//        System.out.println("排序后："+jsonArr.toString());
//        String content ="a,b.c,d;e.f,g";
//        String filter="[{'key':','},{'key':'.'},{'key':';'}]";
//        JSONArray jsonArr = JSON.parseArray(filter);
//        JSONArray filterSite=getSite(content,jsonArr);
//        System.out.println("位置："+filterSite.toString());
//        filterSite.sort(Comparator.comparing(obj -> ((JSONObject) obj).getIntValue("site")));
//        System.out.println("排序后："+filterSite.toString());
//        StringBuffer content2 =new StringBuffer("abcdefg");
//        Assemble(content2,filterSite);
//        System.out.println(content2.toString());
//        //System.out.println("\t".length());
//
//        String a="12345x67890abcdxefgfh12345";
//        String b="12345x67890abcdefgfhpoeghabcdef12345";
//        int sit=0;
//        for(int i=0;i<a.length()-1;i++){
//            String aaa="";
//            int start=5;
//            if(i+5<sit+1){
//                start=sit+1;
//            }else{
//                sit=0;
//                start=i+5;
//            }
//            for(;start<a.length()+1;start++) {
//                String temp=a.substring(i, start);
//                if(b.indexOf(temp)>-1) {
//                    System.out.println("s="+i+";e="+start);
//                    aaa=temp;
//                    sit=start;
//                }else{
//                    break;
//                }
//            }
//            if(aaa.length()>0) {
//                //System.out.println("c==="+i);
//                System.out.println(aaa);
//            }
//        }
        //System.out.println("a;+s?b.c".replaceAll("\\.",""));
//        StringBuffer sb=new StringBuffer("1234567890abcdefgfhpoe");
//        System.out.println(sb.insert(0,"258"));
//        System.out.println(sb);

//        String ss="12";
////        System.out.println(ss.substring(0,ss.length()-1));
//        String regEx="[1\n|文d|\r。热\f、,.]";
//        Pattern p=Pattern.compile(regEx);
//        System.out.println(p.matcher(ss).replaceAll(""));
//        System.out.println(ss.substring(0,ss.length()-1));
    }
    public static String escapeExpr(String keyword){
        if(StringUtils.isNotBlank(keyword)){
            String []fbsArr={"\\","$","(",")","*","+",".","[","]","?","^","{","}","|"};
            for(String key:fbsArr){
                if(keyword.contains(key))
                    keyword=keyword.replace(key,"\\"+key);
            }
        }
        return keyword;
    }
    public static void Assemble(StringBuffer content,JSONArray keyArry){
        int fs=0;
        int is=0;
        for (int i=0;i<keyArry.size();i++){
            JSONObject obj=keyArry.getJSONObject(i);
            String f=obj.getString("key");
            int site=obj.getIntValue("site");
            int type=obj.getIntValue("type");
            int len=obj.getIntValue("len");
            if(type==1) {
                fs += len;
                content.insert(site+is,f);
            }else {
                content.insert(site+is+fs,f);
                is += len;
            }
        }
    }
public static JSONArray getSite(String content,JSONArray keyArry){
        StringBuffer siteBuf=new StringBuffer();
        siteBuf.append("[");
        for (int i=0;i<keyArry.size();i++){
            JSONObject obj=keyArry.getJSONObject(i);
            String f=obj.getString("key");
            int len=f.length();
            for(int x,y=0,z=0;(x=content.indexOf(f,y))>-1;z++){
                if(z==0)
                    siteBuf.append("{'site':"+x+",'key':'"+f+"','type':1,'len':"+len+"}");
                else
                    siteBuf.append(",{'site':"+x+",'key':'"+f+"','type':1,'len':"+len+"}");
                y=x+f.length();
            }
        }
        siteBuf.append(",{'site':1,'key':'<span>','type':2,'len':6}");
        siteBuf.append(",{'site':2,'key':'</span>','type':2,'len':7}");
        siteBuf.append("]");
        return JSON.parseArray(siteBuf.toString());
}
    public static List<Map> get(StringBuffer bufferStr){
        int x=0;
        int y=0;
        List addrelist=new ArrayList();
        while ((x=bufferStr.indexOf(",",y))>-1){
            Map map=new HashMap();
            map.put(x,",");
            addrelist.add(map);
        }
        return null;
    }
    public static void readByText() throws Exception {
        FileInputStream oStream = new FileInputStream("D://s.doc");
        WordExtractor ex=new WordExtractor(oStream);
        System.out.println(ex.getText());
    }
    }
