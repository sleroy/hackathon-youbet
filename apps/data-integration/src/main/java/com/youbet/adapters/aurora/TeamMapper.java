package com.youbet.adapters.aurora;

import com.youbet.agents.domain.Team;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface TeamMapper {
    
    @Select("select * from Team where name=#{name}")
    Team selectTeam(String name);
    
    @Insert("insert into Team(name, team_api_id, short_name) values(name=#{teamName}, team_api_id=#{team_api_id}, short_name=#{shortName}")
    Team insertTeam(String teamName, String team_api_id, String shortName);
}
