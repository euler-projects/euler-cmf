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

import net.eulerframework.constant.EulerSysAttributes;
import net.eulerframework.web.core.base.entity.UUIDEntity;
import net.eulerframework.web.util.ServletUtils;

/**
 * @author cFrost
 *
 */
@Entity
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
    @Column(name = "LOCALE", nullable = false)
    private Locale locale;
    /**
     * 文章标题
     */
    @Column(name = "TITLE", nullable = false)
    private String title;
    /**
     * 文章题图归档文件名(可选字段,归档文件名即文档文件ID)
     */
    @Column(name = "THEME_PIC_AF_ID", nullable = true, length = 36)
    private String themePictureArchiedFileId;
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
     * 文章更新时间(发布时间)
     */
    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updateDate;
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
    /**
     * 是否置顶
     */
    @Column(name = "IS_TOP", nullable = false)
    private Boolean top;
    /**
     * 审核通过
     */
    @Column(name = "APPROVED", nullable = false)
    private Boolean approved;
    /**
     * 附加数据,JSON格式(可选字段)
     */
    @Column(name = "EXTRA_DATA", nullable = true, length = Integer.MAX_VALUE)
    private String extraData;
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThemePictureArchiedFileId() {
        return themePictureArchiedFileId;
    }

    public void setThemePictureArchiedFileId(String themePictureArchiedFileId) {
        this.themePictureArchiedFileId = themePictureArchiedFileId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    /**
     * 文章题图相对于WEB根目录的路径，包含ContextPath，以/开头，并以文件的时间扩展名结尾
     * 
     * <p>eg.
     * <pre class="code">
     * ContextPath = "/demo", themePicturePath = "/demo/image/xxxxxxxxxxxx.jpg"
     * ContextPath = "", themePicturePath = "/image/xxxxxxxxxxxx.jpg"
     * </pre>
     */
    public String getThemePicturePath() {
        return ServletUtils.getServletContext().getAttribute(EulerSysAttributes.IMAGE_DOWNLOAD_PATH_ATTR) + "/"
                + this.themePictureArchiedFileId;
    }
    
    @Transient
    private String authorUsername;

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
    public String getAuthorUsername() {
        return this.authorUsername;
    }
}
