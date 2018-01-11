<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Home</title>

	<style>
       .bold-text {
            font-weight: bold;
       }
       .tabx1 {
            padding-left: 10px;
       }

    </style>
</head>
<body>
	<h1>Hibernate Test</h1>
	<c:if test="${not empty dataList}">
		<!--<p>Data: ${dataList}</p>-->
		<c:forEach var="room" items="${dataList}">
			<div style="padding-bottom: 10px;">
				<p><span class="bold-text">Room id:</span> ${room.getId()}</p>
				<p><span class="bold-text">Room Number:</span> ${room.getNumber()}</p>
				<p class="tabx1"><span class="bold-text">Hotel:</span> ${room.getHotel()}</p>
				<p class="tabx1"><span class="bold-text">Room Info:</span> ${room.getRoomInfo()}</p>
				<p><span class="bold-text">Room Price:</span> ${room.getPrice()}</p>
                <div style="padding-left: 20px;">
                    <p><span class="bold-text">Guests:</span></p>
                    <c:forEach var="guest" items="${room.getGuests()}">
                        <p>${guest.getFullName()}</p>
                    </c:forEach>
				</div>
			</div>
		</c:forEach>
	</c:if>

	<div>
		<form action="${pageContext.request.contextPath}/generate" method="get"><button>Generate</button></form>
		<form action="${pageContext.request.contextPath}/filter" method="get">
			<p>Hotel rating >= <input type="text" name="rating"></p>
			<p>Price from <input type="text" name="priceFrom"> to <input type="text" name="priceTo"></p>
			<p>People count <input type="text" name="peopleCount"></p>
			<button>Filter</button>
		</form>
		<form action="${pageContext.request.contextPath}/guestfilter" method="get">
			<p>Surname <input type="text" name="surname"></p>
			<button>Guest Filter</button>
		</form>
	</div>
</body>
</html>
