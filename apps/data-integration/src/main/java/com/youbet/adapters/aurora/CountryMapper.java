package com.youbet.adapters.aurora;

import com.youbet.agents.domain.Country;
import org.apache.ibatis.annotations.Select;

public interface CountryMapper {
    @Select("select * from Country where name=#{name}")
    Country selectCountry(String country);
}
