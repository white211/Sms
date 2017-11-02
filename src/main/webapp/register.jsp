<%--
  Created by IntelliJ IDEA.
  User: white
  Date: 2017-11-01
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册界面</title>
    <script src="jquery.min.js"></script>
</head>
<body>
<form action="" method="post">
    <table>
        <tr>
            <td>手机号</td>
            <td><input type="text" name="telephone" id="telephone"/></td>
            <td><input type="button" name="send_btn" value="获取验证码" id="send_btn"/></td>
        </tr>
        <tr>
            <td>验证码</td>
            <td><input type="text" name="checkNumber"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="注册"/></td>
            <td><span type="text" id="checkNumber"/></td>
        </tr>
    </table>
</form>
</body>
<script>
    $(document).ready(function () {
        $("#send_btn").click(function () {
            alert("发送开始");
            var telephoneNumber = $("#telephone").val();
            $.ajax({
                type: "GET",
                url: "SendNum.do?telephone="+telephoneNumber,
                dataType:"json",
                success: function (result) {
                    alert(result);
                    $("#checkNumber").val(result);
                },
                error:function () {
                    alert("发送失败");
                }
            });
        });
    });
</script>
</html>
