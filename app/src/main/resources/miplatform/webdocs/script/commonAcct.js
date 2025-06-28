﻿﻿﻿//New Script File

/**
* gfn_FindForm_Account()
* 작성일자 : 2009-07-14
* 개요       : 계정코드팝업
* return : params(문자열)
           gfn_GetRetValue() 이 함수를 이용하여 값을 가져온다.
           ex) gfn_GetRetValue(params,""acct_cd") ;
           
           parmas에 있는 문자열값들 :
           acct_cd,
           acct_nm,
           acct_nm_inq,
           job_gbn,
           job_gbn_nm,
           acct_class,
           acct_class_nm,
           drcr_gbn,
           drcr_gbn_nm,
           slip_yn           
* 사용법    :	slip_yn : 재무제표양식등록에서는 %
                          나머지 프로그램에서는 'Y'를 기본으로 설정해서 사용한다.
			    sort_gb : 코드순이면 '1', 명순이면 '2'
                acctn_gbn   : 회계구분 전체는 '%'
gfn_FindForm_Account(slip_yn, sort_gb, acctn_gbn);
*/
function gfn_FindForm_Account(slip_yn,sort_gb,acctn_gbn,acct_nm,bg_yn){
    if(slip_yn == null || slip_yn == ""){
        alert("slip_yn 인수가 필요합니다.");
        return;
    }
    if(sort_gb == null || sort_gb == ""){
        alert("sort_gb 인수가 필요합니다.");
        return;
    }
    if(acctn_gbn == null || acctn_gbn == ""){
        alert("acctn_gbn 인수가 필요합니다.");
        return;
    }
	var strRetValue = "slip_yn="+quote(slip_yn)
			    +",sort_gb="+quote(sort_gb)
				+",acctn_gbn="+quote(acctn_gbn)
				+",acct_nm="+quote(acct_nm)
				+",bg_yn="+quote(bg_yn);
	return Dialog("ac_cac::cac01_s07_p01.xml","params="+strRetValue,"608","384","",-1,-1);
}