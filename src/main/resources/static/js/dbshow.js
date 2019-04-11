$('.dim-btn-layer').click(function(){
    var $href = $(this).attr('href');
    layer_popup($href);
});

function layer_popup(el){

    var $el = $(el);        //레이어의 id를 $el 변수에 저장
    var isDim = $el.prev().hasClass('dimBg');   //dimmed 레이어를 감지하기 위한 boolean 변수

    isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

    var $elWidth = ~~($el.outerWidth()),
        $elHeight = ~~($el.outerHeight()),
        docWidth = $(document).width(),
        docHeight = $(document).height();

    // 화면의 중앙에 레이어를 띄운다.
    if ($elHeight < docHeight || $elWidth < docWidth) {
        $el.css({
            marginTop: -$elHeight /2,
            marginLeft: -$elWidth/2
        })
    } else {
        $el.css({top: 0, left: 0});
    }

    $el.find('a.dim-btn-layerClose').click(function(){
        isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
        return false;
    });

    $('.layer .dimBg').click(function(){
        $('.dim-layer').fadeOut();
        return false;
    });

}

function characterCheck(obj) {
    var RegExp = /[ \{\}\[\]\/?,;:|\)*~`!^\┼\#$%&\'\"\\\(\=]/gi;//정규식 구문
    // var obj = document.getElementsByName("cmtTxt")[0]
    if (RegExp.test(obj.value)) {
        alert("_ & . 을 제외한 특수문자는 입력하실 수 없습니다.");
        obj.value = obj.value.substring(0, obj.value.length - 1);//특수문자를 지우는 구문
    }
}
