<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header id="header"><!--header-->
    <div class="header_top"><!--header_top-->
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="contactinfo">
                        <ul class="nav nav-pills">
                            <li><a href="#"><i class="fa fa-phone"></i> ${contact.mobile}</a></li>
                            <li><a href="#"><i class="fa fa-envelope"></i> ${contact.email}</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="social-icons pull-right">
                        <ul class="nav navbar-nav">
                            <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                            <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                            <li><a href="#"><i class="fa fa-linkedin"></i></a></li>
                            <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                            <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                            <li></li><li></li>
                            <li><a href="cmsLogout"><i class="fa fa-lock"></i> Logout</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header_top-->



    <div class="header-bottom"><!--header-bottom-->
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div class="mainmenu pull-left">
                        <ul style="font-size: 10px" class="nav navbar-nav collapse navbar-collapse">
                            <li><a href="cmsHome" >Home</a></li>
                            <li><a href="cmsProductManagement">Products Management</a></li> 
                            <li class="dropdown"><a href="#">Pages Management<i class="fa fa-angle-down"></i></a>
                                <ul role="menu" class="sub-menu">
                                    <li><a href="cmsContactPage">Edit Contact Page</a></li>
                                </ul>
                            </li> 
                            <li class="dropdown"><a href="#">Edit Home Slideshow<i class="fa fa-angle-down"></i></a>
                                <ul role="menu" class="sub-menu">
                                    <li><a href="cmsEditSlide/${1}">Edit First Slide</a></li>
                                    <li><a href="cmsEditSlide/${2}">Edit Second Slide</a></li>
                                    <li><a href="cmsEditSlide/${3}">Edit Third Slide</a></li>
                                </ul>
                            </li> 
                            <li><a href="cmsViewMessages" >View Messages</a></li>
                            <li><a href="commands" >View Commands</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-3">
                        <form action="searchEdit" class="searchform" method="POST">
                            <input type="text" name="modelul" placeholder="Search" />
                            <button type="submit" class="btn btn-default"><i class="fa fa-arrow-circle-o-right"></i></button>
                        </form>

                </div>
            </div>
        </div>
    </div><!--/header-bottom-->
</header><!--/header-->