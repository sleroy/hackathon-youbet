package com.youbet.adapters.mysql.mappers;

import com.youbet.ports.matchsystem.Player;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PlayerMapper {
    @Select("select * from Player where player_name=#{name}")
    Player selectPlayer(@Param("name") String name);
    
    @Insert("INSERT INTO Player(id,player_api_id,player_name,birthday,height,weight) values(#{id},#{player_api_id},#{player_name},#{birthday},#{height},#{weight})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int savePlayer(Player player);
    
    
}

	