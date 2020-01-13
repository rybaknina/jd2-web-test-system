<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<ul class="navbar-nav">
	<li class="nav-item">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="localization" />
			<input type="hidden" name="language" value="ru" />
			<button type="submit" class="btn-link"><fmt:message bundle="${lang}" key="local.lnk.text.ru"/></button>
		</form>
	</li>
	<li class="nav-item">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="localization" />
			<input	type="hidden" name="language" value="en" />
			<button type="submit" class="btn-link"><fmt:message bundle="${lang}" key="local.lnk.text.en"/></button>
		</form>
	</li>
</ul>

<nav>
	<a href="controller?command=sign_out">${signout_lnk}</a>
</nav>
<hr />
<br />