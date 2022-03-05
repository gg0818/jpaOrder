var thisScroll=0;
$(function(){
   $(document).on("click", "#dimmed", function(){
       if($("#wrap").hasClass("fixed")){
           $(".layerpop").hide();
           $("#dimmed").hide();
           $("#wrap").removeClass("fixed");
           $("html, body").animate({scrollTop:thisScroll}, 300);
       }
   });

   if($(".cartDiv").length != 0){
       var cnt = parseInt($(".cartDiv #count").val());
       var price = parseInt($(".cartList .price span").text()) / cnt;
       $(".cartDiv .count a").each(function(){
           $(this).on("click", function(){
               if ($(this).hasClass("minus")) {
                   if(cnt == 1){
                       alert("1개 이하는 주문할 수 없습니다.");
                       return false;
                   }
                   cnt-=1;
               }else{
                   cnt+=1;
               }
               $(this).siblings("input").val(cnt);
               $(this).parents(".cartList").find(".price span").text(price * cnt);
               $(".orderInfo .price span").text(price * cnt);
           });
       });
   }

});

//메뉴 팝업 호출
function menuPop(e){
    thisScroll = $(window).scrollTop();
    var id = $(e).attr("data-id");
    var img = $(e).find("img").attr("src");
    var tit = $(e).find(".tit").text();
    var info = $(e).find(".info").text();
    var price = $(e).find(".price span").text();
    var cnt = 0;

    var $menuPop = $(".layerpop.menuOrder");
    $menuPop.find("#id").val(id);
    $menuPop.find(".tit").text(tit);
    $menuPop.find("img").attr("src",img);
    $menuPop.find(".info").text(info);
    $menuPop.find(".price span").text(price);
    $menuPop.find(".total .price span").text("0");
    $("#wrap").addClass("fixed");
    $("#dimmed").fadeIn(300);
    $menuPop.fadeIn(300);

    $menuPop.find(".count a").each(function(){
        $(this).on("click", function(){
            if ($(this).hasClass("minus")) {
                if(cnt == 0) return false;
                cnt-=1;
            }else{
                cnt+=1;
            }
            $(this).siblings("input").val(cnt);
            $(".menuOrder .total .cnt span").text(cnt);
            $(".menuOrder .total .price span").text(price * cnt);
        });
    });

}

//팝업 닫기
function popClose(e){
    $(".layerpop").hide();
    $("#dimmed").hide();
    $("#wrap").removeClass("fixed");
    $("html, body").animate({scrollTop:thisScroll}, 300);
}

//메뉴 cart form check
function menuCart(){
    if($(".menuOrder #count").val() == 0){
        alert("최소 1개 이상 주문 가능합니다.");
        return false;
    }else{
        return true;
    }
}

