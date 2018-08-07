package net.eulerframework.web.module.cmf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.eulerframework.web.core.base.entity.NonIDEntity;

/**
 * @author cFrost
 *
 */
@Entity
@Table(name="CMF_POST_TYPE")
public class PostType extends NonIDEntity<PostType, String> {

    @Id
    @Column(name = "TYPE")
    private String type;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCRIPTION", nullable = true, length = 1000)
    private String description;
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;
    @Column(name = "ADMIN_PAGE_SUFFIX", nullable = false)
    private String adminPageSuffix;
    @Column(name = "VIEW_PAGE_SUFFIX", nullable = false)
    private String viewPageSuffix;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAdminPageSuffix() {
        return adminPageSuffix;
    }

    public void setAdminPageSuffix(String adminPageSuffix) {
        this.adminPageSuffix = adminPageSuffix;
    }

    public String getViewPageSuffix() {
        return viewPageSuffix;
    }

    public void setViewPageSuffix(String viewPageSuffix) {
        this.viewPageSuffix = viewPageSuffix;
    }

    @Override
    public String getId() {
        return type;
    }

    @Override
    public void setId(String id) {
        this.type = id;
    }

    @Override
    public int compareTo(PostType o) {
        return this.getId().compareTo(o.getId());
    }

}
