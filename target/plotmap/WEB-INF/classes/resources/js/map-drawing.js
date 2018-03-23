var data="Not Found";
var selected_plot="";

$(function() {
	
	 $.getJSON('http://localhost:8080/RESTfulExample/rest/hello', function(data){
            
            var layerIdList = ["_SoldPL", "_PlotPoly"];
            var statusList = ["Sold", "Not-Sold", "Hold"];
            var pathId = {};
            var index = 0;
            for (var val = 0; val < layerIdList.length; val++) {
                var layerId = layerIdList[val];
                
                for (var i = 1; i <= 200; i++) {
                    var str = "" + layerId + i;
                    if(data[str]!=null){
                    	console.log(data);
                    	var name=data[str].name;
                    	console.log(name);
                    	var res=name.split("\n");
                    	var status=""+res[1];
                    	if(status.includes("ON HOLD")){
                    		pathId[str] = statusList[2];
                    	}else if(status.includes("NOT SOLD")){
                    		pathId[str] = statusList[1];
                    	}else{
                    		pathId[str] = statusList[0];
                    	}
                    }
                    
                }
            } 
            
            
            
          

            map = new jvm.Map({
                container: $('#map-drawing'),
                map: 'map_drawing',
                backgroundColor: '#b5cae3',
                regionStyle: {
                    initial: {
                        "fill": 'white',
                        "fill-opacity": 0.8,
                        "stroke": 'black',
                        "stroke-width": 0.5,
                        "stroke-opacity": 1
                    },
                    hover: {
                        "fill-opacity": 0.8,
                        "fill": '#ffff99',
                        "cursor": 'pointer'
                    },
                    selected: {
                        "fill": '#ffff99'
                    },
                    selectedHover: {}
                },
                series: {
                    regions: [{
                        values: pathId,
                        scale: {
                            "Sold": "#ff6666",
                            "Not-Sold": "#4dff88",
                            "Hold": "#ffc61a"
                        }
                    }]
                },
                onRegionClick: function(element, code) {
                	selected_plot=code;
                	closeNav();
                	setTimeout(function(){
                		document.getElementById("mySidenav").style.width = "380px";
                	},1000);
                	
                    var data=document.getElementsByClassName("jvectormap-tip")[0].textContent;
                    code=code.replace(/[0-9]/g, '');
                    
                    if(code=="_SoldPL" || code=="_PlotPoly" || code=="_HatchTownhouse"){
                    	
                    	var res=data.split("\n");
                    	var status=""+res[1];
                    	
                    	document.getElementById("details-id").innerHTML=res[0];
                    	document.getElementById("details-status").innerHTML=res[1];
                    	document.getElementById("details-address").innerHTML=res[2];
                    	document.getElementById("details-info").innerHTML=res[3];
                    	
                    	if(status.includes("ON HOLD")){
                    		document.getElementById("buybtn").disabled=true;
                    		document.getElementById("holdbtn").disabled=true;
                    		document.getElementById("buybtn").style.display="block";
                    		document.getElementById("holdbtn").style.display="block";
                    		document.getElementById("buybtn").style.opacity = "0.3";
                    		document.getElementById("holdbtn").style.opacity="0.3";
                    		document.getElementById("details-disclaimer").innerHTML="This Plot has been OnHold!";
                    		showMessage("On Hold");
                    	}else if(status.includes("NOT SOLD")){
                    		
                    		document.getElementById("buybtn").disabled=false;
                    		document.getElementById("holdbtn").disabled=false;
                    		document.getElementById("buybtn").style.opacity = "1.0";
                    		document.getElementById("holdbtn").style.opacity="1.0";
                    		document.getElementById("buybtn").style.display="block";
                    		document.getElementById("holdbtn").style.display="block";
                    		document.getElementById("details-disclaimer").innerHTML="";
                    	}else{
                    		document.getElementById("buybtn").disabled=true;
                    		document.getElementById("holdbtn").disabled=true;
                    		document.getElementById("buybtn").style.display="block";
                    		document.getElementById("holdbtn").style.display="block";
                    		document.getElementById("buybtn").style.opacity = "0.3";
                    		document.getElementById("holdbtn").style.opacity="0.3";
                    		document.getElementById("details-disclaimer").innerHTML="This Plot has been Sold!";
                    		showMessage("Sold");
                    	}
                    	
                    }else{
                    	document.getElementById("buybtn").style.display="none";
                    	document.getElementById("holdbtn").style.display="none";
                    	document.getElementById("details-id").innerHTML="---";
                    	document.getElementById("details-status").innerHTML="---";
                    	document.getElementById("details-address").innerHTML="---";
                    	document.getElementById("details-info").innerHTML="---";
                    	document.getElementById("details-disclaimer").innerHTML="";
                    	showMessage("Not Valid");
                    }
                       
                    
                	//var data = map.getRegionName(code);
                  
                    //window.Android.showToast(data);  //response to Android
                }

            });
            
            document.getElementsByClassName("jvectormap-zoomin")[0].click();
            
	 });
});

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}
function onBuy(){
	closeNav();
	document.getElementById("popUpWindow").style.display="block";
	document.getElementById("formId:pathid").value = selected_plot;
	document.getElementById("formId:btnstatus").value = "buy";
	document.getElementById("formId:link").click(); 
	setTimeout(function(){ 
		document.getElementById("popUpWindow").style.display="none";
		window.location.reload(true);
	}, 
	4000);
} 

function onHold(){
	closeNav();
	document.getElementById("popUpWindow").style.display="block";
	document.getElementById("formId:pathid").value = selected_plot;
	document.getElementById("formId:btnstatus").value = "hold";
	document.getElementById("formId:link").click();
	setTimeout(function(){  
		document.getElementById("popUpWindow").style.display="none";
		window.location.reload(true);
	}, 
	4000);
}

function showMessage(status){
	document.getElementById("formId:btnstatus").value = status;
	document.getElementById("formId:message").click();
}

/*window.onload =  function(e){ 
	document.getElementById("popUpWindow").style.display="block";
	setTimeout(function(){  
		document.getElementById("popUpWindow").style.display="none";
	}, 
	1000);
}*/
  
