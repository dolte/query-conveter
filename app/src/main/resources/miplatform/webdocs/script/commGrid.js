﻿﻿//***********************************************************************
//  프로그램 ID : commGrid.js
//  화면 ID     : 
//  프로그램명  : 그리드 컴포넌트를 위한 공통함수
//  기능설명    : 그리드 컴포넌트를 위한 공통함수
//  작성자      : 
//  작성일      : 
//-----------------------------------------------------------------------
//  수정자      : 
//  수정이력    : 
//  수정내용    : 
//***********************************************************************

//GRD_Sort2 에서 사용하는 전역변수------------------------
var g_SortFlag = true; 
var g_LastSortCell = "";  //마지막으로 정렬된 Cell
//--------------------------------------------------------



//------------------------------------------------------------------------------------
//  함수명  : GRD_Init
//  기  능  : 그리드 초기화
//  인  자  : grid - 그리드 객체
//  리턴값  : 없음
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_Init(grid)
{
	grid.AutoFit = true;
	grid.Border = "Flat";
	grid.BorderColor = "User0";
	grid.CellMoving = true;
	grid.ColSizing = true;
	grid.HeadBorder = "Flat";
	grid.HeadHeight = 22;
	grid.MultiSelect = true;
	grid.NoDataText = "조회된 데이터가 없습니다.";
	grid.RowHeight = 19;
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_Sort
//  기  능  : 그리드 소트
//  인  자  : grid  - 그리드 객체
//            ds    - Dataset 객체
//            col   - 소트될 그리드의 colindex
//            merge - nCell앞에 merge된 경우 재계산(더해줄)할 값 (선택) - 인덱스가 변경되어 재조정 필요. 헤더와 body는 또다른 index를 가져간다.
//            mcnt - 전체 merge 갯수
//  리턴값  : sortmark - 작업내역 마크문자열("↑" 또는 "↓";)
//  비  고  : merge값은 merge되는 갯수-1 값이면 ok
//------------------------------------------------------------------------------------
function GRD_Sort(grid, ds, col, merge, mcnt)
{
	if (merge == null) merge = 0;

	var CONST_ASC_MARK="↑";
	var CONST_DESC_MARK="↓";

	var cols = grid.GetCellProp("head",col,"col");
	merge = toNumber(merge);

	var sortmark;
	var headernm = grid.GetCellProp("head",col,"text");			//헤더제목

	var colid = grid.GetCellProp("body",cols,"colid");	//컬럼id

	//소트마크를 추가 또는 변경
	if (right(headernm, 1) == CONST_ASC_MARK) {
		var rtn = ds.sort(colid,false); //sort desc

		headernm = replace(headernm,CONST_ASC_MARK,CONST_DESC_MARK);
		sortmark = CONST_DESC_MARK;

	} else {
		var rtn = ds.sort(colid,true); //sort asc

		if (right(headernm, 1) <> CONST_DESC_MARK) {
			headernm = headernm+CONST_DESC_MARK;
		}
		headernm = replace(headernm,CONST_DESC_MARK,CONST_ASC_MARK);
		sortmark = CONST_ASC_MARK;
	}

	grid.SetCellProp("head",col,"text",headernm);

	//대상 이외의 헤더 소트마크 제거
	//-->헤더 2개이상, merge한 경우 헤더갯수는 컬럼갯수보다 큰값 출력
	var hcnt = toNumber(grid.GetColCount());

	//for (var i=0; i<(hcnt-decode(mcnt,null,0, toNumber(mcnt))); i++) {
	for (var i=0; i<grid.getCellCount("head"); i++) {
		cols = grid.GetCellProp("head",i,"col");
		var colwidth = toNumber(grid.GetColProp(cols,"Width"));

		if (i <> col && colwidth <> 0) {
			headernm = grid.GetCellProp("head",i,"text");

			headernm = replace(headernm, CONST_ASC_MARK,"");
			grid.SetCellProp("head",i,"text", headernm);

			headernm = replace(headernm, CONST_DESC_MARK,"");
			grid.SetCellProp("head",i,"text", headernm);
		}
	}

	return sortmark;
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_ColId
//  기  능  : 컬럼인덱스를 이용하여 해당 컬럼id 취득
//  인  자  : grid - 그리드 객체
//            col  - colindex
//  리턴값  : 컬럼id
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_ColId(grid, col)
{
	return grid.GetCellProp("body", col, "colid");
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_ColCount
//  기  능  : 그리드 컬럼 갯수 취득
//  인  자  : grid - 그리드 객체
//  리턴값  : 컬럼 갯수
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_ColCount(grid)
{
	var cont = tolower(grid.contents);
	var spos = IndexOf(cont, "<columns>")+10;
	var epos = IndexOf(cont, "</columns>");
	
	cont = Substr(cont, spos, epos - spos);
	
	var arr = split(cont, "<col");

	return arr.length-1;
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_ColFix
//  기  능  : 주어진 컬럼까지 컬럼 고정
//  인  자  : grid - 그리드 객체
//            col  - colindex
//  리턴값  : 없음
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_ColFix(grid, col)
{
	for (var i=0; i<=col; i++) {
		grid.SetColProp(i,"Fix",true);
	}
	
	var colcnt = grid.GetColCount(); //GRD_ColCount(grid);
	for (var i=col+1; i<colcnt; i++) {
		grid.SetColProp(i,"Fix",false);
	}
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_ToExcel
//  기  능  : grid를 Excel 로 Export
//  인  자  : grid   - 그리드 객체
//            title  - 엑셀 쉬트 제목
//  리턴값  : 없음
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_ToExcel(grid, title)
{
	grid.ExportExcelEx(title);
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_ToCVS
//  기  능  : grid를 CVS 형식 문자열로 저장
//  인  자  : grid      - 그리드 객체
//            incheader - 컬럼명 포함 여부(true면 컬럼명 포함, flase면 미포함)
//  리턴값  : 저장한 문자열
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_ToCVS(grid, incheader)
{
	var body = grid.GetCSVData();
	var header = "";
	
	if (incheader == true) {
		for (var i=0; i<grid.GetColCount(); i++) {
			header += grid.GetCellProp("head",i,"text") + ",";
		}
		header += chr(13) + chr(10);
	}
	
	return header + body;
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_GetColRight
//  기  능  : grid에서 특정 컬럼의 Right 좌표값 get
//  인  자  : grid   - 그리드 객체
//            col    - 컬럼index
//  리턴값  : Right 좌표값
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_GetColRight(grid,col)
{
	var arrRect = grid.GetCellRect(0,col);
	return arrRect[2];
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_GetColLeft
//  기  능  : grid에서 특정 컬럼의 Left 좌표값 get
//  인  자  : grid   - 그리드 객체
//            col    - 컬럼index
//  리턴값  : Right 좌표값
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_GetColLeft(grid,col)
{
	var arrRect = grid.GetCellRect(0,col);
	return arrRect[0];
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_GetColTop
//  기  능  : grid에서 특정 컬럼의 Top 좌표값 get
//  인  자  : grid   - 그리드 객체
//            col    - 컬럼index
//  리턴값  : Right 좌표값
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_GetColTop(grid,row,col)
{
	var row2 = row * grid.GetColCount() + col;  //RowLine * Column 개수 + Col 값
	var arrRect = grid.GetCellRect(row2,col);
	
	return arrRect[1];
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_GetColBottom
//  기  능  : grid에서 특정 컬럼의 Bottom 좌표값 get
//  인  자  : grid   - 그리드 객체
//            col    - 컬럼index
//  리턴값  : Right 좌표값
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_GetColBottom(grid,row,col)
{
	var row2 = row * grid.GetColCount() + col;  //RowLine * Column 개수 + Col 값
	var arrRect = grid.GetCellRect(row2,col);
	
	return arrRect[3];
}

//------------------------------------------------------------------------------------
//  함수명  : GRD_GetColindexByColid
//  기  능  : grid와 바인딩된 컬럼ID로 그리드 cell index get
//  인  자  : grid   - 그리드 객체
//            col    - 컬럼index
//  리턴값  : Right 좌표값
//  비  고  : 
//------------------------------------------------------------------------------------
function GRD_GetColindexByColid(grid,colid)
{
	var id;
	for (var i=0; i<grid.GetColCount(); i++) {
		id = grid.GetCellProp("body", i, "colid");
		if (id == colid) return i;
	}
	return -1;
}

// 조회 버튼 Click시 함수 호출
function GRD_ClearHead(grid)
{
	var CONST_ASC_MARK="↑";
	var CONST_DESC_MARK="↓";

	var headernm;
	for (var i=0; i<grid.getCellCount("head"); i++) {
		headernm = grid.GetCellProp("head",i,"text");

		if (headernm == "해제") {
			grid.SetCellProp("head",i,"text", "선택");
		}

		if (right(headernm, 1) == CONST_ASC_MARK) {
			headernm = replace(headernm,CONST_ASC_MARK,"");
			grid.SetCellProp("head",i,"text", headernm);
			return;
		} else if (right(headernm, 1) == CONST_DESC_MARK) {
			headernm = replace(headernm,CONST_DESC_MARK,"");
			grid.SetCellProp("head",i,"text", headernm);
			return;
		}
	}
}
