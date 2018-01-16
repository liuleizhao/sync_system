<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>同步系统登陆</title>
    <%@ include file="/WEB-INF/common/meta.jsp"%>
  </head>
<body style="text-align: center;width: 100%;">
	<div style="width: 400px;height: 200px;background: #EEE;padding-top: 50px;margin: auto auto;" >
	<form id="form" action="${ctx }/sync/channel/ui" method="post">
		<div>
			<p>
				<label>账号：</label> <input id="account" name="account" type="text" />
			</p>
			<p>
				<label>密码：</label> <input id="password" name="password" type="password" />
			</p>
		</div>
		<input type="submit" value="登陆" style="width: 100px;"/>
	</form>
	</div>
</body>

<script type="text/javascript">
	
</script>
</html>