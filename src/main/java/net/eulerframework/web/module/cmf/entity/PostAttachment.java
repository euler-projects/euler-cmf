/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013-2018 cFrost.sun(孙宾, SUN BIN) 
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
 */
package net.eulerframework.web.module.cmf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.eulerframework.web.core.base.entity.UUIDEntity;

/**
 * @author cFrost
 *
 */
@Entity
@Table(name = "CMF_POST_ATTACH")
public class PostAttachment extends UUIDEntity<PostAttachment> {

    @Column(name = "FILE_ID", length=36)
    private String fileId;

    @Column(name = "POST_ID", length=36, nullable = false)
    private String postId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="POST_ID", referencedColumnName="ID", nullable = false)
//    @JsonIgnore
//    private Post post;

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @Column(name = "SHOW_ORDER", nullable = false)
    private Integer order;

    /**
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

//    /**
//     * @return the post
//     */
//    public Post getPost() {
//        return post;
//    }
//
//    /**
//     * @param post the post to set
//     */
//    public void setPost(Post post) {
//        this.post = post;
//    }

    /**
     * @return the postId
     */
    public String getPostId() {
        return postId;
    }

    /**
     * @param postId the postId to set
     */
    public void setPostId(String postId) {
        this.postId = postId;
    }
}
