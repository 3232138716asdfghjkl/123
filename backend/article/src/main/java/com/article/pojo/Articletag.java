package com.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("article_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Articletag {
   @TableId(value = "id", type = IdType.AUTO)
   Integer id;

   int articleId;
   int tagId;
}
