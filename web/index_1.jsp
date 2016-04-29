<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <!-- Site made with Mobirise Website Builder v2.9, https://mobirise.com -->
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="generator" content="Mobirise v2.9, mobirise.com">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" href="assets/images/untitled-382x276-65.png" type="image/x-icon">
        <meta name="description" content="">

        <title>AgriMarket</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/animate.css/animate.min.css">
        <link rel="stylesheet" href="assets/mobirise/css/style.css">
        <link rel="stylesheet" href="assets/mobirise-gallery/style.css">
        <link rel="stylesheet" href="assets/mobirise-slider/style.css">
        <link rel="stylesheet" href="assets/mobirise/css/mbr-additional.css" type="text/css">
        <!-- Google Fonts -->
        <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,200,300,700,600' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700,300' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Raleway:400,100' rel='stylesheet' type='text/css'>

        <!-- Bootstrap -->
        <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">-->

        <!--Font Awesome--> 
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

        <!-- Custom CSS -->
        <link rel="stylesheet" href="assets/flat_cart/css/owl.carousel.css">
        <link rel="stylesheet" href="assets/flat_cart/style.css">
        <link rel="stylesheet" href="assets/flat_cart/css/responsive.css">


        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
        

        <script>

            function addToCart(name) {
                if (${empty sessionScope.user})
                    location.href = "sign_in.jsp";
                var jsonData = {
                    operation: "add",
                    timestamp: new Date().getTime(),
                    product: name
                };
                $.ajax({
                    url: 'Cart',
                    type: 'GET',
                    contentType: 'application/json',
                    data: jsonData,
                    dataType: 'json',
                    success: function (data) {
                        refreshCartAgain(data);
                    }
                });
            }
            function refreshCart() {

                var total = 0;
            <c:set var="items" value="${sessionScope.user.cart.items}"/>

                var i = 0;
            <c:forEach items="${items}" var="item">
                total +=${item.quantity} *${item.product.price};
                i++;
            </c:forEach>
                $("#total").html("$" + total);
                $("#itemNo").html("${fn:length(items)}");
            }
            function refreshCartAgain(cart) {

                var total = 0;
                var items = cart.items;
                for (i = 0; i < items.length; i++) {
                    total += (items[i].product.price * items[i].quantity);
                }
                $("#total").html("$" + total);
                $("#itemNo").html(items.length);
            }
            $("document").ready(function () {
                refreshCart();

                $(".pane .delete").click(function () {
                    $(this).parents(".pane").animate({opacity: 'hide'}, "slow");
                    var id = $(this).attr("id");
                    var jsonData = {
                        operation: "delete",
                        timestamp: new Date().getTime(),
                        product: id
                    };
                    $.ajax({
                        url: 'Cart',
                        type: 'GET',
                        contentType: 'application/json',
                        data: jsonData,
                        dataType: 'json',
                        success: function (data) {
                            refreshCartAgain(data);
                        }
                    });
                });


                $("input.quantity").keydown(function (e) {
                    e.preventDefault();
                });

                $("img.productA").keydown(function (e) {
                    var id = $(this).attr("id");
                    alert(id);
                });

                $("input.quantity").change(function () {
                    var id = $(this).attr("id");
                    var jsonData = {
                        operation: "quantity",
                        newVal: $(this).val(),
                        timestamp: new Date().getTime(),
                        product: id
                    };
                    $.ajax({
                        url: 'Cart',
                        type: 'GET',
                        contentType: 'application/json',
                        data: jsonData,
                        dataType: 'json',
                        success: function (data) {
                            refreshCartAgain(data);
                        }
                    });
                });


            });
        </script>
    </head>
    <body >

        <section class="engine"><a rel="external" href="https://mobirise.com">Mobirise web design software
            </a></section>
        <section class="mbr-navbar mbr-navbar--freeze mbr-navbar--absolute mbr-navbar--transparent mbr-navbar--sticky mbr-navbar--auto-collapse" id="menu-0">
            <div class="mbr-navbar__section mbr-section">
                <div class="mbr-section__container container">
                    <div class="mbr-navbar__container">
                        <div class="mbr-navbar__column mbr-navbar__column--s mbr-navbar__brand">
                            <span class="mbr-navbar__brand-link mbr-brand mbr-brand--inline">
                                <span class="mbr-brand__logo"><a href="index.jsp"><img class="mbr-navbar__brand-img mbr-brand__img" src="assets/images/untitled-382x276-11.png" alt="AgriMarket" title="Flower Shop"></a></span>
                                <span class="mbr-brand__name"><a class="mbr-brand__name text-white" href="index.jsp">AGRIMARKET</a></span>
                            </span>
                        </div>
                        <div class="mbr-navbar__hamburger mbr-hamburger text-white"><span class="mbr-hamburger__line"></span></div>
                        <div class="mbr-navbar__column mbr-navbar__menu">
                            <nav class="mbr-navbar__menu-box mbr-navbar__menu-box--inline-right">
                                <div class="mbr-navbar__column">
                                    <ul class="mbr-navbar__items mbr-navbar__items--right mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-decorator mbr-buttons--active">
                                        <li class="mbr-navbar__item">
                                            <a class="mbr-buttons__link btn text-white" href="index.jsp">HOME</a>
                                        </li>

                                    </ul></div>
                                <div class="mbr-navbar__column">
                                    <ul class="mbr-navbar__items mbr-navbar__items--right mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-inverse mbr-buttons--active">
                                        <li class="mbr-navbar__item">
                                            <a  id="asignin" class="mbr-buttons__btn btn btn-default" href="sign_in.jsp">
                                                <p id="signin">Sign In</p></a>

                                        </li>
                                        <c:if test="${!empty sessionScope.user}">
                                            <script type="text/javascript">

                                                $("#signin").text('${sessionScope.user.userName}');
                                                $("#asignin").attr("href", "profile.jsp");

                                            </script>
                                            <li class="mbr-navbar__item">
                                                <div class="shopping-item" style="margin-top: 0px; margin-left:35px;">
                                                    <a href="cart.jsp">Cart - <span class="cart-amunt" id="total">$800</span> <i class="fa fa-shopping-cart"></i> <span class="product-count" id="itemNo">5</span></a>
                                                </div>
                                            </li>
                                        </c:if>
                                    </ul></div>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-box mbr-section mbr-section--relative mbr-section--fixed-size mbr-section--full-height mbr-section--bg-adapted mbr-parallax-background mbr-after-navbar" id="header1-1"  style="background-image: url(assets/images/istock-000016896298xlarge-4200x2833-56.jpg);">
            <div class="mbr-box__magnet mbr-box__magnet--sm-padding mbr-box__magnet--center-left">
                <div class="mbr-overlay" style="opacity: 0.2; background-color: rgb(0, 0, 0);"></div>
                <div class="mbr-box__container mbr-section__container container">
                    <div class="mbr-box mbr-box--stretched"><div class="mbr-box__magnet mbr-box__magnet--center-left">
                            <div class="row"><div class=" col-sm-6 col-sm-offset-6">
                                    <div class="mbr-hero animated fadeInUp">
                                        <h1 class="mbr-hero__text">AGRIMARKET</h1>
                                        <p class="mbr-hero__subtext">Agricultural Market in Egypt</p>
                                    </div>

                                </div></div>
                        </div></div>
                </div>
                <div class="mbr-arrow mbr-arrow--floating text-center">
                    <div class="mbr-section__container container">
                        <a class="mbr-arrow__link" href="#footer1-8"><i class="glyphicon glyphicon-menu-down"></i></a>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-section" id="header3-8">
            <div class="mbr-section__container container mbr-section__container--first">
                <div class="mbr-header mbr-header--wysiwyg row">
                    <div class="col-sm-8 col-sm-offset-2">
                        <h3 class="mbr-header__text">TITLE !</h3>
                        <p class="mbr-header__subtext">SUBTITLE</p>
                    </div>
                </div>
            </div>
        </section>

        <section class="mbr-section" id="content1-9">
            <div class="mbr-section__container container mbr-section__container--middle">
                <div class="row">
                    <div class="mbr-article mbr-article--wysiwyg col-sm-8 col-sm-offset-2">Text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text.</div>
                </div>
            </div>
        </section>

        <section class="mbr-gallery mbr-section mbr-section--no-padding" id="gallery2-6">
            <!-- Gallery -->
            <div class="container mbr-section__container mbr-gallery-layout-article mbr-section__container--last">
                <div class=" col-sm-8 col-sm-offset-2">
                    <div class="row mbr-gallery-row">
                        <jsp:include page="getallproducts"/>
                        <c:forEach items="${applicationScope.products}" var="product">
                            <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12 mbr-gallery-item">
                                <a  onclick="addToCart('${product.name}');" data-slide-to="0" data-toggle="modal">
                                    <c:if test="${empty sessionScope.user}">
                                        <script>
                                            $("#${product.name}").href = "sign_in.jsp";
                                        </script>
                                    </c:if>
                                    <img id="image"  class="productA"  alt="" src=${product.imageUrl}>
                                    <c:if test="${empty product.imageUrl}">
                                        <script>
                                            $("#image").attr("src",${product.imageUrl});
                                        </script>
                                    </c:if>
                                    <span class="icon glyphicon glyphicon-plus">${product.name}</span>
                                </a>
                            </div>
                        </c:forEach>   
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>

        </section>



        <footer class="mbr-section mbr-section--relative mbr-section--fixed-size" id="footer1-8" style="background-color: rgb(68, 68, 68);">

            <div class="mbr-section__container container">
                <div class="mbr-footer mbr-footer--wysiwyg row">
                    <div class="col-sm-12">
                        <p class="mbr-footer__copyright">Copyright (c) 2016 AGRIMARKET.                
                            <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Terms of Use</a>  | <a class="mbr-footer__link text-gray" href="https://mobirise.com/">Privacy Policy</a></p>
                    </div>
                </div>
            </div>
        </footer>


        <script src="assets/web/assets/jquery/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/smooth-scroll/SmoothScroll.js"></script>
        <script src="assets/jarallax/jarallax.js"></script>
        <script src="assets/masonry/masonry.pkgd.min.js"></script>
        <script src="assets/imagesloaded/imagesloaded.pkgd.min.js"></script>
        <script src="assets/bootstrap-carousel-swipe/bootstrap-carousel-swipe.js"></script>
        <script src="assets/mobirise/js/script.js"></script>
        <script src="assets/mobirise-gallery/script.js"></script>

    </body>
</html>