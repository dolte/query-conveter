
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_s03
{
    public tac01_s03()
    {
    }
    //##**$$saveTac01_s03
    /* * 프로그램명 : tac01_s03
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsaveTac01_s03(DOBJ dobj)
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
            dobj  = CALLsaveTac01_s03_UPD1(Conn, dobj);           //  전표계산서 관리저장
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
    public DOBJ CTLsaveTac01_s03( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveTac01_s03_UPD1(Conn, dobj);           //  전표계산서 관리저장
        return dobj;
    }
    // 전표계산서 관리저장
    // 계산서 발행요청 여부 와 회계전표 발행 여부 를 수정해준다
    public DOBJ CALLsaveTac01_s03_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveTac01_s03_UPD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveTac01_s03_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_ISSREQ_YN = dvobj.getRecord().get("BILL_ISSREQ_YN");   //공급자 주소
        String   ACCTNCHIT_ISS_YN = dvobj.getRecord().get("ACCTNCHIT_ISS_YN");   //회계전표발생여부
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TLEV_USEDEMDREPT  \n";
        query +=" set ACCTNCHIT_ISS_YN=:ACCTNCHIT_ISS_YN , BILL_ISSREQ_YN=:BILL_ISSREQ_YN  \n";
        query +=" where DEMD_NUM=:DEMD_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_ISSREQ_YN", BILL_ISSREQ_YN);               //공급자 주소
        sobj.setString("ACCTNCHIT_ISS_YN", ACCTNCHIT_ISS_YN);               //회계전표발생여부
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    //##**$$saveTac01_s03
    //##**$$searchTac01_s03
    /* * 프로그램명 : tac01_s03
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchTac01_s03(DOBJ dobj)
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
            dobj  = CALLsearchTac01_s03_SEL1(Conn, dobj);           //  전표계산서관리조회
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
    public DOBJ CTLsearchTac01_s03( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchTac01_s03_SEL1(Conn, dobj);           //  전표계산서관리조회
        return dobj;
    }
    // 전표계산서관리조회
    public DOBJ CALLsearchTac01_s03_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchTac01_s03_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchTac01_s03_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YYMN = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //DEMD_YYMN
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEMD_NUM,  BILL_KND,  BILL_GBN,  DEMD_DAY,  BRAN_CD,  BSCON_CD,  BSCON_NM,  BSCON_INS_NUM,  BSCON_FNM_NM,  BSCON_REPPRES_NM,  BSCON_POST_NUM,  BSCON_ADDR,  BSCON_ADDR_DETED,  BSCON_BSTYP_CTENT,  BSCON_BSCDTN_CTENT,  SUPPPRES_CD,  SUPPPRES_NM,  SUPPPRES_INS_NUM,  SUPPPRES_FNM_NM,  SUPPPRES_REPPRES_NM,  SUPPPRES_POST_NUM,  SUPPPRES_ADDR,  SUPPPRES_ADDR_DETED,  SUPPPRES_BSTYP_CTENT,  SUPPPRES_BSCDTN_CTENT,  OCC_AMT,  ATAX_AMT,  BILL_ISSREQ_YN,  ACCTNCHIT_ISS_YN  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.DEMD_NUM)  AS  DEMD_NUM,  '1'  AS  BILL_KND,  (CASE  WHEN  A.REPT_AMT  >  0  THEN  '1'  ELSE  '2'  END)  AS  BILL_GBN,  A.DEMD_DAY  AS  DEMD_DAY,  A.BRAN_CD  AS  BRAN_CD,  B.BSCON_CD  AS  BSCON_CD,  B.BSCONHAN_NM  AS  BSCON_NM,  B.INS_NUM  AS  BSCON_INS_NUM,  B.BSCONHAN_NM  AS  BSCON_FNM_NM,  B.REPPRES_NM  AS  BSCON_REPPRES_NM,  B.POST_NUM  AS  BSCON_POST_NUM,  B.ADDR  AS  BSCON_ADDR,  B.ADDR_DETED  AS  BSCON_ADDR_DETED,  B.BSTYP_CTENT  AS  BSCON_BSTYP_CTENT,  B.BSCDTN_CTENT  AS  BSCON_BSCDTN_CTENT,  C.BSCON_CD  AS  SUPPPRES_CD,  C.BSCONHAN_NM  AS  SUPPPRES_NM,  C.INS_NUM  AS  SUPPPRES_INS_NUM,  C.BSCONHAN_NM  AS  SUPPPRES_FNM_NM,  C.REPPRES_NM  AS  SUPPPRES_REPPRES_NM,  C.POST_NUM  AS  SUPPPRES_POST_NUM,  C.ADDR  AS  SUPPPRES_ADDR,  C.ADDR_DETED  AS  SUPPPRES_ADDR_DETED,  C.BSTYP_CTENT  AS  SUPPPRES_BSTYP_CTENT,  C.BSCDTN_CTENT  AS  SUPPPRES_BSCDTN_CTENT,  (CASE  WHEN  A.REPT_AMT  >  0  THEN  A.REPT_AMT  ELSE  A.DEMD_AMT  END)  AS  OCC_AMT,  A.ATAX_AMT  AS  ATAX_AMT,  A.BILL_ISSREQ_YN  AS  BILL_ISSREQ_YN,  A.ACCTNCHIT_ISS_YN  AS  ACCTNCHIT_ISS_YN  FROM  FIDU.TLEV_USEDEMDREPT  A,  FIDU.TLEV_BSCON  B,  FIDU.TLEV_BSCON  C  WHERE  A.BSCON_CD  =  B.BSCON_CD   \n";
        query +=" AND  C.BSCON_CD  =  'C0438'  UNION  ALL   \n";
        query +=" SELECT  TO_CHAR(A.DEMD_NUM)  AS  DEMD_NUM,  '2'  AS  BILL_KND,  '2'  AS  BILL_GBN,  MAX(A.DEMD_DAY)  AS  DEMD_DAY,  MAX(A.BRAN_CD)  AS  BRAN_CD,  A.BSCON_CD  AS  BSCON_CD,  MAX(F.BSCONHAN_NM)  AS  BSCONHAN_NM,  MAX(F.INS_NUM)  AS  BSCON_INS_NUM,  MAX(F.BSCONHAN_NM)  AS  BSCON_FNM_NM,  MAX(F.REPPRES_NM)  AS  BSCON_REPPRES_NM,  MAX(F.POST_NUM)  AS  BSOCN_POST_NUM,  MAX(F.ADDR)  AS  BSCON_ADDR,  MAX(F.ADDR_DETED)  AS  BSCON_ADDR_DETED,  MAX(F.BSTYP_CTENT)  AS  BSCON_BSTYP_CTENT,  MAX(F.BSCDTN_CTENT)  AS  BSCON_BSCDTN_CTENT,  MAX(C.RIGHTPRES_MB_CD)  AS  SUPPPRES_CD,  MAX(D.HANMB_NM)  AS  SUPPPRES_NM,  MAX(D.INS_NUM)  AS  SUPPPRES_INS_NUM,  MAX(D.HANMB_NM)  AS  SUPPPRES_FNM_NM,  MAX(D.REPPRES_NM)  AS  SUPPPRES_REPPRES_NM,  MAX(E.POST_NUM)  AS  SUPPPRES_POST_NUM,  MAX(E.ADDR)  AS  SUPPPRES_ADDR,  MAX(E.ADDR_DETED)  AS  SUPPPRES_ADDR_DETED,  MAX(D.BSTYP_CTENT)  AS  SUPPPRES_BSTYP_CTENT,  MAX(D.BSCDTN_CTENT)  AS  SUPPPRES_BSCDTN_CTENT,  SUM(C.OCC_AMT)  AS  OCC_AMT,  SUM(C.ATAX_AMT)  AS  ATAX_AMT,  MAX(A.BILL_ISSREQ_YN)  AS  BILL_ISSREQ_YN,  MAX(A.ACCTNCHIT_ISS_YN)  AS  ACCTNCHIT_ISS_YN  FROM  FIDU.TLEV_USEDEMDREPT  A,  FIDU.TLEV_CLRREC  B,  FIDU.TLEV_RIGHTPRESOCCAMT  C,  FIDU.TMEM_MB  D,   \n";
        query +=" (SELECT  MB_CD,  POST_NUM,  ADDR,  ADDR_DETED  FROM  FIDU.TMEM_ADBK  WHERE  ADDR_GBN  =  '2')  E,  FIDU.TLEV_BSCON  F  WHERE  1  =  1   \n";
        query +=" AND  A.DEMD_NUM  =  B.DEMD_NUM   \n";
        query +=" AND  B.APPRV_NUM  =  C.APPRV_NUM   \n";
        query +=" AND  B.CLR_NUM  =  C.CLR_NUM   \n";
        query +=" AND  C.RIGHTPRES_MB_CD  =  D.MB_CD   \n";
        query +=" AND  D.MB_CD  =  E.MB_CD(+)   \n";
        query +=" AND  D.MB_GBN1  =  'F'   \n";
        query +=" AND  A.BSCON_CD  =  F.BSCON_CD  GROUP  BY  A.BSCON_CD,  A.DEMD_NUM  )  WHERE  1  =  1   \n";
        query +=" AND  DEMD_DAY  BETWEEN  :DEMD_YYMN  ||'01'   \n";
        query +=" AND  :DEMD_YYMN  ||  '31'  ORDER  BY  DEMD_NUM  ASC ";
        sobj.setSql(query);
        sobj.setString("DEMD_YYMN", DEMD_YYMN);               //DEMD_YYMN
        return sobj;
    }
    //##**$$searchTac01_s03
    //##**$$end
}
