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

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.eulerframework.constant.EulerSysAttributes;
import net.eulerframework.web.core.base.entity.UUIDEntity;
import net.eulerframework.web.util.ServletUtils;

/**
 * @author cFrost
 *
 */
@Entity
@Table(name="CMF_SLIDE")
public class Slide extends UUIDEntity<Slide> {

    @Column(name = "TYPE", nullable = false)
    private String type;
    @Column(name = "LOCALE", nullable = false)
    private Locale locale;
    @Column(name = "title", nullable = true)
    private String title;
    @Column(name = "FILE_ID", nullable = false)
    private String fileId;
    @Column(name = "URI", nullable = true)
    private String uri;
    @Column(name = "DESCRIPTION", nullable = true, length = 1000)
    private String description;
    @Column(name = "CONTENT", nullable = true, length = 1000)
    private String content;
    @Column(name = "SHOW_ORDER", nullable = false)
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
        return ServletUtils.getServletContext().getAttribute(EulerSysAttributes.IMAGE_DOWNLOAD_PATH_ATTR) + "/"
                + this.fileId;
    }
    

}
