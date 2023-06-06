package com.youbet.adapters.mysql.mappers;

import com.youbet.ports.matchsystem.Country;
import org.apache.ibatis.annotations.Select;

public interface CountryMapper {
    @Select("select * from Country where name=#{name}")
    Country selectCountry(String country);
}
