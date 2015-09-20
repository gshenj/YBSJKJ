<%@ page import="kis.entity.Company" %>
<%@ page import="java.util.List" %>
<%@ page import="kis.dao.CompanyHome" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Company> companies = new CompanyHome().findAll();
%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="UTF-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>系统登录</title>
        <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>

        <link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
		<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/jquery.cookie.js"></script>
        <script type="text/javascript" src="js/LodopFuncs.js"></script>

        <script type="text/javascript">

            var LODOP;
            function checkLodop() {
                LODOP = getLodop();
                //LODOP.PRINT_INITA(0, 0, 522, 333, "打印控件功能演示_Lodop功能_自定义纸张2");
                //LODOP.PRINT_INIT("打印任务1");
                // LODOP.SET_PRINTER_INDEX(-1);   //默认打印机
                LODOP.SET_PRINT_PAGESIZE(0, '210mm', '140mm', '');
            }


            $(document).ready(function(){
                //获取cookie的值
                var username = $.cookie('username');
                var password = $.cookie('password');
                //将获取的值填充入输入框中
                $('#username').val(username);
                $('#password').val(password);
                if(username != null && username != '' && password != null && password != ''){//选中保存秘密的复选框
                    $("#loginkeeping").attr('checked',true);
                }

                checkLodop();
            });

            function login() {
                var c = $('#company').val();
                var u = $('#username').val();
                var p = $('#password').val();
                $('#submit').prop('disabled',true);

                $.post("login", {company:c, username:u, password:p}, function(json) {

                    $('#submit').prop('disabled',false);
                    if (json.success) {
                        // 登录成功，记录cookie，否则不记录。
                        if($('#loginkeeping').prop('checked')){//保存密码
                            $.cookie('username',u, {expires:3650,path:'/'});
                            $.cookie('password',p, {expires:3650,path:'/'});

                        }else{//删除cookie
                            $.removeCookie('username', { path: '/' });
                            $.removeCookie('password', { path: '/' });
                        }

                        window.location.href='sale/order_new.jsp';
                    } else {
                        $('#errMsg').html(json.errorMsg)
                    }
                }, 'json');

                return false;
            }
        </script>

    </head>
    <body>
        <div class="container">
            <!-- Codrops top bar -->
        
            <section>				
                <div id="container_demo" >
                    <!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form method="post" autocomplete="on" onsubmit="return login();">
                                <h1>Log in</h1>
                                <p>
                                    <label for="company" class="" > 公司 </label>
                                    <select  style="width:100%;height:40px;border:1px solid darkgray; margin-top:2px; padding-left:10px;" id="company" name="company">
                                        <%
                                            for (Company company : companies) {
                                        %>
                                        <option value="<%=company.getId()%>"><%=company.getName()%></option>
                                        <%
                                            }
                                        %>

                                     </select>
                                </p>

                                <p> 
                                    <label for="username" class="uname" data-icon="u" > 账号 </label>
                                    <input id="username" name="username" required="required" type="text" placeholder="输入您的登录账号"/>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p"> 密码 </label>
                                    <input id="password" name="password" required="required" type="password" placeholder="输入您的登录密码" /> 
                                </p>
                                <p class="keeplogin"> 
									<input type="checkbox" name="loginkeeping" id="loginkeeping" value="loginkeeping" /> 
									<label for="loginkeeping">下次自动登录</label>
								</p>
                                <p class="login button">
                                    <span id="errMsg" style="color:red; font-style: italic;font-size:14px;margin-right:40px;"></span>
                                    <input type="submit" value="登 录" />
								</p>
                               
                            </form>
                        </div>
                    </div>
                </div>  
            </section>
        </div>
    </body>
</html>