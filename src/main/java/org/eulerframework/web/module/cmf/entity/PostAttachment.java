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
package org.eulerframework.web.module.cmf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.eulerframework.web.core.base.entity.UUIDEntity;

/**
 * @author cFrost
 *
 */
@Entity
@Table(name = "cmf_post_attach")
public class PostAttachment extends UUIDEntity<PostAttachment> {

    @Column(name = "file_id", length=36)
    private String fileId;

    @Column(name = "post_id", length=36, nullable = false)
    private String postId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="post_id", referencedColumnName="id", nullable = false)
//    @JsonIgnore
//    private Post post;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "show_order", nullable = false)
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