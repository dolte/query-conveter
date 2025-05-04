
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s03
{
    public bra03_s03()
    {
    }
    //##**$$ocr_demd_insert
    /*
    */
    public DOBJ CTLocr_data_make(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLocr_demd_insert_OSP1(Conn, dobj);           //  청구 데이터 생성
            dobj  = CALLocr_demd_insert_SEL2(Conn, dobj);           //  청구 데이터 오류 내역
            dobj  = CALLocr_demd_insert_SEL3(Conn, dobj);           //  지부별 청구 완료 정보 조회
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLocr_data_make( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLocr_demd_insert_OSP1(Conn, dobj);           //  청구 데이터 생성
        dobj  = CALLocr_demd_insert_SEL2(Conn, dobj);           //  청구 데이터 오류 내역
        dobj  = CALLocr_demd_insert_SEL3(Conn, dobj);           //  지부별 청구 완료 정보 조회
        return dobj;
    }
    // 청구 데이터 생성
    // 청구 데이터를 생성하기 위한 프로시저 (GIBU.SP_PROC_DEMDGIRO) 를 호출한다
    public DOBJ CALLocr_demd_insert_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        String[]  incolumns ={"BRAN_CD","DEMD_YRMN","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_DEMD_OCR";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={"P_CNT_READ","P_CNT_INST","P_CNT_AUTO","P_CNT_PREPAY","P_CNT_ERR"};;
        int[]    outtypes ={12, 12, 12, 12, 12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        robj.setRetcode(1);
        dobj.setRetObject(robj);
        return dobj;
    }
    // 청구 데이터 오류 내역
    // 청구 데이터 생성 시 발생한 오류 내역
    public DOBJ CALLocr_demd_insert_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLocr_demd_insert_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLocr_demd_insert_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_YRMN  ,  C.CODE_NM  DEMD_NM  ,  A.DEMD_GBN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.ERR_GBN  ,  A.ERR_CTENT  FROM  GIBU.TBRA_DEMD_ERR  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TENV_CODE  C  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  A.DEMD_GBN  =  C.CODE_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 지부별 청구 완료 정보 조회
    // 지부별로 OCR 진행정보를 조회한다
    public DOBJ CALLocr_demd_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLocr_demd_insert_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLocr_demd_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '완료')  BRAN_END  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  DISTINCT  B.BRAN_CD  ,  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    //##**$$ocr_demd_insert
    //##**$$ocr_demd_init
    /* * 프로그램명 : bra03_s03
    * 작성자 : 박태현
    * 작성일 : 2009/10/08
    * 설명    : 청구월의 지부별 지로 청구자료 생성여부를 조회한다.
    Input
    DEMD_YRMN (DEMD_YRMN)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLocr_data_init(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLocr_demd_init_SEL1(Conn, dobj);           //  지부별 청구 완료 정보 조회
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLocr_data_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLocr_demd_init_SEL1(Conn, dobj);           //  지부별 청구 완료 정보 조회
        return dobj;
    }
    // 지부별 청구 완료 정보 조회
    // 지부별로 OCR 진행정보를 조회한다
    public DOBJ CALLocr_demd_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLocr_demd_init_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLocr_demd_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '완료')  BRAN_END  ,  XB.INS_DAY  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  MAX(DEMD_YRMN)  DEMD_YRMN  ,  TO_CHAR(MAX(INS_DATE),  'YYYYMMDD')  INS_DAY  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :DEMD_YRMN  GROUP  BY  BRAN_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    //##**$$ocr_demd_init
    //##**$$rept_closing
    /*
    */
    public DOBJ CTLrept_closing(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  지부별마감체크
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLrept_closing( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  지부별마감체크
        return dobj;
    }
    // 지부별마감체크
    // 지부별 마감테이블의 해당 년월 정보가 있는지 체크한다
    public DOBJ CALLrept_closing_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_closing_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        String message ="해당기간중 마감내역이 존재합니다. 마감내역을 확인하세요.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  END_YEAR  ||  END_MON  =  :DEMD_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD) ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$rept_closing
    //##**$$end
}
