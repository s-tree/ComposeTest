package com.jingxi.smartlife.pad.util

import com.jingxi.smartlife.pad.bean.HomeCardBean
import com.jingxi.smartlife.pad.compose.R

class CardListUtil {

    companion object{

        fun getCardList(): ArrayList<HomeCardBean>{
            val list = arrayListOf<HomeCardBean>(
                HomeCardBean(HomeCardBean.Type.TAG_NOTICE_CENTER.ordinal, R.mipmap.icon_tongzhi),
                HomeCardBean(HomeCardBean.Type.TAG_DOOR.ordinal, R.mipmap.icon_duijiangjiankong),
                HomeCardBean(HomeCardBean.Type.TAG_WELFARE.ordinal, R.mipmap.icon_welfare),
                HomeCardBean(HomeCardBean.Type.TAG_HOME_SERVICE.ordinal, R.mipmap.icon_shequfuwu),
                HomeCardBean(HomeCardBean.Type.TAG_HOME_BUSINESS.ordinal, R.mipmap.icon_shequshangwu),
                HomeCardBean(HomeCardBean.Type.SCENE_ORDER.ordinal, R.mipmap.icon_scene_order),
                HomeCardBean(HomeCardBean.Type.COMMUNITY_AC.ordinal, R.mipmap.icon_community_ac),
                HomeCardBean(HomeCardBean.Type.TAG_ELEVATOR.ordinal, R.mipmap.icon_call_elevator),
                HomeCardBean(HomeCardBean.Type.TAG_SETTING.ordinal, R.mipmap.icon_settings),
            )
            return list
        }
    }
}