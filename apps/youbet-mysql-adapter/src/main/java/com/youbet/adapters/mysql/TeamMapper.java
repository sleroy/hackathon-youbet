package com.youbet.adapters.mysql;

import com.youbet.ports.matchsystem.Team;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeamMapper {
    
    @Select("select * from Team where name=#{name}")
    Team selectTeam(@Param("name") String name);
    
    @Insert("insert into Team(name, team_api_id, short_name) values(#{name}, #{team_api_id}, #{short_name})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int saveTeam(Team team);
}
