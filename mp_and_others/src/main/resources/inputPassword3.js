//用于进行图片上传，返回地址
function setImg(obj){
    var f=$(obj).val();
    alert(f);
    console.log(obj);
    if(f == null || f ==undefined || f == ''){
        return false;
    }
    if(!/\.(?:png|jpg|bmp|gif|PNG|JPG|BMP|GIF)$/.test(f))
    {
        alert("类型必须是图片(.png|jpg|bmp|gif|PNG|JPG|BMP|GIF)");
        $(obj).val('');
        return false;
    }
    var data = new FormData();
    console.log(data);
    $.each($(obj)[0].files,function(i,file){
        data.append('file', file);
    });
    console.log(data);
    $.ajax({
        type: "POST",
        url: GLOBAL_INFO.WEBURL_PREFIX+"business/uploadImg.xhtml",
        data: data,
        cache: false,
        contentType: false,    //不可缺
        processData: false,    //不可缺
        dataType:"json",
        success: function(ret) {
            console.log(ret);
            if(ret.code==0){
                $("#photoUrl").val(ret.result.url);//将地址存储好
                $("#photourlShow").attr("src",ret.result.url);//显示图片
                alertOk(ret.message);
            }else{
                alertError(ret.message);
                $("#url").val("");
                $(obj).val('');
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("上传失败，请检查网络后重试");
        }
    });
}