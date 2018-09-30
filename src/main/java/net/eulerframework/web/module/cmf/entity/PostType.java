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
@Table(name="cmf_post_type")
public class PostType extends NonIDEntity<PostType, String> {

    @Id
    @Column(name = "type")
    private String type;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = true, length = 1000)
    private String description;
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    @Column(name = "admin_page_suffix", nullable = false)
    private String adminPageSuffix;
    @Column(name = "view_page_suffix", nullable = false)
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
