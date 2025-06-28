﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿// Form에서는 절대 Include하여 사용하지 않는다.
// Form에서는 절대 Include하여 사용하지 않는다.
// Form에서는 절대 Include하여 사용하지 않는다.
// Form에서는 절대 Include하여 사용하지 않는다.
// Form에서는 절대 Include하여 사용하지 않는다.
// Form에서는 절대 Include하여 사용하지 않는다.



// MiPlatform Application 내 전역으로 사용하는 Global Function을 정의 한다.
// function 명은 gfn_ 을 Prefix로 사용함.
// js 내 전역변수는 가능한 사용하지 않으며, 필요시 gstr_, gn_ 등을 Prefix로 사용한다.


// 여기에는 일반 Function을 추가하지 마세요. 한줄 Include하기 귀찮다고 여기다 막 코딩 하지 마세요...
// 여기에는 일반 Function을 추가하지 마세요. 한줄 Include하기 귀찮다고 여기다 막 코딩 하지 마세요...
// 여기에는 일반 Function을 추가하지 마세요. 한줄 Include하기 귀찮다고 여기다 막 코딩 하지 마세요...
// 여기에는 일반 Function을 추가하지 마세요. 한줄 Include하기 귀찮다고 여기다 막 코딩 하지 마세요...
// 여기에는 일반 Function을 추가하지 마세요. 한줄 Include하기 귀찮다고 여기다 막 코딩 하지 마세요...
// 여기에는 일반 Function을 추가하지 마세요. 한줄 Include하기 귀찮다고 여기다 막 코딩 하지 마세요...

// 함수 동작 시 Global 영역에서 처리하므로 유의하여 코딩
// 함수 동작 시 Global 영역에서 처리하므로 유의하여 코딩
// 함수 동작 시 Global 영역에서 처리하므로 유의하여 코딩
// 함수 동작 시 Global 영역에서 처리하므로 유의하여 코딩
// 함수 동작 시 Global 영역에서 처리하므로 유의하여 코딩
// 함수 동작 시 Global 영역에서 처리하므로 유의하여 코딩

/**************************************************************************************************************
gfn_isNull              : Null이나 empty Sting인지 체크
gfn_nvl                 : 빈값이면(gfn_isNull이 true이면) 값을 대체
gfn_isSimulator         : 실행중인 브라우저가 Simulator인지 배포용 MiPlatform인지 Return
gfn_IsModify            : 데이타 변경여부 확인
gfn_OpenChildWindow
gfn_GetWindowID
gfn_GetNormalMaxSize    : MDI Status가 Normal을 기준으로 최대크기로 띄울 수 있는 Size를 구한다
gfn_OpenMenuAdd
gfn_OpenMenuDel
gfn_GetScreenAxis       : MiPlatform Work 영역 Size를 Return
gfn_IsOpenForm          : 열려있는 Form인지를 확인한다.
gfn_Base64Encode        : base64 encode
gfn_Base64Decode        : base64 decode
gfn_isInternetConnected : 인터넷 연결여부
gfn_GetIPAddress        : 사용중인 컴퓨터의 IP Address를 린턴한다. 두 개이상 사용시에는 array로 리턴한다.
gfn_GetMacAddress       : 사용중인 컴퓨터의 Mac Address를 리턴한다. 두 개이상 사용시에는 array로 리턴한다
gfn_MsgBox              : 윈도우 표준 메시지 박스 표시
**************************************************************************************************************/
// Null이나 empty Sting인지 체크
function gfn_isNull(strValue)
{
	strValue =ToString(strValue);
	
	if (strValue == null || length(strValue) == 0)
	{
		return true;
	}
	return false;
}

// 빈값이면 대체
function gfn_nvl(strValue,strValue2)
{
	return decode(gfn_isNull(toString(strValue)), true, strValue2, strValue);
}

// 실행중인 브라우저가 Simulator인지 배포용 MiPlatform인지 Return 
function gfn_isSimulator()
{
	return IsSimulator();
}

/*===============================================================
= 기능 : 데이타 변경여부 확인
===============================================================*/
function gfn_IsModify(objDs)
{
	return objDs.GetUpdate();
}

// MDI 창을 띄운다.
function gfn_OpenChildWindow(strUrl, strTitle, strArg, strReload)
{
	var ArgStr = "";
		//ArgStr += "arg_Prefix=" + quote(strPrefix);
		ArgStr += " arg_Url=" + quote(strUrl);
		ArgStr += " arg_Title=" + quote(strTitle);
		ArgStr += " " + strArg;
	var arr = strUrl.split("::");
	var arr1 = arr[1].split(".");
	GV_FORMID = arr1[0];
	if (gfn_isNull(strUrl) != true) 
	{
		if (gfn_IsOpenForm(strUrl) == true)
		{
			//이미 열려진 화면을 메뉴로 클릭하면 다시여는 효과처리
			
			//폼이 이미 열려져 있으면 포커스만 이동한다.
			/*
			var objForms = AllWindows(GV_FORMID);
			if(objForms[0] == null) {
			
			// NewWindow(Formid, "Main::MainForm.xml", "",750,550, "OpenStyle=Normal resize=true",-1 , -1);
			
			} else {
			if ( toUpper(objForms[0].MdiStatus) == "MIN" ) objForms[0].MdiStatus = "Normal";
				gfn_SetFocusForm(strUrl);

			}
			*/
			//폼을 무조건 새로 연다.
			if (strReload == "1") {
				var idx = gds_OpenMenu.FindRow("page_url",strUrl);
				var obj_win = GetFormFromHandle(gds_openMenu.GetColumn(idx,"page_handle"));	
				obj_win.div_form.Reload();
			}
			//obj_win.div_form.Reload();
			
			
			gfn_SetFocusForm(strUrl);
			
			
		
		}else
		{
		
			var strWinID = gfn_GetWindowID(strUrl);
			var nWidth = "";	//gfn_GetNormalMaxSize("width");
			var nHeight = "";	//gfn_GetNormalMaxSize("height");
			NewWindow(	strWinID,
						"main::frm_WorkForm.xml",
						ArgStr,
						nWidth,
						nHeight,
						"OpenStyle=Max AutoSize=true",
						-1,
						-1
						);
		}				
	}	
}

// Form의 Win ID를 가져온다
function gfn_GetWindowID(strUrl)
{
	var strWinID = strUrl;

	strWinID = left(strWinID, pos(strWinID, ".xml"));

	return strWinID;
}

// MDI Status가 Normal을 기준으로 최대크기로 띄울 수 있는 Size를 구한다
function gfn_GetNormalMaxSize(strAxis)
{
	var nSize = 0;

	if (toLower(strAxis) == "height")
	{
		nSize  = toNumber(gfn_GetScreenAxis("height"));
		nSize -= toNumber(global.GetPlatformInfo("Title","cy"));
		nSize -= toNumber(global.GetPlatformInfo("Border","cy"));
		nSize -= toNumber(global.GetPlatformInfo("Border","cy"));
		nSize -= toNumber(global.GetPlatformInfo("Frame","cy"));
		nSize -= toNumber(global.GetPlatformInfo("Frame","cy"));
		nSize -= toNumber(global.GetPlatformInfo("Edge","cy"));
		nSize -= toNumber(global.GetPlatformInfo("Edge","cy"));
	}
	else
	{
		nSize  = toNumber(gfn_GetScreenAxis("width"));
		nSize -= toNumber(global.GetPlatformInfo("Border","cx"));
		nSize -= toNumber(global.GetPlatformInfo("Border","cx"));
		nSize -= toNumber(global.GetPlatformInfo("Frame","cx"));
		nSize -= toNumber(global.GetPlatformInfo("Frame","cx"));
		nSize -= toNumber(global.GetPlatformInfo("Edge","cx"));
		nSize -= toNumber(global.GetPlatformInfo("Edge","cx"));
	}
	return nSize;
}

// gds_OpenMenu Global Dataset의 Row를 추가한다.
function gfn_OpenMenuAdd(strUrl, strTitle, nHandle)
{
	var nRow = gds_OpenMenu.AddRow();
	gds_OpenMenu.SetColumn(nRow,"page_nm", strTitle );
	//gds_OpenMenu.SetColumn(nRow,"page_prefix", strPrefix );
	gds_OpenMenu.SetColumn(nRow,"page_url", strUrl );
	gds_OpenMenu.SetColumn(nRow,"page_handle",nHandle);

	global.frame_bottom.fn_AddTitle(nHandle,strTitle);
}

// gds_OpenMenu Global Dataset의 Row를 삭제한다.
function gfn_OpenMenuDel(nHandle)
{
	var nRow = gds_OpenMenu.FindRow("page_handle", nHandle);

	if( nRow >= 0 )
	{
		global.frame_bottom.fn_DelTitle(nRow);
		gds_OpenMenu.DeleteRow(nRow);
	}
}

// 화면에 해당하는 Tab을 활성화 한다.
function gfn_OpenMenuActiveTab(nHandle)
{
	var nRow = gds_OpenMenu.FindRow("page_handle", nHandle);

	if( nRow >= 0 )
	{
		global.frame_bottom.fn_ChangeTitle(nRow);
	}
}

// MiPlatform Work 영역 Size를 Return한다.
function gfn_GetScreenAxis(strProp)
{
	var nValue;
	switch(toLower(strProp))
	{
		case "left":
			nValue = toInteger(global.GetPlatformInfo("WorkArea","Left"));
			break;
		case "top":
			nValue = toInteger(global.GetPlatformInfo("WorkArea","Top"));
			break;
		case "right":
			nValue = toInteger(global.GetPlatformInfo("WorkArea","Right"));
			break;
		case "bottom":
			nValue = toInteger(global.GetPlatformInfo("WorkArea","Bottom"));
			break;
		case "width":
			nValue = toInteger(global.GetPlatformInfo("WorkArea","Width"));
			break;
		case "height":
			nValue = toInteger(global.GetPlatformInfo("WorkArea","Height"));
			break;
	}

	return nValue;
}

// 열려있는 Form인지를 확인한다.
function gfn_IsOpenForm(strUrl)
{
	var nRow = gds_OpenMenu.SearchRow("page_url=='" + strUrl + "'");

	if( nRow >= 0 )
	{
		return true;
	}
	else
	{
		return false;
	}
}

// 열려있는 Form에 Focus를 준다.
function gfn_SetFocusForm(strUrl)
{
	var nHandle = gfn_GetFormHandle(strUrl);

	gfn_SetFocusFormHandle(nHandle);
}

// Handle에 해당하는 Form에 Focus를 준다.
function gfn_SetFocusFormHandle(nHandle)
{
	if (nHandle == null) return;

	var objForm = GetFormFromHandle(nHandle);
	
	objForm.SetFocus();
}

// 인자에 해당하는 Handle을 Return 한다.
function gfn_GetFormHandle(strUrl)
{
	var nRow = gds_OpenMenu.SearchRow("page_url=='" + strUrl + "'");

	if( nRow >= 0 )
	{
		return gds_OpenMenu.GetColumn(nRow, "page_handle");
	}
	else
	{
		return null;
	}
}

// 열려진 Form 객체를 넘겨받는다. 
// 실제 work_form의 Div_Form안에 페이지 들이 Load되므로 Div_Form을 넘긴다.
/*
function cfn_comm_GetOpenForm(strUrl)
{
	var nHandle = _GetFormHandle(strUrl);
	// 열려진 Form이면
	if (nHandle != null)
	{
		return _SetFocusFormHandle(nHandle).div_Form;
	}
	else
	{
		return null;
	}
}
*/

// base64 encode
function gfn_Base64Encode(str)
{
	var strEncode = Ext_Base64Encode(str);
	return strEncode;
}

// base64 decode
function gfn_Base64Decode(str)
{
	var strDecode = Ext_Base64Decode(str);
	return strDecode;
}

// 인터넷 연결여부
function gfn_isInternetConnected()
{
	var nConn = ext_InternetGetConnectedState();
	
	if (nConn == 1)
		return true;
	else
		return false;
}

// 사용중인 컴퓨터의 IP Address를 리턴한다. 두 개이상 사용시에는 array로 리턴한다.
function gfn_GetIPAddress()
{
	return ext_GetIPAddress();
}

// 사용중인 컴퓨터의 Mac Address를 리턴한다. 두 개이상 사용시에는 array로 리턴한다
function gfn_GetMacAddress()
{
	return ext_GetMacAddress();
}

// 윈도우 표준 메시지 박스 표시. (3 button 표현 가능)
// 각각의 버튼 Return값 확인 요망(true/false아님)
function gfn_MsgBox(strTitle,strMsg,strStyle)
{
/*
	li_result = gfn_MsgBox("Title.....", "메시지 내용", "MB_ICONINFORMATION|MB_YESNOCANCEL|MB_DEFBUTTON1");

	Style 사용 가능 명령
	- 아이콘 형태
		MB_ICONINFORMATION / MB_ICONASTERISK - 소문자 i 
		MB_ICONSTOP / MB_ICONHAND - Stop 아이콘 
		MB_ICONQUESTION - 물음표 
		MB_ICONEXCLAMATION - 느낌표
	- 버튼 형태
		MB_ABORTRETRYIGNORE, MB_OK, MB_OKCANCEL, MB_RETRYCANCEL, MB_YESNO, MB_YESNOCANCEL
	- 디폴트 버튼 Focus 위치
		MB_DEFBUTTON1, MB_DEFBUTTON2, MB_DEFBUTTON3
*/
	return ext_MsgBox(strTitle,strMsg,strStyle);
}


//2레벨 메뉴코드값으로 좌측프레임의 setTreeView함수를 호출하여 메뉴트리를 구성한다
function gfn_makeTree(strMenu_cd)
{
	Global.frame_menu.setTreeView(strMenu_cd);
}

