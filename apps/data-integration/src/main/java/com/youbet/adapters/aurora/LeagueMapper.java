package com.youbet.adapters.aurora;

import com.youbet.agents.domain.League;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface LeagueMapper {
    @Select("select * from League where name=#{name} AND country_id=#{countryId}")
    League selectLeague(Integer countryId, String leagueName);
    
    @Insert("insert into League(name, country_id) values(name=#{leagueName}, country_id=#{countryId}")
    League createLeague(Integer countryId, String leagueApi_id, String leagueName);
}
