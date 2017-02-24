<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html >
    <head>

        <script type="text/javascript">
            function addQantity(id, model) {
                var number = document.getElementById(id);
                var value = parseInt(number.getAttribute('value'), 10) + 1;
                number.setAttribute('value', value);
                number.innerHTML = value;
                totalPrice(id);
                getQantity(id, model);
            }
            function getQantity(id, model) {
                var number = document.getElementById(id);
                var value = parseInt(number.getAttribute('value'), 10);
                var quantity = document.getElementById('idQ').value = value;
            }
            function decQantity(id, model) {
                var number = document.getElementById(id);
                var value = parseInt(number.getAttribute('value'), 10);
                if (value > 1) {
                    value -= 1;
                }
                number.setAttribute('value', value);
                number.innerHTML = value;
                totalPrice(id);
                getQantity(id, model);
            }
            function totalPrice(id) {
                var number = document.getElementById(id);
                var value = parseInt(number.getAttribute('value'), 10);

                var price = document.getElementById(id + 'price');
                var value1 = price.textContent;
                var str = value1.replace(/[0-9]/g, '');
                var numb = value1.match(/\d/g);
                numb = numb.join("");

                var totPrice = document.getElementById(id + 'totPrice').innerHTML = numb * value + str;
            }

        </script>Language: <a href="?language=en">English</a> <a href="?language=ru">Russian</a> <a href="?language=ro">Romanian</a>
    </head>
    <body>
        Current Locale : ${pageContext.response.locale}
        <form action="buy" method="POST">
            <section id="cart_items">
                <div class="container">
                    <div >
                        <ol class="breadcrumb">
                            <li><a href="home"><spring:message code="messages.cart_home"/></a></li>
                            <li class="active"><spring:message code="messages.cart_cart"/></li>
                        </ol>
                    </div>
                    <div class="table-responsive cart_info">

                        <table class="table table-condensed">
                            <thead>
                                <tr class="cart_menu">
                                    <td class="image"><spring:message code="messages.cart_table_item"/></td>
                                    <td class="description"></td>
                                    <td class="price"><spring:message code="messages.cart_table_price"/></td>
                                    <td class="quantity"><spring:message code="messages.cart_table_quantity"/></td>
                                    <td class="total"><spring:message code="messages.cart_table_total"/></td>
                                    <td></td>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach items="${prodlist}" var="p">
                                    <tr>
                                        <td class="cart_product">
                                            <a href="products/${p.value.modelul}/productDetails"><img style="width: 70px; height: 70px;" src="<c:url value='/resources/images/home/${p.key}'/>" alt=""></a>
                                        </td>
                                        <td class="cart_description">
                                            <h4><a href="products/${p.value.modelul}/productDetails" >${p.value.modelul}</a></h4>
                                            <input hidden="hidden" type="text" name="modelul"  value="${p.value.modelul}" readonly="readonly" />
                                            <p>COD: ${p.value.cod}</p>
                                        </td>
                                        <td class="cart_price">
                                            <p id="${p.value.productId}price">${p.value.pret}</p>
                                        </td>
                                        <td class="cart_quantity">
                                            <div class="cart_quantity_button">
                                                <a class="cart_quantity_up" style="cursor: pointer; " onclick="addQantity(${p.value.productId},${p.value.productId})"> + </a>
                                                <input class="cart_quantity_input" type="text" name="quantity" id="${p.value.productId}" value="1" readonly="readonly" autocomplete="off" size="2"/>
                                                <a class="cart_quantity_down" style="cursor: pointer; " onclick="decQantity(${p.value.productId},${p.value.productId})"> - </a>
                                            </div>
                                        </td>
                                        <td class="cart_total">
                                            <p class="cart_total_price" id="${p.value.productId}totPrice" >${p.value.pret}</p>
                                        </td>
                                        <td class="cart_delete">
                                            <a class="cart_quantity_delete" href="deleteProd/${p.value.modelul}"><i class="fa fa-times"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>



                            </tbody>
                        </table> 


                    </div>
                </div>
            </section> <!--/#cart_items-->
            <section id="do_action" >
                <div class="container">
                    <div class="heading">
                        <h3 style="text-align: left; margin-left: 10%;">For buy you need to complete next fields:</h3>
                    </div>
                    <div class="col-sm-6">
                        <div class="total_area">
                            <ul>
                                <!--<li>Total <span>$61</span></li>-->
                                <li><spring:message code="messages.cart_form_name"/> <input type="text" name="nume" /></li>
                                <li><spring:message code="messages.cart_form_phone"/> <input type="text" name="phone" /></li>
                            </ul>
                            <input type="submit" class="btn btn-default update" value="<spring:message code="messages.cart_form_buy"/>"/>
                        </div>
                    </div>
                </div>
            </section>
        </form>
                
               
    </body>