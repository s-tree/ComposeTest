package com.jingxi.smartlife.pad.bean

data class HomeCardBean(
    var cardType:Int = -1,
    var cardIcon:Int = -1,
){
    enum class Type{
        TAG_NONE,
        TAG_NEIGHBOR,
        TAG_MESSAGEBOARD,

        /**
         * 门禁
         */
        TAG_DOOR,

        /**
         * 应用列表
         */
        TAG_ALLSTORE,

        /**
         * 用药提醒
         */
        TAG_MEDICINE,

        /**
         * 健康卡
         */
        TAG_HEALTHCARD,

        /**
         * 社区服务
         */
        TAG_HOME_SERVICE,

        /**
         * 社区商务
         */
        TAG_HOME_BUSINESS,

        /**
         * 物业通知
         */
        TAG_PROPERTY,

        /**
         * 社区公告
         */
        TAG_NOTICE,

        /**
         * 居家生活
         */
        @Deprecated("")
        TAG_HOME_LIFE,

        /**
         * 智能家居
         */
        @Deprecated("智能家居模块已迁移")
        TAG_SMARTHOME,

        /**
         * 完美新家
         */
        TAG_DECORATE,

        /**
         * 安防
         */
        TAG_DEFENCE,

        /**
         * 通知
         */
        TAG_NOTICE_CENTER,

        /**
         * 生活缴费
         */
        TAG_LIFE_PAY,

        /**
         * 社区福利
         */
        TAG_WELFARE,

        /**
         * 系统设置
         */
        TAG_SETTING,
        TAG_ELEVATOR,

        /**
         * 场景预约
         */
        SCENE_ORDER,

        /**
         * 社区活动
         */
        COMMUNITY_AC,

        /**
         * 本地添加第三方入口
         */
        TAG_THIRD_LOCAL
    }
}