package com.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("likes")
public class Like {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    int commentId;
    int userId;
}
