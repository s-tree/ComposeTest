(/iphone|ipod|ipad/i.test(navigator.appVersion)) && document.addEventListener(
    'blur',
    event => {
        // 当页面没出现滚动条时才执行，因为有滚动条时，不会出现这问题
        // input textarea 标签才执行，因为 a 等标签也会触发 blur 事件
        if (
            document.documentElement.offsetHeight <=
            document.documentElement.clientHeight &&
            ['input', 'textarea'].includes(event.target.localName)
        ) {
            document.body.scrollIntoView() // 回顶部
        }
    },
    true
)

layer.config({
    skin: 'layui-layer-awesome',
    width: '80%',
    title: false,
    closeBtn: 0,
    scrollbar: false
});

// 判断商务订单状态
template.helper('businessOrderStatus', function (status,complainStatus) {
    if (complainStatus == "WAIT" || complainStatus == "ALREADY") {
        status = '已投诉';
    } else if (status == "WAIT_BUYER_PAY" || status == "PAY_ERROR") {
        status = "待付款";
    } else if (status == "TRADE_SUCCESS") {
        status = '待接单';
    } else if (status == "TRADE_CLOSED") {
        status = '已取消';
    } 
    else if (status == "WAIT_DELIVER") {
        status = '待发货';
    } else if (status == "SHIPPED") {
        status = '待确认';
    } else if (status == "RECEIVE") {
        status = '待评价';
    } else if (status == "EVALUATE") {
        status = '已评价';
    } else if (status == "APPLY_RETURN") {
        status = '申请退货中';
    } else if (status == "AGREE_RETURN") {
        status = '待退货';
    } else if (status == "HAS_RETURN") {
        status = '已退货';
    } else if (status == "REFUSAL_RETURN") {
        status = '商家拒绝退货';
    } else if (status == "CANCEL_RETURN") {
        status = '取消退货';
    } 
    else if (status == "REFUND") {
        status = '申请退款中';
    } else if (status == "AGREE_REFUND") {
        status = '待退款';
    } else if (status == "REFUSAL_REFUND") {
        status = '商家拒绝退款';
    } else if (status == "HAS_REFUND") {
        status = '退款完成';
    } else if (status == "CANCEL_REFUND") {
        status = '取消退款';
    } else if (status == "CHANGE") {
        status = '退款异常';
    } else if (status == "TRADE_FINISHED") {
        status = '已完成';
    } else if (status == "ORDER_COMPLETION"){
        status = '订单完成';
    } else if (status == "TRANSACTION_CLOSING"){
        status = '交易关闭';
    } else if (status == "ORDER_COMPLETION"){
        status = '订单完成';
    } else if (status == "TRANSACTION_COMPLETE"){
        status = "订单完成";
    }else{
        status = '';
    }
    return status;
});

// 判断慧服务订单状态
template.helper('serviceOrderStatus', function (status) {
    if (status == "TRADE_SUCCESS") {
        status = "待接单";
    } else if (status == "WAIT_BUYER_PAY") {
        status = '待付款';
    } else if (status == "homestatus2") {
        status = '待评价';
    } else if (status == "homestatus3") {
        status = '已完成';
    } else if (status == "homestatus4") {
        status = '已取消';
    } else if (status == "homestatus5") {
        status = '已投诉';
    } else {
        status = '';
    }
    return status;
});

// 判断团购订单状态
template.helper('grouponOrderStatus', function (status) {
    if (status == "WAIT_BUYER_PAY") {
        status = "待付款";
    }  else if (status == "TRADE_SUCCESS") {
        status = '待接单';
    }  else if (status == "WAIT_DELIVER") {
        status = '待发货';
    }  else if (status == "SHIPPED") {
        status = '待确认';
    }  else if (status == "status5") {
        status = '已取消';
    }  else if (status == "status12") {
        status = '退款完成';
    }  else if (status == "TRADE_FINISHED") {
        status = '已完成';
    }  else {
        status = '';
    }
    return status;
});

// 商务订单详情提示语 - status8、status11、status14 直接填理由
template.helper('businessOrderTip', function (status) {
    if (status == "WAIT_BUYER_PAY") {
        status = "请尽快完成付款哦";
    } else if (status == "TRADE_SUCCESS") {
        status = '超过30分钟未接单，订单将自动取消退款';
    } else if (status == "WAIT_DELIVER") {
        status = '商家正在为您准备宝贝';
    } else if (status == "RECEIVE") {
        status = '期待您的评价';
    } else if (status == "EVALUATE") {
        status = '感谢您的评价';
    } else if (status == "HAS_REFUND") {
        status = '支付金额已退回支付账户，期待再次光临';
    } else if (status == "HAS_RETURN") {
        status = '支付金额将退回支付账户，期待再次光临';
    } else if (status == "AGREE_REFUND") {
        status = '支付金额将退回支付账户，期待再次光临';
    } else if (status == "TRANSACTION_COMPLETE") {
        status = '感谢您的信任，期待再次光临';
    } else {
        status = '';
    }
    return status;
});

// 服务订单详情提示语 - homestatus5 有理由显示理由，没有的话显示“请等待客服介入”
template.helper('serviceOrderTip', function (status) {
    if (status == "TRADE_SUCCESS") {
        status = "商家会通过电话或其他形式与您联系，确认服务细节";
    } else if (status == "WAIT_BUYER_PAY") {
        status = '请尽快完成付款哦';
    } else if (status == "EVALUATE") {
        status = '期待您对我们服务的评价';
    } else if (status == "TRADE_FINISHED") {
        status = '感谢您的信任，期待再次光临';
    } else if (status == "TRADE_FINISHED") {
        status = '期待再次光临';
    } else {
        status = '';
    }
    return status;
});

// 团购订单详情提示语 
template.helper('grouponOrderTip', function (status) {
    if (status == "WAIT_BUYER_PAY") {
        status = "请尽快完成付款哦";
    } else if (status == "TRADE_SUCCESS") {
        status = '超过30分钟未接单，订单将自动取消退款';
    } else if (status == "WAIT_DELIVER") {
        status = '商家正在为您准备宝贝';
    } else if (status == "RECEIVE") {
        status = '168小时后将自动确认';
    } else if (status == "TRADE_FINISHED") {
        status = '感谢您的信任，期待再次光临';
    } else if (status == "status5") {
        status = '支付金额已退回支付账户，期待再次光临';
    } else if (status == "status12") {
        status = '支付金额已退回支付账户，期待再次光临';
    } else {
        status = '';
    }
    return status;
});

// 订单支付方式                      
template.helper('orderPayType', function (type) {
    if (type == "aliPay") {
        type = "支付宝";
    }  else if (type == "weChat") {
        type = '微信';
    }  else if (type == "offline") {
        type = '线下支付';
    }  else if (type == "point") {
        type = '积分抵扣';
    }  else if (type == "balance") {
        type = '余额';
    }  else if (type == "familyWallet") {
        type = '家庭钱包';
    }  else {
        type = '-';
    }
    return type;
});

var statusOperation = {
    order: {
        //取消订单
        cancel: function (orderSn, mescroll) {
            layer.confirm('您确定取消订单吗?', {
                btn: ['取消', '确定']
            }, function (i) {
                layer.close(i);
            }, function () {
                $.slAjax({
                    url: root + 'bus/rest/order/cancelOrder/' + orderSn,
                    type: "put",
                    data: {},
                    beforeSend: function () {
                        layer.load(2);
                    },
                    success: function (res) {
                        layer.closeAll();
                        if (res.code == 0) {
                            layer.msg("订单已成功取消", {
                                time: 2500
                            });
                            mescroll.triggerDownScroll();
                        } else {
                            layer.msg(res.msg, {
                                time: 2500
                            });
                        }
                    }
                });
            });
        },
        //支付
        //type : business / groupon
        pay: function (orderSn, familyInfoId, price, type) {
            callHandlerBridge('pay', {
                'familyInfoId': familyInfoId,
                'orderSn': orderSn,
                'orderType': type,
                'price': price,
                'score': 0
            });
        },
        //评价
        evaluate: function (orderid, orderSn, accId) {
            window.location.href = 'evaluate.html?orderId=' + orderid + '&orderSn=' + orderSn + '&accId=' + accId + '&accessToken=' + accessToken;
        },
        //投诉
        complain: function (orderid) {
            window.location.href = "complaint.html?orderid=" + orderid + '&accessToken=' + accessToken;
        },
        //申请退款
        refund: function (orderid) {
            layer.confirm('您确定申请退款吗?', {
                btn: ['取消', '确定']
            }, function (i) {
                layer.close(i);
            }, function () {
                window.location.href = "complaint.html?orderid=" + orderid + '&accessToken=' + accessToken + '&type=refund';
            });
        },
        //取消申请退款
        unRefund: function (orderId){
            layer.confirm('您确定撤销申请吗?', {
                btn: ['取消', '确定']
            }, function (i) {
                layer.close(i);
            }, function () {
                $.slAjax({
                    url: root + 'bus/rest/order/refund/' + orderId + '/CANCEL_REFUND',
                    type: 'put',
                    data: {},
                    beforeSend: function () {
                        layer.load(2);
                    },
                    success: function (res) {
                        layer.closeAll();
                        if (res.code == 0) {
                            mescroll.triggerDownScroll();
                        } else {
                            layer.msg(res.msg, {
                                time: 2500
                            });
                        }
                    }
                });
            });
        },
        //取消投诉
        uncomplain: function (complainid, mescroll) {
            layer.confirm('您确定撤销投诉吗?', {
                btn: ['取消', '确定']
            }, function (i) {
                layer.close(i);
            }, function () {
                $.slAjax({
                    url: root + 'bus/rest/complain/withdrawComplaint/' + complainid,
                    type: "put",
                    data: {},
                    beforeSend: function () {
                        layer.load(2);
                    },
                    success: function (res) {
                        layer.closeAll();
                        if (res.code == 0) {
                            mescroll.triggerDownScroll();
                        } else {
                            layer.msg(res.msg, {
                                time: 2500
                            });
                        }
                    }
                });
            });
        },
    },
    business: {
        //退货
        return: function (orderId, mescroll) {
            layer.confirm('您确定申请退货吗?', {
                btn: ['取消', '确定']
            }, function (i) {
                layer.close(i);
            }, function () {
                window.location.href = "complaint.html?orderId=" + orderId + "&accessToken=" + accessToken + "&type=applyReturn";
            });
        },
        //收货
        receipt: function (orderId, mescroll) {
            layer.confirm('您确定收货吗?', {
                btn: ['取消', '确定']
            }, function (i) {
                layer.close(i);
            }, function () {
                $.slAjax({
                    url: root + 'bus/rest/order/receiveGoods/' + orderId,
                    type: "put",
                    data: {},
                    beforeSend: function () {
                        layer.load(2);
                    },
                    success: function (res) {
                        layer.closeAll();
                        if (res.code == 0) {
                            layer.msg('确认收货!', {
                                time: 2500
                            });
                            mescroll.triggerDownScroll();
                        } else {
                            layer.msg(res.msg, {
                                time: 2500
                            });
                        }
                    }
                });
            });
        },
        //撤销取消订单
        uncancel: function (orderSn, mescroll) {
            layer.confirm('您确定撤销申请吗?', {
                btn: ['取消', '确定']
            }, function (i) {
                layer.close(i);
            }, function () {
                $.slAjax({
                    url: root + 'businessServer/shopOrderRest/cancelCancelOrder',
                    data: {
                        orderSn: orderSn
                    },
                    beforeSend: function () {
                        layer.load(2);
                    },
                    success: function (res) {
                        layer.closeAll();
                        if (res.code == 0) {
                            mescroll.triggerDownScroll();
                        } else {
                            layer.msg(res.msg, {
                                time: 2500
                            });
                        }
                    }
                });
            });
        },
        //撤销申请退货
        unreturn: function (orderId, mescroll) {
            layer.confirm('您确定撤销申请吗?', {
                btn: ['取消', '确定']
            }, function (i) {
                layer.close(i);
            }, function () {
                $.slAjax({
                    url: root + 'bus/rest/order/returnGoods/' + orderId + '/CANCEL_RETURN',
                    type: 'put',
                    data: {},
                    beforeSend: function () {
                        layer.load(2);
                    },
                    success: function (res) {
                        layer.closeAll();
                        if (res.code == 0) {
                            mescroll.triggerDownScroll();
                        } else {
                            layer.msg(res.msg, {
                                time: 2500
                            });
                        }
                    }
                });
            });
        }
    },
    home: {
        pay: function (fromOrderList,orderId, orderSn,payable, score, familyInfoId, accId, mescroll) {
            console.log('price:' + payable, 'score:' + score);
            if (payable !== 0) {
                callHandlerBridge('pay', {
                    'familyInfoId': familyInfoId,
                    'orderSn': orderSn,
                    'orderType': 'homeservice',
                    'price': payable,
                    'score': score
                });
            } else {
                $.slAjax({
                    url: root + 'bus/rest/order/zeroPayment/' + orderId,
                    type: 'put',
                    data: {},
                    success: function (res) {
                        if (res.code == 0) {
                            mescroll.triggerDownScroll();
                        } else {
                            layer.msg(res.msg, function () {
                                mescroll.triggerDownScroll();
                            });
                        }
                    }
                });
            }
        },
        
        home_changeprice: function (orderId, mescroll) {
            $(".changepricenum").val('');
            layer.confirm('', {
                type: 1,
                area:["80%"],
                title: '修改价格',
                btn: ['取消', '确认'],
                scrollbar: false,
                content: $('.alertbox'),
            }, function (i) {
                layer.close(i);
            }, function () {
                var serviceprice = $(".changepricenum").val().replace(/(^\s*)|(\s*$)/g, "");
                if (serviceprice!='') {
                    var request = {};
                    request.id = orderId;
                    request.price = serviceprice;
                    $.slAjax({
                        url: root + 'bus/rest/order/updatePrice',
                        type: 'put',
                        contentType: "application/json",
                        dataType: "json",
                        data: JSON.stringify(request),
                        success: function (res) {
                            if (res.code == 0) {
                                mescroll.triggerDownScroll();
                            } else {
                                layer.msg(res.msg, {
                                    time: 2500
                                });
                            }
                        }
                    });
                } else {
                    layer.msg('请输入要修改的价格');
                }
            });
        }
    },
    groupon: {
        
    }
};

function orderOperation(mescroll) {
    $('.order_btn').unbind('click').on('click', function () {
        var orderSn = $(this).parents('.order_item').data('ordersn');
        var orderId = $(this).parents('.order_item').data('orderid');
        var orderType = $(this).parents('.order_item').data('ordertype');
        var accId = $(this).parents('.order_item').data('accid');
        var familyInfoId = $(this).parents('.order_item').data('familyinfoid');
        var price = $(this).parents('.order_item').data('price');
        var complainid = $(this).parents('.order_item').data('complainid');
        var _this = $(this);
        console.log(orderSn, orderType);
        if (checkHasClass(_this, 'order_cancel')) {
            statusOperation.order.cancel(orderId, mescroll);
        }
        else if(checkHasClass(_this, 'order_evaluate')){
            statusOperation.order.evaluate(orderId,orderSn, accId);
        }
        else if (checkHasClass(_this, 'order_complain')) {
            statusOperation.order.complain(orderId, mescroll);
        } 
        else if (checkHasClass(_this, 'order_uncomplain')) {
            statusOperation.order.uncomplain(complainid, mescroll);
        } 
        else if(checkHasClass(_this, 'order_cancel_pay')){
            statusOperation.order.refund(orderId);
        }
        else if(checkHasClass(_this, "business_uncancel_pay")){
            //撤销申请退款
            statusOperation.order.unRefund(orderId);
        }
        else if (checkHasClass(_this, 'order_receipt')) {
            statusOperation.business.receipt(orderId, mescroll);
        } 
        else{
            if (orderType === 'GOODS') {
                if (checkHasClass(_this, 'business_pay')) {
                    statusOperation.order.pay(orderSn, familyInfoId, price,'business');
                } else if (checkHasClass(_this, 'business_return')) {
                    statusOperation.business.return(orderId, mescroll);
                } else if (checkHasClass(_this, 'business_uncancel')) {
                    statusOperation.business.uncancel(orderSn, mescroll);
                } else if (checkHasClass(_this, 'business_unreturn')) {
                    statusOperation.business.unreturn(orderId, mescroll);
                } 
            } else if (orderType === 'SERVICE') {
                if (checkHasClass(_this, 'home_pay')) {
                    var payable = Number($(this).data('lastprice'));
                    var score = $(this).data('score');
                    var fromOrderList = $(this).data('fromorderlist');
                    statusOperation.home.pay(fromOrderList,orderId, orderSn, payable, score, familyInfoId, accId, mescroll);
                } else if (checkHasClass(_this, 'home_changeprice')) {
                    statusOperation.home.home_changeprice(orderId, mescroll);
                }
            } else if (orderType === 'GROUPON') {
                if (checkHasClass(_this, 'groupon_pay')) {
                    statusOperation.order.pay(orderSn, familyInfoId, price,'groupon');
                } 
            }
        }
        
    });
}

function checkHasClass(ele, cls) {
    return ele.hasClass(cls);
}
