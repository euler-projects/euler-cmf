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
