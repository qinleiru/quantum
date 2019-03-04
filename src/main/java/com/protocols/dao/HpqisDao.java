package com.protocols.dao;

import com.protocols.pojo.Hpqis;

public interface HpqisDao {
    public void addResult(Hpqis hpqis) throws Exception;
    public int findResultAmount(Hpqis hpqis) throws Exception;
//    public int findAllResultByValue(Hpqis hpqis) throws  Exception;
}
