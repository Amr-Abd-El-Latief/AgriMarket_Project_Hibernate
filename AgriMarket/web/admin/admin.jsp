
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="product" class="model.pojo.Product" scope="request">
    <jsp:setProperty name="product" property="*" />
</jsp:useBean>

<c:if test="${user.valid()}">
    <jsp:forward page="SignUpUserController"/>
</c:if>
<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Site made with Mobirise Website Builder v2.9, https://mobirise.com -->
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="generator" content="Mobirise v2.9, mobirise.com">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="../assets/images/untitled-382x276-65.png" type="image/x-icon">
        <meta name="description" content="">
        <title>AgriMarket</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="../assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="../assets/animate.css/animate.min.css">
        <link rel="stylesheet" href="../assets/mobirise/css/style.css">
        <link rel="stylesheet" href="../assets/mobirise-gallery/style.css">
        <link rel="stylesheet" href="../assets/mobirise-slider/style.css">
        <link rel="stylesheet" href="../assets/mobirise/css/mbr-additional.css" type="text/css">
        <link rel="stylesheet" href="../assets/mobirise/css/main.css" type="text/css">
        <link rel="stylesheet" href="../assets/mobirise/css/popup.css" type="text/css">



    </head>
    <body>
        <section class="engine"><a rel="external" href="https://mobirise.com">Mobirise free website builder
            </a></section>
        <section class="mbr-navbar mbr-navbar--freeze mbr-navbar--absolute mbr-navbar--transparent mbr-navbar--sticky mbr-navbar--auto-collapse" id="menu-33">
            <div class="mbr-navbar__section mbr-section">
                <div class="mbr-section__container container">
                    <div class="mbr-navbar__container">
                        <div class="mbr-navbar__column mbr-navbar__column--s mbr-navbar__brand">
                            <span class="mbr-navbar__brand-link mbr-brand mbr-brand--inline">
                                <span class="mbr-brand__logo"><a href="index.jsp"><img class="mbr-navbar__brand-img mbr-brand__img" src="../assets/images/untitled-382x276-11.png" alt="AgriMarket" title="Flower Shop"></a></span>
                                <span class="mbr-brand__name"><a class="mbr-brand__name text-white" href="index.jsp">AGRIMARKET</a></span>
                            </span>
                        </div>
                        <div class="mbr-navbar__hamburger mbr-hamburger text-white"><span class="mbr-hamburger__line"></span></div>
                        <div class="mbr-navbar__column mbr-navbar__menu">
                            <nav class="mbr-navbar__menu-box mbr-navbar__menu-box--inline-right">
                                <div class="mbr-navbar__column"><ul class="mbr-navbar__items mbr-navbar__items--right mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-decorator mbr-buttons--active"><li class="mbr-navbar__item"><a class="mbr-buttons__link btn text-white" href="profile_admin.jsp">Profile</a></li> </ul></div>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-box mbr-section mbr-section--relative mbr-section--fixed-size mbr-section--full-height mbr-section--bg-adapted mbr-parallax-background mbr-after-navbar" id="header1-40" style="background-image: url(../assets/images/istock-000016896298xlarge-4200x2833-32.jpg);">
            <div class="mbr-box__magnet mbr-box__magnet--sm-padding mbr-box__magnet--center-left">
                <div class="mbr-overlay" style="opacity: 0.2; background-color: rgb(34, 34, 34);"></div>
                <div class="mbr-box__container mbr-section__container container">
                    <div class="mbr-box mbr-box--stretched"><div class="mbr-box__magnet mbr-box__magnet--center-left">
                            <div class="row"><div class=" col-sm-6 col-sm-offset-6">
                                    <div class="mbr-hero animated fadeInUp">
                                        <h1 class="mbr-hero__text">AGRIMARKET</h1>
                                        <p class="mbr-hero__subtext">Agricultural Market in Egypt</p>
                                    </div>
                                    <div class="mbr-buttons btn-inverse mbr-buttons--left"><a class="mbr-buttons__btn btn btn-lg animated fadeInUp delay btn-primary" href="#header3-41">PRODUCT MANAGMENT</a> <a class="mbr-buttons__btn btn btn-lg animated fadeInUp delay btn-info" href="https://mobirise.com">USER MANAGMENT</a></div>
                                </div></div>
                        </div></div>
                </div>
                <div class="mbr-arrow mbr-arrow--floating text-center">
                    <div class="mbr-section__container container">
                        <a class="mbr-arrow__link" href="#header3-41"><i class="glyphicon glyphicon-menu-down"></i></a>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-section" id="header3-41">
            <div class="mbr-section__container container mbr-section__container--first">
                <div class="mbr-header mbr-header--wysiwyg row">
                    <div class="col-sm-8 col-sm-offset-2">
                        <h3 class="mbr-header__text" style="    margin-left: 180px;">Product Management</h3>

                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-gallery mbr-section mbr-section--no-padding" id="gallery2-38">
            <!-- Gallery -->
            <div class="container mbr-section__container mbr-gallery-layout-article mbr-section__container--last">





                <div class="panel-group" id="accordion">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                                    ADD NEW PRODUCT</a>
                            </h4>
                            <span id="add-form"></span>
                        </div>
                        <div id="collapse1" class="panel-collapse collapse in">
                            <div class="panel-body"> 
                                <div class="container" style="margin-left: -89px;">
                                    <form class="form-horizontal" role="form" method="post" onsubmit="return validateProductForm()">
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="name">Product Name</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" name="name" id="name" placeholder="product name" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="price">Price</label>
                                            <div class="col-sm-10">          
                                                <input type="text"   class="form-control" name="price" id="price" placeholder="price $" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="categoryId">Category:</label>
                                            <div class="col-sm-10">          
                                                <div class="dropdown">
                                                    <button class="btn btn-primary dropdown-toggle" type="button" name="categoryId" data-toggle="dropdown">Dropdown Example
                                                        <span class="caret"></span></button>
                                                    <ul class="dropdown-menu">
                                                        <li><a href="#">product1</a></li>
                                                        <li><a href="#">product1</a></li>
                                                        <li><a href="#">product1</a></li>
                                                    </ul>
                                                </div>   
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="quantity">Quantity</label>
                                            <div class="col-sm-10">          
                                                <input type="text" class="form-control" id="quantity" name="quantity" placeholder="quantity" required>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="image" >Image</label>
                                            <div class="col-sm-10">          
                                                <span class="btn btn-default btn-file">
                                                    Browse <input type="file" required name="image" >
                                                </span>                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-2" for="desc">Description</label>
                                            <div class="col-sm-10">          
                                                <textarea class="form-control" rows="5" id="desc" name="desc" required></textarea>
                                            </div>
                                        </div>

                                        <div class="form-group">        
                                            <div class="col-sm-offset-2 col-sm-10">
                                                <button type="submit" class="btn btn-default" id="add">ADD</button>
                                            </div>
                                        </div>
                                    </form>
                                </div></div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
                                    View Products</a>
                            </h4>
                        </div>
                        <div id="collapse2" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class=" col-sm-12 col-sm-offset-2" style="    margin-left: 3px;">
                                    <!--<div class="row mbr-gallery-row">-->
                                    <%--<c:forEach items="${users}" var="user" >--%>

                                    <div class="col-lg-2 col-md-4 col-sm-6 col-xs-12 mbr-gallery-item">
                                        <span>product name</span>
                                        <a href="#lb-gallery2-38" data-slide-to="0" data-toggle="modal">
                                            <img alt="" src="../assets/images/images-4-275x183-96-275x183-5.jpg">
                                            <span class="icon glyphicon glyphicon-zoom-in">product name</span>
                                        </a>
                                        <div class="container" style="margin-left: -13px;margin-top: 3px;">
                                            <a  id="modify-product"><span class="glyphicon glyphicon-edit" >Modify</span></a>
                                            <a  href="#"><span class="glyphicon glyphicon-remove" style="margin-left: 24px;">Delete</span></a>
                                        </div>
                                    </div>

                                    <%--</c:forEach>--%>

                                    <!--                           <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 mbr-gallery-item">
                                                                       <a href="#lb-gallery2-38" data-slide-to="1" data-toggle="modal">
                                                                           <img alt="" src="../assets/images/images-2-275x183-16-275x183-48.jpg">
                                                                           <span class="icon glyphicon glyphicon-zoom-in"></span>
                                                                       </a>
                                                                   </div>
                                                               <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 mbr-gallery-item">
                                                                       <a href="#lb-gallery2-38" data-slide-to="2" data-toggle="modal">
                                                                           <img alt="" src="../assets/images/download-2-275x183-80-275x183-28.jpg">
                                                                           <span class="icon glyphicon glyphicon-zoom-in"></span>
                                                                       </a>
                                                                   </div>
                                                               </div>-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="clearfix"></div>
            </div>

            <!-- Lightbox -->
            <div data-app-prevent-settings="" class="mbr-slider modal fade carousel slide" tabindex="-1" data-keyboard="true" data-interval="false" id="lb-gallery2-38">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body">
                            <ol class="carousel-indicators">
                                <li data-app-prevent-settings="" data-target="#lb-gallery2-38" class=" active" data-slide-to="0"></li><li data-app-prevent-settings="" data-target="#lb-gallery2-38" data-slide-to="1"></li><li data-app-prevent-settings="" data-target="#lb-gallery2-38" data-slide-to="2"></li>
                            </ol>
                            <div class="carousel-inner">
                                <div class="item active">
                                    <img alt="" src="../assets/images/images-4-275x183-96.jpg">
                                </div><div class="item">
                                    <img alt="" src="../assets/images/images-2-275x183-16.jpg">
                                </div><div class="item">
                                    <img alt="" src="../assets/images/download-2-275x183-80.jpg">
                                </div>
                            </div>
                            <a class="left carousel-control" role="button" data-slide="prev" href="#lb-gallery2-38">
                                <span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="right carousel-control" role="button" data-slide="next" href="#lb-gallery2-38">
                                <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>

                            <a class="close" href="#" role="button" data-dismiss="modal">
                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                                <span class="sr-only">Close</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <footer class="mbr-section mbr-section--relative mbr-section--fixed-size" id="footer1-35" style="background-color: rgb(68, 68, 68);">

            <div class="mbr-section__container container">
                <div class="mbr-footer mbr-footer--wysiwyg row">
                    <div class="col-sm-12">
                        <p class="mbr-footer__copyright">Copyright (c) 2016 AGRIMARKET. <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Terms of Use</a>  | <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Privacy Policy</a></p>
                    </div>
                </div>
            </div>
        </footer>

        <!-- modify product-->                          
        <div id="signup">

            <div  class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" id="close-btn-signup" class="close" data-dismiss="modal">
                            <span aria-hidden="true">×</span>
                            <span class="sr--only close-modal common-sprite">Close</span>
                        </button>
                        <p>Edit Product </p>
                    </div>
                    <div class="modal-body">
                        <div >
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="product-name-modify">Product Name</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="product-name-modify" placeholder="product name">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="price-modify">Price</label>
                                    <div class="col-sm-10">          
                                        <input type="text" class="form-control" id="price-modify" placeholder="price $">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="pwd">Category:</label>
                                    <div class="col-sm-10">          
                                        <div class="dropdown">
                                            <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Dropdown Example
                                                <span class="caret"></span></button>
                                            <ul class="dropdown-menu">
                                                <li><a href="#">product1</a></li>
                                                <li><a href="#">product1</a></li>
                                                <li><a href="#">product1</a></li>
                                            </ul>
                                        </div>   
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="pwd">Quantity</label>
                                    <div class="col-sm-10">          
                                        <input type="text" class="form-control" id="quantity-modify" placeholder="quantity 1,2,3...">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="pwd">Image</label>
                                    <div class="col-sm-10">          
                                        <span class="btn btn-default btn-file">
                                            Browse <input type="file">
                                        </span>                                            </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-2" for="dec-modify">Description</label>
                                    <div class="col-sm-10">          
                                        <textarea class="form-control" rows="5" id="dec-modify" ></textarea>
                                    </div>
                                </div>

                                <div class="form-group">        
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-default">ADD</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div><!---modal-body--->
                </div>
            </div>
        </div>
        <!-- end modify product-->
        <script src="../assets/web/assets/jquery/jquery.min.js"></script>
        <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="../assets/smooth-scroll/SmoothScroll.js"></script>
        <script src="../assets/jarallax/jarallax.js"></script>
        <script src="../assets/masonry/masonry.pkgd.min.js"></script>
        <script src="../assets/imagesloaded/imagesloaded.pkgd.min.js"></script>
        <script src="../assets/bootstrap-carousel-swipe/bootstrap-carousel-swipe.js"></script>
        <script src="../assets/mobirise/js/script.js"></script>
        <script src="../assets/mobirise-gallery/script.js"></script>
        <script src="../assets/js/index.js"></script>

    </body>
</html>