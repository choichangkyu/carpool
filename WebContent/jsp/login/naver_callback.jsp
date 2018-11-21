<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<head>
<script type="text/javascript"
	src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body>
	<script type="text/javascript">
		var naver_id_login = new naver_id_login("f7aj3kWnxJf7Qzs_qweZ",
				"http://127.0.0.1:8888/carpool/login/naver_login.do");
		// 접근 토큰 값 출력
		//alert(naver_id_login.oauthParams.access_token);
		// 네이버 사용자 프로필 조회
		naver_id_login.get_naver_userprofile("naverSignInCallback()");
		// 네이버 사용자 프로필 조회 이후 프로필 정보를 처리할 callback function
		function naverSignInCallback() {
			var name = naver_id_login.getProfileData('name');
			var gender = naver_id_login.getProfileData('gender');
			var email = naver_id_login.getProfileData('email');
			var id = naver_id_login.getProfileData('id');
			var loginId = email.substring(0, email.indexOf('@'));
			console.log(name);
			console.log(gender);
			console.log(email);
			console.log(id);
			console.log(loginId);

			$.ajax({
				url : 'http://127.0.0.1:8888/carpool/member/add_user.do',
				type : 'post',
				data : {
					'name' : name,
					'gender' : gender,
					'email' : email,
					'id' : id,
					'loginId' : loginId
				},
				success : callback,
				error : function(xhr, ajaxOptions, thrownError) {
					alert(xhr.status);
					alert(thrownError);
				}
			});
		}
		function callback(data) {
			if( data.trim() == 1){
				alert("회원가입 성공");
			}else{
				alert("회원가입 실패");
			}
			console.log(data);
			//location.reload();
		}
	</script>
</body>
</html>