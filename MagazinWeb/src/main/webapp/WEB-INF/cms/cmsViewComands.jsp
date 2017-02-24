<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section>
    <div class="container">
        <div class="row">


            <div class="col-sm-9">
                <div class="blog-post-area">
                    <h2 class="title text-center">Commands</h2>

                </div><!--/blog-post-area-->
                <div class="response-area">

                    <ul class="media-list">
                        <c:forEach items="${buyerList}" var="buy">
                            <li class="media">

                                <div class="media-body">
                                    <ul class="sinlge-post-meta">
                                        <li><i class="fa fa-user"></i>${buy.key.nume}</li>
                                        <li><i class="fa fa-phone"></i>${buy.key.phone}</li>
                                        <li><i class="fa fa-clock-o"></i>${buy.key.ora}</li>
                                        <li><i class="fa fa-calendar"></i>${buy.key.data}</li>
                                    </ul>
                                    <section id="cart_items">
                                        <div class="container">
                                            
                                            <div class="table-responsive cart_info">
                                                <table class="table table-condensed">
                                                    <thead>
                                                        <tr class="cart_menu">
                                                            <td class="image">Item</td>
                                                            <td class="description"></td>
                                                            <td class="price">Price</td>
                                                            <td class="quantity">Quantity</td>
                                                            <td class="total">Total</td>
                                                            <td></td>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach items="${buy.value}" var="p">
                                                            <c:forEach items="${p}" var="pr">
                                                            
                                                            <tr>
                                                                <td class="cart_product">
                                                                    <a href=""><img style="width: 70px; height: 70px;" src="<c:url value='/resources/images/home/${pr.key.images.get(0).getImgName()}'/>" alt=""></a>
                                                                </td>
                                                                <td class="cart_description">
                                                                    <h4><a href="">${pr.key.modelul}</a></h4>
                                                                    <p>COD: ${pr.key.cod}</p>
                                                                </td>
                                                                <td class="cart_price">
                                                                    <p id="price">${pr.key.pret}</p>
                                                                </td>

                                                                <td class="cart_total">
                                                                    <p class="cart_total_price" id="totPrice" >${pr.value}</p>
                                                                </td>
                                                                <td class="cart_delete">
                                                                    <a class="cart_quantity_delete" href="deleteProd/${p.key.modelul}"><i class="fa fa-times"></i></a>
                                                                </td>
                                                            </tr>
                                                            </c:forEach>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                    </section> <!--/#cart_items-->

                                    <a class="btn btn-primary" href="remove/${buy.key.buyerId}"><i class="fa fa-reply"></i>Delete command</a>
                                </div>
                            </li>
                        </c:forEach>

                    </ul>					
                </div><!--/Response-area-->
            </div>	
        </div>
    </div>
</section>
