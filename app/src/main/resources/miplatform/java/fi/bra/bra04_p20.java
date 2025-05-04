
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_p20
{
    public bra04_p20()
    {
    }
    //##**$$bran_note_select
    /*
    */
    public DOBJ CTLbran_note_select(DOBJ dobj)
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
            dobj  = CALLbran_note_select_SEL1(Conn, dobj);           //  원장년월 초기화
            if( dobj.getRetObject("S").getRecord().get("NOTE_GBN").equals("D") || dobj.getRetObject("S").getRecord().get("NOTE_GBN").equals("E"))
            {
                dobj  = CALLbran_note_select_SEL2(Conn, dobj);           //  청구년월 조회
                dobj  = CALLbran_note_select_SEL3(Conn, dobj);           //  지부 상세원장 내역 조회
            }
            else
            {
                dobj  = CALLbran_note_select_SEL4(Conn, dobj);           //  지부 징수내역 조회
            }
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
    public DOBJ CTLbran_note_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbran_note_select_SEL1(Conn, dobj);           //  원장년월 초기화
        if( dobj.getRetObject("S").getRecord().get("NOTE_GBN").equals("D") || dobj.getRetObject("S").getRecord().get("NOTE_GBN").equals("E"))
        {
            dobj  = CALLbran_note_select_SEL2(Conn, dobj);           //  청구년월 조회
            dobj  = CALLbran_note_select_SEL3(Conn, dobj);           //  지부 상세원장 내역 조회
        }
        else
        {
            dobj  = CALLbran_note_select_SEL4(Conn, dobj);           //  지부 징수내역 조회
        }
        return dobj;
    }
    // 원장년월 초기화
    public DOBJ CALLbran_note_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_note_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_note_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YEAR = dobj.getRetObject("S").getRecord().get("NOTE_YEAR");   //원장 년
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(TO_NUMBER(:NOTE_YEAR  -  2))  AS  BB_YEAR  ,  TO_CHAR(TO_NUMBER(:NOTE_YEAR  -  1))  AS  B_YEAR  ,  :NOTE_YEAR  AS  YEAR  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("NOTE_YEAR", NOTE_YEAR);               //원장 년
        return sobj;
    }
    // 청구년월 조회
    public DOBJ CALLbran_note_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_note_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_note_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(DEMD_YRMN)  AS  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR ";
        sobj.setSql(query);
        return sobj;
    }
    // 지부 상세원장 내역 조회
    public DOBJ CALLbran_note_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_note_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_note_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("SEL1").getRecord().get("YEAR");   //검색년도
        double   START_ZIP = dobj.getRetObject("S").getRecord().getDouble("START_ZIP");   //시작 우편번호
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        double   END_ZIP = dobj.getRetObject("S").getRecord().getDouble("END_ZIP");   //종료 우편번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   B_YEAR = dobj.getRetObject("SEL1").getRecord().get("B_YEAR");   //작년
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   BB_YEAR = dobj.getRetObject("SEL1").getRecord().get("BB_YEAR");   //재작년
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.UPSO_CD  ,  XB.B_MONPRNCFEE  ,  XB.MONPRNCFEE  ,  XB.UPSO_NM  ,  XB.MNGEMSTR_NM  ,  XB.MNGEMSTR_RESINUM  ,  XB.MNGEMSTR_RESINUM_OX  ,  XB.PERMMSTR_NM  ,  XB.PERMMSTR_RESINUM  ,  XB.BIOWN_NUM  ,  XB.BIOWN_NUM_OX  ,  XB.INS_DAY  ,  XB.REPT_LAST_DAY  ,  DECODE(SUBSTR(XB.MCH_CNT,  0,  8),  '00000000',  SUBSTR(XB.MCH_CNT,  9),  SUBSTR(XB.MCH_CNT,  0,  8))  MCH_CNT  ,  XB.UPSO_ADDR  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.GRADNM  ,  XB.UPSO_PHON  ,  XB.UPSO_ZIP  ,  XB.OPBI_DAY  ,  XB.BPAP_DAY  ,  XB.ACCU_DAY  ,  XA.BB_REPT_GBN_01  ,  XA.BB_MM_01  ,  XA.BB_MAPPING_DAY_01  ,  XA.BB_REPT_GBN_02  ,  XA.BB_MM_02  ,  XA.BB_MAPPING_DAY_02  ,  XA.BB_REPT_GBN_03  ,  XA.BB_MM_03  ,  XA.BB_MAPPING_DAY_03  ,  XA.BB_REPT_GBN_04  ,  XA.BB_MM_04  ,  XA.BB_MAPPING_DAY_04  ,  XA.BB_REPT_GBN_05  ,  XA.BB_MM_05  ,  XA.BB_MAPPING_DAY_05  ,  XA.BB_REPT_GBN_06  ,  XA.BB_MM_06  ,  XA.BB_MAPPING_DAY_06  ,  XA.BB_REPT_GBN_07  ,  XA.BB_MM_07  ,  XA.BB_MAPPING_DAY_07  ,  XA.BB_REPT_GBN_08  ,  XA.BB_MM_08  ,  XA.BB_MAPPING_DAY_08  ,  XA.BB_REPT_GBN_09  ,  XA.BB_MM_09  ,  XA.BB_MAPPING_DAY_09  ,  XA.BB_REPT_GBN_10  ,  XA.BB_MM_10  ,  XA.BB_MAPPING_DAY_10  ,  XA.BB_REPT_GBN_11  ,  XA.BB_MM_11  ,  XA.BB_MAPPING_DAY_11  ,  XA.BB_REPT_GBN_12  ,  XA.BB_MM_12  ,  XA.BB_MAPPING_DAY_12  ,  XA.B_REPT_GBN_01  ,  XA.B_MM_01  ,  XA.B_MAPPING_DAY_01  ,  XA.B_REPT_GBN_02  ,  XA.B_MM_02  ,  XA.B_MAPPING_DAY_02  ,  XA.B_REPT_GBN_03  ,  XA.B_MM_03  ,  XA.B_MAPPING_DAY_03  ,  XA.B_REPT_GBN_04  ,  XA.B_MM_04  ,  XA.B_MAPPING_DAY_04  ,  XA.B_REPT_GBN_05  ,  XA.B_MM_05  ,  XA.B_MAPPING_DAY_05  ,  XA.B_REPT_GBN_06  ,  XA.B_MM_06  ,  XA.B_MAPPING_DAY_06  ,  XA.B_REPT_GBN_07  ,  XA.B_MM_07  ,  XA.B_MAPPING_DAY_07  ,  XA.B_REPT_GBN_08  ,  XA.B_MM_08  ,  XA.B_MAPPING_DAY_08  ,  XA.B_REPT_GBN_09  ,  XA.B_MM_09  ,  XA.B_MAPPING_DAY_09  ,  XA.B_REPT_GBN_10  ,  XA.B_MM_10  ,  XA.B_MAPPING_DAY_10  ,  XA.B_REPT_GBN_11  ,  XA.B_MM_11  ,  XA.B_MAPPING_DAY_11  ,  XA.B_REPT_GBN_12  ,  XA.B_MM_12  ,  XA.B_MAPPING_DAY_12  ,  XA.REPT_GBN_01  ,  XA.MM_01  ,  XA.MAPPING_DAY_01  ,  XA.REPT_GBN_02  ,  XA.MM_02  ,  XA.MAPPING_DAY_02  ,  XA.REPT_GBN_03  ,  XA.MM_03  ,  XA.MAPPING_DAY_03  ,  XA.REPT_GBN_04  ,  XA.MM_04  ,  XA.MAPPING_DAY_04  ,  XA.REPT_GBN_05  ,  XA.MM_05  ,  XA.MAPPING_DAY_05  ,  XA.REPT_GBN_06  ,  XA.MM_06  ,  XA.MAPPING_DAY_06  ,  XA.REPT_GBN_07  ,  XA.MM_07  ,  XA.MAPPING_DAY_07  ,  XA.REPT_GBN_08  ,  XA.MM_08  ,  XA.MAPPING_DAY_08  ,  XA.REPT_GBN_09  ,  XA.MM_09  ,  XA.MAPPING_DAY_09  ,  XA.REPT_GBN_10  ,  XA.MM_10  ,  XA.MAPPING_DAY_10  ,  XA.REPT_GBN_11  ,  XA.MM_11  ,  XA.MAPPING_DAY_11  ,  XA.REPT_GBN_12  ,  XA.MM_12  ,  XA.MAPPING_DAY_12  ,  GIBU.FT_SPLIT(XB.DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(XB.DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(XB.DEMDS,  ',',  10)  TOT_ADDT_AMT  ,  GIBU.FT_SPLIT(XB.DEMDS,  ',',  12)  TOT_DEMD_AMT  ,  XB.REMAK_1  ,  XB.REMAK_2  ,  XB.MNGEMSTR_ADDR  ,  XB.MNGEMSTR_HPNUM  ,  XB.MNGEMSTR_PHONNUM  ,  XB.CHG_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.REPT_GBN)))  BB_REPT_GBN_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.USE_AMT  )))  BB_MM_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.REPT_GBN)))  BB_REPT_GBN_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.USE_AMT  )))  BB_MM_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.REPT_GBN)))  BB_REPT_GBN_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.USE_AMT  )))  BB_MM_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.REPT_GBN)))  BB_REPT_GBN_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.USE_AMT  )))  BB_MM_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.REPT_GBN)))  BB_REPT_GBN_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.USE_AMT  )))  BB_MM_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.REPT_GBN)))  BB_REPT_GBN_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.USE_AMT  )))  BB_MM_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.REPT_GBN)))  BB_REPT_GBN_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.USE_AMT  )))  BB_MM_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.REPT_GBN)))  BB_REPT_GBN_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.USE_AMT  )))  BB_MM_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.REPT_GBN)))  BB_REPT_GBN_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.USE_AMT  )))  BB_MM_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.REPT_GBN)))  BB_REPT_GBN_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.USE_AMT  )))  BB_MM_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.REPT_GBN)))  BB_REPT_GBN_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.USE_AMT  )))  BB_MM_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.REPT_GBN)))  BB_REPT_GBN_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.USE_AMT  )))  BB_MM_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.MAPPING_DAY  )))  BB_MAPPING_DAY_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.REPT_GBN)))  B_REPT_GBN_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.USE_AMT  )))  B_MM_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.MAPPING_DAY  )))  B_MAPPING_DAY_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.REPT_GBN)))  B_REPT_GBN_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.USE_AMT  )))  B_MM_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.MAPPING_DAY  )))  B_MAPPING_DAY_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.REPT_GBN)))  B_REPT_GBN_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.USE_AMT  )))  B_MM_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.MAPPING_DAY  )))  B_MAPPING_DAY_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.REPT_GBN)))  B_REPT_GBN_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.USE_AMT  )))  B_MM_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.MAPPING_DAY  )))  B_MAPPING_DAY_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.REPT_GBN)))  B_REPT_GBN_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.USE_AMT  )))  B_MM_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.MAPPING_DAY  )))  B_MAPPING_DAY_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.REPT_GBN)))  B_REPT_GBN_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.USE_AMT  )))  B_MM_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.MAPPING_DAY  )))  B_MAPPING_DAY_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.REPT_GBN)))  B_REPT_GBN_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.USE_AMT  )))  B_MM_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.MAPPING_DAY  )))  B_MAPPING_DAY_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.REPT_GBN)))  B_REPT_GBN_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.USE_AMT  )))  B_MM_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.MAPPING_DAY  )))  B_MAPPING_DAY_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.REPT_GBN)))  B_REPT_GBN_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.USE_AMT  )))  B_MM_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.MAPPING_DAY  )))  B_MAPPING_DAY_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.REPT_GBN)))  B_REPT_GBN_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.USE_AMT  )))  B_MM_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.MAPPING_DAY  )))  B_MAPPING_DAY_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.REPT_GBN)))  B_REPT_GBN_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.USE_AMT  )))  B_MM_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.MAPPING_DAY  )))  B_MAPPING_DAY_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.REPT_GBN)))  B_REPT_GBN_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.USE_AMT  )))  B_MM_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.MAPPING_DAY  )))  B_MAPPING_DAY_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.REPT_GBN)))  REPT_GBN_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.USE_AMT  )))  MM_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.MAPPING_DAY  )))  MAPPING_DAY_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.REPT_GBN)))  REPT_GBN_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.USE_AMT  )))  MM_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.MAPPING_DAY  )))  MAPPING_DAY_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.REPT_GBN)))  REPT_GBN_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.USE_AMT  )))  MM_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.MAPPING_DAY  )))  MAPPING_DAY_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.REPT_GBN)))  REPT_GBN_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.USE_AMT  )))  MM_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.MAPPING_DAY  )))  MAPPING_DAY_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.REPT_GBN)))  REPT_GBN_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.USE_AMT  )))  MM_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.MAPPING_DAY  )))  MAPPING_DAY_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.REPT_GBN)))  REPT_GBN_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.USE_AMT  )))  MM_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.MAPPING_DAY  )))  MAPPING_DAY_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.REPT_GBN)))  REPT_GBN_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.USE_AMT  )))  MM_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.MAPPING_DAY  )))  MAPPING_DAY_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.REPT_GBN)))  REPT_GBN_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.USE_AMT  )))  MM_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.MAPPING_DAY  )))  MAPPING_DAY_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.REPT_GBN)))  REPT_GBN_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.USE_AMT  )))  MM_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.MAPPING_DAY  )))  MAPPING_DAY_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.REPT_GBN)))  REPT_GBN_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.USE_AMT  )))  MM_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.MAPPING_DAY  )))  MAPPING_DAY_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.REPT_GBN)))  REPT_GBN_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.USE_AMT  )))  MM_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.MAPPING_DAY  )))  MAPPING_DAY_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.REPT_GBN)))  REPT_GBN_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.USE_AMT  )))  MM_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.MAPPING_DAY  )))  MAPPING_DAY_12  FROM  (   \n";
        query +=" SELECT  TA.NOTE_YRMN  ,  TA.UPSO_CD  ,  TA.USE_AMT  ,  TA.MAPPING_DAY  ,  DECODE(TA.ACCU_GBN,  '22',  '고소',  TB.CODE_NM)  REPT_GBN  FROM  GIBU.TBRA_NOTE  TA  ,  (   \n";
        query +=" SELECT  CODE_CD  ,  DECODE(CODE_CD,  '01',  '자동',  '02',  'MICR',  '03',  '무통',  '04',  'OCR',  '05',  '카드',  CODE_NM)  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'  )  TB  WHERE  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TA.NOTE_YRMN  BETWEEN  :BB_YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  TA.REPT_GBN  =  TB.CODE_CD  )  A  GROUP  BY  A.UPSO_CD  )  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  '경)'  ||  A.MNGEMSTR_NM  MNGEMSTR_NM  ,  DECODE(A.MNGEMSTR_NM,  A.PERMMSTR_NM,  NULL,  '허)'  ||  A.PERMMSTR_NM)  PERMMSTR_NM  ,  DECODE(A.MNGEMSTR_NM,  A.PERMMSTR_NM,  NULL,  '/'  ||  A.PERMMSTR_RESINUM)  PERMMSTR_RESINUM  ,  DECODE(A.MNGEMSTR_RESINUM,  NULL,  '',  'O')  MNGEMSTR_RESINUM_OX  ,  A.BIOWN_NUM  ,  DECODE(A.BIOWN_NUM,  NULL,  '',  'O')  BIOWN_NUM_OX  ,  A.MNGEMSTR_RESINUM  ,  A.OPBI_DAY  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  INS_DAY  ,  GIBU.FT_GET_LAST_REPT_YRMN(A.UPSO_CD,  8)  REPT_LAST_DAY  ,  A.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  UPSO_ADDR  ,  A.UPSO_PHON  ,  A.MNGEMSTR_HPNUM  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  ,  B.GRADNM  ,  B.REMAK_1  ,  B.REMAK_2  ,  B.MONPRNCFEE  ,  B.B_MONPRNCFEE  ,  A.MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  MNGEMSTR_ADDR  ,  DECODE(B.BSTYP_CD,  'o',  GIBU.FT_GET_NOREBANG_GRAD(A.UPSO_CD),  NULL)  MCH_CNT  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  SUBSTR(A.OPBI_DAY,  1,  6),  :DEMD_YRMN,  :DEMD_YRMN,  'O')  DEMDS  ,  (   \n";
        query +=" SELECT  MAX(ACCU_DAY)  ACCU_DAY  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  ACCU_DAY  ,  (   \n";
        query +=" SELECT  MAX(BPAP_DAY)  BPAP_DAY  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  WHERE  UPSO_CD  =  A.UPSO_CD  )  BPAP_DAY  ,  DECODE(A.OPBI_DAY,  B.CHG_DAY,  NULL,  B.CHG_DAY)  CHG_DAY  ,  A.UPSO_BD_MNG_NUM  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  MAX(DECODE(ZA.RNO,  1,  ZA.BSTYP_CD))  BSTYP_CD  ,  MAX(DECODE(ZA.RNO,  1,  ZA.UPSO_GRAD))  UPSO_GRAD  ,  MAX(DECODE(ZA.RNO,  1,  ZB.GRADNM))  GRADNM  ,  MAX(DECODE(ZA.RNO,  1,  ZA.MONPRNCFEE))  MONPRNCFEE  ,  MAX(DECODE(ZA.RNO,  2,  ZA.MONPRNCFEE))  B_MONPRNCFEE  ,  MAX(DECODE(ZA.RNO,  2,  ZA.CHG_DAY))  CHG_DAY  ,  MAX(DECODE(ZC.RNO,  1,  ZC.REMAK))  REMAK_1  ,  MAX(DECODE(ZC.RNO,  2,  ZC.REMAK))  REMAK_2  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.BSTYP_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.CHG_DAY  ,  DENSE_RANK()  OVER(PARTITION  BY  A.UPSO_CD  ORDER  BY  A.JOIN_SEQ  DESC)  RNO  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD  )  ZA  ,  GIBU.TBRA_BSTYPGRAD  ZB  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.REMAK  ,  DENSE_RANK()  OVER(PARTITION  BY  A.UPSO_CD  ORDER  BY  A.VISIT_DAY  DESC,  A.VISIT_SEQ  DESC)  RNO  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOB_GBN  IN  ('V','P','T','E','3','4','M')   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD  )  ZC  WHERE  ZB.BSTYP_CD  =  ZA.BSTYP_CD   \n";
        query +=" AND  ZB.GRAD_GBN  =  ZA.UPSO_GRAD   \n";
        query +=" AND  ZA.RNO  <=  2   \n";
        query +=" AND  ZC.UPSO_CD(+)  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.RNO(+)  <=  2  GROUP  BY  ZA.UPSO_CD  )  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  NVL(A.STAFF_CD,  '  ')  LIKE  :STAFF_CD  ||  '%'   \n";
        query +=" AND  A.UPSO_NEW_ZIP  BETWEEN  :START_ZIP   \n";
        query +=" AND  :END_ZIP   \n";
        query +=" AND  A.UPSO_CD  LIKE  :UPSO_CD  ||  '%'  )  XB  WHERE  XA.UPSO_CD(+)  =  XB.UPSO_CD  ORDER  BY  XB.UPSO_BD_MNG_NUM,  XB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setDouble("START_ZIP", START_ZIP);               //시작 우편번호
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setDouble("END_ZIP", END_ZIP);               //종료 우편번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("B_YEAR", B_YEAR);               //작년
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BB_YEAR", BB_YEAR);               //재작년
        return sobj;
    }
    // 지부 징수내역 조회
    public DOBJ CALLbran_note_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_note_select_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_note_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dobj.getRetObject("SEL1").getRecord().get("YEAR");   //검색년도
        double   START_ZIP = dobj.getRetObject("S").getRecord().getDouble("START_ZIP");   //시작 우편번호
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        double   END_ZIP = dobj.getRetObject("S").getRecord().getDouble("END_ZIP");   //종료 우편번호
        String   B_YEAR = dobj.getRetObject("SEL1").getRecord().get("B_YEAR");   //작년
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   BB_YEAR = dobj.getRetObject("SEL1").getRecord().get("BB_YEAR");   //재작년
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.UPSO_CD  ,  XB.B_MONPRNCFEE  ,  XB.MONPRNCFEE  ,  XB.UPSO_NM  ,  XB.MNGEMSTR_NM  ,  XB.MNGEMSTR_RESINUM  ,  XB.MCH_CNT  ,  XB.UPSO_ADDR  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.GRADNM  ,  XB.UPSO_PHON  ,  XB.UPSO_ZIP  ,  XB.OPBI_DAY  ,  XA.BB_REPT_GBN_01  ,  XA.BB_MM_01  ,  XA.BB_REPT_GBN_02  ,  XA.BB_MM_02  ,  XA.BB_REPT_GBN_03  ,  XA.BB_MM_03  ,  XA.BB_REPT_GBN_04  ,  XA.BB_MM_04  ,  XA.BB_REPT_GBN_05  ,  XA.BB_MM_05  ,  XA.BB_REPT_GBN_06  ,  XA.BB_MM_06  ,  XA.BB_REPT_GBN_07  ,  XA.BB_MM_07  ,  XA.BB_REPT_GBN_08  ,  XA.BB_MM_08  ,  XA.BB_REPT_GBN_09  ,  XA.BB_MM_09  ,  XA.BB_REPT_GBN_10  ,  XA.BB_MM_10  ,  XA.BB_REPT_GBN_11  ,  XA.BB_MM_11  ,  XA.BB_REPT_GBN_12  ,  XA.BB_MM_12  ,  XA.B_REPT_GBN_01  ,  XA.B_MM_01  ,  XA.B_REPT_GBN_02  ,  XA.B_MM_02  ,  XA.B_REPT_GBN_03  ,  XA.B_MM_03  ,  XA.B_REPT_GBN_04  ,  XA.B_MM_04  ,  XA.B_REPT_GBN_05  ,  XA.B_MM_05  ,  XA.B_REPT_GBN_06  ,  XA.B_MM_06  ,  XA.B_REPT_GBN_07  ,  XA.B_MM_07  ,  XA.B_REPT_GBN_08  ,  XA.B_MM_08  ,  XA.B_REPT_GBN_09  ,  XA.B_MM_09  ,  XA.B_REPT_GBN_10  ,  XA.B_MM_10  ,  XA.B_REPT_GBN_11  ,  XA.B_MM_11  ,  XA.B_REPT_GBN_12  ,  XA.B_MM_12  ,  XA.REPT_GBN_01  ,  XA.MM_01  ,  XA.REPT_GBN_02  ,  XA.MM_02  ,  XA.REPT_GBN_03  ,  XA.MM_03  ,  XA.REPT_GBN_04  ,  XA.MM_04  ,  XA.REPT_GBN_05  ,  XA.MM_05  ,  XA.REPT_GBN_06  ,  XA.MM_06  ,  XA.REPT_GBN_07  ,  XA.MM_07  ,  XA.REPT_GBN_08  ,  XA.MM_08  ,  XA.REPT_GBN_09  ,  XA.MM_09  ,  XA.REPT_GBN_10  ,  XA.MM_10  ,  XA.REPT_GBN_11  ,  XA.MM_11  ,  XA.REPT_GBN_12  ,  XA.MM_12  ,  XB.BPAP_DAY  ,  XB.ACCU_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.USE_AMT  ,  NULL)))  BB_MM_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.USE_AMT  ,  NULL)))  BB_MM_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.USE_AMT  ,  NULL)))  BB_MM_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.USE_AMT  ,  NULL)))  BB_MM_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.USE_AMT  ,  NULL)))  BB_MM_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.USE_AMT  ,  NULL)))  BB_MM_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.USE_AMT  ,  NULL)))  BB_MM_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.USE_AMT  ,  NULL)))  BB_MM_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.USE_AMT  ,  NULL)))  BB_MM_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.USE_AMT  ,  NULL)))  BB_MM_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.USE_AMT  ,  NULL)))  BB_MM_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.REPT_GBN,  NULL)))  BB_REPT_GBN_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :BB_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.USE_AMT  ,  NULL)))  BB_MM_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.REPT_GBN,  NULL)))  B_REPT_GBN_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.USE_AMT  ,  NULL)))  B_MM_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.REPT_GBN,  NULL)))  B_REPT_GBN_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.USE_AMT  ,  NULL)))  B_MM_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.REPT_GBN,  NULL)))  B_REPT_GBN_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.USE_AMT  ,  NULL)))  B_MM_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.REPT_GBN,  NULL)))  B_REPT_GBN_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.USE_AMT  ,  NULL)))  B_MM_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.REPT_GBN,  NULL)))  B_REPT_GBN_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.USE_AMT  ,  NULL)))  B_MM_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.REPT_GBN,  NULL)))  B_REPT_GBN_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.USE_AMT  ,  NULL)))  B_MM_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.REPT_GBN,  NULL)))  B_REPT_GBN_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.USE_AMT  ,  NULL)))  B_MM_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.REPT_GBN,  NULL)))  B_REPT_GBN_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.USE_AMT  ,  NULL)))  B_MM_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.REPT_GBN,  NULL)))  B_REPT_GBN_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.USE_AMT  ,  NULL)))  B_MM_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.REPT_GBN,  NULL)))  B_REPT_GBN_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.USE_AMT  ,  NULL)))  B_MM_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.REPT_GBN,  NULL)))  B_REPT_GBN_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.USE_AMT  ,  NULL)))  B_MM_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.REPT_GBN,  NULL)))  B_REPT_GBN_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :B_YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.USE_AMT  ,  NULL)))  B_MM_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.REPT_GBN,  NULL)))  REPT_GBN_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '01',  A.USE_AMT  ,  NULL)))  MM_01  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.REPT_GBN,  NULL)))  REPT_GBN_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '02',  A.USE_AMT  ,  NULL)))  MM_02  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.REPT_GBN,  NULL)))  REPT_GBN_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '03',  A.USE_AMT  ,  NULL)))  MM_03  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.REPT_GBN,  NULL)))  REPT_GBN_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '04',  A.USE_AMT  ,  NULL)))  MM_04  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.REPT_GBN,  NULL)))  REPT_GBN_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '05',  A.USE_AMT  ,  NULL)))  MM_05  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.REPT_GBN,  NULL)))  REPT_GBN_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '06',  A.USE_AMT  ,  NULL)))  MM_06  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.REPT_GBN,  NULL)))  REPT_GBN_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '07',  A.USE_AMT  ,  NULL)))  MM_07  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.REPT_GBN,  NULL)))  REPT_GBN_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '08',  A.USE_AMT  ,  NULL)))  MM_08  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.REPT_GBN,  NULL)))  REPT_GBN_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '09',  A.USE_AMT  ,  NULL)))  MM_09  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.REPT_GBN,  NULL)))  REPT_GBN_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '10',  A.USE_AMT  ,  NULL)))  MM_10  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.REPT_GBN,  NULL)))  REPT_GBN_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '11',  A.USE_AMT  ,  NULL)))  MM_11  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.REPT_GBN,  NULL)))  REPT_GBN_12  ,  MAX(DECODE(SUBSTR(A.NOTE_YRMN,  1,  4),  :YEAR,  DECODE(SUBSTR(A.NOTE_YRMN,  5,  2),  '12',  A.USE_AMT  ,  NULL)))  MM_12  FROM  (   \n";
        query +=" SELECT  TA.NOTE_YRMN  ,  TA.UPSO_CD  ,  TA.USE_AMT  ,  DECODE(TA.ACCU_GBN,  '22',  '고소',  TB.CODE_NM)  REPT_GBN  FROM  GIBU.TBRA_NOTE  TA  ,  (   \n";
        query +=" SELECT  CODE_CD  ,  DECODE(CODE_CD,  '01',  '자동',  '02',  'MICR',  '03',  '무통',  '04',  'OCR',  '05',  '카드',  CODE_NM)  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'  )  TB  WHERE  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TA.NOTE_YRMN  BETWEEN  :BB_YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  TA.REPT_GBN  =  TB.CODE_CD  )  A  GROUP  BY  A.UPSO_CD  )  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.MNGEMSTR_NM  ,  A.MNGEMSTR_RESINUM  ,  A.OPBI_DAY  ,  A.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  UPSO_ADDR  ,  A.UPSO_PHON  ,  A.MNGEMSTR_HPNUM  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  ,  B.GRADNM  ,  B.MONPRNCFEE  ,  B.B_MONPRNCFEE  ,  MNGEMSTR_NEW_ADDR1  ||  '  '  ||  MNGEMSTR_NEW_ADDR2  ||  MNGEMSTR_REF_INFO  AS  MNGEMSTR_ADDR  ,  DECODE(B.BSTYP_CD,  'o',  GIBU.FT_GET_NOREBANG_GRAD(A.UPSO_CD),  NULL)  MCH_CNT  ,  (   \n";
        query +=" SELECT  MAX(ACCU_DAY)  ACCU_DAY  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  ACCU_DAY  ,  (   \n";
        query +=" SELECT  MAX(BPAP_DAY)  BPAP_DAY  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  WHERE  UPSO_CD  =  A.UPSO_CD  )  BPAP_DAY  ,  A.UPSO_BD_MNG_NUM  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  MAX(DECODE(ZA.RNO,  1,  ZA.BSTYP_CD))  BSTYP_CD  ,  MAX(DECODE(ZA.RNO,  1,  ZA.UPSO_GRAD))  UPSO_GRAD  ,  MAX(DECODE(ZA.RNO,  1,  ZB.GRADNM))  GRADNM  ,  MAX(DECODE(ZA.RNO,  1,  ZA.MONPRNCFEE))  MONPRNCFEE  ,  MAX(DECODE(ZA.RNO,  2,  ZA.MONPRNCFEE))  B_MONPRNCFEE  ,  MAX(DECODE(ZA.RNO,  2,  ZA.CHG_DAY))  CHG_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.BSTYP_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.CHG_DAY  ,  DENSE_RANK()  OVER(PARTITION  BY  A.UPSO_CD  ORDER  BY  A.JOIN_SEQ  DESC)  RNO  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD  )  ZA  ,  GIBU.TBRA_BSTYPGRAD  ZB  WHERE  ZB.BSTYP_CD  =  ZA.BSTYP_CD   \n";
        query +=" AND  ZB.GRAD_GBN  =  ZA.UPSO_GRAD   \n";
        query +=" AND  ZA.RNO  <=  2  GROUP  BY  ZA.UPSO_CD  )  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  NVL(A.STAFF_CD,  '  ')  LIKE  :STAFF_CD  ||  '%'   \n";
        query +=" AND  A.UPSO_NEW_ZIP  BETWEEN  :START_ZIP   \n";
        query +=" AND  :END_ZIP   \n";
        query +=" AND  A.UPSO_CD  LIKE  :UPSO_CD  ||  '%'  )  XB  WHERE  XA.UPSO_CD(+)  =  XB.UPSO_CD  ORDER  BY  XB.UPSO_BD_MNG_NUM,  XB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setDouble("START_ZIP", START_ZIP);               //시작 우편번호
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setDouble("END_ZIP", END_ZIP);               //종료 우편번호
        sobj.setString("B_YEAR", B_YEAR);               //작년
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BB_YEAR", BB_YEAR);               //재작년
        return sobj;
    }
    //##**$$bran_note_select
    //##**$$end
}
