﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿﻿//***********************************************************************
//  프로그램 ID : commDs.js
//  화면 ID     : 
//  프로그램명  : Dataset을 위한 공통함수
//  기능설명    : Dataset을 위한 공통함수
//  작성자      : 
//  작성일      : 
//-----------------------------------------------------------------------
//  수정자      : 
//  수정이력    : 
//  수정내용    : 
//***********************************************************************

//------------------------------------------------------------------------------------
//  함수명  : DS_Modified
//  기  능  : Dataset의 변경 여부 체크
//  인  자  : ds  - 체크할 Dataset 객체
//  리턴값  : true   - 변경됨
//            false  - 변경되지 않음
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_Modified(ds)
{
	return ds.GetUpdate();
}

//------------------------------------------------------------------------------------
//  함수명  : DS_ModifiedField
//  기  능  : Dataset의 특정column 변경 여부 체크 (update된 경우 대상)
//  인  자  : ds         - 체크할 Dataset 객체
//            fieldname  - 체크할 Colid
//  리턴값  : true   - 변경됨
//            false  - 변경되지 않음
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_ModifiedField(ds,fieldname)
{
	var oldvalue, newvalue;
	
	var rowcnt = ds.RowCount();
	for (var i=0; i<rowcnt; i++) {
		if(toLower(ds.GetRowType(i)) == "normal") 
			continue;
		
		newvalue = ds.GetColumn(i,fieldname);
		oldvalue = ds.GetOrgBuffColumn(i,fieldname);
		
		if (newvalue != oldvalue)
			return true;
	}
	
	return false;
}

//------------------------------------------------------------------------------------
//  함수명  : DS_ModifiedCell
//  기  능  : Dataset의 특정cell 변경 여부 체크
//  인  자  : ds     - 체크할 Dataset 객체
//            row    - 체크할 row index
//            colid  - 체크할 Colid
//  리턴값  : true   - 변경됨
//            false  - 변경되지 않음
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_ModifiedCell(ds,row,colid)
{
	var oldvalue, newvalue;
	
	if(toLower(ds.GetRowType(row)) == "normal") 
		return false;
	
	newvalue = ds.GetColumn(row,colid);
	oldvalue = ds.GetOrgBuffColumn(row,colid);
	
	if (newvalue != oldvalue)
		return true;
	
	return false;
}

//------------------------------------------------------------------------------------
//  함수명  : DS_Transaction
//  기  능  : Dataset Transaction 처리
//  인  자  : sid         - 서비스id
//            sCallMethod - 호출할 .do 명시
//            inDs        - 입력 dataset
//            outDs       - 출력 dataset
//            sVar        - 변수정의
//            sCallBack   - callback 함수명
//  리턴값  : 없음 (callback)
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_Transaction(sid, sCallMethod, inDs, outDs, sVar, sCallBack)
{
	DS_OutClear(outDs);
	Transaction(sid, sCallMethod, inDs, outDs, sVar, sCallBack);
}

//------------------------------------------------------------------------------------
//  함수명  : DS_NonTransaction
//  기  능  : output dataset을 clear하지 않고 Transaction 처리
//  인  자  : sid         - 서비스id
//            sCallMethod - 호출할 .do 명시
//            inDs        - 입력 dataset
//            outDs       - 출력 dataset
//            sVar        - 변수정의
//            sCallBack   - callback 함수명
//  리턴값  : 없음 (callback)
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_NonTransaction(sid, CallMethod, inDS, outDS, sVar, sCallBack)
{
	Transaction(sid, CallMethod, inDS, outDS, sVar, sCallBack);
}

//------------------------------------------------------------------------------------
//  함수명  : DS_OutClear
//  기  능  : output dataset의 모든 데이터를 삭제
//  인  자  : ds - 대상 Dataset 객체
//  리턴값  : 없음
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_OutClear(ds)
{ 
	var dscount = split(ds," ");
	for(i=0;i<dscount.length();i++)
	{  
		var cDs = split(dscount[i],"=");
		Object(cDs[0]).ClearData();
	}	
}

//------------------------------------------------------------------------------------
//  함수명  : DS_GetQuery
//  기  능  : serviceid(.do)를 이용하여 쿼리 결과 dataset 취득
//  인  자  : ds        - 대상 Dataset 객체
//            serviceid - 서비스명 (.do)
//            force     - true면 무조건 get, false면 ds가 비어있는 경우만 get
//  리턴값  : 없음
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_GetQuery(ds, serviceid, force)
{
	if (force != true) {
		if (ds.Count() > 0)
			return;
	}
	ds.ClearData();
	ds.ServiceId = serviceid;
	ds.Load();
}

//------------------------------------------------------------------------------------
//  함수명  : DS_ColDup
//  기  능  : 주어진 컬럼내에 동일한 값이 중복되는지 체크
//  인  자  : ds      - Dataset 객체
//            colinfo - 체크할 colindex 또는 colid
//            exceptblank - 빈 cell 중복체크에 포함여부(true/false)
//  리턴값  : -1    - 중복된값 없음
//            그외  - 중복된 rowindex
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_ColDup(ds, colinfo, exceptblank)
{
	if (exceptblank == null) exceptblank = true;
	
	colinfo = gfnTrim(colinfo);
	
	if (!isdigit(colinfo)) {
		//colid
		var rowcount = ds.GetRowCount();
		for (var i = 0; i < rowcount; i++) {
			for (var j = 0; j < rowcount; j++) {
				if (exceptblank == false && trim(ds.GetColumn(i,colinfo))+"" == "") continue;
				
				if ((i != j) && (trim(ds.GetColumn(i,colinfo))+"" == trim(ds.GetColumn(j,colinfo))+"")) {
					return j;
				}
			}
		}
		
	} else {
		//colindex
		var col_in = ds.GetColIDXbyorder(colinfo);

		var rowcount = ds.GetRowCount();
		for (var i = 0; i < rowcount; i++) {
			for (var j = 0; j < rowcount; j++) {
				if (exceptblank == false && trim(ds.GetColumn(i,col_in))+"" == "") continue;
				
				if ((i != j) && (trim(ds.GetColumn(i,col_in))+"" == trim(ds.GetColumn(j,col_in))+"")) {
					return j;
				}
			}
		}
	}
	
	return -1;
}

//------------------------------------------------------------------------------------
//  함수명  : DS_CheckRequired
//  기  능  : 주어진 컬럼id리스트(필수입력항목)에 대해 입력여부 체크
//  인  자  : ds        - Dataset 객체
//            collist   - 체크할 컬럼명리스트 문자열 (ex. "AIRLINE_CD;AIRLINE_3CD")
//            flag      - 체크할 플래그값 (insert/update, null이면 둘다)
//  리턴값  : -1   - 정상
//            그외 - 미입력된 rowindex
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_CheckRequired(ds, collist, flag)
{
	var j = 1;
	var colid = NToken(collist, ";", j++);
	
	while (colid != "") {
		for (var i = 0; i < ds.GetRowCount(); i++) {
			if (toLower(ds.GetRowType(i)) == "normal" || toLower(ds.GetRowType(i)) == "delete") 
				continue;
			
			if (flag != null)
				if (toLower(ds.GetRowType(i)) != flag) continue;
				
			if ((length(trim(ds.GetColumn(i,colid)))) == 0) {
				return i;
			}
		}
		colid = NToken(collist, ";", j++);
	}
	return -1;
}
function DS_CheckRequired2(ds, collist, flag) //return값을 "rowindex;컬럼id" 형식으로
{
	var j = 1;
	var colid = NToken(collist, ";", j++);
	
	while (colid != "") {
		for (var i = 0; i < ds.GetRowCount(); i++) {
			if (toLower(ds.GetRowType(i)) == "normal" || toLower(ds.GetRowType(i)) == "delete") 
				continue;
				
			if (flag != null)
				if (toLower(ds.GetRowType(i)) != flag) continue;
				
			if ((length(trim(ds.GetColumn(i,colid)))) == 0) {
				return (i + ";" + colid);
			}
		}
		colid = NToken(collist, ";", j++);
	}
	return -1;
} 

//------------------------------------------------------------------------------------
//  함수명  : DS_DeleteEmptyrows
//  기  능  : 새로 추가된 행 중에서 주어진 컬럼id리스트(필수입력항목)가 비어있는 경우 행을 delete
//  인  자  : ds        - Dataset 객체
//            collist   - 체크할 컬럼명리스트 문자열 (ex. "AIRLINE_CD;AIRLINE_3CD")
//  리턴값  : 없음
//  비  고  : Multi 행 입력후 필수여부 체크 전에 수행하여 아무값도 입력하지 않은경우 체크대상에서 제외하도록
//------------------------------------------------------------------------------------
function DS_DeleteEmptyrows(ds, collist)
{
	var j, empty, colid;
	
	var i = ds.rowcount;
	while (i > 0) {
		
		j = 1;
		empty = true;
		colid = NToken(collist, ";", j++);
		
		if (toLower(ds.GetRowType(i-1)) == "insert") 
		{
			while (colid != "") 
			{
				var value = ds.GetColumn(i-1,colid);

				if (gfnTrim(value) != "") {
					empty = false;
					break;
				}
					
				colid = NToken(collist, ";", j++);
			}
			if (empty == true) ds.DeleteRow(i-1);
		}
		i--;
	}
} 

//------------------------------------------------------------------------------------
//  함수명  : DS_ToString
//  기  능  : dataset을 컬럼별로 sep기호로 구분하여 문자열로 저장
//  인  자  : ds        - Dataset 객체
//            rowsep    - row 구분기호
//            colsep    - col 구분기호
//            incheader - 컬럼명 포함 여부(true면 컬럼명 포함, flase면 미포함)
//  리턴값  : 저장한 문자열
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_ToString(ds, rowsep, colsep, incheader)
{
	var i,j;
	var buff = "";
	var rows = ds.RowCount();
	var cols = ds.ColCount();
	
	if (incheader == true) {
		for (j=0; j < cols; j++) {
			buff += ds.GetColID(j) + colsep;
		}
		buff = left(buff, length(buff)-length(colsep));
		buff += rowsep;
	}	
	
	for (i=0; i < rows; i++) {
		for (j=0; j < cols; j++) {
			buff += trim(ds.GetColumn(i, j)) + colsep;
		}
		buff = left(buff, length(buff)-length(colsep));
		buff += rowsep;
	}
	return buff;
}

//------------------------------------------------------------------------------------
//  함수명  : DS_ToCVS
//  기  능  : dataset을 CVS 형식 문자열로 저장
//  인  자  : ds        - Dataset 객체
//            incheader - 컬럼명 포함 여부(true면 컬럼명 포함, flase면 미포함)
//  리턴값  : 저장한 문자열
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_ToCVS(ds, incheader)
{
	var rowsep = chr(13) + chr(10);
	var colsep = ",";
	
	if (incheader == null) incheader = false;
	return DS_ToString(ds, rowsep, colsep, incheader);
}

//------------------------------------------------------------------------------------
//  함수명  : DS_GetRowtypeCount
//  기  능  : ds에서 주어진 rowtype의 count 취득
//  인  자  : ds      - Dataset 객체
//            rowtype - 갯수를 구할 rowtype 문자열(ex. "normal", "update", "delete", "insert")
//  리턴값  : -1   - 잘못된 rowtype
//            그외 - 발견된 갯수
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_GetRowtypeCount(ds, rowtype)
{
	rowtype = toLower(rowtype);
	
	if (rowtype != "normal" &&
		rowtype != "update" &&
		rowtype != "delete" &&
		rowtype != "insert" ) return -1;
	
	var count = 0;
	if (rowtype == "delete") {
		for (var i=0; i<ds.GetOrgBuffCount(); i++) {
			if (ds.GetOrgBuffType(i) == rowtype )
				count++;
		}	
	}	else {
		for (var i=0; i<ds.RowCount(); i++) {
			if (ds.GetRowType(i) == rowtype)
				count++;
		}
	}
	
	return count;
}

//------------------------------------------------------------------------------------
//  함수명  : DS_SetFocus
//  기  능  : Dataset을 그리드에 표현시 특정 row 에 focusing & scrolling
//  인  자  : ds      - dataset
//            grid    - 그리드 객체
//            row     - focus를 이동할 dataset의 rowindex
//            colinfo - 그리드의 컬럼index 또는 colid (optional. default는 1)
//  리턴값  : 없음
//  비  고  : 포커스를 받은 레코드를 그리드의 맨 상단으로 표시 (OnRowPosChanged 두번 발생에 유의)
//------------------------------------------------------------------------------------
function DS_SetFocus(ds, grid, row, colinfo)
{
	var gap = grid.Height / grid.RowHeight;
	
	ds.RowPos = row + gap - 3;
	ds.RowPos = row; // - 1;
	
	if (grid.MultiSelect == true) {
		ds.ClearSelect();
		ds.SelectRow(row);
	}
	
	if (colinfo == null) 
		colinfo = 1; //default colindex
	
	colinfo = gfnTrim(colinfo);
	
	if (!isdigit(colinfo)) {
		//colid
		var hcnt = grid.GetCellCount("head");
		for (var i=0; i<hcnt; i++) {
			var colid = grid.GetCellProp("body",i,"colid");
			if (colid == colinfo) {
				grid.SetCellPos(i);
				break;
			}
		}
		
	} else {
		//colindex
		grid.SetCellPos(colinfo);
	}

	grid.SetFocus();
}

//------------------------------------------------------------------------------------
//  함수명  : DS_SetFocus2
//  기  능  : Dataset을 그리드에 표현시 특정 row 에 focusing & scrolling
//  인  자  : ds   - dataset
//            grid - 그리드 객체
//            row  - focus를 이동할 dataset의 rowindex
//            colinfo - 그리드의 컬럼index 또는 colid (optional. default는 1)
//  리턴값  : 없음
//  비  고  : 포커스 행으로 한번 스크롤. 그리드 맨 상단으로 위치시키지는 않음 (OnRowPosChanged 한번 발생)
//------------------------------------------------------------------------------------
function DS_SetFocus2(ds, grid, row, colinfo)
{
	ds.RowPos = row;
	
	if (grid.MultiSelect == true) {
		ds.ClearSelect();
		ds.SelectRow(row);
	}
	if (colinfo == null) 
		colinfo = 1; //default colindex
	
	colinfo = gfnTrim(colinfo);
	
//alert(isdigit(1));  -> false
//alert(isdigit("1"));  -> true

	if (!isdigit(colinfo)) {
		//colid
		var hcnt = grid.GetCellCount("head");
		for (var i=0; i<hcnt; i++) {
			var colid = grid.GetCellProp("body",i,"colid");
			if (colid == colinfo) {
				grid.SetCellPos(i);
				break;
			}
		}
		
	} else {
		//colindex
		grid.SetCellPos(colinfo);
	}
	
	grid.SetFocus();
}

//------------------------------------------------------------------------------------
//  함수명  : DS_SetFocus3
//  기  능  : Dataset을 그리드에 표현시 특정 row 에 focusing & scrolling & editing
//  인  자  : ds   - dataset
//            grid - 그리드 객체
//            row  - focus를 이동할 dataset의 rowindex
//            colinfo - 그리드의 컬럼index 또는 colid (optional. default는 1)
//  리턴값  : 없음
//  비  고  : 포커스 행으로 한번 스크롤(OnRowPosChanged 한번 발생) 후, Editing 모드로 전환
//            유의!! colinfo 는 string 타입으로 넘겨야 한다.
//------------------------------------------------------------------------------------
function DS_SetFocus3(ds, grid, row, colinfo)
{
	ds.RowPos = row;
	
	if (grid.MultiSelect == true) {
		ds.ClearSelect();
		ds.SelectRow(row);
	}
	if (colinfo == null) 
		colinfo = 1; //default colindex

	colinfo = gfnTrim(colinfo);

	if (!isdigit(colinfo)) {
		//colid
		var hcnt = grid.GetCellCount("head");
		for (var i=0; i<hcnt; i++) {
			var colid = grid.GetCellProp("body",i,"colid");
			if (colid == colinfo) {
				grid.SetCellPos(i);
				break;
			}
		}
		
	} else {
		//colindex
		grid.SetCellPos(colinfo);
	}
	
	grid.SetFocus();
	grid.ShowEditor();
}

//------------------------------------------------------------------------------------
//  함수명  : DS_GetDistinctCount
//  기  능  : 주어진 컬럼내에 중복되는 데이터를 제외한 레코드 건수
//  인  자  : ds    - Dataset 객체
//            colid - 체크할 컬럼ID
//  리턴값  : 건수
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_GetDistinctCount(ds, colid)
{
	var count=0;
	
	Create("Dataset","ds_tmp","");
	ds_tmp.AddColumn("TEMP_COL","String",256); 
		
	for (var i = 0; i < ds.GetRowCount(); i++) //GetTotalRowCount
	{
		var str = Trim(ds.GetColumn(i,colid));
		if (ds_tmp.FindRow("TEMP_COL",gfnTrim(str)) == -1) {
			ds_tmp.AddRow();
			ds_tmp.SetColumn(count++, "TEMP_COL", str);
		}
	}
	
	count = ds_tmp.rowcount;
	
	Destroy("ds_tmp");
	return count;
}

//------------------------------------------------------------------------------------
//  함수명  : DS_GetCountWhen
//  기  능  : 특정 colid 의 값이 value 인 레코드 건수
//  인  자  : ds    - Dataset 객체
//            colid - 체크할 컬럼ID
//            value - 체크할 값
//  리턴값  : 건수
//  비  고  : 
//------------------------------------------------------------------------------------
function DS_GetCountWhen(ds, colid, value)
{
	var count = 0;
	var rowcount = ds.GetRowCount();
	for (var i = 0; i < rowcount; i++) {
		if (ds.GetColumn(i,colid) == value) {
			count++;
		}
	}
	return count;
}

function ds_LoadDataset(ds_obj, sqlid, code, args)
{
	ds_obj.ServiceID = "dbsvc::pub_comm_SchAction.do";
	ds_obj.Argument = "sqlid = " + quote(sqlid)
					+ " code = " + quote(code);

	if (args != null && args != "") ds_obj.Argument += args;
	ds_obj.Load();
}

/*
 * 여러개의 dataset을 동시에 Load한다.
 * sid      	: 서비스id
 * dataSource 	: data source name 
 * sqlXml   	: JDBCWrapper에 정의된 XML 파일
 * sqlCode  	: query할 sqlcode
 * outds    	: return dataset
 * args     	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag     	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback 	: return될 CallBack함수
 */
function DS_MultiSelect(sid, dataSource, sqlXml, sqlCode, outds, args, flag, callback)
{
	if (sqlXml==null || sqlCode==null || outds==null) return;
	
	if (isValidObject(Object("ds_args"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_args.deleteAll();
	} else {
		Create("Dataset", "ds_args");
		ds_args.addColumn("context", 		"STRING", 255);
		ds_args.addColumn("dataSource", 	"STRING", 255);
		ds_args.addColumn("sqlXml", 		"STRING", 255);
		ds_args.addColumn("sqlCode", 		"STRING", 255);
		ds_args.addColumn("out", 			"STRING", 255);
		ds_args.addColumn("args", 			"STRING", 1024);
	}

	if (isValidObject(object(outds))==false) Create("Dataset", outds);
	
	var nRow = ds_args.AddRow();
	ds_args.setColumn(nRow, "context", 		"java:comp/env");
	ds_args.setColumn(nRow, "dataSource", 	dataSource);	
	ds_args.setColumn(nRow, "sqlXml", 		sqlXml);
	ds_args.setColumn(nRow, "sqlCode", 		sqlCode);
	ds_args.setColumn(nRow, "out", 			outds);
	if (args != null)
		ds_args.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var outstr = "";
		for (var i=0;i<ds_args.getRowCount();i++)
			outstr += iif(outstr!=""," ") + ds_args.getColumn(i, "out") + "=" + ds_args.getColumn(i, "out");
		//trace(ds_args.savexml());
		transaction(sid, "DBSrv::common_DsMultiSelect.do", "input=ds_args", outstr, "", callback);
	}
}

/*
 * 여러개의 dataset을 동시에 Load한다.
 * sid      	: 서비스id
 * dataSource 	: data source name 
 * sqlXml   	: JDBCWrapper에 정의된 XML 파일
 * sqlCode  	: query할 sqlcode
 * outds    	: return dataset
 * args     	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag     	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback 	: return될 CallBack함수
 * extra 변수와 일반 변수의 구분자는 (,,)이다
 */
function DS_MultiSelectImp(sid, dataSource, sqlXml, sqlCode, outds, args, flag, callback)
{
	if (sqlXml==null || sqlCode==null || outds==null) return;
	
	if (isValidObject(Object("ds_args"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_args.deleteAll();
	} else {
		Create("Dataset", "ds_args");
		ds_args.addColumn("context", 		"STRING", 255);
		ds_args.addColumn("dataSource", 	"STRING", 255);
		ds_args.addColumn("sqlXml", 		"STRING", 255);
		ds_args.addColumn("sqlCode", 		"STRING", 255);
		ds_args.addColumn("out", 			"STRING", 255);
		ds_args.addColumn("args", 			"STRING", 1024);
	}

	if (isValidObject(object(outds))==false) Create("Dataset", outds);
	
	var nRow = ds_args.AddRow();
	ds_args.setColumn(nRow, "context", 		"java:comp/env");
	ds_args.setColumn(nRow, "dataSource", 	dataSource);	
	ds_args.setColumn(nRow, "sqlXml", 		sqlXml);
	ds_args.setColumn(nRow, "sqlCode", 		sqlCode);
	ds_args.setColumn(nRow, "out", 			outds);
	if (args != null)
		ds_args.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var outstr = "";
		for (var i=0;i<ds_args.getRowCount();i++)
			outstr += iif(outstr!=""," ") + ds_args.getColumn(i, "out") + "=" + ds_args.getColumn(i, "out");
		//trace(ds_args.savexml());
		transaction(sid, "DBSrv::Common_DsMultiSelectImp.do", "input=ds_args", outstr, "", callback);
	}
}


/*
 * 여러개의 dataset을 동시에 수행 한다.
 * sid        	: 서비스id
 * dataSource 	: data source name
 * sqlXml     	: JDBCWrapper에 정의된 XML 파일
 * sqlCode    	: query할 sqlcode
 * inds       	: input dataset
 * args       	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag       	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback   	: return될 CallBack함수
 */
function DS_MultiTransAction(sid, dataSource, sqlXml, sqlCode, inDs, args, flag, callback)
{
	if (sqlXml==null || sqlCode==null || inDs==null) return;
	
	if (isValidObject(Object("ds_argsTr"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_argsTr.deleteAll();
	} else {
		Create("Dataset", "ds_argsTr");
		ds_argsTr.addColumn("context", 		"STRING", 255);		
		ds_argsTr.addColumn("dataSource", 	"STRING", 255);
		ds_argsTr.addColumn("sqlXml", 		"STRING", 255);
		ds_argsTr.addColumn("sqlCode", 		"STRING", 255);
		ds_argsTr.addColumn("inDs", 		"STRING", 255);
		ds_argsTr.addColumn("args", 		"STRING", 1024);
	}

	if (isValidObject(object(inDs))==false) Create("Dataset", inDs);
	
	var nRow = ds_argsTr.AddRow();
	ds_argsTr.setColumn(nRow, "context", 	"java:comp/env");	
	ds_argsTr.setColumn(nRow, "dataSource", dataSource);
	ds_argsTr.setColumn(nRow, "sqlXml", 	sqlXml);
	ds_argsTr.setColumn(nRow, "sqlCode", 	sqlCode);
	ds_argsTr.setColumn(nRow, "inDs", 		inDs);

	if (args != null)
		ds_argsTr.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var inputStr = "input=ds_argsTr ";
		for (var i=0;i<ds_argsTr.getRowCount();i++){
			inputStr += "input"+i+"=" + ds_argsTr.getColumn(i, "inDs") + " ";
		}

		Transaction(sid, "DBSrv::common_DsMultiTransAction.do", inputStr, "", "", callback);
	}
}

/*
 * 여러개의 dataset을 동시에 수행 한다.
 * sid        	: 서비스id
 * dataSource 	: data source name
 * sqlXml     	: JDBCWrapper에 정의된 XML 파일
 * sqlCode    	: query할 sqlcode
 * inds       	: input dataset
 * args       	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag       	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback   	: return될 CallBack함수
 * extra 변수와 일반 변수의 구분자는 (,,)이다
 */
function DS_MultiTransActionImp(sid, dataSource, sqlXml, sqlCode, inDs, args, flag, callback)
{
	if (sqlXml==null || sqlCode==null || inDs==null) return;
	
	if (isValidObject(Object("ds_argsTr"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_argsTr.deleteAll();
		
	} else {
		Create("Dataset", "ds_argsTr");
		ds_argsTr.addColumn("context", 		"STRING", 255);		
		ds_argsTr.addColumn("dataSource", 	"STRING", 255);
		ds_argsTr.addColumn("sqlXml", 		"STRING", 255);
		ds_argsTr.addColumn("sqlCode", 		"STRING", 255);
		ds_argsTr.addColumn("inDs", 		"STRING", 255);
		ds_argsTr.addColumn("args", 		"STRING", 1024);
	}

	if (isValidObject(object(inDs))==false) Create("Dataset", inDs);
	
	var nRow = ds_argsTr.AddRow();
	ds_argsTr.setColumn(nRow, "context", 	"java:comp/env");	
	ds_argsTr.setColumn(nRow, "dataSource", dataSource);
	ds_argsTr.setColumn(nRow, "sqlXml", 	sqlXml);
	ds_argsTr.setColumn(nRow, "sqlCode", 	sqlCode);
	ds_argsTr.setColumn(nRow, "inDs", 		inDs);
	
	if (args != null)
		ds_argsTr.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {

		var inputStr = "input=ds_argsTr ";
		for (var i=0;i<ds_argsTr.getRowCount();i++){
			inputStr += "input"+i+"=" + ds_argsTr.getColumn(i, "inDs") + " ";
		}
		
		Transaction(sid, "DBSrv::Common_DsMultiTransActionImp.do", inputStr, "", "", callback);
	}
}


/*
 * 여러개의 dataset을 동시에 입력/수정/삭제 한다.
 * sid      	: 서비스id
 * dataSource 	: data source name 
 * sqlXml   	: JDBCWrapper에 정의된 XML 파일
 * sqlCode  	: query할 sqlcode
 * inds     	: input dataset
 * args     	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag     	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback 	: return될 CallBack함수
 */
function DS_IUDMultiTransAction(sid, dataSource, sqlXml, sqlCode, inDs, args, flag, callback)
{
	if (sqlXml==null || sqlCode==null || inDs==null) return;
	
	if (isValidObject(Object("ds_argsTr"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_argsTr.deleteAll();
	} else {
		Create("Dataset", "ds_argsTr");
		ds_argsTr.addColumn("context", 		"STRING", 255);				
		ds_argsTr.addColumn("dataSource", 	"STRING", 255);
		ds_argsTr.addColumn("sqlXml", 		"STRING", 255);
		ds_argsTr.addColumn("sqlCode", 		"STRING", 255);
		ds_argsTr.addColumn("inDs", 		"STRING", 255);
		ds_argsTr.addColumn("args", 		"STRING", 1024);
	}

	if (isValidObject(object(inDs))==false) Create("Dataset", inDs);
	
	var nRow = ds_argsTr.AddRow();
	ds_argsTr.setColumn(nRow, "context", 	"java:comp/env");	
	ds_argsTr.setColumn(nRow, "dataSource", dataSource);	
	ds_argsTr.setColumn(nRow, "sqlXml", 	sqlXml);
	ds_argsTr.setColumn(nRow, "sqlCode", 	sqlCode);
	ds_argsTr.setColumn(nRow, "inDs", 		inDs);

	if (args != null)
		ds_argsTr.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var inputStr = "input=ds_argsTr ";
		for (var i=0;i<ds_argsTr.getRowCount();i++){
			inputStr += "input"+i+"=" + ds_argsTr.getColumn(i, "inDs") + " ";
		}
		trace("inputStr=="+inputStr);
		trace("IsValidObject=="+IsValidObject(Object("ds_Dist:U")));
		Transaction(sid, "DBSrv::common_DsIUDMultiTransAction.do", inputStr, "", "", callback);
	}
}

/*
 * 여러개의 dataset을 동시에 입력/수정/삭제 한다.
 * sid      	: 서비스id
 * dataSource 	: data source name 
 * sqlXml   	: JDBCWrapper에 정의된 XML 파일
 * sqlCode  	: query할 sqlcode
 * inds     	: input dataset
 * args     	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag     	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback 	: return될 CallBack함수
 * extra 변수와 일반 변수의 구분자는 (,,)이다
 */
function DS_IUDMultiTransActionImp(sid, dataSource, sqlXml, sqlCode, inDs, args, flag, callback)
{
	if (sqlXml==null || sqlCode==null || inDs==null) return;
	
	if (isValidObject(Object("ds_argsTr"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_argsTr.deleteAll();
	} else {
		Create("Dataset", "ds_argsTr");
		ds_argsTr.addColumn("context", 		"STRING", 255);				
		ds_argsTr.addColumn("dataSource", 	"STRING", 255);
		ds_argsTr.addColumn("sqlXml", 		"STRING", 255);
		ds_argsTr.addColumn("sqlCode", 		"STRING", 255);
		ds_argsTr.addColumn("inDs", 		"STRING", 255);
		ds_argsTr.addColumn("args", 		"STRING", 1024);
	}

	if (isValidObject(object(inDs))==false) Create("Dataset", inDs);
	
	var nRow = ds_argsTr.AddRow();
	ds_argsTr.setColumn(nRow, "context", 	"java:comp/env");	
	ds_argsTr.setColumn(nRow, "dataSource", dataSource);	
	ds_argsTr.setColumn(nRow, "sqlXml", 	sqlXml);
	ds_argsTr.setColumn(nRow, "sqlCode", 	sqlCode);
	ds_argsTr.setColumn(nRow, "inDs", 		inDs);

	if (args != null)
		ds_argsTr.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var inputStr = "input=ds_argsTr ";
		for (var i=0;i<ds_argsTr.getRowCount();i++){
			inputStr += "input"+i+"=" + ds_argsTr.getColumn(i, "inDs") + " ";
		}
		
		Transaction(sid, "DBSrv::Common_DsIUDMultiTransActionImp.do", inputStr, "", "", callback);
	}
}

/*
 * 여러개의 dataset을 동시에 삭제만 한다.
 * sid      	: 서비스id
 * dataSource 	: data source name 
 * sqlXml   	: JDBCWrapper에 정의된 XML 파일
 * sqlCode  	: query할 sqlcode
 * inds     	: input dataset
 * args     	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag     	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback 	: return될 CallBack함수
 */
function DS_DeleteMultiTransAction(sid, dataSource, sqlXml, sqlCode, inDs, args, flag, callback)
{
	if (sqlXml==null || sqlCode==null || inDs==null) return;
	
	if (isValidObject(Object("ds_argsTr"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_argsTr.deleteAll();
	} else {
		Create("Dataset", "ds_argsTr");
		ds_argsTr.addColumn("context", 		"STRING", 255);				
		ds_argsTr.addColumn("dataSource", 	"STRING", 255);
		ds_argsTr.addColumn("sqlXml", 		"STRING", 255);
		ds_argsTr.addColumn("sqlCode", 		"STRING", 255);
		ds_argsTr.addColumn("inDs", 		"STRING", 255);
		ds_argsTr.addColumn("args", 		"STRING", 1024);
	}

	if (isValidObject(object(inDs))==false) Create("Dataset", inDs);
	
	var nRow = ds_argsTr.AddRow();
	ds_argsTr.setColumn(nRow, "context", 	"java:comp/env");	
	ds_argsTr.setColumn(nRow, "dataSource", dataSource);	
	ds_argsTr.setColumn(nRow, "sqlXml", 	sqlXml);
	ds_argsTr.setColumn(nRow, "sqlCode", 	sqlCode);
	ds_argsTr.setColumn(nRow, "inDs", 		inDs);

	if (args != null)
		ds_argsTr.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var inputStr = "input=ds_argsTr ";
		for (var i=0;i<ds_argsTr.getRowCount();i++){
			inputStr += "input"+i+"=" + ds_argsTr.getColumn(i, "inDs") + " ";
		}
		
		Transaction(sid, "DBSrv::common_DsDeleteMultiTransAction.do", inputStr, "", "", callback);
	}
}

/*
 * 여러개의 dataset을 동시에 삭제만 한다.
 * sid      	: 서비스id
 * dataSource 	: data source name 
 * sqlXml   	: JDBCWrapper에 정의된 XML 파일
 * sqlCode  	: query할 sqlcode
 * inds     	: input dataset
 * args     	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag     	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback 	: return될 CallBack함수
 * extra 변수와 일반 변수의 구분자는 (,,)이다
 */
function DS_DeleteMultiTransActionImp(sid, dataSource, sqlXml, sqlCode, inDs, args, flag, callback)
{
	if (sqlXml==null || sqlCode==null || inDs==null) return;
	
	if (isValidObject(Object("ds_argsTr"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_argsTr.deleteAll();
	} else {
		Create("Dataset", "ds_argsTr");
		ds_argsTr.addColumn("context", 		"STRING", 255);				
		ds_argsTr.addColumn("dataSource", 	"STRING", 255);
		ds_argsTr.addColumn("sqlXml", 		"STRING", 255);
		ds_argsTr.addColumn("sqlCode", 		"STRING", 255);
		ds_argsTr.addColumn("inDs", 		"STRING", 255);
		ds_argsTr.addColumn("args", 		"STRING", 1024);
	}

	if (isValidObject(object(inDs))==false) Create("Dataset", inDs);
	
	var nRow = ds_argsTr.AddRow();
	ds_argsTr.setColumn(nRow, "context", 	"java:comp/env");	
	ds_argsTr.setColumn(nRow, "dataSource", dataSource);	
	ds_argsTr.setColumn(nRow, "sqlXml", 	sqlXml);
	ds_argsTr.setColumn(nRow, "sqlCode", 	sqlCode);
	ds_argsTr.setColumn(nRow, "inDs", 		inDs);

	if (args != null)
		ds_argsTr.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var inputStr = "input=ds_argsTr ";
		for (var i=0;i<ds_argsTr.getRowCount();i++){
			inputStr += "input"+i+"=" + ds_argsTr.getColumn(i, "inDs") + " ";
		}
		
		Transaction(sid, "DBSrv::Common_DsDeleteMultiTransActionImp.do", inputStr, "", "", callback);
	}
}

/*
 * 여러개의 dataset을 동시에 입력/수정/삭제 한다.
 * sid      	: 서비스id
 * dataSource 	: data source name 
 * sqlXml   	: JDBCWrapper에 정의된 XML 파일
 * sqlCode  	: query할 sqlcode
 * inds     	: input dataset
 * args     	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag     	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback 	: return될 CallBack함수
 */
function DS_MultiUploadTransAction(sid, dataSource, sqlXml, sqlCode, inDs, iudType, args, flag, callback)
{
	
	if (sqlXml==null || sqlCode==null || inDs==null) return;
	
	if (isValidObject(Object("ds_argsMTr"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_argsMTr.deleteAll();
	} else {
		Create("Dataset", "ds_argsMTr");
		ds_argsMTr.addColumn("context", 		"STRING", 255);				
		ds_argsMTr.addColumn("dataSource", 	"STRING", 255);
		ds_argsMTr.addColumn("sqlXml", 		"STRING", 255);
		ds_argsMTr.addColumn("sqlCode", 		"STRING", 255);
		ds_argsMTr.addColumn("inDs", 		"STRING", 255);
		ds_argsMTr.addColumn("iudType", 		"STRING", 255);
		ds_argsMTr.addColumn("args", 		"STRING", 1024);
	}

	if (isValidObject(object(inDs))==false) Create("Dataset", inDs);
	
	var nRow = ds_argsMTr.AddRow();
	ds_argsMTr.setColumn(nRow, "context", 	"java:comp/env");	
	ds_argsMTr.setColumn(nRow, "dataSource", dataSource);	
	ds_argsMTr.setColumn(nRow, "sqlXml", 	sqlXml);
	ds_argsMTr.setColumn(nRow, "sqlCode", 	sqlCode);
	ds_argsMTr.setColumn(nRow, "inDs", 		inDs);
	ds_argsMTr.setColumn(nRow, "iudType",	iudType);	

	if (args != null)
		ds_argsMTr.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var inputStr = "input=ds_argsMTr ";
		for (var i=0;i<ds_argsMTr.getRowCount();i++){
			inputStr += "input"+i+"=" + ds_argsMTr.getColumn(i, "inDs") + " ";
		}

		Transaction(sid, "DBSrv::multiUploadBoard_TransAction.do", inputStr, "", "", callback);
	}	
}

/*
 * 파일과 데이터를 한번에 등록/수정/삭제한다(한개의 데이터셋)
 * sid      	: 서비스id
 * dataSource 	: data source name 
 * sqlXml   	: JDBCWrapper에 정의된 XML 파일
 * sqlCode  	: query할 sqlcode
 * inds     	: input dataset
 * args     	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag     	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback 	: return될 CallBack함수
 */
function DsSingle_File_TransAction(sid, dataSource, sqlXml, sqlCode, inDs, iudType, args, flag, callback)
{
	
	if (sqlXml==null || sqlCode==null || inDs==null) return;
	
	if (isValidObject(Object("ds_argsMTr"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_argsMTr.deleteAll();
	} else {
		Create("Dataset", "ds_argsMTr");
		ds_argsMTr.addColumn("context", 		"STRING", 255);				
		ds_argsMTr.addColumn("dataSource", 	"STRING", 255);
		ds_argsMTr.addColumn("sqlXml", 		"STRING", 255);
		ds_argsMTr.addColumn("sqlCode", 		"STRING", 255);
		ds_argsMTr.addColumn("inDs", 		"STRING", 255);
		ds_argsMTr.addColumn("iudType", 		"STRING", 255);
		ds_argsMTr.addColumn("args", 		"STRING", 1024);
	}

	if (isValidObject(object(inDs))==false) Create("Dataset", inDs);
	
	var nRow = ds_argsMTr.AddRow();
	ds_argsMTr.setColumn(nRow, "context", 	"java:comp/env");	
	ds_argsMTr.setColumn(nRow, "dataSource", dataSource);	
	ds_argsMTr.setColumn(nRow, "sqlXml", 	sqlXml);
	ds_argsMTr.setColumn(nRow, "sqlCode", 	sqlCode);
	ds_argsMTr.setColumn(nRow, "inDs", 		inDs);
	ds_argsMTr.setColumn(nRow, "iudType",	iudType);	

	if (args != null)
		ds_argsMTr.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var inputStr = "input=ds_argsMTr ";
		for (var i=0;i<ds_argsMTr.getRowCount();i++){
			inputStr += "input"+i+"=" + ds_argsMTr.getColumn(i, "inDs") + " ";
		}

		Transaction(sid, "DBSrv::Common_DsSingle_File_TransAction.do", inputStr, "", "", callback);
		
	}	
}

/*
 * 여러개의 dataset을 동시에 Load한다.
 * sid      	: 서비스id
 * dataSource 	: data source name 
 * sqlXml   	: JDBCWrapper에 정의된 XML 파일
 * sqlCode  	: query할 sqlcode
 * outds    	: return dataset
 * args     	: 서버에 넘겨 줄 Parameters 반드시 var#=#val 형태로 정의해야 함.
 * flag     	: "clear" => ds_args deletaAll(),
				: "call"  => 해당 Servlet 호출한다.
 * callback 	: return될 CallBack함수
 */
function DS_FileUpload(sid, dataSource, sqlXml, sqlCode, inDs, outds, args, flag, callback)
{
	if (sqlXml==null || sqlCode==null || outds==null) return;
	
	if (isValidObject(Object("ds_args"))) {
		if (flag!=null && indexOf(flag,"clear")>=0) ds_args.deleteAll();
	} else {
		Create("Dataset", "ds_args");
		ds_args.addColumn("context", 		"STRING", 255);
		ds_args.addColumn("dataSource", 	"STRING", 255);
		ds_args.addColumn("sqlXml", 		"STRING", 255);
		ds_args.addColumn("sqlCode", 		"STRING", 255);
		ds_args.addColumn("inDs", 		"STRING", 255);		
		ds_args.addColumn("out", 			"STRING", 255);
		ds_args.addColumn("args", 			"STRING", 1024);
	}

	if (isValidObject(object(outds))==false) Create("Dataset", outds);
	
	var nRow = ds_args.AddRow();
	ds_args.setColumn(nRow, "context", 		"java:comp/env");
	ds_args.setColumn(nRow, "dataSource", 	dataSource);	
	ds_args.setColumn(nRow, "sqlXml", 		sqlXml);
	ds_args.setColumn(nRow, "sqlCode", 		sqlCode);
	ds_args.setColumn(nRow, "inDs", 		inDs);
	ds_args.setColumn(nRow, "out", 			outds);
	if (args != null)
		ds_args.setColumn(nRow, "args", args);
		
	if (flag!=null && indexOf(flag,"call") >= 0) {
		var inputStr = "input=ds_args ";
		for (var i=0;i<ds_args.getRowCount();i++){
			inputStr += "input"+i+"=" + ds_args.getColumn(i, "inDs") + " ";
		}
			
		var outstr = "";
		for (var i=0;i<ds_args.getRowCount();i++)
			outstr += iif(outstr!=""," ") + ds_args.getColumn(i, "out") + "=" + ds_args.getColumn(i, "out");

		transaction(sid, "DBSrv::fileUpload.do", inputStr, outstr, "", callback);
	}
}
