/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013-2017 cFrost.sun(孙宾, SUN BIN) 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * For more information, please visit the following website
 * 
 * https://eulerproject.io
 * https://github.com/euler-form/web-form
 * https://cfrost.net
 */
package net.eulerframework.web.module.cmf.entity;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.eulerframework.web.core.base.entity.UUIDEntity;
import net.eulerframework.web.module.file.conf.FileConfig;
import net.eulerframework.web.util.ServletUtils;

/**
 * @author cFrost
 *
 */
//@Entity
@Table(name="CMF_POST")
public class Post extends UUIDEntity<Post> {

    /**
     * 文章类型
     */
    @Column(name = "TYPE", nullable = false)
    private String type;
    /**
     * 文章语言
     */
    @Column(name = "LANGUAGE", nullable = false)
    private Locale language;
    /**
     * 文章标题
     */
    @Column(name = "title", nullable = false)
    private String title;
    /**
     * 文章题图归档文件名(可选字段,归档文件名即文档文件ID+后缀)
     */
    @Column(name = "THEME_PIC_AFN", nullable = true)
    private String themePictureArchiedFileName;
    /**
     * 文章题图相对于WEB根目录的路径，包含ContextPath，以/开头，并以文件的时间扩展名结尾
     * 
     * <p>eg.
     * <pre class="code">
     * ContextPath = "/demo", themePicturePath = "/demo/image/xxxxxxxxxxxx.jpg"
     * ContextPath = "", themePicturePath = "/image/xxxxxxxxxxxx.jpg"
     * </pre>
     */
    @Transient
    public String getThemePicturePath() {
        return ServletUtils.getServletContext().getAttribute(FileConfig.IMAGE_DOWNLOAD_PATH_ATTR) + "/"
                + this.themePictureArchiedFileName;
    }
    /**
     * 文章作者ID
     */
    @Column(name = "AUTHOR_ID", nullable = false, length = 36)
    private String authorId;
    /**
     * 文章创建时间
     */
    @Column(name = "CREATE_DATE", nullable = false)
    private Date createDate;
    /**
     * 文章更新时间
     */
    @Column(name = "UPDATE_DATE", nullable = false)
    private Date unpdateDate;
    /**
     * 地点(可选字段)
     */
    @Column(name = "LOCATION", nullable = true)
    private String location;
    /**
     * 文章摘要(可选字段)
     */
    @Column(name = "EXCERPT", nullable = true)
    private String excerpt;
    /**
     * 文章正文(可选字段)
     */
    @Column(name = "CONTENT", nullable = true, length = Integer.MAX_VALUE)
    private String content;
    /**
     * 文章显示顺序
     */
    @Column(name = "SHOW_ORDER", nullable = false)
    private Integer order;
    
    
    @Column(name = "FILE_ID", nullable = false)
    private String fileId;
    @Column(name = "URI", nullable = true)
    private String uri;
    @Column(name = "DESCRIPTION", nullable = true, length = 1000)
    private String description;
    public String getType() {
        return type;
    }
}
