package com.jingxi.smartlife.pad.util

import com.jingxi.smartlife.pad.compose.R

class DoorUtil {

    class DoorState internal constructor(
        var onDoorOnline: Boolean = false,
        var outDoorOnline: Boolean = false,
        var managerOnline: Boolean = false
    )

    companion object{

        fun getOnDoorIcon(state: DoorState): Int{
            return if(state.onDoorOnline) R.mipmap.device_ondoor_on else R.mipmap.device_ondoor_off
        }

        fun getOutDoorIcon(state: DoorState): Int{
            return if(state.outDoorOnline) R.mipmap.device_outdoor_on else R.mipmap.device_outdoor_off
        }

        fun getManagerIcon(state: DoorState): Int{
            return if(state.managerOnline) R.mipmap.device_manager_on else R.mipmap.device_manager_off
        }

    }
}