function joinMap(map1, map2)
{
    var result = {};
    for(var att in map1)
    {
        result[att] = map1[att];
    }   
    
    for(var att in map2)
    {
        result[att] = map2[att];
    }   
    
    return result;
}

var definedMap = {};

var haveAttachmentDisplay = '<img src=../images/oa/attachment.gif title=此邮件包含附件>';
var mailDisplay1 = '<img src=../images/oa/mail1.gif title=未阅读邮件>';
var mailDisplay2 = '<img src=../images/oa/mail2.gif title=已阅读邮件>';

var mailFeebackDisplay = '<img src=../images/oa/replied.gif title=已回复>';

definedMap = joinMap(definedMap, {'mailAttachmentDisplay': [{'key' : '0', 'value' : ''}, {'key' : '1', 'value' : haveAttachmentDisplay}]});
definedMap = joinMap(definedMap, {'mailStatusIco': [{'key' : '0', 'value' : mailDisplay1}, {'key' : '1', 'value' : mailDisplay2}]});
definedMap = joinMap(definedMap, {'mailFeebackIco': [{'key' : '0', 'value' : ''}, {'key' : '1', 'value' : mailFeebackDisplay}]});

function joinMap2(map1)
{
    var result = {};
    for(var att in definedMap)
    {
       result[att] = definedMap[att];
    }   
    
    for(var att in map1)
    {
        result[att] = map1[att];
    }   
    
    return result;
}
