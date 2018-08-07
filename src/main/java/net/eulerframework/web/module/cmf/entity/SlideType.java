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
@Table(name="CMF_SLIDE_TYPE")
public class SlideType extends NonIDEntity<SlideType, String> {

    @Id
    @Column(name = "TYPE")
    private String type;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCRIPTION", nullable = true, length = 1000)
    private String description;
    @Column(name = "ENABLED", nullable = false)
    private Boolean enabled;

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

    @Override
    public String getId() {
        return type;
    }

    @Override
    public void setId(String id) {
        this.type = id;
    }

    @Override
    public int compareTo(SlideType o) {
        return this.getId().compareTo(o.getId());
    }

}
