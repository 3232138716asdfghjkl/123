package com.user.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVo extends Comment {
    private String avatar;
    private String username;
    private int like;
}
