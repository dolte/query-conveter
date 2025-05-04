
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_p08
{
    public bra04_p08()
    {
    }
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
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BSTYP_NONPY_PRCON_HISTORY  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$chk_is_history
    //##**$$sel_history_list
    /*
    */
    public DOBJ CTLsel_history_list(DOBJ dobj)
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
            dobj  = CALLsel_history_list_SEL1(Conn, dobj);           //  이력조회
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
    public DOBJ CTLsel_history_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_history_list_SEL1(Conn, dobj);           //  이력조회
        return dobj;
    }
    // 이력조회
    public DOBJ CALLsel_history_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_history_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_history_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PRCON_YRMN  ,  BRAN_CD  ,  FIDU.GET_GIBU_NM(BRAN_CD)  AS  BRAN_NM  ,  INSPRES_ID  ,  FIDU.GET_STAFF_NM(INSPRES_ID)  AS  INSPRES_NM  ,  INS_DATE  FROM  GIBU.TBRA_BSTYP_NONPY_PRCON_HISTORY  ORDER  BY  PRCON_YRMN  DESC,  BRAN_CD  ASC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$sel_history_list
    //##**$$save_nonpy_history
    /*
    */
    public DOBJ CTLsave_nonpy_history(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("BRAN_CD").equals("AL"))
            {
                dobj  = CALLsave_nonpy_history_DEL12(Conn, dobj);           //  기존에 저장된 이력 삭제
                dobj  = CALLsave_nonpy_history_DEL13(Conn, dobj);           //  기존에 저장된 현황 삭제
                dobj  = CALLsave_nonpy_history_SEL14(Conn, dobj);           //  전체지부선택
                dobj  = CALLsave_nonpy_history_MPD15(Conn, dobj);           //  지부별분기
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLsave_nonpy_history_DEL5(Conn, dobj);           //  기존에 저장된 이력 삭제
                dobj  = CALLsave_nonpy_history_DEL6(Conn, dobj);           //  기존에 저장된 현황 삭제
                dobj  = CALLsave_nonpy_history_INS7(Conn, dobj);           //  이력등록
                dobj  = CALLsave_nonpy_history_SEL1(Conn, dobj);           //  업종별미징수통계
                dobj  = CALLsave_nonpy_history_INS20(Conn, dobj);           //  현황데이터저장
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
    public DOBJ CTLsave_nonpy_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("BRAN_CD").equals("AL"))
        {
            dobj  = CALLsave_nonpy_history_DEL12(Conn, dobj);           //  기존에 저장된 이력 삭제
            dobj  = CALLsave_nonpy_history_DEL13(Conn, dobj);           //  기존에 저장된 현황 삭제
            dobj  = CALLsave_nonpy_history_SEL14(Conn, dobj);           //  전체지부선택
            dobj  = CALLsave_nonpy_history_MPD15(Conn, dobj);           //  지부별분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLsave_nonpy_history_DEL5(Conn, dobj);           //  기존에 저장된 이력 삭제
            dobj  = CALLsave_nonpy_history_DEL6(Conn, dobj);           //  기존에 저장된 현황 삭제
            dobj  = CALLsave_nonpy_history_INS7(Conn, dobj);           //  이력등록
            dobj  = CALLsave_nonpy_history_SEL1(Conn, dobj);           //  업종별미징수통계
            dobj  = CALLsave_nonpy_history_INS20(Conn, dobj);           //  현황데이터저장
        }
        return dobj;
    }
    // 기존에 저장된 이력 삭제
    public DOBJ CALLsave_nonpy_history_DEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_nonpy_history_DEL12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_DEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dvobj.getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_BSTYP_NONPY_PRCON_HISTORY  \n";
        query +=" where PRCON_YRMN=:PRCON_YRMN";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 기존에 저장된 현황 삭제
    public DOBJ CALLsave_nonpy_history_DEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_nonpy_history_DEL13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_DEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dvobj.getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BSTYP_NONPY_PRCON  \n";
        query +=" where PRCON_YRMN=:PRCON_YRMN";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 전체지부선택
    public DOBJ CALLsave_nonpy_history_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_nonpy_history_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU  AS  BRAN_CD  ,  BIPLC_SNM  AS  BRAN_NM  FROM  INSA.TCPM_BIPLC  WHERE  GIBU  IS  NOT  NULL   \n";
        query +=" AND  USE_YN  =  'Y' ";
        sobj.setSql(query);
        return sobj;
    }
    // 지부별분기
    public DOBJ CALLsave_nonpy_history_MPD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD15");
        VOBJ dvobj = dobj.getRetObject("SEL14");         //전체지부선택에서 생성시킨 OBJECT입니다.(CALLsave_nonpy_history_SEL14)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_nonpy_history_INS17(Conn, dobj);           //  이력등록
                dobj  = CALLsave_nonpy_history_SEL18(Conn, dobj);           //  업종별미징수통계
                dobj  = CALLsave_nonpy_history_INS19(Conn, dobj);           //  현황데이터저장
            }
        }
        return dobj;
    }
    // 이력등록
    public DOBJ CALLsave_nonpy_history_INS17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS17");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_nonpy_history_INS17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_INS17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BSTYP_NONPY_PRCON_HISTORY (INS_DATE, INSPRES_ID, PRCON_YRMN, BRAN_CD)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :PRCON_YRMN , :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 업종별미징수통계
    public DOBJ CALLsave_nonpy_history_SEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL18");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL18");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_nonpy_history_SEL18(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_SEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //시작 월수
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TG.GRADNM  ,  NVL(TA.CNT,  0)  CNT1  ,  TA.MONPRNCFEE  ,  NVL(TB.NONPY_CNT,  0)  NONPY_CNT1  ,  NVL(TB.NONPY_AMT,  0)  NONPY_AMT1  ,  NVL(TC.CNT,  0)  CNT2  ,  NVL(TD.NONPY_CNT,  0)  NONPY_CNT2  ,  NVL(TD.NONPY_AMT,  0)  NONPY_AMT2  ,  NVL(TE.CNT,  0)  CNT3  ,  NVL(TF.NONPY_CNT,  0)  NONPY_CNT3  ,  NVL(TF.NONPY_AMT,  0)  NONPY_AMT3  ,  TG.GRAD_GBN  FROM  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XA.NEW_DAY  <  :YRMN  ||  '32'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD  GROUP  BY  XB.BSTYP_CD  )  TA  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_STAT  =  '1'  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  TB  ,  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >=  :YRMN)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <  :YRMN  ||  '32'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD  GROUP  BY  XB.BSTYP_CD  )  TC  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  TD  ,  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.UPSO_STAT  =  '1'  GROUP  BY  XB.BSTYP_CD  )  TE  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_STAT  =  '1'  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  TF  ,  GIBU.TBRA_BSTYPGRAD  TG  WHERE  TG.BSTYP_CD  =  'z'   \n";
        query +=" AND  TA.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TB.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TC.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TD.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TE.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TF.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TA.CNT  >  0 ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 현황데이터저장
    public DOBJ CALLsave_nonpy_history_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("SEL18");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_nonpy_history_INS19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NONPY_CNT3 = dvobj.getRecord().get("NONPY_CNT3");
        String   NONPY_AMT2 = dvobj.getRecord().get("NONPY_AMT2");
        String   CNT1 = dvobj.getRecord().get("CNT1");
        String   NONPY_AMT1 = dvobj.getRecord().get("NONPY_AMT1");
        String   NONPY_CNT1 = dvobj.getRecord().get("NONPY_CNT1");
        String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //등급 구분
        String   GRADNM = dvobj.getRecord().get("GRADNM");   //등급명
        String   CNT3 = dvobj.getRecord().get("CNT3");
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //월정료
        String   NONPY_CNT2 = dvobj.getRecord().get("NONPY_CNT2");
        String   NONPY_AMT3 = dvobj.getRecord().get("NONPY_AMT3");
        String   CNT2 = dvobj.getRecord().get("CNT2");
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //지부 코드
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BSTYP_NONPY_PRCON (CNT2, NONPY_AMT3, NONPY_CNT2, MONPRNCFEE, CNT3, GRADNM, GRAD_GBN, NONPY_CNT1, NONPY_AMT1, CNT1, NONPY_AMT2, PRCON_YRMN, BRAN_CD, NONPY_CNT3)  \n";
        query +=" values(:CNT2 , :NONPY_AMT3 , :NONPY_CNT2 , :MONPRNCFEE , :CNT3 , :GRADNM , :GRAD_GBN , :NONPY_CNT1 , :NONPY_AMT1 , :CNT1 , :NONPY_AMT2 , :PRCON_YRMN , :BRAN_CD , :NONPY_CNT3 )";
        sobj.setSql(query);
        sobj.setString("NONPY_CNT3", NONPY_CNT3);
        sobj.setString("NONPY_AMT2", NONPY_AMT2);
        sobj.setString("CNT1", CNT1);
        sobj.setString("NONPY_AMT1", NONPY_AMT1);
        sobj.setString("NONPY_CNT1", NONPY_CNT1);
        sobj.setString("GRAD_GBN", GRAD_GBN);               //등급 구분
        sobj.setString("GRADNM", GRADNM);               //등급명
        sobj.setString("CNT3", CNT3);
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setString("NONPY_CNT2", NONPY_CNT2);
        sobj.setString("NONPY_AMT3", NONPY_AMT3);
        sobj.setString("CNT2", CNT2);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 기존에 저장된 이력 삭제
    public DOBJ CALLsave_nonpy_history_DEL5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_nonpy_history_DEL5(dobj, dvobj);
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
    private SQLObject SQLsave_nonpy_history_DEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   PRCON_YRMN = dvobj.getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_BSTYP_NONPY_PRCON_HISTORY  \n";
        query +=" where PRCON_YRMN=:PRCON_YRMN  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 기존에 저장된 현황 삭제
    public DOBJ CALLsave_nonpy_history_DEL6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_nonpy_history_DEL6(dobj, dvobj);
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
    private SQLObject SQLsave_nonpy_history_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   PRCON_YRMN = dvobj.getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BSTYP_NONPY_PRCON  \n";
        query +=" where PRCON_YRMN=:PRCON_YRMN  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 이력등록
    public DOBJ CALLsave_nonpy_history_INS7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_nonpy_history_INS7(dobj, dvobj);
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
    private SQLObject SQLsave_nonpy_history_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   PRCON_YRMN = dvobj.getRecord().get("PRCON_YRMN");   //현황 년월
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BSTYP_NONPY_PRCON_HISTORY (INS_DATE, INSPRES_ID, PRCON_YRMN, BRAN_CD)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :PRCON_YRMN , :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 업종별미징수통계
    public DOBJ CALLsave_nonpy_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_nonpy_history_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //시작 월수
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TG.GRADNM  ,  NVL(TA.CNT,  0)  CNT1  ,  TA.MONPRNCFEE  ,  NVL(TB.NONPY_CNT,  0)  NONPY_CNT1  ,  NVL(TB.NONPY_AMT,  0)  NONPY_AMT1  ,  NVL(TC.CNT,  0)  CNT2  ,  NVL(TD.NONPY_CNT,  0)  NONPY_CNT2  ,  NVL(TD.NONPY_AMT,  0)  NONPY_AMT2  ,  NVL(TE.CNT,  0)  CNT3  ,  NVL(TF.NONPY_CNT,  0)  NONPY_CNT3  ,  NVL(TF.NONPY_AMT,  0)  NONPY_AMT3  ,  TG.GRAD_GBN  FROM  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XA.NEW_DAY  <  :YRMN  ||  '32'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD  GROUP  BY  XB.BSTYP_CD  )  TA  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_STAT  =  '1'  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  TB  ,  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >=  :YRMN)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <  :YRMN  ||  '32'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD  GROUP  BY  XB.BSTYP_CD  )  TC  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  TD  ,  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.UPSO_STAT  =  '1'  GROUP  BY  XB.BSTYP_CD  )  TE  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_STAT  =  '1'  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  TF  ,  GIBU.TBRA_BSTYPGRAD  TG  WHERE  TG.BSTYP_CD  =  'z'   \n";
        query +=" AND  TA.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TB.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TC.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TD.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TE.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TF.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TA.CNT  >  0 ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 현황데이터저장
    public DOBJ CALLsave_nonpy_history_INS20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS20");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //업종별미징수통계에서 생성시킨 OBJECT입니다.(CALLsave_nonpy_history_SEL1)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_nonpy_history_INS20(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS20") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_INS20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NONPY_CNT3 = dvobj.getRecord().get("NONPY_CNT3");
        String   NONPY_AMT2 = dvobj.getRecord().get("NONPY_AMT2");
        String   CNT1 = dvobj.getRecord().get("CNT1");
        String   NONPY_AMT1 = dvobj.getRecord().get("NONPY_AMT1");
        String   NONPY_CNT1 = dvobj.getRecord().get("NONPY_CNT1");
        String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //등급 구분
        String   GRADNM = dvobj.getRecord().get("GRADNM");   //등급명
        String   CNT3 = dvobj.getRecord().get("CNT3");
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //월정료
        String   NONPY_CNT2 = dvobj.getRecord().get("NONPY_CNT2");
        String   NONPY_AMT3 = dvobj.getRecord().get("NONPY_AMT3");
        String   CNT2 = dvobj.getRecord().get("CNT2");
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BSTYP_NONPY_PRCON (CNT2, NONPY_AMT3, NONPY_CNT2, MONPRNCFEE, CNT3, GRADNM, GRAD_GBN, NONPY_CNT1, NONPY_AMT1, CNT1, NONPY_AMT2, PRCON_YRMN, BRAN_CD, NONPY_CNT3)  \n";
        query +=" values(:CNT2 , :NONPY_AMT3 , :NONPY_CNT2 , :MONPRNCFEE , :CNT3 , :GRADNM , :GRAD_GBN , :NONPY_CNT1 , :NONPY_AMT1 , :CNT1 , :NONPY_AMT2 , :PRCON_YRMN , :BRAN_CD , :NONPY_CNT3 )";
        sobj.setSql(query);
        sobj.setString("NONPY_CNT3", NONPY_CNT3);
        sobj.setString("NONPY_AMT2", NONPY_AMT2);
        sobj.setString("CNT1", CNT1);
        sobj.setString("NONPY_AMT1", NONPY_AMT1);
        sobj.setString("NONPY_CNT1", NONPY_CNT1);
        sobj.setString("GRAD_GBN", GRAD_GBN);               //등급 구분
        sobj.setString("GRADNM", GRADNM);               //등급명
        sobj.setString("CNT3", CNT3);
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setString("NONPY_CNT2", NONPY_CNT2);
        sobj.setString("NONPY_AMT3", NONPY_AMT3);
        sobj.setString("CNT2", CNT2);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    //##**$$save_nonpy_history
    //##**$$sel_nonpy_history
    /*
    */
    public DOBJ CTLsel_nonpy_history(DOBJ dobj)
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
            dobj  = CALLsel_nonpy_history_SEL1(Conn, dobj);           //  현황조회
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
    public DOBJ CTLsel_nonpy_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_nonpy_history_SEL1(Conn, dobj);           //  현황조회
        return dobj;
    }
    // 현황조회
    public DOBJ CALLsel_nonpy_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_nonpy_history_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_nonpy_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  PRCON_YRMN  ,  GRADNM  ,  CNT1  ,  MONPRNCFEE  ,  NONPY_CNT1  ,  NONPY_AMT1  ,  CNT2  ,  NONPY_CNT2  ,  NONPY_AMT2  ,  CNT3  ,  NONPY_CNT3  ,  NONPY_AMT3  ,  GRAD_GBN  FROM  GIBU.TBRA_BSTYP_NONPY_PRCON  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD) ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sel_nonpy_history
    //##**$$end
}
