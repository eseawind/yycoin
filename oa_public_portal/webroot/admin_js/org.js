var levelMap = {};

var gNodeMap = {};

function digui(node)
{
    var subList = shipMap[node.id];
    
    if (subList == null || subList.length == 0)
    {
        return;
    }
    
    for (var i = 0; i < subList.length; i++)
    {
        var ele = subList[i];
        
        var ssItem = shipMap[ele.subId];
        
        var subNode = snode('[' + ele.subLevel +  ']' + ele.subName, ssItem.length > 0, ele.subId);
        
        subNode.sname = ele.subName;
        
        subNode.subLevel = ele.subLevel;
        
        gNodeMap[ele.subId] = subNode;
        
        node.add(subNode);
        
        levelMap[ele.subId] = ele.subLevel;
        
        if (ssItem.length > 0)
        {
            digui(subNode);
        }
    }
}

function allSelect(check)
{
    if (check)
    {
        tv.expandAll();
    }
    else
    {
        tv.collapseAll();
    }
}


function load(expand)
{
    var itemNode = snode('(0)董事会', true, 0);
    
    itemNode.sname = '董事会';
    
    tv.add(itemNode);
    
    gNodeMap[0] = itemNode;
    
    levelMap['0'] = 0;
    
    digui(itemNode)
    
    tv.create(document.getElementById("tree"));
    
    if (expand)
    tv.expandAll();
}

function callBack(node, fun)
{
    buffalo.remoteCall("orgManager.querySubPrincipalship",[node.id], function(reply) {
               var result = reply.getResult();
                
               for (var i = 0; i < result.length; i++)
               {
                   var ele = result[i];
                   
                   var subNode;
                   if (ele.nextCount > 0)
                   {
                       subNode = snode(ele.subName, true, ele.subId);
                       
                       subNode.setCallback(callBack);
                   }
                   else
                   {
                       subNode = snode(ele.subName, false, ele.subId);
                   }
                   
                   //subLevel
                   levelMap[ele.subId] = ele.subLevel;
                       
                   node.add(subNode);
               }
               
               fun.apply(node);
               
               if (result.length == 0)
               {
                   node.setRoot(false);
                   
                   node.refresh(0);
               }
        });
}
