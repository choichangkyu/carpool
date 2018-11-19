/**
 * 리스트 페이지
 */
$(document).ready(function() {
	$("input").click(function(event) {
		var name = $(event.target).attr("name");
		switch (name) {
		case "new_post":
			location.href = "/Mission-Web-MVC01/board/add_post_form.do";
			break;
		case "mem_list":
			location.href = "/Mission-Web-MVC01/page/member/list_user.do";
			break;
		}
	});
	
	$("form[name=search]").submit(function(){
		if( $("select[name=category]").val() == "" ){
			alert("검색 카테고리를 입력하세요");
			return false;				
		}
		if( $("input[name=search]").val() == "" ){
			alert("검색어를 입력하세요");
			return false;
		}
		return true;
	});
});