package com.youbet.adapters.mysql.mappers;

import com.youbet.ports.matchsystem.League;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LeagueMapper {
    @Select("select * from League where name=#{leagueName} AND country_id=#{countryId}")
    League selectLeague(@Param("countryId") Integer countryId, @Param("leagueName") String leagueName);
    
    @Insert("INSERT INTO League(name, country_id,league_api_id) values(#{name}, #{country_id}, #{league_api_id})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int saveLeague(League league);
}
