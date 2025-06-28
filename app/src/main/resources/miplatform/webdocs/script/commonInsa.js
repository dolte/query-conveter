﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿
//---------------------------------------
// Grid Calendar
//---------------------------------------
var gv_rtnDate;
var gv_rtnDFlag;
var gv_objBindDs;
var	gv_grdRow;
var	gv_grdColId;
var gv_objGrid;
var gv_openChkCal = false;
var gv_rtnMonth;
var gv_openChkMon = false;
var gv_creatDs = false;

/****************************************************************
* FUNCTION NAME     : gfn_PostSearch
* FUNCTION DESC		: 우편번호 검색 
* @param 
*      postType      : 리턴받는 타입
                       "1" :  edit 박스 ,리턴 RcvPost1, RcvPost1
                       "2" : 그리드 ,RcvPost1
*      RcvDS         : 리턴받는 DataSet 
*      nRow          : DataSet의 Row
*      RcvPost1      :  postType 1 : 우편번호 앞 3자리, postType 2 : 우편번호 6자리  리턴
*      RcvPost2      :  postType 1 : 우편번호 뒤 3자리, postType 2 : 없음  리턴
*      RcvAddr       :  주소  
                     
* @return
*****************************************************************/
function gfn_InsaPostSearch(postType,RcvDS,nRow,RcvPost1,RcvPost2,RcvAddr)
{ 
	if( postType == 1 )
	{
		Dialog("fi_pub::com01_r08.xml", "postType=1 postNum1=" + RcvPost1 + " postNum2=" + RcvPost2 + " addr=" + RcvPost2, "");
	}
	else if( postType == 2 )
	{
		Dialog("fi_pub::com01_r08.xml", "postType=2 dsName=" + RcvDS + " prNrow=" + nRow + " postNum=" + RcvPost1 + " addr=" + RcvAddr + "", "");
	}
 
}

/****************************************************************
* FUNCTION NAME     : gfn_InsaDSListCode
* FUNCTION DESC		: 공통코드를 가져오는 함수
* @param 
*      RcvDS         : 리턴받는 DataSet
*      SelectType    : 보여주는 형태
                       "1" : 명
                       "2" : 명, all(전체)
                       "3" : 코드+명
                       "4" : 코드+명, all
*      COND_CD         : 조건 값 
*      eventID        : PB에서 생성한 eventID
*						biplc_code_select  : 사업장코드 조회
*						dept_code_select  : 부서코드 조회
*                       emp_pop_select     : 사원 전체
* @return
*****************************************************************/
function gfn_InsaDSListCode(RcvDS,SelectType,cond_nm,eventID)
{ 
	gfn_CreateDataSet("ds_insapubcode","COND_NM,20,STRING SELECT_TYPE,10,STRING"); 
	ds_insapubcode.DeleteAll();
	VAR AROW = ds_insapubcode.AddRow(); 
	ds_insapubcode.SetColumn(AROW,"COND_NM",cond_nm);
	
	 // -- ALL -- 추가 여부  	if(SelectType == "1" || SelectType   == "3")
	{
		ds_insapubcode.SetColumn(AROW,"SELECT_TYPE","ALL"); 
	} 
	 
	if( Length(trim(eventID)) <= 0 ) 
	{
		eventID = "biplc_select";
	}  
    gfn_syncCall("svcSearch_init","KOMCA?SYSID=PATHFINDER&MENUID=1000002001001003&EVENTID="+eventID,"S=ds_insapubcode",RcvDS+"=SEL1","","gfn_CallBack");
	 
	 
    var DSobj = Object(RcvDS); 
	
	//출력형식: [CODE_CD]CODE_NM
	if(SelectType == "3" || SelectType == "4"){  
		for(var i=0; i<DSobj.rowcount; i++)
		{
			if( Length(trim(DSobj.GetColumn(i,"CODE_CD"))) > 0 )
			{
				DSobj.setColumn(i,"CODE_NM","[" + DSobj.GetColumn(i,"CODE_CD") + "]" + DSobj.GetColumn(i,"CODE_NM"));
			}
			trace(DSobj.GetColumn(i,"CODE_NM"));
		}
	}	 
 
}

//그리드 포커스 주기
function gfn_INSAsetGridFocus(obj, nRow, nCellId) { 
	var objGrd = object(objGrdId);
	var objDs = object(objGrd.BindDataset);
	var nCellIdx = objGrd.GetBindCellIndex("body",nCellId);
	objDs.RowPos = nRow;
	objGrd.SetCellPos(nCellIdx);
	objGrd.SetFocus();
}

/********************************************************
*  구분자로 연결된 String값에서 원하는 특정 item찾기
*  strString : 구분자로 연결된 String 값
*  item : strString 에서 찾고자 하는 값
********************************************************/
function gfn_GetRetValue(strString, item)
{
	var strList = Split(strString, ",");
	
	for(var i=0 ; i<Length(strList) ; i++)
	{
		var itemList = Split(strList[i],"=");
		
		if(Trim(itemList[0]) == item)
		{
			return Trim(itemList[1]);
		}
	}
}

/*====================================================================
= 기능 : 콤보박스에 리스트를 조회 저장한다.
= 인수 : strPrefix	 	
				 menuID			pb의 menuID
				 eventID		pb의 eventID
				 
				 Recvds			return dataSet
=====================================================================*/
function gfn_getComboCode(menuID,eventID,ds){
	gfn_syncCall("CommonPup","KOMCA?SYSID=PATHFINDER&MENUID="+menuID+"&EVENTID="+eventID,"",ds+"=SEL1","","fn_CallBack");
}
																																		

/*====================================================================
= 기능 : Modal(Dialog) Form Open(Popup Form)
= 인수 : strPrefix	 	Open File Path(Directory)
				 strFormID			Open Form file Name
				 strInArgument		Argument (id, value)
				 nWidth				Popup Form Width
				 nHeight			Popup Form Height
				 strOpenStyle		Popup Form Open Style 설정
				 nLeft				Popup Form nX(Left Position)
				 nTop				Popup Form nY(Top Position)
= Return : Value(return value)
=====================================================================*/
function gfn_InsaDialog(strPrefix,strFormID,strInArgument,nWidth,nHeight,strOpenStyle,nLeft,nTop)
{
	
	var strURL = strPrefix + "::" + strFormID ;
	
	if(nLeft.trim().length() == 0) nLeft = -1;
	if(nTop.trim().length() == 0) nTop = -1;
	 
	var return_val = Dialog(strURL,strInArgument,nWidth,nHeight,strOpenStyle,nLeft,nTop);
 
	return return_val;
} 
/**********************************************************************************
*  사원찾기 팝업
*  params : emp_no(사원코드[명])
*      ex) params = "emp_no=홍길동"
*  isBtnPress : 찾기 버튼을 누를를경우 true, 에디트박스에서 엔터키를 이용할때 false
   isBtnPress : 데이터가 한건일경우 바로 값을 가져온다. true ,
*  return Value : emp_no(사원코드), emp_nm(사원명)
**********************************************************************************/
function gfn_InsaFindForm_Employee(params,isBtnPress)
{
	return gfn_InsaDialog("hr_cpm","cpm00_r00.xml","params=isBtnPress="+isBtnPress+","+params,600,600,"true",-1,-1);
}
function gfn_InsaFindForm_Bipum(params,isBtnPress)
{
	return gfn_InsaDialog("hr_cpm","cpm00_r08.xml","params=isBtnPress="+isBtnPress+","+params,600,600,"true",-1,-1);
}

function gfn_sintakFindForm_house(params,isBtnPress)
{    alert('insaform');
	return gfn_InsaDialog("fi_pub","com00_r01.xml","params=isBtnPress="+isBtnPress+","+params,600,600,"true",-1,-1);}

function gfn_SinK_FindForm_Employee(params,isBtnPress)
{
	return gfn_InsaDialog("hr_ofl","ofl00_r00.xml","params=isBtnPress="+isBtnPress+","+params,600,600,"true",-1,-1);
}
function gfn_Tofl_FindForm_Employee(params,isBtnPress)
{
	return gfn_InsaDialog("hr_ofl","ofl00_r01.xml","params=isBtnPress="+isBtnPress+","+params,600,600,"true",-1,-1);
}
//채권자 등록 팝업
function gfn_BsCon_FindForm_Employee(params,isBtnPress)
{
	return gfn_InsaDialog("fi_tac","tac07_r00.xml","params=isBtnPress="+isBtnPress+","+params,600,300,"true",-1,-1); 
}
//채권채무등록  팝업
function gfn_BsCon_FindForm_Employee_re(params,isBtnPress)
{
	return gfn_InsaDialog("fi_tac","tac07_r01.xml","params=isBtnPress="+isBtnPress+","+params,600,300,"true",-1,-1); 
}
/*====================================================================
* FUNCTION NAME     : gfn_SetCalendar
* FUNCTION DESC 	: PopupDiv Calendar Set (공통)
* @param 
* obj			
*			Grid Component ID
*			nRow			Current Row
*			nCell 		Selected Cell 
* @return
=====================================================================*/
function gfn_INASSetCalendar(objGrd, nRow, nCell, chkFlg){	
         
	gv_objGrid = objGrd;
	
	if (chkFlg) gv_rtnDFlag = true;
	else gv_rtnDFlag = false;
	
	var objBDs =  gv_objGrid.BindDataset;
	gv_objBindDs = object(objBDS).id;
	
	gv_grdRow = nRow;
	gv_grdColId =  gv_objGrid.GetCellProp("Body",nCell, "ColId");
	//alert(ds_List.saveXML());
	//alert("gv_grdColId::" + gv_grdColId);
	var str_val = object(gv_objBindDs).GetColumn(nRow, gv_grdColId);
	
	var arr_val =  gv_objGrid.GetCellRect(nRow,nCell);	
	var div_x = ClientToScreenX(objGrd, arr_val[0]);
	var div_y = ClientToScreenY(objGrd, arr_val[1]);
	var div_w = arr_val[2] - arr_val[0];
	var div_h = arr_val[3] - arr_val[1];
	
	if (gv_openChkCal == false){
		Create("PopupDiv", "PopDiv_Calendar", 'width="166" height="146"');
		gv_openChkCal = true;
	}
	
	PopDiv_Calendar.Contents = gfn_INSASetPopDivCalContent(str_val);
	PopDiv_Calendar.TrackPopup(div_x, div_y, div_w, div_h);	
	
	return gv_rtnDate;
}		

	
/*===============================================================
= 기능 : PopupDiv Calendar Set Contents (공통)
= 인수 : str_val		Selected Date
= 결과 : return  		PopupDiv Contents				 
= 예제 : ngmf_SetColumnAdd(value)
===============================================================*/
function gfn_INSASetPopDivCalContent(str_val){
	var str_temp;
	
	str_temp += '<Contents>' + chr(10);
	str_temp += '<Calendar Border="Flat" ClickedBkColor="#64c2c8" ClickedTextColor="user9" DayFont="Tahoma,9"  ' + chr(10); 
	str_temp += 'Font="돋움,9" HeaderBorder="NONE" HeaderFont="굴림,9,Bold" Height="146" Id="CAL_PopupDiv" LeftMargin="2"  ' + chr(10); 
	str_temp += 'MonthOnly="TRUE" MonthPickerFormat="yyyy년&#32;MMMM" NullValue="&#32;" OnDayClick="gfn_INSACalDayClick" RightMargin="2"  ' + chr(10); 
	str_temp += 'SaturdayTextColor="#1cafb1" SelectedDayFont="Tahoma,9,Bold" Style="edit_style7" SundayTextColor="#ff780a"  ' + chr(10); 
	str_temp += 'TitleBKColor="#73cbca" TitleTextColor="user9" UseTrailingDay="FALSE" Value="' + str_val + '" WeekBKColor="#24b2b4"  ' + chr(10); 
	str_temp += 'WeekColor="MENU" WeeksFont="Tahoma,9" Width="166"></Calendar> ' + chr(10); 
	str_temp += '</Contents>';
	
	return str_temp;
}	

/*===============================================================
= 기능 : Calendar Date DayClick Event (공통)
= 인수 : obj				Calendar Component ID
= 예제 : ngmf_CalDayClick(obj,strText)
===============================================================*/
function gfn_INSACalDayClick(obj,strText){
	//alert(gv_rtnDFlag + "::" + obj.ID + "::" + strText);
	if (gv_rtnDFlag) gv_rtnDate = strText;
	else {
		//alert(gv_grdRow + "::" + gv_grdColId + "::" + strText);
		object(gv_objBindDs).SetColumn(gv_grdRow, gv_grdColId, strText);
	}
	PopDiv_Calendar.ClosePopup();
}
/****************************************************************
* FUNCTION NAME     : gfn_InsaPubCode
* FUNCTION DESC		: 공통코드를 가져오는 함후
* @param 
*      RcvDS         : 리턴받는 DataSet
*      SelectType    : 보여주는 형태
                       "1" : 명
                       "2" : 명, all(전체)
                       "3" : 코드+명
                       "4" : 코드+명, all
*      high_cd       : 공통코드 (그룹)
* @return
*****************************************************************/
function gfn_InsaPubCode(RcvDS,high_cd)
{
	var eventID;
	gfn_CreateDataSet("ds_pubcode","HIGH_CD,1,STRING");
	ds_pubcode.DeleteAll();
	VAR AROW = ds_pubcode.AddRow();
	ds_pubcode.SetColumn(AROW,"HIGH_CD",high_cd);
	
	//alert(eventID+ " : " + RcvDS);
    gfn_syncCall("svcSearch_init","KOMCA?SYSID=PATHFINDER&MENUID=1000002001001003&EVENTID=searchCommon","S=ds_pubcode",RcvDS+"=SEL1","","gfn_CallBack");
}


/****************************************************************
* FUNCTION NAME     : gfn_InsaCloseCheck
* FUNCTION DESC		: 마감여부 체크
* @param 
*      CLOSE_YYMN    : 마감년월
*      CLOSE_GBN     : 마감 구분
                     
* @return  0 마감되지 않음 1 마감됨
*****************************************************************/
function gfn_InsaCloseCheck(CLOSE_YYMN,CLOSE_GBN){
    gfn_CreateDataSet("ds_insaclosecheck","CLOSE_YYMN,255,STRING CLOSE_GBN,255,STRING");
    gfn_CreateDataSet("ds_insaclosecheckResult","END_YN,255,STRING");
    ds_insaclosecheck.DeleteAll();
    ds_insaclosecheckResult.DeleteAll();
    var addRow = ds_insaclosecheck.AddRow();
    var result;
    
    ds_insaclosecheck.setcolumn(addRow,"CLOSE_YYMN",CLOSE_YYMN);
    ds_insaclosecheck.setcolumn(addRow,"CLOSE_GBN",CLOSE_GBN);
    
    gfn_syncCall("magamCheck","KOMCA?SYSID=PATHFINDER&MENUID=1000002001001015&EVENTID=magamCheck","S=ds_insaclosecheck","ds_insaclosecheckResult=SEL1","","gfn_CallBack");    
    result = ds_insaclosecheckResult.getcolumn(0,"END_YN");
    
    destroy("ds_insaclosecheck");
    destroy("ds_insaclosecheckResult");
    
    return result;

}