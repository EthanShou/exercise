package com.utan.article.domain.DO;

import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class ArticleDO implements Serializable {
    private Long id;

    private Long uniquekey;

    private String title;

    private String titlesub;

    private String heightwidth;

    private Long time;

    private String tag;

    private String utantag;

    private String netorg;

    private Long createtime;

    private Integer year;

    private Integer month;

    private Integer day;

    private Integer flag;

    private Integer state;

    private String extend;

    private Long updatetime;

    private Integer weight;

    private Integer authorid;

    private String extendtags;

    private String videourl;

    private Byte isvideo;

    private Long publishtime;

    private String contextencode;

    private String context;

    private String picurlthumbnail;

    private String picurl;

    private String videocontext;

    public ArticleDO(Long id, Long uniquekey, String title, String titlesub, String heightwidth, Long time, String tag, String utantag, String netorg, Long createtime, Integer year, Integer month, Integer day, Integer flag, Integer state, String extend, Long updatetime, Integer weight, Integer authorid, String extendtags, String videourl, Byte isvideo, Long publishtime, String contextencode, String context, String picurlthumbnail, String picurl, String videocontext) {
        this.time = System.currentTimeMillis();
        this.createtime=this.time;
        this.updatetime=this.time;
        Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH) + 1;
        this.day = c.get(Calendar.DATE);

//        this.id = id;
        this.uniquekey = uniquekey;
        this.title = title;
        this.titlesub = titlesub;
        this.heightwidth = heightwidth;
//        this.time = time;
        this.tag = tag;
        this.utantag = utantag;
        this.netorg = netorg;
//        this.createtime = createtime;
//        this.year = year;
//        this.month = month;
//        this.day = day;
        this.flag = flag;
        this.state = state;
        this.extend = extend;
//        this.updatetime = updatetime;
        this.weight = weight;
        this.authorid = authorid;
        this.extendtags = extendtags;
        this.videourl = videourl;
        this.isvideo = isvideo;
        this.publishtime = publishtime;
        this.contextencode = contextencode;
        this.context = context;
        this.picurlthumbnail = picurlthumbnail;
        this.picurl = picurl;
        this.videocontext = videocontext;
    }
}
