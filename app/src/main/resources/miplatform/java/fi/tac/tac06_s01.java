
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac06_s01
{
    public tac06_s01()
    {
    }
    //##**$$checkDup_mem
    /*
    */
    public DOBJ CTLcheckDup_mem(DOBJ dobj)
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
            dobj  = CALLcheckDup_mem_SEL1(Conn, dobj);           //  중복조회
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
    public DOBJ CTLcheckDup_mem( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcheckDup_mem_SEL1(Conn, dobj);           //  중복조회
        return dobj;
    }
    // 중복조회
    public DOBJ CALLcheckDup_mem_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcheckDup_mem_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcheckDup_mem_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //종료조건
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //시작조건
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TMEM_SUPPSUSP  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN   \n";
        query +=" AND  (SUPPSUSP_START_DAY  BETWEEN  :FROM   \n";
        query +=" AND  :TO   \n";
        query +=" OR  SUPPSUSP_RELS_DAY  BETWEEN  :FROM   \n";
        query +=" AND  :TO   \n";
        query +=" OR  :FROM  BETWEEN  SUPPSUSP_START_DAY   \n";
        query +=" AND  SUPPSUSP_RELS_DAY   \n";
        query +=" OR  :TO  BETWEEN  SUPPSUSP_START_DAY   \n";
        query +=" AND  SUPPSUSP_RELS_DAY) ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TO", TO);               //종료조건
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("FROM", FROM);               //시작조건
        return sobj;
    }
    //##**$$checkDup_mem
    //##**$$checkDup_mdm
    /*
    */
    public DOBJ CTLcheckDup_mdm(DOBJ dobj)
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
            dobj  = CALLcheckDup_mdm_SEL1(Conn, dobj);           //  중복조회
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
    public DOBJ CTLcheckDup_mdm( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcheckDup_mdm_SEL1(Conn, dobj);           //  중복조회
        return dobj;
    }
    // 중복조회
    public DOBJ CALLcheckDup_mdm_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcheckDup_mdm_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcheckDup_mdm_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //종료조건
        String   CLASS_CD = dobj.getRetObject("S").getRecord().get("CLASS_CD");   //분류 코드
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //시작조건
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TTAC_MDMSUPPSUSP  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN   \n";
        query +=" AND  (CLASS_CD  LIKE  :CLASS_CD  ||  '%'   \n";
        query +=" OR  :CLASS_CD  LIKE  CLASS_CD  ||  '%')   \n";
        query +=" AND  (SUPPSUSP_START_DAY  BETWEEN  :FROM   \n";
        query +=" AND  :TO   \n";
        query +=" OR  SUPPSUSP_RELS_DAY  BETWEEN  :FROM   \n";
        query +=" AND  :TO   \n";
        query +=" OR  :FROM  BETWEEN  SUPPSUSP_START_DAY   \n";
        query +=" AND  SUPPSUSP_RELS_DAY   \n";
        query +=" OR  :TO  BETWEEN  SUPPSUSP_START_DAY   \n";
        query +=" AND  SUPPSUSP_RELS_DAY) ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TO", TO);               //종료조건
        sobj.setString("CLASS_CD", CLASS_CD);               //분류 코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("FROM", FROM);               //시작조건
        return sobj;
    }
    //##**$$checkDup_mdm
    //##**$$saveMst
    /* * 프로그램명 : tac06_s01
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsaveMst(DOBJ dobj)
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
            dobj  = CALLsaveMst_MIUD1(Conn, dobj);           //  회원지급보류분기
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
    public DOBJ CTLsaveMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveMst_MIUD1(Conn, dobj);           //  회원지급보류분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 회원지급보류분기
    public DOBJ CALLsaveMst_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst_INS5(Conn, dobj);           //  회원지급보류저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst_UPD6(Conn, dobj);           //  지급보류수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst_DEL7(Conn, dobj);           //  지급보류삭제
            }
        }
        return dobj;
    }
    // 지급보류삭제
    public DOBJ CALLsaveMst_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //관리번호
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TMEM_SUPPSUSP  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN";
        sobj.setSql(query);
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 회원지급보류저장
    public DOBJ CALLsaveMst_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double MNG_NUM = 0;        //관리번호
        String MOD_DATE ="";        //수정 일시
        String   SUPPSUSP_RELS_DAY = dvobj.getRecord().get("SUPPSUSP_RELS_DAY");   //지급보류 해제 일자
        String   SUPPSUSP_ORGMAN_CTENT = dvobj.getRecord().get("SUPPSUSP_ORGMAN_CTENT");   //지급보류 원인 내용
        String   SUPPSUSP_START_DAY = dvobj.getRecord().get("SUPPSUSP_START_DAY");   //지급보류 시작 일자
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TMEM_SUPPSUSP (MODPRES_ID, MB_CD, INS_DATE, INSPRES_ID, MNG_NUM, TRNSF_GBN, SUPPSUSP_START_DAY, MOD_DATE, SUPPSUSP_ORGMAN_CTENT, SUPPSUSP_RELS_DAY)  \n";
        query +=" values(:MODPRES_ID , :MB_CD , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , (SELECT NVL(MAX(MNG_NUM),0) + 1 AS MNG_NUM from FIDU.TMEM_SUPPSUSP), :TRNSF_GBN , :SUPPSUSP_START_DAY , TO_CHAR(SYSDATE,'YYYYMMDD'), :SUPPSUSP_ORGMAN_CTENT , :SUPPSUSP_RELS_DAY )";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_RELS_DAY", SUPPSUSP_RELS_DAY);               //지급보류 해제 일자
        sobj.setString("SUPPSUSP_ORGMAN_CTENT", SUPPSUSP_ORGMAN_CTENT);               //지급보류 원인 내용
        sobj.setString("SUPPSUSP_START_DAY", SUPPSUSP_START_DAY);               //지급보류 시작 일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 지급보류수정
    public DOBJ CALLsaveMst_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPPSUSP_RELS_DAY = dvobj.getRecord().get("SUPPSUSP_RELS_DAY");   //지급보류 해제 일자
        String   SUPPSUSP_ORGMAN_CTENT = dvobj.getRecord().get("SUPPSUSP_ORGMAN_CTENT");   //지급보류 원인 내용
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //수정 일시
        String   SUPPSUSP_START_DAY = dvobj.getRecord().get("SUPPSUSP_START_DAY");   //지급보류 시작 일자
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //관리번호
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   INS_DATE = dvobj.getRecord().get("INS_DATE");   //등록 일시
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TMEM_SUPPSUSP  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , INS_DATE=:INS_DATE , INSPRES_ID=:INSPRES_ID , SUPPSUSP_START_DAY=:SUPPSUSP_START_DAY , MOD_DATE=:MOD_DATE ,  \n";
        query +=" SUPPSUSP_ORGMAN_CTENT=:SUPPSUSP_ORGMAN_CTENT , SUPPSUSP_RELS_DAY=:SUPPSUSP_RELS_DAY  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_RELS_DAY", SUPPSUSP_RELS_DAY);               //지급보류 해제 일자
        sobj.setString("SUPPSUSP_ORGMAN_CTENT", SUPPSUSP_ORGMAN_CTENT);               //지급보류 원인 내용
        sobj.setString("MOD_DATE", MOD_DATE);               //수정 일시
        sobj.setString("SUPPSUSP_START_DAY", SUPPSUSP_START_DAY);               //지급보류 시작 일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("INS_DATE", INS_DATE);               //등록 일시
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$saveMst
    //##**$$saveMst1
    /*
    */
    public DOBJ CTLsaveMst1(DOBJ dobj)
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
            dobj  = CALLsaveMst1_MIUD1(Conn, dobj);           //  회원지급보류분기
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
    public DOBJ CTLsaveMst1( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveMst1_MIUD1(Conn, dobj);           //  회원지급보류분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 회원지급보류분기
    public DOBJ CALLsaveMst1_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst1_INS5(Conn, dobj);           //  회원매체지급보류저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst1_UPD6(Conn, dobj);           //  회원매체지급보류수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst1_DEL7(Conn, dobj);           //  회원매체지급보류삭제
            }
        }
        return dobj;
    }
    // 회원매체지급보류삭제
    public DOBJ CALLsaveMst1_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst1_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst1_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //관리번호
        String   CLASS_CD = dvobj.getRecord().get("CLASS_CD");   //분류 코드
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_MDMSUPPSUSP  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and CLASS_CD=:CLASS_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN";
        sobj.setSql(query);
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        sobj.setString("CLASS_CD", CLASS_CD);               //분류 코드
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 회원매체지급보류저장
    public DOBJ CALLsaveMst1_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst1_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst1_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double MNG_NUM = 0;        //관리번호
        String MOD_DATE ="";        //수정 일시
        String   SUPPSUSP_RELS_DAY = dvobj.getRecord().get("SUPPSUSP_RELS_DAY");   //지급보류 해제 일자
        String   SUPPSUSP_ORGMAN_CTENT = dvobj.getRecord().get("SUPPSUSP_ORGMAN_CTENT");   //지급보류 원인 내용
        String   SUPPSUSP_START_DAY = dvobj.getRecord().get("SUPPSUSP_START_DAY");   //지급보류 시작 일자
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   CLASS_CD = dvobj.getRecord().get("CLASS_CD");   //분류 코드
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_MDMSUPPSUSP (MODPRES_ID, MB_CD, INS_DATE, INSPRES_ID, CLASS_CD, MNG_NUM, TRNSF_GBN, SUPPSUSP_START_DAY, MOD_DATE, SUPPSUSP_ORGMAN_CTENT, SUPPSUSP_RELS_DAY)  \n";
        query +=" values(:MODPRES_ID , :MB_CD , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , :CLASS_CD , (SELECT NVL(MAX(MNG_NUM),0) + 1 AS MNG_NUM from FIDU.TTAC_MDMSUPPSUSP), :TRNSF_GBN , :SUPPSUSP_START_DAY , TO_CHAR(SYSDATE,'YYYYMMDD'), :SUPPSUSP_ORGMAN_CTENT , :SUPPSUSP_RELS_DAY )";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_RELS_DAY", SUPPSUSP_RELS_DAY);               //지급보류 해제 일자
        sobj.setString("SUPPSUSP_ORGMAN_CTENT", SUPPSUSP_ORGMAN_CTENT);               //지급보류 원인 내용
        sobj.setString("SUPPSUSP_START_DAY", SUPPSUSP_START_DAY);               //지급보류 시작 일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("CLASS_CD", CLASS_CD);               //분류 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 회원매체지급보류수정
    public DOBJ CALLsaveMst1_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst1_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst1_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPPSUSP_RELS_DAY = dvobj.getRecord().get("SUPPSUSP_RELS_DAY");   //지급보류 해제 일자
        String   SUPPSUSP_ORGMAN_CTENT = dvobj.getRecord().get("SUPPSUSP_ORGMAN_CTENT");   //지급보류 원인 내용
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //수정 일시
        String   SUPPSUSP_START_DAY = dvobj.getRecord().get("SUPPSUSP_START_DAY");   //지급보류 시작 일자
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //관리번호
        String   CLASS_CD = dvobj.getRecord().get("CLASS_CD");   //분류 코드
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   INS_DATE = dvobj.getRecord().get("INS_DATE");   //등록 일시
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_MDMSUPPSUSP  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , INS_DATE=:INS_DATE , INSPRES_ID=:INSPRES_ID , SUPPSUSP_START_DAY=:SUPPSUSP_START_DAY , MOD_DATE=:MOD_DATE ,  \n";
        query +=" SUPPSUSP_ORGMAN_CTENT=:SUPPSUSP_ORGMAN_CTENT , SUPPSUSP_RELS_DAY=:SUPPSUSP_RELS_DAY  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and CLASS_CD=:CLASS_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_RELS_DAY", SUPPSUSP_RELS_DAY);               //지급보류 해제 일자
        sobj.setString("SUPPSUSP_ORGMAN_CTENT", SUPPSUSP_ORGMAN_CTENT);               //지급보류 원인 내용
        sobj.setString("MOD_DATE", MOD_DATE);               //수정 일시
        sobj.setString("SUPPSUSP_START_DAY", SUPPSUSP_START_DAY);               //지급보류 시작 일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        sobj.setString("CLASS_CD", CLASS_CD);               //분류 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("INS_DATE", INS_DATE);               //등록 일시
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$saveMst1
    //##**$$searchMst
    /* * 프로그램명 : tac06_s01
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchMst(DOBJ dobj)
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
            dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  회원지급보류조회
            dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  회원지급보류조회
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
    public DOBJ CTLsearchMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  회원지급보류조회
        dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  회원지급보류조회
        return dobj;
    }
    // 회원지급보류조회
    public DOBJ CALLsearchMst_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //종료조건
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //시작조건
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.MNG_NUM,  A.TRNSF_GBN,  A.SUPPSUSP_START_DAY,  A.SUPPSUSP_ORGMAN_CTENT,  A.SUPPSUSP_RELS_DAY,  B.HANMB_NM  FROM  FIDU.TMEM_SUPPSUSP  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD=B.MB_CD   \n";
        query +=" AND  A.SUPPSUSP_START_DAY  >=  :FROM   \n";
        query +=" AND  A.SUPPSUSP_RELS_DAY  <=  :TO   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD||'%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN||'%'  ORDER  BY  A.SUPPSUSP_START_DAY ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TO", TO);               //종료조건
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("FROM", FROM);               //시작조건
        return sobj;
    }
    // 회원지급보류조회
    public DOBJ CALLsearchMst_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //종료조건
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //시작조건
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.MNG_NUM,  A.TRNSF_GBN,  A.CLASS_CD,  SUBSTR(A.CLASS_CD,1,1)  AS  LARGECLASS_CD,  (CASE  WHEN  LENGTH(A.CLASS_CD)  =  1  THEN  '전체'  ELSE  FIDU.GET_MDM_NM_EX(A.CLASS_CD,  2)  END)  AS  AVGCLASS_CD,  A.SUPPSUSP_START_DAY,  A.SUPPSUSP_ORGMAN_CTENT,  A.SUPPSUSP_RELS_DAY,  B.HANMB_NM  FROM  FIDU.TTAC_MDMSUPPSUSP  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD=B.MB_CD   \n";
        query +=" AND  A.SUPPSUSP_START_DAY  >=  :FROM   \n";
        query +=" AND  A.SUPPSUSP_RELS_DAY  <=  :TO   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD||'%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN||'%'  ORDER  BY  A.SUPPSUSP_START_DAY ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TO", TO);               //종료조건
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("FROM", FROM);               //시작조건
        return sobj;
    }
    //##**$$searchMst
    //##**$$end
}
