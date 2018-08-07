/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.eulerframework.web.module.cmf.entity;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Transient
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
     * 附件列表
     */
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post")
//    @OrderBy("SHOW_ORDER ASC")
//    @Fetch(FetchMode.SELECT)
    @Transient
    private List<PostAttachment> postAttachments;
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

    public List<PostAttachment> getAttachments() {
        return postAttachments;
    }

    public void setAttachments(List<PostAttachment> postAttachments) {
        this.postAttachments = postAttachments;
    }

    @JsonIgnore
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
        if(StringUtils.hasText(themePictureArchiedFileId)) {
            return ServletUtils.getServletContext().getAttribute(EulerSysAttributes.IMAGE_DOWNLOAD_PATH_ATTR.value()) + "/"
                    + this.themePictureArchiedFileId;
        } else {
            return null;
        }
    }
    
    @Transient
    private String authorUsername;
    @Transient
    private ObjectMapper objectMapper = new ObjectMapper();

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
    
    public String getAuthorUsername() {
        return this.authorUsername;
    }

    public Map<String, Object> getExtra() {
        if(!StringUtils.hasText(this.getExtraData())) {
            return null;
        }
        
        JavaType extraType = this.objectMapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, Object.class);
        try {
            return this.objectMapper.readValue(this.getExtraData(), extraType);
        } catch (IOException e) {
            this.logger.error("Some problem happened with the post extra data parsing, postId: " + this.getId());
            return null;
        }
    }
}
