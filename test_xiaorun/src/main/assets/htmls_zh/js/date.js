
function isExpire(data){
    var currentDate = new Date();
    // 获取要判断的日期和时间
    var targetDate = new Date(data);

    // 比较当前时间和目标时间
    if (currentDate > targetDate) {
        return true;
    } else {
        return false;
    }
}

// 判断团购订单状态
template.helper('isExpire', function (status) {
    var currentDate = new Date();
    // 获取要判断的日期和时间
    var targetDate = new Date(data);

    // 比较当前时间和目标时间
    if (currentDate > targetDate) {
        return true;
    } else {
        return false;
    }
});