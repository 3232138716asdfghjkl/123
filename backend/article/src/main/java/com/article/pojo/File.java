package com.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("file")
@Data

public class File {
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    int articleId;
    String filename;
}
