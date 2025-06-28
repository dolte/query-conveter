﻿//***********************************************************************
//  프로그램ID  : commPage.js
//  기능  설명  : 메뉴함수
//  작  성  자  : 박호경
//  작  성  일  : 2008/10/10
//  비      고  :
//***********************************************************************


/************************************************
 *	Paging 코드 시작
 ***********************************************/


//	OnMouseOver
function f_over_color(){
	
}
//	OnMouseOut
function f_out_color(){
	
}
//	OnFocus
function f_focus_color(){
	
}
//	OnKillFocus
function f_kill_color(){
	
}

function makePage(){

	//iLeft, iWidth로 페이지 번호의 크기와 간격을 지정합니다.
	//한자리 : width = 26, left 간격 : 25
	//두자리 : width = 30, left 간격 : 30
	//세자리 : width = 35, left 간격 : 35
	var iLeft       = 10;
	var iWidth      = 26;
	
	var iMyFont		= "";
	var iMyColor	= "";


	var sb = "<Contents>\n";
	
	var iTotalPage;	//
	if( floor(iTotalCount%iVolumnPerPage) > 0 )	
		iTotalPage = floor(iTotalCount/iVolumnPerPage) +1;
	else
		iTotalPage = floor(iTotalCount/iVolumnPerPage);
		
	//trace("iTotalPage : " + iTotalPage);
	
	var iUint = floor((toInteger(iNowPage)-1) / toInteger(iPageScale));
	iUint = (iUint * iPageScale)+1;
	//trace(iUint);
		
	var iNextUnit = toInteger(iUint+iPageScale);
	//trace(iNextUnit);
	// 첫페이지(<<)
	iLeft = iLeft + iWidth;
	
	if(iUint > iPageScale){
		sb = sb +"<button	Cursor='HAND' Id='first' userData='1' Left='"+iLeft+"'	Top='0' Width='"+(iWidth*2)+"' Height='20' Font='굴림,9' Text='맨처음' Color='Black' Appearance='Falt' OnClick='getList' OnMouseOver='f_over_color' OnMouseOut='f_out_color' OnFocus='f_focus_color' OnKillFocus='f_kill_color' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
	}else{
		sb = sb +"<button	Id='first' userData='1' Left='"+iLeft+"' Top='0' Width='"+(iWidth*2)+"' Height='20' Font='굴림,9' Text='맨처음' Color='Black' Appearance='Falt' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
	}

	// 앞으로(<)
	iLeft = iLeft + (iWidth*2);
	if(iUint > iPageScale){
		sb = sb +"<button	Cursor='HAND' Id='before' userData='"+(iUint-1)+"'	Left='"+iLeft+"'	Top='0'	Width='"+(iWidth*2)+"'	Height='20'	 Font='굴림,9'  Text='이전' Color='Black'  Appearance='Falt' OnClick='getList' OnMouseOver='f_over_color' OnMouseOut='f_out_color' OnFocus='f_focus_color' OnKillFocus='f_kill_color' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
	}else{
		sb = sb +"<button	Id='before' userData='"+(iUint-1)+"'	Left='"+iLeft+"' Top='0' Width='"+(iWidth*2)+"' Height='20' Font='굴림,9' Text='이전' Color='Black' Appearance='Falt' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
	}

	// 페이징[1 2 3 4 5 6 7 8 9 10]
	iLeft = iLeft + (iWidth);
	for(var i = iUint ; i < iNextUnit ; i++ ){
		
		iLeft = iLeft + iWidth;
		if(i > iTotalPage)
			break;
		if(i == iNowPage){
			sb = sb +"<button	Id='"+i+"' Left='"+iLeft+"' Top='0' Width='"+iWidth+"' Height='20' Font='굴림,9,bold' Text='"+i+"' Appearance='Falt' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
		}else{
			sb = sb +"<button	Cursor='HAND' Id='"+i+"'	Left='"+iLeft+"'	Top='0'	Width='"+iWidth+"'	Height='20'	 Font='굴림,9'  Text='"+i+"' Color='Black'  Appearance='Falt' OnClick='getList' OnMouseOver='f_over_color' OnMouseOut='f_out_color' OnFocus='f_focus_color' OnKillFocus='f_kill_color' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
		}
	}
	
	// 뒤로(>)
	iLeft = iLeft + iWidth;
	if(iNextUnit <= iTotalPage){
		sb = sb +"<button	Cursor='HAND' Id='next' userData='"+iNextUnit+"'	Left='"+iLeft+"'	Top='0'	Width='"+(iWidth*2)+"'	Height='20'	 Font='굴림,9'  Text='다음' Color='Black'  Appearance='Falt' OnClick='getList' OnMouseOver='f_over_color' OnMouseOut='f_out_color' OnFocus='f_focus_color' OnKillFocus='f_kill_color' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
	}else{
		sb = sb +"<button	Id='next' userData='"+iNextUnit+"'	Left='"+iLeft+"' Top='0' Width='"+(iWidth*2)+"' Height='20' Font='굴림,9' Text='다음' Color='Black' Appearance='Falt' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
	}
	
	// 마지막페이지(>>)
	iLeft = iLeft + (iWidth*2);
	if(iNextUnit < iTotalPage){
		sb = sb +"<button	Cursor='HAND' Id='end' userData='"+iTotalPage+"'	Left='"+iLeft+"'	Top='0'	Width='"+(iWidth*2)+"'	Height='20'	 Font='굴림,9'  Text='마지막' Color='Black'  Appearance='Falt' OnClick='getList' OnMouseOver='f_over_color' OnMouseOut='f_out_color' OnFocus='f_focus_color' OnKillFocus='f_kill_color' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
	}else{
		sb = sb +"<button	Id='end' userData='"+iTotalPage+"'	Left='"+iLeft+"' Top='0' Width='"+(iWidth*2)+"' Height='20' Font='굴림,9' Text='마지막' Color='Black' Appearance='Falt' BKColor = '#f7f7ef' BorderColor = '#f7f7ef'></button>\n" ;
	}

	sb 			= sb +"</Contents>\n";
	div_page.Contents 	= sb;
	
	//trace(">>>>>>>>>>>>>>>> [Pasing String]\n\n"+sb);
}

// page번호를 선택시 현재페이지(iNowPage)를 세팅.............
function setNowPage(obj){	
	if(obj=='맨처음' || obj=='이전' || obj=='다음' || obj=='마지막'){
		iNowPage = toInteger(obj.userData);
	}else if( isNumber(obj)){
		iNowPage = toInteger(obj.Id);
	}
}

function isNumber(obj) { 
	var str = obj.value;
	if(str.length == 0)
		return false;
	
	for(var i=0; i < str.length; i++) {
		if(!('0' <= str.charAt(i) && str.charAt(i) <= '9'))
			return false;
		}
	return true;
}

/************************************************
 *	Paging 코드 끝
 ***********************************************/