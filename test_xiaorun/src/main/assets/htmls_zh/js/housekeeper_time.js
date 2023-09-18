// 百分制的评分改为5分支
function getExactDate(time) {
    var date = time.split(" ");
    var date1 = date[0].split("-");
    var date2 = date[1].split(":");

    var year = date1[0];
    var month = date1[1];
    var day = date1[2];
    var hour = date2[0];
    var min = date2[1];
    var second = date2[2];
    var timeDate = new Date(year, parseInt(month) - 1,day,hour,min,second);
    return timeDate;
};
