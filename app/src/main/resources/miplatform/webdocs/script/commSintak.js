﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿/*
 *	신탁 공통
 *
 */

/*
 * 기      능: 매체코드 호출
 * 인      수: Argument	설명
			  rcvDS : 수신 데이터셋 명
			  selectType : 1 - 매체코드그룹 반환
			               2 - 전체 포함 매체코드그룹 반환
			               3 - 다중매체코드 선택시(소분류코드로 중분류코드 그룹 알아오기)
			               4 - 전체 포함 다중매체코드 선택시(소분류코드로 중분류코드 그룹 알아오기)
			               5 - 소분류코드로 상위코드값 알아오기
			               6 - 소분류코드로 서비스코드 조회
			               
			  # selectType 1 ~ 2
			  comboNum : 1 - 대분류 콤보박스
			             2 - 중분류 콤보박스
			             3 - 소분류 콤보박스
			  
			  # selectType 3
			  comboNum : 1 - 매체코드로 대분류코드 알아오기
			             2 - 매체코드로 중분류코드 알아오기
			             3 - 매체코드로 소분류코드 알아오기
			             
			  # selectType 4
			  comboNum : 1 - 기본인자
			  
			  # selectType 1 ~ 3
			  highCd : 상위콤보박스에서 선택된 하위콤보의 시퀀스값
			  apprvPgmNm : 해당 프로그램의 ID값
			  
			  # selectType 4
			  highCd : 서비스코드 조회시 필요한 소분류코드값
			  
 * Return    : Return Value	
 * 예     시 : 함수 사용 예	
 */
#include "script::common.js"  	// 공통 js

function gfn_PubMdmCode(rcvDS, selectType, comboNum, highCd, apprvPgmNm){
	var eventID;	gfn_CreateDataSet("ds_PubMdmCode", "HIGH_CD,8,STRING PGM_ID,8,STRING");
		ds_PubMdmCode.DeleteAll();
	var arow = ds_PubMdmCode.AddRow();
	ds_PubMdmCode.SetColumn(arow, "HIGH_CD", highCd);
	ds_PubMdmCode.SetColumn(arow, "PGM_ID", apprvPgmNm);
	
	if (selectType == 1)
	{
		if (comboNum == 1)
		{
			eventID = "mdm_select1";
		}
		else if (comboNum == 2)
		{
			eventID = "mdm_select2";
		}
		else if (comboNum == 3)
		{
			eventID = "mdm_select3";
		}
		else if (comboNum == 4)
		{
			eventID = "mdm_select4";
		}
	}	
	else if (selectType == 2)
	{		if (comboNum == 1)
		{
			eventID = "mdm_select_all1";
		}
		else if (comboNum == 2)
		{
			eventID = "mdm_select_all2";
		}
		else if (comboNum == 3)
		{
			eventID = "mdm_select_all3";
		}
		else if (comboNum == 4)
		{
			eventID = "mdm_select_all4";
		}	}
	else if (selectType == 3)
	{
		if (comboNum == 1)
		{
			eventID = "mdm_upside_select3";
		}
		else if (comboNum == 2)
		{
			eventID = "mdm_upside_select2";
		}
		else if (comboNum == 3)
		{
			eventID = "mdm_upside_select1";
		}
	}
	else if (selectType == 4)
	{
		if (comboNum == 1)
		{
			eventID = "svccd_select";
		}
		else if (comboNum == 2)
		{
			eventID = "svccd_select_all";
		}
	}
	else if( selectType == 5)
	{
		if (comboNum == 1)
		{
			eventID = "mdm_dis_select1";
		}
		else if (comboNum == 2)
		{
			eventID = "mdm_dis_select2";
		}
		else if (comboNum == 3)
		{
			eventID = "mdm_dis_select3";
		}
		else if (comboNum == 4)
		{
			eventID = "mdm_dis_select4";
		}
	}
    gfn_syncCall("svcMdmSearch", "KOMCA?SYSID=PATHFINDER&MENUID=1000001010&EVENTID=" + eventID,
		"S=ds_PubMdmCode", rcvDS + "=SEL1", "", "gfn_CallBack");
	
}
/*
 * 기      능: 매체코드 콤보박스 선택값 반환
 * 인      수: Argument	설명
 * Return    : Return Value	
 * 예     시 : 함수 사용 예	
 */
function gfn_Mdmcd_Val(cobType, dsName, mdmCol, cobName1, cobName2, cobName3, cobName4, cobName5)
{
	if (cobType == 1)
	{
		if (Object(cobName2).Value = "0" && Object(cobName3).Value = "0")
		{
			Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, "");
		}
		else
		{
			if (Object(cobName2).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName2).Value);
			}
			if (Object(cobName3).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName3).Value);
			}
		}
	}
	else if (cobType == 2)
	{
		if (Object(cobName2).Value = "0" && Object(cobName3).Value = "0" && Object(cobName4).Value = "0")
		{
			Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, "");
		}
		else
		{
			if (Object(cobName2).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName2).Value);
			}
			if (Object(cobName3).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName3).Value);
			}
			if (Object(cobName4).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName4).Value);
			}
		}
	}
	else if (cobType == 3)
	{
		if (Object(cobName1).Value = "0" && Object(cobName2).Value = "0" 
			&& Object(cobName3).Value = "0" && Object(cobName4).Value = "0")
		{
			Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, "");
		}
		else
		{
			if (Object(cobName1).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName1).Value);
			}
			if (Object(cobName2).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName2).Value);
			}
			if (Object(cobName3).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName3).Value);
			}
			if (Object(cobName4).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName4).Value);
			}
		}
	}
	else if (cobType == 4)
	{
		if (Object(cobName1).Value = "0" && Object(cobName2).Value = "0" 
			&& Object(cobName3).Value = "0" && Object(cobName4).Value = "0")
		{
			Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, "");
			Object(dsName).SetColumn(Object(dsName).getLastRow(), cobName5, "");
		}
		else
		{
			if (Length(Object(cobName1).Value) > 0 && Object(cobName1).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), cobName5, Object(cobName1).Value);
			}
			if (Length(Object(cobName2).Value) > 0 && Object(cobName2).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), cobName5, Object(cobName2).Value);
			}
			if (Length(Object(cobName3).Value) > 0 && Object(cobName3).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), cobName5, Object(cobName3).Value);
			}
			if (Length(Object(cobName4).Value) > 0 && Object(cobName4).Value != "0")
			{
				Object(dsName).SetColumn(Object(dsName).getLastRow(), mdmCol, Object(cobName4).Value);
			}
		}
	}
	
}

/* * 기      능: 경로및 ID 구하기 * 인      수: @obj = obj			   @lev = 1 : 최하위 객체 ID					  2 : 폼하위의 하위 객체 ID					  3 : 폼 하위 객체 ID					  4 : 폼ID * Return    : Return Value	 * 예     시 : 함수 사용 예	 */function gfn_GetComp(obj, lev){	var rtn = "";  	var sPath = "";
	if (sPath == "")	{
		sPath = toString(obj.id);	}
	if (toString(obj) == "[Global]")	{		arr = split(sPath,"::");		for ( var i = (arr.length()-1); i >=0; i -- ) 		{			rtn = rtn + arr[i] + "::";		}		sPath = substr(rtn, 0, length(rtn) - 2);		return;	}
	var obj = obj.getForm();
	if (lev = 1)	{
		sPath = toString(obj.id);	}
	else if (lev = 2)	{
		sPath = toString(obj.id) + "." + sPath; 	}
	else if (lev = 3)	{
		sPath = GetCurrentComponent() + "." + toString(obj.id) + "." + sPath; 	}
	else if	(lev = 4)	{
		sPath = this.ID + "." + GetCurrentComponent() +  "." + toString(obj.id) + "." + sPath; 	}
	else if	(lev = 5)	{
		sPath = this.ID + "." + GetCurrentComponent() +  "." + toString(obj.id); 	}
	else if	(lev = 6)	{
		sPath = GetCurrentComponent(); 	}
	else if	(lev = 7)	{
		sPath = toString(obj.id); 	}
	else if	(lev = 8)	{
		sPath = this.ID; 	}
	else if	(lev = 9)	{
		sPath = sPath; 	}
	return sPath;
}


/*기타 공연*/
function gfn_PubMdmEtcCode(rcvDS, selectType, comboNum, highCd ){
	var eventID = "";	gfn_CreateDataSet("ds_PubMdmCode", "HIGH_CD,8,STRING PGM_ID,8,STRING");
		ds_PubMdmCode.DeleteAll();
	var arow = ds_PubMdmCode.AddRow();
	ds_PubMdmCode.SetColumn(arow, "HIGH_CD", highCd);

	var rcvSEL = "";
	if (selectType == 0 )
	{
		if (comboNum == 1)
		{
			eventID = "mem_etc_select1";
			rcvSEL  = "SEL1";
		}
		else if (comboNum == 2)
		{
			eventID = "mdm_etc_select2";
			rcvSEL	= "MRG1";
		}
		else if (comboNum == 3)
		{
			eventID = "mdm_etc_select3";
			rcvSEL  = "SEL1";
		}
		else if (comboNum == 4)
		{
			eventID = "mdm_etc_select4";
			rcvSEL  = "SEL1";
		}
	}	

    gfn_syncCall("svcMdmEtcSearch", "KOMCA?SYSID=PATHFINDER&MENUID=1000001010&EVENTID=" + eventID,
		"S=ds_PubMdmCode", rcvDS + "=" + rcvSEL , "", "gfn_CallBack");
	
}

function gfn_ProgramID( val_mdmcd )
{
	var mdmcd = left( val_mdmcd , 1 );
	var pgmID = "";
	switch( mdmcd )
	{
		case "A":
			pgmID = "lev02_s04";
			break;
		
		case "B":
			pgmID = "lev02_s02";
			break;
		
		case "C":
			if( left( val_mdmcd , 2 ) == "CA" )
			{
				pgmID = "lev02_s15";
			}
			else if( left( val_mdmcd , 2 ) == "CB" )
			{
				if( left( val_mdmcd , 4 ) == "CB99" )
				{
					pgmID = "lev02_s13";
				}
				else
				{
					pgmID = "lev02_s14";
				}
			}
			else if( left( val_mdmcd , 2 ) == "CC" )
			{
				if( left( val_mdmcd , 4 ) == "CC03" )
				{
					pgmID = "lev02_s18";
				}
				else if( left( val_mdmcd , 4) == "CC04" )
				{
					pgmID = "lev02_s22";
				}
				else
				{
					pgmID = "lev02_s10";
				}
			}
			else if( left( val_mdmcd , 2 ) == "CD" )
			{
				pgmID = "lev02_s07";
			}
			else if( left( val_mdmcd , 2 ) == "CE" )
			{
				pgmID = "lev02_s06";
			}
			else if( left( val_mdmcd , 2 ) == "CG" )
			{
				pgmID = "lev02_s09";
			}
			else if( left( val_mdmcd , 2 ) == "CH" )
			{
				pgmID = "lev02_s08";
			}
			break;
			
		case "D":
			if( left( val_mdmcd , 2 ) == "DA" )
			{
				pgmID = "lev02_s01";
			}
			else if(left( val_mdmcd , 2 ) == "DC" )
			{
				pgmID = "lev02_s21";
			}
			else if(left( val_mdmcd , 2 ) == "DB")
			{
			    pgmID = "lev02_s21";
			}
			break;
	
		default: break;
	}
	
	return pgmID;
}

function gfn_ProdName( prodcd )
{
	var prodnm;
	gfn_CreateDataSet("ds_prodsearch", "PROD_CD,8,STRING PROD_NM,8,STRING");
	
	ds_prodsearch.DeleteAll();
	var arow = ds_prodsearch.AddRow();
	ds_prodsearch.SetColumn(arow, "PROD_CD", prodcd);
	
	gfn_syncCall("svcProdnmSearch", "KOMCA?SYSID=PATHFINDER&MENUID=1000001001&EVENTID=prodnm_select" ,
					"S=ds_prodsearch", "ds_prodsearch=SEL1", "", "gfn_CallBack");
					
	if( ds_prodsearch.rowcount == 1 )
	{
		prodnm = ds_prodsearch.GetColumn( 0 , "PROD_NM");
	}
	else
	{
		prodnm = "";
	}
	destroy("ds_prodsearch");
	return prodnm;
}

function gfn_Member_Name( Mbcd )
{
	var mbnm;
	gfn_CreateDataSet("ds_mem_name", "MB_CD,8,STRING MB_NM,8,STRING");
	
	ds_mem_name.DeleteAll();
	var arow = ds_mem_name.AddRow();
	ds_mem_name.SetColumn(arow, "MB_CD", Mbcd);
	
	gfn_syncCall("svcMbnmSearch", "KOMCA?SYSID=PATHFINDER&MENUID=1000001001&EVENTID=mem_name_search" ,
					"S=ds_mem_name", "ds_mem_name=SEL1", "", "gfn_CallBack");
	
	
	if( ds_mem_name.rowcount == 1 )
	{
		mbnm = ds_mem_name.GetColumn( 0 , "MB_NM");
	}
	else
	{
		mbnm = "";
	}
	destroy("ds_mem_name");
	return mbnm; 
}

function gfn_ProgramName( val_pgmID )
{
	var pgmID 	= val_pgmID;
	var pgmName = "";
	
	switch( val_pgmID )
	{
		case "lev02_s02" : 
			pgmName = "전송서비스";
			break;
		case "lev02_s04" : 
			pgmName = "방송";
			break;
		case "lev02_s15" : 
			pgmName = "음반";
			break;
		case "lev02_s10" :
			pgmName = "영상";
			break;
		case "lev02_s18" : 
			pgmName = "방송영상";
			break;
		case "lev02_s06" : 
			pgmName = "출판";
			break;
		case "lev02_s07" : 
			pgmName = "반주기기";
			break;
		case "lev02_s08" : 
			pgmName = "광고";
			break;
		case "lev02_s09" : 
			pgmName = "영화";
			break;
		case "lev02_s13" : 
			pgmName = "기타";
			break;
		case "lev02_s14" : 
			pgmName = "선거로고송";
			break;	
		case "lev02_s01" : 
			pgmName = "무대공연";
			break;	
		case "lev02_s21" : 
			pgmName = "유선공연";
			break;
		case "lev02_s22" :
			pgmName = "포토앨범";
		default: break;
	}
	
	return pgmName;
}


/*
 * 기      능: 	정산자료 청구된 자료인지 확인
 * 인      수: 	obj_list : DataSet String명과 IUD flag,
 * Return    : 	boolean
 * 예     시 : 	gfn_usedemdrept_check("DataSetName,UD") Update, Delete 확인
 *				gfn_usedemdrept_check("DataSetName,IUD") Insert, Update, Delete 확인
 * 				gfn_usedemdrept_check("DataSetName1,UD DataSetName2,IUD") 여러개의 DataSet 확인
 */
function gfn_usedemdrept_check(obj_list)
{
	if(!IsValid(Object("gds_TLEV_USEDEMDREPT_CHECK_SEARCH"))) gfn_CreateDataSet("gds_TLEV_USEDEMDREPT_CHECK_SEARCH", "APPRV_NUM,11,STRING CLR_NUM,10,STRING DEMD_NUM,15,STRNIG");
	else gds_TLEV_USEDEMDREPT_CHECK_SEARCH.DeleteAll();

	if(!IsValid(Object("gds_TLEV_USEDEMDREPT_CHECK"))) gfn_CreateDataSet("gds_TLEV_USEDEMDREPT_CHECK", "APPRV_NUM,11,STRING CLR_NUM,10,STRING DEMD_NUM,15,STRNIG");
	else gds_TLEV_USEDEMDREPT_CHECK.DeleteAll();
	
	//Object List 배열로 담기.
	var objInfo = split(obj_list,"/ /");

	//DataSet Update Check 및 Update data DataSet에 담기
	for(var j = 0 ; j < length(objInfo) ; j++){
		
		var check_value = split(objInfo[j],",");
		
		//Object
		var obj = Object(check_value[0]);
		//Check Value
		var p_check_value = check_value[1];
		
		if(obj.GetUpdate() == true){
			gfn_usedemdrept_check_sub(obj,p_check_value,gds_TLEV_USEDEMDREPT_CHECK_SEARCH);
		}
	}
	
	//Update data가 있으면 확인
	if( gds_TLEV_USEDEMDREPT_CHECK_SEARCH.rowcount > 0 ){
		gfn_SyncCall("", "KOMCA?SYSID=PATHFINDER&MENUID=1000001009007&EVENTID=clrrec_usedemdrept_check", 
			"S=gds_TLEV_USEDEMDREPT_CHECK_SEARCH",
			"gds_TLEV_USEDEMDREPT_CHECK=SEL10", "", "fn_CallBack" );
		
		if(length(gds_TLEV_USEDEMDREPT_CHECK.getColumn(0,"DEMD_NUM")) > 0 ) {
			var clipBoardText = "이미 청구서가 발행된 정산자료 입니다.\n수정이 불가능 합니다."+
				"\n승인번호 : " + gds_TLEV_USEDEMDREPT_CHECK.getColumn(0,"APPRV_NUM") +
				"\n정산번호 : " + gds_TLEV_USEDEMDREPT_CHECK.getColumn(0,"CLR_NUM") +
				"\n청구번호 : " + gds_TLEV_USEDEMDREPT_CHECK.getColumn(0,"DEMD_NUM");
			
			alert(clipBoardText);
			ClearClipboard(); // 클립보드를 Clear합니다.
			SetClipBoard("CF_TEXT",clipBoardText);  // 클립보드에 저장
			return false;
		}
	}
	return true;
}

function gfn_usedemdrept_check_sub(obj,p_check_value,p_ds_search){

	for(var i = 0 ; i < obj.RowCountNF() ; i ++){
		//Update Type
		var s_getRowType = toLower(obj.GetRowTypeNF(i));
		
		if(p_check_value == "UD")
			if(s_getRowType != 'update' && s_getRowType != 'delete') continue;
		else if(p_check_value == "IUD")
			if(s_getRowType != 'update' && s_getRowType != 'delete' && s_getRowType != 'insert') continue;
		
		var v_apprv_num = obj.GetColumnNF(i,"APPRV_NUM");
		var v_clr_num = obj.GetColumnNF(i,"CLR_NUM");
		
		if(p_ds_search.SearchRow(" APPRV_NUM=='"+v_apprv_num+"'"
								+"&& CLR_NUM=='"	+v_clr_num	+"'"  ) > -1) continue;
		
		var addRow = p_ds_search.AddRow();
		
		p_ds_search.setColumn(addRow , "APPRV_NUM" 	, v_apprv_num);
		p_ds_search.setColumn(addRow , "CLR_NUM" 	, v_clr_num);
	}
	
}

function gfn_apprv_satn(ds_gw_satn, ds_TLEV_USEAPPRV, ds_TLEV_CLRREC)
{
	/*if(gds_sessioninfo.GetColumn(0, "STAFF_NUM") != ds_TLEV_CLRREC.GetColumn(0, "INSPRES_ID"))
	{
		alert("작성자 본인만 결재요청할 수 있습니다.");
		return;
	}*/
	ds_gw_satn.ClearData();
	ds_gw_satn.AddRow();
	ds_gw_satn.SetColumn(0, "APPRV_NUM", ds_TLEV_USEAPPRV.GetColumn(0, "APPRV_NUM"));
	ds_gw_satn.SetColumn(0, "STAFF_NUM", gds_sessioninfo.GetColumn(0, "STAFF_NUM"));
	ds_gw_satn.SetColumn(0, "DEPT_CD", gds_sessioninfo.GetColumn(0, "DEPT_CD"));
	
	gfn_syncCall("","KOMCA?SYSID=PATHFINDER&MENUID=1000002002005&EVENTID=insert_satn_info","S=ds_gw_satn","","","fn_CallBack");
	
	/*if (ds_TLEV_USEAPPRV.GetUpdate()=1 || ds_TLEV_CLRREC.GetUpdate()=1)
	{
		alert('변동내역이 있습니다. 저장후 작업하세요.');
		return ;
	}*/
	
	if( length(ds_TLEV_CLRREC.GetColumn(0 , "CLR_NUM")) > 0)
	{
		var data1 = "";
		var v_alert = "";
		
		if(left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),2) == "CA" || left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),2) == "CC" || left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),6) == "B9999" || left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),6) == "CB9904" || left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),6) == "CB9902" || left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),6) == "CB9903" || left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),6) == "CB9901")
		{
			data1 = "form_id=CA0000";
			if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 10000000)
			{
				v_alert = "사무총장 전결입니다.";
			}
			else if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 7000000)
			{
				v_alert = "본부장 전결입니다.";
			}
			else if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 3000000)
			{
				v_alert = "국장 전결입니다.";
			}
			else
			{
				v_alert = "팀장 전결입니다.";
			}
		}
		else if(left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),2) == "CG")
		{
			data1 = "form_id=CA0001";
			v_alert = "국장 전결입니다.";
		}
		else if(left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),2) == "CH")
		{
			data1 = "form_id=CA0002";
			v_alert = "국장 전결입니다.";
		}
		else if(left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),2) == "CD" || left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),2) == "CE")
		{
			data1 = "form_id=CA0003";
			if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 10000000)
			{
				v_alert = "사무총장 전결입니다.";
			}
			else if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 7000000)
			{
				v_alert = "본부장 전결입니다.";
			}
			else if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 3000000)
			{
				v_alert = "국장 전결입니다.";
			}
			else
			{
				v_alert = "팀장 전결입니다.";
			}
		}
		else if(left(ds_TLEV_USEAPPRV.GetColumn(0,"MDM_CD"),4) == "CB01")
		{
			data1 = "form_id=CA0004";
			if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 10000000)
			{
				v_alert = "사무총장 전결입니다.";
			}
			else
			{
				v_alert = "본부장 전결입니다.";
			}
		}
		else
		{
			data1 = "form_id=CA0000";
			if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 10000000)
			{
				v_alert = "사무총장 전결입니다.";
			}
			else if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 7000000)
			{
				v_alert = "본부장 전결입니다.";
			}
			else if(parseInt(ds_TLEV_CLRREC.GetColumn(0, "DEMD_PLAN_AMT")) >= 3000000)
			{
				v_alert = "국장 전결입니다.";
			}
			else
			{
				v_alert = "팀장 전결입니다.";
			}
		}
		data1 += "&IU_KEY=" + ds_TLEV_USEAPPRV.GetColumn(0, "APPRV_NUM") + "-" + lpad(ds_TLEV_CLRREC.GetColumn(0, "CLR_NUM"), 0, 4) + "&id=" + gds_sessioninfo.GetColumn(0, "STAFF_NUM") + "&deptId=" + gds_sessioninfo.GetColumn(0, "DEPT_CD");
		var URL = gv_GwURL;
		URL += "?" + data1;
		ExecBrowser(URL);
		//v_alert += "\n결재 화면 종료 후 확인 버튼 클릭 바랍니다.";
		alert(v_alert);
		//gfn_syncCall("SEARCH_STAT","KOMCA?SYSID=PATHFINDER&MENUID=1000002002005&EVENTID=sel_gw_apprv_state","S=ds_state_search","ds_approval_result=SEL1","","fn_CallBack");
	}
	else
	{
		alert("정산자료가 존재하지 않습니다. 정산내역을 확인하세요.");
	}
}