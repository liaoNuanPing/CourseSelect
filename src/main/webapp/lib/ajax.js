//年级和班级可选显示
function gradeAndClasses() {
    $.ajax({
        type: 'POST',
        url : "mapping-config-all-show",
        success: function(data){
            data=JSON.parse(data);

            for(var i=0;i<data[1];i++) {
                var option=document.createElement("option");
                option.setAttribute("value",1+i);
                option.innerHTML=1+i+"班";
                document.getElementById("classes").appendChild(option);
            }

            for(var i=0;i<data[2];i++) {
                var option=document.createElement("option");
                option.setAttribute("value",1+i);
                option.innerHTML=1+i+"年级";
                document.getElementById("grade").appendChild(option);
            }
        },
        error:function(data) {
            alert("error")
        }
    });
}