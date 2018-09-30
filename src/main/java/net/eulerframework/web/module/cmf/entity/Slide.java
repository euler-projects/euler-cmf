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

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import net.eulerframework.constant.EulerSysAttributes;
import net.eulerframework.web.core.base.entity.UUIDEntity;
import net.eulerframework.web.util.ServletUtils;

/**
 * @author cFrost
 *
 */
@Entity
@Table(name="cmf_slide")
public class Slide extends UUIDEntity<Slide> {

    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "locale", nullable = false)
    private Locale locale;
    @Column(name = "title", nullable = true)
    private String title;
    @Column(name = "file_id", nullable = false)
    private String fileId;
    @Column(name = "uri", nullable = true)
    private String uri;
    @Column(name = "description", nullable = true, length = 1000)
    private String description;
    @Column(name = "content", nullable = true, length = 1000)
    private String content;
    @Column(name = "show_order", nullable = false)
    private Integer order;
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
    public String getFileId() {
        return fileId;
    }
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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

    
    /**
     * 图片相对于WEB根目录的路径，包含ContextPath，以/开头
     * 
     * <p>eg.
     * <pre class="code">
     * ContextPath = "/demo", imagePath = "/demo/image/xxxxxxxxxxxx"
     * ContextPath = "", imagePath = "/image/xxxxxxxxxxxx"
     * </pre>
     */
    public String getImagePath() {
        if(StringUtils.hasText(fileId)) {
            return ServletUtils.getServletContext().getAttribute(EulerSysAttributes.IMAGE_DOWNLOAD_PATH_ATTR.value()) + "/"
                    + this.fileId;
        } else {
            return null;
        }
    }
    

}
