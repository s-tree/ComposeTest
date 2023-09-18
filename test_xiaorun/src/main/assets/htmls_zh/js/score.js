// 百分制的评分改为5分支
template.helper('score_100_5', function (score) {
    return parseInt(score / 2 + 0.5) / 10;
});


var WEEK_DAY = ["周一","周二","周三","周四","周五","周六","周日"];
// 拼接openTime,closeTime,weekDay 计算营业时间
template.helper('workStr', function (openTime,closeTime,workDay) {
    var workTime = openTime.substr(0,5) + "~" + closeTime.substr(0,5);
    if(!workDay){
        return;
    }
    var waitEnd = "";
    var workDays = workDay.split(",");
    var workStr = "";
    var workLength = workDays.length;
    var lastWork = -1;
    for(var k = 0 ; k < workLength; k++){
        var currentDay = Number(workDays[k]);
        if(currentDay < 1 || currentDay > 7){
            continue;
        }
        if(lastWork == -1){
            workStr += WEEK_DAY[currentDay - 1];
            workStr += "~";
            waitEnd = "true";
        }
        else if(k == workLength - 1){
            workStr += WEEK_DAY[currentDay - 1];
            waitEnd = false;
        }
        else if(currentDay - lastWork != 1){
            if(waitEnd){
                workStr = workStr.substr(0,workStr.length - 1);
                workStr += "、";
            }
            workStr += WEEK_DAY[currentDay - 1];
            workStr += "~";
            waitEnd = "true";
        }

        lastWork = currentDay;
    }

    if(waitEnd){
        workStr = workStr.substr(0,workStr.length - 1);
    }

    return workTime + " " + workStr;
});