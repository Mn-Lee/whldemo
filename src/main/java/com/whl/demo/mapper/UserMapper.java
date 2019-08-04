package com.whl.demo.mapper;

import com.whl.demo.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,bio,github_id) " +
                         "values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{githubId})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findUserByToken(@Param("token") String token);

    @Select("select * from user where github_id=#{github_id}")
    User findUserByGitHubId(@Param("github_id") Long GithubId);

    @Update("update user set editor_id=#{id}+10000 where id = #{id}")
    void setEditorIdById(@Param("id") int id);

    @Select("select editor_id from user where id=#{id}")
    Integer findEditorIdById(@Param("id") Integer id);
}
