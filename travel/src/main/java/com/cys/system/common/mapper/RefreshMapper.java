package com.cys.system.common.mapper;

import com.cys.system.common.pojo.Refresh;

public interface RefreshMapper {
   Refresh findAllTypeRefreshTime();
   void updateRefreshTimeByType(Integer id,String refreshTime);
   String getRefreshTimeByType(Integer id);
}
