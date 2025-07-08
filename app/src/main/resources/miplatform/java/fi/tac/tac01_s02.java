
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_s02
{
    public tac01_s02()
    {
    }
    //##**$$createBill01tac_s02
    /* * 프로그램명 : tac01_s02
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLcreateBill01tac_s02(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("3"))
            {
                dobj  = CALLcreateBill01tac_s02_XIUD7(Conn, dobj);           //  계산서 삭제
                dobj  = CALLcreateBill01tac_s02_XIUD9(Conn, dobj);           //  사용승인내역 삭제
                dobj  = CALLcreateBill01tac_s02_SEL25(Conn, dobj);           //  계산서 쿼리1
                dobj  = CALLcreateBill01tac_s02_MPD26(Conn, dobj);           //  분기
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("4"))
            {
                dobj  = CALLcreateBill01tac_s02_XIUD20(Conn, dobj);           //  지부계산서 삭제
                dobj  = CALLcreateBill01tac_s02_XIUD19(Conn, dobj);           //  지부계산서 상세 삭제
                dobj  = CALLcreateBill01tac_s02_XIUD21(Conn, dobj);           //  지부쪽 초기화
                dobj  = CALLcreateBill01tac_s02_SEL2(Conn, dobj);           //  계산서 쿼리1
                dobj  = CALLcreateBill01tac_s02_MPD7(Conn, dobj);           //  분기
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLcreateBill01tac_s02( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("3"))
        {
            dobj  = CALLcreateBill01tac_s02_XIUD7(Conn, dobj);           //  계산서 삭제
            dobj  = CALLcreateBill01tac_s02_XIUD9(Conn, dobj);           //  사용승인내역 삭제
            dobj  = CALLcreateBill01tac_s02_SEL25(Conn, dobj);           //  계산서 쿼리1
            dobj  = CALLcreateBill01tac_s02_MPD26(Conn, dobj);           //  분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("4"))
        {
            dobj  = CALLcreateBill01tac_s02_XIUD20(Conn, dobj);           //  지부계산서 삭제
            dobj  = CALLcreateBill01tac_s02_XIUD19(Conn, dobj);           //  지부계산서 상세 삭제
            dobj  = CALLcreateBill01tac_s02_XIUD21(Conn, dobj);           //  지부쪽 초기화
            dobj  = CALLcreateBill01tac_s02_SEL2(Conn, dobj);           //  계산서 쿼리1
            dobj  = CALLcreateBill01tac_s02_MPD7(Conn, dobj);           //  분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // 계산서 삭제
    public DOBJ CALLcreateBill01tac_s02_XIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD7");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_XIUD7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD7");
        rvobj.Println("XIUD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_BILL  \n";
        query +=" WHERE SUBSTR(ISS_DAY,1,6) LIKE SUBSTR(:DEMD_DAY,1,6)||'%'  \n";
        query +=" AND BILL_KND = '3'";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    // 사용승인내역 삭제
    public DOBJ CALLcreateBill01tac_s02_XIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD9");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_XIUD9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD9");
        rvobj.Println("XIUD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_USEAPPRVBRE  \n";
        query +=" WHERE SUBSTR(ISS_DAY,1,6) LIKE SUBSTR(:DEMD_DAY,1,6)||'%'  \n";
        query +=" AND BILL_KND = '3'";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    // 계산서 쿼리1
    public DOBJ CALLcreateBill01tac_s02_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_SEL25(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        rvobj.Println("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("S").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEMD_NUM,  BILL_KND,  BILL_GBN,  DEMD_DAY,  BRAN_CD,  BSCON_CD,  BSCON_NM,  BSCON_INS_NUM,  BSCON_FNM_NM,  BSCON_REPPRES_NM,  BSCON_POST_NUM,  BSCON_ADDR,  BSCON_ADDR_DETED,  BSCON_BSTYP_CTENT,  BSCON_BSCDTN_CTENT,  SUPPPRES_CD,  SUPPPRES_NM,  SUPPPRES_INS_NUM,  SUPPPRES_FNM_NM,  SUPPPRES_REPPRES_NM,  SUPPPRES_POST_NUM,  SUPPPRES_ADDR,  SUPPPRES_ADDR_DETED,  SUPPPRES_BSTYP_CTENT,  SUPPPRES_BSCDTN_CTENT,  (OCC_AMT  +  DEDCT_AMT)  OCC_AMT,  ATAX_AMT,  REMAK  ,  BIGO  FROM  (   \n";
        query +=" SELECT  distinct  A.SUPP_YRMN||'0000'  AS  DEMD_NUM,  '3'  AS  BILL_KND,  '1'  AS  BILL_GBN,  :DEMD_DAY  AS  DEMD_DAY,  A.SUPP_YRMN  AS  APPRV_NUM,  '001'  AS  BIPLC_GBN,  ''  AS  BRAN_CD,  1  AS  CLR_NUM,  A.MB_CD  AS  BSCON_CD,  B.HANMB_NM  AS  BSCON_NM,  B.INS_NUM  AS  BSCON_INS_NUM,  B.HANMB_NM  AS  BSCON_FNM_NM,  B.REPPRES_NM  AS  BSCON_REPPRES_NM,  D.POST_NUM  AS  BSCON_POST_NUM,  D.ADDR  AS  BSCON_ADDR,  D.ADDR_DETED  AS  BSCON_ADDR_DETED,  B.BSTYP_CTENT  AS  BSCON_BSTYP_CTENT,  B.BSCDTN_CTENT  AS  BSCON_BSCDTN_CTENT,  C.BSCON_CD  AS  SUPPPRES_CD,  C.BSCONHAN_NM  AS  SUPPPRES_NM,  C.INS_NUM  AS  SUPPPRES_INS_NUM,  C.BSCONHAN_NM  AS  SUPPPRES_FNM_NM,  C.REPPRES_NM  AS  SUPPPRES_REPPRES_NM,  C.POST_NUM  AS  SUPPPRES_POST_NUM,  C.ADDR  AS  SUPPPRES_ADDR,  C.ADDR_DETED  AS  SUPPPRES_ADDR_DETED,  C.BSTYP_CTENT  AS  SUPPPRES_BSTYP_CTENT,  C.BSCDTN_CTENT  AS  SUPPPRES_BSCDTN_CTENT,  A.TOT_MNGCOMIS_AMT  AS  OCC_AMT,  NVL(E.DEDCT_AMT,0)  DEDCT_AMT,  0  AS  ATAX_AMT,  DECODE(B.MB_GBN1,'P',SUBSTR(A.SUPP_YRMN,1,4)||'년  '||SUBSTR(A.SUPP_YRMN,5,2)||'월  관리수수료',  SUBSTR(A.SUPP_YRMN,1,4)||'년  '||SUBSTR(A.SUPP_YRMN,5,2)||'월  외국분  관리수수료')  AS  REMAK  ,  ''  AS  BIGO  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B,  FIDU.TLEV_BSCON  C,  FIDU.TMEM_ADBK  D,  (   \n";
        query +=" SELECT  A.MB_CD  MB_CD,TRNSF_GBN,  SUM(DEDCT_AMT)DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  SUPP_YRMN  =  SUBSTR(:DEMD_DAY,1,6)   \n";
        query +=" AND  DEDCT_GBNONE  ='01'   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  B.MB_GBN1  IN  ('P','F','B','C')   \n";
        query +=" AND  DEDCT_GBNTWO  ='08'  GROUP  BY  A.MB_CD,  TRNSF_GBN  )  E  WHERE  A.MB_CD  =  B.MB_CD(+)   \n";
        query +=" AND  C.BSCON_CD  =  'C0438'   \n";
        query +=" AND  A.MB_CD  =  E.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  E.TRNSF_GBN(+)   \n";
        query +=" AND  A.MB_CD  =  D.MB_CD(+)   \n";
        query +=" AND  D.ADDR_GBN(+)  =  '2'   \n";
        query +=" AND  A.MB_GBN  IN  ('P','F','B','C')   \n";
        query +=" AND  A.MB_CD  NOT  IN   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  ='00406')   \n";
        query +=" AND  A.SUPP_YRMN  =SUBSTR(:DEMD_DAY,1,6)  )  WHERE  1  =  1   \n";
        query +=" AND  BILL_KND  =  :BILL_KND   \n";
        query +=" AND  OCC_AMT  +  DEDCT_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  DEMD_NUM,  BILL_KND,  BILL_GBN,  DEMD_DAY,  BRAN_CD,  BSCON_CD,  BSCON_NM,  BSCON_INS_NUM,  BSCON_FNM_NM,  BSCON_REPPRES_NM,  BSCON_POST_NUM,  BSCON_ADDR,  BSCON_ADDR_DETED,  BSCON_BSTYP_CTENT,  BSCON_BSCDTN_CTENT,  SUPPPRES_CD,  SUPPPRES_NM,  SUPPPRES_INS_NUM,  SUPPPRES_FNM_NM,  SUPPPRES_REPPRES_NM,  SUPPPRES_POST_NUM,  SUPPPRES_ADDR,  SUPPPRES_ADDR_DETED,  SUPPPRES_BSTYP_CTENT,  SUPPPRES_BSCDTN_CTENT,  DEDCT_AMT  OCC_AMT,  ATAX_AMT,  REMAK  ,  BIGO  FROM  (   \n";
        query +=" SELECT  distinct  A.SUPP_YRMN||'0000'  AS  DEMD_NUM,  '3'  AS  BILL_KND,  '1'  AS  BILL_GBN,  :DEMD_DAY  AS  DEMD_DAY,  A.SUPP_YRMN  AS  APPRV_NUM,  '001'  AS  BIPLC_GBN,  ''  AS  BRAN_CD,  1  AS  CLR_NUM,  A.MB_CD  AS  BSCON_CD,  G.BSCONHAN_NM  AS  BSCON_NM,  G.INS_NUM  AS  BSCON_INS_NUM,  G.BSCONHAN_NM  AS  BSCON_FNM_NM,  G.REPPRES_NM  AS  BSCON_REPPRES_NM,  G.POST_NUM  AS  BSCON_POST_NUM,  G.ADDR  AS  BSCON_ADDR,  G.ADDR_DETED  AS  BSCON_ADDR_DETED,  G.BSTYP_CTENT  AS  BSCON_BSTYP_CTENT,  G.BSCDTN_CTENT  AS  BSCON_BSCDTN_CTENT,  C.BSCON_CD  AS  SUPPPRES_CD,  C.BSCONHAN_NM  AS  SUPPPRES_NM,  C.INS_NUM  AS  SUPPPRES_INS_NUM,  C.BSCONHAN_NM  AS  SUPPPRES_FNM_NM,  C.REPPRES_NM  AS  SUPPPRES_REPPRES_NM,  C.POST_NUM  AS  SUPPPRES_POST_NUM,  C.ADDR  AS  SUPPPRES_ADDR,  C.ADDR_DETED  AS  SUPPPRES_ADDR_DETED,  C.BSTYP_CTENT  AS  SUPPPRES_BSTYP_CTENT,  C.BSCDTN_CTENT  AS  SUPPPRES_BSCDTN_CTENT,  A.TOT_MNGCOMIS_AMT  AS  OCC_AMT,  NVL(E.DEDCT_AMT,0)  DEDCT_AMT,  0  AS  ATAX_AMT,  DECODE(B.MB_GBN1,'P',SUBSTR(A.SUPP_YRMN,1,4)||'년  '||SUBSTR(A.SUPP_YRMN,5,2)||'월  관리수수료',  SUBSTR(A.SUPP_YRMN,1,4)||'년  '||SUBSTR(A.SUPP_YRMN,5,2)||'월  관리수수료')  AS  REMAK  ,  ''  AS  BIGO  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B,  FIDU.TLEV_BSCON  C,  FIDU.TMEM_ADBK  D,  FIDU.TLEV_BSCON  G,  (   \n";
        query +=" SELECT  A.MB_CD  MB_CD,TRNSF_GBN,B.CODE_ETC,  SUM(DEDCT_AMT)DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TENV_CODE  B  WHERE  SUPP_YRMN  =  SUBSTR(:DEMD_DAY,1,6)   \n";
        query +=" AND  DEDCT_GBNONE  ='01'   \n";
        query +=" AND  B.USE_YN  ='Y'   \n";
        query +=" AND  A.MB_CD  =  B.CODE_NM   \n";
        query +=" AND  B.HIGH_CD  =  '00406'   \n";
        query +=" AND  DEDCT_GBNTWO  ='01'  GROUP  BY  A.MB_CD,  TRNSF_GBN,CODE_ETC  )  E  WHERE  A.MB_CD  =  B.MB_CD(+)   \n";
        query +=" AND  C.BSCON_CD  =  'C0438'   \n";
        query +=" AND  A.MB_CD  =  E.MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  =  E.TRNSF_GBN(+)   \n";
        query +=" AND  A.MB_CD  =  D.MB_CD(+)   \n";
        query +=" AND  D.ADDR_GBN(+)  =  '2'   \n";
        query +=" AND  E.CODE_ETC  =  G.BSCON_CD   \n";
        query +=" AND  A.SUPP_YRMN  =SUBSTR(:DEMD_DAY,1,6)  )  WHERE  1  =  1   \n";
        query +=" AND  BILL_KND  =  :BILL_KND   \n";
        query +=" AND  OCC_AMT  +  DEDCT_AMT  >  0  ORDER  BY  DEMD_NUM  ASC ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        return sobj;
    }
    // 분기
    public DOBJ CALLcreateBill01tac_s02_MPD26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD26");
        VOBJ dvobj = dobj.getRetObject("SEL25");         //계산서 쿼리1에서 생성시킨 OBJECT입니다.(CALLcreateBill01tac_s02_SEL25)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLcreateBill01tac_s02_SEL28(Conn, dobj);           //  계산서번호가 없을경우
                dobj  = CALLcreateBill01tac_s02_INS29(Conn, dobj);           //  계산서생성
                dobj  = CALLcreateBill01tac_s02_INS30(Conn, dobj);           //  사용승인내역저장
            }
        }
        return dobj;
    }
    // 계산서번호가 없을경우
    public DOBJ CALLcreateBill01tac_s02_SEL28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL28");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL28");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_SEL28(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL28");
        rvobj.Println("SEL28");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_SEL28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("R").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUBSTR(:DEMD_DAY,1,6)||  DECODE(:BILL_KND,3,'A',4,'G','')||  LPAD(NVL((   \n";
        query +=" SELECT  MAX(SUBSTR(BILL_NUM,8)+1)  FROM  FIDU.TTAC_BILL  WHERE  SUBSTR(BILL_NUM,1,6)  =  SUBSTR(:DEMD_DAY,1,6)   \n";
        query +=" AND  BILL_KND  =  :BILL_KND  ),1  ),3,'0'  )  AS  BILL_NUM  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        return sobj;
    }
    // 계산서생성
    public DOBJ CALLcreateBill01tac_s02_INS29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS29");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_INS29(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS29") ;
        rvobj.Println("INS29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_INS29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BSCON_FNM_NM = dvobj.getRecord().get("BSCON_FNM_NM");   //거래처 상호 명
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_REPPRES_NM = dvobj.getRecord().get("BSCON_REPPRES_NM");   //거래처 대표자 명
        String   SUPPPRES_INS_NUM = dvobj.getRecord().get("SUPPPRES_INS_NUM");   //공급자 등록 번호
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   SUPPPRES_NM = dvobj.getRecord().get("SUPPPRES_NM");   //공급자 명
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   SUPPPRES_REPPRES_NM = dvobj.getRecord().get("SUPPPRES_REPPRES_NM");   //공급자 대표자 명
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //거래처 명
        String   BSCON_INS_NUM = dvobj.getRecord().get("BSCON_INS_NUM");   //거래처 등록 번호
        String   SUPPPRES_CD = dvobj.getRecord().get("SUPPPRES_CD");   //공급자_코드
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //부가세 금액
        String   SUPPPRES_FNM_NM = dvobj.getRecord().get("SUPPPRES_FNM_NM");   //공급자 상호 명
        String   BSCON_POST_NUM = dvobj.getRecord().get("BSCON_POST_NUM");   //거래처 우편 번호
        String   BILL_NUM = dobj.getRetObject("SEL28").getRecord().get("BILL_NUM");   //계산서 번호
        String   BIPLC_GBN ="001";   //사업장 구분
        String   BSCON_ADDR = dobj.getRetObject("R").getRecord().get("BSCON_ADDR")+dobj.getRetObject("R").getRecord().get("BSCON_ADDR_DETED");   //거래처 주소
        String   BSCON_BSCDTN = dobj.getRetObject("R").getRecord().get("BSCON_BSCDTN_CTENT");   //거래처업태
        String   BSCON_BSTYP = dobj.getRetObject("R").getRecord().get("BSCON_BSTYP_CTENT");   //거래처업종
        String   DEMD_NUM = dobj.getRetObject("SEL28").getRecord().get("BILL_NUM");   //청구서번호
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        String   ISS_YN ="1";   //발행 유무
        String   REMAK = dobj.getRetObject("R").getRecord().get("BIGO");   //비고
        String   SUPPPRES_ADDR = dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR")+dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR_DETED");   //공급자 주소
        String   SUPPPRES_BSCDTN = dobj.getRetObject("R").getRecord().get("SUPPPRES_BSCDTN_CTENT");   //공급자_업태
        String   SUPPPRES_BSTYP = dobj.getRetObject("R").getRecord().get("SUPPPRES_BSTYP_CTENT");   //공급자업종
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //공급가 액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_BILL (ISS_YN, BSCON_POST_NUM, SUPPPRES_FNM_NM, BSCON_ADDR, ATAX_AMT, BIPLC_GBN, SUPPPRES_CD, DEMD_NUM, INS_DATE, BSCON_INS_NUM, BSCON_NM, SUPPPRES_BSTYP, BSCON_BSTYP, REMAK, BSCON_BSCDTN, SUPPPRES_REPPRES_NM, BILL_GBN, SUPPPRES_ADDR, SUPPPRES_NM, BSCON_CD, SUPPPRES_INS_NUM, BILL_NUM, ISS_DAY, SUPPTEMP_AMT, SUPPPRES_BSCDTN, BSCON_REPPRES_NM, BRAN_CD, BILL_KND, BSCON_FNM_NM)  \n";
        query +=" values(:ISS_YN , :BSCON_POST_NUM , :SUPPPRES_FNM_NM , :BSCON_ADDR , :ATAX_AMT , :BIPLC_GBN , :SUPPPRES_CD , :DEMD_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :BSCON_INS_NUM , :BSCON_NM , :SUPPPRES_BSTYP , :BSCON_BSTYP , :REMAK , :BSCON_BSCDTN , :SUPPPRES_REPPRES_NM , :BILL_GBN , :SUPPPRES_ADDR , :SUPPPRES_NM , :BSCON_CD , :SUPPPRES_INS_NUM , :BILL_NUM , :ISS_DAY , :SUPPTEMP_AMT , :SUPPPRES_BSCDTN , :BSCON_REPPRES_NM , :BRAN_CD , :BILL_KND , :BSCON_FNM_NM )";
        sobj.setSql(query);
        sobj.setString("BSCON_FNM_NM", BSCON_FNM_NM);               //거래처 상호 명
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BSCON_REPPRES_NM", BSCON_REPPRES_NM);               //거래처 대표자 명
        sobj.setString("SUPPPRES_INS_NUM", SUPPPRES_INS_NUM);               //공급자 등록 번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("SUPPPRES_NM", SUPPPRES_NM);               //공급자 명
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("SUPPPRES_REPPRES_NM", SUPPPRES_REPPRES_NM);               //공급자 대표자 명
        sobj.setString("BSCON_NM", BSCON_NM);               //거래처 명
        sobj.setString("BSCON_INS_NUM", BSCON_INS_NUM);               //거래처 등록 번호
        sobj.setString("SUPPPRES_CD", SUPPPRES_CD);               //공급자_코드
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setString("SUPPPRES_FNM_NM", SUPPPRES_FNM_NM);               //공급자 상호 명
        sobj.setString("BSCON_POST_NUM", BSCON_POST_NUM);               //거래처 우편 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("BIPLC_GBN", BIPLC_GBN);               //사업장 구분
        sobj.setString("BSCON_ADDR", BSCON_ADDR);               //거래처 주소
        sobj.setString("BSCON_BSCDTN", BSCON_BSCDTN);               //거래처업태
        sobj.setString("BSCON_BSTYP", BSCON_BSTYP);               //거래처업종
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("ISS_YN", ISS_YN);               //발행 유무
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("SUPPPRES_ADDR", SUPPPRES_ADDR);               //공급자 주소
        sobj.setString("SUPPPRES_BSCDTN", SUPPPRES_BSCDTN);               //공급자_업태
        sobj.setString("SUPPPRES_BSTYP", SUPPPRES_BSTYP);               //공급자업종
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        return sobj;
    }
    // 사용승인내역저장
    public DOBJ CALLcreateBill01tac_s02_INS30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS30");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_INS30(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS30") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_INS30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double CLR_NUM = 1;        //정산번호
        String DEMD_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");        //청구 일자
        String INS_DATE ="";        //등록 일시
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   CLR_NUM_1 = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //정산번호
        String   APPRV_NUM = dobj.getRetObject("R").getRecord().get("DEMD_DAY")+"001";   //승인 번호
        String   BILL_NUM = dobj.getRetObject("SEL28").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("SEL28").getRecord().get("BILL_NUM");   //청구서번호
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //공급가 액
        String   TITLE_NM = dobj.getRetObject("R").getRecord().get("REMAK");   //타이틀 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_USEAPPRVBRE (DEMD_NUM, INS_DATE, BILL_NUM, CLR_NUM, BILL_GBN, SUPPTEMP_AMT, ISS_DAY, TITLE_NM, BILL_KND, APPRV_NUM)  \n";
        query +=" values(:DEMD_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :BILL_NUM , (SELECT NVL(MAX(CLR_NUM),0) + 1 AS CLR_NUM from FIDU.TTAC_USEAPPRVBRE WHERE SUBSTR(ISS_DAY,1,6)=SUBSTR(:CLR_NUM_1,1,6)), :BILL_GBN , :SUPPTEMP_AMT , :ISS_DAY , :TITLE_NM , :BILL_KND , :APPRV_NUM )";
        sobj.setSql(query);
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("CLR_NUM_1", CLR_NUM_1);               //정산번호
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        sobj.setString("TITLE_NM", TITLE_NM);               //타이틀 명
        return sobj;
    }
    // 지부계산서 삭제
    public DOBJ CALLcreateBill01tac_s02_XIUD20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD20");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_XIUD20(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_XIUD20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  1  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 지부계산서 상세 삭제
    public DOBJ CALLcreateBill01tac_s02_XIUD19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD19");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_XIUD19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_XIUD19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  1  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 지부쪽 초기화
    public DOBJ CALLcreateBill01tac_s02_XIUD21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD21");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_XIUD21(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_XIUD21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  1  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 계산서 쿼리1
    public DOBJ CALLcreateBill01tac_s02_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("S").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEMD_NUM,  BILL_KND,  BILL_GBN,  DEMD_DAY,  BRAN_CD,  APPTN_YRMN,  UPSO_CD,  BSCON_CD,  BSCON_NM,  BSCON_INS_NUM,  BSCON_FNM_NM,  BSCON_REPPRES_NM,  BSCON_POST_NUM,  BSCON_ADDR,  BSCON_ADDR_DETED,  BSCON_BSTYP_CTENT  as  BSCON_BSTYP,  BSCON_BSCDTN_CTENT  as  BSCON_BSCDTN,  SUPPPRES_CD,  SUPPPRES_NM,  SUPPPRES_INS_NUM,  SUPPPRES_FNM_NM,  SUPPPRES_REPPRES_NM,  SUPPPRES_POST_NUM,  SUPPPRES_ADDR,  SUPPPRES_ADDR_DETED,  SUPPPRES_BSTYP_CTENT  as  SUPPPRES_BSTYP,  SUPPPRES_BSCDTN_CTENT  as  SUPPPRES_BSCDTN,  OCC_AMT,  ATAX_AMT,  REMAK  ,  BIGO,  SEQ  FROM  (   \n";
        query +=" SELECT  A.BSCON_CD  AS  DEMD_NUM,  '4'  AS  BILL_KND,  A.BILL_GBN  AS  BILL_GBN,  A.ISS_DAY  AS  DEMD_DAY,  A.APPRV_NUM  AS  APPRV_NUM,  '002'  AS  BIPLC_GBN,  1  AS  CLR_NUM,  A.BRAN_CD  AS  BRAN_CD,  A.APPTN_YRMN,  A.UPSO_CD,  A.BSCON_CD  AS  BSCON_CD,  B.BSCONHAN_NM  AS  BSCON_NM,  B.INS_NUM  AS  BSCON_INS_NUM,  B.BSCONHAN_NM  AS  BSCON_FNM_NM,  B.REPPRES_NM  AS  BSCON_REPPRES_NM,  B.POST_NUM  AS  BSCON_POST_NUM,  B.ADDR  AS  BSCON_ADDR,  B.ADDR_DETED  AS  BSCON_ADDR_DETED,  B.BSTYP_CTENT  AS  BSCON_BSTYP_CTENT,  B.BSCDTN_CTENT  AS  BSCON_BSCDTN_CTENT,  C.BSCON_CD  AS  SUPPPRES_CD,  C.BSCONHAN_NM  AS  SUPPPRES_NM,  C.INS_NUM  AS  SUPPPRES_INS_NUM,  C.BSCONHAN_NM  AS  SUPPPRES_FNM_NM,  C.REPPRES_NM  AS  SUPPPRES_REPPRES_NM,  C.POST_NUM  AS  SUPPPRES_POST_NUM,  C.ADDR  AS  SUPPPRES_ADDR,  C.ADDR_DETED  AS  SUPPPRES_ADDR_DETED,  C.BSTYP_CTENT  AS  SUPPPRES_BSTYP_CTENT,  C.BSCDTN_CTENT  AS  SUPPPRES_BSCDTN_CTENT,  ISS_AMT  AS  OCC_AMT,  0  AS  ATAX_AMT,  A.ISS_BRE  ||  '  ('||   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  A.BRAN_CD  =  GIBU)  ||')'  AS  REMAK  ,  A.REMAK  AS  BIGO,  A.SEQ  SEQ  FROM  GIBU.TBRA_BILL_ISS_MNG  A,  FIDU.TLEV_BSCON  B,  FIDU.TLEV_BSCON  C  WHERE  A.BSCON_CD  =  B.BSCON_CD   \n";
        query +=" AND  SUBSTR(A.APPTN_YRMN,1,6)  =  SUBSTR(:DEMD_DAY,1,6)   \n";
        query +=" AND  C.BSCON_CD  =  'C0438'   \n";
        query +=" AND  A.BSCON_CD  IS  NOT  NULL   \n";
        query +=" AND  A.BILL_NUM  IS  NULL   \n";
        query +=" AND  A.ISS_COMPL_YN  =  '1'  ORDER  BY  A.BRAN_CD  ,  A.INS_DATE  )  WHERE  1  =  1   \n";
        query +=" AND  BILL_KND  =  :BILL_KND ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        return sobj;
    }
    // 분기
    public DOBJ CALLcreateBill01tac_s02_MPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD7");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //계산서 쿼리1에서 생성시킨 OBJECT입니다.(CALLcreateBill01tac_s02_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLcreateBill01tac_s02_SEL20(Conn, dobj);           //  계산서번호생성
                dobj  = CALLcreateBill01tac_s02_INS18(Conn, dobj);           //  계산서생성
                dobj  = CALLcreateBill01tac_s02_INS19(Conn, dobj);           //  사용승인내역저장
                dobj  = CALLcreateBill01tac_s02_XIUD31(Conn, dobj);           //  지부계산서출력저장
            }
        }
        return dobj;
    }
    // 계산서번호생성
    public DOBJ CALLcreateBill01tac_s02_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_SEL20(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        rvobj.Println("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("R").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUBSTR(:DEMD_DAY,1,6)||  DECODE(:BILL_KND,3,'A',4,'G','')||  LPAD(NVL((   \n";
        query +=" SELECT  MAX(SUBSTR(BILL_NUM,8)+1)  FROM  FIDU.TTAC_BILL  WHERE  SUBSTR(BILL_NUM,1,6)  =  SUBSTR(:DEMD_DAY,1,6)   \n";
        query +=" AND  BILL_KND  =  :BILL_KND  ),1  ),3,'0'  )  AS  BILL_NUM  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        return sobj;
    }
    // 계산서생성
    public DOBJ CALLcreateBill01tac_s02_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_INS18(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS18") ;
        rvobj.Println("INS18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BSCON_FNM_NM = dvobj.getRecord().get("BSCON_FNM_NM");   //거래처 상호 명
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_REPPRES_NM = dvobj.getRecord().get("BSCON_REPPRES_NM");   //거래처 대표자 명
        String   SUPPPRES_BSCDTN = dvobj.getRecord().get("SUPPPRES_BSCDTN");   //공급자_업태
        String   SUPPPRES_INS_NUM = dvobj.getRecord().get("SUPPPRES_INS_NUM");   //공급자 등록 번호
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   SUPPPRES_NM = dvobj.getRecord().get("SUPPPRES_NM");   //공급자 명
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   SUPPPRES_REPPRES_NM = dvobj.getRecord().get("SUPPPRES_REPPRES_NM");   //공급자 대표자 명
        String   BSCON_BSCDTN = dvobj.getRecord().get("BSCON_BSCDTN");   //거래처업태
        String   BSCON_BSTYP = dvobj.getRecord().get("BSCON_BSTYP");   //거래처업종
        String   SUPPPRES_BSTYP = dvobj.getRecord().get("SUPPPRES_BSTYP");   //공급자업종
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //거래처 명
        String   BSCON_INS_NUM = dvobj.getRecord().get("BSCON_INS_NUM");   //거래처 등록 번호
        String   SUPPPRES_CD = dvobj.getRecord().get("SUPPPRES_CD");   //공급자_코드
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //부가세 금액
        String   SUPPPRES_FNM_NM = dvobj.getRecord().get("SUPPPRES_FNM_NM");   //공급자 상호 명
        String   BSCON_POST_NUM = dvobj.getRecord().get("BSCON_POST_NUM");   //거래처 우편 번호
        String   BILL_NUM = dobj.getRetObject("SEL20").getRecord().get("BILL_NUM");   //계산서 번호
        String   BIPLC_GBN ="001";   //사업장 구분
        String   BSCON_ADDR = dobj.getRetObject("R").getRecord().get("BSCON_ADDR")+dobj.getRetObject("R").getRecord().get("BSCON_ADDR_DETED");   //거래처 주소
        String   DEMD_NUM = dobj.getRetObject("SEL20").getRecord().get("BILL_NUM");   //청구서번호
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        String   ISS_YN ="1";   //발행 유무
        String   REMAK = dobj.getRetObject("R").getRecord().get("BIGO");   //비고
        String   SUPPPRES_ADDR = dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR")+dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR_DETED");   //공급자 주소
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //공급가 액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_BILL (ISS_YN, BSCON_POST_NUM, SUPPPRES_FNM_NM, BSCON_ADDR, ATAX_AMT, BIPLC_GBN, SUPPPRES_CD, DEMD_NUM, INS_DATE, BSCON_INS_NUM, BSCON_NM, SUPPPRES_BSTYP, BSCON_BSTYP, REMAK, BSCON_BSCDTN, SUPPPRES_REPPRES_NM, BILL_GBN, SUPPPRES_ADDR, SUPPPRES_NM, BSCON_CD, SUPPPRES_INS_NUM, BILL_NUM, ISS_DAY, SUPPTEMP_AMT, SUPPPRES_BSCDTN, BSCON_REPPRES_NM, BRAN_CD, BILL_KND, BSCON_FNM_NM)  \n";
        query +=" values(:ISS_YN , :BSCON_POST_NUM , :SUPPPRES_FNM_NM , :BSCON_ADDR , :ATAX_AMT , :BIPLC_GBN , :SUPPPRES_CD , :DEMD_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :BSCON_INS_NUM , :BSCON_NM , :SUPPPRES_BSTYP , :BSCON_BSTYP , :REMAK , :BSCON_BSCDTN , :SUPPPRES_REPPRES_NM , :BILL_GBN , :SUPPPRES_ADDR , :SUPPPRES_NM , :BSCON_CD , :SUPPPRES_INS_NUM , :BILL_NUM , :ISS_DAY , :SUPPTEMP_AMT , :SUPPPRES_BSCDTN , :BSCON_REPPRES_NM , :BRAN_CD , :BILL_KND , :BSCON_FNM_NM )";
        sobj.setSql(query);
        sobj.setString("BSCON_FNM_NM", BSCON_FNM_NM);               //거래처 상호 명
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BSCON_REPPRES_NM", BSCON_REPPRES_NM);               //거래처 대표자 명
        sobj.setString("SUPPPRES_BSCDTN", SUPPPRES_BSCDTN);               //공급자_업태
        sobj.setString("SUPPPRES_INS_NUM", SUPPPRES_INS_NUM);               //공급자 등록 번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("SUPPPRES_NM", SUPPPRES_NM);               //공급자 명
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("SUPPPRES_REPPRES_NM", SUPPPRES_REPPRES_NM);               //공급자 대표자 명
        sobj.setString("BSCON_BSCDTN", BSCON_BSCDTN);               //거래처업태
        sobj.setString("BSCON_BSTYP", BSCON_BSTYP);               //거래처업종
        sobj.setString("SUPPPRES_BSTYP", SUPPPRES_BSTYP);               //공급자업종
        sobj.setString("BSCON_NM", BSCON_NM);               //거래처 명
        sobj.setString("BSCON_INS_NUM", BSCON_INS_NUM);               //거래처 등록 번호
        sobj.setString("SUPPPRES_CD", SUPPPRES_CD);               //공급자_코드
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setString("SUPPPRES_FNM_NM", SUPPPRES_FNM_NM);               //공급자 상호 명
        sobj.setString("BSCON_POST_NUM", BSCON_POST_NUM);               //거래처 우편 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("BIPLC_GBN", BIPLC_GBN);               //사업장 구분
        sobj.setString("BSCON_ADDR", BSCON_ADDR);               //거래처 주소
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("ISS_YN", ISS_YN);               //발행 유무
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("SUPPPRES_ADDR", SUPPPRES_ADDR);               //공급자 주소
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        return sobj;
    }
    // 사용승인내역저장
    public DOBJ CALLcreateBill01tac_s02_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_INS19(dobj, dvobj);
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
    private SQLObject SQLcreateBill01tac_s02_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double CLR_NUM = 1;        //정산번호
        String DEMD_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");        //청구 일자
        String INS_DATE ="";        //등록 일시
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   CLR_NUM_1 = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //정산번호
        String   APPRV_NUM = dobj.getRetObject("R").getRecord().get("DEMD_DAY")+"001";   //승인 번호
        String   BILL_NUM = dobj.getRetObject("SEL20").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("SEL20").getRecord().get("BILL_NUM");   //청구서번호
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //공급가 액
        String   TITLE_NM = dobj.getRetObject("R").getRecord().get("REMAK");   //타이틀 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_USEAPPRVBRE (DEMD_NUM, INS_DATE, BILL_NUM, CLR_NUM, BILL_GBN, SUPPTEMP_AMT, ISS_DAY, TITLE_NM, BILL_KND, APPRV_NUM)  \n";
        query +=" values(:DEMD_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :BILL_NUM , (SELECT NVL(MAX(CLR_NUM),0) + 1 AS CLR_NUM from FIDU.TTAC_USEAPPRVBRE WHERE SUBSTR(ISS_DAY,1,6)=SUBSTR(:CLR_NUM_1,1,6)), :BILL_GBN , :SUPPTEMP_AMT , :ISS_DAY , :TITLE_NM , :BILL_KND , :APPRV_NUM )";
        sobj.setSql(query);
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("CLR_NUM_1", CLR_NUM_1);               //정산번호
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        sobj.setString("TITLE_NM", TITLE_NM);               //타이틀 명
        return sobj;
    }
    // 지부계산서출력저장
    public DOBJ CALLcreateBill01tac_s02_XIUD31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD31");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_XIUD31(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD31");
        rvobj.Println("XIUD31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_XIUD31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("R").getRecord().get("APPTN_YRMN");   //신청 일시
        String   BILL_NUM = dobj.getRetObject("SEL20").getRecord().get("BILL_NUM");   //계산서 번호
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" SET ISS_COMPL_YN='2', BILL_NUM = :BILL_NUM  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND APPTN_YRMN = :APPTN_YRMN  \n";
        query +=" AND BRAN_CD = :BRAN_CD  \n";
        query +=" AND UPSO_CD = :UPSO_CD  \n";
        query +=" AND BSCON_CD = :BSCON_CD  \n";
        query +=" AND BILL_NUM IS NULL  \n";
        query +=" AND SEQ =:SEQ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$createBill01tac_s02
    //##**$$createBill01tac_s02_2
    /*
    */
    public DOBJ CTLcreateBill01tac_s02_2(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("3"))
            {
                dobj  = CALLcreateBill01tac_s02_2_XIUD7(Conn, dobj);           //  계산서 삭제
                dobj  = CALLcreateBill01tac_s02_2_XIUD9(Conn, dobj);           //  사용승인내역 삭제
                dobj  = CALLcreateBill01tac_s02_2_SEL25(Conn, dobj);           //  계산서 쿼리1
                dobj  = CALLcreateBill01tac_s02_2_MPD26(Conn, dobj);           //  분기
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("4"))
            {
                dobj  = CALLcreateBill01tac_s02_2_SEL2(Conn, dobj);           //  계산서 쿼리1
                dobj  = CALLcreateBill01tac_s02_2_MPD7(Conn, dobj);           //  분기
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("5"))
            {
                dobj  = CALLcreateBill01tac_s02_2_SEL24(Conn, dobj);           //  계산서쿼리
                dobj  = CALLcreateBill01tac_s02_2_MPD25(Conn, dobj);           //  분기
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("6"))
            {
                dobj  = CALLcreateBill01tac_s02_2_SEL24(Conn, dobj);           //  계산서쿼리
                dobj  = CALLcreateBill01tac_s02_2_MPD25(Conn, dobj);           //  분기
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLcreateBill01tac_s02_2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("3"))
        {
            dobj  = CALLcreateBill01tac_s02_2_XIUD7(Conn, dobj);           //  계산서 삭제
            dobj  = CALLcreateBill01tac_s02_2_XIUD9(Conn, dobj);           //  사용승인내역 삭제
            dobj  = CALLcreateBill01tac_s02_2_SEL25(Conn, dobj);           //  계산서 쿼리1
            dobj  = CALLcreateBill01tac_s02_2_MPD26(Conn, dobj);           //  분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("4"))
        {
            dobj  = CALLcreateBill01tac_s02_2_SEL2(Conn, dobj);           //  계산서 쿼리1
            dobj  = CALLcreateBill01tac_s02_2_MPD7(Conn, dobj);           //  분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("5"))
        {
            dobj  = CALLcreateBill01tac_s02_2_SEL24(Conn, dobj);           //  계산서쿼리
            dobj  = CALLcreateBill01tac_s02_2_MPD25(Conn, dobj);           //  분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else if( dobj.getRetObject("S").getRecord().get("BILL_KND").equals("6"))
        {
            dobj  = CALLcreateBill01tac_s02_2_SEL24(Conn, dobj);           //  계산서쿼리
            dobj  = CALLcreateBill01tac_s02_2_MPD25(Conn, dobj);           //  분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // 계산서 삭제
    public DOBJ CALLcreateBill01tac_s02_2_XIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD7");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_XIUD7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD7");
        rvobj.Println("XIUD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_BILL  \n";
        query +=" WHERE SUBSTR(ISS_DAY,1,6) LIKE SUBSTR(:DEMD_DAY,1,6)||'%'  \n";
        query +=" AND BILL_KND = '3'";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    // 사용승인내역 삭제
    public DOBJ CALLcreateBill01tac_s02_2_XIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD9");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_XIUD9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD9");
        rvobj.Println("XIUD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_USEAPPRVBRE  \n";
        query +=" WHERE SUBSTR(ISS_DAY,1,6) LIKE SUBSTR(:DEMD_DAY,1,6)||'%'  \n";
        query +=" AND BILL_KND = '3'";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    // 계산서 쿼리1
    public DOBJ CALLcreateBill01tac_s02_2_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_2_SEL25(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        rvobj.Println("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("S").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEMD_NUM,  BILL_KND,  BILL_GBN,  DEMD_DAY,  BRAN_CD,  BSCON_CD,  BSCON_NM,  BSCON_INS_NUM,  BSCON_FNM_NM,  BSCON_REPPRES_NM,  BSCON_POST_NUM,  BSCON_ADDR,  BSCON_ADDR_DETED,  BSCON_BSTYP_CTENT,  BSCON_BSCDTN_CTENT,  SUPPPRES_CD,  SUPPPRES_NM,  SUPPPRES_INS_NUM,  SUPPPRES_FNM_NM,  SUPPPRES_REPPRES_NM,  SUPPPRES_POST_NUM,  SUPPPRES_ADDR,  SUPPPRES_ADDR_DETED,  SUPPPRES_BSTYP_CTENT,  SUPPPRES_BSCDTN_CTENT,  (OCC_AMT  +  DEDCT_AMT)  OCC_AMT,  ATAX_AMT,  REMAK  ,  BIGO  FROM  (   \n";
        query +=" SELECT  distinct  A.SUPP_YRMN||'0000'  AS  DEMD_NUM,  '3'  AS  BILL_KND,  '1'  AS  BILL_GBN,  :DEMD_DAY  AS  DEMD_DAY,  A.SUPP_YRMN  AS  APPRV_NUM,  '001'  AS  BIPLC_GBN,  ''  AS  BRAN_CD,  1  AS  CLR_NUM,  A.MB_CD  AS  BSCON_CD,  B.HANMB_NM  AS  BSCON_NM,  B.INS_NUM  AS  BSCON_INS_NUM,  B.HANMB_NM  AS  BSCON_FNM_NM,  B.REPPRES_NM  AS  BSCON_REPPRES_NM,  D.POST_NUM  AS  BSCON_POST_NUM,  D.ADDR  AS  BSCON_ADDR,  D.ADDR_DETED  AS  BSCON_ADDR_DETED,  B.BSTYP_CTENT  AS  BSCON_BSTYP_CTENT,  B.BSCDTN_CTENT  AS  BSCON_BSCDTN_CTENT,  C.BSCON_CD  AS  SUPPPRES_CD,  C.BSCONHAN_NM  AS  SUPPPRES_NM,  C.INS_NUM  AS  SUPPPRES_INS_NUM,  C.BSCONHAN_NM  AS  SUPPPRES_FNM_NM,  C.REPPRES_NM  AS  SUPPPRES_REPPRES_NM,  C.POST_NUM  AS  SUPPPRES_POST_NUM,  C.ADDR  AS  SUPPPRES_ADDR,  C.ADDR_DETED  AS  SUPPPRES_ADDR_DETED,  C.BSTYP_CTENT  AS  SUPPPRES_BSTYP_CTENT,  C.BSCDTN_CTENT  AS  SUPPPRES_BSCDTN_CTENT,  A.TOT_MNGCOMIS_AMT  AS  OCC_AMT,  NVL(E.DEDCT_AMT,0)  DEDCT_AMT,  0  AS  ATAX_AMT,  CASE  WHEN  B.MB_GBN1  IN  ('W',  'P',  'B',  'C',  'T')  THEN  SUBSTR(A.SUPP_YRMN,1,4)||'년  '||SUBSTR(A.SUPP_YRMN,5,2)||'월  관리수수료'  ELSE  SUBSTR(A.SUPP_YRMN,1,4)||'년  '||SUBSTR(A.SUPP_YRMN,5,2)||'월  외국분  관리수수료'  END  AS  REMAK  ,  ''  AS  BIGO  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B,  FIDU.TLEV_BSCON  C,  FIDU.TMEM_ADBK  D,  (   \n";
        query +=" SELECT  A.MB_CD  MB_CD,TRNSF_GBN,  SUM(DEDCT_AMT)DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  SUPP_YRMN  =  SUBSTR(:DEMD_DAY,1,6)   \n";
        query +=" AND  DEDCT_GBNONE  ='01'   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  B.MB_GBN1  IN  ('P','F','B','C')   \n";
        query +=" AND  DEDCT_GBNTWO  ='08'  GROUP  BY  A.MB_CD,  TRNSF_GBN  )  E  WHERE  A.MB_CD  =  B.MB_CD(+)   \n";
        query +=" AND  C.BSCON_CD  =  'C0438'   \n";
        query +=" AND  A.MB_CD  =  E.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  E.TRNSF_GBN(+)   \n";
        query +=" AND  A.MB_CD  =  D.MB_CD(+)   \n";
        query +=" AND  D.ADDR_GBN(+)  =  '2'   \n";
        query +=" AND  A.MB_GBN  IN  ('P','F','B','C')   \n";
        query +=" AND  A.MB_CD  NOT  IN   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  ='00406')   \n";
        query +=" AND  A.SUPP_YRMN  =SUBSTR(:DEMD_DAY,1,6)  )  WHERE  1  =  1   \n";
        query +=" AND  BILL_KND  =  :BILL_KND   \n";
        query +=" AND  OCC_AMT  +  DEDCT_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  DEMD_NUM,  BILL_KND,  BILL_GBN,  DEMD_DAY,  BRAN_CD,  BSCON_CD,  BSCON_NM,  BSCON_INS_NUM,  BSCON_FNM_NM,  BSCON_REPPRES_NM,  BSCON_POST_NUM,  BSCON_ADDR,  BSCON_ADDR_DETED,  BSCON_BSTYP_CTENT,  BSCON_BSCDTN_CTENT,  SUPPPRES_CD,  SUPPPRES_NM,  SUPPPRES_INS_NUM,  SUPPPRES_FNM_NM,  SUPPPRES_REPPRES_NM,  SUPPPRES_POST_NUM,  SUPPPRES_ADDR,  SUPPPRES_ADDR_DETED,  SUPPPRES_BSTYP_CTENT,  SUPPPRES_BSCDTN_CTENT,  DEDCT_AMT  OCC_AMT,  ATAX_AMT,  REMAK  ,  BIGO  FROM  (   \n";
        query +=" SELECT  distinct  A.SUPP_YRMN||'0000'  AS  DEMD_NUM,  '3'  AS  BILL_KND,  '1'  AS  BILL_GBN,  :DEMD_DAY  AS  DEMD_DAY,  A.SUPP_YRMN  AS  APPRV_NUM,  '001'  AS  BIPLC_GBN,  ''  AS  BRAN_CD,  1  AS  CLR_NUM,  A.MB_CD  AS  BSCON_CD,  G.BSCONHAN_NM  AS  BSCON_NM,  G.INS_NUM  AS  BSCON_INS_NUM,  G.BSCONHAN_NM  AS  BSCON_FNM_NM,  G.REPPRES_NM  AS  BSCON_REPPRES_NM,  G.POST_NUM  AS  BSCON_POST_NUM,  G.ADDR  AS  BSCON_ADDR,  G.ADDR_DETED  AS  BSCON_ADDR_DETED,  G.BSTYP_CTENT  AS  BSCON_BSTYP_CTENT,  G.BSCDTN_CTENT  AS  BSCON_BSCDTN_CTENT,  C.BSCON_CD  AS  SUPPPRES_CD,  C.BSCONHAN_NM  AS  SUPPPRES_NM,  C.INS_NUM  AS  SUPPPRES_INS_NUM,  C.BSCONHAN_NM  AS  SUPPPRES_FNM_NM,  C.REPPRES_NM  AS  SUPPPRES_REPPRES_NM,  C.POST_NUM  AS  SUPPPRES_POST_NUM,  C.ADDR  AS  SUPPPRES_ADDR,  C.ADDR_DETED  AS  SUPPPRES_ADDR_DETED,  C.BSTYP_CTENT  AS  SUPPPRES_BSTYP_CTENT,  C.BSCDTN_CTENT  AS  SUPPPRES_BSCDTN_CTENT,  A.TOT_MNGCOMIS_AMT  AS  OCC_AMT,  NVL(E.DEDCT_AMT,0)  DEDCT_AMT,  0  AS  ATAX_AMT,  CASE  WHEN  B.MB_GBN1  IN  ('W',  'P',  'B',  'C',  'T')   \n";
        query +=" OR   \n";
        query +=" (SELECT  COUNT(*)  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD='00406'   \n";
        query +=" AND  CODE_CD='IB'   \n";
        query +=" AND  CODE_NM  =A.MB_CD  )>0  THEN  SUBSTR(A.SUPP_YRMN,1,4)||'년  '||SUBSTR(A.SUPP_YRMN,5,2)||'월  관리수수료'  ELSE  SUBSTR(A.SUPP_YRMN,1,4)||'년  '||SUBSTR(A.SUPP_YRMN,5,2)||'월  외국분  관리수수료'  END  AS  REMAK,  ''  AS  BIGO  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B,  FIDU.TLEV_BSCON  C,  FIDU.TMEM_ADBK  D,  FIDU.TLEV_BSCON  G,  (   \n";
        query +=" SELECT  A.MB_CD  MB_CD,TRNSF_GBN,B.CODE_ETC,  SUM(DEDCT_AMT)DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TENV_CODE  B  WHERE  SUPP_YRMN  =  SUBSTR(:DEMD_DAY,1,6)   \n";
        query +=" AND  DEDCT_GBNONE  ='01'   \n";
        query +=" AND  B.USE_YN  ='Y'   \n";
        query +=" AND  A.MB_CD  =  B.CODE_NM   \n";
        query +=" AND  B.HIGH_CD  =  '00406'   \n";
        query +=" AND  DEDCT_GBNTWO  ='01'  GROUP  BY  A.MB_CD,  TRNSF_GBN,CODE_ETC  )  E  WHERE  A.MB_CD  =  B.MB_CD(+)   \n";
        query +=" AND  C.BSCON_CD  =  'C0438'   \n";
        query +=" AND  A.MB_CD  =  E.MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  =  E.TRNSF_GBN(+)   \n";
        query +=" AND  A.MB_CD  =  D.MB_CD(+)   \n";
        query +=" AND  D.ADDR_GBN(+)  =  '2'   \n";
        query +=" AND  E.CODE_ETC  =  G.BSCON_CD   \n";
        query +=" AND  A.SUPP_YRMN  =SUBSTR(:DEMD_DAY,1,6)  )  WHERE  1  =  1   \n";
        query +=" AND  BILL_KND  =  :BILL_KND   \n";
        query +=" AND  OCC_AMT  +  DEDCT_AMT  >  0  ORDER  BY  DEMD_NUM  ASC ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        return sobj;
    }
    // 분기
    public DOBJ CALLcreateBill01tac_s02_2_MPD26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD26");
        VOBJ dvobj = dobj.getRetObject("SEL25");         //계산서 쿼리1에서 생성시킨 OBJECT입니다.(CALLcreateBill01tac_s02_2_SEL25)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLcreateBill01tac_s02_2_SEL28(Conn, dobj);           //  계산서번호가 없을경우
                dobj  = CALLcreateBill01tac_s02_2_INS29(Conn, dobj);           //  계산서생성
                dobj  = CALLcreateBill01tac_s02_2_INS30(Conn, dobj);           //  사용승인내역저장
            }
        }
        return dobj;
    }
    // 계산서번호가 없을경우
    public DOBJ CALLcreateBill01tac_s02_2_SEL28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL28");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL28");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_2_SEL28(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL28");
        rvobj.Println("SEL28");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_SEL28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("R").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUBSTR(:DEMD_DAY,1,6)||  DECODE(:BILL_KND,3,'A',4,'G','')||  LPAD(NVL((   \n";
        query +=" SELECT  MAX(SUBSTR(BILL_NUM,8)+1)  FROM  FIDU.TTAC_BILL  WHERE  SUBSTR(BILL_NUM,1,6)  =  SUBSTR(:DEMD_DAY,1,6)   \n";
        query +=" AND  BILL_KND  =  :BILL_KND  ),1  ),3,'0'  )  AS  BILL_NUM  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        return sobj;
    }
    // 계산서생성
    public DOBJ CALLcreateBill01tac_s02_2_INS29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS29");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_INS29(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS29") ;
        rvobj.Println("INS29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_INS29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BSCON_FNM_NM = dvobj.getRecord().get("BSCON_FNM_NM");   //거래처 상호 명
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_REPPRES_NM = dvobj.getRecord().get("BSCON_REPPRES_NM");   //거래처 대표자 명
        String   SUPPPRES_INS_NUM = dvobj.getRecord().get("SUPPPRES_INS_NUM");   //공급자 등록 번호
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   SUPPPRES_NM = dvobj.getRecord().get("SUPPPRES_NM");   //공급자 명
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   SUPPPRES_REPPRES_NM = dvobj.getRecord().get("SUPPPRES_REPPRES_NM");   //공급자 대표자 명
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //거래처 명
        String   BSCON_INS_NUM = dvobj.getRecord().get("BSCON_INS_NUM");   //거래처 등록 번호
        String   SUPPPRES_CD = dvobj.getRecord().get("SUPPPRES_CD");   //공급자_코드
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //부가세 금액
        String   SUPPPRES_FNM_NM = dvobj.getRecord().get("SUPPPRES_FNM_NM");   //공급자 상호 명
        String   BSCON_POST_NUM = dvobj.getRecord().get("BSCON_POST_NUM");   //거래처 우편 번호
        String   BILL_NUM = dobj.getRetObject("SEL28").getRecord().get("BILL_NUM");   //계산서 번호
        String   BIPLC_GBN ="001";   //사업장 구분
        String   BSCON_ADDR = dobj.getRetObject("R").getRecord().get("BSCON_ADDR")+dobj.getRetObject("R").getRecord().get("BSCON_ADDR_DETED");   //거래처 주소
        String   BSCON_BSCDTN = dobj.getRetObject("R").getRecord().get("BSCON_BSCDTN_CTENT");   //거래처업태
        String   BSCON_BSTYP = dobj.getRetObject("R").getRecord().get("BSCON_BSTYP_CTENT");   //거래처업종
        String   DEMD_NUM = dobj.getRetObject("SEL28").getRecord().get("BILL_NUM");   //청구서번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        String   ISS_YN ="1";   //발행 유무
        String   REMAK = dobj.getRetObject("R").getRecord().get("BIGO");   //비고
        String   SUPPPRES_ADDR = dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR")+dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR_DETED");   //공급자 주소
        String   SUPPPRES_BSCDTN = dobj.getRetObject("R").getRecord().get("SUPPPRES_BSCDTN_CTENT");   //공급자_업태
        String   SUPPPRES_BSTYP = dobj.getRetObject("R").getRecord().get("SUPPPRES_BSTYP_CTENT");   //공급자업종
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //공급가 액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_BILL (INSPRES_ID, ISS_YN, BSCON_POST_NUM, SUPPPRES_FNM_NM, BSCON_ADDR, ATAX_AMT, BIPLC_GBN, SUPPPRES_CD, DEMD_NUM, INS_DATE, BSCON_INS_NUM, BSCON_NM, SUPPPRES_BSTYP, BSCON_BSTYP, REMAK, BSCON_BSCDTN, SUPPPRES_REPPRES_NM, BILL_GBN, SUPPPRES_ADDR, SUPPPRES_NM, BSCON_CD, SUPPPRES_INS_NUM, BILL_NUM, ISS_DAY, SUPPTEMP_AMT, SUPPPRES_BSCDTN, BSCON_REPPRES_NM, BRAN_CD, BILL_KND, BSCON_FNM_NM)  \n";
        query +=" values(:INSPRES_ID , :ISS_YN , :BSCON_POST_NUM , :SUPPPRES_FNM_NM , :BSCON_ADDR , :ATAX_AMT , :BIPLC_GBN , :SUPPPRES_CD , :DEMD_NUM , SYSDATE, :BSCON_INS_NUM , :BSCON_NM , :SUPPPRES_BSTYP , :BSCON_BSTYP , :REMAK , :BSCON_BSCDTN , :SUPPPRES_REPPRES_NM , :BILL_GBN , :SUPPPRES_ADDR , :SUPPPRES_NM , :BSCON_CD , :SUPPPRES_INS_NUM , :BILL_NUM , :ISS_DAY , :SUPPTEMP_AMT , :SUPPPRES_BSCDTN , :BSCON_REPPRES_NM , :BRAN_CD , :BILL_KND , :BSCON_FNM_NM )";
        sobj.setSql(query);
        sobj.setString("BSCON_FNM_NM", BSCON_FNM_NM);               //거래처 상호 명
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BSCON_REPPRES_NM", BSCON_REPPRES_NM);               //거래처 대표자 명
        sobj.setString("SUPPPRES_INS_NUM", SUPPPRES_INS_NUM);               //공급자 등록 번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("SUPPPRES_NM", SUPPPRES_NM);               //공급자 명
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("SUPPPRES_REPPRES_NM", SUPPPRES_REPPRES_NM);               //공급자 대표자 명
        sobj.setString("BSCON_NM", BSCON_NM);               //거래처 명
        sobj.setString("BSCON_INS_NUM", BSCON_INS_NUM);               //거래처 등록 번호
        sobj.setString("SUPPPRES_CD", SUPPPRES_CD);               //공급자_코드
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setString("SUPPPRES_FNM_NM", SUPPPRES_FNM_NM);               //공급자 상호 명
        sobj.setString("BSCON_POST_NUM", BSCON_POST_NUM);               //거래처 우편 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("BIPLC_GBN", BIPLC_GBN);               //사업장 구분
        sobj.setString("BSCON_ADDR", BSCON_ADDR);               //거래처 주소
        sobj.setString("BSCON_BSCDTN", BSCON_BSCDTN);               //거래처업태
        sobj.setString("BSCON_BSTYP", BSCON_BSTYP);               //거래처업종
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("ISS_YN", ISS_YN);               //발행 유무
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("SUPPPRES_ADDR", SUPPPRES_ADDR);               //공급자 주소
        sobj.setString("SUPPPRES_BSCDTN", SUPPPRES_BSCDTN);               //공급자_업태
        sobj.setString("SUPPPRES_BSTYP", SUPPPRES_BSTYP);               //공급자업종
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        return sobj;
    }
    // 사용승인내역저장
    public DOBJ CALLcreateBill01tac_s02_2_INS30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS30");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_INS30(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS30") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_INS30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double CLR_NUM = 1;        //정산번호
        String DEMD_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");        //청구 일자
        String INS_DATE ="";        //등록 일시
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   CLR_NUM_1 = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //정산번호
        String   APPRV_NUM = dobj.getRetObject("R").getRecord().get("DEMD_DAY")+"001";   //승인 번호
        String   BILL_NUM = dobj.getRetObject("SEL28").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("SEL28").getRecord().get("BILL_NUM");   //청구서번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //공급가 액
        String   TITLE_NM = dobj.getRetObject("R").getRecord().get("REMAK");   //타이틀 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_USEAPPRVBRE (DEMD_NUM, INS_DATE, BILL_NUM, INSPRES_ID, CLR_NUM, BILL_GBN, SUPPTEMP_AMT, ISS_DAY, TITLE_NM, BILL_KND, APPRV_NUM)  \n";
        query +=" values(:DEMD_NUM , SYSDATE, :BILL_NUM , :INSPRES_ID , (SELECT NVL(MAX(CLR_NUM),0) + 1 AS CLR_NUM from FIDU.TTAC_USEAPPRVBRE WHERE SUBSTR(ISS_DAY,1,6)=SUBSTR(:CLR_NUM_1,1,6)), :BILL_GBN , :SUPPTEMP_AMT , :ISS_DAY , :TITLE_NM , :BILL_KND , :APPRV_NUM )";
        sobj.setSql(query);
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("CLR_NUM_1", CLR_NUM_1);               //정산번호
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        sobj.setString("TITLE_NM", TITLE_NM);               //타이틀 명
        return sobj;
    }
    // 계산서 쿼리1
    public DOBJ CALLcreateBill01tac_s02_2_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_2_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEMD_NUM  ,  BILL_KND  ,  BILL_GBN  ,  DEMD_DAY  ,  APPRV_NUM  ,  BRAN_CD  ,  APPTN_YRMN  ,  UPSO_CD  ,  BSCON_CD  ,  BSCON_NM  ,  BSCON_INS_NUM  ,  BSCON_FNM_NM  ,  BSCON_REPPRES_NM  ,  BSCON_POST_NUM  ,  BSCON_ADDR  ,  BSCON_ADDR_DETED  ,  BSCON_BSTYP_CTENT  AS  BSCON_BSTYP  ,  BSCON_BSCDTN_CTENT  AS  BSCON_BSCDTN  ,  SUPPPRES_CD  ,  SUPPPRES_NM  ,  SUPPPRES_INS_NUM  ,  SUPPPRES_FNM_NM  ,  SUPPPRES_REPPRES_NM  ,  SUPPPRES_POST_NUM  ,  SUPPPRES_ADDR  ,  SUPPPRES_ADDR_DETED  ,  SUPPPRES_BSTYP_CTENT  AS  SUPPPRES_BSTYP  ,  SUPPPRES_BSCDTN_CTENT  AS  SUPPPRES_BSCDTN  ,  OCC_AMT  ,  ATAX_AMT  ,  REMAK  ,  BIGO  FROM  (   \n";
        query +=" SELECT  A.DEMD_NUM  AS  DEMD_NUM  ,  D.BILL_KND  AS  BILL_KND  ,  D.BILL_GBN  AS  BILL_GBN  ,  D.ISS_DAY  AS  DEMD_DAY  ,  D.APPRV_NUM  AS  APPRV_NUM  ,  '002'  AS  BIPLC_GBN  ,  1  AS  CLR_NUM  ,  A.BRAN_CD  AS  BRAN_CD  ,  A.APPTN_YRMN  ,  A.UPSO_CD  ,  A.BSCON_CD  AS  BSCON_CD  ,  B.BSCONHAN_NM  AS  BSCON_NM  ,  B.INS_NUM  AS  BSCON_INS_NUM  ,  B.BSCONHAN_NM  AS  BSCON_FNM_NM  ,  B.REPPRES_NM  AS  BSCON_REPPRES_NM  ,  B.POST_NUM  AS  BSCON_POST_NUM  ,  B.ADDR  AS  BSCON_ADDR  ,  B.ADDR_DETED  AS  BSCON_ADDR_DETED  ,  B.BSTYP_CTENT  AS  BSCON_BSTYP_CTENT  ,  B.BSCDTN_CTENT  AS  BSCON_BSCDTN_CTENT  ,  C.BSCON_CD  AS  SUPPPRES_CD  ,  C.BSCONHAN_NM  AS  SUPPPRES_NM  ,  C.INS_NUM  AS  SUPPPRES_INS_NUM  ,  C.BSCONHAN_NM  AS  SUPPPRES_FNM_NM  ,  C.REPPRES_NM  AS  SUPPPRES_REPPRES_NM  ,  C.POST_NUM  AS  SUPPPRES_POST_NUM  ,  C.ADDR  AS  SUPPPRES_ADDR  ,  C.ADDR_DETED  AS  SUPPPRES_ADDR_DETED  ,  C.BSTYP_CTENT  AS  SUPPPRES_BSTYP_CTENT  ,  C.BSCDTN_CTENT  AS  SUPPPRES_BSCDTN_CTENT  ,  ISS_AMT  AS  OCC_AMT  ,  0  AS  ATAX_AMT  ,  D.ISS_BRE  ||  '  ('  ||   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  A.BRAN_CD  =  GIBU)  ||  ')'  AS  REMAK  ,  D.REMAK  AS  BIGO  FROM  GIBU.TBRA_BILL_ISS_MST  A  ,  FIDU.TLEV_BSCON  B  ,  FIDU.TLEV_BSCON  C  ,  GIBU.TBRA_BILL_ISS_DTL  D  WHERE  A.BSCON_CD  =  B.BSCON_CD   \n";
        query +=" AND  A.DEMD_NUM  =  D.DEMD_NUM   \n";
        query +=" AND  A.APPTN_YRMN  =  SUBSTR  (:DEMD_DAY,  1,  6)   \n";
        query +=" AND  C.BSCON_CD  =  'C0438'   \n";
        query +=" AND  A.BSCON_CD  IS  NOT  NULL   \n";
        query +=" AND  D.BILL_NUM  IS  NULL   \n";
        query +=" AND  D.ISS_COMPL_YN  =  '1'   \n";
        query +=" AND  D.BILL_KND  =  '4'  ORDER  BY  A.BRAN_CD,  A.INS_DATE) ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    // 분기
    public DOBJ CALLcreateBill01tac_s02_2_MPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD7");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //계산서 쿼리1에서 생성시킨 OBJECT입니다.(CALLcreateBill01tac_s02_2_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLcreateBill01tac_s02_2_SEL20(Conn, dobj);           //  계산서번호생성
                dobj  = CALLcreateBill01tac_s02_2_INS18(Conn, dobj);           //  계산서생성
                dobj  = CALLcreateBill01tac_s02_2_INS19(Conn, dobj);           //  사용승인내역저장
                dobj  = CALLcreateBill01tac_s02_2_XIUD31(Conn, dobj);           //  지부계산서출력저장
            }
        }
        return dobj;
    }
    // 계산서번호생성
    public DOBJ CALLcreateBill01tac_s02_2_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_2_SEL20(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        rvobj.Println("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("R").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUBSTR(:DEMD_DAY,  1,  6)  ||  DECODE(:BILL_KND,  3,  'A',  4,  'G',  5,  'B',  6,  'S',  '')  ||  TRIM(TO_CHAR(NVL(MAX(TO_NUMBER(SUBSTR(BILL_NUM,  8),  'XXX')),  0)  +  1,  '0XX'))  AS  BILL_NUM  FROM  FIDU.TTAC_BILL  WHERE  BILL_NUM  LIKE  SUBSTR(:DEMD_DAY,  1,  6)  ||  DECODE(:BILL_KND,  3,  'A',  4,  'G',  5,  'B',  6,  'S',  '')  ||  '%' ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        return sobj;
    }
    // 계산서생성
    public DOBJ CALLcreateBill01tac_s02_2_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_INS18(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS18") ;
        rvobj.Println("INS18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BSCON_FNM_NM = dvobj.getRecord().get("BSCON_FNM_NM");   //거래처 상호 명
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_REPPRES_NM = dvobj.getRecord().get("BSCON_REPPRES_NM");   //거래처 대표자 명
        String   SUPPPRES_BSCDTN = dvobj.getRecord().get("SUPPPRES_BSCDTN");   //공급자_업태
        String   SUPPPRES_INS_NUM = dvobj.getRecord().get("SUPPPRES_INS_NUM");   //공급자 등록 번호
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   SUPPPRES_NM = dvobj.getRecord().get("SUPPPRES_NM");   //공급자 명
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   SUPPPRES_REPPRES_NM = dvobj.getRecord().get("SUPPPRES_REPPRES_NM");   //공급자 대표자 명
        String   BSCON_BSCDTN = dvobj.getRecord().get("BSCON_BSCDTN");   //거래처업태
        String   BSCON_BSTYP = dvobj.getRecord().get("BSCON_BSTYP");   //거래처업종
        String   SUPPPRES_BSTYP = dvobj.getRecord().get("SUPPPRES_BSTYP");   //공급자업종
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //거래처 명
        String   BSCON_INS_NUM = dvobj.getRecord().get("BSCON_INS_NUM");   //거래처 등록 번호
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        String   SUPPPRES_CD = dvobj.getRecord().get("SUPPPRES_CD");   //공급자_코드
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //부가세 금액
        String   SUPPPRES_FNM_NM = dvobj.getRecord().get("SUPPPRES_FNM_NM");   //공급자 상호 명
        String   BSCON_POST_NUM = dvobj.getRecord().get("BSCON_POST_NUM");   //거래처 우편 번호
        String   BILL_NUM = dobj.getRetObject("SEL20").getRecord().get("BILL_NUM");   //계산서 번호
        String   BIPLC_GBN ="001";   //사업장 구분
        String   BSCON_ADDR = dobj.getRetObject("R").getRecord().get("BSCON_ADDR")+dobj.getRetObject("R").getRecord().get("BSCON_ADDR_DETED");   //거래처 주소
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        String   ISS_YN ="1";   //발행 유무
        String   REMAK = dobj.getRetObject("R").getRecord().get("BIGO");   //비고
        String   SUPPPRES_ADDR = dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR")+dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR_DETED");   //공급자 주소
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //공급가 액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_BILL (INSPRES_ID, ISS_YN, BSCON_POST_NUM, SUPPPRES_FNM_NM, BSCON_ADDR, ATAX_AMT, BIPLC_GBN, SUPPPRES_CD, DEMD_NUM, INS_DATE, BSCON_INS_NUM, BSCON_NM, SUPPPRES_BSTYP, BSCON_BSTYP, REMAK, BSCON_BSCDTN, SUPPPRES_REPPRES_NM, BILL_GBN, SUPPPRES_ADDR, SUPPPRES_NM, BSCON_CD, SUPPPRES_INS_NUM, BILL_NUM, ISS_DAY, SUPPTEMP_AMT, SUPPPRES_BSCDTN, BSCON_REPPRES_NM, BRAN_CD, BILL_KND, BSCON_FNM_NM)  \n";
        query +=" values(:INSPRES_ID , :ISS_YN , :BSCON_POST_NUM , :SUPPPRES_FNM_NM , :BSCON_ADDR , :ATAX_AMT , :BIPLC_GBN , :SUPPPRES_CD , :DEMD_NUM , SYSDATE, :BSCON_INS_NUM , :BSCON_NM , :SUPPPRES_BSTYP , :BSCON_BSTYP , :REMAK , :BSCON_BSCDTN , :SUPPPRES_REPPRES_NM , :BILL_GBN , :SUPPPRES_ADDR , :SUPPPRES_NM , :BSCON_CD , :SUPPPRES_INS_NUM , :BILL_NUM , :ISS_DAY , :SUPPTEMP_AMT , :SUPPPRES_BSCDTN , :BSCON_REPPRES_NM , :BRAN_CD , :BILL_KND , :BSCON_FNM_NM )";
        sobj.setSql(query);
        sobj.setString("BSCON_FNM_NM", BSCON_FNM_NM);               //거래처 상호 명
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BSCON_REPPRES_NM", BSCON_REPPRES_NM);               //거래처 대표자 명
        sobj.setString("SUPPPRES_BSCDTN", SUPPPRES_BSCDTN);               //공급자_업태
        sobj.setString("SUPPPRES_INS_NUM", SUPPPRES_INS_NUM);               //공급자 등록 번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("SUPPPRES_NM", SUPPPRES_NM);               //공급자 명
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("SUPPPRES_REPPRES_NM", SUPPPRES_REPPRES_NM);               //공급자 대표자 명
        sobj.setString("BSCON_BSCDTN", BSCON_BSCDTN);               //거래처업태
        sobj.setString("BSCON_BSTYP", BSCON_BSTYP);               //거래처업종
        sobj.setString("SUPPPRES_BSTYP", SUPPPRES_BSTYP);               //공급자업종
        sobj.setString("BSCON_NM", BSCON_NM);               //거래처 명
        sobj.setString("BSCON_INS_NUM", BSCON_INS_NUM);               //거래처 등록 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("SUPPPRES_CD", SUPPPRES_CD);               //공급자_코드
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setString("SUPPPRES_FNM_NM", SUPPPRES_FNM_NM);               //공급자 상호 명
        sobj.setString("BSCON_POST_NUM", BSCON_POST_NUM);               //거래처 우편 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("BIPLC_GBN", BIPLC_GBN);               //사업장 구분
        sobj.setString("BSCON_ADDR", BSCON_ADDR);               //거래처 주소
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("ISS_YN", ISS_YN);               //발행 유무
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("SUPPPRES_ADDR", SUPPPRES_ADDR);               //공급자 주소
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        return sobj;
    }
    // 사용승인내역저장
    public DOBJ CALLcreateBill01tac_s02_2_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_INS19(dobj, dvobj);
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
    private SQLObject SQLcreateBill01tac_s02_2_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double CLR_NUM = 1;        //정산번호
        String INS_DATE ="";        //등록 일시
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   CLR_NUM_1 = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //정산번호
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        String   BILL_NUM = dobj.getRetObject("SEL20").getRecord().get("BILL_NUM");   //계산서 번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //공급가 액
        String   TITLE_NM = dobj.getRetObject("R").getRecord().get("REMAK");   //타이틀 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_USEAPPRVBRE (DEMD_NUM, INS_DATE, BILL_NUM, INSPRES_ID, CLR_NUM, BILL_GBN, SUPPTEMP_AMT, ISS_DAY, TITLE_NM, BILL_KND, APPRV_NUM)  \n";
        query +=" values(:DEMD_NUM , SYSDATE, :BILL_NUM , :INSPRES_ID , (SELECT NVL(MAX(CLR_NUM),0) + 1 AS CLR_NUM from FIDU.TTAC_USEAPPRVBRE WHERE SUBSTR(ISS_DAY,1,6)=SUBSTR(:CLR_NUM_1,1,6)), :BILL_GBN , :SUPPTEMP_AMT , :ISS_DAY , :TITLE_NM , :BILL_KND , :APPRV_NUM )";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("CLR_NUM_1", CLR_NUM_1);               //정산번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        sobj.setString("TITLE_NM", TITLE_NM);               //타이틀 명
        return sobj;
    }
    // 지부계산서출력저장
    public DOBJ CALLcreateBill01tac_s02_2_XIUD31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD31");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_XIUD31(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD31");
        rvobj.Println("XIUD31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_XIUD31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPRV_NUM = dobj.getRetObject("R").getRecord().get("APPRV_NUM");   //승인 번호
        String   BILL_NUM = dobj.getRetObject("SEL20").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("R").getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BILL_ISS_DTL  \n";
        query +=" SET BILL_NUM = :BILL_NUM , ISS_COMPL_YN = '2'  \n";
        query +=" WHERE DEMD_NUM = :DEMD_NUM  \n";
        query +=" AND APPRV_NUM = :APPRV_NUM";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    // 계산서쿼리
    public DOBJ CALLcreateBill01tac_s02_2_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_2_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("S").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEMD_NUM  ,  BILL_KND  ,  BILL_GBN  ,  DEMD_DAY  ,  APPRV_NUM  ,  BRAN_CD  ,  APPTN_YRMN  ,  UPSO_CD  ,  BSCON_CD  ,  BSCON_NM  ,  BSCON_INS_NUM  ,  BSCON_FNM_NM  ,  BSCON_REPPRES_NM  ,  BSCON_POST_NUM  ,  BSCON_ADDR  ,  BSCON_ADDR_DETED  ,  BSCON_BSTYP_CTENT  AS  BSCON_BSTYP  ,  BSCON_BSCDTN_CTENT  AS  BSCON_BSCDTN  ,  SUPPPRES_CD  ,  SUPPPRES_NM  ,  SUPPPRES_INS_NUM  ,  SUPPPRES_FNM_NM  ,  SUPPPRES_REPPRES_NM  ,  SUPPPRES_POST_NUM  ,  SUPPPRES_ADDR  ,  SUPPPRES_ADDR_DETED  ,  SUPPPRES_BSTYP_CTENT  AS  SUPPPRES_BSTYP  ,  SUPPPRES_BSCDTN_CTENT  AS  SUPPPRES_BSCDTN  ,  OCC_AMT  ,  ATAX_AMT  ,  REMAK  ,  BIGO  FROM  (   \n";
        query +=" SELECT  A.DEMD_NUM  AS  DEMD_NUM  ,  D.BILL_KND  AS  BILL_KND  ,  D.BILL_GBN  AS  BILL_GBN  ,  D.ISS_DAY  AS  DEMD_DAY  ,  D.APPRV_NUM  AS  APPRV_NUM  ,  '002'  AS  BIPLC_GBN  ,  1  AS  CLR_NUM  ,  A.BRAN_CD  AS  BRAN_CD  ,  A.APPTN_YRMN  ,  A.UPSO_CD  ,  A.BSCON_CD  AS  BSCON_CD  ,  B.BSCONHAN_NM  AS  BSCON_NM  ,  B.INS_NUM  AS  BSCON_INS_NUM  ,  B.BSCONHAN_NM  AS  BSCON_FNM_NM  ,  B.REPPRES_NM  AS  BSCON_REPPRES_NM  ,  B.POST_NUM  AS  BSCON_POST_NUM  ,  B.ADDR  AS  BSCON_ADDR  ,  B.ADDR_DETED  AS  BSCON_ADDR_DETED  ,  B.BSTYP_CTENT  AS  BSCON_BSTYP_CTENT  ,  B.BSCDTN_CTENT  AS  BSCON_BSCDTN_CTENT  ,  C.BSCON_CD  AS  SUPPPRES_CD  ,  C.BSCONHAN_NM  AS  SUPPPRES_NM  ,  C.INS_NUM  AS  SUPPPRES_INS_NUM  ,  C.BSCONHAN_NM  AS  SUPPPRES_FNM_NM  ,  C.REPPRES_NM  AS  SUPPPRES_REPPRES_NM  ,  C.POST_NUM  AS  SUPPPRES_POST_NUM  ,  C.ADDR  AS  SUPPPRES_ADDR  ,  C.ADDR_DETED  AS  SUPPPRES_ADDR_DETED  ,  C.BSTYP_CTENT  AS  SUPPPRES_BSTYP_CTENT  ,  C.BSCDTN_CTENT  AS  SUPPPRES_BSCDTN_CTENT  ,  ISS_AMT  -  NVL((SELECT  ATAX_AMT  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  DEMD_YRMN  =  SUBSTR(:DEMD_DAY,  1,  6)   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  D.SUPPBSCON_CD   \n";
        query +=" AND  ATAX_AMT  >  0   \n";
        query +=" AND  :BILL_KND  =  '6'),  0)  AS  OCC_AMT  ,  NVL((SELECT  ATAX_AMT  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  DEMD_YRMN  =  SUBSTR(:DEMD_DAY,  1,  6)   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  D.SUPPBSCON_CD   \n";
        query +=" AND  ATAX_AMT  >  0   \n";
        query +=" AND  :BILL_KND  =  '6'),  0)  AS  ATAX_AMT  ,  D.ISS_BRE  ||  '  ('  ||   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  A.BRAN_CD  =  GIBU)  ||  ')'  AS  REMAK  ,  D.REMAK  AS  BIGO  FROM  GIBU.TBRA_BILL_ISS_MST  A  ,  FIDU.TLEV_BSCON  B  ,  FIDU.TLEV_BSCON  C  ,  GIBU.TBRA_BILL_ISS_DTL  D  WHERE  A.BSCON_CD  =  B.BSCON_CD   \n";
        query +=" AND  A.DEMD_NUM  =  D.DEMD_NUM   \n";
        query +=" AND  A.APPTN_YRMN  =  SUBSTR  (:DEMD_DAY,  1,  6)   \n";
        query +=" AND  C.BSCON_CD  =   \n";
        query +=" (SELECT  BSCON_CD  FROM  FIDU.TLEV_BSCON  WHERE  BSCONENGST_NM  =  D.SUPPBSCON_CD)   \n";
        query +=" AND  A.BSCON_CD  IS  NOT  NULL   \n";
        query +=" AND  D.BILL_NUM  IS  NULL   \n";
        query +=" AND  D.ISS_COMPL_YN  =  '1'   \n";
        query +=" AND  D.BILL_KND  =  :BILL_KND  ORDER  BY  A.BRAN_CD,  A.INS_DATE) ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        return sobj;
    }
    // 분기
    public DOBJ CALLcreateBill01tac_s02_2_MPD25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD25");
        VOBJ dvobj = dobj.getRetObject("SEL24");         //계산서쿼리에서 생성시킨 OBJECT입니다.(CALLcreateBill01tac_s02_2_SEL24)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLcreateBill01tac_s02_2_SEL27(Conn, dobj);           //  계산서번호생성
                dobj  = CALLcreateBill01tac_s02_2_INS28(Conn, dobj);           //  계산서생성
                dobj  = CALLcreateBill01tac_s02_2_INS31(Conn, dobj);           //  사용승인내역저장
                dobj  = CALLcreateBill01tac_s02_2_XIUD32(Conn, dobj);           //  지부계산서출력저장
            }
        }
        return dobj;
    }
    // 계산서번호생성
    public DOBJ CALLcreateBill01tac_s02_2_SEL27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL27");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL27");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s02_2_SEL27(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL27");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_SEL27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("R").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUBSTR(:DEMD_DAY,  1,  6)  ||  DECODE(:BILL_KND,  3,  'A',  4,  'G',  5,  'B',  6,  'S',  '')  ||  TRIM(TO_CHAR(NVL(MAX(TO_NUMBER(SUBSTR(BILL_NUM,  8),  'XXX')),  0)  +  1,  '0XX'))  AS  BILL_NUM  FROM  FIDU.TTAC_BILL  WHERE  BILL_NUM  LIKE  SUBSTR(:DEMD_DAY,  1,  6)  ||  DECODE(:BILL_KND,  3,  'A',  4,  'G',  5,  'B',  6,  'S',  '')  ||  '%' ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        return sobj;
    }
    // 계산서생성
    public DOBJ CALLcreateBill01tac_s02_2_INS28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS28");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_INS28(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS28") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_INS28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //임시컬럼4
        String   BSCON_FNM_NM = dvobj.getRecord().get("BSCON_FNM_NM");   //거래처 상호 명
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_REPPRES_NM = dvobj.getRecord().get("BSCON_REPPRES_NM");   //거래처 대표자 명
        String   SUPPPRES_BSCDTN = dvobj.getRecord().get("SUPPPRES_BSCDTN");   //공급자_업태
        String   SUPPPRES_INS_NUM = dvobj.getRecord().get("SUPPPRES_INS_NUM");   //공급자 등록 번호
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   SUPPPRES_NM = dvobj.getRecord().get("SUPPPRES_NM");   //공급자 명
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   SUPPPRES_REPPRES_NM = dvobj.getRecord().get("SUPPPRES_REPPRES_NM");   //공급자 대표자 명
        String   BSCON_BSCDTN = dvobj.getRecord().get("BSCON_BSCDTN");   //거래처업태
        String   BSCON_BSTYP = dvobj.getRecord().get("BSCON_BSTYP");   //거래처업종
        String   SUPPPRES_BSTYP = dvobj.getRecord().get("SUPPPRES_BSTYP");   //공급자업종
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //거래처 명
        String   BSCON_INS_NUM = dvobj.getRecord().get("BSCON_INS_NUM");   //거래처 등록 번호
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        String   SUPPPRES_CD = dvobj.getRecord().get("SUPPPRES_CD");   //공급자_코드
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //부가세 금액
        String   SUPPPRES_FNM_NM = dvobj.getRecord().get("SUPPPRES_FNM_NM");   //공급자 상호 명
        String   BSCON_POST_NUM = dvobj.getRecord().get("BSCON_POST_NUM");   //거래처 우편 번호
        String   BILL_NUM = dobj.getRetObject("SEL27").getRecord().get("BILL_NUM");   //임시컬럼1
        String   BIPLC_GBN ="001";   //TEMP10
        String   BSCON_ADDR = dobj.getRetObject("R").getRecord().get("BSCON_ADDR")+dobj.getRetObject("R").getRecord().get("BSCON_ADDR_DETED");   //임시컬럼2
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //임시컬럼3
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //임시컬럼5
        String   ISS_YN ="1";   //임시
        String   REMAK = dobj.getRetObject("R").getRecord().get("BIGO");   //임시
        String   SUPPPRES_ADDR = dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR")+dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR_DETED");   //임시
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //임시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_BILL (INSPRES_ID, ISS_YN, BSCON_POST_NUM, SUPPPRES_FNM_NM, BSCON_ADDR, ATAX_AMT, BIPLC_GBN, SUPPPRES_CD, DEMD_NUM, INS_DATE, BSCON_INS_NUM, BSCON_NM, SUPPPRES_BSTYP, BSCON_BSTYP, REMAK, BSCON_BSCDTN, SUPPPRES_REPPRES_NM, BILL_GBN, SUPPPRES_ADDR, SUPPPRES_NM, BSCON_CD, SUPPPRES_INS_NUM, BILL_NUM, ISS_DAY, SUPPTEMP_AMT, SUPPPRES_BSCDTN, BSCON_REPPRES_NM, BRAN_CD, BILL_KND, BSCON_FNM_NM)  \n";
        query +=" values(:INSPRES_ID , :ISS_YN , :BSCON_POST_NUM , :SUPPPRES_FNM_NM , :BSCON_ADDR , :ATAX_AMT , :BIPLC_GBN , :SUPPPRES_CD , :DEMD_NUM , SYSDATE, :BSCON_INS_NUM , :BSCON_NM , :SUPPPRES_BSTYP , :BSCON_BSTYP , :REMAK , :BSCON_BSCDTN , :SUPPPRES_REPPRES_NM , :BILL_GBN , :SUPPPRES_ADDR , :SUPPPRES_NM , :BSCON_CD , :SUPPPRES_INS_NUM , :BILL_NUM , :ISS_DAY , :SUPPTEMP_AMT , :SUPPPRES_BSCDTN , :BSCON_REPPRES_NM , :BRAN_CD , :BILL_KND , :BSCON_FNM_NM )";
        sobj.setSql(query);
        sobj.setString("BSCON_FNM_NM", BSCON_FNM_NM);               //거래처 상호 명
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BSCON_REPPRES_NM", BSCON_REPPRES_NM);               //거래처 대표자 명
        sobj.setString("SUPPPRES_BSCDTN", SUPPPRES_BSCDTN);               //공급자_업태
        sobj.setString("SUPPPRES_INS_NUM", SUPPPRES_INS_NUM);               //공급자 등록 번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("SUPPPRES_NM", SUPPPRES_NM);               //공급자 명
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("SUPPPRES_REPPRES_NM", SUPPPRES_REPPRES_NM);               //공급자 대표자 명
        sobj.setString("BSCON_BSCDTN", BSCON_BSCDTN);               //거래처업태
        sobj.setString("BSCON_BSTYP", BSCON_BSTYP);               //거래처업종
        sobj.setString("SUPPPRES_BSTYP", SUPPPRES_BSTYP);               //공급자업종
        sobj.setString("BSCON_NM", BSCON_NM);               //거래처 명
        sobj.setString("BSCON_INS_NUM", BSCON_INS_NUM);               //거래처 등록 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("SUPPPRES_CD", SUPPPRES_CD);               //공급자_코드
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setString("SUPPPRES_FNM_NM", SUPPPRES_FNM_NM);               //공급자 상호 명
        sobj.setString("BSCON_POST_NUM", BSCON_POST_NUM);               //거래처 우편 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //임시컬럼1
        sobj.setString("BIPLC_GBN", BIPLC_GBN);               //TEMP10
        sobj.setString("BSCON_ADDR", BSCON_ADDR);               //임시컬럼2
        sobj.setString("INSPRES_ID", INSPRES_ID);               //임시컬럼3
        sobj.setString("ISS_DAY", ISS_DAY);               //임시컬럼5
        sobj.setString("ISS_YN", ISS_YN);               //임시
        sobj.setString("REMAK", REMAK);               //임시
        sobj.setString("SUPPPRES_ADDR", SUPPPRES_ADDR);               //임시
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //임시
        return sobj;
    }
    // 사용승인내역저장
    public DOBJ CALLcreateBill01tac_s02_2_INS31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS31");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_INS31(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS31") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_INS31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double CLR_NUM = 1;        //임시컬럼2
        String INS_DATE ="";        //임시컬럼4
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   CLR_NUM_1 = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //정산번호
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        String   BILL_NUM = dobj.getRetObject("SEL27").getRecord().get("BILL_NUM");   //임시컬럼1
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //임시컬럼3
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //임시컬럼5
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //임시
        String   TITLE_NM = dobj.getRetObject("R").getRecord().get("REMAK");   //임시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_USEAPPRVBRE (DEMD_NUM, INS_DATE, BILL_NUM, INSPRES_ID, CLR_NUM, BILL_GBN, SUPPTEMP_AMT, ISS_DAY, TITLE_NM, BILL_KND, APPRV_NUM)  \n";
        query +=" values(:DEMD_NUM , SYSDATE, :BILL_NUM , :INSPRES_ID , (SELECT NVL(MAX(CLR_NUM),0) + 1 AS CLR_NUM from FIDU.TTAC_USEAPPRVBRE WHERE SUBSTR(ISS_DAY,1,6)=SUBSTR(:CLR_NUM_1,1,6)), :BILL_GBN , :SUPPTEMP_AMT , :ISS_DAY , :TITLE_NM , :BILL_KND , :APPRV_NUM )";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("CLR_NUM_1", CLR_NUM_1);               //정산번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("BILL_NUM", BILL_NUM);               //임시컬럼1
        sobj.setString("INSPRES_ID", INSPRES_ID);               //임시컬럼3
        sobj.setString("ISS_DAY", ISS_DAY);               //임시컬럼5
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //임시
        sobj.setString("TITLE_NM", TITLE_NM);               //임시
        return sobj;
    }
    // 지부계산서출력저장
    public DOBJ CALLcreateBill01tac_s02_2_XIUD32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD32");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s02_2_XIUD32(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD32");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s02_2_XIUD32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPRV_NUM = dobj.getRetObject("R").getRecord().get("APPRV_NUM");   //승인 번호
        String   BILL_NUM = dobj.getRetObject("SEL27").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("R").getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BILL_ISS_DTL  \n";
        query +=" SET BILL_NUM = :BILL_NUM , ISS_COMPL_YN = '2'  \n";
        query +=" WHERE DEMD_NUM = :DEMD_NUM  \n";
        query +=" AND APPRV_NUM = :APPRV_NUM";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    //##**$$createBill01tac_s02_2
    //##**$$email_send_gibu
    /*
    */
    public DOBJ CTLemail_send_gibu(DOBJ dobj)
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
            dobj  = CALLemail_send_gibu_XIUD1(Conn, dobj);           //  이메일 전송 적용
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
    public DOBJ CTLemail_send_gibu( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLemail_send_gibu_XIUD1(Conn, dobj);           //  이메일 전송 적용
        return dobj;
    }
    // 이메일 전송 적용
    public DOBJ CALLemail_send_gibu_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLemail_send_gibu_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_gibu_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_KND = dobj.getRetObject("S").getRecord().get("BILL_KND");   //계산서 종류
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO amail.ems_mailqueue (seq , mail_code , to_email , to_name , from_email , from_name , subject , target_flag , map1 , map2 , map3 , map4 , map5 , map_content , reg_date) SELECT amail.ems_mailqueue_seq.NEXTVAL , DECODE (:BILL_KND, '3', '26', '30') , DECODE (:BILL_KND, '3', B.EMAIL_ADDR, C.EMAIL_ADDR) EMAILL_ADDR , DECODE (:BILL_KND, '3', B.HANMB_NM, C.UPSO_NM) HANMB_NM , 'acc1@komca.or.kr' , '한국음악저작권협회' , DECODE (:BILL_KND, '3', SUBSTR (A.BILL_NUM, 1, 4) || '년' || SUBSTR (A.BILL_NUM, 5, 2) || '월' || ' 수수료 계산서', SUBSTR (A.BILL_NUM, 1, 4) || '년' || SUBSTR (A.BILL_NUM, 5, 2) || '월' || '음악 사용료') , 'N' , NULL , A.BILL_NUM , NULL , :BILL_KND , SUBSTR (A.BILL_NUM, 1, 4) || '년 ' || SUBSTR (A.BILL_NUM, 5, 2) || '월' , 'TEMP' , SYSDATE FROM FIDU.TTAC_BILL A , FIDU.TMEM_MB B , (SELECT BB.BILL_NUM , EMAIL_ADDR , CC.UPSO_CD , CC.UPSO_NM FROM GIBU.TBRA_BILL_ISS_MST AA, GIBU.TBRA_BILL_ISS_DTL BB, GIBU.TBRA_UPSO CC WHERE AA.UPSO_CD = CC.UPSO_CD AND AA.DEMD_NUM = BB.DEMD_NUM) C WHERE A.BSCON_CD = B.MB_CD(+) AND A.BILL_NUM = C.BILL_NUM(+) AND A.BILL_NUM = :BILL_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    //##**$$email_send_gibu
    //##**$$deleteBill_tac01_s02
    /*
    */
    public DOBJ CTLdeleteBill_tac01_s02(DOBJ dobj)
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
            dobj  = CALLdeleteBill_tac01_s02_XIUD3(Conn, dobj);           //  지부계산서상태원복
            dobj  = CALLdeleteBill_tac01_s02_XIUD1(Conn, dobj);           //  지부계산서삭제
            dobj  = CALLdeleteBill_tac01_s02_XIUD2(Conn, dobj);           //  지부계산서승인삭제
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
    public DOBJ CTLdeleteBill_tac01_s02( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdeleteBill_tac01_s02_XIUD3(Conn, dobj);           //  지부계산서상태원복
        dobj  = CALLdeleteBill_tac01_s02_XIUD1(Conn, dobj);           //  지부계산서삭제
        dobj  = CALLdeleteBill_tac01_s02_XIUD2(Conn, dobj);           //  지부계산서승인삭제
        return dobj;
    }
    // 지부계산서상태원복
    public DOBJ CALLdeleteBill_tac01_s02_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdeleteBill_tac01_s02_XIUD3(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        rvobj.Println("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdeleteBill_tac01_s02_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" SET ISS_COMPL_YN ='1', BILL_NUM = NULL  \n";
        query +=" WHERE BILL_NUM = :BILL_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    // 지부계산서삭제
    public DOBJ CALLdeleteBill_tac01_s02_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdeleteBill_tac01_s02_XIUD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdeleteBill_tac01_s02_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_BILL  \n";
        query +=" WHERE BILL_NUM = :BILL_NUM  \n";
        query +=" AND DEMD_NUM = :DEMD_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    // 지부계산서승인삭제
    public DOBJ CALLdeleteBill_tac01_s02_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdeleteBill_tac01_s02_XIUD2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        rvobj.Println("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdeleteBill_tac01_s02_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_USEAPPRVBRE  \n";
        query +=" WHERE BILL_NUM = :BILL_NUM  \n";
        query +=" AND DEMD_NUM = :DEMD_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    //##**$$deleteBill_tac01_s02
    //##**$$del_gibu_bill
    /*
    */
    public DOBJ CTLdel_gibu_bill(DOBJ dobj)
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
            dobj  = CALLdel_gibu_bill_XIUD3(Conn, dobj);           //  지부계산서상태원복
            dobj  = CALLdel_gibu_bill_XIUD1(Conn, dobj);           //  지부계산서삭제
            dobj  = CALLdel_gibu_bill_XIUD2(Conn, dobj);           //  지부계산서승인삭제
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
    public DOBJ CTLdel_gibu_bill( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdel_gibu_bill_XIUD3(Conn, dobj);           //  지부계산서상태원복
        dobj  = CALLdel_gibu_bill_XIUD1(Conn, dobj);           //  지부계산서삭제
        dobj  = CALLdel_gibu_bill_XIUD2(Conn, dobj);           //  지부계산서승인삭제
        return dobj;
    }
    // 지부계산서상태원복
    public DOBJ CALLdel_gibu_bill_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdel_gibu_bill_XIUD3(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        rvobj.Println("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_gibu_bill_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BILL_ISS_DTL  \n";
        query +=" SET ISS_COMPL_YN ='1' , BILL_NUM = NULL  \n";
        query +=" WHERE BILL_NUM = :BILL_NUM  \n";
        query +=" AND DEMD_NUM = :DEMD_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    // 지부계산서삭제
    public DOBJ CALLdel_gibu_bill_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdel_gibu_bill_XIUD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_gibu_bill_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_BILL  \n";
        query +=" WHERE BILL_NUM = :BILL_NUM  \n";
        query +=" AND DEMD_NUM = :DEMD_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    // 지부계산서승인삭제
    public DOBJ CALLdel_gibu_bill_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdel_gibu_bill_XIUD2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        rvobj.Println("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_gibu_bill_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_USEAPPRVBRE  \n";
        query +=" WHERE BILL_NUM = :BILL_NUM  \n";
        query +=" AND DEMD_NUM = :DEMD_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    //##**$$del_gibu_bill
    //##**$$checkGibuBillData
    /* * 프로그램명 : tac01_s02
    * 작성자 : 손주환
    * 작성일 : 2009/10/21
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLcheckGibuBillData(DOBJ dobj)
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
            dobj  = CALLcheckGibuBillData_SEL1(Conn, dobj);           //  본사계산서생성전데이타유무체크
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
    public DOBJ CTLcheckGibuBillData( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcheckGibuBillData_SEL1(Conn, dobj);           //  본사계산서생성전데이타유무체크
        return dobj;
    }
    // 본사계산서생성전데이타유무체크
    public DOBJ CALLcheckGibuBillData_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcheckGibuBillData_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcheckGibuBillData_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("S").getRecord().get("BILL_KND");   //계산서 종류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT COUNT(BILL_NUM) AS BILL_CNT  ";
        query +=" FROM FIDU.TTAC_BILL  ";
        query +=" WHERE BILL_NUM LIKE SUBSTR(:DEMD_DAY,1,6)||'%'  ";
        query +=" AND BILL_KND IN(3,4,5,6)  ";
        if( !BILL_KND.equals("") )
        {
            query +=" AND BILL_KND = :BILL_KND  ";
        }
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        if(!BILL_KND.equals(""))
        {
            sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        }
        return sobj;
    }
    //##**$$checkGibuBillData
    //##**$$EventID28
    /*
    */
    public DOBJ CTLEventID28(DOBJ dobj)
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
            dobj  = CALLEventID28_SEL1(Conn, dobj);           //  출판사 수수료계산서 주소출력
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
    public DOBJ CTLEventID28( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID28_SEL1(Conn, dobj);           //  출판사 수수료계산서 주소출력
        return dobj;
    }
    // 출판사 수수료계산서 주소출력
    public DOBJ CALLEventID28_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID28_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID28_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  POST_RCPT_NM,  ADDR  ADDR,  ADDR_DETED,  POST_NUM,  MAX(A.MB_CD)  MB_CD,  B.INS_NUM  FROM  FIDU.TMEM_ADBK  A,  FIDU.TMEM_MB  B,  FIDU.TTAC_COPYRTAL  C  WHERE  ADDR_GBN  =  '2'   \n";
        query +=" AND  NVL(POSTSNDBK_YN,'  ')  <>  '1'   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.MB_CD  =  C.MB_CD   \n";
        query +=" AND  MB_GBN1  IN('F','P','B',  'C'  )   \n";
        query +=" AND  SUPPBRE_POST_RECV_YN  ='E'   \n";
        query +=" AND  C.SUPP_YRMN  =:SUPP_YRMN  GROUP  BY  POST_RCPT_NM,  ADDR  ,  ADDR_DETED,  POST_NUM,  B.INS_NUM  ORDER  BY  B.INS_NUM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$EventID28
    //##**$$EventID31
    /*
    */
    public DOBJ CTLEventID31(DOBJ dobj)
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
            dobj  = CALLEventID31_XIUD1(Conn, dobj);           //  이메일 전송 적용
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
    public DOBJ CTLEventID31( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID31_XIUD1(Conn, dobj);           //  이메일 전송 적용
        return dobj;
    }
    // 이메일 전송 적용
    public DOBJ CALLEventID31_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLEventID31_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID31_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO amail.ems_mailqueue (seq, mail_code, to_email, to_name, from_email, from_name, subject, target_flag, map1, map2, map3, map4, map5, map_content, reg_date ) SELECT amail.ems_mailqueue_seq.nextval,'26',B.EMAIL_ADDR, B.HANMB_NM , 'acc1@komca.or.kr' , '한국음악저작권협회' , SUBSTR(A.BILL_NUM,1,4)||'년'||SUBSTR(A.BILL_NUM,5,2) ||'월'||' 수수료 계산서' , 'N' , 'I', A.BILL_NUM, B.MB_CD , A.BILL_KND, SUBSTR(A.BILL_NUM,1,4)||'년 '||SUBSTR(A.BILL_NUM,5,2)||'월', 'TEMP' , SYSDATE FROM FIDU.TTAC_BILL A, FIDU.TMEM_MB B WHERE A.BSCON_CD = B.MB_CD(+) AND A.BILL_NUM = :BILL_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    //##**$$EventID31
    //##**$$end
}
