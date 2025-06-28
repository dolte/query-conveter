﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿

// 지부 내에서 사용되는 상수 선언
// 입금 구분
var GIBU_INACC_AUTO  = "01";            //00147	01	자동이체
var GIBU_INACC_MICR  = "02";            //00147	02	MICR
var GIBU_INACC_NOAC  = "03";            //00147	03	무통장
var GIBU_INACC_OCR   = "04";            //00147	04	OCR
var GIBU_INACC_CARD  = "05";            //00147	05	카드

var GIBU_CLSED_HUUP  = "11";            //00141	11	휴업
var GIBU_CLSED_SURI  = "12";            //00141	12	수리
var GIBU_CLSED_STOP  = "13";            //00141	13	정지
var GIBU_CLSED_CLSD  = "14";            //00141	14	폐업
var GIBU_CLSED_DISA  = "15";            //00141	15	천재
var GIBU_CLSED_TKIN  = "16";            //00141	16	특인
var GIBU_CLSED_UNKO  = "17";            //00141	17	미확인
var GIBU_CLSED_SAGO  = "18";            //00141	18	사고
var GIBU_CLSED_ETC   = "19";            //00141	19	기타
//var GIBU_CLSED_UNLO  = "20";            //00141	20	폐업해제
                                  
// 지부_입금구분                 
var GIBU_GOSO_REQU   = "21";            //00147	21	고소 중
var GIBU_GOSO_COMP   = "22";            //00147	22	고소 해결

// 청구 구분
var GIBU_DEMD_AUTO  = "31";            //00147	31	자동이체
var GIBU_DEMD_MICR  = "32";            //00147	32	OCR
var GIBU_DEMD_NOAC  = "33";            //00147	33	MICR
var GIBU_DEMD_OCR   = "34";            //00147	34	개별지로
var GIBU_DEMD_CARD  = "35";            //00147	35	고소
var GIBU_DEMD_CARD  = "36";            //00147	36	선납
var GIBU_DEMD_CARD  = "38";            //00147	38	NO_ZIRO (CS 값)
var GIBU_DEMD_CARD  = "39";            //00147	39	기타

// 업소상태, 업소 등록 상태는 동일한 내용 (TBRA_UPSO.UPSO_STAT) 이나 
// UI 적용 시 구분할 필요에 따라 별도로 생성함  
// 지부_업소상태   
var GIBU_UPSOSTAT_MNG = "1";          //00161	1	관리 업소
var GIBU_UPSOSTAT_ING = "2";          //00161	2	개발 중 업소
var GIBU_UPSOSTAT_CLD = "3";          //00161	3	폐업 업소
                                            
// 지부_업소등록상태                        
var GIBU_REGSTAT_REGU = "1";          //00174	1	정식등록
var GIBU_REGSTAT_TEMP = "2";          //00174	2	가등록
                  
// 지부_납부자 정보/우편물 수령지   
var GIBU_RECV_UPSO    = "U";          //00176	U	영업장
var GIBU_RECV_PERM    = "B";          //00176	B	경영주
var GIBU_RECV_OWNE    = "O";          //00176	O	허가주

// 지부_도시 구분 
var GIBU_LOCATE_URBA  = "1";          //00178	1	도시
var GIBU_LOCATE_FARM  = "2";          //00178	2	농어촌     
             
// adrs 관련 변수 -----------------------------------------
var ADRS_IP_ADDRESS1  = "http://172.17.10.105/";
// Protocol Version
var v010 = "01.0";
// Message Gubun
var GB_REQUEST  = "1";   //Request메시지임을 나타냄
var GB_RESPONSE = "2";   //Response메시지임을 나타냄

//Message Type
var TYPE_LOGIN      = "L";  //로그인
var TYPE_CALL       = "C";  //콜
var TYPE_CALLUPDATE = "U";  //콜 상태 update

//Call Action
var CALL_BEGIN       = "B"; //START CALL
var CALL_ACTIVE      = "A"; //Alerting or Active Call
var CALL_TERMINATE   = "T"; //Call Terminate
var CALL_TRANSFER    = "R"; //Call Transfer
var CALL_HOLD        = "H"; //Hold Call
var CALL_PICKUP      = "P"; //n/a
var CALL_RETRIEVE    = "E"; //Retrieve Call
var CALL_ANSWER      = "N"; //Answer Call
var CALL_CONFERENCE  = "C"; //3-way Conference

// ip & port
var IPPBX_IP  = "112.175.137.140";
var IPPBX_PORT = 9000;
// adrs 관련 변수 -----------------------------------------

/*
    지부 리스트를 검색하여 ComboBox obj 에 설정한다.
    obj   : 지부 ComboBox
    GBN   : 0 지부관리팀 포함
          : 1 지부관리팀 제외  
*/

function gfn_GetDataGIBU_CD(obj, gbn)
{
	if (obj != null)
	{
		gfn_CreateDataSet("ds_GIBU_CD", "BRAN_CD,10,STRING GIBU,2,STRING DEPT_NM,20,STRING");
		gfn_syncCall("find_GIBU","KOMCA?SYSID=PATHFINDER&MENUID=1000001006007&EVENTID=c_gibu_select","","ds_GIBU_CD=SEL1","","fn_CallBack");
		
	    if (gbn == "1") ds_GIBU_CD.DeleteRow(0);
		obj.InnerDataset = "ds_GIBU_CD";
		obj.CodeColumn = "GIBU";
		obj.DataColumn = "DEPT_NM";

		obj.Value = gds_sessioninfo.GetColumn(0, "BRAN_CD");
		
		if(    gds_sessioninfo.GetColumn(0, "BRAN_CD") == "AL"
			|| gds_sessioninfo.GetColumn(0, "DEPT_CD") == "122040100" //전산1팀
			|| gds_sessioninfo.GetColumn(0, "DEPT_CD") == "120050000" //침해조사팀
			|| gds_sessioninfo.GetColumn(0, "DEPT_CD") == "114000000" //사무총장실
			|| gds_sessioninfo.GetColumn(0, "DEPT_CD") == "122010000" //분배팀
		   )
		{
			obj.Enable = true;
		}
		else
		{
			obj.Enable = false;
		}
	}
}

/*
    입금 구분 값을 검색한다.
    dsRCV : 입금구분 DataSet
    GBN   : 0 All 포함
          : 1 All 제외  
*/
function gfn_GetReptGubun(dsRCV, gbn)
{
	//gfn_CreateDataSet("ds_REPT_GBN", "CODE_CD,10,STRING CODE_NM,2,STRING");
	
	if (gbn == 0)   gfn_PubCode("dsRCV",1,"00147");
	else            gfn_PubCode("dsRCV",2,"00147");

	if (obj != null)
	{
		obj.InnerDataset = "dsRCV";
		obj.CodeColumn = "CODE_CD";
		obj.DataColumn = "CODE_NM";
	}
}


/*
    검색기간(년월) 간의 차 (월) 을 구한다.
    syymm : 시작년월
    eyymm : 종료년월
    
    return : 개월 차 
*/
function gfn_GetDiffMonth(syymm, eyymm)
{
    var syy = toNumber(substr(syymm, 0, 4));
    var smm = toNumber(substr(syymm, 4, 2));
    var eyy = toNumber(substr(eyymm, 0, 4));
    var emm = toNumber(substr(eyymm, 4, 2));

    emm = (eyy - syy) * 12 + emm;

    return (emm - smm + 1);
}

/*
    yymm 년월에 diff 월 이후의 년월을 구한다.
    yymm : 시작년월
    mm   : 이전 / 이후 월
    
    return : yymm 년월에 mm 월 이후의 년월
*/
function gfn_GetAddMonth(yymm, diff)
{
    var result = "";
    var yy = toNumber(substr(yymm, 0, 4));
    var mm = toNumber(substr(yymm, 4, 2));

    if (diff > 0) {
        mm = mm + diff;
        if (mm > 12) {
            mm = mm % 12;
            yy = yy + toInteger(mm / 12);    
        }    
    }
    else {
        yy = yy + toInteger(mm / 12);
        mm = toInteger(mm / 12) * 12 - mm; 
    }

    if (mm < 10) result = toString(yy) + "0" + toString(mm);
    else         result = toString(yy) + toString(mm);
        
    return result;
}

function gfn_LenStrMake(size, str, filldir, filler)
{
	var rtn = toString(str);
	var strlen = Lengthb(toString(rtn));

    if (Lengthb(filler) == 0) filler = " ";

	for(var i=0;i<size-strlen;i++)
	{
	    if (toUpper(filldir) == "L")
	        rtn = filler + rtn;
	    else 
		    rtn = rtn + filler;
	}

	return rtn;
}


function gfn_PopupUPSO_LIST(BRAN_CD, UPSO_CD, UPSO_NM)
{
    var inParam = "BRAN_CD=" + quote(BRAN_CD.index) + " UPSO_CD=" + quote(UPSO_CD.text) + " UPSO_NM=" + quote(UPSO_NM.text);
	var result = Dialog("fi_bra::find_upso.xml",inParam);
	
	if (length(result) > 0) 
	{
    	var vArr = result.split(",");
    	
    	if (UPSO_CD != null) UPSO_CD.Text = vArr[0];
    	if (UPSO_NM != null) UPSO_NM.Text = vArr[1];
    }
}

function gfn_UPSO_Info(BRAN_CD, UPSO_CD)
{
    if (UPSO_CD != null && UPSO_CD != "" && length(UPSO_CD) == 8)
    {
        gfn_CreateDataSet("ds_UpsoInfoIn" , "BRAN_CD,10,STRING UPSO_CD,8,STRING");
        gfn_CreateDataSet("ds_UpsoInfoOut", "UPSO_CD,8,STRING UPSO_NM,100,STRING");

        ds_UpsoInfoIn.clearData();
        ds_UpsoInfoOut.clearData();
        var row = ds_UpsoInfoIn.AddRow();
    	ds_UpsoInfoIn.SetColumn(row, "BRAN_CD", BRAN_CD);
    	ds_UpsoInfoIn.SetColumn(row, "UPSO_CD", UPSO_CD);

        gfn_syncCall("find_UPSO","KOMCA?SYSID=PATHFINDER&MENUID=1000001006007&EVENTID=p_upso_select","S=ds_UpsoInfoIn","ds_UpsoInfoOut=SEL1","","fn_CallBack");    

        return ds_UpsoInfoOut;
    }
    
    return null;
}

function gfn_GetGRAD_CD(grad)
{
	if (length(grad) == 3) {
        gfn_CreateDataSet("ds_GradIn" , "GRAD,10,STRING");
        gfn_CreateDataSet("ds_GradOut", "GRAD,10,STRING GRADNM,100,STRING STNDAMT,10,STRING");

        ds_GradIn.clearData();
        ds_GradOut.clearData();
        var row = ds_GradIn.AddRow();
    	ds_GradIn.SetColumn(row, "GRAD", grad);

        gfn_syncCall("GetGrad","KOMCA?SYSID=PATHFINDER&MENUID=1000001006007&EVENTID=grade_simple_select","S=ds_GradIn","ds_GradOut=SEL1","","fn_CallBack");    

        if (ds_GradOut.rowcount == 1) {
            var GRAD    = ds_GradOut.GetColumn(0, "GRAD");
            var GRADNM  = ds_GradOut.GetColumn(0, "GRADNM");
            var STNDAMT = ds_GradOut.GetColumn(0, "STNDAMT");
            if (length(GRAD) > 0) {
                return (GRAD + "," + GRADNM + "," + STNDAMT);
            } 
        }
	}
	
	return null;
}

function gfn_GetUPSO_CD(BRAN_CD, UPSO_CD, UPSO_NM)
{
    if (length(BRAN_CD) > 0 && (length(UPSO_CD) > 0 || length(UPSO_NM) > 0))
    {
        gfn_CreateDataSet("ds_SimpleUPSOIn" , "BRAN_CD,10,STRING UPSO_CD,100,STRING UPSO_NM,100,STRING");
        gfn_CreateDataSet("ds_SimpleUPSOOut", "BRAN_CD,10,STRING UPSO_CD,10,STRING UPSO_NM,100,STRING GRADNM,100,STRING NEW_DAY,8,STRING");

        ds_SimpleUPSOIn.clearData();
        ds_SimpleUPSOOut.clearData();
        var row = ds_SimpleUPSOIn.AddRow();
    	ds_SimpleUPSOIn.SetColumn(row, "BRAN_CD", BRAN_CD);
    	ds_SimpleUPSOIn.SetColumn(row, "UPSO_CD", UPSO_CD);
    	ds_SimpleUPSOIn.SetColumn(row, "UPSO_NM", UPSO_NM);
        gfn_syncCall("find_UPSO","KOMCA?SYSID=PATHFINDER&MENUID=1000001006007&EVENTID=upso_simple_select","S=ds_SimpleUPSOIn","ds_SimpleUPSOOut=MRG1","","fn_CallBack");    

        if (ds_SimpleUPSOOut.rowcount == 1) {
            var rBRAN_CD = ds_SimpleUPSOOut.GetColumn(0, "BRAN_CD");

			if (rBRAN_CD == BRAN_CD) {
				var rUPSO_CD = ds_SimpleUPSOOut.GetColumn(0, "UPSO_CD");
				var rUPSO_NM = ds_SimpleUPSOOut.GetColumn(0, "UPSO_NM");
				var rGRADNM  = ds_SimpleUPSOOut.GetColumn(0, "GRADNM");
				var rNEW_DAY = ds_SimpleUPSOOut.GetColumn(0, "NEW_DAY");
				if (length(rUPSO_CD) > 0) {
					return (rUPSO_CD + "," + rUPSO_NM + "," + rGRADNM + "," + rNEW_DAY);
				} 
			}
        }
    }
    
    return null;
}

/*
    필수 입력 검사 --> 공통함수 gfn_CheckFormNull 이용할 것
    
    obj : 필수 입력 component
    return : true/false
*/
function gfn_RequiredCheck(obj, msg)
{
    var cont = "";

    if (obj.GetType() == "Edit"){
        cont = trim(obj.Text);
    }
    else if (obj.GetType() == "Calendar" || obj.GetType() == "Combo" || 
             obj.GetType() == "Radio"    || obj.GetType() == "MaskEdit") {
        cont = trim(obj.value);
   }

	//if (cont == null || cont == "" || trim(cont) == "") {
	if (length(cont) == 0) {
		gfn_Confirm("MB_OK", msg, "필수항목누락", "0");
		gfn_SetStatusMsg(msg);                            
		obj.SetFocus();                                           
		return false;                                                                
	}
	
	return true;                                                           
}

/*
    대표 업종코드를 조회한다.

    obj   : 업종 ComboBox
    GBN   : 0 전체 포함
          : 1 전체 제외  
*/
function gfn_GetDataBSTYP(obj, gbn)
{
	if (obj != null)
	{
		gfn_CreateDataSet("ds_BSTYP", "GRAD_GBN,2,STRING GRAD_NM,100,STRING");
		gfn_syncCall("find_GIBU","KOMCA?SYSID=PATHFINDER&MENUID=1000001006007&EVENTID=m_grade_select","","ds_BSTYP=SEL1","","fn_CallBack");

	    if (gbn == "1") ds_BSTYP.DeleteRow(0);
		obj.InnerDataset = "ds_BSTYP";
		obj.CodeColumn = "GRAD_GBN";
		obj.DataColumn = "GRAD_NM";
	}
}

/*
    지부별로 관리되는 은행 계좌번호를 조회한다.
    
    BRAN_CD : 지부코드
    RETURN DATA : 은행 계좌 정보 dataset
*/
function gfn_GetDataAccountInfo(BRAN_CD)
{
	gfn_CreateDataSet("ds_BankAccountIn" , "BRAN_CD,2,STRING");
	gfn_CreateDataSet("ds_BankAccountOut", "ACCN_SEQ,5,STRING ACCN_NUM,25,STRING BANK_CD,7,STRING BANK_NM,50,STRING BRAN_CD,2,STRING BRAN_NM,20,STRING USAG,60,STRING");

	ds_BankAccountIn.clearData();
	ds_BankAccountOut.clearData();
	
	var row = ds_BankAccountIn.AddRow();
	ds_BankAccountIn.SetColumn(row, "BRAN_CD", BRAN_CD);
	gfn_syncCall("find_account","KOMCA?SYSID=PATHFINDER&MENUID=1000001006007&EVENTID=accn_num_select","S=ds_BankAccountIn","ds_BankAccountOut=SEL1","","fn_CallBack");

	return ds_BankAccountOut;
}

/*
	지부별 공통버튼 권한관리
*/
function gfn_SetPermission(BRAN_CD)
{
	var gv_BRAN_CD = gds_sessioninfo.GetColumn(0, "BRAN_CD");
	var btnEnabled = false;

	if (gv_BRAN_CD == "AL" || gv_BRAN_CD == BRAN_CD 
	|| gds_sessioninfo.GetColumn(0, "DEPT_CD") == "122040100" //전산1팀
	|| gds_sessioninfo.GetColumn(0, "DEPT_CD") == "120050000" //침해조사팀
	|| gds_sessioninfo.GetColumn(0, "DEPT_CD") == "114000000" //사무총장실
	|| gds_sessioninfo.GetColumn(0, "DEPT_CD") == "122010000" //분배팀
	){
	 	btnEnabled = true;
	}

	var objBtn;
	var comps = this.parent.dvtitle.divIcon.Components["Button"];
	for( var i = 0 ; i < comps.Count ; i++)
	{
		objBtn = object("dvtitle.divIcon." + comps[i].id);
		
		//2014-03-14 요청. 타지부의 경우 조회도 안되게...
		//if (objBtn.UserData != "SEARCH" && objBtn.UserData != "CLOSE" ) 
		if ( objBtn.UserData != "CLOSE" ) 
		{
			objBtn.Enable = btnEnabled;
		}
	}
}

/*
	지부별 화면별 버튼 권한관리
*/
function gfn_SetInnerPermission(BTNs)
{
	var btnEnabled = false;
	var objBtn;
	var comps = this.parent.dvtitle.divIcon.Components["Button"];
	for(var i=0; i < comps.Count; i++)
	{
		objBtn = object("dvtitle.divIcon." + comps[i].id);
		if (objBtn.UserData == "SAVE") 
		{
			btnEnabled = objBtn.Enable;
		}
	}

	var vArr = BTNs.split(",");
	for (var i=0; i<vArr.length; i++) {
		objBtn = object(trim(vArr[i]));
		objBtn.Enable = btnEnabled;
	}
}

/*
	회계 계좌번호 연동
*/
function gfn_GetAccountUsage(REPT_GBN)
{
	var USE_TYPE = '';
	
	// 00310 (001:무통장, 002:지로, 자동이체, 003:신용카드, 004:채권추심 009:기타)
	if     (REPT_GBN == "01")	USE_TYPE = "002";
	else if(REPT_GBN == "02")  	USE_TYPE = "002";
	else if(REPT_GBN == "03")  	USE_TYPE = "001";
	else if(REPT_GBN == "04")  	USE_TYPE = "002";
	else if(REPT_GBN == "05")  	USE_TYPE = "003";
	else if(REPT_GBN == "06")  	USE_TYPE = "004";
	else						USE_TYPE = "009";
	
	return USE_TYPE;
}

/*
	금액 한글 처리
*/
function fn_getKrPrc(sPrc)
{
	if (length(sPrc) > 16) return "";
	
	var kr = "";
	var krPrc = "";
    var unit = "원십백천만십백천억십백천조십백천";
	
	// 조 단위 까지 처리함
	for (var i=length(sPrc); i>0; i--) 
	{ 
		kr = fn_getKr(substr(sPrc, (length(sPrc)-i), 1));
		if (length(kr) > 0) {
			//if ((kr == "일") && i > 1) kr = "";
			krPrc = krPrc + kr + substr(unit, (i-1), 1);
		}
		else {
			if (((i-1)%4) == 0 && length(krPrc) > 0) {
				krPrc = krPrc + kr + substr(unit, (i-1), 1);
			}
		}
	}
	
	return krPrc;
}

function fn_getKr(num)
{
	if      (num == 1) return "일";
	else if (num == 2) return "이";
	else if (num == 3) return "삼";
	else if (num == 4) return "사";
	else if (num == 5) return "오";
	else if (num == 6) return "육";
	else if (num == 7) return "칠";
	else if (num == 8) return "팔";
	else if (num == 9) return "구";
	else if (num == 0) return "";
}

function fn_checkTerm(start_day, end_day)
{
	if (toNumber(start_day) > toNumber(end_day)) {
		return false;
	}
	else {
		return true;
	}
}