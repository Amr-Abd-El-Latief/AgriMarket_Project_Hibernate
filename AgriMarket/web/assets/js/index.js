/* Card.js plugin by Jesse Pollak. https://github.com/jessepollak/card */


//modify product
 
 


$("#close-btn-signup").click(function () {
    $("#signup").css("display", "none");
    $("#nav-right").css("display", "block");
        $(".footer").css("display", "block");

});


$("#modify-product").click(function () {
    $("#signup").css("display", "block");
 });




function validateEmail(email) {
    var filter = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
    if (filter.test(email)) {
        return true;
    } else {
        return false;
    }
}


// validation Login form 
function validateProductForm(){
    var reg = /^\d+$/;
     var product_price = $("#price").val();
    var product_quantity = $("#quantity").val();
     if ( !reg.test(product_price) || !reg.test(product_quantity)) {
        $("#add-form").text("Quantity  and price  must be number only").fadeToggle(5000,function (){$("#add-form").css("display","none");});
        $("#add-form").css("color","red");
        return false;
    }
    return true;

}


$('form').card({
    container: '.card-wrapper',
    width: 280,

    formSelectors: {
        nameInput: 'input[name="first-name"], input[name="last-name"]'
    }
});


 