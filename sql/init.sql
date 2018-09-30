-- 图片类型表
CREATE TABLE cmf_slide_type
(
  type        VARCHAR(255)  NOT NULL PRIMARY KEY,
  name        VARCHAR(255)  NOT NULL,
  description VARCHAR(1000) NULL,
  enabled     BIT           NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_bin
  COMMENT ='图片类型表';

-- 图片表
CREATE TABLE cmf_slide
(
  id          VARCHAR(36)   NOT NULL PRIMARY KEY,
  title       VARCHAR(255)  NULL,
  type        VARCHAR(255)  NOT NULL,
  uri         VARCHAR(255)  NULL,
  content     VARCHAR(1000) NULL,
  description VARCHAR(1000) NULL,
  file_id     VARCHAR(255)  NOT NULL,
  locale      VARCHAR(255)  NOT NULL,
  show_order  INT           NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_bin
  COMMENT ='图片表';

-- 内容类型表
CREATE TABLE cmf_post_type
(
  type              VARCHAR(255)  NOT NULL PRIMARY KEY,
  name              VARCHAR(255)  NOT NULL,
  description       VARCHAR(1000) NULL,
  admin_page_suffix VARCHAR(255)  NOT NULL,
  view_page_suffix  VARCHAR(255)  NOT NULL,
  enabled           BIT           NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_bin
  COMMENT ='内容类型表';

-- 内容表
CREATE TABLE cmf_post
(
  id              VARCHAR(36)  NOT NULL PRIMARY KEY,
  type            VARCHAR(255) NOT NULL,
  title           VARCHAR(255) NOT NULL,
  theme_pic_af_id VARCHAR(36)  NULL,
  author_id       VARCHAR(36)  NOT NULL,
  excerpt         VARCHAR(255) NULL,
  content         LONGTEXT     NULL,
  create_date     DATETIME     NOT NULL,
  update_date     DATETIME     NOT NULL,
  extra_data      LONGTEXT     NULL,
  locale          VARCHAR(255) NOT NULL,
  show_order      INT          NOT NULL,
  is_top          BIT          NOT NULL,
  approved        BIT          NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_bin
  COMMENT ='内容表';

-- 内容附件表
CREATE TABLE cmf_post_attach
(
  id         VARCHAR(36)  NOT NULL PRIMARY KEY COMMENT '附件ID',
  file_id    VARCHAR(36)  NOT NULL COMMENT '文件归档ID',
  file_name  VARCHAR(255) NOT NULL COMMENT '附件文件名',
  post_id    VARCHAR(36)  NOT NULL COMMENT '所属内容ID',
  show_order INT          NOT NULL COMMENT '附件显示顺序'
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_bin
  COMMENT ='内容附件表';
