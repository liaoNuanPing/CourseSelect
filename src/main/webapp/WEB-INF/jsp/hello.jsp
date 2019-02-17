<%--
  Created by IntelliJ IDEA.
  User: Ryde_L
  Date: 2019/1/2
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>hello world</h1>


<input type="button" value="按下回显responseBody的字符串值" title="String" id="body" onclick="show('String')" >
<input type="button" value="按下回显responseBody的List值" title="List" id="List" onclick="show('List')" >
<object contenteditable="true"

</body>

<script>
    function show(path) {
        alert("click");
        var request=new XMLHttpRequest();
        request.open("GET","http://localhost:8080/mvc/"+path);
        request.send();
        request.onreadstatechange=function(){
            if(request.readyState===4)
                if(request.status===200){
                    alert("成功");
                    document.getElementById("body").removeAttribute()
                }
        }
    }

</script>

</html>
