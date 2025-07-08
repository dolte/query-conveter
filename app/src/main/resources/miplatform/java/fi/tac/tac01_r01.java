
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_r01
{
    public tac01_r01()
    {
    }
    //##**$$bill_select
    /* * 프로그램명 : tac01_r01
    * 작성자 : 송유빈
    * 작성일 : 2009/08/05
    * 설명 : 계산서 발행내역을 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbill_select(DOBJ dobj)
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
            dobj  = CALLbill_select_SEL1(Conn, dobj);           //  계산서조회
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
    public DOBJ CTLbill_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbill_select_SEL1(Conn, dobj);           //  계산서조회
        return dobj;
    }
    // 계산서조회
    // 계산서조회
    public DOBJ CALLbill_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbill_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //종료조건
        String   BILL_GBN = dobj.getRetObject("S").getRecord().get("BILL_GBN");   //계산서 구분
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //시작조건
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BILL_NUM, FIDU.GET_CODE_NM('00216', BILL_GBN) AS BILL_GBN, BSCON_CD, FIDU.GET_BSCON_NM(BSCON_CD) AS BSCON_NM, OCC_AMT, OCC_DAY, OCC_CTENT, ISS_DAY, ISS_CTENT, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE  ";
        query +=" FROM FIDU.TTAC_BILL  ";
        query +=" WHERE 1 = 1  ";
        if( !BILL_NUM.equals("") )
        {
            query +=" AND BILL_NUM = :BILL_NUM  ";
        }
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        if( !FROM.equals("") )
        {
            query +=" AND ISS_DAY >= :FROM  ";
        }
        if( !TO.equals("") )
        {
            query +=" AND ISS_DAY <= :TO  ";
        }
        if( !BILL_GBN.equals("") )
        {
            query +=" AND BILL_GBN = :BILL_GBN  ";
        }
        query +=" ORDER BY BILL_NUM DESC  ";
        sobj.setSql(query);
        if(!BILL_NUM.equals(""))
        {
            sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        }
        if(!TO.equals(""))
        {
            sobj.setString("TO", TO);               //종료조건
        }
        if(!BILL_GBN.equals(""))
        {
            sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        }
        if(!FROM.equals(""))
        {
            sobj.setString("FROM", FROM);               //시작조건
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    //##**$$bill_select
    //##**$$searchDetail
    /* * 프로그램명 : tac01_r01
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchDetail(DOBJ dobj)
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
            dobj  = CALLsearchDetail_SEL1(Conn, dobj);           //  계
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
    public DOBJ CTLsearchDetail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchDetail_SEL1(Conn, dobj);           //  계
        return dobj;
    }
    // 계
    public DOBJ CALLsearchDetail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchDetail_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchDetail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구서번호
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_NUM,  A.BILL_NUM,  A.ISS_DAY,  A.APPRV_NUM,  A.BILL_KND,  A.BILL_GBN,  A.MDM_CD,  A.MDM_NM  MDM_CD_NM,  A.SVC_CD,  A.SVC_NM,  A.TITLE_NM,  A.QTY_PNUM,  A.CTST_ID,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00231'   \n";
        query +=" AND  CODE_CD  =  TRIM(A.CTST_KND))  AS  CTST_KND,  A.CTST_START_NUM,  A.CTST_END_NUM,  A.SUPPTEMP_AMT,  11  -  LENGTH(  A.SUPPTEMP_AMT)  GONG_LEN,  A.ATAX_AMT  FROM  FIDU.TTAC_USEAPPRVBRE  A  WHERE  1  =  1   \n";
        query +=" AND  A.BILL_NUM  =  :BILL_NUM   \n";
        query +=" AND  A.DEMD_NUM  LIKE  :DEMD_NUM||'%'  ORDER  BY  A.APPRV_NUM,  A.USE_TTL,  A.CLR_YRMN,  A.SVC_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    //##**$$searchDetail
    //##**$$searchMst
    /* * 프로그램명 : tac01_r01
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
            dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  계산서마스타조회
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
        dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  계산서마스타조회
        return dobj;
    }
    // 계산서마스타조회
    // 청구년월로 조회
    public DOBJ CALLsearchMst_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구서번호
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   SUPPPRES_FNM_NM = dobj.getRetObject("S").getRecord().get("SUPPPRES_FNM_NM");   //공급자 상호 명
        String   BILL_KND = dobj.getRetObject("S").getRecord().get("BILL_KND");   //계산서 종류
        String   BSCON_FNM_NM = dvobj.getRecord().get("BSCON_FNM_NM");   //거래처 상호 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.DEMD_NUM, A.BILL_NUM, A.ISS_DAY, A.BILL_KND, A.BILL_GBN, A.BSCON_CD, A.BIPLC_GBN, A.BRAN_CD, A.BSCON_NM, A.SUPPPRES_NM, A.BSCON_INS_NUM, A.BSCON_FNM_NM, A.BSCON_REPPRES_NM, A.BSCON_POST_NUM, A.BSCON_ADDR, A.BSCON_BSCDTN, A.BSCON_BSTYP, A.SUPPPRES_CD, A.SUPPPRES_INS_NUM, A.SUPPPRES_FNM_NM, A.SUPPPRES_REPPRES_NM, A.SUPPPRES_ADDR, A.SUPPPRES_BSCDTN, A.SUPPPRES_BSTYP, A.SUPPTEMP_AMT, A.ATAX_AMT, A.REMAK, A.ISS_YN, A.INSPRES_ID, A.INS_DATE, (SELECT MAX(MDM_CD) MDM_CD  ";
        query +=" FROM FIDU.TTAC_USEAPPRVBRE  ";
        query +=" WHERE BILL_NUM = A.BILL_NUM  ";
        query +=" AND DEMD_NUM = A.DEMD_NUM) MDM_CD, A.MODPRES_ID, A.ELEC_SEND_YN, 11 - LENGTH( A.SUPPTEMP_AMT) GONG_LEN, A.MOD_DATE  ";
        query +=" FROM FIDU.TTAC_BILL A  ";
        query +=" WHERE SUBSTR(A.ISS_DAY,1,6) = :DEMD_DAY  ";
        query +=" AND A.BILL_KND IN (1,2)  ";
        query +=" AND A.DEL_YN IS NULL  ";
        query +=" AND A.BILL_KND LIKE :BILL_KND  ";
        query +=" AND A.BSCON_FNM_NM LIKE '%'|| :BSCON_FNM_NM ||'%'  ";
        query +=" AND A.SUPPPRES_FNM_NM LIKE '%'|| :SUPPPRES_FNM_NM ||'%'  ";
        query +=" AND A.DEMD_NUM LIKE :DEMD_NUM ||'%'  ";
        if( !BILL_NUM.equals("") )
        {
            query +=" AND A.BILL_NUM = :BILL_NUM  ";
        }
        query +=" ORDER BY A.ISS_DAY DESC, A.DEMD_NUM ASC  ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        if(!BILL_NUM.equals(""))
        {
            sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        }
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("SUPPPRES_FNM_NM", SUPPPRES_FNM_NM);               //공급자 상호 명
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BSCON_FNM_NM", BSCON_FNM_NM);               //거래처 상호 명
        return sobj;
    }
    //##**$$searchMst
    //##**$$end
}
