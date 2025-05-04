
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra09_p09
{
    public bra09_p09()
    {
    }
    //##**$$staff_clct_save
    /*
    */
    public DOBJ CTLstaff_clct_save(DOBJ dobj)
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
            dobj  = CALLstaff_clct_save_DEL5(Conn, dobj);           //  저장이력삭제
            dobj  = CALLstaff_clct_save_DEL6(Conn, dobj);           //  저장된현황삭제
            dobj  = CALLstaff_clct_save_INS7(Conn, dobj);           //  지부관리팀이력등록
            dobj  = CALLstaff_clct_save_SEL1(Conn, dobj);           //  지부관리팀현황조회
            dobj  = CALLstaff_clct_save_INS2(Conn, dobj);           //  지부관리팀현황저장
            dobj  = CALLstaff_clct_save_SEL12(Conn, dobj);           //  지부코드조회
            dobj  = CALLstaff_clct_save_MPD10(Conn, dobj);           //  지부코드분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
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
    public DOBJ CTLstaff_clct_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_clct_save_DEL5(Conn, dobj);           //  저장이력삭제
        dobj  = CALLstaff_clct_save_DEL6(Conn, dobj);           //  저장된현황삭제
        dobj  = CALLstaff_clct_save_INS7(Conn, dobj);           //  지부관리팀이력등록
        dobj  = CALLstaff_clct_save_SEL1(Conn, dobj);           //  지부관리팀현황조회
        dobj  = CALLstaff_clct_save_INS2(Conn, dobj);           //  지부관리팀현황저장
        dobj  = CALLstaff_clct_save_SEL12(Conn, dobj);           //  지부코드조회
        dobj  = CALLstaff_clct_save_MPD10(Conn, dobj);           //  지부코드분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 저장이력삭제
    public DOBJ CALLstaff_clct_save_DEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_clct_save_DEL5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_save_DEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_STAFF_CLCT_PRCON_HISTORY  \n";
        query +=" where PRCON_YRMN=:PRCON_YRMN";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 저장된현황삭제
    public DOBJ CALLstaff_clct_save_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_clct_save_DEL6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_save_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_STAFF_CLCT_PRCON  \n";
        query +=" where PRCON_YRMN=:PRCON_YRMN";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 지부관리팀이력등록
    public DOBJ CALLstaff_clct_save_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_clct_save_INS7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_save_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BRAN_CD ="AL";   //지부 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //현황 년월
        double   SEQ = 1;   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_STAFF_CLCT_PRCON_HISTORY (INS_DATE, INSPRES_ID, PRCON_YRMN, SEQ, BRAN_CD)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :PRCON_YRMN , :SEQ , :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    // 지부관리팀현황조회
    public DOBJ CALLstaff_clct_save_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_clct_save_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_save_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD ="AL";   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_CD  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  ,  '1'  AS  GBN  ,  '0'  AS  YDN_GBN  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  RETURN_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN  ||  '%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NEW_DAY  <=  :  YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM,  STAFF_CD  UNION  ALL   \n";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  0  COL_AMT  ,  0  COL_UPSO_CNT  ,  STAFF_CD  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  ,  '2'  AS  GBN  ,  '0'  AS  YDN_GBN  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  (XA.NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(XA.NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.DEMD_YRMN(+)  <=  :YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM,  STAFF_CD  UNION  ALL   \n";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_CD  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  ,  '1'  AS  GBN  ,  '1'  AS  YDN_GBN  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  RETURN_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN  ||  '%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NEW_DAY  <=  :  YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(XA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM,  STAFF_CD  UNION  ALL   \n";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  0  COL_AMT  ,  0  COL_UPSO_CNT  ,  STAFF_CD  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  ,  '2'  AS  GBN  ,  '1'  AS  YDN_GBN  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(XA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')   \n";
        query +=" AND  (XA.NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(XA.NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.DEMD_YRMN(+)  <=  :YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM,  STAFF_CD  ORDER  BY  BRAN_CD,  STAFF_NM,  GBN ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 지부관리팀현황저장
    public DOBJ CALLstaff_clct_save_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //지부관리팀현황조회에서 생성시킨 OBJECT입니다.(CALLstaff_clct_save_SEL1)
        dvobj.Println("INS2");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_clct_save_INS2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_save_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //미납 금액
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BRAN_NM = dvobj.getRecord().get("BRAN_NM");   //지부 이름
        String   NONPY_CNT = dvobj.getRecord().get("NONPY_CNT");
        String   UPSO_CNT = dvobj.getRecord().get("UPSO_CNT");
        String   STAFF_AMT = dvobj.getRecord().get("STAFF_AMT");
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   STAFF_NM = dvobj.getRecord().get("STAFF_NM");   //사원 명
        String   COL_AMT = dvobj.getRecord().get("COL_AMT");
        String   MON_AMT = dvobj.getRecord().get("MON_AMT");
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   COL_UPSO_CNT = dvobj.getRecord().get("COL_UPSO_CNT");
        String   YDN_GBN = dvobj.getRecord().get("YDN_GBN");   //유단노 구분
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //현황 년월
        double   SEQ = 1;   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_STAFF_CLCT_PRCON (YDN_GBN, COL_UPSO_CNT, GBN, MON_AMT, COL_AMT, SEQ, STAFF_NM, STAFF_CD, STAFF_AMT, PRCON_YRMN, UPSO_CNT, NONPY_CNT, BRAN_NM, BRAN_CD, NONPY_AMT)  \n";
        query +=" values(:YDN_GBN , :COL_UPSO_CNT , :GBN , :MON_AMT , :COL_AMT , :SEQ , :STAFF_NM , :STAFF_CD , :STAFF_AMT , :PRCON_YRMN , :UPSO_CNT , :NONPY_CNT , :BRAN_NM , :BRAN_CD , :NONPY_AMT )";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //미납 금액
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BRAN_NM", BRAN_NM);               //지부 이름
        sobj.setString("NONPY_CNT", NONPY_CNT);
        sobj.setString("UPSO_CNT", UPSO_CNT);
        sobj.setString("STAFF_AMT", STAFF_AMT);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("STAFF_NM", STAFF_NM);               //사원 명
        sobj.setString("COL_AMT", COL_AMT);
        sobj.setString("MON_AMT", MON_AMT);
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("COL_UPSO_CNT", COL_UPSO_CNT);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    // 지부코드조회
    public DOBJ CALLstaff_clct_save_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_clct_save_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_save_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU  FROM  INSA.TCPM_BIPLC  WHERE  GIBU  IS  NOT  NULL   \n";
        query +=" AND  USE_YN  =  'Y' ";
        sobj.setSql(query);
        return sobj;
    }
    // 지부코드분기
    public DOBJ CALLstaff_clct_save_MPD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD10");
        VOBJ dvobj = dobj.getRetObject("SEL12");         //지부코드조회에서 생성시킨 OBJECT입니다.(CALLstaff_clct_save_SEL12)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstaff_clct_save_INS9(Conn, dobj);           //  지부이력등록
                dobj  = CALLstaff_clct_save_SEL14(Conn, dobj);           //  지부현황조회
                dobj  = CALLstaff_clct_save_INS15(Conn, dobj);           //  지부현황저장
            }
        }
        return dobj;
    }
    // 지부이력등록
    public DOBJ CALLstaff_clct_save_INS9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS9");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_clct_save_INS9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_save_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("GIBU");   //지부 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //현황 년월
        double   SEQ = 2;   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_STAFF_CLCT_PRCON_HISTORY (INS_DATE, INSPRES_ID, PRCON_YRMN, SEQ, BRAN_CD)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :PRCON_YRMN , :SEQ , :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    // 지부현황조회
    public DOBJ CALLstaff_clct_save_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_clct_save_SEL14(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_save_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("GIBU");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  GUGUN  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_CD  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  ,  '1'  AS  GBN  ,  '0'  AS  YDN_GBN  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  A.UPSO_BD_MNG_NUM))  GUGUN  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  RETURN_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN  ||  '%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NEW_DAY  <=  :  YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XA.UPSO_BD_MNG_NUM))  GUGUN  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k','l','o')  THEN  ZB.MONPRNCFEE2  -  (ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  GUGUN,  STAFF_NM,  STAFF_CD  UNION  ALL   \n";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  GUGUN  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  0  COL_AMT  ,  0  COL_UPSO_CNT  ,  STAFF_CD  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  ,  '2'  AS  GBN  ,  '0'  AS  YDN_GBN  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XA.UPSO_BD_MNG_NUM))  GUGUN  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k','l','o')  THEN  ZB.MONPRNCFEE2  -  (ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  (XA.NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(XA.NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.DEMD_YRMN(+)  <=  :YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  GUGUN,  STAFF_NM,  STAFF_CD  UNION  ALL   \n";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  GUGUN  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_CD  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  ,  '1'  AS  GBN  ,  '1'  AS  YDN_GBN  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  A.UPSO_BD_MNG_NUM))  GUGUN  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  RETURN_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN  ||  '%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NEW_DAY  <=  :  YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XA.UPSO_BD_MNG_NUM))  GUGUN  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k','l','o')  THEN  ZB.MONPRNCFEE2  -  (ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(XA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  GUGUN,  STAFF_NM,  STAFF_CD  UNION  ALL   \n";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  GUGUN  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  0  COL_AMT  ,  0  COL_UPSO_CNT  ,  STAFF_CD  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  ,  '2'  AS  GBN  ,  '1'  AS  YDN_GBN  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XA.UPSO_BD_MNG_NUM))  GUGUN  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k','l','o')  THEN  ZB.MONPRNCFEE2  -  (ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(XA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')   \n";
        query +=" AND  (XA.NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(XA.NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.DEMD_YRMN(+)  <=  :YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  GUGUN,  STAFF_NM,  STAFF_CD  ORDER  BY  BRAN_CD,  STAFF_NM,  GUGUN ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 지부현황저장
    public DOBJ CALLstaff_clct_save_INS15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS15");
        VOBJ dvobj = dobj.getRetObject("SEL14");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS15");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_clct_save_INS15(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS15") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_save_INS15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //미납 금액
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BRAN_NM = dvobj.getRecord().get("BRAN_NM");   //지부 이름
        String   NONPY_CNT = dvobj.getRecord().get("NONPY_CNT");
        String   UPSO_CNT = dvobj.getRecord().get("UPSO_CNT");
        String   STAFF_AMT = dvobj.getRecord().get("STAFF_AMT");
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   STAFF_NM = dvobj.getRecord().get("STAFF_NM");   //사원 명
        String   COL_AMT = dvobj.getRecord().get("COL_AMT");
        String   GUGUN = dvobj.getRecord().get("GUGUN");
        String   MON_AMT = dvobj.getRecord().get("MON_AMT");
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   COL_UPSO_CNT = dvobj.getRecord().get("COL_UPSO_CNT");
        String   YDN_GBN = dvobj.getRecord().get("YDN_GBN");   //유단노 구분
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //현황 년월
        double   SEQ = 2;   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_STAFF_CLCT_PRCON (YDN_GBN, COL_UPSO_CNT, GBN, MON_AMT, GUGUN, COL_AMT, SEQ, STAFF_NM, STAFF_CD, STAFF_AMT, PRCON_YRMN, UPSO_CNT, NONPY_CNT, BRAN_NM, BRAN_CD, NONPY_AMT)  \n";
        query +=" values(:YDN_GBN , :COL_UPSO_CNT , :GBN , :MON_AMT , :GUGUN , :COL_AMT , :SEQ , :STAFF_NM , :STAFF_CD , :STAFF_AMT , :PRCON_YRMN , :UPSO_CNT , :NONPY_CNT , :BRAN_NM , :BRAN_CD , :NONPY_AMT )";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //미납 금액
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BRAN_NM", BRAN_NM);               //지부 이름
        sobj.setString("NONPY_CNT", NONPY_CNT);
        sobj.setString("UPSO_CNT", UPSO_CNT);
        sobj.setString("STAFF_AMT", STAFF_AMT);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("STAFF_NM", STAFF_NM);               //사원 명
        sobj.setString("COL_AMT", COL_AMT);
        sobj.setString("GUGUN", GUGUN);
        sobj.setString("MON_AMT", MON_AMT);
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("COL_UPSO_CNT", COL_UPSO_CNT);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    //##**$$staff_clct_save
    //##**$$staff_collect_list_hist
    /*
    */
    public DOBJ CTLstaff_collect_list_hist(DOBJ dobj)
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
            dobj  = CALLstaff_collect_list_hist_SEL6(Conn, dobj);           //  널
            if( dobj.getRetObject("S").getRecord().getDouble("SEQ") == 1)
            {
                dobj  = CALLstaff_collect_list_hist_SEL4(Conn, dobj);           //  현황조회
            }
            else
            {
                dobj  = CALLstaff_collect_list_hist_SEL1(Conn, dobj);           //  현황조회
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
    public DOBJ CTLstaff_collect_list_hist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_collect_list_hist_SEL6(Conn, dobj);           //  널
        if( dobj.getRetObject("S").getRecord().getDouble("SEQ") == 1)
        {
            dobj  = CALLstaff_collect_list_hist_SEL4(Conn, dobj);           //  현황조회
        }
        else
        {
            dobj  = CALLstaff_collect_list_hist_SEL1(Conn, dobj);           //  현황조회
        }
        return dobj;
    }
    // 널
    public DOBJ CALLstaff_collect_list_hist_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_collect_list_hist_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_hist_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  1  AS  CNT  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 현황조회
    public DOBJ CALLstaff_collect_list_hist_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_collect_list_hist_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_hist_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YDN_GBN = dobj.getRetObject("S").getRecord().get("YDN_GBN");   //유단노 구분
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        double   SEQ = dobj.getRetObject("S").getRecord().getDouble("SEQ");   //관리번호
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PRCON_YRMN  ,  SEQ  ,  BRAN_CD  ,  BRAN_NM  ,  SUM(MON_AMT)  AS  MON_AMT  ,  SUM(UPSO_CNT)  AS  UPSO_CNT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  ,  SUM(NONPY_CNT)  AS  NONPY_CNT  ,  SUM(COL_AMT)  AS  COL_AMT  ,  SUM(COL_UPSO_CNT)  AS  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(STAFF_AMT)  AS  STAFF_AMT  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  PRCON_YRMN  =  :YRMN   \n";
        query +=" AND  SEQ  =  :SEQ   \n";
        query +=" AND  GBN  =  DECODE(:GBN,  '1',  '1',  '2',  '2',  GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  '',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  YDN_GBN  =  :YDN_GBN  GROUP  BY  PRCON_YRMN,  SEQ,  BRAN_CD,  STAFF_NM,  BRAN_NM ";
        sobj.setSql(query);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("GBN", GBN);               //구분
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 현황조회
    public DOBJ CALLstaff_collect_list_hist_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_collect_list_hist_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_hist_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YDN_GBN = dobj.getRetObject("S").getRecord().get("YDN_GBN");   //유단노 구분
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        double   SEQ = dobj.getRetObject("S").getRecord().getDouble("SEQ");   //관리번호
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PRCON_YRMN  ,  SEQ  ,  BRAN_CD  ,  BRAN_NM  ,  GUGUN  ,  SUM(MON_AMT)  AS  MON_AMT  ,  SUM(UPSO_CNT)  AS  UPSO_CNT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  ,  SUM(NONPY_CNT)  AS  NONPY_CNT  ,  SUM(COL_AMT)  AS  COL_AMT  ,  SUM(COL_UPSO_CNT)  AS  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(STAFF_AMT)  AS  STAFF_AMT  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  PRCON_YRMN  =  :YRMN   \n";
        query +=" AND  SEQ  =  :SEQ   \n";
        query +=" AND  GBN  =  DECODE(:GBN,  '1',  '1',  '2',  '2',  GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  '',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  YDN_GBN  =  :YDN_GBN  GROUP  BY  PRCON_YRMN,  SEQ,  BRAN_CD,  STAFF_NM,  BRAN_NM,  GUGUN ";
        sobj.setSql(query);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("GBN", GBN);               //구분
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$staff_collect_list_hist
    //##**$$staff_collect_list_al
    /*
    */
    public DOBJ CTLstaff_collect_list_al(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLstaff_collect_list_al_SEL2(Conn, dobj);           //  담당별 합계(관리)
                dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  담당별합계결과취합
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLstaff_collect_list_al_SEL7(Conn, dobj);           //  담당별 합계(개발)
                dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  담당별합계결과취합
            }
            else
            {
                dobj  = CALLstaff_collect_list_al_SEL9(Conn, dobj);           //  담당별 합계(전체)
                dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  담당별합계결과취합
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
    public DOBJ CTLstaff_collect_list_al( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLstaff_collect_list_al_SEL2(Conn, dobj);           //  담당별 합계(관리)
            dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  담당별합계결과취합
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLstaff_collect_list_al_SEL7(Conn, dobj);           //  담당별 합계(개발)
            dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  담당별합계결과취합
        }
        else
        {
            dobj  = CALLstaff_collect_list_al_SEL9(Conn, dobj);           //  담당별 합계(전체)
            dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  담당별합계결과취합
        }
        return dobj;
    }
    // 담당별 합계(관리)
    public DOBJ CALLstaff_collect_list_al_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_collect_list_al_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_al_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YDN_GBN = dobj.getRetObject("S").getRecord().get("YDN_GBN");   //유단노 구분
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  UNION  ALL   \n";
        query +=" SELECT  RETURN_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN  ||  '%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NEW_DAY  <=  :  YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(XA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM  ORDER  BY  BRAN_CD,  STAFF_NM ";
        sobj.setSql(query);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 담당별합계결과취합
    public DOBJ CALLstaff_collect_list_al_MRG2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL2, SEL7, SEL9","BRAN_CD, BRAN_NM, MON_AMT, UPSO_CNT, NONPY_AMT, NONPY_CNT, COL_AMT, COL_UPSO_CNT,  STAFF_NM, STAFF_AMT");
        rvobj.setName("MRG2") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 담당별 합계(개발)
    public DOBJ CALLstaff_collect_list_al_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_collect_list_al_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_al_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YDN_GBN = dobj.getRetObject("S").getRecord().get("YDN_GBN");   //유단노 구분
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  0  COL_AMT  ,  0  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(XA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  (XA.NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(XA.NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.DEMD_YRMN(+)  <=  :YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM  ORDER  BY  BRAN_CD,  STAFF_NM ";
        sobj.setSql(query);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 담당별 합계(전체)
    public DOBJ CALLstaff_collect_list_al_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_collect_list_al_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_al_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YDN_GBN = dobj.getRetObject("S").getRecord().get("YDN_GBN");   //유단노 구분
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  UNION  ALL   \n";
        query +=" SELECT  RETURN_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN  ||  '%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  (A.NEW_DAY  IS  NULL   \n";
        query +=" OR  A.NEW_DAY  <=  :YRMN||'31')   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  TA.BRAN_CD  ,  TA.UPSO_CD  ,  MAX(TC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(TD.STAFF_NUM)  STAFF_CD  ,  MAX(TD.HAN_NM)  STAFF_NM  ,  SUM(NVL(TB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TINS_MST01  TD  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(TA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  (TA.NEW_DAY  IS  NULL   \n";
        query +=" OR  TA.NEW_DAY  <=  :YRMN||'31')   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  TB.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TB.DEMD_YRMN(+)  <  =:YRMN   \n";
        query +=" AND  TD.STAFF_NUM(+)  =  TA.STAFF_CD  GROUP  BY  TA.BRAN_CD,  TA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM  ORDER  BY  BRAN_CD,  STAFF_NM ";
        sobj.setSql(query);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$staff_collect_list_al
    //##**$$staff_collect_list_gibu
    /*
    */
    public DOBJ CTLstaff_collect_list_gibu(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLstaff_collect_list_gibu_SEL1(Conn, dobj);           //  지역담당자징수현황(관리)
                dobj  = CALLstaff_collect_list_gibu_MRG1( dobj);        //  리스트항목결과취합
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLstaff_collect_list_gibu_SEL6(Conn, dobj);           //  지역담당자징수현황(개발)
                dobj  = CALLstaff_collect_list_gibu_MRG1( dobj);        //  리스트항목결과취합
            }
            else
            {
                dobj  = CALLstaff_collect_list_gibu_SEL8(Conn, dobj);           //  지역담당자징수현황(전체)
                dobj  = CALLstaff_collect_list_gibu_MRG1( dobj);        //  리스트항목결과취합
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
    public DOBJ CTLstaff_collect_list_gibu( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLstaff_collect_list_gibu_SEL1(Conn, dobj);           //  지역담당자징수현황(관리)
            dobj  = CALLstaff_collect_list_gibu_MRG1( dobj);        //  리스트항목결과취합
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLstaff_collect_list_gibu_SEL6(Conn, dobj);           //  지역담당자징수현황(개발)
            dobj  = CALLstaff_collect_list_gibu_MRG1( dobj);        //  리스트항목결과취합
        }
        else
        {
            dobj  = CALLstaff_collect_list_gibu_SEL8(Conn, dobj);           //  지역담당자징수현황(전체)
            dobj  = CALLstaff_collect_list_gibu_MRG1( dobj);        //  리스트항목결과취합
        }
        return dobj;
    }
    // 지역담당자징수현황(관리)
    public DOBJ CALLstaff_collect_list_gibu_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_collect_list_gibu_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_gibu_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YDN_GBN = dobj.getRetObject("S").getRecord().get("YDN_GBN");   //유단노 구분
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  GUGUN  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  A.UPSO_BD_MNG_NUM))  GUGUN  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  UNION  ALL   \n";
        query +=" SELECT  RETURN_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN  ||  '%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NEW_DAY  <=  :  YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XA.UPSO_BD_MNG_NUM))  GUGUN  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(XA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  GUGUN,  STAFF_NM  ORDER  BY  BRAN_CD,  STAFF_NM,  GUGUN ";
        sobj.setSql(query);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 리스트항목결과취합
    public DOBJ CALLstaff_collect_list_gibu_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL6, SEL8","GUGUN, MON_AMT, UPSO_CNT, NONPY_AMT, NONPY_CNT, COL_AMT, COL_UPSO_CNT, STAFF_NM, STAFF_AMT, BRAN_CD, BRAN_NM");
        rvobj.setName("MRG1") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 지역담당자징수현황(개발)
    public DOBJ CALLstaff_collect_list_gibu_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_collect_list_gibu_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_gibu_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YDN_GBN = dobj.getRetObject("S").getRecord().get("YDN_GBN");   //유단노 구분
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  GUGUN  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  0  COL_AMT  ,  0  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XA.UPSO_BD_MNG_NUM))  GUGUN  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(XA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  (XA.NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(XA.NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.DEMD_YRMN(+)  <=  :YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  GUGUN,  STAFF_NM  ORDER  BY  BRAN_CD,  STAFF_NM,  GUGUN ";
        sobj.setSql(query);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 지역담당자징수현황(전체)
    public DOBJ CALLstaff_collect_list_gibu_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_collect_list_gibu_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_gibu_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YDN_GBN = dobj.getRetObject("S").getRecord().get("YDN_GBN");   //유단노 구분
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  GUGUN  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  A.UPSO_BD_MNG_NUM))  GUGUN  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  UNION  ALL   \n";
        query +=" SELECT  RETURN_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN  ||  '%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  *  -1  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  PROC_AMT  ,  0  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  (A.NEW_DAY  IS  NULL   \n";
        query +=" OR  A.NEW_DAY  <=  :YRMN||'31')   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  TA.BRAN_CD  ,  TA.UPSO_CD  ,  MAX(TC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(TD.STAFF_NUM)  STAFF_CD  ,  MAX(TD.HAN_NM)  STAFF_NM  ,  MAX((SELECT  ATTE  ||  '  '  ||  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  TA.UPSO_BD_MNG_NUM))  GUGUN  ,  SUM(NVL(TB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :YRMN)  >  0  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TINS_MST01  TD  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ((:YDN_GBN  =  '1'   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_INFO(TA.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p'))   \n";
        query +=" OR  :YDN_GBN  =  '0')   \n";
        query +=" AND  (TA.NEW_DAY  IS  NULL   \n";
        query +=" OR  TA.NEW_DAY  <=  :YRMN||'31')   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  TB.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TB.DEMD_YRMN(+)  <  =:YRMN   \n";
        query +=" AND  TD.STAFF_NUM(+)  =  TA.STAFF_CD  GROUP  BY  TA.BRAN_CD,  TA.UPSO_CD  )  GROUP  BY  BRAN_CD,  GUGUN,  STAFF_NM  ORDER  BY  BRAN_CD,  STAFF_NM,  GUGUN ";
        sobj.setSql(query);
        sobj.setString("YDN_GBN", YDN_GBN);               //유단노 구분
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$staff_collect_list_gibu
    //##**$$chk_is_history
    /*
    */
    public DOBJ CTLchk_is_history(DOBJ dobj)
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
            dobj  = CALLchk_is_history_SEL1(Conn, dobj);           //  저장이력존재여부확인
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
    public DOBJ CTLchk_is_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_is_history_SEL1(Conn, dobj);           //  저장이력존재여부확인
        return dobj;
    }
    // 저장이력존재여부확인
    public DOBJ CALLchk_is_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_is_history_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_is_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_STAFF_CLCT_PRCON_HISTORY  WHERE  PRCON_YRMN  =  :PRCON_YRMN ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    //##**$$chk_is_history
    //##**$$staff_clct_history
    /*
    */
    public DOBJ CTLstaff_clct_history(DOBJ dobj)
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
            dobj  = CALLstaff_clct_history_SEL1(Conn, dobj);           //  이력 조회
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
    public DOBJ CTLstaff_clct_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_clct_history_SEL1(Conn, dobj);           //  이력 조회
        return dobj;
    }
    // 이력 조회
    public DOBJ CALLstaff_clct_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_clct_history_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_clct_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PRCON_YRMN  ,  DECODE(SEQ,  1,  '지부관리팀',   \n";
        query +=" (SELECT  BIPLC_SNM  FROM  INSA.TCPM_BIPLC  WHERE  GIBU  =  A.BRAN_CD))  AS  BRAN_NM  ,  SEQ  ,  BRAN_CD  ,  INSPRES_ID  ,  FIDU.GET_STAFF_NM(INSPRES_ID)  AS  INSPRES_NM  ,  INS_DATE  FROM  GIBU.TBRA_STAFF_CLCT_PRCON_HISTORY  A  ORDER  BY  PRCON_YRMN  DESC,  SEQ,  BRAN_CD,  INS_DATE  DESC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$staff_clct_history
    //##**$$end
}
