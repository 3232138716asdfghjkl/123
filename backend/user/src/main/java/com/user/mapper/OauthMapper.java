package com.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.user.pojo.Like;
import com.user.pojo.Oauth;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthMapper  extends BaseMapper<Oauth> {
}
